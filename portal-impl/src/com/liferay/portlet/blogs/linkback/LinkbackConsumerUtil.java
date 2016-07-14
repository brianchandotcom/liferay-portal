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

package com.liferay.portlet.blogs.linkback;

/**
 * @author André de Oliveira
 */
public class LinkbackConsumerUtil {

	public static void addNewTrackback(
		long commentId, String url, String entryURL) {

		_linkbackConsumer.addNewTrackback(commentId, url, entryURL);
	}

	/**
	 * @deprecated As of 7.1.0, with no direct replacement
	 */
	@Deprecated
	public static LinkbackConsumer getLinkbackConsumer() {
		return _linkbackConsumer;
	}

	public static void verifyNewTrackbacks() {
		_linkbackConsumer.verifyNewTrackbacks();
	}

	public static void verifyTrackback(
		long commentId, String url, String entryURL) {

		_linkbackConsumer.verifyTrackback(commentId, url, entryURL);
	}

	private static final LinkbackConsumer _linkbackConsumer =
		new LinkbackConsumerImpl();

}