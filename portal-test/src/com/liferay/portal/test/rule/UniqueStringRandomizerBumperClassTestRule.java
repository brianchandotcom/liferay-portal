/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.ClassTestRule;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class UniqueStringRandomizerBumperClassTestRule
	extends ClassTestRule<Void> {

	public static final UniqueStringRandomizerBumperClassTestRule INSTANCE =
		new UniqueStringRandomizerBumperClassTestRule();

	@Override
	public Void beforeClass(Description description) {
		UniqueStringRandomizerBumper.reset();

		return null;
	}

	@Override
	protected void afterClass(Description description, Void v) {
	}

	private UniqueStringRandomizerBumperClassTestRule() {
	}

}