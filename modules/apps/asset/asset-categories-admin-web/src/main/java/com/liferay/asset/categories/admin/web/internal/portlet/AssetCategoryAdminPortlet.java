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

package com.liferay.asset.categories.admin.web.internal.portlet;

import com.liferay.asset.categories.admin.web.internal.configuration.AssetCategoriesAdminWebConfiguration;
import com.liferay.asset.categories.admin.web.internal.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.categories.admin.web.internal.constants.AssetCategoriesAdminWebKeys;
import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.NoSuchClassTypeException;
import com.liferay.asset.kernel.exception.AssetCategoryNameException;
import com.liferay.asset.kernel.exception.CategoryPropertyKeyException;
import com.liferay.asset.kernel.exception.CategoryPropertyValueException;
import com.liferay.asset.kernel.exception.DuplicateCategoryException;
import com.liferay.asset.kernel.exception.DuplicateCategoryPropertyException;
import com.liferay.asset.kernel.exception.DuplicateVocabularyException;
import com.liferay.asset.kernel.exception.InvalidAssetCategoryException;
import com.liferay.asset.kernel.exception.NoSuchCategoryException;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.exception.NoSuchVocabularyException;
import com.liferay.asset.kernel.exception.VocabularyNameException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.asset.categories.admin.web.internal.configuration.AssetCategoriesAdminWebConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-asset-category-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/asset_category_admin.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Asset Category Admin",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class AssetCategoryAdminPortlet extends MVCPortlet {

	public void deleteCategory(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteCategoryIds = null;

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		if (categoryId > 0) {
			deleteCategoryIds = new long[] {categoryId};
		}
		else {
			deleteCategoryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		_assetCategoryService.deleteCategories(deleteCategoryIds);
	}

	public void deleteVocabulary(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteVocabularyIds = null;

		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");

		if (vocabularyId > 0) {
			deleteVocabularyIds = new long[] {vocabularyId};
		}
		else {
			deleteVocabularyIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		for (long deleteVocabularyId : deleteVocabularyIds) {
			_assetVocabularyService.deleteVocabulary(deleteVocabularyId);
		}
	}

	public void editCategory(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		if (categoryId <= 0) {

			// Add category

			_assetCategoryService.addCategory(
				serviceContext.getScopeGroupId(), parentCategoryId, titleMap,
				descriptionMap, vocabularyId, null, serviceContext);
		}
		else {

			// Update category

			List<AssetCategoryProperty> categoryProperties =
				_assetCategoryPropertyLocalService.getCategoryProperties(
					categoryId);

			String[] categoryPropertiesArray = getCategoryProperties(
				categoryProperties);

			_assetCategoryService.updateCategory(
				categoryId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, categoryPropertiesArray, serviceContext);
		}
	}

	public void editProperties(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId", 0);

		AssetCategory category = _assetCategoryService.fetchCategory(
			categoryId);

		String[] categoryProperties = getCategoryProperties(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		_assetCategoryService.updateCategory(
			categoryId, category.getParentCategoryId(), category.getTitleMap(),
			category.getDescriptionMap(), category.getVocabularyId(),
			categoryProperties, serviceContext);
	}

	public void editVocabulary(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetVocabulary.class.getName(), actionRequest);

		if (vocabularyId <= 0) {

			// Add vocabulary

			_assetVocabularyService.addVocabulary(
				serviceContext.getScopeGroupId(), StringPool.BLANK, titleMap,
				descriptionMap, getSettings(actionRequest), serviceContext);
		}
		else {

			// Update vocabulary

			_assetVocabularyService.updateVocabulary(
				vocabularyId, StringPool.BLANK, titleMap, descriptionMap,
				getSettings(actionRequest), serviceContext);
		}
	}

	public void moveCategory(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");

		if ((vocabularyId <= 0) && (parentCategoryId <= 0)) {
			throw new NoSuchVocabularyException();
		}

		if (vocabularyId <= 0) {
			AssetCategory parentCategory = _assetCategoryService.fetchCategory(
				parentCategoryId);

			vocabularyId = parentCategory.getVocabularyId();
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		_assetCategoryService.moveCategory(
			categoryId, parentCategoryId, vocabularyId, serviceContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_assetCategoriesAdminWebConfiguration =
			ConfigurableUtil.createConfigurable(
				AssetCategoriesAdminWebConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchCategoryException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchVocabularyException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);

			return;
		}

		renderRequest.setAttribute(
			AssetCategoriesAdminWebKeys.ASSET_CATEGORIES_ADMIN_CONFIGURATION,
			_assetCategoriesAdminWebConfiguration);

		super.doDispatch(renderRequest, renderResponse);
	}

	protected String[] getCategoryProperties(ActionRequest actionRequest) {
		int[] categoryPropertiesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "categoryPropertiesIndexes"), 0);

		String[] categoryProperties =
			new String[categoryPropertiesIndexes.length];

		for (int i = 0; i < categoryPropertiesIndexes.length; i++) {
			int categoryPropertiesIndex = categoryPropertiesIndexes[i];

			String key = ParamUtil.getString(
				actionRequest, "key" + categoryPropertiesIndex);

			if (Validator.isNull(key)) {
				continue;
			}

			String value = ParamUtil.getString(
				actionRequest, "value" + categoryPropertiesIndex);

			categoryProperties[i] =
				key + AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR +
					value;
		}

		return categoryProperties;
	}

	protected String[] getCategoryProperties(
		List<AssetCategoryProperty> categoryProperties) {

		String[] categoryPropertiesArray =
			new String[categoryProperties.size()];

		for (int i = 0; i < categoryProperties.size(); i++) {
			AssetCategoryProperty categoryProperty = categoryProperties.get(i);

			categoryPropertiesArray[i] =
				categoryProperty.getKey() +
					AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR +
						categoryProperty.getValue();
		}

		return categoryPropertiesArray;
	}

	protected String getSettings(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		int[] indexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "indexes"), 0);

		long[] classNameIds = new long[indexes.length];
		long[] classTypePKs = new long[indexes.length];
		boolean[] requireds = new boolean[indexes.length];

		for (int i = 0; i < indexes.length; i++) {
			int index = indexes[i];

			classNameIds[i] = ParamUtil.getLong(
				actionRequest, "classNameId" + index);

			classTypePKs[i] = ParamUtil.getLong(
				actionRequest,
				StringBundler.concat(
					"subtype", classNameIds[i], "-classNameId", index),
				AssetCategoryConstants.ALL_CLASS_TYPE_PK);

			if (classTypePKs[i] != -1) {
				AssetRendererFactory<?> assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassNameId(classNameIds[i]);

				ClassTypeReader classTypeReader =
					assetRendererFactory.getClassTypeReader();

				try {
					classTypeReader.getClassType(
						classTypePKs[i], themeDisplay.getLocale());
				}
				catch (NoSuchModelException nsme) {
					throw new NoSuchClassTypeException(nsme);
				}
			}

			requireds[i] = ParamUtil.getBoolean(
				actionRequest, "required" + index);
		}

		vocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			classNameIds, classTypePKs, requireds);

		boolean multiValued = ParamUtil.getBoolean(
			actionRequest, "multiValued");

		vocabularySettingsHelper.setMultiValued(multiValued);

		return vocabularySettingsHelper.toString();
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AssetCategoryNameException ||
			cause instanceof CategoryPropertyKeyException ||
			cause instanceof CategoryPropertyValueException ||
			cause instanceof DuplicateCategoryException ||
			cause instanceof DuplicateCategoryPropertyException ||
			cause instanceof DuplicateVocabularyException ||
			cause instanceof InvalidAssetCategoryException ||
			cause instanceof NoSuchCategoryException ||
			cause instanceof NoSuchClassTypeException ||
			cause instanceof NoSuchEntryException ||
			cause instanceof NoSuchVocabularyException ||
			cause instanceof PrincipalException ||
			cause instanceof VocabularyNameException) {

			return true;
		}

		return false;
	}

	private AssetCategoriesAdminWebConfiguration
		_assetCategoriesAdminWebConfiguration;

	@Reference
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

}