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
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_mapping_fields"
	},
	service = MVCResourceCommand.class
)
public class GetMappingFieldsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long classNameId = ParamUtil.getLong(resourceRequest, "classNameId");

		String className = _portal.getClassName(classNameId);

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemFormProviderTracker.getInfoItemFormProvider(className);

		if (infoItemFormProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						className);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		long classTypeId = ParamUtil.getLong(resourceRequest, "classTypeId");
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		InfoForm infoForm = infoItemFormProvider.getInfoForm(classTypeId);

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
		GetMappingFieldsMVCResourceCommand.class);

	@Reference
	private InfoItemFormProviderTracker _infoItemFormProviderTracker;

	@Reference
	private Portal _portal;

}