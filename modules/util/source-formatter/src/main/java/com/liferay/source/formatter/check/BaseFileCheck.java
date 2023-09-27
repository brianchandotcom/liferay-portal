/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.comparator.ElementComparator;
import com.liferay.source.formatter.processor.SourceProcessor;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

/**
 * @author Hugo Huijser
 */
public abstract class BaseFileCheck
	extends BaseSourceCheck implements FileCheck {

	@Override
	public String process(
			SourceProcessor sourceProcessor, String fileName,
			String absolutePath, String content)
		throws Exception {

		clearSourceFormatterMessages(fileName);

		setSourceProcessor(sourceProcessor);

		return doProcess(fileName, absolutePath, content);
	}

	protected void checkElementOrder(
		String fileName, Element rootElement, String elementName,
		String parentElementName, ElementComparator elementComparator) {

		if (rootElement == null) {
			return;
		}

		Node previousNode = null;

		Iterator<Node> iterator = rootElement.nodeIterator();

		while (iterator.hasNext()) {
			Node curNode = (Node)iterator.next();

			if (curNode instanceof Text) {
				Text text = (Text)curNode;

				if (!StringUtil.startsWith(
						StringUtil.trim(text.asXML()), CharPool.POUND)) {

					continue;
				}
			}

			if (previousNode == null) {
				previousNode = curNode;

				continue;
			}

			if ((curNode instanceof Element) &&
				(previousNode instanceof Element)) {

				Element curElement = (Element)curNode;
				Element previousElement = (Element)previousNode;

				String curElementName = curElement.getName();
				String previousElementName = previousElement.getName();

				if (curElementName.equals(elementName) &&
					previousElementName.equals(elementName) &&
					(elementComparator.compare(previousElement, curElement) >
						0)) {

					StringBundler sb = new StringBundler(9);

					sb.append("Incorrect order '");

					if (Validator.isNotNull(parentElementName)) {
						sb.append(parentElementName);
						sb.append(StringPool.POUND);
					}

					sb.append(elementName);
					sb.append("': '");
					sb.append(
						elementComparator.getElementName(previousElement));
					sb.append("' should come after '");
					sb.append(elementComparator.getElementName(curElement));
					sb.append("'");

					addMessage(fileName, sb.toString());
				}
			}

			previousNode = curNode;
		}
	}

	protected abstract String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception;

	protected int getLineLength(String line) {
		int lineLength = 0;

		int tabLength = 4;

		for (char c : line.toCharArray()) {
			if (c == CharPool.TAB) {
				for (int i = 0; i < tabLength; i++) {
					lineLength++;
				}

				tabLength = 4;
			}
			else {
				lineLength++;

				tabLength--;

				if (tabLength <= 0) {
					tabLength = 4;
				}
			}
		}

		return lineLength;
	}

	protected String getPortalVersion(boolean privateApp) throws IOException {
		String portalVersion = _getPublicPortalVersion();

		if (privateApp) {
			portalVersion = _getPrivatePortalVersion();
		}

		if (Validator.isNull(portalVersion) || privateApp) {
			return portalVersion;
		}

		return portalVersion;
	}

	private synchronized String _getPrivatePortalVersion() throws IOException {
		if (_privatePortalVersion != null) {
			return _privatePortalVersion;
		}

		_privatePortalVersion = StringPool.BLANK;

		if (!isPortalSource()) {
			return _privatePortalVersion;
		}

		File workingDirPropertiesFile = new File(
			getPortalDir(), "working.dir.properties");

		if (!workingDirPropertiesFile.exists()) {
			return _privatePortalVersion;
		}

		String content = FileUtil.read(workingDirPropertiesFile);

		Matcher matcher = _privateBranchNamePattern.matcher(content);

		if (!matcher.find()) {
			return _privatePortalVersion;
		}

		String privateBranchName = StringUtil.trim(matcher.group(1));

		if (Validator.isNull(privateBranchName)) {
			return _privatePortalVersion;
		}

		String s = Pattern.quote("lp.version[" + privateBranchName + "]=");

		Pattern pattern = Pattern.compile(s + "(.*)");

		matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return _privatePortalVersion;
		}

		_privatePortalVersion = StringUtil.trim(matcher.group(1));

		return _privatePortalVersion;
	}

	private synchronized String _getPublicPortalVersion() throws IOException {
		if (_publicPortalVersion != null) {
			return _publicPortalVersion;
		}

		_publicPortalVersion = StringPool.BLANK;

		if (!isPortalSource()) {
			return _publicPortalVersion;
		}

		File workingDirPropertiesFile = new File(
			getPortalDir(), "working.dir.properties");

		if (workingDirPropertiesFile.exists()) {
			String content = FileUtil.read(workingDirPropertiesFile);

			Matcher matcher = _privateBranchNamePattern.matcher(content);

			if (matcher.find()) {
				String privateBranchName = StringUtil.trim(matcher.group(1));

				if (Validator.isNotNull(privateBranchName) &&
					privateBranchName.endsWith("-private")) {

					String branchName = StringUtil.replaceLast(
						privateBranchName, "-private", StringPool.BLANK);

					String s = Pattern.quote("lp.version[" + branchName + "]=");

					Pattern pattern = Pattern.compile(s + "(.*)");

					matcher = pattern.matcher(content);

					if (matcher.find()) {
						String match = matcher.group(1);

						_publicPortalVersion = StringUtil.trim(match);
					}
				}
			}
		}
		else {
			File releasePropertiesFile = new File(
				getPortalDir(), _RELEASE_PROPERTIES_FILE_NAME);

			if (releasePropertiesFile.exists()) {
				Properties properties = new Properties();

				properties.load(new FileInputStream(releasePropertiesFile));

				String releaseInfoVersionMajor = properties.getProperty(
					"release.info.version.major");
				String releaseInfoVersionMinor = properties.getProperty(
					"release.info.version.minor");

				if (Validator.isNotNull(releaseInfoVersionMajor) &&
					Validator.isNotNull(releaseInfoVersionMinor)) {

					_publicPortalVersion = StringBundler.concat(
						releaseInfoVersionMajor, ".", releaseInfoVersionMinor,
						".0");
				}
			}
		}

		return _publicPortalVersion;
	}

	private static final String _RELEASE_PROPERTIES_FILE_NAME =
		"release.properties";

	private static final Pattern _privateBranchNamePattern = Pattern.compile(
		"private.branch.name=(.*)\n");

	private String _privatePortalVersion;
	private String _publicPortalVersion;

}