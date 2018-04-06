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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.opensaml.integration.resolver.AttributeResolver;
import com.liferay.saml.util.PortletPropsKeys;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Attribute;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Carlos Sierra
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MIN_VALUE,
	service = AttributeResolver.class
)
public class DefaultAttributeResolver implements AttributeResolver {

	@Override
	public void resolve(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher) {

		String entityId = attributeResolverSAMLContext.resolvePeerEntityId();

		boolean namespaceEnabled =
			_metadataManager.isAttributesNamespaceEnabled(entityId);

		for (String attributeName : getAttributeNames(entityId)) {
			if (attributeName.startsWith("expando:")) {
				attributeName = attributeName.substring(8);

				addExpandoAttribute(
					user, attributeResolverSAMLContext, attributePublisher,
					attributeName, namespaceEnabled);
			}
			else if (attributeName.equals("groups")) {
				addGroupsAttribute(
					user, attributeResolverSAMLContext, attributePublisher,
					attributeName, namespaceEnabled);
			}
			else if (attributeName.equals("organizations")) {
				addOrganizationsAttribute(
					user, attributeResolverSAMLContext, attributePublisher,
					attributeName, namespaceEnabled);
			}
			else if (attributeName.equals("organizationRoles")) {
				addOrganizationRolesAttribute(
					user, attributeResolverSAMLContext, attributePublisher,
					attributeName, namespaceEnabled);
			}
			else if (attributeName.equals("roles")) {
				addRolesAttribute(
					user, attributeResolverSAMLContext, attributePublisher,
					attributeName, namespaceEnabled);
			}
			else if (attributeName.startsWith("static:")) {
				attributeName = attributeName.substring(7);

				addStaticAttribute(
					user, attributeResolverSAMLContext, attributePublisher,
					attributeName, namespaceEnabled);
			}
			else if (attributeName.equals("siteRoles") ||
					 attributeName.equals("userGroupRoles")) {

				addSiteRolesAttribute(
					user, attributeResolverSAMLContext, attributePublisher,
					attributeName, namespaceEnabled);
			}
			else if (attributeName.equals("userGroups")) {
				addUserGroupsAttribute(
					user, attributeResolverSAMLContext, attributePublisher,
					attributeName, namespaceEnabled);
			}
			else {
				addUserAttribute(
					user, attributeResolverSAMLContext, attributePublisher,
					attributeName, namespaceEnabled);
			}
		}

		if (isPeerSalesForce(entityId)) {
			addSalesForceAttributes(
				attributeResolverSAMLContext, attributePublisher);
		}
	}

	@Reference(unbind = "-")
	public void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	public void setMetadataManager(MetadataManager metadataManager) {
		_metadataManager = metadataManager;
	}

	@Reference(unbind = "-")
	public void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	public void setUserGroupGroupRoleLocalService(
		UserGroupGroupRoleLocalService userGroupGroupRoleLocalService) {

		_userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
	}

	@Reference(unbind = "-")
	public void setUserGroupRoleLocalService(
		UserGroupRoleLocalService userGroupRoleLocalService) {

		_userGroupRoleLocalService = userGroupRoleLocalService;
	}

	protected void addExpandoAttribute(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher, String attributeName,
		boolean namespaceEnabled) {

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		Serializable value = expandoBridge.getAttribute(attributeName, false);

		if (!namespaceEnabled) {
			attributePublisher.publish(
				attributeName, Attribute.UNSPECIFIED,
				attributePublisher.buildString(value.toString()));
		}
		else {
			attributePublisher.publish(
				"urn:liferay:user:expando:" + attributeName,
				Attribute.URI_REFERENCE,
				attributePublisher.buildString(value.toString()));
		}
	}

