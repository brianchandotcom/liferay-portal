/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Rubén Pulido
 */
public class AssetVocabularyConstants {

	public static final int VISIBILITY_TYPE_INTERNAL = 1;

	public static final String VISIBILITY_TYPE_INTERNAL_LABEL = "INTERNAL";

	public static final int VISIBILITY_TYPE_PUBLIC = 0;

	public static final String VISIBILITY_TYPE_PUBLIC_LABEL = "PUBLIC";

	public static final int[] VISIBILITY_TYPES = {
		VISIBILITY_TYPE_INTERNAL, VISIBILITY_TYPE_PUBLIC
	};

	public static int getLabelVisibilityType(String label) {
		if (StringUtil.equalsIgnoreCase(
				VISIBILITY_TYPE_INTERNAL_LABEL, label)) {

			return VISIBILITY_TYPE_INTERNAL;
		}

		return VISIBILITY_TYPE_PUBLIC;
	}

	public static String getVisibilityTypeLabel(int visibilityType) {
		if (visibilityType == VISIBILITY_TYPE_INTERNAL) {
			return VISIBILITY_TYPE_INTERNAL_LABEL;
		}

		return VISIBILITY_TYPE_PUBLIC_LABEL;
	}

}