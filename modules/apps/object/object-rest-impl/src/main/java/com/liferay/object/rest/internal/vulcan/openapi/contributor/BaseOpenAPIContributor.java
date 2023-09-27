/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.vulcan.openapi.contributor;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
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

		SystemObjectDefinitionManager systemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager(
					systemObjectDefinition.getName());

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionManager.getJaxRsApplicationDescriptor();

		DTOConverter<?, ?> dtoConverter = _dtoConverterRegistry.getDTOConverter(
			jaxRsApplicationDescriptor.getApplicationName(),
			systemObjectDefinition.getClassName(),
			jaxRsApplicationDescriptor.getVersion());

		return dtoConverter.getExternalDTOClassName();
	}

	protected String getSchemaName(ObjectDefinition objectDefinition) {
		if (objectDefinition.isUnmodifiableSystemObject()) {
			return StringUtil.extractLast(
				getExternalDTOClassName(objectDefinition), StringPool.PERIOD);
		}

		return objectDefinition.getShortName();
	}

	protected void init(
		DTOConverterRegistry dtoConverterRegistry,
		SystemObjectDefinitionManagerRegistry
			systemObjectDefinitionManagerRegistry) {

		_dtoConverterRegistry = dtoConverterRegistry;
		_systemObjectDefinitionManagerRegistry =
			systemObjectDefinitionManagerRegistry;
	}

	private DTOConverterRegistry _dtoConverterRegistry;
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

}