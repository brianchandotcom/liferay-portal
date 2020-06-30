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

package com.liferay.headless.commerce.core.dto.v1_0.converter;

import java.util.Locale;
import java.util.Optional;

import javax.ws.rs.core.UriInfo;

/**
 * @author Alessio Antonio Rendina
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 * 					 com.liferay.portal.vulcan.dto.converter.DTOConverterContext}
 */
@Deprecated
public interface DTOConverterContext {

	public Object getCompositeResourcePrimKey();

	public Locale getLocale();

	public long getResourcePrimKey();

	public Optional<UriInfo> getUriInfoOptional();

}