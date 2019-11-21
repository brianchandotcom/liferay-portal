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

package com.liferay.sharing.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.constants.SharingPortletKeys;
import com.liferay.sharing.service.SharingEntryService;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplayAction;

import java.util.Date;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=" + SharingPortletKeys.SHARING,
		"mvc.command.name=/sharing/share"
	},
	service = MVCActionCommand.class
)
public class ShareEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] userEmailAddresses = ParamUtil.getStringValues(
			actionRequest, "userEmailAddress");

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		boolean shareable = ParamUtil.getBoolean(actionRequest, "shareable");

		String sharingEntryPermissionDisplayActionId = ParamUtil.getString(
			actionRequest, "sharingEntryPermissionDisplayActionId");

		SharingEntryPermissionDisplayAction
			sharingEntryPermissionDisplayAction =
				SharingEntryPermissionDisplayAction.parseFromActionId(
					sharingEntryPermissionDisplayActionId);

		Date expirationDate = ParamUtil.getDate(
			actionRequest, "expirationDate",
			DateFormatFactoryUtil.getDate(themeDisplay.getLocale()), null);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(themeDisplay.getLocale());

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					for (String curUserEmailAddresses : userEmailAddresses) {
						User user = _userLocalService.fetchUserByEmailAddress(
							themeDisplay.getCompanyId(), curUserEmailAddresses);

						if ((user != null) &&
							(user.getUserId() != themeDisplay.getUserId())) {

							_sharingEntryService.addOrUpdateSharingEntry(
								user.getUserId(), classNameId, classPK,
								themeDisplay.getScopeGroupId(), shareable,
								sharingEntryPermissionDisplayAction.
									getSharingEntryActions(),
								expirationDate, serviceContext);
						}
					}

					return null;
				});

			hideDefaultSuccessMessage(actionRequest);

			JSONObject jsonObject = JSONUtil.put(
				"successMessage",
				LanguageUtil.get(
					resourceBundle, "the-item-was-shared-successfully"));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (Throwable t) {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			String errorMessage =
				"an-unexpected-error-occurred-while-sharing-the-item";

			if (t instanceof PrincipalException) {
				errorMessage = "you-do-not-have-permission-to-share-this-item";
			}

			JSONObject jsonObject = JSONUtil.put(
				"errorMessage", LanguageUtil.get(resourceBundle, errorMessage));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private Portal _portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.sharing.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SharingEntryService _sharingEntryService;

	@Reference
	private UserLocalService _userLocalService;

}