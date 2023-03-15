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

package com.liferay.osb.faro.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;

/**
 * @author Matthew Kong
 */
public class EmailUtil {

	public static String getLogoIconURL() {
		return _FARO_URL.concat("/o/osb-faro-web/images/email/ac-chart.png");
	}

	public static String getTitleIconURL() {
		return _FARO_URL.concat("/o/osb-faro-web/images/email/ac-title.png");
	}

	public static String getWorkspaceURL(Group group) {
		StringBuilder sb = new StringBuilder(4);

		sb.append(_FARO_URL);
		sb.append("/workspace");

		String friendlyURL = group.getFriendlyURL();

		if ((friendlyURL != null) && !friendlyURL.isEmpty()) {
			sb.append(friendlyURL);
		}
		else {
			sb.append(StringPool.SLASH);
			sb.append(group.getGroupId());
		}

		return sb.toString();
	}

	private static final String _FARO_URL = System.getenv("FARO_URL");

}