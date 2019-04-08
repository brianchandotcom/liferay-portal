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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.foundation.dto.v1_0.ContactInformation;
import com.liferay.headless.foundation.dto.v1_0.Email;
import com.liferay.headless.foundation.dto.v1_0.Phone;
import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.dto.v1_0.WebUrl;
import com.liferay.headless.foundation.internal.dto.v1_0.util.EmailUtil;
import com.liferay.headless.foundation.internal.dto.v1_0.util.PhoneUtil;
import com.liferay.headless.foundation.internal.dto.v1_0.util.PostalAddressUtil;
import com.liferay.headless.foundation.internal.dto.v1_0.util.WebUrlUtil;
import com.liferay.headless.foundation.internal.odata.entity.v1_0.UserAccountEntityModel;
import com.liferay.headless.foundation.resource.v1_0.UserAccountResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.ListTypeModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ListTypeService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user-account.properties",
	scope = ServiceScope.PROTOTYPE, service = UserAccountResource.class
)
public class UserAccountResourceImpl
	extends BaseUserAccountResourceImpl implements EntityModelResource {

	@Override
	public void deleteUserAccount(Long userAccountId) throws Exception {
		_userService.deleteUser(userAccountId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public UserAccount getMyUserAccount(Long userAccountId) throws Exception {
		return _toUserAccount(_userService.getUserById(userAccountId));
	}

	@Override
	public Page<UserAccount> getOrganizationUserAccountsPage(
			Long organizationId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getUserAccountsPage(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"organizationIds", String.valueOf(organizationId)),
					BooleanClauseOccur.MUST);
			},
			search, filter, pagination, sorts);
	}

	@Override
	public UserAccount getUserAccount(Long userAccountId) throws Exception {
		return _toUserAccount(_userService.getUserById(userAccountId));
	}

	@Override
	public Page<UserAccount> getUserAccountsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(contextCompany.getCompanyId())) {
			throw new PrincipalException.MustBeCompanyAdmin(permissionChecker);
		}

		return _getUserAccountsPage(
			booleanQuery -> {
			},
			search, filter, pagination, sorts);
	}

	@Override
	public Page<UserAccount> getWebSiteUserAccountsPage(
			Long webSiteId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getUserAccountsPage(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter("groupId", String.valueOf(webSiteId)),
					BooleanClauseOccur.MUST);
			},
			search, filter, pagination, sorts);
	}

	@Override
	public UserAccount postUserAccount(MultipartBody multipartBody)
		throws Exception {

		UserAccount userAccount = multipartBody.getValueAsInstance(
			"userAccount", UserAccount.class);

		User user = _addUser(userAccount);

		_userLocalService.updatePortrait(
			user.getUserId(), multipartBody.getBinaryFileAsBytes("file"));

		return _toUserAccount(user);
	}

	@Override
	public UserAccount postUserAccount(UserAccount userAccount)
		throws Exception {

		return _toUserAccount(_addUser(userAccount));
	}

	@Override
	public UserAccount putUserAccount(
			Long userAccountId, UserAccount userAccount)
		throws Exception {

		User user = _userService.getUserById(userAccountId);
		long prefixId = _getListTypeId(
			userAccount.getHonorificPrefix(), ListTypeConstants.CONTACT_PREFIX);
		long suffixId = _getListTypeId(
			userAccount.getHonorificSuffix(), ListTypeConstants.CONTACT_SUFFIX);
		Calendar birthDateCalendar = _getBirthDateCalendar(userAccount);
		ContactInformation contactInformation =
			userAccount.getContactInformation();

		return _toUserAccount(
			_userLocalService.updateUser(
				user.getUserId(), user.getPassword(), null, null, false,
				user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
				userAccount.getAlternateName(), userAccount.getEmail(),
				user.getFacebookId(), user.getOpenId(), false, null,
				user.getLanguageId(), user.getTimeZoneId(), user.getGreeting(),
				user.getComments(), userAccount.getGivenName(),
				user.getMiddleName(), userAccount.getFamilyName(), prefixId,
				suffixId, true, birthDateCalendar.get(Calendar.MONTH),
				birthDateCalendar.get(Calendar.DATE),
				birthDateCalendar.get(Calendar.YEAR),
				_getOrElse(contactInformation, ContactInformation::getSms),
				_getOrElse(contactInformation, ContactInformation::getFacebook),
				_getOrElse(contactInformation, ContactInformation::getJabber),
				_getOrElse(contactInformation, ContactInformation::getSkype),
				_getOrElse(contactInformation, ContactInformation::getTwitter),
				userAccount.getJobTitle(), user.getGroupIds(),
				user.getOrganizationIds(), user.getRoleIds(), null,
				user.getUserGroupIds(), new ServiceContext()));
	}

	private User _addUser(UserAccount userAccount) throws Exception {
		long prefixId = _getListTypeId(
			userAccount.getHonorificPrefix(), ListTypeConstants.CONTACT_PREFIX);
		long suffixId = _getListTypeId(
			userAccount.getHonorificSuffix(), ListTypeConstants.CONTACT_SUFFIX);
		Calendar birthDateCalendar = _getBirthDateCalendar(userAccount);

		return _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, contextCompany.getCompanyId(), true,
			null, null, Validator.isNull(userAccount.getAlternateName()),
			userAccount.getAlternateName(), userAccount.getEmail(), 0,
			StringPool.BLANK, LocaleUtil.getDefault(),
			userAccount.getGivenName(), StringPool.BLANK,
			userAccount.getFamilyName(), prefixId, suffixId, true,
			birthDateCalendar.get(Calendar.MONTH),
			birthDateCalendar.get(Calendar.DATE),
			birthDateCalendar.get(Calendar.YEAR), userAccount.getJobTitle(),
			null, null, null, null, false, new ServiceContext());
	}

	private Calendar _getBirthDateCalendar(UserAccount userAccount) {
		Calendar calendar = Calendar.getInstance();

		if (userAccount.getBirthDate() == null) {
			calendar.setTime(new Date(0));
		}
		else {
			calendar.setTime(userAccount.getBirthDate());
		}

		return calendar;
	}

	private long _getListTypeId(String name, String type) {
		if (name == null) {
			return 0;
		}

		return Optional.ofNullable(
			_listTypeService.getListType(name, type)
		).map(
			ListTypeModel::getListTypeId
		).orElseThrow(
			() -> new BadRequestException("Unable to get list type: " + name)
		);
	}

	private String _getListTypeMessage(long listTypeId) throws Exception {
		if (listTypeId == 0) {
			return null;
		}

		ListType listType = _listTypeService.getListType(listTypeId);

		return LanguageUtil.get(
			contextAcceptLanguage.getPreferredLocale(), listType.getName());
	}

	private String _getOrElse(
		ContactInformation contactInformation,
		Function<ContactInformation, String> function) {

		return Optional.ofNullable(
			contactInformation
		).map(
			function
		).orElse(
			null
		);
	}

	private ThemeDisplay _getThemeDisplay(Group group) {
		return new ThemeDisplay() {
			{
				setPortalURL(StringPool.BLANK);
				setSiteGroupId(group.getGroupId());
			}
		};
	}

	private Page<UserAccount> _getUserAccountsPage(
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQueryUnsafeConsumer, filter, User.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			document -> _toUserAccount(
				_userService.getUserById(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	private UserAccount _toUserAccount(User user) throws Exception {
		Contact contact = user.getContact();

		return new UserAccount() {
			{
				additionalName = user.getMiddleName();
				alternateName = user.getScreenName();
				birthDate = user.getBirthday();
				contactInformation = new ContactInformation() {
					{
						emails = transformToArray(
							user.getEmailAddresses(), EmailUtil::toEmail,
							Email.class);
						facebook = contact.getFacebookSn();
						jabber = contact.getJabberSn();
						postalAddresses = transformToArray(
							user.getAddresses(),
							address -> PostalAddressUtil.toPostalAddress(
								address,
								contextAcceptLanguage.getPreferredLocale()),
							PostalAddress.class);
						skype = contact.getSkypeSn();
						sms = contact.getSmsSn();
						telephones = transformToArray(
							user.getPhones(), PhoneUtil::toPhone, Phone.class);
						twitter = contact.getTwitterSn();
						webUrls = transformToArray(
							user.getWebsites(), WebUrlUtil::toWebUrl,
							WebUrl.class);
					}
				};
				dateCreated = user.getCreateDate();
				dateModified = user.getModifiedDate();
				email = user.getEmailAddress();
				familyName = user.getLastName();
				givenName = user.getFirstName();
				honorificPrefix = _getListTypeMessage(contact.getPrefixId());
				honorificSuffix = _getListTypeMessage(contact.getSuffixId());
				id = user.getUserId();
				jobTitle = user.getJobTitle();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						User.class.getName(), user.getUserId()),
					AssetTag.NAME_ACCESSOR);
				name = user.getFullName();
				setDashboardURL(
					() -> {
						Group group = user.getGroup();

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

						return group.getDisplayURL(_getThemeDisplay(group));
					});
			}
		};
	}

	private static final EntityModel _entityModel =
		new UserAccountEntityModel();

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ListTypeService _listTypeService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}