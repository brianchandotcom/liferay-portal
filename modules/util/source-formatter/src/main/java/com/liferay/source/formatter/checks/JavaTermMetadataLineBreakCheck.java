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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

/**
 * @author Peter Shin
 */
public class JavaTermMetadataLineBreakCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(javaTerm.getContent()));

		String line = null;

		StringBundler newSB = new StringBundler();
		StringBundler oldSB = new StringBundler();

		boolean foundJavaTermDefinition = false;
		String indent = SourceUtil.getIndent(javaTerm.getContent());

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.matches(indent + _JAVA_TERM_DEFINITION_REGEX)) {
				foundJavaTermDefinition = true;

				break;
			}

			oldSB.append(line);
			oldSB.append("\n");

			if (Validator.isNotNull(line)) {
				newSB.append(line);
				newSB.append("\n");
			}
		}

		if ((oldSB.length() < 1) || !foundJavaTermDefinition) {
			return javaTerm.getContent();
		}

		return StringUtil.replaceFirst(
			javaTerm.getContent(), oldSB.toString(), newSB.toString());
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD, JAVA_VARIABLE};
	}

	private static final String _JAVA_TERM_DEFINITION_REGEX =
		"((private|protected|public)( .*|$)|static \\{)";

}