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

package com.liferay.knowledge.base.web.portlet;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.exception.NoSuchCommentException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBCommentLocalService;
import com.liferay.knowledge.base.service.KBCommentService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.knowledge.base.service.KBTemplateService;
import com.liferay.knowledge.base.service.permission.KBArticlePermission;
import com.liferay.knowledge.base.web.constants.KBWebKeys;
import com.liferay.portal.kernel.exception.NoSuchSubscriptionException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
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
		"com.liferay.portlet.css-class-wrapper=knowledge-base-portlet knowledge-base-portlet-search",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.header-portlet-css=/admin/css/common.css",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Knowledge Base Search",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.always-send-redirect=true",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/search/",
		"javax.portlet.init-param.view-template=/search/view.jsp",
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SEARCH,
		"javax.portlet.preferences=classpath:/META-INF/portlet-preferences/default-search-portlet-preferences.xml",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SearchPortlet extends BaseKBPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			int status = getStatus(renderRequest);

			renderRequest.setAttribute(KBWebKeys.KNOWLEDGE_BASE_STATUS, status);

			KBArticle kbArticle = null;

			long resourcePrimKey = ParamUtil.getLong(
				renderRequest, "resourcePrimKey");

			if (resourcePrimKey > 0) {
				kbArticle = _kbArticleService.getLatestKBArticle(
					resourcePrimKey, status);
			}

			renderRequest.setAttribute(
				KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE, kbArticle);
		}
		catch (Exception e) {
			if (e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());
			}
			else {
				throw new PortletException(e);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String mvcPath = ParamUtil.getString(
			renderRequest, "mvcPath", viewTemplate);

		long assetCategoryId = ParamUtil.getLong(renderRequest, "categoryId");
		String assetTagName = ParamUtil.getString(renderRequest, "tag");

		if ((mvcPath.equals(viewTemplate) && (assetCategoryId > 0)) ||
			(mvcPath.equals(viewTemplate) &&
			 Validator.isNotNull(assetTagName))) {

			String path = templatePath + "view_prp_articles.jsp";

			include(path, renderRequest, renderResponse);
		}
		else if (SessionErrors.contains(
					renderRequest, NoSuchArticleException.class.getName()) ||
				 SessionErrors.contains(
					 renderRequest, NoSuchCommentException.class.getName()) ||
				 SessionErrors.contains(
					 renderRequest,
					 NoSuchSubscriptionException.class.getName()) ||
				 SessionErrors.contains(
					 renderRequest, PrincipalException.getNestedClasses())) {

			include(templatePath + "error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected JSONFactory getJSONFactory() {
		return _jsonFactory;
	}

	@Override
	protected KBArticleService getKBArticleService() {
		return _kbArticleService;
	}

	@Override
	protected KBCommentLocalService getKBCommentLocalService() {
		return _kbCommentLocalService;
	}

	@Override
	protected KBCommentService getKBCommentService() {
		return _kbCommentService;
	}

	@Override
	protected KBFolderService getKBFolderService() {
		return _kbFolderService;
	}

	protected KBTemplateService getKBTemplateService() {
		return _kbTemplateService;
	}

	@Override
	protected Portal getPortal() {
		return _portal;
	}

	protected int getStatus(RenderRequest renderRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			KBWebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		String value = renderRequest.getParameter("status");
		int status = GetterUtil.getInteger(value);

		if ((value != null) && (status == WorkflowConstants.STATUS_APPROVED)) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		long resourcePrimKey = ParamUtil.getLong(
			renderRequest, "resourcePrimKey");

		if (resourcePrimKey == 0) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (KBArticlePermission.contains(
				permissionChecker, resourcePrimKey, KBActionKeys.UPDATE)) {

			return ParamUtil.getInteger(
				renderRequest, "status", WorkflowConstants.STATUS_ANY);
		}

		return WorkflowConstants.STATUS_APPROVED;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setKBArticleService(KBArticleService kbArticleService) {
		_kbArticleService = kbArticleService;
	}

	@Reference(unbind = "-")
	protected void setKBCommentLocalService(
		KBCommentLocalService kbCommentLocalService) {

		_kbCommentLocalService = kbCommentLocalService;
	}

	@Reference(unbind = "-")
	protected void setKBCommentService(KBCommentService kbCommentService) {
		_kbCommentService = kbCommentService;
	}

	@Reference(unbind = "-")
	protected void setKBFolderService(KBFolderService kbFolderService) {
		_kbFolderService = kbFolderService;
	}

	@Reference(unbind = "-")
	protected void setKBTemplateService(KBTemplateService kbTemplateService) {
		_kbTemplateService = kbTemplateService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	private JSONFactory _jsonFactory;
	private KBArticleService _kbArticleService;
	private KBCommentLocalService _kbCommentLocalService;
	private KBCommentService _kbCommentService;
	private KBFolderService _kbFolderService;
	private KBTemplateService _kbTemplateService;
	private Portal _portal;

}