/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

/**
 * @author Kenji Heigel
 */
public class PoshiTestBatch extends BaseTestBatch<PoshiTestSelector> {

	public PoshiTestBatch(String name, PoshiTestSelector testSelector) {
		super(name, testSelector);
	}

}