/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesTopLevelBuild
	extends DefaultTopLevelBuild
	implements PortalBranchInformationBuild, QAWebsitesBranchInformationBuild {

	public QAWebsitesTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);

		findDownstreamBuilds();
	}

	@Override
	public String getBaseGitRepositoryName() {
		return "liferay-qa-websites-ee";
	}

	@Override
	public BranchInformation getPortalBaseBranchInformation() {
		return null;
	}

	@Override
	public BranchInformation getPortalBranchInformation() {
		return _portalMasterBranchInformation;
	}

	@Override
	public List<String> getProjectNames() {
		String projectNames = getParameterValue("PROJECT_NAMES");

		return Arrays.asList(projectNames.split(","));
	}

	@Override
	public BranchInformation getQAWebsitesBranchInformation() {
		return getBranchInformation("qa.websites");
	}

	public static class PortalMasterBranchInformation
		extends DefaultBranchInformation {

		@Override
		public String getOriginName() {
			return "liferay";
		}

		@Override
		public Integer getPullRequestNumber() {
			return 0;
		}

		@Override
		public String getReceiverUsername() {
			return "liferay";
		}

		@Override
		public String getRepositoryName() {
			return "liferay-portal";
		}

		@Override
		public String getSenderBranchName() {
			return "master";
		}

		@Override
		public String getSenderBranchSHA() {
			return _remoteGitRef.getSHA();
		}

		@Override
		public String getSenderUsername() {
			return "liferay";
		}

		@Override
		public String getUpstreamBranchName() {
			return "master";
		}

		@Override
		public String getUpstreamBranchSHA() {
			return _remoteGitRef.getSHA();
		}

		protected PortalMasterBranchInformation(Build build) {
			super(build, "portal");

			_remoteGitRef = getSenderRemoteGitRef();
		}

		private final RemoteGitRef _remoteGitRef;

	}

	private final PortalMasterBranchInformation _portalMasterBranchInformation =
		new PortalMasterBranchInformation(this);

}