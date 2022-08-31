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
import com.liferay.poshi.core.util.StringUtil;

import java.net.URL;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Kenji Heigel
 */
public class PoshiScriptParserException extends PoshiElementException {

	public static final String TRANSLATION_LOSS_MESSAGE =
		"Poshi Script syntax is not preserved in translation";

	public static void clear() {
		_poshiScriptParserExceptions.clear();
	}

	public static void throwExceptions() throws Exception {
		if (!_poshiScriptParserExceptions.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			sb.append("\n\n");
			sb.append(_poshiScriptParserExceptions.size());
			sb.append(" errors in Poshi script syntax\n\n");

			for (Exception exception : _poshiScriptParserExceptions) {
				sb.append(exception.getMessage());
				sb.append("\n\n");
			}

			System.out.println(sb.toString());

			throw new Exception();
		}
	}

	public static void throwExceptions(String filePath) throws Exception {
		if (_poshiScriptParserExceptions.isEmpty()) {
			return;
		}

		for (PoshiScriptParserException poshiScriptParserException :
				_poshiScriptParserExceptions) {

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

	private static final Set<PoshiScriptParserException>
		_poshiScriptParserExceptions = Collections.synchronizedSet(
			new HashSet<>());

}