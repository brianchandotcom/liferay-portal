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

package com.liferay.remote.web.app.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.web.app.exception.DuplicateEntryURLException;
import com.liferay.remote.web.app.exception.NoSuchEntryException;
import com.liferay.remote.web.app.model.RemoteWebAppEntry;
import com.liferay.remote.web.app.service.RemoteWebAppEntryLocalService;
import com.liferay.remote.web.app.web.internal.RemoteWebAppPortletRegistrar;
import com.liferay.remote.web.app.web.internal.constants.RemoteWebAppPortletKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteWebAppPortletKeys.REMOTE_WEB_APP_ADMIN,
		"mvc.command.name=/edit_entry"
	},
	service = MVCActionCommand.class
)
public class EditEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		String redirect = ParamUtil.getString(actionRequest, "redirect");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String url = ParamUtil.getString(actionRequest, "url");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			RemoteWebAppEntry.class.getName(), actionRequest);

		try {
			if (cmd.equals(Constants.ADD)) {
				RemoteWebAppEntry remoteWebAppEntry =
					_remoteWebAppEntryLocalService.addEntry(
						serviceContext.getUserId(), nameMap, url,
						serviceContext);

				_remoteWebAppPortletRegistrar.registerPortlet(
					remoteWebAppEntry);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				long entryId = ParamUtil.getLong(actionRequest, "entryId");

				RemoteWebAppEntry remoteWebAppEntry =
					_remoteWebAppEntryLocalService.updateEntry(
						entryId, nameMap, url, serviceContext);

				_remoteWebAppPortletRegistrar.unregisterPortlet(
					remoteWebAppEntry);

				_remoteWebAppPortletRegistrar.registerPortlet(
					remoteWebAppEntry);
			}

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/admin/error.jsp");
			}
			else if (exception instanceof DuplicateEntryURLException) {
				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	@Reference
	private RemoteWebAppEntryLocalService _remoteWebAppEntryLocalService;

	@Reference
	private RemoteWebAppPortletRegistrar _remoteWebAppPortletRegistrar;

}