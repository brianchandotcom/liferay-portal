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

package com.liferay.portal.service.impl;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.mail.kernel.template.MailTemplateContextBuilder;
import com.liferay.mail.kernel.template.MailTemplateFactoryUtil;
import com.liferay.petra.encryptor.Encryptor;
import com.liferay.petra.encryptor.EncryptorException;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheMapSynchronizeUtil;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.ContactBirthdayException;
import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.DuplicateGoogleUserIdException;
import com.liferay.portal.kernel.exception.DuplicateOpenIdException;
import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.NoSuchImageException;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PasswordExpiredException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredRoleException;
import com.liferay.portal.kernel.exception.RequiredUserException;
import com.liferay.portal.kernel.exception.SendPasswordException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserIdException;
import com.liferay.portal.kernel.exception.UserLockoutException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.exception.UserReminderQueryException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.exception.UserSmsException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ContactConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.EmailAddressGenerator;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.security.auth.FullNameDefinition;
import com.liferay.portal.kernel.security.auth.FullNameDefinitionFactory;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.security.auth.FullNameValidator;
import com.liferay.portal.kernel.security.auth.PasswordModificationThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.auth.ScreenNameGenerator;
import com.liferay.portal.kernel.security.auth.ScreenNameValidator;
import com.liferay.portal.kernel.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.UserCacheModel;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.auth.AuthPipeline;
import com.liferay.portal.security.auth.EmailAddressGeneratorFactory;
import com.liferay.portal.security.auth.EmailAddressValidatorFactory;
import com.liferay.portal.security.auth.FullNameValidatorFactory;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.security.auth.ScreenNameValidatorFactory;
import com.liferay.portal.security.pwd.PwdAuthenticator;
import com.liferay.portal.security.pwd.PwdToolkitUtil;
import com.liferay.portal.security.pwd.RegExpToolkit;
import com.liferay.portal.service.base.UserLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.dependency.ServiceDependencyListener;
import com.liferay.registry.dependency.ServiceDependencyManager;
import com.liferay.social.kernel.model.SocialRelation;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

/**
 * Provides the local service for accessing, adding, authenticating, deleting,
 * and updating users.
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Julio Camarero
 * @author Wesley Gong
 * @author Zsigmond Rab
 */
public class UserLocalServiceImpl extends UserLocalServiceBaseImpl {

	/**
	 * Adds a default admin user for the company.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  screenName the user's screen name
	 * @param  emailAddress the user's email address
	 * @param  locale the user's locale
	 * @param  firstName the user's first name
	 * @param  middleName the user's middle name
	 * @param  lastName the user's last name
	 * @return the new default admin user
	 */
	@Override
	public User addDefaultAdminUser(
			long companyId, String screenName, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName)
		throws PortalException {

		long creatorUserId = 0;
		boolean autoPassword = false;

		String password1 = PropsValues.DEFAULT_ADMIN_PASSWORD;

		String password2 = password1;

		boolean autoScreenName = false;

		screenName = getLogin(screenName);

		for (int i = 1;; i++) {
			User screenNameUser = userPersistence.fetchByC_SN(
				companyId, screenName);

			if (screenNameUser == null) {
				break;
			}

			screenName = screenName + i;
		}

		long facebookId = 0;
		String openId = StringPool.BLANK;
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;

		Group guestGroup = groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		long[] groupIds = {guestGroup.getGroupId()};

		long[] organizationIds = null;

		Role adminRole = roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		Role powerUserRole = roleLocalService.getRole(
			companyId, RoleConstants.POWER_USER);

		long[] roleIds = {adminRole.getRoleId(), powerUserRole.getRoleId()};

		long[] userGroupIds = null;
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();

		User defaultAdminUser = addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		updateEmailAddressVerified(defaultAdminUser.getUserId(), true);

		updateLastLogin(
			defaultAdminUser.getUserId(), defaultAdminUser.getLoginIP());

		updatePasswordReset(defaultAdminUser.getUserId(), false);

		return defaultAdminUser;
	}

	/**
	 * Adds the user to the default groups, unless the user is already in these
	 * groups. The default groups can be specified in
	 * <code>portal.properties</code> with the key
	 * <code>admin.default.group.names</code>.
	 *
	 * @param userId the primary key of the user
	 */
	@Override
	public void addDefaultGroups(long userId) throws PortalException {
		User user = userPersistence.findByPrimaryKey(userId);

		Set<Long> groupIdsSet = new HashSet<>();

		String[] defaultGroupNames = PrefsPropsUtil.getStringArray(
			user.getCompanyId(), PropsKeys.ADMIN_DEFAULT_GROUP_NAMES,
			StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_GROUP_NAMES);

		for (String defaultGroupName : defaultGroupNames) {
			Company company = companyPersistence.findByPrimaryKey(
				user.getCompanyId());

			Account account = company.getAccount();

			if (StringUtil.equalsIgnoreCase(
					defaultGroupName, account.getName())) {

				defaultGroupName = GroupConstants.GUEST;
			}

			Group group = groupPersistence.fetchByC_GK(
				user.getCompanyId(), defaultGroupName);

			if (group != null) {
				groupIdsSet.add(group.getGroupId());
			}
		}

		String[] defaultOrganizationGroupNames = PrefsPropsUtil.getStringArray(
			user.getCompanyId(),
			PropsKeys.ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES,
			StringPool.NEW_LINE,
			PropsValues.ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES);

		for (String defaultOrganizationGroupName :
				defaultOrganizationGroupNames) {

			defaultOrganizationGroupName +=
				GroupLocalServiceImpl.ORGANIZATION_NAME_SUFFIX;

			Group group = groupPersistence.fetchByC_GK(
				user.getCompanyId(), defaultOrganizationGroupName);

			if (group != null) {
				groupIdsSet.add(group.getGroupId());
			}
		}

		long[] groupIds = ArrayUtil.toArray(groupIdsSet.toArray(new Long[0]));

		userPersistence.addGroups(userId, groupIds);

		for (long groupId : groupIds) {
			addDefaultRolesAndTeams(groupId, new long[] {userId});
		}
	}

	/**
	 * Adds the user to the default regular roles, unless the user already has
	 * these regular roles. The default regular roles can be specified in
	 * <code>portal.properties</code> with the key
	 * <code>admin.default.role.names</code>.
	 *
	 * @param userId the primary key of the user
	 */
	@Override
	public void addDefaultRoles(long userId) throws PortalException {
		User user = userPersistence.findByPrimaryKey(userId);

		Set<Long> roleIdSet = new HashSet<>();

		String[] defaultRoleNames = PrefsPropsUtil.getStringArray(
			user.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ROLE_NAMES,
			StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_ROLE_NAMES);

		for (String defaultRoleName : defaultRoleNames) {
			Role role = rolePersistence.fetchByC_N(
				user.getCompanyId(), defaultRoleName);

			if ((role != null) &&
				(role.getType() == RoleConstants.TYPE_REGULAR)) {

				roleIdSet.add(role.getRoleId());
			}
		}

		long[] roleIds = ArrayUtil.toArray(roleIdSet.toArray(new Long[0]));

		roleIds = UsersAdminUtil.addRequiredRoles(user, roleIds);

		userPersistence.addRoles(userId, roleIds);
	}

