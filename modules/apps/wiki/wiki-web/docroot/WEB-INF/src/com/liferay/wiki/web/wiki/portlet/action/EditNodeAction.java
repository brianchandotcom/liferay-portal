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

package com.liferay.wiki.web.wiki.portlet.action;

import com.liferay.portal.kernel.configuration.module.ModuleConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.wiki.exception.DuplicateNodeNameException;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NodeNameException;
import com.liferay.wiki.exception.RequiredNodeException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiNodeServiceUtil;
import com.liferay.wiki.util.WikiCacheThreadLocal;
import com.liferay.wiki.util.WikiCacheUtil;
import com.liferay.wiki.web.settings.WikiPortletInstanceOverriddenConfiguration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class EditNodeAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
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

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.wiki.error");
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

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			long nodeId = ParamUtil.getLong(renderRequest, "nodeId");

			if (nodeId > 0) {
				ActionUtil.getNode(renderRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.wiki.edit_node"));
	}

	protected void deleteNode(ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		int nodeCount = WikiNodeLocalServiceUtil.getNodesCount(
			themeDisplay.getScopeGroupId());

		if (nodeCount == 1) {
			SessionErrors.add(actionRequest, RequiredNodeException.class);

			return;
		}

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		WikiNode wikiNode = WikiNodeServiceUtil.getNode(nodeId);

		String oldName = wikiNode.getName();

		WikiCacheThreadLocal.setClearCache(false);

		WikiNode trashWikiNode = null;

		if (moveToTrash) {
			trashWikiNode = WikiNodeServiceUtil.moveNodeToTrash(nodeId);
		}
		else {
			WikiNodeServiceUtil.deleteNode(nodeId);
		}

		WikiCacheUtil.clearCache(nodeId);

		WikiCacheThreadLocal.setClearCache(true);

		WikiPortletInstanceOverriddenConfiguration
			wikiPortletInstanceConfiguration = getWikiPortletInstanceSettings(
				actionRequest);

		updateSettings(
			wikiPortletInstanceConfiguration, oldName, StringPool.BLANK);

		if (moveToTrash && (trashWikiNode != null)) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashWikiNode);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	protected WikiPortletInstanceOverriddenConfiguration
			getWikiPortletInstanceSettings(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		WikiPortletInstanceOverriddenConfiguration
			wikiPortletInstanceConfiguration =
				ModuleConfigurationFactoryUtil.getConfiguration(
					WikiPortletInstanceOverriddenConfiguration.class,
					new PortletInstanceSettingsLocator(
						themeDisplay.getLayout(), portletDisplay.getId()));

		return wikiPortletInstanceConfiguration;
	}

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			TrashEntryServiceUtil.restoreEntry(restoreTrashEntryId);
		}
	}

	protected void subscribeNode(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		WikiNodeServiceUtil.subscribeNode(nodeId);
	}

	protected void unsubscribeNode(ActionRequest actionRequest)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		WikiNodeServiceUtil.unsubscribeNode(nodeId);
	}

	protected void updateNode(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			WikiNode.class.getName(), actionRequest);

		if (nodeId <= 0) {

			// Add node

			WikiNodeServiceUtil.addNode(name, description, serviceContext);
		}
		else {

			// Update node

			WikiNode wikiNode = WikiNodeServiceUtil.getNode(nodeId);

			String oldName = wikiNode.getName();

			WikiNodeServiceUtil.updateNode(
				nodeId, name, description, serviceContext);

			WikiPortletInstanceOverriddenConfiguration
				wikiPortletInstanceConfiguration =
					getWikiPortletInstanceSettings(actionRequest);

			updateSettings(wikiPortletInstanceConfiguration, oldName, name);
		}
	}

	protected void updateSettings(
			WikiPortletInstanceOverriddenConfiguration
				wikiPortletInstanceConfiguration,
			String oldName, String newName)
		throws Exception {

		String[] hiddenNodes = wikiPortletInstanceConfiguration.hiddenNodes();

		ArrayUtil.replace(hiddenNodes, oldName, newName);

		wikiPortletInstanceConfiguration.setHiddenNodes(hiddenNodes);

		String[] visibleNodes = wikiPortletInstanceConfiguration.visibleNodes();

		ArrayUtil.replace(visibleNodes, oldName, newName);

		wikiPortletInstanceConfiguration.setVisibleNodes(visibleNodes);

		wikiPortletInstanceConfiguration.store();
	}

}