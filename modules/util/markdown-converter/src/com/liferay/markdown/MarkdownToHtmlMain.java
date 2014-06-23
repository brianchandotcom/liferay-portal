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

package com.liferay.markdown;

import com.liferay.pegdown.LiferayPegDownConverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author James Hinkey
 */
public class MarkdownToHtmlMain {

	public static void main(String[] args) throws IOException {
		if ((args == null) || (args.length < 2)) {
			throw new IllegalArgumentException(
				"Requires 2 arguments: markdownFile htmlFile");
		}

		String markdownFile = args[0];
		String htmlFile = args[1];

		BufferedReader bufferedReader = new BufferedReader(
			new FileReader(markdownFile));

		String line = bufferedReader.readLine();

		StringBuilder sb = new StringBuilder();

		while (line != null) {
			sb.append(line);
			sb.append("\n");

			line = bufferedReader.readLine();
		}

		bufferedReader.close();

		MarkdownConverter markdownConverter = new LiferayPegDownConverter();

		String html = markdownConverter.convert(sb.toString());

		BufferedWriter bufferedWriter = new BufferedWriter(
			new FileWriter(htmlFile));

		bufferedWriter.append(html);
		bufferedWriter.append("\n");

		bufferedWriter.flush();

		bufferedWriter.close();
	}

}