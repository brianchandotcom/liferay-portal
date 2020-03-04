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
 * Provides a wrapper for {@link DataRecordAppService}.
 *
 * @author Brian Wing Shun Chan
 * @see DataRecordAppService
 * @generated
 */
public class DataRecordAppServiceWrapper
	implements DataRecordAppService, ServiceWrapper<DataRecordAppService> {

	public DataRecordAppServiceWrapper(
		DataRecordAppService dataRecordAppService) {

		_dataRecordAppService = dataRecordAppService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dataRecordAppService.getOSGiServiceIdentifier();
	}

	@Override
	public DataRecordAppService getWrappedService() {
		return _dataRecordAppService;
	}

	@Override
	public void setWrappedService(DataRecordAppService dataRecordAppService) {
		_dataRecordAppService = dataRecordAppService;
	}

	private DataRecordAppService _dataRecordAppService;

}