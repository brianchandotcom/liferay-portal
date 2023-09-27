/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.DockerfileSourceUtil;

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