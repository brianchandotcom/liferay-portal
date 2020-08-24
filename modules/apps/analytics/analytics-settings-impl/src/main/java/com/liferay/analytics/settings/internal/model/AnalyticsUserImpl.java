/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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