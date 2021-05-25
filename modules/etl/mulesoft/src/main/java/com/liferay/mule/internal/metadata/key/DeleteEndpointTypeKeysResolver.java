/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

package com.liferay.mule.internal.metadata.key;

import com.liferay.mule.internal.metadata.MetadataKeysBuilder;
import com.liferay.mule.internal.oas.constants.OASConstants;

import java.util.Set;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

/**
 * @author Matija Petanjek
 */
public class DeleteEndpointTypeKeysResolver implements TypeKeysResolver {

	@Override
	public String getCategoryName() {
		return "liferay-delete";
	}

	@Override
	public Set<MetadataKey> getKeys(MetadataContext metadataContext)
		throws ConnectionException, MetadataResolvingException {

		return metadataKeysBuilder.buildEndpointMetadataKeys(
			metadataContext, OASConstants.OPERATION_DELETE);
	}

	private final MetadataKeysBuilder metadataKeysBuilder =
		new MetadataKeysBuilder();

}