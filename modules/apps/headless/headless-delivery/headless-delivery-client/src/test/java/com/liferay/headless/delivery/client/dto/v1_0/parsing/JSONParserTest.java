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

package com.liferay.headless.delivery.client.dto.v1_0.parsing;

import com.liferay.headless.delivery.client.dto.v1_0.ContentField;
import com.liferay.headless.delivery.client.dto.v1_0.ContentSetElement;
import com.liferay.headless.delivery.client.dto.v1_0.Creator;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.dto.v1_0.Value;
import com.liferay.headless.delivery.client.dto.v1_0.page.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.ContentSetElementSerDes;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentSerDes;
import com.liferay.headless.delivery.client.serdes.v1_0.page.PageSerDes;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class JSONParserTest extends BaseJSONParserTestCase {

	@Test
	public void testToPageOfContentSetElements() throws Exception {
		String fileContent = getFileContent("page-content-set-elements.json");

		Page<ContentSetElement> page = PageSerDes.toPage(
			fileContent, ContentSetElementSerDes::toDTO);

		Assert.assertNotNull(page);
		Assert.assertEquals(1, page.getLastPage());
		Assert.assertEquals(1, page.getPage());
		Assert.assertEquals(20, page.getPageSize());
		Assert.assertEquals(1, page.getTotalCount());

		List<ContentSetElement> contentSetElements =
			(List<ContentSetElement>)page.getItems();

		Assert.assertEquals(
			contentSetElements.toString(), 1, contentSetElements.size());

		ContentSetElement contentSetElement = contentSetElements.get(0);

		Assert.assertEquals(
			"com.liferay.journal.model.JournalArticle",
			contentSetElement.getContentType());

		Assert.assertEquals(0.0, contentSetElement.getOrder());
		Assert.assertEquals("bwc1 title", contentSetElement.getTitle());

		String content = (String)contentSetElement.getContent();

		StructuredContent structuredContent = StructuredContentSerDes.toDTO(
			content);

		Assert.assertArrayEquals(
			new String[] {"en-US"}, structuredContent.getAvailableLanguages());

		ContentField[] contentFields = structuredContent.getContentFields();

		Assert.assertEquals(contentFields.toString(), 1, contentFields.length);

		Assert.assertEquals("html", contentFields[0].getDataType());
		Assert.assertEquals("content", contentFields[0].getName());
		Assert.assertFalse(contentFields[0].getRepeatable());

		Value value = contentFields[0].getValue();

		Assert.assertNotNull(value);

		Assert.assertEquals("<p>wc1c</p>", value.getData());

		Assert.assertEquals(
			Long.valueOf(20123L), structuredContent.getContentSpaceId());
		Assert.assertEquals(
			Long.valueOf(36501L), structuredContent.getContentStructureId());
	}

	@Test
	public void testToPageOfStructuredContents() throws Exception {
		String fileContent = getFileContent("page-structured-contents.json");

		Page page = PageSerDes.toPage(
			fileContent, StructuredContentSerDes::toDTO);

		Assert.assertNotNull(page);
		Assert.assertEquals(1, page.getLastPage());
		Assert.assertEquals(1, page.getPage());
		Assert.assertEquals(20, page.getPageSize());
		Assert.assertEquals(1, page.getTotalCount());

		Collection<StructuredContent> structuredContents = page.getItems();

		Assert.assertEquals(
			structuredContents.toString(), 1, structuredContents.size());

		for (StructuredContent structuredContent : structuredContents) {
			Assert.assertArrayEquals(
				new String[] {"en-US"},
				structuredContent.getAvailableLanguages());

			Creator creator = structuredContent.getCreator();

			Assert.assertNotNull(creator);
		}
	}

	@Test
	public void testToPageOfStructuredContentsEmptyValue() throws Exception {
		String fileContent = getFileContent(
			"page-structured-contents-empty-value.json");

		Page page = PageSerDes.toPage(
			fileContent, StructuredContentSerDes::toDTO);

		Assert.assertNotNull(page);
		Assert.assertEquals(1, page.getLastPage());
		Assert.assertEquals(1, page.getPage());
		Assert.assertEquals(20, page.getPageSize());
		Assert.assertEquals(1, page.getTotalCount());

		Collection<StructuredContent> structuredContents = page.getItems();

		Assert.assertEquals(
			structuredContents.toString(), 1, structuredContents.size());

		for (StructuredContent structuredContent : structuredContents) {
			ContentField[] contentFields = structuredContent.getContentFields();

			Assert.assertEquals(
				contentFields.toString(), 1, contentFields.length);

			ContentField contentField = contentFields[0];

			Assert.assertNotNull(contentField);

			Value value = contentField.getValue();

			Assert.assertNotNull(value);

			Assert.assertNull(value.getData());
			Assert.assertNull(value.getDocument());
			Assert.assertNull(value.getGeo());
			Assert.assertNull(value.getImage());
			Assert.assertNull(value.getLink());
			Assert.assertNull(value.getStructuredContentLink());
		}
	}

	@Test
	public void testToPageOfStructuredContentsNullValue() throws Exception {
		String fileContent = getFileContent(
			"page-structured-contents-null-value.json");

		Page page = PageSerDes.toPage(
			fileContent, StructuredContentSerDes::toDTO);

		Assert.assertNotNull(page);
		Assert.assertEquals(1, page.getLastPage());
		Assert.assertEquals(1, page.getPage());
		Assert.assertEquals(20, page.getPageSize());
		Assert.assertEquals(1, page.getTotalCount());

		Collection<StructuredContent> structuredContents = page.getItems();

		Assert.assertEquals(
			structuredContents.toString(), 1, structuredContents.size());

		for (StructuredContent structuredContent : structuredContents) {
			ContentField[] contentFields = structuredContent.getContentFields();

			Assert.assertEquals(
				contentFields.toString(), 1, contentFields.length);

			ContentField contentField = contentFields[0];

			Assert.assertNotNull(contentField);

			Assert.assertNull(contentField.getValue());
		}
	}

}