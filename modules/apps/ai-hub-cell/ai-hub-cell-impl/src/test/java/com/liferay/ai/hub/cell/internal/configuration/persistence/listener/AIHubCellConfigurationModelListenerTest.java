/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.internal.configuration.persistence.listener;

import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Pedro Victor Silvestre
 */
public class AIHubCellConfigurationModelListenerTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void test() {
		Dictionary<String, Object> properties = new Hashtable<>();

		_aiHubCellConfigurationModelListener.onBeforeSave("pid", properties);

		String secret = GetterUtil.getString(properties.get("secret"));

		Assert.assertFalse(Validator.isBlank(secret));
		Assert.assertEquals(64, Base64.decode(secret).length);
	}

	private final AIHubCellConfigurationModelListener
		_aiHubCellConfigurationModelListener =
			new AIHubCellConfigurationModelListener();

}