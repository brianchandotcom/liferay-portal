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

package com.liferay.service.access.control.profile.service.security;

import com.liferay.portal.kernel.security.ac.ServiceAccessControlProfile;
import com.liferay.service.access.control.profile.model.SACPEntry;

import java.util.List;
import java.util.Locale;

/**
 * @author Mika Koivisto
 */
public class ServiceAccessControlProfileImpl
	implements ServiceAccessControlProfile {

	public ServiceAccessControlProfileImpl(SACPEntry sacpEntry) {
		_sacpEntry = sacpEntry;
	}

	@Override
	public List<String> getAllowedServicesList() {
		return _sacpEntry.getAllowedServicesList();
	}

	@Override
	public String getName() {
		return _sacpEntry.getName();
	}

	@Override
	public String getTitle(Locale locale) {
		return _sacpEntry.getTitle(locale);
	}

	private final SACPEntry _sacpEntry;

}