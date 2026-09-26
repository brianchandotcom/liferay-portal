/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.persistent.resource;

import com.liferay.jenkins.results.parser.RandomTestUtil;

import java.io.File;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Brittney Nguyen
 */
public class BasePersistentResourceTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testDownload() {
		BasePersistentResource basePersistentResource = Mockito.mock(
			BasePersistentResource.class);

		Mockito.doCallRealMethod(
		).when(
			basePersistentResource
		).download(
			Mockito.anyString(), Mockito.any(File.class)
		);

		Mockito.doReturn(
			Collections.singleton(RandomTestUtil.randomString())
		).when(
			basePersistentResource
		).getArtifactNames();

		String artifactName = RandomTestUtil.randomString();

		try {
			basePersistentResource.download(
				artifactName, new File(RandomTestUtil.randomString()));

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			testEquals(
				artifactName + " does not exist",
				runtimeException.getMessage());
		}
	}

	@Test
	public void testGetArtifacts() {
		BasePersistentResource basePersistentResource = Mockito.mock(
			BasePersistentResource.class);

		Mockito.doReturn(
			Collections.singleton(RandomTestUtil.randomString())
		).when(
			basePersistentResource
		).getArtifactNames();

		Mockito.doCallRealMethod(
		).when(
			basePersistentResource
		).getArtifacts();

		List<PersistentResource.Artifact> artifacts1 =
			basePersistentResource.getArtifacts();

		testEquals(1, artifacts1.size());

		List<PersistentResource.Artifact> artifacts2 =
			basePersistentResource.getArtifacts();

		testEquals(artifacts1, artifacts2);
	}

}