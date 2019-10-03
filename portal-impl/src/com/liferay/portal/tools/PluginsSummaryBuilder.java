/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class PluginsSummaryBuilder {

	public static void main(String[] args) throws Exception {
		ToolDependencies.wireBasic();

		File pluginsDir = new File(System.getProperty("plugins.dir"));

		new PluginsSummaryBuilder(pluginsDir);
	}

	public PluginsSummaryBuilder(File pluginsDir) throws Exception {
		_pluginsDir = pluginsDir;

		String latestHASH = _getLatestHASH(pluginsDir);

		_latestHASH = latestHASH;

		_createPluginsSummary();
	}

	private void _createPluginsSummary() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_pluginsDir);
		directoryScanner.setExcludes(
			new String[] {"**\\tmp\\**", "**\\tools\\**"});
		directoryScanner.setIncludes(
			new String[] {"**\\liferay-plugin-package.properties"});

		directoryScanner.scan();

		String[] fileNames = directoryScanner.getIncludedFiles();

		Arrays.sort(fileNames);

		_createPluginsSummary(fileNames);
	}

	private void _createPluginsSummary(String[] fileNames) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("<plugins-summary>\n");

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			_createPluginSummary(sb, fileName);
		}

		for (String author : _distinctAuthors) {
			sb.append("\t<author>");
			sb.append(author);
			sb.append("</author>\n");
		}

		for (String license : _distinctLicenses) {
			sb.append("\t<license>");
			sb.append(license);
			sb.append("</license>\n");
		}

		sb.append("</plugins-summary>");

		FileUtil.write(_pluginsDir + "/summary.xml", sb.toString());
	}

	private void _createPluginSummary(StringBundler sb, String fileName)
		throws Exception {

		String content = FileUtil.read(fileName);

		int x = fileName.indexOf(StringPool.SLASH);

		String type = fileName.substring(0, x);

		if (type.endsWith("s")) {
			type = type.substring(0, type.length() - 1);
		}

		x = fileName.indexOf(StringPool.SLASH, x) + 1;

		int y = fileName.indexOf(StringPool.SLASH, x);

		String artifactId = fileName.substring(x, y);

		Properties properties = PropertiesUtil.load(content);

		String name = _readProperty(properties, "name");
		String tags = _readProperty(properties, "tags");
		String shortDescription = _readProperty(
			properties, "short-description");
		String longDescription = _readProperty(properties, "long-description");
		String changeLog = _readProperty(properties, "change-log");
		String pageURL = _readProperty(properties, "page-url");
		String author = _readProperty(properties, "author");
		String licenses = _readProperty(properties, "licenses");
		String liferayVersions = _readProperty(properties, "liferay-versions");

		_distinctAuthors.add(author);
		_distinctLicenses.add(licenses);

		sb.append("\t<plugin>\n");

		_writeElement(sb, "artifact-id", artifactId, 2);
		_writeElement(sb, "name", name, 2);
		_writeElement(sb, "type", type, 2);
		_writeElement(sb, "tags", tags, 2);
		_writeElement(sb, "short-description", shortDescription, 2);
		_writeElement(sb, "long-description", longDescription, 2);
		_writeElement(sb, "change-log", changeLog, 2);
		_writeElement(sb, "page-url", pageURL, 2);
		_writeElement(sb, "author", author, 2);
		_writeElement(sb, "licenses", licenses, 2);
		_writeElement(sb, "liferay-versions", liferayVersions, 2);

		sb.append("\t\t<releng>\n");
		sb.append(_readReleng(fileName, properties));
		sb.append("\t\t</releng>\n");
		sb.append("\t</plugin>\n");
	}

	private Set<String> _extractTicketIds(File pluginDir, String range)
		throws Exception {

		Set<String> ticketIds = new TreeSet<>(
			new NaturalOrderStringComparator());

		Runtime runtime = Runtime.getRuntime();

		String command = "git log " + range + " .";

		if (OSDetector.isWindows()) {
			command = "cmd /c " + command;
		}

		Process process = runtime.exec(command, null, pluginDir);

		String content = StringUtil.read(process.getInputStream());

		content = StringUtil.replace(content, '\n', ' ');

		for (String ticketIdPrefix : _TICKET_ID_PREFIXES) {
			int x = 0;

			while (true) {
				x = content.indexOf(ticketIdPrefix + "-", x);

				if (x == -1) {
					break;
				}

				int y = x + ticketIdPrefix.length() + 1;

				while (true) {
					if ((y + 1) > content.length()) {
						break;
					}

					if (Character.isDigit(content.charAt(y))) {
						y++;
					}
					else {
						break;
					}
				}

				String ticketId = content.substring(x, y);

				ticketIds.add(ticketId);

				x = y;
			}
		}

		File buildXmlFile = new File(pluginDir, "build.xml");

		System.out.println("## read a " + buildXmlFile);

		String buildXmlContent = _fileImpl.read(buildXmlFile);

		int x = buildXmlContent.indexOf("import.shared");

		if (x == -1) {
			return ticketIds;
		}

		x = buildXmlContent.indexOf("value=\"", x);
		x = buildXmlContent.indexOf("\"", x);

		int y = buildXmlContent.indexOf("\" />", x);

		if ((x == -1) || (y == -1)) {
			return ticketIds;
		}

		String[] importShared = StringUtil.split(
			buildXmlContent.substring(x + 1, y));

		if (importShared.length == 0) {
			return ticketIds;
		}

		for (String currentImportShared : importShared) {
			File currentImportSharedDir = new File(
				pluginDir, "../../shared/" + currentImportShared);

			if (!currentImportSharedDir.exists()) {
				continue;
			}

			ticketIds.addAll(_extractTicketIds(currentImportSharedDir, range));
		}

		return ticketIds;
	}

	private String _getChangeLogEntry(
		int changeLogVersion, String range, String ticketIdsString) {

		StringBundler sb = new StringBundler(8);

		if (changeLogVersion > 1) {
			sb.append("\n\n");
		}

		sb.append("#\n");
		sb.append("# Module Incremental Version ");
		sb.append(changeLogVersion);
		sb.append("\n#\n");
		sb.append(range);
		sb.append("=");
		sb.append(ticketIdsString);

		return sb.toString();
	}

	private String _getLatestHASH(File pluginDir) throws Exception {
		Runtime runtime = Runtime.getRuntime();

		String command = "git rev-parse HEAD";

		if (OSDetector.isWindows()) {
			command = "cmd /c " + command;
		}

		Process process = runtime.exec(command, null, pluginDir);

		return StringUtil.read(process.getInputStream());
	}

	private String _readProperty(Properties properties, String key) {
		return GetterUtil.getString(properties.getProperty(key));
	}

	private String _readReleng(
			String fileName, Properties pluginPackageProperties)
		throws Exception {

		int x = fileName.indexOf("WEB-INF");

		String relativeWebInfDirName = fileName.substring(0, x + 8);

		String fullWebInfDirName =
			_pluginsDir + StringPool.SLASH + relativeWebInfDirName;

		String relengPropertiesFileName =
			fullWebInfDirName + "liferay-releng.properties";

		Properties relengProperties = null;

		if (FileUtil.exists(relengPropertiesFileName)) {
			String relengPropertiesContent = FileUtil.read(
				relengPropertiesFileName);

			relengProperties = PropertiesUtil.load(relengPropertiesContent);
		}
		else {
			relengProperties = new Properties();
		}

		String relengPropertiesContent = _updateRelengPropertiesFile(
			relengPropertiesFileName, relengProperties);

		relengProperties = PropertiesUtil.load(relengPropertiesContent);

		StringBundler sb = new StringBundler();

		_writeElement(sb, "bundle", relengProperties, 3);
		_writeElement(sb, "category", relengProperties, 3);
		_writeElement(sb, "demo-url", relengProperties, 3);
		_writeElement(sb, "dependent-apps", relengProperties, 3);

		if (FileUtil.exists(fullWebInfDirName + "releng/icons/90x90.png")) {
			_writeElement(
				sb, "icon", relativeWebInfDirName + "releng/icons/90x90.png",
				3);
		}

		_writeElement(sb, "labs", relengProperties, 3);
		_writeElement(sb, "marketplace", relengProperties, 3);
		_writeElement(sb, "public", relengProperties, 3);

		String fullScreenshotsDirName =
			fullWebInfDirName + "releng/screenshots/";

		if (FileUtil.exists(fullScreenshotsDirName)) {
			String[] screenshotsFileNames = FileUtil.listFiles(
				fullScreenshotsDirName);

			Arrays.sort(screenshotsFileNames);

			String relativeScreenshotsDirName =
				relativeWebInfDirName + "releng/screenshots/";

			for (String screenshotsFileName : screenshotsFileNames) {
				if (screenshotsFileName.equals("Thumbs.db") ||
					screenshotsFileName.endsWith(".png")) {

					FileUtil.delete(
						fullScreenshotsDirName + screenshotsFileName);
				}

				if (!screenshotsFileName.endsWith(".jpg")) {
					continue;
				}

				_writeElement(
					sb, "screenshot",
					relativeScreenshotsDirName + screenshotsFileName, 3);
			}
		}

		_writeElement(sb, "support-url", relengProperties, 3);
		_writeElement(sb, "supported", relengProperties, 3);

		File relengChangeLogFile = new File(
			fullWebInfDirName + "liferay-releng.changelog");

		if (GetterUtil.getBoolean(
				relengProperties.getProperty("marketplace"))) {

			_updateRelengChangeLogFile(
				pluginPackageProperties, relengChangeLogFile, relengProperties);
		}
		else {
			relengChangeLogFile.delete();
		}

		return sb.toString();
	}

	private void _updateRelengChangeLogFile(
			Properties pluginPackageProperties, File relengChangeLogFile,
			Properties relengProperties)
		throws Exception {

		StringBundler sb = new StringBundler();

		int changeLogVersion = 0;

		int moduleIncrementalVersion = GetterUtil.getInteger(
			pluginPackageProperties.getProperty("module-incremental-version"));

		if (!relengChangeLogFile.exists()) {
			FileUtil.write(relengChangeLogFile, "TEMP=");
		}

		String relengChangeLogContent = FileUtil.read(relengChangeLogFile);

		List<String> relengChangeLogEntries = new ArrayList<>();

		String[] relengChangeLogEntriesArray = StringUtil.split(
			relengChangeLogContent, "\n");

		for (int i = 0; i < relengChangeLogEntriesArray.length; i++) {
			String relengChangeLogEntry = relengChangeLogEntriesArray[i];

			if (Validator.isNull(relengChangeLogEntry) ||
				relengChangeLogEntry.startsWith("#")) {

				continue;
			}

			relengChangeLogEntries.add(relengChangeLogEntry);

			if (((i + 1) == relengChangeLogEntriesArray.length) &&
				!relengChangeLogEntry.contains("HEAD=") &&
				!relengChangeLogEntry.contains("TEMP=") &&
				!relengChangeLogEntry.contains(_latestHASH) &&
				!relengChangeLogEntries.isEmpty()) {

				int x = relengChangeLogEntry.indexOf("..");

				int y = relengChangeLogEntry.indexOf("=", x);

				String range =
					relengChangeLogEntry.substring(x + 2, y) + "^.." +
						_latestHASH;

				relengChangeLogEntries.add(range);
			}
		}

		File webInfDir = relengChangeLogFile.getParentFile();

		File docrootDir = webInfDir.getParentFile();

		File pluginDir = docrootDir.getParentFile();

		for (String relengChangeLogEntry : relengChangeLogEntries) {
			String[] relengChangeLogEntryParts = StringUtil.split(
				relengChangeLogEntry, "=");

			String range = relengChangeLogEntryParts[0];

			if (range.equals("TEMP")) {
				changeLogVersion++;

				sb.append(
					_getChangeLogEntry(
						changeLogVersion, range, StringPool.BLANK));

				break;
			}

			Set<String> ticketIds = _extractTicketIds(pluginDir, range);

			if (range.endsWith("^.." + _latestHASH) && ticketIds.isEmpty() &&
				(relengChangeLogEntries.size() > 1)) {

				continue;
			}

			if (ticketIds.isEmpty()) {
				System.out.println(
					pluginDir + " does not have changes for range " + range);
			}

			String[] dependentApps = StringUtil.split(
				relengProperties.getProperty("dependent-apps"));

			for (String dependentApp : dependentApps) {
				dependentApp = dependentApp.trim();

				if (dependentApp.equals("resources-impoter-web")) {
					continue;
				}

				String dependentAppDirName = null;

				if (dependentApp.endsWith("-hook")) {
					dependentAppDirName = "hooks";
				}
				else if (dependentApp.endsWith("-layouttpl")) {
					dependentAppDirName = "layouttpl";
				}
				else if (dependentApp.endsWith("-portlet")) {
					dependentAppDirName = "portlets";
				}
				else if (dependentApp.endsWith("-theme")) {
					dependentAppDirName = "themes";
				}
				else if (dependentApp.endsWith("-web")) {
					dependentAppDirName = "webs";
				}

				File dependentAppDir = new File(
					_pluginsDir, dependentAppDirName + "/" + dependentApp);

				if (!dependentAppDir.exists()) {
					throw new RuntimeException(
						dependentAppDir + " does not exist");
				}

				ticketIds.addAll(_extractTicketIds(dependentAppDir, range));
			}

			String ticketIdsString = StringUtil.merge(
				ticketIds.toArray(new String[0]), " ");

			changeLogVersion++;

			sb.append(
				_getChangeLogEntry(changeLogVersion, range, ticketIdsString));
		}

		File pluginPackagePropertiesFile = new File(
			webInfDir, "liferay-plugin-package.properties");

		String pluginPackagePropertiesContent = FileUtil.read(
			pluginPackagePropertiesFile);

		if (!pluginPackagePropertiesContent.contains("long-description")) {
			int x = pluginPackagePropertiesContent.indexOf("change-log=");

			pluginPackagePropertiesContent =
				pluginPackagePropertiesContent.substring(0, x) +
					"long-description=\n" +
						pluginPackagePropertiesContent.substring(x);
		}

		if (moduleIncrementalVersion != changeLogVersion) {
			pluginPackagePropertiesContent = StringUtil.replace(
				pluginPackagePropertiesContent,
				"module-incremental-version=" + moduleIncrementalVersion,
				"module-incremental-version=" + changeLogVersion);
		}

		FileUtil.write(
			pluginPackagePropertiesFile, pluginPackagePropertiesContent);

		FileUtil.write(relengChangeLogFile, sb.toString());

		File relengChangeLogMD5File = new File(
			webInfDir, "liferay-releng.changelog.md5");

		String md5Checksum = FileUtil.getMD5Checksum(relengChangeLogFile);

		FileUtil.write(relengChangeLogMD5File, md5Checksum);
	}

	private String _updateRelengPropertiesFile(
			String relengPropertiesFileName, Properties relengProperties)
		throws Exception {

		StringBundler sb = new StringBundler();

		_writeProperty(sb, relengProperties, "bundle", "false");
		_writeProperty(sb, relengProperties, "category", "");
		_writeProperty(sb, relengProperties, "demo-url", "");
		_writeProperty(sb, relengProperties, "dependent-apps", "");
		_writeProperty(sb, relengProperties, "labs", "true");
		_writeProperty(sb, relengProperties, "marketplace", "false");
		_writeProperty(sb, relengProperties, "public", "true");
		_writeProperty(sb, relengProperties, "support-url", "");
		_writeProperty(sb, relengProperties, "supported", "false");

		String relengPropertiesContent = sb.toString();

		FileUtil.write(relengPropertiesFileName, relengPropertiesContent);

		return relengPropertiesContent;
	}

	private void _writeElement(
		StringBundler sb, String name, Properties properties, int tabsCount) {

		_writeElement(sb, name, _readProperty(properties, name), tabsCount);
	}

	private void _writeElement(
		StringBundler sb, String name, String value, int tabsCount) {

		for (int i = 0; i < tabsCount; i++) {
			sb.append("\t");
		}

		sb.append("<");
		sb.append(name);
		sb.append("><![CDATA[");
		sb.append(value);
		sb.append("]]></");
		sb.append(name);
		sb.append(">\n");
	}

	private void _writeProperty(
		StringBundler sb, Properties properties, String key,
		String defaultValue) {

		String value = GetterUtil.getString(
			properties.getProperty(key), defaultValue);

		if (sb.index() > 0) {
			sb.append(StringPool.NEW_LINE);
		}

		sb.append(key);
		sb.append(StringPool.EQUAL);
		sb.append(value);
	}

	private static final String[] _TICKET_ID_PREFIXES = {
		"CLDSVCS", "LPS", "SOS", "SYNC"
	};

	private static final FileImpl _fileImpl = FileImpl.getInstance();

	private final Set<String> _distinctAuthors = new TreeSet<>();
	private final Set<String> _distinctLicenses = new TreeSet<>();
	private final String _latestHASH;
	private final File _pluginsDir;

}