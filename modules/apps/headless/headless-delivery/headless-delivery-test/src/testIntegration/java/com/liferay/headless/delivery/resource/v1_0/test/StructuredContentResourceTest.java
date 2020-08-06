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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.headless.delivery.client.dto.v1_0.ContentField;
import com.liferay.headless.delivery.client.dto.v1_0.ContentFieldValue;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.io.InputStream;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class StructuredContentResourceTest
	extends BaseStructuredContentResourceTestCase {

	@ClassRule
	@Rule
	public static final SynchronousMailTestRule synchronousMailTestRule =
		SynchronousMailTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		testGroup = testDepotEntry.getGroup();

		_ddmLocalizedStructure = _addDDMStructure(
			testGroup, "test-localized-structured-content-structure.json");
		_ddmStructure = _addDDMStructure(
			testGroup, "test-structured-content-structure.json");

		_irrelevantDDMStructure = _addDDMStructure(
			irrelevantGroup, "test-structured-content-structure.json");

		_ddmTemplate = _addDDMTemplate(_ddmStructure);
		_addDDMTemplate(_irrelevantDDMStructure);

		_journalFolder = JournalTestUtil.addFolder(
			testGroup.getGroupId(), RandomTestUtil.randomString());
		_irrelevantJournalFolder = JournalTestUtil.addFolder(
			irrelevantGroup.getGroupId(), RandomTestUtil.randomString());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetSiteStructuredContentWithDifferentLocale()
		throws Exception {

		StructuredContent structuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		String title = structuredContent.getTitle();

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		StructuredContentResource frenchStructuredContentResource =
			builder.authentication(
				"test@liferay.com", "test"
			).locale(
				LocaleUtil.FRANCE
			).build();

		String frenchTitle = RandomTestUtil.randomString();

		structuredContent.setTitle(frenchTitle);

		frenchStructuredContentResource.putStructuredContent(
			structuredContent.getId(), structuredContent);

		structuredContent =
			frenchStructuredContentResource.getStructuredContent(
				structuredContent.getId());

		Assert.assertEquals(frenchTitle, structuredContent.getTitle());

		structuredContent = structuredContentResource.getStructuredContent(
			structuredContent.getId());

		Assert.assertEquals(title, structuredContent.getTitle());
	}

	@Override
	@Test
	public void testGetStructuredContent() throws Exception {

		// Get structured content

		super.testGetStructuredContent();

		// Admin user

		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		Map<String, Map<String, String>> actions =
			getStructuredContent.getActions();

		Assert.assertTrue(actions.containsKey("delete"));
		Assert.assertTrue(actions.containsKey("get"));
		Assert.assertTrue(actions.containsKey("get-rendered-content"));
		Assert.assertTrue(actions.containsKey("replace"));
		Assert.assertTrue(actions.containsKey("subscribe"));
		Assert.assertTrue(actions.containsKey("unsubscribe"));
		Assert.assertTrue(actions.containsKey("update"));

		// Owner

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		RoleTestUtil.addResourcePermission(
			role.getName(), "com.liferay.journal",
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(testGroup.getGroupId()), ActionKeys.ADD_ARTICLE);

		String password = RandomTestUtil.randomString();

		User ownerUser = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			password,
			RandomTestUtil.randomString() + StringPool.AT + "liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		UserLocalServiceUtil.updateEmailAddressVerified(
			ownerUser.getUserId(), true);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			new long[] {ownerUser.getUserId()}, testGroup.getGroupId(),
			role.getRoleId());

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		StructuredContentResource structuredContentResource =
			builder.authentication(
				ownerUser.getLogin(), password
			).locale(
				LocaleUtil.getDefault()
			).build();

		postStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		getStructuredContent = structuredContentResource.getStructuredContent(
			postStructuredContent.getId());

		try {
			actions = getStructuredContent.getActions();

			Assert.assertTrue(actions.containsKey("delete"));
			Assert.assertTrue(actions.containsKey("get"));
			Assert.assertTrue(actions.containsKey("get-rendered-content"));
			Assert.assertTrue(actions.containsKey("replace"));
			Assert.assertTrue(actions.containsKey("subscribe"));
			Assert.assertTrue(actions.containsKey("unsubscribe"));
			Assert.assertTrue(actions.containsKey("update"));
		}
		finally {
			_roleLocalService.deleteRole(role);
		}

		// Regular user

		role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		RoleTestUtil.addResourcePermission(
			role.getName(), JournalArticle.class.getName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(testGroup.getGroupId()), ActionKeys.VIEW);

		User regularUser = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			password,
			RandomTestUtil.randomString() + StringPool.AT + "liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		UserLocalServiceUtil.updateEmailAddressVerified(
			regularUser.getUserId(), true);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			new long[] {regularUser.getUserId()}, testGroup.getGroupId(),
			role.getRoleId());

		builder = StructuredContentResource.builder();

		structuredContentResource = builder.authentication(
			regularUser.getLogin(), password
		).locale(
			LocaleUtil.getDefault()
		).build();

		getStructuredContent = structuredContentResource.getStructuredContent(
			postStructuredContent.getId());

		try {
			actions = getStructuredContent.getActions();

			Assert.assertFalse(actions.containsKey("delete"));
			Assert.assertTrue(actions.containsKey("get"));
			Assert.assertTrue(actions.containsKey("get-rendered-content"));
			Assert.assertFalse(actions.containsKey("replace"));
			Assert.assertFalse(actions.containsKey("subscribe"));
			Assert.assertFalse(actions.containsKey("unsubscribe"));
			Assert.assertFalse(actions.containsKey("update"));
		}
		finally {
			_roleLocalService.deleteRole(role);
			_userLocalService.deleteUser(regularUser);
			_userLocalService.deleteUser(ownerUser);
		}
	}

	@Override
	@Test
	public void testGetStructuredContentPermissionsPage() throws Exception {
		StructuredContent postStructuredContent =
			testPostSiteStructuredContent_addStructuredContent(
				randomStructuredContent());

		Page<Permission> page =
			structuredContentResource.getStructuredContentPermissionsPage(
				postStructuredContent.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	@Override
	@Test
	public void testGetStructuredContentRenderedContentTemplate()
		throws Exception {

		StructuredContent structuredContent =
			testGetSiteStructuredContentByKey_addStructuredContent();

		ContentField[] contentFields = structuredContent.getContentFields();

		ContentFieldValue contentFieldValue =
			contentFields[0].getContentFieldValue();

		Assert.assertEquals(
			"<div>" + contentFieldValue.getData() + "</div>",
			structuredContentResource.
				getStructuredContentRenderedContentTemplate(
					structuredContent.getId(), _ddmTemplate.getTemplateKey()));
	}

	@Test
	public void testPostSiteLocalizedStructuredContent() throws Exception {
		StructuredContent randomLocalizedStructuredContent =
			_randomLocalizedStructuredContent();

		StructuredContent postStructuredContent =
			testPostSiteStructuredContent_addStructuredContent(
				randomLocalizedStructuredContent);

		assertEquals(randomLocalizedStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"contentStructureId", "description", "title"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"contentStructureId", "creatorId"};
	}

	@Override
	protected StructuredContent randomIrrelevantStructuredContent()
		throws Exception {

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setContentStructureId(
			_irrelevantDDMStructure.getStructureId());

		return structuredContent;
	}

	@Override
	protected StructuredContent randomStructuredContent() throws Exception {
		StructuredContent structuredContent = super.randomStructuredContent();

		structuredContent.setContentFields(
			new ContentField[] {
				new ContentField() {
					{
						contentFieldValue = new ContentFieldValue() {
							{
								data = RandomTestUtil.randomString(10);
							}
						};
						name = "MyText";
					}
				}
			});
		structuredContent.setContentStructureId(_ddmStructure.getStructureId());

		return structuredContent;
	}

	@Override
	protected StructuredContent
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				Long assetLibraryId, StructuredContent structuredContent)
		throws Exception {

		structuredContent.setContentStructureId(_ddmStructure.getStructureId());

		return structuredContentResource.postAssetLibraryStructuredContent(
			assetLibraryId, structuredContent);
	}

	@Override
	protected StructuredContent
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				Long contentStructureId, StructuredContent structuredContent)
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), structuredContent);
	}

	@Override
	protected Long
		testGetContentStructureStructuredContentsPage_getContentStructureId() {

		return _ddmStructure.getStructureId();
	}

	@Override
	protected Long
		testGetStructuredContentFolderStructuredContentsPage_getIrrelevantStructuredContentFolderId() {

		return _irrelevantJournalFolder.getFolderId();
	}

	@Override
	protected Long
		testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId() {

		return _journalFolder.getFolderId();
	}

	@Override
	protected StructuredContent
			testGraphQLStructuredContent_addStructuredContent()
		throws Exception {

		return testPostSiteStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	private DDMStructure _addDDMStructure(Group group, String fileName)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(_read(fileName)), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);
	}

	private DDMTemplate _addDDMTemplate(DDMStructure ddmStructure)
		throws Exception {

		return DDMTemplateTestUtil.addTemplate(
			ddmStructure.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			_read("test-structured-content-template.xsl"), LocaleUtil.US);
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private StructuredContent _randomLocalizedStructuredContent()
		throws Exception {

		StructuredContent structuredContent = super.randomStructuredContent();

		ContentFieldValue randomEnglishContentFieldValue =
			new ContentFieldValue() {
				{
					data = RandomTestUtil.randomString(10);
				}
			};
		ContentFieldValue randomSpanishContentFieldValue =
			new ContentFieldValue() {
				{
					data = RandomTestUtil.randomString(10);
				}
			};

		structuredContent.setContentFields(
			new ContentField[] {
				new ContentField() {
					{
						contentFieldValue = randomEnglishContentFieldValue;
						contentFieldValue_i18n = HashMapBuilder.put(
							"en-US", randomEnglishContentFieldValue
						).put(
							"es-ES", randomSpanishContentFieldValue
						).build();
						name = "MyText";
					}
				}
			});

		structuredContent.setContentStructureId(
			_ddmLocalizedStructure.getStructureId());

		return structuredContent;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	private DDMStructure _ddmLocalizedStructure;
	private DDMStructure _ddmStructure;
	private DDMTemplate _ddmTemplate;
	private DDMStructure _irrelevantDDMStructure;
	private JournalFolder _irrelevantJournalFolder;
	private JournalFolder _journalFolder;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}