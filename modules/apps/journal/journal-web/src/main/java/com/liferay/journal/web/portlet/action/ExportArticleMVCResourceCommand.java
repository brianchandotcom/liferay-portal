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

package com.liferay.journal.web.portlet.action;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.web.util.ExportArticleUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=exportArticle"
	},
	service = MVCResourceCommand.class
)
public class ExportArticleMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			_exportArticleUtil.sendFile(resourceRequest, resourceResponse);
		}
		catch (Exception e) {
			PortalUtil.sendError(
				e, (ActionRequest)resourceRequest,
				(ActionResponse)resourceResponse);
		}
	}

	@Reference(unbind = "-")
	protected void setExportArticleUtil(ExportArticleUtil exportArticleUtil) {
		_exportArticleUtil = exportArticleUtil;
	}

	private ExportArticleUtil _exportArticleUtil;

}