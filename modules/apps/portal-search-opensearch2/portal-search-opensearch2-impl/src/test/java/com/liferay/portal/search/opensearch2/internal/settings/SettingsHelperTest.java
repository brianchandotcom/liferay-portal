/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.settings;

import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.test.util.settings.BaseSettingsHelperTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;

/**
 * @author Bryan Engler
 */
public class SettingsHelperTest extends BaseSettingsHelperTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() {
		super.setUp();

		settingsHelper = new SettingsHelperImpl();
	}

}