/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.log4j;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ObjectMessage;
import org.apache.logging.log4j.message.SimpleMessage;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jorge García Jiménez
 */
public class FIPSAuditFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testFilterAcceptsARecord() {
		FIPSAuditFilter fipsAuditFilter = _createFIPSAuditFilter();

		Assert.assertEquals(
			Filter.Result.ACCEPT,
			fipsAuditFilter.filter(
				_createLogEvent(
					MarkerManager.getMarker(FIPSLog4jUtil.MARKER_NAME),
					new ObjectMessage(_record))));
	}

	@Test
	public void testFilterDeniesAnEventWithoutARecord() {
		Marker marker = MarkerManager.getMarker(FIPSLog4jUtil.MARKER_NAME);

		_testFilterDenies(
			"carries no FIPS audit record", marker,
			new ObjectMessage("not-a-map"));
		_testFilterDenies(
			"carries no FIPS audit record", marker,
			new SimpleMessage("Not a record"));
	}

	@Test
	public void testFilterDeniesAnEventWithoutTheMarker() {
		_testFilterDenies(
			"carries no \"FIPS_AUDIT\" marker",
			MarkerManager.getMarker(RandomTestUtil.randomString()),
			new ObjectMessage(_record));
		_testFilterDenies(
			"carries no \"FIPS_AUDIT\" marker", null,
			new ObjectMessage(_record));
	}

	private FIPSAuditFilter _createFIPSAuditFilter() {
		FIPSAuditFilter.Builder builder = FIPSAuditFilter.newBuilder();

		return builder.build();
	}

	private LogEvent _createLogEvent(Marker marker, Message message) {
		Log4jLogEvent.Builder builder = Log4jLogEvent.newBuilder();

		builder.setLevel(Level.INFO);
		builder.setLoggerName(RandomTestUtil.randomString());
		builder.setMarker(marker);
		builder.setMessage(message);

		return builder.build();
	}

	private void _testFilterDenies(
		String expectedMessage, Marker marker, Message message) {

		FIPSAuditFilter fipsAuditFilter = _createFIPSAuditFilter();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				FIPSAuditFilter.class.getName(), LoggerTestUtil.ERROR)) {

			Assert.assertEquals(
				Filter.Result.DENY,
				fipsAuditFilter.filter(_createLogEvent(marker, message)));

			List<String> messages = logCapture.getMessages();

			Assert.assertEquals(messages.toString(), 1, messages.size());

			String errorMessage = messages.get(0);

			Assert.assertTrue(
				errorMessage, errorMessage.contains(expectedMessage));
		}
	}

	private final Map<String, Object> _record = Collections.singletonMap(
		RandomTestUtil.randomString(), RandomTestUtil.randomString());

}