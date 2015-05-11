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

package com.liferay.wiki.web.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.taglib.ui.DeleteMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLToolbarItem;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.wiki.display.context.WikiListPagesDisplayContext;
import com.liferay.wiki.display.context.WikiUIItemKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;
import com.liferay.wiki.settings.WikiGroupServiceSettings;
import com.liferay.wiki.web.display.context.util.WikiRequestHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DefaultWikiListPagesDisplayContext
	implements WikiListPagesDisplayContext {

	public DefaultWikiListPagesDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		WikiNode wikiNode) {

		_request = request;
		_wikiNode = wikiNode;

		_wikiRequestHelper = new WikiRequestHelper(request);
	}

	@Override
	public Menu getMenu(WikiPage wikiPage) throws PortalException {
		Menu menu = new Menu();

		List<MenuItem> menuItems = new ArrayList<>();

		_addEditMenuItem(menuItems, wikiPage);
		_addPermissionsMenuItem(menuItems, wikiPage);
		_addCopyMenuItem(menuItems, wikiPage);
		_addMoveMenuItem(menuItems, wikiPage);
		_addSubscriptionMenuItem(menuItems, wikiPage);
		_addDeleteMenuItem(menuItems, wikiPage);

		menu.setMenuItems(menuItems);

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = new ArrayList<>();

		_addAddPageToolbarItem(toolbarItems);

		return toolbarItems;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	private void _addAddPageToolbarItem(List<ToolbarItem> toolbarItems) {
		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter("struts_action", "/wiki/edit_page");
		portletURL.setParameter("redirect", _wikiRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"nodeId", String.valueOf(_wikiNode.getNodeId()));
		portletURL.setParameter("title", StringPool.BLANK);
		portletURL.setParameter("editTitle", "1");

		URLToolbarItem addPageToolbarItem = new URLToolbarItem();

		addPageToolbarItem.setKey(WikiUIItemKeys.ADD_PAGE);
		addPageToolbarItem.setLabel("add-page");
		addPageToolbarItem.setURL(portletURL.toString());

		toolbarItems.add(addPageToolbarItem);
	}

	private void _addCopyMenuItem(List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		if (_isCopyPasteEnabled(wikiPage)) {
			URLMenuItem menuItem = new URLMenuItem();

			menuItem.setIcon("icon-copy");
			menuItem.setKey(WikiUIItemKeys.COPY);
			menuItem.setLabel("copy");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/wiki/edit_page");
			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter("title", StringPool.BLANK);
			portletURL.setParameter("editTitle", "1");
			portletURL.setParameter(
				"templateNodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"templateTitle", HtmlUtil.unescape(wikiPage.getTitle()));

			menuItem.setURL(portletURL.toString());

			menuItems.add(menuItem);
		}
	}

	private void _addDeleteMenuItem(List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		if (!wikiPage.isDraft() &&
			WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage.getNodeId(),
				HtmlUtil.unescape(wikiPage.getTitle()), ActionKeys.DELETE)) {

			DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

			deleteMenuItem.setKey(WikiUIItemKeys.DELETE);
			deleteMenuItem.setTrash(
				TrashUtil.isTrashEnabled(_wikiRequestHelper.getScopeGroupId()));

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createActionURL();

			portletURL.setParameter("struts_action", "/wiki/edit_page");
			portletURL.setParameter(
				Constants.CMD,
				TrashUtil.isTrashEnabled(_wikiRequestHelper.getScopeGroupId())
					? Constants.MOVE_TO_TRASH : Constants.DELETE);
			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"title", HtmlUtil.unescape(wikiPage.getTitle()));

			deleteMenuItem.setURL(portletURL.toString());

			menuItems.add(deleteMenuItem);
		}

		if (wikiPage.isDraft() &&
			WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.DELETE)) {

			URLMenuItem menuItem = new URLMenuItem();

			menuItem.setIcon("icon-remove");
			menuItem.setKey(WikiUIItemKeys.DELETE);
			menuItem.setLabel("discard-draft");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createActionURL();

			portletURL.setParameter("struts_action", "/wiki/edit_page");
			portletURL.setParameter(Constants.CMD, Constants.DELETE);
			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"title", HtmlUtil.unescape(wikiPage.getTitle()));
			portletURL.setParameter(
				"version", String.valueOf(wikiPage.getVersion()));

			menuItem.setURL(portletURL.toString());

			menuItems.add(menuItem);
		}
	}

	private void _addEditMenuItem(List<MenuItem> menuItems, WikiPage wikiPage) {
		if (WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.UPDATE)) {

			URLMenuItem menuItem = new URLMenuItem();

			menuItem.setIcon("icon-edit");
			menuItem.setKey(WikiUIItemKeys.EDIT);
			menuItem.setLabel("edit");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/wiki/edit_page");
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"title", HtmlUtil.unescape(wikiPage.getTitle()));

			menuItem.setURL(portletURL.toString());

			menuItems.add(menuItem);
		}
	}

	private void _addMoveMenuItem(List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		if (_isCopyPasteEnabled(wikiPage)) {
			URLMenuItem menuItem = new URLMenuItem();

			menuItem.setIcon("icon-move");
			menuItem.setKey(WikiUIItemKeys.MOVE);
			menuItem.setLabel("move");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/wiki/move_page");
			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter("title", StringPool.BLANK);

			menuItem.setURL(portletURL.toString());

			menuItems.add(menuItem);
		}
	}

	private void _addPermissionsMenuItem(
		List<MenuItem> menuItems, WikiPage wikiPage) {

		if (WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.PERMISSIONS)) {

			URLMenuItem menuItem = new URLMenuItem();

			menuItem.setIcon("icon-lock");
			menuItem.setKey(WikiUIItemKeys.PERMISSIONS);
			menuItem.setLabel("permissions");
			menuItem.setMethod("get");
			menuItem.setUseDialog(true);

			String url = null;

			try {
				url = PermissionsURLTag.doTag(
					null, WikiPage.class.getName(), wikiPage.getTitle(), null,
					String.valueOf(wikiPage.getResourcePrimKey()),
					LiferayWindowState.POP_UP.toString(), null, _request);
			}
			catch (Exception e) {
				throw new SystemException(
					"Unable to create permissions URL", e);
			}

			menuItem.setURL(url);

			menuItems.add(menuItem);
		}
	}

	private void _addSubscriptionMenuItem(
		List<MenuItem> menuItems, WikiPage wikiPage) {

		WikiGroupServiceSettings wikiGroupServiceSettings =
			_wikiRequestHelper.getWikiGroupServiceSettings();

		if (WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.SUBSCRIBE) &&
			(wikiGroupServiceSettings.emailPageAddedEnabled() ||
			 wikiGroupServiceSettings.emailPageUpdatedEnabled())) {

			User user = _wikiRequestHelper.getUser();

			if (SubscriptionLocalServiceUtil.isSubscribed(
					user.getCompanyId(), user.getUserId(),
					WikiPage.class.getName(), wikiPage.getResourcePrimKey())) {

				URLMenuItem menuItem = new URLMenuItem();

				menuItem.setIcon("icon-remove-sign");
				menuItem.setKey(WikiUIItemKeys.UNSUBSCRIBE);
				menuItem.setLabel("unsubscribe");

				LiferayPortletResponse liferayPortletResponse =
					_wikiRequestHelper.getLiferayPortletResponse();

				PortletURL portletURL =
					liferayPortletResponse.createActionURL();

				portletURL.setParameter("struts_action", "/wiki/edit_page");
				portletURL.setParameter(Constants.CMD, Constants.UNSUBSCRIBE);
				portletURL.setParameter(
					"redirect", _wikiRequestHelper.getCurrentURL());
				portletURL.setParameter(
					"nodeId", String.valueOf(wikiPage.getNodeId()));
				portletURL.setParameter(
					"title", HtmlUtil.unescape(wikiPage.getTitle()));

				menuItem.setURL(portletURL.toString());

				menuItems.add(menuItem);
			}
			else {
				URLMenuItem menuItem = new URLMenuItem();

				menuItem.setIcon("icon-ok-sign");
				menuItem.setKey(WikiUIItemKeys.SUBSCRIBE);
				menuItem.setLabel("subscribe");

				LiferayPortletResponse liferayPortletResponse =
					_wikiRequestHelper.getLiferayPortletResponse();

				PortletURL portletURL =
					liferayPortletResponse.createActionURL();

				portletURL.setParameter("struts_action", "/wiki/edit_page");
				portletURL.setParameter(Constants.CMD, Constants.SUBSCRIBE);
				portletURL.setParameter(
					"redirect", _wikiRequestHelper.getCurrentURL());
				portletURL.setParameter(
					"nodeId", String.valueOf(wikiPage.getNodeId()));
				portletURL.setParameter(
					"title", HtmlUtil.unescape(wikiPage.getTitle()));

				menuItem.setURL(portletURL.toString());

				menuItems.add(menuItem);
			}
		}
	}

	private boolean _isCopyPasteEnabled(WikiPage wikiPage)
		throws PortalException {

		if (!WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.UPDATE)) {

			return false;
		}

		if (!WikiNodePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage.getNodeId(),
				ActionKeys.ADD_PAGE)) {

			return false;
		}

		return true;
	}

	private static final UUID _UUID = UUID.fromString(
		"628C435B-DB39-4E46-91DF-CEA763CF79F5");

	private final HttpServletRequest _request;
	private final WikiNode _wikiNode;
	private final WikiRequestHelper _wikiRequestHelper;

}