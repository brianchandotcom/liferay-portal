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
 * @author Ruben Pulido
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
			"_embedded.Folder.find {it.name == '" +
				FolderTestActivator.FOLDER_NAME +
					"'}.dateCreated",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == '" +
				FolderTestActivator.FOLDER_NAME +
					"'}.dateModified",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == '" +
				FolderTestActivator.FOLDER_NAME +
					"'}._links.documents",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == '" +
				FolderTestActivator.FOLDER_NAME +
					"'}._links.self.href",
			IsNull.notNullValue()
		).body(
			"_embedded.Folder.find {it.name == '" +
				FolderTestActivator.FOLDER_NAME +
					"'}._links.subFolders",
			IsNull.notNullValue()
		);
	}

	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}