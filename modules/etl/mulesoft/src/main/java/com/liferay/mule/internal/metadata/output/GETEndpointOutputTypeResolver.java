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

package com.liferay.mule.internal.metadata.output;

import com.liferay.mule.internal.metadata.MetadataTypeBuilder;
import com.liferay.mule.internal.oas.OASConstants;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;

/**
 * @author Matija Petanjek
 */
public class GETEndpointOutputTypeResolver
	implements OutputTypeResolver<String> {

	@Override
	public String getCategoryName() {
		return "liferay-get";
	}

	@Override
	public MetadataType getOutputType(
			MetadataContext metadataContext, String endpoint)
		throws ConnectionException, MetadataResolvingException {

		return _metadataTypeBuilder.buildMetadataType(
			metadataContext, endpoint, OASConstants.OPERATION_GET,
			OASConstants.
				PATH_RESPONSES_DEFAULT_CONTENT_APPLICATION_JSON_SCHEMA_PATTERN);
	}

	private final MetadataTypeBuilder _metadataTypeBuilder =
		new MetadataTypeBuilder();

}