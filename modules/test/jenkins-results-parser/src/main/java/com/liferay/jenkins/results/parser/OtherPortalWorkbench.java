package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class OtherPortalWorkbench extends BasePortalWorkbench {

	protected OtherPortalWorkbench(PortalWorkbench portalWorkbench) {
		super(
			_getGitHubURL(portalWorkbench),
			_getUpstreamBranchName(portalWorkbench), null);
	}

	private static String _getGitHubURL(PortalWorkbench portalWorkbench) {
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

		if (portalUpstreamBranchName.contains("7.0.x")) {
			return portalUpstreamBranchName.replace("7.0.x", "master");
		}
		else if (portalUpstreamBranchName.contains("7.1.x")) {
			return portalUpstreamBranchName.replace("7.1.x", "7.0.x");
		}

		return portalUpstreamBranchName.replace("master", "7.0.x");
	}

}
