/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountEntryUserRelService;
import com.liferay.announcements.kernel.service.AnnouncementsDeliveryLocalService;
import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.headless.admin.user.dto.v1_0.AccountBrief;
import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.dto.v1_0.OrganizationBrief;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.dto.v1_0.UserAccountContactInformation;
import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderEmailAddressUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderListTypeUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderPhoneUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.ServiceBuilderWebsiteUtil;
import com.liferay.headless.admin.user.internal.odata.entity.v1_0.UserAccountEntityModel;
import com.liferay.headless.admin.user.resource.v1_0.AccountRoleResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.captcha.CaptchaSettings;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.exception.UserLockoutException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManager;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.util.DTOConverterUtil;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user-account.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = UserAccountResource.class
)
public class UserAccountResourceImpl extends BaseUserAccountResourceImpl {

	@Override
	public void
			deleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(
				String accountExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		User user = _userLocalService.getUserByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		_accountEntryUserRelService.deleteAccountEntryUserRelByEmailAddress(
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, accountExternalReferenceCode),
			user.getEmailAddress());
	}

	@Override
	public void deleteAccountUserAccount(Long accountId, Long userAccountId)
		throws Exception {

		User user = _userLocalService.getUserById(
			contextCompany.getCompanyId(), userAccountId);

		deleteAccountUserAccountByEmailAddress(
			accountId, user.getEmailAddress());
	}

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
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, externalReferenceCode),
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

		deleteUserAccount(
			DTOConverterUtil.getModelPrimaryKey(
				_userResourceDTOConverter, externalReferenceCode));
	}

	@Override
	public UserAccount
			getAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(
				String accountExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.getAccountEntryUserRel(
				DTOConverterUtil.getModelPrimaryKey(
					_accountResourceDTOConverter, accountExternalReferenceCode),
				DTOConverterUtil.getModelPrimaryKey(
					_userResourceDTOConverter, externalReferenceCode));

		return _toUserAccount(
			_userService.getUserById(accountEntryUserRel.getAccountUserId()));
	}

	@Override
	public UserAccount getAccountUserAccount(Long accountId, Long userAccountId)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelLocalService.getAccountEntryUserRel(
				accountId, userAccountId);

		return _toUserAccount(
			_userService.getUserById(accountEntryUserRel.getAccountUserId()));
	}

	@Override
	public Page<UserAccount> getAccountUserAccountsByExternalReferenceCodePage(
			String externalReferenceCode, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return getAccountUserAccountsPage(
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, externalReferenceCode),
			search, filter, pagination, sorts);
	}

	@NestedField(parentClass = Account.class, value = "accountUserAccounts")
	@Override
	public Page<UserAccount> getAccountUserAccountsPage(
			Long accountId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			_getModelActions(
				Collections.singletonMap(
					ActionKeys.MANAGE_USERS,
					new String[] {
						"deleteAccountUserAccountByEmailAddress",
						"deleteAccountUserAccountByExternalReferenceCodeBy" +
							"EmailAddress",
						"deleteAccountUserAccountsByEmailAddress",
						"deleteAccountUserAccountsByExternalReferenceCodeBy" +
							"EmailAddress",
						"getAccountUserAccountsByExternalReferenceCodePage",
						"getAccountUserAccountsPage", "postAccountUserAccount",
						"postAccountUserAccountBatch",
						"postAccountUserAccountByEmailAddress",
						"postAccountUserAccountByExternalReferenceCode",
						"postAccountUserAccountByExternalReferenceCodeBy" +
							"EmailAddress",
						"postAccountUserAccountsByEmailAddress",
						"postAccountUserAccountsByExternalReferenceCodeBy" +
							"EmailAddress"
					}),
				accountId, _accountEntryModelResourcePermission),
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
				Collections.emptyMap(),
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
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

	@NestedField(
		parentClass = com.liferay.headless.admin.user.dto.v1_0.Organization.class,
		value = "userAccounts"
	)
	@Override
	public Page<UserAccount> getOrganizationUserAccountsPage(
			String organizationId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getUserAccountsPage(
			_getModelActions(
				Collections.singletonMap(
					ActionKeys.MANAGE_USERS,
					new String[] {"getOrganizationUserAccountsPage"}),
				DTOConverterUtil.getModelPrimaryKey(
					_organizationOrganizationDTOConverter, organizationId),
				_organizationModelResourcePermission),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"organizationIds",
						String.valueOf(
							DTOConverterUtil.getModelPrimaryKey(
								_organizationOrganizationDTOConverter,
								organizationId))),
					BooleanClauseOccur.MUST);
			},
			filter, search, pagination, sorts);
	}

	@Override
	public Page<UserAccount> getSiteUserAccountsPage(
			Long siteId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getUserAccountsPage(
			Collections.singletonMap(
				_formatActionMapKey("getSiteUserAccountsPage"),
				addAction(
					_formatActionMapKey("getSiteUserAccountsPage"),
					"getSiteUserAccountsPage", User.class.getName(), siteId)),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter("groupId", String.valueOf(siteId)),
					BooleanClauseOccur.MUST);
			},
			filter, search, pagination, sorts);
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

		return _getUserAccountsPage(
			HashMapBuilder.<String, Map<String, String>>putAll(
				_getCompanyScopeActions(
					ActionKeys.VIEW, new String[] {"getUserAccountsPage"},
					User.class.getName())
			).putAll(
				_getCompanyScopeActions(
					ActionKeys.ADD_USER,
					new String[] {
						"postUserAccount",
						"putUserAccountByExternalReferenceCode"
					},
					PortletKeys.PORTAL)
			).build(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter("userName", StringPool.BLANK),
					BooleanClauseOccur.MUST_NOT);
			},
			filter, search, pagination, sorts);
	}

	@Override
	public void
			postAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(
				String accountExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		User user = _userLocalService.getUserByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		_accountEntryUserRelService.addAccountEntryUserRelByEmailAddress(
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, accountExternalReferenceCode),
			user.getEmailAddress(), new long[0], null,
			new ServiceContext() {
				{
					setCompanyId(contextCompany.getCompanyId());
					setLanguageId(
						contextAcceptLanguage.getPreferredLanguageId());
					setUserId(contextUser.getUserId());
				}
			});
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
				_getSuffixId(userAccount), userAccount.getJobTitle(),
				ServiceContextFactory.getInstance(contextHttpServletRequest));

		User user = accountEntryUserRel.getUser();

		_usersAdmin.updateAddresses(
			Contact.class.getName(), user.getContactId(),
			_getAddresses(userAccount));
		_usersAdmin.updateEmailAddresses(
			Contact.class.getName(), user.getContactId(),
			_getServiceBuilderEmailAddresses(userAccount));
		_usersAdmin.updatePhones(
			Contact.class.getName(), user.getContactId(),
			_getServiceBuilderPhones(userAccount));
		_usersAdmin.updateWebsites(
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
				user.getLastName(), contact.getPrefixListTypeId(),
				contact.getSuffixListTypeId(), user.isMale(),
				_getBirthdayMonth(userAccount), _getBirthdayDay(userAccount),
				_getBirthdayYear(userAccount), sms, facebook, jabber, skype,
				twitter, user.getJobTitle(), user.getGroupIds(),
				user.getOrganizationIds(), user.getRoleIds(), null,
				user.getUserGroupIds(), _createServiceContext(userAccount)));
	}

	@Override
	public UserAccount postAccountUserAccountByEmailAddress(
			Long accountId, String emailAddress)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelService.addAccountEntryUserRelByEmailAddress(
				accountId, emailAddress, new long[0], null,
				new ServiceContext() {
					{
						setCompanyId(contextCompany.getCompanyId());
						setLanguageId(
							contextAcceptLanguage.getPreferredLanguageId());
						setUserId(contextUser.getUserId());
					}
				});

		return _toUserAccount(
			_userLocalService.getUser(accountEntryUserRel.getAccountUserId()));
	}

	@Override
	public UserAccount postAccountUserAccountByExternalReferenceCode(
			String externalReferenceCode, UserAccount userAccount)
		throws Exception {

		return postAccountUserAccount(
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, externalReferenceCode),
			userAccount);
	}

	@Override
	public void postAccountUserAccountByExternalReferenceCodeByEmailAddress(
			String externalReferenceCode, String emailAddress)
		throws Exception {

		postAccountUserAccountByEmailAddress(
			DTOConverterUtil.getModelPrimaryKey(
				_accountResourceDTOConverter, externalReferenceCode),
			emailAddress);
	}

	@Override
	public Page<UserAccount> postAccountUserAccountsByEmailAddress(
			Long accountId, String accountRoleIds, String[] emailAddresses)
		throws Exception {

		List<UserAccount> userAccounts = transformToList(
			emailAddresses,
			emailAddress -> postAccountUserAccountByEmailAddress(
				accountId, emailAddress));

		if (Validator.isNull(accountRoleIds)) {
			return Page.of(userAccounts);
		}

		String[] accountRoleIdsArray = StringUtil.split(
			accountRoleIds, CharPool.COMMA);

		for (UserAccount userAccount : userAccounts) {
			for (String accountRoleId : accountRoleIdsArray) {
				_accountRoleResource.
					postAccountAccountRoleUserAccountAssociation(
						accountId, GetterUtil.getLong(accountRoleId),
						userAccount.getId());
			}
		}

		return Page.of(
			transform(
				userAccounts,
				userAccount -> _toUserAccount(
					_userService.getUserByEmailAddress(
						contextCompany.getCompanyId(),
						userAccount.getEmailAddress()))));
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

		User user = null;

		boolean autoPassword = false;
		String password = userAccount.getPassword();

		if (Validator.isNull(password)) {
			autoPassword = true;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			contextHttpServletRequest);

		serviceContext.setExpandoBridgeAttributes(
			CustomFieldsUtil.toMap(
				User.class.getName(), contextCompany.getCompanyId(),
				userAccount.getCustomFields(),
				contextAcceptLanguage.getPreferredLocale()));

		if (contextUser.isGuestUser()) {
			if (_captchaSettings.isCreateAccountCaptchaEnabled()) {
				CaptchaUtil.check(contextHttpServletRequest);
			}

			user = _userService.addUser(
				contextCompany.getCompanyId(), autoPassword, password, password,
				false, userAccount.getAlternateName(),
				userAccount.getEmailAddress(),
				contextAcceptLanguage.getPreferredLocale(),
				userAccount.getGivenName(), userAccount.getAdditionalName(),
				userAccount.getFamilyName(), _getPrefixId(userAccount),
				_getSuffixId(userAccount), true, _getBirthdayMonth(userAccount),
				_getBirthdayDay(userAccount), _getBirthdayYear(userAccount),
				userAccount.getJobTitle(), new long[0], new long[0],
				new long[0], new long[0], true, serviceContext);

			PermissionThreadLocal.setPermissionChecker(
				_permissionCheckerFactory.create(user));

			_usersAdmin.updateAddresses(
				Contact.class.getName(), user.getContactId(),
				_getAddresses(userAccount));
			_usersAdmin.updateEmailAddresses(
				Contact.class.getName(), user.getContactId(),
				_getServiceBuilderEmailAddresses(userAccount));
			_usersAdmin.updatePhones(
				Contact.class.getName(), user.getContactId(),
				_getServiceBuilderPhones(userAccount));
			_usersAdmin.updateWebsites(
				Contact.class.getName(), user.getContactId(),
				_getWebsites(userAccount));
		}
		else {
			user = _userService.addUser(
				contextCompany.getCompanyId(), autoPassword, password, password,
				false, userAccount.getAlternateName(),
				userAccount.getEmailAddress(),
				contextAcceptLanguage.getPreferredLocale(),
				userAccount.getGivenName(), userAccount.getAdditionalName(),
				userAccount.getFamilyName(), _getPrefixId(userAccount),
				_getSuffixId(userAccount), true, _getBirthdayMonth(userAccount),
				_getBirthdayDay(userAccount), _getBirthdayYear(userAccount),
				userAccount.getJobTitle(), new long[0], new long[0],
				new long[0], new long[0], _getAddresses(userAccount),
				_getServiceBuilderEmailAddresses(userAccount),
				_getServiceBuilderPhones(userAccount),
				_getWebsites(userAccount), Collections.emptyList(), true,
				serviceContext);
		}

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
	public Response postUserAccountImage(
			Long userAccountId, MultipartBody multipartBody)
		throws Exception {

		_userService.updatePortrait(
			userAccountId, multipartBody.getBinaryFileAsBytes("image"));

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public UserAccount putUserAccount(
			Long userAccountId, UserAccount userAccount)
		throws Exception {

		AccountBrief[] accountBriefs = userAccount.getAccountBriefs();

		if (accountBriefs != null) {
			_accountEntryUserRelLocalService.
				deleteAccountEntryUserRelsByAccountUserId(userAccountId);

			for (AccountBrief accountBrief : accountBriefs) {
				_accountEntryUserRelLocalService.addAccountEntryUserRel(
					accountBrief.getId(), userAccountId);
			}
		}

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

		long[] organizationIds = user.getOrganizationIds();

		OrganizationBrief[] organizationBriefs =
			userAccount.getOrganizationBriefs();

		if (organizationBriefs != null) {
			Long[] ids = transform(
				organizationBriefs,
				organizationBrief -> organizationBrief.getId(), Long.class);

			organizationIds = ArrayUtil.toArray(ids);
		}

		_updatePassword(
			user, userAccount.getCurrentPassword(), userAccount.getPassword());

		return _toUserAccount(
			_userService.updateUser(
				userAccountId, null, null, null, false, null, null,
				userAccount.getAlternateName(), userAccount.getEmailAddress(),
				true, null, user.getLanguageId(), user.getTimeZoneId(),
				user.getGreeting(), user.getComments(),
				userAccount.getGivenName(), userAccount.getAdditionalName(),
				userAccount.getFamilyName(), _getPrefixId(userAccount),
				_getSuffixId(userAccount), true, _getBirthdayMonth(userAccount),
				_getBirthdayDay(userAccount), _getBirthdayYear(userAccount),
				sms, facebook, jabber, skype, twitter,
				userAccount.getJobTitle(), user.getGroupIds(), organizationIds,
				user.getRoleIds(),
				_userGroupRoleLocalService.getUserGroupRoles(userAccountId),
				user.getUserGroupIds(), _getAddresses(userAccount),
				_getServiceBuilderEmailAddresses(userAccount),
				_getServiceBuilderPhones(userAccount),
				_getWebsites(userAccount),
				_announcementsDeliveryLocalService.getUserDeliveries(
					userAccountId),
				_createServiceContext(userAccount)));
	}

	@Override
	public UserAccount putUserAccountByExternalReferenceCode(
			String externalReferenceCode, UserAccount userAccount)
		throws Exception {

		boolean autoPassword = true;
		String password = userAccount.getPassword();

		if (Validator.isNotNull(password)) {
			autoPassword = false;

			_checkCurrentPassword(
				_userLocalService.fetchUserByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId()),
				userAccount.getCurrentPassword());
		}

		User user = _userService.addOrUpdateUser(
			externalReferenceCode, contextUser.getUserId(),
			contextCompany.getCompanyId(), autoPassword, password, password,
			false, userAccount.getAlternateName(),
			userAccount.getEmailAddress(),
			contextAcceptLanguage.getPreferredLocale(),
			userAccount.getGivenName(), userAccount.getAdditionalName(),
			userAccount.getFamilyName(), _getPrefixId(userAccount),
			_getSuffixId(userAccount), true, _getBirthdayMonth(userAccount),
			_getBirthdayDay(userAccount), _getBirthdayYear(userAccount),
			userAccount.getJobTitle(), _getAddresses(userAccount),
			_getServiceBuilderEmailAddresses(userAccount),
			_getServiceBuilderPhones(userAccount), _getWebsites(userAccount),
			false, _createServiceContext(userAccount));

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

		AccountBrief[] accountBriefs = userAccount.getAccountBriefs();

		if (accountBriefs != null) {
			existingUserAccount.setAccountBriefs(accountBriefs);
		}

		OrganizationBrief[] organizationBriefs =
			userAccount.getOrganizationBriefs();

		if (organizationBriefs != null) {
			existingUserAccount.setOrganizationBriefs(organizationBriefs);
		}

		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation != null) {
			UserAccountContactInformation
				existingUserAccountContactInformation =
					existingUserAccount.getUserAccountContactInformation();

			EmailAddress[] emailAddresses =
				userAccountContactInformation.getEmailAddresses();

			if (emailAddresses != null) {
				existingUserAccountContactInformation.setEmailAddresses(
					emailAddresses);
			}

			String facebook = userAccountContactInformation.getFacebook();

			if (facebook != null) {
				existingUserAccountContactInformation.setFacebook(facebook);
			}

			String jabber = userAccountContactInformation.getJabber();

			if (jabber != null) {
				existingUserAccountContactInformation.setJabber(jabber);
			}

			PostalAddress[] postalAddresses =
				userAccountContactInformation.getPostalAddresses();

			if (postalAddresses != null) {
				existingUserAccountContactInformation.setPostalAddresses(
					postalAddresses);
			}

			String skype = userAccountContactInformation.getSkype();

			if (skype != null) {
				existingUserAccountContactInformation.setSkype(skype);
			}

			String sms = userAccountContactInformation.getSms();

			if (sms != null) {
				existingUserAccountContactInformation.setSms(sms);
			}

			Phone[] telephones = userAccountContactInformation.getTelephones();

			if (telephones != null) {
				existingUserAccountContactInformation.setTelephones(telephones);
			}

			String twitter = userAccountContactInformation.getTwitter();

			if (twitter != null) {
				existingUserAccountContactInformation.setTwitter(twitter);
			}

			WebUrl[] webUrls = userAccountContactInformation.getWebUrls();

			if (webUrls != null) {
				existingUserAccountContactInformation.setWebUrls(webUrls);
			}
		}
	}

	private void _checkCurrentPassword(User user, String currentPassword)
		throws Exception {

		if ((user == null) || (contextUser.getUserId() != user.getUserId())) {
			return;
		}

		if (Validator.isNull(currentPassword)) {
			throw new UserPasswordException.MustMatchCurrentPassword(
				user.getUserId());
		}

		int authResult = _userLocalService.authenticateByUserId(
			contextCompany.getCompanyId(), user.getUserId(), currentPassword,
			new HashMap<>(), new HashMap<>(), new HashMap<>());

		if (authResult == Authenticator.FAILURE) {
			if (user.isLockout()) {
				HttpServletRequest originalHttpServletRequest =
					_portal.getOriginalServletRequest(
						contextHttpServletRequest);
				HttpServletResponse httpServletResponse =
					contextHttpServletResponse;

				AuthenticatedSessionManagerUtil.logout(
					originalHttpServletRequest, httpServletResponse);

				throw new UserLockoutException.PasswordPolicyLockout(
					user, user.getPasswordPolicy());
			}

			throw new UserPasswordException.MustMatchCurrentPassword(
				user.getUserId());
		}
	}

	private ServiceContext _createServiceContext(UserAccount userAccount)
		throws Exception {

		return ServiceContextBuilder.create(
			contextCompany.getGroupId(), contextHttpServletRequest, null
		).expandoBridgeAttributes(
			CustomFieldsUtil.toMap(
				User.class.getName(), contextCompany.getCompanyId(),
				userAccount.getCustomFields(),
				contextAcceptLanguage.getPreferredLocale())
		).build();
	}

	private String _formatActionMapKey(String methodName) {
		return TextFormatter.format(methodName, TextFormatter.K);
	}

	private List<Address> _getAddresses(UserAccount userAccount) {
		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation == null) {
			return Collections.emptyList();
		}

		PostalAddress[] postalAddresses =
			userAccountContactInformation.getPostalAddresses();

		if (postalAddresses == null) {
			return Collections.emptyList();
		}

		return ListUtil.filter(
			transformToList(
				postalAddresses,
				_postalAddress ->
					ServiceBuilderAddressUtil.toServiceBuilderAddress(
						contextCompany.getCompanyId(), _postalAddress,
						ListTypeConstants.CONTACT_ADDRESS)),
			Objects::nonNull);
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

		Date date = userAccount.getBirthDate();

		if (date == null) {
			return defaultValue;
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.setTime(date);

		return calendar.get(calendarField);
	}

	private Map<String, Map<String, String>> _getCompanyScopeActions(
		String actionName, String[] methodNames, String resourceName) {

		Map<String, Map<String, String>> actions = new HashMap<>();

		for (String methodName : methodNames) {
			actions.put(
				_formatActionMapKey(methodName),
				addAction(actionName, methodName, resourceName, 0L));
		}

		return actions;
	}

	private DTOConverterContext _getDTOConverterContext(long userId) {
		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			_getModelActions(
				HashMapBuilder.put(
					ActionKeys.DELETE,
					new String[] {
						"deleteUserAccount",
						"deleteUserAccountByExternalReferenceCode"
					}
				).put(
					ActionKeys.UPDATE,
					new String[] {
						"putUserAccount",
						"putUserAccountByExternalReferenceCode",
						"patchUserAccount"
					}
				).put(
					ActionKeys.VIEW,
					new String[] {
						"getMyUserAccount", "getUserAccount",
						"getUserAccountByExternalReferenceCode"
					}
				).build(),
				userId, _userModelResourcePermission),
			null, contextHttpServletRequest, userId,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private Map<String, Map<String, String>> _getModelActions(
		Map<String, String[]> actionMethodMap, long id,
		ModelResourcePermission<?> modelResourcePermission) {

		Map<String, Map<String, String>> actions = new HashMap<>();

		for (Map.Entry<String, String[]> entry : actionMethodMap.entrySet()) {
			for (String methodName : entry.getValue()) {
				actions.put(
					_formatActionMapKey(methodName),
					addAction(
						entry.getKey(), id, methodName,
						modelResourcePermission));
			}
		}

		return actions;
	}

	private long _getPrefixId(UserAccount userAccount) {
		String prefix = userAccount.getHonorificPrefix();

		if (prefix == null) {
			return 0;
		}

		return ServiceBuilderListTypeUtil.getServiceBuilderListTypeId(
			ListTypeConstants.CONTACT_PREFIX, prefix);
	}

	private List<com.liferay.portal.kernel.model.EmailAddress>
		_getServiceBuilderEmailAddresses(UserAccount userAccount) {

		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation == null) {
			return Collections.emptyList();
		}

		EmailAddress[] emailAddresses =
			userAccountContactInformation.getEmailAddresses();

		if (emailAddresses == null) {
			return Collections.emptyList();
		}

		return ListUtil.filter(
			transformToList(
				emailAddresses,
				emailAddress ->
					ServiceBuilderEmailAddressUtil.toServiceBuilderEmailAddress(
						emailAddress, ListTypeConstants.CONTACT_EMAIL_ADDRESS)),
			Objects::nonNull);
	}

	private List<com.liferay.portal.kernel.model.Phone>
		_getServiceBuilderPhones(UserAccount userAccount) {

		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation == null) {
			return Collections.emptyList();
		}

		Phone[] phones = userAccountContactInformation.getTelephones();

		if (phones == null) {
			return Collections.emptyList();
		}

		return ListUtil.filter(
			transformToList(
				phones,
				telephone -> ServiceBuilderPhoneUtil.toServiceBuilderPhone(
					telephone, ListTypeConstants.CONTACT_PHONE)),
			Objects::nonNull);
	}

	private long _getSuffixId(UserAccount userAccount) {
		String honorificSuffix = userAccount.getHonorificSuffix();

		if (honorificSuffix == null) {
			return 0;
		}

		return ServiceBuilderListTypeUtil.getServiceBuilderListTypeId(
			ListTypeConstants.CONTACT_SUFFIX, honorificSuffix);
	}

	private Page<UserAccount> _getUserAccountsPage(
			Map<String, Map<String, String>> actions,
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Filter filter, String search, Pagination pagination, Sort[] sorts)
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
				actions,
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	private List<Website> _getWebsites(UserAccount userAccount) {
		UserAccountContactInformation userAccountContactInformation =
			userAccount.getUserAccountContactInformation();

		if (userAccountContactInformation == null) {
			return Collections.emptyList();
		}

		WebUrl[] webUrls = userAccountContactInformation.getWebUrls();

		if (webUrls == null) {
			return Collections.emptyList();
		}

		return ListUtil.filter(
			transformToList(
				webUrls,
				webUrl -> ServiceBuilderWebsiteUtil.toServiceBuilderWebsite(
					ListTypeConstants.CONTACT_WEBSITE, webUrl)),
			Objects::nonNull);
	}

	private boolean _isPasswordResetRequired(User user) throws Exception {
		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		boolean ldapPasswordPolicyEnabled =
			LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId());

		if ((user.getLastLoginDate() == null) &&
			(((passwordPolicy == null) && !ldapPasswordPolicyEnabled) ||
			 ((passwordPolicy != null) && passwordPolicy.isChangeable() &&
			  passwordPolicy.isChangeRequired()))) {

			return true;
		}

		return false;
	}

	private UserAccount _toUserAccount(
			Map<String, Map<String, String>> actions, long userId)
		throws Exception {

		return _userResourceDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), actions,
				_dtoConverterRegistry, userId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private UserAccount _toUserAccount(User user) throws Exception {
		return _userResourceDTOConverter.toDTO(
			_getDTOConverterContext(user.getUserId()), user);
	}

	private void _updatePassword(
			User user, String currentPassword, String password)
		throws Exception {

		if ((user == null) || Validator.isNull(password)) {
			return;
		}

		_checkCurrentPassword(user, currentPassword);

		_userService.updatePassword(
			user.getUserId(), password, password,
			_isPasswordResetRequired(user));

		String cookie = CookiesManagerUtil.getCookieValue(
			CookiesConstants.NAME_JSESSIONID, contextHttpServletRequest, false);

		if ((contextUser.getUserId() == user.getUserId()) && (cookie != null)) {
			String login = null;

			String authType = contextCompany.getAuthType();

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				login = user.getEmailAddress();
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				login = user.getScreenName();
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				login = String.valueOf(user.getUserId());
			}

			_authenticatedSessionManager.login(
				contextHttpServletRequest, contextHttpServletResponse, login,
				password, false, null);
		}
	}

	private static final EntityModel _entityModel =
		new UserAccountEntityModel();

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountEntryUserRelService _accountEntryUserRelService;

	@Reference(target = DTOConverterConstants.ACCOUNT_RESOURCE_DTO_CONVERTER)
	private DTOConverter<AccountEntry, Account> _accountResourceDTOConverter;

	@Reference
	private AccountRoleResource _accountRoleResource;

	@Reference
	private AnnouncementsDeliveryLocalService
		_announcementsDeliveryLocalService;

	@Reference
	private AuthenticatedSessionManager _authenticatedSessionManager;

	@Reference
	private CaptchaSettings _captchaSettings;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Organization)"
	)
	private ModelResourcePermission<Organization>
		_organizationModelResourcePermission;

	@Reference(
		target = DTOConverterConstants.ORGANIZATION_RESOURCE_DTO_CONVERTER
	)
	private DTOConverter
		<Organization, com.liferay.headless.admin.user.dto.v1_0.Organization>
			_organizationOrganizationDTOConverter;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.User)"
	)
	private ModelResourcePermission<User> _userModelResourcePermission;

	@Reference(target = DTOConverterConstants.USER_RESOURCE_DTO_CONVERTER)
	private DTOConverter<User, UserAccount> _userResourceDTOConverter;

	@Reference
	private UsersAdmin _usersAdmin;

	@Reference
	private UserService _userService;

}