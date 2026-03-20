/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.persistent.resource;

import com.liferay.jenkins.results.parser.BuildDatabase;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.Workspace;
import com.liferay.jenkins.results.parser.WorkspaceGitRepository;

/**
 * @author Michael Hashimoto
 */
public class AsahBundlePersistentResource extends BaseBundlePersistentResource {

	@Override
	public Type getType() {
		return Type.ASAH_BUNDLE;
	}

	protected AsahBundlePersistentResource(
		BuildDatabase buildDatabase, TopLevelBuild topLevelBuild) {

		super(buildDatabase, topLevelBuild);
	}

	@Override
	protected WorkspaceGitRepository getBundleWorkspaceGitRepository() {
		Workspace workspace = getWorkspace();

		return workspace.getWorkspaceGitRepository(
			"com-liferay-osb-asah-private");
	}

}