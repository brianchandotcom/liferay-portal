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

package com.liferay.object.rest.internal.vulcan.openapi.contributor;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;

/**
 * @author Carlos Correa
 */
public abstract class BaseOpenAPIContributor implements OpenAPIContributor {

	protected String getExternalDTOClassName(
		ObjectDefinition systemObjectDefinition) {

		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
			_systemObjectDefinitionMetadataRegistry.
				getSystemObjectDefinitionMetadata(
					systemObjectDefinition.getName());

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		DTOConverter<?, ?> dtoConverter = _dtoConverterRegistry.getDTOConverter(
			jaxRsApplicationDescriptor.getApplicationName(),
			systemObjectDefinition.getClassName(),
			jaxRsApplicationDescriptor.getVersion());

		return dtoConverter.getExternalDTOClassName();
	}

	protected String getSchemaName(ObjectDefinition objectDefinition) {
		if (objectDefinition.isSystem()) {
			return StringUtil.extractLast(
				getExternalDTOClassName(objectDefinition), StringPool.PERIOD);
		}

		return objectDefinition.getShortName();
	}

	protected void init(
		DTOConverterRegistry dtoConverterRegistry,
		SystemObjectDefinitionMetadataRegistry
			systemObjectDefinitionMetadataRegistry) {

		_dtoConverterRegistry = dtoConverterRegistry;
		_systemObjectDefinitionMetadataRegistry =
			systemObjectDefinitionMetadataRegistry;
	}

	private DTOConverterRegistry _dtoConverterRegistry;
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

}