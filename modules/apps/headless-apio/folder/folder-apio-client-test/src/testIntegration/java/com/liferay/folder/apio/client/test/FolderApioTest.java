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

package com.liferay.folder.apio.client.test;

import com.liferay.folder.apio.client.test.internal.activator.FolderTestActivator;
import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.apio.test.util.ApioClientBuilder;

import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunAsClient
@RunWith(Arquillian.class)
public class FolderApioTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(FolderTestActivator.class);
	}

	@Before
	public void setUp() throws MalformedURLException {
		_rootEndpointURL = new URL(_url, "/o/api");
	}

	@Test
	public void testCreateFolder() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).then(
		).extract(
		).path(
			"_links.folders.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"description\":\"My folder description\",\"name\":\"My folder " +
				"testCreateFolder\"}"
		).when(
		).post(
			href
		).then(
		).statusCode(
			200
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"description", Matchers.equalTo("My folder description")
		).body(
			"name", Matchers.equalTo("My folder testCreateFolder")
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testCreateSubfolder() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).then(
		).extract(
		).path(
			"_links.folders.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"description\":\"My folder description\",\"name\":\"My folder " +
				"testCreateSubfolder\"}"
		).when(
		).post(
			href
		).then(
		).statusCode(
			200
		);

		String subfoldersPath = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).follow(
			"_links.folders.href"
		).then(
		).statusCode(
			200
		).extract(
		).path(
			"_embedded.Folder.find {it.name == 'My folder " +
				"testCreateSubfolder'}._links.subFolders.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"description\":\"My subfolder description\",\"name\":\"My " +
				"subfolder\"}"
		).when(
		).post(
			subfoldersPath
		).then(
		).statusCode(
			200
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"description", Matchers.equalTo("My subfolder description")
		).body(
			"name", Matchers.equalTo("My subfolder")
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testDeleteFolder() {
		String foldersHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).then(
		).extract(
		).path(
			"_links.folders.href"
		);

		String folderHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"description\":\"My folder description\",\"name\":\"My folder " +
				"testDeleteFolder\"}"
		).when(
		).post(
			foldersHref
		).then(
		).extract(
		).path(
			"_links.self.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).when(
		).delete(
			folderHref
		).then(
		).statusCode(
			Matchers.isOneOf(200, 204)
		);
	}

	@Test
	public void testDocumentsRepositoryContainsLinksToFoldersAndDocuments() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME + "'}._links." +
					"documentsRepository.href"
		).then(
		).statusCode(
			200
		).body(
			"_links.documents.href", IsNull.notNullValue()
		).body(
			"_links.folders.href", IsNull.notNullValue()
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testDocumentsRepositoryLinkExistsInContentSpace() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).then(
		).statusCode(
			200
		).body(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME + "'}._links." +
					"documentsRepository.href",
			IsNull.notNullValue()
		);
	}

	@Test
	public void testGetFolders() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).then(
		).extract(
		).path(
			"_links.folders.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"description\":\"My folder description\",\"name\":\"My folder " +
				"testGetFolders\"}"
		).when(
		).post(
			href
		).then(
		).statusCode(
			200
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).follow(
			"_links.folders.href"
		).then(
		).statusCode(
			200
		).body(
			"_embedded.Folder.find {it.name == 'My folder testGetFolders'}." +
				"dateCreated",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == 'My folder testGetFolders'}." +
				"dateModified",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == 'My folder testGetFolders'}." +
				"_links.documents",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == 'My folder testGetFolders'}." +
				"_links.self.href",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == 'My folder testGetFolders'}." +
				"_links.subFolders",
			IsNull.notNullValue()
		);
	}

	@Test
	public void testGetSubfolders() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).then(
		).extract(
		).path(
			"_links.folders.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"description\":\"My folder description\",\"name\":\"My folder " +
				"testGetSubfolders\"}"
		).when(
		).post(
			href
		).then(
		).statusCode(
			200
		);

		String subfoldersPath = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).follow(
			"_links.folders.href"
		).then(
		).statusCode(
			200
		).extract(
		).path(
			"_embedded.Folder.find {it.name == 'My folder " +
				"testGetSubfolders'}._links.subFolders.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"description\":\"My subfolder description\",\"name\":\"My " +
				"subfolder\"}"
		).when(
		).post(
			subfoldersPath
		).then(
		).statusCode(
			200
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).follow(
			"_links.folders.href"
		).follow(
			"_embedded.Folder.find {it.name == 'My folder " +
				"testGetSubfolders'}._links.subFolders.href"
		).then(
		).statusCode(
			200
		).body(
			"_embedded.Folder.find {it.name == 'My subfolder'}.dateCreated",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == 'My subfolder'}.dateModified",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == 'My subfolder'}.description",
			Matchers.equalTo("My subfolder description")
		).body(
			"_embedded.Folder.find {it.name == 'My subfolder'}._links." +
				"documents",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == 'My subfolder'}._links.self." +
				"href",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == 'My subfolder'}._links." +
				"subFolders",
			IsNull.notNullValue()
		);
	}

	@Test
	public void testUpdateFolder() {
		String foldersHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find {it.name == '" +
				FolderTestActivator.CONTENT_SPACE_NAME +
					"'}._links.documentsRepository.href"
		).then(
		).extract(
		).path(
			"_links.folders.href"
		);

		String folderHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"description\":\"My folder description\",\"name\":\"My folder " +
				"testUpdateFolder\"}"
		).when(
		).post(
			foldersHref
		).then(
		).extract(
		).path(
			"_links.self.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			"{\"description\":\"My folder description modified\",\"name\":\"" +
				"My folder testUpdateFolder modified\"}"
		).when(
		).put(
			folderHref
		).then(
		).body(
			"description", Matchers.equalTo("My folder description modified")
		).body(
			"name", Matchers.equalTo("My folder testUpdateFolder modified")
		);
	}

	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}