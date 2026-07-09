/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class WorkspaceGitRepositoryTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testIsFullDotGitDirArchiveRequiredOnlyForEE62X()
		throws Exception {

		Assert.assertTrue(
			"A full .git dir archive is required for ee-6.2.x working dirs",
			_isFullDotGitDirArchiveRequired(
				_newWorkspaceGitRepository("liferay-plugins-ee-6.2.x")));

		Assert.assertTrue(
			"A full .git dir archive is required for ee-6.2.x working dirs",
			_isFullDotGitDirArchiveRequired(
				_newWorkspaceGitRepository("liferay-portal-ee-6.2.x")));

		Assert.assertFalse(
			"A full .git dir archive is not required for master working dirs",
			_isFullDotGitDirArchiveRequired(
				_newWorkspaceGitRepository("liferay-portal")));

		Assert.assertFalse(
			"A full .git dir archive is not required for 7.0.x working dirs",
			_isFullDotGitDirArchiveRequired(
				_newWorkspaceGitRepository("liferay-portal-7.0.x")));
	}

	@Test
	public void testValidateSHAInRemoteGitRefFetchesBeforeContainsCheck()
		throws Exception {

		GitWorkingDirectory gitWorkingDirectory = Mockito.mock(
			GitWorkingDirectory.class);

		LocalGitBranch localGitBranch = Mockito.mock(LocalGitBranch.class);
		RemoteGitRef remoteGitRef = Mockito.mock(RemoteGitRef.class);

		String sha = "0123456789012345678901234567890123456789";

		Mockito.when(
			gitWorkingDirectory.fetch(remoteGitRef)
		).thenReturn(
			localGitBranch
		);

		Mockito.when(
			gitWorkingDirectory.refContainsSHA(localGitBranch, sha)
		).thenReturn(
			true
		);

		DefaultWorkspaceGitRepository defaultWorkspaceGitRepository =
			Mockito.mock(DefaultWorkspaceGitRepository.class);

		Mockito.when(
			defaultWorkspaceGitRepository.getGitWorkingDirectory()
		).thenReturn(
			gitWorkingDirectory
		);

		_validateSHAInRemoteGitRef(
			defaultWorkspaceGitRepository, "master", remoteGitRef, sha);

		InOrder inOrder = Mockito.inOrder(gitWorkingDirectory);

		inOrder.verify(
			gitWorkingDirectory
		).fetch(
			remoteGitRef
		);

		inOrder.verify(
			gitWorkingDirectory
		).refContainsSHA(
			localGitBranch, sha
		);
	}

	private boolean _isFullDotGitDirArchiveRequired(
			WorkspaceGitRepository workspaceGitRepository)
		throws Exception {

		Method method = BaseWorkspaceGitRepository.class.getDeclaredMethod(
			"_isFullDotGitDirArchiveRequired");

		method.setAccessible(true);

		return (Boolean)method.invoke(workspaceGitRepository);
	}

	private WorkspaceGitRepository _newWorkspaceGitRepository(
		String workingDirectoryName) {

		GitWorkingDirectory gitWorkingDirectory = Mockito.mock(
			GitWorkingDirectory.class);

		Mockito.when(
			gitWorkingDirectory.getWorkingDirectory()
		).thenReturn(
			new File(workingDirectoryName)
		);

		DefaultWorkspaceGitRepository defaultWorkspaceGitRepository =
			Mockito.mock(DefaultWorkspaceGitRepository.class);

		Mockito.when(
			defaultWorkspaceGitRepository.getGitWorkingDirectory()
		).thenReturn(
			gitWorkingDirectory
		);

		return defaultWorkspaceGitRepository;
	}

	private void _validateSHAInRemoteGitRef(
			WorkspaceGitRepository workspaceGitRepository, String branchName,
			RemoteGitRef remoteGitRef, String sha)
		throws Exception {

		Method method = BaseWorkspaceGitRepository.class.getDeclaredMethod(
			"_validateSHAInRemoteGitRef", String.class, RemoteGitRef.class,
			String.class);

		method.setAccessible(true);

		try {
			method.invoke(
				workspaceGitRepository, branchName, remoteGitRef, sha);
		}
		catch (InvocationTargetException invocationTargetException) {
			Throwable throwable = invocationTargetException.getCause();

			if (throwable instanceof RuntimeException) {
				throw (RuntimeException)throwable;
			}

			throw invocationTargetException;
		}
	}

}