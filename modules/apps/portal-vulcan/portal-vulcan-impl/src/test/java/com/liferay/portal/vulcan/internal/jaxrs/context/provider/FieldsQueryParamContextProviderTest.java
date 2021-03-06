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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.fields.FieldsQueryParam;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class FieldsQueryParamContextProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void test() {

		// Null

		MatcherAssert.assertThat(
			_getFieldNames(null), Matchers.is(Matchers.nullValue()));

		// Empty

		MatcherAssert.assertThat(
			_getFieldNames(""), Matchers.is(Matchers.empty()));

		// Expanded

		MatcherAssert.assertThat(
			_getFieldNames("hello.hi.hello,potato"),
			Matchers.containsInAnyOrder(
				"hello", "hello.hi", "hello.hi.hello", "potato"));

		// No duplicates

		MatcherAssert.assertThat(
			_getFieldNames("hello,hi,hello"),
			Matchers.containsInAnyOrder("hello", "hi"));
	}

	private Set<String> _getFieldNames(String fieldNames) {
		ContextProvider<FieldsQueryParam> contextProvider =
			new FieldsQueryParamContextProvider();

		FieldsQueryParam fieldsQueryParam = contextProvider.createContext(
			_getMessage(fieldNames));

		return fieldsQueryParam.getFieldNames();
	}

	private Message _getMessage(String fieldNames) {
		Message message = Mockito.mock(Message.class);

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			message.getContextualProperty("HTTP.REQUEST")
		).thenReturn(
			httpServletRequest
		);

		Mockito.when(
			httpServletRequest.getParameter("fields")
		).thenReturn(
			fieldNames
		);

		return message;
	}

}