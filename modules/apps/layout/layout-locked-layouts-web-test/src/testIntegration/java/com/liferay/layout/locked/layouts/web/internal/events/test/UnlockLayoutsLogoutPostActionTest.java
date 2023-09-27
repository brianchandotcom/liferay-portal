/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.events.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@FeatureFlags("LPS-180328")
@RunWith(Arquillian.class)
public class UnlockLayoutsLogoutPostActionTest extends BaseActionTest {

	@Test
	public void testProcessLifecycleEvent() throws Exception {
		testProcessLifecycleEvent(
			getLifecycleAction(
				"com.liferay.layout.locked.layouts.web.internal.events." +
					"UnlockLayoutsLogoutPostAction",
				"logout.events.post"));
	}

}