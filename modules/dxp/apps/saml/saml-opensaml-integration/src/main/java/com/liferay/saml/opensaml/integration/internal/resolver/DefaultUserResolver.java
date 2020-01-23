/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.exportimport.UserImporter;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.opensaml.integration.resolver.UserResolver;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.SubjectException;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.joda.time.DateTime;

import org.opensaml.saml.saml2.core.NameIDType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MIN_VALUE,
	service = UserResolver.class
)
public class DefaultUserResolver implements UserResolver {

	@Override
	public User resolveUser(
			UserResolverSAMLContext userResolverSAMLContext,
			ServiceContext serviceContext)
		throws Exception {

		if (_log.isDebugEnabled()) {
			String subjectNameIdentifier =
				userResolverSAMLContext.resolveSubjectNameIdentifier();

			String subjectNameFormat =
				userResolverSAMLContext.resolveSubjectNameFormat();

			_log.debug(
				StringBundler.concat(
					"Resolving user with name ID format ", subjectNameFormat,
					" and value ", subjectNameIdentifier));
		}

		User user = null;

		long companyId = CompanyThreadLocal.getCompanyId();

		String subjectNameIdentifier = getSubjectNameIdentifier(
			userResolverSAMLContext);

		String subjectNameIdentifierType = getSubjectNameIdentifierType(
			userResolverSAMLContext);

		if (_samlProviderConfigurationHelper.isLDAPImportEnabled()) {
			user = importLdapUser(
				companyId, subjectNameIdentifier, subjectNameIdentifierType);
		}

		if (user == null) {
			return importUser(
				companyId, subjectNameIdentifier, subjectNameIdentifierType,
				userResolverSAMLContext, serviceContext);
		}

		return user;
	}

	@Reference(unbind = "-")
	public void setMetadataManager(MetadataManager metadataManager) {
		_metadataManager = metadataManager;
	}

	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		_samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	@Reference(unbind = "-")
	public void setUserImporter(UserImporter userImporter) {
		_userImporter = userImporter;
	}

