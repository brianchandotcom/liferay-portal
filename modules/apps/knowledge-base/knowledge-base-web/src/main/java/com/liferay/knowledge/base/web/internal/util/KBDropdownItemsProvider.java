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
import com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBCommentConstants;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.web.internal.KBUtil;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.DisplayPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBArticlePermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBCommentPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBFolderPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBTemplatePermission;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.rss.util.RSSUtil;
import com.liferay.subscription.service.SubscriptionLocalServiceUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;

/**
 * @author Adolfo Pérez
 */
public class KBDropdownItemsProvider {

	public KBDropdownItemsProvider(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		this(liferayPortletRequest, liferayPortletResponse, null);
	}

	public KBDropdownItemsProvider(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		KBGroupServiceConfiguration kbGroupServiceConfiguration) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_kbGroupServiceConfiguration = kbGroupServiceConfiguration;

		_currentURL = String.valueOf(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse));

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getKBArticleDropdownItems(KBArticle kbArticle) {
		return getKBArticleDropdownItems(kbArticle, null, true, true, true);
	}

	public List<DropdownItem> getKBArticleDropdownItems(
		KBArticle kbArticle, boolean historyEnabled, boolean printEnabled,
		boolean subscriptionEnabled) {

		return getKBArticleDropdownItems(
			kbArticle, null, historyEnabled, printEnabled, subscriptionEnabled);
	}

	public List<DropdownItem> getKBArticleDropdownItems(
		KBArticle kbArticle, List<Long> selectedItemAncestorIds) {

		return getKBArticleDropdownItems(
			kbArticle, selectedItemAncestorIds, true, true, true);
	}

	public List<DropdownItem> getKBArticleDropdownItems(
		KBArticle kbArticle, List<Long> selectedItemAncestorIds,
		boolean historyEnabled, boolean printEnabled,
		boolean subscriptionEnabled) {

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> dropdownGroupItem.setDropdownItems(
				DropdownItemListBuilder.add(
					() -> _hasUpdatePermission(kbArticle),
					_getEditDropdownItem(kbArticle)
				).add(
					this::_hasAddPermission, _getAddChildDropdownItem(kbArticle)
				).add(
					() ->
						subscriptionEnabled &&
						_hasSubscriptionPermission(kbArticle) &&
						_hasSubscription(kbArticle),
					_getUnsubscribeDropdownItem(kbArticle)
				).add(
					() ->
						subscriptionEnabled &&
						_hasSubscriptionPermission(kbArticle) &&
						!_hasSubscription(kbArticle),
					_getSubscribeDropdownItem(kbArticle)
				).add(
					() -> historyEnabled && _hasHistoryPermission(kbArticle),
					_getHistoryDropdownItem(kbArticle)
				).build())
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _hasRSSPermission(kbArticle),
						_getRSSDropdownItem(kbArticle)
					).add(
						() -> printEnabled && _hasPrintPermission(),
						_getPrintDropdownItem(kbArticle)
					).add(
						() -> _hasMovePermission(kbArticle),
						_getMoveDropdownItem(kbArticle)
					).build());

				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _hasPermissionsPermission(kbArticle),
						_getPermissionsDropdownItem(kbArticle)
					).add(
						() -> _hasDeletePermission(kbArticle),
						_getDeleteDropdownItem(
							kbArticle, selectedItemAncestorIds)
					).build());

				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	public List<DropdownItem> getKBCommentDropdownItems(
			KBArticle kbArticle, KBComment kbComment)
		throws Exception {

		if (!KBArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), kbArticle,
				KBActionKeys.UPDATE)) {

			return null;
		}

		int previousStatus = KBUtil.getPreviousStatus(kbComment.getStatus());
		int nextStatus = KBUtil.getNextStatus(kbComment.getStatus());

		return DropdownItemListBuilder.add(
			() -> previousStatus != KBCommentConstants.STATUS_NONE,
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/knowledge_base/update_kb_comment_status"
					).setRedirect(
						_currentURL
					).setParameter(
						"kbCommentId", kbComment.getKbCommentId()
					).setParameter(
						"kbCommentStatus", previousStatus
					).buildActionURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						KBUtil.getStatusTransitionLabel(previousStatus)));
			}
		).add(
			() -> nextStatus != KBCommentConstants.STATUS_NONE,
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/knowledge_base/update_kb_comment_status"
					).setRedirect(
						_currentURL
					).setParameter(
						"kbCommentId", kbComment.getKbCommentId()
					).setParameter(
						"kbCommentStatus", nextStatus
					).buildActionURL());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						KBUtil.getStatusTransitionLabel(nextStatus)));
			}
		).add(
			() -> _hasDeletePermission(kbComment),
			dropdownItem -> {
				dropdownItem.putData("action", "delete");
				dropdownItem.putData(
					"deleteURL",
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/knowledge_base/delete_kb_comment"
					).setRedirect(
						_currentURL
					).setParameter(
						"kbCommentId", kbComment.getKbCommentId()
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"delete"));
			}
		).build();
	}

	public List<DropdownItem> getKBFolderDropdownItems(KBFolder kbFolder) {
		return getKBFolderDropdownItems(kbFolder, null);
	}

	public List<DropdownItem> getKBFolderDropdownItems(
		KBFolder kbFolder, List<Long> selectedItemAncestorIds) {

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> dropdownGroupItem.setDropdownItems(
				DropdownItemListBuilder.add(
					() -> _hasUpdatePermission(kbFolder),
					_getEditDropdownItem(kbFolder)
				).add(
					() -> _hasImportPermission(kbFolder),
					_getImportDropdownItem(kbFolder)
				).add(
					() -> _hasMovePermission(kbFolder),
					_getMoveDropdownItem(kbFolder)
				).add(
					() ->
						_hasSubscriptionPermission(kbFolder) &&
						!_hasSubscription(),
					_getGroupSubscribeDropdownItem()
				).add(
					() ->
						_hasSubscriptionPermission(kbFolder) &&
						_hasSubscription(),
					_getGroupUnsubscribeDropdownItem()
				).build())
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _hasPermissionsPermission(kbFolder),
						_getPermissionsDropdownItem(kbFolder)
					).add(
						() -> _hasDeletePermission(kbFolder),
						_getDeleteDropdownItem(
							kbFolder, selectedItemAncestorIds)
					).build());

				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	public List<DropdownItem> getKBTemplateDropdownItems(
		KBTemplate kbTemplate) {

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> dropdownGroupItem.setDropdownItems(
				DropdownItemListBuilder.add(
					() -> _hasViewPermission(kbTemplate),
					_getViewDropdownItem(kbTemplate)
				).add(
					() -> _hasUpdatePermission(kbTemplate),
					_getEditDropdownItem(kbTemplate)
				).build())
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _hasPermissionsPermission(kbTemplate),
						_getPermissionsDropdownItem(kbTemplate)
					).add(
						() -> _hasDeletePermission(kbTemplate),
						_getDeleteDropdownItem(kbTemplate)
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private String _createKBArticleRenderURL(KBArticle kbArticle) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/admin/view_kb_article.jsp"
		).setParameter(
			"resourceClassNameId", kbArticle.getClassNameId()
		).setParameter(
			"resourcePrimKey", kbArticle.getResourcePrimKey()
		).setParameter(
			"selectedItemId", kbArticle.getResourcePrimKey()
		).buildString();
	}

	private String _createKBFolderRenderURL(long kbFolderId) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/admin/view_kb_folders.jsp"
		).setParameter(
			"parentResourceClassNameId",
			PortalUtil.getClassNameId(KBFolderConstants.getClassName())
		).setParameter(
			"parentResourcePrimKey", kbFolderId
		).setParameter(
			"selectedItemId", kbFolderId
		).buildString();
	}

	private String _createKbHomeRenderURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/knowledge_base/view"
		).buildString();
	}

	private UnsafeConsumer<DropdownItem, Exception> _getAddChildDropdownItem(
		KBArticle kbArticle) {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						_liferayPortletRequest,
						KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
						PortletRequest.RENDER_PHASE)
				).setMVCPath(
					"/admin/common/edit_kb_article.jsp"
				).setRedirect(
					_currentURL
				).setParameter(
					"parentResourceClassNameId", kbArticle.getClassNameId()
				).setParameter(
					"parentResourcePrimKey", kbArticle.getResourcePrimKey()
				).buildString());
			dropdownItem.setIcon("document-text");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(),
					"add-child-article"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getDeleteDropdownItem(
		KBArticle kbArticle, List<Long> selectedItemAncestorIds) {

		return dropdownItem -> {
			dropdownItem.putData("action", "delete");
			dropdownItem.putData(
				"deleteURL",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/knowledge_base/delete_kb_article"
				).setRedirect(
					() -> {
						PortletDisplay portletDisplay =
							_themeDisplay.getPortletDisplay();

						if (!Objects.equals(
								portletDisplay.getRootPortletId(),
								KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {

							return _createKbHomeRenderURL();
						}

						if (((selectedItemAncestorIds == null) &&
							 _isKBArticleSelected(kbArticle)) ||
							((selectedItemAncestorIds != null) &&
							 selectedItemAncestorIds.contains(
								 kbArticle.getResourcePrimKey()))) {

							return _getParentNodeURL(kbArticle);
						}

						return _currentURL;
					}
				).setParameter(
					"resourcePrimKey", kbArticle.getResourcePrimKey()
				).buildString());
			dropdownItem.setIcon("trash");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getDeleteDropdownItem(
		KBFolder kbFolder, List<Long> selectedItemAncestorIds) {

		return dropdownItem -> {
			dropdownItem.putData("action", "delete");
			dropdownItem.putData(
				"deleteURL",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/knowledge_base/delete_kb_folder"
				).setRedirect(
					() -> {
						if (((selectedItemAncestorIds == null) &&
							 _isKBFolderSelected(kbFolder)) ||
							((selectedItemAncestorIds != null) &&
							 selectedItemAncestorIds.contains(
								 kbFolder.getKbFolderId()))) {

							return _createKBFolderRenderURL(
								kbFolder.getParentKBFolderId());
						}

						return _currentURL;
					}
				).setParameter(
					"kbFolderId", kbFolder.getKbFolderId()
				).buildString());
			dropdownItem.setIcon("trash");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getDeleteDropdownItem(
		KBTemplate kbTemplate) {

		return dropdownItem -> {
			dropdownItem.putData("action", "delete");
			dropdownItem.putData(
				"deleteURL",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/knowledge_base/delete_kb_template"
				).setRedirect(
					_currentURL
				).setParameter(
					"kbTemplateId", kbTemplate.getKbTemplateId()
				).buildString());
			dropdownItem.setIcon("trash");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getEditDropdownItem(
		KBArticle kbArticle) {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						_liferayPortletRequest,
						KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
						PortletRequest.RENDER_PHASE)
				).setMVCPath(
					"/admin/common/edit_kb_article.jsp"
				).setRedirect(
					_currentURL
				).setParameter(
					"resourcePrimKey", kbArticle.getResourcePrimKey()
				).buildString());
			dropdownItem.setIcon("pencil");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getEditDropdownItem(
		KBFolder kbFolder) {

		return dropdownItem -> {
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
			dropdownItem.setIcon("pencil");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getEditDropdownItem(
		KBTemplate kbTemplate) {

		return dropdownItem -> {
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
			dropdownItem.setIcon("pencil");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getGroupSubscribeDropdownItem() {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/knowledge_base/subscribe_group_kb_articles"
				).setRedirect(
					_currentURL
				).buildActionURL());
			dropdownItem.setIcon("bell-on");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(),
					"subscribe"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getGroupUnsubscribeDropdownItem() {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/knowledge_base/unsubscribe_group_kb_articles"
				).setRedirect(
					_currentURL
				).buildActionURL());
			dropdownItem.setIcon("bell-off");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(),
					"unsubscribe"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getHistoryDropdownItem(
		KBArticle kbArticle) {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						_liferayPortletRequest,
						KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
						PortletRequest.RENDER_PHASE)
				).setMVCPath(
					"/admin/common/kb_history.jsp"
				).setRedirect(
					_currentURL
				).setParameter(
					"resourceClassNameId", kbArticle.getClassNameId()
				).setParameter(
					"resourcePrimKey", kbArticle.getResourcePrimKey()
				).setParameter(
					"status",
					_liferayPortletRequest.getAttribute(
						KBWebKeys.KNOWLEDGE_BASE_STATUS)
				).buildString());
			dropdownItem.setIcon("date-time");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "history"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getImportDropdownItem(
		KBFolder kbFolder) {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/import.jsp"
				).setRedirect(
					PortalUtil.getCurrentURL(
						_liferayPortletRequest.getHttpServletRequest())
				).setParameter(
					"parentKBFolderId",
					() -> {
						if (kbFolder == null) {
							return KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;
						}

						return kbFolder.getKbFolderId();
					}
				).buildPortletURL());
			dropdownItem.setIcon("import");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "import"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getMoveDropdownItem(
		KBArticle kbArticle) {

		return dropdownItem -> {
			dropdownItem.putData("action", "move");
			dropdownItem.putData(
				"moveURL",
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
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());
			dropdownItem.setIcon("move-folder");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "move"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getMoveDropdownItem(
		KBFolder kbFolder) {

		return dropdownItem -> {
			dropdownItem.putData("action", "move");
			dropdownItem.putData(
				"moveURL",
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
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());
			dropdownItem.setIcon("move-folder");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "move"));
		};
	}

	private String _getParentNodeURL(KBArticle kbArticle)
		throws PortalException {

		KBArticle parentKBArticle = kbArticle.getParentKBArticle();

		if (parentKBArticle != null) {
			return _createKBArticleRenderURL(parentKBArticle);
		}

		return _createKBFolderRenderURL(kbArticle.getKbFolderId());
	}

	private UnsafeConsumer<DropdownItem, Exception> _getPermissionsDropdownItem(
		KBArticle kbArticle) {

		return dropdownItem -> {
			dropdownItem.putData("action", "permissions");
			dropdownItem.putData(
				"permissionsURL",
				PermissionsURLTag.doTag(
					null, KBArticle.class.getName(), kbArticle.getTitle(),
					kbArticle.getGroupId(),
					String.valueOf(kbArticle.getResourcePrimKey()),
					LiferayWindowState.POP_UP.toString(), null,
					_liferayPortletRequest.getHttpServletRequest()));
			dropdownItem.setIcon("password-policies");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(),
					"permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getPermissionsDropdownItem(
		KBFolder kbFolder) {

		return dropdownItem -> {
			dropdownItem.putData("action", "permissions");
			dropdownItem.putData(
				"permissionsURL", _getPermissionsURL(kbFolder));
			dropdownItem.setIcon("password-policies");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(),
					"permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getPermissionsDropdownItem(
		KBTemplate kbTemplate) {

		return dropdownItem -> {
			dropdownItem.putData("action", "permissions");
			dropdownItem.putData(
				"permissionsURL",
				PermissionsURLTag.doTag(
					null, KBTemplate.class.getName(), kbTemplate.getTitle(),
					String.valueOf(kbTemplate.getGroupId()),
					String.valueOf(kbTemplate.getKbTemplateId()),
					LiferayWindowState.POP_UP.toString(), null,
					_liferayPortletRequest.getHttpServletRequest()));
			dropdownItem.setIcon("password-policies");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(),
					"permissions"));
		};
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

	private UnsafeConsumer<DropdownItem, Exception> _getPrintDropdownItem(
		KBArticle kbArticle) {

		return dropdownItem -> {
			dropdownItem.putData("action", "print");
			dropdownItem.putData(
				"printURL",
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/common/print_kb_article.jsp"
				).setParameter(
					"resourceClassNameId", kbArticle.getClassNameId()
				).setParameter(
					"resourcePrimKey", kbArticle.getResourcePrimKey()
				).setParameter(
					"viewMode", Constants.PRINT
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());
			dropdownItem.setIcon("print");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "print"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getRSSDropdownItem(
		KBArticle kbArticle) {

		return dropdownItem -> {
			dropdownItem.setHref(
				RSSUtil.getURL(
					ResourceURLBuilder.createResourceURL(
						_liferayPortletResponse
					).setParameter(
						"resourceClassNameId", kbArticle.getClassNameId()
					).setParameter(
						"resourcePrimKey", kbArticle.getResourcePrimKey()
					).setResourceID(
						"kbArticleRSS"
					).buildString(),
					_kbGroupServiceConfiguration.rssDelta(),
					_kbGroupServiceConfiguration.rssDisplayStyle(),
					_kbGroupServiceConfiguration.rssFeedType(), null));
			dropdownItem.setIcon("shortcut");
			dropdownItem.setLabel("RSS");
			dropdownItem.setTarget("_blank");
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getSubscribeDropdownItem(
		KBArticle kbArticle) {

		return dropdownItem -> {
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
			dropdownItem.setIcon("bell-on");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(),
					"subscribe"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getUnsubscribeDropdownItem(
		KBArticle kbArticle) {

		return dropdownItem -> {
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
			dropdownItem.setIcon("bell-off");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(),
					"unsubscribe"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getViewDropdownItem(
		KBTemplate kbTemplate) {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/admin/view_kb_template.jsp"
				).setRedirect(
					_currentURL
				).setParameter(
					"kbTemplateId", kbTemplate.getKbTemplateId()
				).setParameter(
					"selectedItemId", kbTemplate.getPrimaryKey()
				).buildRenderURL());
			dropdownItem.setIcon("view");
			dropdownItem.setLabel(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), "view"));
		};
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

	private boolean _hasDeletePermission(KBComment kbComment) throws Exception {
		if (KBCommentPermission.contains(
				_themeDisplay.getPermissionChecker(), kbComment,
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

	private Boolean _hasHistoryPermission(KBArticle kbArticle) {
		if (kbArticle.isApproved() || !kbArticle.isFirstVersion()) {
			return true;
		}

		return false;
	}

	private boolean _hasImportPermission(KBFolder kbFolder)
		throws PortalException {

		if ((kbFolder == null) &&
			AdminPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), KBActionKeys.ADD_KB_ARTICLE)) {

			return true;
		}

		if ((kbFolder != null) &&
			KBFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), kbFolder,
				KBActionKeys.ADD_KB_ARTICLE)) {

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

	private Boolean _hasPrintPermission() {
		return true;
	}

	private Boolean _hasRSSPermission(KBArticle kbArticle) {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		if ((_kbGroupServiceConfiguration != null) &&
			_kbGroupServiceConfiguration.enableRSS() &&
			(kbArticle.isApproved() || !kbArticle.isFirstVersion()) &&
			!Objects.equals(
				portletDisplay.getRootPortletId(),
				KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {

			return true;
		}

		return false;
	}

	private boolean _hasSubscription() {
		if (SubscriptionLocalServiceUtil.isSubscribed(
				_themeDisplay.getCompanyId(), _themeDisplay.getUserId(),
				KBArticle.class.getName(), _themeDisplay.getScopeGroupId())) {

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

	private boolean _hasSubscriptionPermission(KBArticle kbArticle)
		throws Exception {

		if ((kbArticle.isApproved() || !kbArticle.isFirstVersion()) &&
			KBArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), kbArticle,
				KBActionKeys.SUBSCRIBE)) {

			return true;
		}

		return false;
	}

	private boolean _hasSubscriptionPermission(KBFolder kbFolder) {
		if ((kbFolder == null) &&
			AdminPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), KBActionKeys.SUBSCRIBE)) {

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

	private boolean _isKBArticleSelected(KBArticle kbArticle) {
		long resourceClassNameId = ParamUtil.getLong(
			_liferayPortletRequest, "resourceClassNameId");

		if (resourceClassNameId != kbArticle.getClassNameId()) {
			return false;
		}

		long resourcePrimaryKey = ParamUtil.getLong(
			_liferayPortletRequest, "resourcePrimKey",
			KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY);

		if (resourcePrimaryKey == kbArticle.getResourcePrimKey()) {
			return true;
		}

		return false;
	}

	private boolean _isKBFolderSelected(KBFolder kbFolder) {
		long parentResourceClassNameId = ParamUtil.getLong(
			_liferayPortletRequest, "parentResourceClassNameId");

		if (parentResourceClassNameId != kbFolder.getClassNameId()) {
			return false;
		}

		long parentResourcePrimaryKey = ParamUtil.getLong(
			_liferayPortletRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		if (parentResourcePrimaryKey == kbFolder.getKbFolderId()) {
			return true;
		}

		return false;
	}

	private final String _currentURL;
	private final KBGroupServiceConfiguration _kbGroupServiceConfiguration;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}