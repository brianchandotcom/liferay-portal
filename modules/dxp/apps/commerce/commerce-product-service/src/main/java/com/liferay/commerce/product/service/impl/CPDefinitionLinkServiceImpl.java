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

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.base.CPDefinitionLinkServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPDefinitionLinkServiceImpl
	extends CPDefinitionLinkServiceBaseImpl {

	@Override
	public CPDefinitionLink deleteCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionLink(
			getPermissionChecker(), cpDefinitionLinkId, ActionKeys.UPDATE);

		return cpDefinitionLinkLocalService.deleteCPDefinitionLink(
			cpDefinitionLinkId);
	}

	@Override
	public CPDefinitionLink fetchCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException {

		CPDefinitionLink cpDefinitionLink =
			cpDefinitionLinkLocalService.fetchCPDefinitionLink(
				cpDefinitionLinkId);

		if (cpDefinitionLink != null) {
			CPDefinitionPermission.checkCPDefinitionLink(
				getPermissionChecker(), cpDefinitionLink, ActionKeys.VIEW);
		}

		return cpDefinitionLink;
	}

	@Override
	public CPDefinitionLink getCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionLink(
			getPermissionChecker(), cpDefinitionLinkId, ActionKeys.VIEW);

		return cpDefinitionLinkLocalService.getCPDefinitionLink(
			cpDefinitionLinkId);
	}

	@Override
	public List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId1, String type)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId1, ActionKeys.VIEW);

		return cpDefinitionLinkLocalService.getCPDefinitionLinks(
			cpDefinitionId1, type);
	}

	@Override
	public List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId1, String type, int start, int end,
			OrderByComparator<CPDefinitionLink> orderByComparator)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId1, ActionKeys.VIEW);

		return cpDefinitionLinkLocalService.getCPDefinitionLinks(
			cpDefinitionId1, type, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionLinksCount(long cpDefinitionId1, String type)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId1, ActionKeys.VIEW);

		return cpDefinitionLinkLocalService.getCPDefinitionLinksCount(
			cpDefinitionId1, type);
	}

	@Override
	public CPDefinitionLink updateCPDefinitionLink(
			long cpDefinitionLinkId, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPDefinitionLink(
			getPermissionChecker(), cpDefinitionLinkId, ActionKeys.UPDATE);

		return cpDefinitionLinkLocalService.updateCPDefinitionLink(
			cpDefinitionLinkId, priority, serviceContext);
	}

	@Override
	public void updateCPDefinitionLinks(
			long cpDefinitionId1, long[] cpDefinitionIds2, String type,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId1, ActionKeys.UPDATE);

		cpDefinitionLinkLocalService.updateCPDefinitionLinks(
			cpDefinitionId1, cpDefinitionIds2, type, serviceContext);
	}

}