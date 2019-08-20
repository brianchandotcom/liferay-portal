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

package com.liferay.data.engine.service.impl;

import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.service.base.DEDataListViewLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.data.engine.model.DEDataListView",
	service = AopService.class
)
public class DEDataListViewLocalServiceImpl
	extends DEDataListViewLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DEDataListView addDEDataListView(
			long groupId, long companyId, long userId, String appliedFilters,
			long ddmStructureId, String fieldNames, Map<Locale, String> name,
			String sortField)
		throws Exception {

		DEDataListView deDataListView = deDataListViewPersistence.create(
			counterLocalService.increment());

		deDataListView.setGroupId(groupId);
		deDataListView.setCompanyId(companyId);
		deDataListView.setUserId(userId);

		User user = _userLocalService.getUser(userId);

		deDataListView.setUserName(user.getFullName());

		deDataListView.setCreateDate(new Date());
		deDataListView.setModifiedDate(new Date());
		deDataListView.setAppliedFilters(appliedFilters);
		deDataListView.setDdmStructureId(ddmStructureId);
		deDataListView.setFieldNames(fieldNames);
		deDataListView.setNameMap(name);
		deDataListView.setSortField(sortField);

		deDataListViewPersistence.update(deDataListView);

		return deDataListView;
	}

	@Override
	public List<DEDataListView> getDEDataListViews(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return deDataListViewPersistence.findByG_C_D(
			groupId, companyId, ddmStructureId, start, end, orderByComparator);
	}

	@Override
	public int getDEDataListViewsCount(
		long groupId, long companyId, long ddmStructureId) {

		return deDataListViewPersistence.countByG_C_D(
			groupId, companyId, ddmStructureId);
	}

	@Override
	public DEDataListView updateDEDataListView(
			long deDataListViewId, String appliedFilters, String fieldNames,
			Map<Locale, String> nameMap, String sortField)
		throws Exception {

		DEDataListView deDataListView =
			deDataListViewPersistence.findByPrimaryKey(deDataListViewId);

		deDataListView.setModifiedDate(new Date());
		deDataListView.setAppliedFilters(appliedFilters);
		deDataListView.setFieldNames(fieldNames);
		deDataListView.setNameMap(nameMap);
		deDataListView.setSortField(sortField);

		return deDataListViewPersistence.update(deDataListView);
	}

	@Reference
	private UserLocalService _userLocalService;

}