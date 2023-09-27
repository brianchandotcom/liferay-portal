/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Date;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitCommit implements GitCommit {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BaseGitCommit)) {
			return false;
		}

		if (Objects.equals(hashCode(), object.hashCode())) {
			return true;
		}

		return false;
	}

	@Override
	public String getAbbreviatedSHA() {
		return _sha.substring(0, 7);
	}

	@Override
	public Date getCommitDate() {
		if (commitTime == null) {
			initCommitTime();
		}

		return new Date(commitTime);
	}

	@Override
	public String getEmailAddress() {
		if (emailAddress == null) {
			initEmailAddress();
		}

		return emailAddress;
	}

	@Override
	public String getGitRepositoryName() {
		return _gitRepositoryName;
	}

	@Override
	public String getMessage() {
		if (message == null) {
			initMessage();
		}

		return message;
	}

	@Override
	public String getSHA() {
		return _sha;
	}

	@Override
	public GitCommit.Type getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		String json = String.valueOf(toJSONObject());

		return json.hashCode();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"commitTime", commitTime
		).put(
			"emailAddress", emailAddress
		).put(
			"message", message
		).put(
			"sha", _sha
		);

		return jsonObject;
	}

	protected BaseGitCommit(
		String gitRepositoryName, String sha, GitCommit.Type type) {

		_gitRepositoryName = gitRepositoryName;
		_sha = sha;
		_type = type;
	}

	protected BaseGitCommit(
		String emailAddress, String gitRepositoryName, String message,
		String sha, GitCommit.Type type, long commitTime) {

		_gitRepositoryName = gitRepositoryName;
		_sha = sha;
		_type = type;
		this.emailAddress = emailAddress;
		this.message = message;
		this.commitTime = commitTime;
	}

	protected abstract void initCommitTime();

	protected abstract void initEmailAddress();

	protected abstract void initMessage();

	protected Long commitTime;
	protected String emailAddress;
	protected String message;

	private final String _gitRepositoryName;
	private final String _sha;
	private final GitCommit.Type _type;

}