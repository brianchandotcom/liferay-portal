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
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
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

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 * @author Jorge Ferrer
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_info_item_mapping_fields"
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
				_jsonFactory.createJSONArray());

			return;
		}

		long classPK = ParamUtil.getLong(resourceRequest, "classPK");

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			classPK);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, itemClassName,
				infoItemIdentifier.getInfoItemServiceFilter());

		if (infoItemObjectProvider == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_jsonFactory.createJSONArray());

			return;
		}

		Object infoItemObject = infoItemObjectProvider.getInfoItem(
			infoItemIdentifier);

		if (infoItemObject == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_jsonFactory.createJSONArray());

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String fieldType = ParamUtil.getString(resourceRequest, "fieldType");

		JSONArray defaultFieldSetFieldsJSONArray =
			_jsonFactory.createJSONArray();

		JSONArray fieldSetsJSONArray = JSONUtil.put(
			JSONUtil.put("fields", defaultFieldSetFieldsJSONArray));

		InfoForm infoForm = infoItemFormProvider.getInfoForm(infoItemObject);

		for (InfoFieldSetEntry infoFieldSetEntry :
				infoForm.getInfoFieldSetEntries()) {

			if (infoFieldSetEntry instanceof InfoField) {
				InfoField<?> infoField = (InfoField<?>)infoFieldSetEntry;

				InfoFieldType infoFieldType = infoField.getInfoFieldType();

				if (_isFieldMappable(infoField, fieldType)) {
					defaultFieldSetFieldsJSONArray.put(
						JSONUtil.put(
							"key", infoField.getUniqueId()
						).put(
							"label",
							infoField.getLabel(themeDisplay.getLocale())
						).put(
							"name", infoField.getName()
						).put(
							"type", infoFieldType.getName()
						));
				}
			}
			else if (infoFieldSetEntry instanceof InfoFieldSet) {
				JSONArray fieldSetFieldsJSONArray =
					_jsonFactory.createJSONArray();

				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				List<InfoField<?>> infoFields = ListUtil.filter(
					infoFieldSet.getAllInfoFields(),
					infoField -> _isFieldMappable(infoField, fieldType));

				for (InfoField<?> infoField : infoFields) {
					fieldSetFieldsJSONArray.put(
						JSONUtil.put(
							"key", infoField.getUniqueId()
						).put(
							"label",
							infoField.getLabel(themeDisplay.getLocale())
						).put(
							"name", infoField.getName()
						).put(
							"type",
							() -> {
								InfoFieldType infoFieldType =
									infoField.getInfoFieldType();

								return infoFieldType.getName();
							}
						));
				}

				if (fieldSetFieldsJSONArray.length() > 0) {
					fieldSetsJSONArray.put(
						JSONUtil.put(
							"fields", fieldSetFieldsJSONArray
						).put(
							"label",
							infoFieldSet.getLabel(themeDisplay.getLocale())
						));
				}
			}
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, fieldSetsJSONArray);
	}

	private boolean _isFieldMappable(InfoField<?> infoField, String fieldType) {
		boolean imageInfoFieldType =
			infoField.getInfoFieldType() instanceof ImageInfoFieldType;

		if (Objects.equals(fieldType, "background-image") ||
			Objects.equals(fieldType, "image")) {

			return imageInfoFieldType;
		}

		return !imageInfoFieldType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetInfoItemMappingFieldsMVCResourceCommand.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}