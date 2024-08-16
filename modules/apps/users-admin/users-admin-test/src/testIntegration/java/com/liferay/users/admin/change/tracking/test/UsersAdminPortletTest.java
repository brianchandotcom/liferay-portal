/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.change.tracking.test;

import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.announcements.kernel.service.AnnouncementsDeliveryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayActionRequest;
import com.liferay.portal.kernel.portlet.LiferayStateAwareResponse;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.Event;
import javax.portlet.Portlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class UsersAdminPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, "P1", null);

		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());
	}

	@Test
	public void testNoCTEntryForAddedEditUserMVCAction() throws Exception {
		String addUserScreenName = RandomTestUtil.randomString();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/edit_user",
			HashMapBuilder.put(
				Constants.CMD, Constants.ADD
			).put(
				"birthdayDay", String.valueOf(1)
			).put(
				"birthdayMonth", String.valueOf(Calendar.JANUARY)
			).put(
				"birthdayYear", String.valueOf(1970)
			).put(
				"emailAddress", RandomTestUtil.randomString() + "@liferay.com"
			).put(
				"firstName", RandomTestUtil.randomString()
			).put(
				"lastName", RandomTestUtil.randomString()
			).put(
				"screenName", addUserScreenName
			).build());

		_assertNoCTEntry();

		User addUser = _userLocalService.fetchUserByScreenName(
			TestPropsValues.getCompanyId(), addUserScreenName);

		Assert.assertEquals(
			addUser.getScreenName(), StringUtil.toLowerCase(addUserScreenName));
	}

	@Test
	public void testNoCTEntryForDeletedEditUserMVCAction() throws Exception {
		User user = UserTestUtil.addUser();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/edit_user",
			HashMapBuilder.put(
				Constants.CMD, Constants.DELETE
			).put(
				"deleteUserIds", String.valueOf(user.getUserId())
			).build());

		_assertNoCTEntry();

		Assert.assertNull(_userLocalService.fetchUser(user.getUserId()));
	}

	@Test
	public void testNoCTEntryForDeletedRoleEditUserMVCAction()
		throws Exception {

		User user = UserTestUtil.addUser();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_userLocalService.addRoleUser(role.getRoleId(), user.getUserId());

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/edit_user",
			HashMapBuilder.put(
				Constants.CMD, "deleteRole"
			).put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).put(
				"roleId", String.valueOf(role.getRoleId())
			).build());

		_assertNoCTEntry();

		Assert.assertEquals(
			0,
			_userLocalService.getRoleUsers(
				role.getRoleId()
			).size());
	}

	@Test(expected = NoSuchOrganizationException.class)
	public void testNoCTEntryForDeleteOrganizationsMVCActionCommand()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/delete_organizations_and_users",
			HashMapBuilder.put(
				"deleteOrganizationIds",
				String.valueOf(organization.getOrganizationId())
			).build());

		_assertNoCTEntry();

		Assert.assertNull(
			_organizationLocalService.getOrganization(
				organization.getOrganizationId()));
	}

	@Test(expected = NoSuchUserException.class)
	public void testNoCTEntryForDeleteUsersMVCActionCommand() throws Exception {
		User user = UserTestUtil.addUser();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/delete_organizations_and_users",
			HashMapBuilder.put(
				"deleteUserIds", String.valueOf(user.getUserId())
			).build());

		_assertNoCTEntry();

		Assert.assertNull(_userLocalService.getUser(user.getUserId()));
	}

	@Test
	public void testNoCTEntryForEditDisplaySettingsMVCActionCommand()
		throws Exception {

		User user = UserTestUtil.addUser();

		String greeting = RandomTestUtil.randomString();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/edit_display_settings",
			HashMapBuilder.put(
				"greeting", greeting
			).put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).build());

		_assertNoCTEntry();

		user = _userLocalService.fetchUser(user.getUserId());

		Assert.assertEquals(greeting, user.getGreeting());
	}

	@Test
	public void testNoCTEntryForEditOrganizationAssignmentsMVCActionCommand()
		throws Exception {

		User user = UserTestUtil.addUser();

		User organizationUser = UserTestUtil.addUser();

		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_userLocalService.addOrganizationUser(
			organization.getOrganizationId(), organizationUser);

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/edit_organization_assignments",
			HashMapBuilder.put(
				"addUserIds", String.valueOf(user.getUserId())
			).put(
				"organizationId",
				String.valueOf(organization.getOrganizationId())
			).put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).put(
				"removeOrganizationIds",
				String.valueOf(suborganization.getOrganizationId())
			).put(
				"removeUserIds", String.valueOf(organizationUser.getUserId())
			).build());

		_assertNoCTEntry();

		List<User> organizationUsers = _userLocalService.getOrganizationUsers(
			organization.getOrganizationId());

		Assert.assertTrue(organizationUsers.contains(user));

		Assert.assertFalse(organizationUsers.contains(organizationUser));

		List<Organization> parentOrganizations =
			_organizationLocalService.getParentOrganizations(
				suborganization.getOrganizationId());

		Assert.assertFalse(parentOrganizations.contains(organization));
	}

	@Test
	public void testNoCTEntryForEditOrganizationMVCActionCommand()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		ListType listType = ListTypeServiceUtil.getListType(
			TestPropsValues.getCompanyId(),
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
			ListTypeConstants.ORGANIZATION_STATUS);

		String newOrganizationName = RandomTestUtil.randomString();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/edit_organization",
			HashMapBuilder.put(
				Constants.CMD, Constants.UPDATE
			).put(
				"name", newOrganizationName
			).put(
				"organizationId",
				String.valueOf(organization.getOrganizationId())
			).put(
				"statusId", String.valueOf(listType.getListTypeId())
			).put(
				"type", organization.getType()
			).build());

		organization = _organizationLocalService.fetchOrganization(
			organization.getOrganizationId());

		Assert.assertEquals(newOrganizationName, organization.getName());

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/edit_organization",
			HashMapBuilder.put(
				Constants.CMD, Constants.DELETE
			).put(
				"deleteOrganizationIds",
				String.valueOf(organization.getOrganizationId())
			).build());

		_assertNoCTEntry();

		Assert.assertNull(
			_organizationLocalService.fetchOrganization(
				organization.getOrganizationId()));
	}

	@Test
	public void testNoCTEntryForEditUserMVCAction() throws Exception {
		User user = UserTestUtil.addUser();

		String newFirstName = RandomTestUtil.randomString();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/edit_user",
			HashMapBuilder.put(
				Constants.CMD, Constants.UPDATE
			).put(
				"firstName", newFirstName
			).put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).build());

		_assertNoCTEntry();

		user = _userLocalService.getUser(user.getUserId());

		Assert.assertEquals(newFirstName, user.getFirstName());
	}

	@Test
	public void testNoCTEntryForEditUserOrganizationMVCAction()
		throws Exception {

		User user = UserTestUtil.addUser();

		Organization organization = OrganizationTestUtil.addOrganization();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/edit_user_organizations",
			HashMapBuilder.put(
				"addOrganizationIds",
				String.valueOf(organization.getOrganizationId())
			).put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).build());

		_assertNoCTEntry();

		Assert.assertTrue(
			_userLocalService.hasOrganizationUser(
				organization.getOrganizationId(), user.getUserId()));
	}

	@Test
	public void testNoCTEntryForOrganizationUpdateContactInformationMVCActionCommand()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Address address = OrganizationTestUtil.addAddress(organization);

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_contact_information",
			HashMapBuilder.put(
				Constants.CMD, "makePrimary"
			).put(
				"className", Organization.class.getName()
			).put(
				"classPK", String.valueOf(organization.getOrganizationId())
			).put(
				"listType", ListTypeConstants.ADDRESS
			).put(
				"primaryKey", String.valueOf(address.getPrimaryKey())
			).build());

		address = organization.getAddresses(
		).get(
			0
		);

		Assert.assertTrue(address.isPrimary());

		String newAddressCity = RandomTestUtil.randomString();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_contact_information",
			HashMapBuilder.put(
				Constants.CMD, Constants.EDIT
			).put(
				"addressCity", newAddressCity
			).put(
				"addressListTypeId",
				String.valueOf(
					_listTypeLocalService.getListTypeId(
						organization.getCompanyId(), "billing",
						ListTypeConstants.ORGANIZATION_ADDRESS))
			).put(
				"addressStreet1", RandomTestUtil.randomString()
			).put(
				"addressZip", RandomTestUtil.randomString()
			).put(
				"className", Organization.class.getName()
			).put(
				"classPK", String.valueOf(organization.getOrganizationId())
			).put(
				"listType", ListTypeConstants.ADDRESS
			).put(
				"primaryKey", String.valueOf(address.getPrimaryKey())
			).build());

		address = organization.getAddresses(
		).get(
			0
		);

		Assert.assertEquals(newAddressCity, address.getCity());

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_contact_information",
			HashMapBuilder.put(
				Constants.CMD, Constants.DELETE
			).put(
				"className", Organization.class.getName()
			).put(
				"classPK", String.valueOf(organization.getOrganizationId())
			).put(
				"listType", ListTypeConstants.ADDRESS
			).put(
				"primaryKey", String.valueOf(address.getPrimaryKey())
			).build());

		_assertNoCTEntry();

		Assert.assertEquals(
			0,
			organization.getAddresses(
			).size());
	}

	@Test
	public void testNoCTEntryForUpdateAnnouncementsDeliveriesMVCActionCommand()
		throws Exception {

		User user = UserTestUtil.addUser();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_announcements_deliveries",
			HashMapBuilder.put(
				"announcementsTypegeneralEmail", Boolean.TRUE.toString()
			).put(
				"email", Boolean.TRUE.toString()
			).put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).build());

		_assertNoCTEntry();

		List<AnnouncementsDelivery> announcementsDeliveries =
			_announcementsDeliveryLocalService.getUserDeliveries(
				user.getUserId());

		for (AnnouncementsDelivery delivery : announcementsDeliveries) {
			if (Objects.equals(delivery.getType(), "general")) {
				Assert.assertTrue(delivery.isEmail());
			}
		}
	}

	@Test
	public void testNoCTEntryForUpdateMembershipsMVCActionCommand()
		throws Exception {

		User user = UserTestUtil.addUser();

		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_memberships",
			HashMapBuilder.put(
				"addGroupIds", String.valueOf(TestPropsValues.getGroupId())
			).put(
				"addUserGroupIds", String.valueOf(userGroup.getUserGroupId())
			).put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).build());

		_assertNoCTEntry();

		Assert.assertTrue(
			_userLocalService.hasGroupUser(
				TestPropsValues.getGroupId(), user.getUserId()));

		Assert.assertTrue(
			_userLocalService.hasUserGroupUser(
				userGroup.getUserGroupId(), user.getUserId()));
	}

	@Test
	public void testNoCTEntryForUpdateOrganizationOrganizationSiteMVCActionCommand()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_organization_organization_site",
			HashMapBuilder.put(
				"organizationId",
				String.valueOf(organization.getOrganizationId())
			).put(
				"site", Boolean.TRUE.toString()
			).build());

		_assertNoCTEntry();

		Group organizationGroup = organization.getGroup();

		Assert.assertTrue(organizationGroup.isSite());
	}

	@Test
	public void testNoCTEntryForUpdatePasswordMVCActionCommand()
		throws Exception {

		User user = UserTestUtil.addUser();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_password",
			HashMapBuilder.put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).put(
				"passwordReset", Boolean.TRUE.toString()
			).build());

		_assertNoCTEntry();

		user = _userLocalService.fetchUser(user.getUserId());

		Assert.assertTrue(user.isPasswordReset());
	}

	@Test
	public void testNoCTEntryForUpdateUserContactInformationFormMVCActionCommand()
		throws Exception {

		User user = UserTestUtil.addUser();

		String facebookSn = RandomTestUtil.randomString();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_user_contact_information_form",
			HashMapBuilder.put(
				"facebookSn", facebookSn
			).put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).build());

		_assertNoCTEntry();

		user = _userLocalService.getUser(user.getUserId());

		Contact contact = user.getContact();

		Assert.assertEquals(
			StringUtil.toLowerCase(facebookSn),
			StringUtil.toLowerCase(contact.getFacebookSn()));
	}

	@Test
	public void testNoCTEntryForUpdateUserRolesMVCActionCommand()
		throws Exception {

		User user = UserTestUtil.addUser();

		Role role = RoleTestUtil.addRole(
			RandomTestUtil.randomString(), RoleConstants.TYPE_REGULAR);

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_user_roles",
			HashMapBuilder.put(
				"addRoleIds", String.valueOf(role.getRoleId())
			).put(
				"p_u_i_d", String.valueOf(user.getUserId())
			).build());

		_assertNoCTEntry();

		Assert.assertTrue(
			_userLocalService.hasRoleUser(role.getRoleId(), user.getUserId()));
	}

	@Test
	public void testNoCTEntryForUserUpdateContactInformationMVCActionCommand()
		throws Exception {

		User user = UserTestUtil.addUser();

		Contact contact = user.getContact();

		long listTypeId = _listTypeLocalService.getListTypeId(
			TestPropsValues.getCompanyId(), "personal",
			ListTypeConstants.CONTACT_ADDRESS);

		Address address = _addressLocalService.addAddress(
			null, user.getUserId(), Contact.class.getName(),
			user.getContactId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, RandomTestUtil.randomString(), null, 0, 0, listTypeId, false,
			false, null, ServiceContextTestUtil.getServiceContext());

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_contact_information",
			HashMapBuilder.put(
				Constants.CMD, "makePrimary"
			).put(
				"className", contact.getClassName()
			).put(
				"classPK", String.valueOf(contact.getContactId())
			).put(
				"listType", ListTypeConstants.ADDRESS
			).put(
				"primaryKey", String.valueOf(address.getPrimaryKey())
			).build());

		address = user.getAddresses(
		).get(
			0
		);

		Assert.assertTrue(address.isPrimary());

		String newAddressCity = RandomTestUtil.randomString();

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_contact_information",
			HashMapBuilder.put(
				Constants.CMD, Constants.EDIT
			).put(
				"addressCity", newAddressCity
			).put(
				"addressListTypeId", String.valueOf(listTypeId)
			).put(
				"addressStreet1", RandomTestUtil.randomString()
			).put(
				"addressZip", RandomTestUtil.randomString()
			).put(
				"className", contact.getClassName()
			).put(
				"classPK", String.valueOf(contact.getContactId())
			).put(
				"listType", ListTypeConstants.ADDRESS
			).put(
				"primaryKey", String.valueOf(address.getPrimaryKey())
			).build());

		address = user.getAddresses(
		).get(
			0
		);

		Assert.assertEquals(newAddressCity, address.getCity());

		_processActionRequestInPublication(
			_ctCollection, "/users_admin/update_contact_information",
			HashMapBuilder.put(
				Constants.CMD, Constants.DELETE
			).put(
				"className", contact.getClassName()
			).put(
				"classPK", String.valueOf(contact.getContactId())
			).put(
				"listType", ListTypeConstants.ADDRESS
			).put(
				"primaryKey", String.valueOf(address.getPrimaryKey())
			).build());

		_assertNoCTEntry();

		Assert.assertEquals(
			0,
			user.getAddresses(
			).size());
	}

	private void _assertNoCTEntry() throws Exception {
		Assert.assertEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				_ctCollection.getCtCollectionId()));
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private void _processActionRequestInPublication(
			CTCollection ctCollection, String actionName,
			Map<String, String> params)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			mockLiferayPortletActionRequest.getHttpServletRequest());
		mockLiferayPortletActionRequest.setAttribute(WebKeys.LAYOUT, _layout);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.PORTLET_ID, UsersAdminPortletKeys.USERS_ADMIN);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletActionRequest.addParameter(
			ActionRequest.ACTION_NAME, actionName);

		for (Map.Entry<String, String> entry : params.entrySet()) {
			mockLiferayPortletActionRequest.addParameter(
				entry.getKey(), entry.getValue());
		}

		LiferayActionRequest liferayActionRequest = ActionRequestFactory.create(
			mockLiferayPortletActionRequest.getHttpServletRequest(),
			_portletLocalService.getPortletById(
				UsersAdminPortletKeys.USERS_ADMIN),
			null, null, null, null, null, TestPropsValues.getPlid());

		liferayActionRequest.setPortletRequestDispatcherRequest(
			mockLiferayPortletActionRequest.getHttpServletRequest());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			_portlet.processAction(
				liferayActionRequest,
				new CustomMockLiferayPortletActionResponse());
		}
	}

	@Inject
	private static AddressLocalService _addressLocalService;

	private static CTCollection _ctCollection;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private static ListTypeLocalService _listTypeLocalService;

	@Inject
	private AnnouncementsDeliveryLocalService
		_announcementsDeliveryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject(
		filter = "component.name=com.liferay.users.admin.web.internal.portlet.UsersAdminPortlet"
	)
	private Portlet _portlet;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private UserLocalService _userLocalService;

	private class CustomMockLiferayPortletActionResponse
		extends MockLiferayPortletActionResponse
		implements LiferayStateAwareResponse {

		@Override
		public List<Event> getEvents() {
			return Collections.emptyList();
		}

		@Override
		public String getRedirectLocation() {
			return StringPool.BLANK;
		}

		@Override
		public boolean isCalledSetRenderParameter() {
			return false;
		}

	}

}