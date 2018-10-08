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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.service.SharingEntryService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplayAction;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + SharingPortletKeys.MANAGE_COLLABORATORS,
		"mvc.command.name=/sharing/manage_collaborators"
	},
	service = MVCActionCommand.class
)
public class ManageCollaboratorsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(themeDisplay.getLocale());

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					long[] deleteSharingEntryIds = ParamUtil.getLongValues(
						actionRequest, "deleteSharingEntryIds");

					ServiceContext serviceContext =
						ServiceContextFactory.getInstance(actionRequest);

					for (long sharingEntryId : deleteSharingEntryIds) {
						_sharingEntryService.deleteSharingEntry(
							sharingEntryId, serviceContext);
					}

					String[] sharingEntryIdActionIdPairs =
						ParamUtil.getParameterValues(
							actionRequest, "sharingEntryIdActionIdPairs",
							new String[0], false);

					for (String sharingEntryIdActionIdPair :
							sharingEntryIdActionIdPairs) {

						String[] parts = StringUtil.split(
							sharingEntryIdActionIdPair);

						long sharingEntryId = Long.valueOf(parts[0]);

						SharingEntryPermissionDisplayAction
							sharingEntryPermissionDisplayActionKey =
								SharingEntryPermissionDisplayAction.
									parseFromActionId(parts[1]);

						SharingEntry sharingEntry =
							_sharingEntryLocalService.getSharingEntry(
								sharingEntryId);

						_sharingEntryService.updateSharingEntry(
							sharingEntryId,
							sharingEntryPermissionDisplayActionKey.
								getSharingEntryActions(),
							sharingEntry.isShareable(),
							sharingEntry.getExpirationDate(), serviceContext);
					}

					return null;
				});

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"successMessage",
				LanguageUtil.get(resourceBundle, "permissions-changed"));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (Throwable t) {
			HttpServletResponse response = _portal.getHttpServletResponse(
				actionResponse);

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String errorMessage =
				"an-unexpected-error-occurred-while-updating-permissions";

			if (t instanceof PrincipalException) {
				errorMessage =
					"you-do-not-have-permission-to-update-these-permissions";
			}

			jsonObject.put(
				"errorMessage", LanguageUtil.get(resourceBundle, errorMessage));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);

			return;
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
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingEntryService _sharingEntryService;

	@Reference
	private UserLocalService _userLocalService;

}