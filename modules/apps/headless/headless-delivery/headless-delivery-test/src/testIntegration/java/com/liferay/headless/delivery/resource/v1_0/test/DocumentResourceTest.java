/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.headless.delivery.client.dto.v1_0.Creator;
import com.liferay.headless.delivery.client.dto.v1_0.Document;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.DocumentSerDes;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.io.File;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class DocumentResourceTest extends BaseDocumentResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteDocumentMyRating() throws Exception {
		super.testDeleteDocumentMyRating();

		Document document = testDeleteDocumentMyRating_addDocument();

		assertHttpResponseStatusCode(
			204,
			documentResource.deleteDocumentMyRatingHttpResponse(
				document.getId()));
		assertHttpResponseStatusCode(
			404,
			documentResource.deleteDocumentMyRatingHttpResponse(
				document.getId()));

		Document irrelevantDocument = randomIrrelevantDocument();

		assertHttpResponseStatusCode(
			404,
			documentResource.deleteDocumentMyRatingHttpResponse(
				irrelevantDocument.getId()));
	}

	@Override
	@Test
	public void testGetDocument() throws Exception {
		super.testGetDocument();

		Document document1 = documentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());

		Assert.assertTrue(Validator.isNotNull(document1.getContentUrl()));

		Document document2 = documentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(),
			HashMapBuilder.put(
				"file", () -> FileUtil.createTempFile(new byte[0])
			).build());

		Assert.assertTrue(Validator.isNull(document2.getContentUrl()));

		Role guestRole = _roleLocalService.getRole(
			testCompany.getCompanyId(), RoleConstants.GUEST);

		_resourcePermissionLocalService.removeResourcePermission(
			testCompany.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(document1.getId()), guestRole.getRoleId(),
			ActionKeys.DOWNLOAD);

		DocumentResource.Builder builder = DocumentResource.builder();

		String password = StringUtil.randomString();

		User user = UserTestUtil.addUser(
			testCompany.getCompanyId(), testCompany.getUserId(), password,
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		DocumentResource regularUserDocumentResource = builder.authentication(
			user.getLogin(), password
		).build();

		document1 = regularUserDocumentResource.getDocument(document1.getId());

		Assert.assertTrue(Validator.isNull(document1.getContentUrl()));
	}

	@Override
	@Test
	public void testGetDocumentRenderedContentByDisplayPageDisplayPageKey()
		throws Exception {

		Document document = testGetDocument_addDocument();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				testGroup.getCreatorUserId(), testGroup.getGroupId(), 0,
				_portal.getClassNameId(FileEntry.class.getName()), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				false, 0, 0, 0, WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId()));

		Assert.assertNotNull(
			documentResource.
				getDocumentRenderedContentByDisplayPageDisplayPageKey(
					document.getId(),
					layoutPageTemplateEntry.getLayoutPageTemplateEntryKey()));
	}

	@Override
	@Test
	public void testGraphQLGetSiteDocumentsPage() throws Exception {
		Document document1 = testGraphQLDocument_addDocument();
		Document document2 = testGraphQLDocument_addDocument();

		JSONObject documentsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(
				new GraphQLField(
					"documents",
					HashMapBuilder.<String, Object>put(
						"flatten", true
					).put(
						"page", 1
					).put(
						"pageSize", 2
					).put(
						"siteKey", "\"" + testGroup.getGroupId() + "\""
					).build(),
					new GraphQLField("items", getGraphQLFields()),
					new GraphQLField("page"), new GraphQLField("totalCount"))),
			"JSONObject/data", "JSONObject/documents");

		Assert.assertEquals(2, documentsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			Arrays.asList(
				DocumentSerDes.toDTOs(documentsJSONObject.getString("items"))));
	}

	@Override
	protected void assertValid(
			Document document, Map<String, File> multipartFiles)
		throws Exception {

		Assert.assertEquals(
			new String(FileUtil.getBytes(multipartFiles.get("file"))),
			_read("http://localhost:8080" + document.getContentUrl()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "fileName", "title"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId", "fileExtension", "sizeInBytes"};
	}

	@Override
	protected Map<String, File> getMultipartFiles() {
		return HashMapBuilder.<String, File>put(
			"file",
			() -> FileUtil.createTempFile(TestDataConstants.TEST_BYTE_ARRAY)
		).build();
	}

	@Override
	protected Document randomDocument() throws Exception {
		Document document = super.randomDocument();

		document.setDocumentFolderId(0L);
		document.setViewableBy(Document.ViewableBy.ANYONE);

		return document;
	}

	@Override
	protected Document
			testDeleteAssetLibraryDocumentByExternalReferenceCode_addDocument()
		throws Exception {

		return documentResource.postAssetLibraryDocument(
			testDepotEntry.getDepotEntryId(), randomDocument(),
			getMultipartFiles());
	}

	@Override
	protected Long
			testDeleteAssetLibraryDocumentByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected Document testDeleteDocumentMyRating_addDocument()
		throws Exception {

		Document document = super.testDeleteDocumentMyRating_addDocument();

		documentResource.putDocumentMyRating(document.getId(), randomRating());

		return document;
	}

	@Override
	protected Document
			testGetAssetLibraryDocumentByExternalReferenceCode_addDocument()
		throws Exception {

		return testPostAssetLibraryDocument_addDocument(
			randomDocument(), getMultipartFiles());
	}

	@Override
	protected Long
			testGetAssetLibraryDocumentByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected Document testGetAssetLibraryDocumentsRatedByMePage_addDocument(
			Long assetLibraryId, Document document)
		throws Exception {

		Document addedDocument =
			super.testGetAssetLibraryDocumentsRatedByMePage_addDocument(
				assetLibraryId, document);

		_addDocumentRatingsEntry(addedDocument);

		return addedDocument;
	}

	@Override
	protected Long testGetDocumentFolderDocumentsPage_getDocumentFolderId()
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);

		Folder folder = DLAppLocalServiceUtil.addFolder(
			null, UserLocalServiceUtil.getGuestUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		return folder.getFolderId();
	}

	@Override
	protected Document testGetSiteDocumentsRatedByMePage_addDocument(
			Long siteId, Document document)
		throws Exception {

		Document addedDocument =
			super.testGetSiteDocumentsRatedByMePage_addDocument(
				siteId, document);

		_addDocumentRatingsEntry(addedDocument);

		return addedDocument;
	}

	@Override
	protected Document testGraphQLDocument_addDocument() throws Exception {
		return testPostDocumentFolderDocument_addDocument(
			randomDocument(), getMultipartFiles());
	}

	@Override
	protected Document
			testGraphQLGetAssetLibraryDocumentByExternalReferenceCode_addDocument()
		throws Exception {

		return testGetAssetLibraryDocumentByExternalReferenceCode_addDocument();
	}

	@Override
	protected Long
			testGraphQLGetAssetLibraryDocumentByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected Long
			testPutAssetLibraryDocumentByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	private void _addDocumentRatingsEntry(Document document) throws Exception {
		Creator creator = document.getCreator();

		_ratingsEntryLocalService.updateEntry(
			creator.getId(), DLFileEntry.class.getName(), document.getId(), 1.0,
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));
	}

	private String _read(String url) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}