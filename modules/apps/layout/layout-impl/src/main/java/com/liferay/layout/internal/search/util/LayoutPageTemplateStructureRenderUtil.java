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
import com.liferay.layout.page.template.util.LayoutDataConverter;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

		if (LayoutDataConverter.isLatestVersion(dataJSONObject)) {
			return _renderLatestLayoutData(
				data, fragmentRendererController, httpServletRequest,
				httpServletResponse, mode, parameterMap, locale,
				segmentsExperienceIds);
		}

		return _renderLayoutData(
			dataJSONObject, fragmentRendererController, httpServletRequest,
			httpServletResponse, mode, parameterMap, locale,
			segmentsExperienceIds);
	}

	private static String _renderLatestLayoutData(
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

			FragmentEntryLink fragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
					fragmentLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink == null) {
				continue;
			}

			DefaultFragmentRendererContext fragmentRendererContext =
				new DefaultFragmentRendererContext(fragmentEntryLink);

			fragmentRendererContext.setFieldValues(parameterMap);
			fragmentRendererContext.setLocale(locale);
			fragmentRendererContext.setMode(mode);
			fragmentRendererContext.setSegmentsExperienceIds(
				segmentsExperienceIds);

			sb.append(
				fragmentRendererController.render(
					fragmentRendererContext, httpServletRequest,
					httpServletResponse));
		}

		return sb.toString();
	}

	private static String _renderLayoutData(
		JSONObject dataJSONObject,
		FragmentRendererController fragmentRendererController,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String mode,
		Map<String, Object> parameterMap, Locale locale,
		long[] segmentsExperienceIds) {

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		if (structureJSONArray == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

			JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

			for (int j = 0; j < columnsJSONArray.length(); j++) {
				JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

				JSONArray fragmentEntryLinkIdsJSONArray =
					columnJSONObject.getJSONArray("fragmentEntryLinkIds");

				for (int k = 0; k < fragmentEntryLinkIdsJSONArray.length();
					 k++) {

					long fragmentEntryLinkId =
						fragmentEntryLinkIdsJSONArray.getLong(k);

					if (fragmentEntryLinkId <= 0) {
						continue;
					}

					FragmentEntryLink fragmentEntryLink =
						FragmentEntryLinkLocalServiceUtil.
							fetchFragmentEntryLink(fragmentEntryLinkId);

					if (fragmentEntryLink == null) {
						continue;
					}

					DefaultFragmentRendererContext fragmentRendererContext =
						new DefaultFragmentRendererContext(fragmentEntryLink);

					fragmentRendererContext.setFieldValues(parameterMap);
					fragmentRendererContext.setLocale(locale);
					fragmentRendererContext.setMode(mode);
					fragmentRendererContext.setSegmentsExperienceIds(
						segmentsExperienceIds);

					sb.append(
						fragmentRendererController.render(
							fragmentRendererContext, httpServletRequest,
							httpServletResponse));
				}
			}
		}

		return sb.toString();
	}

}