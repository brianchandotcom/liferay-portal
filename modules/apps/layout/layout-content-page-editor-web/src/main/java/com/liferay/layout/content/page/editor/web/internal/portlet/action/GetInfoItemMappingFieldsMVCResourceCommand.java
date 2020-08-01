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
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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
		"mvc.command.name=/content_layout/get_info_item_mapping_fields"
	},
	service = MVCResourceCommand.class
)
public class GetInfoItemMappingFieldsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long classNameId = ParamUtil.getLong(resourceRequest, "classNameId");

		String itemClassName = _portal.getClassName(classNameId);

		InfoItemFormProvider<Object> infoItemFormProvider =
			(InfoItemFormProvider<Object>)
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFormProvider.class, itemClassName);

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
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, itemClassName);

		if (infoItemObjectProvider == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		long classPK = ParamUtil.getLong(resourceRequest, "classPK");

		InfoItemIdentifier infoItemIdentifier = new InfoItemIdentifier(classPK);

		Object infoItemObject = infoItemObjectProvider.getInfoItem(
			infoItemIdentifier);

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

		String fieldType = ParamUtil.getString(resourceRequest, "fieldType");

		List<InfoField> infoFields = ListUtil.filter(
			infoForm.getAllInfoFields(), _infoFieldTypePredicate(fieldType));

		for (InfoField infoField : infoFields) {
			jsonArray.put(
				JSONUtil.put(
					"key", infoField.getName()
				).put(
					"label", infoField.getLabel(themeDisplay.getLocale())
				).put(
					"type",
					infoField.getInfoFieldType(
					).getName()
				));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	private Predicate<InfoField> _infoFieldTypePredicate(String fieldType) {
		return infoField -> {
			boolean imageInfoFieldType =
				infoField.getInfoFieldType() instanceof ImageInfoFieldType;

			if (Objects.equals(fieldType, "background-image") ||
				Objects.equals(fieldType, "image")) {

				return imageInfoFieldType;
			}

			return !imageInfoFieldType;
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetInfoItemMappingFieldsMVCResourceCommand.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Portal _portal;

}