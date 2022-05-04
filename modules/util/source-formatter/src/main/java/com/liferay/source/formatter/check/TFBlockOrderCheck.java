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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class TFBlockOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		BlockComparator blockComparator = new BlockComparator();

		Matcher matcher = _blockPattern.matcher(content);

		String previousBlock = StringPool.BLANK;
		int previousBlockStartPosition = 0;

		while (matcher.find()) {
			String block = matcher.group();
			int blockStartPosition = matcher.start();

			if (Validator.isNull(previousBlock)) {
				previousBlock = block;
				previousBlockStartPosition = blockStartPosition;

				continue;
			}

			if (blockComparator.compare(previousBlock, block) > 0) {
				content = StringUtil.replaceFirst(
					content, block, previousBlock, matcher.start());

				return StringUtil.replaceFirst(
					content, previousBlock, block, previousBlockStartPosition);
			}

			previousBlock = block;
			previousBlockStartPosition = blockStartPosition;
		}

		return content;
	}

	private static final Pattern _blockPattern = Pattern.compile(
		"(?<=\\A|\n)\\w.+\\{[\\s\\S]*?\n\\}");

	private class BlockComparator extends NaturalOrderStringComparator {

		@Override
		public int compare(String block1, String block2) {
			String blockName1 = _getBlockName(block1);
			String blockName2 = _getBlockName(block2);

			return super.compare(blockName1, blockName2);
		}

		private String _getBlockName(String block) {
			int x = block.indexOf(CharPool.OPEN_CURLY_BRACE);

			if (x == -1) {
				return StringPool.BLANK;
			}

			return StringUtil.removeChars(
				block.substring(0, x - 1), CharPool.QUOTE);
		}

	}

}