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

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Peter Yoo
 */
public class LegacyDataReadMeSummaryElement extends DefaultElement {

	public LegacyDataReadMeSummaryElement() {
		super("details");

		_summaryElement = Dom4JUtil.getNewElement("summary", this);
		_unorderedListElement = Dom4JUtil.getNewElement("ul", this);
	}

	public void addLineItem(Element element) {
		_lineItemElements.add(
			Dom4JUtil.getNewElement("li", _unorderedListElement, element));

		updateSummaryElement();
	}

	public void setSummaryContent(Element summaryContentElement) {
		_summaryContentElement = summaryContentElement;

		updateSummaryElement();
	}

	protected void updateSummaryElement() {
		if (_summaryElement != null) {
			remove(_summaryElement);
		}

		_summaryElement = Dom4JUtil.getNewElement(
			"summary", null,
			Dom4JUtil.getNewElement(
				"b", null,
				JenkinsResultsParserUtil.combine(
					"(", Integer.toString(_lineItemElements.size()), ") ")),
			_summaryContentElement.clone());

		add(_summaryElement);
	}

	private final List<Element> _lineItemElements = new ArrayList<>();
	private Element _summaryContentElement;
	private Element _summaryElement;
	private final Element _unorderedListElement;

}