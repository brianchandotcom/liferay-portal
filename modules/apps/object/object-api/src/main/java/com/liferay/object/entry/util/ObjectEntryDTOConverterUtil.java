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

package com.liferay.object.entry.util;

import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.InternalServerErrorException;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryDTOConverterUtil {

	public static DTOConverter<BaseModel<?>, ?> getDTOConverter(
			DTOConverterRegistry dtoConverterRegistry,
			SystemObjectDefinitionManager systemObjectDefinitionManager)
		throws Exception {

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionManager.getJaxRsApplicationDescriptor();

		DTOConverter<BaseModel<?>, ?> dtoConverter =
			(DTOConverter<BaseModel<?>, ?>)dtoConverterRegistry.getDTOConverter(
				jaxRsApplicationDescriptor.getApplicationName(),
				systemObjectDefinitionManager.getModelClassName(),
				jaxRsApplicationDescriptor.getVersion());

		if (dtoConverter == null) {
			throw new InternalServerErrorException(
				"No DTO converter found for " +
					systemObjectDefinitionManager.getModelClassName());
		}

		return dtoConverter;
	}

	public static Map<String, Object> toDTO(
		BaseModel<?> baseModel, DTOConverterRegistry dtoConverterRegistry,
		String objectDefinitionName,
		SystemObjectDefinitionManagerRegistry
			systemObjectDefinitionManagerRegistry,
		User user) {

		try {
			Object dto = toDTO(
				baseModel, dtoConverterRegistry,
				systemObjectDefinitionManagerRegistry.
					getSystemObjectDefinitionManager(objectDefinitionName),
				user);

			if (Validator.isNull(dto)) {
				return new HashMap<>();
			}

			return ObjectMapperUtil.readValue(Map.class, dto.toString());
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return new HashMap<>();
	}

	public static Object toDTO(
			BaseModel<?> baseModel, DTOConverterRegistry dtoConverterRegistry,
			SystemObjectDefinitionManager systemObjectDefinitionManager,
			User user)
		throws Exception {

		DTOConverter<BaseModel<?>, ?> dtoConverter = getDTOConverter(
			dtoConverterRegistry, systemObjectDefinitionManager);

		Locale locale = null;

		if (user != null) {
			locale = user.getLocale();
		}

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), dtoConverterRegistry,
				baseModel.getPrimaryKeyObj(), locale, null, user);

		return dtoConverter.toDTO(defaultDTOConverterContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryDTOConverterUtil.class);

}