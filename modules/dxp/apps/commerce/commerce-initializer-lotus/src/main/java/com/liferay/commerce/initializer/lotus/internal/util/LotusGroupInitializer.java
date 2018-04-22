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

package com.liferay.commerce.initializer.lotus.internal.util;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.model.CommercePaymentEngine;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.demo.data.creator.CPDemoDataCreator;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.service.CommercePaymentMethodLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.util.CommercePaymentEngineRegistry;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.ThemeSetting;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.GroupInitializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "group.initializer.key=" + LotusGroupInitializer.KEY,
	service = GroupInitializer.class
)
public class LotusGroupInitializer implements GroupInitializer {

	public static final String KEY = "lotus";

	public void createSampleData(ServiceContext serviceContext)
		throws Exception {

		_cpDemoDataCreator.create(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), true);
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "lotus-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, KEY);
	}

	public ServiceContext getServiceContext(long groupId)
		throws PortalException {

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		Locale locale = LocaleUtil.getSiteDefault();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(user.getUserId());
		serviceContext.setTimeZone(user.getTimeZone());

		return serviceContext;
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.jpg";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			ServiceContext serviceContext = getServiceContext(groupId);

			_cpFileImporter.cleanLayouts(serviceContext);

			createJournalArticles(serviceContext);

			_cpFileImporter.updateLookAndFeel(_LOTUS_THEME_ID, serviceContext);

			createLayouts(serviceContext);

			createSampleData(serviceContext);

			setPaymentMethod(serviceContext);

			setShippingMethod(serviceContext);

			setThemeSettings(serviceContext);

			setThemePortletSettings(serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new InitializationException(e);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		Theme theme = _themeLocalService.fetchTheme(companyId, _LOTUS_THEME_ID);

		if (theme == null) {
			if (_log.isInfoEnabled()) {
				_log.info(_LOTUS_THEME_ID + " is not registered");
			}

			return false;
		}

		return true;
	}

	protected void createJournalArticles(ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		String journalArticleJSON = StringUtil.read(
			clazz.getClassLoader(), _DEPENDENCY_PATH + "journal-articles.json",
			false);

		JSONArray jsonArray = _jsonFactory.createJSONArray(journalArticleJSON);

		_cpFileImporter.createJournalArticles(
			jsonArray, clazz.getClassLoader(), _DEPENDENCY_PATH,
			serviceContext);
	}

	protected void createLayouts(ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		String layoutsJSON = StringUtil.read(
			clazz.getClassLoader(), _DEPENDENCY_PATH + "layouts.json", false);

		JSONArray jsonArray = _jsonFactory.createJSONArray(layoutsJSON);

		_cpFileImporter.createLayouts(jsonArray, false, serviceContext);
	}

	protected DDMTemplate getDDMTemplate(ServiceContext serviceContext)
		throws Exception {

		File file = _getFile(
			_DEPENDENCY_PATH +
				"asset_categories_navigation_portlet_display_template_lotus." +
					"ftl");

		long classNameId = _portal.getClassNameId(
			_CP_ASSET_CATEGORIES_NAVIGATION_PORTLET_CLASS_NAME);
		long resourceClassNameId = _portal.getClassNameId(
			PortletDisplayTemplate.class);

		return _cpFileImporter.getDDMTemplate(
			file, classNameId, 0L, resourceClassNameId,
			"Commerce Categories Navigation Lotus",
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
			TemplateConstants.LANG_TYPE_FTL, serviceContext);
	}

	protected JSONArray getThemePortletSettingJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String themePortletSettingsJSON = StringUtil.read(
			clazz.getClassLoader(),
			_DEPENDENCY_PATH + "theme-portlet-settings.json", false);

		return _jsonFactory.createJSONArray(themePortletSettingsJSON);
	}

	protected JSONObject getThemeSettingsJSONObject() throws Exception {
		Class<?> clazz = getClass();

		String themeSettingsJSON = StringUtil.read(
			clazz.getClassLoader(), _DEPENDENCY_PATH + "theme-settings.json",
			false);

		return _jsonFactory.createJSONObject(themeSettingsJSON);
	}

	protected void setCPAssetCategoriesNavigationPortletSettings(
			JSONObject jsonObject, String portletName,
			ServiceContext serviceContext)
		throws Exception {

		if (portletName.equals(_CP_ASSET_CATEGORIES_NAVIGATION_PORTLET_NAME)) {
			String instanceId = jsonObject.getString("instanceId");
			String vocabularyName = jsonObject.getString("vocabularyName");

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

				if (key.equals("assetVocabularyId")) {
					AssetVocabulary assetVocabulary = null;

					if (Validator.isNotNull(vocabularyName)) {
						assetVocabulary =
							_assetVocabularyLocalService.fetchGroupVocabulary(
								serviceContext.getScopeGroupId(),
								vocabularyName);
					}

					if (assetVocabulary == null) {
						value = StringPool.BLANK;
					}
					else {
						value = String.valueOf(
							assetVocabulary.getVocabularyId());
					}
				}

				portletSetup.setValue(key, value);
			}

			DDMTemplate ddmTemplate = getDDMTemplate(serviceContext);

			String ddmTemplateKey =
				"ddmTemplate_" + ddmTemplate.getTemplateKey();

			portletSetup.setValue("displayStyle", ddmTemplateKey);

			portletSetup.setValue(
				"displayStyleGroupId",
				String.valueOf(ddmTemplate.getGroupId()));

			portletSetup.store();
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

	protected void setPaymentMethod(ServiceContext serviceContext)
		throws Exception {

		Locale locale = serviceContext.getLocale();

		String engineKey = "money-order";

		CommercePaymentEngine commercePaymentEngine =
			_commercePaymentEngineRegistry.getCommercePaymentEngine(engineKey);

		if (commercePaymentEngine == null) {
			return;
		}

		Map<Locale, String> nameMap = Collections.singletonMap(
			locale, commercePaymentEngine.getName(locale));
		Map<Locale, String> descriptionMap = Collections.singletonMap(
			locale, commercePaymentEngine.getDescription(locale));

		File imageFile = _getFile(_DEPENDENCY_PATH + "money_order.png");

		_commercePaymentMethodLocalService.addCommercePaymentMethod(
			nameMap, descriptionMap, imageFile, engineKey,
			Collections.<String, String>emptyMap(), 1, true, serviceContext);
	}

	protected void setShippingMethod(ServiceContext serviceContext)
		throws Exception {

		Locale locale = serviceContext.getLocale();

		String engineKey = "fixed";

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				engineKey);

		if (commerceShippingEngine == null) {
			return;
		}

		Map<Locale, String> nameMap = Collections.singletonMap(
			locale, commerceShippingEngine.getName(locale));
		Map<Locale, String> descriptionMap = Collections.singletonMap(
			locale, commerceShippingEngine.getDescription(locale));

		File imageFile = _getFile(_DEPENDENCY_PATH + "fixed_price.png");

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.addCommerceShippingMethod(
				nameMap, descriptionMap, imageFile, engineKey, 1, true,
				serviceContext);

		Map<Locale, String> shippingFixedOptionNameMap =
			Collections.singletonMap(locale, "Free Shipping");

		_commerceShippingFixedOptionLocalService.addCommerceShippingFixedOption(
			commerceShippingMethod.getCommerceShippingMethodId(),
			shippingFixedOptionNameMap, Collections.<Locale, String>emptyMap(),
			BigDecimal.ZERO, 1, serviceContext);
	}

	protected void setSiteNavigationMenuPortletSettings(
			JSONObject jsonObject, String portletName,
			ServiceContext serviceContext)
		throws Exception {

		if (portletName.equals(_SITE_NAVIGATION_MENU_PORTLET_NAME)) {
			String instanceId = jsonObject.getString("instanceId");
			String layoutFriendlyURL = jsonObject.getString(
				"layoutFriendlyURL");
			String rootLayoutFriendlyURL = jsonObject.getString(
				"rootLayoutFriendlyURL");

			JSONObject portletPreferencesJSONObject = jsonObject.getJSONObject(
				"portletPreferences");

			Layout rootLayout = null;

			if (Validator.isNotNull(rootLayoutFriendlyURL)) {
				rootLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
					serviceContext.getScopeGroupId(), false,
					rootLayoutFriendlyURL);
			}

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
				else if (key.equals("rootLayoutUuid")) {
					if (rootLayout == null) {
						value = StringPool.BLANK;
					}
					else {
						value = rootLayout.getUuid();
					}
				}

				portletSetup.setValue(key, value);
			}

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

			setCPAssetCategoriesNavigationPortletSettings(
				jsonObject, portletName, serviceContext);

			setCPSearchResultPortletSettings(
				jsonObject, portletName, serviceContext);

			setSiteNavigationMenuPortletSettings(
				jsonObject, portletName, serviceContext);
		}
	}

	protected void setThemeSettings(ServiceContext serviceContext)
		throws Exception {

		JSONObject themeSettingsJSONObject = getThemeSettingsJSONObject();

		Iterator<String> iterator = themeSettingsJSONObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			String value = themeSettingsJSONObject.getString(key);

			updateThemeSetting(key, value, serviceContext);
		}
	}

	protected void updateThemeSetting(
		String key, String value, ServiceContext serviceContext) {

		Theme theme = _themeLocalService.fetchTheme(
			serviceContext.getCompanyId(), _LOTUS_THEME_ID);

		if (theme == null) {
			return;
		}

		Map<String, ThemeSetting> configurableSettings =
			theme.getConfigurableSettings();

		ThemeSetting themeSetting = configurableSettings.get(key);

		themeSetting.setValue(value);
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

	private static final String
		_CP_ASSET_CATEGORIES_NAVIGATION_PORTLET_CLASS_NAME =
			"com.liferay.commerce.product.asset.categories.navigation.web." +
				"internal.portlet.CPAssetCategoriesNavigationPortlet";

	private static final String _CP_ASSET_CATEGORIES_NAVIGATION_PORTLET_NAME =
		"com_liferay_commerce_product_asset_categories_navigation_web_" +
			"internal_portlet_CPAssetCategoriesNavigationPortlet";

	private static final String _CP_SEARCH_RESULT_PORTLET_NAME =
		"com_liferay_commerce_product_content_search_web_internal_portlet_" +
			"CPSearchResultsPortlet";

	private static final String _DEPENDENCY_PATH =
		"com/liferay/commerce/initializer/lotus/internal/dependencies/";

	private static final String _LOTUS_THEME_ID =
		"lotus_WAR_commercethemelotus";

	private static final String _SITE_NAVIGATION_MENU_PORTLET_NAME =
		"com_liferay_site_navigation_menu_web_portlet_" +
			"SiteNavigationMenuPortlet";

	private static final Log _log = LogFactoryUtil.getLog(
		LotusGroupInitializer.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CommercePaymentEngineRegistry _commercePaymentEngineRegistry;

	@Reference
	private CommercePaymentMethodLocalService
		_commercePaymentMethodLocalService;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private CPDemoDataCreator _cpDemoDataCreator;

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.initializer.lotus)"
	)
	private ServletContext _servletContext;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}