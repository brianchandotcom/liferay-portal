/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.headless.admin.user.dto.v1_0.UserAccountBrief;
import com.liferay.portal.kernel.model.User;

/**
 * @author Crescenzo Rega
 */
public class UserAccountBriefUtil {

	public static UserAccountBrief toUserAccountBrief(User user) {
		return new UserAccountBrief() {
			{
				setAlternateName(user::getScreenName);
				setEmailAddress(user::getEmailAddress);
				setExternalReferenceCode(user::getExternalReferenceCode);
				setId(user::getUserId);
				setName(user::getFullName);
			}
		};
	}

}