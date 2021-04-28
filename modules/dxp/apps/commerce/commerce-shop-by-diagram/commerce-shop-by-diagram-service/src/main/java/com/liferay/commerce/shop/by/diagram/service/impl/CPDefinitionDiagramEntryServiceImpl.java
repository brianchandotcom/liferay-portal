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
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionDiagramEntryServiceImpl
	extends CPDefinitionDiagramEntryServiceBaseImpl {

	@Override
	public CPDefinitionDiagramEntry addCPDefinitionDiagramEntry(
			long userId, long cpDefinitionDiagramSettingsId,
			String cpInstanceUuid, long cProductId, int number, int quantity,
			double positionX, double positionY, double radius,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramSettingsLocalService.
				getCPDefinitionDiagramSettings(cpDefinitionDiagramSettingsId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSettings.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.addCPDefinitionDiagramEntry(
			userId,
			cpDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId(),
			cpInstanceUuid, cProductId, number, quantity, positionX, positionY,
			radius, serviceContext);
	}

	@Override
	public void deleteCPDefinitionDiagramEntry(long cpDefinitionDiagramEntryId)
		throws PortalException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryLocalService.getCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId);

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramEntry.getCPDefinitionDiagramSettings();

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSettings.getCPDefinitionId(), ActionKeys.UPDATE);

		cpDefinitionDiagramEntryLocalService.deleteCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntry);
	}

	@Override
	public List<CPDefinitionDiagramEntry> getCPDefinitionDiagramEntries(
			long cpDefinitionDiagramSettingsId, int start, int end)
		throws PortalException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramSettingsLocalService.
				getCPDefinitionDiagramSettings(cpDefinitionDiagramSettingsId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSettings.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.
			getCPDefinitionDiagramEntries(
				cpDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId(),
				start, end);
	}

	@Override
	public int getCPDefinitionDiagramEntriesCount(
			long cpDefinitionDiagramSettingsId)
		throws PortalException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramSettingsLocalService.
				getCPDefinitionDiagramSettings(cpDefinitionDiagramSettingsId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSettings.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.
			getCPDefinitionDiagramEntriesCount(
				cpDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId());
	}

	@Override
	public CPDefinitionDiagramEntry getCPDefinitionDiagramEntry(
			long cpDefinitionDiagramEntryId)
		throws PortalException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryLocalService.getCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId);

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramEntry.getCPDefinitionDiagramSettings();

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSettings.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramEntry;
	}

	@Override
	public CPDefinitionDiagramEntry updateCPDefinitionDiagramEntry(
			long cpDefinitionDiagramEntryId, String cpInstanceUuid,
			long cProductId, int number, int quantity, double positionX,
			double positionY, double radius, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryLocalService.getCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId);

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramEntry.getCPDefinitionDiagramSettings();

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSettings.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramEntryLocalService.
			updateCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId, cpInstanceUuid, cProductId, number,
				quantity, positionX, positionY, radius, serviceContext);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPDefinitionDiagramEntryServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}