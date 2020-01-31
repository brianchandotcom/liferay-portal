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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class MarkdownSourceFormatterReadmeCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException {

		if (!absolutePath.endsWith("/source-formatter/README.markdown")) {
			return content;
		}

		_populateMaps();

		return _getReadmeContent(absolutePath);
	}

	private void _createChecksTableMarkdown(
			String header, File file, Set<CheckInfo> checkInfoSet,
			File documentationChecksDir, boolean displayCategory,
			boolean displayFileExtensions)
		throws IOException {

		StringBundler sb = new StringBundler();

		sb.append("# ");
		sb.append(header);
		sb.append("\n\n");

		sb.append("Check | ");

		if (displayCategory) {
			sb.append("Category | ");
		}

		if (displayFileExtensions) {
			sb.append("File Extensions | ");
		}

		sb.append("Description\n");

		sb.append("----- | ");

		if (displayCategory) {
			sb.append("-------- | ");
		}

		if (displayFileExtensions) {
			sb.append("--------------- | ");
		}

		sb.append("-----------\n");

		for (CheckInfo checkInfo : checkInfoSet) {
			String checkName = checkInfo.getName();

			String markdownFileName = SourceFormatterUtil.getMarkdownFileName(
				checkName);

			File markdownFile = new File(
				documentationChecksDir, markdownFileName);

			if (markdownFile.exists()) {
				sb.append("[");
				sb.append(checkName);
				sb.append("](");
				sb.append(_DOCUMENTATION_CHECKS_DIR_NAME);
				sb.append(markdownFileName);
				sb.append(") | ");
			}
			else {
				sb.append(checkName);
				sb.append(" | ");
			}

			if (displayCategory) {
				if (Validator.isNotNull(checkInfo.getCategory())) {
					sb.append(checkInfo.getCategory());
					sb.append(" | ");
				}
				else {
					sb.append("| ");
				}
			}

			if (displayFileExtensions) {
				if (Validator.isNotNull(checkInfo.getFileExtensions())) {
					sb.append(checkInfo.getFileExtensions());
					sb.append(" | ");
				}
				else {
					sb.append("| ");
				}
			}

			if (Validator.isNotNull(checkInfo.getDescription())) {
				sb.append(checkInfo.getDescription());
				sb.append(" |");
			}
			else {
				sb.append("|");
			}

			sb.append("\n");
		}

		FileUtil.write(file, StringUtil.trim(sb.toString()));
	}

	private Set<CheckInfo> _getAllChecks() {
		Set<CheckInfo> allChecks = new TreeSet<>();

		for (Map.Entry<String, Set<CheckInfo>> entry :
				_categoryCheckInfoMap.entrySet()) {

			allChecks.addAll(entry.getValue());
		}

		return allChecks;
	}

	private String _getReadmeContent(String absolutePath)
		throws DocumentException, IOException {

		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		String rootFolderLocation = absolutePath.substring(0, x + 1);

		File documentationDir = new File(
			rootFolderLocation + _DOCUMENTATION_DIR_LOCATION);

		File documentationChecksDir = new File(
			documentationDir, _DOCUMENTATION_CHECKS_DIR_NAME);

		StringBundler sb = new StringBundler();

		sb.append("# Source Formatter\n\n");

		String allChecksMarkdownFileName =
			SourceFormatterUtil.getMarkdownFileName("AllChecks");

		sb.append("### [All Checks](");
		sb.append(_DOCUMENTATION_DIR_LOCATION);
		sb.append(allChecksMarkdownFileName);
		sb.append(")\n\n");

		_createChecksTableMarkdown(
			"All Checks", new File(documentationDir, allChecksMarkdownFileName),
			_getAllChecks(), documentationChecksDir, true, true);

		sb.append("## Categories:\n");

		for (Map.Entry<String, Set<CheckInfo>> entry :
				_categoryCheckInfoMap.entrySet()) {

			String category = entry.getKey();

			String markdownFileName = SourceFormatterUtil.getMarkdownFileName(
				StringUtil.removeChar(category, CharPool.SPACE) + "Checks");

			sb.append("- [");
			sb.append(category);
			sb.append("](");
			sb.append(_DOCUMENTATION_DIR_LOCATION);
			sb.append(markdownFileName);
			sb.append(")\n");

			_createChecksTableMarkdown(
				category + " Checks",
				new File(documentationDir, markdownFileName), entry.getValue(),
				documentationChecksDir, false, true);
		}

		sb.append("\n");

		sb.append("## File Extensions:\n");

		for (Map.Entry<SourceProcessorInfo, Set<CheckInfo>> entry :
				_sourceProcessorCheckInfoMap.entrySet()) {

			SourceProcessorInfo sourceProcessorInfo = entry.getKey();

			String sourceProcessorName = sourceProcessorInfo.getName();

			if (sourceProcessorName.equals("all")) {
				continue;
			}

			String markdownFileName = SourceFormatterUtil.getMarkdownFileName(
				sourceProcessorName + "Checks");

			sb.append("- [");
			sb.append(sourceProcessorInfo.getFileExtensions());
			sb.append("](");
			sb.append(_DOCUMENTATION_DIR_LOCATION);
			sb.append(markdownFileName);
			sb.append(")\n");

			_createChecksTableMarkdown(
				"Checks for " + sourceProcessorInfo.getFileExtensions(),
				new File(documentationDir, markdownFileName), entry.getValue(),
				documentationChecksDir, true, false);
		}

		return StringUtil.trim(sb.toString());
	}

	private void _populateMaps() throws DocumentException, IOException {
		String sourceChecksContent = getContent(
			"modules/util/source-formatter/src/main/resources/sourcechecks.xml",
			ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		Document document = SourceUtil.readXML(sourceChecksContent);

		Element rootElement = document.getRootElement();

		for (Element sourceProcessorElement :
				(List<Element>)rootElement.elements("source-processor")) {

			SourceProcessorInfo sourceProcessorInfo = new SourceProcessorInfo(
				sourceProcessorElement.attributeValue("name"));

			for (Element checkElement :
					(List<Element>)sourceProcessorElement.elements("check")) {

				String checkName = checkElement.attributeValue("name");

				Element categoryElement = checkElement.element("category");

				String category = "Miscellaneous";

				if (categoryElement != null) {
					category = categoryElement.attributeValue("name");
				}

				Element descriptionElement = checkElement.element(
					"description");

				String description = StringPool.BLANK;

				if (descriptionElement != null) {
					description = descriptionElement.attributeValue("name");
				}

				CheckInfo checkInfo = new CheckInfo(
					checkName, category, description, sourceProcessorInfo);

				Set<CheckInfo> checkInfoSet =
					_categoryCheckInfoMap.computeIfAbsent(
						category, key -> new TreeSet<>());

				checkInfoSet.add(checkInfo);

				checkInfoSet = _sourceProcessorCheckInfoMap.computeIfAbsent(
					sourceProcessorInfo, key -> new TreeSet<>());

				checkInfoSet.add(checkInfo);
			}
		}
	}

	private static final String _DOCUMENTATION_CHECKS_DIR_NAME = "checks/";

	private static final String _DOCUMENTATION_DIR_LOCATION =
		"src/main/resources/documentation/";

	private final Map<String, Set<CheckInfo>> _categoryCheckInfoMap =
		new TreeMap<>();
	private final Map<SourceProcessorInfo, Set<CheckInfo>>
		_sourceProcessorCheckInfoMap = new TreeMap<>();

	private class CheckInfo implements Comparable<CheckInfo> {

		public CheckInfo(
			String name, String category, String description,
			SourceProcessorInfo sourceProcessorInfo) {

			_name = name;
			_category = category;
			_description = description;
			_sourceProcessorInfo = sourceProcessorInfo;
		}

		@Override
		public int compareTo(CheckInfo checkInfo) {
			return _name.compareTo(checkInfo.getName());
		}

		public String getCategory() {
			return _category;
		}

		public String getDescription() {
			return _description;
		}

		public String getFileExtensions() {
			return _sourceProcessorInfo.getFileExtensions();
		}

		public String getName() {
			return _name;
		}

		private final String _category;
		private final String _description;
		private String _name;
		private final SourceProcessorInfo _sourceProcessorInfo;

	}

	private class SourceProcessorInfo
		implements Comparable<SourceProcessorInfo> {

		public SourceProcessorInfo(String name) {
			_name = name;

			_fileExtensions = _getFileExtensions();
		}

		@Override
		public int compareTo(SourceProcessorInfo sourceProcessorInfo) {
			return _fileExtensions.compareTo(
				sourceProcessorInfo.getFileExtensions());
		}

		public String getFileExtensions() {
			return _fileExtensions;
		}

		public String getName() {
			return _name;
		}

		private String _getFileExtensions() {
			String[] includes = null;

			try {
				Class<?> clazz = Class.forName(
					"com.liferay.source.formatter." + _name);

				Field field = clazz.getDeclaredField("_INCLUDES");

				field.setAccessible(true);

				includes = (String[])field.get(null);
			}
			catch (Exception exception) {
				return StringPool.BLANK;
			}

			StringBundler sb = new StringBundler();

			for (int i = 0; i < includes.length; i++) {
				String includeExtension = includes[i];

				int x = includeExtension.lastIndexOf(CharPool.PERIOD);
				int y = includeExtension.lastIndexOf(CharPool.SLASH);

				if (x < y) {
					sb.append(includeExtension.substring(y + 1));
				}
				else {
					sb.append(includeExtension.substring(x));
				}

				if (i == (includes.length - 2)) {
					sb.append(" or ");
				}
				else if (i < (includes.length - 1)) {
					sb.append(", ");
				}
			}

			return sb.toString();
		}

		private final String _fileExtensions;
		private String _name;

	}

}