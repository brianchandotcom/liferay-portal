/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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