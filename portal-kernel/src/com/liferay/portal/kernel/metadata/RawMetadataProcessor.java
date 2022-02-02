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

package com.liferay.portal.kernel.metadata;

import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;

import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Miguel Pastor
 */
@ProviderType
public interface RawMetadataProcessor {

	public static final String TIKA_RAW_METADATA = "TIKARAWMETADATA";

	public Map<String, Set<String>> getFieldNames();

	public Map<String, DDMFormValues> getRawMetadataMap(
			String mimeType, InputStream inputStream)
		throws PortalException;

}