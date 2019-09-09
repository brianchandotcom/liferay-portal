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

package com.liferay.mule.internal.metadata;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.mule.internal.connection.LiferayConnection;

import java.io.IOException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataKeyBuilder;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.FailureCode;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

/**
 * @author Matija Petanjek
 */
public abstract class LiferayBaseEndpointsKeysResolver
	implements TypeKeysResolver {

	public Set<MetadataKey> getKeys(
			MetadataContext metadataContext, String httpMethod)
		throws ConnectionException, MetadataResolvingException {

		Set<MetadataKey> metadataKeys = new HashSet<>();

		Optional<LiferayConnection> connection =
			metadataContext.getConnection();

		if (connection.isPresent()) {
			LiferayConnection liferayConnection = connection.get();

			try {
				JsonNode openAPISpecJsonNode =
					liferayConnection.getOpenAPISpecJsonNode();

				JsonNode pathsJsonNode = openAPISpecJsonNode.get("paths");

				Iterator<Map.Entry<String, JsonNode>> pathsIterator =
					pathsJsonNode.fields();

				while (pathsIterator.hasNext()) {
					Map.Entry<String, JsonNode> pathEntry =
						pathsIterator.next();

					JsonNode pathJsonNode = pathEntry.getValue();

					if (pathJsonNode.has(httpMethod)) {
						String path = pathEntry.getKey();

						MetadataKeyBuilder key = MetadataKeyBuilder.newKey(
							path);

						metadataKeys.add(key.build());
					}
				}
			}
			catch (IOException ioe) {
				throw new MetadataResolvingException(
					ioe.getMessage(), FailureCode.NO_DYNAMIC_KEY_AVAILABLE);
			}
			catch (TimeoutException te) {
				throw new MetadataResolvingException(
					te.getMessage(), FailureCode.CONNECTION_FAILURE);
			}
		}

		return metadataKeys;
	}

}