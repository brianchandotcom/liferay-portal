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

package com.liferay.headless.document.library.client.test;

import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.document.library.dto.Folder;
import com.liferay.portal.kernel.util.StringUtil;

import io.restassured.RestAssured;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Map;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.datumedge.hamcrest.json.SameJSONAs;

/**
 * @author Rubén Pulido
 */
@RunAsClient
@RunWith(Arquillian.class)
public class FolderResourceTest {

	@BeforeClass
	public static void setUpClass() {
		_inputObjectMapper = new ObjectMapper();

		_inputObjectMapper.setSerializationInclusion(
			JsonInclude.Include.NON_NULL);

		_outputObjectMapper = new ObjectMapper();

		_outputObjectMapper.addMixIn(Folder.class, IgnoreIdFieldMixin.class);
	}

	@Before
	public void setUp() throws MalformedURLException {
		_headlessDocumentLibraryURL = new URL(
			_url.toExternalForm() + "/o/headless-document-library/1.0.0");

		_groupId = Long.valueOf(
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

	@After
	public void tearDown() throws MalformedURLException {
		RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when(
		).param(
			"groupId", _groupId
		).param(
			"active", true
		).get(
			new URL(_url, "/api/jsonws/group/delete-group")
		).then(
		).statusCode(
			200
		);
	}

	@Test
	public void testDeleteFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = _getFolder();

		int folderId = _createFolder(folder);

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
			new URL(
				_headlessDocumentLibraryURL.toExternalForm() + "/folder/" +
					folderId)
		).then(
		).statusCode(
			200
		);
	}

	@Test
	public void testGetDocumentsRepositoryFolderPage()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = _getFolder();

		int folderId = _createFolder(folder);

		Map firstFolderMap = RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when(
		).get(
			new URL(
				_headlessDocumentLibraryURL.toExternalForm() +
					"/documents-repository/" + _groupId + "/folder/")
		).then(
		).statusCode(
			200
		).body(
			"itemsPerPage", Matchers.equalTo(20)
		).body(
			"lastPageNumber", Matchers.equalTo(1)
		).body(
			"pageNumber", Matchers.equalTo(1)
		).body(
			"totalCount", Matchers.equalTo(1)
		).body(
			"items[0].id", Matchers.equalTo(folderId)
		).extract(
		).path(
			"items[0]"
		);

		Folder firstFolder = _outputObjectMapper.convertValue(
			firstFolderMap, Folder.class);

		assertThat(
			_outputObjectMapper.writeValueAsString(firstFolder),
			SameJSONAs.sameJSONAs(
				_outputObjectMapper.writeValueAsString(folder)
			).allowingExtraUnexpectedFields());
	}

	@Test
	public void testGetFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = _getFolder();

		int folderId = _createFolder(folder);

		RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when(
		).get(
			new URL(
				_headlessDocumentLibraryURL.toExternalForm() + "/folder/" +
					folderId)
		).then(
		).statusCode(
			200
		).body(
			SameJSONAs.sameJSONAs(
				_outputObjectMapper.writeValueAsString(folder)
			).allowingExtraUnexpectedFields()
		).body(
			"id", Matchers.equalTo(folderId)
		);
	}

	@Test
	public void testGetFolderFolderPage()
		throws JsonProcessingException, MalformedURLException {

		Folder parentFolder = _getFolder();

		int parentFolderId = _createFolder(parentFolder);

		Folder folder = _getFolder();

		int folderId = _createSubfolder(parentFolderId, folder);

		Map firstFolderMap = RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when(
		).get(
			new URL(
				_headlessDocumentLibraryURL.toExternalForm() + "/folder/" +
					parentFolderId + "/folder/")
		).then(
		).statusCode(
			200
		).body(
			"itemsPerPage", Matchers.equalTo(20)
		).body(
			"lastPageNumber", Matchers.equalTo(1)
		).body(
			"pageNumber", Matchers.equalTo(1)
		).body(
			"totalCount", Matchers.equalTo(1)
		).body(
			"items[0].id", Matchers.equalTo(folderId)
		).extract(
		).path(
			"items[0]"
		);

		Folder firstFolder = _outputObjectMapper.convertValue(
			firstFolderMap, Folder.class);

		assertThat(
			_outputObjectMapper.writeValueAsString(firstFolder),
			SameJSONAs.sameJSONAs(
				_outputObjectMapper.writeValueAsString(folder)
			).allowingExtraUnexpectedFields());
	}

	@Test
	public void testPostDocumentsRepositoryFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = _getFolder();

		RestAssured.given(
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
			new URL(
				_headlessDocumentLibraryURL.toExternalForm() +
					"/documents-repository/" + _groupId + "/folder")
		).then(
		).statusCode(
			200
		).body(
			SameJSONAs.sameJSONAs(
				_outputObjectMapper.writeValueAsString(folder)
			).allowingExtraUnexpectedFields()
		).body(
			"id", IsNull.notNullValue()
		);
	}

	@Test
	public void testPostFolderFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = _getFolder();

		int parentFolderId = _createFolder(folder);

		RestAssured.given(
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
			new URL(
				_headlessDocumentLibraryURL.toExternalForm() + "/folder/" +
					parentFolderId + "/folder")
		).then(
		).statusCode(
			200
		).body(
			SameJSONAs.sameJSONAs(
				_outputObjectMapper.writeValueAsString(folder)
			).allowingExtraUnexpectedFields()
		).body(
			"id", IsNull.notNullValue()
		);
	}

	@Test
	public void testUpdateFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder folder = _getFolder();

		int folderId = _createFolder(folder);

		Folder updatedFolder = _getFolder();

		RestAssured.given(
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
			new URL(
				_headlessDocumentLibraryURL.toExternalForm() + "/folder/" +
					folderId)
		).then(
		).statusCode(
			200
		).body(
			SameJSONAs.sameJSONAs(
				_outputObjectMapper.writeValueAsString(updatedFolder)
			).allowingExtraUnexpectedFields()
		).body(
			"id", Matchers.equalTo(folderId)
		);
	}

	private int _createFolder(Folder folder)
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
			new URL(
				_headlessDocumentLibraryURL.toExternalForm() +
					"/documents-repository/" + _groupId + "/folder")
		).then(
		).extract(
		).path(
			"id"
		);
	}

	private int _createSubfolder(int parentFolderId, Folder folder)
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
			new URL(
				_headlessDocumentLibraryURL.toExternalForm() + "/folder/" +
					parentFolderId + "/folder")
		).then(
		).extract(
		).path(
			"id"
		);
	}

	private Folder _getFolder() {
		Folder folder = new Folder();

		folder.setDescription(StringUtil.randomString(10) + " description");
		folder.setName(StringUtil.randomString(10));

		return folder;
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