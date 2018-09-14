package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalWorkbench
	extends BaseWorkbench implements PortalWorkbench {

	protected BasePortalWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);
	}

}
