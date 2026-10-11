/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal.adaptive.media;

import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.responsive.image.ImageResourceFactory;
import com.liferay.responsive.image.ResponsiveImageMarkupRenderer;
import com.liferay.responsive.image.ResponsiveImageRequest;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Routes existing Adaptive Media tag factory callers through the
 * {@link ResponsiveImageMarkupRenderer}.
 *
 * <p>
 * It is registered above Adaptive Media's own implementation so that the
 * consumers already calling {@code AMImageHTMLTagFactory} (content
 * transformers, export and import, the taglib, several commerce classes) pick
 * up whichever is configured without being modified.
 * </p>
 *
 * @author Daniel Sanz
 */
@Component(
	property = "service.ranking:Integer=100",
	service = AMImageHTMLTagFactory.class
)
public class ResponsiveImageAMImageHTMLTagFactory
	implements AMImageHTMLTagFactory {

	@Override
	public String create(String originalImgTag, FileEntry fileEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled(
				fileEntry.getCompanyId(), "LPD-94784")) {

			return _amImageHTMLTagFactory.create(originalImgTag, fileEntry);
		}

		String markup = _responsiveImageMarkupRenderer.render(
			originalImgTag,
			ResponsiveImageRequest.builder(
				_imageResourceFactory.fromFileEntry(fileEntry)
			).httpServletRequest(
				_getHttpServletRequest()
			).build());

		if (markup == null) {
			return _amImageHTMLTagFactory.create(originalImgTag, fileEntry);
		}

		return _addFileEntryIdAttribute(markup, fileEntry);
	}

	/**
	 * Returns the markup with the file entry named on its
	 * <code>&lt;picture&gt;</code> element, which is how Adaptive Media's
	 * consumers find the image a tag stands for.
	 *
	 * <p>
	 * The tag name ends where HTML says it ends, at <code>&gt;</code> or at
	 * the whitespace before the global attributes the element may carry, so
	 * that an element merely starting with <code>picture</code> is never
	 * rewritten into nonsense.
	 * </p>
	 */
	private String _addFileEntryIdAttribute(
		String markup, FileEntry fileEntry) {

		if (!markup.startsWith(_OPEN_TAG_PICTURE)) {
			return markup;
		}

		String markupFragment = markup.substring(_OPEN_TAG_PICTURE.length());

		if (!markupFragment.startsWith(StringPool.GREATER_THAN) &&
			!markupFragment.startsWith(StringPool.SPACE)) {

			return markup;
		}

		return StringBundler.concat(
			_OPEN_TAG_PICTURE, StringPool.SPACE,
			AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID, "=\"",
			fileEntry.getFileEntryId(), "\"", markupFragment);
	}

	private HttpServletRequest _getHttpServletRequest() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		return serviceContext.getRequest();
	}

	private static final String _OPEN_TAG_PICTURE = "<picture";

	@Reference(
		target = "(!(component.name=com.liferay.responsive.image.internal.adaptive.media.ResponsiveImageAMImageHTMLTagFactory))"
	)
	private AMImageHTMLTagFactory _amImageHTMLTagFactory;

	@Reference
	private ImageResourceFactory _imageResourceFactory;

	@Reference
	private ResponsiveImageMarkupRenderer _responsiveImageMarkupRenderer;

}