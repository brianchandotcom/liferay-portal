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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.CompatClassImportsCheck;
import com.liferay.source.formatter.checks.CopyrightCheck;
import com.liferay.source.formatter.checks.EmptyArrayCheck;
import com.liferay.source.formatter.checks.EmptyCollectionCheck;
import com.liferay.source.formatter.checks.GetterUtilCheck;
import com.liferay.source.formatter.checks.JSPButtonTagCheck;
import com.liferay.source.formatter.checks.JSPDefineObjectsCheck;
import com.liferay.source.formatter.checks.JSPEmptyLinesCheck;
import com.liferay.source.formatter.checks.JSPExceptionOrderCheck;
import com.liferay.source.formatter.checks.JSPIfStatementCheck;
import com.liferay.source.formatter.checks.JSPImportsCheck;
import com.liferay.source.formatter.checks.JSPIncludeCheck;
import com.liferay.source.formatter.checks.JSPIndentationCheck;
import com.liferay.source.formatter.checks.JSPLanguageKeysCheck;
import com.liferay.source.formatter.checks.JSPLanguageUtilCheck;
import com.liferay.source.formatter.checks.JSPLogFileNameCheck;
import com.liferay.source.formatter.checks.JSPModuleIllegalImportsCheck;
import com.liferay.source.formatter.checks.JSPRedirectBackURLCheck;
import com.liferay.source.formatter.checks.JSPSendRedirectCheck;
import com.liferay.source.formatter.checks.JSPSessionKeysCheck;
import com.liferay.source.formatter.checks.JSPStringBundlerCheck;
import com.liferay.source.formatter.checks.JSPStringMethodsCheck;
import com.liferay.source.formatter.checks.JSPStylingCheck;
import com.liferay.source.formatter.checks.JSPSubnameCheck;
import com.liferay.source.formatter.checks.JSPTagAttributesCheck;
import com.liferay.source.formatter.checks.JSPTaglibVariableCheck;
import com.liferay.source.formatter.checks.JSPUnusedImportCheck;
import com.liferay.source.formatter.checks.JSPUnusedTaglibCheck;
import com.liferay.source.formatter.checks.JSPUnusedVariableCheck;
import com.liferay.source.formatter.checks.JSPWhitespaceCheck;
import com.liferay.source.formatter.checks.JSPXSSVulnerabilitiesCheck;
import com.liferay.source.formatter.checks.MethodCallsOrderCheck;
import com.liferay.source.formatter.checks.PrimitiveWrapperInstantiationCheck;
import com.liferay.source.formatter.checks.PrincipalExceptionCheck;
import com.liferay.source.formatter.checks.ResourceBundleCheck;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.StringUtilCheck;
import com.liferay.source.formatter.checks.UnparameterizedClassCheck;
import com.liferay.source.formatter.checks.ValidatorEqualsCheck;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {"**/null.jsp", "**/tools/**"};

		List<String> fileNames = getFileNames(excludes, getIncludes());

		if (fileNames.isEmpty()) {
			return fileNames;
		}

		excludes = new String[] {"**/null.jsp", "**/tools/**"};

		List<String> allJSPFileNames = getFileNames(
			excludes, getIncludes(), true);

		_contentsMap = _getContentsMap(allJSPFileNames);

		if (sourceFormatterArgs.isFormatCurrentBranch() ||
			sourceFormatterArgs.isFormatLatestAuthor() ||
			sourceFormatterArgs.isFormatLocalChanges()) {

			return JSPSourceUtil.addIncludedAndReferencedFileNames(
				fileNames, new HashSet<String>(), _contentsMap);
		}

		return fileNames;
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<SourceCheck> getModuleSourceChecks() {
		List<SourceCheck> moduleSourceChecks = new ArrayList<>();

		moduleSourceChecks.add(new JSPModuleIllegalImportsCheck());

		return moduleSourceChecks;
	}

	@Override
	protected List<SourceCheck> getSourceChecks() throws Exception {
		List<SourceCheck> sourceChecks = new ArrayList<>();

		if (_contentsMap == null) {
			_contentsMap = _getContentsMap(sourceFormatterArgs.getFileNames());
		}

		sourceChecks.add(new JSPWhitespaceCheck());

		sourceChecks.add(new CopyrightCheck(getCopyright()));
		sourceChecks.add(new EmptyArrayCheck());
		sourceChecks.add(new EmptyCollectionCheck());
		sourceChecks.add(new GetterUtilCheck());
		sourceChecks.add(new JSPButtonTagCheck());
		sourceChecks.add(new JSPDefineObjectsCheck());
		sourceChecks.add(new JSPEmptyLinesCheck());
		sourceChecks.add(new JSPExceptionOrderCheck());
		sourceChecks.add(new JSPIfStatementCheck());
		sourceChecks.add(new JSPImportsCheck());
		sourceChecks.add(new JSPIncludeCheck());
		sourceChecks.add(new JSPIndentationCheck());
		sourceChecks.add(new JSPLanguageUtilCheck());
		sourceChecks.add(new JSPLogFileNameCheck());
		sourceChecks.add(new JSPRedirectBackURLCheck());
		sourceChecks.add(new JSPSendRedirectCheck());
		sourceChecks.add(new JSPSessionKeysCheck());
		sourceChecks.add(new JSPStringBundlerCheck());
		sourceChecks.add(new JSPStylingCheck());
		sourceChecks.add(new JSPSubnameCheck());
		sourceChecks.add(new JSPTagAttributesCheck());
		sourceChecks.add(new JSPTaglibVariableCheck());
		sourceChecks.add(new JSPUnusedImportCheck(_contentsMap));
		sourceChecks.add(new JSPXSSVulnerabilitiesCheck());
		sourceChecks.add(new MethodCallsOrderCheck());
		sourceChecks.add(new PrimitiveWrapperInstantiationCheck());
		sourceChecks.add(new PrincipalExceptionCheck());
		sourceChecks.add(new StringUtilCheck());
		sourceChecks.add(new UnparameterizedClassCheck());
		sourceChecks.add(new ValidatorEqualsCheck());

		if (portalSource || subrepository) {
			sourceChecks.add(new JSPStringMethodsCheck());
			sourceChecks.add(new JSPUnusedTaglibCheck(_contentsMap));
			sourceChecks.add(new JSPUnusedVariableCheck(_contentsMap));
			sourceChecks.add(new ResourceBundleCheck());
		}
		else {
			if (GetterUtil.getBoolean(
					getProperty("use.portal.compat.import"))) {

				sourceChecks.add(new CompatClassImportsCheck());
			}
		}

		if (portalSource) {
			sourceChecks.add(new JSPLanguageKeysCheck());
		}

		return sourceChecks;
	}

	private Map<String, String> _getContentsMap(List<String> fileNames)
		throws Exception {

		Map<String, String> contentsMap = new HashMap<>();

		if (ListUtil.isEmpty(fileNames)) {
			return contentsMap;
		}

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			File file = new File(fileName);

			String content = FileUtil.read(file);

			if (content == null) {
				continue;
			}

			Matcher matcher = _includeFilePattern.matcher(content);

			while (matcher.find()) {
				content = StringUtil.replaceFirst(
					content, matcher.group(),
					"@ include file=\"" + matcher.group(1) + "\"",
					matcher.start());
			}

			contentsMap.put(fileName, content);
		}

		return contentsMap;
	}

	private static final String[] _INCLUDES =
		new String[] {"**/*.jsp", "**/*.jspf", "**/*.tpl", "**/*.vm"};

	private Map<String, String> _contentsMap;
	private final Pattern _includeFilePattern = Pattern.compile(
		"\\s*@\\s*include\\s*file=['\"](.*)['\"]");

}