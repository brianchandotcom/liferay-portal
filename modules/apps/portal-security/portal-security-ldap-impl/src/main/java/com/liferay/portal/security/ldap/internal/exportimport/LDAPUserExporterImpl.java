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

package com.liferay.portal.security.ldap.internal.exportimport;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.security.exportimport.UserOperation;
import com.liferay.portal.security.ldap.GroupConverterKeys;
import com.liferay.portal.security.ldap.SafeLdapContext;
import com.liferay.portal.security.ldap.SafeLdapName;
import com.liferay.portal.security.ldap.SafeLdapNameFactory;
import com.liferay.portal.security.ldap.SafePortalLDAP;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.exportimport.Modifications;
import com.liferay.portal.security.ldap.exportimport.PortalToLDAPConverter;
import com.liferay.portal.security.ldap.internal.PortalLDAPContext;
import com.liferay.portal.security.ldap.util.LDAPUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SchemaViolationException;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Wesley Gong
 * @author Vilmos Papp
 */
@Component(immediate = true, service = UserExporter.class)
public class LDAPUserExporterImpl implements UserExporter {

	@Override
	public void exportUser(
			Contact contact, Map<String, Serializable> contactExpandoAttributes)
		throws Exception {

		long companyId = contact.getCompanyId();

		StopWatch stopWatch = new StopWatch();

		if (_log.isDebugEnabled()) {
			stopWatch.start();

			_log.debug("Exporting contact " + contact);
		}

		if (!_ldapSettings.isExportEnabled(companyId)) {
			return;
		}

		User user = _userLocalService.getUserByContactId(
			contact.getContactId());

		if (user.isDefaultUser() ||
			((user.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
			 (user.getStatus() != WorkflowConstants.STATUS_INACTIVE)) ||
			_isAnonymousUser(user)) {

			return;
		}

		long ldapServerId = _safePortalLDAP.getLdapServerId(
			companyId, user.getScreenName(), user.getEmailAddress());

		SafeLdapContext safeLdapContext = _safePortalLDAP.getSafeLdapContext(
			ldapServerId, companyId);

		try {
			if (safeLdapContext == null) {
				return;
			}

			Properties contactMappings = _ldapSettings.getContactMappings(
				ldapServerId, companyId);
			Properties contactExpandoMappings =
				_ldapSettings.getContactExpandoMappings(
					ldapServerId, companyId);

			Binding userBinding = _safePortalLDAP.getUser(
				ldapServerId, contact.getCompanyId(), user.getScreenName(),
				user.getEmailAddress());

			if (userBinding == null) {
				Properties userMappings = _ldapSettings.getUserMappings(
					ldapServerId, companyId);

				userBinding = addUser(
					ldapServerId, safeLdapContext, user, userMappings);
			}

			Modifications modifications =
				_portalToLDAPConverter.getLDAPContactModifications(
					contact, contactExpandoAttributes, contactMappings,
					contactExpandoMappings);

			if (modifications == null) {
				return;
			}

			ModificationItem[] modificationItems = modifications.getItems();

			safeLdapContext.modifyAttributes(
				SafeLdapNameFactory.from(userBinding), modificationItems);
		}
		finally {
			if (safeLdapContext != null) {
				safeLdapContext.close();
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Finished exporting contact ", contact, " in ",
						stopWatch.getTime(), "ms"));
			}
		}
	}

	@Override
	public void exportUser(
			long userId, long userGroupId, UserOperation userOperation)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		StopWatch stopWatch = new StopWatch();

		if (_log.isDebugEnabled()) {
			stopWatch.start();

			_log.debug(
				StringBundler.concat(
					"Exporting user ", user, " in user group ", userGroupId));
		}

		if (!_ldapSettings.isExportEnabled(companyId) ||
			!_ldapSettings.isExportGroupEnabled(companyId)) {

			return;
		}

		long ldapServerId = _safePortalLDAP.getLdapServerId(
			companyId, user.getScreenName(), user.getEmailAddress());

		SafeLdapContext safeLdapContext = _safePortalLDAP.getSafeLdapContext(
			ldapServerId, companyId);

		if (safeLdapContext == null) {
			return;
		}

		UserGroup userGroup = _userGroupLocalService.getUserGroup(userGroupId);

		Properties groupMappings = _ldapSettings.getGroupMappings(
			ldapServerId, companyId);
		Properties userMappings = _ldapSettings.getUserMappings(
			ldapServerId, companyId);

		Binding userGroupBinding = _safePortalLDAP.getGroup(
			ldapServerId, companyId, userGroup.getName());

		if (userGroupBinding == null) {
			if (userOperation == UserOperation.ADD) {
				_addGroup(
					ldapServerId, safeLdapContext, userGroup, user,
					groupMappings, userMappings);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get or add LDAP bindings for user group " +
							userGroup.getName());
				}
			}

			return;
		}

		SafeLdapName userGroupSafeLdapName = SafeLdapNameFactory.from(
			userGroupBinding);

		try {
			Modifications modifications =
				_portalToLDAPConverter.getLDAPGroupModifications(
					ldapServerId, userGroup, user, groupMappings, userMappings,
					userOperation);

			ModificationItem[] modificationItems = modifications.getItems();

			safeLdapContext.modifyAttributes(
				userGroupSafeLdapName, modificationItems);
		}
		catch (SchemaViolationException schemaViolationException) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to update LDAP bindings for user group " +
						userGroup.getName(),
					schemaViolationException);
			}

			Attributes attributes = _safePortalLDAP.getGroupAttributes(
				ldapServerId, companyId, safeLdapContext, userGroupSafeLdapName,
				true);

			Attribute groupMembers = attributes.get(
				groupMappings.getProperty(GroupConverterKeys.USER));

			if ((groupMembers != null) && (groupMembers.size() == 1)) {
				safeLdapContext.unbind(userGroupSafeLdapName);
			}
		}
		finally {
			if (safeLdapContext != null) {
				safeLdapContext.close();
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Finished exporting user ", user, " in user group ",
						userGroupId, " in ", stopWatch.getTime(), "ms"));
			}
		}
	}

	@Override
	public void exportUser(
			User user, Map<String, Serializable> userExpandoAttributes)
		throws Exception {

		if (user.isDefaultUser() ||
			((user.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
			 (user.getStatus() != WorkflowConstants.STATUS_INACTIVE)) ||
			_isAnonymousUser(user)) {

			return;
		}

		long companyId = user.getCompanyId();

		if (!_ldapSettings.isExportEnabled(companyId)) {
			return;
		}

		long ldapServerId = _safePortalLDAP.getLdapServerId(
			companyId, user.getScreenName(), user.getEmailAddress());

		SafeLdapContext safeLdapContext = _safePortalLDAP.getSafeLdapContext(
			ldapServerId, companyId);

		try {
			if (safeLdapContext == null) {
				return;
			}

			Properties userMappings = _ldapSettings.getUserMappings(
				ldapServerId, companyId);
			Properties userExpandoMappings =
				_ldapSettings.getUserExpandoMappings(ldapServerId, companyId);

			Binding userBinding = _safePortalLDAP.getUser(
				ldapServerId, user.getCompanyId(), user.getScreenName(),
				user.getEmailAddress(), true);

			if (userBinding == null) {
				userBinding = addUser(
					ldapServerId, safeLdapContext, user, userMappings);
			}
			else {
				Attributes attributes = _safePortalLDAP.getUserAttributes(
					ldapServerId, companyId, safeLdapContext,
					SafeLdapNameFactory.from(userBinding));

				String modifyTimestamp = LDAPUtil.getAttributeString(
					attributes, "modifyTimestamp");

				Date modifiedDate = LDAPUtil.parseDate(modifyTimestamp);

				if ((modifiedDate != null) &&
					modifiedDate.equals(user.getModifiedDate())) {

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Skipping user " + user.getEmailAddress() +
								" because he is already synchronized");
					}

					return;
				}
			}

			Modifications modifications =
				_portalToLDAPConverter.getLDAPUserModifications(
					user, userExpandoAttributes, userMappings,
					userExpandoMappings);

			if (modifications == null) {
				return;
			}

			ModificationItem[] modificationItems = modifications.getItems();

			SafeLdapName userSafeLdapName = SafeLdapNameFactory.from(
				userBinding);

			safeLdapContext.modifyAttributes(
				userSafeLdapName, modificationItems);

			if (!_ldapSettings.isExportGroupEnabled(companyId)) {
				return;
			}

			List<UserGroup> userGroups =
				_userGroupLocalService.getUserUserGroups(user.getUserId());

			for (UserGroup userGroup : userGroups) {
				exportUser(
					user.getUserId(), userGroup.getUserGroupId(),
					UserOperation.ADD);
			}

			Modifications groupModifications =
				_portalToLDAPConverter.getLDAPUserGroupModifications(
					ldapServerId, userGroups, user, userMappings);

			ModificationItem[] groupModificationItems =
				groupModifications.getItems();

			if (groupModificationItems.length > 0) {
				safeLdapContext.modifyAttributes(
					userSafeLdapName, groupModificationItems);
			}
		}
		catch (NameNotFoundException nameNotFoundException) {
			LDAPAuthConfiguration ldapAuthConfiguration =
				_ldapAuthConfigurationProvider.getConfiguration(companyId);

			if (ldapAuthConfiguration.required()) {
				throw nameNotFoundException;
			}

			_log.error(nameNotFoundException, nameNotFoundException);
		}
		finally {
			if (safeLdapContext != null) {
				safeLdapContext.close();
			}
		}
	}

	protected Binding addUser(
			long ldapServerId, LdapContext ldapContext, User user,
			Properties userMappings)
		throws Exception {

		SafeLdapName userSafeLdapName =
			_portalToLDAPConverter.getUserSafeLdapName(
				ldapServerId, user, userMappings);

		Attributes attributes = _portalToLDAPConverter.getLDAPUserAttributes(
			ldapServerId, user, userMappings);

		ldapContext.bind(userSafeLdapName, new PortalLDAPContext(attributes));

		return _safePortalLDAP.getUser(
			ldapServerId, user.getCompanyId(), user.getScreenName(),
			user.getEmailAddress());
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration)",
		unbind = "-"
	)
	protected void setConfigurationProvider(
		ConfigurationProvider<LDAPAuthConfiguration>
			ldapAuthConfigurationProvider) {

		_ldapAuthConfigurationProvider = ldapAuthConfigurationProvider;
	}

	@Reference(unbind = "-")
	protected void setLdapSettings(LDAPSettings ldapSettings) {
		_ldapSettings = ldapSettings;
	}

	@Reference(unbind = "-")
	protected void setUserGroupLocalService(
		UserGroupLocalService userGroupLocalService) {

		_userGroupLocalService = userGroupLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private Binding _addGroup(
			long ldapServerId, LdapContext ldapContext, UserGroup userGroup,
			User user, Properties groupMappings, Properties userMappings)
		throws Exception {

		SafeLdapName userGroupSafeLdapName =
			_portalToLDAPConverter.getGroupSafeLdapName(
				ldapServerId, userGroup, groupMappings);

		Attributes attributes = _portalToLDAPConverter.getLDAPGroupAttributes(
			ldapServerId, userGroup, user, groupMappings, userMappings);

		ldapContext.bind(
			userGroupSafeLdapName, new PortalLDAPContext(attributes));

		return _safePortalLDAP.getGroup(
			ldapServerId, userGroup.getCompanyId(), userGroup.getName());
	}

	private User _getAnonymousUser(long companyId) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(companyId=%s)(service.factoryPid=%s))", companyId,
				"com.liferay.user.associated.data.web.internal.configuration." +
					"AnonymousUserConfiguration"));

		if (configurations == null) {
			return null;
		}

		Optional<Configuration> configurationOptional = Optional.of(
			configurations[0]);

		if (!configurationOptional.isPresent()) {
			return null;
		}

		Configuration configuration = configurationOptional.get();

		Dictionary<String, Object> properties = configuration.getProperties();

		long anonymousUserId = (long)properties.get("userId");

		return _userLocalService.fetchUser(anonymousUserId);
	}

	private boolean _isAnonymousUser(User user) {
		try {
			User anonymousUser = _getAnonymousUser(user.getCompanyId());

			if ((anonymousUser != null) &&
				(user.getUserId() == anonymousUser.getUserId())) {

				return true;
			}

			return false;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPUserExporterImpl.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;
	private LDAPSettings _ldapSettings;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile PortalToLDAPConverter _portalToLDAPConverter;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile SafePortalLDAP _safePortalLDAP;

	private UserGroupLocalService _userGroupLocalService;
	private UserLocalService _userLocalService;

}