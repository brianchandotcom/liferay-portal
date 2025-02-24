/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.osgi.commands.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@FeatureFlags("LPS-178642")
@RunWith(Arquillian.class)
public class HeadlessBuilderOSGiCommandsTest extends BaseTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testReset() throws Exception {
		ReflectionTestUtil.invoke(
			_headlessBuilderOSGiCommands, "reset", new Class<?>[] {long.class},
			TestPropsValues.getCompanyId());
	}

	@Inject(
		filter = "osgi.command.scope=headlessBuilder",
		type = Inject.NoType.class
	)
	private static Object _headlessBuilderOSGiCommands;

}