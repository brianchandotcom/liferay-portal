/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

/**
 * @author Kenji Heigel
 */
public class DefaultTestBatch extends BaseTestBatch<BaseTestSelector> {

	public DefaultTestBatch(String name) {
		this(name, null);
	}

	public DefaultTestBatch(String name, TestSelector testSelector) {
		super(name, null);
	}

	@Override
	public void merge(TestBatch testBatch) {
	}

}