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

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.AccountBrief;
import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.dto.v1_0.OrganizationBrief;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.dto.v1_0.SiteBrief;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.dto.v1_0.UserAccountContactInformation;
import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.EmailAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PhoneUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PostalAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderListTypeUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.WebUrlUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.User",
	service = {DTOConverter.class, UserResourceDTOConverter.class}
)
public class UserResourceDTOConverter
	implements DTOConverter<User, UserAccount> {

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	@Override
	public User getObject(String externalReferenceCode) throws Exception {
		User user = _userLocalService.fetchUserByExternalReferenceCode(
			CompanyThreadLocal.getCompanyId(), externalReferenceCode);

		if (user == null) {
			user = _userLocalService.getUser(
				GetterUtil.getLong(externalReferenceCode));
		}

		return user;
	}

	public long getUserId(String externalReferenceCode) throws Exception {
		User user = getObject(externalReferenceCode);

		return user.getUserId();
	}

	@Override
	public UserAccount toDTO(DTOConverterContext dtoConverterContext, User user)
		throws Exception {

		if (user == null) {
			return null;
		}

		Contact contact = user.getContact();

		return new UserAccount() {
			{
				accountBriefs = TransformUtil.transformToArray(
					_accountEntryUserRelService.
						getAccountEntryUserRelsByAccountUserId(
							user.getUserId()),
					accountEntryUserRel -> _toAccountBrief(
						accountEntryUserRel, dtoConverterContext, user),
					AccountBrief.class);
				actions = dtoConverterContext.getActions();
				additionalName = user.getMiddleName();
				alternateName = user.getScreenName();
				birthDate = user.getBirthday();
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					User.class.getName(), user.getUserId(), user.getCompanyId(),
					dtoConverterContext.getLocale());
				dateCreated = user.getCreateDate();
				dateModified = user.getModifiedDate();
				emailAddress = user.getEmailAddress();
				externalReferenceCode = user.getExternalReferenceCode();
				familyName = user.getLastName();
				givenName = user.getFirstName();
				honorificPrefix =
					ServiceBuilderListTypeUtil.getServiceBuilderListTypeMessage(
						contact.getPrefixId(), dtoConverterContext.getLocale());
				honorificSuffix =
					ServiceBuilderListTypeUtil.getServiceBuilderListTypeMessage(
						contact.getSuffixId(), dtoConverterContext.getLocale());
				id = user.getUserId();
				jobTitle = user.getJobTitle();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						User.class.getName(), user.getUserId()),
					AssetTag.NAME_ACCESSOR);
				lastLoginDate = user.getLastLoginDate();
				name = user.getFullName();
				organizationBriefs = TransformUtil.transformToArray(
					user.getOrganizations(),
					organization -> _toOrganizationBrief(
						dtoConverterContext, organization, user),
					OrganizationBrief.class);
				roleBriefs = TransformUtil.transformToArray(
					_roleService.getUserRoles(user.getUserId()),
					role -> _toRoleBrief(dtoConverterContext, role),
					RoleBrief.class);
				siteBriefs = TransformUtil.transformToArray(
					_groupService.getGroups(
						user.getCompanyId(),
						GroupConstants.DEFAULT_PARENT_GROUP_ID, true),
					group -> _toSiteBrief(dtoConverterContext, group),
					SiteBrief.class);
				userAccountContactInformation =
					new UserAccountContactInformation() {
						{
							emailAddresses = TransformUtil.transformToArray(
								user.getEmailAddresses(),
								EmailAddressUtil::toEmailAddress,
								EmailAddress.class);
							facebook = contact.getFacebookSn();
							jabber = contact.getJabberSn();
							postalAddresses = TransformUtil.transformToArray(
								user.getAddresses(),
								address -> PostalAddressUtil.toPostalAddress(
									dtoConverterContext.isAcceptAllLanguages(),
									address, user.getCompanyId(),
									dtoConverterContext.getLocale()),
								PostalAddress.class);
							skype = contact.getSkypeSn();
							sms = contact.getSmsSn();
							telephones = TransformUtil.transformToArray(
								user.getPhones(), PhoneUtil::toPhone,
								Phone.class);
							twitter = contact.getTwitterSn();
							webUrls = TransformUtil.transformToArray(
								user.getWebsites(), WebUrlUtil::toWebUrl,
								WebUrl.class);
						}
					};

				setDashboardURL(
					() -> {
						Group group = user.getGroup();

						if (group == null) {
							return null;
						}

						return group.getDisplayURL(
							_getThemeDisplay(group), true);
					});
				setImage(
					() -> {
						if (user.getPortraitId() == 0) {
							return null;
						}

						ThemeDisplay themeDisplay = new ThemeDisplay() {
							{
								setPathImage(_portal.getPathImage());
							}
						};

						return user.getPortraitURL(themeDisplay);
					});
				setProfileURL(
					() -> {
						Group group = user.getGroup();

						if (group == null) {
							return null;
						}

						return group.getDisplayURL(_getThemeDisplay(group));
					});
			}
		};
	}

	private ThemeDisplay _getThemeDisplay(Group group) {
		return new ThemeDisplay() {
			{
				setPortalURL(StringPool.BLANK);

				if (group != null) {
					setSiteGroupId(group.getGroupId());
				}
			}
		};
	}

	private AccountBrief _toAccountBrief(
			AccountEntryUserRel accountEntryUserRel,
			DTOConverterContext dtoConverterContext, User user)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryUserRel.getAccountEntryId());

		return new AccountBrief() {
			{
				externalReferenceCode = accountEntry.getExternalReferenceCode();
				id = accountEntry.getAccountEntryId();
				name = accountEntry.getName();
				roleBriefs = TransformUtil.transformToArray(
					_accountRoleLocalService.getAccountRoles(
						accountEntry.getAccountEntryId(), user.getUserId()),
					accountRole -> _toRoleBrief(
						accountRole, dtoConverterContext),
					RoleBrief.class);
			}
		};
	}

	private OrganizationBrief _toOrganizationBrief(
			DTOConverterContext dtoConverterContext, Organization organization,
			User user)
		throws PortalException {

		return new OrganizationBrief() {
			{
				id = organization.getOrganizationId();
				name = organization.getName();
				roleBriefs = TransformUtil.transformToArray(
					_roleService.getUserGroupRoles(
						user.getUserId(), organization.getGroupId()),
					role -> _toRoleBrief(dtoConverterContext, role),
					RoleBrief.class);
			}
		};
	}

	private RoleBrief _toRoleBrief(
			AccountRole accountRole, DTOConverterContext dtoConverterContext)
		throws PortalException {

		Role role = accountRole.getRole();

		return new RoleBrief() {
			{
				id = accountRole.getAccountRoleId();
				name = accountRole.getRoleName();
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					role.getTitleMap());
			}
		};
	}

	private RoleBrief _toRoleBrief(
		DTOConverterContext dtoConverterContext, Role role) {

		return new RoleBrief() {
			{
				id = role.getRoleId();
				name = role.getTitle(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					role.getTitleMap());
			}
		};
	}

	private SiteBrief _toSiteBrief(
		DTOConverterContext dtoConverterContext, Group group) {

		return new SiteBrief() {
			{
				id = group.getGroupId();
				name = group.getName(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					group.getNameMap());
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelService;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private Portal _portal;

	@Reference
	private RoleService _roleService;

	@Reference
	private UserLocalService _userLocalService;

}