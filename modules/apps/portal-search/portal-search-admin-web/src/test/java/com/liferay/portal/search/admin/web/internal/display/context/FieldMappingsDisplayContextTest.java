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

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.index.IndexInformation;
import com.liferay.portal.util.HttpImpl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class FieldMappingsDisplayContextTest {

	@Before
	public void setUp() {
		setUpHttpUtil();
		setUpIndexInformation();
		setUpPortalUtil();
	}

	@Test
	public void testGetIndexes() {
		FieldMappingsDisplayBuilder fieldMappingsDisplayBuilder =
			new FieldMappingsDisplayBuilder(http);

		fieldMappingsDisplayBuilder.setCurrentURL("/");
		fieldMappingsDisplayBuilder.setIndexInformation(indexInformation);
		fieldMappingsDisplayBuilder.setNamespace("_namespace_");

		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			fieldMappingsDisplayBuilder.build();

		List<FieldMappingIndexDisplayContext> fieldMappingIndexDisplayContexts =
			fieldMappingsDisplayContext.getFieldMappingIndexDisplayContexts();

		Assert.assertEquals(
			fieldMappingIndexDisplayContexts.toString(), 2,
			fieldMappingIndexDisplayContexts.size());

		FieldMappingIndexDisplayContext fieldMappingIndexDisplayContext =
			fieldMappingIndexDisplayContexts.get(0);

		Assert.assertEquals(
			"index1", fieldMappingIndexDisplayContext.getName());
		Assert.assertEquals(
			"active", fieldMappingIndexDisplayContext.getCssClass());

		String url = fieldMappingIndexDisplayContext.getUrl();

		Assert.assertTrue(
			url, url.contains("_namespace_selectedIndexName=index1"));

		fieldMappingIndexDisplayContext = fieldMappingIndexDisplayContexts.get(
			1);

		Assert.assertEquals(
			"index2", fieldMappingIndexDisplayContext.getName());
		Assert.assertEquals("", fieldMappingIndexDisplayContext.getCssClass());

		url = fieldMappingIndexDisplayContext.getUrl();

		Assert.assertTrue(
			url, url.contains("_namespace_selectedIndexName=index2"));
	}

	@Test
	public void testGetSelectedIndexName() {
		FieldMappingsDisplayBuilder fieldMappingsDisplayBuilder =
			new FieldMappingsDisplayBuilder(http);

		fieldMappingsDisplayBuilder.setCurrentURL("/");
		fieldMappingsDisplayBuilder.setIndexInformation(indexInformation);
		fieldMappingsDisplayBuilder.setNamespace("_namespace_");
		fieldMappingsDisplayBuilder.setSelectedIndexName("index2");

		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			fieldMappingsDisplayBuilder.build();

		Assert.assertEquals(
			"index2", fieldMappingsDisplayContext.getSelectedIndexName());

		List<FieldMappingIndexDisplayContext> fieldMappingIndexDisplayContexts =
			fieldMappingsDisplayContext.getFieldMappingIndexDisplayContexts();

		Assert.assertEquals(
			fieldMappingIndexDisplayContexts.toString(), 2,
			fieldMappingIndexDisplayContexts.size());

		FieldMappingIndexDisplayContext fieldMappingIndexDisplayContext =
			fieldMappingIndexDisplayContexts.get(0);

		Assert.assertEquals(
			"index1", fieldMappingIndexDisplayContext.getName());
		Assert.assertEquals("", fieldMappingIndexDisplayContext.getCssClass());

		fieldMappingIndexDisplayContext = fieldMappingIndexDisplayContexts.get(
			1);

		Assert.assertEquals(
			"index2", fieldMappingIndexDisplayContext.getName());
		Assert.assertEquals(
			"active", fieldMappingIndexDisplayContext.getCssClass());
	}

	@Test
	public void testGetSelectedIndexNameDefaultCompany() {
		FieldMappingsDisplayBuilder fieldMappingsDisplayBuilder =
			new FieldMappingsDisplayBuilder(http);

		fieldMappingsDisplayBuilder.setCompanyId(2);
		fieldMappingsDisplayBuilder.setCurrentURL("/");
		fieldMappingsDisplayBuilder.setIndexInformation(indexInformation);
		fieldMappingsDisplayBuilder.setNamespace("_namespace_");

		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			fieldMappingsDisplayBuilder.build();

		Assert.assertEquals(
			"index2", fieldMappingsDisplayContext.getSelectedIndexName());

		List<FieldMappingIndexDisplayContext> fieldMappingIndexDisplayContexts =
			fieldMappingsDisplayContext.getFieldMappingIndexDisplayContexts();

		Assert.assertEquals(
			fieldMappingIndexDisplayContexts.toString(), 2,
			fieldMappingIndexDisplayContexts.size());

		FieldMappingIndexDisplayContext fieldMappingIndexDisplayContext =
			fieldMappingIndexDisplayContexts.get(0);

		Assert.assertEquals(
			"index1", fieldMappingIndexDisplayContext.getName());
		Assert.assertEquals("", fieldMappingIndexDisplayContext.getCssClass());

		fieldMappingIndexDisplayContext = fieldMappingIndexDisplayContexts.get(
			1);

		Assert.assertEquals(
			"index2", fieldMappingIndexDisplayContext.getName());
		Assert.assertEquals(
			"active", fieldMappingIndexDisplayContext.getCssClass());
	}

	@Test
	public void testGetSelectedIndexNameDefaultFirst() {
		FieldMappingsDisplayBuilder fieldMappingsDisplayBuilder =
			new FieldMappingsDisplayBuilder(http);

		fieldMappingsDisplayBuilder.setCurrentURL("/");
		fieldMappingsDisplayBuilder.setIndexInformation(indexInformation);
		fieldMappingsDisplayBuilder.setNamespace("_namespace_");

		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			fieldMappingsDisplayBuilder.build();

		Assert.assertEquals(
			"index1", fieldMappingsDisplayContext.getSelectedIndexName());

		List<FieldMappingIndexDisplayContext> fieldMappingIndexDisplayContexts =
			fieldMappingsDisplayContext.getFieldMappingIndexDisplayContexts();

		Assert.assertEquals(
			fieldMappingIndexDisplayContexts.toString(), 2,
			fieldMappingIndexDisplayContexts.size());

		FieldMappingIndexDisplayContext fieldMappingIndexDisplayContext =
			fieldMappingIndexDisplayContexts.get(0);

		Assert.assertEquals(
			"index1", fieldMappingIndexDisplayContext.getName());
		Assert.assertEquals(
			"active", fieldMappingIndexDisplayContext.getCssClass());

		fieldMappingIndexDisplayContext = fieldMappingIndexDisplayContexts.get(
			1);

		Assert.assertEquals(
			"index2", fieldMappingIndexDisplayContext.getName());
		Assert.assertEquals("", fieldMappingIndexDisplayContext.getCssClass());
	}

	protected void setUpHttpUtil() {
		http = new HttpImpl();
	}

	protected void setUpIndexInformation() {
		indexInformation = Mockito.mock(IndexInformation.class);

		Mockito.when(
			indexInformation.getIndexNames()
		).thenReturn(
			new String[] {"index1", "index2"}
		);

		Mockito.when(
			indexInformation.getCompanyIndexName(Matchers.anyLong())
		).thenAnswer(
			invocation -> "index" + invocation.getArguments()[0]
		);
	}

	protected void setUpPortalUtil() {
		_portal = Mockito.mock(Portal.class);

		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgumentAt(0, String.class), StringPool.BLANK
			}
		).when(
			_portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected Http http;
	protected IndexInformation indexInformation;

	private Portal _portal;

}