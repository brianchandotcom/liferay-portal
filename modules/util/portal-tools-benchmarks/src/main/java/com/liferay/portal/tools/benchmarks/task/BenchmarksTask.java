/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.task;

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.List;

/**
 * @author Tina Tian
 */
public interface BenchmarksTask {

	public List<ObjectValuePair<String, Long>> execute() throws Exception;

}