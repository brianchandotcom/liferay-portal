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

package com.liferay.structured.content.apio.internal.architect.provider;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.structured.content.apio.architect.filter.Filter;
import com.liferay.structured.content.apio.architect.filter.FilterParser;
import com.liferay.structured.content.apio.architect.filter.InvalidFilterException;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitException;
import com.liferay.structured.content.apio.architect.filter.expression.LiteralExpression;
import com.liferay.structured.content.apio.internal.architect.filter.expression.LiteralExpressionImpl;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class FilterProviderTest {

	@Test
	public void testCreateContextWithInValidFilter() {
		FilterProvider filterProvider = new FilterProvider();

		FilterParser filterParser = filterString -> {
			throw new ExpressionVisitException("errorMessage", new Exception());
		};

		filterProvider.setFilterParser(filterParser);

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> filterProvider.createContext(_createFilterRequest())
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessageContaining("errorMessage");
	}

	@Test
	public void testCreateContextWithUnexpectedException() {
		FilterProvider filterProvider = new FilterProvider();

		FilterParser filterParser = filterString -> {
			throw new RuntimeException("errorMessage");
		};

		filterProvider.setFilterParser(filterParser);

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> filterProvider.createContext(_createFilterRequest())
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessageContaining("errorMessage");
	}

	@Test
	public void testCreateContextWithValidFilter() {
		FilterProvider filterProvider = new FilterProvider();

		FilterParser filterParser = filterString -> new LiteralExpressionImpl(
			"some String", LiteralExpression.Type.STRING);

		filterProvider.setFilterParser(filterParser);

		Filter filter = filterProvider.createContext(_createFilterRequest());

		Assert.assertNotNull(filter.getExpression());
	}

	private HttpServletRequest _createFilterRequest() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

		Mockito.when(
			request.getParameter("filter")
		).thenReturn(
			RandomTestUtil.randomString()
		);

		return request;
	}

}