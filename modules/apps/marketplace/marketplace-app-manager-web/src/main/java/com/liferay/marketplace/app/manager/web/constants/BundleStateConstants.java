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

package com.liferay.marketplace.app.manager.web.constants;

import com.liferay.portal.kernel.util.StringPool;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class BundleStateConstants {

	public static final int ACTIVE = Bundle.ACTIVE;

	public static final String ACTIVE_LABEL = "active";

	public static final int INSTALLED = Bundle.INSTALLED;

	public static final String INSTALLED_LABEL = "installed";

	// Ordered from fully operational to unoperational

	public static final int[] INSTALLED_STATES = {
		BundleStateConstants.ACTIVE, BundleStateConstants.RESOLVED,
		BundleStateConstants.INSTALLED
	};

	public static final int RESOLVED = Bundle.RESOLVED;

	public static final String RESOLVED_LABEL = "resolved";

	public static final int UNINSTALLED = Bundle.UNINSTALLED;

	public static final String UNINSTALLED_LABEL = "uninstalled";

	public static String getLabel(int state) {
		if (state == ACTIVE) {
			return ACTIVE_LABEL;
		}
		else if (state == INSTALLED) {
			return INSTALLED_LABEL;
		}
		else if (state == RESOLVED) {
			return RESOLVED_LABEL;
		}
		else if (state == UNINSTALLED) {
			return UNINSTALLED_LABEL;
		}
		else {
			return StringPool.BLANK;
		}
	}

}