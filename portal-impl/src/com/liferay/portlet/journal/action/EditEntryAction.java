/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.DuplicateFolderNameException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 */
public class EditEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE) ||
				cmd.equals(Constants.DELETE_VERSIONS)) {

				deleteEntries(actionRequest);
			}
			else if (cmd.equals(Constants.EXPIRE)) {
				expireEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveEntries(actionRequest);
			}

			String redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			if (cmd.equals(Constants.DELETE_VERSIONS) &&
				hasArticle(actionRequest)) {

				redirect = ParamUtil.getString(
					actionRequest, "originalRedirect");
			}

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof NoSuchFolderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.journal.error");
			}
			else if (e instanceof DuplicateArticleIdException ||
					 e instanceof DuplicateFolderNameException ||
					 e instanceof NoSuchFolderException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getArticles(renderRequest);
			ActionUtil.getFolders(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.journal.edit_entry";

		return mapping.findForward(getForward(renderRequest, forward));
	}

	protected void deleteEntries(ActionRequest actionRequest) throws Exception {
		long[] deleteFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (long deleteFolderId : deleteFolderIds) {
			JournalFolderServiceUtil.deleteFolder(deleteFolderId);
		}

		String[] deleteArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String deleteArticleId : deleteArticleIds) {
			ActionUtil.deleteArticle(actionRequest, deleteArticleId);
		}
	}

	protected void expireEntries(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		long[] expireFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (long expireFolderId : expireFolderIds) {
			ActionUtil.expireFolder(groupId, expireFolderId, serviceContext);
		}

		String[] expireArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String expireArticleId : expireArticleIds) {
			ActionUtil.expireArticle(actionRequest, expireArticleId);
		}
	}

	protected boolean hasArticle(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		String articleId = ParamUtil.getString(actionRequest, "articleId");

		try {
			JournalArticleLocalServiceUtil.getArticle(groupId, articleId);
		}
		catch (NoSuchArticleException nsae) {
			return true;
		}

		return false;
	}

	protected void moveEntries(ActionRequest actionRequest) throws Exception {
		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (long folderId : folderIds) {
			JournalFolderServiceUtil.moveFolder(
				folderId, newFolderId, serviceContext);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		String[] articleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String articleId : articleIds) {
			JournalArticleServiceUtil.moveArticle(
				groupId, articleId, newFolderId);
		}
	}

}