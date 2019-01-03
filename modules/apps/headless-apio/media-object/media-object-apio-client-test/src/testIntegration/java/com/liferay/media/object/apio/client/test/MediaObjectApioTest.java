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

package com.liferay.media.object.apio.client.test;

import com.liferay.media.object.apio.client.test.internal.activator.MediaObjectTestActivator;
import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.apio.test.util.ApioClientBuilder;
import com.liferay.portal.apio.test.util.ContentSpaceApioTestUtil;
import com.liferay.portal.apio.test.util.FileTestUtil;
import com.liferay.portal.apio.test.util.MediaObjectTestUtil;

import io.restassured.response.Response;

import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunAsClient
@RunWith(Arquillian.class)
public class MediaObjectApioTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			MediaObjectTestActivator.class);
	}

	@Before
	public void setUp() throws MalformedURLException {
		URL rootEndpointURL = new URL(_url, "/o/api");

		_contentSpaceHrefURL = new URL(
			ContentSpaceApioTestUtil.getContentSpaceHref(
				rootEndpointURL.toExternalForm(),
				MediaObjectTestActivator.CONTENT_SPACE_NAME));

		Response response = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_contentSpaceHrefURL.toExternalForm()
		).follow(
			"_links.documentsRepository.href"
		).then(
		).extract(
		).response();

		_foldersHref = response.path("_links.folders.href");

		_documentsHref = response.path("_links.documents.href");
	}

	@After
	public void tearDown() {
		if (_documentHref != null) {
			_delete(_documentHref);
		}

		if (_folderHref != null) {
			_delete(_folderHref);
		}
	}

	@Test
	public void testCreateDocumentInDocumentRepository() {
		_documentHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "multipart/form-data"
		).multipart(
			"binaryFile", FileTestUtil.getFile("document.pdf", getClass())
		).when(
		).post(
			_documentsHref
		).then(
		).statusCode(
			200
		).body(
			"contentUrl", IsNull.notNullValue()
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"encodingFormat", Matchers.equalTo("application/pdf")
		).body(
			"fileExtension", Matchers.equalTo("pdf")
		).body(
			"keywords", IsNull.notNullValue()
		).body(
			"sizeInBytes", Matchers.greaterThan(0)
		).body(
			"title", Matchers.equalTo("document.pdf")
		).body(
			"_links.self.href", IsNull.notNullValue()
		).extract(
		).path(
			"_links.self.href"
		);
	}

	@Test
	public void testCreateDocumentInFolder() {
		String folderName = "My folder testCreateDocumentInFolder";

		_folderHref = _createFolder(_foldersHref, folderName);

		String documentsHref = _getDocumentsHref(folderName);

		_documentHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "multipart/form-data"
		).multipart(
			"binaryFile", FileTestUtil.getFile("document.pdf", getClass())
		).when(
		).post(
			documentsHref
		).then(
		).statusCode(
			200
		).body(
			"contentUrl", IsNull.notNullValue()
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"encodingFormat", Matchers.equalTo("application/pdf")
		).body(
			"fileExtension", Matchers.equalTo("pdf")
		).body(
			"keywords", IsNull.notNullValue()
		).body(
			"sizeInBytes", Matchers.greaterThan(0)
		).body(
			"title", Matchers.equalTo("document.pdf")
		).body(
			"_links.self.href", IsNull.notNullValue()
		).extract(
		).path(
			"_links.self.href"
		);
	}

	@Test
	public void testDeleteDocument() {
		String documentHref = MediaObjectTestUtil.createDocumentInRootFolder(
			_contentSpaceHrefURL.toExternalForm(),
			FileTestUtil.getFile("document.pdf", getClass()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).when(
		).delete(
			documentHref
		).then(
		).statusCode(
			Matchers.isOneOf(200, 204)
		);
	}

	@Test
	public void testGetDocumentsInDocumentsRepository() {
		_documentHref = MediaObjectTestUtil.createDocumentInRootFolder(
			_contentSpaceHrefURL.toExternalForm(),
			FileTestUtil.getFile("document.pdf", getClass()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_documentsHref
		).then(
		).statusCode(
			200
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"contentUrl",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"dateCreated",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"dateModified",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"encodingFormat",
			Matchers.equalTo("application/pdf")
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"fileExtension",
			Matchers.equalTo("pdf")
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"keywords",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"sizeInBytes",
			Matchers.greaterThan(0)
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"title",
			Matchers.equalTo("document.pdf")
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"_links.category.href",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"_links.creator",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"_links.folder",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"_links.self",
			IsNull.notNullValue()
		);
	}

	@Test
	public void testGetDocumentsInFolder() {
		String folderName = "My folder testGetDocumentsInFolder";

		_folderHref = _createFolder(_foldersHref, folderName);

		String documentsHref = _getDocumentsHref(folderName);

		_documentHref = MediaObjectTestUtil.createDocumentInFolder(
			documentsHref, FileTestUtil.getFile("document.pdf", getClass()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			documentsHref
		).then(
		).statusCode(
			200
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"contentUrl",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"dateCreated",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"dateModified",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"encodingFormat",
			Matchers.equalTo("application/pdf")
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"fileExtension",
			Matchers.equalTo("pdf")
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"keywords",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"sizeInBytes",
			Matchers.greaterThan(0)
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"title",
			Matchers.equalTo("document.pdf")
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"_links.category.href",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"_links.creator",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"_links.folder",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:Document'.find {it.title == 'document.pdf'}." +
				"_links.self",
			IsNull.notNullValue()
		);
	}

	private String _createFolder(String foldersHref, String folderName) {
		return ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"name\":\"" + folderName + "\"}"
		).when(
		).post(
			foldersHref
		).then(
		).statusCode(
			200
		).extract(
		).path(
			"_links.self.href"
		);
	}

	private void _delete(String href) {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).when(
		).delete(
			href
		);
	}

	private String _getDocumentsHref(String folderName) {
		return ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_foldersHref
		).then(
		).statusCode(
			200
		).extract(
		).path(
			"_embedded.Folder.find {it.name == '" + folderName + "'}._links." +
				"documents.href"
		);
	}

	private URL _contentSpaceHrefURL;
	private String _documentHref;
	private String _documentsHref;
	private String _folderHref;
	private String _foldersHref;

	@ArquillianResource
	private URL _url;

}