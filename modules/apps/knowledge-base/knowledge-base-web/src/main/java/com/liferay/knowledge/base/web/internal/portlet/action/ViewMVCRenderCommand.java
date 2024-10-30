/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.portlet.action;

import com.liferay.change.tracking.spi.constants.CTTimelineKeys;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SEARCH,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SECTION,
		"mvc.command.name=/", "mvc.command.name=/knowledge_base/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		String rootPortletId = _getRootPortletId(renderRequest);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(renderRequest));

		originalHttpServletRequest.setAttribute(
			CTTimelineKeys.CLASS_NAME, KBArticle.class.getName());

		if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {
			return "/admin/view.jsp";
		}

		if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE)) {
			KBArticle kbArticle = (KBArticle)renderRequest.getAttribute(
				KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

			originalHttpServletRequest.setAttribute(
				CTTimelineKeys.CLASS_PK, kbArticle.getKbArticleId());

			return "/article/view.jsp";
		}

		if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) {
			KBArticle kbArticle = (KBArticle)renderRequest.getAttribute(
				KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

			originalHttpServletRequest.setAttribute(
				CTTimelineKeys.CLASS_PK, kbArticle.getKbArticleId());

			return "/display/view.jsp";
		}

		if (rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_SEARCH)) {
			return _getSearchJSP(renderRequest);
		}

		return "/section/view.jsp";
	}

	private String _getRootPortletId(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getRootPortletId();
	}

	private String _getSearchJSP(PortletRequest portletRequest) {
		long assetCategoryId = ParamUtil.getLong(portletRequest, "categoryId");
		String assetTagName = ParamUtil.getString(portletRequest, "tag");

		if ((assetCategoryId > 0) || Validator.isNotNull(assetTagName)) {
			return "/search/view_prp_kb_articles.jsp";
		}

		return "/search/view.jsp";
	}

	@Reference
	private Portal _portal;

}