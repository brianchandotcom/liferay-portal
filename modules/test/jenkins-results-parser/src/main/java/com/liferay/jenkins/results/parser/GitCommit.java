/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Date;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface GitCommit {

	@Override
	public boolean equals(Object object);

	public String getAbbreviatedSHA();

	public Date getCommitDate();

	public String getEmailAddress();

	public String getGitRepositoryName();

	public String getMessage();

	public String getSHA();

	public GitCommit.Type getType();

	public JSONObject toJSONObject();

	public enum Type {

		LEGACY_ARCHIVE, MANUAL

	}

}