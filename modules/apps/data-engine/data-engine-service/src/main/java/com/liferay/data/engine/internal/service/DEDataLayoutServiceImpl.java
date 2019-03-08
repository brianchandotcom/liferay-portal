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

package com.liferay.data.engine.internal.service;

import com.liferay.data.engine.exception.DEDataLayoutException;
import com.liferay.data.engine.internal.executor.DEDataLayoutDeleteRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutGetRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutListRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutSaveRequestExecutor;
import com.liferay.data.engine.internal.io.DEDataLayoutDeserializerTracker;
import com.liferay.data.engine.internal.io.DEDataLayoutSerializerTracker;
import com.liferay.data.engine.service.DEDataLayoutDeleteRequest;
import com.liferay.data.engine.service.DEDataLayoutDeleteResponse;
import com.liferay.data.engine.service.DEDataLayoutGetRequest;
import com.liferay.data.engine.service.DEDataLayoutGetResponse;
import com.liferay.data.engine.service.DEDataLayoutListRequest;
import com.liferay.data.engine.service.DEDataLayoutListResponse;
import com.liferay.data.engine.service.DEDataLayoutSaveRequest;
import com.liferay.data.engine.service.DEDataLayoutSaveResponse;
import com.liferay.data.engine.service.DEDataLayoutService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DEDataLayoutService.class)
public class DEDataLayoutServiceImpl implements DEDataLayoutService {

	@Override
	public DEDataLayoutDeleteResponse execute(
			DEDataLayoutDeleteRequest deDataLayoutDeleteRequest)
		throws DEDataLayoutException {

		DEDataLayoutDeleteRequestExecutor deDataLayoutDeleteRequestExecutor =
			getDEDataLayoutDeleteRequestExecutor();

		return deDataLayoutDeleteRequestExecutor.execute(
			deDataLayoutDeleteRequest);
	}

	@Override
	public DEDataLayoutGetResponse execute(
			DEDataLayoutGetRequest deDataLayoutGetRequest)
		throws DEDataLayoutException {

		DEDataLayoutGetRequestExecutor deDataLayoutGetRequestExecutor =
			getDEDataLayoutGetRequestExecutor();

		return deDataLayoutGetRequestExecutor.execute(deDataLayoutGetRequest);
	}

	@Override
	public DEDataLayoutListResponse execute(
			DEDataLayoutListRequest deDataLayoutListRequest)
		throws DEDataLayoutException {

		DEDataLayoutListRequestExecutor deDataLayoutListRequestExecutor =
			getDEDataLayoutListRequestExecutor();

		return deDataLayoutListRequestExecutor.execute(deDataLayoutListRequest);
	}

	@Override
	public DEDataLayoutSaveResponse execute(
			DEDataLayoutSaveRequest deDataLayoutSaveRequest)
		throws DEDataLayoutException {

		DEDataLayoutSaveRequestExecutor deDataLayoutSaveRequestExecutor =
			getDEDataLayoutSaveRequestExecutor();

		return deDataLayoutSaveRequestExecutor.execute(deDataLayoutSaveRequest);
	}

	public DEDataLayoutDeleteRequestExecutor
		getDEDataLayoutDeleteRequestExecutor() {

		if (_deDataLayoutDeleteRequestExecutor == null) {
			_deDataLayoutDeleteRequestExecutor =
				new DEDataLayoutDeleteRequestExecutor(
					_ddmStructureLayoutLocalService,
					_ddmStructureVersionLocalService);
		}

		return _deDataLayoutDeleteRequestExecutor;
	}

	public DEDataLayoutGetRequestExecutor getDEDataLayoutGetRequestExecutor() {
		if (_deDataLayoutGetRequestExecutor == null) {
			_deDataLayoutGetRequestExecutor =
				new DEDataLayoutGetRequestExecutor(
					_ddmStructureLayoutLocalService,
					_ddmStructureVersionLocalService,
					_deDataLayoutDeserializerTracker);
		}

		return _deDataLayoutGetRequestExecutor;
	}

	public DEDataLayoutListRequestExecutor
		getDEDataLayoutListRequestExecutor() {

		if (_deDataLayoutListRequestExecutor == null) {
			_deDataLayoutListRequestExecutor =
				new DEDataLayoutListRequestExecutor(
					_ddmStructureLayoutLocalService,
					_ddmStructureVersionLocalService,
					_deDataLayoutDeserializerTracker);
		}

		return _deDataLayoutListRequestExecutor;
	}

	public DEDataLayoutSaveRequestExecutor
		getDEDataLayoutSaveRequestExecutor() {

		if (_deDataLayoutSaveRequestExecutor == null) {
			_deDataLayoutSaveRequestExecutor =
				new DEDataLayoutSaveRequestExecutor(
					_ddmStructureLayoutLocalService,
					_ddmStructureVersionLocalService, _ddmStructureLocalService,
					_deDataLayoutSerializerTracker);
		}

		return _deDataLayoutSaveRequestExecutor;
	}

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DEDataLayoutDeserializerTracker _deDataLayoutDeserializerTracker;

	private DEDataLayoutDeleteRequestExecutor _deDataLayoutDeleteRequestExecutor;
	private DEDataLayoutGetRequestExecutor _deDataLayoutGetRequestExecutor;
	private DEDataLayoutListRequestExecutor _deDataLayoutListRequestExecutor;
	private DEDataLayoutSaveRequestExecutor _deDataLayoutSaveRequestExecutor;

	@Reference
	private DEDataLayoutSerializerTracker _deDataLayoutSerializerTracker;

}