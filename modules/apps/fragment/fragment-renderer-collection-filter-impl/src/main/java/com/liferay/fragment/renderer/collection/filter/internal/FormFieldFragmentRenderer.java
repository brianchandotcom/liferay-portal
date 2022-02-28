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

package com.liferay.fragment.renderer.collection.filter.internal;

import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Víctor Galán
 */
@Component(service = FragmentRenderer.class)
public class FormFieldFragmentRenderer
	implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "form-components";
	}

	@Override
	public String getIcon() {
		return "container";
	}

	@Override
	public String getLabel(Locale locale) {
		return "Form field";
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		String classNameId =
			(String) _fragmentEntryConfigurationParser.getConfigurationFieldValue(
				fragmentEntryLink.getEditableValues(), "classNameId",
				FragmentConfigurationFieldDataType.STRING);

		String classTypeId =
			(String) _fragmentEntryConfigurationParser.getConfigurationFieldValue(
				fragmentEntryLink.getEditableValues(), "classTypeId",
				FragmentConfigurationFieldDataType.STRING);

		if (Validator.isNull(classNameId)) {
			renderInvalidInfo(httpServletResponse);

			return;
		}

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, _portal.getClassName(
					GetterUtil.getLong(classNameId)));

		String field =
			(String) _fragmentEntryConfigurationParser.getConfigurationFieldValue(
				fragmentEntryLink.getEditableValues(), "field",
				FragmentConfigurationFieldDataType.STRING);

		if (Validator.isNull(field)) {
			renderInvalidInfo(httpServletResponse);

			return;
		}

		InfoForm infoForm = null;
		try {
			infoForm = infoItemFormProvider.getInfoForm(classTypeId,
				themeDisplay.getScopeGroupId());
		}
		catch (NoSuchFormVariationException e) {
			e.printStackTrace();
		}

		List<InfoField> infoFields = infoForm.getAllInfoFields();

		Optional<InfoField> infoFieldOptional = infoFields.stream().filter(
			infoField -> infoField.getName().equals(field)).findFirst();

		if (!infoFieldOptional.isPresent()) {
			renderInvalidInfo(httpServletResponse);

			return;
		}

		String label =
			(String) _fragmentEntryConfigurationParser.getConfigurationFieldValue(
				fragmentEntryLink.getEditableValues(), "label",
				FragmentConfigurationFieldDataType.STRING);

		String placeholder =
			(String) _fragmentEntryConfigurationParser.getConfigurationFieldValue(
				fragmentEntryLink.getEditableValues(), "placeholder",
				FragmentConfigurationFieldDataType.STRING);

		InfoField infoField = infoFieldOptional.get();

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		FormFieldRenderer formFieldRenderer =
			_formFieldRendererTracker.getFormFieldRenderer(
				infoFieldType.getClass().getName());

		if (formFieldRenderer != null) {
			formFieldRenderer.render(
				label, placeholder, infoField, httpServletRequest,
				httpServletResponse);
		} else {
			renderTextField(label, placeholder, infoField, httpServletRequest,
				httpServletResponse);
		}
	}

	@Reference
	private Portal _portal;

	private void renderTextField(
		String label, String placeholder, InfoField<?> infoField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		httpServletRequest.setAttribute("name", infoField.getName());
		httpServletRequest.setAttribute(
			"label",
			Validator.isNull(label) ?
				infoField.getLabel(httpServletRequest.getLocale()) : label);
		httpServletRequest.setAttribute(
			"placeholder", placeholder);

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/form_field_text.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	private void renderInvalidInfo(HttpServletResponse httpServletResponse) {
		try {
			StringBundler stringBundler = new StringBundler();

			stringBundler.append(
				"<div class=\"alert alert-info\" role=\"alert\">");
			stringBundler.append(
				"<strong class=\"lead\">Error:</strong>Not field selected");
			stringBundler.append("</div>");

			PrintWriter writer = httpServletResponse.getWriter();

			writer.write(stringBundler.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Reference
	private FormFieldRendererTrackerImpl _formFieldRendererTracker;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	private static final Log _log = LogFactoryUtil.getLog(
		FormFieldFragmentRenderer.class);

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.renderer.collection.filter.impl)"
	)
	private ServletContext _servletContext;

}