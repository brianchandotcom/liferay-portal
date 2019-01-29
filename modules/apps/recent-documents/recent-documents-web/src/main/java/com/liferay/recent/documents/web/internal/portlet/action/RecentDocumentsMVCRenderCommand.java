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

package com.liferay.recent.documents.web.internal.portlet.action;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.recent.documents.web.internal.constants.RecentDocumentsPortletKeys;
import com.liferay.recent.documents.web.internal.constants.RecentDocumentsWebKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RecentDocumentsPortletKeys.RECENT_DOCUMENTS,
		"mvc.command.name=/recent_documents/view"
	},
	service = MVCRenderCommand.class
)
public class RecentDocumentsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute(
			RecentDocumentsWebKeys.DL_URL_HELPER, _dlurlHelper);

		return "/view.jsp";
	}

	@Reference
	private DLURLHelper _dlurlHelper;

}