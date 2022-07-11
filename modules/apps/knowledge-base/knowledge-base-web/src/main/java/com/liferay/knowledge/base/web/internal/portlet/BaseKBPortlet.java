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

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.knowledge.base.exception.KBArticleContentException;
import com.liferay.knowledge.base.exception.KBArticlePriorityException;
import com.liferay.knowledge.base.exception.KBArticleTitleException;
import com.liferay.knowledge.base.exception.KBCommentContentException;
import com.liferay.knowledge.base.exception.NoSuchArticleException;
import com.liferay.knowledge.base.exception.NoSuchCommentException;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBCommentLocalService;
import com.liferay.knowledge.base.service.KBCommentService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.knowledge.base.service.KBTemplateService;
import com.liferay.knowledge.base.util.AdminHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseKBPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String cmd = ParamUtil.getString(renderRequest, Constants.CMD);

		if (Validator.isNotNull(cmd) && cmd.equals("compareVersions")) {
			_compareVersions(renderRequest);
		}

		doRender(renderRequest, renderResponse);

		super.render(renderRequest, renderResponse);
	}

	public void serveKBArticleRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletPreferences portletPreferences =
			resourceRequest.getPreferences();

		boolean enableRss = GetterUtil.getBoolean(
			portletPreferences.getValue("enableRss", null), true);

		if (!portal.isRSSFeedsEnabled() || !enableRss) {
			portal.sendRSSFeedsDisabledError(resourceRequest, resourceResponse);

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long resourcePrimKey = ParamUtil.getLong(
			resourceRequest, "resourcePrimKey");

		int rssDelta = ParamUtil.getInteger(resourceRequest, "rssDelta");
		String rssDisplayStyle = ParamUtil.getString(
			resourceRequest, "rssDisplayStyle");
		String rssFormat = ParamUtil.getString(resourceRequest, "rssFormat");

		String rss = kbArticleService.getKBArticleRSS(
			resourcePrimKey, WorkflowConstants.STATUS_APPROVED, rssDelta,
			rssDisplayStyle, rssFormat, themeDisplay);

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, null,
			rss.getBytes(StringPool.UTF8), ContentTypes.TEXT_XML_UTF8);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("compareVersions")) {
				try {
					long resourcePrimKey = ParamUtil.getLong(
						resourceRequest, "resourcePrimKey");
					double sourceVersion = ParamUtil.getDouble(
						resourceRequest, "filterSourceVersion");
					double targetVersion = ParamUtil.getDouble(
						resourceRequest, "filterTargetVersion");

					String diffHtmlResults = adminHelper.getKBArticleDiff(
						resourcePrimKey, GetterUtil.getInteger(sourceVersion),
						GetterUtil.getInteger(targetVersion), "content");

					resourceRequest.setAttribute(
						WebKeys.DIFF_HTML_RESULTS, diffHtmlResults);

					PortletContext portletContext =
						resourceRequest.getPortletContext();

					PortletRequestDispatcher portletRequestDispatcher =
						portletContext.getRequestDispatcher(
							"/admin/common/compare_versions_diff_html.jsp");

					portletRequestDispatcher.include(
						resourceRequest, resourceResponse);
				}
				catch (Exception exception) {
					PortalUtil.sendError(
						exception,
						PortalUtil.getHttpServletRequest(resourceRequest),
						PortalUtil.getHttpServletResponse(resourceResponse));
				}
			}
			else if (resourceID.equals("kbArticleRSS")) {
				serveKBArticleRSS(resourceRequest, resourceResponse);
			}
		}
		catch (IOException | PortletException exception) {
			throw exception;
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	protected abstract void doRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException;

	@Override
	protected boolean isSessionErrorException(Throwable throwable) {
		if (throwable instanceof AssetCategoryException ||
			throwable instanceof AssetTagException ||
			throwable instanceof FileNameException ||
			throwable instanceof FileSizeException ||
			throwable instanceof KBArticleContentException ||
			throwable instanceof KBArticlePriorityException ||
			throwable instanceof KBArticleTitleException ||
			throwable instanceof KBCommentContentException ||
			throwable instanceof NoSuchArticleException ||
			throwable instanceof NoSuchCommentException ||
			throwable instanceof NoSuchFileException ||
			throwable instanceof PrincipalException ||
			super.isSessionErrorException(throwable)) {

			return true;
		}

		return false;
	}

	@Reference
	protected AdminHelper adminHelper;

	@Reference
	protected KBArticleService kbArticleService;

	@Reference
	protected KBCommentLocalService kbCommentLocalService;

	@Reference
	protected KBCommentService kbCommentService;

	@Reference
	protected KBFolderService kbFolderService;

	@Reference
	protected KBTemplateService kbTemplateService;

	@Reference
	protected Portal portal;

	private void _compareVersions(RenderRequest renderRequest)
		throws PortletException {

		long resourcePrimKey = ParamUtil.getLong(
			renderRequest, "resourcePrimKey");
		double sourceVersion = ParamUtil.getDouble(
			renderRequest, "sourceVersion");
		double targetVersion = ParamUtil.getDouble(
			renderRequest, "targetVersion");

		String diffHtmlResults = null;

		try {
			diffHtmlResults = adminHelper.getKBArticleDiff(
				resourcePrimKey, GetterUtil.getInteger(sourceVersion),
				GetterUtil.getInteger(targetVersion), "content");
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		renderRequest.setAttribute(WebKeys.DIFF_HTML_RESULTS, diffHtmlResults);
	}

}