/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
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
import com.liferay.headless.delivery.client.dto.v1_0.ContentDocument;
import com.liferay.headless.delivery.client.dto.v1_0.ContentField;
import com.liferay.headless.delivery.client.dto.v1_0.ContentFieldValue;
import com.liferay.headless.delivery.client.dto.v1_0.Geo;
import com.liferay.headless.delivery.client.dto.v1_0.RelatedContent;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentLink;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.problem.Problem;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.io.InputStream;

import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		_blogsEntry = BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));
		_complexDDMStructure = _addDDMStructure(
			testGroup, "test-complex-ddm-structure.json");

		_ddmStructure = _addDDMStructure(testGroup, "test-ddm-structure.json");

		_ddmTemplate = _addDDMTemplate(_ddmStructure);

		_depotDDMStructure = _addDDMStructure(
			testDepotEntry.getGroup(), "test-ddm-structure.json");

		DLFolder dlFolder = DLTestUtil.addDLFolder(testGroup.getGroupId());

		_dlFileEntry = DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

		_irrelevantDDMStructure = _addDDMStructure(
			irrelevantGroup, "test-ddm-structure.json");

		_addDDMTemplate(_irrelevantDDMStructure);

		_irrelevantJournalFolder = JournalTestUtil.addFolder(
			irrelevantGroup.getGroupId(), RandomTestUtil.randomString());
		_journalFolder = JournalTestUtil.addFolder(
			testGroup.getGroupId(), RandomTestUtil.randomString());
		_layout = LayoutTestUtil.addTypeContentLayout(testGroup);
		_localizedDDMStructure = _addDDMStructure(
			testGroup, "test-localized-ddm-structure.json");
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PrincipalThreadLocal.setName(_originalName);
	}

	@Override
	@Test
	public void testDeleteAssetLibraryStructuredContentByExternalReferenceCode()
		throws Exception {

		_useDepotDDMStructureStructureId = true;

		super.testDeleteAssetLibraryStructuredContentByExternalReferenceCode();

		StructuredContent randomStructuredContent = randomStructuredContent();

		randomStructuredContent.setExternalReferenceCode("");

		StructuredContent postStructuredContent =
			structuredContentResource.postAssetLibraryStructuredContent(
				testDeleteAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
				randomStructuredContent);

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.
				deleteAssetLibraryStructuredContentByExternalReferenceCodeHttpResponse(
					testDeleteAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
					postStructuredContent.getUuid()));
		assertHttpResponseStatusCode(
			404,
			structuredContentResource.
				getAssetLibraryStructuredContentByExternalReferenceCodeHttpResponse(
					testDeleteAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
					postStructuredContent.getExternalReferenceCode()));

		String externalReferenceCode = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		try {
			structuredContentResource.
				deleteAssetLibraryStructuredContentByExternalReferenceCode(
					testDeleteAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
					externalReferenceCode);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertEquals(
				StringBundler.concat(
					"No JournalArticle exists with the key {groupId=",
					testDepotEntry.getGroupId(), ", externalReferenceCode=",
					externalReferenceCode, "}"),
				problem.getTitle());
		}
	}

	@Override
	@Test
	public void testDeleteStructuredContentMyRating() throws Exception {
		super.testDeleteStructuredContentMyRating();

		StructuredContent structuredContent =
			testDeleteStructuredContentMyRating_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.
				deleteStructuredContentMyRatingHttpResponse(
					structuredContent.getId()));
		assertHttpResponseStatusCode(
			404,
			structuredContentResource.
				deleteStructuredContentMyRatingHttpResponse(
					structuredContent.getId()));

		StructuredContent irrelevantStructuredContent =
			randomIrrelevantStructuredContent();

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.
				deleteStructuredContentMyRatingHttpResponse(
					irrelevantStructuredContent.getId()));
	}

	@Override
	@Test
	public void testGetAssetLibraryStructuredContentByExternalReferenceCode()
		throws Exception {

		_useDepotDDMStructureStructureId = true;

		super.testGetAssetLibraryStructuredContentByExternalReferenceCode();

		// Blank external reference code

		StructuredContent randomStructuredContent = randomStructuredContent();

		randomStructuredContent.setExternalReferenceCode("");

		StructuredContent postStructuredContent =
			structuredContentResource.postAssetLibraryStructuredContent(
				testGetAssetLibraryStructuredContentsPage_getAssetLibraryId(),
				randomStructuredContent);

		StructuredContent getStructuredContent =
			structuredContentResource.
				getAssetLibraryStructuredContentByExternalReferenceCode(
					testGetAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
					postStructuredContent.getUuid());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);

		// Nonexistent asset library ID

		long assetLibraryId = RandomTestUtil.randomLong();

		try {
			structuredContentResource.
				getAssetLibraryStructuredContentByExternalReferenceCode(
					assetLibraryId,
					postStructuredContent.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertEquals(
				"Unable to get a valid asset library with ID " + assetLibraryId,
				problem.getTitle());
		}

		// Nonexistent external reference code

		String externalReferenceCode = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		try {
			structuredContentResource.
				getAssetLibraryStructuredContentByExternalReferenceCode(
					testGetAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
					externalReferenceCode);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertEquals(
				StringBundler.concat(
					"No JournalArticle exists with the key {groupId=",
					testDepotEntry.getGroupId(), ", externalReferenceCode=",
					externalReferenceCode, "}"),
				problem.getTitle());
		}
	}

	@Override
	@Test
	public void testGetSiteStructuredContentsPage() throws Exception {
		super.testGetSiteStructuredContentsPage();

		_testGetSiteStructuredContentsPageByDefaultPriority();
		_testGetSiteStructuredContentsPageByGivenPriority();
		_testGetSiteStructuredContentsPageOrderedByDescendingPriority();
	}

	@Override
	@Test
	public void testGetStructuredContent() throws Exception {

		// Get structured content

		super.testGetStructuredContent();

		// Complete structured content with all types of content fields

		StructuredContent postStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGroup.getGroupId(), _randomCompleteStructuredContent());

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);

		// Different folder

		postStructuredContent =
			structuredContentResource.
				postStructuredContentFolderStructuredContent(
					_journalFolder.getFolderId(),
					_randomCompleteStructuredContent());

		getStructuredContent = structuredContentResource.getStructuredContent(
			postStructuredContent.getId());

		Assert.assertEquals(
			_journalFolder.getFolderId(),
			(long)getStructuredContent.getStructuredContentFolderId());

		// Different locale

		postStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		String title = postStructuredContent.getTitle();

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		StructuredContentResource frenchStructuredContentResource =
			builder.authentication(
				"test@liferay.com", "test"
			).locale(
				LocaleUtil.FRANCE
			).build();

		String frenchTitle = RandomTestUtil.randomString();

		postStructuredContent.setTitle(frenchTitle);

		frenchStructuredContentResource.putStructuredContent(
			postStructuredContent.getId(), postStructuredContent);

		getStructuredContent =
			frenchStructuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		Assert.assertEquals(frenchTitle, getStructuredContent.getTitle());

		getStructuredContent = structuredContentResource.getStructuredContent(
			getStructuredContent.getId());

		Assert.assertEquals(title, getStructuredContent.getTitle());

		// Role admin user

		postStructuredContent = testGetStructuredContent_addStructuredContent();

		getStructuredContent = structuredContentResource.getStructuredContent(
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

		// Role owner

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		RoleTestUtil.addResourcePermission(
			role.getName(), "com.liferay.journal",
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(testGroup.getGroupId()), ActionKeys.ADD_ARTICLE);

		String password = RandomTestUtil.randomString();

		User ownerUser = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			password, RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		UserLocalServiceUtil.updateEmailAddressVerified(
			ownerUser.getUserId(), true);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			new long[] {ownerUser.getUserId()}, testGroup.getGroupId(),
			role.getRoleId());

		StructuredContentResource ownerUserStructuredContentResource =
			builder.authentication(
				ownerUser.getLogin(), password
			).locale(
				LocaleUtil.getDefault()
			).build();

		postStructuredContent =
			ownerUserStructuredContentResource.postSiteStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		getStructuredContent =
			ownerUserStructuredContentResource.getStructuredContent(
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

		// Role regular user

		role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		RoleTestUtil.addResourcePermission(
			role.getName(), JournalArticle.class.getName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(testGroup.getGroupId()), ActionKeys.VIEW);

		User regularUser = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			password, RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		UserLocalServiceUtil.updateEmailAddressVerified(
			regularUser.getUserId(), true);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			new long[] {regularUser.getUserId()}, testGroup.getGroupId(),
			role.getRoleId());

		builder = StructuredContentResource.builder();

		StructuredContentResource regularUserStructuredContentResource =
			builder.authentication(
				regularUser.getLogin(), password
			).locale(
				LocaleUtil.getDefault()
			).build();

		getStructuredContent =
			regularUserStructuredContentResource.getStructuredContent(
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

		_testGetStructuredContentAssetLibrary();
	}

	@Override
	@Test
	public void testGetStructuredContentRenderedContentByDisplayPageDisplayPageKey()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				testGroup.getCreatorUserId(), testGroup.getGroupId(), 0,
				_portal.getClassNameId(JournalArticle.class.getName()),
				_ddmStructure.getStructureId(), RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				false, 0, 0, 0, WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId()));

		Assert.assertNotNull(
			structuredContentResource.
				getStructuredContentRenderedContentByDisplayPageDisplayPageKey(
					journalArticle.getResourcePrimKey(),
					layoutPageTemplateEntry.getLayoutPageTemplateEntryKey()));
	}

	@Override
	@Test
	public void testGetStructuredContentRenderedContentContentTemplate()
		throws Exception {

		StructuredContent structuredContent =
			testGetSiteStructuredContentByKey_addStructuredContent();

		ContentField[] contentFields = structuredContent.getContentFields();

		ContentFieldValue contentFieldValue =
			contentFields[0].getContentFieldValue();

		Assert.assertEquals(
			"<div>" + contentFieldValue.getData() + "</div>",
			structuredContentResource.
				getStructuredContentRenderedContentContentTemplate(
					structuredContent.getId(), _ddmTemplate.getTemplateKey()));
	}

	@Override
	@Test
	public void testPatchStructuredContent() throws Exception {
		super.testPatchStructuredContent();

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setPriority(1.0);

		StructuredContent postStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGroup.getGroupId(), structuredContent);

		StructuredContent patchStructuredContent =
			structuredContentResource.patchStructuredContent(
				postStructuredContent.getId(),
				new StructuredContent() {
					{
						title = RandomTestUtil.randomString();
					}
				});

		Assert.assertEquals(
			Double.valueOf(1.0), patchStructuredContent.getPriority());
	}

	@Override
	@Test
	public void testPostAssetLibraryStructuredContent() throws Exception {
		super.testPostAssetLibraryStructuredContent();

		// Default external reference code and UUID

		StructuredContent randomStructuredContent1 = randomStructuredContent();

		randomStructuredContent1.setExternalReferenceCode("");
		randomStructuredContent1.setUuid("");

		StructuredContent postStructuredContent1 =
			testPostAssetLibraryStructuredContent_addStructuredContent(
				randomStructuredContent1);

		Assert.assertNotNull(postStructuredContent1.getExternalReferenceCode());
		Assert.assertNotNull(postStructuredContent1.getUuid());
		Assert.assertEquals(
			postStructuredContent1.getExternalReferenceCode(),
			postStructuredContent1.getUuid());
		assertValid(postStructuredContent1);

		// External reference code

		_testPostAssetLibraryStructuredContent(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));

		StructuredContent postStructuredContent2 =
			testPostAssetLibraryStructuredContent_addStructuredContent(
				randomStructuredContent());

		_testPostAssetLibraryStructuredContent(
			String.valueOf(postStructuredContent2.getId()));

		// Duplicate external reference code

		StructuredContent postStructuredContent3 =
			testPostAssetLibraryStructuredContent_addStructuredContent(
				randomStructuredContent());

		StructuredContent randomStructuredContent2 = randomStructuredContent();

		randomStructuredContent2.setContentStructureId(
			_depotDDMStructure.getStructureId());
		randomStructuredContent2.setExternalReferenceCode(
			postStructuredContent3.getExternalReferenceCode());

		HttpInvoker.HttpResponse httpResponse =
			structuredContentResource.
				postAssetLibraryStructuredContentHttpResponse(
					testDepotEntry.getDepotEntryId(), randomStructuredContent2);

		Assert.assertEquals(
			StringBundler.concat(
				"Duplicate journal article external reference code ",
				postStructuredContent3.getExternalReferenceCode(), "in group ",
				testDepotEntry.getGroupId()),
			httpResponse.getContent());
	}

	@Override
	@Test
	public void testPostSiteStructuredContent() throws Exception {
		super.testPostSiteStructuredContent();

		// Localized structured content with the default language

		Locale locale = LocaleUtil.getDefault();

		StructuredContent randomLocalizedStructuredContent1 =
			_randomStructuredContent(locale);

		StructuredContentResource englishStructuredContentResource =
			_buildStructureContentResource(locale);

		StructuredContent postStructuredContent1 =
			englishStructuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getSiteId(),
				randomLocalizedStructuredContent1);

		_assertLocalizedValues(
			postStructuredContent1, LocaleUtil.toW3cLanguageId(locale));
		assertEquals(randomLocalizedStructuredContent1, postStructuredContent1);
		assertValid(postStructuredContent1);

		// Localized structured content with a different language from the
		// default language

		locale = LocaleUtil.fromLanguageId("es-ES");

		StructuredContent randomLocalizedStructuredContent2 =
			_randomStructuredContent(locale);

		StructuredContentResource spanishStructuredContentResource =
			_buildStructureContentResource(locale);

		StructuredContent postStructuredContent2 =
			spanishStructuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getSiteId(),
				randomLocalizedStructuredContent2);

		_assertLocalizedValues(
			postStructuredContent2, LocaleUtil.toW3cLanguageId(locale));
		assertEquals(randomLocalizedStructuredContent2, postStructuredContent2);
		assertValid(postStructuredContent2);

		// Structured content with the default priority

		locale = LocaleUtil.getDefault();

		StructuredContent randomStructuredContent = _randomStructuredContent(
			locale);

		StructuredContentResource structuredContentResource =
			_buildStructureContentResource(locale);

		randomStructuredContent.setPriority((Double)null);

		StructuredContent postStructuredContent3 =
			structuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getSiteId(),
				randomStructuredContent);

		Assert.assertEquals(
			Double.valueOf(0.0), postStructuredContent3.getPriority());
		assertValid(postStructuredContent3);
	}

	@Override
	@Test
	public void testPostStructuredContentFolderStructuredContent()
		throws Exception {

		super.testPostStructuredContentFolderStructuredContent();

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			testGroup.getCreatorUserId(), testGroup.getGroupId(), 0,
			_portal.getClassNameId(JournalArticle.class.getName()),
			_localizedDDMStructure.getStructureId(),
			RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true, 0,
			0, 0, WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));

		Locale locale = LocaleUtil.getDefault();

		StructuredContent randomStructuredContent = _randomStructuredContent(
			locale);

		StructuredContentResource structuredContentResource =
			_buildStructureContentResource(locale);

		StructuredContent postStructuredContent =
			structuredContentResource.
				postStructuredContentFolderStructuredContent(
					_journalFolder.getFolderId(), randomStructuredContent);

		Assert.assertTrue(
			postStructuredContent.getRenderedContents()[0].
				getMarkedAsDefault());
	}

	@Override
	@Test
	public void testPutAssetLibraryStructuredContentByExternalReferenceCode()
		throws Exception {

		_useDepotDDMStructureStructureId = true;

		super.testPutAssetLibraryStructuredContentByExternalReferenceCode();

		// Different external reference code in payload

		StructuredContent randomStructuredContent1 = randomStructuredContent();

		StructuredContent putStructuredContent1 =
			structuredContentResource.
				putAssetLibraryStructuredContentByExternalReferenceCode(
					testPutAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
					randomStructuredContent1.getExternalReferenceCode(),
					randomStructuredContent1);

		StructuredContent randomStructuredContent2 =
			testPutAssetLibraryStructuredContentByExternalReferenceCode_createStructuredContent();

		String externalReferenceCode = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		randomStructuredContent2.setTitle(putStructuredContent1.getTitle());

		StructuredContent putStructuredContent2 =
			structuredContentResource.
				putAssetLibraryStructuredContentByExternalReferenceCode(
					testPutAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
					externalReferenceCode, randomStructuredContent2);

		Assert.assertNotEquals(
			putStructuredContent1.getId(), putStructuredContent2.getId());
		Assert.assertEquals(
			putStructuredContent1.getTitle(), putStructuredContent2.getTitle());
		Assert.assertEquals(
			externalReferenceCode,
			putStructuredContent2.getExternalReferenceCode());

		StructuredContent randomStructuredContent3 = randomStructuredContent();

		StructuredContent putStructuredContent3 =
			structuredContentResource.
				putAssetLibraryStructuredContentByExternalReferenceCode(
					testPutAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
					randomStructuredContent3.getExternalReferenceCode(),
					randomStructuredContent3);

		assertEquals(
			putStructuredContent3,
			structuredContentResource.
				putAssetLibraryStructuredContentByExternalReferenceCode(
					testPutAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId(),
					putStructuredContent3.getExternalReferenceCode(),
					randomStructuredContent3));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"contentStructureId", "description", "priority", "title"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {
			"assetLibraryId", "contentStructureId", "creatorId", "siteId"
		};
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
						name = "Foo";
					}
				}
			});
		structuredContent.setContentStructureId(
			_useDepotDDMStructureStructureId ?
				_depotDDMStructure.getStructureId() :
					_ddmStructure.getStructureId());
		structuredContent.setRelatedContents(
			new RelatedContent[] {
				new RelatedContent() {
					{
						contentType = "BlogPosting";
						id = _blogsEntry.getEntryId();
					}
				}
			});
		structuredContent.setStructuredContentFolderId(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return structuredContent;
	}

	@Override
	protected StructuredContent
			testDeleteAssetLibraryStructuredContentByExternalReferenceCode_addStructuredContent()
		throws Exception {

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setContentStructureId(
			_depotDDMStructure.getStructureId());

		return structuredContentResource.postAssetLibraryStructuredContent(
			testDepotEntry.getDepotEntryId(), structuredContent);
	}

	@Override
	protected Long
			testDeleteAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected StructuredContent
			testDeleteStructuredContentMyRating_addStructuredContent()
		throws Exception {

		StructuredContent structuredContent =
			super.testDeleteStructuredContentMyRating_addStructuredContent();

		structuredContentResource.putStructuredContentMyRatingHttpResponse(
			structuredContent.getId(), randomRating());

		return structuredContent;
	}

	@Override
	protected StructuredContent
			testGetAssetLibraryStructuredContentByExternalReferenceCode_addStructuredContent()
		throws Exception {

		return testPostAssetLibraryStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	@Override
	protected Long
			testGetAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected StructuredContent
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				Long assetLibraryId, StructuredContent structuredContent)
		throws Exception {

		structuredContent.setContentStructureId(
			_depotDDMStructure.getStructureId());

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
			testGraphQLGetAssetLibraryStructuredContentByExternalReferenceCode_addStructuredContent()
		throws Exception {

		_useDepotDDMStructureStructureId = true;

		return testPostAssetLibraryStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	@Override
	protected Long
			testGraphQLGetAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected StructuredContent
			testGraphQLStructuredContent_addStructuredContent()
		throws Exception {

		return testPostSiteStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	@Override
	protected StructuredContent
			testPostAssetLibraryStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		structuredContent.setContentStructureId(
			_depotDDMStructure.getStructureId());

		return super.testPostAssetLibraryStructuredContent_addStructuredContent(
			structuredContent);
	}

	@Override
	protected StructuredContent
			testPutAssetLibraryStructuredContentByExternalReferenceCode_addStructuredContent()
		throws Exception {

		return testPostAssetLibraryStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	@Override
	protected Long
			testPutAssetLibraryStructuredContentByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected StructuredContent
			testPutAssetLibraryStructuredContentPermissionsPage_addStructuredContent()
		throws Exception {

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setContentStructureId(
			_depotDDMStructure.getStructureId());

		return structuredContentResource.postAssetLibraryStructuredContent(
			testDepotEntry.getDepotEntryId(), structuredContent);
	}

	private DDMStructure _addDDMStructure(Group group, String fileName)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(_read(fileName)), StorageType.DEFAULT.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);
	}

	private DDMTemplate _addDDMTemplate(DDMStructure ddmStructure)
		throws Exception {

		return DDMTemplateTestUtil.addTemplate(
			ddmStructure.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			_read("test-structured-content-template.vm"), LocaleUtil.US);
	}

	private void _assertLocalizedValue(
		Map<String, String> localizedValues, String value, String w3cLanguageId,
		Set<String> w3cLanguageIds) {

		Assert.assertEquals(w3cLanguageIds, localizedValues.keySet());
		Assert.assertEquals(value, localizedValues.get(w3cLanguageId));
	}

	private void _assertLocalizedValues(
		StructuredContent structuredContent, String w3cLanguageId) {

		Set<String> w3cLanguageIds = SetUtil.fromArray("es-ES", "en-US");

		Assert.assertEquals(
			w3cLanguageIds,
			SetUtil.fromArray(structuredContent.getAvailableLanguages()));

		_assertLocalizedValue(
			structuredContent.getDescription_i18n(),
			structuredContent.getDescription(), w3cLanguageId, w3cLanguageIds);
		_assertLocalizedValue(
			structuredContent.getTitle_i18n(), structuredContent.getTitle(),
			w3cLanguageId, w3cLanguageIds);
		_assertLocalizedValue(
			structuredContent.getFriendlyUrlPath_i18n(),
			structuredContent.getFriendlyUrlPath(), w3cLanguageId,
			w3cLanguageIds);
		_assertLocalizedValue(
			structuredContent.getDescription_i18n(),
			structuredContent.getDescription(), w3cLanguageId, w3cLanguageIds);

		for (ContentField contentField : structuredContent.getContentFields()) {
			Map<String, ContentFieldValue> contentFieldValue_i18n =
				contentField.getContentFieldValue_i18n();

			Assert.assertEquals(
				w3cLanguageIds, contentFieldValue_i18n.keySet());
		}
	}

	private StructuredContentResource _buildStructureContentResource(
		Locale locale) {

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		return builder.authentication(
			"test@liferay.com", "test"
		).locale(
			locale
		).header(
			"X-Accept-All-Languages", "true"
		).build();
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private String _randomColor() {
		return String.format(
			"#%02d%02d%02d", RandomTestUtil.randomInt(0, 100),
			RandomTestUtil.randomInt(0, 100), RandomTestUtil.randomInt(0, 100));
	}

	private StructuredContent _randomCompleteStructuredContent()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(), _journalFolder.getFolderId());

		StructuredContent structuredContent = super.randomStructuredContent();

		structuredContent.setContentFields(
			_randomContentFields(journalArticle));
		structuredContent.setContentStructureId(
			_complexDDMStructure.getStructureId());
		structuredContent.setRelatedContents(
			new RelatedContent[] {
				new RelatedContent() {
					{
						contentType = "BlogPosting";
						id = _blogsEntry.getEntryId();
					}
				}
			});
		structuredContent.setStructuredContentFolderId(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return structuredContent;
	}

	private ContentField[] _randomContentFields(JournalArticle journalArticle) {
		return new ContentField[] {
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = RandomTestUtil.randomString(10);
						}
					};
					name = "Text";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _COMPLETE_STRUCTURED_CONTENT_OPTIONS
								[RandomTestUtil.randomInt(0, 2)];
						}
					};
					name = "SelectFromList";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _COMPLETE_STRUCTURED_CONTENT_OPTIONS
								[RandomTestUtil.randomInt(0, 2)];
						}
					};
					name = "SingleSelection";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data =
								"[" +
									_COMPLETE_STRUCTURED_CONTENT_OPTIONS
										[RandomTestUtil.randomInt(0, 2)] + "]";
						}
					};
					name = "MultipleSelection";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _randomGrid();
						}
					};
					name = "Grid";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _randomDate();
						}
					};
					name = "Date";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = String.valueOf(RandomTestUtil.randomInt());
						}
					};
					name = "Numeric";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							image = new ContentDocument() {
								{
									id = _dlFileEntry.getPrimaryKey();
								}
							};
						}
					};
					name = "Image";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = RandomTestUtil.randomString(500);
						}
					};
					name = "RichText";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							document = new ContentDocument() {
								{
									id = _dlFileEntry.getPrimaryKey();
								}
							};
						}
					};
					name = "Upload";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							data = _randomColor();
						}
					};
					name = "Color";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							structuredContentLink =
								new StructuredContentLink() {
									{
										id =
											journalArticle.getResourcePrimKey();
									}
								};
						}
					};
					name = "WebContent";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							geo = new Geo() {
								{
									latitude = RandomTestUtil.randomDouble();
									longitude = RandomTestUtil.randomDouble();
								}
							};
						}
					};
					name = "Geolocation";
				}
			},
			new ContentField() {
				{
					contentFieldValue = new ContentFieldValue() {
						{
							link = _layout.getFriendlyURL();
						}
					};
					name = "LinkToPage";
				}
			}
		};
	}

	private String _randomDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		return simpleDateFormat.format(new Date());
	}

	private String _randomGrid() {
		return StringBundler.concat(
			"{", _COMPLETE_STRUCTURED_CONTENT_OPTIONS[0], ":",
			_COMPLETE_STRUCTURED_CONTENT_OPTIONS
				[RandomTestUtil.randomInt(0, 2)],
			",", _COMPLETE_STRUCTURED_CONTENT_OPTIONS[1], ":",
			_COMPLETE_STRUCTURED_CONTENT_OPTIONS
				[RandomTestUtil.randomInt(0, 2)],
			",", _COMPLETE_STRUCTURED_CONTENT_OPTIONS[2], ":",
			_COMPLETE_STRUCTURED_CONTENT_OPTIONS
				[RandomTestUtil.randomInt(0, 2)],
			"}");
	}

	private StructuredContent _randomStructuredContent(Locale locale)
		throws Exception {

		StructuredContent structuredContent = super.randomStructuredContent();

		String w3cLanguageId = LocaleUtil.toW3cLanguageId(locale);

		Map<String, ContentFieldValue> contentFieldValues = HashMapBuilder.put(
			"en-US",
			(ContentFieldValue)new ContentFieldValue() {

				{
					data = RandomTestUtil.randomString(10);
				}
			}
		).put(
			"es-ES",
			(ContentFieldValue)new ContentFieldValue() {

				{
					data = RandomTestUtil.randomString(10);
				}
			}
		).build();

		structuredContent.setContentFields(
			new ContentField[] {
				new ContentField() {
					{
						contentFieldValue = contentFieldValues.get(
							w3cLanguageId);
						contentFieldValue_i18n = contentFieldValues;
						name = "MyText";
					}
				}
			});

		structuredContent.setContentStructureId(
			_localizedDDMStructure.getStructureId());

		Map<String, String> description_i18n = HashMapBuilder.put(
			"en-US", RandomTestUtil.randomString()
		).put(
			"es-ES", RandomTestUtil.randomString()
		).build();

		structuredContent.setDescription(description_i18n.get(w3cLanguageId));
		structuredContent.setDescription_i18n(description_i18n);

		Map<String, String> friendlyUrlPath_i18n = HashMapBuilder.put(
			"en-US", RandomTestUtil.randomString()
		).put(
			"es-ES", RandomTestUtil.randomString()
		).build();

		structuredContent.setFriendlyUrlPath(
			friendlyUrlPath_i18n.get(w3cLanguageId));
		structuredContent.setFriendlyUrlPath_i18n(friendlyUrlPath_i18n);

		structuredContent.setRelatedContents(
			new RelatedContent[] {
				new RelatedContent() {
					{
						contentType = "BlogPosting";
						id = _blogsEntry.getEntryId();
					}
				}
			});
		structuredContent.setStructuredContentFolderId(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Map<String, String> title_i18n = HashMapBuilder.put(
			"en-US", RandomTestUtil.randomString()
		).put(
			"es-ES", RandomTestUtil.randomString()
		).build();

		structuredContent.setTitle(title_i18n.get(w3cLanguageId));
		structuredContent.setTitle_i18n(title_i18n);

		return structuredContent;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _testGetSiteStructuredContentsPageByDefaultPriority()
		throws Exception {

		StructuredContent irrelevantStructuredContent =
			randomIrrelevantStructuredContent();

		StructuredContent irrelevantPostStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getIrrelevantSiteId(),
				irrelevantStructuredContent);

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setPriority((Double)null);

		StructuredContent postStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getSiteId(),
				structuredContent);

		Page<StructuredContent> page =
			structuredContentResource.getSiteStructuredContentsPage(
				testGetSiteStructuredContentsPage_getSiteId(), true, null, null,
				"priority eq 0.0", Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(postStructuredContent),
			(List<StructuredContent>)page.getItems());

		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			irrelevantPostStructuredContent.getId());
		structuredContentResource.deleteStructuredContent(
			postStructuredContent.getId());
	}

	private void _testGetSiteStructuredContentsPageByGivenPriority()
		throws Exception {

		StructuredContent irrelevantStructuredContent =
			randomIrrelevantStructuredContent();

		StructuredContent irrelevantPostStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getIrrelevantSiteId(),
				irrelevantStructuredContent);

		StructuredContent structuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getSiteId(),
				structuredContent);

		StructuredContent patchStructuredContent =
			structuredContentResource.patchStructuredContent(
				postStructuredContent.getId(),
				new StructuredContent() {
					{
						priority = Double.valueOf(1.3);
					}
				});

		Page<StructuredContent> page =
			structuredContentResource.getSiteStructuredContentsPage(
				testGetSiteStructuredContentsPage_getSiteId(), true, null, null,
				"priority eq 1.3", Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(patchStructuredContent),
			(List<StructuredContent>)page.getItems());

		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			irrelevantPostStructuredContent.getId());
		structuredContentResource.deleteStructuredContent(
			postStructuredContent.getId());
	}

	private void _testGetSiteStructuredContentsPageOrderedByDescendingPriority()
		throws Exception {

		StructuredContent irrelevantStructuredContent =
			randomIrrelevantStructuredContent();

		StructuredContent irrelevantPostStructuredContent =
			structuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getIrrelevantSiteId(),
				irrelevantStructuredContent);

		StructuredContent structuredContent1 = randomStructuredContent();

		structuredContent1.setPriority(Double.valueOf(1.2));

		StructuredContent postStructuredContent1 =
			structuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getSiteId(),
				structuredContent1);

		StructuredContent structuredContent2 = randomStructuredContent();

		structuredContent2.setPriority(Double.valueOf(1.1));

		StructuredContent postStructuredContent2 =
			structuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getSiteId(),
				structuredContent2);

		StructuredContent structuredContent3 = randomStructuredContent();

		structuredContent3.setPriority(Double.valueOf(1.3));

		StructuredContent postStructuredContent3 =
			structuredContentResource.postSiteStructuredContent(
				testGetSiteStructuredContentsPage_getSiteId(),
				structuredContent3);

		Page<StructuredContent> page =
			structuredContentResource.getSiteStructuredContentsPage(
				testGetSiteStructuredContentsPage_getSiteId(), true, null, null,
				null, Pagination.of(1, 10), "priority:desc");

		Assert.assertEquals(3, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				postStructuredContent3, postStructuredContent1,
				postStructuredContent2),
			(List<StructuredContent>)page.getItems());

		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			irrelevantPostStructuredContent.getId());
		structuredContentResource.deleteStructuredContent(
			postStructuredContent1.getId());
		structuredContentResource.deleteStructuredContent(
			postStructuredContent2.getId());
		structuredContentResource.deleteStructuredContent(
			postStructuredContent3.getId());
	}

	private void _testGetStructuredContentAssetLibrary() throws Exception {

		// Get structured content inside folder in asset library

		JournalFolder journalFolder1 = JournalTestUtil.addFolder(
			testDepotEntry.getGroupId(), RandomTestUtil.randomString());

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setContentStructureId(
			_depotDDMStructure.getStructureId());

		StructuredContent postStructuredContent =
			structuredContentResource.
				postStructuredContentFolderStructuredContent(
					journalFolder1.getFolderId(), structuredContent);

		StructuredContent getStructuredContent1 =
			structuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		Assert.assertEquals(
			journalFolder1.getFolderId(),
			GetterUtil.getLong(
				getStructuredContent1.getStructuredContentFolderId()));

		// Get structured content inside current folder in asset library

		JournalFolder journalFolder2 = JournalTestUtil.addFolder(
			testDepotEntry.getGroupId(), RandomTestUtil.randomString());

		_journalArticleLocalService.moveArticle(
			testDepotEntry.getGroupId(), postStructuredContent.getKey(),
			journalFolder2.getFolderId(),
			ServiceContextTestUtil.getServiceContext(
				testDepotEntry.getGroupId()));

		StructuredContent getStructuredContent2 =
			structuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		Assert.assertEquals(
			journalFolder2.getFolderId(),
			GetterUtil.getLong(
				getStructuredContent2.getStructuredContentFolderId()));

		// Get structured content inside current subfolder in asset library

		JournalFolder journalFolder3 = JournalTestUtil.addFolder(
			testDepotEntry.getGroupId(), journalFolder2.getFolderId(),
			RandomTestUtil.randomString());

		_journalArticleLocalService.moveArticle(
			testDepotEntry.getGroupId(), postStructuredContent.getKey(),
			journalFolder3.getFolderId(),
			ServiceContextTestUtil.getServiceContext(
				testDepotEntry.getGroupId()));

		StructuredContent getStructuredContent3 =
			structuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		Assert.assertEquals(
			journalFolder3.getFolderId(),
			GetterUtil.getLong(
				getStructuredContent3.getStructuredContentFolderId()));
	}

	private void _testPostAssetLibraryStructuredContent(
			String externalReferenceCode)
		throws Exception {

		StructuredContent randomStructuredContent = randomStructuredContent();

		randomStructuredContent.setExternalReferenceCode(externalReferenceCode);

		StructuredContent postStructuredContent =
			testPostAssetLibraryStructuredContent_addStructuredContent(
				randomStructuredContent);

		Assert.assertNotNull(postStructuredContent.getExternalReferenceCode());
		Assert.assertEquals(
			externalReferenceCode,
			postStructuredContent.getExternalReferenceCode());
		assertValid(postStructuredContent);
	}

	private static final String[] _COMPLETE_STRUCTURED_CONTENT_OPTIONS = {
		"Option1", "Option2", "Option3"
	};

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	private BlogsEntry _blogsEntry;
	private DDMStructure _complexDDMStructure;
	private DDMStructure _ddmStructure;
	private DDMTemplate _ddmTemplate;
	private DDMStructure _depotDDMStructure;
	private DLFileEntry _dlFileEntry;
	private DDMStructure _irrelevantDDMStructure;
	private JournalFolder _irrelevantJournalFolder;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	private JournalFolder _journalFolder;
	private Layout _layout;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	private DDMStructure _localizedDDMStructure;
	private String _originalName;

	@Inject
	private Portal _portal;

	@Inject
	private RoleLocalService _roleLocalService;

	private boolean _useDepotDDMStructureStructureId;

	@Inject
	private UserLocalService _userLocalService;

}