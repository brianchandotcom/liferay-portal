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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.DockerfileSourceUtil;

import java.io.IOException;

/**
 * @author Peter Shin
 */
public class DockerfileEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String instruction = StringPool.BLANK;
			String line = StringPool.BLANK;
			String previousInstruction = StringPool.BLANK;
			String previousLine = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (Validator.isNull(line)) {
					continue;
				}

				instruction = DockerfileSourceUtil.getInstruction(
					line, previousLine);

				if (DockerfileSourceUtil.isNewInstruction(
						instruction, previousInstruction, previousLine)) {

					sb.append("\n");
				}

				sb.append(line);
				sb.append("\n");

				if (Validator.isNotNull(instruction)) {
					previousInstruction = instruction;
				}

				previousLine = line;
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

}