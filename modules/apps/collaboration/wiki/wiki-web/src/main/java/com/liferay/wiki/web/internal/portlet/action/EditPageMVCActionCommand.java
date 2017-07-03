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

package com.liferay.wiki.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.struts.StrutsActionPortletURL;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashEntryService;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.exception.DuplicatePageException;
import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.exception.PageTitleException;
import com.liferay.wiki.exception.PageVersionException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.WikiPageResourceLocalService;
import com.liferay.wiki.service.WikiPageService;
import com.liferay.wiki.web.internal.WikiAttachmentsHelper;
import com.liferay.wiki.web.util.WikiWebComponentProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/wiki/edit_page"
	},
	service = MVCActionCommand.class
)
public class EditPageMVCActionCommand extends BaseMVCActionCommand {

	@Reference(unbind = "-")
	public void setWikiAttachmentsHelper(
		WikiAttachmentsHelper wikiAttachmentsHelper) {

		_wikiAttachmentsHelper = wikiAttachmentsHelper;
	}

	protected void deletePage(ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		double version = ParamUtil.getDouble(actionRequest, "version");

		String[] deletePageTitles = null;

		if (Validator.isNotNull(title)) {
			deletePageTitles = new String[] {title};
		}
		else {
			deletePageTitles = ParamUtil.getStringValues(
				actionRequest, "rowIdsWikiPage");
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (String deletePageTitle : deletePageTitles) {
			if (moveToTrash) {
				WikiPage trashedWikiPage = null;

				if (version > 0) {
					trashedWikiPage = _wikiPageService.movePageToTrash(
						nodeId, deletePageTitle, version);
				}
				else {
					trashedWikiPage = _wikiPageService.movePageToTrash(
						nodeId, deletePageTitle);
				}

				trashedModels.add(trashedWikiPage);
			}
			else {
				if (version > 0) {
					_wikiPageService.discardDraft(
						nodeId, deletePageTitle, version);
				}
				else {
					_wikiPageService.deletePage(nodeId, deletePageTitle);
				}
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			Map<String, Object> data = new HashMap<>();

			data.put("trashedModels", trashedModels);

			addDeleteSuccessData(actionRequest, data);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
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
			}
			else if (e instanceof DuplicatePageException ||
					 e instanceof PageContentException ||
					 e instanceof PageTitleException ||
					 e instanceof PageVersionException ||
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

		portletURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
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

	protected void restorePage(ActionRequest actionRequest) throws Exception {
		long[] restoreEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreEntryId : restoreEntryIds) {
			long overridePageResourcePrimKey = 0;

			TrashEntry trashEntry = _trashEntryLocalService.getTrashEntry(
				restoreEntryId);

			WikiPageResource pageResource =
				_wikiPageResourceLocalService.getPageResource(
					trashEntry.getClassPK());

			String title = _trashHelper.getOriginalTitle(
				pageResource.getTitle());

			WikiWebComponentProvider wikiWebComponentProvider =
				WikiWebComponentProvider.getWikiWebComponentProvider();

			WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
				wikiWebComponentProvider.getWikiGroupServiceConfiguration();

			if (title.equals(wikiGroupServiceConfiguration.frontPageName())) {
				WikiPage overridePage = _wikiPageLocalService.fetchPage(
					pageResource.getNodeId(),
					wikiGroupServiceConfiguration.frontPageName());

				if (overridePage != null) {
					overridePageResourcePrimKey =
						overridePage.getResourcePrimKey();
				}
			}

			_trashEntryService.restoreEntry(
				restoreEntryId, overridePageResourcePrimKey, null);
		}
	}

	protected void revertPage(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");
		double version = ParamUtil.getDouble(actionRequest, "version");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			WikiPage.class.getName(), actionRequest);

		_wikiPageService.revertPage(nodeId, title, version, serviceContext);
	}

	@Reference(unbind = "-")
	protected void setTrashEntryLocalService(
		TrashEntryLocalService trashEntryLocalService) {

		_trashEntryLocalService = trashEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageResourceLocalService(
		WikiPageResourceLocalService wikiPageResourceLocalService) {

		_wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	protected void subscribePage(ActionRequest actionRequest) throws Exception {
		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		_wikiPageService.subscribePage(nodeId, title);
	}

	protected void unsubscribePage(ActionRequest actionRequest)
		throws Exception {

		long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
		String title = ParamUtil.getString(actionRequest, "title");

		_wikiPageService.unsubscribePage(nodeId, title);
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
			page = _wikiPageService.updatePage(
				nodeId, title, version, content, summary, minorEdit, format,
				parentTitle, redirectTitle, serviceContext);
		}
		else {
			page = _wikiPageService.addPage(
				nodeId, title, content, summary, minorEdit, format, parentTitle,
				redirectTitle, serviceContext);

			if (copyPageAttachments) {
				long templateNodeId = ParamUtil.getLong(
					actionRequest, "templateNodeId");
				String templateTitle = ParamUtil.getString(
					actionRequest, "templateTitle");

				_wikiPageService.copyPageAttachments(
					templateNodeId, templateTitle, page.getNodeId(),
					page.getTitle());
			}
		}

		_wikiAttachmentsHelper.addAttachments(actionRequest);

		return page;
	}

	private TrashEntryLocalService _trashEntryLocalService;
	private TrashEntryService _trashEntryService;

	@Reference
	private TrashHelper _trashHelper;

	private WikiAttachmentsHelper _wikiAttachmentsHelper;
	private WikiPageLocalService _wikiPageLocalService;
	private WikiPageResourceLocalService _wikiPageResourceLocalService;
	private WikiPageService _wikiPageService;

}