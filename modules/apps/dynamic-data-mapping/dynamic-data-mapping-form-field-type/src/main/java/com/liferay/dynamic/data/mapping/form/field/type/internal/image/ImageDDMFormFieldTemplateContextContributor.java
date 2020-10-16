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

package com.liferay.dynamic.data.mapping.form.field.type.internal.image;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Lancha
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=image",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		ImageDDMFormFieldTemplateContextContributor.class
	}
)
public class ImageDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"itemSelectorURL",
			getItemSelectorURL(
				ddmFormFieldRenderingContext.getHttpServletRequest(),
				ddmFormFieldRenderingContext)
		).put(
			"portletNamespace",
			ddmFormFieldRenderingContext.getPortletNamespace()
		).put(
			"predefinedValue",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"predefinedValue")
		).put(
			"value",
			getValue(
				DDMFormFieldTypeUtil.getPropertyValue(
					ddmFormFieldRenderingContext, "value"))
		).build();
	}

	protected String getItemSelectorURL(
		HttpServletRequest httpServletRequest,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		if (_itemSelector == null) {
			return null;
		}

		JournalItemSelectorCriterion journalItemSelectorCriterion =
			new JournalItemSelectorCriterion();

		journalItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		ImageItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DownloadFileEntryItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			ddmFormFieldRenderingContext.getPortletNamespace() +
				"selectDocumentLibrary",
			journalItemSelectorCriterion, imageItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	protected String getValue(String value) {
		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(value);

			if (Validator.isNotNull(jsonObject.getString("uuid")) &&
				Validator.isNotNull(jsonObject.getLong("groupId"))) {

				FileEntry fileEntry =
					_dlAppService.getFileEntryByUuidAndGroupId(
						GetterUtil.getString(jsonObject.getString("uuid")),
						GetterUtil.getLong(jsonObject.getString("groupId")));

				jsonObject.put(
					"description", jsonObject.getString("alt")
				).put(
					"url",
					_dlURLHelper.getDownloadURL(
						fileEntry, fileEntry.getFileVersion(), null,
						StringPool.BLANK)
				);

				return jsonObject.toString();
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return value;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageDDMFormFieldTemplateContextContributor.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSONFactory _jsonFactory;

}