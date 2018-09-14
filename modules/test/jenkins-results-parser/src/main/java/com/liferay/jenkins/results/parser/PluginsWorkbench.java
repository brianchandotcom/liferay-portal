package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class PluginsWorkbench extends BaseWorkbench {

	protected PluginsWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);
	}

}
