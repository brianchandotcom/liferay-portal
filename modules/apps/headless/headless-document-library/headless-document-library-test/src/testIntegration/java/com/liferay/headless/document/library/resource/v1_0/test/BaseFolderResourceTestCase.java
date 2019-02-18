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

package com.liferay.headless.document.library.resource.v1_0.test;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.resource.v1_0.test.dto.FolderImpl;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URL;
import java.util.Date;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.datumedge.hamcrest.json.SameJSONAs;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFolderResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/headless-document-library/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceFoldersPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostContentSpaceFolder() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testDeleteFolder() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetFolder() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutFolder() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetFolderFoldersPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostFolderFolder() throws Exception {
			Assert.assertTrue(true);
	}

	protected static SameJSONAs<? super String> sameJSONAs(Folder folder)
		throws JsonProcessingException {

		return SameJSONAs.sameJSONAs(
			toJSON(folder)
		).allowingExtraUnexpectedFields();
	}

	protected static String toJSON(Folder folder)
		throws JsonProcessingException {

		return _outputObjectMapper.writeValueAsString(folder);
	}

	protected Response invokeGetContentSpaceFoldersPage( Long contentSpaceId , Pagination pagination ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/content-spaces/{content-space-id}/folders",
				contentSpaceId
			);

	}
	protected Response invokePostContentSpaceFolder( Long contentSpaceId , Folder folder ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				_inputObjectMapper.writeValueAsString(folder)
			).when(
			).post(
				_resourceURL + "/content-spaces/{content-space-id}/folders",
				contentSpaceId
			);

	}
	protected Response invokeDeleteFolder( Long folderId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).delete(
				_resourceURL + "/folders/{folder-id}",
				folderId
			);

	}
	protected Response invokeGetFolder( Long folderId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/folders/{folder-id}",
				folderId
			);

	}
	protected Response invokePutFolder( Long folderId , Folder folder ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				_inputObjectMapper.writeValueAsString(folder)
			).when(
			).put(
				_resourceURL + "/folders/{folder-id}",
				folderId 
			);

	}
	protected Response invokeGetFolderFoldersPage( Long folderId , Pagination pagination ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/folders/{folder-id}/folders",
				folderId 
			);

	}
	protected Response invokePostFolderFolder( Long folderId , Folder folder ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				folder
			).when(
			).post(
				_resourceURL + "/folders/{folder-id}/folders",
				folderId 
			);

	}

	protected Folder randomFolder() {
		Folder folder = new FolderImpl();

		folder.setDateCreated(RandomTestUtil.nextDate());
		folder.setDateModified(RandomTestUtil.nextDate());
		folder.setDescription(RandomTestUtil.randomString());
		folder.setHasDocuments(RandomTestUtil.randomBoolean());
		folder.setHasFolders(RandomTestUtil.randomBoolean());
		folder.setId(RandomTestUtil.randomLong());
		folder.setName(RandomTestUtil.randomString());
		folder.setRepositoryId(RandomTestUtil.randomLong());

		return folder;
	}

	protected Group testGroup;

	private abstract class IgnoreFieldsMixin {

		@JsonIgnore
		public abstract Date getDateCreated();

		@JsonIgnore
		public abstract Date getDateModified();

		@JsonIgnore
		public abstract Long getId();

	}

	private RequestSpecification _createRequestSpecification() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		);
	}

	protected static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(Include.NON_NULL);
		}
	};

	private static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			addMixIn(FolderImpl.class, IgnoreFieldsMixin.class);
		}
	};

	private URL _resourceURL;

}