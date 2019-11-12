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

package com.liferay.portal.template.soy.data;

import com.liferay.portal.template.soy.util.SoyRawData;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Describes the API of an OSGi service that creates specialized complex types
 * that can be used in Soy templates.
 *
 * @author Iván Zaera Avellón
 * @review
 */
@ProviderType
public interface SoyDataFactory {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #createSoyRawData(String)}
	 */
	@Deprecated
	public SoyHTMLData createSoyHTMLData(String html);

	public SoyRawData createSoyRawData(String html);

}