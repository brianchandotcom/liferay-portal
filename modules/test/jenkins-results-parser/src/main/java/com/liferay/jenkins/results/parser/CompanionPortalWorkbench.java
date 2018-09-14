package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class CompanionPortalWorkbench extends BasePortalWorkbench {

	protected CompanionPortalWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);
	}

}
