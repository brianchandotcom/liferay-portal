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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryService;
import com.liferay.account.service.AccountEntryUserRelService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_USERS_ADMIN,
		"mvc.command.name=/account_admin/add_account_user"
	},
	service = AopService.class
)
public class AddAccountUserMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long accountEntryId = ParamUtil.getLong(
			actionRequest, "accountEntryId");
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		long prefixListTypeId = ParamUtil.getLong(
			actionRequest, "prefixListTypeId");
		long suffixListTypeId = ParamUtil.getLong(
			actionRequest, "suffixListTypeId");
		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");

		try {
			AccountEntryUserRel accountEntryUserRel = null;

			AccountEntry accountEntry = _accountEntryService.fetchAccountEntry(
				accountEntryId);

			if ((accountEntry != null) &&
				Objects.equals(
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON,
					accountEntry.getType())) {

				accountEntryUserRel =
					_accountEntryUserRelService.
						addPersonTypeAccountEntryUserRel(
							accountEntryId, themeDisplay.getUserId(),
							screenName, emailAddress,
							LocaleUtil.fromLanguageId(languageId), firstName,
							middleName, lastName, prefixListTypeId,
							suffixListTypeId, jobTitle,
							ServiceContextFactory.getInstance(
								AccountEntryUserRel.class.getName(),
								actionRequest));
			}
			else {
				accountEntryUserRel =
					_accountEntryUserRelService.addAccountEntryUserRel(
						accountEntryId, themeDisplay.getUserId(), screenName,
						emailAddress, LocaleUtil.fromLanguageId(languageId),
						firstName, middleName, lastName, prefixListTypeId,
						suffixListTypeId, jobTitle,
						ServiceContextFactory.getInstance(
							AccountEntryUserRel.class.getName(),
							actionRequest));
			}

			byte[] portraitBytes = null;

			long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

			if (fileEntryId > 0) {
				FileEntry fileEntry = _dlAppLocalService.getFileEntry(
					fileEntryId);

				portraitBytes = FileUtil.getBytes(fileEntry.getContentStream());
			}

			if (portraitBytes != null) {
				_userService.updatePortrait(
					accountEntryUserRel.getAccountUserId(), portraitBytes);
			}

			String portletId = _portal.getPortletId(actionRequest);

			if (portletId.equals(
					AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT)) {

				boolean enableAutomaticSiteMembership =
					PrefsParamUtil.getBoolean(
						_portletPreferencesLocalService.getPreferences(
							themeDisplay.getCompanyId(),
							PortletKeys.PREFS_OWNER_ID_DEFAULT,
							PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
							themeDisplay.getPlid(), portletId),
						actionRequest, "enableAutomaticSiteMembership", true);

				if (enableAutomaticSiteMembership) {
					_userLocalService.addGroupUser(
						themeDisplay.getSiteGroupId(),
						accountEntryUserRel.getAccountUserId());
				}
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				redirect = HttpComponentsUtil.setParameter(
					redirect, actionResponse.getNamespace() + "p_u_i_d",
					accountEntryUserRel.getAccountUserId());

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (PortalException portalException) {
			if (portalException instanceof UserEmailAddressException ||
				portalException instanceof UserScreenNameException) {

				SessionErrors.add(
					actionRequest, portalException.getClass(), portalException);

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/account_admin/add_account_user");
			}
			else {
				throw portalException;
			}
		}
	}

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private AccountEntryUserRelService _accountEntryUserRelService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}