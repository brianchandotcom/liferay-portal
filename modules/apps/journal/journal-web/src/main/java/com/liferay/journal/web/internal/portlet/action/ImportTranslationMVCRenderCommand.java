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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemWorkflowProvider;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.display.context.ImportTranslationDisplayContext;
import com.liferay.journal.web.internal.display.context.JournalDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.util.TrashWebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/import_translation"
	},
	service = MVCRenderCommand.class
)
public class ImportTranslationMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			InfoItemWorkflowProvider<Object> infoItemWorkflowProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemWorkflowProvider.class,
					JournalArticle.class.getName());

			renderRequest.setAttribute(
				ImportTranslationDisplayContext.class.getName(),
				new ImportTranslationDisplayContext(
					_portal.getHttpServletRequest(renderRequest),
					infoItemWorkflowProvider,
					JournalDisplayContext.create(
						_portal.getHttpServletRequest(renderRequest),
						_portal.getLiferayPortletRequest(renderRequest),
						_portal.getLiferayPortletResponse(renderResponse),
						(AssetDisplayPageFriendlyURLProvider)
							renderRequest.getAttribute(
								AssetDisplayPageFriendlyURLProvider.class.
									getName()),
						(TrashHelper)renderRequest.getAttribute(
							TrashWebKeys.TRASH_HELPER)),
					_portal.getLiferayPortletResponse(renderResponse),
					ActionUtil.getArticle(renderRequest)));

			return "/import_translation.jsp";
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Portal _portal;

}