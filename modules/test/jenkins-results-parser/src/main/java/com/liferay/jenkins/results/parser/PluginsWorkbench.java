package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class PluginsWorkbench extends BaseWorkbench {

	protected PluginsWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);
	}

	protected PluginsWorkbench(PortalWorkbench portalWorkbench) {
		super(
			_getGitHubURL(portalWorkbench),
			_getUpstreamBranchName(portalWorkbench),
			_getBranchSHA(portalWorkbench));
	}

	private static String _getBranchSHA(PortalWorkbench portalWorkbench) {
		String gitCommitFileContent = _getGitCommitFileContent(portalWorkbench);

		if (gitCommitFileContent.matches("[0-9a-f]{7,40}")) {
			return gitCommitFileContent;
		}

		return null;
	}

	private static String _getGitCommitFileContent(
		PortalWorkbench portalWorkbench) {

		return portalWorkbench.getFileContent("git-commit-plugins");
	}

	private static String _getGitHubURL(PortalWorkbench portalWorkbench) {
		String gitCommitFileContent = _getGitCommitFileContent(portalWorkbench);

		if (GitUtil.isValidGitHubRefURL(gitCommitFileContent) ||
			PullRequest.isValidGitHubPullRequestURL(gitCommitFileContent)) {

			return gitCommitFileContent;
		}

		return JenkinsResultsParserUtil.combine(
			"https://github.com/liferay/liferay-plugins-ee/tree/",
			_getUpstreamBranchName(portalWorkbench));
	}

	private static String _getUpstreamBranchName(
		PortalWorkbench portalWorkbench) {

		String portalUpstreamBranchName =
			portalWorkbench.getUpstreamBranchName();

		if (portalUpstreamBranchName.contains("7.0.x") ||
			portalUpstreamBranchName.contains("7.1.x") ||
			portalUpstreamBranchName.contains("master")) {

			return "7.0.x";
		}

		return portalUpstreamBranchName;
	}

}
