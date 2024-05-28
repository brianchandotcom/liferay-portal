/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Petteri Karttunen
 */
public class OpenSearchTestRule implements TestRule {

	public static final OpenSearchTestRule INSTANCE = new OpenSearchTestRule();

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				if (isUnitTestEnabled()) {
					base.evaluate();
				}
			}

		};
	}

	public boolean isUnitTestEnabled() {
		return Boolean.valueOf(
			System.getProperty(
				"com.liferay.portal.search.opensearch2.test.unit.enabled"));
	}

}