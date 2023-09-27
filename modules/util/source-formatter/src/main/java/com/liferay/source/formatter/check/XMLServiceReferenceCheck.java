/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLServiceReferenceCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("/service.xml") ||
			absolutePath.contains("/gradleTest/") ||
			absolutePath.contains("/samples/") ||
			absolutePath.contains("-test-service")) {

			return content;
		}

		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return content;
		}

		int pos = absolutePath.lastIndexOf(StringPool.SLASH);

		String dirName = absolutePath.substring(0, pos + 1);

		Element rootElement = document.getRootElement();

		String packageName = rootElement.attributeValue("package-path");

		for (Element entityElement :
				(List<Element>)rootElement.elements("entity")) {

			if (GetterUtil.getBoolean(
					entityElement.attributeValue("deprecated"))) {

				continue;
			}

			String entityName = entityElement.attributeValue("name");
			boolean localService = GetterUtil.getBoolean(
				entityElement.attributeValue("local-service"));
			boolean remoteService = GetterUtil.getBoolean(
				entityElement.attributeValue("remote-service"));

			for (Element referenceElement :
					(List<Element>)entityElement.elements("reference")) {

				String referenceEntityName = referenceElement.attributeValue(
					"entity");

				if (isAttributeValue(_AVOID_REFERENCES_KEY, absolutePath)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Avoid using reference '", referenceEntityName,
							"' for Entity '", entityName,
							"', use private variables in *ServiceImpl ",
							"instead"));
				}
				else if (!_isRequiredReference(
							entityName, referenceEntityName, localService,
							remoteService, dirName, packageName)) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Reference '", referenceEntityName,
							"' not needed for Entity '", entityName, "'"));
				}
			}
		}

		return content;
	}

	private boolean _containsReference(
		String content, String varName, String referenceType) {

		Pattern pattern = Pattern.compile(
			StringBundler.concat("\\W", varName, referenceType, "\\W"));

		Matcher matcher = pattern.matcher(content);

		return matcher.find();
	}

	private File _findFile(
		String dirName, String fileName, String packageName) {

		File file = new File(dirName + "service/impl/" + fileName);

		if (file.exists()) {
			return file;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(dirName);
		sb.append("src/main/java/");
		sb.append(
			StringUtil.replace(packageName, CharPool.PERIOD, CharPool.SLASH));
		sb.append("/service/impl/");
		sb.append(fileName);

		file = new File(sb.toString());

		if (file.exists()) {
			return file;
		}

		return null;
	}

	private boolean _isRequiredReference(
			String entityName, String referenceEntityName, boolean localService,
			boolean remoteService, String dirName, String packageName)
		throws IOException {

		String referenceVarName = TextFormatter.format(
			referenceEntityName, TextFormatter.I);

		if (localService) {
			File localServiceImplFile = _findFile(
				dirName, entityName + "LocalServiceImpl.java", packageName);

			if (localServiceImplFile == null) {
				return true;
			}

			String content = FileUtil.read(localServiceImplFile);

			if (_containsReference(content, referenceVarName, "Finder") ||
				_containsReference(content, referenceVarName, "LocalService") ||
				_containsReference(content, referenceVarName, "Persistence")) {

				return true;
			}
		}

		if (!remoteService) {
			return false;
		}

		File serviceImplFile = _findFile(
			dirName, entityName + "ServiceImpl.java", packageName);

		if (serviceImplFile == null) {
			return true;
		}

		String content = FileUtil.read(serviceImplFile);

		if (_containsReference(content, referenceVarName, "Finder") ||
			_containsReference(content, referenceVarName, "LocalService") ||
			_containsReference(content, referenceVarName, "Persistence") ||
			_containsReference(content, referenceVarName, "Service")) {

			return true;
		}

		return false;
	}

	private static final String _AVOID_REFERENCES_KEY = "avoidReferences";

}