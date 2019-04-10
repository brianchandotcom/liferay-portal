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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiAnnotationsOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		Matcher matcher = _annotationsPattern.matcher(content);

		if (matcher.find()) {
			List<String> annotations = ListUtil.fromString(matcher.group());

			Collections.sort(
				annotations,
				new Comparator<String>() {

					@Override
					public int compare(String annotation1, String annotation2) {
						String annotation1Name = StringUtil.extractFirst(
							annotation1, CharPool.SPACE);
						String annotation2Name = StringUtil.extractFirst(
							annotation2, CharPool.SPACE);

						return annotation1Name.compareTo(annotation2Name);
					}

				});
			content = StringUtil.replaceFirst(
				content, matcher.group(0),
				ListUtil.toString(annotations, StringPool.BLANK, "\n") + "\n");
		}

		return content;
	}

	private static final Pattern _annotationsPattern = Pattern.compile(
		"(@.+?=.+?\n){2,}(?=definition \\{)");

}