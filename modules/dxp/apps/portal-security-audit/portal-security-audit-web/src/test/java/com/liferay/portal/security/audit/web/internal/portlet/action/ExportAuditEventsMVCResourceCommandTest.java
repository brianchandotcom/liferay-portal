/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.web.internal.portlet.action;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.security.audit.AuditEvent;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Caio Farias
 */
public class ExportAuditEventsMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBuildCSV() {
		AuditEvent firstAuditEvent = Mockito.mock(AuditEvent.class);

		long firstUserId = RandomTestUtil.randomLong();

		Mockito.when(
			firstAuditEvent.getUserId()
		).thenReturn(
			firstUserId
		);

		String firstUserName = RandomTestUtil.randomString();

		Mockito.when(
			firstAuditEvent.getUserName()
		).thenReturn(
			firstUserName
		);

		AuditEvent secondAuditEvent = Mockito.mock(AuditEvent.class);

		long secondUserId = RandomTestUtil.randomLong();

		Mockito.when(
			secondAuditEvent.getUserId()
		).thenReturn(
			secondUserId
		);

		String secondUserName = RandomTestUtil.randomString();

		Mockito.when(
			secondAuditEvent.getUserName()
		).thenReturn(
			secondUserName
		);

		Assert.assertEquals(
			StringBundler.concat(
				"user-name,user-id\n", firstUserName, StringPool.COMMA,
				firstUserId, "\n", secondUserName, StringPool.COMMA,
				secondUserId, "\n"),
			_buildCSV(
				new String[] {"user-name", "user-id"},
				Arrays.asList(firstAuditEvent, secondAuditEvent)));
	}

	private String _buildCSV(String[] columns, List<AuditEvent> auditEvents) {
		return ReflectionTestUtil.invoke(
			new ExportAuditEventsMVCResourceCommand(), "_buildCSV",
			new Class<?>[] {List.class, String[].class, ProgressTracker.class},
			auditEvents, columns, null);
	}

}