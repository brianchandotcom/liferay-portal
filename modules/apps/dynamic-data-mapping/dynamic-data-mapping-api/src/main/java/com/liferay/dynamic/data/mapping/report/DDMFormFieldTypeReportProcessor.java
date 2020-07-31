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

package com.liferay.dynamic.data.mapping.report;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Marcos Martins
 */
public interface DDMFormFieldTypeReportProcessor {

	public default JSONObject process(
			DDMFormFieldValue ddmFormFieldValue, JSONObject fieldJSONObject,
			long formInstanceRecordId, String ddmFormInstanceReportEvent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #process(DDMFormFieldValue, JSONObject, long, String)}
	 */
	@Deprecated
	public default JSONObject process(
			DDMFormFieldValue ddmFormFieldValue,
			JSONObject ddmFormInstanceReportDataJSONObject,
			String ddmFormInstanceReportEvent)
		throws Exception {

		return process(ddmFormFieldValue, null, 0, ddmFormInstanceReportEvent);
	}

}