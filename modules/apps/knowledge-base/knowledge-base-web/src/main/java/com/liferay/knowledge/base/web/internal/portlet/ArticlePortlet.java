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
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.exception.NoSuchCommentException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.portal.kernel.exception.NoSuchSubscriptionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=knowledge-base-portlet knowledge-base-portlet-article",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.header-portlet-css=/admin/css/common.css",
		"com.liferay.portlet.header-portlet-css=/article/css/main.css",
		"com.liferay.portlet.icon=/icons/article.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.struts-path=knowledge_base",
		"javax.portlet.display-name=Knowledge Base Article",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.always-send-redirect=true",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/article/",
		"javax.portlet.init-param.view-template=/article/view.jsp",
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=tag"
	},
	service = Portlet.class
)
public class ArticlePortlet extends BaseKBPortlet {

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (actionName.equals("deleteKBArticle")) {
			return;
		}

		super.addSuccessMessage(actionRequest, actionResponse);
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
			KBArticle kbArticle = null;

			long resourcePrimKey = getResourcePrimKey(renderRequest);

			int status = ParamUtil.getInteger(
				renderRequest, "status", WorkflowConstants.STATUS_APPROVED);

			if (resourcePrimKey > 0) {
				kbArticle = kbArticleService.getLatestKBArticle(
					resourcePrimKey, status);
			}

			renderRequest.setAttribute(
				KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE, kbArticle);

			renderRequest.setAttribute(KBWebKeys.KNOWLEDGE_BASE_STATUS, status);
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				SessionMessages.add(
					renderRequest,
					portal.getPortletId(renderRequest) +
						SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			}
			else {
				throw new PortletException(e);
			}
		}
	}

	protected long getResourcePrimKey(RenderRequest renderRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			KBWebKeys.THEME_DISPLAY);

		PortletPreferences preferences = renderRequest.getPreferences();

		long defaultValue = GetterUtil.getLong(
			preferences.getValue("resourcePrimKey", null));

		KBArticle defaultKBArticle = kbArticleService.fetchLatestKBArticle(
			defaultValue, WorkflowConstants.STATUS_ANY);

		if (defaultKBArticle == null) {
			defaultValue = 0;
		}

		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

		if ((defaultValue == 0) && mvcPath.equals(viewTemplate)) {
			return 0;
		}

		long resourcePrimKey = getResourcePrimKeyFromUrlTitle(renderRequest);

		if (resourcePrimKey == 0) {
			resourcePrimKey = ParamUtil.getLong(
				renderRequest, "resourcePrimKey", defaultValue);
		}

		if ((resourcePrimKey == 0) || (resourcePrimKey != defaultValue)) {
			return resourcePrimKey;
		}

		if (!_kbArticleModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), defaultValue,
				KBActionKeys.VIEW)) {

			return 0;
		}

		return defaultValue;
	}

	protected long getResourcePrimKeyFromUrlTitle(RenderRequest renderRequest)
		throws PortalException {

		String urlTitle = ParamUtil.getString(renderRequest, "urlTitle");

		if (Validator.isNull(urlTitle)) {
			return 0;
		}

		String kbFolderUrlTitle = ParamUtil.getString(
			renderRequest, "kbFolderUrlTitle");

		KBArticle kbArticle = null;

		if (Validator.isNull(kbFolderUrlTitle)) {
			kbArticle = _kbArticleLocalService.fetchKBArticleByUrlTitle(
				_portal.getScopeGroupId(renderRequest),
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, urlTitle);
		}
		else {
			kbArticle = _kbArticleLocalService.fetchKBArticleByUrlTitle(
				_portal.getScopeGroupId(renderRequest), kbFolderUrlTitle,
				urlTitle);
		}

		if (kbArticle != null) {
			return kbArticle.getResourcePrimKey();
		}

		return 0;
	}

	@Reference(unbind = "-")
	protected void setKBArticleLocalService(
		KBArticleLocalService kbArticleLocalService) {

		_kbArticleLocalService = kbArticleLocalService;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.knowledge.base.web)(&(release.schema.version>=1.2.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private KBArticleLocalService _kbArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBArticle)"
	)
	private ModelResourcePermission<KBArticle>
		_kbArticleModelResourcePermission;

	@Reference
	private Portal _portal;

}