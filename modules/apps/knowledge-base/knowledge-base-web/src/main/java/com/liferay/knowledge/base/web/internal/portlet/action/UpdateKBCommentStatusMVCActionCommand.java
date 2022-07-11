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

package com.liferay.knowledge.base.web.internal.portlet.action;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.service.KBCommentService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ARTICLE,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SEARCH,
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_SECTION,
		"mvc.command.name=/knowledge_base/update_kb_comment_status"
	},
	service = MVCActionCommand.class
)
public class UpdateKBCommentStatusMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long kbCommentId = ParamUtil.getLong(actionRequest, "kbCommentId");

		int status = ParamUtil.getInteger(actionRequest, "kbCommentStatus");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			KBComment.class.getName(), actionRequest);

		_kbCommentService.updateStatus(kbCommentId, status, serviceContext);

		SessionMessages.add(actionRequest, "suggestionStatusUpdated");
	}

	@Reference
	private KBCommentService _kbCommentService;

}