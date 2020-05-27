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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormProviderTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.provider.InfoItemProviderTracker;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_asset_mapping_fields"
	},
	service = MVCResourceCommand.class
)
public class GetAssetMappingFieldsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long classNameId = ParamUtil.getLong(resourceRequest, "classNameId");

		String itemClassName = _portal.getClassName(classNameId);

		InfoItemFormProvider<Object> infoItemFormProvider =
			_infoItemFormProviderTracker.getInfoItemFormProvider(itemClassName);

		if (infoItemFormProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						itemClassName);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemProviderTracker.getInfoItemObjectProvider(itemClassName);

		if (infoItemObjectProvider == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		long classPK = ParamUtil.getLong(resourceRequest, "classPK");

		Object infoItemObject = infoItemObjectProvider.getInfoItem(classPK);

		if (infoItemObject == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		InfoForm infoForm = infoItemFormProvider.getInfoForm(infoItemObject);

		for (InfoField infoField : infoForm.getAllInfoFields()) {
			JSONObject jsonObject = JSONUtil.put(
				"key", infoField.getName()
			).put(
				"label", infoField.getLabel(themeDisplay.getLocale())
			).put(
				"type",
				infoField.getInfoFieldType(
				).getName()
			);

			jsonArray.put(jsonObject);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetAssetMappingFieldsMVCResourceCommand.class);

	@Reference
	private InfoItemFormProviderTracker _infoItemFormProviderTracker;

	@Reference
	private InfoItemProviderTracker _infoItemProviderTracker;

	@Reference
	private Portal _portal;

}