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

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.admin.web.internal.constants.AccountWebKeys;
import com.liferay.account.admin.web.internal.display.AccountEntryDisplayFactoryUtil;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Albert Lee
 */
@Component(
	configurationPid = "com.liferay.users.admin.configuration.UserFileUploadsConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_USERS_ADMIN,
		"mvc.command.name=/account_admin/add_account_user"
	},
	service = MVCRenderCommand.class
)
public class AddAccountUserMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long accountEntryId = ParamUtil.getLong(
			renderRequest, "accountEntryId");

		renderRequest.setAttribute(
			AccountWebKeys.ACCOUNT_ENTRY_DISPLAY,
			AccountEntryDisplayFactoryUtil.create(
				accountEntryId, renderRequest));

		renderRequest.setAttribute(
			UserFileUploadsConfiguration.class.getName(),
			_userFileUploadsConfiguration);

		return "/account_entries_admin/add_account_user.jsp";
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	private volatile UserFileUploadsConfiguration _userFileUploadsConfiguration;

}