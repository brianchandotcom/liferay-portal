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

package com.liferay.portal.security.sso.google.internal;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.sso.google.GoogleAuthorization;
import com.liferay.portal.security.sso.google.configuration.GoogleAuthorizationConfiguration;
import com.liferay.portal.security.sso.google.constants.GoogleConstants;
import com.liferay.portal.security.sso.google.exception.StrangersNotAllowedException;
import com.liferay.portal.security.sso.google.internal.constants.GoogleWebKeys;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Serves as the core implementation of the Google protocol.
 *
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.google.configuration.GoogleAuthorizationConfiguration",
	immediate = true, service = GoogleAuthorization.class
)
public class GoogleAuthorizationImpl implements GoogleAuthorization {

	@Override
	public User addOrUpdateUser(
			HttpSession session, long companyId, String authorizationCode,
			String returnRequestUri, List<String> scopes)
		throws Exception {

		GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow =
			getGoogleAuthorizationCodeFlow(companyId, scopes);

		GoogleAuthorizationCodeTokenRequest
			googleAuthorizationCodeTokenRequest =
				googleAuthorizationCodeFlow.newTokenRequest(authorizationCode);

		googleAuthorizationCodeTokenRequest.setRedirectUri(returnRequestUri);

		GoogleTokenResponse googleTokenResponse =
			googleAuthorizationCodeTokenRequest.execute();

		Credential credential =
			googleAuthorizationCodeFlow.createAndStoreCredential(
				googleTokenResponse, null);

		Userinfoplus userinfoplus = getUserinfoplus(credential);

		if (userinfoplus == null) {
			return null;
		}

		try {
			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> doAddOrUpdateUser(session, companyId, userinfoplus));
		}
		catch (Throwable t) {
			if (t instanceof PortalException) {
				throw (PortalException)t;
			}

			throw new Exception(t);
		}
	}

	@Override
	public String getLoginRedirect(
			long companyId, String returnRequestUri, List<String> scopes)
		throws Exception {

		GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow =
			getGoogleAuthorizationCodeFlow(companyId, scopes);

		GoogleAuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl =
			googleAuthorizationCodeFlow.newAuthorizationUrl();

		googleAuthorizationCodeRequestUrl =
			googleAuthorizationCodeRequestUrl.setRedirectUri(returnRequestUri);

		return googleAuthorizationCodeRequestUrl.build();
	}

	@Override
	public boolean isEnabled(long companyId) {
		GoogleAuthorizationConfiguration googleConfiguration =
			getGoogleConfiguration(companyId);

		if (Validator.isNull(googleConfiguration.clientId()) ||
			Validator.isNull(googleConfiguration.clientSecret())) {

			return false;
		}

		return googleConfiguration.enabled();
	}

	protected User addUser(long companyId, Userinfoplus userinfoplus)
		throws Exception {

		long creatorUserId = 0;
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		String emailAddress = userinfoplus.getEmail();
		String googleUserId = userinfoplus.getId();
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = userinfoplus.getGivenName();
		String middleName = StringPool.BLANK;
		String lastName = userinfoplus.getFamilyName();
		long prefixId = 0;
		long suffixId = 0;
		boolean male = Objects.equals(userinfoplus.getGender(), "male");
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = true;

		ServiceContext serviceContext = new ServiceContext();

		User user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, 0, openId, locale,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		user = _userLocalService.updateGoogleUserId(
			user.getUserId(), googleUserId);

		user = _userLocalService.updateLastLogin(
			user.getUserId(), user.getLoginIP());

		user = _userLocalService.updatePasswordReset(user.getUserId(), false);

		user = _userLocalService.updateEmailAddressVerified(
			user.getUserId(), true);

		return user;
	}

	protected User doAddOrUpdateUser(
			HttpSession session, long companyId, Userinfoplus userinfoplus)
		throws Exception {

		User user = null;

		String googleUserId = userinfoplus.getId();

		if (Validator.isNotNull(googleUserId)) {
			user = _userLocalService.fetchUserByGoogleUserId(
				companyId, googleUserId);

			if ((user != null) &&
				(user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE)) {

				session.setAttribute(
					GoogleWebKeys.GOOGLE_USER_ID, String.valueOf(googleUserId));
			}
		}

		String emailAddress = userinfoplus.getEmail();

		if ((user == null) && Validator.isNotNull(emailAddress)) {
			user = _userLocalService.fetchUserByEmailAddress(
				companyId, emailAddress);

			if ((user != null) &&
				(user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE)) {

				session.setAttribute(
					GoogleWebKeys.GOOGLE_USER_EMAIL_ADDRESS, emailAddress);
			}
		}

		if (user != null) {
			if (user.getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
				session.setAttribute(
					WebKeys.GOOGLE_INCOMPLETE_USER_ID, userinfoplus.getId());

				user.setEmailAddress(userinfoplus.getEmail());
				user.setFirstName(userinfoplus.getGivenName());
				user.setLastName(userinfoplus.getFamilyName());

				return user;
			}

			user = updateUser(user, userinfoplus);
		}
		else {
			_checkAllowUserCreation(companyId, userinfoplus);

			user = addUser(companyId, userinfoplus);

			session.setAttribute(
				GoogleWebKeys.GOOGLE_USER_EMAIL_ADDRESS, emailAddress);
		}

		return user;
	}

	protected GoogleAuthorizationCodeFlow getGoogleAuthorizationCodeFlow(
			long companyId, List<String> scopes)
		throws Exception {

		GoogleAuthorizationConfiguration googleAuthorizationConfiguration =
			getGoogleConfiguration(companyId);

		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow.Builder googleAuthorizationCodeFlowBuilder =
			new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory,
				googleAuthorizationConfiguration.clientId(),
				googleAuthorizationConfiguration.clientSecret(), scopes);

		googleAuthorizationCodeFlowBuilder =
			googleAuthorizationCodeFlowBuilder.setAccessType(
				_ONLINE_ACCESS_TYPE);

		return googleAuthorizationCodeFlowBuilder.build();
	}

	protected GoogleAuthorizationConfiguration getGoogleConfiguration(
		long companyId) {

		try {
			return _configurationProvider.getConfiguration(
				GoogleAuthorizationConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, GoogleConstants.SERVICE_NAME));
		}
		catch (ConfigurationException ce) {
			throw new SystemException(ce);
		}
	}

	protected Userinfoplus getUserinfoplus(Credential credentials)
		throws Exception {

		Oauth2.Builder builder = new Oauth2.Builder(
			new NetHttpTransport(), new JacksonFactory(), credentials);

		Oauth2 oauth2 = builder.build();

		Oauth2.Userinfo oAuth2Userinfo = oauth2.userinfo();

		Oauth2.Userinfo.Get oAuth2UserinfoGet = oAuth2Userinfo.get();

		Userinfoplus userinfoplus = oAuth2UserinfoGet.execute();

		if ((userinfoplus == null) || (userinfoplus.getId() == null)) {
			throw new PrincipalException();
		}

		return userinfoplus;
	}

	protected User updateUser(User user, Userinfoplus userinfoplus)
		throws Exception {

		String emailAddress = userinfoplus.getEmail();
		String firstName = userinfoplus.getGivenName();
		String lastName = userinfoplus.getFamilyName();
		boolean male = Objects.equals(userinfoplus.getGender(), "male");

		if (emailAddress.equals(user.getEmailAddress()) &&
			firstName.equals(user.getFirstName()) &&
			lastName.equals(user.getLastName()) && (male == user.isMale())) {

			return user;
		}

		Contact contact = user.getContact();

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(contact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;

		ServiceContext serviceContext = new ServiceContext();

		String googleUserId = userinfoplus.getId();

		if (!StringUtil.equalsIgnoreCase(
				googleUserId, user.getGoogleUserId())) {

			_userLocalService.updateGoogleUserId(
				user.getUserId(), googleUserId);
		}

		if (!StringUtil.equalsIgnoreCase(
				emailAddress, user.getEmailAddress())) {

			_userLocalService.updateEmailAddress(
				user.getUserId(), StringPool.BLANK, emailAddress, emailAddress);
		}

		_userLocalService.updateEmailAddressVerified(user.getUserId(), true);

		return _userLocalService.updateUser(
			user.getUserId(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, false, user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), user.getScreenName(), emailAddress,
			0, user.getOpenId(), true, null, user.getLanguageId(),
			user.getTimeZoneId(), user.getGreeting(), user.getComments(),
			firstName, user.getMiddleName(), lastName, contact.getPrefixId(),
			contact.getSuffixId(), male, birthdayMonth, birthdayDay,
			birthdayYear, contact.getSmsSn(), contact.getFacebookSn(),
			contact.getJabberSn(), contact.getSkypeSn(), contact.getTwitterSn(),
			contact.getJobTitle(), groupIds, organizationIds, roleIds,
			userGroupRoles, userGroupIds, serviceContext);
	}

	private void _checkAllowUserCreation(
			long companyId, Userinfoplus userinfoplus)
		throws PortalException {

		Company company = _companyLocalService.getCompany(companyId);

		if (!company.isStrangers()) {
			throw new StrangersNotAllowedException(companyId);
		}

		String emailAddress = userinfoplus.getEmail();

		if (company.hasCompanyMx(emailAddress) &&
			!company.isStrangersWithMx()) {

			throw new UserEmailAddressException.MustNotUseCompanyMx(
				emailAddress);
		}
	}

	private static final String _ONLINE_ACCESS_TYPE = "online";

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private UserLocalService _userLocalService;

}