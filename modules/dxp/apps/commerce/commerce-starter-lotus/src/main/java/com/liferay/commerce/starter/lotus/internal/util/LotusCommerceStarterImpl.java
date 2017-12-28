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

package com.liferay.commerce.starter.lotus.internal.util;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.product.demo.data.creator.CPDemoDataCreator;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.product.service.CPGroupLocalService;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.util.CommerceStarter;
import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.service.CommerceRegionLocalService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.starter.key=" + LotusCommerceStarterImpl.KEY,
		"commerce.starter.order:Integer=10"
	},
	service = CommerceStarter.class
)
public class LotusCommerceStarterImpl implements CommerceStarter {

	public static final String CP_ASSET_CATEGORIES_NAVIGATION_PORTLET_NAME =
		"com_liferay_commerce_product_asset_categories_navigation_web_" +
			"internal_portlet_CPAssetCategoriesNavigationPortlet";

	public static final String DEPENDENCY_PATH =
		"com/liferay/commerce/starter/lotus/internal/dependencies/";

	public static final String KEY = "lotus";

	public static final String LOTUS_THEME_ID = "lotus_WAR_commercethemelotus";

	public static final String SITE_NAVIGATION_MENU_PORTLET_NAME =
		"com_liferay_site_navigation_menu_web_portlet_" +
			"SiteNavigationMenuPortlet";

	@Override
	public void create(HttpServletRequest httpServletRequest) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = getServiceContext(httpServletRequest);

		_cpFileImporter.cleanLayouts(serviceContext);

		createJournalArticles(serviceContext, themeDisplay);

		_cpFileImporter.updateLookAndFeel(LOTUS_THEME_ID, serviceContext);

		createLayouts(serviceContext);

		_cpGroupLocalService.addCPGroup(serviceContext);

		createSampleData(serviceContext);

		_commerceCountryLocalService.importDefaultCountries(serviceContext);

		_commerceRegionLocalService.importCommerceRegions(serviceContext);

		_cpMeasurementUnitLocalService.importDefaultValues(serviceContext);

		_commerceCurrencyLocalService.importDefaultValues(serviceContext);

		setThemePortletSettings(serviceContext);
	}

	public void createSampleData(ServiceContext serviceContext)
		throws Exception {

		_cpDemoDataCreator.create(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), true);
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "lotus-store-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "lotus-store");
	}

	public ServiceContext getServiceContext(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return serviceContext;
	}

	@Override
	public String getThumbnailSrc() {
		String contextPath = _servletContext.getContextPath();

		String thumbnailSrc = contextPath + "/images/thumbnail.png";

		return thumbnailSrc;
	}

	@Override
	public boolean isActive(HttpServletRequest httpServletRequest) {
		long companyId = _portal.getCompanyId(httpServletRequest);

		Theme theme = _themeLocalService.fetchTheme(companyId, LOTUS_THEME_ID);

		if (theme == null) {
			if (_log.isInfoEnabled()) {
				_log.info(LOTUS_THEME_ID + " is not registered");
			}

			return false;
		}

		return true;
	}

	@Override
	public void renderPreview(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			"render.jsp-servletContext", _servletContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/render.jsp");
	}

	protected void createJournalArticles(
			ServiceContext serviceContext, ThemeDisplay themeDisplay)
		throws Exception {

		Class<?> clazz = getClass();

		String journalArticlePath = DEPENDENCY_PATH + "journal-articles.json";

		String journalArticleJSON = StringUtil.read(
			clazz.getClassLoader(), journalArticlePath, false);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			journalArticleJSON);

		_cpFileImporter.createJournalArticles(
			jsonArray, clazz.getClassLoader(), DEPENDENCY_PATH, serviceContext,
			themeDisplay);
	}

	protected void createLayouts(ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		String layoutsPath = DEPENDENCY_PATH + "layouts.json";

		String layoutsJSON = StringUtil.read(
			clazz.getClassLoader(), layoutsPath, false);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(layoutsJSON);

		_cpFileImporter.createLayouts(jsonArray, serviceContext);
	}

	protected JSONArray getThemePortletSettingJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String themePortletSettingsPath =
			DEPENDENCY_PATH + "theme-portlet-settings.json";

		String themePortletSettingsJSON = StringUtil.read(
			clazz.getClassLoader(), themePortletSettingsPath, false);

		return JSONFactoryUtil.createJSONArray(themePortletSettingsJSON);
	}

	protected void setCPAssetCategoriesNavigationPortletSettings(
			JSONObject jsonObject, String portletName,
			ServiceContext serviceContext)
		throws Exception {

		if (portletName.equals(CP_ASSET_CATEGORIES_NAVIGATION_PORTLET_NAME)) {
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

			portletSetup.store();
		}
	}

	protected void setSiteNavigationMenuPortletSettings(
			JSONObject jsonObject, String portletName,
			ServiceContext serviceContext)
		throws Exception {

		if (portletName.equals(SITE_NAVIGATION_MENU_PORTLET_NAME)) {
			String instanceId = jsonObject.getString("instanceId");
			String rootLayoutFriendlyURL = jsonObject.getString(
				"rootLayoutFriendlyURL");

			JSONObject portletPreferencesJSONObject = jsonObject.getJSONObject(
				"portletPreferences");

			Layout layout = null;

			if (Validator.isNotNull(rootLayoutFriendlyURL)) {
				layout = _layoutLocalService.fetchLayoutByFriendlyURL(
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
					if (layout == null) {
						value = StringPool.BLANK;
					}
					else {
						value = layout.getUuid();
					}
				}

				portletSetup.setValue(key, value);
			}

			portletSetup.store();
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

			setSiteNavigationMenuPortletSettings(
				jsonObject, portletName, serviceContext);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LotusCommerceStarterImpl.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceRegionLocalService _commerceRegionLocalService;

	@Reference
	private CPDemoDataCreator _cpDemoDataCreator;

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference
	private CPGroupLocalService _cpGroupLocalService;

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.starter.lotus)"
	)
	private ServletContext _servletContext;

	@Reference
	private ThemeLocalService _themeLocalService;

}