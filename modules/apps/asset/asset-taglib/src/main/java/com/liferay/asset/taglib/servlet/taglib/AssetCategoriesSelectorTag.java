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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.asset.taglib.internal.util.AssetCategoryUtil;
import com.liferay.asset.taglib.internal.util.AssetVocabularyUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.aui.AUIUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.HashMap;
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
					String categoryIdsParam = request.getParameter(
						_hiddenInput + StringPool.UNDERLINE +
							vocabulary.getVocabularyId());

					if (Validator.isNotNull(categoryIdsParam)) {
						categoryIds = categoryIdsParam;
					}
				}

				String[] categoryIdsTitle =
					AssetCategoryUtil.getCategoryIdsTitles(
						categoryIds, categoryNames,
						vocabulary.getVocabularyId(), themeDisplay);

				categoryIdsTitles.add(categoryIdsTitle);
			}
		}
		catch (Exception e) {
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
				return PortalUtil.getCurrentAndAncestorSiteGroupIds(
					themeDisplay.getScopeGroupId());
			}

			return PortalUtil.getCurrentAndAncestorSiteGroupIds(_groupIds);
		}
		catch (Exception e) {
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
		catch (Exception e) {
		}

		return null;
	}

	protected List<Map<String, Object>> getVocabularies() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<Map<String, Object>> vocabularies = new ArrayList<>();

		List<AssetVocabulary> assetVocabularies = _getVocabularies();

		List<String[]> categoryIdsTitles = getCategoryIdsTitles();

		for (int i = 0; i < assetVocabularies.size(); i++) {
			Map<String, Object> vocabulary = new HashMap<>();

			AssetVocabulary assetVocabulary = assetVocabularies.get(i);

			vocabulary.put("id", assetVocabulary.getVocabularyId());
			vocabulary.put(
				"title", assetVocabulary.getTitle(themeDisplay.getLocale()));

			String vocabularyGroupName = StringPool.BLANK;

			if (assetVocabulary.getGroupId() != themeDisplay.getSiteGroupId()) {
				Group vocabularyGroup = GroupLocalServiceUtil.getGroup(
					assetVocabulary.getGroupId());

				vocabularyGroupName = vocabularyGroup.getDescriptiveName(
					themeDisplay.getLocale());
			}

			vocabulary.put("group", vocabularyGroupName);

			vocabulary.put(
				"required",
				assetVocabulary.isRequired(
					PortalUtil.getClassNameId(_className), _classTypePK) &&
				_showRequiredLabel);

			String selectedCategoryIds = categoryIdsTitles.get(i)[0];

			vocabulary.put("selectedCategoryIds", selectedCategoryIds);

			if (Validator.isNotNull(selectedCategoryIds)) {
				List<Map<String, Object>> selectedItems = new ArrayList<>();

				String[] categoryIds = selectedCategoryIds.split(",");

				String selectedCategoryIdTitles = categoryIdsTitles.get(i)[1];

				String[] categoryTitles = selectedCategoryIdTitles.split(
					"_CATEGORY_");

				for (int j = 0; j < categoryIds.length; j++) {
					Map<String, Object> category = new HashMap<>();

					category.put("label", categoryTitles[j]);
					category.put("value", categoryIds[j]);

					selectedItems.add(category);
				}

				vocabulary.put("selectedItems", selectedItems);
			}

			vocabulary.put("singleSelect", !assetVocabulary.isMultiValued());

			vocabularies.add(vocabulary);
		}

		return vocabularies;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		try {
			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"eventName", getEventName()
			).put(
				"groupIds", ListUtil.fromArray(getGroupIds())
			).put(
				"id", _getNamespace() + _getId() + "assetCategoriesSelector"
			).put(
				"inputName", _getInputName()
			).put(
				"portletURL", getPortletURL().toString()
			).put(
				"vocabularies", getVocabularies()
			).build();

			httpServletRequest.setAttribute(
				"liferay-asset:asset-categories-selector:data", data);
		}
		catch (Exception e) {
			_log.error(e, e);
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
		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(getGroupIds());

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

				int vocabularyCategoriesCount =
					AssetCategoryServiceUtil.getVocabularyCategoriesCount(
						vocabulary.getGroupId(), vocabulary.getVocabularyId());

				if (vocabularyCategoriesCount > 0) {
					return true;
				}

				return false;
			});
	}

	private static final String _PAGE = "/asset_categories_selector/page.jsp";

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

}