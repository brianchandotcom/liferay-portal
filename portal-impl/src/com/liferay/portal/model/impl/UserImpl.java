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

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.security.auth.EmailAddressGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ContactLocalServiceUtil;
import com.liferay.portal.kernel.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.WebsiteLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.RemotePreference;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.EmailAddressGeneratorFactory;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.users.admin.kernel.util.UserInitialsGeneratorUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

/**
 * Represents a portal user, providing access to the user's contact information,
 * groups, organizations, teams, user groups, roles, locale, timezone, and more.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Wesley Gong
 */
public class UserImpl extends UserBaseImpl {

	@Override
	public void addRemotePreference(RemotePreference remotePreference) {
		_remotePreferences.put(remotePreference.getName(), remotePreference);
	}

	@Override
	public Contact fetchContact() {
		if (_contact == _NULL_CONTACT) {
			return null;
		}

		if (_contact == null) {
			Contact contact = ContactLocalServiceUtil.fetchContact(
				getContactId());

			if (contact == null) {
				_contact = _NULL_CONTACT;
			}
			else {
				_contact = contact;
			}
		}

		return _contact;
	}

	@Override
	public String fetchPortraitURL(ThemeDisplay themeDisplay) {
		Contact contact = fetchContact();

		if (contact == null) {
			return StringPool.BLANK;
		}

		return UserConstants.getPortraitURL(
			themeDisplay.getPathImage(), contact.isMale(), getPortraitId(),
			getUserUuid());
	}

	/**
	 * Returns the user's addresses.
	 *
	 * @return the user's addresses
	 */
	@Override
	public List<Address> getAddresses() {
		return AddressLocalServiceUtil.getAddresses(
			getCompanyId(), Contact.class.getName(), getContactId());
	}

	/**
	 * Returns the user's birth date.
	 *
	 * @return the user's birth date
	 */
	@Override
	public Date getBirthday() throws PortalException {
		return getContact().getBirthday();
	}

	/**
	 * Returns the user's company's mail domain.
	 *
	 * @return the user's company's mail domain
	 */
	@Override
	public String getCompanyMx() throws PortalException {
		Company company = CompanyLocalServiceUtil.getCompanyById(
			getCompanyId());

		return company.getMx();
	}

	/**
	 * Returns the user's associated contact.
	 *
	 * @return the user's associated contact
	 * @see    Contact
	 */
	@Override
	public Contact getContact() throws PortalException {
		if ((_contact == null) || (_contact == _NULL_CONTACT)) {
			_contact = ContactLocalServiceUtil.getContact(getContactId());
		}

		return _contact;
	}

	/**
	 * Returns the user's digest.
	 *
	 * @return the user's digest
	 */
	@Override
	public String getDigest() {
		String digest = super.getDigest();

		if (Validator.isNull(digest) && !isPasswordEncrypted()) {
			digest = getDigest(getPassword());
		}

		return digest;
	}

	/**
	 * Returns a digest for the user, incorporating the password.
	 *
	 * @param  password a password to incorporate with the digest
	 * @return a digest for the user, incorporating the password
	 */
	@Override
	public String getDigest(String password) {
		if (Validator.isNull(getScreenName())) {
			throw new IllegalStateException("Screen name is null");
		}
		else if (Validator.isNull(getEmailAddress())) {
			throw new IllegalStateException("Email address is null");
		}

		StringBundler sb = new StringBundler(5);

		String digest1 = DigesterUtil.digestHex(
			Digester.MD5, getEmailAddress(), Portal.PORTAL_REALM, password);

		sb.append(digest1);

		sb.append(StringPool.COMMA);

		String digest2 = DigesterUtil.digestHex(
			Digester.MD5, getScreenName(), Portal.PORTAL_REALM, password);

		sb.append(digest2);

		sb.append(StringPool.COMMA);

		String digest3 = DigesterUtil.digestHex(
			Digester.MD5, String.valueOf(getUserId()), Portal.PORTAL_REALM,
			password);

		sb.append(digest3);

		return sb.toString();
	}

