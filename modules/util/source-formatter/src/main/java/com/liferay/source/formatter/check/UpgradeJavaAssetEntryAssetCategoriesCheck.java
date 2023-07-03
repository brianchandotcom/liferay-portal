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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaAssetEntryAssetCategoriesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		String newContent = _replaceAddOrDeleteAssetCategories(content);

		newContent = _replaceAddOrDeleteAssetCategory(newContent);

		if (!content.equals(newContent)) {
			newContent = JavaSourceUtil.addImports(
				newContent,
				"com.liferay.asset.entry.rel.service." +
					"AssetEntryAssetCategoryRelLocalService",
				"org.osgi.service.component.annotations.Reference");

			newContent = StringUtil.replaceLast(
				newContent, CharPool.CLOSE_CURLY_BRACE,
				"\n\t@Reference\n\tprivate " +
					"AssetEntryAssetCategoryRelLocalService\n\t\t" +
						"_assetEntryAssetCategoryRelLocalService;\n\n}");
		}

		return newContent;
	}

	private String _replaceAddOrDeleteAssetCategories(String content) {
		String newContent = content;

		Matcher matcher = _addOrDeleteAssetEntryAssetCategoriesPattern.matcher(
			content);

		while (matcher.find()) {
			String methodCall = matcher.group();

			if (!hasClassOrVariableName(
					"AssetCategoryLocalService", newContent, methodCall)) {

				continue;
			}

			String line = getLine(
				content, getLineNumber(content, matcher.start()));

			String indent = SourceUtil.getIndent(line);

			String newLine = null;
			String newMethodCall = null;

			String secondParameter = matcher.group(2);

			String variableTypeName = getVariableTypeName(
				newContent, newContent, secondParameter, true);

			if (variableTypeName.equals("List<AssetCategory>")) {
				newLine = StringBundler.concat(
					indent, "for (AssetCategory assetCategory : ",
					secondParameter, ") {\n\t", line);

				newMethodCall = StringUtil.replace(
					methodCall, secondParameter,
					"assetCategory.getCategoryId()");
			}
			else {
				newLine = StringBundler.concat(
					indent, "for (long assetCategoryId : ", secondParameter,
					") {\n\t", line);

				newMethodCall = StringUtil.replace(
					methodCall, secondParameter, "assetCategoryId");
			}

			newContent = StringUtil.replaceFirst(newContent, line, newLine);

			newMethodCall = StringBundler.concat(
				newMethodCall, StringPool.SEMICOLON, StringPool.NEW_LINE,
				indent, StringPool.CLOSE_CURLY_BRACE);

			newMethodCall = StringUtil.replace(newMethodCall, "(\n", "(\n\t");
			newMethodCall = StringUtil.replace(newMethodCall, ",\n", ",\n\t");

			String methodStart = matcher.group(1);

			if (methodStart.contains("addAssetEntryAssetCategories")) {
				newMethodCall = StringUtil.replace(
					newMethodCall, methodStart, _NEW_ADD_METHOD);
			}
			else {
				newMethodCall = StringUtil.replace(
					newMethodCall, methodStart, _NEW_DELETE_METHOD);
			}

			newContent = StringUtil.replaceFirst(
				newContent, methodCall + StringPool.SEMICOLON, newMethodCall);
		}

		return newContent;
	}

	private String _replaceAddOrDeleteAssetCategory(String content) {
		String newContent = content;

		Matcher matcher = _addOrDeleteAssetEntryAssetCategoryPattern.matcher(
			content);

		while (matcher.find()) {
			String methodCall = matcher.group();

			if (!hasClassOrVariableName(
					"AssetCategoryLocalService", newContent, methodCall)) {

				continue;
			}

			String newMethodCall = null;

			String methodStart = matcher.group(1);

			if (methodStart.contains("addAssetEntryAssetCategory")) {
				newMethodCall = StringUtil.replace(
					methodCall, methodStart, _NEW_ADD_METHOD);
			}
			else {
				newMethodCall = StringUtil.replace(
					methodCall, methodStart, _NEW_DELETE_METHOD);
			}

			String secondParameter = matcher.group(2);

			String variableTypeName = getVariableTypeName(
				newContent, newContent, secondParameter);

			if ((variableTypeName != null) &&
				variableTypeName.equals("AssetCategory")) {

				newMethodCall = StringUtil.replace(
					newMethodCall, secondParameter,
					secondParameter + ".getCategoryId()");
			}

			newContent = StringUtil.replaceFirst(
				newContent, methodCall, newMethodCall);
		}

		return newContent;
	}

	private static final String _NEW_ADD_METHOD =
		"_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel";

	private static final String _NEW_DELETE_METHOD =
		"_assetEntryAssetCategoryRelLocalService." +
			"deleteAssetEntryAssetCategoryRel";

	private static final Pattern _addOrDeleteAssetEntryAssetCategoriesPattern =
		Pattern.compile(
			"(\\w*\\.(?:addAssetEntryAssetCategories|" +
				"deleteAssetEntryAssetCategories))" +
					"\\(\\s*\\w+,\\s*(\\w+)\\s*\\)");
	private static final Pattern _addOrDeleteAssetEntryAssetCategoryPattern =
		Pattern.compile(
			"(\\w*\\.(?:addAssetEntryAssetCategory|" +
				"deleteAssetEntryAssetCategory))\\(\\s*\\w+,\\s*(\\w+)\\s*\\)");

}