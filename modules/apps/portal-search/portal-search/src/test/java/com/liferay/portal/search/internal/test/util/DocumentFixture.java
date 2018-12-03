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

package com.liferay.portal.search.internal.test.util;

import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class DocumentFixture {

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

		Map<String, Object> properties = new HashMap<>();

		properties.put(PropsKeys.INDEX_DATE_FORMAT_PATTERN, "yyyyMMddHHmmss");
		properties.put(
			PropsKeys.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_ENABLED, "true");
		properties.put(
			PropsKeys.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_SCORES_THRESHOLD,
			"50");
		properties.put(PropsKeys.INDEX_SEARCH_HIGHLIGHT_FRAGMENT_SIZE, "80");
		properties.put(
			PropsKeys.INDEX_SEARCH_HIGHLIGHT_REQUIRE_FIELD_MATCH, "true");
		properties.put(PropsKeys.INDEX_SEARCH_HIGHLIGHT_SNIPPET_SIZE, "3");
		properties.put(PropsKeys.INDEX_SEARCH_QUERY_INDEXING_ENABLED, "true");
		properties.put(PropsKeys.INDEX_SEARCH_QUERY_INDEXING_THRESHOLD, "50");
		properties.put(PropsKeys.INDEX_SEARCH_QUERY_SUGGESTION_ENABLED, "true");
		properties.put(
			PropsKeys.INDEX_SEARCH_QUERY_SUGGESTION_MAX, "yyyyMMddHHmmss");
		properties.put(
			PropsKeys.INDEX_SEARCH_QUERY_SUGGESTION_SCORES_THRESHOLD, "0");
		properties.put(PropsKeys.INDEX_SEARCH_SCORING_ENABLED, "true");
		properties.put(
			PropsKeys.INDEX_SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH, "255");

		props = PropsTestUtil.setProps(properties);
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