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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
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
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.User",
	service = {DTOConverter.class, UserAccountResourceDTOConverter.class}
)
public class UserAccountResourceDTOConverter
	implements DTOConverter<User, UserAccount> {

	@Override
	public String getContentType() {
		return UserAccount.class.getSimpleName();
	}

	@Override
	public User getObject(String externalReferenceCode) throws Exception {
		User user = _userLocalService.fetchUserByReferenceCode(
			CompanyThreadLocal.getCompanyId(), externalReferenceCode);

		if (user == null) {
			user = _userLocalService.getUser(
				GetterUtil.getLong(externalReferenceCode));
		}

		return user;
	}

	@Override
	public UserAccount toDTO(DTOConverterContext dtoConverterContext, User user)
		throws Exception {

		if (user == null) {
			return null;
		}

		Contact contact = _contactLocalService.fetchContact(
			user.getContactId());
		User contextUser = dtoConverterContext.getUser();

		return new UserAccount() {
			{
				actions = dtoConverterContext.getActions();
				additionalName = user.getMiddleName();
				alternateName = user.getScreenName();
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
					organization -> _toOrganizationBrief(organization),
					OrganizationBrief.class);
				siteBriefs = TransformUtil.transformToArray(
					_groupLocalService.getGroups(
						contextUser.getCompanyId(),
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
							postalAddresses = TransformUtil.transformToArray(
								user.getAddresses(),
								address -> PostalAddressUtil.toPostalAddress(
									dtoConverterContext.isAcceptAllLanguages(),
									address, user.getCompanyId(),
									dtoConverterContext.getLocale()),
								PostalAddress.class);
							telephones = TransformUtil.transformToArray(
								user.getPhones(), PhoneUtil::toPhone,
								Phone.class);
							webUrls = TransformUtil.transformToArray(
								user.getWebsites(), WebUrlUtil::toWebUrl,
								WebUrl.class);

							setFacebook(
								_getContactField(
									contact, Contact::getFacebookSn));
							setJabber(
								_getContactField(
									contact, Contact::getJabberSn));
							setSkype(
								_getContactField(contact, Contact::getSkypeSn));
							setSms(
								_getContactField(contact, Contact::getSmsSn));
							setTwitter(
								_getContactField(
									contact, Contact::getTwitterSn));
						}
					};

				setBirthDate(_getContactField(contact, Contact::getBirthday));
				setDashboardURL(
					() -> {
						Group group = user.getGroup();

						if (group == null) {
							return null;
						}

						return group.getDisplayURL(
							_getThemeDisplay(group), true);
					});
				setHonorificPrefix(
					() -> {
						if (contact == null) {
							return null;
						}

						return ServiceBuilderListTypeUtil.
							getServiceBuilderListTypeMessage(
								contact.getPrefixId(),
								dtoConverterContext.getLocale());
					});
				setHonorificSuffix(
					() -> {
						if (contact == null) {
							return null;
						}

						return ServiceBuilderListTypeUtil.
							getServiceBuilderListTypeMessage(
								contact.getSuffixId(),
								dtoConverterContext.getLocale());
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
				setRoleBriefs(
					() -> {
						if (contact == null) {
							return new RoleBrief[0];
						}

						return TransformUtil.transformToArray(
							_roleLocalService.getUserRoles(user.getUserId()),
							role -> _toRoleBrief(dtoConverterContext, role),
							RoleBrief.class);
					});
			}
		};
	}

	private <T> T _getContactField(
			Contact contact,
			UnsafeFunction<Contact, T, Exception> unsafeFunction)
		throws Exception {

		if (contact != null) {
			return unsafeFunction.apply(contact);
		}

		return null;
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

	private OrganizationBrief _toOrganizationBrief(Organization organization) {
		return new OrganizationBrief() {
			{
				id = organization.getOrganizationId();
				name = organization.getName();
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
			DTOConverterContext dtoConverterContext, Group group)
		throws Exception {

		return new SiteBrief() {
			{
				descriptiveName = group.getDescriptiveName(
					dtoConverterContext.getLocale());
				descriptiveName_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					group.getDescriptiveNameMap());
				id = group.getGroupId();
				name = group.getName(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					group.getNameMap());
			}
		};
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}