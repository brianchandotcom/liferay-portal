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

import com.liferay.commerce.shop.by.diagram.exception.DuplicateCPDefinitionDiagramEntryException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry",
	service = AopService.class
)
public class CPDefinitionDiagramEntryLocalServiceImpl
	extends CPDefinitionDiagramEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionDiagramEntry addCPDefinitionDiagramEntry(
			long userId, long cpDefinitionId, String cpInstanceUuid,
			long cProductId, boolean diagram, int quantity, String sequence,
			String sku, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(null, cpDefinitionId, sequence);

		long cpDefinitionDiagramEntryId = counterLocalService.increment();

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryPersistence.create(
				cpDefinitionDiagramEntryId);

		cpDefinitionDiagramEntry.setCompanyId(user.getCompanyId());
		cpDefinitionDiagramEntry.setUserId(user.getUserId());
		cpDefinitionDiagramEntry.setUserName(user.getFullName());
		cpDefinitionDiagramEntry.setCPDefinitionId(cpDefinitionId);
		cpDefinitionDiagramEntry.setCPInstanceUuid(cpInstanceUuid);
		cpDefinitionDiagramEntry.setCProductId(cProductId);
		cpDefinitionDiagramEntry.setDiagram(diagram);
		cpDefinitionDiagramEntry.setQuantity(quantity);
		cpDefinitionDiagramEntry.setSequence(sequence);
		cpDefinitionDiagramEntry.setSku(sku);
		cpDefinitionDiagramEntry.setExpandoBridgeAttributes(serviceContext);

		return cpDefinitionDiagramEntryPersistence.update(
			cpDefinitionDiagramEntry);
	}

	@Override
	public void deleteCPDefinitionDiagramEntries(long cpDefinitionId) {
		List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			cpDefinitionDiagramEntryPersistence.findByCPDefinitionId(
				cpDefinitionId);

		for (CPDefinitionDiagramEntry cpDefinitionDiagramEntry :
				cpDefinitionDiagramEntries) {

			cpDefinitionDiagramEntryLocalService.deleteCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntry);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionDiagramEntry deleteCPDefinitionDiagramEntry(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		cpDefinitionDiagramEntry = cpDefinitionDiagramEntryPersistence.remove(
			cpDefinitionDiagramEntry);

		expandoRowLocalService.deleteRows(
			cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId());

		return cpDefinitionDiagramEntry;
	}

	@Override
	public CPDefinitionDiagramEntry fetchCPDefinitionDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		return cpDefinitionDiagramEntryPersistence.fetchByCPDI_S(
			cpDefinitionId, sequence);
	}

	@Override
	public List<CPDefinitionDiagramEntry> getCPDefinitionDiagramEntries(
		long cpDefinitionId, int start, int end) {

		return cpDefinitionDiagramEntryPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end);
	}

	@Override
	public int getCPDefinitionDiagramEntriesCount(long cpDefinitionId) {
		return cpDefinitionDiagramEntryPersistence.countByCPDefinitionId(
			cpDefinitionId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionDiagramEntry getCPDefinitionDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		return cpDefinitionDiagramEntryPersistence.findByCPDI_S(
			cpDefinitionId, sequence);
	}

	@Override
	public CPDefinitionDiagramEntry updateCPDefinitionDiagramEntry(
			long cpDefinitionDiagramEntryId, String cpInstanceUuid,
			long cProductId, boolean diagram, int quantity, String sequence,
			String sku, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryLocalService.getCPDefinitionDiagramEntry(
				cpDefinitionDiagramEntryId);

		validate(
			cpDefinitionDiagramEntry,
			cpDefinitionDiagramEntry.getCPDefinitionId(), sequence);

		cpDefinitionDiagramEntry.setCPInstanceUuid(cpInstanceUuid);
		cpDefinitionDiagramEntry.setCProductId(cProductId);
		cpDefinitionDiagramEntry.setDiagram(diagram);
		cpDefinitionDiagramEntry.setSequence(sequence);
		cpDefinitionDiagramEntry.setQuantity(quantity);
		cpDefinitionDiagramEntry.setSku(sku);
		cpDefinitionDiagramEntry.setExpandoBridgeAttributes(serviceContext);

		return cpDefinitionDiagramEntryPersistence.update(
			cpDefinitionDiagramEntry);
	}

	protected void validate(
			CPDefinitionDiagramEntry oldCPDefinitionDiagramEntry,
			long cpDefinitionId, String sequence)
		throws PortalException {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			cpDefinitionDiagramEntryPersistence.fetchByCPDI_S(
				cpDefinitionId, sequence);

		if (!Objects.equals(
				oldCPDefinitionDiagramEntry, cpDefinitionDiagramEntry)) {

			throw new DuplicateCPDefinitionDiagramEntryException();
		}
	}

}