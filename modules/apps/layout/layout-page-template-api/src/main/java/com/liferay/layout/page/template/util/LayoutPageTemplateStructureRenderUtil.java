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

package com.liferay.layout.page.template.util;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.util.FragmentEntryRenderUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateStructureRenderUtil {

	public static String renderLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws PortalException {

		return renderLayoutContent(
			request, response, layoutPageTemplateStructure,
			FragmentEntryLinkConstants.VIEW, null);
	}

	public static String renderLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			LayoutPageTemplateStructure layoutPageTemplateStructure,
			String mode, Map<String, Object> parameterMap)
		throws PortalException {

		return renderLayoutContent(
			request, response, layoutPageTemplateStructure, mode, parameterMap,
			LocaleUtil.getMostRelevantLocale());
	}

	public static String renderLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			LayoutPageTemplateStructure layoutPageTemplateStructure,
			String mode, Map<String, Object> parameterMap, Locale locale)
		throws PortalException {

		String data = layoutPageTemplateStructure.getData();

		if (Validator.isNull(data)) {
			return StringPool.BLANK;
		}

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(
			layoutPageTemplateStructure.getData());

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		if (structureJSONArray == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

			JSONObject rowConfigJSONObject = rowJSONObject.getJSONObject(
				"config");

			sb.append("<div class=\"container-fluid px-");

			String horizontalPadding = GetterUtil.getString(
				rowConfigJSONObject.getString("paddingHorizontal"));

			if (Validator.isNotNull(horizontalPadding)) {
				sb.append(horizontalPadding);
			}
			else {
				sb.append("3");
			}

			sb.append(" py-");

			String verticalPadding = GetterUtil.getString(
				rowConfigJSONObject.getString("paddingVertical"));

			if (Validator.isNotNull(verticalPadding)) {
				sb.append(verticalPadding);
			}
			else {
				sb.append("3");
			}

			sb.append("\" style=\"");

			String backgroundColor = GetterUtil.getString(
				rowConfigJSONObject.getString("backgroundColor"));

			if (Validator.isNotNull(backgroundColor)) {
				sb.append("background-color:");
				sb.append(backgroundColor);
				sb.append(";");
			}

			String backgroundImage = GetterUtil.getString(
				rowConfigJSONObject.getString("backgroundImage"));

			if (Validator.isNotNull(backgroundImage)) {
				sb.append("background-image: url(");
				sb.append(backgroundImage);
				sb.append(");background-position: 50% 50%;");
				sb.append("background-repeat: no-repeat;");
				sb.append("background-size: cover;");
			}

			sb.append("\"><div class=\"");

			String containerType = GetterUtil.getString(
				rowConfigJSONObject.getString("containerType"));

			if (Objects.equals(containerType, "fixed")) {
				sb.append("container");
			}
			else {
				sb.append("container-fluid");
			}

			sb.append(" p-0\"><div class=\"row");

			String columnSpacing = GetterUtil.getString(
				rowConfigJSONObject.getString("columnSpacing"), "true");

			if (Objects.equals(columnSpacing, "false")) {
				sb.append(" no-gutters");
			}

			sb.append("\">");

			JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

			for (int j = 0; j < columnsJSONArray.length(); j++) {
				JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

				sb.append("<div class=\"col");

				String size = GetterUtil.getString(
					columnJSONObject.getString("size"));

				if (Validator.isNotNull(size)) {
					sb.append(" col-");
					sb.append(size);
				}

				sb.append("\">");

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

					String renderFragmentEntryLink = StringPool.BLANK;

					if (parameterMap != null) {
						renderFragmentEntryLink =
							FragmentEntryRenderUtil.renderFragmentEntryLink(
								fragmentEntryLink, mode, parameterMap, request,
								response, locale);
					}
					else {
						renderFragmentEntryLink =
							FragmentEntryRenderUtil.renderFragmentEntryLink(
								fragmentEntryLink, mode,
								new HashMap<String, Object>(), request,
								response, locale);
					}

					sb.append(renderFragmentEntryLink);
				}

				sb.append("</div>");
			}

			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
		}

		return sb.toString();
	}

}