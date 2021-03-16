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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsImpl;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Adam Brandizzi
 */
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.liferay.portal.kernel.search.BaseIndexer")
public class FolderTitleLookupTest {

	@Before
	public void setUp() {
		setUpPropsUtil();
	}

	@Test
	public void testGetFolderTitle() throws SearchException {
		Hits hits = getHitsWithDocument(
			getTitleLocalizedFieldName(LocaleUtil.BRAZIL), "My Title");

		FolderTitleLookup folderTitleLookup = new FolderTitleLookupImpl(
			mockFolderSearcher(hits), mockHttpServletRequest(LocaleUtil.US));

		Assert.assertEquals(
			"My Title",
			folderTitleLookup.getFolderTitle(RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetFolderTitleFromAnyLocalizedField()
		throws SearchException {

		Hits hits = getHitsWithDocument(Field.TITLE, "My Title");

		FolderTitleLookup folderTitleLookup = new FolderTitleLookupImpl(
			mockFolderSearcher(hits),
			mockHttpServletRequest(LocaleUtil.BRAZIL));

		Assert.assertEquals(
			"My Title",
			folderTitleLookup.getFolderTitle(RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetFolderTitleFromDisplayLocaleLocalizedField()
		throws SearchException {

		Hits hits = getHitsWithDocument(
			getTitleLocalizedFieldName(LocaleUtil.BRAZIL), "My Title");

		FolderTitleLookup folderTitleLookup = new FolderTitleLookupImpl(
			mockFolderSearcher(hits),
			mockHttpServletRequest(LocaleUtil.BRAZIL));

		Assert.assertEquals(
			"My Title",
			folderTitleLookup.getFolderTitle(RandomTestUtil.randomLong()));
	}

	protected Hits getHitsWithDocument(String fieldName, String value) {
		Document document = new DocumentImpl();

		document.addText(fieldName, value);

		Hits hits = new HitsImpl();

		hits.setLength(1);
		hits.setDocs(new Document[] {document});

		return hits;
	}

	protected String getTitleLocalizedFieldName(Locale locale) {
		return Field.TITLE + StringPool.UNDERLINE + locale;
	}

	protected FolderSearcher mockFolderSearcher(Hits hits)
		throws SearchException {

		FolderSearcher folderSearcher = Mockito.mock(FolderSearcher.class);

		Mockito.when(
			folderSearcher.search(Matchers.any())
		).thenReturn(
			hits
		);

		return folderSearcher;
	}

	protected MockHttpServletRequest mockHttpServletRequest(Locale locale) {
		MockHttpServletRequest request = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			locale
		);

		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return request;
	}

	protected void setUpPropsUtil() {
		PropsUtil.setProps(new PropsImpl());
	}

}