/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Job;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildTestrayCaseResultDescriptor
	extends BaseTestrayCaseResultDescriptor {

	@Override
	public abstract String getBatchName();

	protected BaseBuildTestrayCaseResultDescriptor(
		Job job,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor) {

		super(job, parentTestrayCaseResultDescriptor);
	}

}