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

package com.liferay.portal.security.service.access.policy.internal;

import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicy;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import java.util.List;
import java.util.Locale;

/**
 * @author Mika Koivisto
 */
public class ServiceAccessPolicyImpl implements ServiceAccessPolicy {

	public ServiceAccessPolicyImpl(SAPEntry sapEntry) {
		_sapEntry = sapEntry;
	}

	@Override
	public List<String> getAllowedServiceSignaturesList() {
		return _sapEntry.getAllowedServiceSignaturesList();
	}

	@Override
	public String getName() {
		return _sapEntry.getName();
	}

	@Override
	public String getTitle(Locale locale) {
		return _sapEntry.getTitle(locale);
	}

	private final SAPEntry _sapEntry;

}