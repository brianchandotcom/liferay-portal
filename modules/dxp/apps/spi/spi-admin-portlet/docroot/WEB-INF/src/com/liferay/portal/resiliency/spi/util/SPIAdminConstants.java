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

package com.liferay.portal.resiliency.spi.util;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Michael C. Han
 */
public class SPIAdminConstants {

	public static final String LABEL_STARTED = "started";

	public static final String LABEL_STARTING = "starting";

	public static final String LABEL_STOPPED = "stopped";

	public static final String LABEL_STOPPING = "stopping";

	public static final int STATUS_STARTED = 0;

	public static final int STATUS_STARTING = 1;

	public static final int STATUS_STOPPED = 2;

	public static final int STATUS_STOPPING = 3;

	public static String getStatusCssClass(int status) {
		if (status == STATUS_STOPPED) {
			return "label-important";
		}
		else if ((status == STATUS_STARTING) || (status == STATUS_STOPPING)) {
			return "label-info";
		}
		else if (status == STATUS_STARTED) {
			return "label-success";
		}

		return StringPool.BLANK;
	}

	public static String getStatusLabel(int status) {
		if (status == STATUS_STARTED) {
			return LABEL_STARTED;
		}
		else if (status == STATUS_STARTING) {
			return LABEL_STARTING;
		}
		else if (status == STATUS_STOPPED) {
			return LABEL_STOPPED;
		}
		else if (status == STATUS_STOPPING) {
			return LABEL_STOPPING;
		}
		else {
			return StringPool.BLANK;
		}
	}

}