package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalWorkbench
	extends BaseWorkbench implements PortalWorkbench {

	protected BasePortalWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);
	}

	public String getFileContent(String filePath) {
		File file = new File(getDirectory(), filePath);

		try {
			String fileContent = JenkinsResultsParserUtil.read(file);

			return fileContent.trim();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}
