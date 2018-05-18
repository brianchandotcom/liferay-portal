/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.service.base.CommerceTaxMethodServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxMethodServiceImpl
	extends CommerceTaxMethodServiceBaseImpl {

	@Override
	public CommerceTaxMethod addCommerceTaxMethod(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String engineKey, boolean percentage, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxMethodLocalService.addCommerceTaxMethod(
			nameMap, descriptionMap, engineKey, percentage, active,
			serviceContext);
	}

	@Override
	public CommerceTaxMethod createCommerceTaxMethod(long commerceTaxMethodId) {
		return commerceTaxMethodLocalService.createCommerceTaxMethod(
			commerceTaxMethodId);
	}

	@Override
	public void deleteCommerceTaxMethod(long commerceTaxMethodId)
		throws PortalException {

		CommerceTaxMethod commerceTaxMethod =
			commerceTaxMethodLocalService.getCommerceTaxMethod(
				commerceTaxMethodId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxMethod.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		commerceTaxMethodLocalService.deleteCommerceTaxMethod(
			commerceTaxMethod);
	}

	@Override
	public CommerceTaxMethod fetchCommerceTaxMethod(
		long groupId, String engineKey) {

		return commerceTaxMethodLocalService.fetchCommerceTaxMethod(
			groupId, engineKey);
	}

	@Override
	public CommerceTaxMethod getCommerceTaxMethod(long commerceTaxMethodId)
		throws PortalException {

		return commerceTaxMethodLocalService.getCommerceTaxMethod(
			commerceTaxMethodId);
	}

	@Override
	public List<CommerceTaxMethod> getCommerceTaxMethods(long groupId)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxMethodLocalService.getCommerceTaxMethods(groupId);
	}

	@Override
	public List<CommerceTaxMethod> getCommerceTaxMethods(
		long groupId, boolean active) {

		return commerceTaxMethodLocalService.getCommerceTaxMethods(
			groupId, active);
	}

	@Override
	public int getCommerceTaxMethodsCount(long groupId, boolean active) {
		return commerceTaxMethodLocalService.getCommerceTaxMethodsCount(
			groupId, active);
	}

	@Override
	public CommerceTaxMethod setActive(long commerceTaxMethodId, boolean active)
		throws PortalException {

		CommerceTaxMethod commerceTaxMethod =
			commerceTaxMethodLocalService.fetchCommerceTaxMethod(
				commerceTaxMethodId);

		if (commerceTaxMethod != null) {
			CommercePermission.check(
				getPermissionChecker(), commerceTaxMethod.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);
		}

		return commerceTaxMethodLocalService.setActive(
			commerceTaxMethodId, active);
	}

	@Override
	public CommerceTaxMethod updateCommerceTaxMethod(
			long commerceTaxMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean percentage,
			boolean active)
		throws PortalException {

		CommerceTaxMethod commerceTaxMethod =
			commerceTaxMethodLocalService.getCommerceTaxMethod(
				commerceTaxMethodId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxMethod.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxMethodLocalService.updateCommerceTaxMethod(
			commerceTaxMethod.getCommerceTaxMethodId(), nameMap, descriptionMap,
			percentage, active);
	}

}