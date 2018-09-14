package com.liferay.jenkins.results.parser;

import org.json.JSONObject;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseWorkbench implements Workbench {

	@Override
	public String getBranchHeadSHA() {
		return _jsonObject.getString("branch_head_sha");
	}

	@Override
	public String getBranchSHA() {
		return _jsonObject.getString("branch_sha");
	}

	@Override
	public File getDirectory() {
		return _localGitRepository.getDirectory();
	}

	@Override
	public String getGitHubDevBranchName() {
		return _jsonObject.getString("git_hub_dev_branch_name");
	}

	@Override
	public String getGitHubURL() {
		return _jsonObject.getString("git_hub_url");
	}

	@Override
	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	@Override
	public LocalGitRepository getLocalGitRepository() {
		return _localGitRepository;
	}

	@Override
	public String getUpstreamBranchName() {
		return _jsonObject.getString("upstream_branch_name");
	}

	@Override
	public void setUp() {
		System.out.println();
		System.out.println("##");
		System.out.println("## " + getDirectory());
		System.out.println("## " + toString());
		System.out.println("##");
		System.out.println();

		GitWorkingDirectory gitWorkingDirectory =
			_localGitRepository.getGitWorkingDirectory();

		LocalGitBranch localGitBranch =
			gitWorkingDirectory.createLocalGitBranch(
				getBranchSHA(), true, getBranchSHA());

		gitWorkingDirectory.createLocalGitBranch(localGitBranch, true);

		gitWorkingDirectory.checkoutLocalGitBranch(localGitBranch);

		gitWorkingDirectory.reset("--hard " + localGitBranch.getSHA());

		gitWorkingDirectory.clean();

		gitWorkingDirectory.displayLog();
	}

	@Override
	public void tearDown() {
		GitWorkingDirectory gitWorkingDirectory =
			_localGitRepository.getGitWorkingDirectory();

		LocalGitBranch upstreamLocalGitBranch =
			gitWorkingDirectory.getLocalGitBranch(
				gitWorkingDirectory.getUpstreamBranchName());

		System.out.println();
		System.out.println("##");
		System.out.println("## " + getDirectory());
		System.out.println("## " + upstreamLocalGitBranch.toString());
		System.out.println("##");
		System.out.println();

		gitWorkingDirectory.checkoutLocalGitBranch(upstreamLocalGitBranch);

		gitWorkingDirectory.reset("--hard " + upstreamLocalGitBranch.getSHA());

		gitWorkingDirectory.clean();

		gitWorkingDirectory.displayLog();
	}

	@Override
	public String toString() {
		return JenkinsResultsParserUtil.combine(
			getGitHubURL(), " - ", getBranchSHA());
	}

	@Override
	public void writePropertiesFiles() {
		_localGitRepository.writePropertiesFiles();
	}

	protected BaseWorkbench(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		Matcher matcher = _gitHubURLPattern.matcher(gitHubURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		_jsonObject = new JSONObject();

		_jsonObject.put("git_hub_url", gitHubURL);
		_jsonObject.put("upstream_branch_name", upstreamBranchName);

		_localGitRepository =
			GitRepositoryFactory.getLocalGitRepository(
				matcher.group("repositoryName"), upstreamBranchName);

		if (PullRequest.isValidGitHubPullRequestURL(gitHubURL)) {
			PullRequest pullRequest = new PullRequest(gitHubURL);

			LocalGitBranch localGitBranch =
				GitHubDevSyncUtil.createCachedLocalGitBranch(
					_localGitRepository, pullRequest,
					JenkinsResultsParserUtil.isCINode());

			_jsonObject.put("branch_head_sha", localGitBranch.getSHA());
			_jsonObject.put("branch_name", localGitBranch.getName());

			if (JenkinsResultsParserUtil.isCINode()) {
				_jsonObject.put(
					"git_hub_dev_branch_name",
					GitHubDevSyncUtil.getCachedBranchName(pullRequest));
			}
		}
		else if (GitUtil.isValidGitHubRefURL(gitHubURL)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(gitHubURL);

			LocalGitBranch localGitBranch =
				GitHubDevSyncUtil.createCachedLocalGitBranch(
					_localGitRepository, remoteGitRef,
					JenkinsResultsParserUtil.isCINode());

			_jsonObject.put("branch_head_sha", localGitBranch.getSHA());
			_jsonObject.put("branch_name", localGitBranch.getName());

			if (JenkinsResultsParserUtil.isCINode()) {
				_jsonObject.put(
					"git_hub_dev_branch_name",
					GitHubDevSyncUtil.getCachedBranchName(remoteGitRef));
			}
		}
		else {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		if ((branchSHA != null) && branchSHA.matches("[0-9a-f]{7,40}")) {
			_jsonObject.put("branch_sha", branchSHA);
		}
		else {
			_jsonObject.put("branch_sha", getBranchHeadSHA());
		}

		_validateJSONObject();
	}

	private void _validateJSONObject() {
		if (!_jsonObject.has("branch_head_sha")) {
			throw new RuntimeException("Please set required branch_head_sha");
		}

		if (!_jsonObject.has("branch_name")) {
			throw new RuntimeException("Please set required branch_name");
		}

		if (!_jsonObject.has("branch_sha")) {
			throw new RuntimeException("Please set required branch_sha");
		}

		if (JenkinsResultsParserUtil.isCINode()) {
			if (!_jsonObject.has("git_hub_dev_branch_name")) {
				throw new RuntimeException(
					"Please set required git_hub_dev_branch_name");
			}
		}

		if (!_jsonObject.has("git_hub_url")) {
			throw new RuntimeException("Please set required git_hub_url");
		}

		if (!_jsonObject.has("upstream_branch_name")) {
			throw new RuntimeException(
				"Please set required upstream_branch_name");
		}
	}

	private final JSONObject _jsonObject;
	private final LocalGitRepository _localGitRepository;

	private static final Pattern _gitHubURLPattern = Pattern.compile(
		"https://[^/]+/(?<repositoryName>[^/]+)/.*");

}
