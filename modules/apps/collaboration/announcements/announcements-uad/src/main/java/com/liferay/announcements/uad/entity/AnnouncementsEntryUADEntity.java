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

package com.liferay.announcements.uad.entity;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.user.associated.data.entity.BaseUADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.exception.UADEntityFieldNameException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Noah Sherrill
 */
public class AnnouncementsEntryUADEntity extends BaseUADEntity {

	public AnnouncementsEntryUADEntity(
		long userId, String uadEntityId,
		AnnouncementsEntry announcementsEntry) {

		super(
			userId, uadEntityId, AnnouncementsUADConstants.ANNOUNCEMENTS_ENTRY);

		_announcementsEntry = announcementsEntry;
	}

	public AnnouncementsEntry getAnnouncementsEntry() {
		return _announcementsEntry;
	}

	@Override
	public Map<String, Object> getEntityNonAnonymizableFieldValues(
			List<String> nonAnonymizableFieldNames)
		throws PortalException {

		Map<String, Object> nonAnonymizableFieldValues = new HashMap<>();

		for (String nonAnonymizableFieldName : nonAnonymizableFieldNames) {
			try {
				Method method = AnnouncementsEntry.class.getMethod(
					_getMethodName(nonAnonymizableFieldName));

				nonAnonymizableFieldValues.put(
					nonAnonymizableFieldName,
					method.invoke(_announcementsEntry));
			}
			catch (IllegalAccessException | InvocationTargetException e) {
				throw new UADEntityException(e);
			}
			catch (NoSuchMethodException nsme) {
				throw new UADEntityFieldNameException(nsme);
			}
		}

		return nonAnonymizableFieldValues;
	}

	private String _getMethodName(String fieldName) {
		return "get" + StringUtil.upperCaseFirstLetter(fieldName);
	}

	private final AnnouncementsEntry _announcementsEntry;

}