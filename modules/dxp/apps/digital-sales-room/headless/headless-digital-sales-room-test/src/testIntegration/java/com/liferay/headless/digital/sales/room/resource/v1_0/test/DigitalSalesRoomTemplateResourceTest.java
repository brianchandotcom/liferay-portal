/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.digital.sales.room.test.util.DigitalSalesRoomTestUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntryModel;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoomTemplate;
import com.liferay.headless.digital.sales.room.client.pagination.Page;
import com.liferay.headless.digital.sales.room.client.pagination.Pagination;
import com.liferay.headless.digital.sales.room.client.resource.v1_0.DigitalSalesRoomResource;
import com.liferay.headless.digital.sales.room.client.resource.v1_0.DigitalSalesRoomTemplateResource;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;

import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-66359")
@RunWith(Arquillian.class)
public class DigitalSalesRoomTemplateResourceTest
	extends BaseDigitalSalesRoomTemplateResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dsrRoomObjectDefinition = DigitalSalesRoomTestUtil.getObjectDefinition(
			DigitalSalesRoomResourceTest.class);
		_dsrTemplateObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DSR_TEMPLATE", TestPropsValues.getCompanyId());
	}

	@Override
	@Test
	public void testDeleteDigitalSalesRoomTemplate() throws Exception {
		super.testDeleteDigitalSalesRoomTemplate();

		_testDeleteDigitalSalesRoomTemplateWithPermission();
	}

	@Ignore
	@Override
	@Test
	public void testGetDigitalSalesRoomDigitalSalesRoomTemplatesPage()
		throws Exception {

		super.testGetDigitalSalesRoomDigitalSalesRoomTemplatesPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetDigitalSalesRoomTemplateDigitalSalesRoomTemplatesPage()
		throws Exception {

		super.testGetDigitalSalesRoomTemplateDigitalSalesRoomTemplatesPage();
	}

	@Override
	@Test
	public void testGetDigitalSalesRoomTemplatesPage() throws Exception {
		super.testGetDigitalSalesRoomTemplatesPage();

		_testGetDigitalSalesRoomTemplatesPageWithPermission();
	}

	@Override
	@Test
	public void testPatchDigitalSalesRoomTemplate() throws Exception {
		super.testPatchDigitalSalesRoomTemplate();

		_testPatchDigitalSalesRoomTemplateWithPermission();
	}

	@Override
	@Test
	public void testPostDigitalSalesRoomDigitalSalesRoomTemplate()
		throws Exception {

		super.testPostDigitalSalesRoomDigitalSalesRoomTemplate();

		_testPostDigitalSalesRoomDigitalSalesRoomTemplate();
		_testPostDigitalSalesRoomDigitalSalesRoomTemplateWithPermission();
	}

	@Override
	@Test
	public void testPostDigitalSalesRoomTemplate() throws Exception {
		super.testPostDigitalSalesRoomTemplate();

		_testPostDigitalSalesRoomTemplateWithPermission();
	}

	@Override
	@Test
	public void testPostDigitalSalesRoomTemplateDigitalSalesRoomTemplate()
		throws Exception {

		super.testPostDigitalSalesRoomTemplateDigitalSalesRoomTemplate();

		_testPostDigitalSalesRoomTemplateDigitalSalesRoomTemplate();
		_testPostDigitalSalesRoomTemplateDigitalSalesRoomTemplateWithPermission();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"clientName", "description", "name", "primaryColor",
			"secondaryColor"
		};
	}

	@Override
	protected DigitalSalesRoomTemplate randomDigitalSalesRoomTemplate()
		throws Exception {

		return new DigitalSalesRoomTemplate() {
			{
				clientName = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				primaryColor = RandomTestUtil.randomString();
				secondaryColor = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected DigitalSalesRoomTemplate
			testDeleteDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate()
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			randomDigitalSalesRoomTemplate());
	}

	@Override
	protected DigitalSalesRoomTemplate
			testGetDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate()
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			randomDigitalSalesRoomTemplate());
	}

	@Override
	protected DigitalSalesRoomTemplate
			testGetDigitalSalesRoomTemplatesPage_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			digitalSalesRoomTemplate);
	}

	@Override
	protected DigitalSalesRoomTemplate
			testPatchDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate()
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			randomDigitalSalesRoomTemplate());
	}

	@Override
	protected DigitalSalesRoomTemplate
			testPostDigitalSalesRoomDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			digitalSalesRoomTemplate);
	}

	@Override
	protected DigitalSalesRoomTemplate
			testPostDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			digitalSalesRoomTemplate);
	}

	@Override
	protected DigitalSalesRoomTemplate
			testPostDigitalSalesRoomTemplateDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		return digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
			digitalSalesRoomTemplate);
	}

	private void _addUserRole(String[] actionIds, long userId)
		throws Exception {

		Role role = _roleLocalService.addRole(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), null, 0,
			RandomTestUtil.randomString(), null, null,
			RoleConstants.TYPE_REGULAR, null, new ServiceContext());

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ActionKeys.VIEW);

		for (String actionId : actionIds) {
			String name = _dsrTemplateObjectDefinition.getClassName();

			if (Objects.equals(actionId, ObjectActionKeys.ADD_OBJECT_ENTRY)) {
				name = _dsrTemplateObjectDefinition.getResourceName();
			}

			_resourcePermissionLocalService.addResourcePermission(
				TestPropsValues.getCompanyId(), name,
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()),
				role.getRoleId(), actionId);
		}

		_roleLocalService.addUserRole(userId, role.getRoleId());
	}

	private void _testDeleteDigitalSalesRoomTemplateWithPermission()
		throws Exception {

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), "test",
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		DigitalSalesRoomTemplate digitalSalesRoomTemplate =
			digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate());

		DigitalSalesRoomTemplateResource digitalSalesRoomTemplateResource =
			DigitalSalesRoomTemplateResource.builder(
			).authentication(
				user.getEmailAddress(), "test"
			).build();

		assertHttpResponseStatusCode(
			403,
			digitalSalesRoomTemplateResource.
				deleteDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate.getId()));

		_addUserRole(new String[] {ActionKeys.DELETE}, user.getUserId());

		assertHttpResponseStatusCode(
			204,
			digitalSalesRoomTemplateResource.
				deleteDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate.getId()));
	}

	private void _testGetDigitalSalesRoomTemplatesPageWithPermission()
		throws Exception {

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), "test",
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		DigitalSalesRoomTemplateResource digitalSalesRoomTemplateResource =
			DigitalSalesRoomTemplateResource.builder(
			).authentication(
				user.getEmailAddress(), "test"
			).build();

		Page<DigitalSalesRoomTemplate> digitalSalesRoomsPage =
			digitalSalesRoomTemplateResource.getDigitalSalesRoomTemplatesPage(
				null, Pagination.of(1, 10));

		Map<String, Map<String, String>> actions =
			digitalSalesRoomsPage.getActions();

		Assert.assertTrue(actions.isEmpty());

		_addUserRole(
			new String[] {ObjectActionKeys.ADD_OBJECT_ENTRY}, user.getUserId());

		digitalSalesRoomsPage =
			digitalSalesRoomTemplateResource.getDigitalSalesRoomTemplatesPage(
				null, Pagination.of(1, 10));

		actions = digitalSalesRoomsPage.getActions();

		Assert.assertNotNull(actions.get("create"));
	}

	private void _testPatchDigitalSalesRoomTemplateWithPermission()
		throws Exception {

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), "test",
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		DigitalSalesRoomTemplate digitalSalesRoomTemplate =
			digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate());

		DigitalSalesRoomTemplateResource digitalSalesRoomTemplateResource =
			DigitalSalesRoomTemplateResource.builder(
			).authentication(
				user.getEmailAddress(), "test"
			).build();

		assertHttpResponseStatusCode(
			403,
			digitalSalesRoomTemplateResource.
				patchDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate.getId(),
					digitalSalesRoomTemplate));

		_addUserRole(
			new String[] {
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW
			},
			user.getUserId());

		assertHttpResponseStatusCode(
			200,
			digitalSalesRoomTemplateResource.
				patchDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate.getId(),
					digitalSalesRoomTemplate));
	}

	private void _testPostDigitalSalesRoomDigitalSalesRoomTemplate()
		throws Exception {

		User user = UserTestUtil.getAdminUser(
			_dsrRoomObjectDefinition.getCompanyId());

		DigitalSalesRoomResource digitalSalesRoomResource =
			DigitalSalesRoomResource.builder(
			).authentication(
				user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
			).build();

		DigitalSalesRoom digitalSalesRoom =
			digitalSalesRoomResource.postDigitalSalesRoom(
				new DigitalSalesRoom() {
					{
						accountId = 0L;
						channelId = 0L;
						channelName = RandomTestUtil.randomString();
						clientName = RandomTestUtil.randomString();
						description = RandomTestUtil.randomString();
						externalReferenceCode = RandomTestUtil.randomString();
						friendlyUrlPath = StringUtil.toLowerCase(
							StringPool.SLASH + RandomTestUtil.randomString());
						name = RandomTestUtil.randomString();
						primaryColor = RandomTestUtil.randomString();
						secondaryColor = RandomTestUtil.randomString();
					}
				});

		DigitalSalesRoomTemplate randomDigitalSalesRoomTemplate =
			randomDigitalSalesRoomTemplate();

		DigitalSalesRoomTemplate digitalSalesRoomTemplate =
			digitalSalesRoomTemplateResource.
				postDigitalSalesRoomDigitalSalesRoomTemplate(
					digitalSalesRoom.getId(), randomDigitalSalesRoomTemplate);

		Assert.assertEquals(
			digitalSalesRoom.getClientName(),
			digitalSalesRoomTemplate.getClientName());
		Assert.assertEquals(
			randomDigitalSalesRoomTemplate.getDescription(),
			digitalSalesRoomTemplate.getDescription());
		Assert.assertEquals(
			randomDigitalSalesRoomTemplate.getName() + " (Template)",
			digitalSalesRoomTemplate.getName());
		Assert.assertEquals(
			digitalSalesRoom.getPrimaryColor(),
			digitalSalesRoomTemplate.getPrimaryColor());
		Assert.assertEquals(
			digitalSalesRoom.getSecondaryColor(),
			digitalSalesRoomTemplate.getSecondaryColor());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				digitalSalesRoomTemplate.getId(), "digital-sales-room");

		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					_fragmentEntryLocalService.getFragmentEntries(
						digitalSalesRoomTemplate.getId(),
						fragmentCollection.getFragmentCollectionId(), 0),
					FragmentEntryModel::getName, String.class),
				new String[] {
					"Gallery Block", "Header Main", "Header User",
					"Our Team Block", "PDF Preview Block",
					"Question and Answer Block", "Text Block", "Timeline Block",
					"Video Block", "Welcome Block"
				}));

		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					_layoutLocalService.getLayouts(
						digitalSalesRoomTemplate.getId(), false),
					layout -> layout.getName(LocaleUtil.getSiteDefault()),
					String.class),
				new String[] {"Documents", "Onboarding"}));
	}

	private void _testPostDigitalSalesRoomDigitalSalesRoomTemplateWithPermission()
		throws Exception {

		User user1 = UserTestUtil.getAdminUser(
			_dsrRoomObjectDefinition.getCompanyId());

		DigitalSalesRoomResource digitalSalesRoomResource =
			DigitalSalesRoomResource.builder(
			).authentication(
				user1.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
			).build();

		DigitalSalesRoom digitalSalesRoom =
			digitalSalesRoomResource.postDigitalSalesRoom(
				new DigitalSalesRoom() {
					{
						accountId = 0L;
						channelId = 0L;
						channelName = RandomTestUtil.randomString();
						clientName = RandomTestUtil.randomString();
						description = RandomTestUtil.randomString();
						externalReferenceCode = RandomTestUtil.randomString();
						friendlyUrlPath = StringUtil.toLowerCase(
							StringPool.SLASH + RandomTestUtil.randomString());
						name = RandomTestUtil.randomString();
						primaryColor = RandomTestUtil.randomString();
						secondaryColor = RandomTestUtil.randomString();
					}
				});

		User user2 = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), "test",
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		DigitalSalesRoomTemplateResource digitalSalesRoomTemplateResource =
			DigitalSalesRoomTemplateResource.builder(
			).authentication(
				user2.getEmailAddress(), "test"
			).build();

		DigitalSalesRoomTemplate randomDigitalSalesRoomTemplate =
			randomDigitalSalesRoomTemplate();

		assertHttpResponseStatusCode(
			403,
			digitalSalesRoomTemplateResource.
				postDigitalSalesRoomDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoom.getId(), randomDigitalSalesRoomTemplate));

		_addUserRole(
			new String[] {ObjectActionKeys.ADD_OBJECT_ENTRY},
			user2.getUserId());

		assertHttpResponseStatusCode(
			200,
			digitalSalesRoomTemplateResource.
				postDigitalSalesRoomDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoom.getId(), randomDigitalSalesRoomTemplate));
	}

	private void _testPostDigitalSalesRoomTemplateDigitalSalesRoomTemplate()
		throws Exception {

		DigitalSalesRoomTemplate digitalSalesRoomTemplate1 =
			digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate());

		Layout layout1 = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(),
			digitalSalesRoomTemplate1.getId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			"T" + RandomTestUtil.randomString(), StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_NODE, false,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));
		Layout layout2 = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(),
			digitalSalesRoomTemplate1.getId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			"Z" + RandomTestUtil.randomString(), StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_NODE, false,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		DigitalSalesRoomTemplate digitalSalesRoomTemplate2 =
			digitalSalesRoomTemplateResource.
				postDigitalSalesRoomTemplateDigitalSalesRoomTemplate(
					digitalSalesRoomTemplate1.getId());

		Assert.assertEquals(
			digitalSalesRoomTemplate1.getClientName(),
			digitalSalesRoomTemplate2.getClientName());
		Assert.assertEquals(
			digitalSalesRoomTemplate1.getDescription(),
			digitalSalesRoomTemplate2.getDescription());
		Assert.assertEquals(
			digitalSalesRoomTemplate1.getName() + " (Copy)",
			digitalSalesRoomTemplate2.getName());
		Assert.assertEquals(
			digitalSalesRoomTemplate1.getPrimaryColor(),
			digitalSalesRoomTemplate2.getPrimaryColor());
		Assert.assertEquals(
			digitalSalesRoomTemplate1.getSecondaryColor(),
			digitalSalesRoomTemplate2.getSecondaryColor());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				digitalSalesRoomTemplate2.getId(), "digital-sales-room");

		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					_fragmentEntryLocalService.getFragmentEntries(
						digitalSalesRoomTemplate2.getId(),
						fragmentCollection.getFragmentCollectionId(), 0),
					FragmentEntryModel::getName, String.class),
				new String[] {
					"Gallery Block", "Header Main", "Header User",
					"Our Team Block", "PDF Preview Block",
					"Question and Answer Block", "Text Block", "Timeline Block",
					"Video Block", "Welcome Block"
				}));

		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					_layoutLocalService.getLayouts(
						digitalSalesRoomTemplate2.getId(), false),
					layout -> layout.getName(LocaleUtil.getSiteDefault()),
					String.class),
				new String[] {
					"Documents", "Onboarding",
					layout1.getName(LocaleUtil.getSiteDefault()),
					layout2.getName(LocaleUtil.getSiteDefault())
				}));
	}

	private void _testPostDigitalSalesRoomTemplateDigitalSalesRoomTemplateWithPermission()
		throws Exception {

		DigitalSalesRoomTemplate digitalSalesRoomTemplate1 =
			digitalSalesRoomTemplateResource.postDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate());

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), "test",
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		DigitalSalesRoomTemplateResource digitalSalesRoomTemplateResource =
			DigitalSalesRoomTemplateResource.builder(
			).authentication(
				user.getEmailAddress(), "test"
			).build();

		assertHttpResponseStatusCode(
			403,
			digitalSalesRoomTemplateResource.
				postDigitalSalesRoomTemplateDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate1.getId()));

		_addUserRole(
			new String[] {ObjectActionKeys.ADD_OBJECT_ENTRY}, user.getUserId());

		assertHttpResponseStatusCode(
			200,
			digitalSalesRoomTemplateResource.
				postDigitalSalesRoomTemplateDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate1.getId()));
	}

	private void _testPostDigitalSalesRoomTemplateWithPermission()
		throws Exception {

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), "test",
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		DigitalSalesRoomTemplateResource digitalSalesRoomTemplateResource =
			DigitalSalesRoomTemplateResource.builder(
			).authentication(
				user.getEmailAddress(), "test"
			).build();

		DigitalSalesRoomTemplate digitalSalesRoomTemplate =
			randomDigitalSalesRoomTemplate();

		assertHttpResponseStatusCode(
			403,
			digitalSalesRoomTemplateResource.
				postDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate));

		_addUserRole(
			new String[] {ObjectActionKeys.ADD_OBJECT_ENTRY}, user.getUserId());

		assertHttpResponseStatusCode(
			200,
			digitalSalesRoomTemplateResource.
				postDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate));
	}

	private ObjectDefinition _dsrRoomObjectDefinition;
	private ObjectDefinition _dsrTemplateObjectDefinition;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}