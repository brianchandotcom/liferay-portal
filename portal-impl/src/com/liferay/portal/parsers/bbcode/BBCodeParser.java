/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.parsers.bbcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Iliyan Peychev
 */
public class BBCodeParser {
	public static final int TOKEN_DATA = 0x04;
	public static final int TOKEN_TAG_END = 0x02;
	public static final int TOKEN_TAG_START = 0x01;

	public BBCodeParser() {
		_dataPointer = 0;

		_elementsBlock = new ArrayList(14);

		_elementsBlock.add("*");
		_elementsBlock.add("center");
		_elementsBlock.add("code");
		_elementsBlock.add("justify");
		_elementsBlock.add("left");
		_elementsBlock.add("li");
		_elementsBlock.add("list");
		_elementsBlock.add("q");
		_elementsBlock.add("quote");
		_elementsBlock.add("right");
		_elementsBlock.add("table");
		_elementsBlock.add("td");
		_elementsBlock.add("th");
		_elementsBlock.add("tr");

		_elementsCloseSelf = new ArrayList(1);

		_elementsCloseSelf.add("*");

		_elementsInline = new ArrayList(9);

		_elementsInline.add("b");
		_elementsInline.add("color");
		_elementsInline.add("font");
		_elementsInline.add("i");
		_elementsInline.add("img");
		_elementsInline.add("s");
		_elementsInline.add("size");
		_elementsInline.add("u");
		_elementsInline.add("url");
	}

	public List<BBCodeItem> parse(String text) {
		_lexer = new BBCodeLexer(text);

		_tagsStack = new Stack();

		_itemsList = new ArrayList();

		BBCodeToken token = null;

		while ((token = _lexer.getNextToken()) != null) {
			_handleData(token, text);

			if (token.getStartTag() != null) {
				_handleTagStart(token);

				if (token.getStartTag().equals(STR_TAG_CODE)) {

					while ( (token = _lexer.getNextToken()) != null &&
							!(STR_TAG_CODE.equals(token.getEndTag())));

					_handleData(token, text);

					if (token != null) {
						_handleTagEnd(token);
					}
					else {
						break;
					}
				}
			}
			else {
				_handleTagEnd(token);
			}
		}

		_handleData(null, text);

		_handleTagEnd(null);

		List<BBCodeItem> result = new ArrayList(_itemsList);

		_reset();

		return result;
	}

	private void _handleData(BBCodeToken token, String data) {
		int length = data.length();

		int lastIndex = length;

		if (token != null) {
			length = token.getStart();

			lastIndex = _lexer.getLastIndex();
		}

		if (length > _dataPointer) {
			BBCodeItem bbCodeItem =
					new BBCodeItem(
							TOKEN_DATA, null,
							data.substring(_dataPointer, length));

			_itemsList.add(bbCodeItem);
		}

		_dataPointer = lastIndex;
	}

	private void _handleTagEnd(BBCodeToken token) {
		int position = 0;

		if (token != null) {
			String tagName = token.getEndTag();

			for (position = _tagsStack.size() - 1; position >= 0; position--) {

				if (_tagsStack.elementAt(position).equals(tagName)) {
					break;
				}
			}
		}

		if (position >= 0) {

			for (int i = _tagsStack.size() - 1; i >= position; i--) {
				_itemsList.add(
					new BBCodeItem(
						TOKEN_TAG_END, null, _tagsStack.elementAt(i)));
			}

			_tagsStack.setSize(position);
		}
	}

	private void _handleTagStart(BBCodeToken token) {
		String tagName = token.getStartTag();

		if (_elementsBlock.contains(tagName)) {
			String lastTag = null;

			while (_tagsStack.size() > 0 &&
					(lastTag = _tagsStack.lastElement()) != null &&
						_elementsInline.contains(lastTag)) {

				_handleTagEnd(new BBCodeToken(lastTag));
			}
		}

		if (_elementsCloseSelf.contains(tagName) &&
				_tagsStack.lastElement().equals(tagName)) {

			_handleTagEnd(new BBCodeToken(tagName));
		}

		_tagsStack.push(tagName);

		BBCodeItem item =
				new BBCodeItem(
						TOKEN_TAG_START, token.getAttribute(), tagName);

		_itemsList.add(item);
	}

	private void _reset() {
		_tagsStack.empty();
		_itemsList.clear();

		_dataPointer = 0;
	}

	private final String STR_TAG_CODE = "code";

	private int _dataPointer;
	private List<String> _elementsBlock = null;
	private List<String> _elementsCloseSelf = null;
	private List<String> _elementsInline = null;
	private List<BBCodeItem> _itemsList = null;
	private BBCodeLexer _lexer = null;
	private Stack<String> _tagsStack = null;

}