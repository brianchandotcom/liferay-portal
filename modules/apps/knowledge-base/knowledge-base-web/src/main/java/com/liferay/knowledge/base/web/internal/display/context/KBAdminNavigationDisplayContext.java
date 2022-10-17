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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.knowledge.base.service.KBTemplateServiceUtil;
import com.liferay.knowledge.base.util.comparator.KBArticleTitleComparator;
import com.liferay.knowledge.base.util.comparator.KBObjectsPriorityComparator;
import com.liferay.knowledge.base.util.comparator.KBTemplateTitleComparator;
import com.liferay.knowledge.base.web.internal.display.context.helper.KBArticleURLHelper;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.knowledge.base.web.internal.util.KBDropdownItemsProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.LiferayPortletUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class KBAdminNavigationDisplayContext {

	public KBAdminNavigationDisplayContext(
			HttpServletRequest httpServletRequest, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		_httpServletRequest = httpServletRequest;

		_selectedItemAncestorIds = _getSelectedItemAncestorIds();

		_kbArticleURLHelper = new KBArticleURLHelper(
			renderRequest, renderResponse);
		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST));
		_liferayPortletResponse = LiferayPortletUtil.getLiferayPortletResponse(
			renderResponse);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_kbDropdownItemsProvider = new KBDropdownItemsProvider(
			_liferayPortletRequest, _liferayPortletResponse);
	}

	public List<NavigationItem> getInfoPanelNavigationItems() {
		return ListUtil.fromArray(
			NavigationItemBuilder.setActive(
				true
			).setHref(
				_themeDisplay.getURLCurrent()
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "details")
			).build());
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String mvcPath = ParamUtil.getString(_httpServletRequest, "mvcPath");

		return NavigationItemListBuilder.add(
			() -> PortletPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _themeDisplay.getPlid(),
				portletDisplay.getId(), KBActionKeys.VIEW),
			NavigationItemBuilder.setActive(
				() -> {
					if (!mvcPath.equals("/admin/view_kb_suggestions.jsp") &&
						!mvcPath.equals("/admin/view_kb_templates.jsp")) {

						return true;
					}

					return false;
				}
			).setHref(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCRenderCommandName(
					"/knowledge_base/view"
				).buildString()
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "articles")
			).build()
		).add(
			() -> AdminPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				KBActionKeys.VIEW_KB_TEMPLATES),
			NavigationItemBuilder.setActive(
				() -> {
					if (mvcPath.equals("/admin/view_kb_templates.jsp")) {
						return true;
					}

					return false;
				}
			).setHref(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/view_kb_templates.jsp"
				).buildString()
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "templates")
			).build()
		).add(
			() -> AdminPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), KBActionKeys.VIEW_SUGGESTIONS),
			NavigationItemBuilder.setActive(
				() -> {
					if (mvcPath.equals("/admin/view_kb_suggestions.jsp")) {
						return true;
					}

					return false;
				}
			).setHref(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/view_kb_suggestions.jsp"
				).buildString()
			).setLabel(
				LanguageUtil.get(_httpServletRequest, "suggestions")
			).build()
		).build();
	}

	public List<JSONObject> getVerticalNavigationJSONObjects()
		throws PortalException {

		List<JSONObject> verticalNavigationItems = new ArrayList<>();

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String mvcPath = ParamUtil.getString(_httpServletRequest, "mvcPath");

		if (PortletPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _themeDisplay.getPlid(),
				portletDisplay.getId(), KBActionKeys.VIEW)) {

			boolean active = false;
			JSONArray navigationItemsJSONArray = null;

			if (!mvcPath.equals("/admin/view_kb_suggestions.jsp") &&
				!mvcPath.equals("/admin/view_kb_template.jsp") &&
				!mvcPath.equals("/admin/view_kb_templates.jsp")) {

				active = true;
				navigationItemsJSONArray = _getChildrenJSONArray();
			}

			verticalNavigationItems.add(
				JSONUtil.put(
					"active", active
				).put(
					"href",
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/view.jsp"
					).buildString()
				).put(
					"icon", "pages-tree"
				).put(
					"key", "article"
				).put(
					"navigationItems", navigationItemsJSONArray
				).put(
					"selectedItemId", _getSelectedItemId()
				).put(
					"title",
					LanguageUtil.get(
						_httpServletRequest, "folders-and-articles")
				));
		}

		if (AdminPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				KBActionKeys.VIEW_KB_TEMPLATES)) {

			boolean active = false;
			JSONArray navigationItemsJSONArray = null;

			if (mvcPath.equals("/admin/view_kb_template.jsp") ||
				mvcPath.equals("/admin/view_kb_templates.jsp")) {

				active = true;
				navigationItemsJSONArray =
					_getKBTemplatesNavigationItemsJSONArray();
			}

			verticalNavigationItems.add(
				JSONUtil.put(
					"active", active
				).put(
					"href",
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/view_kb_templates.jsp"
					).buildString()
				).put(
					"icon", "page-template"
				).put(
					"key", "template"
				).put(
					"navigationItems", navigationItemsJSONArray
				).put(
					"selectedItemId",
					ParamUtil.getLong(
						_httpServletRequest, "selectedItemId",
						KBFolderConstants.DEFAULT_PARENT_FOLDER_ID)
				).put(
					"title", LanguageUtil.get(_httpServletRequest, "templates")
				));
		}

		if (AdminPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				KBActionKeys.VIEW_SUGGESTIONS)) {

			boolean active = false;

			if (mvcPath.equals("/admin/view_kb_suggestions.jsp")) {
				active = true;
			}

			verticalNavigationItems.add(
				JSONUtil.put(
					"active", active
				).put(
					"href",
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/view_kb_suggestions.jsp"
					).buildString()
				).put(
					"icon", "message"
				).put(
					"key", "suggestion"
				).put(
					"title",
					LanguageUtil.get(_httpServletRequest, "suggestions")
				));
		}

		return verticalNavigationItems;
	}

	public boolean isProductMenuOpen() {
		String productMenuState = SessionClicks.get(
			_httpServletRequest,
			"com.liferay.product.navigation.product.menu.web_productMenuState",
			"closed");

		return Objects.equals(productMenuState, "open");
	}

	private JSONArray _getChildKBArticlesJSONArray(KBArticle parentKBArticle)
		throws PortalException {

		JSONArray childrenJSONArray = JSONFactoryUtil.createJSONArray();

		List<KBArticle> kbArticles = KBArticleServiceUtil.getKBArticles(
			parentKBArticle.getGroupId(), parentKBArticle.getResourcePrimKey(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowConstants.STATUS_ANY,
			new KBArticleTitleComparator(true));

		for (KBArticle kbArticle : kbArticles) {
			childrenJSONArray.put(
				JSONUtil.put(
					"actions",
					_kbDropdownItemsProvider.getKBArticleDropdownItems(
						kbArticle, _selectedItemAncestorIds)
				).put(
					"children", _getChildKBArticlesJSONArray(kbArticle)
				).put(
					"href",
					_kbArticleURLHelper.createViewWithRedirectURL(
						kbArticle,
						PortalUtil.getCurrentURL(_httpServletRequest))
				).put(
					"id", kbArticle.getResourcePrimKey()
				).put(
					"name", kbArticle.getTitle()
				).put(
					"type", "article"
				));
		}

		return childrenJSONArray;
	}

	private JSONArray _getChildrenJSONArray() throws PortalException {
		return JSONUtil.put(
			JSONUtil.put(
				"actions",
				_kbDropdownItemsProvider.getKBFolderDropdownItems(null)
			).put(
				"children",
				_getChildrenJSONArray(
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID)
			).put(
				"href",
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/view.jsp"
				).buildString()
			).put(
				"id", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID
			).put(
				"name", _themeDisplay.translate("home")
			).put(
				"type", "folder"
			));
	}

	private JSONArray _getChildrenJSONArray(long parentFolderId)
		throws PortalException {

		JSONArray childrenJSONArray = JSONFactoryUtil.createJSONArray();

		List<Object> kbObjects = KBFolderServiceUtil.getKBFoldersAndKBArticles(
			_themeDisplay.getScopeGroupId(), parentFolderId,
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new KBObjectsPriorityComparator<>(true));

		for (Object kbObject : kbObjects) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (kbObject instanceof KBFolder) {
				KBFolder kbFolder = (KBFolder)kbObject;

				jsonObject.put(
					"actions",
					_kbDropdownItemsProvider.getKBFolderDropdownItems(kbFolder)
				).put(
					"children", _getChildrenJSONArray(kbFolder.getKbFolderId())
				).put(
					"href",
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/view_kb_folders.jsp"
					).setParameter(
						"parentResourceClassNameId", kbFolder.getClassNameId()
					).setParameter(
						"parentResourcePrimKey", kbFolder.getKbFolderId()
					).setParameter(
						"selectedItemId", kbFolder.getKbFolderId()
					).buildString()
				).put(
					"id", kbFolder.getKbFolderId()
				).put(
					"name", kbFolder.getName()
				).put(
					"type", "folder"
				);
			}
			else {
				KBArticle kbArticle = (KBArticle)kbObject;

				jsonObject.put(
					"actions",
					_kbDropdownItemsProvider.getKBArticleDropdownItems(
						kbArticle, _selectedItemAncestorIds)
				).put(
					"children", _getChildKBArticlesJSONArray(kbArticle)
				).put(
					"href",
					_kbArticleURLHelper.createViewWithRedirectURL(
						kbArticle,
						PortalUtil.getCurrentURL(_httpServletRequest))
				).put(
					"id", kbArticle.getResourcePrimKey()
				).put(
					"name", kbArticle.getTitle()
				).put(
					"type", "article"
				);
			}

			childrenJSONArray.put(jsonObject);
		}

		return childrenJSONArray;
	}

	private KBArticle _getKBArticle(long resourcePrimKey)
		throws PortalException {

		if (resourcePrimKey !=
			KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY) {

			return KBArticleServiceUtil.getLatestKBArticle(
				resourcePrimKey, WorkflowConstants.STATUS_ANY);
		}

		return null;
	}

	private long _getKBArticleResourcePrimaryKeyFromRequestParameter() {
		return ParamUtil.getLong(
			_httpServletRequest, "resourcePrimKey",
			KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY);
	}

	private KBFolder _getKBFolder(long kbFolderId) throws PortalException {
		if (kbFolderId != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return KBFolderServiceUtil.getKBFolder(kbFolderId);
		}

		return null;
	}

	private long _getKBFolderIdFromRequestParameter() {
		return ParamUtil.getLong(
			_httpServletRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	private JSONArray _getKBTemplateChildrenJSONArray() {
		JSONArray navigationItemsJSONArray = JSONFactoryUtil.createJSONArray();

		List<KBTemplate> kbTemplates =
			KBTemplateServiceUtil.getGroupKBTemplates(
				_themeDisplay.getScopeGroupId(), QueryUtil.ALL_POS,
				WorkflowConstants.STATUS_ANY,
				new KBTemplateTitleComparator(true));

		for (KBTemplate kbTemplate : kbTemplates) {
			navigationItemsJSONArray.put(
				JSONUtil.put(
					"actions",
					_kbDropdownItemsProvider.getKBTemplateDropdownItems(
						kbTemplate)
				).put(
					"href",
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/common/edit_kb_template.jsp"
					).setRedirect(
						PortalUtil.getCurrentURL(_httpServletRequest)
					).setParameter(
						"kbTemplateId", kbTemplate.getKbTemplateId()
					).setParameter(
						"selectedItemId", kbTemplate.getPrimaryKey()
					).buildString()
				).put(
					"id", kbTemplate.getPrimaryKey()
				).put(
					"name", kbTemplate.getTitle()
				).put(
					"type", "template"
				));
		}

		return navigationItemsJSONArray;
	}

	private JSONArray _getKBTemplatesNavigationItemsJSONArray() {
		return JSONUtil.put(
			JSONUtil.put(
				"actions",
				_kbDropdownItemsProvider.getKBFolderDropdownItems(null)
			).put(
				"children", _getKBTemplateChildrenJSONArray()
			).put(
				"href",
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/view_kb_templates.jsp"
				).buildString()
			).put(
				"id", KBFolderConstants.DEFAULT_PARENT_FOLDER_ID
			).put(
				"name", _themeDisplay.translate("home")
			).put(
				"type", "folder"
			));
	}

	private List<Long> _getSelectedItemAncestorIds() throws PortalException {
		List<Long> selectedItemAncestorIds = new ArrayList<>();

		if (!_isRootFolderSelected()) {
			Long kbFolderId = null;

			if (_isKBArticleSelected()) {
				KBArticle kbArticle = _getKBArticle(
					_getKBArticleResourcePrimaryKeyFromRequestParameter());

				if (kbArticle != null) {
					selectedItemAncestorIds.addAll(
						kbArticle.getAncestorResourcePrimaryKeys());
					kbFolderId = kbArticle.getKbFolderId();
				}
			}

			if (kbFolderId == null) {
				kbFolderId = _getKBFolderIdFromRequestParameter();
			}

			KBFolder kbFolder = _getKBFolder(kbFolderId);

			if (kbFolder != null) {
				selectedItemAncestorIds.addAll(
					kbFolder.getAncestorKBFolderIds());
			}
		}

		return selectedItemAncestorIds;
	}

	private long _getSelectedItemId() {
		return ParamUtil.getLong(
			_httpServletRequest, "selectedItemId",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	private boolean _isKBArticleSelected() {
		long kbArticleClassNameId = PortalUtil.getClassNameId(
			KBArticleConstants.getClassName());

		long resourceClassNameId = ParamUtil.getLong(
			_httpServletRequest, "resourceClassNameId");

		if (resourceClassNameId == kbArticleClassNameId) {
			return true;
		}

		return false;
	}

	private boolean _isRootFolderSelected() {
		if (_getSelectedItemId() ==
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return true;
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final KBArticleURLHelper _kbArticleURLHelper;
	private final KBDropdownItemsProvider _kbDropdownItemsProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final List<Long> _selectedItemAncestorIds;
	private final ThemeDisplay _themeDisplay;

}