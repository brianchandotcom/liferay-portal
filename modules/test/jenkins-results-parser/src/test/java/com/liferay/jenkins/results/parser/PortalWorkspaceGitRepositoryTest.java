/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * @author Kenji Heigel
 */
public class PortalWorkspaceGitRepositoryTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetPortalTestPropertiesReadsBundleVersionFromEnvironment()
		throws Exception {

		mockEnvironment(
			Collections.singletonMap(
				"PORTAL_LATEST_BUNDLE_VERSION", "environment.value"));

		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"portal.bundle.tomcat[environment.value]", "dummy.value");

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		Properties portalTestProperties = new Properties();

		portalTestProperties.setProperty(
			"test.released.release.bundle.version", "property.value");

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_newPortalWorkspaceGitRepository();

		Mockito.doReturn(
			portalTestProperties
		).when(
			portalWorkspaceGitRepository
		).getProperties(
			"portal.test.properties"
		);

		mockShell();

		Properties testProperties = _getPortalTestProperties(
			portalWorkspaceGitRepository);

		Assert.assertEquals(
			"The bundle version must be read from the " +
				"PORTAL_LATEST_BUNDLE_VERSION environment variable",
			"environment.value",
			testProperties.getProperty("test.released.release.bundle.version"));

		Assert.assertEquals(
			"The bundle zip URL must be resolved from the " +
				"portal.bundle.tomcat property keyed by the bundle version",
			"dummy.value",
			testProperties.getProperty(
				"test.released.test.portal.bundle.zip.url"));
	}

	@Test
	public void testPrepareGitWorkingDirectory() throws Exception {
		_testPrepareGitWorkingDirectory(false);
		_testPrepareGitWorkingDirectory(true);
	}

	@Test
	public void testSetUpAdditionalCaches() throws Exception {
		_testSetUpAdditionalCaches(false);
		_testSetUpAdditionalCaches(true);
	}

	@Test
	public void testSetUpFailure() throws Exception {
		mockShell();

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_newPortalWorkspaceGitRepository();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).isSetUp();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).setSetUp(
			Mockito.anyBoolean()
		);

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).setUp();

		IOException ioException = new IOException(
			"The additional caches could not be set up");

		Mockito.doThrow(
			ioException
		).when(
			portalWorkspaceGitRepository
		).setUpAdditionalCaches();

		RuntimeException runtimeException = Assert.assertThrows(
			RuntimeException.class, portalWorkspaceGitRepository::setUp);

		Assert.assertSame(
			"setUp() must rethrow the mid-sequence IOException as the " +
				"RuntimeException cause",
			ioException, runtimeException.getCause());

		Assert.assertFalse(
			"A failed setUp() must not mark the repository set up",
			portalWorkspaceGitRepository.isSetUp());
	}

	@Test
	public void testSetUpRunsOnce() throws Exception {
		mockEnvironment(
			Collections.singletonMap("MASTER_NETWORK_NAME", "aws-network"));

		Properties buildProperties = new Properties();

		buildProperties.setProperty("git.archive.enabled", "true");

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		BuildDatabase buildDatabase = Mockito.mock(BuildDatabase.class);

		BuildDatabaseUtil.setBuildDatabase(buildDatabase);

		mockShell();

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_newPortalWorkspaceGitRepository();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).isSetUp();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).setSetUp(
			Mockito.anyBoolean()
		);

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).setUp();

		Assert.assertFalse(
			"The repository must not be set up before setUp() runs",
			portalWorkspaceGitRepository.isSetUp());

		portalWorkspaceGitRepository.setUp();

		Assert.assertTrue(
			"The repository must be set up after setUp() runs",
			portalWorkspaceGitRepository.isSetUp());

		InOrder inOrder = Mockito.inOrder(
			buildDatabase, portalWorkspaceGitRepository);

		inOrder.verify(
			portalWorkspaceGitRepository
		).prepareGitWorkingDirectory();

		inOrder.verify(
			portalWorkspaceGitRepository
		).setUpAdditionalCaches();

		inOrder.verify(
			buildDatabase
		).putWorkspaceGitRepository(
			Mockito.anyString(), Mockito.any(WorkspaceGitRepository.class)
		);

		portalWorkspaceGitRepository.setUp();

		Mockito.verify(
			portalWorkspaceGitRepository, Mockito.times(1)
		).prepareGitWorkingDirectory();

		Mockito.verify(
			portalWorkspaceGitRepository, Mockito.times(1)
		).setUpAdditionalCaches();

		Mockito.verify(
			buildDatabase, Mockito.times(1)
		).putWorkspaceGitRepository(
			Mockito.anyString(), Mockito.any(WorkspaceGitRepository.class)
		);
	}

	private Properties _getPortalTestProperties(
			PortalWorkspaceGitRepository portalWorkspaceGitRepository)
		throws Exception {

		Method method = PortalWorkspaceGitRepository.class.getDeclaredMethod(
			"_getPortalTestProperties");

		method.setAccessible(true);

		return (Properties)method.invoke(portalWorkspaceGitRepository);
	}

	private GitWorkingDirectory _newGitWorkingDirectory() {
		return Mockito.mock(GitWorkingDirectory.class);
	}

	private LocalGitBranch _newLocalGitBranch() {
		LocalGitBranch localGitBranch = Mockito.mock(LocalGitBranch.class);

		Mockito.doReturn(
			"1234567890123456789012345678901234567890"
		).when(
			localGitBranch
		).getSHA();

		return localGitBranch;
	}

	private PortalWorkspaceGitRepository _newPortalWorkspaceGitRepository()
		throws Exception {

		File workingDirectory = File.createTempFile("portal-workspace-", null);

		workingDirectory.delete();

		workingDirectory.mkdir();

		File gitDirectory = new File(workingDirectory, ".git");

		gitDirectory.mkdir();

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			Mockito.mock(PortalWorkspaceGitRepository.class);

		Mockito.doReturn(
			"1234567890123456789012345678901234567890"
		).when(
			portalWorkspaceGitRepository
		).getBaseBranchSHA();

		Mockito.doReturn(
			workingDirectory
		).when(
			portalWorkspaceGitRepository
		).getDirectory();

		Mockito.doReturn(
			"liferay-portal"
		).when(
			portalWorkspaceGitRepository
		).getDirectoryName();

		Mockito.doReturn(
			_newGitWorkingDirectory()
		).when(
			portalWorkspaceGitRepository
		).getGitWorkingDirectory();

		Mockito.doReturn(
			_newLocalGitBranch()
		).when(
			portalWorkspaceGitRepository
		).getLocalGitBranch();

		Mockito.doReturn(
			"0987654321098765432109876543210987654321"
		).when(
			portalWorkspaceGitRepository
		).getSenderBranchSHA();

		Mockito.doReturn(
			"master"
		).when(
			portalWorkspaceGitRepository
		).getUpstreamBranchName();

		return portalWorkspaceGitRepository;
	}

	private void _testPrepareGitWorkingDirectory(boolean gitArchiveEnabled)
		throws Exception {

		mockEnvironment(
			Collections.singletonMap("MASTER_NETWORK_NAME", "aws-network"));

		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"binaries.cache.enabled", String.valueOf(!gitArchiveEnabled));
		buildProperties.setProperty(
			"cloud.ci.s3.bucket.dist.path", "s3://liferayci-dist/dist");
		buildProperties.setProperty(
			"git.archive.enabled", String.valueOf(gitArchiveEnabled));

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		Shell shell = mockShell();

		if (gitArchiveEnabled) {
			Mockito.doReturn(
				new Shell.ExecutionResult(0, "", "")
			).when(
				shell
			).doExecute(
				Mockito.any(Shell.ExecutionRequest.class)
			);
		}

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_newPortalWorkspaceGitRepository();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).isBinariesCacheEnabled();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).prepareGitWorkingDirectory();

		portalWorkspaceGitRepository.prepareGitWorkingDirectory();

		Mockito.verify(
			shell, gitArchiveEnabled ? Mockito.atLeastOnce() : Mockito.never()
		).doExecute(
			Mockito.argThat(
				executionRequest -> hasCommand(
					executionRequest, "git-archives"))
		);

		Assert.assertEquals(
			"The binaries cache must be enabled if and only if the git " +
				"archive is disabled",
			!gitArchiveEnabled,
			portalWorkspaceGitRepository.isBinariesCacheEnabled());
	}

	private void _testSetUpAdditionalCaches(boolean binariesCacheEnabled)
		throws Exception {

		mockEnvironment(
			Collections.singletonMap("MASTER_NETWORK_NAME", "aws-network"));

		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"binaries.cache.enabled", String.valueOf(binariesCacheEnabled));

		if (binariesCacheEnabled) {
			buildProperties.setProperty(
				"binaries.cache.s3.path",
				"s3://liferayci-file-propagator/binaries-cache/master.tar.gz");
		}

		buildProperties.setProperty(
			"git.archive.enabled", String.valueOf(!binariesCacheEnabled));

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		Shell shell = mockShell();

		if (binariesCacheEnabled) {
			Mockito.doReturn(
				new Shell.ExecutionResult(0, "", "")
			).when(
				shell
			).doExecute(
				Mockito.any(Shell.ExecutionRequest.class)
			);
		}

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_newPortalWorkspaceGitRepository();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).isBinariesCacheEnabled();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).setUpAdditionalCaches();

		portalWorkspaceGitRepository.setUpAdditionalCaches();

		Mockito.verify(
			shell, binariesCacheEnabled ? Mockito.times(1) : Mockito.never()
		).doExecute(
			Mockito.argThat(
				executionRequest -> hasCommand(
					executionRequest, "aws s3 cp", "binaries-cache.tar.gz"))
		);

		if (binariesCacheEnabled) {
			Mockito.verify(
				shell
			).doExecute(
				Mockito.argThat(
					executionRequest -> hasCommand(
						executionRequest, "tar --directory=",
						"binaries-cache.tar.gz"))
			);
		}
	}

}