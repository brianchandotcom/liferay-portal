/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.Validator;

import org.dom4j.Attribute;
import org.dom4j.tree.DefaultAttribute;

/**
 * @author Peter Yoo
 */
public class PoshiElementAttribute
	extends DefaultAttribute
	implements PoshiNode<Attribute, PoshiElementAttribute> {

	public PoshiElementAttribute(Attribute attribute) {
		super(
			attribute.getParent(), attribute.getName(), attribute.getValue(),
			attribute.getNamespace());
	}

	public PoshiElementAttribute(
		String name, String value, String poshiScript) {

		super(name, value);

		setPoshiScript(poshiScript);
	}

	@Override
	public PoshiElementAttribute clone(Attribute attribute) {
		return null;
	}

	@Override
	public PoshiElementAttribute clone(String poshiScript)
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
		StringBuilder sb = new StringBuilder();

		sb.append(getName());

		String value = getValue();

		PoshiElement parentPoshiElement = (PoshiElement)getParent();

		if ((parentPoshiElement instanceof CommandPoshiElement ||
			 parentPoshiElement instanceof DefinitionPoshiElement) &&
			value.equals("")) {

			return sb.toString();
		}

		sb.append(" = ");

		value = value.replace("\"", "\\\"");

		if (parentPoshiElement.isQuotedContent(value)) {
			value = "\"" + value + "\"";
		}

		sb.append(value);

		return sb.toString();
	}

	@Override
	public void validatePoshiScript() throws PoshiScriptParserException {
		if (Validator.isNull(getPoshiScript())) {
			return;
		}

		String originalPoshiScript = getPoshiScript();

		originalPoshiScript = originalPoshiScript.replaceAll("\\s+", "");

		String generatedPoshiScript = toPoshiScript();

		generatedPoshiScript = generatedPoshiScript.replaceAll("\\s+", "");

		if (!originalPoshiScript.equals(generatedPoshiScript)) {
			originalPoshiScript = originalPoshiScript.replaceFirst("\"", "");

			originalPoshiScript = originalPoshiScript.substring(
				0, originalPoshiScript.length() - 1);

			if (!originalPoshiScript.equals(generatedPoshiScript)) {
				throw new PoshiScriptParserException(
					PoshiScriptParserException.TRANSLATION_LOSS_MESSAGE, this);
			}
		}
	}

	private String _poshiScript;

}