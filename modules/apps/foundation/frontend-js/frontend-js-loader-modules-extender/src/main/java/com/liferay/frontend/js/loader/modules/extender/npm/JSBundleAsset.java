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

package com.liferay.frontend.js.loader.modules.extender.npm;

import java.io.IOException;
import java.io.InputStream;

/**
 * A {@link JSBundleObject} which contents can be requested from the outside
 * world using a portal URL or by means of an {@link InputStream}.
 * @author Iván Zaera
 */
public interface JSBundleAsset extends JSBundleObject {

	/**
	 * Retrieve the contents of the asset.
	 * @return an {@link InputStream} that allows reading the bytes inside the
	 *         asset
	 * @throws IOException
	 */
	public InputStream getInputStream() throws IOException;

	/**
	 * Get the public URL of the asset. This URL can be used to retrieve the
	 * asset's contents from the outside world by making an HTTP request to the
	 * portal.
	 * @return a standard URL
	 */
	public String getURL();

}