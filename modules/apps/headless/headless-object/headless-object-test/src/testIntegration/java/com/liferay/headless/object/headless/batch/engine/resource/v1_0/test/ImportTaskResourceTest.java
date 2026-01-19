/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.resource.v1_0.ImportTaskResource;
import com.liferay.headless.object.client.dto.v1_0.ObjectEntryFolder;
import com.liferay.headless.object.resource.v1_0.test.BaseObjectEntryFolderResourceTestCase;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.FeatureFlag;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jhosseph Gonzalez
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class ImportTaskResourceTest
	extends BaseObjectEntryFolderResourceTestCase {

	@Test
	public void testPostImportTask() throws Exception {
		String label = RandomTestUtil.randomString();
		String objectEntryFolderExternalReferenceCode =
			RandomTestUtil.randomString();
		String title = RandomTestUtil.randomString();

		ObjectEntryFolder objectEntryFolder = _postImportTask(
			"UPSERT", objectEntryFolderExternalReferenceCode, label, title);

		Assert.assertEquals(label, objectEntryFolder.getLabel());
		Assert.assertEquals(
			objectEntryFolderExternalReferenceCode,
			objectEntryFolder.getExternalReferenceCode());
		Assert.assertEquals(title, objectEntryFolder.getTitle());

		label = RandomTestUtil.randomString();
		title = RandomTestUtil.randomString();

		objectEntryFolder = _postImportTask(
			"UPSERT", objectEntryFolderExternalReferenceCode, label, title);

		Assert.assertEquals(label, objectEntryFolder.getLabel());
		Assert.assertEquals(
			objectEntryFolderExternalReferenceCode,
			objectEntryFolder.getExternalReferenceCode());
		Assert.assertEquals(title, objectEntryFolder.getTitle());

		label = RandomTestUtil.randomString();
		objectEntryFolderExternalReferenceCode = RandomTestUtil.randomString();
		title = RandomTestUtil.randomString();

		objectEntryFolder = _postImportTask(
			"INSERT", objectEntryFolderExternalReferenceCode, label, title);

		Assert.assertEquals(label, objectEntryFolder.getLabel());
		Assert.assertEquals(
			objectEntryFolderExternalReferenceCode,
			objectEntryFolder.getExternalReferenceCode());
		Assert.assertEquals(title, objectEntryFolder.getTitle());
	}

	private ObjectEntryFolder _postImportTask(
			String createStrategy, String externalReferenceCode, String label,
			String title)
		throws Exception {

		ImportTaskResource importTaskResource = ImportTaskResource.builder(
		).authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).parameter(
			"siteExternalReferenceCode", testGroup.getExternalReferenceCode()
		).parameter(
			"taskItemDelegateName", "depot-object-entry-folder"
		).build();

		ImportTask importTask = importTaskResource.postImportTask(
			"com.liferay.headless.object.dto.v1_0.ObjectEntryFolder", null, null, null, createStrategy,
			null, null, null, null,
			JSONUtil.putAll(
				JSONUtil.put(
					"externalReferenceCode", externalReferenceCode
				).put(
					"label", label
				).put(
					"title", title
				)
			).toString());

		waitForFinish("COMPLETED", JSONUtil.put("id", importTask.getId()));

		return objectEntryFolderResource.
			getScopeScopeKeyObjectEntryFolderByExternalReferenceCode(
				String.valueOf(testGroup.getGroupId()), externalReferenceCode);
	}

}