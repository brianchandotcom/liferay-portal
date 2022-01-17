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

package com.liferay.journal.internal.transformer;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.layout.dynamic.data.mapping.form.field.type.constants.LayoutDDMFormFieldTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
public class JournalTransformerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIncludeBackwardsCompatibilityTemplateNodes() {
		JournalTransformer journalTransformer = new JournalTransformer();

		List<TemplateNode> includeBackwardsCompatibilityTemplateNodes =
			journalTransformer.includeBackwardsCompatibilityTemplateNodes(
				_getInitTemplateNodes(), 0);

		Assert.assertEquals(
			_getExpectedTemplateNodes(),
			includeBackwardsCompatibilityTemplateNodes);
	}

	@Test
	public void testIncludeBackwardsCompatibilityTemplateNodesFirstChildWithSiblings() {
		JournalTransformer journalTransformer = new JournalTransformer();

		List<TemplateNode> includeBackwardsCompatibilityTemplateNodes =
			journalTransformer.includeBackwardsCompatibilityTemplateNodes(
				_getInitTemplateNodesFirstChildWithSiblings(), 0);

		Assert.assertEquals(
			includeBackwardsCompatibilityTemplateNodes.toString(), 1,
			includeBackwardsCompatibilityTemplateNodes.size());

		Assert.assertEquals(
			_getExpectedTemplateNodesFirstChildWithSiblings(),
			includeBackwardsCompatibilityTemplateNodes);
	}

	@Test
	public void testIncludeBackwardsCompatibilityTemplateNodesWithSiblings() {
		JournalTransformer journalTransformer = new JournalTransformer();

		List<TemplateNode> includeBackwardsCompatibilityTemplateNodes =
			journalTransformer.includeBackwardsCompatibilityTemplateNodes(
				_getInitTemplateNodesWithSiblings(), 0);

		TemplateNode separatorTemplateNode =
			includeBackwardsCompatibilityTemplateNodes.get(0);

		List<TemplateNode> separatorChildTemplateNode =
			separatorTemplateNode.getChildren();

		TemplateNode bookmarksTitleTemplateNode =
			separatorChildTemplateNode.get(0);

		List<TemplateNode> bookmarksTitleSiblingsTemplateNodes =
			bookmarksTitleTemplateNode.getSiblings();

		Assert.assertEquals(
			bookmarksTitleSiblingsTemplateNodes.toString(), 3,
			bookmarksTitleSiblingsTemplateNodes.size());
		Assert.assertEquals(
			_getExpectedSiblingsTemplateNodes(),
			bookmarksTitleSiblingsTemplateNodes);
	}

	private TemplateNode _createTemplateNode(String name, String type) {
		return _createTemplateNode(name, type, StringPool.BLANK);
	}

	private TemplateNode _createTemplateNode(
		String name, String type, String value) {

		return new TemplateNode(
			null, name, value, type, Collections.emptyMap());
	}

	private List<TemplateNode> _getExpectedSiblingsTemplateNodes() {
		TemplateNode bookmarksTitleTemplateNode1 = _createTemplateNode(
			"BookmarksTitle1", DDMFormFieldTypeConstants.TEXT);

		TemplateNode bookmarksTemplateNode1 = _createTemplateNode(
			"BookmarksLink1", LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT);

		bookmarksTitleTemplateNode1.appendChild(bookmarksTemplateNode1);

		bookmarksTitleTemplateNode1.appendSibling(bookmarksTitleTemplateNode1);

		TemplateNode bookmarksTitleTemplateNode2 = _createTemplateNode(
			"BookmarksTitle2", DDMFormFieldTypeConstants.TEXT);

		TemplateNode bookmarksTemplateNode2 = _createTemplateNode(
			"BookmarksLink2", LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT);

		bookmarksTitleTemplateNode2.appendChild(bookmarksTemplateNode2);

		bookmarksTitleTemplateNode1.appendSibling(bookmarksTitleTemplateNode2);

		TemplateNode bookmarksTitleTemplateNode3 = _createTemplateNode(
			"BookmarksTitle3", DDMFormFieldTypeConstants.TEXT);

		TemplateNode bookmarksTemplateNode3 = _createTemplateNode(
			"BookmarksLink3", LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT);

		bookmarksTitleTemplateNode3.appendChild(bookmarksTemplateNode3);

		bookmarksTitleTemplateNode1.appendSibling(bookmarksTitleTemplateNode3);

		return bookmarksTitleTemplateNode1.getSiblings();
	}

	private List<TemplateNode> _getExpectedTemplateNodes() {
		List<TemplateNode> expectedTemplateNodes = new ArrayList<>();

		TemplateNode titleTemplateNode = _createTemplateNode(
			"Title", DDMFormFieldTypeConstants.TEXT);

		TemplateNode imageTemplateNode = _createTemplateNode(
			"Image", DDMFormFieldTypeConstants.IMAGE);

		TemplateNode geolocationTemplateNode = _createTemplateNode(
			"Geolocation", DDMFormFieldTypeConstants.GEOLOCATION);

		imageTemplateNode.appendChild(geolocationTemplateNode);

		titleTemplateNode.appendChild(imageTemplateNode);

		TemplateNode dateTemplateNode = _createTemplateNode(
			"Date", DDMFormFieldTypeConstants.DATE);

		TemplateNode statusTemplateNode = _createTemplateNode(
			"Status", DDMFormFieldTypeConstants.OPTIONS);

		dateTemplateNode.appendChild(statusTemplateNode);

		titleTemplateNode.appendChild(dateTemplateNode);

		expectedTemplateNodes.add(titleTemplateNode);

		TemplateNode separatorTemplateNode = _createTemplateNode(
			"Separator", DDMFormFieldTypeConstants.SEPARATOR);

		TemplateNode bookmarksTitleTemplateNode = _createTemplateNode(
			"BookmarksTitle", DDMFormFieldTypeConstants.TEXT);

		TemplateNode bookmarksTemplateNode = _createTemplateNode(
			"Bookmarks", LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT);

		bookmarksTitleTemplateNode.appendChild(bookmarksTemplateNode);

		separatorTemplateNode.appendChild(bookmarksTitleTemplateNode);

		expectedTemplateNodes.add(separatorTemplateNode);

		return expectedTemplateNodes;
	}

	private List<TemplateNode>
		_getExpectedTemplateNodesFirstChildWithSiblings() {

		TemplateNode repeatableTextTemplateNode1 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField1");

		repeatableTextTemplateNode1.appendSibling(repeatableTextTemplateNode1);

		TemplateNode repeatableTextTemplateNode2 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField2");

		repeatableTextTemplateNode1.appendSibling(repeatableTextTemplateNode2);

		TemplateNode repeatableTextTemplateNode3 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField3");

		repeatableTextTemplateNode1.appendSibling(repeatableTextTemplateNode3);

		TemplateNode repeatableTextTemplateNode4 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField4");

		repeatableTextTemplateNode1.appendSibling(repeatableTextTemplateNode4);

		TemplateNode repeatableTextTemplateNode5 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField5");

		repeatableTextTemplateNode1.appendSibling(repeatableTextTemplateNode5);

		TemplateNode textTemplateNode1 = _createTemplateNode(
			_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT, "TextField1");

		repeatableTextTemplateNode1.appendChild(textTemplateNode1);

		TemplateNode textTemplateNode2 = _createTemplateNode(
			_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT, "TextField2");

		textTemplateNode1.appendSibling(textTemplateNode2);

		return ListUtil.fromArray(repeatableTextTemplateNode1);
	}

	private List<TemplateNode> _getInitTemplateNodes() {
		List<TemplateNode> templateNodes = new ArrayList<>();

		TemplateNode titleFieldSetTemplateNode = _createTemplateNode(
			"TitleFieldSet", DDMFormFieldTypeConstants.FIELDSET);

		TemplateNode titleTemplateNode = _createTemplateNode(
			"Title", DDMFormFieldTypeConstants.TEXT);

		titleFieldSetTemplateNode.appendChild(titleTemplateNode);

		TemplateNode imageFieldSetTemplateNode = _createTemplateNode(
			"ImageFieldSet", DDMFormFieldTypeConstants.FIELDSET);

		TemplateNode imageTemplateNode = _createTemplateNode(
			"Image", DDMFormFieldTypeConstants.IMAGE);

		imageFieldSetTemplateNode.appendChild(imageTemplateNode);

		TemplateNode geolocationTemplateNode = _createTemplateNode(
			"Geolocation", DDMFormFieldTypeConstants.GEOLOCATION);

		imageFieldSetTemplateNode.appendChild(geolocationTemplateNode);

		titleFieldSetTemplateNode.appendChild(imageFieldSetTemplateNode);

		TemplateNode dateFieldSetTemplateNode = _createTemplateNode(
			"DateFieldSet", DDMFormFieldTypeConstants.FIELDSET);

		TemplateNode dateTemplateNode = _createTemplateNode(
			"Date", DDMFormFieldTypeConstants.DATE);

		dateFieldSetTemplateNode.appendChild(dateTemplateNode);

		TemplateNode statusTemplateNode = _createTemplateNode(
			"Status", DDMFormFieldTypeConstants.OPTIONS);

		dateFieldSetTemplateNode.appendChild(statusTemplateNode);

		titleFieldSetTemplateNode.appendChild(dateFieldSetTemplateNode);

		templateNodes.add(titleFieldSetTemplateNode);

		TemplateNode separatorFieldSetTemplateNode = _createTemplateNode(
			"SeparatorFieldSet", DDMFormFieldTypeConstants.FIELDSET);

		TemplateNode separatorTemplateNode = _createTemplateNode(
			"Separator", DDMFormFieldTypeConstants.SEPARATOR);

		separatorFieldSetTemplateNode.appendChild(separatorTemplateNode);

		TemplateNode bookmarksTitleFieldSetTemplateNode = _createTemplateNode(
			"BookmarksTitleFieldSet", DDMFormFieldTypeConstants.FIELDSET);

		TemplateNode bookmarksTitleTemplateNode = _createTemplateNode(
			"BookmarksTitle", DDMFormFieldTypeConstants.TEXT);

		bookmarksTitleFieldSetTemplateNode.appendChild(
			bookmarksTitleTemplateNode);

		TemplateNode bookmarksTemplateNode = _createTemplateNode(
			"Bookmarks", LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT);

		bookmarksTitleFieldSetTemplateNode.appendChild(bookmarksTemplateNode);

		separatorFieldSetTemplateNode.appendChild(
			bookmarksTitleFieldSetTemplateNode);

		templateNodes.add(separatorFieldSetTemplateNode);

		return templateNodes;
	}

	private List<TemplateNode> _getInitTemplateNodesFirstChildWithSiblings() {
		TemplateNode textFieldSetTemplateNode1 = _createTemplateNode(
			_TEXT_FIELD_SET_NAME, DDMFormFieldTypeConstants.FIELDSET);

		textFieldSetTemplateNode1.appendSibling(textFieldSetTemplateNode1);

		TemplateNode repeatableTextTemplateNode1 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField1");

		repeatableTextTemplateNode1.appendSibling(repeatableTextTemplateNode1);

		TemplateNode repeatableTextTemplateNode2 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField2");

		repeatableTextTemplateNode1.appendSibling(repeatableTextTemplateNode2);

		TemplateNode repeatableTextTemplateNode3 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField3");

		repeatableTextTemplateNode1.appendSibling(repeatableTextTemplateNode3);

		textFieldSetTemplateNode1.appendChild(repeatableTextTemplateNode1);

		TemplateNode textTemplateNode1 = _createTemplateNode(
			_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT, "TextField1");

		textFieldSetTemplateNode1.appendChild(textTemplateNode1);

		TemplateNode textFieldSetTemplateNode2 = _createTemplateNode(
			_TEXT_FIELD_SET_NAME, DDMFormFieldTypeConstants.FIELDSET);

		textFieldSetTemplateNode1.appendSibling(textFieldSetTemplateNode2);

		TemplateNode repeatableTextTemplateNode4 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField4");

		repeatableTextTemplateNode4.appendSibling(repeatableTextTemplateNode4);

		textFieldSetTemplateNode2.appendChild(repeatableTextTemplateNode4);

		TemplateNode repeatableTextTemplateNode5 = _createTemplateNode(
			_REPEATABLE_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT,
			"RepeatableTextField5");

		repeatableTextTemplateNode4.appendSibling(repeatableTextTemplateNode5);

		TemplateNode textTemplateNode2 = _createTemplateNode(
			_TEXT_FIELD_NAME, DDMFormFieldTypeConstants.TEXT, "TextField2");

		textFieldSetTemplateNode2.appendChild(textTemplateNode2);

		return ListUtil.fromArray(textFieldSetTemplateNode1);
	}

	private List<TemplateNode> _getInitTemplateNodesWithSiblings() {
		List<TemplateNode> templateNodes = new ArrayList<>();

		TemplateNode separatorFieldSetTemplateNode = _createTemplateNode(
			"SeparatorFieldSet", DDMFormFieldTypeConstants.FIELDSET);

		TemplateNode separatorTemplateNode = _createTemplateNode(
			"Separator", DDMFormFieldTypeConstants.SEPARATOR);

		separatorFieldSetTemplateNode.appendChild(separatorTemplateNode);

		TemplateNode bookmarksTitleFieldSetTemplateNode1 = _createTemplateNode(
			"BookmarksTitleFieldSet1", DDMFormFieldTypeConstants.FIELDSET);

		TemplateNode bookmarksTitleTemplateNode1 = _createTemplateNode(
			"BookmarksTitle1", DDMFormFieldTypeConstants.TEXT);

		bookmarksTitleFieldSetTemplateNode1.appendChild(
			bookmarksTitleTemplateNode1);

		TemplateNode bookmarksTemplateNode1 = _createTemplateNode(
			"BookmarksLink1", LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT);

		bookmarksTitleFieldSetTemplateNode1.appendChild(bookmarksTemplateNode1);

		bookmarksTitleFieldSetTemplateNode1.appendSibling(
			bookmarksTitleFieldSetTemplateNode1);

		TemplateNode bookmarksTitleFieldSetTemplateNode2 = _createTemplateNode(
			"BookmarksTitleFieldSet", DDMFormFieldTypeConstants.FIELDSET);

		TemplateNode bookmarksTitleTemplateNode2 = _createTemplateNode(
			"BookmarksTitle2", DDMFormFieldTypeConstants.TEXT);

		bookmarksTitleFieldSetTemplateNode2.appendChild(
			bookmarksTitleTemplateNode2);

		TemplateNode bookmarksTemplateNode2 = _createTemplateNode(
			"BookmarksLink2", LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT);

		bookmarksTitleFieldSetTemplateNode2.appendChild(bookmarksTemplateNode2);

		bookmarksTitleFieldSetTemplateNode1.appendSibling(
			bookmarksTitleFieldSetTemplateNode2);

		TemplateNode bookmarksTitleFieldSetTemplateNode3 = _createTemplateNode(
			"BookmarksTitleFieldSet3", DDMFormFieldTypeConstants.FIELDSET);

		TemplateNode bookmarksTitleTemplateNode3 = _createTemplateNode(
			"BookmarksTitle3", DDMFormFieldTypeConstants.TEXT);

		bookmarksTitleFieldSetTemplateNode3.appendChild(
			bookmarksTitleTemplateNode3);

		TemplateNode bookmarksTemplateNode3 = _createTemplateNode(
			"BookmarksLink3", LayoutDDMFormFieldTypeConstants.LINK_TO_LAYOUT);

		bookmarksTitleFieldSetTemplateNode3.appendChild(bookmarksTemplateNode3);

		bookmarksTitleFieldSetTemplateNode1.appendSibling(
			bookmarksTitleFieldSetTemplateNode3);

		separatorFieldSetTemplateNode.appendChild(
			bookmarksTitleFieldSetTemplateNode1);

		templateNodes.add(separatorFieldSetTemplateNode);

		return templateNodes;
	}

	private static final String _REPEATABLE_TEXT_FIELD_NAME =
		"RepeatableTextField";

	private static final String _TEXT_FIELD_NAME = "TextField";

	private static final String _TEXT_FIELD_SET_NAME = "TextFieldSet";

}