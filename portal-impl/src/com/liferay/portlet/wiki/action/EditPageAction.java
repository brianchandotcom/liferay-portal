/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.struts.StrutsActionPortletURL;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.wiki.DuplicatePageException;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.PageTitleException;
import com.liferay.portlet.wiki.PageVersionException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class EditPageAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		WikiPage page = null;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				page = updatePage(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deletePage(
					(LiferayPortletConfig)portletConfig, actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deletePage(
					(LiferayPortletConfig)portletConfig, actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restorePage(actionRequest);
			}
			else if (cmd.equals(Constants.REVERT)) {
				revertPage(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribePage(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribePage(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				int workflowAction = ParamUtil.getInteger(
					actionRequest, "workflowAction",
					WorkflowConstants.ACTION_PUBLISH);

				if (page != null) {
					if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
						redirect = getSaveAndContinueRedirect(
							actionRequest, actionResponse, page, redirect);
					}
					else if (redirect.endsWith("title=")) {
						redirect += page.getTitle();
					}
				}

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.wiki.error");
			}
			else if (e instanceof DuplicatePageException ||
					 e instanceof PageContentException ||
					 e instanceof PageVersionException ||
					 e instanceof PageTitleException ||
					 e instanceof SanitizerException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				Throwable cause = e.getCause();

				if (cause instanceof SanitizerException) {
					SessionErrors.add(actionRequest, SanitizerException.class);
				}
				else {
					throw e;
				}
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getNode(renderRequest);

			if (!SessionErrors.contains(
					renderRequest, DuplicatePageException.class.getName())) {

				getPage(renderRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PageTitleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.wiki.error");
			}
			else if (e instanceof NoSuchPageException) {

				// Let edit_page.jsp handle this case

			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.wiki.edit_page"));
	}

	protected void deletePage(
			LiferayPortletConfig liferayPortletConfig,
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		double version = ParamUtil.getDouble(actionRequest, "version");

		WikiPage wikiPage = null;

		if (moveToTrash) {
			if (version > 0) {
				wikiPage = WikiPageLocalServiceUtil.getPage(
					nodeId, title, version);

				WikiPageServiceUtil.movePageToTrash(nodeId, title, version);
			}
			else {
				wikiPage = WikiPageLocalServiceUtil.getPage(nodeId, title);

				WikiPageServiceUtil.movePageToTrash(nodeId, title);
			}
		}
		else {
			if (version > 0) {
				WikiPageServiceUtil.discardDraft(nodeId, title, version);
			}
			else {
				WikiPageServiceUtil.deletePage(nodeId, title);
			}
		}

		if (moveToTrash) {
			Map<String, String[]> data = new HashMap<String, String[]>();

			data.put(
				"restoreEntryIds",
				new String[] {String.valueOf(wikiPage.getResourcePrimKey())});

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA, data);

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}
	}

	protected void getPage(RenderRequest renderRequest) throws Exception {
		long nodeId = ParamUtil.getLong(renderRequest, "nodeId");
		String title = ParamUtil.getString(renderRequest, "title");
		double version = ParamUtil.getDouble(renderRequest, "version");
		boolean removeRedirect = ParamUtil.getBoolean(
			renderRequest, "removeRedirect");

		if (nodeId == 0) {
			WikiNode node = (WikiNode)renderRequest.getAttribute(
				WebKeys.WIKI_NODE);

			if (node != null) {
				nodeId = node.getNodeId();
			}
		}

		WikiPage page = null;

		if (Validator.isNull(title)) {
			renderRequest.setAttribute(WebKeys.WIKI_PAGE, page);

			return;
		}

		try {
			if (version == 0) {
				page = WikiPageServiceUtil.getPage(nodeId, title, null);
			}
			else {
				page = WikiPageServiceUtil.getPage(nodeId, title, version);
			}
		}
		catch (NoSuchPageException nspe1) {
			try {
				page = WikiPageServiceUtil.getPage(nodeId, title, false);
			}
			catch (NoSuchPageException nspe2) {
				if (title.equals(WikiPageConstants.FRONT_PAGE) &&
					(version == 0)) {

					ServiceContext serviceContext = new ServiceContext();

					page = WikiPageServiceUtil.addPage(
						nodeId, title, null, WikiPageConstants.NEW, true,
						serviceContext);
				}
				else {
					throw nspe2;
				}
			}
		}

		if (removeRedirect) {
			page.setContent(StringPool.BLANK);
			page.setRedirectTitle(StringPool.BLANK);
		}

		renderRequest.setAttribute(WebKeys.WIKI_PAGE, page);
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			WikiPage page, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String originalRedirect = ParamUtil.getString(
			actionRequest, "originalRedirect");

		PortletURLImpl portletURL = new StrutsActionPortletURL(
			(PortletResponseImpl)actionResponse, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/wiki/edit_page");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("originalRedirect", originalRedirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()), false);
		portletURL.setParameter(
			"nodeId", String.valueOf(page.getNodeId()), false);
		portletURL.setParameter("title", page.getTitle(), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void restorePage(ActionRequest actionRequest) throws Exception {
		long[] restoreEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreEntryIds"), 0L);

		for (long restoreEntryId : restoreEntryIds) {
			WikiPageServiceUtil.restorePageFromTrash(restoreEntryId);
		}
	}

	protected void revertPage(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		double version = ParamUtil.getDouble(actionRequest, "version");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			WikiPage.class.getName(), actionRequest);

		WikiPageServiceUtil.revertPage(nodeId, title, version, serviceContext);
	}

	protected void subscribePage(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		WikiPageServiceUtil.subscribePage(nodeId, title);
	}

	protected void unsubscribePage(ActionRequest actionRequest)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		WikiPageServiceUtil.unsubscribePage(nodeId, title);
	}

	protected WikiPage updatePage(ActionRequest actionRequest)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		double version = ParamUtil.getDouble(actionRequest, "version");

		String content = ParamUtil.getString(actionRequest, "content");
		String summary = ParamUtil.getString(actionRequest, "summary");
		boolean minorEdit = ParamUtil.getBoolean(actionRequest, "minorEdit");
		String format = ParamUtil.getString(actionRequest, "format");
		String parentTitle = ParamUtil.getString(actionRequest, "parentTitle");
		String redirectTitle = null;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			WikiPage.class.getName(), actionRequest);

		WikiPage page = null;

		if (cmd.equals(Constants.ADD)) {
			page = WikiPageServiceUtil.addPage(
				nodeId, title, content, summary, minorEdit, format, parentTitle,
				redirectTitle, serviceContext);
		}
		else {
			page = WikiPageServiceUtil.updatePage(
				nodeId, title, version, content, summary, minorEdit, format,
				parentTitle, redirectTitle, serviceContext);
		}

		return page;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}