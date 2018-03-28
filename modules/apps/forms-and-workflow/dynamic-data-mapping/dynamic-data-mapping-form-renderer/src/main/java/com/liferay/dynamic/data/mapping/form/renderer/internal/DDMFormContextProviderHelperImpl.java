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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.DDMFormTemplateContextProcessor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormContextProviderHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Victor Oliveira
 */
@Component(immediate = true, service = DDMFormContextProviderHelper.class)
public class DDMFormContextProviderHelperImpl
	implements DDMFormContextProviderHelper {

	public List<Object> createDDMFormPagesTemplateContext(
			DDMFormRenderingContext ddmFormRenderingContext,
			String serializedFormContext, String languageId)
		throws Exception {

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			getDDMFormPagesTemplateContext(
				ddmFormRenderingContext, serializedFormContext, languageId);

		ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
			_ddmFormEvaluator);
		ddmFormPagesTemplateContextFactory.setDDMFormFieldTypeServicesTracker(
			_ddmFormFieldTypeServicesTracker);
		ddmFormPagesTemplateContextFactory.setJSONFactory(_jsonFactory);

		return ddmFormPagesTemplateContextFactory.create();
	}

	protected DDMFormTemplateContextProcessor
			createDMFormTemplateContextProcessor(
				String serializedFormContext, String languageId)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedFormContext);

		return new DDMFormTemplateContextProcessor(jsonObject, languageId);
	}

	protected DDMFormPagesTemplateContextFactory getDDMFormPagesTemplateContext(
			DDMFormRenderingContext ddmFormRenderingContext,
			String serializedFormContext, String languageId)
		throws Exception {

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		DDMFormTemplateContextProcessor ddmFormTemplateContextProcessor =
			createDMFormTemplateContextProcessor(
				serializedFormContext, languageId);

		DDMFormValues ddmFormValues =
			ddmFormTemplateContextProcessor.getDDMFormValues();

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

		ddmFormRenderingContext.setGroupId(
			ddmFormTemplateContextProcessor.getGroupId());

		_prepareThreadLocal(locale);

		DDMForm ddmForm = ddmFormTemplateContextProcessor.getDDMForm();

		DDMFormLayout ddmFormLayout =
			ddmFormTemplateContextProcessor.getDDMFormLayout();

		return new DDMFormPagesTemplateContextFactory(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	private static void _prepareThreadLocal(Locale locale)
		throws Exception, PortalException {

		LocaleThreadLocal.setThemeDisplayLocale(locale);
	}

	@Reference
	private DDMFormEvaluator _ddmFormEvaluator;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private JSONFactory _jsonFactory;

}