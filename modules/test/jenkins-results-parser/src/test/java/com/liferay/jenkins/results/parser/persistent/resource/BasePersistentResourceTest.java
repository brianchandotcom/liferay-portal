/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.persistent.resource;

import com.liferay.jenkins.results.parser.CloudBucketUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Brittney Nguyen
 * @author Michael Hashimoto
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

	@Test
	public void testSave() {
		BasePersistentResource basePersistentResource =
			_getBasePersistentResource(true);

		List<String> artifactS3ObjectPaths = _getArtifactS3ObjectPaths(
			basePersistentResource, 3);
		String dataS3ObjectPath = _getDataS3ObjectPath(basePersistentResource);

		List<String> writtenS3ObjectPaths = new ArrayList<>();

		try (MockedStatic<CloudBucketUtil> cloudBucketUtilMockedStatic =
				_mockCloudBucketUtil(
					new HashSet<>(
						Arrays.asList(
							artifactS3ObjectPaths.get(0),
							artifactS3ObjectPaths.get(2))),
					Collections.emptySet(), writtenS3ObjectPaths)) {

			basePersistentResource.save();

			cloudBucketUtilMockedStatic.verify(
				() -> CloudBucketUtil.uploadS3Object(
					Mockito.anyString(), Mockito.eq(dataS3ObjectPath)));
		}

		Assert.assertEquals(
			Arrays.asList(
				dataS3ObjectPath, artifactS3ObjectPaths.get(0),
				artifactS3ObjectPaths.get(2)),
			writtenS3ObjectPaths);
	}

	@Test
	public void testSaveBuildCachingDisabled() {
		BasePersistentResource basePersistentResource =
			_getBasePersistentResource(false);

		_getArtifactS3ObjectPaths(basePersistentResource, 2);

		Mockito.doCallRealMethod(
		).when(
			basePersistentResource
		).getDataJSONObject();

		try (MockedStatic<CloudBucketUtil> cloudBucketUtilMockedStatic =
				_mockCloudBucketUtil(
					Collections.emptySet(), Collections.emptySet(),
					new ArrayList<>())) {

			basePersistentResource.save();

			JSONObject dataJSONObject =
				basePersistentResource.getDataJSONObject();

			Assert.assertEquals(
				String.valueOf(PersistentResource.Status.SUCCESS),
				dataJSONObject.getString("status"));

			cloudBucketUtilMockedStatic.verifyNoInteractions();
		}
	}

	@Test
	public void testSaveFailure() {
		BasePersistentResource basePersistentResource =
			_getBasePersistentResource(true);

		List<String> artifactS3ObjectPaths = _getArtifactS3ObjectPaths(
			basePersistentResource, 2);
		String dataS3ObjectPath = _getDataS3ObjectPath(basePersistentResource);

		List<String> writtenS3ObjectPaths = new ArrayList<>();

		try (MockedStatic<CloudBucketUtil> cloudBucketUtilMockedStatic =
				_mockCloudBucketUtil(
					new HashSet<>(artifactS3ObjectPaths),
					Collections.singleton(artifactS3ObjectPaths.get(0)),
					writtenS3ObjectPaths)) {

			basePersistentResource.save();
		}

		Assert.assertEquals(
			Arrays.asList(
				dataS3ObjectPath, artifactS3ObjectPaths.get(0),
				artifactS3ObjectPaths.get(1)),
			writtenS3ObjectPaths);
	}

	@Test
	public void testTouch() {
		BasePersistentResource basePersistentResource =
			_getBasePersistentResource(true);

		List<String> artifactS3ObjectPaths = _getArtifactS3ObjectPaths(
			basePersistentResource, 2);
		String dataS3ObjectPath = _getDataS3ObjectPath(basePersistentResource);

		Set<String> availableS3ObjectPaths = new HashSet<>(
			artifactS3ObjectPaths);

		availableS3ObjectPaths.add(dataS3ObjectPath);

		List<String> writtenS3ObjectPaths = new ArrayList<>();

		try (MockedStatic<CloudBucketUtil> cloudBucketUtilMockedStatic =
				_mockCloudBucketUtil(
					availableS3ObjectPaths, Collections.emptySet(),
					writtenS3ObjectPaths)) {

			basePersistentResource.touch();
			basePersistentResource.touch();
		}

		Assert.assertEquals(
			Arrays.asList(
				dataS3ObjectPath, artifactS3ObjectPaths.get(0),
				artifactS3ObjectPaths.get(1)),
			writtenS3ObjectPaths);

		Assert.assertTrue(basePersistentResource.isTouched());

		basePersistentResource = _getBasePersistentResource(true);

		artifactS3ObjectPaths = _getArtifactS3ObjectPaths(
			basePersistentResource, 1);
		dataS3ObjectPath = _getDataS3ObjectPath(basePersistentResource);

		availableS3ObjectPaths = new HashSet<>(artifactS3ObjectPaths);

		availableS3ObjectPaths.add(dataS3ObjectPath);

		writtenS3ObjectPaths.clear();

		try (MockedStatic<CloudBucketUtil> cloudBucketUtilMockedStatic =
				_mockCloudBucketUtil(
					availableS3ObjectPaths,
					Collections.singleton(artifactS3ObjectPaths.get(0)),
					writtenS3ObjectPaths)) {

			basePersistentResource.touch();

			Assert.assertFalse(basePersistentResource.isTouched());

			basePersistentResource.touch();

			Assert.assertTrue(basePersistentResource.isTouched());

			basePersistentResource.touch();
		}

		Assert.assertEquals(
			Arrays.asList(
				dataS3ObjectPath, artifactS3ObjectPaths.get(0),
				dataS3ObjectPath, artifactS3ObjectPaths.get(0)),
			writtenS3ObjectPaths);
	}

	@Test
	public void testTouchBuildCachingDisabled() {
		BasePersistentResource basePersistentResource =
			_getBasePersistentResource(false);

		_getArtifactS3ObjectPaths(basePersistentResource, 1);

		try (MockedStatic<CloudBucketUtil> cloudBucketUtilMockedStatic =
				_mockCloudBucketUtil(
					Collections.emptySet(), Collections.emptySet(),
					new ArrayList<>())) {

			basePersistentResource.touch();

			cloudBucketUtilMockedStatic.verifyNoInteractions();
		}

		Assert.assertTrue(basePersistentResource.isTouched());
	}

	private List<String> _getArtifactS3ObjectPaths(
		BasePersistentResource basePersistentResource, int count) {

		List<String> artifactS3ObjectPaths = new ArrayList<>();

		List<PersistentResource.Artifact> artifacts = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			PersistentResource.Artifact artifact =
				new PersistentResource.Artifact(
					RandomTestUtil.randomString(), basePersistentResource);

			artifacts.add(artifact);
			artifactS3ObjectPaths.add(artifact.getS3ObjectPath());
		}

		Mockito.doReturn(
			artifacts
		).when(
			basePersistentResource
		).getArtifacts();

		return artifactS3ObjectPaths;
	}

	private BasePersistentResource _getBasePersistentResource(
		boolean buildCachingEnabled) {

		BasePersistentResource basePersistentResource = Mockito.mock(
			BasePersistentResource.class);

		Mockito.doCallRealMethod(
		).when(
			basePersistentResource
		).isTouched();

		Mockito.doCallRealMethod(
		).when(
			basePersistentResource
		).save();

		Mockito.doCallRealMethod(
		).when(
			basePersistentResource
		).touch();

		Mockito.doReturn(
			"s3://" + RandomTestUtil.randomString()
		).when(
			basePersistentResource
		).getBaseS3ObjectPath();

		Mockito.doReturn(
			PersistentResource.Status.SUCCESS
		).when(
			basePersistentResource
		).getStatus();

		Mockito.doReturn(
			buildCachingEnabled
		).when(
			basePersistentResource
		).isBuildCachingEnabled();

		return basePersistentResource;
	}

	private String _getDataS3ObjectPath(
		BasePersistentResource basePersistentResource) {

		return basePersistentResource.getBaseS3ObjectPath() + "/data.json.gz";
	}

	private MockedStatic<CloudBucketUtil> _mockCloudBucketUtil(
		Set<String> availableS3ObjectPaths, Set<String> failingS3ObjectPaths,
		List<String> writtenS3ObjectPaths) {

		return Mockito.mockStatic(
			CloudBucketUtil.class,
			invocation -> {
				Object[] arguments = invocation.getArguments();

				String s3ObjectPath = (String)arguments[arguments.length - 1];

				Method method = invocation.getMethod();

				String methodName = method.getName();

				if (methodName.equals("isS3ObjectPathAvailable")) {
					return availableS3ObjectPaths.contains(s3ObjectPath);
				}

				writtenS3ObjectPaths.add(s3ObjectPath);

				if (methodName.equals("touchS3File") &&
					failingS3ObjectPaths.contains(s3ObjectPath)) {

					throw new IOException(s3ObjectPath);
				}

				return null;
			});
	}

}