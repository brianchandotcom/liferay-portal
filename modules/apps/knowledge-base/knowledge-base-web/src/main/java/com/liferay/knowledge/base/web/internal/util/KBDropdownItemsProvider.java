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

package com.liferay.knowledge.base.web.internal.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.DisplayPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBArticlePermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBFolderPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBTemplatePermission;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.subscription.service.SubscriptionLocalServiceUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;
import java.util.Objects;

/**
 * @author Adolfo Pérez
 */
public class KBDropdownItemsProvider {

	public KBDropdownItemsProvider(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_currentURL = PortalUtil.getCurrentURL(_liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getKBArticleDropdownItems(KBArticle kbArticle) {
		return DropdownItemListBuilder.add(
			() -> _hasUpdatePermission(kbArticle),
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/common/edit_kb_article.jsp"
					).setRedirect(
						_currentURL
					).setParameter(
						"resourcePrimKey", kbArticle.getResourcePrimKey()
					).buildRenderURL());
				dropdownItem.setIcon("edit");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"edit"));
			}
		).add(
			this::_hasAddPermission,
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/common/edit_kb_article.jsp"
					).setRedirect(
						_currentURL
					).setParameter(
						"parentResourceClassNameId", kbArticle.getClassNameId()
					).setParameter(
						"parentResourcePrimKey", kbArticle.getResourcePrimKey()
					).buildRenderURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"add-child-article"));
			}
		).add(
			() -> _hasPermissionsPermission(kbArticle),
			dropdownItem -> {
				dropdownItem.putData("action", "permissions");
				dropdownItem.putData(
					"permissionsURL",
					PermissionsURLTag.doTag(
						null, KBArticle.class.getName(), kbArticle.getTitle(),
						kbArticle.getGroupId(),
						String.valueOf(kbArticle.getResourcePrimKey()),
						LiferayWindowState.POP_UP.toString(), null,
						_liferayPortletRequest.getHttpServletRequest()));
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"permissions"));
			}
		).add(
			() -> _hasSubscription(kbArticle),
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/knowledge_base/unsubscribe_kb_article"
					).setRedirect(
						_currentURL
					).setParameter(
						"resourcePrimKey", kbArticle.getResourcePrimKey()
					).buildActionURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"unsubscribe"));
			}
		).add(
			() -> !_hasSubscription(kbArticle),
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/knowledge_base/subscribe_kb_article"
					).setRedirect(
						_currentURL
					).setParameter(
						"resourcePrimKey", kbArticle.getResourcePrimKey()
					).buildActionURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"subscribe"));
			}
		).add(
			() -> _hasMovePermission(kbArticle),
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/common/move_object.jsp"
					).setRedirect(
						_currentURL
					).setParameter(
						"parentResourceClassNameId",
						kbArticle.getParentResourceClassNameId()
					).setParameter(
						"parentResourcePrimKey",
						kbArticle.getParentResourcePrimKey()
					).setParameter(
						"resourceClassNameId", kbArticle.getClassNameId()
					).setParameter(
						"resourcePrimKey", kbArticle.getResourcePrimKey()
					).buildRenderURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"move"));
			}
		).add(
			() -> _hasDeletePermission(kbArticle),
			dropdownItem -> {
				dropdownItem.putData("action", "delete");
				dropdownItem.putData(
					"deleteKBArticleURL",
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/knowledge_base/delete_kb_article"
					).setRedirect(
						_currentURL
					).setParameter(
						"resourcePrimKey", kbArticle.getResourcePrimKey()
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"delete"));
			}
		).build();
	}

	public List<DropdownItem> getKBFolderDropdownItems(KBFolder kbFolder) {
		return DropdownItemListBuilder.add(
			() -> _hasUpdatePermission(kbFolder),
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/common/edit_kb_folder.jsp"
					).setRedirect(
						_currentURL
					).setParameter(
						"kbFolderId", kbFolder.getKbFolderId()
					).buildRenderURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"edit"));
			}
		).add(
			() -> _hasMovePermission(kbFolder),
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/common/move_object.jsp"
					).setRedirect(
						_currentURL
					).setParameter(
						"parentResourceClassNameId", kbFolder.getClassNameId()
					).setParameter(
						"parentResourcePrimKey", kbFolder.getParentKBFolderId()
					).setParameter(
						"resourceClassNameId", kbFolder.getClassNameId()
					).setParameter(
						"resourcePrimKey", kbFolder.getKbFolderId()
					).buildRenderURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"move"));
			}
		).add(
			() -> _hasDeletePermission(kbFolder),
			dropdownItem -> {
				dropdownItem.putData("action", "delete");
				dropdownItem.putData(
					"deleteKBFolderURL",
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/knowledge_base/delete_kb_folder"
					).setRedirect(
						_currentURL
					).setParameter(
						"kbFolderId", kbFolder.getKbFolderId()
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"delete"));
			}
		).add(
			() -> _hasPermissionsPermission(kbFolder),
			dropdownItem -> {
				dropdownItem.putData("action", "permissions");
				dropdownItem.putData(
					"permissionsURL", _getPermissionsURL(kbFolder));
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"permissions"));
			}
		).build();
	}

	public List<DropdownItem> getKBTemplateDropdownItems(KBTemplate kbTemplate)
		throws Exception {

		return DropdownItemListBuilder.add(
			() -> _hasViewPermission(kbTemplate),
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/view_kb_template.jsp"
					).setParameter(
						"kbTemplateId", kbTemplate.getKbTemplateId()
					).buildRenderURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"view"));
			}
		).add(
			() -> _hasUpdatePermission(kbTemplate),
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/common/edit_kb_template.jsp"
					).setRedirect(
						_currentURL
					).setParameter(
						"kbTemplateId", kbTemplate.getKbTemplateId()
					).buildRenderURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"edit"));
			}
		).add(
			() -> _hasPermissionsPermission(kbTemplate),
			dropdownItem -> {
				dropdownItem.putData("action", "permissions");
				dropdownItem.putData(
					"permissionsURL",
					PermissionsURLTag.doTag(
						null, KBTemplate.class.getName(), kbTemplate.getTitle(),
						String.valueOf(kbTemplate.getGroupId()),
						String.valueOf(kbTemplate.getKbTemplateId()),
						LiferayWindowState.POP_UP.toString(), null,
						_liferayPortletRequest.getHttpServletRequest()));
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"permissions"));
			}
		).add(
			() -> _hasDeletePermission(kbTemplate),
			dropdownItem -> {
				dropdownItem.putData("action", "delete");
				dropdownItem.putData(
					"deleteKBTemplateURL",
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/knowledge_base/delete_kb_template"
					).setRedirect(
						_currentURL
					).setParameter(
						"kbTemplateId", kbTemplate.getKbTemplateId()
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"delete"));
			}
		).build();
	}

	private String _getPermissionsURL(KBFolder kbFolder) throws Exception {
		if (kbFolder == null) {
			return PermissionsURLTag.doTag(
				null, KBConstants.RESOURCE_NAME_ADMIN,
				_themeDisplay.getScopeGroupName(), null,
				String.valueOf(_themeDisplay.getScopeGroupId()),
				LiferayWindowState.POP_UP.toString(), null,
				_liferayPortletRequest.getHttpServletRequest());
		}

		return PermissionsURLTag.doTag(
			null, KBFolder.class.getName(), kbFolder.getName(),
			String.valueOf(_themeDisplay.getScopeGroupId()),
			String.valueOf(kbFolder.getKbFolderId()),
			LiferayWindowState.POP_UP.toString(), null,
			_liferayPortletRequest.getHttpServletRequest());
	}

	private boolean _hasAddPermission() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		if (AdminPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), KBActionKeys.ADD_KB_ARTICLE) &&
			Objects.equals(
				portletDisplay.getRootPortletId(),
				KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {

			return true;
		}

		if (DisplayPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), KBActionKeys.ADD_KB_ARTICLE) &&
			DisplayPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), KBActionKeys.ADMINISTRATOR) &&
			Objects.equals(
				portletDisplay.getRootPortletId(),
				KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) {

			return true;
		}

		return false;
	}

	private boolean _hasDeletePermission(KBArticle kbArticle) throws Exception {
		if (KBArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), kbArticle,
				KBActionKeys.DELETE)) {

			return true;
		}

		return false;
	}

	private boolean _hasDeletePermission(KBFolder kbFolder) throws Exception {
		if (kbFolder == null) {
			return false;
		}

		if (KBFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), kbFolder,
				KBActionKeys.DELETE)) {

			return true;
		}

		return false;
	}

	private boolean _hasDeletePermission(KBTemplate kbTemplate)
		throws Exception {

		if (KBTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), kbTemplate,
				KBActionKeys.DELETE)) {

			return true;
		}

		return false;
	}

	private boolean _hasMovePermission(KBArticle kbArticle) throws Exception {
		if (KBArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), kbArticle,
				KBActionKeys.MOVE_KB_ARTICLE)) {

			return true;
		}

		return false;
	}

	private boolean _hasMovePermission(KBFolder kbFolder) throws Exception {
		if (kbFolder == null) {
			return false;
		}

		if (KBFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), kbFolder,
				KBActionKeys.MOVE_KB_FOLDER)) {

			return true;
		}

		return false;
	}

	private boolean _hasPermissionsPermission(KBArticle kbArticle)
		throws Exception {

		if (KBArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), kbArticle,
				KBActionKeys.PERMISSIONS)) {

			return true;
		}

		return false;
	}

	private boolean _hasPermissionsPermission(KBFolder kbFolder)
		throws Exception {

		if (kbFolder == null) {
			if (AdminPermission.contains(
					_themeDisplay.getPermissionChecker(),
					_themeDisplay.getScopeGroupId(), ActionKeys.PERMISSIONS) &&
				GroupPermissionUtil.contains(
					_themeDisplay.getPermissionChecker(),
					_themeDisplay.getScopeGroupId(), ActionKeys.PERMISSIONS)) {

				return true;
			}

			return false;
		}

		if (KBFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), kbFolder,
				KBActionKeys.PERMISSIONS)) {

			return true;
		}

		return false;
	}

	private boolean _hasPermissionsPermission(KBTemplate kbTemplate)
		throws Exception {

		if (KBTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), kbTemplate,
				KBActionKeys.PERMISSIONS)) {

			return true;
		}

		return false;
	}

	private boolean _hasSubscription(KBArticle kbArticle) {
		if (SubscriptionLocalServiceUtil.isSubscribed(
				_themeDisplay.getCompanyId(), _themeDisplay.getUserId(),
				KBArticle.class.getName(), kbArticle.getResourcePrimKey())) {

			return true;
		}

		return false;
	}

	private boolean _hasUpdatePermission(KBArticle kbArticle) throws Exception {
		if (KBArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), kbArticle,
				KBActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

	private boolean _hasUpdatePermission(KBFolder kbFolder) throws Exception {
		if (kbFolder == null) {
			return false;
		}

		if (KBFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), kbFolder,
				KBActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

	private boolean _hasUpdatePermission(KBTemplate kbTemplate)
		throws Exception {

		if (KBTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), kbTemplate,
				KBActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

	private boolean _hasViewPermission(KBTemplate kbTemplate) throws Exception {
		if (KBTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), kbTemplate,
				KBActionKeys.VIEW)) {

			return true;
		}

		return false;
	}

	private final String _currentURL;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}