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

import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.MalformedURLException;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunAsClient
@RunWith(Arquillian.class)
public class FolderResourceTest extends BaseFolderResourceTestCase {

	@Test
	public void testPostFolderFolder()
		throws JsonProcessingException, MalformedURLException {

		Folder parentFolder = createFolder(
			buildPostFolder(),
			"/documents-repository/" + getGroupId() + "/folder");

		Folder subfolder = buildPostFolder();

		assertThat(
			toJSON(
				createFolder(
					subfolder, "/folder/" + parentFolder.getId() + "/folder")),
			sameJSONAs(subfolder));
	}

	@Override
	protected Folder buildPostFolder() {
		Folder folder = new Folder();

		folder.setDescription(StringUtil.randomString(10));
		folder.setDocumentsRepositoryId(getGroupId());
		folder.setName(StringUtil.randomString(10));

		return folder;
	}

	@Override
	protected Folder buildPutFolder() {
		return buildPostFolder();
	}

}