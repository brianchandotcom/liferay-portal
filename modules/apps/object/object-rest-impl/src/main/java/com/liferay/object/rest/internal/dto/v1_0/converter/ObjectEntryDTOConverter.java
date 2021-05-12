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

package com.liferay.object.rest.internal.dto.v1_0.converter;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectEntry",
	service = {DTOConverter.class, ObjectEntryDTOConverter.class}
)
public class ObjectEntryDTOConverter
	implements DTOConverter<com.liferay.object.model.ObjectEntry, ObjectEntry> {

	@Override
	public String getContentType() {
		return ObjectEntry.class.getSimpleName();
	}

	@Override
	public ObjectEntry toDTO(
		DTOConverterContext dtoConverterContext,
		com.liferay.object.model.ObjectEntry objectEntry) {

		return new ObjectEntry() {
			{
				actions = dtoConverterContext.getActions();
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfoOptional(),
					_userLocalService.fetchUser(objectEntry.getUserId()));
				dateCreated = objectEntry.getCreateDate();
				dateModified = objectEntry.getModifiedDate();
				id = objectEntry.getObjectEntryId();
				properties = (Map)objectEntry.getValues();
			}
		};
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}