	/**
	 * Adds the user to the default user groups, unless the user is already in
	 * these user groups. The default user groups can be specified in
	 * <code>portal.properties</code> with the property
	 * <code>admin.default.user.group.names</code>.
	 *
	 * @param userId the primary key of the user
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void addDefaultUserGroups(long userId) throws PortalException {
		User user = userPersistence.findByPrimaryKey(userId);

		Set<Long> userGroupIdSet = new HashSet<>();

		String[] defaultUserGroupNames = PrefsPropsUtil.getStringArray(
			user.getCompanyId(), PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES,
			StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_USER_GROUP_NAMES);

		for (String defaultUserGroupName : defaultUserGroupNames) {
			UserGroup userGroup = userGroupPersistence.fetchByC_N(
				user.getCompanyId(), defaultUserGroupName);

			if (userGroup != null) {
				userGroupIdSet.add(userGroup.getUserGroupId());
			}
		}

		long[] userGroupIds = ArrayUtil.toArray(
			userGroupIdSet.toArray(new Long[0]));

		if (PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {
			for (long userGroupId : userGroupIds) {
				userGroupLocalService.copyUserGroupLayouts(userGroupId, userId);
			}
		}

		userPersistence.addUserGroups(userId, userGroupIds);
	}

	/**
	 * Adds the user to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userId the primary key of the user
	 */
	@Override
	public void addGroupUser(long groupId, long userId) {
		groupPersistence.addUser(groupId, userId);

		try {
			reindex(userId);

			addDefaultRolesAndTeams(groupId, new long[] {userId});
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * Adds the user to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param user the user
	 */
	@Override
	public void addGroupUser(long groupId, User user) {
		addGroupUser(groupId, user.getUserId());
	}

	/**
	 * Adds the users to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  users the users
	 * @throws PortalException
	 */
	@Override
	public void addGroupUsers(long groupId, List<User> users)
		throws PortalException {

		List<Long> userIds = new ArrayList<>();

		for (User user : users) {
			userIds.add(user.getUserId());
		}

		addGroupUsers(groupId, ArrayUtil.toLongArray(userIds));
	}

	/**
	 * Adds the users to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void addGroupUsers(long groupId, long[] userIds)
		throws PortalException {

		groupPersistence.addUsers(groupId, userIds);

		reindex(userIds);

		addDefaultRolesAndTeams(groupId, userIds);
	}

	/**
	 * Adds the user to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param userId the primary key of the user
	 */
	@Override
	public void addOrganizationUser(long organizationId, long userId) {
		organizationPersistence.addUser(organizationId, userId);

		try {
			reindex(userId);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	/**
	 * Adds the user to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param user the user
	 */
	@Override
	public void addOrganizationUser(long organizationId, User user) {
		organizationPersistence.addUser(organizationId, user);

		try {
			reindex(user);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	/**
	 * Adds the users to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param users the users
	 */
	@Override
	public void addOrganizationUsers(long organizationId, List<User> users)
		throws PortalException {

		organizationPersistence.addUsers(organizationId, users);

		reindex(users);
	}

	/**
	 * Adds the users to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void addOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException {

		organizationPersistence.addUsers(organizationId, userIds);

		reindex(userIds);
	}

	/**
	 * Assigns the password policy to the users, removing any other currently
	 * assigned password policies.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds) {
		_checkPasswordReset(
			passwordPolicyLocalService.fetchPasswordPolicy(passwordPolicyId),
			userIds);

		passwordPolicyRelLocalService.addPasswordPolicyRels(
			passwordPolicyId, User.class.getName(), userIds);
	}

	/**
	 * Adds the user to the role.
	 *
	 * @param roleId the primary key of the role
	 * @param userId the primary key of the user
	 */
	@Override
	public void addRoleUser(long roleId, long userId) {
		rolePersistence.addUser(roleId, userId);

		try {
			reindex(userId);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	/**
	 * Adds the user to the role.
	 *
	 * @param roleId the primary key of the role
	 * @param user the user
	 */
	@Override
	public void addRoleUser(long roleId, User user) {
		rolePersistence.addUser(roleId, user);

		try {
			reindex(user);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	/**
	 * Adds the users to the role.
	 *
	 * @param  roleId the primary key of the role
	 * @param  users the users
	 * @throws PortalException
	 */
	@Override
	public void addRoleUsers(long roleId, List<User> users)
		throws PortalException {

		rolePersistence.addUsers(roleId, users);

		reindex(users);
	}

	/**
	 * Adds the users to the role.
	 *
	 * @param roleId the primary key of the role
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void addRoleUsers(long roleId, long[] userIds)
		throws PortalException {

		rolePersistence.addUsers(roleId, userIds);

		reindex(userIds);
	}

	/**
	 * Adds the user to the team.
	 *
	 * @param teamId the primary key of the team
	 * @param userId the primary key of the user
	 */
	@Override
	public void addTeamUser(long teamId, long userId) {
		teamPersistence.addUser(teamId, userId);

		try {
			reindex(userId);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	/**
	 * Adds the user to the team.
	 *
	 * @param teamId the primary key of the team
	 * @param user the user
	 */
	@Override
	public void addTeamUser(long teamId, User user) {
		teamPersistence.addUser(teamId, user);

		try {
			reindex(user);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	/**
	 * Adds the users to the team.
	 *
	 * @param  teamId the primary key of the team
	 * @param  users the users
	 * @throws PortalException
	 */
	@Override
	public void addTeamUsers(long teamId, List<User> users)
		throws PortalException {

		teamPersistence.addUsers(teamId, users);

		reindex(users);
	}

	/**
	 * Adds the users to the team.
	 *
	 * @param teamId the primary key of the team
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void addTeamUsers(long teamId, long[] userIds)
		throws PortalException {

		teamPersistence.addUsers(teamId, userIds);

		reindex(userIds);
	}

	/**
	 * Adds a user.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user including
	 * its resources, metadata, and internal data structures. It is not
	 * necessary to make subsequent calls to any methods to setup default
	 * groups, resources, etc.
	 * </p>
	 *
	 * @param  creatorUserId the primary key of the creator
	 * @param  companyId the primary key of the user's company
	 * @param  autoPassword whether a password should be automatically generated
	 *         for the user
	 * @param  password1 the user's password
	 * @param  password2 the user's password confirmation
	 * @param  autoScreenName whether a screen name should be automatically
	 *         generated for the user
	 * @param  screenName the user's screen name
	 * @param  emailAddress the user's email address
	 * @param  facebookId the user's facebook ID
	 * @param  openId the user's OpenID
	 * @param  locale the user's locale
	 * @param  firstName the user's first name
	 * @param  middleName the user's middle name
	 * @param  lastName the user's last name
	 * @param  prefixId the user's name prefix ID
	 * @param  suffixId the user's name suffix ID
	 * @param  male whether the user is male
	 * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
	 *         January)
	 * @param  birthdayDay the user's birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  jobTitle the user's job title
	 * @param  groupIds the primary keys of the user's groups
	 * @param  organizationIds the primary keys of the user's organizations
	 * @param  roleIds the primary keys of the roles this user possesses
	 * @param  userGroupIds the primary keys of the user's user groups
	 * @param  sendEmail whether to send the user an email notification about
	 *         their new account
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set the UUID (with the <code>uuid</code>
	 *         attribute), asset category IDs, asset tag names, and expando
	 *         bridge attributes for the user.
	 * @return the new user
	 */
	@Override
	public User addUser(
			long creatorUserId, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, long facebookId,
			String openId, Locale locale, String firstName, String middleName,
			String lastName, long prefixId, long suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, long[] groupIds, long[] organizationIds,
			long[] roleIds, long[] userGroupIds, boolean sendEmail,
			ServiceContext serviceContext)
		throws PortalException {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(false);

			if (serviceContext == null) {
				serviceContext = new ServiceContext();
			}

			if (serviceContext.getWorkflowAction() !=
					WorkflowConstants.ACTION_PUBLISH) {

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);
			}

			return addUserWithWorkflow(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
				serviceContext);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	/**
	 * Adds the user to the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void addUserGroupUser(long userGroupId, long userId) {
		try {
			if (PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {
				userGroupLocalService.copyUserGroupLayouts(userGroupId, userId);
			}

			userGroupPersistence.addUser(userGroupId, userId);

			reindex(userId);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * Adds the user to the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param user the user
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void addUserGroupUser(long userGroupId, User user) {
		addUserGroupUser(userGroupId, user.getUserId());
	}

	/**
	 * Adds the users to the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param users the users
	 */
	@Override
	public void addUserGroupUsers(long userGroupId, List<User> users)
		throws PortalException {

		List<Long> userIds = new ArrayList<>();

		for (User user : users) {
			userIds.add(user.getUserId());
		}

		try {
			addUserGroupUsers(userGroupId, ArrayUtil.toLongArray(userIds));
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	/**
	 * Adds the users to the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param userIds the primary keys of the users
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException {

		if (PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {
			userGroupLocalService.copyUserGroupLayouts(userGroupId, userIds);
		}

		userGroupPersistence.addUsers(userGroupId, userIds);

		reindex(userIds);
	}

	/**
	 * Adds a user with workflow.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user including
	 * its resources, metadata, and internal data structures. It is not
	 * necessary to make subsequent calls to any methods to setup default
	 * groups, resources, etc.
	 * </p>
	 *
	 * @param  creatorUserId the primary key of the creator
	 * @param  companyId the primary key of the user's company
	 * @param  autoPassword whether a password should be automatically generated
	 *         for the user
	 * @param  password1 the user's password
	 * @param  password2 the user's password confirmation
	 * @param  autoScreenName whether a screen name should be automatically
	 *         generated for the user
	 * @param  screenName the user's screen name
	 * @param  emailAddress the user's email address
	 * @param  facebookId the user's facebook ID
	 * @param  openId the user's OpenID
	 * @param  locale the user's locale
	 * @param  firstName the user's first name
	 * @param  middleName the user's middle name
	 * @param  lastName the user's last name
	 * @param  prefixId the user's name prefix ID
	 * @param  suffixId the user's name suffix ID
	 * @param  male whether the user is male
	 * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
	 *         January)
	 * @param  birthdayDay the user's birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  jobTitle the user's job title
	 * @param  groupIds the primary keys of the user's groups
	 * @param  organizationIds the primary keys of the user's organizations
	 * @param  roleIds the primary keys of the roles this user possesses
	 * @param  userGroupIds the primary keys of the user's user groups
	 * @param  sendEmail whether to send the user an email notification about
	 *         their new account
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set the UUID (with the <code>uuid</code>
	 *         attribute), asset category IDs, asset tag names, and expando
	 *         bridge attributes for the user.
	 * @return the new user
	 */
	@Override
	@SuppressWarnings("deprecation")
	public User addUserWithWorkflow(
			long creatorUserId, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, long facebookId,
			String openId, Locale locale, String firstName, String middleName,
			String lastName, long prefixId, long suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, long[] groupIds, long[] organizationIds,
			long[] roleIds, long[] userGroupIds, boolean sendEmail,
			ServiceContext serviceContext)
		throws PortalException {

		// User

		Company company = companyPersistence.findByPrimaryKey(companyId);
		screenName = getLogin(screenName);
		openId = StringUtil.trim(openId);

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE)) {

			autoScreenName = true;
		}

		// PLACEHOLDER 01

		long userId = counterLocalService.increment();

		EmailAddressGenerator emailAddressGenerator =
			EmailAddressGeneratorFactory.getInstance();

		if ((emailAddress == null) ||
			emailAddressGenerator.isGenerated(emailAddress)) {

			emailAddress = StringPool.BLANK;
		}
		else {
			emailAddress = StringUtil.toLowerCase(emailAddress.trim());
		}

		if (!PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.USERS_EMAIL_ADDRESS_REQUIRED) &&
			Validator.isNull(emailAddress)) {

			emailAddress = emailAddressGenerator.generate(companyId, userId);
		}

		validate(
			companyId, userId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, openId, firstName,
			middleName, lastName, organizationIds, locale);

		if (!autoPassword &&
			(Validator.isNull(password1) || Validator.isNull(password2))) {

			throw new UserPasswordException.MustNotBeNull(userId);
		}

		if (autoScreenName) {
			ScreenNameGenerator screenNameGenerator =
				ScreenNameGeneratorFactory.getInstance();

			try {
				screenName = screenNameGenerator.generate(
					companyId, userId, emailAddress);
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		User defaultUser = getDefaultUser(companyId);

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String fullName = fullNameGenerator.getFullName(
			firstName, middleName, lastName);

		String greeting = LanguageUtil.format(
			locale, "welcome-x", fullName, false);

		User user = userPersistence.create(userId);

		if (serviceContext != null) {
			String uuid = serviceContext.getUuid();

			if (Validator.isNotNull(uuid)) {
				user.setUuid(uuid);
			}
		}

		user.setCompanyId(companyId);
		user.setDefaultUser(false);
		user.setContactId(counterLocalService.increment());

		if (Validator.isNotNull(password1)) {
			PasswordModificationThreadLocal.setPasswordModified(true);
			PasswordModificationThreadLocal.setPasswordUnencrypted(password1);

			user.setPassword(PasswordEncryptorUtil.encrypt(password1));
			user.setPasswordUnencrypted(password1);
		}

		user.setPasswordEncrypted(true);

		PasswordPolicy passwordPolicy = defaultUser.getPasswordPolicy();

		if ((passwordPolicy != null) && passwordPolicy.isChangeable() &&
			passwordPolicy.isChangeRequired()) {

			user.setPasswordReset(true);
		}
		else {
			user.setPasswordReset(false);
		}

		user.setScreenName(screenName);
		user.setEmailAddress(emailAddress);

		user.setDigest(user.getDigest(password1));

		user.setFacebookId(facebookId);

		Long ldapServerId = null;

		if (serviceContext != null) {
			ldapServerId = (Long)serviceContext.getAttribute("ldapServerId");
		}

		if (ldapServerId != null) {
			user.setLdapServerId(ldapServerId);
		}
		else {
			user.setLdapServerId(-1);
		}

		user.setOpenId(openId);
		user.setLanguageId(LocaleUtil.toLanguageId(locale));
		user.setTimeZoneId(defaultUser.getTimeZoneId());
		user.setGreeting(greeting);
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setJobTitle(jobTitle);
		user.setStatus(WorkflowConstants.STATUS_DRAFT);
		user.setExpandoBridgeAttributes(serviceContext);

		userPersistence.update(user, serviceContext);

		// Contact

		String creatorUserName = StringPool.BLANK;

		if (creatorUserId <= 0) {
			creatorUserId = user.getUserId();

			// Don't grab the full name from the User object because it doesn't
			// have a corresponding Contact object yet

			//creatorUserName = user.getFullName();
		}
		else {
			User creatorUser = userPersistence.findByPrimaryKey(creatorUserId);

			creatorUserName = creatorUser.getFullName();
		}

		Date birthday = getBirthday(birthdayMonth, birthdayDay, birthdayYear);

		Contact contact = contactPersistence.create(user.getContactId());

		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(creatorUserId);
		contact.setUserName(creatorUserName);
		contact.setClassName(User.class.getName());
		contact.setClassPK(user.getUserId());
		contact.setAccountId(company.getAccountId());
		contact.setParentContactId(ContactConstants.DEFAULT_PARENT_CONTACT_ID);
		contact.setEmailAddress(user.getEmailAddress());
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setJobTitle(jobTitle);

		contactPersistence.update(contact, serviceContext);

		// Group

		groupLocalService.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			User.class.getName(), user.getUserId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, (Map<Locale, String>)null,
			null, 0, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + screenName, false, true, null);

		// Groups

		if (!ArrayUtil.isEmpty(groupIds)) {
			List<Group> groups = new ArrayList<>();

			for (long groupId : groupIds) {
				Group group = groupLocalService.fetchGroup(groupId);

				if (group != null) {
					groups.add(group);
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn("Group " + groupId + " does not exist");
					}
				}
			}

			groupLocalService.addUserGroups(userId, groups);
		}

		addDefaultGroups(userId);

		// Organizations

		updateOrganizations(userId, organizationIds, false);

		// Roles

		if (roleIds != null) {
			roleIds = UsersAdminUtil.addRequiredRoles(user, roleIds);

			userPersistence.setRoles(userId, roleIds);
		}

		addDefaultRoles(userId);

		// User groups

		if (userGroupIds != null) {
			if (PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {
				for (long userGroupId : userGroupIds) {
					userGroupLocalService.copyUserGroupLayouts(
						userGroupId, new long[] {userId});
				}
			}

			userPersistence.setUserGroups(userId, userGroupIds);
		}

		addDefaultUserGroups(userId);

		// Resources

		resourceLocalService.addResources(
			companyId, 0, creatorUserId, User.class.getName(), user.getUserId(),
			false, false, false);

		// Asset

		if (serviceContext != null) {
			updateAsset(
				creatorUserId, user, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames());
		}

		// Indexer

		if ((serviceContext == null) || serviceContext.isIndexingEnabled()) {
			reindex(user);
		}

		// Workflow

		long workflowUserId = creatorUserId;

		if (workflowUserId == userId) {
			workflowUserId = defaultUser.getUserId();
		}

		ServiceContext workflowServiceContext = new ServiceContext();

		if (serviceContext != null) {
			workflowServiceContext = (ServiceContext)serviceContext.clone();
		}

		workflowServiceContext.setAttribute("autoPassword", autoPassword);
		workflowServiceContext.setAttribute("passwordUnencrypted", password1);
		workflowServiceContext.setAttribute("sendEmail", sendEmail);

		Map<String, Serializable> workflowContext =
			(Map<String, Serializable>)workflowServiceContext.removeAttribute(
				"workflowContext");

		if (workflowContext == null) {
			workflowContext = Collections.emptyMap();
		}

		user = WorkflowHandlerRegistryUtil.startWorkflowInstance(
			companyId, WorkflowConstants.DEFAULT_GROUP_ID, workflowUserId,
			User.class.getName(), userId, user, workflowServiceContext,
			workflowContext);

		if (serviceContext != null) {
			String passwordUnencrypted = (String)serviceContext.getAttribute(
				"passwordUnencrypted");

			if (Validator.isNotNull(passwordUnencrypted)) {
				user.setPasswordUnencrypted(passwordUnencrypted);
			}
		}

		return user;
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			_serviceDependencyListener);

		serviceDependencyManager.registerDependencies(EntityCache.class);
	}

	/**
	 * Attempts to authenticate the user by their email address and password,
	 * while using the AuthPipeline.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  emailAddress the user's email address
	 * @param  password the user's password
	 * @param  headerMap the header map from the authentication request
	 * @param  parameterMap the parameter map from the authentication request
	 * @param  resultsMap the map of authentication results (may be nil). After
	 *         a successful authentication the user's primary key will be placed
	 *         under the key <code>userId</code>.
	 * @return the authentication status. This can be {@link
	 *         Authenticator#FAILURE} indicating that the user's credentials are
	 *         invalid, {@link Authenticator#SUCCESS} indicating a successful
	 *         login, or {@link Authenticator#DNE} indicating that a user with
	 *         that login does not exist.
	 * @see    AuthPipeline
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int authenticateByEmailAddress(
			long companyId, String emailAddress, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap,
			Map<String, Object> resultsMap)
		throws PortalException {

		return authenticate(
			companyId, emailAddress, password, CompanyConstants.AUTH_TYPE_EA,
			headerMap, parameterMap, resultsMap);
	}

	/**
	 * Attempts to authenticate the user by their screen name and password,
	 * while using the AuthPipeline.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  screenName the user's screen name
	 * @param  password the user's password
	 * @param  headerMap the header map from the authentication request
	 * @param  parameterMap the parameter map from the authentication request
	 * @param  resultsMap the map of authentication results (may be nil). After
	 *         a successful authentication the user's primary key will be placed
	 *         under the key <code>userId</code>.
	 * @return the authentication status. This can be {@link
	 *         Authenticator#FAILURE} indicating that the user's credentials are
	 *         invalid, {@link Authenticator#SUCCESS} indicating a successful
	 *         login, or {@link Authenticator#DNE} indicating that a user with
	 *         that login does not exist.
	 * @see    AuthPipeline
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int authenticateByScreenName(
			long companyId, String screenName, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap,
			Map<String, Object> resultsMap)
		throws PortalException {

		return authenticate(
			companyId, screenName, password, CompanyConstants.AUTH_TYPE_SN,
			headerMap, parameterMap, resultsMap);
	}

	/**
	 * Attempts to authenticate the user by their primary key and password,
	 * while using the AuthPipeline.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  userId the user's primary key
	 * @param  password the user's password
	 * @param  headerMap the header map from the authentication request
	 * @param  parameterMap the parameter map from the authentication request
	 * @param  resultsMap the map of authentication results (may be nil). After
	 *         a successful authentication the user's primary key will be placed
	 *         under the key <code>userId</code>.
	 * @return the authentication status. This can be {@link
	 *         Authenticator#FAILURE} indicating that the user's credentials are
	 *         invalid, {@link Authenticator#SUCCESS} indicating a successful
	 *         login, or {@link Authenticator#DNE} indicating that a user with
	 *         that login does not exist.
	 * @see    AuthPipeline
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int authenticateByUserId(
			long companyId, long userId, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap,
			Map<String, Object> resultsMap)
		throws PortalException {

		return authenticate(
			companyId, String.valueOf(userId), password,
			CompanyConstants.AUTH_TYPE_ID, headerMap, parameterMap, resultsMap);
	}

	/**
	 * Attempts to authenticate the user using HTTP basic access authentication,
	 * without using the AuthPipeline. Primarily used for authenticating users
	 * of <code>tunnel-web</code>.
	 *
	 * <p>
	 * Authentication type specifies what <code>login</code> contains.The valid
	 * values are:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * <code>CompanyConstants.AUTH_TYPE_EA</code> - <code>login</code> is the
	 * user's email address
	 * </li>
	 * <li>
	 * <code>CompanyConstants.AUTH_TYPE_SN</code> - <code>login</code> is the
	 * user's screen name
	 * </li>
	 * <li>
	 * <code>CompanyConstants.AUTH_TYPE_ID</code> - <code>login</code> is the
	 * user's primary key
	 * </li>
	 * </ul>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  authType the type of authentication to perform
	 * @param  login either the user's email address, screen name, or primary
	 *         key depending on the value of <code>authType</code>
	 * @param  password the user's password
	 * @return the user's primary key if authentication is successful;
	 *         <code>0</code> otherwise
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long authenticateForBasic(
			long companyId, String authType, String login, String password)
		throws PortalException {

		if (PropsValues.AUTH_LOGIN_DISABLED) {
			return 0;
		}

		User user = null;

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			user = fetchUserByEmailAddress(companyId, login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			user = fetchUserByScreenName(companyId, login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			user = userPersistence.fetchByPrimaryKey(GetterUtil.getLong(login));
		}

		if (user == null) {
			return 0;
		}

		if (!isUserAllowedToAuthenticate(user)) {
			return 0;
		}

		user = _checkPasswordPolicy(user);

		if (!PropsValues.BASIC_AUTH_PASSWORD_REQUIRED) {
			return user.getUserId();
		}

		String userPassword = user.getPassword();

		if (!user.isPasswordEncrypted()) {
			userPassword = PasswordEncryptorUtil.encrypt(userPassword);
		}

		String encPassword = PasswordEncryptorUtil.encrypt(
			password, userPassword);

		if (userPassword.equals(password) || userPassword.equals(encPassword)) {
			resetFailedLoginAttempts(user);

			return user.getUserId();
		}

		handleAuthenticationFailure(
			login, authType, user, Collections.<String, String[]>emptyMap(),
			Collections.<String, String[]>emptyMap());

		return 0;
	}

	/**
	 * Attempts to authenticate the user using HTTP digest access
	 * authentication, without using the AuthPipeline. Primarily used for
	 * authenticating users of <code>tunnel-web</code>.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  username either the user's email address, screen name, or primary
	 *         key
	 * @param  realm unused
	 * @param  nonce the number used once
	 * @param  method the request method
	 * @param  uri the request URI
	 * @param  response the authentication response hash
	 * @return the user's primary key if authentication is successful;
	 *         <code>0</code> otherwise
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public long authenticateForDigest(
			long companyId, String username, String realm, String nonce,
			String method, String uri, String response)
		throws PortalException {

		if (PropsValues.AUTH_LOGIN_DISABLED) {
			return 0;
		}

		// Get User

		User user = fetchUserByEmailAddress(companyId, username);

		if (user == null) {
			user = fetchUserByScreenName(companyId, username);
		}

		if (user == null) {
			user = userPersistence.fetchByPrimaryKey(
				GetterUtil.getLong(username));
		}

		if (user == null) {
			return 0;
		}

		if (!isUserAllowedToAuthenticate(user)) {
			return 0;
		}

		user = _checkPasswordPolicy(user);

		// Verify digest

		if (Validator.isNull(user.getDigest())) {
			_log.error(
				"User must first login through the portal " + user.getUserId());

			return 0;
		}

		String[] digestArray = StringUtil.split(user.getDigest());

		for (String ha1 : digestArray) {
			String ha2 = DigesterUtil.digestHex(Digester.MD5, method, uri);

			String curResponse = DigesterUtil.digestHex(
				Digester.MD5, ha1, nonce, ha2);

			if (response.equals(curResponse)) {
				resetFailedLoginAttempts(user);

				return user.getUserId();
			}
		}

		Company company = companyPersistence.findByPrimaryKey(companyId);

		handleAuthenticationFailure(
			username, company.getAuthType(), user,
			new HashMap<String, String[]>(), new HashMap<String, String[]>());

		return 0;
	}

	/**
	 * Attempts to authenticate the user using JAAS credentials, without using
	 * the AuthPipeline.
	 *
	 * @param  userId the primary key of the user
	 * @param  encPassword the encrypted password
	 * @return <code>true</code> if authentication is successful;
	 *         <code>false</code> otherwise
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean authenticateForJAAS(long userId, String encPassword) {
		if (PropsValues.AUTH_LOGIN_DISABLED) {
			return false;
		}

		try {
			User user = userPersistence.findByPrimaryKey(userId);

			if (user.isDefaultUser()) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"JAAS authentication is disabled for the default user");
				}

				return false;
			}
			else if (!user.isActive()) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"JAAS authentication is disabled for inactive user " +
							userId);
				}

				return false;
			}

			String userPassword = user.getPassword();

			if (user.isPasswordEncrypted()) {
				if (userPassword.equals(encPassword)) {
					return true;
				}

				if (!PropsValues.PORTAL_JAAS_STRICT_PASSWORD) {
					encPassword = PasswordEncryptorUtil.encrypt(
						encPassword, userPassword);

					if (userPassword.equals(encPassword)) {
						return true;
					}
				}
			}
			else {
				if (!PropsValues.PORTAL_JAAS_STRICT_PASSWORD &&
					userPassword.equals(encPassword)) {

					return true;
				}

				userPassword = PasswordEncryptorUtil.encrypt(
					userPassword, encPassword);

				if (userPassword.equals(encPassword)) {
					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to authenticate for JAAS", e);
		}

		return false;
	}

	/**
	 * Checks if the user is currently locked out based on the password policy,
	 * and performs maintenance on the user's lockout and failed login data.
	 *
	 * @param user the user
	 */
	@Override
	public void checkLockout(User user) throws PortalException {
		if (LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
			return;
		}

		doCheckLockout(user, user.getPasswordPolicy());
	}

	/**
	 * Adds a failed login attempt to the user and updates the user's last
	 * failed login date.
	 *
	 * @param user the user
	 */
	@Override
	public void checkLoginFailure(User user) {
		int failedLoginAttempts = user.getFailedLoginAttempts();

		user.setLastFailedLoginDate(new Date());
		user.setFailedLoginAttempts(++failedLoginAttempts);

		userPersistence.update(user);
	}

	/**
	 * Adds a failed login attempt to the user with the email address and
	 * updates the user's last failed login date.
	 *
	 * @param companyId the primary key of the user's company
	 * @param emailAddress the user's email address
	 */
	@Override
	public void checkLoginFailureByEmailAddress(
			long companyId, String emailAddress)
		throws PortalException {

		User user = getUserByEmailAddress(companyId, emailAddress);

		checkLoginFailure(user);
	}

	/**
	 * Adds a failed login attempt to the user and updates the user's last
	 * failed login date.
	 *
	 * @param userId the primary key of the user
	 */
	@Override
	public void checkLoginFailureById(long userId) throws PortalException {
		User user = userPersistence.findByPrimaryKey(userId);

		checkLoginFailure(user);
	}

	/**
	 * Adds a failed login attempt to the user with the screen name and updates
	 * the user's last failed login date.
	 *
	 * @param companyId the primary key of the user's company
	 * @param screenName the user's screen name
	 */
	@Override
	public void checkLoginFailureByScreenName(long companyId, String screenName)
		throws PortalException {

		User user = getUserByScreenName(companyId, screenName);

		checkLoginFailure(user);
	}

	/**
	 * Checks if the user's password is expired based on the password policy,
	 * and performs maintenance on the user's grace login and password reset
	 * data.
	 *
	 * @param user the user
	 */
	@Override
	public void checkPasswordExpired(User user) throws PortalException {
		if (LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
			return;
		}

		doCheckPasswordExpired(user, user.getPasswordPolicy());
	}

	/**
	 * Completes the user's registration by generating a password and sending
	 * the confirmation email.
	 *
	 * @param user the user
	 * @param serviceContext the service context to be applied. You can specify
	 *        an unencrypted custom password for the user via attribute
	 *        <code>passwordUnencrypted</code>. You automatically generate a
	 *        password for the user by setting attribute
	 *        <code>autoPassword</code> to <code>true</code>. You can send a
	 *        confirmation email to the user by setting attribute
	 *        <code>sendEmail</code> to <code>true</code>.
	 */
	@Override
	public void completeUserRegistration(
			User user, ServiceContext serviceContext)
		throws PortalException {

		boolean autoPassword = ParamUtil.getBoolean(
			serviceContext, "autoPassword");

		String password = (String)serviceContext.getAttribute(
			"passwordUnencrypted");

		if (autoPassword) {
			if (LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(4);

					sb.append("When LDAP password policy is enabled, it is ");
					sb.append("possible that portal generated passwords will ");
					sb.append("not match the LDAP policy. Using ");
					sb.append("RegExpToolkit to generate new password.");

					_log.warn(sb.toString());
				}

				RegExpToolkit regExpToolkit = new RegExpToolkit();

				password = regExpToolkit.generate(null);
			}
			else {
				PasswordPolicy passwordPolicy =
					passwordPolicyLocalService.getPasswordPolicy(
						user.getCompanyId(), user.getOrganizationIds());

				password = PwdToolkitUtil.generate(passwordPolicy);
			}

			serviceContext.setAttribute("passwordUnencrypted", password);

			PasswordModificationThreadLocal.setPasswordModified(true);
			PasswordModificationThreadLocal.setPasswordUnencrypted(password);

			user.setPassword(PasswordEncryptorUtil.encrypt(password));
			user.setPasswordUnencrypted(password);
			user.setPasswordEncrypted(true);
			user.setPasswordModified(true);
			user.setPasswordModifiedDate(new Date());

			userPersistence.update(user);

			user.setPasswordModified(false);
		}

		if (user.hasCompanyMx()) {
			mailService.addUser(
				user.getCompanyId(), user.getUserId(), password,
				user.getFirstName(), user.getMiddleName(), user.getLastName(),
				user.getEmailAddress());
		}

		boolean sendEmail = ParamUtil.getBoolean(serviceContext, "sendEmail");

		if (sendEmail) {
			notifyUser(user, serviceContext);
		}

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		if (company.isStrangersVerify() && (user.getLdapServerId() < 0)) {
			sendEmailAddressVerification(
				user, user.getEmailAddress(), serviceContext);
		}
	}

	/**
	 * Decrypts the user's primary key and password from their encrypted forms.
	 * Used for decrypting a user's credentials from the values stored in an
	 * automatic login cookie.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  name the encrypted primary key of the user
	 * @param  password the encrypted password of the user
	 * @return the user's primary key and password
	 */
	@Override
	public KeyValuePair decryptUserId(
			long companyId, String name, String password)
		throws PortalException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		try {
			name = Encryptor.decrypt(company.getKeyObj(), name);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}

		long userId = GetterUtil.getLong(name);

		User user = userPersistence.findByPrimaryKey(userId);

		try {
			password = Encryptor.decrypt(company.getKeyObj(), password);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}

		String userPassword = user.getPassword();

		String encPassword = PasswordEncryptorUtil.encrypt(
			password, userPassword);

		if (userPassword.equals(encPassword)) {
			if (isPasswordExpired(user)) {
				user.setPasswordReset(true);

				userPersistence.update(user);
			}

			return new KeyValuePair(name, password);
		}

		throw new PrincipalException.MustBeAuthenticated(userId);
	}

	/**
	 * Deletes the user's portrait image.
	 *
	 * @param userId the primary key of the user
	 */
	@Override
	public void deletePortrait(long userId) throws PortalException {
		User user = userPersistence.findByPrimaryKey(userId);

		PortalUtil.updateImageId(user, false, null, "portraitId", 0, 0, 0);
	}

	/**
	 * Removes the user from the role.
	 *
	 * @param roleId the primary key of the role
	 * @param userId the primary key of the user
	 */
	@Override
	public void deleteRoleUser(long roleId, long userId)
		throws PortalException {

		rolePersistence.removeUser(roleId, userId);

		reindex(userId);
	}

	/**
	 * Deletes the user.
	 *
	 * @param  userId the primary key of the user
	 * @return the deleted user
	 */
	@Override
	public User deleteUser(long userId) throws PortalException {
		User user = userPersistence.findByPrimaryKey(userId);

		return deleteUser(user);
	}

	/**
	 * Deletes the user.
	 *
	 * @param  user the user
	 * @return the deleted user
	 */
	@Override
	public User deleteUser(User user) throws PortalException {
		if (!PropsValues.USERS_DELETE) {
			throw new RequiredUserException();
		}

		// Browser tracker

		browserTrackerLocalService.deleteUserBrowserTracker(user.getUserId());

		// Group

		Group group = null;

		if (!user.isDefaultUser()) {
			group = user.getGroup();
		}

		if (group != null) {
			groupLocalService.deleteGroup(group);
		}

		// Portrait

		try {
			imageLocalService.deleteImage(user.getPortraitId());
		}
		catch (NoSuchImageException nsie) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to delete image " + user.getPortraitId(), nsie);
			}
		}

		// Password policy relation

		passwordPolicyRelLocalService.deletePasswordPolicyRel(
			User.class.getName(), user.getUserId());

		// Old passwords

		passwordTrackerLocalService.deletePasswordTrackers(user.getUserId());

		// External user ids

		userIdMapperLocalService.deleteUserIdMappers(user.getUserId());

		// Announcements

		announcementsDeliveryLocalService.deleteDeliveries(user.getUserId());

		// Asset

		assetEntryLocalService.deleteEntry(
			User.class.getName(), user.getUserId());

		// Expando

		expandoRowLocalService.deleteRows(user.getUserId());

		// Membership requests

		membershipRequestLocalService.deleteMembershipRequestsByUserId(
			user.getUserId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			User.class.getName(), user.getUserId());

		// Social

		socialActivityLocalService.deleteUserActivities(user.getUserId());
		socialRequestLocalService.deleteReceiverUserRequests(user.getUserId());
		socialRequestLocalService.deleteUserRequests(user.getUserId());

		// Mail

		mailService.deleteUser(user.getCompanyId(), user.getUserId());

		// Contact

		Contact contact = contactLocalService.fetchContact(user.getContactId());

		if (contact != null) {
			contactLocalService.deleteContact(contact);
		}

		// Group roles

		userGroupRoleLocalService.deleteUserGroupRolesByUserId(
			user.getUserId());

		// Recent layouts

		recentLayoutBranchLocalService.deleteUserRecentLayoutBranches(
			user.getUserId());
		recentLayoutRevisionLocalService.deleteUserRecentLayoutRevisions(
			user.getUserId());
		recentLayoutSetBranchLocalService.deleteUserRecentLayoutSetBranches(
			user.getUserId());

		// Resources

		resourceLocalService.deleteResource(
			user.getCompanyId(), User.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, user.getUserId());

		// User

		userPersistence.remove(user);

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			user.getCompanyId(), 0, User.class.getName(), user.getUserId());

		return user;
	}

	/**
	 * Removes the user from the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param userId the primary key of the user
	 */
	@Override
	public void deleteUserGroupUser(long userGroupId, long userId)
		throws PortalException {

		userGroupPersistence.removeUser(userGroupId, userId);

		reindex(userId);
	}

	/**
	 * Encrypts the primary key of the user. Used when encrypting the user's
	 * credentials for storage in an automatic login cookie.
	 *
	 * @param  name the primary key of the user
	 * @return the user's encrypted primary key
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String encryptUserId(String name) throws PortalException {
		long userId = GetterUtil.getLong(name);

		User user = userPersistence.findByPrimaryKey(userId);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		try {
			return Encryptor.encrypt(company.getKeyObj(), name);
		}
		catch (EncryptorException ee) {
			throw new SystemException(ee);
		}
	}

	/**
	 * Returns the user with the contact ID.
	 *
	 * @param  contactId the user's contact ID
	 * @return the user with the contact ID, or <code>null</code> if a user with
	 *         the contact ID could not be found
	 */
	@Override
	public User fetchUserByContactId(long contactId) {
		return userPersistence.fetchByContactId(contactId);
	}

	/**
	 * Returns the user with the email address.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  emailAddress the user's email address
	 * @return the user with the email address, or <code>null</code> if a user
	 *         with the email address could not be found
	 */
	@Override
	public User fetchUserByEmailAddress(long companyId, String emailAddress) {
		emailAddress = getLogin(emailAddress);

		return userPersistence.fetchByC_EA(companyId, emailAddress);
	}

	/**
	 * Returns the user with the Facebook ID.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  facebookId the user's Facebook ID
	 * @return the user with the Facebook ID, or <code>null</code> if a user
	 *         with the Facebook ID could not be found
	 */
	@Override
	public User fetchUserByFacebookId(long companyId, long facebookId) {
		return userPersistence.fetchByC_FID(companyId, facebookId);
	}

	/**
	 * Returns the user with the Google user ID.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  googleUserId the user's Google user ID
	 * @return the user with the Google user ID, or <code>null</code> if a user
	 *         with the Google user ID could not be found
	 */
	@Override
	public User fetchUserByGoogleUserId(long companyId, String googleUserId) {
		return userPersistence.fetchByC_GUID(companyId, googleUserId);
	}

	/**
	 * Returns the user with the primary key.
	 *
	 * @param  userId the primary key of the user
	 * @return the user with the primary key, or <code>null</code> if a user
	 *         with the primary key could not be found
	 */
	@Override
	public User fetchUserById(long userId) {
		return userPersistence.fetchByPrimaryKey(userId);
	}

	/**
	 * Returns the user with the OpenID.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  openId the user's OpenID
	 * @return the user with the OpenID, or <code>null</code> if a user with the
	 *         OpenID could not be found
	 */
	@Override
	public User fetchUserByOpenId(long companyId, String openId) {
		return userPersistence.fetchByC_O(companyId, openId);
	}

	/**
	 * Returns the user with the portrait ID.
	 *
	 * @param  portraitId the user's portrait ID
	 * @return the user with the portrait ID, or <code>null</code> if a user
	 *         with the portrait ID could not be found
	 */
	@Override
	public User fetchUserByPortraitId(long portraitId) {
		return userPersistence.fetchByPortraitId(portraitId);
	}

	/**
	 * Returns the user with the screen name.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  screenName the user's screen name
	 * @return the user with the screen name, or <code>null</code> if a user
	 *         with the screen name could not be found
	 */
	@Override
	public User fetchUserByScreenName(long companyId, String screenName) {
		screenName = getLogin(screenName);

		return userPersistence.fetchByC_SN(companyId, screenName);
	}

	/**
	 * Returns a range of all the users belonging to the company.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @return the range of users belonging to the company
	 */
	@Override
	public List<User> getCompanyUsers(long companyId, int start, int end) {
		return userPersistence.findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns the number of users belonging to the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the number of users belonging to the company
	 */
	@Override
	public int getCompanyUsersCount(long companyId) {
		return userPersistence.countByCompanyId(companyId);
	}

	/**
	 * Returns the default user for the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the default user for the company
	 */
	@Override
	@Transactional(enabled = false)
	public User getDefaultUser(long companyId) throws PortalException {
		User userModel = _defaultUsers.get(companyId);

		if (userModel == null) {
			userModel = userLocalService.loadGetDefaultUser(companyId);

			_defaultUsers.put(companyId, userModel);
		}

		return userModel;
	}

	/**
	 * Returns the primary key of the default user for the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the primary key of the default user for the company
	 */
	@Override
	@Transactional(enabled = false)
	public long getDefaultUserId(long companyId) throws PortalException {
		User user = getDefaultUser(companyId);

		return user.getUserId();
	}

	/**
	 * Returns the primary keys of all the users belonging to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the primary keys of the users belonging to the group
	 */
	@Override
	public long[] getGroupUserIds(long groupId) {
		return groupPersistence.getUserPrimaryKeys(groupId);
	}

	/**
	 * Returns the users belonging to a group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  status the workflow status
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @param  obc the comparator to order the users by (optionally
	 *         <code>null</code>)
	 * @return the matching users
	 */
	@Override
	public List<User> getGroupUsers(
			long groupId, int status, int start, int end,
			OrderByComparator<User> obc)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"usersGroups", Long.valueOf(groupId)
			).build();

		return search(
			group.getCompanyId(), null, status, params, start, end, obc);
	}

	/**
	 * Returns the users belonging to a group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  status the workflow status
	 * @param  obc the comparator to order the users by (optionally
	 *         <code>null</code>)
	 * @return the matching users
	 */
	@Override
	public List<User> getGroupUsers(
			long groupId, int status, OrderByComparator<User> obc)
		throws PortalException {

		return getGroupUsers(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
	}

	/**
	 * Returns the number of users with the status belonging to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  status the workflow status
	 * @return the number of users with the status belonging to the group
	 */
	@Override
	public int getGroupUsersCount(long groupId, int status)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"usersGroups", Long.valueOf(groupId)
			).build();

		return searchCount(group.getCompanyId(), null, status, params);
	}

	@Override
	public List<User> getInheritedRoleUsers(
			long roleId, int start, int end, OrderByComparator<User> obc)
		throws PortalException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"inherit", Boolean.TRUE
			).put(
				"usersRoles", roleId
			).build();

		return search(
			role.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
			params, start, end, obc);
	}

	/**
	 * Returns all the users who have not had any announcements of the type
	 * delivered, excluding the default user.
	 *
	 * @param  type the type of announcement
	 * @return the users who have not had any annoucements of the type delivered
	 */
	@Override
	public List<User> getNoAnnouncementsDeliveries(String type) {
		return userFinder.findByNoAnnouncementsDeliveries(type);
	}

	/**
	 * Returns all the users who do not belong to any groups, excluding the
	 * default user.
	 *
	 * @return the users who do not belong to any groups
	 */
	@Override
	public List<User> getNoGroups() {
		return userFinder.findByNoGroups();
	}

	@Override
	public int getOrganizationsAndUserGroupsUsersCount(
		long[] organizationIds, long[] userGroupIds) {

		return userFinder.countByOrganizationsAndUserGroups(
			organizationIds, userGroupIds);
	}

	/**
	 * Returns the primary keys of all the users belonging to the organization.
	 *
	 * @param  organizationId the primary key of the organization
	 * @return the primary keys of the users belonging to the organization
	 */
	@Override
	public long[] getOrganizationUserIds(long organizationId) {
		return organizationPersistence.getUserPrimaryKeys(organizationId);
	}

	/**
	 * Returns the users belonging to the organization with the status.
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  status the workflow status
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @param  obc the comparator to order the users by (optionally
	 *         <code>null</code>)
	 * @return the matching users
	 */
	@Override
	public List<User> getOrganizationUsers(
			long organizationId, int status, int start, int end,
			OrderByComparator<User> obc)
		throws PortalException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"usersOrgs", Long.valueOf(organizationId)
			).build();

		return search(
			organization.getCompanyId(), null, status, params, start, end, obc);
	}

	/**
	 * Returns the users belonging to the organization with the status.
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  status the workflow status
	 * @param  obc the comparator to order the users by (optionally
	 *         <code>null</code>)
	 * @return the matching users
	 */
	@Override
	public List<User> getOrganizationUsers(
			long organizationId, int status, OrderByComparator<User> obc)
		throws PortalException {

		return getOrganizationUsers(
			organizationId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
	}

	/**
	 * Returns the number of users with the status belonging to the
	 * organization.
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  status the workflow status
	 * @return the number of users with the status belonging to the organization
	 */
	@Override
	public int getOrganizationUsersCount(long organizationId, int status)
		throws PortalException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"usersOrgs", Long.valueOf(organizationId)
			).build();

		return searchCount(organization.getCompanyId(), null, status, params);
	}

	/**
	 * Returns the primary keys of all the users belonging to the role.
	 *
	 * @param  roleId the primary key of the role
	 * @return the primary keys of the users belonging to the role
	 */
	@Override
	public long[] getRoleUserIds(long roleId) {
		return rolePersistence.getUserPrimaryKeys(roleId);
	}

	/**
	 * Returns the number of users with the status belonging to the role.
	 *
	 * @param  roleId the primary key of the role
	 * @param  status the workflow status
	 * @return the number of users with the status belonging to the role
	 */
	@Override
	public int getRoleUsersCount(long roleId, int status)
		throws PortalException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"usersRoles", Long.valueOf(roleId)
			).build();

