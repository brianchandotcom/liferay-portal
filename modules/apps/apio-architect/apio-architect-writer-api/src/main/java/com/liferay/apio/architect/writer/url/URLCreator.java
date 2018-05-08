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

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageType;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.url.ServerURL;

import javax.ws.rs.core.UriBuilder;

/**
 * Manages the creation of URLs, and has all their necessary information.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public final class URLCreator {

	/**
	 * Returns the URL for a binary resource.
	 *
	 * @param  serverURL the server URL
	 * @param  binaryId the binary resource's ID
	 * @param  path the resource's {@code com.liferay.apio.architect.uri.Path}
	 * @return the URL for a binary resource
	 */
	public static String createBinaryURL(
		ServerURL serverURL, String binaryId, Path path) {

		return String.join("/", serverURL.get(), "b", path.asURI(), binaryId);
	}

	/**
	 * Returns the URL for a collection page.
	 *
	 * @param  collectionURL the collection URL
	 * @param  page the page
	 * @return the collection page URL
	 */
	public static String createCollectionPageURL(
		String collectionURL, Page page, PageType pageType) {

		return UriBuilder.fromUri(
			collectionURL
		).queryParam(
			"page", pageType.getPageNumber(page)
		).queryParam(
			"per_page", page.getItemsPerPage()
		).build(
		).toString();
	}

	/**
	 * Returns the URL for a collection.
	 *
	 * @param  serverURL the server URL
	 * @param  name the resource's name
	 * @return the collection URL
	 */
	public static String createCollectionURL(ServerURL serverURL, String name) {
		return serverURL.get() + "/p/" + name;
	}

	/**
	 * Returns the URL for a {@code Form}.
	 *
	 * @param  serverURL the server URL
	 * @param  form the form
	 * @return the URL for a {@code Form}
	 */
	public static String createFormURL(ServerURL serverURL, Form form) {
		return String.join("/", serverURL.get(), "f", form.id);
	}

	/**
	 * Returns the URL for a nested collection.
	 *
	 * @param  serverURL the server URL
	 * @param  path the single resource's {@link Path}
	 * @param  name the nested resource's name
	 * @return the collection URL
	 */
	public static String createNestedCollectionURL(
		ServerURL serverURL, Path path, String name) {

		return String.join("/", serverURL.get(), "p", path.asURI(), name);
	}

	/**
	 * Returns the URL of a model's resource.
	 *
	 * @param  serverURL the server URL
	 * @param  path the resource's {@link Path}
	 * @return the URL for the {@link
	 *         com.liferay.apio.architect.resource.CollectionResource}
	 */
	public static String createSingleURL(ServerURL serverURL, Path path) {
		return serverURL.get() + "/p/" + path.asURI();
	}

	private URLCreator() {
		throw new UnsupportedOperationException();
	}

}