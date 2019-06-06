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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
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
		throws DocumentException, IOException {

		if (!fileName.endsWith("/service.xml")) {
			return content;
		}

		int pos = absolutePath.lastIndexOf(StringPool.SLASH);

		String dirName = absolutePath.substring(0, pos + 1);

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		String packageName = rootElement.attributeValue("package-path");

		for (Element entityElement :
				(List<Element>)rootElement.elements("entity")) {

			String entityName = entityElement.attributeValue("name");
			boolean localService = GetterUtil.getBoolean(
				entityElement.attributeValue("local-service"));
			boolean remoteService = GetterUtil.getBoolean(
				entityElement.attributeValue("remote-service"));

			List<String> requiredEntityNames = _getRequiredEntityNames(
				entityElement);

			for (Element referenceElement :
					(List<Element>)entityElement.elements("reference")) {

				String referenceEntityName = referenceElement.attributeValue(
					"entity");

				if (requiredEntityNames.contains(referenceEntityName)) {
					continue;
				}

				if (!_isRequiredReference(
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

	private List<String> _getRequiredEntityNames(Element entityElement) {
		List<String> requiredEntityNames = new ArrayList<>();

		for (Element columnElement :
				(List<Element>)entityElement.elements("column")) {

			String entityName = columnElement.attributeValue("entity");

			if (entityName != null) {
				requiredEntityNames.add(entityName);
			}
		}

		return requiredEntityNames;
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

}