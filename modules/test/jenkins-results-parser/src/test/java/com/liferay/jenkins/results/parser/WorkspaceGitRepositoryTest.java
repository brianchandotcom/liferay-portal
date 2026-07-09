/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class WorkspaceGitRepositoryTest
	extends com.liferay.jenkins.results.parser.Test {

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