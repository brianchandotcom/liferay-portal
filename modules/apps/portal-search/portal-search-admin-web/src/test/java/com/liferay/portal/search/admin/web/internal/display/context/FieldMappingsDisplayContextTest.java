/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.admin.web.internal.display.context.builder.FieldMappingsDisplayContextBuilder;
import com.liferay.portal.search.index.IndexInformation;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class FieldMappingsDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		setUpIndexInformation();
		setUpPortalUtil();
	}

	@Test
	public void testGetIndexes() {
		FieldMappingsDisplayContextBuilder fieldMappingsDisplayContextBuilder =
			new FieldMappingsDisplayContextBuilder();

		fieldMappingsDisplayContextBuilder.setCurrentURL("/");
		fieldMappingsDisplayContextBuilder.setIndexInformation(
			indexInformation);
		fieldMappingsDisplayContextBuilder.setNamespace("_namespace_");

		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			fieldMappingsDisplayContextBuilder.build();

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
		FieldMappingsDisplayContextBuilder fieldMappingsDisplayContextBuilder =
			new FieldMappingsDisplayContextBuilder();

		fieldMappingsDisplayContextBuilder.setCurrentURL("/");
		fieldMappingsDisplayContextBuilder.setIndexInformation(
			indexInformation);
		fieldMappingsDisplayContextBuilder.setNamespace("_namespace_");
		fieldMappingsDisplayContextBuilder.setSelectedIndexName("index2");

		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			fieldMappingsDisplayContextBuilder.build();

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
		FieldMappingsDisplayContextBuilder fieldMappingsDisplayContextBuilder =
			new FieldMappingsDisplayContextBuilder();

		fieldMappingsDisplayContextBuilder.setCompanyId(2);
		fieldMappingsDisplayContextBuilder.setCurrentURL("/");
		fieldMappingsDisplayContextBuilder.setIndexInformation(
			indexInformation);
		fieldMappingsDisplayContextBuilder.setNamespace("_namespace_");

		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			fieldMappingsDisplayContextBuilder.build();

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
		FieldMappingsDisplayContextBuilder fieldMappingsDisplayContextBuilder =
			new FieldMappingsDisplayContextBuilder();

		fieldMappingsDisplayContextBuilder.setCurrentURL("/");
		fieldMappingsDisplayContextBuilder.setIndexInformation(
			indexInformation);
		fieldMappingsDisplayContextBuilder.setNamespace("_namespace_");

		FieldMappingsDisplayContext fieldMappingsDisplayContext =
			fieldMappingsDisplayContextBuilder.build();

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

	protected void setUpIndexInformation() {
		indexInformation = Mockito.mock(IndexInformation.class);

		Mockito.when(
			indexInformation.getIndexNames()
		).thenReturn(
			new String[] {"index1", "index2"}
		);

		Mockito.when(
			indexInformation.getCompanyIndexName(Mockito.anyLong())
		).thenAnswer(
			invocation -> "index" + invocation.getArguments()[0]
		);
	}

	protected void setUpPortalUtil() {
		_portal = Mockito.mock(Portal.class);

		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgument(0, String.class), StringPool.BLANK
			}
		).when(
			_portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected IndexInformation indexInformation;

	private Portal _portal;

}