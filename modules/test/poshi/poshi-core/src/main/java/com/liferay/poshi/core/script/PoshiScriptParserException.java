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

package com.liferay.poshi.core.script;

import com.liferay.poshi.core.elements.PoshiElement;
import com.liferay.poshi.core.elements.PoshiElementException;
import com.liferay.poshi.core.elements.PoshiNode;
import com.liferay.poshi.core.util.NaturalOrderStringComparator;
import com.liferay.poshi.core.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class PoshiScriptParserException extends PoshiElementException {

	public static final String TRANSLATION_LOSS_MESSAGE =
		"Poshi Script syntax is not preserved in translation";

	public static void clear() {
		_poshiScriptParserExceptions.clear();
	}

	public static List<PoshiScriptParserException> getExceptions() {
		Collections.sort(
			_poshiScriptParserExceptions,
			(poshiScriptParserException1, poshiScriptParserException2) -> {
				String filePath1 = poshiScriptParserException1.getFilePath();
				String filePath2 = poshiScriptParserException2.getFilePath();

				NaturalOrderStringComparator naturalOrderStringComparator =
					new NaturalOrderStringComparator();

				if (!filePath1.equals(filePath2)) {
					return naturalOrderStringComparator.compare(
						filePath1, filePath2);
				}

				return naturalOrderStringComparator.compare(
					String.valueOf(
						poshiScriptParserException1.getErrorLineNumber()),
					String.valueOf(
						poshiScriptParserException2.getErrorLineNumber()));
			});

		return _poshiScriptParserExceptions;
	}

	public static void throwExceptions() throws Exception {
		if (!_poshiScriptParserExceptions.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			sb.append("\n\n");
			sb.append(_poshiScriptParserExceptions.size());
			sb.append(" error");

			if (_poshiScriptParserExceptions.size() > 1) {
				sb.append("s");
			}

			sb.append(" in Poshi Script syntax\n\n");

			int i = 1;

			for (Exception exception : _poshiScriptParserExceptions) {
				sb.append(i);
				sb.append(". ");
				sb.append(exception.getMessage());
				sb.append("\n\n");

				i++;
			}

			System.out.println(sb.toString());

			throw new Exception();
		}
	}

	public static void throwExceptions(String filePath) throws Exception {
		for (PoshiScriptParserException poshiScriptParserException :
				getExceptions()) {

			if (!filePath.equals(poshiScriptParserException.getFilePath())) {
				continue;
			}

			StringBuilder sb = new StringBuilder();

			sb.append("\n\nPoshi parsing errors in " + filePath + "\n\n");
			sb.append(poshiScriptParserException.getMessage());
			sb.append("\n\n");

			System.out.println(sb.toString());

			throw new Exception();
		}
	}

	public PoshiScriptParserException(
		String msg, int errorLineNumber, String errorSnippet, URL filePathURL) {

		super(msg, errorLineNumber, errorSnippet, filePathURL);

		_poshiScriptParserExceptions.add(this);
	}

	public PoshiScriptParserException(String msg, PoshiNode<?, ?> poshiNode) {
		super(
			msg, poshiNode.getPoshiScriptLineNumber(), getFilePath(poshiNode),
			poshiNode);

		_poshiScriptParserExceptions.add(this);
	}

	public PoshiScriptParserException(
		String msg, String poshiScript, PoshiNode<?, ?> parentPoshiNode) {

		super(
			msg, _getErrorLineNumber(poshiScript, parentPoshiNode),
			getFilePath(parentPoshiNode), parentPoshiNode);

		_poshiScriptParserExceptions.add(this);
	}

	private static int _getErrorLineNumber(
		String poshiScript, PoshiNode<?, ?> parentPoshiNode) {

		String parentPoshiScript = parentPoshiNode.getPoshiScript();

		parentPoshiScript = parentPoshiScript.replaceFirst("^[\\n\\r]*", "");

		int startingLineNumber = parentPoshiNode.getPoshiScriptLineNumber();

		if (parentPoshiNode instanceof PoshiElement) {
			PoshiElement parentPoshiElement = (PoshiElement)parentPoshiNode;

			startingLineNumber = parentPoshiElement.getPoshiScriptLineNumber(
				true);
		}

		int index = parentPoshiScript.indexOf(poshiScript.trim());

		return startingLineNumber +
			StringUtil.count(parentPoshiScript, "\n", index);
	}

	private static final List<PoshiScriptParserException>
		_poshiScriptParserExceptions = Collections.synchronizedList(
			new ArrayList<>());

}