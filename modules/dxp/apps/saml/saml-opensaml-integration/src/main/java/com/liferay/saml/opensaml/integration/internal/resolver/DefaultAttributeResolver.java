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
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.internal.util.SamlUtil;
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

import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.XMLObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true, property = {"companyId=0"},
	service = AttributeResolver.class
)
public class DefaultAttributeResolver implements AttributeResolver {

	@Override
	public List<Attribute> resolve(
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext) {

		List<Attribute> attributes = new ArrayList<>();

		String entityId = samlMessageContext.getPeerEntityId();

		boolean namespaceEnabled =
			_metadataManager.isAttributesNamespaceEnabled(
				samlMessageContext.getPeerEntityId());

		for (String attributeName : getAttributeNames(entityId)) {
			if (attributeName.startsWith("expando:")) {
				attributeName = attributeName.substring(8);

				addExpandoAttribute(
					user, samlMessageContext, attributes, attributeName,
					namespaceEnabled);
			}
			else if (attributeName.equals("groups")) {
				addGroupsAttribute(
					user, samlMessageContext, attributes, attributeName,
					namespaceEnabled);
			}
			else if (attributeName.equals("organizations")) {
				addOrganizationsAttribute(
					user, samlMessageContext, attributes, attributeName,
					namespaceEnabled);
			}
			else if (attributeName.equals("organizationRoles")) {
				addOrganizationRolesAttribute(
					user, samlMessageContext, attributes, attributeName,
					namespaceEnabled);
			}
			else if (attributeName.equals("roles")) {
				addRolesAttribute(
					user, samlMessageContext, attributes, attributeName,
					namespaceEnabled);
			}
			else if (attributeName.startsWith("static:")) {
				attributeName = attributeName.substring(7);

				addStaticAttribute(
					user, samlMessageContext, attributes, attributeName,
					namespaceEnabled);
			}
			else if (attributeName.equals("siteRoles") ||
					 attributeName.equals("userGroupRoles")) {

				addSiteRolesAttribute(
					user, samlMessageContext, attributes, attributeName,
					namespaceEnabled);
			}
			else if (attributeName.equals("userGroups")) {
				addUserGroupsAttribute(
					user, samlMessageContext, attributes, attributeName,
					namespaceEnabled);
			}
			else {
				addUserAttribute(
					user, samlMessageContext, attributes, attributeName,
					namespaceEnabled);
			}
		}

		if (isPeerSalesForce(entityId)) {
			List<Attribute> salesForceAttributes = getSalesForceAttributes(
				samlMessageContext);

			if (!salesForceAttributes.isEmpty()) {
				attributes.addAll(salesForceAttributes);
			}
		}

		return attributes;
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
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext,
		List<Attribute> attributes, String attributeName,
		boolean namespaceEnabled) {

		Attribute attribute = null;

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		Serializable value = expandoBridge.getAttribute(attributeName, false);

		if (!namespaceEnabled) {
			attribute = OpenSamlUtil.buildAttribute(attributeName, value);
		}
		else {
			attribute = OpenSamlUtil.buildAttribute(
				"urn:liferay:user:expando:" + attributeName,
				Attribute.URI_REFERENCE, value);
		}

		attributes.add(attribute);
	}

	protected void addGroupsAttribute(
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext,
		List<Attribute> attributes, String attributeName,
		boolean namespaceEnabled) {

		try {
			List<Group> groups = user.getGroups();

			if (groups.isEmpty()) {
				return;
			}

			Attribute attribute = OpenSamlUtil.buildAttribute();

			if (namespaceEnabled) {
				attribute.setName("urn:liferay:groups");
				attribute.setNameFormat(Attribute.URI_REFERENCE);
			}
			else {
				attribute.setName("groups");
				attribute.setNameFormat(Attribute.UNSPECIFIED);
			}

			List<XMLObject> xmlObjects = attribute.getAttributeValues();

			for (Group group : groups) {
				XMLObject xmlObject = OpenSamlUtil.buildAttributeValue(
					group.getName());

				xmlObjects.add(xmlObject);
			}

			attributes.add(attribute);
		}
		catch (Exception e) {
			_log.error("Unable to get groups for user " + user.getUserId(), e);
		}
	}

	protected void addOrganizationRolesAttribute(
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext,
		List<Attribute> attributes, String attributeName,
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

				Set<Role> roles = groupRoles.get(group.getName());

				if (roles == null) {
					roles = new HashSet<>();

					groupRoles.put(group.getName(), roles);
				}

				roles.add(userGroupRole.getRole());
			}

