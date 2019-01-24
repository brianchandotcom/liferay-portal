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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.DockerfileSourceUtil;

import java.io.IOException;

/**
 * @author Peter Shin
 */
public class DockerfileInstructionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = StringPool.BLANK;
			String previousLine = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				sb.append(_getLine(line, previousLine));
				sb.append("\n");

				previousLine = line;
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _getLine(String line, String previousLine) {
		if (Validator.isNull(line)) {
			return StringPool.BLANK;
		}

		String instruction = DockerfileSourceUtil.getInstruction(
			line, previousLine);

		if (DockerfileSourceUtil.endsWithBackSlash(previousLine) &&
			!Character.isWhitespace(line.charAt(0))) {

			return StringPool.TAB + line;
		}

		if (Validator.isNull(instruction)) {
			return line;
		}

		String lowerCaseInstruction = StringUtil.toLowerCase(instruction);
		String lowerCaseLine = StringUtil.toLowerCase(line);

		int index = lowerCaseLine.indexOf(lowerCaseInstruction);

		String arguments = line.substring(index + instruction.length());

		return instruction + StringPool.SPACE + StringUtil.trim(arguments);
	}

}