	@Reference(unbind = "-")
	public void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected User addUser(
			long companyId, Map<String, List<Serializable>> attributesMap,
			UserResolverSAMLContext userResolverSAMLContext,
			ServiceContext serviceContext)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Adding user with attributes map " +
					MapUtil.toString(attributesMap));
		}

		SamlSpIdpConnection samlSpIdpConnection =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
				companyId, userResolverSAMLContext.resolvePeerEntityId());

		Company company = _companyLocalService.getCompany(companyId);
		String emailAddress = getValueAsString("emailAddress", attributesMap);

		if (samlSpIdpConnection.isUnknownUsersAreStrangers()) {
			if (!company.isStrangers()) {
				throw new SubjectException(
					"User is not known to the portal and company " + companyId +
						" does not allow strangers to create accounts");
			}
			else if (!company.isStrangersWithMx() &&
					 company.hasCompanyMx(emailAddress)) {

				throw new UserEmailAddressException.MustNotUseCompanyMx(
					emailAddress);
			}
		}

		long creatorUserId = 0;
		boolean autoPassword = true;

		String password1 = null;

		String password2 = null;

		boolean autoScreenName = false;
		String screenName = getValueAsString("screenName", attributesMap);
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = serviceContext.getLocale();
		String firstName = getValueAsString("firstName", attributesMap);
		String middleName = StringPool.BLANK;
		String lastName = getValueAsString("lastName", attributesMap);
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		String uuid = getValueAsString("uuid", attributesMap);

		serviceContext.setUuid(uuid);

		User user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		user = _userLocalService.updateEmailAddressVerified(
			user.getUserId(), true);

		user = _userLocalService.updatePasswordReset(user.getUserId(), false);

		Date modifiedDate = getValueAsDate("modifiedDate", attributesMap);

		if (modifiedDate != null) {
			user = _userLocalService.updateModifiedDate(
				user.getUserId(), modifiedDate);
		}

		return user;
	}

	protected Map<String, List<Serializable>> getAttributesMap(
		UserResolverSAMLContext userResolverSAMLContext) {

		String peerEntityId = userResolverSAMLContext.resolvePeerEntityId();

		try {
			String userAttributeMappings =
				_metadataManager.getUserAttributeMappings(peerEntityId);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Attributes mapping for ", peerEntityId, " ",
						userAttributeMappings));
			}

			Properties userAttributeMappingsProperties = new Properties();

			if (Validator.isNotNull(userAttributeMappings)) {
				userAttributeMappingsProperties = PropertiesUtil.load(
					userAttributeMappings);
			}

			return userResolverSAMLContext.
				resolveBearerAssertionAttributesWithMapping(
					userAttributeMappingsProperties);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception.getMessage(), exception);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(exception.getMessage());
			}
		}

		return Collections.emptyMap();
	}

	protected String getSubjectNameIdentifier(
		UserResolverSAMLContext userResolverSAMLContext) {

		return userResolverSAMLContext.resolveSubjectNameIdentifier();
	}

	protected String getSubjectNameIdentifierType(
		UserResolverSAMLContext userResolverSAMLContext) {

		String format = userResolverSAMLContext.resolveSubjectNameFormat();

		if (Validator.isNull(format)) {
			return _SUBJECT_NAME_TYPE_UNSPECIFIED;
		}

		if (format.equals(NameIDType.EMAIL)) {
			return _SUBJECT_NAME_TYPE_EMAIL_ADDRESS;
		}

		return _SUBJECT_NAME_TYPE_SCREENNAME;
	}

	protected User getUser(
			long companyId, String subjectNameIdentifier,
			String subjectNameIdentifierType)
		throws PortalException {

		try {
			if (subjectNameIdentifierType.endsWith(
					_SUBJECT_NAME_TYPE_EMAIL_ADDRESS)) {

				return _userLocalService.getUserByEmailAddress(
					companyId, subjectNameIdentifier);
			}
			else if (subjectNameIdentifierType.endsWith(
						_SUBJECT_NAME_TYPE_SCREENNAME)) {

				return _userLocalService.getUserByScreenName(
					companyId, subjectNameIdentifier);
			}
			else if (subjectNameIdentifierType.endsWith(
						_SUBJECT_NAME_TYPE_UUID)) {

				return _userLocalService.getUserByUuidAndCompanyId(
					subjectNameIdentifier, companyId);
			}
		}
		catch (NoSuchUserException noSuchUserException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchUserException, noSuchUserException);
			}
		}

		return null;
	}

	protected Date getValueAsDate(
		String key, Map<String, List<Serializable>> attributesMap) {

		List<Serializable> values = attributesMap.get(key);

		if (ListUtil.isEmpty(values)) {
			return null;
		}

		DateTime dateTime = new DateTime(values.get(0));

		return dateTime.toDate();
	}

	protected String getValueAsString(
		String key, Map<String, List<Serializable>> attributesMap) {

		List<Serializable> values = attributesMap.get(key);

		if (ListUtil.isEmpty(values)) {
			return null;
		}

		return String.valueOf(values.get(0));
	}

	protected User importLdapUser(
			long companyId, String subjectNameIdentifier,
			String subjectNameIdentifierType)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Importing user from LDAP with identifier ",
					subjectNameIdentifier, " of type ",
					subjectNameIdentifierType));
		}

		User user = null;

		if (subjectNameIdentifierType.endsWith(
				_SUBJECT_NAME_TYPE_EMAIL_ADDRESS)) {

			user = _userImporter.importUser(
				companyId, subjectNameIdentifier, StringPool.BLANK);
		}
		else {
			user = _userImporter.importUser(
				companyId, StringPool.BLANK, subjectNameIdentifier);
		}

		return user;
	}

	protected User importUser(
			long companyId, String subjectNameIdentifier,
			String subjectNameIdentifierType,
			UserResolverSAMLContext userResolverSAMLContext,
			ServiceContext serviceContext)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Importing user with identifier ", subjectNameIdentifier,
					" of type ", subjectNameIdentifierType));
		}

		Map<String, List<Serializable>> attributesMap = getAttributesMap(
			userResolverSAMLContext);

		User user = getUser(
			companyId, subjectNameIdentifier, subjectNameIdentifierType);

		if (user != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Found user " + user.toString());
			}

			user = updateUser(companyId, user, attributesMap, serviceContext);
		}
		else {
			user = addUser(
				companyId, attributesMap, userResolverSAMLContext,
				serviceContext);

			if (_log.isDebugEnabled()) {
				_log.debug("Added user " + user.toString());
			}
		}

		return user;
	}

	protected User updateUser(
			long companyId, User user,
			Map<String, List<Serializable>> attributesMap,
			ServiceContext serviceContext)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Updating user ", user.getUserId(), " with attributes map ",
					MapUtil.toString(attributesMap)));
		}

		Date modifiedDate = null;
		String screenName = null;
		String emailAddress = null;
		String firstName = null;
		String lastName = null;

		if (getValueAsDate("modifiedDate", attributesMap) != null) {
			modifiedDate = getValueAsDate("modifiedDate", attributesMap);
		}
		else {
			modifiedDate = user.getModifiedDate();
		}

		if (Validator.isNotNull(
				getValueAsString("screenName", attributesMap))) {

			screenName = getValueAsString("screenName", attributesMap);
		}
		else {
			screenName = user.getScreenName();
		}

		if (Validator.isNotNull(
				getValueAsString("emailAddress", attributesMap))) {

			emailAddress = getValueAsString("emailAddress", attributesMap);
		}
		else {
			emailAddress = user.getEmailAddress();
		}

		if (Validator.isNotNull(getValueAsString("firstName", attributesMap))) {
			firstName = getValueAsString("firstName", attributesMap);
		}
		else {
			firstName = user.getFirstName();
		}

		if (Validator.isNotNull(getValueAsString("lastName", attributesMap))) {
			lastName = getValueAsString("lastName", attributesMap);
		}
		else {
			lastName = user.getLastName();
		}

		Contact contact = user.getContact();

		if (!StringUtil.equalsIgnoreCase(
				emailAddress, user.getEmailAddress())) {

			user = _userLocalService.updateEmailAddress(
				user.getUserId(), StringPool.BLANK, emailAddress, emailAddress);

			user = _userLocalService.updateEmailAddressVerified(
				user.getUserId(), true);
		}

		if (!Objects.equals(user.getFirstName(), firstName) ||
			!Objects.equals(user.getLastName(), lastName) ||
			!Objects.equals(user.getScreenName(), screenName) ||
			!Objects.equals(user.getModifiedDate(), modifiedDate)) {

			Date oldModifiedDate = user.getModifiedDate();

			Calendar birthdayCalendar = CalendarFactoryUtil.getCalendar();

			birthdayCalendar.setTime(contact.getBirthday());

			user = _userLocalService.updateUser(
				user.getUserId(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, false, user.getReminderQueryQuestion(),
				user.getReminderQueryAnswer(), screenName, emailAddress,
				user.getFacebookId(), user.getOpenId(), true, null,
				user.getLanguageId(), user.getTimeZoneId(), user.getGreeting(),
				user.getComments(), firstName, user.getMiddleName(), lastName,
				contact.getPrefixId(), contact.getSuffixId(), user.getMale(),
				birthdayCalendar.get(Calendar.MONTH),
				birthdayCalendar.get(Calendar.DATE),
				birthdayCalendar.get(Calendar.YEAR), contact.getSmsSn(),
				contact.getFacebookSn(), contact.getJabberSn(),
				contact.getSkypeSn(), contact.getTwitterSn(),
				contact.getJobTitle(), null, null, null, null, null,
				serviceContext);

			if (!Objects.equals(oldModifiedDate, modifiedDate)) {
				user = _userLocalService.updateModifiedDate(
					user.getUserId(), modifiedDate);
			}
		}

		return user;
	}

	private static final String _SUBJECT_NAME_TYPE_EMAIL_ADDRESS =
		"emailAddress";

	private static final String _SUBJECT_NAME_TYPE_SCREENNAME = "screenName";

	private static final String _SUBJECT_NAME_TYPE_UNSPECIFIED = "unspecified";

	private static final String _SUBJECT_NAME_TYPE_UUID = "uuid";

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultUserResolver.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private MetadataManager _metadataManager;
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	private UserImporter _userImporter;
	private UserLocalService _userLocalService;

}