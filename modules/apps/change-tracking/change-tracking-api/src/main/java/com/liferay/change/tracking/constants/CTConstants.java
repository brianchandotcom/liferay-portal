/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.constants;

import com.liferay.petra.string.StringPool;

/**
 * @author Daniel Kocsis
 */
public interface CTConstants {

	public static final int CT_CHANGE_TYPE_ADDITION = 0;

	public static final int CT_CHANGE_TYPE_DELETION = 1;

	public static final String CT_CHANGE_TYPE_LABEL_ADDITION = "added";

	public static final String CT_CHANGE_TYPE_LABEL_DELETION = "deleted";

	public static final String CT_CHANGE_TYPE_LABEL_MODIFICATION = "modified";

	public static final int CT_CHANGE_TYPE_MODIFICATION = 2;

	public static final long CT_COLLECTION_ID_PRODUCTION = 0;

	public static final int CT_PROCESS_MOVE = 1;

	public static final int CT_PROCESS_PUBLISH = 0;

	public static final String RESOURCE_NAME = "com.liferay.change.tracking";

	public static final String TYPE_AFTER = "after";

	public static final String TYPE_BEFORE = "before";

	public static final String TYPE_LATEST = "latest";

	public static String getCTChangeTypeLabel(int changeType) {
		if (changeType == CT_CHANGE_TYPE_ADDITION) {
			return CT_CHANGE_TYPE_LABEL_ADDITION;
		}
		else if (changeType == CT_CHANGE_TYPE_DELETION) {
			return CT_CHANGE_TYPE_LABEL_DELETION;
		}
		else if (changeType == CT_CHANGE_TYPE_MODIFICATION) {
			return CT_CHANGE_TYPE_LABEL_MODIFICATION;
		}

		return StringPool.BLANK;
	}

}