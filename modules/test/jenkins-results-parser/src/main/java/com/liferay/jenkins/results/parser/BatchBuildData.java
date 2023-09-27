/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public interface BatchBuildData extends BuildData {

	public String getBatchName();

	public List<String> getTestList();

	public void setBatchName(String batchName);

	public void setTestList(List<String> testList);

}