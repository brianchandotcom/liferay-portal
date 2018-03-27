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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;

/**
 * @author Pei-Jung Lan
 */
public class UADExportProcessUtil {

	public static String getStatusStyle(int status) {
		if (status == BackgroundTaskConstants.STATUS_FAILED) {
			return "danger";
		}
		else if (status == BackgroundTaskConstants.STATUS_IN_PROGRESS) {
			return "warning";
		}
		else if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			return "success";
		}

		return StringPool.BLANK;
	}

}