/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(property = "data.storage.type=json", service = DataStorage.class)
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