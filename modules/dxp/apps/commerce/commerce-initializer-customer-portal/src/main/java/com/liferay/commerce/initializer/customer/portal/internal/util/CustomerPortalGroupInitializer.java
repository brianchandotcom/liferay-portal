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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.service.CommerceRegionLocalService;
import com.liferay.commerce.service.CommerceWarehouseItemLocalService;
import com.liferay.commerce.service.CommerceWarehouseLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.GroupInitializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
	property = "group.initializer.key=" + CustomerPortalGroupInitializer.KEY,
	service = GroupInitializer.class
)
public class CustomerPortalGroupInitializer implements GroupInitializer {

	public static final String KEY = "customer-portal-initializer";

	public long[] addAssetCategories(
			long vocabularyId, JSONArray jsonArray,
			ServiceContext serviceContext)
		throws Exception {

		List<Long> assetCategoryIdsList = new ArrayList<>();

		long classNameId = _portal.getClassNameId(AssetCategory.class);

		for (int i = 0; i < jsonArray.length(); i++) {
			String title = jsonArray.getString(i);

			AssetCategory assetCategory =
				_assetCategoryLocalService.fetchCategory(
					serviceContext.getScopeGroupId(),
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, title,
					vocabularyId);

			if (assetCategory == null) {
				Map<Locale, String> titleMap = Collections.singletonMap(
					LocaleUtil.getSiteDefault(), title);

				assetCategory = _assetCategoryLocalService.addCategory(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, titleMap,
					null, vocabularyId, new String[0], serviceContext);
			}

			assetCategoryIdsList.add(assetCategory.getCategoryId());

			List<CPFriendlyURLEntry> cpFriendlyURLEntries =
				_cpFriendlyURLEntryLocalService.getCPFriendlyURLEntries(
					serviceContext.getScopeGroupId(), classNameId,
					assetCategory.getCategoryId());

			if (cpFriendlyURLEntries.isEmpty()) {
				Map<Locale, String> urlTitleMap = _getUniqueUrlTitles(
					assetCategory);

				_cpFriendlyURLEntryLocalService.addCPFriendlyURLEntries(
					serviceContext.getScopeGroupId(),
					serviceContext.getCompanyId(), AssetCategory.class,
					assetCategory.getCategoryId(), urlTitleMap);
			}
		}

		return ListUtil.toLongArray(assetCategoryIdsList, Long::longValue);
	}

