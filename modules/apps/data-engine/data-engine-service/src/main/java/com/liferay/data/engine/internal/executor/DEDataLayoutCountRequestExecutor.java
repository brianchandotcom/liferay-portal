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
import com.liferay.data.engine.internal.io.DEDataLayoutDeserializerTracker;
import com.liferay.data.engine.io.DEDataLayoutDeserializer;
import com.liferay.data.engine.io.DEDataLayoutDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataLayoutDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.service.DEDataLayoutCountRequest;
import com.liferay.data.engine.service.DEDataLayoutCountResponse;
import com.liferay.data.engine.service.DEDataLayoutGetRequest;
import com.liferay.data.engine.service.DEDataLayoutGetResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Marcelo Mello
 */
public class DEDataLayoutCountRequestExecutor {

	public DEDataLayoutCountRequestExecutor(
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {

		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
	}

	public DEDataLayoutCountResponse execute(
			DEDataLayoutCountRequest deDataLayoutCountRequest)
		throws DEDataLayoutException {

		return DEDataLayoutCountResponse.Builder.of(
			_ddmStructureLayoutLocalService.getDDMStructureLayoutsCount()
		);
	}

	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;

}