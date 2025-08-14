/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.Map;

/**
 * @author Kenji Heigel
 */
public class AppServerBundleDownstreamBuild extends BaseDownstreamBuild {

	protected AppServerBundleDownstreamBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);
	}

	protected void createBuildFailureObjectRef() throws IOException {
		Map<String, String> startPropertiesTempMap =
			getStartPropertiesTempMap();

		String s3ObjectPath =
			startPropertiesTempMap.get("S3_BUCKET_DIST_PATH") +
				"/build-failure";

		if (CloudBucketUtil.isS3ObjectRefAvailable(s3ObjectPath)) {
			return;
		}

		PortalWorkspaceBuild portalWorkspaceBuild =
			(PortalWorkspaceBuild)getTopLevelBuild();

		Workspace portalWorkspace = portalWorkspaceBuild.getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			portalWorkspace.getPrimaryWorkspaceGitRepository();

		File directory = workspaceGitRepository.getDirectory();

		File buildFailureFile = new File(directory, "build-failure");

		buildFailureFile.createNewFile();

		CloudBucketUtil.uploadS3File(s3ObjectPath, buildFailureFile);

		StringBuilder sb = new StringBuilder();

		sb.append(
			JenkinsResultsParserUtil.getBuildProperty(
				"cloud.ci.s3.bucket.bundles.path"));
		sb.append("/");
		sb.append(workspaceGitRepository.getName());
		sb.append("/");
		sb.append(workspaceGitRepository.getBaseBranchSHA());
		sb.append("/");
		sb.append(workspaceGitRepository.getSenderBranchSHA());
		sb.append("/build-failure");

		CloudBucketUtil.createS3ObjectRef(s3ObjectPath, sb.toString());
	}

}