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

package com.liferay.headless.document.library.test;

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

import javax.annotation.Generated;

import org.hamcrest.core.IsNull;

import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.datumedge.hamcrest.json.SameJSONAs;

/**
 * @author Rubén Pulido
 */
@Generated("")
public abstract class BaseFolderResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		_inputObjectMapper = new ObjectMapper();

		_inputObjectMapper.setSerializationInclusion(
			JsonInclude.Include.NON_NULL);

		_outputObjectMapper = new ObjectMapper();

		_outputObjectMapper.addMixIn(Folder.class, IgnoreIdFieldMixin.class);

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
	public void testDeleteFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = _createFolder(getFolder());

		String path = "/folder/" + folder.getId();

		_deleteFolder(path);

		Response response = _getFolderResponse(path);

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void testGetFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = _createFolder(getFolder());

		assertThat(
			_toJSON(_getFolder("/folder/" + folder.getId())),
			_sameJSONAs(folder));
	}

	@Test
	public void testPostDocumentsRepositoryFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = getFolder();

		assertThat(
			_toJSON(
				_createFolder(
					folder, "/documents-repository/" + _groupId + "/folder")),
			_sameJSONAs(folder));
	}

	@Test
	public void testPostFolderFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder parentFolder = _createFolder(getFolder());

		Folder folder = getFolder();

		assertThat(
			_toJSON(
				_createFolder(
					folder, "/folder/" + parentFolder.getId() + "/folder")),
			_sameJSONAs(folder));
	}

	@Test
	public void testUpdateFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = _createFolder(getFolder());

		Folder updatedFolder = getFolder();

		assertThat(
			_toJSON(_updateFolder(updatedFolder, "/folder/" + folder.getId())),
			_sameJSONAs(updatedFolder));
	}

	protected Folder getFolder() {
		return new Folder();
	}

	protected long getGroupId() {
		return _groupId;
	}

	private static SameJSONAs<? super String> _sameJSONAs(Folder folder)
		throws JsonProcessingException {

		return SameJSONAs.sameJSONAs(
			_toJSON(folder)
		).allowingExtraUnexpectedFields();
	}

	private static String _toJSON(Folder folder)
		throws JsonProcessingException {

		return _outputObjectMapper.writeValueAsString(folder);
	}

	private Folder _createFolder(Folder folder)
		throws JsonProcessingException, MalformedURLException {

		return _createFolder(
			folder, "/documents-repository/" + _groupId + "/folder");
	}

	private Folder _createFolder(Folder folder, String path)
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

		return response.as(Folder.class);
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

	private abstract class IgnoreIdFieldMixin {

		@JsonIgnore
		public abstract Long getId();

	}

}