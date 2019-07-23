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

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the de data list view local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.data.engine.service.DEDataListViewLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEDataListViewLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.data.engine.model.DEDataListView",
	service = AopService.class
)
public class DEDataListViewLocalServiceImpl
	extends DEDataListViewLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.liferay.data.engine.service.DEDataListViewLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.data.engine.service.DEDataListViewLocalServiceUtil</code>.
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DEDataListView addDEDataListView(
			long groupId, long companyId, long userId, String appliedFilters,
			long ddmStructureId, String fieldNames, Map<Locale, String> name,
			String sortField)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		DEDataListView deDataListView = deDataListViewPersistence.create(
			counterLocalService.increment());

		deDataListView.setGroupId(groupId);
		deDataListView.setCompanyId(companyId);
		deDataListView.setUserId(userId);
		deDataListView.setUserName(user.getFullName());
		deDataListView.setAppliedFilters(appliedFilters);
		deDataListView.setDdmStructureId(ddmStructureId);
		deDataListView.setFieldNames(fieldNames);
		deDataListView.setNameMap(name);
		deDataListView.setSortField(sortField);

		deDataListViewPersistence.update(deDataListView);

		return deDataListView;
	}

	@Reference
	private UserLocalService _userLocalService;

}