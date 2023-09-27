/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.rule;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Matthew Tambara
 */
public abstract class MethodTestRule<M> extends AbstractTestRule<Void, M> {

	@Override
	protected void afterClass(Description description, Void v) {
	}

	@Override
	protected Void beforeClass(Description description) {
		return null;
	}

	@Override
	protected Statement createClassStatement(
		Statement statement, Description description) {

		return statement;
	}

}