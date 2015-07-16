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

package com.liferay.portal.kernel.security.access.control.profile;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public class ServiceAccessControlProfileThreadLocal {

	public static void addActiveServiceAccessControlProfileName(
		String profileName) {

		List<String> activeServiceAccessControlProfileNames =
			getActiveServiceAccessControlProfileNames();

		if (activeServiceAccessControlProfileNames == null) {
			activeServiceAccessControlProfileNames = new ArrayList<>();

			setActiveServiceAccessControlProfileNames(
				activeServiceAccessControlProfileNames);
		}

		activeServiceAccessControlProfileNames.add(profileName);
	}

	public static List<String>
		getActiveServiceAccessControlProfileNames() {

		return _activeServiceAccessControlProfileNames.get();
	}

	public static void setActiveServiceAccessControlProfileNames(
		List<String> activeServiceAccessControlProfileNames) {

		_activeServiceAccessControlProfileNames.set(
			activeServiceAccessControlProfileNames);
	}

	private static final ThreadLocal<List<String>>
		_activeServiceAccessControlProfileNames = new AutoResetThreadLocal<>(
			AutoResetThreadLocal.class +
			"._activeServiceAccessControlProfileNames");

}