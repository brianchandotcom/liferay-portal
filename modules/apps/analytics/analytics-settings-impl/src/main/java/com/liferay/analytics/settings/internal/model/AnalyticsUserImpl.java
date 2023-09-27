/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.internal.model;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserWrapper;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class AnalyticsUserImpl extends UserWrapper {

	public AnalyticsUserImpl(User user, Map<String, long[]> memberships) {
		super(user);

		_memberships = memberships;
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> modelAttributes = super.getModelAttributes();

		modelAttributes.put("memberships", _memberships);

		return modelAttributes;
	}

	private final Map<String, long[]> _memberships;

}