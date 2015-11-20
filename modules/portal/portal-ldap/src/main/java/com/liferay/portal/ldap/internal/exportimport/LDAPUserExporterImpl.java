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

package com.liferay.portal.ldap.internal.exportimport;

import com.liferay.portal.kernel.ldap.LDAPUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.ldap.GroupConverterKeys;
import com.liferay.portal.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.ldap.internal.PortalLDAPContext;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.security.exportimport.UserOperation;
import com.liferay.portal.security.ldap.LDAPSettings;
import com.liferay.portal.security.ldap.Modifications;
import com.liferay.portal.security.ldap.PortalLDAP;
import com.liferay.portal.security.ldap.PortalToLDAPConverter;
import com.liferay.portal.service.UserGroupLocalService;
import com.liferay.portal.service.UserLocalService;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SchemaViolationException;
import javax.naming.ldap.LdapContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Wesley Gong
 * @author Vilmos Papp
 */
@Component(
	configurationPid = "com.liferay.portal.authenticator.ldap.configuration.LDAPAuthConfiguration",
	immediate = true, service = UserExporter.class
)
public class LDAPUserExporterImpl implements UserExporter {

	@Override
	public void exportUser(
			Contact contact, Map<String, Serializable> contactExpandoAttributes)
		throws Exception {

		long companyId = contact.getCompanyId();

		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(companyId);

		if (!ldapAuthConfiguration.enabled() ||
			!_ldapSettings.isExportEnabled(companyId)) {

			return;
		}

		User user = _userLocalService.getUserByContactId(
			contact.getContactId());

		if (user.isDefaultUser() ||
			(user.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		long ldapServerId = _portalLDAP.getLdapServerId(
			companyId, user.getScreenName(), user.getEmailAddress());

		LdapContext ldapContext = _portalLDAP.getContext(
			ldapServerId, companyId);

		try {
			if (ldapContext == null) {
				return;
			}

			Properties contactMappings = _ldapSettings.getContactMappings(
				ldapServerId, companyId);
			Properties contactExpandoMappings =
				_ldapSettings.getContactExpandoMappings(
					ldapServerId, companyId);

			Binding binding = _portalLDAP.getUser(
				ldapServerId, contact.getCompanyId(), user.getScreenName(),
				user.getEmailAddress());

			if (binding == null) {
				Properties userMappings = _ldapSettings.getUserMappings(
					ldapServerId, companyId);

				binding = addUser(
					ldapServerId, ldapContext, user, userMappings);
			}

			Name name = new CompositeName();

			name.add(
				_portalLDAP.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications =
				_portalToLDAPConverter.getLDAPContactModifications(
					contact, contactExpandoAttributes, contactMappings,
					contactExpandoMappings);

			if (modifications == null) {
				return;
			}

			ModificationItem[] modificationItems = modifications.getItems();

			ldapContext.modifyAttributes(name, modificationItems);
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Override
	public void exportUser(
			long userId, long userGroupId, UserOperation userOperation)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(companyId);

		if (!ldapAuthConfiguration.enabled() ||
			!_ldapSettings.isExportEnabled(companyId) ||
			!_ldapSettings.isExportGroupEnabled(companyId)) {

			return;
		}

		long ldapServerId = _portalLDAP.getLdapServerId(
			companyId, user.getScreenName(), user.getEmailAddress());

		LdapContext ldapContext = _portalLDAP.getContext(
			ldapServerId, companyId);

		if (ldapContext == null) {
			return;
		}

		UserGroup userGroup = _userGroupLocalService.getUserGroup(userGroupId);

		Properties groupMappings = _ldapSettings.getGroupMappings(
			ldapServerId, companyId);
		Properties userMappings = _ldapSettings.getUserMappings(
			ldapServerId, companyId);

		Binding binding = _portalLDAP.getGroup(
			ldapServerId, companyId, userGroup.getName());

		try {
			if (binding == null) {
				if (userOperation == UserOperation.ADD) {
					addGroup(
						ldapServerId, ldapContext, userGroup, user,
						groupMappings, userMappings);
				}

				return;
			}

			Name name = new CompositeName();

			name.add(
				_portalLDAP.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications =
				_portalToLDAPConverter.getLDAPGroupModifications(
					ldapServerId, userGroup, user, groupMappings, userMappings,
					userOperation);

			ModificationItem[] modificationItems = modifications.getItems();

			ldapContext.modifyAttributes(name, modificationItems);
		}
		catch (SchemaViolationException sve) {
			String fullGroupDN = _portalLDAP.getNameInNamespace(
				ldapServerId, companyId, binding);

			Attributes attributes = _portalLDAP.getGroupAttributes(
				ldapServerId, companyId, ldapContext, fullGroupDN, true);

			Attribute groupMembers = attributes.get(
				groupMappings.getProperty(GroupConverterKeys.USER));

			if ((groupMembers != null) && (groupMembers.size() == 1)) {
				ldapContext.unbind(fullGroupDN);
			}
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Override
	public void exportUser(
			User user, Map<String, Serializable> userExpandoAttributes)
		throws Exception {

		if (user.isDefaultUser() ||
			(user.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		long companyId = user.getCompanyId();

		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(companyId);

		if (!ldapAuthConfiguration.enabled() ||
			!_ldapSettings.isExportEnabled(companyId)) {

			return;
		}

		long ldapServerId = _portalLDAP.getLdapServerId(
			companyId, user.getScreenName(), user.getEmailAddress());

		LdapContext ldapContext = _portalLDAP.getContext(
			ldapServerId, companyId);

		try {
			if (ldapContext == null) {
				return;
			}

			Properties userMappings = _ldapSettings.getUserMappings(
				ldapServerId, companyId);
			Properties userExpandoMappings =
				_ldapSettings.getUserExpandoMappings(ldapServerId, companyId);

			Binding binding = _portalLDAP.getUser(
				ldapServerId, user.getCompanyId(), user.getScreenName(),
				user.getEmailAddress(), true);

			if (binding == null) {
				binding = addUser(
					ldapServerId, ldapContext, user, userMappings);
			}
			else {
				Attributes attributes = _portalLDAP.getUserAttributes(
					ldapServerId, companyId, ldapContext,
					_portalLDAP.getNameInNamespace(
						ldapServerId, companyId, binding));

				String modifyTimestamp = LDAPUtil.getAttributeString(
					attributes, "modifyTimestamp");

				if (Validator.isNotNull(modifyTimestamp)) {
					Date modifiedDate = LDAPUtil.parseDate(modifyTimestamp);

					if (modifiedDate.equals(user.getModifiedDate())) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Skipping user " + user.getEmailAddress() +
									" because he is already synchronized");
						}

						return;
					}
				}
			}

			Name name = new CompositeName();

			name.add(
				_portalLDAP.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications =
				_portalToLDAPConverter.getLDAPUserModifications(
					user, userExpandoAttributes, userMappings,
					userExpandoMappings);

			if (modifications == null) {
				return;
			}

			ModificationItem[] modificationItems = modifications.getItems();

			ldapContext.modifyAttributes(name, modificationItems);

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
				ldapContext.modifyAttributes(name, groupModificationItems);
			}
		}
		catch (NameNotFoundException nnfe) {
			if (ldapAuthConfiguration.required()) {
				throw nnfe;
			}

			_log.error(nnfe, nnfe);
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Reference(unbind = "-")
	public void setPortalToLDAPConverter(
		PortalToLDAPConverter portalToLDAPConverter) {

		_portalToLDAPConverter = portalToLDAPConverter;
	}

	protected Binding addGroup(
			long ldapServerId, LdapContext ldapContext, UserGroup userGroup,
			User user, Properties groupMappings, Properties userMappings)
		throws Exception {

		Name name = new CompositeName();

		name.add(
			_portalToLDAPConverter.getGroupDNName(
				ldapServerId, userGroup, groupMappings));

		Attributes attributes = _portalToLDAPConverter.getLDAPGroupAttributes(
			ldapServerId, userGroup, user, groupMappings, userMappings);

		ldapContext.bind(name, new PortalLDAPContext(attributes));

		Binding binding = _portalLDAP.getGroup(
			ldapServerId, userGroup.getCompanyId(), userGroup.getName());

		return binding;
	}

	protected Binding addUser(
			long ldapServerId, LdapContext ldapContext, User user,
			Properties userMappings)
		throws Exception {

		Name name = new CompositeName();

		name.add(
			_portalToLDAPConverter.getUserDNName(
				ldapServerId, user, userMappings));

		Attributes attributes = _portalToLDAPConverter.getLDAPUserAttributes(
			ldapServerId, user, userMappings);

		ldapContext.bind(name, new PortalLDAPContext(attributes));

		Binding binding = _portalLDAP.getUser(
			ldapServerId, user.getCompanyId(), user.getScreenName(),
			user.getEmailAddress());

		return binding;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.ldap.authenticator.configuration.LDAPAuthConfiguration)",
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
	protected void setPortalLDAP(PortalLDAP portalLDAP) {
		_portalLDAP = portalLDAP;
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

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPUserExporterImpl.class);

	private volatile ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;
	private volatile LDAPSettings _ldapSettings;
	private volatile PortalLDAP _portalLDAP;
	private volatile PortalToLDAPConverter _portalToLDAPConverter;
	private volatile UserGroupLocalService _userGroupLocalService;
	private volatile UserLocalService _userLocalService;

}