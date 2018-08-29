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

package com.liferay.structured.content.apio.internal.architect.filter;

import org.apache.olingo.server.core.SchemaBasedEdmProvider;

/**
 * <p>
 * Provider of the Common Schema Definition Language (CSDL) for an Entity Data
 * Model (EDM) used by a service.
 * </p>
 *
 * <p>
 * EDM is the underlying abstract data model used by OData services to
 * formalize the description of the resources it exposes. Meanwhile, CSDL
 * defines an XML-based representation of the entity model.
 *
 * @author David Arques
 * @review
 */
public abstract class SingleEntitySchemaBaseProvider
	extends SchemaBasedEdmProvider {

	/**
	 * Returns the name of the single entity used to create the EDM.
	 *
	 * @return the entity name
	 * @review
	 */
	public abstract String getSingleEntityTypeName();

}