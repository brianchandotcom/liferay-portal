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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Rubén Pulido
 */
public class PortalPreferencesUtil {

	public static List<String> getSortedPortalPreferencesValues(
		PortalPreferences portalPreferences, String namespace, String key) {

		return ListUtil.fromArray(
			portalPreferences.getValues(namespace, key, new String[0]));
	}

	public static void updateSortedPortalPreferencesValues(
		PortalPreferences portalPreferences, String namespace, String key,
		String[] values) {

		portalPreferences.setValues(namespace, key, values);
	}

}