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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.service.SharingEntryService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplayAction;

import java.io.IOException;

import java.text.DateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

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
					_manageCollaborators(
						actionRequest, actionResponse, resourceBundle);

					return null;
				});
		}
		catch (Throwable t) {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			String errorMessage =
				"an-unexpected-error-occurred-while-updating-permissions";

			if (t instanceof PrincipalException) {
				errorMessage =
					"you-do-not-have-permission-to-update-these-permissions";
			}

			JSONObject jsonObject = JSONUtil.put(
				"errorMessage", LanguageUtil.get(resourceBundle, errorMessage));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
	}

	private DateFormat _getDateFormat(Locale locale) {
		return DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd", locale);
	}

	private Map<Long, Collection<SharingEntryAction>> _getSharingEntryActions(
		ActionRequest actionRequest) {

		Map<Long, Collection<SharingEntryAction>> sharingEntryActions =
			new HashMap<>();

		String[] sharingEntryIdActionIdPairs = ParamUtil.getParameterValues(
			actionRequest, "sharingEntryIdActionIdPairs", new String[0], false);

		for (String sharingEntryIdActionIdPair : sharingEntryIdActionIdPairs) {
			String[] parts = StringUtil.split(sharingEntryIdActionIdPair);

			long sharingEntryId = Long.valueOf(parts[0]);

			SharingEntryPermissionDisplayAction
				sharingEntryPermissionDisplayAction =
					SharingEntryPermissionDisplayAction.parseFromActionId(
						parts[1]);

			sharingEntryActions.put(
				sharingEntryId,
				sharingEntryPermissionDisplayAction.getSharingEntryActions());
		}

		return sharingEntryActions;
	}

	private Map<Long, Date> _getSharingEntryExpirationDates(
		ActionRequest actionRequest, ResourceBundle resourceBundle) {

		Map<Long, Date> expirationDates = new HashMap<>();

		String[] sharingEntryIdExpirationDatePairs =
			ParamUtil.getParameterValues(
				actionRequest, "sharingEntryIdExpirationDatePairs",
				new String[0], false);

		for (String sharingEntryIdExpirationDatePair :
				sharingEntryIdExpirationDatePairs) {

			String[] parts = StringUtil.split(sharingEntryIdExpirationDatePair);

			long sharingEntryId = Long.valueOf(parts[0]);

			Date expirationDate = null;

			if (parts.length > 1) {
				expirationDate = GetterUtil.getDate(
					parts[1], _getDateFormat(resourceBundle.getLocale()), null);
			}

			expirationDates.put(sharingEntryId, expirationDate);
		}

		return expirationDates;
	}

	private Set<Long> _getSharingEntryIdsToDelete(ActionRequest actionRequest) {
		long[] deleteSharingEntryIds = ParamUtil.getLongValues(
			actionRequest, "deleteSharingEntryIds");

		Set<Long> sharingEntryIdsToDelete = new HashSet<>();

		for (Long sharingEntryId : deleteSharingEntryIds) {
			sharingEntryIdsToDelete.add(sharingEntryId);
		}

		return sharingEntryIdsToDelete;
	}

	private Map<Long, Boolean> _getSharingEntryShareables(
		ActionRequest actionRequest) {

		Map<Long, Boolean> shareables = new HashMap<>();

		String[] sharingEntryIdShareablePairs = ParamUtil.getParameterValues(
			actionRequest, "sharingEntryIdShareablePairs", new String[0],
			false);

		for (String sharingEntryIdShareablePair :
				sharingEntryIdShareablePairs) {

			String[] parts = StringUtil.split(sharingEntryIdShareablePair);

			long sharingEntryId = Long.valueOf(parts[0]);

			boolean shareable = GetterUtil.getBoolean(parts[1]);

			shareables.put(sharingEntryId, shareable);
		}

		return shareables;
	}

	private void _manageCollaborators(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ResourceBundle resourceBundle)
		throws IOException, PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		Map<Long, Collection<SharingEntryAction>> sharingEntryActions =
			_getSharingEntryActions(actionRequest);

		Set<Long> toEditSharingEntryIds = new HashSet<>(
			sharingEntryActions.keySet());

		Map<Long, Date> sharingEntryExpirationDates =
			_getSharingEntryExpirationDates(actionRequest, resourceBundle);

		toEditSharingEntryIds.addAll(sharingEntryExpirationDates.keySet());

		Map<Long, Boolean> sharingEntryShareables = _getSharingEntryShareables(
			actionRequest);

		toEditSharingEntryIds.addAll(sharingEntryShareables.keySet());

		Set<Long> sharingEntryIdsToDelete = _getSharingEntryIdsToDelete(
			actionRequest);

		toEditSharingEntryIds.removeAll(sharingEntryIdsToDelete);

		for (Long sharingEntryId : toEditSharingEntryIds) {
			SharingEntry sharingEntry =
				_sharingEntryLocalService.getSharingEntry(sharingEntryId);

			_sharingEntryService.updateSharingEntry(
				sharingEntryId,
				sharingEntryActions.getOrDefault(
					sharingEntryId,
					SharingEntryAction.getSharingEntryActions(
						sharingEntry.getActionIds())),
				sharingEntryShareables.getOrDefault(
					sharingEntryId, sharingEntry.isShareable()),
				sharingEntryExpirationDates.getOrDefault(
					sharingEntryId, sharingEntry.getExpirationDate()),
				serviceContext);
		}

		for (long sharingEntryId : sharingEntryIdsToDelete) {
			_sharingEntryService.deleteSharingEntry(
				sharingEntryId, serviceContext);
		}

		String portletId = (String)actionRequest.getAttribute(
			WebKeys.PORTLET_ID);

		PortletConfig portletConfig = PortletConfigFactoryUtil.get(portletId);

		SessionMessages.add(
			actionRequest,
			portletConfig.getPortletName() +
				_KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);

		JSONObject jsonObject = JSONUtil.put(
			"successMessage",
			LanguageUtil.get(resourceBundle, "permissions-changed"));

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private static final String _KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE =
		".hideDefaultSuccessMessage";

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

}