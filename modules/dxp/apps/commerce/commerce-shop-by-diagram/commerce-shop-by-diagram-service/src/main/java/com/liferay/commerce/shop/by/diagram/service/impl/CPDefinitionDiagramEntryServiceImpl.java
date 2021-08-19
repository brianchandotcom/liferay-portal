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

package com.liferay.commerce.shop.by.diagram.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CPDefinitionDiagramEntry"
	},
	service = AopService.class
)
public class CPDefinitionDiagramEntryServiceImpl
	extends CPDefinitionDiagramEntryServiceBaseImpl {

	@Override
	public CPDefinitionDiagramEntry addCPDefinitionDiagramEntry(
			long cpDefinitionId, String cpInstanceUuid, long cProductId,
			boolean diagram, int number, String sku,
			String sequence, ServiceContext serviceContext)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.addCPDefinitionDiagramEntry(
			getUserId(), cpDefinitionId, cpInstanceUuid, cProductId, diagram,
			number, sku, sequence, serviceContext);
	}

	@Override
	public void deleteCPDefinitionDiagramEntry(long cpDefinitionDiagramEntryId)
		throws PortalException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryLocalService.getCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramEntry.getCPDefinitionId(), ActionKeys.UPDATE);

		cpDefinitionDiagramEntryLocalService.deleteCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntry);
	}

	@Override
	public CPDefinitionDiagramEntry fetchCPDefinitionDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.
			fetchCPDefinitionDiagramEntry(cpDefinitionId, sequence);
	}

	@Override
	public List<CPDefinitionDiagramEntry> getCPDefinitionDiagramEntries(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.
			getCPDefinitionDiagramEntries(cpDefinitionId, start, end);
	}

	@Override
	public int getCPDefinitionDiagramEntriesCount(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.
			getCPDefinitionDiagramEntriesCount(cpDefinitionId);
	}

	@Override
	public CPDefinitionDiagramEntry getCPDefinitionDiagramEntry(
			long cpDefinitionDiagramEntryId)
		throws PortalException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryLocalService.getCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramEntry.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramEntry;
	}

	@Override
	public CPDefinitionDiagramEntry getCPDefinitionDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.getCPDefinitionDiagramEntry(
			cpDefinitionId, sequence);
	}

	@Override
	public CPDefinitionDiagramEntry updateCPDefinitionDiagramEntry(
			long cpDefinitionDiagramEntryId, String cpInstanceUuid,
			long cProductId, boolean diagram, int number, String sku,
			String sequence, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryLocalService.getCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramEntry.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.
			updateCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId, cpInstanceUuid, cProductId, diagram,
				number, sku, sequence, serviceContext);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPDefinitionDiagramEntryServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}