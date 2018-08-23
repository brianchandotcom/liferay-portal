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

package com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib.util;

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextRequest;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextResponse;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRequest;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsResponse;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRetriever;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true)
public class DDMFormTaglibUtil {

	public static DDMForm getDDMForm(
		long ddmStructureId, long ddmStructureVersionId) {

		DDMForm ddmForm = new DDMForm();

		if (ddmStructureVersionId > 0) {
			DDMStructureVersion ddmStructureVersion =
				_ddmStructureVersionLocalService.fetchDDMStructureVersion(
					ddmStructureVersionId);

			if (ddmStructureVersion != null) {
				return ddmStructureVersion.getDDMForm();
			}
		}

		if (ddmStructureId > 0) {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId);

			if (ddmStructure != null) {
				return ddmStructure.getDDMForm();
			}
		}

		return ddmForm;
	}

	public static DDMFormBuilderSettingsResponse getDDMFormBuilderSettings(
		DDMFormBuilderSettingsRequest ddmFormBuilderSettingsRequest) {

		DDMFormBuilderSettingsRetriever ddmFormBuilderSettingsRetriever =
			getDDMFormBuilderSettingsRetriever();

		return ddmFormBuilderSettingsRetriever.getSettings(
			ddmFormBuilderSettingsRequest);
	}

	public static String getFormBuilderContext(
		long ddmStructureId, long ddmStructureVersionId,
		HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String serializedFormBuilderContext = ParamUtil.getString(
			request, "serializedFormBuilderContext");

		if (Validator.isNotNull(serializedFormBuilderContext)) {
			return serializedFormBuilderContext;
		}

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		Optional<DDMStructure> ddmStructureOptional = Optional.ofNullable(
			_ddmStructureLocalService.fetchDDMStructure(ddmStructureId));

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.fetchDDMStructureVersion(
				ddmStructureVersionId);

		Locale locale = themeDisplay.getSiteDefaultLocale();

		if (ddmStructureOptional.isPresent() || (ddmStructureVersion != null)) {
			DDMForm ddmForm = getDDMForm(ddmStructureId, ddmStructureVersionId);

			locale = ddmForm.getDefaultLocale();
		}

		DDMFormBuilderContextRequest ddmFormBuilderContextRequest =
			DDMFormBuilderContextRequest.with(
				ddmStructureOptional, themeDisplay.getRequest(),
				themeDisplay.getResponse(), locale, true);

		ddmFormBuilderContextRequest.addProperty(
			"ddmStructureVersion", ddmStructureVersion);

		DDMFormBuilderContextResponse formBuilderContextResponse =
			_ddmFormBuilderContextFactory.create(ddmFormBuilderContextRequest);

		return jsonSerializer.serializeDeep(
			formBuilderContextResponse.getContext());
	}

	protected static DDMFormBuilderSettingsRetriever
		getDDMFormBuilderSettingsRetriever() {

		if (_ddmFormBuilderSettingsRetriever == null) {
			throw new IllegalStateException();
		}

		return _ddmFormBuilderSettingsRetriever;
	}

	@Reference(unbind = "-")
	protected void setDDMFormBuilderContextFactory(
		DDMFormBuilderContextFactory ddmFormBuilderContextFactory) {

		_ddmFormBuilderContextFactory = ddmFormBuilderContextFactory;
	}

	@Reference(unbind = "-")
	protected void setDDMFormBuilderSettingsRetriever(
		DDMFormBuilderSettingsRetriever ddmFormBuilderSettingsRetriever) {

		_ddmFormBuilderSettingsRetriever = ddmFormBuilderSettingsRetriever;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {

		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private static DDMFormBuilderContextFactory _ddmFormBuilderContextFactory;
	private static DDMFormBuilderSettingsRetriever
		_ddmFormBuilderSettingsRetriever;
	private static DDMStructureLocalService _ddmStructureLocalService;
	private static DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private static JSONFactory _jsonFactory;

}