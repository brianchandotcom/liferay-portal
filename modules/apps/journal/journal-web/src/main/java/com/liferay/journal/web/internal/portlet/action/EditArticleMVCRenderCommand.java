/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.web.internal.display.context.JournalDisplayContext;
import com.liferay.journal.web.internal.display.context.JournalEditArticleDisplayContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.trash.TrashHelper;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/edit_article"
	},
	service = MVCRenderCommand.class
)
public class EditArticleMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(renderResponse);

		JournalDisplayContext journalDisplayContext =
			JournalDisplayContext.create(
				httpServletRequest,
				_portal.getLiferayPortletRequest(renderRequest),
				liferayPortletResponse, _assetDisplayPageFriendlyURLProvider,
				_trashHelper);

		try {
			renderRequest.setAttribute(
				JournalEditArticleDisplayContext.class.getName(),
				new JournalEditArticleDisplayContext(
					httpServletRequest, liferayPortletResponse,
					journalDisplayContext.getArticle()));

			return "/edit_article.jsp";
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
			else {
				_log.error(exception);
			}

			SessionErrors.add(renderRequest, exception.getClass());
		}

		return "/error.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditArticleMVCRenderCommand.class);

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

}