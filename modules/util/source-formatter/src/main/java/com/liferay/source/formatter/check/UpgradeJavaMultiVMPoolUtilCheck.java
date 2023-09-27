/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaMultiVMPoolUtilCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String afterFormat(
		String fileName, String absolutePath, String content,
		String newContent) {

		newContent = addNewImports(newContent);
		newContent = StringUtil.replace(
			newContent, "MultiVMPoolUtil.getPortalCache(",
			_WARNING_CASE_TYPE + " _multiVMPool.getPortalCache(");
		newContent = StringUtil.replaceLast(
			newContent, CharPool.CLOSE_CURLY_BRACE,
			"\n\t@Reference\n\tprivate MultiVMPool _multiVMPool;\n\n}");

		return newContent;
	}

	@Override
	protected String beforeFormatMatcherIteration(
		String fileName, String absolutePath, String content) {

		if (content.contains(_WARNING_CASE_TYPE)) {
			addMessage(
				fileName,
				"Could not resolve types for MultiVMPool.getPortalCache(). " +
					"Replace 'TO_BE_REPLACED' with the correct type");
		}

		return StringUtil.replace(
			content, _MULTI_VM_POOL_UTIL_IMPORT,
			"import com.liferay.portal.kernel.cache.MultiVMPool;");
	}

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String newDeclaration = StringUtil.replace(
			matcher.group(0), "MultiVMPoolUtil.getPortalCache(",
			"(PortalCache" + matcher.group(1) +
				") _multiVMPool.getPortalCache(");

		return StringUtil.replace(newContent, matcher.group(0), newDeclaration);
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"org.osgi.service.component.annotations.Reference"
		};
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile(
			"PortalCache\\s*(<.+, +?.+>)\\s*\\w+" +
				"\\s*=\\s*MultiVMPoolUtil\\.getPortalCache\\(");
	}

	private static final String _MULTI_VM_POOL_UTIL_IMPORT =
		"import com.liferay.portal.kernel.cache.MultiVMPoolUtil;";

	private static final String _WARNING_CASE_TYPE =
		"(PortalCache<TO_BE_REPLACED, TO_BE_REPLACED>)";

}