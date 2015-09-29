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

package com.liferay.portal.search.unit.test;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.math.RandomUtils;

import org.mockito.Mockito;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class DocumentFixture {

	public static Document newDocument(
		long companyId, long groupId, String entryClassName) {

		DocumentImpl document = new DocumentImpl();

		long entryClassPk = RandomUtils.nextLong();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addUID(entryClassName, entryClassPk);

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setEntryKey(entryClassName, entryClassPk);

		return document;
	}

	public void setUp() {
		setUpFastDateFormatFactoryUtil();
		setUpPropsUtil();
	}

	protected static void setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		FastDateFormatFactory fastDateFormatFactory = Mockito.mock(
			FastDateFormatFactory.class);

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			fastDateFormatFactory);

		Mockito.when(
			fastDateFormatFactory.getSimpleDateFormat("yyyyMMddHHmmss")
		).thenReturn(
			new SimpleDateFormat("yyyyMMddHHmmss")
		);
	}

	protected void mockProperty(String property, String value) {
		Mockito.when(
			props.get(property)
		).thenReturn(
			value
		);
	}

	protected void setUpPropsUtil() {
		mockProperty(PropsKeys.INDEX_DATE_FORMAT_PATTERN, "yyyyMMddHHmmss");
		mockProperty(
			PropsKeys.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_ENABLED, "true");
		mockProperty(
			PropsKeys.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_SCORES_THRESHOLD,
			"50");
		mockProperty(PropsKeys.INDEX_SEARCH_HIGHLIGHT_ENABLED, "true");
		mockProperty(PropsKeys.INDEX_SEARCH_HIGHLIGHT_FRAGMENT_SIZE, "80");
		mockProperty(
			PropsKeys.INDEX_SEARCH_HIGHLIGHT_REQUIRE_FIELD_MATCH, "true");
		mockProperty(PropsKeys.INDEX_SEARCH_HIGHLIGHT_SNIPPET_SIZE, "3");
		mockProperty(PropsKeys.INDEX_SEARCH_QUERY_INDEXING_ENABLED, "true");
		mockProperty(PropsKeys.INDEX_SEARCH_QUERY_INDEXING_THRESHOLD, "50");
		mockProperty(PropsKeys.INDEX_SEARCH_QUERY_SUGGESTION_ENABLED, "true");
		mockProperty(
			PropsKeys.INDEX_SEARCH_QUERY_SUGGESTION_MAX, "yyyyMMddHHmmss");
		mockProperty(
			PropsKeys.INDEX_SEARCH_QUERY_SUGGESTION_SCORES_THRESHOLD, "0");
		mockProperty(PropsKeys.INDEX_SEARCH_SCORING_ENABLED, "true");

		PropsUtil.setProps(props);
	}

	protected Props props = Mockito.mock(Props.class);

}