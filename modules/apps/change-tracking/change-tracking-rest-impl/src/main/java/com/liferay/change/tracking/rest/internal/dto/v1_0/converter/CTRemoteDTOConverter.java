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

package com.liferay.change.tracking.rest.internal.dto.v1_0.converter;

import com.liferay.change.tracking.rest.dto.v1_0.CTRemote;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "dto.class.name=com.liferay.change.tracking.model.CTRemote",
	service = DTOConverter.class
)
public class CTRemoteDTOConverter
	implements DTOConverter
		<com.liferay.change.tracking.model.CTRemote, CTRemote> {

	@Override
	public String getContentType() {
		return CTRemote.class.getSimpleName();
	}

	@Override
	public CTRemote toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.change.tracking.model.CTRemote ctRemote)
		throws Exception {

		if (ctRemote == null) {
			return null;
		}

		return new CTRemote() {
			{
				actions = dtoConverterContext.getActions();
				dateCreated = ctRemote.getCreateDate();
				dateModified = ctRemote.getModifiedDate();
				description = ctRemote.getDescription();
				id = ctRemote.getCtRemoteId();
				name = ctRemote.getName();
				ownerName = ctRemote.getUserName();
				url = ctRemote.getUrl();
			}
		};
	}

}