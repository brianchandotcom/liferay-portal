/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectFolder;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Murilo Stodolni
 */
@RunWith(Arquillian.class)
public class ObjectFolderResourceTest extends BaseObjectFolderResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectFolder() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectFolderByExternalReferenceCode() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectFolderByExternalReferenceCodeNotFound() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectFolderNotFound() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectFoldersPage() {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"label"};
	}

	@Override
	protected ObjectFolder randomObjectFolder() throws Exception {
		ObjectFolder objectFolder = super.randomObjectFolder();

		objectFolder.setLabel(
			Collections.singletonMap("en_US", RandomTestUtil.randomString()));

		return objectFolder;
	}

	@Override
	protected ObjectFolder testDeleteObjectFolder_addObjectFolder()
		throws Exception {

		return testPostObjectFolder_addObjectFolder(randomObjectFolder());
	}

	@Override
	protected ObjectFolder testGetObjectFolder_addObjectFolder()
		throws Exception {

		return testPostObjectFolder_addObjectFolder(randomObjectFolder());
	}

	@Override
	protected ObjectFolder
			testGetObjectFolderByExternalReferenceCode_addObjectFolder()
		throws Exception {

		return testPostObjectFolder_addObjectFolder(randomObjectFolder());
	}

	@Override
	protected ObjectFolder testGetObjectFoldersPage_addObjectFolder(
			ObjectFolder objectFolder)
		throws Exception {

		return testPostObjectFolder_addObjectFolder(objectFolder);
	}

	@Override
	protected ObjectFolder testGraphQLObjectFolder_addObjectFolder()
		throws Exception {

		return testPostObjectFolder_addObjectFolder(randomObjectFolder());
	}

	@Override
	protected ObjectFolder testPatchObjectFolder_addObjectFolder()
		throws Exception {

		return testPostObjectFolder_addObjectFolder(randomObjectFolder());
	}

	@Override
	protected ObjectFolder testPostObjectFolder_addObjectFolder(
			ObjectFolder objectFolder)
		throws Exception {

		return objectFolderResource.postObjectFolder(objectFolder);
	}

	@Override
	protected ObjectFolder testPutObjectFolder_addObjectFolder()
		throws Exception {

		return testPostObjectFolder_addObjectFolder(randomObjectFolder());
	}

	@Override
	protected ObjectFolder
			testPutObjectFolderByExternalReferenceCode_addObjectFolder()
		throws Exception {

		return testPostObjectFolder_addObjectFolder(randomObjectFolder());
	}

	@Override
	protected ObjectFolder
			testPutObjectFolderByExternalReferenceCode_createObjectFolder()
		throws Exception {

		return testPostObjectFolder_addObjectFolder(randomObjectFolder());
	}

}