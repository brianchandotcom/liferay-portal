/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.UserConstants;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.EmailAddressGenerator;
import com.liferay.portal.security.auth.EmailAddressGeneratorFactory;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.WebsiteLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Wesley Gong
 */
public class UserImpl extends UserBaseImpl {

	public UserImpl() {
	}

	@Override
	public List<Address> getAddresses() throws SystemException {
		return AddressLocalServiceUtil.getAddresses(
			getCompanyId(), Contact.class.getName(), getContactId());
	}

	@Override
	public Date getBirthday() throws PortalException, SystemException {
		return getContact().getBirthday();
	}

	@Override
	public String getCompanyMx() throws PortalException, SystemException {
		Company company = CompanyLocalServiceUtil.getCompanyById(
			getCompanyId());

		return company.getMx();
	}

	@Override
	public Contact getContact() throws PortalException, SystemException {
		try {
			ShardUtil.pushCompanyService(getCompanyId());

			return ContactLocalServiceUtil.getContact(getContactId());
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	@Override
	public String getDigest() {
		String digest = super.getDigest();

		if (Validator.isNull(digest) && !isPasswordEncrypted()) {
			digest = getDigest(getPassword());
		}

		return digest;
	}

	@Override
	public String getDigest(String password) {
		if (Validator.isNull(getScreenName())) {
			throw new IllegalStateException("Screen name cannot be null");
		}
		else if (Validator.isNull(getEmailAddress())) {
			throw new IllegalStateException("Email address cannot be null");
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

	@Override
	public String getDisplayURL(String portalURL, String mainPath)
		throws PortalException, SystemException {

		return getDisplayURL(portalURL, mainPath, false);
	}

	@Override
	public String getDisplayURL(
			String portalURL, String mainPath, boolean privateLayout)
		throws PortalException, SystemException {

		if (isDefaultUser()) {
			return StringPool.BLANK;
		}

		String profileFriendlyURL = getProfileFriendlyURL();

		if (Validator.isNotNull(profileFriendlyURL)) {
			return portalURL.concat(PortalUtil.getPathContext()).concat(
				profileFriendlyURL);
		}

		Group group = getGroup();

		int publicLayoutsPageCount = group.getPublicLayoutsPageCount();

		if (publicLayoutsPageCount > 0) {
			StringBundler sb = new StringBundler(5);

			sb.append(portalURL);
			sb.append(mainPath);
			sb.append("/my_sites/view?groupId=");
			sb.append(group.getGroupId());

			if (privateLayout) {
				sb.append("&privateLayout=1");
			}
			else {
				sb.append("&privateLayout=0");
			}

			return sb.toString();
		}

		return StringPool.BLANK;
	}

	@Override
	public String getDisplayURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getDisplayURL(
			themeDisplay.getPortalURL(), themeDisplay.getPathMain(), false);
	}

	@Override
	public String getDisplayURL(
			ThemeDisplay themeDisplay, boolean privateLayout)
		throws PortalException, SystemException {

		return getDisplayURL(
			themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
			privateLayout);
	}

	@Override
	public List<EmailAddress> getEmailAddresses() throws SystemException {
		return EmailAddressLocalServiceUtil.getEmailAddresses(
			getCompanyId(), Contact.class.getName(), getContactId());
	}

	@Override
	public boolean getFemale() throws PortalException, SystemException {
		return !getMale();
	}

	@AutoEscape
	@Override
	public String getFullName() {
		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		return fullNameGenerator.getFullName(
			getFirstName(), getMiddleName(), getLastName());
	}

	@Override
	public Group getGroup() throws PortalException, SystemException {
		return GroupLocalServiceUtil.getUserGroup(getCompanyId(), getUserId());
	}

	@Override
	public long getGroupId() throws PortalException, SystemException {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public long[] getGroupIds() throws SystemException {
		List<Group> groups = getGroups();

		long[] groupIds = new long[groups.size()];

		for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);

			groupIds[i] = group.getGroupId();
		}

		return groupIds;
	}

	@Override
	public List<Group> getGroups() throws SystemException {
		return GroupLocalServiceUtil.getUserGroups(getUserId());
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public String getLogin() throws PortalException, SystemException {
		String login = null;

		Company company = CompanyLocalServiceUtil.getCompanyById(
			getCompanyId());

		if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_EA)) {
			login = getEmailAddress();
		}
		else if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_SN)) {
			login = getScreenName();
		}
		else if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_ID)) {
			login = String.valueOf(getUserId());
		}

		return login;
	}

	@Override
	public boolean getMale() throws PortalException, SystemException {
		return getContact().getMale();
	}

	@Override
	public List<Group> getMySites() throws PortalException, SystemException {
		return getMySites(null, false, QueryUtil.ALL_POS);
	}

	@Override
	public List<Group> getMySites(boolean includeControlPanel, int max)
		throws PortalException, SystemException {

		return getMySites(null, includeControlPanel, max);
	}

	@Override
	public List<Group> getMySites(int max)
		throws PortalException, SystemException {

		return getMySites(null, false, max);
	}

	@Override
	public List<Group> getMySites(
			String[] classNames, boolean includeControlPanel, int max)
		throws PortalException, SystemException {

		ThreadLocalCache<List<Group>> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, UserImpl.class.getName());

		String key = StringUtil.toHexString(max);

		if ((classNames != null) && (classNames.length > 0)) {
			key = StringUtil.merge(classNames).concat(StringPool.POUND).concat(
				key);
		}

		key = key.concat(StringPool.POUND).concat(
			String.valueOf(includeControlPanel));

		List<Group> myPlaces = threadLocalCache.get(key);

		if (myPlaces != null) {
			return myPlaces;
		}

		myPlaces = GroupServiceUtil.getUserPlacesGroups(
			getUserId(), classNames, includeControlPanel, max);

		threadLocalCache.put(key, myPlaces);

		return myPlaces;
	}

	@Override
	public List<Group> getMySites(String[] classNames, int max)
		throws PortalException, SystemException {

		return getMySites(classNames, false, max);
	}

	@Override
	public long[] getOrganizationIds() throws PortalException, SystemException {
		return getOrganizationIds(false);
	}

	@Override
	public long[] getOrganizationIds(boolean includeAdministrative)
		throws PortalException, SystemException {

		List<Organization> organizations = getOrganizations(
			includeAdministrative);

		long[] organizationIds = new long[organizations.size()];

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			organizationIds[i] = organization.getOrganizationId();
		}

		return organizationIds;
	}

	@Override
	public List<Organization> getOrganizations()
		throws PortalException, SystemException {

		return getOrganizations(false);
	}

	@Override
	public List<Organization> getOrganizations(boolean includeAdministrative)
		throws PortalException, SystemException {

		return OrganizationLocalServiceUtil.getUserOrganizations(
			getUserId(), includeAdministrative);
	}

	@Override
	public boolean getPasswordModified() {
		return _passwordModified;
	}

	@Override
	public PasswordPolicy getPasswordPolicy()
		throws PortalException, SystemException {

		if (_passwordPolicy == null) {
			_passwordPolicy =
				PasswordPolicyLocalServiceUtil.getPasswordPolicyByUserId(
					getUserId());
		}

		return _passwordPolicy;
	}

	@Override
	public String getPasswordUnencrypted() {
		return _passwordUnencrypted;
	}

	@Override
	public List<Phone> getPhones() throws SystemException {
		return PhoneLocalServiceUtil.getPhones(
			getCompanyId(), Contact.class.getName(), getContactId());
	}

	@Override
	public String getPortraitURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return UserConstants.getPortraitURL(
			themeDisplay.getPathImage(), isMale(), getPortraitId());
	}

	@Override
	public int getPrivateLayoutsPageCount()
		throws PortalException, SystemException {

		return LayoutLocalServiceUtil.getLayoutsCount(this, true);
	}

	@Override
	public int getPublicLayoutsPageCount()
		throws PortalException, SystemException {

		return LayoutLocalServiceUtil.getLayoutsCount(this, false);
	}

	@Override
	public Set<String> getReminderQueryQuestions()
		throws PortalException, SystemException {

		Set<String> questions = new TreeSet<String>();

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(getUserId());

		for (Organization organization : organizations) {
			Set<String> organizationQuestions =
				organization.getReminderQueryQuestions(getLanguageId());

			if (organizationQuestions.size() == 0) {
				Organization parentOrganization =
					organization.getParentOrganization();

				while ((organizationQuestions.size() == 0) &&
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

		if (questions.size() == 0) {
			Set<String> defaultQuestions = SetUtil.fromArray(
				PropsUtil.getArray(PropsKeys.USERS_REMINDER_QUERIES_QUESTIONS));

			questions.addAll(defaultQuestions);
		}

		return questions;
	}

	@Override
	public long[] getRoleIds() throws SystemException {
		List<Role> roles = getRoles();

		long[] roleIds = new long[roles.size()];

		for (int i = 0; i < roles.size(); i++) {
			Role role = roles.get(i);

			roleIds[i] = role.getRoleId();
		}

		return roleIds;
	}

	@Override
	public List<Role> getRoles() throws SystemException {
		return RoleLocalServiceUtil.getUserRoles(getUserId());
	}

	@Override
	public List<Group> getSites() throws PortalException, SystemException {
		return getSites(false);
	}

	@Override
	public List<Group> getSites(boolean includeAdministrative)
		throws PortalException, SystemException {

		return GroupLocalServiceUtil.getUserSitesGroups(
			getUserId(), includeAdministrative);
	}

	@Override
	public long[] getTeamIds() throws SystemException {
		List<Team> teams = getTeams();

		long[] teamIds = new long[teams.size()];

		for (int i = 0; i < teams.size(); i++) {
			Team team = teams.get(i);

			teamIds[i] = team.getTeamId();
		}

		return teamIds;
	}

	@Override
	public List<Team> getTeams() throws SystemException {
		return TeamLocalServiceUtil.getUserTeams(getUserId());
	}

	@Override
	public TimeZone getTimeZone() {
		return _timeZone;
	}

	@Override
	public long[] getUserGroupIds() throws SystemException {
		List<UserGroup> userGroups = getUserGroups();

		long[] userGroupIds = new long[userGroups.size()];

		for (int i = 0; i < userGroups.size(); i++) {
			UserGroup userGroup = userGroups.get(i);

			userGroupIds[i] = userGroup.getUserGroupId();
		}

		return userGroupIds;
	}

	@Override
	public List<UserGroup> getUserGroups() throws SystemException {
		return UserGroupLocalServiceUtil.getUserUserGroups(getUserId());
	}

	@Override
	public List<Website> getWebsites() throws SystemException {
		return WebsiteLocalServiceUtil.getWebsites(
			getCompanyId(), Contact.class.getName(), getContactId());
	}

	@Override
	public boolean hasCompanyMx() throws PortalException, SystemException {
		return hasCompanyMx(getEmailAddress());
	}

	@Override
	public boolean hasCompanyMx(String emailAddress)
		throws PortalException, SystemException {

		if (Validator.isNull(emailAddress)) {
			return false;
		}

		Company company = CompanyLocalServiceUtil.getCompanyById(
			getCompanyId());

		return company.hasCompanyMx(emailAddress);
	}

	@Override
	public boolean hasMySites() throws PortalException, SystemException {
		if (isDefaultUser()) {
			return false;
		}

		int max = PropsValues.MY_SITES_MAX_ELEMENTS;

		if (max == 1) {

			// Increment so that we return more than just the Control Panel
			// group

			max++;
		}

		List<Group> groups = getMySites(true, max);

		return !groups.isEmpty();
	}

	@Override
	public boolean hasOrganization() throws PortalException, SystemException {
		List<Organization> organizations = getOrganizations();

		return !organizations.isEmpty();
	}

	@Override
	public boolean hasPrivateLayouts() throws PortalException, SystemException {
		return LayoutLocalServiceUtil.hasLayouts(this, true);
	}

	@Override
	public boolean hasPublicLayouts() throws PortalException, SystemException {
		return LayoutLocalServiceUtil.hasLayouts(this, false);
	}

	@Override
	public boolean hasReminderQuery() {
		if (Validator.isNotNull(getReminderQueryQuestion()) &&
			Validator.isNotNull(getReminderQueryAnswer())) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isActive() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isFemale() throws PortalException, SystemException {
		return getFemale();
	}

	@Override
	public boolean isMale() throws PortalException, SystemException {
		return getMale();
	}

	@Override
	public boolean isPasswordModified() {
		return _passwordModified;
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
			timeZoneId = TimeZoneUtil.getDefault().getID();
		}

		_timeZone = TimeZoneUtil.getTimeZone(timeZoneId);

		super.setTimeZoneId(timeZoneId);
	}

	protected String getProfileFriendlyURL() {
		if (Validator.isNull(PropsValues.USERS_PROFILE_FRIENDLY_URL)) {
			return null;
		}

		return StringUtil.replace(
			PropsValues.USERS_PROFILE_FRIENDLY_URL,
			new String[] {"${liferay:screenName}", "${liferay:userId}"},
			new String[] {
				HtmlUtil.escapeURL(getScreenName()), String.valueOf(getUserId())
			});
	}

	private Locale _locale;
	private boolean _passwordModified;
	private PasswordPolicy _passwordPolicy;
	private String _passwordUnencrypted;
	private TimeZone _timeZone;

}