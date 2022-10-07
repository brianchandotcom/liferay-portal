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

package com.liferay.dynamic.data.mapping.uad.exporter;

import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.user.associated.data.exporter.UADExporter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = UADExporter.class)
public class DDMFormInstanceUADExporter extends BaseDDMFormInstanceUADExporter {

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery(long userId) {
		ActionableDynamicQuery actionableDynamicQuery =
			doGetActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property formInstanceIdProperty = PropertyFactoryUtil.forName(
					"formInstanceId");

				DynamicQuery formInstanceIdDynamicQuery =
					_ddmFormInstanceRecordLocalService.dynamicQuery();

				formInstanceIdDynamicQuery.add(
					RestrictionsFactoryUtil.eq("userId", userId));
				formInstanceIdDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("formInstanceId"));

				dynamicQuery.add(
					formInstanceIdProperty.in(formInstanceIdDynamicQuery));
			});

		return actionableDynamicQuery;
	}

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

}