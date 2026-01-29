/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJobHistory implements JobHistory {

	@Override
	public List<BatchHistory> getBatchHistories() {
		return new ArrayList<>(_batchHistories.values());
	}

	@Override
	public BatchHistory getBatchHistory(String batchName) {
		if (batchName == null) {
			return null;
		}

		Matcher matcher = _pattern.matcher(batchName);

		if (matcher.find()) {
			batchName = matcher.group("batchName");
		}

		return _batchHistories.get(batchName);
	}

	@Override
	public String getPortalUpstreamBranchName() {
		return _portalUpstreamBranchName;
	}

	protected BaseJobHistory(String portalUpstreamBranchName) {
		_portalUpstreamBranchName = portalUpstreamBranchName;
	}

	protected void addBatchHistory(BatchHistory batchHistory) {
		if (batchHistory == null) {
			return;
		}

		_batchHistories.put(batchHistory.getBatchName(), batchHistory);
	}

	private static final Pattern _pattern = Pattern.compile(
		"(?<batchName>.+)_stable");

	private final Map<String, BatchHistory> _batchHistories = new HashMap<>();
	private final String _portalUpstreamBranchName;

}