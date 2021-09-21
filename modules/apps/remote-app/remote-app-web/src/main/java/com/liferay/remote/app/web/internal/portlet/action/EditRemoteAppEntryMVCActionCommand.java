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

package com.liferay.remote.app.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryService;
import com.liferay.remote.app.web.internal.constants.RemoteAppAdminPortletKeys;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
		"javax.portlet.name=" + RemoteAppAdminPortletKeys.REMOTE_APP_ADMIN,
		"mvc.command.name=/remote_app_admin/edit_remote_app_entry"
	},
	service = MVCActionCommand.class
)
public class EditRemoteAppEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (cmd.equals(Constants.ADD)) {
				_add(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				_update(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/admin/edit_remote_app_entry.jsp");
		}
	}

	private void _add(ActionRequest actionRequest) throws PortalException {
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String portletCategoryName = ParamUtil.getString(
			actionRequest, "portletCategoryName");
		String type = ParamUtil.getString(actionRequest, "type");

		if (type.equals(RemoteAppConstants.TYPE_CUSTOM_ELEMENT)) {
			_remoteAppEntryService.addCustomElementRemoteAppEntry(
				ParamUtil.getString(actionRequest, "customElementCSSURLs"),
				ParamUtil.getString(
					actionRequest, "customElementHTMLElementName"),
				ParamUtil.getString(actionRequest, "customElementURLs"),
				nameMap, portletCategoryName,
				ParamUtil.getString(actionRequest, "properties"));
		}
		else if (type.equals(RemoteAppConstants.TYPE_IFRAME)) {
			_remoteAppEntryService.addIFrameRemoteAppEntry(
				ParamUtil.getString(actionRequest, "iFrameURL"), nameMap,
				portletCategoryName,
				ParamUtil.getString(actionRequest, "properties"));
		}
	}

	private void _update(ActionRequest actionRequest) throws PortalException {
		long remoteAppEntryId = ParamUtil.getLong(
			actionRequest, "remoteAppEntryId");

		RemoteAppEntry remoteAppEntry =
			_remoteAppEntryService.getRemoteAppEntry(remoteAppEntryId);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String portletCategoryName = ParamUtil.getString(
			actionRequest, "portletCategoryName");

		if (Objects.equals(
				remoteAppEntry.getType(),
				RemoteAppConstants.TYPE_CUSTOM_ELEMENT)) {

			String[] customElementCSSURLs = ParamUtil.getStringValues(
				actionRequest, "customElementCSSURLs");
			String[] customElementURLs = ParamUtil.getStringValues(
				actionRequest, "customElementURLs");

			_remoteAppEntryService.updateCustomElementRemoteAppEntry(
				remoteAppEntryId,
				StringUtil.merge(customElementCSSURLs, StringPool.NEW_LINE),
				ParamUtil.getString(
					actionRequest, "customElementHTMLElementName"),
				StringUtil.merge(customElementURLs, StringPool.NEW_LINE),
				nameMap, portletCategoryName,
				ParamUtil.getString(actionRequest, "properties"));
		}
		else if (Objects.equals(
					remoteAppEntry.getType(), RemoteAppConstants.TYPE_IFRAME)) {

			_remoteAppEntryService.updateIFrameRemoteAppEntry(
				remoteAppEntryId,
				ParamUtil.getString(actionRequest, "iFrameURL"), nameMap,
				portletCategoryName,
				ParamUtil.getString(actionRequest, "properties"));
		}
	}

	@Reference
	private RemoteAppEntryService _remoteAppEntryService;

}