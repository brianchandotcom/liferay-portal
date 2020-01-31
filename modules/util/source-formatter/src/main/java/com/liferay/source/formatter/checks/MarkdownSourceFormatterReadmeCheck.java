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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

		return _getReadmeContent(absolutePath, _getCheckInfoMap());
	}

	private void _createChecksTableMarkdown(
			String header, File file, Collection<CheckInfo> checkInfos,
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

		for (CheckInfo checkInfo : checkInfos) {
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
				String fileExtensionsString = _getFileExtensionsString(
					checkInfo.getSourceProcessorInfos());

				if (Validator.isNotNull(fileExtensionsString)) {
					sb.append(fileExtensionsString);
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

	private Set<String> _getCategories(Map<String, CheckInfo> checkInfoMap) {
		Set<String> categories = new TreeSet<>();

		for (CheckInfo checkInfo : checkInfoMap.values()) {
			categories.add(checkInfo.getCategory());
		}

		return categories;
	}

	private List<CheckInfo> _getCategoryCheckInfos(
		String category, Map<String, CheckInfo> checkInfoMap) {

		List<CheckInfo> checkInfos = new ArrayList<>();

		for (CheckInfo checkInfo : checkInfoMap.values()) {
			if (category.equals(checkInfo.getCategory())) {
				checkInfos.add(checkInfo);
			}
		}

		return checkInfos;
	}

	private Map<String, CheckInfo> _getCheckInfoMap()
		throws DocumentException, IOException {

		Map<String, CheckInfo> checkInfoMap = new TreeMap<>();

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

				CheckInfo checkInfo = checkInfoMap.get(checkName);

				if (checkInfo != null) {
					checkInfo.addSourceProcessorInfo(sourceProcessorInfo);

					checkInfoMap.put(checkName, checkInfo);

					continue;
				}

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

				checkInfoMap.put(
					checkName,
					new CheckInfo(
						checkName, category, description, sourceProcessorInfo));
			}
		}

		return checkInfoMap;
	}

	private String _getFileExtensionsString(
		List<SourceProcessorInfo> sourceProcessorInfos) {

		List<String> fileExtensions = new ArrayList<>();

		for (SourceProcessorInfo sourceProcessorInfo : sourceProcessorInfos) {
			fileExtensions.addAll(sourceProcessorInfo.getFileExtensions());
		}

		Collections.sort(fileExtensions);

		StringBundler sb = new StringBundler();

		for (int i = 0; i < fileExtensions.size(); i++) {
			sb.append(fileExtensions.get(i));

			if (i == (fileExtensions.size() - 2)) {
				sb.append(" or ");
			}
			else if (i < (fileExtensions.size() - 1)) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

	private String _getReadmeContent(
			String absolutePath, Map<String, CheckInfo> checkInfoMap)
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
			checkInfoMap.values(), documentationChecksDir, true, true);

		sb.append("## Categories:\n");

		for (String category : _getCategories(checkInfoMap)) {
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
				new File(documentationDir, markdownFileName),
				_getCategoryCheckInfos(category, checkInfoMap),
				documentationChecksDir, false, true);
		}

		sb.append("\n");

		sb.append("## File Extensions:\n");

		for (SourceProcessorInfo sourceProcessorInfo :
				_getSourceProcessorInfos(checkInfoMap)) {

			String sourceProcessorName = sourceProcessorInfo.getName();

			if (sourceProcessorName.equals("all")) {
				continue;
			}

			sb.append("- [");

			String fileExtensionsString = _getFileExtensionsString(
				ListUtil.fromArray(sourceProcessorInfo));

			sb.append(fileExtensionsString);

			sb.append("](");
			sb.append(_DOCUMENTATION_DIR_LOCATION);

			String markdownFileName = SourceFormatterUtil.getMarkdownFileName(
				sourceProcessorName + "Checks");

			sb.append(markdownFileName);

			sb.append(")\n");

			_createChecksTableMarkdown(
				"Checks for " + fileExtensionsString,
				new File(documentationDir, markdownFileName),
				_getSourceProcessorInfoCheckInfos(
					sourceProcessorInfo, checkInfoMap),
				documentationChecksDir, true, false);
		}

		return StringUtil.trim(sb.toString());
	}

	private List<CheckInfo> _getSourceProcessorInfoCheckInfos(
		SourceProcessorInfo sourceProcessorInfo,
		Map<String, CheckInfo> checkInfoMap) {

		List<CheckInfo> checkInfos = new ArrayList<>();

		for (CheckInfo checkInfo : checkInfoMap.values()) {
			List<SourceProcessorInfo> sourceProcessorInfos =
				checkInfo.getSourceProcessorInfos();

			if (sourceProcessorInfos.contains(sourceProcessorInfo)) {
				checkInfos.add(checkInfo);
			}
		}

		return checkInfos;
	}

	private Set<SourceProcessorInfo> _getSourceProcessorInfos(
		Map<String, CheckInfo> checkInfoMap) {

		Set<SourceProcessorInfo> sourceProcessorInfos = new TreeSet<>();

		for (CheckInfo checkInfo : checkInfoMap.values()) {
			for (SourceProcessorInfo sourceProcessorInfo :
					checkInfo.getSourceProcessorInfos()) {

				if (!ListUtil.isEmpty(
						sourceProcessorInfo.getFileExtensions())) {

					sourceProcessorInfos.add(sourceProcessorInfo);
				}
			}
		}

		return sourceProcessorInfos;
	}

	private static final String _DOCUMENTATION_CHECKS_DIR_NAME = "checks/";

	private static final String _DOCUMENTATION_DIR_LOCATION =
		"src/main/resources/documentation/";

	private class CheckInfo implements Comparable<CheckInfo> {

		public CheckInfo(
			String name, String category, String description,
			SourceProcessorInfo sourceProcessorInfo) {

			_name = name;
			_category = category;
			_description = description;

			_sourceProcessorInfos.add(sourceProcessorInfo);
		}

		public void addSourceProcessorInfo(
			SourceProcessorInfo sourceProcessorInfo) {

			_sourceProcessorInfos.add(sourceProcessorInfo);
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

		public String getName() {
			return _name;
		}

		public List<SourceProcessorInfo> getSourceProcessorInfos() {
			return _sourceProcessorInfos;
		}

		private final String _category;
		private final String _description;
		private String _name;
		private final List<SourceProcessorInfo> _sourceProcessorInfos =
			new ArrayList<>();

	}

	private class SourceProcessorInfo
		implements Comparable<SourceProcessorInfo> {

		public SourceProcessorInfo(String name) {
			_name = name;

			_fileExtensions = _getFileExtensions();
		}

		@Override
		public int compareTo(SourceProcessorInfo sourceProcessorInfo) {
			String fileExtensionsString = _getFileExtensionsString(
				ListUtil.fromArray(this));

			return fileExtensionsString.compareTo(
				_getFileExtensionsString(
					ListUtil.fromArray(sourceProcessorInfo)));
		}

		public List<String> getFileExtensions() {
			return _fileExtensions;
		}

		public String getName() {
			return _name;
		}

		private List<String> _getFileExtensions() {
			List<String> fileExtensions = new ArrayList<>();

			try {
				Class<?> clazz = Class.forName(
					"com.liferay.source.formatter." + _name);

				Field field = clazz.getDeclaredField("_INCLUDES");

				field.setAccessible(true);

				String[] includes = (String[])field.get(null);

				for (String include : includes) {
					int x = include.lastIndexOf(CharPool.PERIOD);
					int y = include.lastIndexOf(CharPool.SLASH);

					if (x < y) {
						fileExtensions.add(include.substring(y + 1));
					}
					else {
						fileExtensions.add(include.substring(x));
					}
				}
			}
			catch (Exception exception) {
			}

			return fileExtensions;
		}

		private final List<String> _fileExtensions;
		private String _name;

	}

}