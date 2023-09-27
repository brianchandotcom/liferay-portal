/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class RedirectPatternConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test(expected = ConfigurationModelListenerException.class)
	public void testInvalidPattern() throws Exception {
		_redirectPatternConfigurationModelListener.onBeforeSave(
			StringUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"patternStrings", new String[] {"*** abc all"}
			).build());
	}

	private final RedirectPatternConfigurationModelListener
		_redirectPatternConfigurationModelListener =
			new RedirectPatternConfigurationModelListener();

}