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

package com.liferay.portal.kernel.security.service.access.policy;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public class ServiceAccessPolicyThreadLocal {

	public static void addActiveServiceAccessPolicyName(
		String serviceAccessPolicyName) {

		List<String> activeServiceAccessPolicyNames =
			getActiveServiceAccessPolicyNames();

		if (activeServiceAccessPolicyNames == null) {
			activeServiceAccessPolicyNames = new ArrayList<>();

			setActiveServiceAccessPolicyNames(activeServiceAccessPolicyNames);
		}

		activeServiceAccessPolicyNames.add(serviceAccessPolicyName);
	}

	public static List<String> getActiveServiceAccessPolicyNames() {
		return _activeServiceAccessPolicyNames.get();
	}

	public static void setActiveServiceAccessPolicyNames(
		List<String> activeServiceAccessPolicyNames) {

		_activeServiceAccessPolicyNames.set(activeServiceAccessPolicyNames);
	}

	private static final ThreadLocal<List<String>>
		_activeServiceAccessPolicyNames = new AutoResetThreadLocal<>(
			AutoResetThreadLocal.class + "._activeServiceAccessPolicyNames");

}