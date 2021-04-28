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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionDiagramEntryLocalServiceImpl
	extends CPDefinitionDiagramEntryLocalServiceBaseImpl {

	@Override
	public CPDefinitionDiagramEntry addCPDefinitionDiagramEntry(
			long userId, long cpDefinitionDiagramSettingsId,
			String cpInstanceUuid, long cProductId, int number, int quantity,
			double positionX, double positionY, double radius,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long cpDefinitionDiagramEntryId = counterLocalService.increment();

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryPersistence.create(
				cpDefinitionDiagramEntryId);

		cpDefinitionDiagramEntry.setCompanyId(user.getCompanyId());
		cpDefinitionDiagramEntry.setUserId(user.getUserId());
		cpDefinitionDiagramEntry.setUserName(user.getFullName());
		cpDefinitionDiagramEntry.setCPDefinitionDiagramSettingsId(
			cpDefinitionDiagramSettingsId);
		cpDefinitionDiagramEntry.setCPInstanceUuid(cpInstanceUuid);
		cpDefinitionDiagramEntry.setCProductId(cProductId);
		cpDefinitionDiagramEntry.setNumber(number);
		cpDefinitionDiagramEntry.setQuantity(quantity);
		cpDefinitionDiagramEntry.setPositionX(positionX);
		cpDefinitionDiagramEntry.setPositionY(positionY);
		cpDefinitionDiagramEntry.setRadius(radius);
		cpDefinitionDiagramEntry.setExpandoBridgeAttributes(serviceContext);

		return cpDefinitionDiagramEntryPersistence.update(
			cpDefinitionDiagramEntry);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionDiagramEntry deleteCPDefinitionDiagramEntry(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		// Expando

		expandoRowLocalService.deleteRows(
			cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId());

		// Commerce product definition diagram entry

		return cpDefinitionDiagramEntryPersistence.remove(
			cpDefinitionDiagramEntry);
	}

	@Override
	public List<CPDefinitionDiagramEntry> getCPDefinitionDiagramEntries(
		long cpDefinitionDiagramSettingsId, int start, int end) {

		return cpDefinitionDiagramEntryPersistence.
			findByCPDefinitionDiagramSettingsId(
				cpDefinitionDiagramSettingsId, start, end);
	}

	@Override
	public int getCPDefinitionDiagramEntriesCount(
		long cpDefinitionDiagramSettingsId) {

		return cpDefinitionDiagramEntryPersistence.
			countByCPDefinitionDiagramSettingsId(cpDefinitionDiagramSettingsId);
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

		cpDefinitionDiagramEntry.setCPInstanceUuid(cpInstanceUuid);
		cpDefinitionDiagramEntry.setCProductId(cProductId);
		cpDefinitionDiagramEntry.setNumber(number);
		cpDefinitionDiagramEntry.setQuantity(quantity);
		cpDefinitionDiagramEntry.setPositionX(positionX);
		cpDefinitionDiagramEntry.setPositionY(positionY);
		cpDefinitionDiagramEntry.setRadius(radius);
		cpDefinitionDiagramEntry.setExpandoBridgeAttributes(serviceContext);

		return cpDefinitionDiagramEntryPersistence.update(
			cpDefinitionDiagramEntry);
	}

}