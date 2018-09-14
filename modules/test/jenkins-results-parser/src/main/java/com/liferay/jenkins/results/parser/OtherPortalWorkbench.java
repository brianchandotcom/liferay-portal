package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class OtherPortalWorkbench extends BasePortalWorkbench {

	protected OtherPortalWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);
	}

}
