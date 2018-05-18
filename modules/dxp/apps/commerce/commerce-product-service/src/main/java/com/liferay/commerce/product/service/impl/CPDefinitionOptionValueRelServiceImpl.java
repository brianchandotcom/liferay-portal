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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.base.CPDefinitionOptionValueRelServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPDefinitionOptionValueRelServiceImpl
	extends CPDefinitionOptionValueRelServiceBaseImpl {

	@Override
	public CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
			long cpDefinitionOptionRelId, Map<Locale, String> nameMap,
			double priority, String key, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionRel(
			getPermissionChecker(), cpDefinitionOptionRelId, ActionKeys.UPDATE);

		return cpDefinitionOptionValueRelLocalService.
			addCPDefinitionOptionValueRel(
				cpDefinitionOptionRelId, nameMap, priority, key,
				serviceContext);
	}

	@Override
	public CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionValueRel(
			getPermissionChecker(), cpDefinitionOptionValueRel,
			ActionKeys.UPDATE);

		return cpDefinitionOptionValueRelLocalService.
			deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	@Override
	public CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionValueRel(
			getPermissionChecker(), cpDefinitionOptionValueRelId,
			ActionKeys.UPDATE);

		return cpDefinitionOptionValueRelLocalService.
			deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	@Override
	public CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelLocalService.
				fetchCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

		if (cpDefinitionOptionValueRel != null) {
			CPDefinitionPermission.checkCPDefinitionOptionValueRel(
				getPermissionChecker(), cpDefinitionOptionValueRel,
				ActionKeys.VIEW);
		}

		return cpDefinitionOptionValueRel;
	}

	@Override
	public CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionValueRel(
			getPermissionChecker(), cpDefinitionOptionValueRelId,
			ActionKeys.VIEW);

		return cpDefinitionOptionValueRelLocalService.
			getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			long cpDefinitionOptionRelId, int start, int end)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionRel(
			getPermissionChecker(), cpDefinitionOptionRelId, ActionKeys.VIEW);

		return cpDefinitionOptionValueRelLocalService.
			getCPDefinitionOptionValueRels(cpDefinitionOptionRelId, start, end);
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			long cpDefinitionOptionRelId, int start, int end,
			OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionRel(
			getPermissionChecker(), cpDefinitionOptionRelId, ActionKeys.VIEW);

		return cpDefinitionOptionValueRelLocalService.
			getCPDefinitionOptionValueRels(
				cpDefinitionOptionRelId, start, end, orderByComparator);
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long groupId, String key, int start, int end) {

		return cpDefinitionOptionValueRelLocalService.
			getCPDefinitionOptionValueRels(key, start, end);
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount(long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionRel(
			getPermissionChecker(), cpDefinitionOptionRelId, ActionKeys.VIEW);

		return cpDefinitionOptionValueRelLocalService.
			getCPDefinitionOptionValueRelsCount(cpDefinitionOptionRelId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return cpDefinitionOptionValueRelLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPDefinitionOptionValueRel>
			searchCPDefinitionOptionValueRels(
				long companyId, long groupId, long cpDefinitionOptionRelId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		return cpDefinitionOptionValueRelLocalService.
			searchCPDefinitionOptionValueRels(
				companyId, groupId, cpDefinitionOptionRelId, keywords, start,
				end, sort);
	}

	@Override
	public CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId, Map<Locale, String> nameMap,
			double priority, String key, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionOptionValueRel(
			getPermissionChecker(), cpDefinitionOptionValueRelId,
			ActionKeys.UPDATE);

		return cpDefinitionOptionValueRelLocalService.
			updateCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId, nameMap, priority, key,
				serviceContext);
	}

}