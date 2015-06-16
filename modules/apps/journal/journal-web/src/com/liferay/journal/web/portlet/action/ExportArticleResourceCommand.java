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

import com.liferay.journal.web.constants.JournalPortletKeys;
import com.liferay.journal.web.util.ExportArticleUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.ResourceCommand;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Farache
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"command.name=exportArticle",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL
	},
	service = ResourceCommand.class
)
public class ExportArticleResourceCommand extends BaseResourceCommand {

	@Override
	protected void doProcessCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ExportArticleUtil.sendFile(resourceRequest, resourceResponse);
	}

}