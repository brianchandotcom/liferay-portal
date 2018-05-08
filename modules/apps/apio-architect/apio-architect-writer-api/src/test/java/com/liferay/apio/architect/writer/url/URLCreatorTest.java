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

package com.liferay.apio.architect.writer.url;

import static java.util.Collections.emptyList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.PageType;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.url.ServerURL;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class URLCreatorTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			URLCreator.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test
	public void testCreateBinaryURL() {
		String binaryId = "binary";

		String url = URLCreator.createBinaryURL(_serverURL, binaryId, _path);

		assertThat(url, is("www.liferay.com/b/name/id/binary"));
	}

	@Test
	public void testCreateCollectionPageURL() {
		Pagination pagination = Mockito.mock(Pagination.class);

		Mockito.when(
			pagination.getItemsPerPage()
		).thenReturn(
			30
		);

		Mockito.when(
			pagination.getPageNumber()
		).thenReturn(
			1
		);

		PageItems<String> pageItems = new PageItems<>(emptyList(), 0);

		Page page = new Page<>("", pageItems, pagination, null);

		String firstPageURL = URLCreator.createCollectionPageURL(
			"www.liferay.com", page, PageType.FIRST);

		assertThat(firstPageURL, is("www.liferay.com?page=1&per_page=30"));
	}

	@Test
	public void testCreateCollectionURL() {
		String url = URLCreator.createCollectionURL(_serverURL, "resource");

		assertThat(url, is("www.liferay.com/p/resource"));
	}

	@Test
	public void testCreateNestedCollectionURL() {
		String url = URLCreator.createNestedCollectionURL(
			_serverURL, _path, "related");

		assertThat(url, is("www.liferay.com/p/name/id/related"));
	}

	@Test
	public void testCreateSingleURL() {
		String url = URLCreator.createSingleURL(_serverURL, _path);

		assertThat(url, is("www.liferay.com/p/name/id"));
	}

	private final Path _path = new Path("name", "id");
	private final ServerURL _serverURL = () -> "www.liferay.com";

}