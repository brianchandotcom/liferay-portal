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

package com.liferay.marketplace.app.manager.web.util;

import com.liferay.marketplace.app.manager.web.constants.BundleConstants;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * @author Ryan Park
 */
public class ModuleGroupDisplayFactoryUtil {

	public static List<ModuleGroupDisplay> getModuleGroupDisplays(
		List<Bundle> bundles) {

		Map<String, ModuleGroupDisplay> moduleGroupDisplaysMap =
			new HashMap<>();

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders();

			String moduleGroupTitle = headers.get(
				BundleConstants.LIFERAY_RELENG_MODULE_GROUP_TITLE);

			if (moduleGroupTitle == null) {
				moduleGroupTitle =
					ModuleGroupDisplay.MODULE_GROUP_TITLE_UNCATEGORIZED;
			}

			ModuleGroupDisplay moduleGroupDisplay = moduleGroupDisplaysMap.get(
				moduleGroupTitle);

			if (moduleGroupDisplay == null) {
				String moduleGroupDescription = headers.get(
					BundleConstants.LIFERAY_RELENG_MODULE_GROUP_DESCRIPTION);
				Version moduleGroupVersion = bundle.getVersion();

				moduleGroupDisplay = new SimpleModuleGroupDisplay(
					moduleGroupTitle, moduleGroupDescription,
					moduleGroupVersion);

				moduleGroupDisplaysMap.put(
					moduleGroupTitle, moduleGroupDisplay);
			}

			moduleGroupDisplay.addBundle(bundle);
		}

		return ListUtil.fromMapValues(moduleGroupDisplaysMap);
	}

	public static List<ModuleGroupDisplay> getModuleGroupDisplays(
		List<Bundle> bundles, int state) {

		List<ModuleGroupDisplay> moduleGroupDisplays = getModuleGroupDisplays(
			bundles);

		filterModuleGroupDisplays(moduleGroupDisplays, state);

		return moduleGroupDisplays;
	}

	protected static void filterModuleGroupDisplays(
		List<ModuleGroupDisplay> moduleGroupDisplays, int state) {

		Iterator<ModuleGroupDisplay> iterator = moduleGroupDisplays.iterator();

		while (iterator.hasNext()) {
			ModuleGroupDisplay moduleGroupDisplay = iterator.next();

			if ((state > 0) && (moduleGroupDisplay.getState() != state)) {
				iterator.remove();
			}
		}
	}

}