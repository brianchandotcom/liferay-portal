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

package com.liferay.commerce.initializer.customer.portal.internal.util;

import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.commerce.product.demo.data.creator.CPDemoDataCreator;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.GroupInitializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {"group.initializer.key=" + CustomerPortalGroupInitializer.KEY},
	service = GroupInitializer.class
)
public class CustomerPortalGroupInitializer implements GroupInitializer {

	public static final String KEY = "customer-portal-initializer";

	public void createSampleData(ServiceContext serviceContext)
		throws Exception {

		_cpDemoDataCreator.create(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), true);
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "customer-portal-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "customer-portal");
	}

	public ServiceContext getServiceContext(long groupId)
		throws PortalException {

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());
		Group group = _groupLocalService.getGroup(groupId);

		Locale locale = LocaleUtil.getSiteDefault();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(user.getUserId());
		serviceContext.setTimeZone(user.getTimeZone());

		return serviceContext;
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			ServiceContext serviceContext = getServiceContext(groupId);

			configureB2BSite(groupId, serviceContext);

			_cpFileImporter.cleanLayouts(serviceContext);

			_cpFileImporter.updateLookAndFeel(
				_CUSTOMER_PORTAL_THEME_ID, serviceContext);

			createLayouts(serviceContext);

			createRoles(serviceContext);

			createSampleData(serviceContext);

			setThemePortletSettings(serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new InitializationException(e);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		Theme theme = _themeLocalService.fetchTheme(
			companyId, _CUSTOMER_PORTAL_THEME_ID);

		if (theme == null) {
			if (_log.isInfoEnabled()) {
				_log.info(_CUSTOMER_PORTAL_THEME_ID + " is not registered");
			}

			return false;
		}

		return true;
	}

	protected void configureB2BSite(long groupId, ServiceContext serviceContext)
		throws Exception {

		_commerceOrganizationLocalService.configureB2BSite(
			groupId, serviceContext);

		Group group = _groupLocalService.getGroup(groupId);

		_addDemoAccountOrganizations(
			group.getOrganizationId(), group.getNameCurrentValue(), 3,
			serviceContext);
	}

	protected void createCommerceRoles(JSONArray jsonArray) throws Exception {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String name = jsonObject.getString("name");

			_createConfiguration(name);
		}
	}

	protected void createLayouts(ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		String layoutsPath = _DEPENDENCY_PATH + "layouts.json";

		String layoutsJSON = StringUtil.read(
			clazz.getClassLoader(), layoutsPath, false);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(layoutsJSON);

		_cpFileImporter.createLayouts(jsonArray, false, serviceContext);
	}

	protected void createRoles(ServiceContext serviceContext) throws Exception {
		Class<?> clazz = getClass();

		String rolePath = _DEPENDENCY_PATH + "roles.json";

		String rolesJSON = StringUtil.read(
			clazz.getClassLoader(), rolePath, true);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(rolesJSON);

		_cpFileImporter.createRoles(jsonArray, serviceContext);

		createCommerceRoles(jsonArray);
	}

	protected DDMTemplate getDDMTemplate(
			String portletClassName, String filePath, String name,
			ServiceContext serviceContext)
		throws Exception {

		long classNameId = _portal.getClassNameId(portletClassName);
		long resourceClassNameId = _portal.getClassNameId(
			PortletDisplayTemplate.class);

		return _cpFileImporter.getDDMTemplate(
			_getFile(filePath), classNameId, 0L, resourceClassNameId, name,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
			TemplateConstants.LANG_TYPE_FTL, serviceContext);
	}

	protected JSONArray getThemePortletSettingJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String themePortletSettingsPath =
			_DEPENDENCY_PATH + "theme-portlet-settings.json";

		String themePortletSettingsJSON = StringUtil.read(
			clazz.getClassLoader(), themePortletSettingsPath, true);

		return JSONFactoryUtil.createJSONArray(themePortletSettingsJSON);
	}

	protected void setCPContentPortletSettings(
			JSONObject jsonObject, String portletName,
			ServiceContext serviceContext)
		throws Exception {

		if (portletName.equals(_CP_CONTENT_PORTLET_NAME)) {
			String instanceId = jsonObject.getString("instanceId");
			String layoutFriendlyURL = jsonObject.getString(
				"layoutFriendlyURL");

			JSONObject portletPreferencesJSONObject = jsonObject.getJSONObject(
				"portletPreferences");

			String portletId = PortletIdCodec.encode(portletName, instanceId);

			PortletPreferences portletSetup =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					serviceContext.getCompanyId(),
					serviceContext.getScopeGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
					LayoutConstants.DEFAULT_PLID, portletId, StringPool.BLANK);

			Iterator<String> iterator = portletPreferencesJSONObject.keys();

			while (iterator.hasNext()) {
				String key = iterator.next();

				String value = portletPreferencesJSONObject.getString(key);

				if (key.equals("displayStyleGroupId")) {
					value = String.valueOf(serviceContext.getScopeGroupId());
				}

				portletSetup.setValue(key, value);
			}

			String filePath = _DEPENDENCY_PATH + "product_display_template.ftl";

			DDMTemplate ddmTemplate = getDDMTemplate(
				_SIMPLE_CP_TYPE_CLASS_NAME, filePath,
				"Commerce Product Customer Portal", serviceContext);

			String ddmTemplateKey =
				"ddmTemplate_" + ddmTemplate.getTemplateKey();

			portletSetup.setValue("displayStyle", ddmTemplateKey);

			portletSetup.store();

			long plid = LayoutConstants.DEFAULT_PLID;

			if (Validator.isNotNull(layoutFriendlyURL)) {
				Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
					serviceContext.getScopeGroupId(), false, layoutFriendlyURL);

				if (layout != null) {
					plid = layout.getPlid();
				}
			}

			if (plid > LayoutConstants.DEFAULT_PLID) {
				_setPlidPortletPreferences(plid, portletId, serviceContext);
			}
		}
	}

	protected void setCPSearchResultPortletSettings(
			JSONObject jsonObject, String portletName,
			ServiceContext serviceContext)
		throws Exception {

		if (portletName.equals(_CP_SEARCH_RESULT_PORTLET_NAME)) {
			String instanceId = jsonObject.getString("instanceId");
			String layoutFriendlyURL = jsonObject.getString(
				"layoutFriendlyURL");

			JSONObject portletPreferencesJSONObject = jsonObject.getJSONObject(
				"portletPreferences");

			String portletId = PortletIdCodec.encode(portletName, instanceId);

			PortletPreferences portletSetup =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					serviceContext.getCompanyId(),
					serviceContext.getScopeGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
					LayoutConstants.DEFAULT_PLID, portletId, StringPool.BLANK);

			Iterator<String> iterator = portletPreferencesJSONObject.keys();

			while (iterator.hasNext()) {
				String key = iterator.next();

				String value = portletPreferencesJSONObject.getString(key);

				if (key.equals("displayStyleGroupId")) {
					value = String.valueOf(serviceContext.getScopeGroupId());
				}

				portletSetup.setValue(key, value);
			}

			String filePath = _DEPENDENCY_PATH + "catalog_display_template.ftl";

			DDMTemplate ddmTemplate = getDDMTemplate(
				_CP_SEARCH_RESULT_PORTLET_CLASS_NAME, filePath,
				"Commerce Catalog Customer Portal", serviceContext);

			String ddmTemplateKey =
				"ddmTemplate_" + ddmTemplate.getTemplateKey();

			portletSetup.setValue("displayStyle", ddmTemplateKey);

			portletSetup.store();

			long plid = LayoutConstants.DEFAULT_PLID;

			if (Validator.isNotNull(layoutFriendlyURL)) {
				Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
					serviceContext.getScopeGroupId(), false, layoutFriendlyURL);

				if (layout != null) {
					plid = layout.getPlid();
				}
			}

			if (plid > LayoutConstants.DEFAULT_PLID) {
				_setPlidPortletPreferences(plid, portletId, serviceContext);
			}
		}
	}

	protected void setThemePortletSettings(ServiceContext serviceContext)
		throws Exception {

		JSONArray jsonArray = getThemePortletSettingJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String portletName = jsonObject.getString("portletName");

			setCPContentPortletSettings(
				jsonObject, portletName, serviceContext);

			setCPSearchResultPortletSettings(
				jsonObject, portletName, serviceContext);
		}
	}

	private void _addDemoAccountOrganizations(
			long organizationId, String groupName, int quantity,
			ServiceContext serviceContext)
		throws Exception {

		int i = 1;

		while (i <= quantity) {
			String name = "Demo Account " + groupName + StringPool.SPACE + i;

			_organizationLocalService.addOrganization(
				serviceContext.getUserId(), organizationId, name,
				CommerceOrganizationConstants.TYPE_ACCOUNT, 0L, 0L,
				ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
				false, serviceContext);

			i++;
		}
	}

	private void _createConfiguration(String name) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			_getConfigurationFilter());

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				Dictionary<String, Object> props =
					configuration.getProperties();

				String roleName = (String)props.get("roleName");

				if (name.equals(roleName)) {
					return;
				}
			}
		}

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				_COMMERCE_ROLE_CONFIGURATION_PID, StringPool.QUESTION);

		Dictionary<String, Object> props = configuration.getProperties();

		if (props == null) {
			props = new Hashtable<>();
		}

		props.put("roleName", name);

		configuration.update(props);
	}

	private String _getConfigurationFilter() {
		StringBundler sb = new StringBundler(5);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		sb.append(StringPool.EQUAL);
		sb.append(_COMMERCE_ROLE_CONFIGURATION_PID);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private File _getFile(String location) throws IOException {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(location);

		return FileUtil.createTempFile(inputStream);
	}

	private void _setPlidPortletPreferences(
			long plid, String portletId, ServiceContext serviceContext)
		throws Exception {

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				serviceContext.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
				StringPool.BLANK);

		portletSetup.store();
	}

	private static final String _COMMERCE_ROLE_CONFIGURATION_PID =
		"com.liferay.commerce.user.web.internal.configuration." +
			"CommerceRoleGroupServiceConfiguration";

	private static final String _CP_CONTENT_PORTLET_NAME =
		"com_liferay_commerce_product_content_web_internal_portlet_" +
			"CPContentPortlet";

	private static final String _CP_SEARCH_RESULT_PORTLET_CLASS_NAME =
		"com.liferay.commerce.product.content.search.web.internal.portlet." +
			"CPSearchResultsPortlet";

	private static final String _CP_SEARCH_RESULT_PORTLET_NAME =
		"com_liferay_commerce_product_content_search_web_internal_portlet_" +
			"CPSearchResultsPortlet";

	private static final String _CUSTOMER_PORTAL_THEME_ID =
		"customerportal_WAR_commercethemecustomerportal";

	private static final String _DEPENDENCY_PATH =
		"com/liferay/commerce/initializer/customer/portal/internal" +
			"/dependencies/";

	private static final String _SIMPLE_CP_TYPE_CLASS_NAME =
		"com.liferay.commerce.product.type.simple.internal.SimpleCPType";

	private static final Log _log = LogFactoryUtil.getLog(
		CustomerPortalGroupInitializer.class);

	@Reference
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private CPDemoDataCreator _cpDemoDataCreator;

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.initializer.customer.portal)"
	)
	private ServletContext _servletContext;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}