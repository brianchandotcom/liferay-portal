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

package com.liferay.commerce.notification.web.internal.portlet.action;

import com.liferay.commerce.notification.exception.CommerceNotificationTemplateFromException;
import com.liferay.commerce.notification.exception.CommerceNotificationTemplateNameException;
import com.liferay.commerce.notification.exception.CommerceNotificationTemplateTypeException;
import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateCommerceAccountGroupRelService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_CHANNELS,
		"mvc.command.name=/commerce_channels/edit_commerce_notification_template"
	},
	service = MVCActionCommand.class
)
public class EditCommerceNotificationTemplateMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				_deleteCommerceNotificationTemplates(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				_updateCommerceNotificationTemplate(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchNotificationTemplateException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof
						CommerceNotificationTemplateFromException ||
					 exception instanceof
						 CommerceNotificationTemplateNameException ||
					 exception instanceof
						 CommerceNotificationTemplateTypeException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/commerce_channels/edit_commerce_notification_template");
			}
			else {
				throw exception;
			}
		}
	}

	private void _deleteCommerceNotificationTemplates(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceNotificationTemplateIds = null;

		long commerceNotificationTemplateId = ParamUtil.getLong(
			actionRequest, "commerceNotificationTemplateId");

		if (commerceNotificationTemplateId > 0) {
			deleteCommerceNotificationTemplateIds = new long[] {
				commerceNotificationTemplateId
			};
		}
		else {
			deleteCommerceNotificationTemplateIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceNotificationTemplateIds"),
				0L);
		}

		for (long deleteCommerceNotificationTemplateId :
				deleteCommerceNotificationTemplateIds) {

			_commerceNotificationTemplateService.
				deleteCommerceNotificationTemplate(
					deleteCommerceNotificationTemplateId);
		}
	}

	private CommerceNotificationTemplate _updateCommerceNotificationTemplate(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceNotificationTemplateId = ParamUtil.getLong(
			actionRequest, "commerceNotificationTemplateId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String to = ParamUtil.getString(actionRequest, "to");
		String from = ParamUtil.getString(actionRequest, "from");
		Map<Locale, String> fromNameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "fromName");
		String cc = ParamUtil.getString(actionRequest, "cc");
		String bcc = ParamUtil.getString(actionRequest, "bcc");
		String type = ParamUtil.getString(actionRequest, "type");
		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		Map<Locale, String> subjectMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "subject");
		Map<Locale, String> bodyMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "body");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceNotificationTemplate.class.getName(), actionRequest);

		CommerceNotificationTemplate commerceNotificationTemplate = null;

		if (commerceNotificationTemplateId <= 0) {
			long commerceChannelId = ParamUtil.getLong(
				actionRequest, "commerceChannelId");

			CommerceChannel commerceChannel =
				_commerceChannelService.getCommerceChannel(commerceChannelId);

			commerceNotificationTemplate =
				_commerceNotificationTemplateService.
					addCommerceNotificationTemplate(
						commerceChannel.getGroupId(), name, description, from,
						fromNameMap, to, cc, bcc, type, enabled, subjectMap,
						bodyMap, serviceContext);
		}
		else {
			commerceNotificationTemplate =
				_commerceNotificationTemplateService.
					updateCommerceNotificationTemplate(
						commerceNotificationTemplateId, name, description, from,
						fromNameMap, to, cc, bcc, type, enabled, subjectMap,
						bodyMap, serviceContext);
		}

		return commerceNotificationTemplate;
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceNotificationTemplateCommerceAccountGroupRelService
		_commerceNotificationTemplateCommerceAccountGroupRelService;

	@Reference
	private CommerceNotificationTemplateService
		_commerceNotificationTemplateService;

	@Reference
	private Portal _portal;

}