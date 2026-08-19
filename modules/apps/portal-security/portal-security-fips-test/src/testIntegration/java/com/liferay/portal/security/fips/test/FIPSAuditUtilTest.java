/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge García Jiménez
 */
@RunWith(Arquillian.class)
public class FIPSAuditUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Assume.assumeTrue(PropsValues.FIPS_ENABLED);
	}

	@Test
	public void testWriteCreatesAnOwnerOnlyAuditLogFile() throws Exception {
		Path path = _getAuditLogPath();

		FileSystem fileSystem = path.getFileSystem();

		Set<String> supportedFileAttributeViews =
			fileSystem.supportedFileAttributeViews();

		Assume.assumeTrue(supportedFileAttributeViews.contains("posix"));

		Assert.assertEquals(
			PosixFilePermissions.fromString("rw-------"),
			Files.getPosixFilePermissions(path));
	}

	@Test
	public void testWriteCreatesTheAuditLogFile() {
		Path path = _getAuditLogPath();

		Assert.assertTrue(Files.exists(path));
	}

	@Test
	public void testWriteProducesACanonicalTimestamp() throws Exception {
		for (JSONObject jsonObject : _getRecordJSONObjects()) {
			String timestamp = jsonObject.getString("timestamp");

			Assert.assertTrue(
				timestamp.matches(
					"\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
		}
	}

	@Test
	public void testWriteProducesParseableRecords() throws Exception {
		for (String line : Files.readAllLines(_getAuditLogPath())) {
			Assert.assertTrue(Validator.isNotNull(line));

			JSONFactoryUtil.createJSONObject(line);
		}
	}

	@Test
	public void testWriteProducesTheCommonEnvelope() throws Exception {
		for (JSONObject jsonObject : _getRecordJSONObjects()) {
			for (String envelopeKey : _ENVELOPE_KEYS) {
				Assert.assertTrue(jsonObject.has(envelopeKey));
			}
		}
	}

	@Test
	public void testWriteRecordsTheBootStateTransitions() throws Exception {
		List<String> transitions = new ArrayList<>();

		for (JSONObject jsonObject : _getRecordJSONObjects()) {
			String eventType = jsonObject.getString("event-type");

			if (!eventType.equals("fips-state-transition")) {
				continue;
			}

			JSONObject fieldsJSONObject = jsonObject.getJSONObject("fields");

			transitions.add(
				StringBundler.concat(
					fieldsJSONObject.getString("from-state"), " to ",
					fieldsJSONObject.getString("to-state")));
		}

		Assert.assertTrue(transitions.contains("INITIALIZING to SELF_TEST"));
		Assert.assertTrue(transitions.contains("SELF_TEST to OPERATIONAL"));
	}

	@Test
	public void testWriteSequencesRecordsContiguously() throws Exception {
		long previousEventSequence = 0;

		for (JSONObject jsonObject : _getRecordJSONObjects()) {
			long eventSequence = jsonObject.getLong("event-sequence");

			if (eventSequence > previousEventSequence) {
				Assert.assertEquals(previousEventSequence + 1, eventSequence);
			}

			previousEventSequence = eventSequence;
		}
	}

	private Path _getAuditLogPath() {
		LocalDate localDate = LocalDate.now(ZoneOffset.UTC);

		return Paths.get(
			PropsValues.LIFERAY_HOME, "logs",
			StringBundler.concat("fips-audit.", localDate, ".ndjson"));
	}

	private List<JSONObject> _getRecordJSONObjects() throws Exception {
		List<JSONObject> jsonObjects = new ArrayList<>();

		for (String line : Files.readAllLines(_getAuditLogPath())) {
			jsonObjects.add(JSONFactoryUtil.createJSONObject(line));
		}

		return jsonObjects;
	}

	private static final String[] _ENVELOPE_KEYS = {
		"cmvp-certificate-id", "deployment-instance-id", "event-schema-version",
		"event-sequence", "event-type", "fields", "provider-name",
		"provider-version", "severity", "timestamp"
	};

}