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

package com.liferay.depot.service.impl;

import com.liferay.depot.model.DepotAppCustomization;
import com.liferay.depot.service.base.DepotAppCustomizationLocalServiceBaseImpl;
import com.liferay.depot.service.persistence.DepotAppCustomizationPersistence;
import com.liferay.portal.aop.AopService;

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
	public DepotAppCustomization addDepotAppCustomization(
		long depotEntryId, String portletId, boolean enabled) {

		DepotAppCustomization depotAppCustomization =
			_depotAppCustomizationPersistence.fetchByD_PI(
				depotEntryId, portletId);

		if (depotAppCustomization != null) {
			return depotAppCustomization;
		}

		depotAppCustomization = _depotAppCustomizationPersistence.create(
			counterLocalService.increment());

		depotAppCustomization.setDepotEntryId(depotEntryId);
		depotAppCustomization.setEnabled(enabled);
		depotAppCustomization.setPortletId(portletId);

		return _depotAppCustomizationPersistence.update(depotAppCustomization);
	}

	@Override
	public DepotAppCustomization fetchDepotAppCustomization(
		long depotEntryId, String portletId) {

		return _depotAppCustomizationPersistence.fetchByD_PI(
			depotEntryId, portletId);
	}

	@Reference
	private DepotAppCustomizationPersistence _depotAppCustomizationPersistence;

}