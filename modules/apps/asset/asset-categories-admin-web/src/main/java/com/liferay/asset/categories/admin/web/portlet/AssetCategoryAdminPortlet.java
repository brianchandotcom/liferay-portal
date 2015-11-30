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

package com.liferay.asset.categories.admin.web.portlet;

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portlet.asset.AssetCategoryNameException;
import com.liferay.portlet.asset.DuplicateCategoryException;
import com.liferay.portlet.asset.DuplicateCategoryPropertyException;
import com.liferay.portlet.asset.DuplicateVocabularyException;
import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.NoSuchVocabularyException;
import com.liferay.portlet.asset.VocabularyNameException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryService;
import com.liferay.portlet.asset.service.AssetVocabularyService;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-asset-category-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/asset_category_admin.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Asset Category Admin",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
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
		String[] categoryProperties = getCategoryProperties(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		if (categoryId <= 0) {

			// Add category

			_assetCategoryService.addCategory(
				serviceContext.getScopeGroupId(), parentCategoryId, titleMap,
				descriptionMap, vocabularyId, categoryProperties,
				serviceContext);
		}
		else {

			// Update category

			_assetCategoryService.updateCategory(
				categoryId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, categoryProperties, serviceContext);
		}
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

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		_assetCategoryService.moveCategory(
			categoryId, parentCategoryId, vocabularyId, serviceContext);
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
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
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

	protected String getSettings(ActionRequest actionRequest) {
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
				"subtype" + classNameIds[i] + "-classNameId" + index,
				AssetCategoryConstants.ALL_CLASS_TYPE_PK);

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
			cause instanceof DuplicateCategoryException ||
			cause instanceof DuplicateCategoryPropertyException ||
			cause instanceof DuplicateVocabularyException ||
			cause instanceof NoSuchCategoryException ||
			cause instanceof NoSuchVocabularyException ||
			cause instanceof PrincipalException ||
			cause instanceof VocabularyNameException) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryService(
		AssetCategoryService assetCategoryService) {

		_assetCategoryService = assetCategoryService;
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyService(
		AssetVocabularyService assetVocabularyService) {

		_assetVocabularyService = assetVocabularyService;
	}

	private volatile AssetCategoryService _assetCategoryService;
	private volatile AssetVocabularyService _assetVocabularyService;

}