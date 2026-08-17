/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.log4j;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayOutputStream;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ObjectMessage;
import org.apache.logging.log4j.message.SimpleMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public class FIPSAuditNDJSONLayoutTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Before
	public void setUp() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testEncode() {
		Map<String, Object> record = LinkedHashMapBuilder.<String, Object>put(
			"event-type", "fips-state-transition"
		).put(
			"severity", "CRITICAL"
		).build();

		FIPSAuditNDJSONLayout fipsAuditNDJSONLayout =
			_createFIPSAuditNDJSONLayout();

		TestByteBufferDestination testByteBufferDestination =
			new TestByteBufferDestination();

		fipsAuditNDJSONLayout.encode(
			_createLogEvent(new ObjectMessage(record)),
			testByteBufferDestination);

		Assert.assertEquals(
			_toSerializable(record), testByteBufferDestination.toString());
	}

	@Test
	public void testGetContentType() {
		FIPSAuditNDJSONLayout fipsAuditNDJSONLayout =
			_createFIPSAuditNDJSONLayout();

		Assert.assertEquals(
			"application/x-ndjson; charset=UTF-8",
			fipsAuditNDJSONLayout.getContentType());
	}

	@Test
	public void testToSerializableEndsWithASingleNewLine() {
		Assert.assertEquals(
			"{\"event-type\":\"fips-state-transition\"}\n",
			_toSerializable(
				Collections.singletonMap(
					"event-type", "fips-state-transition")));
	}

	@Test
	public void testToSerializableEscapesReservedCharacters() {
		Assert.assertEquals(
			"{\"provider-error-message\":\"a\\\\b\\\"c\\nd\\re\\tf\\u0001g" +
				"\\u2028h\\u2029i\"}\n",
			_toSerializable(
				Collections.singletonMap(
					"provider-error-message",
					new String(
						new char[] {
							'a', '\\', 'b', '"', 'c', '\n', 'd', '\r', 'e',
							'\t', 'f', 0x01, 'g', 0x2028, 'h', 0x2029, 'i'
						}))));
	}

	@Test
	public void testToSerializableRejectsAForeignEvent() {
		FIPSAuditNDJSONLayout fipsAuditNDJSONLayout =
			_createFIPSAuditNDJSONLayout();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				FIPSAuditNDJSONLayout.class.getName(), LoggerTestUtil.ERROR)) {

			Assert.assertEquals(
				"",
				fipsAuditNDJSONLayout.toSerializable(
					_createLogEvent(new SimpleMessage("Not a record"))));
			Assert.assertEquals(
				"",
				fipsAuditNDJSONLayout.toSerializable(
					_createLogEvent(new ObjectMessage("not-a-map"))));

			List<String> messages = logCapture.getMessages();

			Assert.assertEquals(messages.toString(), 2, messages.size());

			for (String message : messages) {
				Assert.assertTrue(
					message,
					message.contains("does not carry a FIPS audit record"));
			}
		}
	}

	@Test
	public void testToSerializableWritesEveryEntry() throws Exception {
		Map<String, Object> record = LinkedHashMapBuilder.<String, Object>put(
			"event-type", "fips-state-transition"
		).put(
			"severity", "INFO"
		).put(
			"timestamp", "2026-08-04T12:00:00.000Z"
		).build();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_toSerializable(record));

		Assert.assertEquals(record.size(), jsonObject.length());

		for (Map.Entry<String, Object> entry : record.entrySet()) {
			Assert.assertEquals(
				entry.getValue(), jsonObject.getString(entry.getKey()));
		}
	}

	@Test
	public void testToSerializableWritesNestedMaps() {
		Assert.assertEquals(
			"{\"fields\":{\"from-state\":\"SELF_TEST\"}}\n",
			_toSerializable(
				Collections.singletonMap(
					"fields",
					Collections.singletonMap("from-state", "SELF_TEST"))));
	}

	@Test
	public void testToSerializableWritesValueTypes() {
		_testToSerializable(
			"array", new String[] {"first", "second"},
			"[\"first\",\"second\"]");
		_testToSerializable("boolean", Boolean.TRUE, "true");
		_testToSerializable("decimal", 1.5D, "1.5");
		_testToSerializable("iterable", Arrays.asList("one", 2), "[\"one\",2]");
		_testToSerializable("number", 42, "42");
		_testToSerializable("string", "text", "\"text\"");
	}

	private FIPSAuditNDJSONLayout _createFIPSAuditNDJSONLayout() {
		FIPSAuditNDJSONLayout.Builder builder =
			FIPSAuditNDJSONLayout.newBuilder();

		return builder.build();
	}

	private LogEvent _createLogEvent(Message message) {
		Log4jLogEvent.Builder builder = Log4jLogEvent.newBuilder();

		builder.setLevel(Level.INFO);
		builder.setLoggerName(RandomTestUtil.randomString());
		builder.setMessage(message);

		return builder.build();
	}

	private void _testToSerializable(
		String key, Object value, String valueJSON) {

		Assert.assertEquals(
			StringBundler.concat("{\"", key, "\":", valueJSON, "}\n"),
			_toSerializable(Collections.singletonMap(key, value)));
	}

	private String _toSerializable(Map<String, Object> record) {
		FIPSAuditNDJSONLayout fipsAuditNDJSONLayout =
			_createFIPSAuditNDJSONLayout();

		return fipsAuditNDJSONLayout.toSerializable(
			_createLogEvent(new ObjectMessage(record)));
	}

	private static class TestByteBufferDestination
		implements ByteBufferDestination {

		@Override
		public ByteBuffer drain(ByteBuffer byteBuffer) {
			byteBuffer.flip();

			writeBytes(byteBuffer);

			byteBuffer.clear();

			return byteBuffer;
		}

		@Override
		public ByteBuffer getByteBuffer() {
			return _byteBuffer;
		}

		@Override
		public String toString() {
			drain(_byteBuffer);

			byte[] bytes = _byteArrayOutputStream.toByteArray();

			return new String(bytes, StandardCharsets.UTF_8);
		}

		@Override
		public void writeBytes(byte[] bytes, int offset, int length) {
			_byteArrayOutputStream.write(bytes, offset, length);
		}

		@Override
		public void writeBytes(ByteBuffer byteBuffer) {
			byte[] bytes = new byte[byteBuffer.remaining()];

			byteBuffer.get(bytes);

			writeBytes(bytes, 0, bytes.length);
		}

		private final ByteArrayOutputStream _byteArrayOutputStream =
			new ByteArrayOutputStream();
		private final ByteBuffer _byteBuffer = ByteBuffer.allocate(4096);

	}

}