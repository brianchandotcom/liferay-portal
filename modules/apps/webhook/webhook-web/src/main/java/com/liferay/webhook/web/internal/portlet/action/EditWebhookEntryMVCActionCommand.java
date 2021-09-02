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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.webhook.exception.WebhookEntryDestinationNameException;
import com.liferay.webhook.exception.WebhookEntryDestinationWebhookEventKeysException;
import com.liferay.webhook.exception.WebhookEntryNameException;
import com.liferay.webhook.exception.WebhookEntryURLException;
import com.liferay.webhook.model.WebhookEntry;
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
		"mvc.command.name=/webhook/edit_webhook_entry"
	},
	service = MVCActionCommand.class
)
public class EditWebhookEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			boolean active = ParamUtil.getBoolean(actionRequest, "active");
			String destinationName = ParamUtil.getString(
				actionRequest, "destinationName");
			String destinationWebhookEventKeys = ParamUtil.getString(
				actionRequest, "destinationWebhookEventKeys");
			String name = ParamUtil.getString(actionRequest, "name");
			String secret = ParamUtil.getString(actionRequest, "secret");
			String url = ParamUtil.getString(actionRequest, "URL");

			if (cmd.equals(Constants.ADD)) {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(
						WebhookEntry.class.getName(), actionRequest);

				_webhookEntryLocalService.addWebhookEntry(
					serviceContext.getUserId(), active, destinationName,
					destinationWebhookEventKeys, name, secret, url,
					serviceContext);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				long webhookEntryId = ParamUtil.getLong(
					actionRequest, "webhookEntryId");

				_webhookEntryLocalService.updateWebhookEntry(
					webhookEntryId, active, destinationName,
					destinationWebhookEventKeys, name, secret, url);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			if (exception instanceof WebhookEntryDestinationNameException ||
				exception instanceof
					WebhookEntryDestinationWebhookEventKeysException ||
				exception instanceof WebhookEntryNameException ||
				exception instanceof WebhookEntryURLException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	@Reference
	private WebhookEntryLocalService _webhookEntryLocalService;

}