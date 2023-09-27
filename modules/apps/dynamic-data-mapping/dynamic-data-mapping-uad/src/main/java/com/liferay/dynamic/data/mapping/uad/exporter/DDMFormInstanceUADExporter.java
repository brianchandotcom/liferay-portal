/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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