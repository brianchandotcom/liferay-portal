/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.portlet.action;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Galluzzi
 */
@Component(
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SEARCH,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SECTION,
		"mvc.command.name=/knowledge_base/edit_kb_article"
	},
	service = MVCRenderCommand.class
)
public class EditKBArticleMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		KBArticle kbArticle = (KBArticle)renderRequest.getAttribute(
			KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

		if (kbArticle != null) {
			try {
				_kbArticleService.lockKBArticle(kbArticle.getResourcePrimKey());
			}
			catch (PortalException portalException) {
				SessionErrors.add(
					_portal.getOriginalServletRequest(
						_portal.getHttpServletRequest(renderRequest)),
					portalException.getClass());

				_sendRedirect(renderRequest, renderResponse);

				return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
			}
		}

		return "/admin/common/edit_kb_article.jsp";
	}

	private void _sendRedirect(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			httpServletResponse.sendRedirect(
				ParamUtil.getString(renderRequest, "redirect"));
		}
		catch (IOException ioException) {
			throw new PortletException(ioException);
		}
	}

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private Portal _portal;

}