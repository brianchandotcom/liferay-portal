/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaFacetedSearcherCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			String newJavaMethodContent = javaMethodContent;

			Matcher matcher = _facetedSearcherGetInstancePattern.matcher(
				javaMethodContent);

			while (matcher.find()) {
				String methodCall = matcher.group(0);

				newJavaMethodContent = StringUtil.replace(
					newJavaMethodContent, methodCall,
					"FacetedSearcher facetedSearcher = " +
						"FacetedSearcherManagerUtil.createFacetedSearcher();");

				newJavaMethodContent = StringUtil.replace(
					newJavaMethodContent, matcher.group(1) + ".search(",
					"facetedSearcher.search(");
			}

			content = StringUtil.replace(
				content, javaMethodContent, newJavaMethodContent);
		}

		return content;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.kernel.search.facet.faceted.searcher." +
				"FacetedSearcherManagerUtil"
		};
	}

	private static final Pattern _facetedSearcherGetInstancePattern =
		Pattern.compile(
			"Indexer[<\\?>]*\\s+(\\w+)\\s*=\\s*\\s*FacetedSearcher\\s*\\.\\s*" +
				"getInstance\\(\\);");

}