/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;

import org.dom4j.Comment;
import org.dom4j.tree.DefaultComment;

/**
 * @author Michael Hashimoto
 */
public abstract class PoshiComment
	extends DefaultComment implements PoshiNode<Comment, PoshiComment> {

	public PoshiComment() {
		super("");
	}

	@Override
	public String getPoshiScript() {
		return _poshiScript;
	}

	public abstract boolean isPoshiScriptComment(String poshiScript);

	@Override
	public void setPoshiScript(String poshiScript) {
		_poshiScript = poshiScript;
	}

	protected PoshiComment(Comment comment) {
		super(comment.getText());
	}

	protected PoshiComment(String poshiScript)
		throws PoshiScriptParserException {

		this();

		setPoshiScript(poshiScript);

		parsePoshiScript(poshiScript.trim());

		if (poshiProperties.testPoshiScriptValidation) {
			validatePoshiScript();
		}
	}

	private String _poshiScript;

}