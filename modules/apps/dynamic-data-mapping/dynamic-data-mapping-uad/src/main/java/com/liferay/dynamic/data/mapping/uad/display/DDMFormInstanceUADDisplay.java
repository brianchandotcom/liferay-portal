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

package com.liferay.dynamic.data.mapping.uad.display;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.uad.constants.DDMFormInstanceUADConstants;
import com.liferay.dynamic.data.mapping.uad.util.DDMUADHelper;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.user.associated.data.display.UADDisplay;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	service = {DDMFormInstanceUADDisplay.class, UADDisplay.class}
)
public class DDMFormInstanceUADDisplay extends BaseDDMFormInstanceUADDisplay {

	@Override
	public Map<String, Object> getFieldValues(
		DDMFormInstance ddmFormInstance, String[] fieldNames, Locale locale) {

		Map<String, Object> fieldValues = super.getFieldValues(
			ddmFormInstance, fieldNames, locale);

		_ddmUADHelper.formatCreateDateIfExist(fieldValues);

		return fieldValues;
	}

	@Override
	public String getName(DDMFormInstance ddmFormInstance, Locale locale) {
		return _ddmUADHelper.getDDMFormInstanceFormattedName(ddmFormInstance);
	}

	@Override
	public Class<?> getParentContainerClass() {
		return DDMFormInstance.class;
	}

	@Override
	public Serializable getParentContainerId(DDMFormInstance ddmFormInstance) {
		return DDMFormInstanceUADConstants.DEFAULT_DDM_FORM_INSTANCE_PARENT_ID;
	}

	@Override
	public DDMFormInstance getTopLevelContainer(
		Class parentContainerClass, Serializable parentContainerId,
		Object childObject) {

		if (childObject instanceof DDMFormInstanceRecord) {
			try {
				DDMFormInstanceRecord ddmFormInstanceRecord =
					(DDMFormInstanceRecord)childObject;

				if ((long)parentContainerId ==
						DDMFormInstanceUADConstants.
							DEFAULT_DDM_FORM_INSTANCE_PARENT_ID) {

					return ddmFormInstanceRecord.getFormInstance();
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}

		return null;
	}

	@Override
	public boolean isUserOwned(DDMFormInstance ddmFormInstance, long userId) {
		if (ddmFormInstance.getUserId() == userId) {
			return true;
		}

		return false;
	}

	@Override
	protected DynamicQuery getDynamicQuery(long userId) {
		DynamicQuery dynamicQuery = ddmFormInstanceLocalService.dynamicQuery();

		DynamicQuery dynamicSubquery =
			_ddmFormInstanceRecordLocalService.dynamicQuery();

		dynamicSubquery.setProjection(
			ProjectionFactoryUtil.property("formInstanceId"));

		Property userIdProperty = PropertyFactoryUtil.forName("userId");
		Property versionUserIdProperty = PropertyFactoryUtil.forName(
			"versionUserId");

		dynamicSubquery.add(
			RestrictionsFactoryUtil.or(
				userIdProperty.eq(userId), versionUserIdProperty.eq(userId)));

		Property formInstanceIdProperty = PropertyFactoryUtil.forName(
			"formInstanceId");

		return dynamicQuery.add(formInstanceIdProperty.in(dynamicSubquery));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceUADDisplay.class);

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Reference
	private DDMUADHelper _ddmUADHelper;

}