		return searchCount(role.getCompanyId(), null, status, params);
	}

	@Override
	public List<User> getSocialUsers(
			long userId, int socialRelationType,
			String socialRelationTypeComparator, int start, int end,
			OrderByComparator<User> obc)
		throws PortalException {

		if (!socialRelationTypeComparator.equals(StringPool.EQUAL) &&
			!socialRelationTypeComparator.equals(StringPool.NOT_EQUAL)) {

			throw new IllegalArgumentException(
				"Invalid social relation type comparator " +
					socialRelationTypeComparator);
		}

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			List<SocialRelation> socialRelations =
				socialRelationPersistence.findByU1_T(
					userId, socialRelationType);

			if (socialRelationTypeComparator.equals(StringPool.NOT_EQUAL)) {
				socialRelations = ListUtil.remove(
					socialRelationPersistence.findByUserId1(userId),
					socialRelations);
			}

			List<User> users = new ArrayList<>();

			for (SocialRelation socialRelation : socialRelations) {
				User user = userPersistence.findByPrimaryKey(
					socialRelation.getUserId2());

				if (user.isDefaultUser() ||
					(user.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

					continue;
				}

				if (!users.contains(user)) {
					users.add(user);
				}
			}

			if (obc != null) {
				users = ListUtil.sort(users, obc);
			}

			return users;
		}

		User user = userPersistence.findByPrimaryKey(userId);

		return userFinder.findBySocialUsers(
			user.getCompanyId(), userId, socialRelationType,
			socialRelationTypeComparator, WorkflowConstants.STATUS_APPROVED,
			start, end, obc);
	}

	/**
	 * Returns an ordered range of all the users with a mutual social relation
	 * of the type with both of the given users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId1 the primary key of the first user
	 * @param  userId2 the primary key of the second user
	 * @param  socialRelationType the type of social relation. The possible
	 *         types can be found in {@link SocialRelationConstants}.
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @param  obc the comparator to order the users by (optionally
	 *         <code>null</code>)
	 * @return the ordered range of users with a mutual social relation of the
	 *         type with the user
	 */
	@Override
	public List<User> getSocialUsers(
			long userId1, long userId2, int socialRelationType, int start,
			int end, OrderByComparator<User> obc)
		throws PortalException {

		User user1 = userPersistence.findByPrimaryKey(userId1);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"socialMutualRelationType",
				new Long[] {
					userId1, Long.valueOf(socialRelationType), userId2,
					Long.valueOf(socialRelationType)
				}
			).build();

		return search(
			user1.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
			params, start, end, obc);
	}

	/**
	 * Returns an ordered range of all the users with a mutual social relation
	 * with both of the given users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId1 the primary key of the first user
	 * @param  userId2 the primary key of the second user
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @param  obc the comparator to order the users by (optionally
	 *         <code>null</code>)
	 * @return the ordered range of users with a mutual social relation with the
	 *         user
	 */
	@Override
	public List<User> getSocialUsers(
			long userId1, long userId2, int start, int end,
			OrderByComparator<User> obc)
		throws PortalException {

		User user1 = userPersistence.findByPrimaryKey(userId1);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"socialMutualRelation", new Long[] {userId1, userId2}
			).build();

		return search(
			user1.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
			params, start, end, obc);
	}

	/**
	 * Returns the number of users with a social relation with the user.
	 *
	 * @param  userId the primary key of the user
	 * @param  socialRelationType the type of social relation. The possible
	 *         types can be found in {@link SocialRelationConstants}.
	 * @return the number of users with a social relation with the user
	 */
	@Override
	public int getSocialUsersCount(
			long userId, int socialRelationType,
			String socialRelationTypeComparator)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (!socialRelationTypeComparator.equals(StringPool.EQUAL) &&
			!socialRelationTypeComparator.equals(StringPool.NOT_EQUAL)) {

			throw new IllegalArgumentException(
				"Invalid social relation type comparator " +
					socialRelationTypeComparator);
		}

		return userFinder.countBySocialUsers(
			user.getCompanyId(), user.getUserId(), socialRelationType,
			socialRelationTypeComparator, WorkflowConstants.STATUS_APPROVED);
	}

	/**
	 * Returns the number of users with a mutual social relation with both of
	 * the given users.
	 *
	 * @param  userId1 the primary key of the first user
	 * @param  userId2 the primary key of the second user
	 * @return the number of users with a mutual social relation with the user
	 */
	@Override
	public int getSocialUsersCount(long userId1, long userId2)
		throws PortalException {

		User user1 = userPersistence.findByPrimaryKey(userId1);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"socialMutualRelation", new Long[] {userId1, userId2}
			).build();

		return searchCount(
			user1.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
			params);
	}

	/**
	 * Returns the number of users with a mutual social relation of the type
	 * with both of the given users.
	 *
	 * @param  userId1 the primary key of the first user
	 * @param  userId2 the primary key of the second user
	 * @param  socialRelationType the type of social relation. The possible
	 *         types can be found in {@link SocialRelationConstants}.
	 * @return the number of users with a mutual social relation of the type
	 *         with the user
	 */
	@Override
	public int getSocialUsersCount(
			long userId1, long userId2, int socialRelationType)
		throws PortalException {

		User user1 = userPersistence.findByPrimaryKey(userId1);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"socialMutualRelationType",
				new Long[] {
					userId1, Long.valueOf(socialRelationType), userId2,
					Long.valueOf(socialRelationType)
				}
			).build();

		return searchCount(
			user1.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
			params);
	}

	/**
	 * Returns the user with the contact ID.
	 *
	 * @param  contactId the user's contact ID
	 * @return the user with the contact ID
	 */
	@Override
	public User getUserByContactId(long contactId) throws PortalException {
		return userPersistence.findByContactId(contactId);
	}

	/**
	 * Returns the user with the email address.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  emailAddress the user's email address
	 * @return the user with the email address
	 */
	@Override
	public User getUserByEmailAddress(long companyId, String emailAddress)
		throws PortalException {

		emailAddress = getLogin(emailAddress);

		return userPersistence.findByC_EA(companyId, emailAddress);
	}

	/**
	 * Returns the user with the Facebook ID.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  facebookId the user's Facebook ID
	 * @return the user with the Facebook ID
	 */
	@Override
	public User getUserByFacebookId(long companyId, long facebookId)
		throws PortalException {

		return userPersistence.findByC_FID(companyId, facebookId);
	}

	/**
	 * Returns the user with the Google user ID.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  googleUserId the user's Google user ID
	 * @return the user with the Google user ID
	 */
	@Override
	public User getUserByGoogleUserId(long companyId, String googleUserId)
		throws PortalException {

		return userPersistence.findByC_GUID(companyId, googleUserId);
	}

	/**
	 * Returns the user with the primary key.
	 *
	 * @param  userId the primary key of the user
	 * @return the user with the primary key
	 */
	@Override
	public User getUserById(long userId) throws PortalException {
		return userPersistence.findByPrimaryKey(userId);
	}

	/**
	 * Returns the user with the primary key from the company.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  userId the primary key of the user
	 * @return the user with the primary key
	 */
	@Override
	public User getUserById(long companyId, long userId)
		throws PortalException {

		return userPersistence.findByC_U(companyId, userId);
	}

	/**
	 * Returns the user with the OpenID.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  openId the user's OpenID
	 * @return the user with the OpenID
	 */
	@Override
	public User getUserByOpenId(long companyId, String openId)
		throws PortalException {

		return userPersistence.findByC_O(companyId, openId);
	}

	/**
	 * Returns the user with the portrait ID.
	 *
	 * @param  portraitId the user's portrait ID
	 * @return the user with the portrait ID
	 */
	@Override
	public User getUserByPortraitId(long portraitId) throws PortalException {
		return userPersistence.findByPortraitId(portraitId);
	}

	/**
	 * Returns the user with the screen name.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  screenName the user's screen name
	 * @return the user with the screen name
	 */
	@Override
	public User getUserByScreenName(long companyId, String screenName)
		throws PortalException {

		screenName = getLogin(screenName);

		return userPersistence.findByC_SN(companyId, screenName);
	}

	/**
	 * Returns the user with the UUID.
	 *
	 * @param  uuid the user's UUID
	 * @param  companyId the primary key of the user's company
	 * @return the user with the UUID
	 */
	@Override
	public User getUserByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		List<User> users = userPersistence.findByUuid_C(uuid, companyId);

		if (users.isEmpty()) {
			StringBundler sb = new StringBundler(5);

			sb.append("{uuid=");
			sb.append(uuid);
			sb.append(", companyId=");
			sb.append(companyId);
			sb.append("}");

			throw new NoSuchUserException(sb.toString());
		}

		return users.get(0);
	}

	/**
	 * Returns the number of users with the status belonging to the user group.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @param  status the workflow status
	 * @return the number of users with the status belonging to the user group
	 */
	@Override
	public int getUserGroupUsersCount(long userGroupId, int status)
		throws PortalException {

		UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
			userGroupId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"usersUserGroups", Long.valueOf(userGroupId)
			).build();

		return searchCount(userGroup.getCompanyId(), null, status, params);
	}

	/**
	 * Returns the primary key of the user with the email address.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  emailAddress the user's email address
	 * @return the primary key of the user with the email address
	 */
	@Override
	public long getUserIdByEmailAddress(long companyId, String emailAddress)
		throws PortalException {

		emailAddress = StringUtil.toLowerCase(StringUtil.trim(emailAddress));

		User user = userPersistence.findByC_EA(companyId, emailAddress);

		return user.getUserId();
	}

	/**
	 * Returns the primary key of the user with the screen name.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  screenName the user's screen name
	 * @return the primary key of the user with the screen name
	 */
	@Override
	public long getUserIdByScreenName(long companyId, String screenName)
		throws PortalException {

		screenName = getLogin(screenName);

		User user = userPersistence.findByC_SN(companyId, screenName);

		return user.getUserId();
	}

	@Override
	public List<User> getUsers(
		long companyId, boolean defaultUser, int status, int start, int end,
		OrderByComparator<User> obc) {

		return userPersistence.findByC_DU_S(
			companyId, defaultUser, status, start, end, obc);
	}

	@Override
	public int getUsersCount(long companyId, boolean defaultUser, int status) {
		return userPersistence.countByC_DU_S(companyId, defaultUser, status);
	}

	/**
	 * Returns <code>true</code> if the password policy has been assigned to the
	 * user.
	 *
	 * @param  passwordPolicyId the primary key of the password policy
	 * @param  userId the primary key of the user
	 * @return <code>true</code> if the password policy is assigned to the user;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean hasPasswordPolicyUser(long passwordPolicyId, long userId) {
		return passwordPolicyRelLocalService.hasPasswordPolicyRel(
			passwordPolicyId, User.class.getName(), userId);
	}

	/**
	 * Returns <code>true</code> if the user has the role with the name,
	 * optionally through inheritance.
	 *
	 * @param  companyId the primary key of the role's company
	 * @param  name the name of the role (must be a regular role, not an
	 *         organization, site or provider role)
	 * @param  userId the primary key of the user
	 * @param  inherited whether to include roles inherited from organizations,
	 *         sites, etc.
	 * @return <code>true</code> if the user has the role; <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean hasRoleUser(
			long companyId, String name, long userId, boolean inherited)
		throws PortalException {

		return roleLocalService.hasUserRole(userId, companyId, name, inherited);
	}

	/**
	 * Returns <code>true</code> if the user's password is expired.
	 *
	 * @param  user the user
	 * @return <code>true</code> if the user's password is expired;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean isPasswordExpired(User user) throws PortalException {
		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		if ((passwordPolicy != null) && passwordPolicy.isExpireable()) {
			long currentTime = System.currentTimeMillis();

			long passwordModifiedTime = 0;

			Date passwordModifiedDate = user.getPasswordModifiedDate();

			if (passwordModifiedDate == null) {
				passwordModifiedTime = currentTime;
			}
			else {
				passwordModifiedTime = passwordModifiedDate.getTime();
			}

			long elapsedTime = currentTime - passwordModifiedTime;

			if (elapsedTime > (passwordPolicy.getMaxAge() * 1000)) {
				return true;
			}

			return false;
		}

		return false;
	}

	/**
	 * Returns the default user for the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the default user for the company
	 */
	@Override
	public User loadGetDefaultUser(long companyId) throws PortalException {
		return userPersistence.findByC_DU(companyId, true);
	}

	/**
	 * Returns an ordered range of all the users who match the keywords and
	 * status, without using the indexer. It is preferable to use the indexed
	 * version {@link #search(long, String, int, LinkedHashMap, int, int, Sort)}
	 * instead of this method wherever possible for performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         user's first name, middle name, last name, screen name, or email
	 *         address
	 * @param  status the workflow status
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portal.kernel.service.persistence.UserFinder}.
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @param  obc the comparator to order the users by (optionally
	 *         <code>null</code>)
	 * @return the matching users
	 * @see    com.liferay.portal.kernel.service.persistence.UserFinder
	 */
	@Override
	public List<User> search(
		long companyId, String keywords, int status,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<User> obc) {

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		if (!indexer.isIndexerEnabled() ||
			!PropsValues.USERS_SEARCH_WITH_INDEX || isUseCustomSQL(params)) {

			return userFinder.findByKeywords(
				companyId, keywords, status, params, start, end, obc);
		}

		try {
			return UsersAdminUtil.getUsers(
				search(
					companyId, keywords, status, params, start, end,
					getSorts(obc)));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns an ordered range of all the users who match the keywords and
	 * status, using the indexer. It is preferable to use this method instead of
	 * the non-indexed version whenever possible for performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         user's first name, middle name, last name, screen name, or email
	 *         address
	 * @param  status the workflow status
	 * @param  params the indexer parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portlet.usersadmin.util.UserIndexer}.
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @param  sort the field and direction to sort by (optionally
	 *         <code>null</code>)
	 * @return the matching users
	 * @see    com.liferay.portlet.usersadmin.util.UserIndexer
	 */
	@Override
	public Hits search(
		long companyId, String keywords, int status,
		LinkedHashMap<String, Object> params, int start, int end, Sort sort) {

		return search(
			companyId, keywords, status, params, start, end, new Sort[] {sort});
	}

	@Override
	public Hits search(
		long companyId, String keywords, int status,
		LinkedHashMap<String, Object> params, int start, int end,
		Sort[] sorts) {

		String firstName = null;
		String middleName = null;
		String lastName = null;
		String fullName = null;
		String screenName = null;
		String emailAddress = null;
		String street = null;
		String city = null;
		String zip = null;
		String region = null;
		String country = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			firstName = keywords;
			middleName = keywords;
			lastName = keywords;
			fullName = keywords;
			screenName = keywords;
			emailAddress = keywords;
			street = keywords;
			city = keywords;
			zip = keywords;
			region = keywords;
			country = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		try {
			Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				User.class);

			SearchContext searchContext = buildSearchContext(
				companyId, firstName, middleName, lastName, fullName,
				screenName, emailAddress, street, city, zip, region, country,
				status, params, andOperator, start, end, sorts);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns an ordered range of all the users with the status, and whose
	 * first name, middle name, last name, screen name, and email address match
	 * the keywords specified for them, without using the indexer. It is
	 * preferable to use the indexed version {@link #search(long, String,
	 * String, String, String, String, int, LinkedHashMap, boolean, int, int,
	 * Sort)} instead of this method wherever possible for performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  firstName the first name keywords (space separated)
	 * @param  middleName the middle name keywords
	 * @param  lastName the last name keywords
	 * @param  screenName the screen name keywords
	 * @param  emailAddress the email address keywords
	 * @param  status the workflow status
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portal.kernel.service.persistence.UserFinder}.
	 * @param  andSearch whether every field must match its keywords, or just
	 *         one field. For example, &quot;users with the first name 'bob' and
	 *         last name 'smith'&quot; vs &quot;users with the first name 'bob'
	 *         or the last name 'smith'&quot;.
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @param  obc the comparator to order the users by (optionally
	 *         <code>null</code>)
	 * @return the matching users
	 * @see    com.liferay.portal.kernel.service.persistence.UserFinder
	 */
	@Override
	public List<User> search(
		long companyId, String firstName, String middleName, String lastName,
		String screenName, String emailAddress, int status,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, OrderByComparator<User> obc) {

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		if (!indexer.isIndexerEnabled() ||
			!PropsValues.USERS_SEARCH_WITH_INDEX || isUseCustomSQL(params)) {

			return userFinder.findByC_FN_MN_LN_SN_EA_S(
				companyId, firstName, middleName, lastName, screenName,
				emailAddress, status, params, andSearch, start, end, obc);
		}

		try {
			return UsersAdminUtil.getUsers(
				search(
					companyId, firstName, middleName, lastName, screenName,
					emailAddress, status, params, andSearch, start, end,
					getSorts(obc)));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns an ordered range of all the users with the status, and whose
	 * first name, middle name, last name, screen name, and email address match
	 * the keywords specified for them, using the indexer. It is preferable to
	 * use this method instead of the non-indexed version whenever possible for
	 * performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  firstName the first name keywords (space separated)
	 * @param  middleName the middle name keywords
	 * @param  lastName the last name keywords
	 * @param  screenName the screen name keywords
	 * @param  emailAddress the email address keywords
	 * @param  status the workflow status
	 * @param  params the indexer parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portlet.usersadmin.util.UserIndexer}.
	 * @param  andSearch whether every field must match its keywords, or just
	 *         one field. For example, &quot;users with the first name 'bob' and
	 *         last name 'smith'&quot; vs &quot;users with the first name 'bob'
	 *         or the last name 'smith'&quot;.
	 * @param  start the lower bound of the range of users
	 * @param  end the upper bound of the range of users (not inclusive)
	 * @param  sort the field and direction to sort by (optionally
	 *         <code>null</code>)
	 * @return the matching users
	 * @see    com.liferay.portlet.usersadmin.util.UserIndexer
	 */
	@Override
	public Hits search(
		long companyId, String firstName, String middleName, String lastName,
		String screenName, String emailAddress, int status,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort sort) {

		return search(
			companyId, firstName, middleName, lastName, screenName,
			emailAddress, status, params, andSearch, start, end,
			new Sort[] {sort});
	}

	@Override
	public Hits search(
		long companyId, String firstName, String middleName, String lastName,
		String screenName, String emailAddress, int status,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort[] sorts) {

		try {
			Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				User.class);

			SearchContext searchContext = buildSearchContext(
				companyId, firstName, middleName, lastName, null, screenName,
				emailAddress, null, null, null, null, null, status, params,
				andSearch, start, end, sorts);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns the number of users who match the keywords and status.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         user's first name, middle name, last name, screen name, or email
	 *         address
	 * @param  status the workflow status
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portal.kernel.service.persistence.UserFinder}.
	 * @return the number matching users
	 */
	@Override
	public int searchCount(
		long companyId, String keywords, int status,
		LinkedHashMap<String, Object> params) {

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		if (!indexer.isIndexerEnabled() ||
			!PropsValues.USERS_SEARCH_WITH_INDEX || isUseCustomSQL(params)) {

			return userFinder.countByKeywords(
				companyId, keywords, status, params);
		}

		try {
			String firstName = null;
			String middleName = null;
			String lastName = null;
			String fullName = null;
			String screenName = null;
			String emailAddress = null;
			String street = null;
			String city = null;
			String zip = null;
			String region = null;
			String country = null;
			boolean andOperator = false;

			if (Validator.isNotNull(keywords)) {
				firstName = keywords;
				middleName = keywords;
				lastName = keywords;
				fullName = keywords;
				screenName = keywords;
				emailAddress = keywords;
				street = keywords;
				city = keywords;
				zip = keywords;
				region = keywords;
				country = keywords;
			}
			else {
				andOperator = true;
			}

			if (params != null) {
				params.put("keywords", keywords);
			}

			SearchContext searchContext = buildSearchContext(
				companyId, firstName, middleName, lastName, fullName,
				screenName, emailAddress, street, city, zip, region, country,
				status, params, andOperator, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			return (int)indexer.searchCount(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Returns the number of users with the status, and whose first name, middle
	 * name, last name, screen name, and email address match the keywords
	 * specified for them.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  firstName the first name keywords (space separated)
	 * @param  middleName the middle name keywords
	 * @param  lastName the last name keywords
	 * @param  screenName the screen name keywords
	 * @param  emailAddress the email address keywords
	 * @param  status the workflow status
	 * @param  params the finder parameters (optionally <code>null</code>). For
	 *         more information see {@link
	 *         com.liferay.portal.kernel.service.persistence.UserFinder}.
	 * @param  andSearch whether every field must match its keywords, or just
	 *         one field. For example, &quot;users with the first name 'bob' and
	 *         last name 'smith'&quot; vs &quot;users with the first name 'bob'
	 *         or the last name 'smith'&quot;.
	 * @return the number of matching users
	 */
	@Override
	public int searchCount(
		long companyId, String firstName, String middleName, String lastName,
		String screenName, String emailAddress, int status,
		LinkedHashMap<String, Object> params, boolean andSearch) {

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		if (!indexer.isIndexerEnabled() ||
			!PropsValues.USERS_SEARCH_WITH_INDEX || isUseCustomSQL(params)) {

			return userFinder.countByC_FN_MN_LN_SN_EA_S(
				companyId, firstName, middleName, lastName, screenName,
				emailAddress, status, params, andSearch);
		}

		try {
			FullNameGenerator fullNameGenerator =
				FullNameGeneratorFactory.getInstance();

			String fullName = fullNameGenerator.getFullName(
				firstName, middleName, lastName);

			SearchContext searchContext = buildSearchContext(
				companyId, firstName, middleName, lastName, fullName,
				screenName, emailAddress, null, null, null, null, null, status,
				params, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			return (int)indexer.searchCount(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public Map<Long, Integer> searchCounts(
		long companyId, int status, long[] groupIds) {

		return userFinder.countByGroups(companyId, status, groupIds);
	}

	@Override
	public List<User> searchSocial(
			long userId, int[] socialRelationTypes, String keywords, int start,
			int end)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"socialRelationType",
				new Long[][] {
					{userId}, ArrayUtil.toLongArray(socialRelationTypes)
				}
			).put(
				"wildcardMode", WildcardMode.TRAILING
			).build();

		return userFinder.findByKeywords(
			user.getCompanyId(), keywords, WorkflowConstants.STATUS_APPROVED,
			params, start, end, null);
	}

	@Override
	public List<User> searchSocial(
		long companyId, long[] groupIds, String keywords, int start, int end) {

		return searchSocial(companyId, groupIds, keywords, start, end, null);
	}

	@Override
	public List<User> searchSocial(
		long companyId, long[] groupIds, String keywords, int start, int end,
		OrderByComparator<User> obc) {

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"usersGroups", ArrayUtil.toLongArray(groupIds)
			).put(
				"wildcardMode", WildcardMode.TRAILING
			).build();

		return userFinder.findByKeywords(
			companyId, keywords, WorkflowConstants.STATUS_APPROVED, params,
			start, end, obc);
	}

	@Override
	public List<User> searchSocial(
			long[] groupIds, long userId, int[] socialRelationTypes,
			String keywords, int start, int end)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"socialRelationType",
				new Long[][] {
					{userId}, ArrayUtil.toLongArray(socialRelationTypes)
				}
			).put(
				"socialRelationTypeUnionUserGroups", true
			).put(
				"usersGroups", ArrayUtil.toLongArray(groupIds)
			).put(
				"wildcardMode", WildcardMode.TRAILING
			).build();

		return userFinder.findByKeywords(
			user.getCompanyId(), keywords, WorkflowConstants.STATUS_APPROVED,
			params, start, end, null);
	}

	@Override
	public BaseModelSearchResult<User> searchUsers(
			long companyId, String keywords, int status,
			LinkedHashMap<String, Object> params, int start, int end, Sort sort)
		throws PortalException {

		return searchUsers(
			companyId, keywords, status, params, start, end, new Sort[] {sort});
	}

	@Override
	public BaseModelSearchResult<User> searchUsers(
			long companyId, String keywords, int status,
			LinkedHashMap<String, Object> params, int start, int end,
			Sort[] sorts)
		throws PortalException {

		String firstName = null;
		String middleName = null;
		String lastName = null;
		String fullName = null;
		String screenName = null;
		String emailAddress = null;
		String street = null;
		String city = null;
		String zip = null;
		String region = null;
		String country = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			firstName = keywords;
			middleName = keywords;
			lastName = keywords;
			fullName = keywords;
			screenName = keywords;
			emailAddress = keywords;
			street = keywords;
			city = keywords;
			zip = keywords;
			region = keywords;
			country = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		SearchContext searchContext = buildSearchContext(
			companyId, firstName, middleName, lastName, fullName, screenName,
			emailAddress, street, city, zip, region, country, status, params,
			andOperator, start, end, sorts);

		return searchUsers(searchContext);
	}

	@Override
	public BaseModelSearchResult<User> searchUsers(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress, int status,
			LinkedHashMap<String, Object> params, boolean andSearch, int start,
			int end, Sort sort)
		throws PortalException {

		return searchUsers(
			companyId, firstName, middleName, lastName, screenName,
			emailAddress, status, params, andSearch, start, end,
			new Sort[] {sort});
	}

	@Override
	public BaseModelSearchResult<User> searchUsers(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress, int status,
			LinkedHashMap<String, Object> params, boolean andSearch, int start,
			int end, Sort[] sorts)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, firstName, middleName, lastName, null, screenName,
			emailAddress, null, null, null, null, null, status, params,
			andSearch, start, end, sorts);

		return searchUsers(searchContext);
	}

	/**
	 * Sends an email address verification to the user.
	 *
	 * @param user the verification email recipient
	 * @param emailAddress the recipient's email address
	 * @param serviceContext the service context to be applied. Must set the
	 *        portal URL, main path, primary key of the layout, remote address,
	 *        remote host, and agent for the user.
	 */
	@Override
	public void sendEmailAddressVerification(
			User user, String emailAddress, ServiceContext serviceContext)
		throws PortalException {

		if (user.isEmailAddressVerified() &&
			StringUtil.equalsIgnoreCase(emailAddress, user.getEmailAddress())) {

			return;
		}

		Ticket ticket = ticketLocalService.addDistinctTicket(
			user.getCompanyId(), User.class.getName(), user.getUserId(),
			TicketConstants.TYPE_EMAIL_ADDRESS, emailAddress, null,
			serviceContext);

		String verifyEmailAddressURL = StringBundler.concat(
			serviceContext.getPortalURL(), serviceContext.getPathMain(),
			"/portal/verify_email_address?ticketKey=", ticket.getKey());

		long plid = serviceContext.getPlid();

		if (plid > 0) {
			Layout layout = layoutLocalService.fetchLayout(plid);

			if (layout != null) {
				Group group = layout.getGroup();

				if (!layout.isPrivateLayout() && !group.isUser()) {
					verifyEmailAddressURL +=
						"&p_l_id=" + serviceContext.getPlid();
				}
			}
		}

		String fromAddress = PrefsPropsUtil.getString(
			user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		String fromName = PrefsPropsUtil.getString(
			user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(user.getCompanyId(), true);

		Map<Locale, String> localizedSubjectMap =
			LocalizationUtil.getLocalizationMap(
				companyPortletPreferences, "adminEmailVerificationSubject",
				PropsKeys.ADMIN_EMAIL_VERIFICATION_SUBJECT);
		Map<Locale, String> localizedBodyMap =
			LocalizationUtil.getLocalizationMap(
				companyPortletPreferences, "adminEmailVerificationBody",
				PropsKeys.ADMIN_EMAIL_VERIFICATION_BODY);

		String subject = _getLocalizedValue(
			localizedSubjectMap, user.getLocale(), LocaleUtil.getDefault());

		String body = _getLocalizedValue(
			localizedBodyMap, user.getLocale(), LocaleUtil.getDefault());

		Company company = companyLocalService.getCompany(user.getCompanyId());

		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		mailTemplateContextBuilder.put(
			"[$COMPANY_ID$]", String.valueOf(company.getCompanyId()));
		mailTemplateContextBuilder.put("[$COMPANY_MX$]", company.getMx());
		mailTemplateContextBuilder.put(
			"[$COMPANY_NAME$]", HtmlUtil.escape(company.getName()));
		mailTemplateContextBuilder.put(
			"[$EMAIL_VERIFICATION_CODE$]", HtmlUtil.escape(ticket.getKey()));
		mailTemplateContextBuilder.put(
			"[$EMAIL_VERIFICATION_URL$]", verifyEmailAddressURL);
		mailTemplateContextBuilder.put("[$FROM_ADDRESS$]", fromAddress);
		mailTemplateContextBuilder.put(
			"[$FROM_NAME$]", HtmlUtil.escape(fromName));
		mailTemplateContextBuilder.put(
			"[$PORTAL_URL$]", company.getPortalURL(0));
		mailTemplateContextBuilder.put(
			"[$REMOTE_ADDRESS$]", serviceContext.getRemoteAddr());
		mailTemplateContextBuilder.put(
			"[$REMOTE_HOST$]", HtmlUtil.escape(serviceContext.getRemoteHost()));
		mailTemplateContextBuilder.put("[$TO_ADDRESS$]", emailAddress);
		mailTemplateContextBuilder.put(
			"[$TO_NAME$]", HtmlUtil.escape(user.getFullName()));
		mailTemplateContextBuilder.put(
			"[$USER_ID$]", String.valueOf(user.getUserId()));
		mailTemplateContextBuilder.put(
			"[$USER_SCREENNAME$]", HtmlUtil.escape(user.getScreenName()));

		MailTemplateContext mailTemplateContext =
			mailTemplateContextBuilder.build();

		_sendNotificationEmail(
			fromAddress, fromName, emailAddress, user, subject, body,
			mailTemplateContext);
	}

	/**
	 * Sends the password email to the user with the email address. The content
	 * of this email can be specified in <code>portal.properties</code> with the
	 * <code>admin.email.password</code> keys.
	 *
	 * @param companyId the primary key of the user's company
	 * @param emailAddress the user's email address
	 * @param fromName the name of the individual that the email should be from
	 * @param fromAddress the address of the individual that the email should be
	 *        from
	 * @param subject the email subject. If <code>null</code>, the subject
	 *        specified in <code>portal.properties</code> will be used.
	 * @param body the email body. If <code>null</code>, the body specified in
	 *        <code>portal.properties</code> will be used.
	 * @param serviceContext the service context to be applied
	 */
	@Override
	public boolean sendPassword(
			long companyId, String emailAddress, String fromName,
			String fromAddress, String subject, String body,
			ServiceContext serviceContext)
		throws PortalException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		if (!company.isSendPasswordResetLink()) {
			throw new SendPasswordException.MustBeEnabled(company);
		}

		emailAddress = StringUtil.toLowerCase(StringUtil.trim(emailAddress));

		if (Validator.isNull(emailAddress)) {
			throw new UserEmailAddressException.MustNotBeNull();
		}

		User user = userPersistence.findByC_EA(companyId, emailAddress);

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		Date expirationDate = null;

		if ((passwordPolicy != null) &&
			(passwordPolicy.getResetTicketMaxAge() > 0)) {

			expirationDate = new Date(
				System.currentTimeMillis() +
					(passwordPolicy.getResetTicketMaxAge() * 1000));
		}

		Ticket ticket = ticketLocalService.addDistinctTicket(
			companyId, User.class.getName(), user.getUserId(),
			TicketConstants.TYPE_PASSWORD, null, expirationDate,
			serviceContext);

		StringBundler sb = new StringBundler(6);

		sb.append(serviceContext.getPortalURL());
		sb.append(serviceContext.getPathMain());
		sb.append("/portal/update_password?p_l_id=");
		sb.append(serviceContext.getPlid());
		sb.append("&ticketKey=");
		sb.append(ticket.getKey());

		String passwordResetURL = sb.toString();

		sendPasswordNotification(
			user, companyId, null, passwordResetURL, fromName, fromAddress,
			subject, body, serviceContext);

		return false;
	}

	/**
	 * Sends a password notification email to the user matching the email
	 * address. The portal's settings determine whether a password is sent
	 * explicitly or whether a link for resetting the user's password is sent.
	 * The method sends the email asynchronously and returns before the email is
	 * sent.
	 *
	 * <p>
	 * The content of the notification email is specified with the
	 * <code>admin.email.password</code> portal property keys. They can be
	 * overridden via a <code>portal-ext.properties</code> file or modified
	 * through the Portal Settings UI.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  emailAddress the user's email address
	 * @return <code>true</code> if the notification email includes a new
	 *         password; <code>false</code> if the notification email only
	 *         contains a reset link
	 */
	@Override
	public boolean sendPasswordByEmailAddress(
			long companyId, String emailAddress)
		throws PortalException {

		User user = userPersistence.findByC_EA(companyId, emailAddress);

		return sendPassword(
			user.getCompanyId(), user.getEmailAddress(), null, null, null, null,
			ServiceContextThreadLocal.getServiceContext());
	}

	/**
	 * Sends a password notification email to the user matching the screen name.
	 * The portal's settings determine whether a password is sent explicitly or
	 * whether a link for resetting the user's password is sent. The method
	 * sends the email asynchronously and returns before the email is sent.
	 *
	 * <p>
	 * The content of the notification email is specified with the
	 * <code>admin.email.password</code> portal property keys. They can be
	 * overridden via a <code>portal-ext.properties</code> file or modified
	 * through the Portal Settings UI.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  screenName the user's screen name
	 * @return <code>true</code> if the notification email includes a new
	 *         password; <code>false</code> if the notification email only
	 *         contains a reset link
	 */
	@Override
	public boolean sendPasswordByScreenName(long companyId, String screenName)
		throws PortalException {

		User user = userPersistence.findByC_SN(companyId, screenName);

		return sendPassword(
			user.getCompanyId(), user.getEmailAddress(), null, null, null, null,
			ServiceContextThreadLocal.getServiceContext());
	}

	/**
	 * Sends a password notification email to the user matching the ID. The
	 * portal's settings determine whether a password is sent explicitly or
	 * whether a link for resetting the user's password is sent. The method
	 * sends the email asynchronously and returns before the email is sent.
	 *
	 * <p>
	 * The content of the notification email is specified with the
	 * <code>admin.email.password</code> portal property keys. They can be
	 * overridden via a <code>portal-ext.properties</code> file or modified
	 * through the Portal Settings UI.
	 * </p>
	 *
	 * @param  userId the user's primary key
	 * @return <code>true</code> if the notification email includes a new
	 *         password; <code>false</code> if the notification email only
	 *         contains a reset link
	 */
	@Override
	public boolean sendPasswordByUserId(long userId) throws PortalException {
		User user = userPersistence.findByPrimaryKey(userId);

		return sendPassword(
			user.getCompanyId(), user.getEmailAddress(), null, null, null, null,
			ServiceContextThreadLocal.getServiceContext());
	}

	/**
	 * Sets the users in the role, removing and adding users to the role as
	 * necessary.
	 *
	 * @param roleId the primary key of the role
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void setRoleUsers(long roleId, long[] userIds)
		throws PortalException {

		long[] oldUserIds = rolePersistence.getUserPrimaryKeys(roleId);

		Set<Long> updatedUserIdsSet = SetUtil.symmetricDifference(
			userIds, oldUserIds);

		long[] updateUserIds = ArrayUtil.toLongArray(updatedUserIdsSet);

		rolePersistence.setUsers(roleId, userIds);

		reindex(updateUserIds);
	}

	/**
	 * Sets the users in the user group, removing and adding users to the user
	 * group as necessary.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param userIds the primary keys of the users
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException {

		if (PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {
			userGroupLocalService.copyUserGroupLayouts(userGroupId, userIds);
		}

		long[] oldUserIds = userGroupPersistence.getUserPrimaryKeys(
			userGroupId);

		Set<Long> updatedUserIdsSet = SetUtil.symmetricDifference(
			userIds, oldUserIds);

		long[] updateUserIds = ArrayUtil.toLongArray(updatedUserIdsSet);

		userGroupPersistence.setUsers(userGroupId, userIds);

		reindex(updateUserIds);
	}

	/**
	 * Removes the users from the teams of a group.
	 *
	 * @param groupId the primary key of the group
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void unsetGroupTeamsUsers(long groupId, long[] userIds)
		throws PortalException {

		List<Team> teams = teamPersistence.findByGroupId(groupId);

		for (Team team : teams) {
			unsetTeamUsers(team.getTeamId(), userIds);
		}
	}

	/**
	 * Removes the users from the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userIds the primary keys of the users
	 * @param serviceContext the service context to be applied (optionally
	 *        <code>null</code>)
	 */
	@Override
	public void unsetGroupUsers(
			long groupId, long[] userIds, ServiceContext serviceContext)
		throws PortalException {

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, groupId, RoleConstants.TYPE_SITE);

		unsetGroupTeamsUsers(groupId, userIds);

		groupPersistence.removeUsers(groupId, userIds);

		reindex(userIds);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Message message = new Message();

				message.put("groupId", groupId);
				message.put("userIds", userIds);

				MessageBusUtil.sendMessage(
					DestinationNames.SUBSCRIPTION_CLEAN_UP, message);

				return null;
			});
	}

	/**
	 * Removes the users from the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		final Group group = organization.getGroup();

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, group.getGroupId());

		organizationPersistence.removeUsers(organizationId, userIds);

		reindex(userIds);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Message message = new Message();

				message.put("groupId", group.getGroupId());
				message.put("userIds", userIds);

				MessageBusUtil.sendMessage(
					DestinationNames.SUBSCRIPTION_CLEAN_UP, message);

				return null;
			});
	}

	/**
	 * Removes the users from the password policy.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void unsetPasswordPolicyUsers(
		long passwordPolicyId, long[] userIds) {

		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			_checkPasswordReset(
				passwordPolicyLocalService.getDefaultPasswordPolicy(companyId),
				userIds);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}

		passwordPolicyRelLocalService.deletePasswordPolicyRels(
			passwordPolicyId, User.class.getName(), userIds);
	}

	/**
	 * Removes the users from the role.
	 *
	 * @param roleId the primary key of the role
	 * @param users the users
	 */
	@Override
	public void unsetRoleUsers(long roleId, List<User> users)
		throws PortalException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		String roleName = role.getName();

		if ((roleName.equals(RoleConstants.ADMINISTRATOR) &&
			 (getRoleUsersCount(role.getRoleId()) <= 1))) {

			throw new RequiredRoleException.RequiredAdminRoleException();
		}

		if (roleName.equals(RoleConstants.USER)) {
			throw new RequiredRoleException.RequiredUserRoleException();
		}

		rolePersistence.removeUsers(roleId, users);

		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		indexer.reindex(users);
	}

	/**
	 * Removes the users from the role.
	 *
	 * @param roleId the primary key of the role
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void unsetRoleUsers(long roleId, long[] userIds)
		throws PortalException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		String roleName = role.getName();

		if ((roleName.equals(RoleConstants.ADMINISTRATOR) &&
			 (getRoleUsersCount(role.getRoleId()) <= 1))) {

			throw new RequiredRoleException.RequiredAdminRoleException();
		}

		if (roleName.equals(RoleConstants.USER)) {
			throw new RequiredRoleException.RequiredUserRoleException();
		}

		rolePersistence.removeUsers(roleId, userIds);

		reindex(userIds);
	}

	/**
	 * Removes the users from the team.
	 *
	 * @param teamId the primary key of the team
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void unsetTeamUsers(long teamId, long[] userIds)
		throws PortalException {

		teamPersistence.removeUsers(teamId, userIds);

		reindex(userIds);
	}

	/**
	 * Removes the users from the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param userIds the primary keys of the users
	 */
	@Override
	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException {

		userGroupPersistence.removeUsers(userGroupId, userIds);

		reindex(userIds);
	}

	/**
	 * Updates whether the user has agreed to the terms of use.
	 *
	 * @param  userId the primary key of the user
	 * @param  agreedToTermsOfUse whether the user has agreet to the terms of
	 *         use
	 * @return the user
	 */
	@Override
	public User updateAgreedToTermsOfUse(
			long userId, boolean agreedToTermsOfUse)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setAgreedToTermsOfUse(agreedToTermsOfUse);

		userPersistence.update(user);

		return user;
	}

	/**
	 * Updates the user's asset with the new asset categories and tag names,
	 * removing and adding asset categories and tag names as necessary.
	 *
	 * @param userId the primary key of the user
	 * @param user ID the primary key of the user
	 * @param assetCategoryIds the primary key's of the new asset categories
	 * @param assetTagNames the new asset tag names
	 */
	@Override
	public void updateAsset(
			long userId, User user, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException {

		User owner = userPersistence.findByPrimaryKey(userId);

		Company company = companyPersistence.findByPrimaryKey(
			owner.getCompanyId());

		Group companyGroup = company.getGroup();

		assetEntryLocalService.updateEntry(
			userId, companyGroup.getGroupId(), user.getCreateDate(),
			user.getModifiedDate(), User.class.getName(), user.getUserId(),
			user.getUuid(), 0, assetCategoryIds, assetTagNames, true, false,
			null, null, null, null, null, user.getFullName(), null, null, null,
			null, 0, 0, null);
	}

	/**
	 * Updates the user's creation date.
	 *
	 * @param  userId the primary key of the user
	 * @param  createDate the new creation date
	 * @return the user
	 */
	@Override
	public User updateCreateDate(long userId, Date createDate)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setCreateDate(createDate);

		userPersistence.update(user);

		return user;
	}

	/**
	 * Updates the user's email address.
	 *
	 * @param  userId the primary key of the user
	 * @param  password the user's password
	 * @param  emailAddress1 the user's new email address
	 * @param  emailAddress2 the user's new email address confirmation
	 * @return the user
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public User updateEmailAddress(
			long userId, String password, String emailAddress1,
			String emailAddress2)
		throws PortalException {

		emailAddress1 = StringUtil.toLowerCase(StringUtil.trim(emailAddress1));
		emailAddress2 = StringUtil.toLowerCase(StringUtil.trim(emailAddress2));

		User user = userPersistence.findByPrimaryKey(userId);

		validateEmailAddress(user, emailAddress1, emailAddress2);

		setEmailAddress(
			user, password, user.getFirstName(), user.getMiddleName(),
			user.getLastName(), emailAddress1);

		userPersistence.update(user);

		Contact contact = user.getContact();

		contact.setEmailAddress(user.getEmailAddress());

		contactPersistence.update(contact);

		return user;
	}

	/**
	 * Updates the user's email address or sends verification email.
	 *
	 * @param  userId the primary key of the user
	 * @param  password the user's password
	 * @param  emailAddress1 the user's new email address
	 * @param  emailAddress2 the user's new email address confirmation
	 * @param  serviceContext the service context to be applied. Must set the
	 *         portal URL, main path, primary key of the layout, remote address,
	 *         remote host, and agent for the user.
	 * @return the user
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public User updateEmailAddress(
			long userId, String password, String emailAddress1,
			String emailAddress2, ServiceContext serviceContext)
		throws PortalException {

		emailAddress1 = StringUtil.toLowerCase(StringUtil.trim(emailAddress1));
		emailAddress2 = StringUtil.toLowerCase(StringUtil.trim(emailAddress2));

		User user = userPersistence.findByPrimaryKey(userId);

		validateEmailAddress(user, emailAddress1, emailAddress2);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		if (company.isStrangersVerify() &&
			!StringUtil.equalsIgnoreCase(
				emailAddress1, user.getEmailAddress())) {

			sendEmailAddressVerification(user, emailAddress1, serviceContext);
		}
		else {
			setEmailAddress(
				user, password, user.getFirstName(), user.getMiddleName(),
				user.getLastName(), emailAddress1);

			userPersistence.update(user);

			Contact contact = user.getContact();

			contact.setEmailAddress(user.getEmailAddress());

			contactPersistence.update(contact);
		}

		return user;
	}

	/**
	 * Updates whether the user has verified email address.
	 *
	 * @param  userId the primary key of the user
	 * @param  emailAddressVerified whether the user has verified email address
	 * @return the user
	 */
	@Override
	public User updateEmailAddressVerified(
			long userId, boolean emailAddressVerified)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setEmailAddressVerified(emailAddressVerified);

		userPersistence.update(user);

		return user;
	}

	/**
	 * Updates the user's Facebook ID.
	 *
	 * @param  userId the primary key of the user
	 * @param  facebookId the user's new Facebook ID
	 * @return the user
	 */
	@Override
	public User updateFacebookId(long userId, long facebookId)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setFacebookId(facebookId);

		userPersistence.update(user);

		return user;
	}

	/**
	 * Updates the user's Google user ID.
	 *
	 * @param  userId the primary key of the user
	 * @param  googleUserId the new Google user ID
	 * @return the user
	 */
	@Override
	public User updateGoogleUserId(long userId, String googleUserId)
		throws PortalException {

		googleUserId = StringUtil.trim(googleUserId);

		User user = userPersistence.findByPrimaryKey(userId);

		validateGoogleUserId(user.getCompanyId(), userId, googleUserId);

		user.setGoogleUserId(googleUserId);

		userPersistence.update(user);

		return user;
	}

	/**
	 * Sets the groups the user is in, removing and adding groups as necessary.
	 *
	 * @param userId the primary key of the user
	 * @param newGroupIds the primary keys of the groups
	 * @param serviceContext the service context to be applied (optionally
	 *        <code>null</code>)
	 */
	@Override
	public void updateGroups(
			long userId, long[] newGroupIds, ServiceContext serviceContext)
		throws PortalException {

		boolean indexingEnabled = true;

		if (serviceContext != null) {
			indexingEnabled = serviceContext.isIndexingEnabled();
		}

		updateGroups(userId, newGroupIds, indexingEnabled);
	}

	/**
	 * Updates a user account that was automatically created when a guest user
	 * participated in an action (e.g. posting a comment) and only provided his
	 * name and email address.
	 *
	 * @param  creatorUserId the primary key of the creator
	 * @param  companyId the primary key of the user's company
	 * @param  autoPassword whether a password should be automatically generated
	 *         for the user
	 * @param  password1 the user's password
	 * @param  password2 the user's password confirmation
	 * @param  autoScreenName whether a screen name should be automatically
	 *         generated for the user
	 * @param  screenName the user's screen name
	 * @param  emailAddress the user's email address
	 * @param  facebookId the user's facebook ID
	 * @param  openId the user's OpenID
	 * @param  locale the user's locale
	 * @param  firstName the user's first name
	 * @param  middleName the user's middle name
	 * @param  lastName the user's last name
	 * @param  prefixId the user's name prefix ID
	 * @param  suffixId the user's name suffix ID
	 * @param  male whether the user is male
	 * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
	 *         January)
	 * @param  birthdayDay the user's birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  jobTitle the user's job title
	 * @param  updateUserInformation whether to update the user's information
	 * @param  sendEmail whether to send the user an email notification about
	 *         their new account
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         user.
	 * @return the user
	 */
	@Override
	public User updateIncompleteUser(
			long creatorUserId, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, long facebookId,
			String openId, Locale locale, String firstName, String middleName,
			String lastName, long prefixId, long suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, boolean updateUserInformation, boolean sendEmail,
			ServiceContext serviceContext)
		throws PortalException {

		User user = getUserByEmailAddress(companyId, emailAddress);

		if (user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) {
			throw new PortalException("Invalid user status");
		}

		User defaultUser = getDefaultUser(companyId);

		if (facebookId > 0) {
			autoPassword = false;

			if ((password1 == null) || (password2 == null)) {
				password1 = PwdGenerator.getPassword();

				password2 = password1;
			}

			sendEmail = false;
		}

		if (updateUserInformation) {
			autoScreenName = false;

			if (PrefsPropsUtil.getBoolean(
					companyId,
					PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE)) {

				autoScreenName = true;
			}

			validate(
				companyId, user.getUserId(), autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, openId, firstName,
				middleName, lastName, null, locale);

			if (!autoPassword &&
				(Validator.isNull(password1) || Validator.isNull(password2))) {

				throw new UserPasswordException.MustNotBeNull(user.getUserId());
			}

			if (autoScreenName) {
				ScreenNameGenerator screenNameGenerator =
					ScreenNameGeneratorFactory.getInstance();

				try {
					screenName = screenNameGenerator.generate(
						companyId, user.getUserId(), emailAddress);
				}
				catch (Exception e) {
					throw new SystemException(e);
				}
			}

			FullNameGenerator fullNameGenerator =
				FullNameGeneratorFactory.getInstance();

			String fullName = fullNameGenerator.getFullName(
				firstName, middleName, lastName);

			String greeting = LanguageUtil.format(
				locale, "welcome-x", fullName, false);

			if (Validator.isNotNull(password1)) {
				user.setPassword(PasswordEncryptorUtil.encrypt(password1));
				user.setPasswordUnencrypted(password1);
			}

			user.setPasswordEncrypted(true);

			PasswordPolicy passwordPolicy = defaultUser.getPasswordPolicy();

			if ((passwordPolicy != null) && passwordPolicy.isChangeable() &&
				passwordPolicy.isChangeRequired()) {

				user.setPasswordReset(true);
			}
			else {
				user.setPasswordReset(false);
			}

			user.setScreenName(screenName);
			user.setFacebookId(facebookId);
			user.setOpenId(openId);
			user.setLanguageId(locale.toString());
			user.setTimeZoneId(defaultUser.getTimeZoneId());
			user.setGreeting(greeting);
			user.setFirstName(firstName);
			user.setMiddleName(middleName);
			user.setLastName(lastName);
			user.setJobTitle(jobTitle);
			user.setExpandoBridgeAttributes(serviceContext);

			Date birthday = getBirthday(
				birthdayMonth, birthdayDay, birthdayYear);

			Contact contact = user.getContact();

			contact.setFirstName(firstName);
			contact.setMiddleName(middleName);
			contact.setLastName(lastName);
			contact.setPrefixId(prefixId);
			contact.setSuffixId(suffixId);
			contact.setMale(male);
			contact.setBirthday(birthday);
			contact.setJobTitle(jobTitle);

			contactPersistence.update(contact, serviceContext);

			// Indexer

			Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				User.class);

			indexer.reindex(user);
		}

		user.setStatus(WorkflowConstants.STATUS_DRAFT);

		userPersistence.update(user, serviceContext);

		// Workflow

		long workflowUserId = creatorUserId;

		if (workflowUserId == user.getUserId()) {
			workflowUserId = defaultUser.getUserId();
		}

		ServiceContext workflowServiceContext = serviceContext;

		if (workflowServiceContext == null) {
			workflowServiceContext = new ServiceContext();
		}

		workflowServiceContext.setAttribute("autoPassword", autoPassword);
		workflowServiceContext.setAttribute("passwordUnencrypted", password1);
		workflowServiceContext.setAttribute("sendEmail", sendEmail);

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			companyId, workflowUserId, User.class.getName(), user.getUserId(),
			user, workflowServiceContext);

		return getUserByEmailAddress(companyId, emailAddress);
	}

	/**
	 * Updates the user's job title.
	 *
	 * @param  userId the primary key of the user
	 * @param  jobTitle the user's job title
	 * @return the user
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public User updateJobTitle(long userId, String jobTitle)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setJobTitle(jobTitle);

		userPersistence.update(user);

		Contact contact = contactPersistence.findByPrimaryKey(
			user.getContactId());

		contact.setJobTitle(jobTitle);

		contactPersistence.update(contact);

		return user;
	}

	/**
	 * Updates the user's last login with the current time and the IP address.
	 *
	 * @param  userId the primary key of the user
	 * @param  loginIP the IP address the user logged in from
	 * @return the user
	 */
	@Override
	public User updateLastLogin(long userId, String loginIP)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		Date lastLoginDate = user.getLoginDate();

		if (lastLoginDate == null) {
			lastLoginDate = new Date();
		}

		String lastLoginIP = user.getLoginIP();

		if (lastLoginIP == null) {
			lastLoginIP = loginIP;
		}

		user.setLoginDate(new Date());
		user.setLoginIP(loginIP);
		user.setLastLoginDate(lastLoginDate);
		user.setLastLoginIP(lastLoginIP);

		return resetFailedLoginAttempts(user, true);
	}

	/**
	 * Updates whether the user is locked out from logging in.
	 *
	 * @param  user the user
	 * @param  lockout whether the user is locked out
	 * @return the user
	 */
	@Override
	public User updateLockout(User user, boolean lockout)
		throws PortalException {

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		if ((passwordPolicy == null) || !passwordPolicy.isLockout()) {
			return user;
		}

		Date lockoutDate = null;

		if (lockout) {
			lockoutDate = new Date();
		}

		user.setLockout(lockout);
		user.setLockoutDate(lockoutDate);

		if (!lockout) {
			user.setFailedLoginAttempts(0);
		}

		userPersistence.update(user);

		return user;
	}

	/**
	 * Updates whether the user is locked out from logging in.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  emailAddress the user's email address
	 * @param  lockout whether the user is locked out
	 * @return the user
	 */
	@Override
	public User updateLockoutByEmailAddress(
			long companyId, String emailAddress, boolean lockout)
		throws PortalException {

		User user = getUserByEmailAddress(companyId, emailAddress);

		return updateLockout(user, lockout);
	}

	/**
	 * Updates whether the user is locked out from logging in.
	 *
	 * @param  userId the primary key of the user
	 * @param  lockout whether the user is locked out
	 * @return the user
	 */
	@Override
	public User updateLockoutById(long userId, boolean lockout)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		return updateLockout(user, lockout);
	}

	/**
	 * Updates whether the user is locked out from logging in.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  screenName the user's screen name
	 * @param  lockout whether the user is locked out
	 * @return the user
	 */
	@Override
	public User updateLockoutByScreenName(
			long companyId, String screenName, boolean lockout)
		throws PortalException {

		User user = getUserByScreenName(companyId, screenName);

		return updateLockout(user, lockout);
	}

	/**
	 * Updates the user's modified date.
	 *
	 * @param  userId the primary key of the user
	 * @param  modifiedDate the new modified date
	 * @return the user
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public User updateModifiedDate(long userId, Date modifiedDate)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setModifiedDate(modifiedDate);

		userPersistence.update(user);

		return user;
	}

	/**
	 * Updates the user's OpenID.
	 *
	 * @param  userId the primary key of the user
	 * @param  openId the new OpenID
	 * @return the user
	 */
	@Override
	public User updateOpenId(long userId, String openId)
		throws PortalException {

		openId = StringUtil.trim(openId);

		User user = userPersistence.findByPrimaryKey(userId);

		user.setOpenId(openId);

		userPersistence.update(user);

		return user;
	}

	/**
	 * Sets the organizations that the user is in, removing and adding
	 * organizations as necessary.
	 *
	 * @param userId the primary key of the user
	 * @param newOrganizationIds the primary keys of the organizations
	 * @param serviceContext the service context to be applied. Must set whether
	 *        user indexing is enabled.
	 */
	@Override
	public void updateOrganizations(
			long userId, long[] newOrganizationIds,
			ServiceContext serviceContext)
		throws PortalException {

		updateOrganizations(
			userId, newOrganizationIds, serviceContext.isIndexingEnabled());
	}

	/**
	 * Updates the user's password without tracking or validation of the change.
	 *
	 * @param  userId the primary key of the user
	 * @param  password1 the user's new password
	 * @param  password2 the user's new password confirmation
	 * @param  passwordReset whether the user should be asked to reset their
	 *         password the next time they log in
	 * @return the user
	 */
	@Override
	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException {

		return updatePassword(
			userId, password1, password2, passwordReset, false);
	}

	/**
	 * Updates the user's password, optionally with tracking and validation of
	 * the change.
	 *
	 * @param  userId the primary key of the user
	 * @param  password1 the user's new password
	 * @param  password2 the user's new password confirmation
	 * @param  passwordReset whether the user should be asked to reset their
	 *         password the next time they login
	 * @param  silentUpdate whether the password should be updated without being
	 *         tracked, or validated. Primarily used for password imports.
	 * @return the user
	 */
	@Override
	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset, boolean silentUpdate)
		throws PortalException {

		// Password hashing takes a long time. Therefore, encrypt the password
		// before we get the user to avoid
		// an org.hibernate.StaleObjectStateException.

		String newEncPwd = PasswordEncryptorUtil.encrypt(password1);

		User user = userPersistence.findByPrimaryKey(userId);

		if (!silentUpdate) {
			validatePassword(user.getCompanyId(), userId, password1, password2);

			trackPassword(user);
		}

		if (user.hasCompanyMx()) {
			mailService.updatePassword(user.getCompanyId(), userId, password1);
		}

		user.setPassword(newEncPwd);
		user.setPasswordUnencrypted(password1);
		user.setPasswordEncrypted(true);
		user.setPasswordReset(passwordReset);

		if (!silentUpdate || (user.getPasswordModifiedDate() == null)) {
			user.setPasswordModifiedDate(new Date());
		}

		user.setDigest(user.getDigest(password1));
		user.setGraceLoginCount(0);

		if (!silentUpdate) {
			user.setPasswordModified(true);
		}

		PasswordModificationThreadLocal.setPasswordModified(
			user.getPasswordModified());
		PasswordModificationThreadLocal.setPasswordUnencrypted(
			user.getPasswordUnencrypted());

		try {
			user = userPersistence.update(user);
		}
		catch (ModelListenerException mle) {
			Throwable throwable = mle.getCause();

			String msg = GetterUtil.getString(throwable.getMessage());

			if (LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
				String[] errorPasswordHistoryKeywords =
					LDAPSettingsUtil.getErrorPasswordHistoryKeywords(
						user.getCompanyId());

				for (String errorPasswordHistoryKeyword :
						errorPasswordHistoryKeywords) {

					if (msg.contains(errorPasswordHistoryKeyword)) {
						throw new UserPasswordException.MustNotBeRecentlyUsed(
							userId);
					}
				}
			}

			throw new UserPasswordException.MustComplyWithModelListeners(
				userId, mle);
		}

		if (!silentUpdate) {
			user.setPasswordModified(false);
		}

		if (!silentUpdate && (PrincipalThreadLocal.getUserId() != userId)) {
			sendPasswordNotification(
				user, user.getCompanyId(), password1, null, null, null, null,
				null, ServiceContextThreadLocal.getServiceContext());
		}

		return user;
	}

	/**
	 * Updates the user's password with manually input information. This method
	 * should only be used when performing maintenance.
	 *
	 * @param  userId the primary key of the user
	 * @param  password the user's new password
	 * @param  passwordEncrypted the user's new encrypted password
	 * @param  passwordReset whether the user should be asked to reset their
	 *         password the next time they login
	 * @param  passwordModifiedDate the new password modified date
	 * @return the user
	 */
	@Override
	public User updatePasswordManually(
			long userId, String password, boolean passwordEncrypted,
			boolean passwordReset, Date passwordModifiedDate)
		throws PortalException {

		// This method should only be used to manually massage data

		User user = userPersistence.findByPrimaryKey(userId);

		user.setPassword(password);
		user.setPasswordEncrypted(passwordEncrypted);
		user.setPasswordReset(passwordReset);
		user.setPasswordModifiedDate(passwordModifiedDate);
		user.setDigest(user.getDigest(password));

		userPersistence.update(user);

		return user;
	}

	/**
	 * Updates whether the user should be asked to reset their password the next
	 * time they login.
	 *
	 * @param  userId the primary key of the user
	 * @param  passwordReset whether the user should be asked to reset their
	 *         password the next time they login
	 * @return the user
	 */
	@Override
	public User updatePasswordReset(long userId, boolean passwordReset)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		user.setPasswordReset(passwordReset);

		userPersistence.update(user);

		return user;
	}

	/**
	 * Updates the user's portrait image.
	 *
	 * @param  userId the primary key of the user
	 * @param  bytes the new portrait image data
	 * @return the user
	 */
	@Override
	public User updatePortrait(long userId, byte[] bytes)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		PortalUtil.updateImageId(
			user, true, bytes, "portraitId",
			_userFileUploadsSettings.getImageMaxSize(),
			_userFileUploadsSettings.getImageMaxHeight(),
			_userFileUploadsSettings.getImageMaxWidth());

		return userPersistence.update(user);
	}

	/**
	 * Updates the user's password reset question and answer.
	 *
	 * @param  userId the primary key of the user
	 * @param  question the user's new password reset question
	 * @param  answer the user's new password reset answer
	 * @return the user
	 */
	@Override
	public User updateReminderQuery(long userId, String question, String answer)
		throws PortalException {

		validateReminderQuery(question, answer);

		User user = userPersistence.findByPrimaryKey(userId);

		user.setReminderQueryQuestion(question);
		user.setReminderQueryAnswer(answer);

		userPersistence.update(user);

		return user;
	}

	/**
	 * Updates the user's screen name.
	 *
	 * @param  userId the primary key of the user
	 * @param  screenName the user's new screen name
	 * @return the user
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public User updateScreenName(long userId, String screenName)
		throws PortalException {

		// User

		User user = userPersistence.findByPrimaryKey(userId);

		screenName = getLogin(screenName);

		validateScreenName(user.getCompanyId(), userId, screenName);

		if (!StringUtil.equalsIgnoreCase(user.getScreenName(), screenName)) {
			user.setDigest(StringPool.BLANK);
		}

		user.setScreenName(screenName);

		userPersistence.update(user);

		// Group

		Group group = groupLocalService.getUserGroup(
			user.getCompanyId(), userId);

		group.setFriendlyURL(
			FriendlyURLNormalizerUtil.normalize(StringPool.SLASH + screenName));

		groupPersistence.update(group);

		return user;
	}

	/**
	 * Updates the user's workflow status.
	 *
	 * @param  userId the primary key of the user
	 * @param  status the user's new workflow status
	 * @param  serviceContext the service context to be applied. You can specify
	 *         an unencrypted custom password (used by an LDAP listener) for the
	 *         user via attribute <code>passwordUnencrypted</code>.
	 * @return the user
	 */
	@Override
	public User updateStatus(
			long userId, int status, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(user.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			validateCompanyMaxUsers(user.getCompanyId());
		}

		String passwordUnencrypted = (String)serviceContext.getAttribute(
			"passwordUnencrypted");

		if (Validator.isNotNull(passwordUnencrypted)) {
			user.setPasswordUnencrypted(passwordUnencrypted);
		}

		user.setStatus(status);

		user = userPersistence.update(user, serviceContext);

		reindex(user);

		Group group = user.getGroup();

		if (status == WorkflowConstants.STATUS_INACTIVE) {
			group.setActive(false);
		}
		else {
			group.setActive(true);
		}

		groupLocalService.updateGroup(group);

		return user;
	}

	/**
	 * Updates the user.
	 *
	 * @param  userId the primary key of the user
	 * @param  oldPassword the user's old password
	 * @param  newPassword1 the user's new password (optionally
	 *         <code>null</code>)
	 * @param  newPassword2 the user's new password confirmation (optionally
	 *         <code>null</code>)
	 * @param  passwordReset whether the user should be asked to reset their
	 *         password the next time they login
	 * @param  reminderQueryQuestion the user's new password reset question
	 * @param  reminderQueryAnswer the user's new password reset answer
	 * @param  screenName the user's new screen name
	 * @param  emailAddress the user's new email address
	 * @param  facebookId the user's new Facebook ID
	 * @param  openId the user's new OpenID
	 * @param  hasPortrait if the user has a custom portrait image
	 * @param  portraitBytes the new portrait image data
	 * @param  languageId the user's new language ID
	 * @param  timeZoneId the user's new time zone ID
	 * @param  greeting the user's new greeting
	 * @param  comments the user's new comments
	 * @param  firstName the user's new first name
	 * @param  middleName the user's new middle name
	 * @param  lastName the user's new last name
	 * @param  prefixId the user's new name prefix ID
	 * @param  suffixId the user's new name suffix ID
	 * @param  male whether user is male
	 * @param  birthdayMonth the user's new birthday month (0-based, meaning 0
	 *         for January)
	 * @param  birthdayDay the user's new birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  smsSn the user's new SMS screen name
	 * @param  facebookSn the user's new Facebook screen name
	 * @param  jabberSn the user's new Jabber screen name
	 * @param  skypeSn the user's new Skype screen name
	 * @param  twitterSn the user's new Twitter screen name
	 * @param  jobTitle the user's new job title
	 * @param  groupIds the primary keys of the user's groups
	 * @param  organizationIds the primary keys of the user's organizations
	 * @param  roleIds the primary keys of the user's roles
	 * @param  userGroupRoles the user user's group roles
	 * @param  userGroupIds the primary keys of the user's user groups
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set the UUID (with the <code>uuid</code>
	 *         attribute), asset category IDs, asset tag names, and expando
	 *         bridge attributes for the user.
	 * @return the user
	 */
	@Override
	@SuppressWarnings("deprecation")
	public User updateUser(
			long userId, String oldPassword, String newPassword1,
			String newPassword2, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer,
			String screenName, String emailAddress, long facebookId,
			String openId, boolean hasPortrait, byte[] portraitBytes,
			String languageId, String timeZoneId, String greeting,
			String comments, String firstName, String middleName,
			String lastName, long prefixId, long suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn,
			String facebookSn, String jabberSn, String skypeSn,
			String twitterSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			List<UserGroupRole> userGroupRoles, long[] userGroupIds,
			ServiceContext serviceContext)
		throws PortalException {

		// User

		String password = oldPassword;
		screenName = getLogin(screenName);
		emailAddress = StringUtil.toLowerCase(StringUtil.trim(emailAddress));
		openId = StringUtil.trim(openId);
		facebookSn = StringUtil.toLowerCase(StringUtil.trim(facebookSn));
		jabberSn = StringUtil.toLowerCase(StringUtil.trim(jabberSn));
		skypeSn = StringUtil.toLowerCase(StringUtil.trim(skypeSn));
		twitterSn = StringUtil.toLowerCase(StringUtil.trim(twitterSn));

		EmailAddressGenerator emailAddressGenerator =
			EmailAddressGeneratorFactory.getInstance();

		if (emailAddressGenerator.isGenerated(emailAddress)) {
			emailAddress = StringPool.BLANK;
		}

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		validate(
			userId, screenName, emailAddress, openId, firstName, middleName,
			lastName, smsSn, locale);

		User user = userPersistence.findByPrimaryKey(userId);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		if (!PropsValues.USERS_EMAIL_ADDRESS_REQUIRED &&
			Validator.isNull(emailAddress)) {

			emailAddress = emailAddressGenerator.generate(
				user.getCompanyId(), userId);
		}

		if (Validator.isNotNull(newPassword1) ||
			Validator.isNotNull(newPassword2)) {

			user = updatePassword(
				userId, newPassword1, newPassword2, passwordReset);

			password = newPassword1;

			user.setDigest(user.getDigest(password));
		}

		if (user.getContactId() <= 0) {
			user.setContactId(counterLocalService.increment());
		}

		user.setPasswordReset(passwordReset);

		if (Validator.isNotNull(reminderQueryQuestion) &&
			Validator.isNotNull(reminderQueryAnswer)) {

			user.setReminderQueryQuestion(reminderQueryQuestion);
			user.setReminderQueryAnswer(reminderQueryAnswer);
		}

		if (!StringUtil.equalsIgnoreCase(user.getScreenName(), screenName)) {
			user.setScreenName(screenName);

			user.setDigest(StringPool.BLANK);
		}

		boolean sendEmailAddressVerification = false;

		if (company.isStrangersVerify() &&
			!StringUtil.equalsIgnoreCase(
				emailAddress, user.getEmailAddress())) {

			sendEmailAddressVerification = true;
		}
		else {
			setEmailAddress(
				user, password, firstName, middleName, lastName, emailAddress);
		}

		if (serviceContext != null) {
			String uuid = serviceContext.getUuid();

			if (Validator.isNotNull(uuid)) {
				user.setUuid(uuid);
			}
		}

		user.setFacebookId(facebookId);

		Long ldapServerId = null;

		if (serviceContext != null) {
			ldapServerId = (Long)serviceContext.getAttribute("ldapServerId");
		}

		if (ldapServerId != null) {
			user.setLdapServerId(ldapServerId);
		}

		user.setOpenId(openId);

		PortalUtil.updateImageId(
			user, hasPortrait, portraitBytes, "portraitId",
			_userFileUploadsSettings.getImageMaxSize(),
			_userFileUploadsSettings.getImageMaxHeight(),
			_userFileUploadsSettings.getImageMaxWidth());

		user.setLanguageId(languageId);
		user.setTimeZoneId(timeZoneId);
		user.setGreeting(greeting);
		user.setComments(comments);
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setJobTitle(jobTitle);
		user.setExpandoBridgeAttributes(serviceContext);

		user = userPersistence.update(user, serviceContext);

		// Contact

		Date birthday = getBirthday(birthdayMonth, birthdayDay, birthdayYear);

		long contactId = user.getContactId();

		Contact contact = contactPersistence.fetchByPrimaryKey(contactId);

		if (contact == null) {
			contact = contactPersistence.create(contactId);

			contact.setCompanyId(user.getCompanyId());
			contact.setUserName(StringPool.BLANK);
			contact.setClassName(User.class.getName());
			contact.setClassPK(user.getUserId());
			contact.setAccountId(company.getAccountId());
			contact.setParentContactId(
				ContactConstants.DEFAULT_PARENT_CONTACT_ID);
		}

		contact.setEmailAddress(user.getEmailAddress());
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setSmsSn(smsSn);
		contact.setFacebookSn(facebookSn);
		contact.setJabberSn(jabberSn);
		contact.setSkypeSn(skypeSn);
		contact.setTwitterSn(twitterSn);
		contact.setJobTitle(jobTitle);

		contactPersistence.update(contact, serviceContext);

		// Group

		Group group = groupLocalService.getUserGroup(
			user.getCompanyId(), userId);

		group.setFriendlyURL(
			FriendlyURLNormalizerUtil.normalize(StringPool.SLASH + screenName));

		groupPersistence.update(group);

		// Groups and organizations

		// See LPS-33205. Cache the user's list of user group roles because
		// adding or removing groups may add or remove user group roles
		// depending on the site default user associations.

		List<UserGroupRole> previousUserGroupRoles =
			userGroupRolePersistence.findByUserId(userId);

		updateGroups(userId, groupIds, false);
		updateOrganizations(userId, organizationIds, false);

		// Roles

		if (roleIds != null) {
			roleIds = UsersAdminUtil.addRequiredRoles(user, roleIds);

			userPersistence.setRoles(userId, roleIds);
		}

		// User group roles

		updateUserGroupRoles(
			user, groupIds, organizationIds, userGroupRoles,
			previousUserGroupRoles);

		// User groups

		if (userGroupIds != null) {
			if (PropsValues.USER_GROUPS_COPY_LAYOUTS_TO_USER_PERSONAL_SITE) {
				userGroupLocalService.copyUserGroupLayouts(
					userGroupIds, userId);
			}

			userPersistence.setUserGroups(userId, userGroupIds);
		}

		// Announcements

		announcementsDeliveryLocalService.getUserDeliveries(user.getUserId());

		// Asset

		if (serviceContext != null) {
			updateAsset(
				userId, user, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames());
		}

		// Indexer

		if ((serviceContext == null) || serviceContext.isIndexingEnabled()) {
			Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				User.class);

			indexer.reindex(user);
		}

		// Email address verification

		if ((serviceContext != null) && sendEmailAddressVerification) {
			sendEmailAddressVerification(user, emailAddress, serviceContext);
		}

		return user;
	}

	/**
	 * Verifies the email address of the ticket.
	 *
	 * @param ticketKey the ticket key
	 */
	@Override
	public void verifyEmailAddress(String ticketKey) throws PortalException {
		Ticket ticket = ticketLocalService.getTicket(ticketKey);

		if (ticket.isExpired() ||
			(ticket.getType() != TicketConstants.TYPE_EMAIL_ADDRESS)) {

			throw new NoSuchTicketException("{ticketKey=" + ticketKey + "}");
		}

		User user = userPersistence.findByPrimaryKey(ticket.getClassPK());

		String emailAddress = ticket.getExtraInfo();

		emailAddress = StringUtil.toLowerCase(StringUtil.trim(emailAddress));

		if (!emailAddress.equals(user.getEmailAddress())) {
			User userWithSameEmailAddress = userPersistence.fetchByC_EA(
				user.getCompanyId(), emailAddress);

			if (userWithSameEmailAddress != null) {
				throw new UserEmailAddressException.MustNotBeDuplicate(
					user.getCompanyId(), user.getUserId(), emailAddress);
			}

			setEmailAddress(
				user, StringPool.BLANK, user.getFirstName(),
				user.getMiddleName(), user.getLastName(), emailAddress);

			Contact contact = user.getContact();

			contact.setEmailAddress(user.getEmailAddress());

			contactPersistence.update(contact);
		}

		user.setEmailAddressVerified(true);

		user = userPersistence.update(user);

		ticketLocalService.deleteTicket(ticket);
	}

	protected void addDefaultRolesAndTeams(long groupId, long[] userIds)
		throws PortalException {

		List<Role> defaultSiteRoles = new ArrayList<>();

		Group group = groupLocalService.getGroup(groupId);

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		long[] defaultSiteRoleIds = StringUtil.split(
			typeSettingsProperties.getProperty("defaultSiteRoleIds"), 0L);

		for (long defaultSiteRoleId : defaultSiteRoleIds) {
			Role defaultSiteRole = rolePersistence.fetchByPrimaryKey(
				defaultSiteRoleId);

			if (defaultSiteRole == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to find role " + defaultSiteRoleId);
				}

				continue;
			}

			defaultSiteRoles.add(defaultSiteRole);
		}

		List<Team> defaultTeams = new ArrayList<>();

		long[] defaultTeamIds = StringUtil.split(
			typeSettingsProperties.getProperty("defaultTeamIds"), 0L);

		for (long defaultTeamId : defaultTeamIds) {
			Team defaultTeam = teamPersistence.findByPrimaryKey(defaultTeamId);

			if (defaultTeam == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to find team " + defaultTeamId);
				}

				continue;
			}

			defaultTeams.add(defaultTeam);
		}

		for (long userId : userIds) {
			Set<Long> userRoleIdsSet = new HashSet<>();

			for (Role role : defaultSiteRoles) {
				userRoleIdsSet.add(role.getRoleId());
			}

			long[] userRoleIds = ArrayUtil.toArray(
				userRoleIdsSet.toArray(new Long[0]));

			userGroupRoleLocalService.addUserGroupRoles(
				userId, groupId, userRoleIds);

			Set<Long> userTeamIdsSet = new HashSet<>();

			for (Team team : defaultTeams) {
				userTeamIdsSet.add(team.getTeamId());
			}

			long[] userTeamIds = ArrayUtil.toArray(
				userTeamIdsSet.toArray(new Long[0]));

			userPersistence.addTeams(userId, userTeamIds);
		}
	}

	/**
	 * Attempts to authenticate the user by their login and password, while
	 * using the AuthPipeline.
	 *
	 * <p>
	 * Authentication type specifies what <code>login</code> contains.The valid
	 * values are:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * <code>CompanyConstants.AUTH_TYPE_EA</code> - <code>login</code> is the
	 * user's email address
	 * </li>
	 * <li>
	 * <code>CompanyConstants.AUTH_TYPE_SN</code> - <code>login</code> is the
	 * user's screen name
	 * </li>
	 * <li>
	 * <code>CompanyConstants.AUTH_TYPE_ID</code> - <code>login</code> is the
	 * user's primary key
	 * </li>
	 * </ul>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  login either the user's email address, screen name, or primary
	 *         key depending on the value of <code>authType</code>
	 * @param  password the user's password
	 * @param  authType the type of authentication to perform
	 * @param  headerMap the header map from the authentication request
	 * @param  parameterMap the parameter map from the authentication request
	 * @param  resultsMap the map of authentication results (may be nil). After
	 *         a successful authentication the user's primary key will be placed
	 *         under the key <code>userId</code>.
	 * @return the authentication status. This can be {@link
	 *         Authenticator#FAILURE} indicating that the user's credentials are
	 *         invalid, {@link Authenticator#SUCCESS} indicating a successful
	 *         login, or {@link Authenticator#DNE} indicating that a user with
	 *         that login does not exist.
	 * @see    AuthPipeline
	 */
	protected int authenticate(
			long companyId, String login, String password, String authType,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap,
			Map<String, Object> resultsMap)
		throws PortalException {

		if (PropsValues.AUTH_LOGIN_DISABLED) {
			return Authenticator.FAILURE;
		}

		login = StringUtil.toLowerCase(StringUtil.trim(login));

		long userId = GetterUtil.getLong(login);

		// User input validation

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			if (Validator.isNull(login)) {
				throw new UserEmailAddressException.MustNotBeNull();
			}
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			if (Validator.isNull(login)) {
				throw new UserScreenNameException.MustNotBeNull();
			}
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			if (Validator.isNull(login)) {
				throw new UserIdException.MustNotBeNull();
			}
		}

		if (Validator.isNull(password)) {
			throw new UserPasswordException.MustNotBeNull(userId);
		}

		int authResult = Authenticator.FAILURE;

		// Pre-authentication pipeline

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			authResult = AuthPipeline.authenticateByEmailAddress(
				PropsKeys.AUTH_PIPELINE_PRE, companyId, login, password,
				headerMap, parameterMap);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			authResult = AuthPipeline.authenticateByScreenName(
				PropsKeys.AUTH_PIPELINE_PRE, companyId, login, password,
				headerMap, parameterMap);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			authResult = AuthPipeline.authenticateByUserId(
				PropsKeys.AUTH_PIPELINE_PRE, companyId, userId, password,
				headerMap, parameterMap);
		}

		// Get user

		User user = null;

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			user = userPersistence.fetchByC_EA(companyId, login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			user = userPersistence.fetchByC_SN(companyId, login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			user = userPersistence.fetchByPrimaryKey(GetterUtil.getLong(login));
		}

		if (user == null) {
			return Authenticator.DNE;
		}

		if (!isUserAllowedToAuthenticate(user)) {
			return Authenticator.FAILURE;
		}

		user = _checkPasswordPolicy(user);

		if (!user.isPasswordEncrypted()) {
			user.setPassword(PasswordEncryptorUtil.encrypt(user.getPassword()));
			user.setPasswordEncrypted(true);

			user = userPersistence.update(user);
		}

		// Authenticate against the User_ table

		boolean skipLiferayCheck = false;

		if (authResult == Authenticator.SKIP_LIFERAY_CHECK) {
			authResult = Authenticator.SUCCESS;

			skipLiferayCheck = true;
		}
		else if ((authResult == Authenticator.SUCCESS) &&
				 PropsValues.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK) {

			boolean authenticated = PwdAuthenticator.authenticate(
				login, password, user.getPassword());

			if (authenticated) {
				authResult = Authenticator.SUCCESS;
			}
			else {
				authResult = Authenticator.FAILURE;
			}
		}

		// Post-authentication pipeline

		if (authResult == Authenticator.SUCCESS) {
			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				authResult = AuthPipeline.authenticateByEmailAddress(
					PropsKeys.AUTH_PIPELINE_POST, companyId, login, password,
					headerMap, parameterMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				authResult = AuthPipeline.authenticateByScreenName(
					PropsKeys.AUTH_PIPELINE_POST, companyId, login, password,
					headerMap, parameterMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				authResult = AuthPipeline.authenticateByUserId(
					PropsKeys.AUTH_PIPELINE_POST, companyId, userId, password,
					headerMap, parameterMap);
			}
		}

		if (authResult == Authenticator.SUCCESS) {

			// Update digest

			user = userPersistence.fetchByPrimaryKey(user.getUserId());

			String digest = user.getDigest();

			if (skipLiferayCheck ||
				!PropsValues.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK ||
				Validator.isNull(digest)) {

				String newDigest = user.getDigest(password);

				if (!newDigest.equals(digest)) {
					user.setDigest(newDigest);

					user = userPersistence.update(user);
				}
			}
		}

		// Execute code triggered by authentication failure

		if (authResult == Authenticator.FAILURE) {
			authResult = handleAuthenticationFailure(
				login, authType, user, headerMap, parameterMap);

			user = userPersistence.fetchByPrimaryKey(user.getUserId());
		}
		else {
			user = resetFailedLoginAttempts(user);
		}

		if (resultsMap != null) {
			resultsMap.put("user", user);
			resultsMap.put("userId", user.getUserId());
		}

		return authResult;
	}

	protected SearchContext buildSearchContext(
		long companyId, String firstName, String middleName, String lastName,
		String fullName, String screenName, String emailAddress, String street,
		String city, String zip, String region, String country, int status,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort[] sorts) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(andSearch);

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				"city", city
			).put(
				"country", country
			).put(
				"emailAddress", emailAddress
			).put(
				"firstName", firstName
			).put(
				"fullName", fullName
			).put(
				"lastName", lastName
			).put(
				"middleName", middleName
			).put(
				"params", params
			).put(
				"region", region
			).put(
				"screenName", screenName
			).put(
				"status", status
			).put(
				"street", street
			).put(
				"zip", zip
			).build();

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (params != null) {
			String keywords = (String)params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		if (sorts != null) {
			searchContext.setSorts(sorts);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected User doCheckLockout(User user, PasswordPolicy passwordPolicy)
		throws PortalException {

		if (!passwordPolicy.isLockout()) {
			return user;
		}

		// Reset failure count

		Date now = new Date();
		int failedLoginAttempts = user.getFailedLoginAttempts();

		if (failedLoginAttempts > 0) {
			Date lastFailedLoginDate = user.getLastFailedLoginDate();

			long failedLoginTime = lastFailedLoginDate.getTime();

			long elapsedTime = now.getTime() - failedLoginTime;

			long requiredElapsedTime =
				passwordPolicy.getResetFailureCount() * 1000;

			if ((requiredElapsedTime != 0) &&
				(elapsedTime > requiredElapsedTime)) {

				user.setFailedLoginAttempts(0);

				user = userPersistence.update(user);
			}
		}

		// Reset lockout

		if (user.isLockout()) {
			Date lockoutDate = user.getLockoutDate();

			long lockoutTime = lockoutDate.getTime();

			long elapsedTime = now.getTime() - lockoutTime;

			long requiredElapsedTime =
				passwordPolicy.getLockoutDuration() * 1000;

			if ((requiredElapsedTime != 0) &&
				(elapsedTime > requiredElapsedTime)) {

				user.setLockout(false);
				user.setLockoutDate(null);

				user = userPersistence.update(user);
			}
		}

		if (user.isLockout()) {
			throw new UserLockoutException.PasswordPolicyLockout(
				user, passwordPolicy);
		}

		return user;
	}

	protected User doCheckPasswordExpired(
			User user, PasswordPolicy passwordPolicy)
		throws PortalException {

		// Check if password has expired

		if (isPasswordExpired(user)) {
			int graceLoginCount = user.getGraceLoginCount();

			if (graceLoginCount < passwordPolicy.getGraceLimit()) {
				user.setGraceLoginCount(++graceLoginCount);

				user = userPersistence.update(user);
			}
			else {
				user.setDigest(StringPool.BLANK);

				userPersistence.update(user);

				throw new PasswordExpiredException();
			}
		}

		// Check if user should be forced to change password on first login

		if (passwordPolicy.isChangeable() &&
			passwordPolicy.isChangeRequired() &&
			(user.getLastLoginDate() == null)) {

			user.setPasswordReset(true);

			user = userPersistence.update(user);
		}

		return user;
	}

	protected Date getBirthday(
			int birthdayMonth, int birthdayDay, int birthdayYear)
		throws PortalException {

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			ContactBirthdayException.class);

		if (birthday.after(new Date())) {
			throw new ContactBirthdayException();
		}

		return birthday;
	}

	protected String getLogin(String login) {
		return StringUtil.lowerCase(StringUtil.trim(login));
	}

	protected Sort[] getSorts(OrderByComparator<User> obc) {
		if (obc == null) {
			return new Sort[0];
		}

		String[] orderByClauses = StringUtil.split(obc.getOrderBy());

		String[] orderByFields = obc.getOrderByFields();

		Sort[] sorts = new Sort[orderByFields.length];

		for (int i = 0; i < orderByFields.length; i++) {
			boolean reverse = orderByClauses[i].contains("DESC");

			sorts[i] = new Sort(orderByFields[i], reverse);
		}

		return sorts;
	}

	protected int handleAuthenticationFailure(
		String login, String authType, User user,
		Map<String, String[]> headerMap, Map<String, String[]> parameterMap) {

		if (user == null) {
			return Authenticator.DNE;
		}

		try {
			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				AuthPipeline.onFailureByEmailAddress(
					PropsKeys.AUTH_FAILURE, user.getCompanyId(),
					user.getEmailAddress(), headerMap, parameterMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				AuthPipeline.onFailureByScreenName(
					PropsKeys.AUTH_FAILURE, user.getCompanyId(),
					user.getScreenName(), headerMap, parameterMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				AuthPipeline.onFailureByUserId(
					PropsKeys.AUTH_FAILURE, user.getCompanyId(),
					user.getUserId(), headerMap, parameterMap);
			}

			user = userPersistence.fetchByPrimaryKey(user.getUserId());

			if (user == null) {
				return Authenticator.DNE;
			}

			// Let LDAP handle max failure event

			if (!LDAPSettingsUtil.isPasswordPolicyEnabled(
					user.getCompanyId())) {

				PasswordPolicy passwordPolicy = user.getPasswordPolicy();

				user = userPersistence.fetchByPrimaryKey(user.getUserId());

				int failedLoginAttempts = user.getFailedLoginAttempts();

				int maxFailures = passwordPolicy.getMaxFailure();

				if ((failedLoginAttempts >= maxFailures) &&
					(maxFailures != 0)) {

					if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
						AuthPipeline.onMaxFailuresByEmailAddress(
							PropsKeys.AUTH_MAX_FAILURES, user.getCompanyId(),
							user.getEmailAddress(), headerMap, parameterMap);
					}
					else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
						AuthPipeline.onMaxFailuresByScreenName(
							PropsKeys.AUTH_MAX_FAILURES, user.getCompanyId(),
							user.getScreenName(), headerMap, parameterMap);
					}
					else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
						AuthPipeline.onMaxFailuresByUserId(
							PropsKeys.AUTH_MAX_FAILURES, user.getCompanyId(),
							user.getUserId(), headerMap, parameterMap);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return Authenticator.FAILURE;
	}

	protected boolean isUseCustomSQL(LinkedHashMap<String, Object> params) {
		if (MapUtil.isEmpty(params)) {
			return false;
		}

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();

			if (key.equals("inherit")) {
				if (Boolean.TRUE.equals(entry.getValue())) {
					return true;
				}
			}
			else if (key.equals("noOrganizations")) {
				if (!Boolean.TRUE.equals(entry.getValue())) {
					return true;
				}

				Object usersOrgsCount = params.get("usersOrgsCount");

				if ((usersOrgsCount == null) ||
					(GetterUtil.getLong(usersOrgsCount) != 0)) {

					return true;
				}
			}
			else if (!key.equals("usersGroups") && !key.equals("usersOrgs") &&
					 !key.equals("usersOrgsCount") &&
					 !key.equals("usersRoles") && !key.equals("usersTeams") &&
					 !key.equals("usersUserGroups")) {

				return true;
			}
		}

		return false;
	}

	protected boolean isUserAllowedToAuthenticate(User user)
		throws PortalException {

		if (user.isDefaultUser()) {
			if (_log.isInfoEnabled()) {
				_log.info("Authentication is disabled for the default user");
			}

			return false;
		}
		else if (!user.isActive()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Authentication is disabled for inactive user " +
						user.getUserId());
			}

			return false;
		}

		return true;
	}

	protected void notifyUser(User user, ServiceContext serviceContext) {
		if (!PrefsPropsUtil.getBoolean(
				user.getCompanyId(),
				PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED)) {

			return;
		}

		boolean autoPassword = GetterUtil.getBoolean(
			serviceContext.getAttribute("autoPassword"));
		Company company = null;
		String fromAddress = PrefsPropsUtil.getString(
			user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		String fromName = PrefsPropsUtil.getString(
			user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String passwordResetURL = StringPool.BLANK;
		String portalURL = null;

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(user.getCompanyId(), true);

		Map<Locale, String> localizedSubjectMap =
			LocalizationUtil.getLocalizationMap(
				companyPortletPreferences, "adminEmailUserAddedSubject",
				PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT);

		try {
			company = companyLocalService.getCompany(user.getCompanyId());

			portalURL = company.getPortalURL(0);
		}
		catch (PortalException pe) {
			ReflectionUtil.throwException(pe);
		}

		final Map<Locale, String> localizedBodyMap;

		if (!autoPassword) {
			localizedBodyMap = LocalizationUtil.getLocalizationMap(
				companyPortletPreferences, "adminEmailUserAddedNoPasswordBody",
				PropsKeys.ADMIN_EMAIL_USER_ADDED_NO_PASSWORD_BODY);
		}
		else {
			Ticket ticket = ticketLocalService.addDistinctTicket(
				user.getCompanyId(), User.class.getName(), user.getUserId(),
				TicketConstants.TYPE_PASSWORD, null, null, serviceContext);

			String updatePasswordURL = "/portal/update_password?";

			long plid = serviceContext.getPlid();

			if (plid > 0) {
				Layout layout = layoutLocalService.fetchLayout(plid);

				if (layout != null) {
					Group group = layout.getGroup();

					if (!layout.isPrivateLayout() && !group.isUser()) {
						updatePasswordURL +=
							"p_l_id=" + serviceContext.getPlid() + "&";
					}
				}
			}

			passwordResetURL = StringBundler.concat(
				serviceContext.getPortalURL(), serviceContext.getPathMain(),
				updatePasswordURL, "ticketKey=", ticket.getKey());

			localizedBodyMap = LocalizationUtil.getLocalizationMap(
				companyPortletPreferences,
				"adminEmailUserAddedResetPasswordBody",
				PropsKeys.ADMIN_EMAIL_USER_ADDED_RESET_PASSWORD_BODY);
		}

		String subject = _getLocalizedValue(
			localizedSubjectMap, user.getLocale(), LocaleUtil.getDefault());

		String body = _getLocalizedValue(
			localizedBodyMap, user.getLocale(), LocaleUtil.getDefault());

		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		mailTemplateContextBuilder.put("[$FROM_ADDRESS$]", fromAddress);
		mailTemplateContextBuilder.put(
			"[$FROM_NAME$]", HtmlUtil.escape(fromName));
		mailTemplateContextBuilder.put("[$PORTAL_URL$]", portalURL);
		mailTemplateContextBuilder.put(
			"[$TO_ADDRESS$]", user.getEmailAddress());
		mailTemplateContextBuilder.put(
			"[$TO_NAME$]", HtmlUtil.escape(user.getFullName()));
		mailTemplateContextBuilder.put(
			"[$PASSWORD_SETUP_URL$]", passwordResetURL);
		mailTemplateContextBuilder.put(
			"[$USER_ID$]", String.valueOf(user.getUserId()));
		mailTemplateContextBuilder.put(
			"[$USER_SCREENNAME$]", HtmlUtil.escape(user.getScreenName()));

		MailTemplateContext mailTemplateContext =
			mailTemplateContextBuilder.build();

		try {
			_sendNotificationEmail(
				fromAddress, fromName, user.getEmailAddress(), user, subject,
				body, mailTemplateContext);
		}
		catch (PortalException pe) {
			ReflectionUtil.throwException(pe);
		}
	}

	protected void reindex(final List<User> users) throws SearchException {
		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		try {
			indexer.reindex(users);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	protected void reindex(long userId) throws SearchException {
		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		User user = userLocalService.fetchUser(userId);

		indexer.reindex(user);
	}

	protected void reindex(long[] userIds) throws SearchException {
		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		List<User> users = new ArrayList<>(userIds.length);

		for (Long userId : userIds) {
			User user = userLocalService.fetchUser(userId);

			users.add(user);
		}

		indexer.reindex(users);
	}

	protected void reindex(final User user) throws SearchException {
		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		indexer.reindex(user);
	}

	protected User resetFailedLoginAttempts(User user) {
		return resetFailedLoginAttempts(user, false);
	}

	protected User resetFailedLoginAttempts(User user, boolean forceUpdate) {
		if (forceUpdate || (user.getFailedLoginAttempts() > 0)) {
			user.setFailedLoginAttempts(0);

			user = userPersistence.update(user);
		}

		return user;
	}

	protected BaseModelSearchResult<User> searchUsers(
			SearchContext searchContext)
		throws PortalException {

		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<User> users = UsersAdminUtil.getUsers(hits);

			if (users != null) {
				return new BaseModelSearchResult<>(users, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void sendPasswordNotification(
		User user, long companyId, String newPassword, String passwordResetURL,
		String fromName, String fromAddress, String subject, String body,
		ServiceContext serviceContext) {

		if (Validator.isNull(fromName)) {
			fromName = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
		}

		if (Validator.isNull(fromAddress)) {
			fromAddress = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		}

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(companyId, true);

		final String bodyProperty;
		final String prefix;
		final String subjectProperty;

		if (Validator.isNotNull(passwordResetURL)) {
			bodyProperty = PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_BODY;
			prefix = "adminEmailPasswordReset";
			subjectProperty = PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT;
		}
		else {
			bodyProperty = PropsKeys.ADMIN_EMAIL_PASSWORD_CHANGED_BODY;
			prefix = "adminEmailPasswordChanged";
			subjectProperty = PropsKeys.ADMIN_EMAIL_PASSWORD_CHANGED_SUBJECT;
		}

		String localizedBody = body;

		if (Validator.isNull(body)) {
			Map<Locale, String> localizedBodyMap =
				LocalizationUtil.getLocalizationMap(
					companyPortletPreferences, prefix + "Body", bodyProperty);

			localizedBody = _getLocalizedValue(
				localizedBodyMap, user.getLocale(), LocaleUtil.getDefault());
		}

		String localizedSubject = subject;

		if (Validator.isNull(subject)) {
			Map<Locale, String> localizedSubjectMap =
				LocalizationUtil.getLocalizationMap(
					companyPortletPreferences, prefix + "Subject",
					subjectProperty);

			localizedSubject = _getLocalizedValue(
				localizedSubjectMap, user.getLocale(), LocaleUtil.getDefault());
		}

		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		mailTemplateContextBuilder.put("[$FROM_ADDRESS$]", fromAddress);
		mailTemplateContextBuilder.put(
			"[$FROM_NAME$]", HtmlUtil.escape(fromName));
		mailTemplateContextBuilder.put(
			"[$PASSWORD_RESET_URL$]", passwordResetURL);
		mailTemplateContextBuilder.put(
			"[$PORTAL_URL$]", serviceContext.getPortalURL());
		mailTemplateContextBuilder.put(
			"[$REMOTE_ADDRESS$]", serviceContext.getRemoteAddr());
		mailTemplateContextBuilder.put(
			"[$REMOTE_HOST$]", HtmlUtil.escape(serviceContext.getRemoteHost()));
		mailTemplateContextBuilder.put("[$TO_ADDRESS$]", toAddress);
		mailTemplateContextBuilder.put("[$TO_NAME$]", HtmlUtil.escape(toName));
		mailTemplateContextBuilder.put(
			"[$USER_ID$]", String.valueOf(user.getUserId()));
		mailTemplateContextBuilder.put(
			"[$USER_SCREENNAME$]", HtmlUtil.escape(user.getScreenName()));

		MailTemplateContext mailTemplateContext =
			mailTemplateContextBuilder.build();

		try {
			_sendNotificationEmail(
				fromAddress, fromName, toAddress, user, localizedSubject,
				localizedBody, mailTemplateContext);
		}
		catch (PortalException pe) {
			ReflectionUtil.throwException(pe);
		}
	}

	protected void setEmailAddress(
			User user, String password, String firstName, String middleName,
			String lastName, String emailAddress)
		throws PortalException {

		if (StringUtil.equalsIgnoreCase(emailAddress, user.getEmailAddress())) {
			return;
		}

		long userId = user.getUserId();

		if (!user.hasCompanyMx() && user.hasCompanyMx(emailAddress) &&
			Validator.isNotNull(password)) {

			// test@test.com -> test@liferay.com

			mailService.addUser(
				user.getCompanyId(), userId, password, firstName, middleName,
				lastName, emailAddress);
		}
		else if (user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {

			// test@liferay.com -> bob@liferay.com

			mailService.updateEmailAddress(
				user.getCompanyId(), userId, emailAddress);
		}
		else if (user.hasCompanyMx() && !user.hasCompanyMx(emailAddress)) {

			// test@liferay.com -> test@test.com

			mailService.deleteEmailAddress(user.getCompanyId(), userId);
		}

		user.setDigest(StringPool.BLANK);
		user.setEmailAddress(emailAddress);
	}

	protected void trackPassword(User user) throws PortalException {
		String oldEncPwd = user.getPassword();

		if (!user.isPasswordEncrypted()) {
			oldEncPwd = PasswordEncryptorUtil.encrypt(user.getPassword());
		}

		passwordTrackerLocalService.trackPassword(user.getUserId(), oldEncPwd);
	}

	protected void unsetUserGroups(long userId, long[] groupIds)
		throws PortalException {

		List<UserGroupRole> userGroupRoles =
			userGroupRolePersistence.findByUserId(userId);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			if (ArrayUtil.contains(groupIds, userGroupRole.getGroupId())) {
				Role role = rolePersistence.findByPrimaryKey(
					userGroupRole.getRoleId());

				if (role.getType() == RoleConstants.TYPE_SITE) {
					userGroupRolePersistence.remove(userGroupRole);
				}
			}
		}

		List<Team> oldTeams = userPersistence.getTeams(userId);

		List<Team> removedFromTeams = new ArrayList<>();

		for (Team team : oldTeams) {
			if (ArrayUtil.contains(groupIds, team.getGroupId())) {
				removedFromTeams.add(team);
			}
		}

		if (!removedFromTeams.isEmpty()) {
			userPersistence.removeTeams(userId, removedFromTeams);
		}

		userPersistence.removeGroups(userId, groupIds);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Message message = new Message();

				message.put("groupIds", groupIds);
				message.put("userId", userId);

				MessageBusUtil.sendMessage(
					DestinationNames.SUBSCRIPTION_CLEAN_UP, message);

				return null;
			});
	}

	protected void unsetUserOrganizations(long userId, long[] organizationIds)
		throws PortalException {

		long[] groupIds = new long[organizationIds.length];

		for (int i = 0; i < organizationIds.length; i++) {
			Organization organization =
				organizationPersistence.findByPrimaryKey(organizationIds[i]);

			groupIds[i] = organization.getGroupId();
		}

		userGroupRoleLocalService.deleteUserGroupRoles(userId, groupIds);

		organizationLocalService.deleteUserOrganizations(
			userId, organizationIds);

		reindex(userId);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Message message = new Message();

				message.put("groupIds", groupIds);
				message.put("userId", userId);

				MessageBusUtil.sendMessage(
					DestinationNames.SUBSCRIPTION_CLEAN_UP, message);

				return null;
			});
	}

	protected void updateGroups(
			long userId, long[] newGroupIds, boolean indexingEnabled)
		throws PortalException {

		if (newGroupIds == null) {
			return;
		}

		List<Long> oldGroupIds = ListUtil.fromArray(
			getGroupPrimaryKeys(userId));

		for (long newGroupId : newGroupIds) {
			oldGroupIds.remove(newGroupId);
		}

		if (!oldGroupIds.isEmpty()) {
			unsetUserGroups(userId, ArrayUtil.toLongArray(oldGroupIds));
		}

		userPersistence.setGroups(userId, newGroupIds);

		for (long newGroupId : newGroupIds) {
			addDefaultRolesAndTeams(newGroupId, new long[] {userId});
		}

		if (indexingEnabled) {
			reindex(userId);
		}
	}

	protected void updateOrganizations(
			long userId, long[] newOrganizationIds, boolean indexingEnabled)
		throws PortalException {

		if (newOrganizationIds == null) {
			return;
		}

		List<Long> oldOrganizationIds = ListUtil.fromArray(
			getOrganizationPrimaryKeys(userId));

		for (long newOrganizationId : newOrganizationIds) {
			oldOrganizationIds.remove(newOrganizationId);
		}

		if (!oldOrganizationIds.isEmpty()) {
			unsetUserOrganizations(
				userId, ArrayUtil.toLongArray(oldOrganizationIds));
		}

		userPersistence.setOrganizations(userId, newOrganizationIds);

		if (indexingEnabled) {
			reindex(userId);
		}
	}

	protected void updateUserGroupRoles(
			User user, long[] groupIds, long[] organizationIds,
			List<UserGroupRole> userGroupRoles,
			List<UserGroupRole> previousUserGroupRoles)
		throws PortalException {

		if (userGroupRoles == null) {
			return;
		}

		userGroupRoles = new ArrayList<>(userGroupRoles);

		for (UserGroupRole userGroupRole : previousUserGroupRoles) {
			if (userGroupRoles.contains(userGroupRole)) {
				userGroupRoles.remove(userGroupRole);
			}
			else {
				userGroupRoleLocalService.deleteUserGroupRole(userGroupRole);
			}
		}

		if (ListUtil.isEmpty(userGroupRoles)) {
			return;
		}

		long[] validGroupIds = null;

		if (groupIds != null) {
			validGroupIds = ArrayUtil.clone(groupIds);
		}
		else {
			List<Group> userGroups = groupLocalService.getUserGroups(
				user.getUserId(), true);

			int size = userGroups.size();

			validGroupIds = new long[size];

			for (int i = 0; i < size; i++) {
				Group userGroup = userGroups.get(i);

				validGroupIds[i] = userGroup.getGroupId();
			}
		}

		if (organizationIds == null) {
			organizationIds = user.getOrganizationIds();
		}

		for (long organizationId : organizationIds) {
			Organization organization =
				organizationPersistence.findByPrimaryKey(organizationId);

			if (!ArrayUtil.contains(validGroupIds, organization.getGroupId())) {
				validGroupIds = ArrayUtil.append(
					validGroupIds, organization.getGroupId());
			}
		}

		Arrays.sort(validGroupIds);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			int count = Arrays.binarySearch(
				validGroupIds, userGroupRole.getGroupId());

			if (count >= 0) {
				userGroupRoleLocalService.addUserGroupRole(userGroupRole);
			}
		}
	}

	protected void validate(
			long companyId, long userId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, String openId, String firstName,
			String middleName, String lastName, long[] organizationIds,
			Locale locale)
		throws PortalException {

		validateCompanyMaxUsers(companyId);

		if (!autoScreenName) {
			validateScreenName(companyId, userId, screenName);
		}

		if (!autoPassword) {
			PasswordPolicy passwordPolicy =
				passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);

			PwdToolkitUtil.validate(
				companyId, 0, password1, password2, passwordPolicy);
		}

		validateEmailAddress(companyId, emailAddress);

		if (Validator.isNotNull(emailAddress)) {
			User user = userPersistence.fetchByC_EA(companyId, emailAddress);

			if ((user != null) && (user.getUserId() != userId)) {
				throw new UserEmailAddressException.MustNotBeDuplicate(
					companyId, emailAddress);
			}
		}

		validateOpenId(companyId, userId, openId);

		validateFullName(companyId, firstName, middleName, lastName, locale);

		if (organizationIds != null) {
			for (long organizationId : organizationIds) {
				Organization organization =
					organizationPersistence.fetchByPrimaryKey(organizationId);

				if (organization == null) {
					throw new NoSuchOrganizationException(
						"{organizationId=" + organizationId + "}");
				}
			}
		}
	}

	protected void validate(
			long userId, String screenName, String emailAddress, String openId,
			String firstName, String middleName, String lastName, String smsSn,
			Locale locale)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (!StringUtil.equalsIgnoreCase(user.getScreenName(), screenName)) {
			validateScreenName(user.getCompanyId(), userId, screenName);
		}

		validateEmailAddress(user.getCompanyId(), emailAddress);

		validateOpenId(user.getCompanyId(), userId, openId);

		if (!user.isDefaultUser()) {
			if (Validator.isNotNull(emailAddress) &&
				!StringUtil.equalsIgnoreCase(
					user.getEmailAddress(), emailAddress)) {

				User userWithSameEmailAddress = userPersistence.fetchByC_EA(
					user.getCompanyId(), emailAddress);

				if (userWithSameEmailAddress != null) {
					throw new UserEmailAddressException.MustNotBeDuplicate(
						user.getCompanyId(), userId, emailAddress);
				}
			}

			validateFullName(
				user.getCompanyId(), firstName, middleName, lastName, locale);
		}

		if (Validator.isNotNull(smsSn) && !Validator.isEmailAddress(smsSn)) {
			throw new UserSmsException.MustBeEmailAddress(smsSn);
		}
	}

	protected void validateCompanyMaxUsers(long companyId)
		throws PortalException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		if (company.isSystem() || (company.getMaxUsers() == 0)) {
			return;
		}

		int userCount = searchCount(
			companyId, null, WorkflowConstants.STATUS_APPROVED, null);

		if (userCount >= company.getMaxUsers()) {
			throw new CompanyMaxUsersException();
		}
	}

	protected void validateEmailAddress(long companyId, String emailAddress)
		throws PortalException {

		if (Validator.isNull(emailAddress) &&
			!PropsValues.USERS_EMAIL_ADDRESS_REQUIRED) {

			return;
		}

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();

		if (!emailAddressValidator.validate(companyId, emailAddress)) {
			throw new UserEmailAddressException.MustValidate(
				emailAddress, emailAddressValidator);
		}

		String pop3User = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_POP3_USER,
			PropsValues.MAIL_SESSION_MAIL_POP3_USER);

		if (StringUtil.equalsIgnoreCase(emailAddress, pop3User)) {
			throw new UserEmailAddressException.MustNotBePOP3User(emailAddress);
		}

		String[] reservedEmailAddresses = PrefsPropsUtil.getStringArray(
			companyId, PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES,
			StringPool.NEW_LINE, PropsValues.ADMIN_RESERVED_EMAIL_ADDRESSES);

		for (String reservedEmailAddress : reservedEmailAddresses) {
			if (StringUtil.equalsIgnoreCase(
					emailAddress, reservedEmailAddress)) {

				throw new UserEmailAddressException.MustNotBeReserved(
					emailAddress, reservedEmailAddresses);
			}
		}
	}

	protected void validateEmailAddress(
			User user, String emailAddress1, String emailAddress2)
		throws PortalException {

		if (!emailAddress1.equals(emailAddress2)) {
			throw new UserEmailAddressException.MustBeEqual(
				user, emailAddress1, emailAddress2);
		}

		validateEmailAddress(user.getCompanyId(), emailAddress1);
		validateEmailAddress(user.getCompanyId(), emailAddress2);

		if (!StringUtil.equalsIgnoreCase(
				emailAddress1, user.getEmailAddress())) {

			User userWithSameEmailAddress = userPersistence.fetchByC_EA(
				user.getCompanyId(), emailAddress1);

			if (userWithSameEmailAddress != null) {
				throw new UserEmailAddressException.MustNotBeDuplicate(
					user.getCompanyId(), user.getUserId(), emailAddress1);
			}
		}
	}

	protected void validateFullName(
			long companyId, String firstName, String middleName,
			String lastName, Locale locale)
		throws PortalException {

		FullNameDefinition fullNameDefinition =
			FullNameDefinitionFactory.getInstance(locale);

		if (Validator.isNull(firstName)) {
			throw new ContactNameException.MustHaveFirstName();
		}
		else if (Validator.isNull(middleName) &&
				 fullNameDefinition.isFieldRequired("middle-name")) {

			throw new ContactNameException.MustHaveMiddleName();
		}
		else if (Validator.isNull(lastName) &&
				 fullNameDefinition.isFieldRequired("last-name")) {

			throw new ContactNameException.MustHaveLastName();
		}

		FullNameValidator fullNameValidator =
			FullNameValidatorFactory.getInstance();

		if (!fullNameValidator.validate(
				companyId, firstName, middleName, lastName)) {

			throw new ContactNameException.MustHaveValidFullName(
				fullNameValidator);
		}
	}

	protected void validateGoogleUserId(
			long companyId, long userId, String googleUserId)
		throws PortalException {

		if (Validator.isNull(googleUserId)) {
			return;
		}

		User user = userPersistence.fetchByC_GUID(companyId, googleUserId);

		if ((user != null) && (user.getUserId() != userId)) {
			throw new DuplicateGoogleUserIdException(
				StringBundler.concat(
					"New user ", userId, " conflicts with existing user ",
					userId, " who is already associated with Google user ID ",
					googleUserId));
		}
	}

	protected void validateOpenId(long companyId, long userId, String openId)
		throws PortalException {

		if (Validator.isNull(openId)) {
			return;
		}

		User user = userPersistence.fetchByC_O(companyId, openId);

		if ((user != null) && (user.getUserId() != userId)) {
			throw new DuplicateOpenIdException("{userId=" + userId + "}");
		}
	}

	protected void validatePassword(
			long companyId, long userId, String password1, String password2)
		throws PortalException {

		if (Validator.isNull(password1) || Validator.isNull(password2)) {
			throw new UserPasswordException.MustNotBeNull(userId);
		}

		if (!password1.equals(password2)) {
			throw new UserPasswordException.MustMatch(userId);
		}

		PasswordPolicy passwordPolicy =
			passwordPolicyLocalService.getPasswordPolicyByUserId(userId);

		PwdToolkitUtil.validate(
			companyId, userId, password1, password2, passwordPolicy);
	}

	protected void validateReminderQuery(String question, String answer)
		throws PortalException {

		if (!PropsValues.USERS_REMINDER_QUERIES_ENABLED) {
			return;
		}

		if (Validator.isNull(question)) {
			throw new UserReminderQueryException("Question is null");
		}

		if (Validator.isNull(answer)) {
			throw new UserReminderQueryException("Answer is null");
		}
	}

	protected void validateScreenName(
			long companyId, long userId, String screenName)
		throws PortalException {

		if (Validator.isNull(screenName)) {
			throw new UserScreenNameException.MustNotBeNull(userId);
		}

		ScreenNameValidator screenNameValidator =
			ScreenNameValidatorFactory.getInstance();

		if (!screenNameValidator.validate(companyId, screenName)) {
			throw new UserScreenNameException.MustValidate(
				userId, screenName, screenNameValidator);
		}

		if (Validator.isNumber(screenName) &&
			!PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC) {

			throw new UserScreenNameException.MustNotBeNumeric(
				userId, screenName);
		}

		String[] anonymousNames = BaseServiceImpl.ANONYMOUS_NAMES;

		for (String anonymousName : anonymousNames) {
			if (StringUtil.equalsIgnoreCase(screenName, anonymousName)) {
				throw new UserScreenNameException.MustNotBeReservedForAnonymous(
					userId, screenName, anonymousNames);
			}
		}

		User user = userPersistence.fetchByC_SN(companyId, screenName);

		if ((user != null) && (user.getUserId() != userId)) {
			throw new UserScreenNameException.MustNotBeDuplicate(
				user.getUserId(), screenName);
		}

		String friendlyURL = FriendlyURLNormalizerUtil.normalize(
			StringPool.SLASH + screenName);

		Group group = groupPersistence.fetchByC_F(companyId, friendlyURL);

		if ((group != null) && (group.getClassPK() != userId)) {
			GroupFriendlyURLException gfurle = new GroupFriendlyURLException(
				GroupFriendlyURLException.DUPLICATE);

			gfurle.setDuplicateClassPK(group.getGroupId());
			gfurle.setDuplicateClassName(Group.class.getName());

			throw gfurle;
		}

		int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

		if (exceptionType != -1) {
			throw new UserScreenNameException.MustProduceValidFriendlyURL(
				userId, screenName, exceptionType);
		}

		String[] reservedScreenNames = PrefsPropsUtil.getStringArray(
			companyId, PropsKeys.ADMIN_RESERVED_SCREEN_NAMES,
			StringPool.NEW_LINE, PropsValues.ADMIN_RESERVED_SCREEN_NAMES);

		for (String reservedScreenName : reservedScreenNames) {
			if (StringUtil.equalsIgnoreCase(screenName, reservedScreenName)) {
				throw new UserScreenNameException.MustNotBeReserved(
					userId, screenName, reservedScreenNames);
			}
		}
	}

	@BeanReference(type = MailService.class)
	protected MailService mailService;

	private User _checkPasswordPolicy(User user) throws PortalException {

		// Check password policy to see if the is account locked out or if the
		// password is expired

		if (!LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
			PasswordPolicy passwordPolicy = user.getPasswordPolicy();

			user = doCheckLockout(user, passwordPolicy);

			if (!PasswordModificationThreadLocal.isPasswordModified()) {
				user = doCheckPasswordExpired(user, passwordPolicy);
			}
		}

		return user;
	}

	private void _checkPasswordReset(
		PasswordPolicy passwordPolicy, long[] userIds) {

		// Check password policy to see if changing the password is allowed. If
		// it is not allowed, set the user's passwordReset field to false to
		// prevent issues while logging in. See LPS-76504.

		if ((passwordPolicy == null) || passwordPolicy.isChangeable()) {
			return;
		}

		for (long userId : userIds) {
			try {
				updatePasswordReset(userId, false);
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}
			}
		}
	}

	private String _getLocalizedValue(
		Map<Locale, String> localizedValueMap, Locale locale,
		Locale fallbackLocale) {

		if (localizedValueMap == null) {
			return null;
		}

		String localizedValue = localizedValueMap.get(locale);

		if (Validator.isNotNull(localizedValue)) {
			return localizedValue;
		}

		return localizedValueMap.get(fallbackLocale);
	}

	private void _sendNotificationEmail(
			String fromAddress, String fromName, String toAddress, User toUser,
			String subject, String body,
			MailTemplateContext mailTemplateContext)
		throws PortalException {

		try {
			MailTemplate subjectTemplate =
				MailTemplateFactoryUtil.createMailTemplate(subject, false);

			MailTemplate bodyTemplate =
				MailTemplateFactoryUtil.createMailTemplate(body, true);

			MailMessage mailMessage = new MailMessage(
				new InternetAddress(fromAddress, fromName),
				new InternetAddress(toAddress, toUser.getFullName()),
				subjectTemplate.renderAsString(
					toUser.getLocale(), mailTemplateContext),
				bodyTemplate.renderAsString(
					toUser.getLocale(), mailTemplateContext),
				true);

			Company company = companyLocalService.getCompany(
				toUser.getCompanyId());

			mailMessage.setMessageId(
				PortalUtil.getMailId(
					company.getMx(), "user", toUser.getUserId()));

			mailService.sendEmail(mailMessage);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserLocalServiceImpl.class);

	private static volatile UserFileUploadsSettings _userFileUploadsSettings =
		ServiceProxyFactory.newServiceTrackedInstance(
			UserFileUploadsSettings.class, UserLocalServiceImpl.class,
			"_userFileUploadsSettings", false);

	private final Map<Long, User> _defaultUsers = new ConcurrentHashMap<>();

	private final ServiceDependencyListener _serviceDependencyListener =
		new ServiceDependencyListener() {

			@Override
			public void dependenciesFulfilled() {
				Registry registry = RegistryUtil.getRegistry();

				registry.callService(
					EntityCache.class,
					entityCache -> {
						PortalCache<Serializable, Serializable> portalCache =
							entityCache.getPortalCache(UserImpl.class);

						PortalCacheMapSynchronizeUtil.synchronize(
							portalCache, _defaultUsers, _synchronizer);

						return null;
					});
			}

			@Override
			public void destroy() {
			}

		};

	private final PortalCacheMapSynchronizeUtil.Synchronizer
		<Serializable, Serializable> _synchronizer =
			new PortalCacheMapSynchronizeUtil.Synchronizer
				<Serializable, Serializable>() {

				@Override
				public void onSynchronize(
					Map<? extends Serializable, ? extends Serializable> map,
					Serializable key, Serializable value, int timeToLive) {

					if (!(value instanceof UserCacheModel)) {
						return;
					}

					UserCacheModel userCacheModel = (UserCacheModel)value;

					if (userCacheModel.defaultUser) {
						_defaultUsers.remove(userCacheModel.companyId);
					}
				}

			};

}