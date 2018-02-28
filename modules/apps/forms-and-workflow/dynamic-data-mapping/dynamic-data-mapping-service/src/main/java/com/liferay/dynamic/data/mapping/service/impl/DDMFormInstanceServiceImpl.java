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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceServiceBaseImpl;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Leonardo Barros
 */
public class DDMFormInstanceServiceImpl extends DDMFormInstanceServiceBaseImpl {

	@Override
	public DDMFormInstance addFormInstance(
			long groupId, long ddmStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap,
			DDMFormValues settingsDDMFormValues, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, DDMActionKeys.ADD_FORM_INSTANCE);

		return ddmFormInstanceLocalService.addFormInstance(
			getUserId(), groupId, ddmStructureId, nameMap, descriptionMap,
			settingsDDMFormValues, serviceContext);
	}

	@Override
	public void deleteFormInstance(long ddmFormInstanceId)
		throws PortalException {

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(), ddmFormInstanceId, ActionKeys.DELETE);

		ddmFormInstanceLocalService.deleteFormInstance(ddmFormInstanceId);
	}

	@Override
	public DDMFormInstance fetchFormInstance(long ddmFormInstanceId)
		throws PortalException {

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceLocalService.fetchFormInstance(ddmFormInstanceId);

		if (ddmFormInstance == null) {
			return null;
		}

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(), ddmFormInstance.getFormInstanceId(),
			ActionKeys.VIEW);

		return ddmFormInstance;
	}

	@Override
	public DDMFormInstance getFormInstance(long ddmFormInstanceId)
		throws PortalException {

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(), ddmFormInstanceId, ActionKeys.VIEW);

		return ddmFormInstanceLocalService.getFormInstance(ddmFormInstanceId);
	}

	@Override
	public List<DDMFormInstance> getFormInstances(long[] groupIds) {
		return ddmFormInstancePersistence.findByGroupId(groupIds);
	}

	@Override
	public List<DDMFormInstance> search(
		long companyId, long groupId, String keywords, int start, int end,
		OrderByComparator<DDMFormInstance> orderByComparator) {

		return ddmFormInstanceFinder.filterFindByKeywords(
			companyId, groupId, keywords, start, end, orderByComparator);
	}

	@Override
	public List<DDMFormInstance> search(
		long companyId, long groupId, String[] names, String[] descriptions,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMFormInstance> orderByComparator) {

		return ddmFormInstanceFinder.filterFindByC_G_N_D(
			companyId, groupId, names, descriptions, andOperator, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long groupId, String keywords) {
		return ddmFormInstanceFinder.filterCountByKeywords(
			companyId, groupId, keywords);
	}

	@Override
	public int searchCount(
		long companyId, long groupId, String[] names, String[] descriptions,
		boolean andOperator) {

		return ddmFormInstanceFinder.filterCountByC_G_N_D(
			companyId, groupId, names, descriptions, andOperator);
	}

	/**
	 * Updates the the record set's settings.
	 *
	 * @param  formInstanceId the primary key of the form instance
	 * @param  settingsDDMFormValues the record set's settings. For more
	 *         information see <code>DDMFormValues</code> in the
	 *         <code>dynamic.data.mapping.api</code> module.
	 * @return the record set
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public DDMFormInstance updateFormInstance(
			long formInstanceId, DDMFormValues settingsDDMFormValues)
		throws PortalException {

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(), formInstanceId, ActionKeys.UPDATE);

		return ddmFormInstanceLocalService.updateFormInstance(
			formInstanceId, settingsDDMFormValues);
	}

	@Override
	public DDMFormInstance updateFormInstance(
			long ddmFormInstanceId, long ddmStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMFormValues settingsDDMFormValues, ServiceContext serviceContext)
		throws PortalException {

		_ddmFormInstanceModelResourcePermission.check(
			getPermissionChecker(), ddmFormInstanceId, ActionKeys.UPDATE);

		return ddmFormInstanceLocalService.updateFormInstance(
			ddmFormInstanceId, ddmStructureId, nameMap, descriptionMap,
			settingsDDMFormValues, serviceContext);
	}

	private static volatile ModelResourcePermission<DDMFormInstance>
		_ddmFormInstanceModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DDMFormInstanceServiceImpl.class,
				"_ddmFormInstanceModelResourcePermission",
				DDMFormInstance.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				DDMFormInstanceServiceImpl.class, "_portletResourcePermission",
				DDMConstants.RESOURCE_NAME);

}