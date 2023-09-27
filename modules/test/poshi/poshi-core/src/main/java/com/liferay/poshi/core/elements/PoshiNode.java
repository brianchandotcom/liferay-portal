/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.PoshiProperties;
import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.StringUtil;

import java.net.URL;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import org.dom4j.Node;

/**
 * @author Kenji Heigel
 * @author Michael Hashimoto
 */
public interface PoshiNode<A extends Node, B extends PoshiNode<A, B>>
	extends Node {

	public B clone(A node);

	public B clone(String poshiScript) throws PoshiScriptParserException;

	public default String getFileExtension() {
		PoshiNode<?, ?> parentPoshiNode = (PoshiNode<?, ?>)getParent();

		return parentPoshiNode.getFileExtension();
	}

	public default URL getFilePathURL() {
		PoshiNode<?, ?> parentPoshiNode = (PoshiNode<?, ?>)getParent();

		return parentPoshiNode.getFilePathURL();
	}

	public String getPoshiScript();

	public default int getPoshiScriptLineNumber() {
		PoshiElement parentPoshiElement = (PoshiElement)getParent();

		if ((parentPoshiElement == null) ||
			((this instanceof PoshiElementAttribute) &&
			 (parentPoshiElement instanceof DefinitionPoshiElement))) {

			return 1;
		}

		List<PoshiNode<?, ?>> poshiNodes = parentPoshiElement.getPoshiNodes();

		PoshiNode<?, ?> previousPoshiNode = null;

		for (Iterator<PoshiNode<?, ?>> iterator = poshiNodes.iterator();
			 iterator.hasNext();) {

			PoshiNode<?, ?> poshiNode = iterator.next();

			if (poshiNode instanceof DescriptionPoshiElement) {
				continue;
			}

			if (!poshiNode.equals(this)) {
				previousPoshiNode = poshiNode;

				if (iterator.hasNext()) {
					continue;
				}
			}

			if (previousPoshiNode == null) {
				return parentPoshiElement.getPoshiScriptLineNumber() +
					StringUtil.countStartingNewLines(getPoshiScript());
			}

			int poshiScriptLineNumber =
				previousPoshiNode.getPoshiScriptLineNumber();

			String previousPoshiScript = previousPoshiNode.getPoshiScript();

			poshiScriptLineNumber =
				poshiScriptLineNumber -
					StringUtil.countStartingNewLines(previousPoshiScript) +
						StringUtil.count(previousPoshiScript, "\n");

			String poshiScript = poshiNode.getPoshiScript();

			if (!iterator.hasNext()) {
				poshiScript = getPoshiScript();
			}

			poshiScriptLineNumber =
				poshiScriptLineNumber +
					StringUtil.countStartingNewLines(poshiScript);

			if (previousPoshiNode instanceof PoshiElement) {
				PoshiElement previousPoshiElement =
					(PoshiElement)previousPoshiNode;

				if (previousPoshiElement.getBlockName() != null) {
					Matcher poshiScriptBlockMatcher =
						PoshiElement.poshiScriptBlockPattern.matcher(
							previousPoshiScript);

					if (poshiScriptBlockMatcher.find()) {
						int newLineCount = StringUtil.count(
							parentPoshiElement.getBlockName(
								previousPoshiScript),
							"\n");

						poshiScriptLineNumber =
							poshiScriptLineNumber - newLineCount;
					}
				}
			}

			return poshiScriptLineNumber;
		}

		if (previousPoshiNode == null) {
			return parentPoshiElement.getPoshiScriptLineNumber() +
				StringUtil.countStartingNewLines(getPoshiScript());
		}

		return previousPoshiNode.getPoshiScriptLineNumber() +
			StringUtil.count(previousPoshiNode.getPoshiScript(), "\n");
	}

	public default boolean isValidPoshiXML() throws PoshiScriptParserException {
		PoshiNode<?, ?> parentPoshiNode = (PoshiNode<?, ?>)getParent();

		return parentPoshiNode.isValidPoshiXML();
	}

	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException;

	public void setPoshiScript(String poshiScript);

	public String toPoshiScript();

	public default void validatePoshiScript()
		throws PoshiScriptParserException {

		String originalPoshiScript = getPoshiScript();
		String generatedPoshiScript = toPoshiScript();

		originalPoshiScript = originalPoshiScript.replaceAll("\\s+", "");

		generatedPoshiScript = generatedPoshiScript.replaceAll("\\s+", "");

		if (!originalPoshiScript.equals(generatedPoshiScript)) {
			PoshiScriptParserException poshiScriptParserException =
				new PoshiScriptParserException(
					PoshiScriptParserException.TRANSLATION_LOSS_MESSAGE, this);

			throw poshiScriptParserException;
		}
	}

	public PoshiProperties poshiProperties =
		PoshiProperties.getPoshiProperties();

}