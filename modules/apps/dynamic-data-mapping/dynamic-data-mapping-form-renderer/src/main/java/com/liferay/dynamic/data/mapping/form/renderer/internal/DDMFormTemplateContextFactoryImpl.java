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
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.renderer.internal.util.DDMFormTemplateContextFactoryUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.soy.util.SoyHTMLSanitizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormTemplateContextFactory.class)
public class DDMFormTemplateContextFactoryImpl
	implements DDMFormTemplateContextFactory {

	@Override
	public Map<String, Object> create(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		return doCreate(ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	@Override
	public Map<String, Object> create(
			DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		return doCreate(
			ddmForm, _ddm.getDefaultDDMFormLayout(ddmForm),
			ddmFormRenderingContext);
	}

	protected void collectResourceBundles(
		Class<?> clazz, List<ResourceBundle> resourceBundles, Locale locale) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			collectResourceBundles(interfaceClass, resourceBundles, locale);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());

		if (resourceBundle != null) {
			resourceBundles.add(resourceBundle);
		}
	}

	protected Map<String, Object> doCreate(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		String containerId = ddmFormRenderingContext.getContainerId();

		if (Validator.isNull(containerId)) {
			containerId = StringUtil.randomId();
		}

		Map<String, Object> templateContext =
			HashMapBuilder.<String, Object>put(
				"containerId", containerId
			).build();

		String currentPage = ParamUtil.getString(
			ddmFormRenderingContext.getHttpServletRequest(), "currentPage",
			"1");

		templateContext.put("currentPage", currentPage);

		setDDMFormFieldsEvaluableProperty(ddmForm);

		Locale locale = ddmFormRenderingContext.getLocale();

		if (locale == null) {
			locale = LocaleThreadLocal.getSiteDefaultLocale();
		}

		templateContext.put(
			"editingLanguageId", LanguageUtil.getLanguageId(locale));

		templateContext.put(
			"evaluatorURL", getDDMFormContextProviderServletURL());

		templateContext.put("groupId", ddmFormRenderingContext.getGroupId());

		List<Object> pages = getPages(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);

		templateContext.put("pages", pages);

		templateContext.put(
			"paginationMode", ddmFormLayout.getPaginationMode());
		templateContext.put(
			"portletNamespace", ddmFormRenderingContext.getPortletNamespace());
		templateContext.put("readOnly", ddmFormRenderingContext.isReadOnly());

		ResourceBundle resourceBundle = getResourceBundle(locale);

		templateContext.put(
			"requiredFieldsWarningMessageHTML",
			_soyHTMLSanitizer.sanitize(
				getRequiredFieldsWarningMessageHTML(
					resourceBundle,
					ddmFormRenderingContext.getHttpServletRequest())));

		templateContext.put("rules", toObjectList(ddmForm.getDDMFormRules()));
		templateContext.put(
			"showRequiredFieldsWarning",
			ddmFormRenderingContext.isShowRequiredFieldsWarning());

		boolean showSubmitButton = ddmFormRenderingContext.isShowSubmitButton();

		if (ddmFormRenderingContext.isReadOnly()) {
			showSubmitButton = false;
		}

		templateContext.put("showSubmitButton", showSubmitButton);
		templateContext.put("strings", getLanguageStringsMap(resourceBundle));

		String submitLabel = GetterUtil.getString(
			ddmFormRenderingContext.getSubmitLabel(),
			LanguageUtil.get(resourceBundle, "submit-form"));

		templateContext.put("submitLabel", submitLabel);

		templateContext.put(
			"templateNamespace", getTemplateNamespace(ddmFormLayout));
		templateContext.put("viewMode", ddmFormRenderingContext.isViewMode());

		return templateContext;
	}

	protected String getDDMFormContextProviderServletURL() {
		String servletContextPath = getServletContextPath();

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-context-provider/");
	}

	protected Map<String, String> getLanguageStringsMap(
		ResourceBundle resourceBundle) {

		return HashMapBuilder.put(
			"next", LanguageUtil.get(resourceBundle, "next")
		).put(
			"previous", LanguageUtil.get(resourceBundle, "previous")
		).build();
	}

	protected List<Object> getPages(
		DDMForm ddmForm, DDMFormLayout ddmFormLayout,
		DDMFormRenderingContext ddmFormRenderingContext) {

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			new DDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

		ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
			_ddmFormEvaluator);
		ddmFormPagesTemplateContextFactory.setDDMFormFieldTypeServicesTracker(
			_ddmFormFieldTypeServicesTracker);

		return ddmFormPagesTemplateContextFactory.create();
	}

	protected String getRequiredFieldsWarningMessageHTML(
		ResourceBundle resourceBundle, HttpServletRequest httpServletRequest) {

		StringBundler sb = new StringBundler(3);

		sb.append("<label class=\"required-warning\">");
		sb.append(
			LanguageUtil.format(
				resourceBundle, "all-fields-marked-with-x-are-required",
				getRequiredMarkTagHTML(httpServletRequest), false));
		sb.append("</label>");

		return sb.toString();
	}

	protected String getRequiredMarkTagHTML(
		HttpServletRequest httpServletRequest) {

		StringBundler sb = new StringBundler(4);

		sb.append("<svg aria-hidden=\"true\" class=\"lexicon-icon ");
		sb.append("lexicon-icon-asterisk reference-mark\"><use xlink:href=\"");
		sb.append(
			DDMFormTemplateContextFactoryUtil.getPathThemeImages(
				httpServletRequest));
		sb.append("/lexicon/icons.svg#asterisk\" /></svg>");

		return sb.toString();
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		List<ResourceBundle> resourceBundles = new ArrayList<>();

		ResourceBundle portalResourceBundle = _portal.getResourceBundle(locale);

		resourceBundles.add(portalResourceBundle);

		collectResourceBundles(getClass(), resourceBundles, locale);

		ResourceBundle[] resourceBundlesArray = resourceBundles.toArray(
			new ResourceBundle[0]);

		return new AggregateResourceBundle(resourceBundlesArray);
	}

	protected String getServletContextPath() {
		String proxyPath = _portal.getPathProxy();

		ServletConfig servletConfig =
			_ddmFormContextProviderServlet.getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		return proxyPath.concat(servletContext.getContextPath());
	}

	protected String getTemplateNamespace(DDMFormLayout ddmFormLayout) {
		String paginationMode = ddmFormLayout.getPaginationMode();

		if (Objects.equals(paginationMode, DDMFormLayout.SETTINGS_MODE)) {
			return "ddm.settings_form";
		}

		if (Objects.equals(paginationMode, DDMFormLayout.SINGLE_PAGE_MODE)) {
			return "ddm.simple_form";
		}
		else if (Objects.equals(paginationMode, DDMFormLayout.TABBED_MODE)) {
			return "ddm.tabbed_form";
		}
		else if (Objects.equals(paginationMode, DDMFormLayout.WIZARD_MODE)) {
			return "ddm.wizard_form";
		}

		return "ddm.paginated_form";
	}

	protected void setDDMFormFieldsEvaluableProperty(DDMForm ddmForm) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (String evaluableDDMFormFieldName :
				_ddmFormTemplateContextFactoryHelper.
					getEvaluableDDMFormFieldNames(ddmForm)) {

			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				evaluableDDMFormFieldName);

			ddmFormField.setProperty("evaluable", true);
		}
	}

	protected Map<String, Object> toMap(DDMFormRule ddmFormRule) {
		return HashMapBuilder.<String, Object>put(
			"actions", ddmFormRule.getActions()
		).put(
			"condition", ddmFormRule.getCondition()
		).put(
			"enable", ddmFormRule.isEnabled()
		).build();
	}

	protected List<Object> toObjectList(List<DDMFormRule> ddmFormRules) {
		if (ddmFormRules == null) {
			return Collections.emptyList();
		}

		Stream<DDMFormRule> stream = ddmFormRules.stream();

		return stream.map(
			this::toMap
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.DDMFormContextProviderServlet)"
	)
	private Servlet _ddmFormContextProviderServlet;

	@Reference
	private DDMFormEvaluator _ddmFormEvaluator;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	private final DDMFormTemplateContextFactoryHelper
		_ddmFormTemplateContextFactoryHelper =
			new DDMFormTemplateContextFactoryHelper();

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private SoyHTMLSanitizer _soyHTMLSanitizer;

}