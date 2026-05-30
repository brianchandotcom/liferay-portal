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
		AuditEvent auditEvent1 = _mockAuditEvent();
		AuditEvent auditEvent2 = _mockAuditEvent();

		Assert.assertEquals(
			StringBundler.concat(
				"user-name,user-id\n", auditEvent1.getUserName(),
				StringPool.COMMA, auditEvent1.getUserId(), "\n",
				auditEvent2.getUserName(), StringPool.COMMA,
				auditEvent2.getUserId(), "\n"),
			_buildCSV(
				Arrays.asList(auditEvent1, auditEvent2),
				new String[] {"user-name", "user-id"}));
	}

	@Test
	public void testBuildCSVWhenColumnValueIsNull() {
		AuditEvent auditEvent = Mockito.mock(AuditEvent.class);

		long userId = RandomTestUtil.randomLong();

		Mockito.when(
			auditEvent.getUserId()
		).thenReturn(
			userId
		);

		Assert.assertEquals(
			StringBundler.concat("user-name,user-id\n,", userId, "\n"),
			_buildCSV(
				Arrays.asList(auditEvent),
				new String[] {"user-name", "user-id"}));
	}

	private String _buildCSV(List<AuditEvent> auditEvents, String[] columns) {
		return ReflectionTestUtil.invoke(
			new ExportAuditEventsMVCResourceCommand(), "_buildCSV",
			new Class<?>[] {List.class, String[].class, ProgressTracker.class},
			auditEvents, columns, null);
	}

	private AuditEvent _mockAuditEvent() {
		AuditEvent auditEvent = Mockito.mock(AuditEvent.class);

		Mockito.when(
			auditEvent.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			auditEvent.getUserName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		return auditEvent;
	}

}