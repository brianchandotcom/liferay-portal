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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the User service. Represents a row in the &quot;User_&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see UserModel
 * @see com.liferay.portal.model.impl.UserImpl
 * @see com.liferay.portal.model.impl.UserModelImpl
 * @generated
 */
@ProviderType
public interface User extends UserModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.UserImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<User, Long> USER_ID_ACCESSOR = new Accessor<User, Long>() {
			@Override
			public Long get(User user) {
				return user.getUserId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<User> getTypeClass() {
				return User.class;
			}
		};

	public void addRemotePreference(
		com.liferay.portal.kernel.util.RemotePreference remotePreference);

	public com.liferay.portal.model.Contact fetchContact();

	/**
	* Returns the user's addresses.
	*
	* @return the user's addresses
	*/
	public java.util.List<com.liferay.portal.model.Address> getAddresses();

	/**
	* Returns the user's birth date.
	*
	* @return the user's birth date
	*/
	public java.util.Date getBirthday()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the user's company's mail domain.
	*
	* @return the user's company's mail domain
	*/
	public java.lang.String getCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the user's associated contact.
	*
	* @return the user's associated contact
	* @see Contact
	*/
	public com.liferay.portal.model.Contact getContact()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns a digest for the user, incorporating the password.
	*
	* @param password a password to incorporate with the digest
	* @return a digest for the user, incorporating the password
	*/
	public java.lang.String getDigest(java.lang.String password);

	/**
	* Returns the user's primary email address, or a blank string if the
	* address is fake.
	*
	* @return the user's primary email address, or a blank string if the
	address is fake
	*/
	public java.lang.String getDisplayEmailAddress();

	/**
	* Returns the user's display URL, discounting the URL of the user's default
	* intranet site home page.
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
	* @param portalURL the portal's URL
	* @param mainPath the main path
	* @return the user's display URL
	* @deprecated As of 7.0.0, replaced by {@link #getDisplayURL(ThemeDisplay)}
	*/
	@java.lang.Deprecated()
	public java.lang.String getDisplayURL(java.lang.String portalURL,
		java.lang.String mainPath)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the user's display URL.
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
	* @param portalURL the portal's URL
	* @param mainPath the main path
	* @param privateLayout whether to use the URL of the user's default
	intranet(versus extranet)  site home page, if no friendly URL
	is available for the user's profile
	* @return the user's display URL
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link #getDisplayURL(ThemeDisplay)}
	*/
	@java.lang.Deprecated()
	public java.lang.String getDisplayURL(java.lang.String portalURL,
		java.lang.String mainPath, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException;

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
	* @param themeDisplay the theme display
	* @return the user's display URL
	*/
	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException;

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
	* @param themeDisplay the theme display
	* @param privateLayout whether to use the URL of the user's default
	intranet (versus extranet) site home page, if no friendly URL is
	available for the user's profile
	* @return the user's display URL
	* @throws PortalException
	*/
	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the user's email addresses.
	*
	* @return the user's email addresses
	*/
	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses();

	/**
	* Returns <code>true</code> if the user is female.
	*
	* @return <code>true</code> if the user is female; <code>false</code>
	otherwise
	*/
	public boolean getFemale()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the user's full name.
	*
	* @return the user's full name
	*/
	@com.liferay.portal.kernel.bean.AutoEscape()
	public java.lang.String getFullName();

	/**
	* Returns the user's full name.
	*
	* @return the user's full name
	*/
	@com.liferay.portal.kernel.bean.AutoEscape()
	public java.lang.String getFullName(boolean usePrefix, boolean useSuffix);

	public com.liferay.portal.model.Group getGroup();

	public long getGroupId();

	public long[] getGroupIds();

	public java.util.List<com.liferay.portal.model.Group> getGroups();

	public java.lang.String getInitials();

	public java.util.Locale getLocale();

	public java.lang.String getLogin()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns <code>true</code> if the user is male.
	*
	* @return <code>true</code> if the user is male; <code>false</code>
	otherwise
	*/
	public boolean getMale()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups(
		int max) throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups}
	*/
	@java.lang.Deprecated()
	public java.util.List<com.liferay.portal.model.Group> getMySites()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link User#getMySiteGroups(int)}
	*/
	@java.lang.Deprecated()
	public java.util.List<com.liferay.portal.model.Group> getMySites(
		boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(int)}
	*/
	@java.lang.Deprecated()
	public java.util.List<com.liferay.portal.model.Group> getMySites(int max)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(String[],
	int)}
	*/
	@java.lang.Deprecated()
	public java.util.List<com.liferay.portal.model.Group> getMySites(
		java.lang.String[] classNames, boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(String[],
	int)}
	*/
	@java.lang.Deprecated()
	public java.util.List<com.liferay.portal.model.Group> getMySites(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException;

	public long[] getOrganizationIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long[] getOrganizationIds(boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getOriginalEmailAddress();

	public boolean getPasswordModified();

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getPasswordUnencrypted();

	public java.util.List<com.liferay.portal.model.Phone> getPhones();

	public java.lang.String getPortraitURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Set<java.lang.String> getReminderQueryQuestions()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.util.RemotePreference getRemotePreference(
		java.lang.String name);

	public java.lang.Iterable<com.liferay.portal.kernel.util.RemotePreference> getRemotePreferences();

	public long[] getRoleIds();

	public java.util.List<com.liferay.portal.model.Role> getRoles();

	public java.util.List<com.liferay.portal.model.Group> getSiteGroups()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.model.Group> getSiteGroups(
		boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException;

	public long[] getTeamIds();

	public java.util.List<com.liferay.portal.model.Team> getTeams();

	public java.util.TimeZone getTimeZone();

	public java.util.Date getUnlockDate()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Date getUnlockDate(
		com.liferay.portal.model.PasswordPolicy passwordPolicy);

	public long[] getUserGroupIds();

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups();

	public java.util.List<com.liferay.portal.model.Website> getWebsites();

	public boolean hasCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasCompanyMx(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasMySites()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasOrganization();

	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasReminderQuery();

	public boolean isActive();

	public boolean isEmailAddressComplete();

	public boolean isEmailAddressVerificationComplete();

	public boolean isFemale()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isMale()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isPasswordModified();

	public boolean isReminderQueryComplete();

	public boolean isSetupComplete();

	public boolean isTermsOfUseComplete();

	public void setPasswordModified(boolean passwordModified);

	public void setPasswordUnencrypted(java.lang.String passwordUnencrypted);
}