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

package com.liferay.wiki.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.trash.service.TrashEntryService;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.exception.DuplicateNodeNameException;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NodeNameException;
import com.liferay.wiki.exception.RequiredNodeException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiNodeService;
import com.liferay.wiki.util.WikiCacheThreadLocal;
import com.liferay.wiki.util.WikiCacheUtil;
import com.liferay.wiki.web.configuration.WikiPortletInstanceOverriddenConfiguration;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/wiki/edit_node"
	},
	service = MVCActionCommand.class
)
public class EditNodeMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteNode(ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		int nodeCount = _wikiNodeLocalService.getNodesCount(
			themeDisplay.getScopeGroupId());

		if (nodeCount == 1) {
			SessionErrors.add(actionRequest, RequiredNodeException.class);

			return;
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		long[] deleteNodeIds = null;

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		if (nodeId > 0) {
			deleteNodeIds = new long[] {nodeId};
		}
		else {
			deleteNodeIds = ParamUtil.getLongValues(
				actionRequest, "rowIdsWikiNode");
		}

		WikiPortletInstanceOverriddenConfiguration
			wikiPortletInstanceOverriddenConfiguration =
				getWikiPortletInstanceOverriddenConfiguration(actionRequest);

		for (long deleteNodeId : deleteNodeIds) {
			WikiNode wikiNode = _wikiNodeService.getNode(deleteNodeId);

			String oldName = wikiNode.getName();

			WikiCacheThreadLocal.setClearCache(false);

			WikiNode trashWikiNode = null;

			if (moveToTrash) {
				trashWikiNode = _wikiNodeService.moveNodeToTrash(deleteNodeId);

				trashedModels.add(trashWikiNode);
			}
			else {
				_wikiNodeService.deleteNode(deleteNodeId);
			}

			WikiCacheUtil.clearCache(deleteNodeId);

			updateSettings(
				wikiPortletInstanceOverriddenConfiguration, oldName,
				StringPool.BLANK);
		}

		WikiCacheThreadLocal.setClearCache(true);

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateNode(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteNode(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteNode(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreTrashEntries(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeNode(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeNode(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/wiki/error.jsp");
			}
			else if (e instanceof DuplicateNodeNameException ||
					 e instanceof NodeNameException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected WikiPortletInstanceOverriddenConfiguration
		getWikiPortletInstanceOverriddenConfiguration(
			ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		WikiPortletInstanceOverriddenConfiguration
			wikiPortletInstanceOverriddenConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					WikiPortletInstanceOverriddenConfiguration.class,
					new PortletInstanceSettingsLocator(
						themeDisplay.getLayout(), portletDisplay.getId()));

		return wikiPortletInstanceOverriddenConfiguration;
	}

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiNodeService(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	protected void subscribeNode(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		_wikiNodeService.subscribeNode(nodeId);
	}

	protected void unsubscribeNode(ActionRequest actionRequest)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		_wikiNodeService.unsubscribeNode(nodeId);
	}

	protected void updateNode(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			WikiNode.class.getName(), actionRequest);

		if (nodeId <= 0) {

			// Add node

			_wikiNodeService.addNode(name, description, serviceContext);
		}
		else {

			// Update node

			WikiNode wikiNode = _wikiNodeService.getNode(nodeId);

			String oldName = wikiNode.getName();

			_wikiNodeService.updateNode(
				nodeId, name, description, serviceContext);

			WikiPortletInstanceOverriddenConfiguration
				wikiPortletInstanceOverriddenConfiguration =
					getWikiPortletInstanceOverriddenConfiguration(
						actionRequest);

			updateSettings(
				wikiPortletInstanceOverriddenConfiguration, oldName, name);
		}
	}

	protected void updateSettings(
			WikiPortletInstanceOverriddenConfiguration
				wikiPortletInstanceOverriddenConfiguration,
			String oldName, String newName)
		throws Exception {

		String[] hiddenNodes =
			wikiPortletInstanceOverriddenConfiguration.hiddenNodes();

		ArrayUtil.replace(hiddenNodes, oldName, newName);

		wikiPortletInstanceOverriddenConfiguration.setHiddenNodes(hiddenNodes);

		String[] visibleNodes =
			wikiPortletInstanceOverriddenConfiguration.visibleNodes();

		ArrayUtil.replace(visibleNodes, oldName, newName);

		wikiPortletInstanceOverriddenConfiguration.setVisibleNodes(
			visibleNodes);

		wikiPortletInstanceOverriddenConfiguration.store();
	}

	private volatile TrashEntryService _trashEntryService;
	private volatile WikiNodeLocalService _wikiNodeLocalService;
	private volatile WikiNodeService _wikiNodeService;

}