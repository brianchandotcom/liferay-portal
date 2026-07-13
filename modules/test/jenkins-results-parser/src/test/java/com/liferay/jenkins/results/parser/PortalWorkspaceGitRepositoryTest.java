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
	public void testPrepareGitWorkingDirectoryWithGitArchiveDisabled()
		throws Exception {

		mockEnvironment(
			Collections.singletonMap("MASTER_NETWORK_NAME", "aws-network"));

		Properties buildProperties = new Properties();

		buildProperties.setProperty("binaries.cache.enabled", "true");
		buildProperties.setProperty(
			"cloud.ci.s3.bucket.dist.path", "s3://liferayci-dist/dist");
		buildProperties.setProperty("git.archive.enabled", "false");

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		Shell shell = mockShell();

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
			shell, Mockito.never()
		).doExecute(
			Mockito.argThat(
				executionRequest -> _isCommand(
					executionRequest, "git-archives"))
		);

		Assert.assertTrue(
			"The binaries cache must stay enabled when the git archive is " +
				"disabled",
			portalWorkspaceGitRepository.isBinariesCacheEnabled());
	}

	@Test
	public void testPrepareGitWorkingDirectoryWithGitArchiveEnabled()
		throws Exception {

		mockEnvironment(
			Collections.singletonMap("MASTER_NETWORK_NAME", "aws-network"));

		Properties buildProperties = new Properties();

		buildProperties.setProperty("binaries.cache.enabled", "false");
		buildProperties.setProperty(
			"cloud.ci.s3.bucket.dist.path", "s3://liferayci-dist/dist");
		buildProperties.setProperty("git.archive.enabled", "true");

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		Shell shell = mockShell();

		Mockito.doReturn(
			new Shell.ExecutionResult(0, "", "")
		).when(
			shell
		).doExecute(
			Mockito.any(Shell.ExecutionRequest.class)
		);

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
			shell, Mockito.atLeastOnce()
		).doExecute(
			Mockito.argThat(
				executionRequest -> _isCommand(
					executionRequest, "git-archives"))
		);

		Assert.assertFalse(
			"The binaries cache must stay disabled when the git archive is " +
				"enabled",
			portalWorkspaceGitRepository.isBinariesCacheEnabled());
	}

	@Test
	public void testSetUp() throws Exception {
		mockEnvironment(
			Collections.singletonMap("MASTER_NETWORK_NAME", "aws-network"));

		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"binaries.cache.s3.path",
			"s3://liferayci-file-propagator/binaries-cache/master.tar.gz");
		buildProperties.setProperty("git.archive.enabled", "true");

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		BuildDatabaseUtil.setBuildDatabase(
			BuildDatabaseTestUtil.newBuildDatabaseWithPullRequest());

		Shell shell = mockShell();

		Mockito.doReturn(
			new Shell.ExecutionResult(0, "", "")
		).when(
			shell
		).doExecute(
			Mockito.any(Shell.ExecutionRequest.class)
		);

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			_newPortalWorkspaceGitRepository();

		Mockito.doReturn(
			true
		).when(
			portalWorkspaceGitRepository
		).isBinariesCacheEnabled();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).prepareGitWorkingDirectory();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).setUp();

		Mockito.doCallRealMethod(
		).when(
			portalWorkspaceGitRepository
		).setUpAdditionalCaches();

		portalWorkspaceGitRepository.setUp();

		Mockito.verify(
			shell
		).doExecute(
			Mockito.argThat(
				executionRequest -> _isCommand(
					executionRequest, "aws s3 cp", "binaries-cache.tar.gz"))
		);

		Mockito.verify(
			shell
		).doExecute(
			Mockito.argThat(
				executionRequest -> _isCommand(
					executionRequest, "tar --directory=",
					"binaries-cache.tar.gz"))
		);
	}

	@Test
	public void testSetUpAdditionalCachesWithBinariesCacheDisabled()
		throws Exception {

		mockEnvironment(
			Collections.singletonMap("MASTER_NETWORK_NAME", "aws-network"));

		Properties buildProperties = new Properties();

		buildProperties.setProperty("binaries.cache.enabled", "false");
		buildProperties.setProperty("git.archive.enabled", "true");

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		Shell shell = mockShell();

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
			shell, Mockito.never()
		).doExecute(
			Mockito.argThat(
				executionRequest -> _isCommand(
					executionRequest, "aws s3 cp", "binaries-cache.tar.gz"))
		);

		Assert.assertTrue(
			"The git archive must stay enabled when binaries cache is disabled",
			_isGitArchiveEnabled(portalWorkspaceGitRepository));
	}

	@Test
	public void testSetUpAdditionalCachesWithBinariesCacheEnabled()
		throws Exception {

		mockEnvironment(
			Collections.singletonMap("MASTER_NETWORK_NAME", "aws-network"));

		Properties buildProperties = new Properties();

		buildProperties.setProperty("binaries.cache.enabled", "true");
		buildProperties.setProperty(
			"binaries.cache.s3.path",
			"s3://liferayci-file-propagator/binaries-cache/master.tar.gz");
		buildProperties.setProperty("git.archive.enabled", "false");

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		Shell shell = mockShell();

		Mockito.doReturn(
			new Shell.ExecutionResult(0, "", "")
		).when(
			shell
		).doExecute(
			Mockito.any(Shell.ExecutionRequest.class)
		);

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
			shell
		).doExecute(
			Mockito.argThat(
				executionRequest -> _isCommand(
					executionRequest, "aws s3 cp", "binaries-cache.tar.gz"))
		);

		Assert.assertFalse(
			"The git archive must stay disabled when binaries cache is enabled",
			_isGitArchiveEnabled(portalWorkspaceGitRepository));
	}

	@Test
	public void testSetUpFailureDoesNotMarkTheRepositorySetUp()
		throws Exception {

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
	public void testSetUpRunsStepsInOrderExactlyOnce() throws Exception {
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

	private boolean _isCommand(
		Shell.ExecutionRequest executionRequest, String... substrings) {

		if (executionRequest == null) {
			return false;
		}

		String command = executionRequest.getCommands()[0];

		for (String substring : substrings) {
			if (!command.contains(substring)) {
				return false;
			}
		}

		return true;
	}

	private boolean _isGitArchiveEnabled(
			WorkspaceGitRepository workspaceGitRepository)
		throws Exception {

		Method method = BaseWorkspaceGitRepository.class.getDeclaredMethod(
			"_isGitArchiveEnabled");

		method.setAccessible(true);

		return (Boolean)method.invoke(workspaceGitRepository);
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

}