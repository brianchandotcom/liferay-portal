/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.util;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.internal.instance.lifecycle.DepotRolesPortalInstanceLifecycleListener;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Shuyang Zhou
 */
public class DepotRoleUtil {

	public static final String[] DEPOT_ROLE_NAMES = {
		DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
		DepotRolesConstants.ASSET_LIBRARY_CONNECTED_SITE_MEMBER,
		DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER,
		DepotRolesConstants.ASSET_LIBRARY_MEMBER,
		DepotRolesConstants.ASSET_LIBRARY_OWNER
	};

	public static Map<Locale, String> getDescriptionMap(
		Language language, String name) {

		Map<Locale, String> descriptionMap = new HashMap<>();

		for (Locale locale : language.getAvailableLocales()) {
			String description = _getDescription(locale, name);

			if (description != null) {
				descriptionMap.put(locale, description);
			}
		}

		return descriptionMap;
	}

	private static String _getDescription(Locale locale, String name) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, DepotRolesPortalInstanceLifecycleListener.class);

		if (Objects.equals(
				DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR, name)) {

			return ResourceBundleUtil.getString(
				resourceBundle,
				"asset-library-administrators-are-super-users-of-their-asset-" +
					"library-but-cannot-make-other-users-into-asset-library-" +
						"administrators");
		}
		else if (Objects.equals(
					DepotRolesConstants.ASSET_LIBRARY_MEMBER, name)) {

			return ResourceBundleUtil.getString(
				resourceBundle,
				"all-users-who-belong-to-an-asset-library-have-this-role-" +
					"within-that-asset-library");
		}
		else if (Objects.equals(
					DepotRolesConstants.ASSET_LIBRARY_OWNER, name)) {

			return ResourceBundleUtil.getString(
				resourceBundle,
				"asset-library-owners-are-super-users-of-their-asset-library-" +
					"and-can-assign-asset-library-roles-to-users");
		}

		return null;
	}

}