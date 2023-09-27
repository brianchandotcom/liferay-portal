/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.StringUtil;

import org.dom4j.Comment;

/**
 * @author Peter Yoo
 */
public class InlinePoshiComment extends PoshiComment {

	@Override
	public PoshiComment clone(Comment comment) {
		String commentText = comment.getText();

		if (!commentText.contains("\n")) {
			return new InlinePoshiComment(comment);
		}

		return null;
	}

	@Override
	public PoshiComment clone(String poshiScript)
		throws PoshiScriptParserException {

		if (isPoshiScriptComment(poshiScript)) {
			return new InlinePoshiComment(poshiScript);
		}

		return null;
	}

	@Override
	public boolean isPoshiScriptComment(String poshiScript) {
		poshiScript = poshiScript.trim();

		if (poshiScript.startsWith("//")) {
			return true;
		}

		return false;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		if (isPoshiScriptComment(poshiScript)) {
			setText(poshiScript.substring(2));
		}
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\n\t//");

		String comment = getText();

		sb.append(StringUtil.trimTrailing(comment));

		return sb.toString();
	}

	protected InlinePoshiComment() {
	}

	protected InlinePoshiComment(Comment comment) {
		super(comment);
	}

	protected InlinePoshiComment(String poshiScript)
		throws PoshiScriptParserException {

		super(poshiScript);
	}

}