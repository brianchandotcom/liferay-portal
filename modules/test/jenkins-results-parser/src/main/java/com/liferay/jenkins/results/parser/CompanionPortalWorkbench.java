package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class CompanionPortalWorkbench extends BasePortalWorkbench {

	protected CompanionPortalWorkbench(PortalWorkbench portalWorkbench) {
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

		String portalUpstreamBranchName =
			portalWorkbench.getUpstreamBranchName();

		String gitCommitFileName = "git-commit-portal";

		if (!portalUpstreamBranchName.endsWith("-private")) {
			gitCommitFileName += "-private";
		}

		return portalWorkbench.getFileContent(gitCommitFileName);
	}

	private static String _getGitHubURL(PortalWorkbench portalWorkbench) {
		String gitCommitFileContent = _getGitCommitFileContent(portalWorkbench);

		if (GitUtil.isValidGitHubRefURL(gitCommitFileContent) ||
			PullRequest.isValidGitHubPullRequestURL(gitCommitFileContent)) {

			return gitCommitFileContent;
		}

		String upstreamBranchName = _getUpstreamBranchName(portalWorkbench);

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/liferay/liferay-portal");

		if (!upstreamBranchName.equals("master")) {
			sb.append("-ee");
		}

		sb.append("/tree/");
		sb.append(upstreamBranchName);

		return sb.toString();
	}

	private static String _getUpstreamBranchName(
		PortalWorkbench portalWorkbench) {

		String portalUpstreamBranchName =
			portalWorkbench.getUpstreamBranchName();

		if (portalUpstreamBranchName.endsWith("-private")) {
			return portalUpstreamBranchName.replace("-private", "");
		}

		return portalUpstreamBranchName + "-private";
	}

}
