/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.responsive.image.ImageResource;
import com.liferay.responsive.image.ResponsiveImage;
import com.liferay.responsive.image.ResponsiveImageCandidate;
import com.liferay.responsive.image.ResponsiveImageMarkupRenderer;
import com.liferay.responsive.image.ResponsiveImageRequest;
import com.liferay.responsive.image.ResponsiveImageSource;
import com.liferay.responsive.image.ResponsiveImageURLTransformerProvider;
import com.liferay.responsive.image.internal.configuration.ResponsiveImageConfigurationHelper;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(service = ResponsiveImageMarkupRenderer.class)
public class ResponsiveImageMarkupRendererImpl
	implements ResponsiveImageMarkupRenderer {

	@Override
	public String render(
			String originalImgTag,
			ResponsiveImageRequest responsiveImageRequest)
		throws PortalException {

		if ((responsiveImageRequest == null) ||
			!FeatureFlagManagerUtil.isEnabled(
				_responsiveImageConfigurationHelper.getCompanyId(
					responsiveImageRequest),
				"LPD-94784")) {

			return null;
		}

		ImageResource imageResource = responsiveImageRequest.getImageResource();

		if (imageResource == null) {
			return null;
		}

		ResponsiveImage responsiveImage = _responsiveImageFactory.create(
			responsiveImageRequest);

		if (responsiveImage == null) {
			return null;
		}

		List<ResponsiveImageSource> responsiveImageSources =
			responsiveImage.getSources();

		if (responsiveImageSources.isEmpty()) {
			return null;
		}

		if (_isPictureElementRequired(responsiveImageSources)) {
			return _renderPicture(
				responsiveImageSources, responsiveImage.isLazyLoading(),
				originalImgTag);
		}

		return _renderImg(
			responsiveImageSources.get(0), responsiveImage.isLazyLoading(),
			originalImgTag);
	}

	@Activate
	protected void activate() {
		_responsiveImageConfigurationHelper =
			new ResponsiveImageConfigurationHelper(
				_configurationProvider, _portal);

		_responsiveImageFactory = new ResponsiveImageFactory(
			_portal, _responsiveImageConfigurationHelper,
			_responsiveImageURLTransformerProvider);
	}

	/**
	 * Returns the value of the named attribute of the image tag, or
	 * <code>null</code> when the tag does not carry it.
	 */
	private String _getAttributeValue(String imgTag, String name) {
		int index = _getEqualIndex(imgTag, name);

		if (index == -1) {
			return null;
		}

		index++;

		while ((index < imgTag.length()) &&
			   Character.isWhitespace(imgTag.charAt(index))) {

			index++;
		}

		if (index == imgTag.length()) {
			return null;
		}

		char quote = imgTag.charAt(index);

		if ((quote != CharPool.APOSTROPHE) && (quote != CharPool.QUOTE)) {
			return null;
		}

		int endIndex = imgTag.indexOf(quote, index + 1);

		if (endIndex == -1) {
			return null;
		}

		return imgTag.substring(index + 1, endIndex);
	}

	/**
	 * Returns the index of the equal sign of the named attribute of the image
	 * tag, or <code>-1</code> when the tag does not carry it.
	 *
	 * <p>
	 * The name ends where HTML says it ends, so an attribute whose name merely
	 * ends with the one asked for, as <code>data-loading</code> does with
	 * <code>loading</code>, is never mistaken for it.
	 * </p>
	 */
	private int _getEqualIndex(String imgTag, String name) {
		int index = imgTag.indexOf(name);

		while (index != -1) {
			if ((index == 0) ||
				Character.isWhitespace(imgTag.charAt(index - 1))) {

				int endIndex = index + name.length();

				while ((endIndex < imgTag.length()) &&
					   Character.isWhitespace(imgTag.charAt(endIndex))) {

					endIndex++;
				}

				if ((endIndex < imgTag.length()) &&
					(imgTag.charAt(endIndex) == CharPool.EQUAL)) {

					return endIndex;
				}
			}

			index = imgTag.indexOf(name, index + name.length());
		}

		return -1;
	}

	/**
	 * Returns the index of the image tag, or <code>-1</code> when the markup
	 * holds none.
	 *
	 * <p>
	 * The tag name ends where HTML says it ends, at whitespace, at
	 * <code>/</code> or at <code>&gt;</code>, so that an element whose name
	 * merely starts with <code>img</code> is never mistaken for one.
	 * </p>
	 */
	private int _getImgTagIndex(String imgTag) {
		int index = imgTag.indexOf(_IMG_TAG_START);

		while (index != -1) {
			int endIndex = index + _IMG_TAG_START.length();

			if (endIndex < imgTag.length()) {
				char c = imgTag.charAt(endIndex);

				if ((c == CharPool.GREATER_THAN) || (c == CharPool.SLASH) ||
					Character.isWhitespace(c)) {

					return index;
				}
			}

			index = imgTag.indexOf(_IMG_TAG_START, endIndex);
		}

		return -1;
	}

	/**
	 * Returns the <code>sizes</code> value to render with, without the
	 * <code>auto</code> an administrator declared where it would not be valid.
	 *
	 * <p>
	 * Browsers honor <code>auto</code> only on a lazily loaded image element,
	 * and only measure one that already has an aspect ratio to measure.
	 * Whether a page gives it one, by the tag's own width and height or by its
	 * style, is the administrator's to know and not this renderer's to guess,
	 * so <code>auto</code> is never added here. It is taken away where the
	 * markup alone says it is invalid.
	 * </p>
	 *
	 * <p>
	 * A value of <code>auto</code> alone leaves nothing to render once it is
	 * taken away, and an absent <code>sizes</code> renders the same as one a
	 * browser cannot read, so it is answered with nothing at all.
	 * </p>
	 */
	private String _getSizes(boolean lazyLoading, String sizes) {
		if (Validator.isBlank(sizes)) {
			return sizes;
		}

		int index = sizes.indexOf(CharPool.COMMA);

		String firstSize = sizes;

		if (index != -1) {
			firstSize = sizes.substring(0, index);
		}

		firstSize = StringUtil.toLowerCase(StringUtil.trim(firstSize));

		if (lazyLoading || !firstSize.equals("auto")) {
			return sizes;
		}

		if (index == -1) {
			return StringPool.BLANK;
		}

		return StringUtil.trim(sizes.substring(index + 1));
	}

	private String _getSrcSet(
		List<ResponsiveImageCandidate> responsiveImageCandidates) {

		StringBundler sb = new StringBundler(
			responsiveImageCandidates.size() * 4);

		for (ResponsiveImageCandidate responsiveImageCandidate :
				responsiveImageCandidates) {

			if (responsiveImageCandidate.getWidth() == null) {
				continue;
			}

			if (sb.index() > 0) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			sb.append(responsiveImageCandidate.getURL());
			sb.append(StringPool.SPACE);
			sb.append(responsiveImageCandidate.getWidth());
			sb.append("w");
		}

		return sb.toString();
	}

	private String _injectAttributes(String imgTag, String attributes) {
		int index = _getImgTagIndex(imgTag);

		if (index == -1) {
			return imgTag;
		}

		int endIndex = index + _IMG_TAG_START.length();

		return StringBundler.concat(
			imgTag.substring(0, endIndex), StringPool.SPACE, attributes,
			imgTag.substring(endIndex));
	}

	private boolean _isPictureElementRequired(
		List<ResponsiveImageSource> responsiveImageSources) {

		for (ResponsiveImageSource responsiveImageSource :
				responsiveImageSources) {

			if (!Validator.isBlank(responsiveImageSource.getQuery())) {
				return true;
			}
		}

		return false;
	}

	private String _renderImg(
		ResponsiveImageSource responsiveImageSource, boolean lazyLoading,
		String originalImgTag) {

		StringBundler sb = new StringBundler(7);

		sb.append("srcset=\"");
		sb.append(
			HtmlUtil.escapeAttribute(
				_getSrcSet(responsiveImageSource.getCandidates())));
		sb.append("\"");

		// An author who already set loading has decided for this image, so the
		// preset does not overrule it. The value they set still decides
		// whether the image is lazily loaded.

		int loadingIndex = _getEqualIndex(originalImgTag, "loading");

		if (loadingIndex != -1) {
			lazyLoading = StringUtil.equalsIgnoreCase(
				"lazy", _getAttributeValue(originalImgTag, "loading"));
		}

		String sizes = _getSizes(lazyLoading, responsiveImageSource.getSizes());

		if (!Validator.isBlank(sizes)) {
			sb.append(" sizes=\"");
			sb.append(HtmlUtil.escapeAttribute(sizes));
			sb.append("\"");
		}

		if (lazyLoading && (loadingIndex == -1)) {
			sb.append(" loading=\"lazy\"");
		}

		return _injectAttributes(originalImgTag, sb.toString());
	}

	private String _renderPicture(
		List<ResponsiveImageSource> responsiveImageSources, boolean lazyLoading,
		String originalImgTag) {

		StringBundler sb = new StringBundler(
			(responsiveImageSources.size() * 7) + 3);

		sb.append("<picture>");

		for (ResponsiveImageSource responsiveImageSource :
				responsiveImageSources) {

			String query = responsiveImageSource.getQuery();

			if (Validator.isBlank(query)) {
				continue;
			}

			sb.append("<source media=\"");
			sb.append(HtmlUtil.escapeAttribute(query));
			sb.append("\" srcset=\"");
			sb.append(
				HtmlUtil.escapeAttribute(
					_getSrcSet(responsiveImageSource.getCandidates())));
			sb.append("\"");

			// A source element is never lazily loaded, so it never carries
			// auto, however an administrator declared it.

			String sizes = _getSizes(false, responsiveImageSource.getSizes());

			if (!Validator.isBlank(sizes)) {
				sb.append(" sizes=\"");
				sb.append(HtmlUtil.escapeAttribute(sizes));
				sb.append("\"");
			}

			sb.append(" />");
		}

		// The last source also feeds the img element, which is both the
		// fallback for browsers without picture support and the final source
		// when no media condition matches.

		sb.append(
			_renderImg(
				responsiveImageSources.get(responsiveImageSources.size() - 1),
				lazyLoading, originalImgTag));

		sb.append("</picture>");

		return sb.toString();
	}

	private static final String _IMG_TAG_START = "<img";

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

	private ResponsiveImageConfigurationHelper
		_responsiveImageConfigurationHelper;
	private ResponsiveImageFactory _responsiveImageFactory;

	@Reference
	private ResponsiveImageURLTransformerProvider
		_responsiveImageURLTransformerProvider;

}