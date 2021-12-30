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
import com.liferay.dynamic.data.mapping.form.renderer.internal.configuration.FFDateFieldTypeValidationConfigurationActivator;
import com.liferay.dynamic.data.mapping.form.renderer.internal.helper.DDMFormTemplateContextFactoryHelper;
import com.liferay.dynamic.data.mapping.form.validation.DDMValidation;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.SettingsDDMFormFieldsUtil;
import com.liferay.osgi.service.tracker.collections.ServiceTrackerMapBuilder;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormLayoutDDMFormField :
				ddmFormLayout.getDDMFormFields()) {

			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				ddmFormLayoutDDMFormField.getName());

			if (!ddmFormField.isRequired()) {
				ddmFormField.setRequired(
					ddmFormLayoutDDMFormField.isRequired());
			}

			Map<String, DDMFormField> settingsDDMFormFieldsMap =
				SettingsDDMFormFieldsUtil.getSettingsDDMFormFields(
					_ddmFormFieldTypeServicesTracker,
					ddmFormLayoutDDMFormField.getType());

			List<DDMFormField> visualPropertiesDDMFormFields = ListUtil.filter(
				new ArrayList<>(settingsDDMFormFieldsMap.values()),
				visualPropertyDDMFormField ->
					visualPropertyDDMFormField.isVisualProperty() &&
					!StringUtil.equals(
						visualPropertyDDMFormField.getName(), "required"));

			for (DDMFormField visualPropertyDDMFormField :
					visualPropertiesDDMFormFields) {

				Object value = ddmFormLayoutDDMFormField.getProperty(
					visualPropertyDDMFormField.getName());

				if ((value == null) ||
					(Objects.equals(
						visualPropertyDDMFormField.getName(), "label") &&
					 GetterUtil.getBoolean(
						 ddmFormField.getProperty("labelAtStructureLevel")))) {

					continue;
				}

				if (visualPropertyDDMFormField.isLocalizable()) {
					LocalizedValue localizedValue = (LocalizedValue)value;

					if (MapUtil.isEmpty(localizedValue.getValues())) {
						continue;
					}
				}

				ddmFormField.setProperty(
					visualPropertyDDMFormField.getName(), value);
			}
		}

		return _doCreate(ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	@Override
	public Map<String, Object> create(
			DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		return _doCreate(
			ddmForm, _ddm.getDefaultDDMFormLayout(ddmForm),
			ddmFormRenderingContext);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			ServiceTrackerMapBuilder.SelectorFactory.newSelector(
				bundleContext, DDMValidation.class
			).map(
				"ddm.validation.data.type"
			).collectMultiValue(
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator<>(
						"ddm.validation.ranking"))
			).build();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		List<ResourceBundle> resourceBundles = new ArrayList<>();

		ResourceBundle portalResourceBundle = _portal.getResourceBundle(locale);

		resourceBundles.add(portalResourceBundle);

		_collectResourceBundles(getClass(), resourceBundles, locale);

		ResourceBundle[] resourceBundlesArray = resourceBundles.toArray(
			new ResourceBundle[0]);

		return new AggregateResourceBundle(resourceBundlesArray);
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

	private void _collectResourceBundles(
		Class<?> clazz, List<ResourceBundle> resourceBundles, Locale locale) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			_collectResourceBundles(interfaceClass, resourceBundles, locale);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());

		if (resourceBundle != null) {
			resourceBundles.add(resourceBundle);
		}
	}

	private Map<String, Object> _doCreate(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws PortalException {

		String containerId = ddmFormRenderingContext.getContainerId();

		if (Validator.isNull(containerId)) {
			containerId = StringUtil.randomId();
		}

		_setDDMFormFieldsEvaluableProperty(ddmForm, ddmFormLayout);

		Locale locale = ddmFormRenderingContext.getLocale();

		if (locale == null) {
			locale = LocaleThreadLocal.getSiteDefaultLocale();
		}

		Map<String, Object> templateContext = new HashMap<>();

		ResourceBundle resourceBundle = getResourceBundle(locale);

		String cancelLabel = GetterUtil.getString(
			ddmFormRenderingContext.getCancelLabel(),
			LanguageUtil.get(resourceBundle, "cancel"));

		templateContext.put("cancelLabel", cancelLabel);

		templateContext.put("containerId", containerId);
		templateContext.put(
			"currentPage",
			ParamUtil.getString(
				ddmFormRenderingContext.getHttpServletRequest(), "currentPage",
				"1"));
		templateContext.put(
			"dateFieldTypeValidationEnabled",
			_ffDateFieldTypeValidationConfigurationActivator.enabled());
		templateContext.put(
			"ddmStructureLayoutId",
			ddmFormRenderingContext.getDDMStructureLayoutId());

		String defaultLanguageId = GetterUtil.getString(
			ddmFormRenderingContext.getProperty("defaultLanguageId"));

		if (Validator.isNotNull(defaultLanguageId)) {
			templateContext.put("defaultLanguageId", defaultLanguageId);
		}
		else {
			templateContext.put(
				"defaultLanguageId",
				LanguageUtil.getLanguageId(ddmForm.getDefaultLocale()));
		}

		templateContext.put(
			"defaultSiteLanguageId",
			LanguageUtil.getLanguageId(LocaleUtil.getSiteDefault()));
		templateContext.put(
			"editingLanguageId", LanguageUtil.getLanguageId(locale));
		templateContext.put(
			"evaluatorURL", _getDDMFormContextProviderServletURL());
		templateContext.put("groupId", ddmFormRenderingContext.getGroupId());
		templateContext.put(
			"pages",
			_getPages(ddmForm, ddmFormLayout, ddmFormRenderingContext));
		templateContext.put(
			"paginationMode", ddmFormLayout.getPaginationMode());
		templateContext.put(
			"persistDefaultValues",
			GetterUtil.getBoolean(
				(Boolean)ddmFormRenderingContext.getProperty(
					"persistDefaultValues"),
				true));
		templateContext.put(
			"portletNamespace", ddmFormRenderingContext.getPortletNamespace());
		templateContext.put("readOnly", ddmFormRenderingContext.isReadOnly());

		String redirectURL = ddmFormRenderingContext.getRedirectURL();

		templateContext.put("redirectURL", redirectURL);

		List<DDMFormRule> ddmFormRules = ddmFormLayout.getDDMFormRules();

		if (ListUtil.isEmpty(ddmFormRules)) {
			ddmFormRules = ddmForm.getDDMFormRules();
		}

		templateContext.put("rules", _toObjectList(ddmFormRules));

		boolean showCancelButton = ddmFormRenderingContext.isShowCancelButton();

		if (ddmFormRenderingContext.isReadOnly() ||
			Validator.isNull(redirectURL)) {

			showCancelButton = false;
		}

		templateContext.put("showCancelButton", showCancelButton);

		templateContext.put(
			"showPartialResultsToRespondents",
			GetterUtil.getBoolean(
				(Boolean)ddmFormRenderingContext.getProperty(
					"showPartialResultsToRespondents")));
		templateContext.put(
			"showRequiredFieldsWarning",
			ddmFormRenderingContext.isShowRequiredFieldsWarning());

		boolean showSubmitButton = ddmFormRenderingContext.isShowSubmitButton();

		if (ddmFormRenderingContext.isReadOnly()) {
			showSubmitButton = false;
		}

		templateContext.put("showSubmitButton", showSubmitButton);

		templateContext.put("strings", _getLanguageStringsMap(resourceBundle));

		String submitLabel = GetterUtil.getString(
			ddmFormRenderingContext.getSubmitLabel(),
			LanguageUtil.get(resourceBundle, "submit-form"));

		templateContext.put("submitLabel", submitLabel);

		templateContext.put(
			"submittable", ddmFormRenderingContext.isSubmittable());
		templateContext.put(
			"templateNamespace", getTemplateNamespace(ddmFormLayout));
		templateContext.put("validations", _getValidations(locale));
		templateContext.put("viewMode", ddmFormRenderingContext.isViewMode());

		return templateContext;
	}

	private String _getDDMFormContextProviderServletURL() {
		String servletContextPath = _getServletContextPath();

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-context-provider/");
	}

	private Map<String, String> _getLanguageStringsMap(
		ResourceBundle resourceBundle) {

		return HashMapBuilder.put(
			"next", LanguageUtil.get(resourceBundle, "next")
		).put(
			"previous", LanguageUtil.get(resourceBundle, "previous")
		).build();
	}

	private List<Object> _getPages(
		DDMForm ddmForm, DDMFormLayout ddmFormLayout,
		DDMFormRenderingContext ddmFormRenderingContext) {

		DDMFormPagesTemplateContextFactory ddmFormPagesTemplateContextFactory =
			new DDMFormPagesTemplateContextFactory(
				ddmForm, ddmFormLayout, ddmFormRenderingContext,
				_ddmStructureLayoutLocalService, _ddmStructureLocalService,
				_groupLocalService, _jsonFactory);

		ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
			_ddmFormEvaluator);
		ddmFormPagesTemplateContextFactory.setDDMFormFieldTypeServicesTracker(
			_ddmFormFieldTypeServicesTracker);

		return ddmFormPagesTemplateContextFactory.create();
	}

	private String _getServletContextPath() {
		String proxyPath = _portal.getPathProxy();

		ServletConfig servletConfig =
			_ddmFormContextProviderServlet.getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		return proxyPath.concat(servletContext.getContextPath());
	}

	private HashMap<String, Object> _getValidations(Locale locale) {
		HashMap<String, Object> map = new HashMap<>();

		for (String key : _serviceTrackerMap.keySet()) {
			List<DDMValidation> ddmValidations = _serviceTrackerMap.getService(
				key);

			Stream<DDMValidation> stream = ddmValidations.stream();

			map.put(
				key,
				stream.map(
					ddmValidation -> HashMapBuilder.put(
						"label", ddmValidation.getLabel(locale)
					).put(
						"name", ddmValidation.getName()
					).put(
						"parameterMessage",
						ddmValidation.getParameterMessage(locale)
					).put(
						"template", ddmValidation.getTemplate()
					).build()
				).toArray());
		}

		return map;
	}

	private void _setDDMFormFieldsEvaluableProperty(
		DDMForm ddmForm, DDMFormLayout ddmFormLayout) {

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (String evaluableDDMFormFieldName :
				_ddmFormTemplateContextFactoryHelper.
					getEvaluableDDMFormFieldNames(ddmForm, ddmFormLayout)) {

			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				evaluableDDMFormFieldName);

			ddmFormField.setProperty("evaluable", true);
		}
	}

	private Map<String, Object> _toMap(DDMFormRule ddmFormRule) {
		return HashMapBuilder.<String, Object>put(
			"actions", ddmFormRule.getActions()
		).put(
			"condition", ddmFormRule.getCondition()
		).put(
			"enable", ddmFormRule.isEnabled()
		).build();
	}

	private List<Object> _toObjectList(List<DDMFormRule> ddmFormRules) {
		if (ddmFormRules == null) {
			return Collections.emptyList();
		}

		Stream<DDMFormRule> stream = ddmFormRules.stream();

		return stream.map(
			this::_toMap
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
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private FFDateFieldTypeValidationConfigurationActivator
		_ffDateFieldTypeValidationConfigurationActivator;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, List<DDMValidation>> _serviceTrackerMap;

}