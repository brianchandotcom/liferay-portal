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

package com.liferay.object.dynamic.data.mapping.form.field.type.internal.attachment;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + ObjectDDMFormFieldTypeConstants.ATTACHMENT,
	service = DDMFormFieldTemplateContextContributor.class
)
public class AttachmentDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"acceptedFileExtensions",
			ddmFormField.getProperty("acceptedFileExtensions")
		).put(
			"fileSource", ddmFormField.getProperty("fileSource")
		).put(
			"maximumFileSize", ddmFormField.getProperty("maximumFileSize")
		).put(
			"objectEntryId", ddmFormField.getProperty("objectEntryId")
		).put(
			"uploadURL",
			_getUploadURL(
				ddmFormField,
				ddmFormFieldRenderingContext.getHttpServletRequest())
		).put(
			"value", ddmFormFieldRenderingContext.getValue()
		).build();
	}

	private String _getUploadURL(
		DDMFormField ddmFormField, HttpServletRequest httpServletRequest) {

		String uploadURL = GetterUtil.getString(
			ddmFormField.getProperty("uploadURL"));

		if (Validator.isNotNull(uploadURL)) {
			return uploadURL;
		}

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createActionURL(
				GetterUtil.getString(ddmFormField.getProperty("portletId")))
		).setActionName(
			"/object_entries/upload_attachment"
		).setParameter(
			"folderId", GetterUtil.getLong(ddmFormField.getProperty("folderId"))
		).setParameter(
			"objectFieldId",
			GetterUtil.getLong(ddmFormField.getProperty("objectFieldId"))
		).buildString();
	}

}