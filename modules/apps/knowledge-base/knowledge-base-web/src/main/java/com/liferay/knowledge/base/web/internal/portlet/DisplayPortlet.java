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

package com.liferay.knowledge.base.web.internal.portlet;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.exception.NoSuchCommentException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.knowledge.base.web.internal.KBUtil;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.knowledge.base.web.internal.selector.KBArticleSelection;
import com.liferay.knowledge.base.web.internal.selector.KBArticleSelector;
import com.liferay.knowledge.base.web.internal.selector.KBArticleSelectorFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchSubscriptionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=knowledge-base-portlet knowledge-base-portlet-display",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.header-portlet-css=/admin/css/common.css",
		"com.liferay.portlet.header-portlet-css=/display/css/main.css",
		"com.liferay.portlet.icon=/icons/display.png",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.struts-path=knowledge_base",
		"javax.portlet.display-name=Knowledge Base Display",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.always-send-redirect=true",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/display/",
		"javax.portlet.init-param.view-template=/display/view.jsp",
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=tag"
	},
	service = Portlet.class
)
public class DisplayPortlet extends BaseKBPortlet {

	public void updateRootKBFolderId(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortalException {

		KBFolder kbFolder = null;

		long kbFolderId = ParamUtil.getLong(actionRequest, "rootKBFolderId");

		long kbFolderGroupId = _portal.getScopeGroupId(actionRequest);
		String kbFolderURLTitle = StringPool.BLANK;

		if (kbFolderId != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			kbFolder = kbFolderService.getKBFolder(kbFolderId);

			kbFolderGroupId = kbFolder.getGroupId();
			kbFolderURLTitle = kbFolder.getUrlTitle();
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_portal.getLiferayPortletRequest(actionRequest));

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		String contentRootPrefix = GetterUtil.getString(
			portletPreferences.getValue("contentRootPrefix", null));

		String previousPreferredKBFolderURLTitle =
			KBUtil.getPreferredKBFolderURLTitle(
				portalPreferences, contentRootPrefix);

		KnowledgeBaseUtil.setPreferredKBFolderURLTitle(
			portalPreferences, contentRootPrefix, kbFolderURLTitle);

		String urlTitle = ParamUtil.getString(actionRequest, "urlTitle");

		KBArticle kbArticle = null;

		if (Validator.isNotNull(urlTitle)) {
			kbArticle = _kbArticleLocalService.fetchKBArticleByUrlTitle(
				kbFolderGroupId, kbFolderURLTitle, urlTitle);

			if ((kbArticle == null) &&
				Validator.isNotNull(previousPreferredKBFolderURLTitle)) {

				kbArticle = findClosestMatchingKBArticle(
					kbFolderGroupId, previousPreferredKBFolderURLTitle,
					kbFolderId, urlTitle);
			}
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			KBWebKeys.THEME_DISPLAY);

		if ((kbArticle != null) &&
			!_kbArticleModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), kbArticle,
				KBActionKeys.VIEW)) {

			kbArticle = null;
		}

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			actionRequest, KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
			PortletRequest.RENDER_PHASE);

		redirectURL.setParameter("kbFolderId", String.valueOf(kbFolderId));

		redirectURL.setParameter("kbFolderUrlTitle", kbFolderURLTitle);

		if (kbArticle != null) {
			redirectURL.setParameter("urlTitle", kbArticle.getUrlTitle());
		}

		actionResponse.sendRedirect(redirectURL.toString());
	}

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (actionName.equals("updateRootKBFolderId")) {
			return;
		}

		super.addSuccessMessage(actionRequest, actionResponse);
	}

	@Override
	protected void deleteKBArticle(
			ActionRequest actionRequest, ActionResponse actionResponse,
			long resourcePrimKey)
		throws Exception {

		KBArticle kbArticle = kbArticleService.getLatestKBArticle(
			resourcePrimKey, WorkflowConstants.STATUS_ANY);

		long kbFolderId = kbArticle.getKbFolderId();

		long parentResourcePrimKey = kbArticle.getParentResourcePrimKey();

		super.deleteKBArticle(actionRequest, actionResponse, resourcePrimKey);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			actionRequest, _portal.getPortletId(actionRequest),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		if (parentResourcePrimKey != kbFolderId) {
			liferayPortletURL.setParameter(
				"resourcePrimKey", String.valueOf(parentResourcePrimKey));
		}

		actionResponse.sendRedirect(liferayPortletURL.toString());
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchArticleException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchCommentException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchSubscriptionException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include(templatePath + "error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected void doRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			KBArticleSelection kbArticleSelection = getKBArticle(renderRequest);

			renderRequest.setAttribute(
				KBWebKeys.KNOWLEDGE_BASE_EXACT_MATCH,
				kbArticleSelection.isExactMatch());

			KBArticle kbArticle = kbArticleSelection.getKBArticle();

			if ((kbArticle != null) &&
				(kbArticle.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

				kbArticle = _kbArticleLocalService.fetchLatestKBArticle(
					kbArticle.getResourcePrimKey(),
					WorkflowConstants.STATUS_APPROVED);
			}

			renderRequest.setAttribute(
				KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE, kbArticle);

			renderRequest.setAttribute(
				KBWebKeys.KNOWLEDGE_BASE_SEARCH_KEYWORDS,
				kbArticleSelection.getKeywords());

			renderRequest.setAttribute(
				KBWebKeys.KNOWLEDGE_BASE_STATUS,
				WorkflowConstants.STATUS_APPROVED);

			String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

			if ((mvcPath.equals("") ||
				 mvcPath.equals("/display/view_article.jsp")) &&
				!kbArticleSelection.isExactMatch()) {

				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(renderResponse);

				httpServletResponse.setStatus(404);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				SessionMessages.add(
					renderRequest,
					_portal.getPortletId(renderRequest) +
						SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			}
			else {
				throw new PortletException(e);
			}
		}
	}

	protected KBArticle findClosestMatchingKBArticle(
			long groupId, String oldKBFolderURLTitle, long newKBFolderId,
			String urlTitle)
		throws PortalException {

		KBArticle oldKBArticle =
			_kbArticleLocalService.fetchKBArticleByUrlTitle(
				groupId, oldKBFolderURLTitle, urlTitle);

		KBArticle kbArticle = null;

		while ((kbArticle == null) && (oldKBArticle != null)) {
			kbArticle = _kbArticleLocalService.fetchKBArticleByUrlTitle(
				groupId, newKBFolderId, oldKBArticle.getUrlTitle());

			if (kbArticle == null) {
				oldKBArticle = oldKBArticle.getParentKBArticle();
			}
		}

		if (kbArticle == null) {
			List<KBArticle> kbArticles = _kbArticleLocalService.getKBArticles(
				groupId, newKBFolderId, WorkflowConstants.STATUS_APPROVED, 0, 1,
				new KBArticlePriorityComparator(true));

			if (!kbArticles.isEmpty()) {
				kbArticle = kbArticles.get(0);
			}
		}

		return kbArticle;
	}

	protected KBArticleSelection getKBArticle(RenderRequest renderRequest)
		throws PortalException {

		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

		if (mvcPath.endsWith("/edit_article.jsp") ||
			mvcPath.endsWith("/history.jsp") ||
			mvcPath.endsWith("/print_article.jsp")) {

			long resourcePrimKey = ParamUtil.getLong(
				renderRequest, "resourcePrimKey");

			if (resourcePrimKey == 0) {
				return new KBArticleSelection(null, false);
			}

			KBArticle latestKBArticle =
				_kbArticleLocalService.getLatestKBArticle(
					resourcePrimKey, WorkflowConstants.STATUS_ANY);

			return new KBArticleSelection(latestKBArticle, true);
		}

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		long kbFolderClassNameId = _classNameLocalService.getClassNameId(
			KBFolderConstants.getClassName());

		long parentResourcePrimKey = GetterUtil.getLong(
			portletPreferences.getValue("resourcePrimKey", null));

		long parentResourceClassNameId = GetterUtil.getLong(
			portletPreferences.getValue("resourceClassNameId", null),
			kbFolderClassNameId);

		long kbFolderId = ParamUtil.getLong(renderRequest, "kbFolderId");

		if ((parentResourceClassNameId == kbFolderClassNameId) &&
			(kbFolderId != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			parentResourcePrimKey = kbFolderId;
		}

		KBArticleSelector kbArticleSelector =
			_kbArticleSelectorFactory.getKBArticleSelector(
				parentResourceClassNameId);

		String urlTitle = ParamUtil.getString(renderRequest, "urlTitle");

		String preferredKBFolderURLTitle = getPreferredKBFolderUrlTitle(
			renderRequest, portletPreferences);

		if (Validator.isNotNull(urlTitle)) {
			String kbFolderUrlTitle = ParamUtil.getString(
				renderRequest, "kbFolderUrlTitle");

			return kbArticleSelector.findByUrlTitle(
				_portal.getScopeGroupId(renderRequest),
				preferredKBFolderURLTitle, parentResourcePrimKey,
				kbFolderUrlTitle, urlTitle);
		}

		long resourcePrimKey = ParamUtil.getLong(
			renderRequest, "resourcePrimKey",
			KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY);

		return kbArticleSelector.findByResourcePrimKey(
			_portal.getScopeGroupId(renderRequest), preferredKBFolderURLTitle,
			parentResourcePrimKey, resourcePrimKey);
	}

	protected String getPreferredKBFolderUrlTitle(
			RenderRequest renderRequest, PortletPreferences portletPreferences)
		throws PortalException {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(renderRequest);

		String contentRootPrefix = GetterUtil.getString(
			portletPreferences.getValue("contentRootPrefix", null));

		return KBUtil.getPreferredKBFolderURLTitle(
			portalPreferences, contentRootPrefix);
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.knowledge.base.web)(&(release.schema.version>=1.2.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBArticle)"
	)
	private ModelResourcePermission<KBArticle>
		_kbArticleModelResourcePermission;

	@Reference
	private KBArticleSelectorFactory _kbArticleSelectorFactory;

	@Reference
	private Portal _portal;

}