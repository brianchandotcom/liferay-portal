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

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.categories.admin.web.internal.configuration.AssetCategoriesAdminWebConfiguration;
import com.liferay.asset.categories.admin.web.internal.constants.AssetCategoriesAdminWebKeys;
import com.liferay.asset.category.property.exception.CategoryPropertyKeyException;
import com.liferay.asset.category.property.exception.CategoryPropertyValueException;
import com.liferay.asset.category.property.exception.DuplicateCategoryPropertyException;
import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.display.page.portlet.AssetDisplayPageEntryFormProcessor;
import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.NoSuchClassTypeException;
import com.liferay.asset.kernel.exception.AssetCategoryLimitException;
import com.liferay.asset.kernel.exception.AssetCategoryNameException;
import com.liferay.asset.kernel.exception.DuplicateCategoryException;
import com.liferay.asset.kernel.exception.DuplicateVocabularyException;
import com.liferay.asset.kernel.exception.InvalidAssetCategoryException;
import com.liferay.asset.kernel.exception.NoSuchCategoryException;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.exception.NoSuchVocabularyException;
import com.liferay.asset.kernel.exception.VocabularyNameException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Date;
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

		hideDefaultSuccessMessage(actionRequest);

		MultiSessionMessages.add(
			actionRequest, actionResponse.getNamespace() + "requestProcessed");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetCategory category = null;

		if (categoryId <= 0) {

			// Add category

			long groupId = ParamUtil.getLong(actionRequest, "groupId");

			category = _assetCategoryService.addCategory(
				groupId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, null, serviceContext);

			MultiSessionMessages.add(
				actionRequest, "categoryAdded",
				LanguageUtil.format(
					_portal.getHttpServletRequest(actionRequest),
					"x-was-created-successfully",
					new Object[] {
						HtmlUtil.escape(titleMap.get(themeDisplay.getLocale()))
					}));
		}
		else {

			// Update category

			String[] categoryPropertiesArray = _getCategoryProperties(
				_assetCategoryPropertyLocalService.getCategoryProperties(
					categoryId));

			category = _assetCategoryService.updateCategory(
				categoryId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, categoryPropertiesArray, serviceContext);

			MultiSessionMessages.add(
				actionRequest, "categoryUpdated",
				LanguageUtil.format(
					_portal.getHttpServletRequest(actionRequest),
					"x-was-updated-successfully",
					new Object[] {
						HtmlUtil.escape(titleMap.get(themeDisplay.getLocale()))
					}));
		}

		// Asset display page

		_assetDisplayPageEntryFormProcessor.process(
			AssetCategory.class.getName(), category.getCategoryId(),
			actionRequest);

		sendRedirect(actionRequest, actionResponse);
	}

	public void moveCategory(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");

		if ((vocabularyId <= 0) && (parentCategoryId <= 0)) {
			throw new NoSuchVocabularyException();
		}

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

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

	public void setCategoryDisplayPageTemplate(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] categoryIds = ParamUtil.getLongValues(
			actionRequest, "categoryIds");

		for (long categoryId : categoryIds) {
			AssetCategory category = _assetCategoryLocalService.getCategory(
				categoryId);

			_assetDisplayPageEntryFormProcessor.process(
				AssetCategory.class.getName(), category.getCategoryId(),
				actionRequest);

			category.setModifiedDate(new Date());

			_assetCategoryLocalService.updateAssetCategory(category);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
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
		renderRequest.setAttribute(
			AssetCategoriesAdminWebKeys.
				ASSET_DISPLAY_PAGE_FRIENDLY_URL_PROVIDER,
			_assetDisplayPageFriendlyURLProvider);

		super.doDispatch(renderRequest, renderResponse);
	}

	@Override
	protected boolean isSessionErrorException(Throwable throwable) {
		if (throwable instanceof AssetCategoryLimitException ||
			throwable instanceof AssetCategoryNameException ||
			throwable instanceof CategoryPropertyKeyException ||
			throwable instanceof CategoryPropertyValueException ||
			throwable instanceof DuplicateCategoryException ||
			throwable instanceof DuplicateCategoryPropertyException ||
			throwable instanceof DuplicateVocabularyException ||
			throwable instanceof InvalidAssetCategoryException ||
			throwable instanceof NoSuchCategoryException ||
			throwable instanceof NoSuchClassTypeException ||
			throwable instanceof NoSuchEntryException ||
			throwable instanceof NoSuchVocabularyException ||
			throwable instanceof PrincipalException ||
			throwable instanceof VocabularyNameException) {

			return true;
		}

		return false;
	}

	private String[] _getCategoryProperties(
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

	private volatile AssetCategoriesAdminWebConfiguration
		_assetCategoriesAdminWebConfiguration;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetDisplayPageEntryFormProcessor
		_assetDisplayPageEntryFormProcessor;

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private Portal _portal;

}