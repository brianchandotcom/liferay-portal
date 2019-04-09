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
import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.io.ByteArrayInputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class DocumentResourceTest extends BaseDocumentResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"contentUrl", "encodingFormat", "title"};
	}

	@Override
	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = super.getEntityFields(type);

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> !StringUtil.equals(
				"fileExtension", entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	@Override
	protected Document testDeleteDocument_addDocument() throws Exception {
		return invokePostSiteDocument(
			testGroup.getGroupId(), _getMultipartBody(randomDocument()));
	}

	@Override
	protected Document testDeleteDocumentMyRating_addDocument()
		throws Exception {

		return invokePostSiteDocument(
			testGroup.getGroupId(), _getMultipartBody(randomDocument()));
	}

	@Override
	protected Document testGetDocument_addDocument() throws Exception {
		return invokePostSiteDocument(
			testGroup.getGroupId(), _getMultipartBody(randomDocument()));
	}

	@Override
	protected Document testGetDocumentFolderDocumentsPage_addDocument(
			Long documentFolderId, Document document)
		throws Exception {

		return invokePostDocumentFolderDocument(
			documentFolderId, _getMultipartBody(document));
	}

	@Override
	protected Long testGetDocumentFolderDocumentsPage_getDocumentFolderId()
		throws Exception {

		Folder folder = DLAppLocalServiceUtil.addFolder(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new ServiceContext());

		return folder.getFolderId();
	}

	@Override
	protected Document testGetSiteDocumentsPage_addDocument(
			Long siteId, Document document)
		throws Exception {

		return invokePostSiteDocument(siteId, _getMultipartBody(document));
	}

	@Override
	protected Document testPatchDocument_addDocument() throws Exception {
		return invokePostSiteDocument(
			testGroup.getGroupId(), _getMultipartBody(randomDocument()));
	}

	@Override
	protected Document testPostDocumentFolderDocument_addDocument(
			Document document)
		throws Exception {

		return invokePostDocumentFolderDocument(
			testGetDocumentFolderDocumentsPage_getDocumentFolderId(),
			_getMultipartBody(document));
	}

	@Override
	protected Document testPostSiteDocument_addDocument(Document document)
		throws Exception {

		return invokePostSiteDocument(
			testGroup.getGroupId(), _getMultipartBody(document));
	}

	@Override
	protected Document testPutDocument_addDocument() throws Exception {
		return invokePostSiteDocument(
			testGroup.getGroupId(), _getMultipartBody(randomDocument()));
	}

	private MultipartBody _getMultipartBody(Document document) {
		contentType = "multipart/form-data;boundary=PART";

		Map<String, BinaryFile> binaryFileMap = new HashMap<>();

		String randomString = RandomTestUtil.randomString();

		binaryFileMap.put(
			"file",
			new BinaryFile(
				contentType, RandomTestUtil.randomString(),
				new ByteArrayInputStream(randomString.getBytes()), 0));

		return MultipartBody.of(
			binaryFileMap, __ -> inputObjectMapper,
			inputObjectMapper.convertValue(document, HashMap.class));
	}

}