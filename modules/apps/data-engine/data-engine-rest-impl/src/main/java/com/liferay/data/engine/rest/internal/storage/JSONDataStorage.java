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

package com.liferay.data.engine.rest.internal.storage;

import com.liferay.data.engine.storage.DataStorage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.storage.type=json",
	service = DataStorage.class
)
public class JSONDataStorage implements DataStorage {

	@Override
	public long delete(long dataStorageId) throws Exception {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON data storage is deprecated, using default data storage");
		}

		return _dataStorage.delete(dataStorageId);
	}

	@Override
	public Map<String, Object> get(long dataDefinitionId, long dataStorageId)
		throws Exception {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON data storage is deprecated, using default data storage");
		}

		return _dataStorage.get(dataDefinitionId, dataStorageId);
	}

	@Override
	public long save(
			long dataRecordCollectionId, Map<String, Object> dataRecordValues,
			long siteId)
		throws Exception {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON data storage is deprecated, using default data storage");
		}

		return _dataStorage.save(
			dataRecordCollectionId, dataRecordValues, siteId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONDataStorage.class);

	@Reference(target = "(data.storage.type=default)")
	private DataStorage _dataStorage;

}