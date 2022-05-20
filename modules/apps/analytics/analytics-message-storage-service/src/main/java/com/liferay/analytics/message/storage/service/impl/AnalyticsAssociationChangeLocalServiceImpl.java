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

package com.liferay.analytics.message.storage.service.impl;

import com.liferay.analytics.message.storage.model.AnalyticsAssociationChange;
import com.liferay.analytics.message.storage.service.base.AnalyticsAssociationChangeLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.analytics.message.storage.model.AnalyticsAssociationChange",
	service = AopService.class
)
public class AnalyticsAssociationChangeLocalServiceImpl
	extends AnalyticsAssociationChangeLocalServiceBaseImpl {

	public AnalyticsAssociationChange addAnalyticsAssociationChange(
		long companyId, Date createDate, long userId,
		String associationClassName, long associationClassPK, String className,
		long classPK) {

		AnalyticsAssociationChange analyticsAssociationChange =
			analyticsAssociationChangePersistence.create(
				counterLocalService.increment());

		analyticsAssociationChange.setCompanyId(companyId);
		analyticsAssociationChange.setCreateDate(createDate);
		analyticsAssociationChange.setModifiedDate(createDate);
		analyticsAssociationChange.setUserId(userId);
		analyticsAssociationChange.setAssociationClassName(
			associationClassName);
		analyticsAssociationChange.setAssociationClassPK(associationClassPK);
		analyticsAssociationChange.setClassName(className);
		analyticsAssociationChange.setClassPK(classPK);

		return analyticsAssociationChangePersistence.update(
			analyticsAssociationChange);
	}

	@Override
	public void deleteAnalyticsAssociationChanges(
		long companyId, String associationClassName, long associationClassPK) {

		analyticsAssociationChangePersistence.removeByC_A_A(
			companyId, associationClassName, associationClassPK);
	}

	@Override
	public List<AnalyticsAssociationChange> getAnalyticsAssociationChanges(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end) {

		return analyticsAssociationChangePersistence.findByC_GtM_A(
			companyId, modifiedDate, associationClassName, start, end);
	}

	@Override
	public List<AnalyticsAssociationChange> getAnalyticsAssociationChanges(
		long companyId, String associationClassName, int start, int end) {

		return analyticsAssociationChangePersistence.findByC_A(
			companyId, associationClassName, start, end);
	}

	@Override
	public int getAnalyticsAssociationChangesCount(
		long companyId, Date modifiedDate, String associationClassName) {

		return analyticsAssociationChangePersistence.countByC_GtM_A(
			companyId, modifiedDate, associationClassName);
	}

	@Override
	public int getAnalyticsAssociationChangesCount(
		long companyId, String associationClassName) {

		return analyticsAssociationChangePersistence.countByC_A(
			companyId, associationClassName);
	}

}