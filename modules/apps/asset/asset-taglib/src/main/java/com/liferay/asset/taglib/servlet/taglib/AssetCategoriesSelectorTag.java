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

package com.liferay.asset.taglib.servlet.taglib;

import com.liferay.asset.categories.configuration.AssetCategoriesCompanyConfiguration;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.asset.taglib.internal.util.AssetCategoryUtil;
import com.liferay.asset.taglib.internal.util.AssetVocabularyUtil;
import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.comparator.AssetVocabularyGroupLocalizedTitleComparator;
import com.liferay.taglib.aui.AUIUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Chema Balsas
 * @author Carlos Lancha
 */
public class AssetCategoriesSelectorTag extends IncludeTag {

	public String getCategoryIds() {
		return _categoryIds;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public long getClassTypePK() {
		return _classTypePK;
	}

	public String getHiddenInput() {
		return _hiddenInput;
	}

	public String getId() {
		return _id;
	}

	public int[] getVisibilityTypes() {
		return _visibilityTypes;
	}

	public boolean isIgnoreRequestValue() {
		return _ignoreRequestValue;
	}

	public boolean isShowOnlyRequiredVocabularies() {
		return _showOnlyRequiredVocabularies;
	}

	public boolean isShowRequiredLabel() {
		return _showRequiredLabel;
	}

	public boolean isSingleSelect() {
		return _singleSelect;
	}

	public void setCategoryIds(String categoryIds) {
		_categoryIds = categoryIds;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setClassTypePK(long classTypePK) {
		_classTypePK = classTypePK;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setShowOnlyRequiredVocabularies(
		boolean showOnlyRequiredVocabularies) {

		_showOnlyRequiredVocabularies = showOnlyRequiredVocabularies;
	}

	public void setShowRequiredLabel(boolean showRequiredLabel) {
		_showRequiredLabel = showRequiredLabel;
	}

	public void setSingleSelect(boolean singleSelect) {
		_singleSelect = singleSelect;
	}

	public void setVisibilityTypes(int[] visibilityTypes) {
		_visibilityTypes = visibilityTypes;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_categoryIds = null;
		_className = null;
		_classPK = 0;
		_classTypePK = AssetCategoryConstants.ALL_CLASS_TYPE_PK;
		_groupIds = null;
		_hiddenInput = "assetCategoryIds";
		_id = null;
		_ignoreRequestValue = false;
		_namespace = null;
		_showOnlyRequiredVocabularies = false;
		_showRequiredLabel = true;
		_singleSelect = false;
		_visibilityTypes = _VISIBILITY_TYPES;
	}

	protected List<String[]> getCategoryIdsTitles() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<String[]> categoryIdsTitles = new ArrayList<>();

		String categoryIds = StringPool.BLANK;

		if (Validator.isNotNull(_categoryIds)) {
			categoryIds = _categoryIds;
		}

		if (Validator.isNull(_className)) {
			if (!_ignoreRequestValue) {
				String categoryIdsParam = request.getParameter(_hiddenInput);

				if (categoryIdsParam != null) {
					categoryIds = categoryIdsParam;
				}
			}

			String[] categoryIdsTitle = AssetCategoryUtil.getCategoryIdsTitles(
				categoryIds, StringPool.BLANK, 0, themeDisplay);

			categoryIdsTitles.add(categoryIdsTitle);

			return categoryIdsTitles;
		}

		try {
			for (AssetVocabulary vocabulary : _getVocabularies()) {
				String categoryNames = StringPool.BLANK;

				if (Validator.isNotNull(_className) && (_classPK > 0)) {
					List<AssetCategory> categories =
						AssetCategoryServiceUtil.getCategories(
							_className, _classPK);

					categoryIds = ListUtil.toString(
						categories, AssetCategory.CATEGORY_ID_ACCESSOR);
					categoryNames = ListUtil.toString(
						categories, AssetCategory.NAME_ACCESSOR);
				}

				if (!_ignoreRequestValue) {
					String[] categoryIdsParam = request.getParameterValues(
						_hiddenInput + StringPool.UNDERLINE +
							vocabulary.getVocabularyId());

					if (categoryIdsParam != null) {
						categoryIds = StringUtil.merge(
							categoryIdsParam, StringPool.COMMA);
					}
				}

				String[] categoryIdsTitle =
					AssetCategoryUtil.getCategoryIdsTitles(
						categoryIds, categoryNames,
						vocabulary.getVocabularyId(), themeDisplay);

				categoryIdsTitles.add(categoryIdsTitle);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return categoryIdsTitles;
	}

	protected String getEventName() {
		String portletId = PortletProviderUtil.getPortletId(
			AssetCategory.class.getName(), PortletProvider.Action.BROWSE);

		return PortalUtil.getPortletNamespace(portletId) + "selectCategory";
	}

	protected long[] getGroupIds() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (ArrayUtil.isEmpty(_groupIds)) {
				return SiteConnectedGroupGroupProviderUtil.
					getCurrentAndAncestorSiteAndDepotGroupIds(
						themeDisplay.getScopeGroupId());
			}

			return SiteConnectedGroupGroupProviderUtil.
				getCurrentAndAncestorSiteAndDepotGroupIds(_groupIds);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return new long[0];
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected PortletURL getPortletURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				request, AssetCategory.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter("eventName", getEventName());
			portletURL.setParameter(
				"selectedCategories", "{selectedCategories}");
			portletURL.setParameter("singleSelect", "{singleSelect}");
			portletURL.setParameter("vocabularyIds", "{vocabularyIds}");

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	protected List<Map<String, Object>> getVocabularies() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<Map<String, Object>> vocabulariesList = new ArrayList<>();

		List<AssetVocabulary> vocabularies = _getVocabularies();

		List<String[]> categoryIdsTitles = getCategoryIdsTitles();

		for (int i = 0; i < vocabularies.size(); i++) {
			AssetVocabulary vocabulary = vocabularies.get(i);

			if (!ArrayUtil.contains(
					getVisibilityTypes(), vocabulary.getVisibilityType())) {

				continue;
			}

			int index = i;

			if (Validator.isNull(_className)) {
				index = 0;
			}

			String selectedCategoryIds = categoryIdsTitles.get(index)[0];

			Map<String, Object> vocabularyMap =
				HashMapBuilder.<String, Object>put(
					"id", vocabulary.getVocabularyId()
				).put(
					"required",
					vocabulary.isRequired(
						PortalUtil.getClassNameId(_className), _classTypePK) &&
					_showRequiredLabel
				).put(
					"selectedCategories", selectedCategoryIds
				).put(
					"title",
					vocabulary.getUnambiguousTitle(
						vocabularies, themeDisplay.getScopeGroupId(),
						themeDisplay.getLocale())
				).put(
					"visibilityType", vocabulary.getVisibilityType()
				).build();

			if (Validator.isNotNull(selectedCategoryIds)) {
				List<Map<String, Object>> selectedItems = new ArrayList<>();

				String[] categoryIds = selectedCategoryIds.split(",");

				String selectedCategoryIdTitles =
					categoryIdsTitles.get(index)[1];

				String[] categoryTitles = selectedCategoryIdTitles.split(
					AssetCategoryUtil.CATEGORY_SEPARATOR);

				for (int j = 0; j < categoryIds.length; j++) {
					selectedItems.add(
						HashMapBuilder.<String, Object>put(
							"label", categoryTitles[j]
						).put(
							"value", categoryIds[j]
						).build());
				}

				vocabularyMap.put("selectedItems", selectedItems);
			}

			vocabularyMap.put("singleSelect", !vocabulary.isMultiValued());

			vocabulariesList.add(vocabularyMap);
		}

		return vocabulariesList;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		try {
			httpServletRequest.setAttribute(
				"liferay-asset:asset-categories-selector:data",
				HashMapBuilder.<String, Object>put(
					"eventName", getEventName()
				).put(
					"groupIds", ListUtil.fromArray(getGroupIds())
				).put(
					"id", _getNamespace() + _getId() + "assetCategoriesSelector"
				).put(
					"inputName", _getInputName()
				).put(
					"learnHowURL",
					() -> {
						ThemeDisplay themeDisplay =
							(ThemeDisplay)httpServletRequest.getAttribute(
								WebKeys.THEME_DISPLAY);

						AssetCategoriesCompanyConfiguration
							assetCategoriesCompanyConfiguration =
								ConfigurationProviderUtil.
									getCompanyConfiguration(
										AssetCategoriesCompanyConfiguration.
											class,
										themeDisplay.getCompanyId());

						return assetCategoriesCompanyConfiguration.
							linkToDocumentationURL();
					}
				).put(
					"portletURL", getPortletURL().toString()
				).put(
					"vocabularies", getVocabularies()
				).build());
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private String _getId() {
		if (Validator.isNotNull(_id)) {
			return _id;
		}

		String randomKey = PortalUtil.generateRandomKey(
			request, "taglib_ui_asset_categories_selector_page");

		return randomKey + StringPool.UNDERLINE;
	}

	private String _getInputName() {
		return _getNamespace() + _hiddenInput + StringPool.UNDERLINE;
	}

	private String _getNamespace() {
		if (_namespace != null) {
			return _namespace;
		}

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest == null) || (portletResponse == null)) {
			_namespace = AUIUtil.getNamespace(request);

			return _namespace;
		}

		_namespace = AUIUtil.getNamespace(portletRequest, portletResponse);

		return _namespace;
	}

	private List<AssetVocabulary> _getVocabularies() {
		List<AssetVocabulary> vocabularies = new ArrayList<>();

		vocabularies.addAll(
			AssetVocabularyServiceUtil.getGroupVocabularies(getGroupIds()));

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		vocabularies.sort(
			new AssetVocabularyGroupLocalizedTitleComparator(
				themeDisplay.getScopeGroupId(), themeDisplay.getLocale(),
				true));

		if (Validator.isNotNull(_className)) {
			vocabularies = AssetVocabularyUtil.filterVocabularies(
				vocabularies, _className, _classTypePK);
		}

		return ListUtil.filter(
			vocabularies,
			vocabulary -> {
				if (_showOnlyRequiredVocabularies &&
					!vocabulary.isRequired(
						PortalUtil.getClassNameId(_className), _classTypePK)) {

					return false;
				}

				return true;
			});
	}

	private static final String _PAGE = "/asset_categories_selector/page.jsp";

	private static final int[] _VISIBILITY_TYPES = {
		AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoriesSelectorTag.class);

	private String _categoryIds;
	private String _className;
	private long _classPK;
	private long _classTypePK = AssetCategoryConstants.ALL_CLASS_TYPE_PK;
	private long[] _groupIds;
	private String _hiddenInput = "assetCategoryIds";
	private String _id;
	private boolean _ignoreRequestValue;
	private String _namespace;
	private boolean _showOnlyRequiredVocabularies;
	private boolean _showRequiredLabel = true;
	private boolean _singleSelect;
	private int[] _visibilityTypes = _VISIBILITY_TYPES;

}