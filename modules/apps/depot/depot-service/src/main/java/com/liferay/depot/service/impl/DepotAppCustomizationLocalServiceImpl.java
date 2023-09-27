/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.service.impl;

import com.liferay.depot.model.DepotAppCustomization;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.base.DepotAppCustomizationLocalServiceBaseImpl;
import com.liferay.depot.service.persistence.DepotEntryPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "model.class.name=com.liferay.depot.model.DepotAppCustomization",
	service = AopService.class
)
public class DepotAppCustomizationLocalServiceImpl
	extends DepotAppCustomizationLocalServiceBaseImpl {

	@Override
	public DepotAppCustomization fetchDepotAppCustomization(
		long depotEntryId, String portletId) {

		return depotAppCustomizationPersistence.fetchByD_P(
			depotEntryId, portletId);
	}

	@Override
	public List<DepotAppCustomization> getDepotAppCustomizations(
		long depotEntryId) {

		return depotAppCustomizationPersistence.findByDepotEntryId(
			depotEntryId);
	}

	@Override
	public int getDepotAppCustomizationsCount(
		long depotEntryId, boolean enabled) {

		return depotAppCustomizationPersistence.countByD_E(
			depotEntryId, enabled);
	}

	@Override
	public DepotAppCustomization updateDepotAppCustomization(
			long depotEntryId, boolean enabled, String portletId)
		throws PortalException {

		DepotAppCustomization depotAppCustomization =
			depotAppCustomizationPersistence.fetchByD_P(
				depotEntryId, portletId);

		if (depotAppCustomization == null) {
			return _addDepotAppCustomization(depotEntryId, enabled, portletId);
		}

		depotAppCustomization.setEnabled(enabled);

		return depotAppCustomizationPersistence.update(depotAppCustomization);
	}

	private DepotAppCustomization _addDepotAppCustomization(
			long depotEntryId, boolean enabled, String portletId)
		throws PortalException {

		DepotAppCustomization depotAppCustomization =
			depotAppCustomizationPersistence.create(
				counterLocalService.increment());

		DepotEntry depotEntry = _depotEntryPersistence.findByPrimaryKey(
			depotEntryId);

		depotAppCustomization.setCompanyId(depotEntry.getCompanyId());

		depotAppCustomization.setDepotEntryId(depotEntryId);
		depotAppCustomization.setEnabled(enabled);
		depotAppCustomization.setPortletId(portletId);

		return depotAppCustomizationPersistence.update(depotAppCustomization);
	}

	@Reference
	private DepotEntryPersistence _depotEntryPersistence;

}