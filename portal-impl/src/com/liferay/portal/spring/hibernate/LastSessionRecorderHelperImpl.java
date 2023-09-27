/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.spring.orm.LastSessionRecorderHelper;

/**
 * @author Shuyang Zhou
 */
public class LastSessionRecorderHelperImpl
	implements LastSessionRecorderHelper {

	@Override
	public void syncLastSessionState() {
		LastSessionRecorderUtil.syncLastSessionState(true);
	}

	@Override
	public void syncLastSessionState(boolean portalSessionOnly) {
		LastSessionRecorderUtil.syncLastSessionState(portalSessionOnly);
	}

}