	protected void addGroupsAttribute(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher, String attributeName,
		boolean namespaceEnabled) {

		try {
			List<Group> groups = user.getGroups();

			if (groups.isEmpty()) {
				return;
			}

			String name = null;
			String nameFormat = null;

			if (namespaceEnabled) {
				name = "urn:liferay:groups";
				nameFormat = Attribute.URI_REFERENCE;
			}
			else {
				name = "groups";
				nameFormat = Attribute.UNSPECIFIED;
			}

			Stream<Group> groupsStream = groups.stream();

			attributePublisher.publish(
				name, nameFormat,
				groupsStream.map(
					Group::getName
				).map(
					attributePublisher::buildString
				).toArray(
					AttributePublisher.AttributeValue[]::new
				));
		}
		catch (Exception e) {
			_log.error("Unable to get groups for user " + user.getUserId(), e);
		}
	}

	protected void addOrganizationRolesAttribute(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher, String attributeName,
		boolean namespaceEnabled) {

		try {
			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRoles(user.getUserId());

			Map<String, Set<Role>> groupRoles = new HashMap<>();

			for (UserGroupRole userGroupRole : userGroupRoles) {
				Group group = userGroupRole.getGroup();

				Role role = userGroupRole.getRole();

				if (role.getType() != RoleConstants.TYPE_ORGANIZATION) {
					continue;
				}

				Set<Role> roles = groupRoles.computeIfAbsent(
					group.getName(), k -> new HashSet<>());

				roles.add(userGroupRole.getRole());
			}

			for (Entry<String, Set<Role>> entry : groupRoles.entrySet()) {
				String groupName = entry.getKey();

				String name = null;
				String nameFormat = null;

				if (namespaceEnabled) {
					name = "urn:liferay:organizationRole:" + groupName;
					nameFormat = Attribute.URI_REFERENCE;
				}
				else {
					name = "organizationRole:" + groupName;
					nameFormat = Attribute.UNSPECIFIED;
				}

				Set<Role> roles = entry.getValue();

				Stream<Role> rolesStream = roles.stream();

				attributePublisher.publish(
					name, nameFormat,
					rolesStream.map(
						Role::getName
					).map(
						attributePublisher::buildString
					).toArray(
						AttributePublisher.AttributeValue[]::new
					)
				);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to get organization roles for user " + user.getUserId(),
				e);
		}
	}

	protected void addOrganizationsAttribute(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher publisher, String attributeName,
		boolean namespaceEnabled) {

		try {
			List<Organization> organizations = user.getOrganizations();

			if (organizations.isEmpty()) {
				return;
			}

			String name = null;
			String nameFormat = null;

			if (namespaceEnabled) {
				name = "urn:liferay:organizations";
				nameFormat = Attribute.URI_REFERENCE;
			}
			else {
				name = "organizations";
				nameFormat = Attribute.UNSPECIFIED;
			}

			Stream<Organization> organizationsStream = organizations.stream();

			publisher.publish(
				name, nameFormat,
				organizationsStream.map(
					Organization::getName
				).map(
					publisher::buildString
				).toArray(
					AttributePublisher.AttributeValue[]::new
				));
		}
		catch (Exception e) {
			_log.error(
				"Unable to get organizations for user " + user.getUserId(), e);
		}
	}

