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

package com.liferay.structure.apio.internal.util;

import com.liferay.structure.apio.architect.util.StructureFieldConverter;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class StructureFieldConverterImplTest {

	@Test
	public void testGetFieldDataTypeDate() {
		Assert.assertEquals(
			"date",
			_structureFieldConverter.getFieldDataType("string", "date"));
	}

	@Test
	public void testGetFieldDataTypeDefault() {
		Assert.assertEquals(
			"aaa", _structureFieldConverter.getFieldDataType("aaa"));
	}

	@Test
	public void testGetFieldDataTypeDocumentLibrary() {
		Assert.assertEquals(
			"document",
			_structureFieldConverter.getFieldDataType("document-library"));
	}

	@Test
	public void testGetFieldDataTypeDocumentLibraryWithDataTypeString() {
		Assert.assertEquals(
			"document",
			_structureFieldConverter.getFieldDataType(
				"string", "document_library"));
	}

	@Test
	public void testGetFieldDataTypeJournalArticle() {
		Assert.assertEquals(
			"structuredContent",
			_structureFieldConverter.getFieldDataType("journal-article"));
	}

	@Test
	public void testGetFieldDataTypeLinkToPage() {
		Assert.assertEquals(
			"url", _structureFieldConverter.getFieldDataType("link-to-page"));
	}

	@Test
	public void testGetFieldDataTypeParagraph() {
		Assert.assertEquals(
			"string",
			_structureFieldConverter.getFieldDataType("string", "paragraph"));
	}

	@Test
	public void testGetFieldDataTypeRadio() {
		Assert.assertEquals(
			"string", _structureFieldConverter.getFieldDataType("radio"));
	}

	@Test
	public void testGetFieldInputControlCheckbox() {
		Assert.assertEquals(
			"checkbox",
			_structureFieldConverter.getFieldInputControl("checkbox"));
	}

	@Test
	public void testGetFieldInputControlCheckboxMultiple() {
		Assert.assertEquals(
			"checkbox_multiple",
			_structureFieldConverter.getFieldInputControl("checkbox_multiple"));
	}

	@Test
	public void testGetFieldInputControlDefault() {
		Assert.assertNull(_structureFieldConverter.getFieldInputControl("aaa"));
	}

	@Test
	public void testGetFieldInputControlGrid() {
		Assert.assertEquals(
			"grid", _structureFieldConverter.getFieldInputControl("grid"));
	}

	@Test
	public void testGetFieldInputControlParagraph() {
		Assert.assertEquals(
			"paragraph",
			_structureFieldConverter.getFieldInputControl("paragraph"));
	}

	@Test
	public void testGetFieldInputControlRadio() {
		Assert.assertEquals(
			"radio", _structureFieldConverter.getFieldInputControl("radio"));
	}

	@Test
	public void testGetFieldInputControlSelect() {
		Assert.assertEquals(
			"select", _structureFieldConverter.getFieldInputControl("select"));
	}

	@Test
	public void testGetFieldInputControlText() {
		Assert.assertEquals(
			"text", _structureFieldConverter.getFieldInputControl("text"));
	}

	@Test
	public void testGetFieldInputControlTextArea() {
		Assert.assertEquals(
			"textarea",
			_structureFieldConverter.getFieldInputControl("textarea"));
	}

	private final StructureFieldConverter _structureFieldConverter =
		new StructureFieldConverterImpl();

}