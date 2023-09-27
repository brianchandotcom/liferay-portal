/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;

import org.dom4j.Comment;

/**
 * @author Kenji Heigel
 */
public class MultilinePoshiComment extends PoshiComment {

	@Override
	public PoshiComment clone(Comment comment) {
		String commentText = comment.getText();

		if (commentText.contains("\n")) {
			return new MultilinePoshiComment(comment);
		}

		return null;
	}

	@Override
	public PoshiComment clone(String poshiScript)
		throws PoshiScriptParserException {

		if (isPoshiScriptComment(poshiScript)) {
			return new MultilinePoshiComment(poshiScript);
		}

		return null;
	}

	@Override
	public boolean isPoshiScriptComment(String poshiScript) {
		poshiScript = poshiScript.trim();

		if (poshiScript.endsWith("*/") && poshiScript.startsWith("/*")) {
			return true;
		}

		return false;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		setText(poshiScript.substring(2, poshiScript.length() - 2));
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\t/*");
		sb.append(getText());
		sb.append("*/");

		return sb.toString();
	}

	protected MultilinePoshiComment() {
	}

	protected MultilinePoshiComment(Comment comment) {
		super(comment);
	}

	protected MultilinePoshiComment(String poshiScript)
		throws PoshiScriptParserException {

		super(poshiScript);
	}

}