	protected void addRolesAttribute(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher, String attributeName,
		boolean namespaceEnabled) {

		try {
			List<Role> roles = user.getRoles();

			List<Group> groups = user.getGroups();
			List<Organization> organizations = user.getOrganizations();
			List<UserGroup> userGroups = user.getUserGroups();

			List<Group> inheritedSiteGroups =
				_groupLocalService.getUserGroupsRelatedGroups(userGroups);

			List<Group> organizationsRelatedGroups = Collections.emptyList();

			if (!organizations.isEmpty()) {
				organizationsRelatedGroups =
					_groupLocalService.getOrganizationsRelatedGroups(
						organizations);

				for (Group group : organizationsRelatedGroups) {
					if (!inheritedSiteGroups.contains(group)) {
						inheritedSiteGroups.add(group);
					}
				}
			}

			List<Group> allGroups = new ArrayList<>();

			allGroups.addAll(groups);
			allGroups.addAll(inheritedSiteGroups);
			allGroups.addAll(organizationsRelatedGroups);
			allGroups.addAll(
				_groupLocalService.getOrganizationsGroups(organizations));
			allGroups.addAll(
				_groupLocalService.getUserGroupsGroups(userGroups));

			Set<Role> uniqueRoles = new HashSet<>();

			uniqueRoles.addAll(roles);

			for (Group group : allGroups) {
				if (_roleLocalService.hasGroupRoles(group.getGroupId())) {
					List<Role> groupRoles = _roleLocalService.getGroupRoles(
						group.getGroupId());

					uniqueRoles.addAll(groupRoles);
				}
			}

			if (uniqueRoles.isEmpty()) {
				return;
			}

			String name = null;
			String nameFormat = null;

			if (namespaceEnabled) {
				name = "urn:liferay:roles";
				nameFormat = Attribute.URI_REFERENCE;
			}
			else {
				name = "roles";
				nameFormat = Attribute.UNSPECIFIED;
			}

			Stream<Role> uniqueRolesStream = uniqueRoles.stream();

			attributePublisher.publish(
				name, nameFormat,
				uniqueRolesStream.map(
					Role::getName
				).map(
					attributePublisher::buildString
				).toArray(
					AttributePublisher.AttributeValue[]::new
				));
		}
		catch (Exception e) {
			_log.error("Unable to get roles for user " + user.getUserId(), e);
		}
	}

	protected void addSalesForceAttributes(
		AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher) {

		String samlIdpMetadataSalesForceLogoutURL = GetterUtil.getString(
			PropsUtil.get(
				PortletPropsKeys.SAML_IDP_METADATA_SALESFORCE_LOGOUT_URL));

		attributePublisher.publish(
			"logoutURL", Attribute.UNSPECIFIED,
			attributePublisher.buildString(samlIdpMetadataSalesForceLogoutURL));

		String samlIdpMetadataSalesForceSsoStartPage = GetterUtil.getString(
			PropsUtil.get(
				PortletPropsKeys.SAML_IDP_METADATA_SALESFORCE_SSO_START_PAGE));

		List<String> locations =
			attributeResolverSAMLContext.resolveSsoServicesLocationForBinding(
				SAMLConstants.SAML2_POST_BINDING_URI);

		if (!locations.isEmpty()) {
			samlIdpMetadataSalesForceSsoStartPage = locations.get(0);
		}

		attributePublisher.publish(
			"ssoStartPage", Attribute.UNSPECIFIED,
			attributePublisher.buildString(
				samlIdpMetadataSalesForceSsoStartPage));
	}

	protected void addSiteRolesAttribute(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher, String attributeName,
		boolean namespaceEnabled) {

		try {
			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRoles(user.getUserId());

			Map<String, Set<Role>> groupRoles = new HashMap<>();

			for (UserGroupRole userGroupRole : userGroupRoles) {
				Group group = userGroupRole.getGroup();

				Role role = userGroupRole.getRole();

				if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) &&
					!attributeName.equals("userGroupRoles")) {

					continue;
				}

				Set<Role> roles = groupRoles.computeIfAbsent(
					group.getName(), k -> new HashSet<>());

				roles.add(userGroupRole.getRole());
			}

			List<UserGroupGroupRole> inheritedSiteRoles =
				_userGroupGroupRoleLocalService.getUserGroupGroupRolesByUser(
					user.getUserId());

			for (UserGroupGroupRole userGroupGroupRole : inheritedSiteRoles) {
				Group group = userGroupGroupRole.getGroup();
				Role role = userGroupGroupRole.getRole();

				Set<Role> roles = groupRoles.computeIfAbsent(
					group.getName(), k -> new HashSet<>());

				roles.add(role);
			}

