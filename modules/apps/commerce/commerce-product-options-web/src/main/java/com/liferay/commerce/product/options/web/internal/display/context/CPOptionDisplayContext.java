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

package com.liferay.commerce.product.options.web.internal.display.context;

import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.product.configuration.CPOptionConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPOptionDisplayContext {

	public CPOptionDisplayContext(
			ConfigurationProvider configurationProvider, CPOption cpOption,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			PortletResourcePermission portletResourcePermission,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_configurationProvider = configurationProvider;
		_cpOption = cpOption;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_portletResourcePermission = portletResourcePermission;

		cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public CPOption getCPOption() {
		return _cpOption;
	}

	public long getCPOptionId() {
		if (_cpOption == null) {
			return 0;
		}

		return _cpOption.getCPOptionId();
	}

	public CreationMenu getCreationMenu() throws Exception {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						cpRequestHelper.getLiferayPortletResponse()
					).setMVCRenderCommandName(
						"/cp_options/add_cp_option"
					).setBackURL(
						cpRequestHelper.getCurrentURL()
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildString());
				dropdownItem.setLabel("add-option-template");
				dropdownItem.setTarget("modal");
			}
		).build();
	}

	public String getDDMFormFieldTypeLabel(
		DDMFormFieldType ddmFormFieldType, Locale locale) {

		Map<String, Object> ddmFormFieldTypeProperties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldType.getName());

		String label = MapUtil.getString(
			ddmFormFieldTypeProperties, "ddm.form.field.type.label");

		try {
			if (Validator.isNotNull(label)) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", locale, ddmFormFieldType.getClass());

				return LanguageUtil.get(resourceBundle, label);
			}
		}
		catch (MissingResourceException missingResourceException) {
			if (_log.isWarnEnabled()) {
				_log.warn(missingResourceException, missingResourceException);
			}
		}

		return ddmFormFieldType.getName();
	}

	public List<DDMFormFieldType> getDDMFormFieldTypes()
		throws PortalException {

		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		CPOptionConfiguration cpOptionConfiguration =
			_configurationProvider.getConfiguration(
				CPOptionConfiguration.class,
				new SystemSettingsLocator(CPConstants.SERVICE_NAME_CP_OPTION));

		String[] ddmFormFieldTypesAllowed =
			cpOptionConfiguration.ddmFormFieldTypesAllowed();

		return DDMFormFieldTypeUtil.getDDMFormFieldTypesAllowed(
			ddmFormFieldTypes, ddmFormFieldTypesAllowed);
	}

	public List<HeaderActionModel> getHeaderActionModels() {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		RenderResponse renderResponse = cpRequestHelper.getRenderResponse();

		HeaderActionModel publishHeaderActionModel = new HeaderActionModel(
			"btn-primary", renderResponse.getNamespace() + "fm", null, null,
			"save");

		headerActionModels.add(publishHeaderActionModel);

		return headerActionModels;
	}

	public List<ClayDataSetActionDropdownItem>
			getOptionClayDataSetActionDropdownItems()
		throws PortalException {

		return _getClayDataSetActionDropdownItems(
			PortletURLBuilder.createRenderURL(
				cpRequestHelper.getRenderResponse()
			).setMVCRenderCommandName(
				"/cp_options/edit_cp_option"
			).setRedirect(
				cpRequestHelper.getCurrentURL()
			).setParameter(
				"cpOptionId", "{id}"
			).setParameter(
				"screenNavigationCategoryKey",
				CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS
			).buildString(),
			false);
	}

	public List<ClayDataSetActionDropdownItem>
			getOptionValueClayDataSetActionDropdownItems()
		throws PortalException {

		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			cpRequestHelper.getRenderResponse()
		).setMVCRenderCommandName(
			"/cp_options/edit_cp_option_value"
		).setRedirect(
			cpRequestHelper.getCurrentURL()
		).setParameter(
			"cpOptionValueId", "{id}"
		).setParameter(
			"screenNavigationCategoryKey",
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			throw new PortalException(windowStateException);
		}

		return _getClayDataSetActionDropdownItems(portletURL.toString(), true);
	}

	public CreationMenu getOptionValueCreationMenu(long cpOptionId)
		throws Exception {

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						cpRequestHelper.getLiferayPortletResponse()
					).setMVCRenderCommandName(
						"/cp_options/add_cp_option_value"
					).setBackURL(
						cpRequestHelper.getCurrentURL()
					).setParameter(
						"cpOptionId", cpOptionId
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildString());
				dropdownItem.setLabel("add-option-value-template");
				dropdownItem.setTarget("modal");
			}
		).build();
	}

	public boolean hasPermission(String actionId) throws PortalException {
		RenderRequest renderRequest = cpRequestHelper.getRenderRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _portletResourcePermission.contains(
			themeDisplay.getPermissionChecker(), null, actionId);
	}

	public boolean hasValues(CPOption cpOption) {
		if (_hasDDMFormFieldTypeProperties(
				cpOption.getDDMFormFieldTypeName()) &&
			_isListFieldTypeDataDomain(cpOption.getDDMFormFieldTypeName())) {

			return true;
		}

		return false;
	}

	protected final CPRequestHelper cpRequestHelper;

	private List<ClayDataSetActionDropdownItem>
		_getClayDataSetActionDropdownItems(
			String portletURL, boolean sidePanel) {

		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems =
			new ArrayList<>();

		ClayDataSetActionDropdownItem clayDataSetActionDropdownItem =
			new ClayDataSetActionDropdownItem(
				portletURL, "pencil", "edit",
				LanguageUtil.get(cpRequestHelper.getRequest(), "edit"), "get",
				null, null);

		if (sidePanel) {
			clayDataSetActionDropdownItem.setTarget("sidePanel");
		}

		clayDataSetActionDropdownItems.add(clayDataSetActionDropdownItem);

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(cpRequestHelper.getRequest(), "delete"),
				"delete", "delete", "headless"));

		return clayDataSetActionDropdownItems;
	}

	private boolean _hasDDMFormFieldTypeProperties(
		String ddmFormFieldTypeName) {

		Map<String, Object> ddmFormFieldTypeProperties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldTypeName);

		if (ddmFormFieldTypeProperties == null) {
			return false;
		}

		return true;
	}

	private boolean _isListFieldTypeDataDomain(String ddmFormFieldTypeName) {
		Map<String, Object> properties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldTypeName);

		String fieldTypeDataDomain = MapUtil.getString(
			properties, "ddm.form.field.type.data.domain");

		if (Validator.isNotNull(fieldTypeDataDomain) &&
			fieldTypeDataDomain.equals("list")) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPOptionDisplayContext.class);

	private final ConfigurationProvider _configurationProvider;
	private CPOption _cpOption;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final PortletResourcePermission _portletResourcePermission;

}