	/**
	 * Returns the user's primary email address, or a blank string if the
	 * address is fake.
	 *
	 * @return the user's primary email address, or a blank string if the
	 *         address is fake
	 */
	@Override
	public String getDisplayEmailAddress() {
		String emailAddress = super.getEmailAddress();

		EmailAddressGenerator emailAddressGenerator =
			EmailAddressGeneratorFactory.getInstance();

		if (emailAddressGenerator.isFake(emailAddress)) {
			emailAddress = StringPool.BLANK;
		}

		return emailAddress;
	}

	/**
	 * Returns the user's display URL based on the theme display, discounting
	 * the URL of the user's default intranet site home page.
	 *
	 * <p>
	 * The logic for the display URL to return is as follows:
	 * </p>
	 *
	 * <ol>
	 * <li>
	 * If the user is the guest user, return an empty string.
	 * </li>
	 * <li>
	 * Else, if a friendly URL is available for the user's profile, return that
	 * friendly URL.
	 * </li>
	 * <li>
	 * Otherwise, return the URL of the user's default extranet site home page.
	 * </li>
	 * </ol>
	 *
	 * @param  themeDisplay the theme display
	 * @return the user's display URL
	 */
	@Override
	public String getDisplayURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getDisplayURL(themeDisplay, false);
	}

	/**
	 * Returns the user's display URL based on the theme display.
	 *
	 * <p>
	 * The logic for the display URL to return is as follows:
	 * </p>
	 *
	 * <ol>
	 * <li>
	 * If the user is the guest user, return an empty string.
	 * </li>
	 * <li>
	 * Else, if a friendly URL is available for the user's profile, return that
	 * friendly URL.
	 * </li>
	 * <li>
	 * Else, if <code>privateLayout</code> is <code>true</code>, return the URL
	 * of the user's default intranet site home page.
	 * </li>
	 * <li>
	 * Otherwise, return the URL of the user's default extranet site home page.
	 * </li>
	 * </ol>
	 *
	 * @param  themeDisplay the theme display
	 * @param  privateLayout whether to use the URL of the user's default
	 *         intranet (versus extranet) site home page, if no friendly URL is
	 *         available for the user's profile
	 * @return the user's display URL
	 * @throws PortalException
	 */
	@Override
	public String getDisplayURL(
			ThemeDisplay themeDisplay, boolean privateLayout)
		throws PortalException {

		if (isDefaultUser() || (themeDisplay == null)) {
			return StringPool.BLANK;
		}

		String profileFriendlyURL = getProfileFriendlyURL();

		if (profileFriendlyURL != null) {
			String portalURL = themeDisplay.getPortalURL();

			return PortalUtil.addPreservedParameters(
				themeDisplay,
				portalURL.concat(
					PortalUtil.getPathContext()
				).concat(
					profileFriendlyURL
				));
		}

		Group group = getGroup();

		return group.getDisplayURL(themeDisplay, privateLayout);
	}

	/**
	 * Returns the user's email addresses.
	 *
	 * @return the user's email addresses
	 */
	@Override
	public List<EmailAddress> getEmailAddresses() {
		return EmailAddressLocalServiceUtil.getEmailAddresses(
			getCompanyId(), Contact.class.getName(), getContactId());
	}

	/**
	 * Returns <code>true</code> if the user is female.
	 *
	 * @return <code>true</code> if the user is female; <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean getFemale() throws PortalException {
		return !getMale();
	}

	/**
	 * Returns the user's full name.
	 *
	 * @return the user's full name
	 */
	@AutoEscape
	@Override
	public String getFullName() {
		return getFullName(false, false);
	}

	/**
	 * Returns the user's full name.
	 *
	 * @return the user's full name
	 */
	@AutoEscape
	@Override
	public String getFullName(boolean usePrefix, boolean useSuffix) {
		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		long prefixId = 0;

		if (usePrefix) {
			Contact contact = fetchContact();

			if (contact != null) {
				prefixId = contact.getPrefixId();
			}
		}

		long suffixId = 0;

		if (useSuffix) {
			Contact contact = fetchContact();

			if (contact != null) {
				suffixId = contact.getSuffixId();
			}
		}

		return fullNameGenerator.getLocalizedFullName(
			getFirstName(), getMiddleName(), getLastName(), getLocale(),
			prefixId, suffixId);
	}

	@Override
	public Group getGroup() {
		return GroupLocalServiceUtil.fetchUserGroup(
			getCompanyId(), getUserId());
	}

	@Override
	public long getGroupId() {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public long[] getGroupIds() {
		return UserLocalServiceUtil.getGroupPrimaryKeys(getUserId());
	}

	@Override
	public List<Group> getGroups() {
		return GroupLocalServiceUtil.getUserGroups(getUserId());
	}

	@Override
	public String getInitials() {
		String initials = UserInitialsGeneratorUtil.getInitials(this);

		if (initials == null) {
			String firstInitial = StringUtil.shorten(getFirstName(), 1);
			String lastInitial = StringUtil.shorten(getLastName(), 1);

			initials = StringUtil.toUpperCase(firstInitial.concat(lastInitial));
		}

		return initials;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public String getLogin() throws PortalException {
		String login = null;

		Company company = CompanyLocalServiceUtil.getCompanyById(
			getCompanyId());

		String authType = company.getAuthType();

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			login = getEmailAddress();
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			login = getScreenName();
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			login = String.valueOf(getUserId());
		}

		return login;
	}

	/**
	 * Returns <code>true</code> if the user is male.
	 *
	 * @return <code>true</code> if the user is male; <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean getMale() throws PortalException {
		return getContact().getMale();
	}

	@Override
	public List<Group> getMySiteGroups() throws PortalException {
		return getMySiteGroups(null, QueryUtil.ALL_POS);
	}

	@Override
	public List<Group> getMySiteGroups(int max) throws PortalException {
		return getMySiteGroups(null, max);
	}

	@Override
	public List<Group> getMySiteGroups(String[] classNames, int max)
		throws PortalException {

		return GroupServiceUtil.getUserSitesGroups(
			getUserId(), classNames, max);
	}

	@Override
	public long[] getOrganizationIds() throws PortalException {
		return getOrganizationIds(false);
	}

	@Override
	public long[] getOrganizationIds(boolean includeAdministrative)
		throws PortalException {

		return OrganizationLocalServiceUtil.getUserOrganizationIds(
			getUserId(), includeAdministrative);
	}

	@Override
	public List<Organization> getOrganizations() throws PortalException {
		return getOrganizations(false);
	}

	@Override
	public List<Organization> getOrganizations(boolean includeAdministrative)
		throws PortalException {

		return OrganizationLocalServiceUtil.getUserOrganizations(
			getUserId(), includeAdministrative);
	}

	@Override
	public String getOriginalEmailAddress() {
		return super.getOriginalEmailAddress();
	}

	@Override
	public boolean getPasswordModified() {
		return _passwordModified;
	}

	@Override
	public PasswordPolicy getPasswordPolicy() throws PortalException {
		if (_passwordPolicy == null) {
			_passwordPolicy =
				PasswordPolicyLocalServiceUtil.getPasswordPolicyByUser(this);
		}

		return _passwordPolicy;
	}

	@Override
	public String getPasswordUnencrypted() {
		return _passwordUnencrypted;
	}

	@Override
	public List<Phone> getPhones() {
		return PhoneLocalServiceUtil.getPhones(
			getCompanyId(), Contact.class.getName(), getContactId());
	}

	@Override
	public String getPortraitURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return UserConstants.getPortraitURL(
			themeDisplay.getPathImage(), isMale(), getPortraitId(),
			getUserUuid());
	}

	@Override
	public int getPrivateLayoutsPageCount() throws PortalException {
		return LayoutLocalServiceUtil.getLayoutsCount(this, true);
	}

	@Override
	public int getPublicLayoutsPageCount() throws PortalException {
		return LayoutLocalServiceUtil.getLayoutsCount(this, false);
	}

	@Override
	public Set<String> getReminderQueryQuestions() throws PortalException {
		Set<String> questions = new TreeSet<>();

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(getUserId());

		for (Organization organization : organizations) {
			Set<String> organizationQuestions =
				organization.getReminderQueryQuestions(getLanguageId());

			if (organizationQuestions.isEmpty()) {
				Organization parentOrganization =
					organization.getParentOrganization();

				while (organizationQuestions.isEmpty() &&
					   (parentOrganization != null)) {

					organizationQuestions =
						parentOrganization.getReminderQueryQuestions(
							getLanguageId());

					parentOrganization =
						parentOrganization.getParentOrganization();
				}
			}

			questions.addAll(organizationQuestions);
		}

		if (questions.isEmpty()) {
			Set<String> defaultQuestions = SetUtil.fromArray(
				PropsUtil.getArray(PropsKeys.USERS_REMINDER_QUERIES_QUESTIONS));

			questions.addAll(defaultQuestions);
		}

		return questions;
	}

	@Override
	public RemotePreference getRemotePreference(String name) {
		return _remotePreferences.get(name);
	}

	@Override
	public Iterable<RemotePreference> getRemotePreferences() {
		Collection<RemotePreference> values = _remotePreferences.values();

		return Collections.unmodifiableCollection(values);
	}

	@Override
	public long[] getRoleIds() {
		return UserLocalServiceUtil.getRolePrimaryKeys(getUserId());
	}

	@Override
	public List<Role> getRoles() {
		return RoleLocalServiceUtil.getUserRoles(getUserId());
	}

	@Override
	public List<Group> getSiteGroups() throws PortalException {
		return getSiteGroups(false);
	}

	@Override
	public List<Group> getSiteGroups(boolean includeAdministrative)
		throws PortalException {

		return GroupLocalServiceUtil.getUserSitesGroups(
			getUserId(), includeAdministrative);
	}

	@Override
	public long[] getTeamIds() {
		return UserLocalServiceUtil.getTeamPrimaryKeys(getUserId());
	}

	@Override
	public List<Team> getTeams() {
		return TeamLocalServiceUtil.getUserTeams(getUserId());
	}

	@Override
	public TimeZone getTimeZone() {
		return _timeZone;
	}

	@Override
	public Date getUnlockDate() throws PortalException {
		return getUnlockDate(getPasswordPolicy());
	}

	@Override
	public Date getUnlockDate(PasswordPolicy passwordPolicy) {
		Date lockoutDate = getLockoutDate();

		return new Date(
			lockoutDate.getTime() +
				(passwordPolicy.getLockoutDuration() * 1000));
	}

	@Override
	public long[] getUserGroupIds() {
		return UserLocalServiceUtil.getUserGroupPrimaryKeys(getUserId());
	}

	@Override
	public List<UserGroup> getUserGroups() {
		return UserGroupLocalServiceUtil.getUserUserGroups(getUserId());
	}

	@Override
	public List<Website> getWebsites() {
		return WebsiteLocalServiceUtil.getWebsites(
			getCompanyId(), Contact.class.getName(), getContactId());
	}

	@Override
	public boolean hasCompanyMx() throws PortalException {
		return hasCompanyMx(getEmailAddress());
	}

	@Override
	public boolean hasCompanyMx(String emailAddress) throws PortalException {
		if (Validator.isNull(emailAddress)) {
			return false;
		}

		Company company = CompanyLocalServiceUtil.getCompanyById(
			getCompanyId());

		return company.hasCompanyMx(emailAddress);
	}

	@Override
	public boolean hasMySites() throws PortalException {
		if (isDefaultUser()) {
			return false;
		}

		if ((PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED ||
			 PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) &&
			(getUserId() == PrincipalThreadLocal.getUserId())) {

			return true;
		}

		List<Group> groups = getMySiteGroups(1);

		return !groups.isEmpty();
	}

	@Override
	public boolean hasOrganization() {
		return OrganizationLocalServiceUtil.hasUserOrganizations(getUserId());
	}

	@Override
	public boolean hasPrivateLayouts() throws PortalException {
		return LayoutLocalServiceUtil.hasLayouts(this, true);
	}

	@Override
	public boolean hasPublicLayouts() throws PortalException {
		return LayoutLocalServiceUtil.hasLayouts(this, false);
	}

	@Override
	public boolean hasReminderQuery() {
		if (Validator.isNotNull(getReminderQueryQuestion()) &&
			Validator.isNotNull(getReminderQueryAnswer())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isActive() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isEmailAddressComplete() {
		if (isDefaultUser()) {
			return true;
		}

		if (Validator.isNull(getEmailAddress()) ||
			(PropsValues.USERS_EMAIL_ADDRESS_REQUIRED &&
			 Validator.isNull(getDisplayEmailAddress()))) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isEmailAddressVerificationComplete() {
		if (isDefaultUser() || isEmailAddressVerified()) {
			return true;
		}

		boolean emailAddressVerificationRequired = false;

		try {
			Company company = CompanyLocalServiceUtil.getCompany(
				getCompanyId());

			emailAddressVerificationRequired = company.isStrangersVerify();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		if (emailAddressVerificationRequired) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isFemale() throws PortalException {
		return getFemale();
	}

	@Override
	public boolean isMale() throws PortalException {
		return getMale();
	}

	@Override
	public boolean isPasswordModified() {
		return _passwordModified;
	}

	@Override
	public boolean isReminderQueryComplete() {
		if (isDefaultUser()) {
			return true;
		}

		if (PropsValues.USERS_REMINDER_QUERIES_ENABLED &&
			(Validator.isNull(getReminderQueryQuestion()) ||
			 Validator.isNull(getReminderQueryAnswer()))) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isSetupComplete() {
		if (isDefaultUser()) {
			return true;
		}

		if (isEmailAddressComplete() && isEmailAddressVerificationComplete() &&
			!_isRequirePasswordReset() && isReminderQueryComplete() &&
			isTermsOfUseComplete()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isTermsOfUseComplete() {
		if (isDefaultUser() || isAgreedToTermsOfUse()) {
			return true;
		}

		boolean termsOfUseRequired = PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.TERMS_OF_USE_REQUIRED,
			PropsValues.TERMS_OF_USE_REQUIRED);

		if (termsOfUseRequired) {
			return false;
		}

		return true;
	}

	@Override
	public void setLanguageId(String languageId) {
		_locale = LocaleUtil.fromLanguageId(languageId);

		super.setLanguageId(LocaleUtil.toLanguageId(_locale));
	}

	@Override
	public void setPasswordModified(boolean passwordModified) {
		_passwordModified = passwordModified;
	}

	@Override
	public void setPasswordUnencrypted(String passwordUnencrypted) {
		_passwordUnencrypted = passwordUnencrypted;
	}

	@Override
	public void setTimeZoneId(String timeZoneId) {
		if (Validator.isNull(timeZoneId)) {
			TimeZone defaultTimeZone = TimeZoneUtil.getDefault();

			timeZoneId = defaultTimeZone.getID();
		}

		_timeZone = TimeZoneUtil.getTimeZone(timeZoneId);

		super.setTimeZoneId(timeZoneId);
	}

	protected String getProfileFriendlyURL() {
		if (!_HAS_USERS_PROFILE_FRIENDLY_URL) {
			return null;
		}

		String normalizedScreenName = FriendlyURLNormalizerUtil.normalize(
			getScreenName());

		return StringUtil.replace(
			PropsValues.USERS_PROFILE_FRIENDLY_URL,
			new String[] {"${liferay:screenName}", "${liferay:userId}"},
			new String[] {
				HtmlUtil.escapeURL(normalizedScreenName),
				String.valueOf(getUserId())
			});
	}

	private boolean _isRequirePasswordReset() {
		if (!isPasswordReset() ||
			((_passwordPolicy != null) && !_passwordPolicy.isChangeable())) {

			return false;
		}

		return true;
	}

	private static final boolean _HAS_USERS_PROFILE_FRIENDLY_URL =
		Validator.isNotNull(PropsValues.USERS_PROFILE_FRIENDLY_URL);

	private static final Contact _NULL_CONTACT = new ContactImpl();

	private static final Log _log = LogFactoryUtil.getLog(UserImpl.class);

	private Contact _contact;
	private Locale _locale;
	private boolean _passwordModified;
	private PasswordPolicy _passwordPolicy;
	private String _passwordUnencrypted;
	private final transient Map<String, RemotePreference> _remotePreferences =
		new HashMap<>();
	private TimeZone _timeZone;

}