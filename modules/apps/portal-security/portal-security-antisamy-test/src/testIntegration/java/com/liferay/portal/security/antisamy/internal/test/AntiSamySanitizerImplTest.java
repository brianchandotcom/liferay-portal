/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.antisamy.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Victor Silvestre
 */
@RunWith(Arquillian.class)
public class AntiSamySanitizerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSanitize() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.security.antisamy.internal." +
					"AntiSamySanitizerImpl",
				LoggerTestUtil.WARN)) {

			_antiSamySanitizer.sanitize(
				TestPropsValues.getCompanyId(), 0, 0, StringPool.BLANK, 0,
				ContentTypes.TEXT_HTML, new String[0],
				"<p><a href=\"test\" rel=\"noopener noreferrer\" " +
					"target=\"_blank\"></a></p>",
				new HashMap<>());

			Assert.assertTrue(ListUtil.isEmpty(logCapture.getLogEntries()));
		}
	}

	@Inject(
		filter = "(&(objectClass=com.liferay.portal.kernel.sanitizer.Sanitizer)(!(component.name=*)))"
	)
	private Sanitizer _antiSamySanitizer;

}