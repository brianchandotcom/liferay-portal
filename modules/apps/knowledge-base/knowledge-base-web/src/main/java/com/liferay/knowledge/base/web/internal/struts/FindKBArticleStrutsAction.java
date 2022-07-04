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

package com.liferay.knowledge.base.web.internal.struts;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.knowledge.base.util.AdminHelper;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBArticlePermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Shin
 */
@Component(
	immediate = true, property = "path=/knowledge_base/find_kb_article",
	service = StrutsAction.class
)
public class FindKBArticleStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String backURL = ParamUtil.getString(
			httpServletRequest, "p_l_back_url");
		long plid = ParamUtil.getLong(httpServletRequest, "plid");
		long resourcePrimKey = ParamUtil.getLong(
			httpServletRequest, "resourcePrimKey");
		int status = ParamUtil.getInteger(
			httpServletRequest, "status", WorkflowConstants.STATUS_APPROVED);
		boolean maximized = ParamUtil.getBoolean(
			httpServletRequest, "maximized");

		KBArticle kbArticle = _getKBArticle(resourcePrimKey, status);

		if (!_isValidPlid(plid)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			plid = themeDisplay.getPlid();
		}

		PortletURL portletURL = null;

		if ((kbArticle == null) ||
			(status != WorkflowConstants.STATUS_APPROVED)) {

			portletURL = _getDynamicPortletURL(
				plid, status, httpServletRequest);
		}

		if (portletURL == null) {
			portletURL = _getKBArticleURL(
				plid, false, kbArticle, httpServletRequest);
		}

		if (portletURL == null) {
			portletURL = _getKBArticleURL(
				plid, true, kbArticle, httpServletRequest);
		}

		if (portletURL == null) {
			portletURL = _getDynamicPortletURL(
				plid, status, httpServletRequest);
		}

		if (Validator.isNotNull(backURL)) {
			portletURL.setParameter("backURL", backURL);
		}

		if (maximized) {
			portletURL.setPortletMode(PortletMode.VIEW);
			portletURL.setWindowState(LiferayWindowState.MAXIMIZED);
		}

		httpServletResponse.sendRedirect(portletURL.toString());

		return null;
	}

	private List<Layout> _getCandidateLayouts(
			long plid, boolean privateLayout, KBArticle kbArticle)
		throws Exception {

		List<Layout> candidateLayouts = new ArrayList<>();

		Group group = _groupLocalService.getGroup(kbArticle.getGroupId());

		if (group.isLayout()) {
			Layout layout = _layoutLocalService.getLayout(group.getClassPK());

			candidateLayouts.add(layout);

			group = layout.getGroup();
		}

		candidateLayouts.addAll(
			_layoutLocalService.getLayouts(
				group.getGroupId(), privateLayout,
				LayoutConstants.TYPE_PORTLET));

		Layout layout = _layoutLocalService.getLayout(plid);

		if ((layout.getGroupId() == kbArticle.getGroupId()) &&
			layout.isTypePortlet()) {

			candidateLayouts.remove(layout);
			candidateLayouts.add(0, layout);
		}

		return candidateLayouts;
	}

	private PortletURL _getDynamicPortletURL(
			long plid, int status, HttpServletRequest httpServletRequest)
		throws Exception {

		String portletId = _getPortletId(plid);

		PortletURL portletURL = _getKBArticleURL(
			plid, portletId, null, httpServletRequest);

		if (status != WorkflowConstants.STATUS_APPROVED) {
			portletURL.setParameter("status", String.valueOf(status));
		}

		if (_PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED) {
			portletURL.setParameter(
				"p_p_auth",
				AuthTokenUtil.getToken(httpServletRequest, plid, portletId));
		}

		portletURL.setPortletMode(PortletMode.VIEW);

		if (Objects.equals(
				portletId,
				KBPortletKeys.KNOWLEDGE_BASE_ARTICLE_DEFAULT_INSTANCE)) {

			portletURL.setWindowState(LiferayWindowState.MAXIMIZED);
		}

		return portletURL;
	}

	private KBArticle _getKBArticle(long resourcePrimKey, int status)
		throws Exception {

		KBArticle kbArticle = _kbArticleLocalService.fetchLatestKBArticle(
			resourcePrimKey, status);

		if ((kbArticle == null) ||
			!KBArticlePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), kbArticle,
				KBActionKeys.VIEW)) {

			return null;
		}

		return kbArticle;
	}

	private PortletURL _getKBArticleURL(
			long plid, boolean privateLayout, KBArticle kbArticle,
			HttpServletRequest httpServletRequest)
		throws Exception {

		PortletURL firstMatchPortletURL = null;

		List<Layout> layouts = _getCandidateLayouts(
			plid, privateLayout, kbArticle);

		for (Layout layout : layouts) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

			for (Portlet portlet : portlets) {
				String rootPortletId = PortletIdCodec.decodePortletName(
					portlet.getPortletId());

				if (rootPortletId.equals(
						KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) {

					PortletPreferences portletPreferences =
						PortletPreferencesFactoryUtil.getPortletSetup(
							layout, portlet.getPortletId(), StringPool.BLANK);

					long kbFolderClassNameId = _portal.getClassNameId(
						KBFolderConstants.getClassName());

					long resourceClassNameId = GetterUtil.getLong(
						portletPreferences.getValue(
							"resourceClassNameId", null),
						kbFolderClassNameId);

					long resourcePrimKey = GetterUtil.getLong(
						portletPreferences.getValue("resourcePrimKey", null),
						KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

					if (resourceClassNameId == kbFolderClassNameId) {
						if (_isParentFolder(
								resourcePrimKey, kbArticle.getKbFolderId())) {

							return _getKBArticleURL(
								layout.getPlid(), portlet.getPortletId(),
								kbArticle, httpServletRequest);
						}
					}
					else if (resourcePrimKey ==
								kbArticle.getResourcePrimKey()) {

						return _getKBArticleURL(
							layout.getPlid(), portlet.getPortletId(), kbArticle,
							httpServletRequest);
					}

					if (firstMatchPortletURL == null) {
						firstMatchPortletURL = _getKBArticleURL(
							layout.getPlid(), portlet.getPortletId(), kbArticle,
							httpServletRequest);
					}
				}

				if (rootPortletId.equals(
						KBPortletKeys.KNOWLEDGE_BASE_SECTION)) {

					KBArticle rootKBArticle =
						_kbArticleLocalService.fetchLatestKBArticle(
							kbArticle.getRootResourcePrimKey(),
							WorkflowConstants.STATUS_APPROVED);

					if (rootKBArticle == null) {
						continue;
					}

					PortletPreferences portletPreferences =
						PortletPreferencesFactoryUtil.getPortletSetup(
							layout, portlet.getPortletId(), StringPool.BLANK);

					String[] kbArticlesSections = portletPreferences.getValues(
						"kbArticlesSections", new String[0]);

					String[] sections = _adminHelper.unescapeSections(
						rootKBArticle.getSections());

					for (String section : sections) {
						if (!ArrayUtil.contains(kbArticlesSections, section)) {
							continue;
						}

						return _getKBArticleURL(
							layout.getPlid(), portlet.getPortletId(), kbArticle,
							httpServletRequest);
					}
				}

				if (!rootPortletId.equals(
						KBPortletKeys.KNOWLEDGE_BASE_ARTICLE)) {

					continue;
				}

				PortletPreferences portletPreferences =
					PortletPreferencesFactoryUtil.getPortletSetup(
						layout, portlet.getPortletId(), StringPool.BLANK);

				long resourcePrimKey = GetterUtil.getLong(
					portletPreferences.getValue("resourcePrimKey", null));

				KBArticle selKBArticle =
					_kbArticleLocalService.fetchLatestKBArticle(
						resourcePrimKey, WorkflowConstants.STATUS_APPROVED);

				if (selKBArticle == null) {
					continue;
				}

				long rootResourcePrimKey = kbArticle.getRootResourcePrimKey();
				long selRootResourcePrimKey =
					selKBArticle.getRootResourcePrimKey();

				if (rootResourcePrimKey == selRootResourcePrimKey) {
					return _getKBArticleURL(
						layout.getPlid(), portlet.getPortletId(), kbArticle,
						httpServletRequest);
				}

				if (firstMatchPortletURL == null) {
					firstMatchPortletURL = _getKBArticleURL(
						layout.getPlid(), portlet.getPortletId(), kbArticle,
						httpServletRequest);
				}
			}
		}

		return firstMatchPortletURL;
	}

	private PortletURL _getKBArticleURL(
			long plid, String portletId, KBArticle kbArticle,
			HttpServletRequest httpServletRequest)
		throws Exception {

		String mvcPath = null;

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {
			mvcPath = "/admin/view_article.jsp";
		}
		else if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE)) {
			mvcPath = "/article/view_article.jsp";
		}
		else if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_SECTION)) {
			mvcPath = "/section/view_article.jsp";
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, portletId, plid, PortletRequest.RENDER_PHASE);

		if (mvcPath != null) {
			portletURL.setParameter("mvcPath", mvcPath);
		}

		if ((kbArticle == null) || Validator.isNull(kbArticle.getUrlTitle())) {
			long resourcePrimKey = ParamUtil.getLong(
				httpServletRequest, "resourcePrimKey");

			portletURL.setParameter(
				"resourcePrimKey", String.valueOf(resourcePrimKey));
		}
		else {
			portletURL.setParameter("urlTitle", kbArticle.getUrlTitle());

			if (kbArticle.getKbFolderId() !=
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				KBFolder kbFolder = _kbFolderLocalService.getKBFolder(
					kbArticle.getKbFolderId());

				portletURL.setParameter(
					"kbFolderUrlTitle", String.valueOf(kbFolder.getUrlTitle()));
			}
		}

		portletURL.setPortletMode(PortletMode.VIEW);

		portletURL.setWindowState(LiferayWindowState.NORMAL);

		if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_SECTION)) {
			portletURL.setWindowState(LiferayWindowState.MAXIMIZED);
		}

		return portletURL;
	}

	private String _getPortletId(long plid) throws Exception {
		Layout layout = _layoutLocalService.getLayout(plid);

		long selPlid = _portal.getPlidFromPortletId(
			layout.getGroupId(), KBPortletKeys.KNOWLEDGE_BASE_DISPLAY);

		if (selPlid != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			return KBPortletKeys.KNOWLEDGE_BASE_DISPLAY;
		}

		if (layout.isTypeControlPanel()) {
			return KBPortletKeys.KNOWLEDGE_BASE_ADMIN;
		}

		return KBPortletKeys.KNOWLEDGE_BASE_ARTICLE_DEFAULT_INSTANCE;
	}

	private boolean _isParentFolder(long resourcePrimKey, long kbFolderId)
		throws Exception {

		if (resourcePrimKey == kbFolderId) {
			return true;
		}

		while (kbFolderId != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			if (resourcePrimKey == kbFolderId) {
				return true;
			}

			KBFolder kbFolder = _kbFolderLocalService.getKBFolder(kbFolderId);

			kbFolderId = kbFolder.getParentKBFolderId();
		}

		return false;
	}

	private boolean _isValidPlid(long plid) throws Exception {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return false;
		}

		return true;
	}

	private static final boolean _PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED));

	@Reference
	private AdminHelper _adminHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference
	private KBFolderLocalService _kbFolderLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}