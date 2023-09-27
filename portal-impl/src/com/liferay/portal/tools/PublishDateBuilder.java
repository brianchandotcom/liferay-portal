/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.xml.SAXReaderFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Yuxing Wu
 */
public class PublishDateBuilder {

	public static void main(String[] args) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(System.in));

		String xmls = bufferedReader.readLine();

		new PublishDateBuilder(StringUtil.split(xmls));
	}

	public PublishDateBuilder(String[] xmls)
		throws DocumentException, IOException {

		System.setProperty("line.separator", StringPool.NEW_LINE);

		for (String xml : xmls) {
			_addDate(xml);
		}
	}

	private Element _addCVPDElement(
		Element libraryElement, String groupId, String artifactId,
		String version) {

		Element cvpdElement = libraryElement.element(_CVPD);

		if (cvpdElement != null) {
			return libraryElement;
		}

		cvpdElement = libraryElement.addElement(_CVPD);

		String date = _getMavenVersionDate(groupId, artifactId, version);

		if (date != null) {
			cvpdElement.setText(date);
		}

		return libraryElement;
	}

	private void _addDate(String xml) throws DocumentException, IOException {
		try {
			SAXReader saxReader = SAXReaderFactory.getSAXReader(
				null, false, false);

			Document document = saxReader.read(new File(xml));

			Element rootElement = document.getRootElement();

			Element versionElement = rootElement.element("version");

			Element librariesElement = versionElement.element("libraries");

			List<Element> libraryElements = librariesElement.elements(
				"library");

			for (Element libraryElement : libraryElements) {
				String value = null;

				String fileNameElementText = libraryElement.elementText(
					"file-name");

				if (fileNameElementText.startsWith("lib/")) {
					value = _getDependencyFromPropertyFile(fileNameElementText);
				}
				else {
					value = _getDependencyFromGradleFile(fileNameElementText);
				}

				String[] dependency = StringUtil.split(value, ':');

				if (dependency.length == 0) {
					continue;
				}

				String groupId = dependency[0];
				String artifactId = dependency[1];
				String version = dependency[2];

				_addCVPDElement(libraryElement, groupId, artifactId, version);

				_addLVPDElement(libraryElement, groupId, artifactId);
			}

			_writeDocument(document, xml);
		}
		catch (DocumentException documentException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read the " + xml, documentException);
			}

			throw new DocumentException("Unable to read the " + xml);
		}
	}

	private Element _addLVPDElement(
		Element libraryElement, String groupId, String artifactId) {

		Element lvpdElement = libraryElement.element(_LVPD);

		if (lvpdElement == null) {
			lvpdElement = libraryElement.addElement(_LVPD);
		}

		String date = _getMavenVersionDate(
			groupId, artifactId, _LATEST_VERSION);

		if (date != null) {
			lvpdElement.setText(date);
		}

		return libraryElement;
	}

	private String _extractGradleDependency(String content) {
		char[] quote = {CharPool.QUOTE, CharPool.QUOTE};

		int groupStartIndex = content.indexOf("group:");

		int groupEndIndex = content.indexOf(StringPool.COMMA, groupStartIndex);

		int nameStartIndex = content.indexOf("name:");

		int nameEndIndex = content.indexOf(StringPool.COMMA, nameStartIndex);

		int versionStartIndex = content.indexOf("version:");

		String groupPart = content.substring(groupStartIndex, groupEndIndex);

		String group = StringUtil.extractLast(groupPart, StringPool.COLON);

		group = StringUtil.removeChars(group.trim(), quote);

		String namePart = content.substring(nameStartIndex, nameEndIndex);

		String name = StringUtil.extractLast(namePart, StringPool.COLON);

		name = StringUtil.removeChars(name.trim(), quote);

		String versionPart = content.substring(versionStartIndex);

		String version = StringUtil.extractLast(versionPart, StringPool.COLON);

		version = StringUtil.removeChars(version.trim(), quote);

		return StringBundler.concat(
			group, StringPool.COLON, name, StringPool.COLON, version);
	}

	private String _formatDate(long time) {
		String format = "yyyy-MM-dd HH:mm:ss";

		DateFormat dateFormat = new SimpleDateFormat(format);

		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		Timestamp timestamp = new Timestamp(time);

		return dateFormat.format(timestamp);
	}

	private Map<String, File> _getBundleNameGradleFileMap() {
		if (_bundleNameGradleFileMap != null) {
			return _bundleNameGradleFileMap;
		}

		File folder = new File(System.getProperty("project.modules.dir"));

		_bundleNameGradleFileMap = new HashMap<>();

		return _getBundleNameGradleFileMap(folder);
	}

	private Map<String, File> _getBundleNameGradleFileMap(File folder) {
		File[] files = folder.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				_getBundleNameGradleFileMap(file);

				continue;
			}

			String name = file.getName();

			if (!name.equals("bnd.bnd")) {
				continue;
			}

			String bundleSymbolicName = _getBundleSymbolicName(file);

			if (bundleSymbolicName == null) {
				continue;
			}

			File gradleFile = new File(file.getParent() + "/build.gradle");

			if (!gradleFile.exists()) {
				continue;
			}

			_bundleNameGradleFileMap.put(bundleSymbolicName, gradleFile);
		}

		return _bundleNameGradleFileMap;
	}

	private String _getBundleSymbolicName(File file) {
		Path path = Paths.get(file.getAbsolutePath());

		try {
			BufferedReader bufferedReader = Files.newBufferedReader(path);

			String content = null;

			while ((content = bufferedReader.readLine()) != null) {
				if (StringUtil.startsWith(content, "Bundle-SymbolicName")) {
					int start = content.indexOf(StringPool.COLON);

					String name = content.substring(start + 1);

					return name.trim();
				}
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}

		return null;
	}

	private String _getDependencyFromGradleFile(String fileNameElementText) {
		String dependency = null;

		String bundleJar = StringUtil.extractFirst(
			fileNameElementText, StringPool.EXCLAMATION);

		int x = bundleJar.lastIndexOf(StringPool.PERIOD);

		String bundleName = bundleJar.substring(0, x);

		File gradleFile = _getGradleFile(bundleName);

		Path path = Paths.get(gradleFile.getAbsolutePath());

		try {
			BufferedReader bufferedReader = Files.newBufferedReader(path);

			String content = null;

			String artifactJar = StringUtil.extractLast(
				fileNameElementText, StringPool.EXCLAMATION);

			int y = artifactJar.lastIndexOf(StringPool.PERIOD);

			String artifactName = artifactJar.substring(0, y);

			StringBuilder regexSB = new StringBuilder();

			regexSB.append(".*group:.*name:\\s*\"");
			regexSB.append(artifactName);
			regexSB.append("\".*version:.*");

			while ((content = bufferedReader.readLine()) != null) {
				if (content.matches(regexSB.toString())) {
					dependency = _extractGradleDependency(content);

					break;
				}
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}

		return dependency;
	}

	private String _getDependencyFromPropertyFile(String fileNameElementText) {
		int startIndex = fileNameElementText.lastIndexOf("/");

		int endIndex = fileNameElementText.lastIndexOf(".");

		String name = fileNameElementText.substring(startIndex + 1, endIndex);

		return _dependenciesProperties.getProperty(name);
	}

	private File _getGradleFile(String bundleName) {
		Map<String, File> bundleNameGradleFileMap =
			_getBundleNameGradleFileMap();

		return bundleNameGradleFileMap.get(bundleName);
	}

	private String _getMavenVersionDate(
		String groupId, String artifactId, String version) {

		JSONObject mavenVersionDetailsJSONObject =
			_getMavenVersionDetailsJSONObject(groupId, artifactId);

		if (mavenVersionDetailsJSONObject == null) {
			return null;
		}

		JSONObject responseJSONObject =
			mavenVersionDetailsJSONObject.getJSONObject("response");

		int numFound = responseJSONObject.getInt("numFound");

		if (numFound == 0) {
			return null;
		}

		JSONObject docJSONObject = null;

		JSONArray docsJSONArray = responseJSONObject.getJSONArray("docs");

		if (version.equals(_LATEST_VERSION)) {
			docJSONObject = docsJSONArray.getJSONObject(0);
		}
		else {
			String key = StringBundler.concat(
				groupId, StringPool.COLON, artifactId, StringPool.COLON,
				version);

			Iterator<Object> iterator = docsJSONArray.iterator();

			while (iterator.hasNext()) {
				docJSONObject = (JSONObject)iterator.next();

				String id = docJSONObject.getString("id");

				if (id.equals(key)) {
					break;
				}
			}
		}

		long timestamp = docJSONObject.getLong("timestamp");

		return _formatDate(timestamp);
	}

	private JSONObject _getMavenVersionDetailsJSONObject(
		String groupId, String artifactId) {

		String key = groupId + ":" + artifactId;

		JSONObject mavenVersionDetailsJSONObject =
			_mavenVersionDetailsCache.get(key);

		if (mavenVersionDetailsJSONObject != null) {
			return mavenVersionDetailsJSONObject;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("https://search.maven.org/solrsearch/select?q=g:");
		sb.append(groupId);
		sb.append("+AND+");
		sb.append("a:");
		sb.append(artifactId);
		sb.append("&core=gav&rows=200&wt=json");

		try {
			URL url = new URL(sb.toString());

			HttpURLConnection connection =
				(HttpURLConnection)url.openConnection();

			connection.setRequestMethod(HttpMethods.GET);

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));

				StringBuilder response = new StringBuilder();

				String line = null;

				while ((line = bufferedReader.readLine()) != null) {
					response.append(line);
				}

				mavenVersionDetailsJSONObject = new JSONObject(
					response.toString());

				_mavenVersionDetailsCache.put(
					key, mavenVersionDetailsJSONObject);
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}

		return mavenVersionDetailsJSONObject;
	}

	private void _writeDocument(Document document, String xml)
		throws IOException {

		try {
			OutputFormat outFormat = OutputFormat.createPrettyPrint();

			outFormat.setIndent("\t");
			outFormat.setOmitEncoding(true);
			outFormat.setExpandEmptyElements(false);
			outFormat.setPadText(false);

			OutputStream outputStream = new FileOutputStream(xml);

			XMLWriter xmlWriter = new XMLWriter(outputStream, outFormat);

			xmlWriter.write(document);
			xmlWriter.flush();
			xmlWriter.close();
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to write the " + xml, ioException);
			}

			throw new IOException("Unable to write the " + xml);
		}
	}

	private static final String _CVPD = "current-version-publish-date";

	private static final String _LATEST_VERSION = "LATEST VERSION";

	private static final String _LVPD = "latest-version-publish-date";

	private static final Log _log = LogFactoryUtil.getLog(
		PublishDateBuilder.class);

	private static final Properties _dependenciesProperties;
	private static final Map<String, JSONObject> _mavenVersionDetailsCache =
		new HashMap<>();

	static {
		String projectDir = System.getProperty("project.dir");

		_dependenciesProperties = new Properties();

		try {
			InputStream developmentPropertiesInputStream =
				new BufferedInputStream(
					new FileInputStream(
						projectDir +
							"/lib/development/dependencies.properties"));

			InputStream portalPropertiesInputStream = new BufferedInputStream(
				new FileInputStream(
					projectDir + "/lib/portal/dependencies.properties"));

			_dependenciesProperties.load(developmentPropertiesInputStream);
			_dependenciesProperties.load(portalPropertiesInputStream);

			_dependenciesProperties.put(
				"ant-contrib", "ant-contrib:ant-contrib:1.0b3");
			_dependenciesProperties.put(
				"antelope", "com.liferay:ise.antelope:3.4.0");
			_dependenciesProperties.put("bsh", "org.beanshell:bsh:2.0b4");
			_dependenciesProperties.put(
				"xmltask", "com.oopsconsultancy:xmltask:1.16");

			developmentPropertiesInputStream.close();
			portalPropertiesInputStream.close();
		}
		catch (FileNotFoundException fileNotFoundException) {
			_log.error(fileNotFoundException);
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}
	}

	private Map<String, File> _bundleNameGradleFileMap;

}