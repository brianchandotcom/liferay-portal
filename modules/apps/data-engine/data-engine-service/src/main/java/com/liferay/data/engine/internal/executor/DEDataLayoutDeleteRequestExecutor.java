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

package com.liferay.data.engine.internal.executor;

import com.liferay.data.engine.exception.DEDataLayoutException;
import com.liferay.data.engine.service.DEDataLayoutDeleteRequest;
import com.liferay.data.engine.service.DEDataLayoutDeleteResponse;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marcelo Mello
 */
public class DEDataLayoutDeleteRequestExecutor {

	public DEDataLayoutDeleteRequestExecutor(
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {

		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
	}

	public DEDataLayoutDeleteResponse execute(
			DEDataLayoutDeleteRequest deDataLayoutDeleteRequest)
		throws DEDataLayoutException {

		Long deDataLayoutId = deDataLayoutDeleteRequest.getDEDataLayoutId();

		try {
			_ddmStructureLayoutLocalService.deleteStructureLayout(
				deDataLayoutId);
		}
		catch (PortalException pe) {
			throw new DEDataLayoutException(pe);
		}

		return DEDataLayoutDeleteResponse.Builder.of(deDataLayoutId);
	}

	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;

}