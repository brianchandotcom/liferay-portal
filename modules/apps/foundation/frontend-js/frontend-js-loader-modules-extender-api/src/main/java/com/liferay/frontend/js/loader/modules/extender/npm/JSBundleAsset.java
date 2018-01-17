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
 * Provides contents of a <code>JSBundleObject</code>. These contents can be
 * requested from external apps by using a portal URL or {@link InputStream}.
 *
 * @author Iván Zaera
 */
public interface JSBundleAsset extends JSBundleObject {

	/**
	 * Returns the asset's contents.
	 *
	 * @return an {@link InputStream} that allows reading the bytes inside the
	 *         asset
	 * @throws IOException if an IO exception occurred
	 */
	public InputStream getInputStream() throws IOException;

	/**
	 * Returns the asset's public URL. This URL can be used to retrieve the
	 * asset's contents from external apps by making an HTTP request to the
	 * portal.
	 *
	 * @return the asset's public URL
	 */
	public String getURL();

}