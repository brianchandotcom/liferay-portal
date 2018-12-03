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

package com.liferay.wiki.web.internal.portlet.toolbar.item;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.BasePortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeService;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"mvc.render.command.name=/wiki/view_pages"
	},
	service = {
		PortletToolbarContributor.class, WikiPortletToolbarContributor.class
	}
)
public class WikiPortletToolbarContributor
	extends BasePortletToolbarContributor {

	protected void addPortletTitleMenuItem(
			List<MenuItem> menuItems, WikiNode node, ThemeDisplay themeDisplay,
			PortletRequest portletRequest)
		throws PortalException {

		if (!_wikiNodeModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), node,
				ActionKeys.ADD_PAGE)) {

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("icon-plus-sign-2");
		urlMenuItem.setLabel(
			LanguageUtil.get(
				_portal.getHttpServletRequest(portletRequest), "add-page"));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(portletRequest));
		portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
		portletURL.setParameter("title", StringPool.BLANK);
		portletURL.setParameter("editTitle", "1");

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			WikiNode node = _getNode(themeDisplay, portletRequest);

			if (node != null) {
				addPortletTitleMenuItem(
					menuItems, node, themeDisplay, portletRequest);
			}
		}
		catch (PortalException pe) {
			_log.error("Unable to add page menu item", pe);
		}

		return menuItems;
	}

	@Reference(unbind = "-")
	protected void setWikiNodeService(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	private WikiNode _getNode(
		ThemeDisplay themeDisplay, PortletRequest portletRequest) {

		WikiNode node = (WikiNode)portletRequest.getAttribute(
			WikiWebKeys.WIKI_NODE);

		if (node != null) {
			return node;
		}

		String initialNodeName = StringPool.BLANK;

		try {
			WikiGroupServiceOverriddenConfiguration
				wikiGroupServiceOverriddenConfiguration =
					ConfigurationProviderUtil.getConfiguration(
						WikiGroupServiceOverriddenConfiguration.class,
						new GroupServiceSettingsLocator(
							themeDisplay.getScopeGroupId(),
							WikiConstants.SERVICE_NAME));

			initialNodeName =
				wikiGroupServiceOverriddenConfiguration.initialNodeName();
		}
		catch (ConfigurationException ce) {
			_log.error(
				"Unable to get initial node name for group " +
					themeDisplay.getScopeGroupId(),
				ce);
		}

		String name = BeanParamUtil.getString(
			node, portletRequest, "name", initialNodeName);

		if (Validator.isNotNull(name)) {
			try {
				node = _wikiNodeService.getNode(
					themeDisplay.getScopeGroupId(), name);
			}
			catch (NoSuchNodeException nsne) {
				node = null;
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return node;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPortletToolbarContributor.class);

	@Reference
	private Portal _portal;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiNode)")
	private ModelResourcePermission<WikiNode> _wikiNodeModelResourcePermission;

	private WikiNodeService _wikiNodeService;

}