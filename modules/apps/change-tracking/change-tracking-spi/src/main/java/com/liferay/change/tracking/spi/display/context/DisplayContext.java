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

package com.liferay.change.tracking.spi.display.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The DisplayContext used by <code>CTDisplayRenderer</code> for rendering a
 * model.
 *
 * @author Preston Crary
 * @see    com.liferay.change.tracking.spi.display.CTDisplayRenderer
 */
@ProviderType
public interface DisplayContext<T> {

	/**
	 * Creates a download URL for use while rendering. Only only be used for
	 * <code>CTModel</code> renderers.
	 *
	 * @param  key to be passed to {@link
	 *         com.liferay.change.tracking.spi.display.CTDisplayRenderer#getDownloadInputStream(
	 *         Object, String)}
	 * @param  size the size of the download in bytes or <code>0</code>
	 * @param  title the title to use for the download
	 * @return the URL string or <code>null</code>
	 */
	public String getDownloadURL(String key, long size, String title);

	/**
	 * Returns the request used for rendering.
	 *
	 * @return the request
	 */
	public HttpServletRequest getHttpServletRequest();

	/**
	 * Returns the response used for rendering.
	 *
	 * @return the response
	 */
	public HttpServletResponse getHttpServletResponse();

	/**
	 * Returns the model to be rendered.
	 *
	 * @return the model
	 */
	public T getModel();

}