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

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;

import org.dom4j.CDATA;
import org.dom4j.tree.DefaultCDATA;

/**
 * @author Kenji Heigel
 */
public class PoshiCDATA
	extends DefaultCDATA implements PoshiNode<CDATA, PoshiCDATA> {

	public PoshiCDATA(CDATA cdata) {
		super(cdata.getParent(), cdata.getText());
	}

	public PoshiCDATA(String text) {
		super(text);
	}

	@Override
	public PoshiCDATA clone(CDATA cdata) {
		return null;
	}

	@Override
	public PoshiCDATA clone(String poshiScript)
		throws PoshiScriptParserException {

		return null;
	}

	@Override
	public String getPoshiScript() {
		return _poshiScript;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {
	}

	@Override
	public void setPoshiScript(String poshiScript) {
		_poshiScript = poshiScript;
	}

	@Override
	public String toPoshiScript() {
		return null;
	}

	@Override
	public void validatePoshiScript() throws PoshiScriptParserException {
	}

	private String _poshiScript;

}