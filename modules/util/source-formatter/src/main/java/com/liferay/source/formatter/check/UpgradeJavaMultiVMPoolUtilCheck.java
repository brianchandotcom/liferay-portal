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
	protected String beforeFormatIteration(
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
	protected String formatIteration(
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