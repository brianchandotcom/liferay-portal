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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.opensaml.integration.internal.util.SamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.XMLObject;

/**
 * @author Mika Koivisto
 */
public class DefaultAttributeResolverTest extends BaseSamlTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		_beanProperties = mock(BeanProperties.class);

		beanPropertiesUtil.setBeanProperties(_beanProperties);

		_defaultAttributeResolver.setGroupLocalService(groupLocalService);

		_metadataManager = mock(MetadataManager.class);

		_defaultAttributeResolver.setMetadataManager(_metadataManager);

		when(
			_metadataManager.isAttributesEnabled(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			true
		);

		_user = mock(User.class);

		_expandoBridge = mock(ExpandoBridge.class);

		when(
			_user.getExpandoBridge()
		).thenReturn(
			_expandoBridge
		);

		_roleLocalService = mock(RoleLocalService.class);

		_defaultAttributeResolver.setRoleLocalService(_roleLocalService);

		_samlMessageContext = new BasicSAMLMessageContext<>();

		_samlMessageContext.setPeerEntityId(SP_ENTITY_ID);

		_userGroupGroupRoleLocalService = mock(
			UserGroupGroupRoleLocalService.class);

		_defaultAttributeResolver.setUserGroupGroupRoleLocalService(
			_userGroupGroupRoleLocalService);

		_userGroupRoleLocalService = mock(UserGroupRoleLocalService.class);

		_defaultAttributeResolver.setUserGroupRoleLocalService(
			_userGroupRoleLocalService);
	}

	@Test
	public void testResolveExpandoAttributes() throws Exception {
		when(
			_expandoBridge.getAttribute(
				Mockito.eq("customerId"), Mockito.anyBoolean())
		).thenReturn(
			"12345"
		);

		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {"expando:customerId"}
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		assertEquals(
			attributePublisherImpl.getAttributes(), "customerId", "12345");
	}

	@Test
	public void testResolveGroupsAttributes() throws Exception {
		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {"groups"}
		);

		List<Group> groups = new ArrayList<>();

		Group group1 = mock(Group.class);

		when(
			group1.getName()
		).thenReturn(
			"Test 1"
		);

		groups.add(group1);

		Group group2 = mock(Group.class);

		when(
			group2.getName()
		).thenReturn(
			"Test 2"
		);

		groups.add(group2);

		when(
			_user.getGroups()
		).thenReturn(
			groups
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		assertEquals(attributes, "groups", new String[] {"Test 1", "Test 2"});
	}

	@Test
	public void testResolveOrganizationRolesAttributes() throws Exception {
		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {"organizationRoles"}
		);

		Group group1 = mock(Group.class);

		when(
			group1.getName()
		).thenReturn(
			"Group Test 1"
		);

		Role role1 = mock(Role.class);

		when(
			role1.getName()
		).thenReturn(
			"Role Test 1"
		);

		when(
			role1.getType()
		).thenReturn(
			RoleConstants.TYPE_ORGANIZATION
		);

		Role role2 = mock(Role.class);

		when(
			role2.getName()
		).thenReturn(
			"Role Test 2"
		);

		when(
			role2.getType()
		).thenReturn(
			RoleConstants.TYPE_ORGANIZATION
		);

		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		UserGroupRole userGroupRole1 = mock(UserGroupRole.class);

		when(
			userGroupRole1.getGroup()
		).thenReturn(
			group1
		);

		when(
			userGroupRole1.getRole()
		).thenReturn(
			role1
		);

		userGroupRoles.add(userGroupRole1);

		UserGroupRole userGroupRole2 = mock(UserGroupRole.class);

		when(
			userGroupRole2.getGroup()
		).thenReturn(
			group1
		);

		when(
			userGroupRole2.getRole()
		).thenReturn(
			role2
		);

		userGroupRoles.add(userGroupRole2);

		when(
			_userGroupRoleLocalService.getUserGroupRoles(Mockito.anyLong())
		).thenReturn(
			userGroupRoles
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		assertEquals(
			attributes, "organizationRole:Group Test 1",
			new String[] {"Role Test 1", "Role Test 2"});
	}

	@Test
	public void testResolveOrganizationsAttributes() throws Exception {
		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {"organizations"}
		);

		List<Organization> organizations = new ArrayList<>();

		Organization organization1 = mock(Organization.class);

		when(
			organization1.getName()
		).thenReturn(
			"Test 1"
		);

		organizations.add(organization1);

		Organization organization2 = mock(Organization.class);

		when(
			organization2.getName()
		).thenReturn(
			"Test 2"
		);

		organizations.add(organization2);

		when(
			_user.getOrganizations()
		).thenReturn(
			organizations
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		assertEquals(
			attributes, "organizations", new String[] {"Test 1", "Test 2"});
	}

	@Test
	public void testResolveRolesAttributes() throws Exception {
		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {"roles"}
		);

		List<Role> roles = new ArrayList<>();

		Role role1 = mock(Role.class);

		when(
			role1.getName()
		).thenReturn(
			"Test 1"
		);

		roles.add(role1);

		Role role2 = mock(Role.class);

		when(
			role2.getName()
		).thenReturn(
			"Test 2"
		);

		roles.add(role2);

		when(
			_user.getRoles()
		).thenReturn(
			roles
		);

		List<Group> groups = new ArrayList<>();

		Group group1 = mock(Group.class);

		when(
			group1.getName()
		).thenReturn(
			"Group Test 1"
		);

		groups.add(group1);

		when(
			_user.getGroups()
		).thenReturn(
			groups
		);

		when(
			_roleLocalService.hasGroupRoles(Mockito.anyLong())
		).thenReturn(
			Boolean.TRUE
		);

		List<Role> groupRoles = new ArrayList<>();

		Role groupRole1 = mock(Role.class);

		when(
			groupRole1.getName()
		).thenReturn(
			"Group Role Test 1"
		);

		groupRoles.add(groupRole1);

		when(
			_roleLocalService.getGroupRoles(Mockito.anyLong())
		).thenReturn(
			groupRoles
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		assertEquals(
			attributes, "roles",
			new String[] {"Test 1", "Test 2", "Group Role Test 1"});
	}

	@Test
	public void testResolveSiteRolesAttributes() throws Exception {
		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {"siteRoles"}
		);

		Group group1 = mock(Group.class);

		when(
			group1.getName()
		).thenReturn(
			"Group Test 1"
		);

		Role role1 = mock(Role.class);

		when(
			role1.getName()
		).thenReturn(
			"Role Test 1"
		);

		Role role2 = mock(Role.class);

		when(
			role2.getName()
		).thenReturn(
			"Role Test 2"
		);

		Role role3 = mock(Role.class);

		when(
			role3.getName()
		).thenReturn(
			"Org Role Test"
		);

		when(
			role3.getType()
		).thenReturn(
			RoleConstants.TYPE_ORGANIZATION
		);

		Role role4 = mock(Role.class);

		when(
			role4.getName()
		).thenReturn(
			"Inherited Role Test"
		);

		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		UserGroupRole userGroupRole1 = mock(UserGroupRole.class);

		when(
			userGroupRole1.getGroup()
		).thenReturn(
			group1
		);

		when(
			userGroupRole1.getRole()
		).thenReturn(
			role1
		);

		userGroupRoles.add(userGroupRole1);

		UserGroupRole userGroupRole2 = mock(UserGroupRole.class);

		when(
			userGroupRole2.getGroup()
		).thenReturn(
			group1
		);

		when(
			userGroupRole2.getRole()
		).thenReturn(
			role2
		);

		userGroupRoles.add(userGroupRole2);

		UserGroupRole userGroupRole3 = mock(UserGroupRole.class);

		when(
			userGroupRole3.getGroup()
		).thenReturn(
			group1
		);

		when(
			userGroupRole3.getRole()
		).thenReturn(
			role3
		);

		userGroupRoles.add(userGroupRole3);

		when(
			_userGroupRoleLocalService.getUserGroupRoles(Mockito.anyLong())
		).thenReturn(
			userGroupRoles
		);

		List<UserGroupGroupRole> userGroupGroupRoles = new ArrayList<>();

		UserGroupGroupRole userGroupGroupRole = mock(UserGroupGroupRole.class);

		when(
			userGroupGroupRole.getGroup()
		).thenReturn(
			group1
		);

		when(
			userGroupGroupRole.getRole()
		).thenReturn(
			role4
		);

		userGroupGroupRoles.add(userGroupGroupRole);

		when(
			_userGroupGroupRoleLocalService.getUserGroupGroupRolesByUser(
				Mockito.anyLong())
		).thenReturn(
			userGroupGroupRoles
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		assertEquals(
			attributes, "siteRole:Group Test 1",
			new String[] {"Role Test 1", "Role Test 2", "Inherited Role Test"});
	}

	@Test
	public void testResolveStaticAttributes() throws Exception {
		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {
				"static:emailAddress=test@liferay.com",
				"static:screenName=test=test2"
			}
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		assertEquals(attributes, "emailAddress", "test@liferay.com");
		assertEquals(attributes, "screenName", "test=test2");
	}

	@Test
	public void testResolveUserAttributes() throws Exception {
		when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("emailAddress"))
		).thenReturn(
			"test@liferay.com"
		);

		when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("firstName"))
		).thenReturn(
			"Test"
		);

		when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("lastName"))
		).thenReturn(
			"Test"
		);

		when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("screenName"))
		).thenReturn(
			"test"
		);

		when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("uuid"))
		).thenReturn(
			"xxxx-xxxx-xxx-xxxx"
		);

		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {
				"emailAddress", "firstName", "lastName", "screenName", "uuid"
			}
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		assertEquals(attributes, "emailAddress", "test@liferay.com");
		assertEquals(attributes, "firstName", "Test");
		assertEquals(attributes, "lastName", "Test");
		assertEquals(attributes, "screenName", "test");
		assertEquals(attributes, "uuid", "xxxx-xxxx-xxx-xxxx");
	}

	@Test
	public void testResolveUserGroupRolesAttributes() throws Exception {
		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {"userGroupRoles"}
		);

		Group group1 = mock(Group.class);

		when(
			group1.getName()
		).thenReturn(
			"Group Test 1"
		);

		Role role1 = mock(Role.class);

		when(
			role1.getName()
		).thenReturn(
			"Role Test 1"
		);

		Role role2 = mock(Role.class);

		when(
			role2.getName()
		).thenReturn(
			"Role Test 2"
		);

		Role role3 = mock(Role.class);

		when(
			role3.getName()
		).thenReturn(
			"Org Role Test"
		);

		when(
			role3.getType()
		).thenReturn(
			RoleConstants.TYPE_ORGANIZATION
		);

		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		UserGroupRole userGroupRole1 = mock(UserGroupRole.class);

		when(
			userGroupRole1.getGroup()
		).thenReturn(
			group1
		);

		when(
			userGroupRole1.getRole()
		).thenReturn(
			role1
		);

		userGroupRoles.add(userGroupRole1);

		UserGroupRole userGroupRole2 = mock(UserGroupRole.class);

		when(
			userGroupRole2.getGroup()
		).thenReturn(
			group1
		);

		when(
			userGroupRole2.getRole()
		).thenReturn(
			role2
		);

		userGroupRoles.add(userGroupRole2);

		UserGroupRole userGroupRole3 = mock(UserGroupRole.class);

		when(
			userGroupRole3.getGroup()
		).thenReturn(
			group1
		);

		when(
			userGroupRole3.getRole()
		).thenReturn(
			role3
		);

		userGroupRoles.add(userGroupRole3);

		when(
			_userGroupRoleLocalService.getUserGroupRoles(Mockito.anyLong())
		).thenReturn(
			userGroupRoles
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		assertEquals(
			attributes, "userGroupRole:Group Test 1",
			new String[] {"Role Test 1", "Role Test 2", "Org Role Test"});
	}

	@Test
	public void testResolveUserGroupsAttributes() throws Exception {
		when(
			_metadataManager.getAttributeNames(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			new String[] {"userGroups"}
		);

		List<UserGroup> userGroups = new ArrayList<>();

		UserGroup userGroup1 = mock(UserGroup.class);

		when(
			userGroup1.getName()
		).thenReturn(
			"Test 1"
		);

		userGroups.add(userGroup1);

		UserGroup userGroup2 = mock(UserGroup.class);

		when(
			userGroup2.getName()
		).thenReturn(
			"Test 2"
		);

		userGroups.add(userGroup2);

		when(
			_user.getUserGroups()
		).thenReturn(
			userGroups
		);

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		_defaultAttributeResolver.resolve(
			_user, new AttributeResolverSAMLContextImpl(_samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		assertEquals(
			attributes, "userGroups", new String[] {"Test 1", "Test 2"});
	}

	protected void assertEquals(
		List<Attribute> attributes, String attributeName,
		String attributeValue) {

		Attribute attribute = SamlUtil.getAttribute(attributes, attributeName);

		Assert.assertEquals(
			attributeValue, SamlUtil.getValueAsString(attribute));
	}

	protected void assertEquals(
		List<Attribute> attributes, String attributeName,
		String[] attributeValues) {

		Attribute attribute = SamlUtil.getAttribute(attributes, attributeName);

		Assert.assertNotNull(attribute);

		List<XMLObject> xmlObjects = attribute.getAttributeValues();

		Assert.assertEquals(
			xmlObjects.toString(), attributeValues.length, xmlObjects.size());

		for (XMLObject xmlObject : xmlObjects) {
			String attributeValue = SamlUtil.getValueAsString(xmlObject);

			Assert.assertTrue(
				ArrayUtil.contains(attributeValues, attributeValue));
		}
	}

	private BeanProperties _beanProperties;
	private final DefaultAttributeResolver _defaultAttributeResolver =
		new DefaultAttributeResolver();
	private ExpandoBridge _expandoBridge;
	private MetadataManager _metadataManager;
	private RoleLocalService _roleLocalService;
	private SAMLMessageContext<AuthnRequest, Response, NameID>
		_samlMessageContext;
	private User _user;
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}