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

import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.servlet.SessionErrors;
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
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.wiki.configuration.WikiPropsValues;
import com.liferay.wiki.configuration.WikiSettings;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.exception.DuplicatePageException;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.exception.PageTitleException;
import com.liferay.wiki.exception.PageVersionException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;

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
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		WikiPage page = null;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				page = updatePage(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deletePage(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deletePage(actionRequest, true);
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
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
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

				return actionMapping.findForward("portlet.wiki.error");
			}
			else if (e instanceof NoSuchPageException) {

				// Let edit_page.jsp handle this case

			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.wiki.edit_page"));
	}

	protected void deletePage(ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		double version = ParamUtil.getDouble(actionRequest, "version");

		WikiPage wikiPage = null;

		if (moveToTrash) {
			if (version > 0) {
				wikiPage = WikiPageServiceUtil.movePageToTrash(
					nodeId, title, version);
			}
			else {
				wikiPage = WikiPageServiceUtil.movePageToTrash(nodeId, title);
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

		if (moveToTrash && (wikiPage != null)) {
			TrashUtil.addTrashSessionMessages(actionRequest, wikiPage);

			hideDefaultSuccessMessage(actionRequest);
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
				WikiWebKeys.WIKI_NODE);

			if (node != null) {
				nodeId = node.getNodeId();
			}
		}

		WikiPage page = null;

		if (Validator.isNull(title)) {
			renderRequest.setAttribute(WikiWebKeys.WIKI_PAGE, page);

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
				WikiSettings wikiSettings = WikiSettings.getInstance(
					page.getGroupId());

				if (title.equals(WikiPropsValues.FRONT_PAGE_NAME) &&
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

		renderRequest.setAttribute(WikiWebKeys.WIKI_PAGE, page);
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			WikiPage page, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletURLImpl portletURL = new StrutsActionPortletURL(
			(PortletResponseImpl)actionResponse, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/wiki/edit_page");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
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
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreEntryId : restoreEntryIds) {
			long overridePageResourcePrimKey = 0;

			TrashEntry trashEntry = TrashEntryLocalServiceUtil.getTrashEntry(
				restoreEntryId);

			WikiPageResource pageResource =
				WikiPageResourceLocalServiceUtil.getPageResource(
					trashEntry.getClassPK());

			String title = TrashUtil.getOriginalTitle(pageResource.getTitle());

			WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(
				pageResource.getResourcePrimKey());

			WikiSettings wikiSettings = WikiSettings.getInstance(
				wikiPage.getGroupId());

			if (title.equals(WikiPropsValues.FRONT_PAGE_NAME)) {
				WikiPage overridePage = WikiPageLocalServiceUtil.fetchPage(
					pageResource.getNodeId(), WikiPropsValues.FRONT_PAGE_NAME);

				if (overridePage != null) {
					overridePageResourcePrimKey =
						overridePage.getResourcePrimKey();
				}
			}

			TrashEntryServiceUtil.restoreEntry(
				restoreEntryId, overridePageResourcePrimKey, null);
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
		boolean copyPageAttachments = ParamUtil.getBoolean(
			actionRequest, "copyPageAttachments");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			WikiPage.class.getName(), actionRequest);

		WikiPage page = null;

		if (cmd.equals(Constants.UPDATE)) {
			page = WikiPageServiceUtil.updatePage(
				nodeId, title, version, content, summary, minorEdit, format,
				parentTitle, redirectTitle, serviceContext);
		}
		else {
			page = WikiPageServiceUtil.addPage(
				nodeId, title, content, summary, minorEdit, format, parentTitle,
				redirectTitle, serviceContext);

			if (copyPageAttachments) {
				long templateNodeId = ParamUtil.getLong(
					actionRequest, "templateNodeId");
				String templateTitle = ParamUtil.getString(
					actionRequest, "templateTitle");

				WikiPageServiceUtil.copyPageAttachments(
					templateNodeId, templateTitle, page.getNodeId(),
					page.getTitle());
			}
		}

		return page;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}