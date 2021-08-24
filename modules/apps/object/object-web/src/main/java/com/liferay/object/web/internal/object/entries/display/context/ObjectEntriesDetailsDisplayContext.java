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

package com.liferay.object.web.internal.object.entries.display.context;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.util.ObjectRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.taglib.servlet.PipingServletResponseFactory;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 */
public class ObjectEntriesDetailsDisplayContext {

	public ObjectEntriesDetailsDisplayContext(
		HttpServletRequest httpServletRequest, DDMFormRenderer ddmFormRenderer,
		ObjectFieldLocalService objectFieldLocalService) {

		_ddmFormRenderer = ddmFormRenderer;
		_objectFieldLocalService = objectFieldLocalService;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public ObjectDefinition getObjectDefinition() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		return (ObjectDefinition)httpServletRequest.getAttribute(
			ObjectWebKeys.OBJECT_DEFINITION);
	}

	public String renderDDMForm(PageContext pageContext)
		throws PortalException {

		DDMForm ddmForm = _getDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId("edit_object_entry");
		ddmFormRenderingContext.setHttpServletRequest(
			_objectRequestHelper.getRequest());
		ddmFormRenderingContext.setHttpServletResponse(
			PipingServletResponseFactory.createPipingServletResponse(
				pageContext));
		ddmFormRenderingContext.setLocale(_objectRequestHelper.getLocale());

		LiferayPortletResponse liferayPortletResponse =
			_objectRequestHelper.getLiferayPortletResponse();

		ddmFormRenderingContext.setPortletNamespace(
			liferayPortletResponse.getNamespace());

		ddmFormRenderingContext.setShowRequiredFieldsWarning(true);

		return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
	}

	private DDMForm _getDDMForm() {
		ObjectDefinition objectDefinition = getObjectDefinition();

		DDMForm ddmForm = new DDMForm();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			DDMFormField ddmFormField = _getDDMFormField(
				objectField, _objectRequestHelper.getLocale());

			ddmForm.addDDMFormField(ddmFormField);
		}

		ddmForm.addAvailableLocale(_objectRequestHelper.getLocale());
		ddmForm.setDefaultLocale(_objectRequestHelper.getLocale());

		return ddmForm;
	}

	private DDMFormField _getDDMFormField(
		ObjectField objectField, Locale locale) {

		DDMFormField ddmFormField = new DDMFormField(
			objectField.getDBColumnName(), DDMFormFieldTypeConstants.TEXT);

		LocalizedValue ddmFormFieldLabelLocalizedValue = new LocalizedValue(
			locale);

		ddmFormFieldLabelLocalizedValue.addString(
			locale, objectField.getLabel(locale));

		ddmFormField.setLabel(ddmFormFieldLabelLocalizedValue);

		ddmFormField.setRequired(objectField.isRequired());

		return ddmFormField;
	}

	private final DDMFormRenderer _ddmFormRenderer;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRequestHelper _objectRequestHelper;

}