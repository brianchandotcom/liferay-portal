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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.text.SimpleDateFormat;

import org.mockito.Mockito;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class DocumentFixture {

	public static Document newDocument(
		long companyId, long groupId, String entryClassName) {

		DocumentImpl document = new DocumentImpl();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.GROUP_ID, groupId);

		long entryClassPK = RandomTestUtil.randomLong();

		document.addUID(entryClassName, entryClassPK);

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setEntryKey(entryClassName, entryClassPK);

		return document;
	}

	public void mockProperty(String property, String value) {
		Mockito.when(
			props.get(property)
		).thenReturn(
			value
		);
	}

	public void setUp() {
		setUpFastDateFormatFactoryUtil();
		setUpPropsUtil();
	}

	public void tearDown() {
		tearDownFastDateFormatFactoryUtil();
		tearDownPropsUtil();
	}

	protected void setUpFastDateFormatFactoryUtil() {
		_fastDateFormatFactory =
			FastDateFormatFactoryUtil.getFastDateFormatFactory();

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		FastDateFormatFactory fastDateFormatFactory = Mockito.mock(
			FastDateFormatFactory.class);

		Mockito.when(
			fastDateFormatFactory.getSimpleDateFormat("yyyyMMddHHmmss")
		).thenReturn(
			new SimpleDateFormat("yyyyMMddHHmmss")
		);

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			fastDateFormatFactory);
	}

	protected void setUpPropsUtil() {
		_props = PropsUtil.getProps();

		props = Mockito.mock(Props.class);

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
		mockProperty(
			PropsKeys.INDEX_SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH, "255");

		PropsUtil.setProps(props);
	}

	protected void tearDownFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			_fastDateFormatFactory);

		_fastDateFormatFactory = null;
	}

	protected void tearDownPropsUtil() {
		PropsUtil.setProps(_props);

		_props = null;

		props = null;
	}

	protected Props props;

	private FastDateFormatFactory _fastDateFormatFactory;
	private Props _props;

}