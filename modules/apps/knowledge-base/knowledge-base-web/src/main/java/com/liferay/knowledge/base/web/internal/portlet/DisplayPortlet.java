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

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.exception.NoSuchCommentException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.web.internal.KBUtil;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.knowledge.base.web.internal.selector.KBArticleSelection;
import com.liferay.knowledge.base.web.internal.selector.KBArticleSelector;
import com.liferay.knowledge.base.web.internal.selector.KBArticleSelectorFactory;
import com.liferay.portal.kernel.exception.NoSuchSubscriptionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
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
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/knowledge_base/view",
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class DisplayPortlet extends BaseKBPortlet {

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

			include("/admin/common/error.jsp", renderRequest, renderResponse);
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
			KBArticleSelection kbArticleSelection = _getKBArticle(
				renderRequest);

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
			String mvcRenderCommandName = ParamUtil.getString(
				renderRequest, "mvcRenderCommandName");

			if ((mvcPath.equals("") ||
				 mvcRenderCommandName.equals(
					 "/knowledge_base/view_kb_article")) &&
				!kbArticleSelection.isExactMatch()) {

				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(renderResponse);

				httpServletResponse.setStatus(404);
			}
		}
		catch (NoSuchArticleException | PrincipalException exception) {
			SessionErrors.add(renderRequest, exception.getClass());

			SessionMessages.add(
				renderRequest,
				_portal.getPortletId(renderRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
	}

	private KBArticleSelection _getKBArticle(RenderRequest renderRequest)
		throws PortalException {

		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

		if (mvcPath.endsWith("/edit_kb_article.jsp") ||
			mvcPath.endsWith("/kb_history.jsp") ||
			mvcPath.endsWith("/print_kb_article.jsp")) {

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

		String preferredKBFolderURLTitle = _getPreferredKBFolderUrlTitle(
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

	private String _getPreferredKBFolderUrlTitle(
			RenderRequest renderRequest, PortletPreferences portletPreferences)
		throws PortalException {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(renderRequest);

		String contentRootPrefix = GetterUtil.getString(
			portletPreferences.getValue("contentRootPrefix", null));

		return KBUtil.getPreferredKBFolderURLTitle(
			portalPreferences, contentRootPrefix);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference
	private KBArticleSelectorFactory _kbArticleSelectorFactory;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.knowledge.base.web)(&(release.schema.version>=1.2.0)(!(release.schema.version>=2.0.0))))"
	)
	private Release _release;

}