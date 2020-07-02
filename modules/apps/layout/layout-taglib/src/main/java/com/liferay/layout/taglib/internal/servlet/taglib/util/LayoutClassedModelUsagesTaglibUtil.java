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

package com.liferay.layout.taglib.internal.servlet.taglib.util;

import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.LayoutClassedModelUsageRecorder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class LayoutClassedModelUsagesTaglibUtil {

	public static void recordLayoutClassedModelUsage(
		String className, long classPK) {

		try {
			Map<String, LayoutClassedModelUsageRecorder>
				layoutClassedModelUsageRecorders =
					ServletContextUtil.getLayoutClassedModelUsageRecorders();

			LayoutClassedModelUsageRecorder layoutClassedModelUsageRecorder =
				layoutClassedModelUsageRecorders.get(className);

			if (layoutClassedModelUsageRecorder != null) {
				layoutClassedModelUsageRecorder.record(
					PortalUtil.getClassNameId(className), classPK);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to check layout classed model usages for ",
						"class name ", className, " and class PK ", classPK),
					portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutClassedModelUsagesTaglibUtil.class);

}