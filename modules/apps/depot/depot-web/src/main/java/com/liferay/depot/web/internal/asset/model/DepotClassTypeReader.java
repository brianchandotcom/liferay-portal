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

package com.liferay.depot.web.internal.asset.model;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class DepotClassTypeReader implements ClassTypeReader {

	public DepotClassTypeReader(
		ClassTypeReader classTypeReader,
		DepotEntryLocalService depotEntryLocalService) {

		_classTypeReader = classTypeReader;
		_depotEntryLocalService = depotEntryLocalService;
	}

	@Override
	public List<ClassType> getAvailableClassTypes(
		long[] groupIds, Locale locale) {

		try {
			return _classTypeReader.getAvailableClassTypes(
				_getGroupIds(groupIds), locale);
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public ClassType getClassType(long classTypeId, Locale locale)
		throws PortalException {

		return _classTypeReader.getClassType(classTypeId, locale);
	}

	private long[] _getGroupIds(long[] siteGroupIds) throws PortalException {
		List<Long> groupIds = new ArrayList<>(ListUtil.fromArray(siteGroupIds));

		for (long siteGroupId : siteGroupIds) {
			IntervalActionProcessor<Void> intervalActionProcessor =
				new IntervalActionProcessor<>(
					_depotEntryLocalService.getGroupConnectedDepotEntriesCount(
						siteGroupId));

			intervalActionProcessor.setPerformIntervalActionMethod(
				(start, end) -> {
					List<DepotEntry> depotEntries =
						_depotEntryLocalService.getGroupConnectedDepotEntries(
							siteGroupId, start, end);

					for (DepotEntry depotEntry : depotEntries) {
						groupIds.add(depotEntry.getGroupId());
					}

					return null;
				});

			intervalActionProcessor.performIntervalActions();
		}

		return ListUtil.toLongArray(groupIds, groupId -> groupId);
	}

	private final ClassTypeReader _classTypeReader;
	private final DepotEntryLocalService _depotEntryLocalService;

}