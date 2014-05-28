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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.portlet.wiki.DuplicateNodeNameException;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NodeNameException;
import com.liferay.portlet.wiki.RequiredNodeException;
import com.liferay.portlet.wiki.WikiPortletInstanceSettings;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;
import com.liferay.portlet.wiki.util.WikiUtil;

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

		WikiPortletInstanceSettings wikiPortletInstanceSettings =
			getWikiPortletInstanceSettings(actionRequest);

		updateSettings(wikiPortletInstanceSettings, oldName, StringPool.BLANK);

		if (moveToTrash && (trashWikiNode != null)) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashWikiNode);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	protected WikiPortletInstanceSettings getWikiPortletInstanceSettings(
			ActionRequest actionRequest)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = portletDisplay.getId();

		WikiPortletInstanceSettings wikiPortletInstanceSettings =
			WikiUtil.getWikiPortletInstanceSettings(layout, portletId);

		return wikiPortletInstanceSettings;
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

			WikiPortletInstanceSettings wikiPortletInstanceSettings =
				getWikiPortletInstanceSettings(actionRequest);

			updateSettings(wikiPortletInstanceSettings, oldName, name);
		}
	}

	protected void updateSettings(
			WikiPortletInstanceSettings wikiPortletInstanceSettings,
			String oldName, String newName)
		throws Exception {

		String[] hiddenNodes = wikiPortletInstanceSettings.getHiddenNodes();

		ArrayUtil.replace(hiddenNodes, oldName, newName);

		wikiPortletInstanceSettings.setHiddenNodes(hiddenNodes);

		String[] visibleNodes = wikiPortletInstanceSettings.getVisibleNodes();

		ArrayUtil.replace(visibleNodes, oldName, newName);

		wikiPortletInstanceSettings.setVisibleNodes(visibleNodes);

		wikiPortletInstanceSettings.store();
	}

}