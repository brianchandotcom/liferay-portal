/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.util;

import com.liferay.site.cms.site.initializer.internal.constants.CMSSiteInitializerFDSNames;

import java.util.Map;
import java.util.Objects;

/**
 * @author Marco Galluzzi
 */
public class FDSViewUtil {

	public static boolean isDefault(String fdsName, String viewType) {
		return Objects.equals(_viewTypes.get(fdsName), viewType);
	}

	private static final Map<String, String> _viewTypes = Map.of(
		CMSSiteInitializerFDSNames.ALL_SECTION, "table",
		CMSSiteInitializerFDSNames.CONTENTS_SECTION, "table",
		CMSSiteInitializerFDSNames.FILES_SECTION, "cards",
		CMSSiteInitializerFDSNames.SPACE_FILES_SUMMARY_SECTION, "cards",
		CMSSiteInitializerFDSNames.VIEW_CONTENTS_FOLDER, "table",
		CMSSiteInitializerFDSNames.VIEW_FILES_FOLDER, "table");

}