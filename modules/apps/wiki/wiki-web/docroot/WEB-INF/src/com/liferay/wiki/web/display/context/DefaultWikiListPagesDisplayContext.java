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
import com.liferay.portal.kernel.language.LanguageUtil;
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
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.display.context.WikiListPagesDisplayContext;
import com.liferay.wiki.display.context.WikiUIItemKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;
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

		addEditMenuItem(menuItems, wikiPage);

		addPermissionsMenuItem(menuItems, wikiPage);

		addCopyMenuItem(menuItems, wikiPage);

		addMoveMenuItem(menuItems, wikiPage);

		addSubscriptionMenuItem(menuItems, wikiPage);

		addDeleteMenuItem(menuItems, wikiPage);

		menu.setMenuItems(menuItems);

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() {
		List<ToolbarItem> toolbarItems = new ArrayList<>();

		addAddPageToolbarItem(toolbarItems);

		return toolbarItems;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	protected void addAddPageToolbarItem(List<ToolbarItem> toolbarItems) {
		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter("struts_action", "/wiki/edit_page");
		portletURL.setParameter("redirect", _wikiRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"nodeId", String.valueOf(_wikiNode.getNodeId()));
		portletURL.setParameter("title", StringPool.BLANK);
		portletURL.setParameter("editTitle", "1");

		URLToolbarItem addPageURLToolbarItem = new URLToolbarItem();

		addPageURLToolbarItem.setKey(WikiUIItemKeys.ADD_PAGE);
		addPageURLToolbarItem.setLabel(LanguageUtil.get(_request, "add-page"));
		addPageURLToolbarItem.setURL(portletURL.toString());

		toolbarItems.add(addPageURLToolbarItem);
	}

	protected void addCopyMenuItem(List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		if (!isCopyPasteEnabled(wikiPage)) {
			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("icon-copy");
		urlMenuItem.setKey(WikiUIItemKeys.COPY);
		urlMenuItem.setLabel("copy");

		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/wiki/edit_page");
		portletURL.setParameter("redirect", _wikiRequestHelper.getCurrentURL());
		portletURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
		portletURL.setParameter("title", StringPool.BLANK);
		portletURL.setParameter("editTitle", "1");
		portletURL.setParameter(
			"templateNodeId", String.valueOf(wikiPage.getNodeId()));
		portletURL.setParameter(
			"templateTitle", HtmlUtil.unescape(wikiPage.getTitle()));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addDeleteMenuItem(
			List<MenuItem> menuItems, WikiPage wikiPage)
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

			String cmd = Constants.DELETE;

			if (TrashUtil.isTrashEnabled(
					_wikiRequestHelper.getScopeGroupId())) {

				cmd = Constants.MOVE_TO_TRASH;
			}

			portletURL.setParameter(Constants.CMD, cmd);

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

			URLMenuItem urlMenuItem = new URLMenuItem();

			urlMenuItem.setIcon("icon-remove");
			urlMenuItem.setKey(WikiUIItemKeys.DELETE);
			urlMenuItem.setLabel("discard-draft");

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

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
	}

	protected void addEditMenuItem(
		List<MenuItem> menuItems, WikiPage wikiPage) {

		if (!WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.UPDATE)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("icon-edit");
		urlMenuItem.setKey(WikiUIItemKeys.EDIT);
		urlMenuItem.setLabel("edit");

		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/wiki/edit_page");
		portletURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
		portletURL.setParameter(
			"title", HtmlUtil.unescape(wikiPage.getTitle()));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addMoveMenuItem(List<MenuItem> menuItems, WikiPage wikiPage)
		throws PortalException {

		if (!isCopyPasteEnabled(wikiPage)) {
			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("icon-move");
		urlMenuItem.setKey(WikiUIItemKeys.MOVE);
		urlMenuItem.setLabel("move");

		LiferayPortletResponse liferayPortletResponse =
			_wikiRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/wiki/move_page");
		portletURL.setParameter("redirect", _wikiRequestHelper.getCurrentURL());
		portletURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
		portletURL.setParameter(
			"title", HtmlUtil.unescape(wikiPage.getTitle()));

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	protected void addPermissionsMenuItem(
		List<MenuItem> menuItems, WikiPage wikiPage) {

		if (!WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.PERMISSIONS)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("icon-lock");
		urlMenuItem.setKey(WikiUIItemKeys.PERMISSIONS);
		urlMenuItem.setLabel("permissions");
		urlMenuItem.setMethod("get");
		urlMenuItem.setUseDialog(true);

		String url = null;

		try {
			url = PermissionsURLTag.doTag(
				null, WikiPage.class.getName(), wikiPage.getTitle(), null,
				String.valueOf(wikiPage.getResourcePrimKey()),
				LiferayWindowState.POP_UP.toString(), null, _request);
		}
		catch (Exception e) {
			throw new SystemException("Unable to create permissions URL", e);
		}

		urlMenuItem.setURL(url);

		menuItems.add(urlMenuItem);
	}

	protected void addSubscriptionMenuItem(
		List<MenuItem> menuItems, WikiPage wikiPage) {

		WikiGroupServiceOverriddenConfiguration wikiGroupServiceConfiguration =
			_wikiRequestHelper.getWikiGroupServiceSettings();

		if (!WikiPagePermissionChecker.contains(
				_wikiRequestHelper.getPermissionChecker(), wikiPage,
				ActionKeys.SUBSCRIBE) ||
			(!wikiGroupServiceConfiguration.emailPageAddedEnabled() &&
			 !wikiGroupServiceConfiguration.emailPageUpdatedEnabled())) {

			return;
		}

		User user = _wikiRequestHelper.getUser();

		if (SubscriptionLocalServiceUtil.isSubscribed(
				user.getCompanyId(), user.getUserId(), WikiPage.class.getName(),
				wikiPage.getResourcePrimKey())) {

			URLMenuItem urlMenuItem = new URLMenuItem();

			urlMenuItem.setIcon("icon-remove-sign");
			urlMenuItem.setKey(WikiUIItemKeys.UNSUBSCRIBE);
			urlMenuItem.setLabel("unsubscribe");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createActionURL();

			portletURL.setParameter("struts_action", "/wiki/edit_page");
			portletURL.setParameter(Constants.CMD, Constants.UNSUBSCRIBE);
			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"title", HtmlUtil.unescape(wikiPage.getTitle()));

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
		else {
			URLMenuItem urlMenuItem = new URLMenuItem();

			urlMenuItem.setIcon("icon-ok-sign");
			urlMenuItem.setKey(WikiUIItemKeys.SUBSCRIBE);
			urlMenuItem.setLabel("subscribe");

			LiferayPortletResponse liferayPortletResponse =
				_wikiRequestHelper.getLiferayPortletResponse();

			PortletURL portletURL = liferayPortletResponse.createActionURL();

			portletURL.setParameter("struts_action", "/wiki/edit_page");
			portletURL.setParameter(Constants.CMD, Constants.SUBSCRIBE);
			portletURL.setParameter(
				"redirect", _wikiRequestHelper.getCurrentURL());
			portletURL.setParameter(
				"nodeId", String.valueOf(wikiPage.getNodeId()));
			portletURL.setParameter(
				"title", HtmlUtil.unescape(wikiPage.getTitle()));

			urlMenuItem.setURL(portletURL.toString());

			menuItems.add(urlMenuItem);
		}
	}

	protected boolean isCopyPasteEnabled(WikiPage wikiPage)
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