/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.schema.manifest.builder;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.io.File;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.time.Instant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Luis Ortiz
 */
public class SchemaManifestBuilderTest {

	@Before
	public void setUp() {
		_baseDir = temporaryFolder.getRoot();

		_manifestFile = new File(_baseDir, _MANIFEST_FILE_NAME);
		_schemaManifestBuilder = new SchemaManifestBuilder(
			_baseDir.getAbsolutePath(), _MANIFEST_FILE_NAME, null);
	}

	@Test
	public void testBuild() throws Exception {
		String module = RandomTestUtil.randomString();

		_writeBndFile(module);

		_schemaManifestBuilder.build();

		String timestamp = _assertManifest(module);

		Instant.parse(timestamp);

		String randomTimestamp = String.valueOf(
			Instant.ofEpochSecond(RandomTestUtil.randomInt()));

		_writeManifest(
			StringUtil.replace(
				_read(_manifestFile), timestamp, randomTimestamp));

		_schemaManifestBuilder.build();

		Assert.assertEquals(randomTimestamp, _assertManifest(module));

		_writeManifest("{}");

		_schemaManifestBuilder.build();

		Assert.assertNotEquals(randomTimestamp, _assertManifest(module));

		module = RandomTestUtil.randomString();

		_writeBndFile(module);

		_schemaManifestBuilder.build();

		_assertManifest(module);
	}

	@Test
	public void testBuildWithCRLFLineEndings() throws Exception {
		_writeBndFile(RandomTestUtil.randomString());

		_schemaManifestBuilder.build();

		String content = StringUtil.replace(_read(_manifestFile), "\n", "\r\n");

		_writeManifest(content);

		_schemaManifestBuilder.build();

		Assert.assertEquals(content, _read(_manifestFile));
	}

	@Test
	public void testBuildWithModule() throws Exception {
		_writeBndFile(RandomTestUtil.randomString());

		String module = RandomTestUtil.randomString();

		SchemaManifestBuilder schemaManifestBuilder = new SchemaManifestBuilder(
			_baseDir.getAbsolutePath(), _MANIFEST_FILE_NAME, module);

		schemaManifestBuilder.build();

		_assertManifest(module);
	}

