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

package com.liferay.object.rest.internal.util;

import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Collections;

import javax.ws.rs.InternalServerErrorException;

/**
 * @author Carolina Barbosa
 */
public class DTOConverterUtil {

	public static Object toDTO(
			BaseModel<?> baseModel, DTOConverterRegistry dtoConverterRegistry,
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata,
			User user)
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		DTOConverter<BaseModel<?>, ?> dtoConverter =
			(DTOConverter<BaseModel<?>, ?>)dtoConverterRegistry.getDTOConverter(
				jaxRsApplicationDescriptor.getApplicationName(),
				systemObjectDefinitionMetadata.getModelClassName(),
				jaxRsApplicationDescriptor.getVersion());

		if (dtoConverter == null) {
			throw new InternalServerErrorException(
				"No DTO converter found for " +
					systemObjectDefinitionMetadata.getModelClassName());
		}

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), dtoConverterRegistry,
				baseModel.getPrimaryKeyObj(), user.getLocale(), null, user);

		return dtoConverter.toDTO(defaultDTOConverterContext);
	}

}