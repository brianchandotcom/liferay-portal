/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class BatchBuildRunner<T extends BatchBuildData>
	extends BaseBuildRunner<T> {

	protected BatchBuildRunner(T batchBuildData) {
		super(batchBuildData);
	}

}