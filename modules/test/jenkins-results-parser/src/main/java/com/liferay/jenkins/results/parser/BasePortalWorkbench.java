package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalWorkbench
	extends BaseWorkbench implements PortalWorkbench {

	protected BasePortalWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);

		LocalGitRepository localGitRepository = getLocalGitRepository();

		if (!(localGitRepository instanceof PortalLocalGitRepository)) {
			throw new RuntimeException("Invalid local Git repository");
		}

		_portalLocalGitRepository =
			(PortalLocalGitRepository)localGitRepository;
	}

	@Override
	public void setPortalBuildProperties(Properties properties) {
		_portalLocalGitRepository.setBuildProperties(properties);
	}

	@Override
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

	private final PortalLocalGitRepository _portalLocalGitRepository;

}
