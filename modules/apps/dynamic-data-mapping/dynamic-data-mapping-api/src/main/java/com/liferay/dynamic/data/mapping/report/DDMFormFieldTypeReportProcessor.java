/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.report;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
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

	public default void updateData(
		String ddmFormInstanceReportEvent, JSONObject jsonObject, String key) {

		if (ddmFormInstanceReportEvent.equals(
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

			jsonObject.put(key, jsonObject.getInt(key, 0) + 1);
		}
		else if (ddmFormInstanceReportEvent.equals(
					DDMFormInstanceReportConstants.
						EVENT_DELETE_RECORD_VERSION)) {

			int value = jsonObject.getInt(key, 0) - 1;

			if (value > 0) {
				jsonObject.put(key, value);
			}
			else {
				jsonObject.remove(key);
			}
		}
	}

}