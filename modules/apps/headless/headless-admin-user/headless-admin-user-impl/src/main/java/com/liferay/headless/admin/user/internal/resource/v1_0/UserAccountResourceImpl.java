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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelService;
import com.liferay.announcements.kernel.service.AnnouncementsDeliveryLocalService;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.dto.v1_0.UserAccountContactInformation;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.AccountResourceDTOConverter;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.OrganizationResourceDTOConverter;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.UserAccountResourceDTOConverter;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderEmailAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderListTypeUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderPhoneUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderWebsiteUtil;
import com.liferay.headless.admin.user.internal.odata.entity.v1_0.UserAccountEntityModel;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.common.spi.service.context.ServiceContextRequestUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
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
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
	public void deleteAccountUserAccountByEmailAddress(
			Long accountId, String emailAddress)
		throws Exception {

		_accountEntryUserRelService.deleteAccountEntryUserRelByEmailAddress(
			accountId, emailAddress);
	}

	@Override
	public void deleteAccountUserAccountByExternalReferenceCodeByEmailAddress(
			String externalReferenceCode, String emailAddress)
		throws Exception {

		deleteAccountUserAccountByEmailAddress(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			emailAddress);
	}

	@Override
	public void deleteAccountUserAccountsByEmailAddress(
			Long accountId, String[] emailAddresses)
		throws Exception {

		for (String emailAddress : emailAddresses) {
			deleteAccountUserAccountByEmailAddress(accountId, emailAddress);
		}
	}

	@Override
	public void deleteAccountUserAccountsByExternalReferenceCodeByEmailAddress(
			String externalReferenceCode, String[] emailAddresses)
		throws Exception {

		for (String emailAddress : emailAddresses) {
			deleteAccountUserAccountByExternalReferenceCodeByEmailAddress(
				externalReferenceCode, emailAddress);
		}
	}

	@Override
	public void deleteUserAccount(Long userAccountId) throws Exception {
		_userService.deleteUser(userAccountId);
	}

	@Override
	public void deleteUserAccountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		User user = _userLocalService.getUserByExternalReferenceCode(
			contextCompany.getCompanyId(), externalReferenceCode);

		deleteUserAccount(user.getUserId());
	}

	@Override
	public Page<UserAccount> getAccountUserAccountsByExternalReferenceCodePage(
			String externalReferenceCode, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return getAccountUserAccountsPage(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			search, filter, pagination, sorts);
	}

	@Override
	public Page<UserAccount> getAccountUserAccountsPage(
			Long accountId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"accountEntryIds", String.valueOf(accountId)),
					BooleanClauseOccur.MUST);
			},
			filter, User.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toUserAccount(
				_userLocalService.getUserById(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public UserAccount getMyUserAccount() throws Exception {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return _toUserAccount(
			_userService.getUserById(permissionChecker.getUserId()));
	}

	@Override
	public Page<UserAccount> getOrganizationUserAccountsPage(
			String organizationId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Organization organization = _organizationResourceDTOConverter.getObject(
			organizationId);

		return _getUserAccountsPage(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"organizationIds",
						String.valueOf(organization.getOrganizationId())),
					BooleanClauseOccur.MUST);
			},
			search, filter, pagination, sorts);
	}

	@Override
	public Page<UserAccount> getSiteUserAccountsPage(
			Long siteId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getUserAccountsPage(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter("groupId", String.valueOf(siteId)),
					BooleanClauseOccur.MUST);
			},
			search, filter, pagination, sorts);
	}

	@Override
	public UserAccount getUserAccount(Long userAccountId) throws Exception {
		return _toUserAccount(_userService.getUserById(userAccountId));
	}

	@Override
	public UserAccount getUserAccountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		return _toUserAccount(
			_userService.getUserByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode));
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
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter("userName", StringPool.BLANK),
					BooleanClauseOccur.MUST_NOT);
			},
			search, filter, pagination, sorts);
	}

	@Override
	public UserAccount postAccountUserAccount(
			Long accountId, UserAccount userAccount)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelService.addAccountEntryUserRel(
				accountId, contextUser.getUserId(),
				userAccount.getAlternateName(), userAccount.getEmailAddress(),
				contextAcceptLanguage.getPreferredLocale(),
				userAccount.getGivenName(), userAccount.getAdditionalName(),
				userAccount.getFamilyName(), _getPrefixId(userAccount),
				_getSuffixId(userAccount));

		User user = accountEntryUserRel.getUser();

		user = _userLocalService.updateJobTitle(
			user.getUserId(), userAccount.getJobTitle());

		UsersAdminUtil.updateAddresses(
			Contact.class.getName(), user.getContactId(),
			_getAddresses(userAccount));
		UsersAdminUtil.updateEmailAddresses(
			Contact.class.getName(), user.getContactId(),
			_getServiceBuilderEmailAddresses(userAccount));
		UsersAdminUtil.updatePhones(
			Contact.class.getName(), user.getContactId(),
			_getServiceBuilderPhones(userAccount));
		UsersAdminUtil.updateWebsites(
			Contact.class.getName(), user.getContactId(),
			_getWebsites(userAccount));

		Contact contact = user.getContact();

		String sms = null;
		String facebook = null;
		String jabber = null;
		String skype = null;
		String twitter = null;

		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation != null) {
			sms = userAccountContactInformation.getSms();
			facebook = userAccountContactInformation.getFacebook();
			jabber = userAccountContactInformation.getJabber();
			skype = userAccountContactInformation.getSkype();
			twitter = userAccountContactInformation.getTwitter();
		}

		return _toUserAccount(
			_userLocalService.updateUser(
				user.getUserId(), null, null, null, false,
				user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
				user.getScreenName(), user.getEmailAddress(), false, null,
				user.getLanguageId(), user.getTimeZoneId(), user.getGreeting(),
				user.getComments(), user.getFirstName(), user.getMiddleName(),
				user.getLastName(), contact.getPrefixId(),
				contact.getSuffixId(), user.isMale(),
				_getBirthdayMonth(userAccount), _getBirthdayDay(userAccount),
				_getBirthdayYear(userAccount), sms, facebook, jabber, skype,
				twitter, user.getJobTitle(), user.getGroupIds(),
				user.getOrganizationIds(), user.getRoleIds(), null,
				user.getUserGroupIds(),
				ServiceContextRequestUtil.createServiceContext(
					CustomFieldsUtil.toMap(
						User.class.getName(), contextCompany.getCompanyId(),
						userAccount.getCustomFields(),
						contextAcceptLanguage.getPreferredLocale()),
					contextCompany.getGroupId(), contextHttpServletRequest,
					null)));
	}

	@Override
	public void postAccountUserAccountByEmailAddress(
			Long accountId, String emailAddress)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(contextCompany.getCompanyId());
		serviceContext.setLanguageId(
			contextAcceptLanguage.getPreferredLanguageId());
		serviceContext.setUserId(contextUser.getUserId());

		_accountEntryUserRelService.addAccountEntryUserRelByEmailAddress(
			accountId, emailAddress, new long[0], null, serviceContext);
	}

	@Override
	public UserAccount postAccountUserAccountByExternalReferenceCode(
			String externalReferenceCode, UserAccount userAccount)
		throws Exception {

		return postAccountUserAccount(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			userAccount);
	}

	@Override
	public void postAccountUserAccountByExternalReferenceCodeByEmailAddress(
			String externalReferenceCode, String emailAddress)
		throws Exception {

		postAccountUserAccountByEmailAddress(
			_accountResourceDTOConverter.getAccountEntryId(
				externalReferenceCode),
			emailAddress);
	}

	@Override
	public void postAccountUserAccountsByEmailAddress(
			Long accountId, String[] emailAddresses)
		throws Exception {

		for (String emailAddress : emailAddresses) {
			postAccountUserAccountByEmailAddress(accountId, emailAddress);
		}
	}

	@Override
	public void postAccountUserAccountsByExternalReferenceCodeByEmailAddress(
			String externalReferenceCode, String[] emailAddresses)
		throws Exception {

		for (String emailAddress : emailAddresses) {
			postAccountUserAccountByExternalReferenceCodeByEmailAddress(
				externalReferenceCode, emailAddress);
		}
	}

	@Override
	public UserAccount postUserAccount(UserAccount userAccount)
		throws Exception {

		User user = _userService.addUser(
			contextCompany.getCompanyId(), true, null, null, false,
			userAccount.getAlternateName(), userAccount.getEmailAddress(),
			contextAcceptLanguage.getPreferredLocale(),
			userAccount.getGivenName(), userAccount.getAdditionalName(),
			userAccount.getFamilyName(), _getPrefixId(userAccount),
			_getSuffixId(userAccount), true, _getBirthdayMonth(userAccount),
			_getBirthdayDay(userAccount), _getBirthdayYear(userAccount),
			userAccount.getJobTitle(), new long[0], new long[0], new long[0],
			new long[0], _getAddresses(userAccount),
			_getServiceBuilderEmailAddresses(userAccount),
			_getServiceBuilderPhones(userAccount), _getWebsites(userAccount),
			Collections.emptyList(), false,
			ServiceContextRequestUtil.createServiceContext(
				CustomFieldsUtil.toMap(
					User.class.getName(), contextCompany.getCompanyId(),
					userAccount.getCustomFields(),
					contextAcceptLanguage.getPreferredLocale()),
				contextCompany.getGroupId(), contextHttpServletRequest, null));

		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation != null) {
			Contact contact = user.getContact();

			contact.setSmsSn(userAccountContactInformation.getSms());
			contact.setFacebookSn(userAccountContactInformation.getFacebook());
			contact.setJabberSn(userAccountContactInformation.getJabber());
			contact.setSkypeSn(userAccountContactInformation.getSkype());
			contact.setTwitterSn(userAccountContactInformation.getTwitter());

			_contactLocalService.updateContact(contact);

			user = _userService.getUserById(user.getUserId());
		}

		return _toUserAccount(user);
	}

	@Override
	public UserAccount putUserAccount(
			Long userAccountId, UserAccount userAccount)
		throws Exception {

		User user = _userService.getUserById(userAccountId);

		String sms = null;
		String facebook = null;
		String jabber = null;
		String skype = null;
		String twitter = null;

		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation != null) {
			sms = userAccountContactInformation.getSms();
			facebook = userAccountContactInformation.getFacebook();
			jabber = userAccountContactInformation.getJabber();
			skype = userAccountContactInformation.getSkype();
			twitter = userAccountContactInformation.getTwitter();
		}

		return _toUserAccount(
			_userService.updateUser(
				userAccountId, null, null, null, false, null, null,
				userAccount.getAlternateName(), userAccount.getEmailAddress(),
				false, null, user.getLanguageId(), user.getTimeZoneId(),
				user.getGreeting(), user.getComments(),
				userAccount.getGivenName(), userAccount.getAdditionalName(),
				userAccount.getFamilyName(), _getPrefixId(userAccount),
				_getSuffixId(userAccount), true, _getBirthdayMonth(userAccount),
				_getBirthdayDay(userAccount), _getBirthdayYear(userAccount),
				sms, facebook, jabber, skype, twitter,
				userAccount.getJobTitle(), user.getGroupIds(),
				user.getOrganizationIds(), user.getRoleIds(),
				_userGroupRoleLocalService.getUserGroupRoles(userAccountId),
				user.getUserGroupIds(), _getAddresses(userAccount),
				_getServiceBuilderEmailAddresses(userAccount),
				_getServiceBuilderPhones(userAccount),
				_getWebsites(userAccount),
				_announcementsDeliveryLocalService.getUserDeliveries(
					userAccountId),
				ServiceContextRequestUtil.createServiceContext(
					CustomFieldsUtil.toMap(
						User.class.getName(), contextCompany.getCompanyId(),
						userAccount.getCustomFields(),
						contextAcceptLanguage.getPreferredLocale()),
					contextCompany.getGroupId(), contextHttpServletRequest,
					null)));
	}

	@Override
	public UserAccount putUserAccountByExternalReferenceCode(
			String externalReferenceCode, UserAccount userAccount)
		throws Exception {

		User user = _userService.addOrUpdateUser(
			externalReferenceCode, contextUser.getUserId(),
			contextCompany.getCompanyId(), true, null, null, false,
			userAccount.getAlternateName(), userAccount.getEmailAddress(),
			contextAcceptLanguage.getPreferredLocale(),
			userAccount.getGivenName(), userAccount.getAdditionalName(),
			userAccount.getFamilyName(), _getPrefixId(userAccount),
			_getSuffixId(userAccount), true, _getBirthdayMonth(userAccount),
			_getBirthdayDay(userAccount), _getBirthdayYear(userAccount),
			userAccount.getJobTitle(), _getAddresses(userAccount),
			_getServiceBuilderEmailAddresses(userAccount),
			_getServiceBuilderPhones(userAccount), _getWebsites(userAccount),
			false,
			ServiceContextRequestUtil.createServiceContext(
				CustomFieldsUtil.toMap(
					User.class.getName(), contextCompany.getCompanyId(),
					userAccount.getCustomFields(),
					contextAcceptLanguage.getPreferredLocale()),
				contextCompany.getGroupId(), contextHttpServletRequest, null));

		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation != null) {
			Contact contact = user.getContact();

			contact.setSmsSn(userAccountContactInformation.getSms());
			contact.setFacebookSn(userAccountContactInformation.getFacebook());
			contact.setJabberSn(userAccountContactInformation.getJabber());
			contact.setSkypeSn(userAccountContactInformation.getSkype());
			contact.setTwitterSn(userAccountContactInformation.getTwitter());

			_contactLocalService.updateContact(contact);

			user = _userService.getUserById(user.getUserId());
		}

		return _toUserAccount(user);
	}

	@Override
	protected void preparePatch(
		UserAccount userAccount, UserAccount existingUserAccount) {

		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation != null) {
			UserAccountContactInformation
				existingUserAccountContactInformation =
					existingUserAccount.getUserAccountContactInformation();

			Optional.ofNullable(
				userAccountContactInformation.getEmailAddresses()
			).ifPresent(
				existingUserAccountContactInformation::setEmailAddresses
			);
			Optional.ofNullable(
				userAccountContactInformation.getFacebook()
			).ifPresent(
				existingUserAccountContactInformation::setFacebook
			);
			Optional.ofNullable(
				userAccountContactInformation.getJabber()
			).ifPresent(
				existingUserAccountContactInformation::setJabber
			);
			Optional.ofNullable(
				userAccountContactInformation.getPostalAddresses()
			).ifPresent(
				existingUserAccountContactInformation::setPostalAddresses
			);
			Optional.ofNullable(
				userAccountContactInformation.getSkype()
			).ifPresent(
				existingUserAccountContactInformation::setSkype
			);
			Optional.ofNullable(
				userAccountContactInformation.getSms()
			).ifPresent(
				existingUserAccountContactInformation::setSms
			);
			Optional.ofNullable(
				userAccountContactInformation.getTelephones()
			).ifPresent(
				existingUserAccountContactInformation::setTelephones
			);
			Optional.ofNullable(
				userAccountContactInformation.getTwitter()
			).ifPresent(
				existingUserAccountContactInformation::setTwitter
			);
			Optional.ofNullable(
				userAccountContactInformation.getWebUrls()
			).ifPresent(
				existingUserAccountContactInformation::setWebUrls
			);
		}

		if (userAccount.getCustomFields() != null) {
			Optional.ofNullable(
				userAccount.getCustomFields()
			).ifPresent(
				existingUserAccount::setCustomFields
			);
		}
	}

	private List<Address> _getAddresses(UserAccount userAccount) {
		return Optional.ofNullable(
			userAccount.getUserAccountContactInformation()
		).map(
			UserAccountContactInformation::getPostalAddresses
		).map(
			postalAddresses -> ListUtil.filter(
				transformToList(
					postalAddresses,
					_postalAddress ->
						ServiceBuilderAddressUtil.toServiceBuilderAddress(
							contextCompany.getCompanyId(), _postalAddress,
							ListTypeConstants.CONTACT_ADDRESS)),
				Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
	}

	private int _getBirthdayDay(UserAccount userAccount) {
		return _getCalendarFieldValue(userAccount, Calendar.DAY_OF_MONTH, 1);
	}

	private int _getBirthdayMonth(UserAccount userAccount) {
		return _getCalendarFieldValue(
			userAccount, Calendar.MONTH, Calendar.JANUARY);
	}

	private int _getBirthdayYear(UserAccount userAccount) {
		return _getCalendarFieldValue(userAccount, Calendar.YEAR, 1977);
	}

	private int _getCalendarFieldValue(
		UserAccount userAccount, int calendarField, int defaultValue) {

		return Optional.ofNullable(
			userAccount.getBirthDate()
		).map(
			date -> {
				Calendar calendar = CalendarFactoryUtil.getCalendar();

				calendar.setTime(date);

				return calendar.get(calendarField);
			}
		).orElse(
			defaultValue
		);
	}

	private DTOConverterContext _getDTOConverterContext(long userId) {
		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			Collections.emptyMap(), null, contextHttpServletRequest, userId,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private long _getPrefixId(UserAccount userAccount) {
		return Optional.ofNullable(
			userAccount.getHonorificPrefix()
		).map(
			prefix -> ServiceBuilderListTypeUtil.getServiceBuilderListTypeId(
				ListTypeConstants.CONTACT_PREFIX, prefix)
		).orElse(
			0L
		);
	}

	private List<EmailAddress> _getServiceBuilderEmailAddresses(
		UserAccount userAccount) {

		return Optional.ofNullable(
			userAccount.getUserAccountContactInformation()
		).map(
			UserAccountContactInformation::getEmailAddresses
		).map(
			emailAddresses -> ListUtil.filter(
				transformToList(
					emailAddresses,
					emailAddress ->
						ServiceBuilderEmailAddressUtil.
							toServiceBuilderEmailAddress(
								emailAddress,
								ListTypeConstants.CONTACT_EMAIL_ADDRESS)),
				Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
	}

	private List<Phone> _getServiceBuilderPhones(UserAccount userAccount) {
		return Optional.ofNullable(
			userAccount.getUserAccountContactInformation()
		).map(
			UserAccountContactInformation::getTelephones
		).map(
			telephones -> ListUtil.filter(
				transformToList(
					telephones,
					telephone -> ServiceBuilderPhoneUtil.toServiceBuilderPhone(
						telephone, ListTypeConstants.CONTACT_PHONE)),
				Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
	}

	private long _getSuffixId(UserAccount userAccount) {
		return Optional.ofNullable(
			userAccount.getHonorificSuffix()
		).map(
			prefix -> ServiceBuilderListTypeUtil.getServiceBuilderListTypeId(
				ListTypeConstants.CONTACT_SUFFIX, prefix)
		).orElse(
			0L
		);
	}

	private Page<UserAccount> _getUserAccountsPage(
			Map<String, Map<String, String>> actions,
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			actions, booleanQueryUnsafeConsumer, filter, User.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toUserAccount(
				_userService.getUserById(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	private List<Website> _getWebsites(UserAccount userAccount) {
		return Optional.ofNullable(
			userAccount.getUserAccountContactInformation()
		).map(
			UserAccountContactInformation::getWebUrls
		).map(
			webUrls -> ListUtil.filter(
				transformToList(
					webUrls,
					webUrl -> ServiceBuilderWebsiteUtil.toServiceBuilderWebsite(
						ListTypeConstants.CONTACT_WEBSITE, webUrl)),
				Objects::nonNull)
		).orElse(
			Collections.emptyList()
		);
	}

	private UserAccount _toUserAccount(User user) throws Exception {
		return _userAccountResourceDTOConverter.toDTO(
			_getDTOConverterContext(user.getUserId()), user);
	}

	private static final EntityModel _entityModel =
		new UserAccountEntityModel();

	@Reference
	private AccountEntryUserRelService _accountEntryUserRelService;

	@Reference
	private AccountResourceDTOConverter _accountResourceDTOConverter;

	@Reference
	private AnnouncementsDeliveryLocalService
		_announcementsDeliveryLocalService;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private OrganizationResourceDTOConverter _organizationResourceDTOConverter;

	@Reference
	private UserAccountResourceDTOConverter _userAccountResourceDTOConverter;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}