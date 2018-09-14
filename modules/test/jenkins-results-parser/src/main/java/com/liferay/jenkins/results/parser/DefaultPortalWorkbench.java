package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class DefaultPortalWorkbench extends BasePortalWorkbench {

	protected DefaultPortalWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);
	}

}
