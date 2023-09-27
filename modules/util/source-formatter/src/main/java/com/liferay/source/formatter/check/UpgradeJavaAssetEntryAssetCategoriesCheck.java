/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaAssetEntryAssetCategoriesCheck
	extends BaseUpgradeCheck {

	@Override
	protected String afterFormat(
		String fileName, String absolutePath, String content,
		String newContent) {

		newContent = addNewImports(newContent);

		return StringUtil.replaceLast(
			newContent, CharPool.CLOSE_CURLY_BRACE,
			"\n\t@Reference\n\tprivate " +
				"AssetEntryAssetCategoryRelLocalService\n\t\t" +
					"_assetEntryAssetCategoryRelLocalService;\n\n}");
	}

	@Override
	protected String format(
		String fileName, String absolutePath, String content) {

		String newContent = _replaceAddOrDeleteAssetCategories(content);

		return _replaceAddOrDeleteAssetCategory(newContent);
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.asset.entry.rel.service." +
				"AssetEntryAssetCategoryRelLocalService",
			"org.osgi.service.component.annotations.Reference"
		};
	}

	private String _replaceAddOrDeleteAssetCategories(String content) {
		String newContent = content;

		Matcher matcher = _addOrDeleteAssetEntryAssetCategoriesPattern.matcher(
			content);

		while (matcher.find()) {
			String methodCall = matcher.group();

			if (!hasClassOrVariableName(
					"AssetCategoryLocalService", newContent, newContent,
					methodCall)) {

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
					"AssetCategoryLocalService", newContent, newContent,
					methodCall)) {

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