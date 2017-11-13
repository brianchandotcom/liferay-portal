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

package com.liferay.reading.time.service.impl;

import com.liferay.reading.time.model.ReadingTimeEntry;
import com.liferay.reading.time.service.base.ReadingTimeEntryLocalServiceBaseImpl;

/**
 * @author Alejandro Tardín
 */
public class ReadingTimeEntryLocalServiceImpl
	extends ReadingTimeEntryLocalServiceBaseImpl {

	@Override
	public ReadingTimeEntry addReadingTimeEntry(
		long groupId, long classNameId, long classPK, long readingTime) {

		long entryId = counterLocalService.increment();

		ReadingTimeEntry entry = readingTimeEntryPersistence.create(entryId);

		entry.setClassNameId(classNameId);
		entry.setClassPK(classPK);
		entry.setGroupId(groupId);
		entry.setReadingTime(readingTime);

		return readingTimeEntryPersistence.update(entry);
	}

	@Override
	public ReadingTimeEntry fetchReadingTimeEntry(
		long groupId, long classNameId, long classPK) {

		return readingTimeEntryPersistence.fetchByG_C_C(
			groupId, classNameId, classPK);
	}

}