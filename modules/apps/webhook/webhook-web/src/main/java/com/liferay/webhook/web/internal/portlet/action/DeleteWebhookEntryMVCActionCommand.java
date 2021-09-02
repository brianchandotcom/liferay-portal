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

package com.liferay.webhook.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.webhook.service.WebhookEntryLocalService;
import com.liferay.webhook.web.internal.constants.WebhookPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WebhookPortletKeys.WEBHOOK,
		"mvc.command.name=/webhook/delete_webhook_entry"
	},
	service = MVCActionCommand.class
)
public class DeleteWebhookEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long webhookEntryId = ParamUtil.getLong(
			actionRequest, "webhookEntryId");

		_webhookEntryLocalService.deleteWebhookEntry(webhookEntryId);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	@Reference
	private WebhookEntryLocalService _webhookEntryLocalService;

}