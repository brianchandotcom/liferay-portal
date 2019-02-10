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
import com.liferay.data.engine.exception.DEDataLayoutSerializerException;
import com.liferay.data.engine.internal.io.DEDataLayoutSerializerTracker;
import com.liferay.data.engine.io.DEDataLayoutSerializer;
import com.liferay.data.engine.io.DEDataLayoutSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataLayoutSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.service.DEDataLayoutSaveRequest;
import com.liferay.data.engine.service.DEDataLayoutSaveResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import java.util.Locale;
import java.util.Map;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutSaveRequestExecutor {

	public DEDataLayoutSaveRequestExecutor(
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureVersionLocalService ddmStructureVersionLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DEDataLayoutSerializerTracker deDataLayoutSerializerTracker) {

		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_deDataLayoutSerializerTracker = deDataLayoutSerializerTracker;
	}

	public DEDataLayoutSaveResponse execute(
			DEDataLayoutSaveRequest deDataLayoutSaveRequest)
		throws DEDataLayoutException {

		DDMStructureLayout ddmStructureLayout = null;

		try {
			DEDataLayout deDataLayout =
				deDataLayoutSaveRequest.getDEDataLayout();

			Map<Locale, String> name = deDataLayout.getName();

			if (name.isEmpty()) {
				throw new DEDataLayoutException.InvalidName();
			}

			if (deDataLayout.getDEDataLayoutId() == null) {
				ddmStructureLayout =
					_ddmStructureLayoutLocalService.addStructureLayout(
						deDataLayoutSaveRequest.getUserId(),
						deDataLayoutSaveRequest.getGroupId(),
						_getDDMStructureVersionId(
							deDataLayout.getDEDataDefinitionId()),
						name, deDataLayout.getDescription(),
						_serializeDEDataLayout(deDataLayout),
						ServiceContextThreadLocal.getServiceContext());
			}
			else {
				ddmStructureLayout =
					_ddmStructureLayoutLocalService.updateStructureLayout(
						deDataLayout.getDEDataLayoutId(),
						_getDDMStructureVersionId(
							deDataLayout.getDEDataDefinitionId()),
						name, deDataLayout.getDescription(),
						_serializeDEDataLayout(deDataLayout),
						ServiceContextThreadLocal.getServiceContext());
			}
		}
		catch (PortalException pe) {
			throw new DEDataLayoutException(pe);
		}
		catch (Exception e) {
			throw new DEDataLayoutException(e);
		}

		return DEDataLayoutSaveResponse.Builder.of(
			ddmStructureLayout.getStructureLayoutId());
	}

	private Long _getDDMStructureVersionId(Long deDataDefinitionId)
		throws PortalException {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private String _serializeDEDataLayout(DEDataLayout deDataLayout)
		throws DEDataLayoutSerializerException {

		DEDataLayoutSerializer deDataLayoutSerializer =
			_deDataLayoutSerializerTracker.getDEDataLayoutSerializer("json");

		DEDataLayoutSerializerApplyResponse
			deDataLayoutSerializerApplyResponse = deDataLayoutSerializer.apply(
				DEDataLayoutSerializerApplyRequest.Builder.of(deDataLayout));

		return deDataLayoutSerializerApplyResponse.getContent();
	}

	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private final DEDataLayoutSerializerTracker _deDataLayoutSerializerTracker;

}