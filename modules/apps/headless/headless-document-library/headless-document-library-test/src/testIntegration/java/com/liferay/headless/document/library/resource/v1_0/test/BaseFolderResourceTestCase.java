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

import javax.annotation.Generated;

import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.portal.kernel.util.StringUtil;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;

import org.hamcrest.core.IsNull;

import org.jboss.arquillian.test.api.ArquillianResource;

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
		_inputObjectMapper = new ObjectMapper();

		_inputObjectMapper.setSerializationInclusion(
			JsonInclude.Include.NON_NULL);

		_outputObjectMapper = new ObjectMapper();

		_outputObjectMapper.addMixIn(Folder.class, IgnoreFieldsMixin.class);

		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws MalformedURLException {
		_headlessDocumentLibraryURL = new URL(
			_url.toExternalForm() + "/o/headless-document-library/v1.0");

		_groupId = _createGroup();
	}

	@After
	public void tearDown() throws MalformedURLException {
		_deleteGroup(_groupId);
	}

	@Test
	public void testCRUD()
		throws JsonProcessingException, MalformedURLException {

		// Create

		Folder inputCreateFolder = buildPostFolder();

		Folder createdFolder = createFolder(
			inputCreateFolder, "/documents-repository/" + _groupId + "/folder");

		assertThat(toJSON(createdFolder), sameJSONAs(inputCreateFolder));

		// Retrieve

		Folder retrievedCreatedFolder = _getFolder(
			"/folder/" + createdFolder.getId());

		assertThat(
			toJSON(retrievedCreatedFolder), sameJSONAs(inputCreateFolder));

		// Update

		Folder inputUpdateFolder = buildPutFolder();

		Folder updatedFolder = _updateFolder(
			inputUpdateFolder, "/folder/" + retrievedCreatedFolder.getId());

		assertThat(toJSON(updatedFolder), sameJSONAs(inputUpdateFolder));

		Folder retrievedUpdatedFolder = _getFolder(
			"/folder/" + updatedFolder.getId());

		assertThat(
			toJSON(retrievedUpdatedFolder), sameJSONAs(inputUpdateFolder));

		// Delete

		String path = "/folder/" + retrievedCreatedFolder.getId();

		_deleteFolder(path);

		Response response = _getFolderResponse(path);

		Assert.assertEquals(404, response.getStatusCode());
	}

	protected Folder buildPostFolder() {
		return new Folder();
	}

	protected Folder buildPutFolder() {
		return new Folder();
	}

	protected long getGroupId() {
		return _groupId;
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

	private Folder createFolder(Folder folder)
		throws JsonProcessingException, MalformedURLException {

		return createFolder(
			folder, "/documents-repository/" + _groupId + "/folder");
	}

	protected Folder createFolder(Folder folder, String path)
		throws JsonProcessingException, MalformedURLException {

		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		).body(
			_inputObjectMapper.writeValueAsString(folder)
		).when(
		).post(
			new URL(_headlessDocumentLibraryURL.toExternalForm() + path)
		).then(
		).statusCode(
			200
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"id", IsNull.notNullValue()
		).extract(
		).response(
		).as(
			Folder.class
		);
	}

	private Long _createGroup() throws MalformedURLException {
		return Long.valueOf(
			RestAssured.given(
			).auth(
			).preemptive(
			).basic(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/json"
			).when(
			).param(
				"virtualHost", "localhost"
			).param(
				"parentGroupId", 0
			).param(
				"liveGroupId", 0
			).param(
				"name", StringUtil.randomString(10)
			).param(
				"description", ""
			).param(
				"type", 1
			).param(
				"manualMembership", true
			).param(
				"membershipRestriction", 0
			).param(
				"friendlyURL", "/" + StringUtil.randomString(10)
			).param(
				"site", true
			).param(
				"active", true
			).get(
				new URL(_url, "/api/jsonws/group/add-group")
			).then(
			).statusCode(
				200
			).extract(
			).path(
				"groupId"
			)
		);
	}

	private void _deleteFolder(String path) throws MalformedURLException {
		RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		).when(
		).delete(
			new URL(_headlessDocumentLibraryURL.toExternalForm() + path)
		).then(
		).statusCode(
			204
		);
	}

	private void _deleteGroup(long groupId) throws MalformedURLException {
		RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when(
		).param(
			"groupId", groupId
		).param(
			"active", true
		).get(
			new URL(_url, "/api/jsonws/group/delete-group")
		).then(
		).statusCode(
			200
		);
	}

	private Folder _getFolder(String path) throws MalformedURLException {
		Response response = _getFolderResponse(path);

		Assert.assertEquals(200, response.statusCode());

		Folder folder = response.as(Folder.class);

		assertThat(folder.getDateCreated(), IsNull.notNullValue());
		assertThat(folder.getDateModified(), IsNull.notNullValue());
		assertThat(folder.getId(), IsNull.notNullValue());

		return folder;
	}

	private Response _getFolderResponse(String path)
		throws MalformedURLException {

		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when(
		).get(
			new URL(_headlessDocumentLibraryURL.toExternalForm() + path)
		).then(
		).extract(
		).response();
	}

	private Folder _updateFolder(Folder updatedFolder, String path)
		throws JsonProcessingException, MalformedURLException {

		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		).body(
			_inputObjectMapper.writeValueAsString(updatedFolder)
		).when(
		).put(
			new URL(_headlessDocumentLibraryURL.toExternalForm() + path)
		).then(
		).statusCode(
			200
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"id", IsNull.notNullValue()
		).extract(
		).response(
		).as(
			Folder.class
		);
	}

	private static ObjectMapper _inputObjectMapper;
	private static ObjectMapper _outputObjectMapper;

	private long _groupId;
	private URL _headlessDocumentLibraryURL;

	@ArquillianResource
	private URL _url;

	private abstract class IgnoreFieldsMixin {

		@JsonIgnore
		public abstract Date getDateCreated();

		@JsonIgnore
		public abstract Date getDateModified();

		@JsonIgnore
		public abstract Long getId();

	}

}