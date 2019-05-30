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

package com.liferay.data.engine.taglib.servlet.taglib.util;

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.client.resource.v1_0.DataRecordResource;
import com.liferay.data.engine.spi.renderer.DataLayoutRenderer;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = {})
public class DataLayoutTaglibUtil {

	public static String renderDataLayout(
			long dataLayoutId, long dataRecordId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return _dataLayoutRenderer.render(
			dataLayoutId, _getDataRecordValues(dataRecordId),
			httpServletRequest, httpServletResponse);
	}

	@Reference(unbind = "-")
	protected void setDataLayoutRenderer(
		DataLayoutRenderer dataLayoutRenderer) {

		_dataLayoutRenderer = dataLayoutRenderer;
	}

	private static Map<String, Object> _getDataRecordValues(long dataRecordId)
		throws Exception {

		if (dataRecordId == 0) {
			return Collections.emptyMap();
		}

		DataRecordResource dataRecordResource = new DataRecordResource();

		DataRecord dataRecord = dataRecordResource.getDataRecord(dataRecordId);

		return dataRecord.getDataRecordValues();
	}

	private static DataLayoutRenderer _dataLayoutRenderer;

}