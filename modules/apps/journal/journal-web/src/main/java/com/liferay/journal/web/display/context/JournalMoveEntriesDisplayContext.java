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

package com.liferay.journal.web.display.context;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.journal.service.permission.JournalFolderPermission;
import com.liferay.journal.web.asset.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class JournalMoveEntriesDisplayContext {

	public JournalMoveEntriesDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, String currentURL)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_servletRequest = PortalUtil.getHttpServletRequest(
			_liferayPortletRequest);

		_currentURL = currentURL;

		processFolders(getMoveFolders());
		processArticles(getMoveArticles());

		setViewAttributes();
	}

	public String getIconCssClass(JournalArticle article)
		throws PortalException {

		if (_journalArticleAssetRendererFactory == null) {
			_journalArticleAssetRendererFactory =
				AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
					JournalArticle.class);
		}

		AssetRenderer<JournalArticle> assetRenderer =
			_journalArticleAssetRendererFactory.getAssetRenderer(
				JournalArticleAssetRenderer.getClassPK(article));

		return assetRenderer.getIconCssClass();
	}

	public String getIconCssClass(JournalFolder folder) throws PortalException {
		if (_journalFolderAssetRendererFactory == null) {
			_journalFolderAssetRendererFactory =
				AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
					JournalFolder.class);
		}

		AssetRenderer<JournalFolder> assetRenderer =
			_journalFolderAssetRendererFactory.getAssetRenderer(
				folder.getFolderId());

		return assetRenderer.getIconCssClass();
	}

	public List<JournalArticle> getInvalidMoveArticles() {
		return _invalidMoveArticles;
	}

	public List<JournalFolder> getInvalidMoveFolders() {
		return _invalidMoveFolders;
	}

	public List<JournalArticle> getMoveArticles() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)
			_liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		List<JournalArticle> articles = new ArrayList<>();

		String[] articleIds = StringUtil.split(
			ParamUtil.getString(_liferayPortletRequest, "articleIds"));

		for (String articleId : articleIds) {
			JournalArticle article = JournalArticleServiceUtil.fetchArticle(
				themeDisplay.getScopeGroupId(), articleId);

			if (article != null) {
				articles.add(article);
			}
		}

		return articles;
	}

	public List<JournalFolder> getMoveFolders() throws PortalException {
		long[] folderIds = StringUtil.split(
			ParamUtil.getString(_liferayPortletRequest, "folderIds"), 0L);

		List<JournalFolder> folders = new ArrayList<>();

		for (long folderId : folderIds) {
			JournalFolder folder = JournalFolderServiceUtil.fetchFolder(
				folderId);

			if (folder != null) {
				folders.add(folder);
			}
		}

		return folders;
	}

	public long getNewFolderId() {
		if (_newFolderId > 0) {
			return _newFolderId;
		}

		_newFolderId = ParamUtil.getLong(_liferayPortletRequest, "newFolderId");

		return _newFolderId;
	}

	public String getNewFolderName() throws PortalException {
		if (Validator.isNotNull(_newFolderName)) {
			return _newFolderName;
		}

		if (getNewFolderId() > 0) {
			JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
				getNewFolderId());

			_newFolderName = folder.getName();
		}
		else {
			_newFolderName = LanguageUtil.get(_servletRequest, "home");
		}

		return _newFolderName;
	}

	public PermissionChecker getPermissionChecker() {
		if (_permissionChecker != null) {
			return _permissionChecker;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay) _liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_permissionChecker = themeDisplay.getPermissionChecker();

		return _permissionChecker;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_liferayPortletRequest, "redirect");

		return _redirect;
	}

	public List<JournalArticle> getValidMoveArticles() {
		return _validMoveArticles;
	}

	public List<JournalFolder> getValidMoveFolders() {
		return _validMoveFolders;
	}

	public void processArticles(List<JournalArticle> articles)
		throws PortalException {

		_validMoveArticles = new ArrayList<>();
		_invalidMoveArticles = new ArrayList<>();

		for (JournalArticle curArticle : articles) {
			boolean hasUpdatePermission = JournalArticlePermission.contains(
				getPermissionChecker(), curArticle, ActionKeys.UPDATE);

			if (hasUpdatePermission) {
				_validMoveArticles.add(curArticle);
			}
			else {
				_invalidMoveArticles.add(curArticle);
			}
		}
	}

	public void processFolders(List<JournalFolder> folders)
		throws PortalException {

		_validMoveFolders = new ArrayList<>();
		_invalidMoveFolders = new ArrayList<>();

		for (JournalFolder curFolder : folders) {
			boolean hasUpdatePermission = JournalFolderPermission.contains(
				getPermissionChecker(), curFolder, ActionKeys.UPDATE);

			if (hasUpdatePermission) {
				_validMoveFolders.add(curFolder);
			}
			else {
				_invalidMoveFolders.add(curFolder);
			}
		}
	}

	public void setViewAttributes() {
		PortalUtil.addPortletBreadcrumbEntry(
			_servletRequest,
			LanguageUtil.get(_servletRequest, "move-web-content"), _currentURL);

		ThemeDisplay themeDisplay =
			(ThemeDisplay) _liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(getRedirect());

		if (_liferayPortletResponse instanceof RenderResponse) {
			RenderResponse renderResponse =
				(RenderResponse)_liferayPortletResponse;

			renderResponse.setTitle(
				LanguageUtil.get(_servletRequest, "move-web-content"));
		}
	}

	private final String _currentURL;
	private List<JournalArticle> _invalidMoveArticles;
	private List<JournalFolder> _invalidMoveFolders;
	private AssetRendererFactory _journalArticleAssetRendererFactory;
	private AssetRendererFactory _journalFolderAssetRendererFactory;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private long _newFolderId;
	private String _newFolderName;
	private PermissionChecker _permissionChecker;
	private String _redirect;
	private final HttpServletRequest _servletRequest;
	private List<JournalArticle> _validMoveArticles;
	private List<JournalFolder> _validMoveFolders;

}