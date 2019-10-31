/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.constants;

import com.liferay.petra.string.StringPool;

/**
 * @author Olivia Yu
 */
public class ResultRankingsConstants {

	public static final int STATUS_ACTIVE = 1;

	public static final int STATUS_INACTIVE = 0;

	public static final String STATUS_LABEL_ACTIVE = "active";

	public static final String STATUS_LABEL_INACTIVE = "inactive";

	public static String getStatusLabel(int status) {
		if (status == STATUS_ACTIVE) {
			return STATUS_LABEL_ACTIVE;
		}
		else if (status == STATUS_INACTIVE) {
			return STATUS_LABEL_INACTIVE;
		}

		return StringPool.BLANK;
	}

}