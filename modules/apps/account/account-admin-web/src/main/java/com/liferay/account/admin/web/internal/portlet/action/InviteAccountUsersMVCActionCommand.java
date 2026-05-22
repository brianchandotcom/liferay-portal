/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.notification.AccountUserInvitationNotificationHelper;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelService;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletMode;
import jakarta.portlet.PortletRequest;
import jakarta.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"jakarta.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"jakarta.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"mvc.command.name=/account_admin/invite_account_users"
	},
	service = MVCActionCommand.class
)
public class InviteAccountUsersMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			long accountEntryId = ParamUtil.getLong(
				actionRequest, "accountEntryId");

			_accountEntryLocalService.getAccountEntry(accountEntryId);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				User.class.getName(), actionRequest);

			for (int index = 0;
				 index < ParamUtil.getInteger(actionRequest, "count");
				 index++) {

				long[] accountRoleIds = ParamUtil.getLongValues(
					actionRequest, "accountRoleIds" + index);
				String[] emailAddresses = ParamUtil.getStringValues(
					actionRequest, "emailAddresses" + index);

				_inviteUsers(
					accountEntryId, accountRoleIds, emailAddresses,
					themeDisplay.getScopeGroupId(),
					PrefsParamUtil.getString(
						actionRequest.getPreferences(), actionRequest,
						"invitationNotificationTemplateExternalReferenceCode"),
					themeDisplay.getUser(), serviceContext);
			}

			jsonObject.put("success", true);
		}
		catch (Exception exception) {
			jsonObject.put("success", false);

			throw exception;
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private String _getCallbackURL(
			long companyId, long groupId, String ticketKey,
			ServiceContext serviceContext)
		throws PortalException {

		Layout layout = null;

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.
				fetchDefaultLayoutUtilityPageEntry(
					groupId,
					LayoutUtilityPageEntryConstants.TYPE_CREATE_ACCOUNT);

		if (layoutUtilityPageEntry != null) {
			layout = _layoutLocalService.fetchLayout(
				layoutUtilityPageEntry.getPlid());
		}

		if (layout == null) {
			Group group = _groupLocalService.getGroup(
				companyId, GroupConstants.GUEST);

			layout = _layoutLocalService.fetchDefaultLayout(
				group.getGroupId(), false);
		}

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				serviceContext.getRequest(),
				AccountPortletKeys.ACCOUNT_USERS_REGISTRATION, layout,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/account_admin/create_account_user"
		).setParameter(
			"ticketKey", ticketKey
		).setPortletMode(
			PortletMode.VIEW
		).setWindowState(
			WindowState.MAXIMIZED
		).buildString();
	}

	private void _inviteUsers(
			long accountEntryId, long[] accountRoleIds, String[] emailAddresses,
			long groupId, String notificationTemplateExternalReferenceCode,
			User user, ServiceContext serviceContext)
		throws PortalException {

		for (String emailAddress : emailAddresses) {
			User existingUser = _userLocalService.fetchUserByEmailAddress(
				user.getCompanyId(), emailAddress);

			if (existingUser != null) {
				continue;
			}

			if (Validator.isNotNull(
					notificationTemplateExternalReferenceCode)) {

				Ticket ticket =
					_accountEntryUserRelService.addUserInvitationTicket(
						accountEntryId, accountRoleIds, emailAddress, user,
						serviceContext);

				_accountUserInvitationNotificationHelper.sendNotification(
					accountEntryId,
					_getCallbackURL(
						user.getCompanyId(), groupId, ticket.getKey(),
						serviceContext),
					emailAddress, notificationTemplateExternalReferenceCode,
					user);
			}
			else {
				_accountEntryUserRelService.inviteUser(
					accountEntryId, accountRoleIds, emailAddress, user,
					serviceContext);
			}
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelService _accountEntryUserRelService;

	@Reference
	private AccountUserInvitationNotificationHelper
		_accountUserInvitationNotificationHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}