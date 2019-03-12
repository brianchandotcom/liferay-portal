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
import com.liferay.data.engine.service.DEDataLayoutListRequest;
import com.liferay.data.engine.service.DEDataLayoutListResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public class DEDataLayoutListRequestExecutor {

	public DEDataLayoutListRequestExecutor(
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureVersionLocalService ddmStructureVersionLocalService,
		DEDataLayoutDeserializerTracker deDataLayoutDeserializerTracker) {

		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
		_deDataLayoutDeserializerTracker = deDataLayoutDeserializerTracker;
	}

	public DEDataLayoutListResponse execute(
			DEDataLayoutListRequest deDataLayoutListRequest)
		throws DEDataLayoutException {

		List<DDMStructureLayout> ddmStructureLayouts;

		try {
			ddmStructureLayouts =
				_ddmStructureLayoutLocalService.getStructureLayouts(
					deDataLayoutListRequest.getGroupId(),
					deDataLayoutListRequest.getStart(),
					deDataLayoutListRequest.getEnd());

			List<DEDataLayout> deDataLayouts = new ArrayList<>();

			for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
				deDataLayouts.add(_map(ddmStructureLayout));
			}

			return DEDataLayoutListResponse.Builder.of(deDataLayouts);
		}
		catch (PortalException pe) {
			throw new DEDataLayoutException(pe);
		}
	}

	private Long _getDDMStructureId(DDMStructureLayout ddmStructureLayout)
		throws PortalException {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		return ddmStructure.getStructureId();
	}

	private DEDataLayout _map(DDMStructureLayout ddmStructureLayout)
		throws DEDataLayoutException {

		DEDataLayoutDeserializer deDataLayoutDeserializer =
			_deDataLayoutDeserializerTracker.getDEDataLayoutSerializer("json");

		DEDataLayout deDataLayout = null;

		try {
			DEDataLayoutDeserializerApplyResponse
				deDataLayoutDeserializerApplyResponse =
					deDataLayoutDeserializer.apply(
						DEDataLayoutDeserializerApplyRequest.Builder.of(
							ddmStructureLayout.getDefinition()));

			deDataLayout =
				deDataLayoutDeserializerApplyResponse.getDEDataLayout();

			deDataLayout.setCreateDate(ddmStructureLayout.getCreateDate());
			deDataLayout.setDEDataDefinitionId(
				_getDDMStructureId(ddmStructureLayout));
			deDataLayout.setDEDataLayoutId(
				ddmStructureLayout.getStructureLayoutId());
			deDataLayout.setDescription(ddmStructureLayout.getDescriptionMap());
			deDataLayout.setModifiedDate(ddmStructureLayout.getModifiedDate());
			deDataLayout.setName(ddmStructureLayout.getNameMap());
			deDataLayout.setUserId(ddmStructureLayout.getUserId());
		}
		catch (PortalException pe) {
			throw new DEDataLayoutException(pe);
		}

		return deDataLayout;
	}

	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private final DEDataLayoutDeserializerTracker
		_deDataLayoutDeserializerTracker;

}