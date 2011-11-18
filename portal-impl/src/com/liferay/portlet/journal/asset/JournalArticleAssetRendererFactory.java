/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.journal.service.permission.JournalStructurePermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
public class JournalArticleAssetRendererFactory
	extends BaseAssetRendererFactory {

	public static final String CLASS_NAME = JournalArticle.class.getName();

	public static final String TYPE = "content";

	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException {

		JournalArticle article = null;

		try {
			article = JournalArticleLocalServiceUtil.getArticle(classPK);
		}
		catch (NoSuchArticleException nsae) {
			JournalArticleResource articleResource =
				JournalArticleResourceLocalServiceUtil.getArticleResource(
					classPK);

			if (type == TYPE_LATEST_APPROVED) {
				article = JournalArticleLocalServiceUtil.getDisplayArticle(
					articleResource.getGroupId(),
					articleResource.getArticleId());
			}
			else {
				article = JournalArticleLocalServiceUtil.getLatestArticle(
					articleResource.getGroupId(),
					articleResource.getArticleId(),
					WorkflowConstants.STATUS_ANY);
			}
		}

		return new JournalArticleAssetRenderer(article);
	}

	@Override
	public AssetRenderer getAssetRenderer(long groupId, String urlTitle)
		throws PortalException, SystemException {

		JournalArticle article =
			JournalArticleServiceUtil.getDisplayArticleByUrlTitle(
				groupId, urlTitle);

		return new JournalArticleAssetRenderer(article);
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public Map<Long, String> getClassTypes(long[] groupIds) throws Exception {
		Map<Long, String> classTypes = new HashMap<Long, String>();

		for (long groupId : groupIds) {
			List<JournalStructure> structures =
				JournalStructureLocalServiceUtil.getStructures(groupId);

			for (JournalStructure structure : structures) {
				classTypes.put(structure.getId(), structure.getName());
			}
		}

		return classTypes;
	}

	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException, SystemException {

		HttpServletRequest request =
			liferayPortletRequest.getHttpServletRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!JournalPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_ARTICLE)) {

			return null;
		}

		long classTypeId = GetterUtil.getLong(
			liferayPortletRequest.getAttribute(
				WebKeys.ASSET_RENDERER_FACTORY_CLASS_TYPE_ID));

		if ((classTypeId > 0) &&
			!JournalStructurePermission.contains(
				themeDisplay.getPermissionChecker(), classTypeId,
				ActionKeys.VIEW)) {

			return null;
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, PortletKeys.JOURNAL, getControlPanelPlid(themeDisplay),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/journal/edit_article");

		return portletURL;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return JournalArticlePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	public boolean isLinkable() {
		return _LINKABLE;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	private static final boolean _LINKABLE = true;

}