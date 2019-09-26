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

package com.liferay.portal.kernel.xml.simple;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.LinkedList;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class Element {

	public Element(Element parentElement, String name) {
		this(parentElement, name, null);
	}

	public Element(Element parentElement, String name, String text) {
		if (parentElement._elementClosed) {
			throw new IllegalArgumentException("Parent element is closed");
		}

		_parentElement = parentElement;
		_name = name;
		_text = _formatText(text);
		_elementStack = parentElement._elementStack;
		_sb = parentElement._sb;

		_appendChildElement();
	}

	public Element(String name) {
		this(name, null, true);
	}

	public Element(String name, boolean addHeader) {
		this(name, null, addHeader);
	}

	public Element(String name, String text) {
		this(name, text, true);
	}

	public Element(String name, String text, boolean addHeader) {
		_name = name;

		_text = _formatText(text);
		_elementStack = new LinkedList<>();

		_sb = new StringBundler();

		if (addHeader) {
			_sb.append(_XML_HEADER);
		}

		_openElement(this);
	}

	public void addAttribute(String name, String value) {
		if (_openTagClosed) {
			throw new IllegalStateException("Element is closed");
		}

		_sb.append(StringPool.SPACE);
		_sb.append(name);
		_sb.append(_EQUAL_QUOTE);
		_sb.append(value);
		_sb.append(StringPool.QUOTE);
	}

	public Element addElement(String name) {
		return addElement(name, null);
	}

	public Element addElement(String name, boolean text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, double text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, float text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, int text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, long text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, Object text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, short text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, String text) {
		return new Element(this, name, text);
	}

	public String getName() {
		return _name;
	}

	public Element getParentElement() {
		return _parentElement;
	}

	public String getText() {
		return _text;
	}

	public boolean isRootElement() {
		if (_parentElement == null) {
			return true;
		}

		return false;
	}

	public String toXMLString() {
		if (_parentElement != null) {
			throw new IllegalStateException(
				"XML string can only generated from a root element");
		}

		if (_xmlString == null) {
			_flushPendingOpenElements();

			_xmlString = _sb.toString();
		}

		return _xmlString;
	}

	private void _appendChildElement() {
		Element topElement = _elementStack.getLast();

		while ((topElement != _parentElement) && (topElement != null)) {

			// Close previous sibling elements

			_closeElement(topElement);

			_elementStack.removeLast();

			topElement = _elementStack.getLast();
		}

		if (topElement == _parentElement) {

			// Append current element to its parent

			_closeOpenTag(topElement);

			_openElement(this);
		}
		else {
			throw new IllegalArgumentException(
				"The parent element does not exist");
		}
	}

	private void _closeElement(Element element) {
		_closeOpenTag(element);

		_sb.append(_CLOSE_PRE);
		_sb.append(element._name);
		_sb.append(_CLOSE_POST);

		element._elementClosed = true;
	}

	private void _closeOpenTag(Element element) {
		if (element._openTagClosed == false) {
			_sb.append(_OPEN_POST);

			if (element._text != null) {
				_sb.append(element._text);
			}

			element._openTagClosed = true;
		}
	}

	private void _flushPendingOpenElements() {
		while (!_elementStack.isEmpty()) {
			_closeElement(_elementStack.removeLast());
		}
	}

	private String _formatText(String text) {
		return HtmlUtil.escape(text);
	}

	private void _openElement(Element element) {
		_sb.append(_OPEN_PRE);
		_sb.append(element._name);

		_elementStack.addLast(element);
	}

	private static final String _CLOSE_POST = ">";

	private static final String _CLOSE_PRE = "</";

	private static final String _EQUAL_QUOTE = "=\"";

	private static final String _OPEN_POST = ">";

	private static final String _OPEN_PRE = "<";

	private static final String _XML_HEADER =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

	private boolean _elementClosed;
	private final LinkedList<Element> _elementStack;
	private final String _name;
	private boolean _openTagClosed;
	private Element _parentElement;
	private final StringBundler _sb;
	private final String _text;
	private String _xmlString;

}