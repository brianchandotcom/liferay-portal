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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.base.CPDefinitionLinkLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionLinkLocalServiceImpl
	extends CPDefinitionLinkLocalServiceBaseImpl {

	@Override
	public CPDefinitionLink addCPDefinitionLink(
			long cpDefinitionId1, long cpDefinitionId2, double priority,
			int type, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		Date now = new Date();

		long cpDefinitionLinkId = counterLocalService.increment();

		CPDefinitionLink cpDefinitionLink = cpDefinitionLinkPersistence.create(
			cpDefinitionLinkId);

		cpDefinitionLink.setCompanyId(user.getCompanyId());
		cpDefinitionLink.setUserId(user.getUserId());
		cpDefinitionLink.setUserName(user.getFullName());
		cpDefinitionLink.setCreateDate(now);
		cpDefinitionLink.setCPDefinitionId1(cpDefinitionId1);
		cpDefinitionLink.setCPDefinitionId2(cpDefinitionId2);
		cpDefinitionLink.setPriority(priority);
		cpDefinitionLink.setType(type);

		cpDefinitionLinkPersistence.update(cpDefinitionLink);

		return cpDefinitionLink;
	}

	@Override
	public CPDefinitionLink deleteCPDefinitionLink(
			CPDefinitionLink cpDefinitionLink) throws PortalException {

		return cpDefinitionLinkPersistence.remove(cpDefinitionLink);
	}

	@Override
	public CPDefinitionLink deleteCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException {

		CPDefinitionLink cpDefinitionLink =
			cpDefinitionLinkPersistence.findByPrimaryKey(cpDefinitionLinkId);

		return deleteCPDefinitionLink(cpDefinitionLink);
	}

	@Override
	public List<CPDefinitionLink> getCPDefinitionLinks(long cpDefinitionId)
		throws PortalException {

		return cpDefinitionLinkPersistence.findByC1(cpDefinitionId);
	}

	@Override
	public List<CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator)
	throws PortalException {

		return cpDefinitionLinkPersistence.findByC1(
				cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionLinksCount(long cpDefinitionId)
		throws PortalException {

		return cpDefinitionLinkPersistence.countByC1(cpDefinitionId);
	}

	@Override
	public CPDefinitionLink updateCPDefinitionLink(
		long cpDefinitionLinkId, double priority) throws PortalException {

		CPDefinitionLink cpDefinitionLink =
			cpDefinitionLinkPersistence.findByPrimaryKey(cpDefinitionLinkId);

		cpDefinitionLink.setPriority(priority);

		cpDefinitionLinkPersistence.update(cpDefinitionLink);

		return cpDefinitionLink;
	}

	@Override
	public void updateCPDefinitionLinks(
			long cpDefinitionId, long[] cpDefinitionLinkEntryIds, int type,
			ServiceContext serviceContext)
		throws PortalException {

		if (cpDefinitionLinkEntryIds == null) {
			return;
		}

		List<CPDefinitionLink> cpDefinitionLinks = getCPDefinitionLinks(
			cpDefinitionId);

		for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
			if (((cpDefinitionLink.getCPDefinitionId1() == cpDefinitionId) &&
				!ArrayUtil.contains(cpDefinitionLinkEntryIds, cpDefinitionLink.getCPDefinitionId2()))) {

				deleteCPDefinitionLink(cpDefinitionLink);
			}
		}

		for (long cpDefinitionLinkEntryId : cpDefinitionLinkEntryIds) {
			if (cpDefinitionLinkEntryId != cpDefinitionId) {
				CPDefinitionLink cpDefinitionLink =
					cpDefinitionLinkPersistence.fetchByC_C_T(
						cpDefinitionId, cpDefinitionLinkEntryId, type);

				if (cpDefinitionLink == null) {
					addCPDefinitionLink(
						cpDefinitionId, cpDefinitionLinkEntryId, 0, type,
						serviceContext);
				}
			}
		}
	}
}