/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.responsive.image.ImageResource;
import com.liferay.responsive.image.ResponsiveImage;
import com.liferay.responsive.image.ResponsiveImageCandidate;
import com.liferay.responsive.image.ResponsiveImageRequest;
import com.liferay.responsive.image.ResponsiveImageSource;
import com.liferay.responsive.image.ResponsiveImageURLTransformer;
import com.liferay.responsive.image.ResponsiveImageURLTransformerProvider;
import com.liferay.responsive.image.internal.configuration.ResponsiveImageConfigurationHelper;
import com.liferay.responsive.image.internal.configuration.ResponsiveImageSettings;
import com.liferay.responsive.image.internal.configuration.ResponsiveImageSettingsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageFactory {

	public ResponsiveImageFactory(
		Portal portal,
		ResponsiveImageConfigurationHelper responsiveImageConfigurationHelper,
		ResponsiveImageURLTransformerProvider
			responsiveImageURLTransformerProvider) {

		_portal = portal;
		_responsiveImageConfigurationHelper =
			responsiveImageConfigurationHelper;
		_responsiveImageURLTransformerProvider =
			responsiveImageURLTransformerProvider;

		_responsiveImageSettingsProvider = new ResponsiveImageSettingsProvider(
			responsiveImageConfigurationHelper);
	}

	public ResponsiveImage create(
		ResponsiveImageRequest responsiveImageRequest) {

		ImageResource imageResource = responsiveImageRequest.getImageResource();

		if (!_isSupported(imageResource)) {
			return null;
		}

		long companyId = _responsiveImageConfigurationHelper.getCompanyId(
			responsiveImageRequest);

		ResponsiveImageSettings responsiveImageSettings =
			_responsiveImageSettingsProvider.getResponsiveImageSettings(
				companyId,
				_responsiveImageConfigurationHelper.getGroupId(
					responsiveImageRequest));

		if ((responsiveImageSettings == null) ||
			!responsiveImageSettings.isEnabled()) {

			return null;
		}

		ResponsiveImageURLTransformer responsiveImageURLTransformer =
			_responsiveImageURLTransformerProvider.
				getResponsiveImageURLTransformer(
					responsiveImageSettings.
						getResponsiveImageURLTransformerName());

		if (responsiveImageURLTransformer == null) {
			return null;
		}

		String deliveryURL = _getDeliveryURL(
			responsiveImageSettings.getCDNHost(), companyId,
			imageResource.getURL());

		if (deliveryURL == null) {
			return null;
		}

		ResponsiveImageSettings.Preset preset =
			responsiveImageSettings.getPreset(
				responsiveImageRequest.getPresetName());

		return ResponsiveImage.builder(
		).lazyLoading(
			preset.isLazyLoading()
		).sources(
			_getResponsiveImageSources(
				responsiveImageSettings.getCandidateWidths(),
				responsiveImageSettings.getDefaultTransformations(),
				responsiveImageURLTransformer, deliveryURL,
				preset.getVariants())
		).build();
	}

	/**
	 * Returns the image URL behind the CDN host the transformed images are
	 * delivered by, or <code>null</code> when the image names a host the
	 * configured one is not.
	 *
	 * <p>
	 * The given CDN host wins, so images can be delivered by an image
	 * optimization service that fronts nothing else. Absent it, the company's
	 * own CDN host is used.
	 * </p>
	 *
	 * <p>
	 * An image the portal is itself served by is taken back to its path and
	 * delivered like any other, whichever host it happens to name. See
	 * {@link #_getPortalPath(long, String)} for what counts as one.
	 * </p>
	 *
	 * <p>
	 * An image that names a host the portal is not served by is left alone,
	 * there being no telling whether that host is already a CDN. It is
	 * declined only when a host is configured and is not the one naming it,
	 * since transforming it would then ask a CDN for an image it does not
	 * front. A protocol relative URL names a host too, so it counts as one of
	 * these rather than as a path that merely begins with a slash.
	 * </p>
	 *
	 * @param  cdnHost the host to deliver by, or <code>null</code> for the
	 *         company's own
	 */
	private String _getDeliveryURL(String cdnHost, long companyId, String url) {
		if (!url.startsWith(StringPool.SLASH) ||
			url.startsWith(StringPool.DOUBLE_SLASH)) {

			String path = _getPortalPath(companyId, url);

			if (path == null) {
				if (Validator.isBlank(cdnHost) ||
					Objects.equals(
						HttpComponentsUtil.getDomain(cdnHost),
						HttpComponentsUtil.getDomain(url))) {

					return url;
				}

				return null;
			}

			url = path;
		}

		if (Validator.isBlank(cdnHost)) {
			cdnHost = _portal.getCDNHostHttps(companyId);
		}

		if (Validator.isBlank(cdnHost)) {
			cdnHost = _portal.getCDNHostHttp(companyId);
		}

		if (Validator.isBlank(cdnHost)) {
			return url;
		}

		return _stripTrailingSlash(cdnHost) + url;
	}

	/**
	 * Returns the portal's own path behind an absolute URL, or
	 * <code>null</code> when the host naming it is not one the portal is
	 * served by.
	 *
	 * <p>
	 * The portal is asked which domains are its own, so the virtual hosts of
	 * the company and of its sites count, and so do the company's own CDN
	 * hosts, since a URL names one because something put it there rather than
	 * because that is where the image lives.
	 * </p>
	 */
	private String _getPortalPath(long companyId, String url) {
		if (_portal.isValidPortalDomain(
				companyId, HttpComponentsUtil.getDomain(url))) {

			return HttpComponentsUtil.removeDomain(url);
		}

		String path = _stripCDNHost(_portal.getCDNHostHttps(companyId), url);

		if (path != null) {
			return path;
		}

		return _stripCDNHost(_portal.getCDNHostHttp(companyId), url);
	}

	private List<ResponsiveImageCandidate> _getResponsiveImageCandidates(
		Map<String, String> imageTransformations,
		ResponsiveImageURLTransformer responsiveImageURLTransformer, String url,
		List<Integer> widths) {

		return TransformUtil.transform(
			widths,
			width -> ResponsiveImageCandidate.builder(
				responsiveImageURLTransformer.transform(
					url,
					HashMapBuilder.putAll(
						imageTransformations
					).put(
						"width", String.valueOf(width)
					).build())
			).width(
				width
			).build());
	}

	private List<ResponsiveImageSource> _getResponsiveImageSources(
		TreeSet<Integer> candidateWidths,
		Map<String, String> defaultTransformations,
		ResponsiveImageURLTransformer responsiveImageURLTransformer, String url,
		List<ResponsiveImageSettings.Variant> variants) {

		return TransformUtil.transform(
			variants,
			variant -> {
				List<Integer> widths = _getWidths(
					candidateWidths, variant.getMaxWidth());

				if (widths.isEmpty()) {
					return null;
				}

				return ResponsiveImageSource.builder(
				).candidates(
					_getResponsiveImageCandidates(
						HashMapBuilder.putAll(
							defaultTransformations
						).putAll(
							variant.getTransformations()
						).build(),
						responsiveImageURLTransformer, url, widths)
				).query(
					variant.getQuery()
				).sizes(
					variant.getSizes()
				).build();
			});
	}

	private List<Integer> _getWidths(
		TreeSet<Integer> candidateWidths, Integer maxWidth) {

		List<Integer> widths = new ArrayList<>(candidateWidths.size());

		for (Integer width : candidateWidths) {
			widths.add(width);

			if ((maxWidth != null) && (width >= maxWidth)) {
				break;
			}
		}

		return widths;
	}

	private boolean _isSupported(ImageResource imageResource) {
		if ((imageResource == null) ||
			!_supportedMimeTypes.contains(imageResource.getMimeType()) ||
			Validator.isBlank(imageResource.getURL())) {

			return false;
		}

		return true;
	}

	/**
	 * Returns the URL without the given host, or <code>null</code> when the URL
	 * does not name it. The host must end where the path begins, so that a URL
	 * merely starting with it is never mistaken for one it serves.
	 */
	private String _stripCDNHost(String cdnHost, String url) {
		if (Validator.isBlank(cdnHost)) {
			return null;
		}

		cdnHost = _stripTrailingSlash(cdnHost);

		if (!url.startsWith(cdnHost + StringPool.SLASH)) {
			return null;
		}

		return url.substring(cdnHost.length());
	}

	private String _stripTrailingSlash(String url) {
		if (url.endsWith(StringPool.SLASH)) {
			return url.substring(0, url.length() - 1);
		}

		return url;
	}

	/**
	 * Names the raster formats a rendition can be generated from.
	 */
	private static final Set<String> _supportedMimeTypes = SetUtil.fromArray(
		"image/bmp", "image/gif", "image/jpeg", "image/pjpeg", "image/png",
		"image/webp", "image/x-citrix-jpeg", "image/x-citrix-png",
		"image/x-ms-bmp", "image/x-png");

	private final Portal _portal;
	private final ResponsiveImageConfigurationHelper
		_responsiveImageConfigurationHelper;
	private final ResponsiveImageSettingsProvider
		_responsiveImageSettingsProvider;
	private final ResponsiveImageURLTransformerProvider
		_responsiveImageURLTransformerProvider;

}