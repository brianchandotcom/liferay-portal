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

package com.liferay.vulcan.writer.url;

import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.PageType;
import com.liferay.vulcan.uri.Path;
import com.liferay.vulcan.url.ServerURL;

import javax.ws.rs.core.UriBuilder;

/**
 * This class manages the creation of URLs and has all the necessary information
 * about them.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 * @review
 */
public final class URLCreator {

	/**
	 * Returns the URL for a binary resource.
	 *
	 * @param  serverURL the server URL.
	 * @param  binaryId the ID of the binary resource
	 * @param  path the {@code Path} of the resource
	 * @return the URL for a binary resource
	 * @review
	 */
	public static String createBinaryURL(
		ServerURL serverURL, String binaryId, Path path) {

		return String.join(
			"/", serverURL.getServerURL(), "b", path.asURI(), binaryId);
	}

	/**
	 * Returns the URL for a collection.
	 *
	 * @param  collectionURL the collection URL
	 * @param  page the page
	 * @return the collection page URL
	 * @review
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
	 * @param  path the {@code Path} of the single resource
	 * @param  name the name of the resource
	 * @return the collection URL
	 * @review
	 */
	public static String createCollectionURL(
		ServerURL serverURL, Path path, String name) {

		return String.join(
			"/", serverURL.getServerURL(), "p", path.asURI(), name);
	}

	/**
	 * Returns the URL to the resource of a certain model.
	 *
	 * @param  serverURL the server URL
	 * @param  path the {@code Path} of the resource
	 * @return the single URL for the {@code CollectionResource}
	 * @review
	 */
	public static String createSingleURL(ServerURL serverURL, Path path) {
		return serverURL.getServerURL() + "/p/" + path.asURI();
	}

	private URLCreator() {
		throw new UnsupportedOperationException();
	}

}