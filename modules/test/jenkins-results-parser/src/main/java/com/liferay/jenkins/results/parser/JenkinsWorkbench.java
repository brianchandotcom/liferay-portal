package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class JenkinsWorkbench extends BaseWorkbench {

	protected JenkinsWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);
	}

}
