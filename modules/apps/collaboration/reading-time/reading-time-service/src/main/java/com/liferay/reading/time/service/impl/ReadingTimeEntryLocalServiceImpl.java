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

import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.reading.time.calculator.ReadingTimeCalculator;
import com.liferay.reading.time.model.ReadingTimeEntry;
import com.liferay.reading.time.service.base.ReadingTimeEntryLocalServiceBaseImpl;

import java.time.Duration;

import java.util.Optional;

/**
 * @author Alejandro Tardín
 */
public class ReadingTimeEntryLocalServiceImpl
	extends ReadingTimeEntryLocalServiceBaseImpl {

	@Override
	public ReadingTimeEntry addReadingTimeEntry(
		GroupedModel groupedModel, Duration readingTime) {

		return addReadingTimeEntry(
			groupedModel.getGroupId(),
			_classNameLocalService.getClassNameId(groupedModel.getModelClass()),
			(Long)groupedModel.getPrimaryKeyObj(), readingTime);
	}

	@Override
	public ReadingTimeEntry addReadingTimeEntry(
		long groupId, long classNameId, long classPK, Duration readingTime) {

		long entryId = counterLocalService.increment();

		ReadingTimeEntry entry = readingTimeEntryPersistence.create(entryId);

		entry.setClassNameId(classNameId);
		entry.setClassPK(classPK);
		entry.setGroupId(groupId);
		entry.setReadingTime(readingTime.toMillis());

		return readingTimeEntryPersistence.update(entry);
	}

	@Override
	public ReadingTimeEntry deleteReadingTimeEntry(GroupedModel groupedModel) {
		ReadingTimeEntry readingTimeEntry = fetchReadingTimeEntry(groupedModel);

		if (readingTimeEntry != null) {
			return deleteReadingTimeEntry(readingTimeEntry);
		}

		return null;
	}

	@Override
	public ReadingTimeEntry deleteReadingTimeEntry(
		long groupId, long classNameId, long classPK) {

		ReadingTimeEntry readingTimeEntry = fetchReadingTimeEntry(
			groupId, classNameId, classPK);

		if (readingTimeEntry != null) {
			return deleteReadingTimeEntry(readingTimeEntry);
		}

		return null;
	}

	@Override
	public ReadingTimeEntry fetchOrAddReadingTimeEntry(
		GroupedModel groupedModel) {

		ReadingTimeEntry readingTimeEntry = fetchReadingTimeEntry(groupedModel);

		if (readingTimeEntry != null) {
			return readingTimeEntry;
		}

		return updateReadingTimeEntry(groupedModel);
	}

	@Override
	public ReadingTimeEntry fetchReadingTimeEntry(GroupedModel groupedModel) {
		return fetchReadingTimeEntry(
			groupedModel.getGroupId(),
			_classNameLocalService.getClassNameId(groupedModel.getModelClass()),
			(Long)groupedModel.getPrimaryKeyObj());
	}

	@Override
	public ReadingTimeEntry fetchReadingTimeEntry(
		long groupId, long classNameId, long classPK) {

		return readingTimeEntryPersistence.fetchByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public ReadingTimeEntry updateReadingTimeEntry(GroupedModel groupedModel) {
		Optional<Duration> readingTimeOptional =
			_readingTimeCalculator.calculate(groupedModel);

		if (readingTimeOptional.isPresent()) {
			return updateReadingTimeEntry(
				groupedModel.getGroupId(),
				_classNameLocalService.getClassNameId(
					groupedModel.getModelClass()),
				(Long)groupedModel.getPrimaryKeyObj(),
				readingTimeOptional.get());
		}

		return null;
	}

	@Override
	public ReadingTimeEntry updateReadingTimeEntry(
		long groupId, long classNameId, long classPK, Duration readingTime) {

		ReadingTimeEntry readingTimeEntry = fetchReadingTimeEntry(
			groupId, classNameId, classPK);

		if (readingTimeEntry == null) {
			return addReadingTimeEntry(
				groupId, classNameId, classPK, readingTime);
		}
		else {
			readingTimeEntry.setReadingTime(readingTime.toMillis());

			return updateReadingTimeEntry(readingTimeEntry);
		}
	}

	@ServiceReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

	@ServiceReference(type = ReadingTimeCalculator.class)
	private ReadingTimeCalculator _readingTimeCalculator;

}