	public void addCPDefinitionAttachmentFileEntry(
			long cpDefinitionId, String fileName, ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		Map<Locale, String> titleMap = Collections.singletonMap(
			serviceContext.getLocale(), fileName);

		InputStream inputStream = classLoader.getResourceAsStream(
			_DEPENDENCY_PATH + "images/" + fileName);

		if (inputStream == null) {
			return;
		}

		File file = null;
		FileEntry fileEntry = null;

		try {
			file = FileUtil.createTempFile(inputStream);

			fileEntry = _dlAppService.addFileEntry(
				serviceContext.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
				_mimeTypes.getContentType(file), fileName, StringPool.BLANK,
				StringPool.BLANK, file, serviceContext);
		}
		finally {
			FileUtil.delete(file);
		}

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		displayCalendar.add(Calendar.YEAR, -1);

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
		int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH);
		int expirationDateDay = expirationCalendar.get(Calendar.DAY_OF_MONTH);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);
		int expirationDateAmPm = expirationCalendar.get(Calendar.AM_PM);

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		_cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(
			classNameId, cpDefinitionId, fileEntry.getFileEntryId(),
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute, true,
			titleMap, null, 0, CPAttachmentFileEntryConstants.TYPE_IMAGE,
			serviceContext);
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "beryl-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "beryl");
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
		return _servletContext.getContextPath() + "/images/thumbnail.jpg";
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

			importProducts(serviceContext);

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

	protected List<CommerceWarehouse> addCommerceWarehouses(
			ServiceContext serviceContext)
		throws PortalException {

		CommerceCountry commerceCountry =
			_commerceCountryLocalService.fetchCommerceCountry(
				serviceContext.getScopeGroupId(), 840);

		CommerceRegion commerceRegion =
			_commerceRegionLocalService.getCommerceRegion(
				commerceCountry.getCommerceCountryId(), "MN");

		List<CommerceWarehouse> commerceWarehouses = new ArrayList<>();

		commerceWarehouses.add(
			_commerceWarehouseLocalService.addCommerceWarehouse(
				"Thief River Falls, Minnesota", StringPool.BLANK, true,
				"1101 MN-1", "", "", "Thief River Falls", "56701",
				commerceRegion.getCommerceRegionId(),
				commerceCountry.getCommerceCountryId(), 48.1252560, -96.1635400,
				serviceContext));

		commerceRegion = _commerceRegionLocalService.getCommerceRegion(
			commerceCountry.getCommerceCountryId(), "IA");

		commerceWarehouses.add(
			_commerceWarehouseLocalService.addCommerceWarehouse(
				"Des Moines, Iowa", StringPool.BLANK, true, "1330 Grand Ave",
				"", "", "Des Moines", "50309",
				commerceRegion.getCommerceRegionId(),
				commerceCountry.getCommerceCountryId(), 41.5853130, -93.6345580,
				serviceContext));

		commerceRegion = _commerceRegionLocalService.getCommerceRegion(
			commerceCountry.getCommerceCountryId(), "ID");

		commerceWarehouses.add(
			_commerceWarehouseLocalService.addCommerceWarehouse(
				"Twin Falls, Idaho", StringPool.BLANK, true, "660 Park Ave", "",
				"", "Twin Falls", "83301", commerceRegion.getCommerceRegionId(),
				commerceCountry.getCommerceCountryId(), 42.5408580,
				-114.4663890, serviceContext));

		return commerceWarehouses;
	}

	protected void configureB2BSite(long groupId, ServiceContext serviceContext)
		throws Exception {

		updateOrganizationTypes();

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

			_createCommerceRole(name);
		}
	}

	protected CPDefinition createCPDefinition(
			String title, String description, String sku,
			long[] assetCategoryIds, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		displayCalendar.add(Calendar.YEAR, -1);

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
		int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH);
		int expirationDateDay = expirationCalendar.get(Calendar.DAY_OF_MONTH);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);
		int expirationDateAmPm = expirationCalendar.get(Calendar.AM_PM);

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		Map<Locale, String> titleMap = Collections.singletonMap(
			serviceContext.getLocale(), title);
		Map<Locale, String> descriptionMap = Collections.singletonMap(
			serviceContext.getLocale(), description);

		return _cpDefinitionLocalService.addCPDefinition(
			titleMap, null, descriptionMap, titleMap, null, null, null,
			"simple", true, true, false, false, 0, 10, 10, 10, 10, 0, false,
			false, null, true, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, true, sku,
			StringPool.BLANK, serviceContext);
	}

	protected void createLayouts(ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		String layoutsJSON = StringUtil.read(
			clazz.getClassLoader(), _DEPENDENCY_PATH + "layouts.json", false);

		JSONArray jsonArray = _jsonFactory.createJSONArray(layoutsJSON);

		_cpFileImporter.createLayouts(
			jsonArray, false, clazz.getClassLoader(), _DEPENDENCY_PATH,
			serviceContext);
	}

	protected void createRoles(ServiceContext serviceContext) throws Exception {
		Class<?> clazz = getClass();

		String rolesJSON = StringUtil.read(
			clazz.getClassLoader(), _DEPENDENCY_PATH + "roles.json", true);

		JSONArray jsonArray = _jsonFactory.createJSONArray(rolesJSON);

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

		String themePortletSettingsJSON = StringUtil.read(
			clazz.getClassLoader(),
			_DEPENDENCY_PATH + "theme-portlet-settings.json", true);

		return _jsonFactory.createJSONArray(themePortletSettingsJSON);
	}

	protected void importProducts(ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		String productsJSON = StringUtil.read(
			clazz.getClassLoader(), _DEPENDENCY_PATH + "products.json", false);

		JSONArray jsonArray = _jsonFactory.createJSONArray(productsJSON);

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				serviceContext.getScopeGroupId(), _COMMERCE_VOCABULARY);

		if (assetVocabulary == null) {
			assetVocabulary = _assetVocabularyLocalService.addVocabulary(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_COMMERCE_VOCABULARY, serviceContext);
		}

		List<CommerceWarehouse> commerceWarehouses = addCommerceWarehouses(
			serviceContext);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			BigDecimal cost = BigDecimal.valueOf(
				jsonObject.getDouble("Cost", 0));
			String description = jsonObject.getString("Description");
			String image = jsonObject.getString("Image");
			String name = jsonObject.getString("Name");
			BigDecimal price = BigDecimal.valueOf(
				jsonObject.getDouble("Price", 0));
			String sku = jsonObject.getString("Sku");

			int[] warehouseQuantities = {
				jsonObject.getInt("Warehouse1"),
				jsonObject.getInt("Warehouse2"), jsonObject.getInt("Warehouse3")
			};

			JSONArray categories = jsonObject.getJSONArray("Categories");

			//Asset categories

			long[] assetCategoryIds = addAssetCategories(
				assetVocabulary.getVocabularyId(), categories, serviceContext);

			// Commerce product definition

			CPDefinition cpDefinition = createCPDefinition(
				name, description, sku, assetCategoryIds, serviceContext);

			// Commerce product instance

			CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
				cpDefinition.getCPDefinitionId(), sku);

			cpInstance.setPrice(price);
			cpInstance.setCost(cost);

			_cpInstanceLocalService.updateCPInstance(cpInstance);

			// Commerce warehouse item

			for (int j = 0; j < commerceWarehouses.size(); j++) {
				CommerceWarehouse commerceWarehouse = commerceWarehouses.get(j);

				_commerceWarehouseItemLocalService.addCommerceWarehouseItem(
					commerceWarehouse.getCommerceWarehouseId(),
					cpInstance.getCPInstanceId(), warehouseQuantities[j],
					serviceContext);
			}

			// Commerce product definition inventory

			_cpDefinitionInventoryLocalService.addCPDefinitionInventory(
				cpDefinition.getCPDefinitionId(), null, null, false, false, 0,
				true, CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY,
				CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY, null,
				CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY,
				serviceContext);

			// Commerce product attachment file entry

			if (Validator.isNotNull(image)) {
				addCPDefinitionAttachmentFileEntry(
					cpDefinition.getCPDefinitionId(), image, serviceContext);
			}
		}
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

			DDMTemplate ddmTemplate = getDDMTemplate(
				_SIMPLE_CP_TYPE_CLASS_NAME,
				_DEPENDENCY_PATH + "product_display_template.ftl",
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

			DDMTemplate ddmTemplate = getDDMTemplate(
				_CP_SEARCH_RESULT_PORTLET_CLASS_NAME,
				_DEPENDENCY_PATH + "catalog_display_template.ftl",
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

	protected void updateOrganizationTypes() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			_getConfigurationFilter(_ORGANIZATION_TYPE_CONFIGURATION_PID));

		for (String organizationType : _ORGANIZATION_TYPES) {
			_updateOrganizationType(configurations, organizationType);
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

	private void _createCommerceRole(String name) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			_getConfigurationFilter(_COMMERCE_ROLE_CONFIGURATION_PID));

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				String roleName = (String)properties.get("roleName");

				if (name.equals(roleName)) {
					return;
				}
			}
		}

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				_COMMERCE_ROLE_CONFIGURATION_PID, StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new Hashtable<>();
		}

		properties.put("roleName", name);

		configuration.update(properties);
	}

	private void _createOrganizationType(String organizationType)
		throws Exception {

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				_ORGANIZATION_TYPE_CONFIGURATION_PID, StringPool.QUESTION);

		configuration.update(
			_getOrganizationTypeProperties(configuration, organizationType));
	}

	private String _getConfigurationFilter(String configurationPid) {
		StringBundler sb = new StringBundler(5);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		sb.append(StringPool.EQUAL);
		sb.append(configurationPid);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private File _getFile(String location) throws IOException {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(location);

		return FileUtil.createTempFile(inputStream);
	}

	private String[] _getOrganizationTypeChildrenTypes(
		String organizationType) {

		if (organizationType.equals(OrganizationConstants.TYPE_ORGANIZATION)) {
			return new String[] {
				OrganizationConstants.TYPE_ORGANIZATION,
				CommerceOrganizationConstants.TYPE_ACCOUNT
			};
		}
		else if (organizationType.equals(
					CommerceOrganizationConstants.TYPE_ACCOUNT)) {

			return new String[] {
				OrganizationConstants.TYPE_ORGANIZATION,
				CommerceOrganizationConstants.TYPE_BRANCH
			};
		}

		return new String[0];
	}

	private Dictionary<String, Object> _getOrganizationTypeProperties(
		Configuration configuration, String organizationType) {

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new Hashtable<>();
		}

		boolean rootable = false;

		if (organizationType.equals(OrganizationConstants.TYPE_ORGANIZATION)) {
			rootable = true;
		}

		properties.put(
			"childrenTypes",
			_getOrganizationTypeChildrenTypes(organizationType));
		properties.put("countryEnabled", false);
		properties.put("countryRequired", false);
		properties.put("name", organizationType);
		properties.put("rootable", rootable);

		return properties;
	}

	private Map<Locale, String> _getUniqueUrlTitles(
		AssetCategory assetCategory) {

		Map<Locale, String> urlTitleMap = new HashMap<>();

		Map<Locale, String> titleMap = assetCategory.getTitleMap();

		long classNameId = _portal.getClassNameId(AssetCategory.class);

		for (Map.Entry<Locale, String> titleEntry : titleMap.entrySet()) {
			String languageId = LocaleUtil.toLanguageId(titleEntry.getKey());

			String urlTitle = _cpFriendlyURLEntryLocalService.buildUrlTitle(
				assetCategory.getGroupId(), classNameId,
				assetCategory.getCategoryId(), languageId,
				titleEntry.getValue());

			urlTitleMap.put(titleEntry.getKey(), urlTitle);
		}

		return urlTitleMap;
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

	private void _updateOrganizationType(
			Configuration[] configurations, String organizationType)
		throws Exception {

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				String name = (String)properties.get("name");

				if (organizationType.equals(name)) {
					configuration.update(
						_getOrganizationTypeProperties(
							configuration, organizationType));

					return;
				}
			}
		}

		_createOrganizationType(organizationType);
	}

	private static final String _COMMERCE_ROLE_CONFIGURATION_PID =
		"com.liferay.commerce.user.web.internal.configuration." +
			"CommerceRoleGroupServiceConfiguration";

	private static final String _COMMERCE_VOCABULARY = "Commerce";

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

	private static final String _ORGANIZATION_TYPE_CONFIGURATION_PID =
		"com.liferay.organizations.service.internal.configuration." +
			"OrganizationTypeConfiguration";

	private static final String[] _ORGANIZATION_TYPES = ArrayUtil.append(
		new String[] {OrganizationConstants.TYPE_ORGANIZATION},
		CommerceOrganizationConstants.TYPES);

	private static final String _SIMPLE_CP_TYPE_CLASS_NAME =
		"com.liferay.commerce.product.type.simple.internal.SimpleCPType";

	private static final Log _log = LogFactoryUtil.getLog(
		CustomerPortalGroupInitializer.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Reference
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@Reference
	private CommerceRegionLocalService _commerceRegionLocalService;

	@Reference
	private CommerceWarehouseItemLocalService
		_commerceWarehouseItemLocalService;

	@Reference
	private CommerceWarehouseLocalService _commerceWarehouseLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private MimeTypes _mimeTypes;

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