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

package com.liferay.layout.internal.search.util;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateStructureRenderUtil {

	public static String renderLayoutContent(
			FragmentRendererController fragmentRendererController,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			LayoutPageTemplateStructure layoutPageTemplateStructure,
			String mode, Map<String, Object> parameterMap, Locale locale,
			long[] segmentsExperienceIds)
		throws PortalException {

		if (fragmentRendererController == null) {
			return StringPool.BLANK;
		}

		String data = layoutPageTemplateStructure.getData(
			segmentsExperienceIds);

		if (Validator.isNull(data)) {
			return StringPool.BLANK;
		}

		return _renderLayoutData(
			data, fragmentRendererController, httpServletRequest,
			httpServletResponse, mode, parameterMap, locale,
			segmentsExperienceIds);
	}

	private static String _renderFragmentEntryLink(
		long fragmentEntryLinkId,
		FragmentRendererController fragmentRendererController,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String mode,
		Map<String, Object> parameterMap, Locale locale,
		long[] segmentsExperienceIds) {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		if (fragmentEntryLink == null) {
			return StringPool.BLANK;
		}

		DefaultFragmentRendererContext fragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		fragmentRendererContext.setFieldValues(parameterMap);
		fragmentRendererContext.setLocale(locale);
		fragmentRendererContext.setMode(mode);
		fragmentRendererContext.setSegmentsExperienceIds(segmentsExperienceIds);

		return fragmentRendererController.render(
			fragmentRendererContext, httpServletRequest, httpServletResponse);
	}

	private static String _renderLayoutData(
		String data, FragmentRendererController fragmentRendererController,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String mode,
		Map<String, Object> parameterMap, Locale locale,
		long[] segmentsExperienceIds) {

		StringBundler sb = new StringBundler();

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			if (!(layoutStructureItem instanceof FragmentLayoutStructureItem)) {
				continue;
			}

			FragmentLayoutStructureItem fragmentLayoutStructureItem =
				(FragmentLayoutStructureItem)layoutStructureItem;

			if (fragmentLayoutStructureItem.getFragmentEntryLinkId() <= 0) {
				continue;
			}

			sb.append(
				_renderFragmentEntryLink(
					fragmentLayoutStructureItem.getFragmentEntryLinkId(),
					fragmentRendererController, httpServletRequest,
					httpServletResponse, mode, parameterMap, locale,
					segmentsExperienceIds));
		}

		return sb.toString();
	}

}