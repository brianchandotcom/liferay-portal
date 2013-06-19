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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
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
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.DuplicateFolderNameException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 */
public class EditEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE) ||
				cmd.equals(Constants.DELETE_VERSIONS)) {

				deleteEntries(
					(LiferayPortletConfig)portletConfig, actionRequest, false);
			}
			else if (cmd.equals(Constants.EXPIRE)) {
				expireEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntries(
					(LiferayPortletConfig)portletConfig, actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreEntryFromTrash(actionRequest);
			}

			String redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			if (cmd.equals(Constants.DELETE_VERSIONS) &&
				!ActionUtil.hasArticle(actionRequest)) {

				String referringPortletResource = ParamUtil.getString(
					actionRequest, "referringPortletResource");

				if (Validator.isNotNull(referringPortletResource)) {
					setForward(
						actionRequest,
						"portlet.journal.asset.add_asset_redirect");

					return;
				}
				else {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)actionRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					PortletURL portletURL = PortletURLFactoryUtil.create(
						actionRequest, portletConfig.getPortletName(),
						themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

					redirect = portletURL.toString();
				}
			}

			WindowState windowState = actionRequest.getWindowState();

			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				sendRedirect(actionRequest, actionResponse);
			}
			else if (Validator.isNotNull(redirect)) {
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

				SessionErrors.add(actionRequest, e.getClass(), e);
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
			ActionUtil.getArticles(renderRequest);
			ActionUtil.getFolders(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.journal.edit_entry";

		return actionMapping.findForward(getForward(renderRequest, forward));
	}

	protected void deleteEntries(
			LiferayPortletConfig liferayPortletConfig,
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		for (long deleteFolderId : deleteFolderIds) {
			if (moveToTrash) {
				JournalFolderServiceUtil.moveFolderToTrash(deleteFolderId);
			}
			else {
				JournalFolderServiceUtil.deleteFolder(deleteFolderId);
			}
		}

		String[] deleteArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		long[] restoreArticleIds = new long[deleteArticleIds.length];

		for (int i = 0; i < deleteArticleIds.length; i++) {
			String deleteArticleId = deleteArticleIds[i];

			if (moveToTrash) {
				JournalArticle article =
					JournalArticleServiceUtil.moveArticleToTrash(
						themeDisplay.getScopeGroupId(), deleteArticleId);

				restoreArticleIds[i] = article.getResourcePrimKey();
			}
			else {
				ActionUtil.deleteArticle(actionRequest, deleteArticleId);
			}
		}

		if (moveToTrash &&
			((deleteArticleIds.length > 0) || (deleteFolderIds.length > 0))) {

			Map<String, String[]> data = new HashMap<String, String[]>();

			data.put(
				"restoreArticleIds",
				ArrayUtil.toStringArray(restoreArticleIds));
			data.put(
				"restoreFolderIds", ArrayUtil.toStringArray(deleteFolderIds));

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

	protected void expireEntries(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] expireFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (long expireFolderId : expireFolderIds) {
			ActionUtil.expireFolder(
				themeDisplay.getScopeGroupId(), expireFolderId, serviceContext);
		}

		String[] expireArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String expireArticleId : expireArticleIds) {
			ActionUtil.expireArticle(actionRequest, expireArticleId);
		}
	}

	protected void moveEntries(ActionRequest actionRequest) throws Exception {
		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		long[] folderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "folderIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (long folderId : folderIds) {
			JournalFolderServiceUtil.moveFolder(
				folderId, newFolderId, serviceContext);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] articleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "articleIds"));

		for (String articleId : articleIds) {
			JournalArticleServiceUtil.moveArticle(
				themeDisplay.getScopeGroupId(), articleId, newFolderId);
		}
	}

	protected void restoreEntryFromTrash(ActionRequest actionRequest)
		throws PortalException, SystemException {

		long[] restoreFolderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreFolderIds"), 0L);

		for (long restoreFolderId : restoreFolderIds) {
			JournalFolderServiceUtil.restoreFolderFromTrash(restoreFolderId);
		}

		long[] restoreArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreArticleIds"), 0L);

		for (long restoreEntryId : restoreArticleIds) {
			JournalArticleServiceUtil.restoreArticleFromTrash(restoreEntryId);
		}
	}

}