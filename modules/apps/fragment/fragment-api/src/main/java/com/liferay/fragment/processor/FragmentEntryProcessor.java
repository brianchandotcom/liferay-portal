/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.processor;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

/**
 * @author Pavel Savinov
 */
public interface FragmentEntryProcessor {

	public default void deleteFragmentEntryLinkData(
		FragmentEntryLink fragmentEntryLink) {
	}

	public default JSONArray getAvailableTagsJSONArray() {
		return null;
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default JSONObject getDefaultEditableValuesJSONObject(String html) {
		return null;
	}

	public default JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		return null;
	}

	public default String processFragmentEntryLinkCSS(
			FragmentEntryLink fragmentEntryLink, String css,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		return css;
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default String processFragmentEntryLinkCSS(
			FragmentEntryLink fragmentEntryLink, String css, String mode,
			Locale locale, long[] segmentsExperienceIds)
		throws PortalException {

		return processFragmentEntryLinkCSS(
			fragmentEntryLink, css, mode, locale, segmentsExperienceIds, 0);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default String processFragmentEntryLinkCSS(
			FragmentEntryLink fragmentEntryLink, String css, String mode,
			Locale locale, long[] segmentsExperienceIds, long previewClassPK)
		throws PortalException {

		return processFragmentEntryLinkCSS(
			fragmentEntryLink, css, mode, locale, segmentsExperienceIds,
			previewClassPK, AssetRendererFactory.TYPE_LATEST_APPROVED);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default String processFragmentEntryLinkCSS(
			FragmentEntryLink fragmentEntryLink, String css, String mode,
			Locale locale, long[] segmentsExperienceIds, long previewClassPK,
			int previewType)
		throws PortalException {

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					null, null, mode, locale);

		defaultFragmentEntryProcessorContext.setPreviewClassPK(previewClassPK);
		defaultFragmentEntryProcessorContext.setPreviewType(previewType);
		defaultFragmentEntryProcessorContext.setSegmentsExperienceIds(
			segmentsExperienceIds);

		return processFragmentEntryLinkCSS(
			fragmentEntryLink, css, defaultFragmentEntryProcessorContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html)
		throws PortalException {

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, html, FragmentEntryLinkConstants.EDIT);
	}

	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		return fragmentEntryLink.getHtml();
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode)
		throws PortalException {

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, html, mode, LocaleUtil.getMostRelevantLocale());
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode,
			Locale locale)
		throws PortalException {

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, html, mode, locale, new long[0]);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode,
			Locale locale, long[] segmentsExperienceIds)
		throws PortalException {

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, html, mode, locale, segmentsExperienceIds, 0);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode,
			Locale locale, long[] segmentsExperienceIds, long previewClassPK)
		throws PortalException {

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, html, mode, locale, segmentsExperienceIds,
			previewClassPK, AssetRendererFactory.TYPE_LATEST_APPROVED);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode,
			Locale locale, long[] segmentsExperienceIds, long previewClassPK,
			int previewType)
		throws PortalException {

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					null, null, mode, locale);

		defaultFragmentEntryProcessorContext.setPreviewClassPK(previewClassPK);
		defaultFragmentEntryProcessorContext.setPreviewType(previewType);
		defaultFragmentEntryProcessorContext.setSegmentsExperienceIds(
			segmentsExperienceIds);

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, html, defaultFragmentEntryProcessorContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public default void validateFragmentEntryHTML(String html)
		throws PortalException {

		validateFragmentEntryHTML(html, null);
	}

	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException;

}