	@Test
	public void testBuildWithUpgradeStepRegistrator() throws Exception {
		String module = RandomTestUtil.randomString();

		_writeBndFile(module);

		_copyUpgradeStepRegistratorFile("TestUpgradeStepRegistrator");

		_schemaManifestBuilder.build();

		JSONObject manifestJSONObject = new JSONObjectImpl(
			_read(_manifestFile));

		Assert.assertEquals(
			_read("expected/TestUpgradeStepRegistrator.json"),
			JSONUtil.toString(manifestJSONObject.getJSONArray("history")));
		Assert.assertEquals(module, manifestJSONObject.getString("module"));
		Assert.assertEquals(
			"1.10.0", manifestJSONObject.getString("schemaVersion"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildWithoutBndFile() throws Exception {
		_schemaManifestBuilder.build();
	}

	@Test
	public void testGetBundleSymbolicName() throws Exception {
		String bundleSymbolicName = RandomTestUtil.randomString();

		_writeBndFile(bundleSymbolicName);

		Assert.assertEquals(
			bundleSymbolicName,
			_schemaManifestBuilder.getBundleSymbolicName(
				new File(_baseDir, "bnd.bnd")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetBundleSymbolicNameWithoutBndFile() throws Exception {
		_schemaManifestBuilder.getBundleSymbolicName(
			new File(_baseDir, "bnd.bnd"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetBundleSymbolicNameWithoutBundleSymbolicName()
		throws Exception {

		File bndFile = new File(_baseDir, "bnd.bnd");
		String bundleNameString = StringBundler.concat(
			"Bundle-Name: ", RandomTestUtil.randomString(), "\n");

		Files.write(
			bndFile.toPath(),
			bundleNameString.getBytes(StandardCharsets.UTF_8));

		_schemaManifestBuilder.getBundleSymbolicName(bndFile);
	}

	@Test
	public void testGetHistoryJSONArray() throws Exception {
		_copyUpgradeStepRegistratorFile("TestUpgradeStepRegistrator");

		Assert.assertEquals(
			_read("expected/TestUpgradeStepRegistrator.json"),
			JSONUtil.toString(
				_schemaManifestBuilder.getHistoryJSONArray(_baseDir)));
	}

	@Test
	public void testGetHistoryJSONArrayWithoutRegisterCalls() throws Exception {
		_copyUpgradeStepRegistratorFile("InitializationUpgradeStepRegistrator");

		JSONArray historyJSONArray = _schemaManifestBuilder.getHistoryJSONArray(
			_baseDir);

		Assert.assertEquals(0, historyJSONArray.length());
	}

	@Test
	public void testGetManifestJSONObject() {
		JSONArray historyJSONArray = _getHistoryJSONArray("1.0.0");
		String module = RandomTestUtil.randomString();

		JSONObject manifestJSONObject =
			_schemaManifestBuilder.getManifestJSONObject(
				historyJSONArray, module);

		Assert.assertEquals(
			_DESCRIPTION, manifestJSONObject.get("description"));
		Assert.assertEquals(
			historyJSONArray.toString(),
			String.valueOf(manifestJSONObject.get("history")));
		Assert.assertEquals(module, manifestJSONObject.get("module"));
		Assert.assertEquals("1.0.0", manifestJSONObject.get("schemaVersion"));
		Assert.assertFalse(manifestJSONObject.has("timestamp"));
	}

	@Test
	public void testGetManifestJSONObjectWithoutHistory() {
		JSONObject manifestJSONObject =
			_schemaManifestBuilder.getManifestJSONObject(
				new JSONArrayImpl(), RandomTestUtil.randomString());

		Assert.assertFalse(manifestJSONObject.has("schemaVersion"));
	}

	@Test
	public void testGetSchemaVersion() {
		Assert.assertEquals(
			"1.10.0",
			_schemaManifestBuilder.getSchemaVersion(
				_getHistoryJSONArray(
					"1.0.0", "1.10.0", "1.0.0.step-1", "1.9.0")));
	}

	@Test
	public void testGetSchemaVersionWithoutHistory() {
		Assert.assertNull(
			_schemaManifestBuilder.getSchemaVersion(new JSONArrayImpl()));
	}

	@Test
	public void testGetTimestamp() {
		String timestamp = String.valueOf(
			Instant.ofEpochSecond(RandomTestUtil.randomInt()));

		Assert.assertEquals(
			timestamp,
			_schemaManifestBuilder.getTimestamp(
				StringBundler.concat(
					"{\n\t\"module\": \"", RandomTestUtil.randomString(),
					"\",\n\t\"timestamp\": \"", timestamp, "\"\n}")));
	}

	@Test
	public void testGetTimestampWithoutTimestamp() {
		Assert.assertNull(
			_schemaManifestBuilder.getTimestamp(
				StringBundler.concat(
					"{\n\t\"module\": \"", RandomTestUtil.randomString(),
					"\"\n}")));
	}

	@Test
	public void testWrite() throws Exception {
		String module = RandomTestUtil.randomString();

		_schemaManifestBuilder.write(
			_manifestFile,
			_schemaManifestBuilder.getManifestJSONObject(
				new JSONArrayImpl(), module));

		String content = _read(_manifestFile);

		String timestamp = _assertManifest(module);

		Instant.parse(timestamp);

		_schemaManifestBuilder.write(
			_manifestFile,
			_schemaManifestBuilder.getManifestJSONObject(
				new JSONArrayImpl(), module));

		Assert.assertEquals(content, _read(_manifestFile));
	}

	@Test
	public void testWriteWithChangedContent() throws Exception {
		String timestamp = String.valueOf(
			Instant.ofEpochSecond(RandomTestUtil.randomInt()));

		JSONObject manifestJSONObject =
			_schemaManifestBuilder.getManifestJSONObject(
				new JSONArrayImpl(), RandomTestUtil.randomString());

		manifestJSONObject.put("timestamp", timestamp);

		Path manifestFilePath = _manifestFile.toPath();

		Files.createDirectories(manifestFilePath.getParent());

		_writeManifest(JSONUtil.toString(manifestJSONObject));

		String module = RandomTestUtil.randomString();

		_schemaManifestBuilder.write(
			_manifestFile,
			_schemaManifestBuilder.getManifestJSONObject(
				new JSONArrayImpl(), module));

		Assert.assertNotEquals(timestamp, _assertManifest(module));
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private String _assertManifest(String module) throws Exception {
		String content = _read(_manifestFile);

		Matcher matcher = _timestampPattern.matcher(content);

		Assert.assertTrue(matcher.find());

		String timestamp = matcher.group(1);

		Assert.assertEquals(
			StringBundler.concat(
				"{\n\t\"description\": \"", _DESCRIPTION,
				"\",\n\t\"history\": [\n\t],\n\t\"module\": \"", module,
				"\",\n\t\"timestamp\": \"", timestamp, "\"\n}"),
			content);

		return timestamp;
	}

	private void _copyUpgradeStepRegistratorFile(String className)
		throws Exception {

		File file = new File(
			_baseDir,
			"src/main/java/com/liferay/test/internal/upgrade/registry/" +
				className + ".java");

		File dir = file.getParentFile();

		dir.mkdirs();

		String content = _read(className + ".testjava");

		Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	private JSONArray _getHistoryJSONArray(String... toSchemaVersions) {
		JSONArray historyJSONArray = new JSONArrayImpl();

		for (String toSchemaVersion : toSchemaVersions) {
			JSONObject edgeJSONObject = new JSONObjectImpl();

			edgeJSONObject.put("toSchemaVersion", toSchemaVersion);

			historyJSONArray.put(edgeJSONObject);
		}

		return historyJSONArray;
	}

	private String _read(File file) throws Exception {
		return new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
	}

	private String _read(String fileName) throws Exception {
		URL url = SchemaManifestBuilderTest.class.getResource(
			"dependencies/" + fileName);

		return _read(new File(url.toURI()));
	}

	private void _writeBndFile(String bundleSymbolicName) throws Exception {
		File bndFile = new File(_baseDir, "bnd.bnd");
		String content = StringBundler.concat(
			"Bundle-Name: ", RandomTestUtil.randomString(),
			"\nBundle-SymbolicName: ", bundleSymbolicName, "\n");

		Files.write(bndFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	private void _writeManifest(String content) throws Exception {
		Files.write(
			_manifestFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	private static final String _DESCRIPTION = StringBundler.concat(
		"Database changes manifest of one Liferay module, generated by ",
		"Liferay Schema Manifest Builder from its upgrade steps. Do not edit ",
		"it by hand. Top level keys: 'history' stores the upgrade steps that ",
		"move a database between schema versions; 'module' is the module's ",
		"Bundle-SymbolicName, or 'portal' for portal core; 'schemaVersion' is ",
		"the current schema version; 'timestamp' is when the content last ",
		"changed. Rules: 'history' is a SET of edges, not a sequence: array ",
		"order is not meaningful, several edges may share a ",
		"'toSchemaVersion', and only one applies to any given database. ",
		"Compute the upgrade path by following ",
		"'fromSchemaVersion'/'toSchemaVersion'. 'changes' inside an edge IS ",
		"ordered, in execution order. Schema versions are opaque strings: ",
		"never compare or sort them. A change's 'className' is the class that ",
		"implements the step, as the upgrade log names it; for an anonymous ",
		"class it is its binary name, '<registrator>$<n>'. The edge's ",
		"'registrator' plus the change's 'registratorLine' is where the step ",
		"is declared; an upgrade log entry '<class>:<line> - ...' written by ",
		"UpgradeProcessFactory matches the change whose edge 'registrator' is ",
		"<class> and whose 'registratorLine' is <line>. 'customChange' means ",
		"the step's effect could NOT be determined, not that it made no ",
		"change. A database's own schemaVersion for this module is in its ",
		"Release_ table, in the row where servletContextName equals 'module'.");

	private static final String _MANIFEST_FILE_NAME =
		"src/main/resources/META-INF/schema/manifest.json";

	private static final Pattern _timestampPattern = Pattern.compile(
		"\"timestamp\": \"([^\"]*)\"");

	private File _baseDir;
	private File _manifestFile;
	private SchemaManifestBuilder _schemaManifestBuilder;

}