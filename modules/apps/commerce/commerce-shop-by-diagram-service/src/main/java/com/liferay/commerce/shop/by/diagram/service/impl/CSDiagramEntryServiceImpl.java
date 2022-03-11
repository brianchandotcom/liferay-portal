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

package com.liferay.commerce.shop.by.diagram.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.base.CSDiagramEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CSDiagramEntry"
	},
	service = AopService.class
)
public class CSDiagramEntryServiceImpl extends CSDiagramEntryServiceBaseImpl {

	@Override
	public CSDiagramEntry addCSDiagramEntry(
			long cpDefinitionId, long cpInstanceId, long cProductId,
			boolean diagram, int quantity, String sequence, String sku,
			ServiceContext serviceContext)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramEntryLocalService.addCSDiagramEntry(
			getUserId(), cpDefinitionId, cpInstanceId, cProductId, diagram,
			quantity, sequence, sku, serviceContext);
	}

	@Override
	public void deleteCSDiagramEntries(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		csDiagramEntryLocalService.deleteCSDiagramEntries(cpDefinitionId);
	}

	@Override
	public void deleteCSDiagramEntry(CSDiagramEntry csDiagramEntry)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), csDiagramEntry.getCPDefinitionId(),
			ActionKeys.UPDATE);

		csDiagramEntryLocalService.deleteCSDiagramEntry(csDiagramEntry);
	}

	@Override
	public CSDiagramEntry fetchCSDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramEntryLocalService.fetchCSDiagramEntry(
			cpDefinitionId, sequence);
	}

	@Override
	public List<CSDiagramEntry> getCSDiagramEntries(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramEntryLocalService.getCSDiagramEntries(
			cpDefinitionId, start, end);
	}

	@Override
	public int getCSDiagramEntriesCount(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramEntryLocalService.getCSDiagramEntriesCount(
			cpDefinitionId);
	}

	@Override
	public CSDiagramEntry getCSDiagramEntry(long csDiagramEntryId)
		throws PortalException {

		CSDiagramEntry csDiagramEntry =
			csDiagramEntryLocalService.getCSDiagramEntry(csDiagramEntryId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), csDiagramEntry.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return csDiagramEntry;
	}

	@Override
	public CSDiagramEntry getCSDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramEntryLocalService.getCSDiagramEntry(
			cpDefinitionId, sequence);
	}

	@Override
	public CSDiagramEntry updateCSDiagramEntry(
			long csDiagramEntryId, long cpInstanceId, long cProductId,
			boolean diagram, int quantity, String sequence, String sku,
			ServiceContext serviceContext)
		throws PortalException {

		CSDiagramEntry csDiagramEntry =
			csDiagramEntryLocalService.getCSDiagramEntry(csDiagramEntryId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), csDiagramEntry.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return csDiagramEntryLocalService.updateCSDiagramEntry(
			csDiagramEntryId, cpInstanceId, cProductId, diagram, quantity,
			sequence, sku, serviceContext);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CSDiagramEntryServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}