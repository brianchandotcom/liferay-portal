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
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.headless.delivery.client.dto.v1_0.Document;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.serdes.v1_0.DocumentSerDes;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.io.File;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class DocumentResourceTest extends BaseDocumentResourceTestCase {

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

		String documentRenderedContentByDisplayPageDisplayPageKey =
			documentResource.
				getDocumentRenderedContentByDisplayPageDisplayPageKey(
					document.getId(),
					layoutPageTemplateEntry.getLayoutPageTemplateEntryKey());

		Assert.assertNotNull(
			documentRenderedContentByDisplayPageDisplayPageKey);
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
		return new String[] {"description", "title"};
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
	protected Long testGetDocumentFolderDocumentsPage_getDocumentFolderId()
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);

		Folder folder = DLAppLocalServiceUtil.addFolder(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		return folder.getFolderId();
	}

	@Override
	protected Document testGraphQLDocument_addDocument() throws Exception {
		return testPostDocumentFolderDocument_addDocument(
			randomDocument(), getMultipartFiles());
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

}