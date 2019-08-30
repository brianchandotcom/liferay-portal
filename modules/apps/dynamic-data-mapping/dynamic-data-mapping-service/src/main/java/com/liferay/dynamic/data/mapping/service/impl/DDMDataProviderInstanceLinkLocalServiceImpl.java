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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;
import com.liferay.dynamic.data.mapping.service.base.DDMDataProviderInstanceLinkLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink",
	service = AopService.class
)
public class DDMDataProviderInstanceLinkLocalServiceImpl
	extends DDMDataProviderInstanceLinkLocalServiceBaseImpl {

	@Override
	public DDMDataProviderInstanceLink addDataProviderInstanceLink(
		long dataProviderInstanceId, long structureId) {

		long dataProviderInstanceLinkId = counterLocalService.increment();

		DDMDataProviderInstanceLink dataProviderInstanceLink =
			ddmDataProviderInstanceLinkPersistence.create(
				dataProviderInstanceLinkId);

		dataProviderInstanceLink.setDataProviderInstanceId(
			dataProviderInstanceId);
		dataProviderInstanceLink.setStructureId(structureId);

		ddmDataProviderInstanceLinkPersistence.update(dataProviderInstanceLink);

		return dataProviderInstanceLink;
	}

	@Override
	public void deleteDataProviderInstanceLink(
		DDMDataProviderInstanceLink dataProviderInstanceLink) {

		ddmDataProviderInstanceLinkPersistence.remove(dataProviderInstanceLink);
	}

	@Override
	public void deleteDataProviderInstanceLink(long dataProviderInstanceLinkId)
		throws PortalException {

		DDMDataProviderInstanceLink dataProviderInstanceLink =
			ddmDataProviderInstanceLinkPersistence.findByPrimaryKey(
				dataProviderInstanceLinkId);

		ddmDataProviderInstanceLinkPersistence.remove(dataProviderInstanceLink);
	}

	@Override
	public void deleteDataProviderInstanceLink(
			long dataProviderInstanceId, long structureId)
		throws PortalException {

		DDMDataProviderInstanceLink dataProviderInstanceLink =
			ddmDataProviderInstanceLinkPersistence.findByD_S(
				dataProviderInstanceId, structureId);

		ddmDataProviderInstanceLinkPersistence.remove(dataProviderInstanceLink);
	}

	@Override
	public void deleteDataProviderInstanceLinks(long structureId) {
		List<DDMDataProviderInstanceLink> dataProviderInstanceLinks =
			ddmDataProviderInstanceLinkPersistence.findByStructureId(
				structureId);

		for (DDMDataProviderInstanceLink dataProviderInstanceLink :
				dataProviderInstanceLinks) {

			deleteDataProviderInstanceLink(dataProviderInstanceLink);
		}
	}

	@Override
	public DDMDataProviderInstanceLink fetchDataProviderInstanceLink(
		long dataProviderInstanceId, long structureId) {

		return ddmDataProviderInstanceLinkPersistence.fetchByD_S(
			dataProviderInstanceId, structureId);
	}

	@Override
	public List<DDMDataProviderInstanceLink> getDataProviderInstanceLinks(
		long structureId) {

		return ddmDataProviderInstanceLinkPersistence.findByStructureId(
			structureId);
	}

}