			for (Entry<String, Set<Role>> entry : groupRoles.entrySet()) {
				String groupName = entry.getKey();
				Set<Role> roles = entry.getValue();

				Attribute attribute = OpenSamlUtil.buildAttribute();

				if (namespaceEnabled) {
					attribute.setName(
						"urn:liferay:organizationRole:" + groupName);
					attribute.setNameFormat(Attribute.URI_REFERENCE);
				}
				else {
					attribute.setName("organizationRole:" + groupName);
					attribute.setNameFormat(Attribute.UNSPECIFIED);
				}

				List<XMLObject> xmlObjects = attribute.getAttributeValues();

				for (Role role : roles) {
					XMLObject xmlObject = OpenSamlUtil.buildAttributeValue(
						role.getName());

					xmlObjects.add(xmlObject);
				}

				attributes.add(attribute);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to get organization roles for user " + user.getUserId(),
				e);
		}
	}

	protected void addOrganizationsAttribute(
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext,
		List<Attribute> attributes, String attributeName,
		boolean namespaceEnabled) {

		try {
			List<Organization> organizations = user.getOrganizations();

			if (organizations.isEmpty()) {
				return;
			}

			Attribute attribute = OpenSamlUtil.buildAttribute();

			if (namespaceEnabled) {
				attribute.setName("urn:liferay:organizations");
				attribute.setNameFormat(Attribute.URI_REFERENCE);
			}
			else {
				attribute.setName("organizations");
				attribute.setNameFormat(Attribute.UNSPECIFIED);
			}

			List<XMLObject> xmlObjects = attribute.getAttributeValues();

			for (Organization organization : organizations) {
				XMLObject xmlObject = OpenSamlUtil.buildAttributeValue(
					organization.getName());

				xmlObjects.add(xmlObject);
			}

			attributes.add(attribute);
		}
		catch (Exception e) {
			_log.error(
				"Unable to get organizations for user " + user.getUserId(), e);
		}
	}

	protected void addRolesAttribute(
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext,
		List<Attribute> attributes, String attributeName,
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

			Attribute attribute = OpenSamlUtil.buildAttribute();

			if (namespaceEnabled) {
				attribute.setName("urn:liferay:roles");
				attribute.setNameFormat(Attribute.URI_REFERENCE);
			}
			else {
				attribute.setName("roles");
				attribute.setNameFormat(Attribute.UNSPECIFIED);
			}

			List<XMLObject> xmlObjects = attribute.getAttributeValues();

			for (Role role : uniqueRoles) {
				XMLObject xmlObject = OpenSamlUtil.buildAttributeValue(
					role.getName());

				xmlObjects.add(xmlObject);
			}

			attributes.add(attribute);
		}
		catch (Exception e) {
			_log.error("Unable to get roles for user " + user.getUserId(), e);
		}
	}

	protected void addSiteRolesAttribute(
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext,
		List<Attribute> attributes, String attributeName,
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

				Set<Role> roles = groupRoles.get(group.getName());

				if (roles == null) {
					roles = new HashSet<>();

					groupRoles.put(group.getName(), roles);
				}

				roles.add(userGroupRole.getRole());
			}

			List<UserGroupGroupRole> inheritedSiteRoles =
				_userGroupGroupRoleLocalService.getUserGroupGroupRolesByUser(
					user.getUserId());

			for (UserGroupGroupRole userGroupGroupRole : inheritedSiteRoles) {
				Group group = userGroupGroupRole.getGroup();
				Role role = userGroupGroupRole.getRole();

				Set<Role> roles = groupRoles.get(group.getName());

				if (roles == null) {
					roles = new HashSet<>();

					groupRoles.put(group.getName(), roles);
				}

				roles.add(role);
			}

			for (Entry<String, Set<Role>> entry : groupRoles.entrySet()) {
				String groupName = entry.getKey();
				Set<Role> roles = entry.getValue();

				Attribute attribute = OpenSamlUtil.buildAttribute();

				if (namespaceEnabled) {
					if (attributeName.equals("siteRoles")) {
						attribute.setName("urn:liferay:siteRole:" + groupName);
					}
					else {
						attribute.setName(
							"urn:liferay:userGroupRole:" + groupName);
					}

					attribute.setNameFormat(Attribute.URI_REFERENCE);
				}
				else {
					if (attributeName.equals("siteRoles")) {
						attribute.setName("siteRole:" + groupName);
					}
					else {
						attribute.setName("userGroupRole:" + groupName);
					}

					attribute.setNameFormat(Attribute.UNSPECIFIED);
				}

				List<XMLObject> xmlObjects = attribute.getAttributeValues();

				for (Role role : roles) {
					XMLObject xmlObject = OpenSamlUtil.buildAttributeValue(
						role.getName());

					xmlObjects.add(xmlObject);
				}

				attributes.add(attribute);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to get user group roles for user " + user.getUserId(),
				e);
		}
	}

	protected void addStaticAttribute(
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext,
		List<Attribute> attributes, String attributeName,
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

		Attribute attribute = OpenSamlUtil.buildAttribute(
			attributeName, attributeValue);

		if (namespaceEnabled) {
			attribute.setNameFormat(Attribute.URI_REFERENCE);
		}
		else {
			attribute.setNameFormat(Attribute.UNSPECIFIED);
		}

		attributes.add(attribute);
	}

	protected void addUserAttribute(
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext,
		List<Attribute> attributes, String attributeName,
		boolean namespaceEnabled) {

		Attribute attribute = null;

		Serializable value = (Serializable)BeanPropertiesUtil.getObject(
			user, attributeName);

		if (!namespaceEnabled) {
			attribute = OpenSamlUtil.buildAttribute(attributeName, value);
		}
		else {
			attribute = OpenSamlUtil.buildAttribute(
				"urn:liferay:user:" + attributeName, Attribute.URI_REFERENCE,
				value);
		}

		attributes.add(attribute);
	}

	protected void addUserGroupsAttribute(
		User user, SAMLMessageContext<?, ?, ?> samlMessageContext,
		List<Attribute> attributes, String attributeName,
		boolean namespaceEnabled) {

		try {
			List<UserGroup> userGroups = user.getUserGroups();

			if (userGroups.isEmpty()) {
				return;
			}

			Attribute attribute = OpenSamlUtil.buildAttribute();

			if (namespaceEnabled) {
				attribute.setName("urn:liferay:userGroups");
				attribute.setNameFormat(Attribute.URI_REFERENCE);
			}
			else {
				attribute.setName("userGroups");
				attribute.setNameFormat(Attribute.UNSPECIFIED);
			}

			List<XMLObject> xmlObjects = attribute.getAttributeValues();

			for (UserGroup userGroup : userGroups) {
				XMLObject xmlObject = OpenSamlUtil.buildAttributeValue(
					userGroup.getName());

				xmlObjects.add(xmlObject);
			}

			attributes.add(attribute);
		}
		catch (Exception e) {
			_log.error(
				"Unable to get user groups for user " + user.getUserId(), e);
		}
	}

	protected String[] getAttributeNames(String entityId) {
		return _metadataManager.getAttributeNames(entityId);
	}

	protected List<Attribute> getSalesForceAttributes(
		SAMLMessageContext<?, ?, ?> samlMessageContext) {

		List<Attribute> attributes = new ArrayList<>();

		String samlIdpMetadataSalesForceLogoutUrl = GetterUtil.getString(
			PropsUtil.get(
				PortletPropsKeys.SAML_IDP_METADATA_SALESFORCE_LOGOUT_URL));

		Attribute logoutURLAttribute = OpenSamlUtil.buildAttribute(
			"logoutURL", samlIdpMetadataSalesForceLogoutUrl);

		attributes.add(logoutURLAttribute);

		String samlIdpMetadataSalesForceSsoStartPage = GetterUtil.getString(
			PropsUtil.get(
				PortletPropsKeys.SAML_IDP_METADATA_SALESFORCE_SSO_START_PAGE));

		try {
			IDPSSODescriptor idpSsoDescriptor =
				(IDPSSODescriptor)
					samlMessageContext.getLocalEntityRoleMetadata();

			SingleSignOnService singleSignOnService =
				SamlUtil.getSingleSignOnServiceForBinding(
					idpSsoDescriptor, SAMLConstants.SAML2_POST_BINDING_URI);

			samlIdpMetadataSalesForceSsoStartPage =
				singleSignOnService.getLocation();
		}
		catch (MetadataProviderException mpe) {
		}

		Attribute ssoStartPageAattribute = OpenSamlUtil.buildAttribute(
			"ssoStartPage", samlIdpMetadataSalesForceSsoStartPage);

		attributes.add(ssoStartPageAattribute);

		return attributes;
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