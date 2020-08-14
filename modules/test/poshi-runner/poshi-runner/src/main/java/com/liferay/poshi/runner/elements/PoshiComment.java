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

package com.liferay.poshi.runner.elements;

import com.liferay.poshi.runner.script.PoshiScriptParserException;
import com.liferay.poshi.runner.util.PropsValues;

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

		if (PropsValues.TEST_POSHI_SCRIPT_VALIDATION) {
			validatePoshiScript();
		}
	}

	private String _poshiScript;

}