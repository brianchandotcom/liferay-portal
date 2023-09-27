/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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