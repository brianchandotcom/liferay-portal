/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryce Osterhaus
 */
public class LanguageImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testProcess() {
		LanguageImpl languageImpl = new LanguageImpl();

		Assert.assertEquals(
			"foo;bar;{/*removed: import '@liferay/language...'*/};baz;",
			languageImpl.process(
				null, null, "foo;bar;import '@liferay/language/foo/all.js';baz;"
			).toString());
		Assert.assertEquals(
			"foo;{/*removed: import '@liferay/language...'*/};bar;" +
				"{/*removed: import '@liferay/language...'*/};baz;",
			languageImpl.process(
				null, null,
				"foo;import   '@liferay/language/foo/all.js';bar;" +
					"import '@liferay/language/foo/all.js';baz;"
			).toString());
	}

}