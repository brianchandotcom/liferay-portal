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

package com.liferay.data.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DataDefinitionAppLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DataDefinitionAppLocalService
 * @generated
 */
public class DataDefinitionAppLocalServiceWrapper
	implements DataDefinitionAppLocalService,
			   ServiceWrapper<DataDefinitionAppLocalService> {

	public DataDefinitionAppLocalServiceWrapper(
		DataDefinitionAppLocalService dataDefinitionAppLocalService) {

		_dataDefinitionAppLocalService = dataDefinitionAppLocalService;
	}

	@Override
	public com.liferay.data.engine.model.DEDataDefinition addDataDefinition(
			java.util.Map<java.util.Locale, String> name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dataDefinitionAppLocalService.addDataDefinition(name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dataDefinitionAppLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public DataDefinitionAppLocalService getWrappedService() {
		return _dataDefinitionAppLocalService;
	}

	@Override
	public void setWrappedService(
		DataDefinitionAppLocalService dataDefinitionAppLocalService) {

		_dataDefinitionAppLocalService = dataDefinitionAppLocalService;
	}

	private DataDefinitionAppLocalService _dataDefinitionAppLocalService;

}