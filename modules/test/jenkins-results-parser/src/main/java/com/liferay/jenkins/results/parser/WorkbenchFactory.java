package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class WorkbenchFactory {

	public static Workbench newWorkbench(
		String gitHubURL, String upstreamBranchName) {

		return newWorkbench(gitHubURL, upstreamBranchName, null);
	}

	public static Workbench newWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		if (gitHubURL.contains("/liferay-jenkins-ee")) {
			return new JenkinsWorkbench(
				gitHubURL, upstreamBranchName, branchSHA);
		}
		else if (gitHubURL.contains("/liferay-plugins")) {
			return new PluginsWorkbench(
				gitHubURL, upstreamBranchName, branchSHA);
		}
		else if (gitHubURL.contains("/liferay-portal")) {
			return new DefaultPortalWorkbench(
				gitHubURL, upstreamBranchName, branchSHA);
		}

		throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
	}

	public static CompanionPortalWorkbench newCompanionPortalWorkbench(
		PortalWorkbench portalWorkbench) {

		if (portalWorkbench == null) {
			throw new RuntimeException("Portal workbench is null");
		}

		return new CompanionPortalWorkbench(portalWorkbench);
	}

	public static OtherPortalWorkbench newOtherPortalWorkbench(
		PortalWorkbench portalWorkbench) {

		if (portalWorkbench == null) {
			throw new RuntimeException("Portal workbench is null");
		}

		return new OtherPortalWorkbench(portalWorkbench);
	}

	public static PluginsWorkbench newPluginsWorkbench(
		PortalWorkbench portalWorkbench) {

		if (portalWorkbench == null) {
			throw new RuntimeException("Portal workbench is null");
		}

		return new PluginsWorkbench(portalWorkbench);
	}

}
