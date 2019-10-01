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

package com.liferay.content.repository.web.internal.portlet.action;

import com.liferay.content.repository.model.ContentRepositoryEntry;
import com.liferay.content.repository.service.ContentRepositoryEntryLocalService;
import com.liferay.content.repository.web.internal.constants.ContentRepositoryPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentRepositoryPortletKeys.CONTENT_REPOSITORY_ADMIN,
		"mvc.command.name=/content_repository_entry/edit"
	},
	service = MVCRenderCommand.class
)
public class EditContentRepositoryEntryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ContentRepositoryEntry contentRepositoryEntry =
				_contentRepositoryEntryLocalService.getContentRepositoryEntry(
					ParamUtil.getLong(
						renderRequest, "contentRepositoryEntryId"));

			renderRequest.setAttribute(
				"contentRepositoryEntry", contentRepositoryEntry);

			return "/edit_content_repository_entry.jsp";
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}
	}

	@Reference
	private ContentRepositoryEntryLocalService
		_contentRepositoryEntryLocalService;

}