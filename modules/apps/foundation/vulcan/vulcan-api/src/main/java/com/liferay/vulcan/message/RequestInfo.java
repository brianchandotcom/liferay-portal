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

package com.liferay.vulcan.message;

import aQute.bnd.annotation.ProviderType;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Instances of this interface will provide information of the current request.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@ProviderType
public interface RequestInfo {

	/**
	 * Returns the HTTP headers of the current request.
	 *
	 * @return HTTP headers.
	 */
	public MultivaluedMap<String, Object> getHttpHeaders();

	/**
	 * Returns the Media Type of the current request.
	 *
	 * @return the media type.
	 */
	public MediaType getMediaType();

}