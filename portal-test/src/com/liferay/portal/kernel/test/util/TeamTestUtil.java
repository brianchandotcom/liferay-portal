/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.util;

import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;

/**
 * @author     Jonathan McCann
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class TeamTestUtil {

	public static Team addTeam() throws Exception {
		return TeamLocalServiceUtil.addTeam(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

}