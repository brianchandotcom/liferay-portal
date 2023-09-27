/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.uad.display;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.uad.util.DDMUADUtil;
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
@Component(service = UADDisplay.class)
public class DDMFormInstanceUADDisplay extends BaseDDMFormInstanceUADDisplay {

	@Override
	public Map<String, Object> getFieldValues(
		DDMFormInstance ddmFormInstance, String[] fieldNames, Locale locale) {

		Map<String, Object> fieldValues = super.getFieldValues(
			ddmFormInstance, fieldNames, locale);

		DDMUADUtil.formatCreateDate(fieldValues);

		return fieldValues;
	}

	@Override
	public String getName(DDMFormInstance ddmFormInstance, Locale locale) {
		return DDMUADUtil.getFormattedName(ddmFormInstance);
	}

	@Override
	public Class<?> getParentContainerClass() {
		return DDMFormInstance.class;
	}

	@Override
	public Serializable getParentContainerId(DDMFormInstance ddmFormInstance) {
		return 0;
	}

	@Override
	public DDMFormInstance getTopLevelContainer(
		Class<?> parentContainerClass, Serializable parentContainerId,
		Object childObject) {

		if ((childObject instanceof DDMFormInstanceRecord) &&
			(parentContainerId instanceof Long)) {

			try {
				Long ddmFormInstanceParentId = (Long)parentContainerId;

				if (ddmFormInstanceParentId.longValue() == 0) {
					DDMFormInstanceRecord ddmFormInstanceRecord =
						(DDMFormInstanceRecord)childObject;

					return ddmFormInstanceRecord.getFormInstance();
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException);
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

		Property formInstanceIdProperty = PropertyFactoryUtil.forName(
			"formInstanceId");

		DynamicQuery formInstanceIdDynamicQuery =
			_ddmFormInstanceRecordLocalService.dynamicQuery();

		Property userIdProperty = PropertyFactoryUtil.forName("userId");
		Property versionUserIdProperty = PropertyFactoryUtil.forName(
			"versionUserId");

		formInstanceIdDynamicQuery.add(
			RestrictionsFactoryUtil.or(
				userIdProperty.eq(userId), versionUserIdProperty.eq(userId)));

		formInstanceIdDynamicQuery.setProjection(
			ProjectionFactoryUtil.property("formInstanceId"));

		return dynamicQuery.add(
			formInstanceIdProperty.in(formInstanceIdDynamicQuery));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceUADDisplay.class);

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

}