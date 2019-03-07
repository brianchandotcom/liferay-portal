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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.liferay.portal.vulcan.fields.FieldsQueryParam;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class FieldsQueryParamContextProviderTest {

	@Test
	public void testEmptyStringFieldsReturnValidObjectWithEmptyList() {
		ContextProvider<FieldsQueryParam> contextProvider =
			new FieldsQueryParamContextProvider();

		Message message = _getMessage("");

		FieldsQueryParam fieldsQueryParam = contextProvider.createContext(
			message);

		assertThat(fieldsQueryParam.getFieldNames(), is(empty()));
	}

	@Test
	public void testNestedFieldsAreExpandedIntoSet() {
		ContextProvider<FieldsQueryParam> contextProvider =
			new FieldsQueryParamContextProvider();

		Message message = _getMessage("hello.hi.hello,potato");

		FieldsQueryParam fieldsQueryParam = contextProvider.createContext(
			message);

		assertThat(
			fieldsQueryParam.getFieldNames(),
			containsInAnyOrder(
				"hello", "hello.hi", "hello.hi.hello", "potato"));
	}

	@Test
	public void testNullFieldsReturnValidObjectWithNullList() {
		ContextProvider<FieldsQueryParam> contextProvider =
			new FieldsQueryParamContextProvider();

		Message message = _getMessage(null);

		FieldsQueryParam fieldsQueryParam = contextProvider.createContext(
			message);

		assertThat(fieldsQueryParam, is(not(nullValue())));
		assertThat(fieldsQueryParam.getFieldNames(), is(nullValue()));
	}

	@Test
	public void testPlainFieldsAreProcessedIntoSet() {
		ContextProvider<FieldsQueryParam> contextProvider =
			new FieldsQueryParamContextProvider();

		Message message = _getMessage("hello,hi,hello");

		FieldsQueryParam fieldsQueryParam = contextProvider.createContext(
			message);

		assertThat(
			fieldsQueryParam.getFieldNames(),
			containsInAnyOrder("hello", "hi"));
	}

	private Message _getMessage(String fields) {
		Message message = Mockito.mock(Message.class);

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

		Mockito.when(
			request.getParameter("fields")
		).thenReturn(
			fields
		);

		Mockito.when(
			message.getContextualProperty("HTTP.REQUEST")
		).thenReturn(
			request
		);

		return message;
	}

}