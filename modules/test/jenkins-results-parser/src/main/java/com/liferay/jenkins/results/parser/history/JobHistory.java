/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.BatchHistory;

import java.net.URL;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public interface JobHistory {

	public List<BatchHistory> getBatchHistories();

	public BatchHistory getBatchHistory(String batchName);

	public URL getTestrayURL();

	public String getUpstreamBranchName();

}