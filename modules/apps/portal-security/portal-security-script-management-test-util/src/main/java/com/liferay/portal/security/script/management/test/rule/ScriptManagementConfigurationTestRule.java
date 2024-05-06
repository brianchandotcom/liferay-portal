/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.script.management.test.rule;

import com.liferay.portal.kernel.test.rule.ClassTestRule;
import com.liferay.portal.security.script.management.test.util.ScriptManagementConfigurationTestUtil;

import org.junit.runner.Description;

/**
 * @author Feliphe Marinho
 */
public class ScriptManagementConfigurationTestRule extends ClassTestRule<Void> {

	public static final ScriptManagementConfigurationTestRule INSTANCE =
		new ScriptManagementConfigurationTestRule();

	@Override
	protected void afterClass(Description description, Void unused)
		throws Throwable {

		ScriptManagementConfigurationTestUtil.delete();
	}

	@Override
	protected Void beforeClass(Description description) throws Throwable {
		ScriptManagementConfigurationTestUtil.save(true);

		return null;
	}

	private ScriptManagementConfigurationTestRule() {
	}

}