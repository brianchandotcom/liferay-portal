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

package com.liferay.dynamic.data.lists.display.web.util;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordService;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistry;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldValueRendererAccessor;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDLDisplayRendererHelper.class)
public class DDLDisplayRendererHelper {

	public List<DDMFormField> getDDMFormFields(DDLRecordSet recordSet) {
		try {
			DDMForm ddmForm = getDDMForm(recordSet);

			return ddmForm.getDDMFormFields();
		}
		catch (PortalException pe) {
			_log.error(pe);
		}

		return Collections.emptyList();
	}

	public String renderRecord(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			DDLRecord record = getRecord(renderRequest);

			DDMForm ddmForm = getDDMForm(record.getRecordSet());

			DDMFormRenderingContext ddmFormRenderingContext =
				createDDMFormRenderingContext(
					renderRequest, renderResponse, record.getDDMFormValues());

			return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
		}
		catch (PortalException pe) {
			_log.error(pe);
		}

		return StringPool.BLANK;
	}

	public String renderRecordField(
		DDLRecord record, DDMFormField ddmFormField, Locale locale) {

		try {
			List<DDMFormFieldValue> ddmFormFieldValues =
				record.getDDMFormFieldValues(ddmFormField.getName());

			DDMFormFieldValueRendererAccessor
				ddmFormFieldValueRendererAccessor =
					getDDMFormFieldValueRendererAccessor(ddmFormField, locale);

			return ListUtil.toString(
				ddmFormFieldValues, ddmFormFieldValueRendererAccessor,
				StringPool.COMMA_AND_SPACE);
		}
		catch (PortalException pe) {
			_log.error(pe);
		}

		return StringPool.BLANK;
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDMFormValues ddmFormValues) {

		String languageId = ParamUtil.getString(renderRequest, "languageId");

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
		ddmFormRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(renderRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(renderResponse));
		ddmFormRenderingContext.setLocale(
			LocaleUtil.fromLanguageId(languageId));
		ddmFormRenderingContext.setPortletNamespace(
			renderResponse.getNamespace());

		return ddmFormRenderingContext;
	}

	protected DDMForm getDDMForm(DDLRecordSet recordSet)
		throws PortalException {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ddmStructure.getDDMForm();
	}

	protected DDMFormFieldValueRendererAccessor
		getDDMFormFieldValueRendererAccessor(
			DDMFormField ddmFormField, Locale locale) {

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeRegistry.getDDMFormFieldType(
				ddmFormField.getType());

		return ddmFormFieldType.getDDMFormFieldValueRendererAccessor(locale);
	}

	protected DDLRecord getRecord(RenderRequest renderRequest)
		throws PortalException {

		long recordId = ParamUtil.getLong(renderRequest, "recordId");

		return _ddlRecordService.getRecord(recordId);
	}

	@Reference
	protected void setDDLRecordService(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	@Reference
	protected void setDDMFormFieldTypeRegistry(
		DDMFormFieldTypeRegistry ddmFormFieldTypeRegistry) {

		_ddmFormFieldTypeRegistry = ddmFormFieldTypeRegistry;
	}

	@Reference
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLDisplayRendererHelper.class);

	private DDLRecordService _ddlRecordService;
	private DDMFormFieldTypeRegistry _ddmFormFieldTypeRegistry;
	private DDMFormRenderer _ddmFormRenderer;

}