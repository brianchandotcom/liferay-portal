/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.Dom4JUtil;

import java.net.URL;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class DefinitionPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		return clone(element, null);
	}

	public PoshiElement clone(Element element, URL url) {
		if (isElementType(getElementName(), element)) {
			return new DefinitionPoshiElement(element, url);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		return clone(poshiScript, null);
	}

	public PoshiElement clone(String poshiScript, URL url)
		throws PoshiScriptParserException {

		if (isElementType(poshiScript)) {
			return new DefinitionPoshiElement(poshiScript, url);
		}

		return null;
	}

	@Override
	public String getFileExtension() {
		URL filePathURL = getFilePathURL();

		String filePath = filePathURL.getPath();

		int index = filePath.lastIndexOf(".");

		return filePath.substring(index + 1);
	}

	@Override
	public URL getFilePathURL() {
		return _filePathURL;
	}

	@Override
	public int getPoshiScriptLineNumber() {
		return getPoshiScriptLineNumber(false);
	}

	@Override
	public boolean isValidPoshiXML() throws PoshiScriptParserException {
		if (_validPoshiXML == null) {
			_validPoshiXML = false;

			URL filePathURL = getFilePathURL();

			PoshiNode<?, ?> poshiNode = PoshiNodeFactory.newPoshiNodeFromFile(
				filePathURL);

			String poshiScript = poshiNode.toPoshiScript();

			PoshiNode<?, ?> generatedPoshiNode = PoshiNodeFactory.newPoshiNode(
				poshiScript, filePathURL);

			if (Dom4JUtil.elementsEqual(poshiNode, generatedPoshiNode)) {
				_validPoshiXML = true;
			}
		}

		return _validPoshiXML;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		Matcher poshiScriptAnnotationMatcher =
			poshiScriptAnnotationPattern.matcher(getBlockName(poshiScript));

		while (poshiScriptAnnotationMatcher.find()) {
			String annotation = poshiScriptAnnotationMatcher.group();

			String name = getNameFromAssignment(annotation);
			String value = getDoubleQuotedContent(annotation);

			addAttribute(name, value);
		}

		String blockContent = getBlockContent(poshiScript);

		for (String poshiScriptSnippet : getPoshiScriptSnippets(blockContent)) {
			add(PoshiNodeFactory.newPoshiNode(this, poshiScriptSnippet));
		}
	}

	@Override
	public void setFilePathURL(URL filePathURL) {
		_filePathURL = filePathURL;
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributeList())) {

			sb.append("\n@");

			sb.append(poshiElementAttribute.toPoshiScript());
		}

		sb.append(createPoshiScriptBlock(getPoshiNodes()));

		String string = sb.toString();

		return string.trim();
	}

	protected DefinitionPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected DefinitionPoshiElement(Element element, URL url) {
		super(_ELEMENT_NAME, element, url);
	}

	protected DefinitionPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected DefinitionPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	protected DefinitionPoshiElement(String poshiScript, URL url)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, null, poshiScript, url);
	}

	@Override
	protected String getBlockName() {
		return "definition";
	}

	protected String getElementName() {
		return _ELEMENT_NAME;
	}

	@Override
	protected String getPad() {
		return "";
	}

	@Override
	protected String getPoshiScriptKeyword() {
		String fileExtension = getFileExtension();

		if (fileExtension.equals("testcase")) {
			return "test";
		}

		return fileExtension;
	}

	protected boolean isElementType(String poshiScript) {
		return isValidPoshiScriptBlock(_blockNamePattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "definition";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	private static final Pattern _blockNamePattern = Pattern.compile(
		"^" + BLOCK_NAME_ANNOTATION_REGEX + _POSHI_SCRIPT_KEYWORD,
		Pattern.DOTALL);

	private URL _filePathURL;
	private Boolean _validPoshiXML;

}