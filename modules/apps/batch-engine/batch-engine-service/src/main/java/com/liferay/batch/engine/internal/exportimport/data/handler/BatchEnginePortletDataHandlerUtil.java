/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.text.Format;

import java.util.Map;

/**
 * @author Vendel Toreki
 * @author Alejandro Tardín
 * @author Petteri Karttunen
 */
public class BatchEnginePortletDataHandlerUtil {

	public static Map<String, Serializable> buildParameters(
		PortletDataContext portletDataContext) {

		return HashMapBuilder.<String, Serializable>put(
			"batchNestedFields",
			() -> {
				if (MapUtil.getBoolean(
						portletDataContext.getParameterMap(),
						PortletDataHandlerKeys.PERMISSIONS)) {

					return "permissions";
				}

				return null;
			}
		).put(
			"filter",
			() -> {
				if ((portletDataContext.getEndDate() == null) &&
					(portletDataContext.getStartDate() == null)) {

					return null;
				}

				StringBundler sb = new StringBundler(5);

				if (portletDataContext.getEndDate() != null) {
					sb.append("dateModified le ");
					sb.append(_format.format(portletDataContext.getEndDate()));
				}

				if (portletDataContext.getStartDate() != null) {
					if (sb.length() > 0) {
						sb.append(" and ");
					}

					sb.append("dateModified ge ");
					sb.append(
						_format.format(portletDataContext.getStartDate()));
				}

				return sb.toString();
			}
		).build();
	}

	private static final Format _format =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

}