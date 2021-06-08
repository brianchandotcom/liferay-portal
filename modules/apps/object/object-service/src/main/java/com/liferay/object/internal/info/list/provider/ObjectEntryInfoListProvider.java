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

package com.liferay.object.internal.info.list.provider;

import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoListProvider.class)
public class ObjectEntryInfoListProvider
	implements InfoListProvider<ObjectEntry> {

	@Override
	public List<ObjectEntry> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return getInfoList(infoListProviderContext, Pagination.of(0, 20), null);
	}

	@Override
	public List<ObjectEntry> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		return _objectEntryLocalService.getObjectEntries(
			pagination.getStart(), pagination.getEnd());
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		return _objectEntryLocalService.getObjectEntriesCount();
	}

	@Override
	public String getLabel(Locale locale) {
		return "objects";
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}