			for (Entry<String, Set<Role>> entry : groupRoles.entrySet()) {
				String groupName = entry.getKey();

				String name = null;
				String nameFormat = null;

				if (namespaceEnabled) {
					if (attributeName.equals("siteRoles")) {
						name = "urn:liferay:siteRole:" + groupName;
					}
					else {
						name = "urn:liferay:userGroupRole:" + groupName;
					}

					nameFormat = Attribute.URI_REFERENCE;
				}
				else {
					if (attributeName.equals("siteRoles")) {
						name = "siteRole:" + groupName;
					}
					else {
						name = "userGroupRole:" + groupName;
					}

					nameFormat = Attribute.UNSPECIFIED;
				}

				Set<Role> roles = entry.getValue();

				Stream<Role> rolesStream = roles.stream();

				attributePublisher.publish(
					name, nameFormat,
					rolesStream.map(
						Role::getName
					).map(
						attributePublisher::buildString
					).toArray(
						AttributePublisher.AttributeValue[]::new
					)
				);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to get user group roles for user " + user.getUserId(),
				e);
		}
	}

	protected void addStaticAttribute(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher, String attributeName,
		boolean namespaceEnabled) {

		String attributeValue = StringPool.BLANK;

		if (attributeName.indexOf('=') > 0) {
			String[] values = StringUtil.split(attributeName, "=");

			attributeName = values[0];
			attributeValue = values[1];

			if (values.length > 2) {
				for (int i = 2; i < values.length; i++) {
					attributeValue = attributeValue.concat("=").concat(
						values[i]);
				}
			}
		}

		String nameFormat = null;

		if (namespaceEnabled) {
			nameFormat = Attribute.URI_REFERENCE;
		}
		else {
			nameFormat = Attribute.UNSPECIFIED;
		}

		attributePublisher.publish(
			attributeName, nameFormat,
			attributePublisher.buildString(attributeValue));
	}

	protected void addUserAttribute(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher, String attributeName,
		boolean namespaceEnabled) {

		Serializable value = (Serializable)BeanPropertiesUtil.getObject(
			user, attributeName);

		if (!namespaceEnabled) {
			attributePublisher.publish(
				attributeName, Attribute.UNSPECIFIED,
				attributePublisher.buildString(value.toString()));
		}
		else {
			attributePublisher.publish(
				"urn:liferay:user:" + attributeName, Attribute.URI_REFERENCE,
				attributePublisher.buildString(value.toString()));
		}
	}

	protected void addUserGroupsAttribute(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher attributePublisher, String attributeName,
		boolean namespaceEnabled) {

		try {
			List<UserGroup> userGroups = user.getUserGroups();

			if (userGroups.isEmpty()) {
				return;
			}

			String name = null;
			String nameFormat = null;

			if (namespaceEnabled) {
				name = "urn:liferay:userGroups";
				nameFormat = Attribute.URI_REFERENCE;
			}
			else {
				name = "userGroups";
				nameFormat = Attribute.UNSPECIFIED;
			}

			Stream<UserGroup> userGroupsStream = userGroups.stream();

			attributePublisher.publish(
				name, nameFormat,
				userGroupsStream.map(
					UserGroup::getName
				).map(
					attributePublisher::buildString
				).toArray(
					AttributePublisher.AttributeValue[]::new
				));
		}
		catch (Exception e) {
			_log.error(
				"Unable to get user groups for user " + user.getUserId(), e);
		}
	}

	protected String[] getAttributeNames(String entityId) {
		return _metadataManager.getAttributeNames(entityId);
	}

	protected boolean isPeerSalesForce(String entityId) {
		if (entityId.equals(_SALESFORCE_ENTITY_ID)) {
			return true;
		}

		return GetterUtil.getBoolean(
			PropsUtil.get(
				PortletPropsKeys.
					SAML_IDP_METADATA_SALESFORCE_ATTRIBUTES_ENABLED,
				new Filter(entityId)));
	}

	private static final String _SALESFORCE_ENTITY_ID =
		"https://saml.salesforce.com";

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultAttributeResolver.class);

	private GroupLocalService _groupLocalService;
	private MetadataManager _metadataManager;
	private RoleLocalService _roleLocalService;
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}