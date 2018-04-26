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

package com.liferay.user.associated.data.web.internal.configuration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = AnonymousUserConfigurationTracker.class)
public class AnonymousUserConfigurationTracker {

	public long getAnonymousUserId(long companyId) {
		AnonymousUserConfigurationWrapper anonymousUserConfigurationWrapper =
			_anonymousUserConfigurationWrappers.get(companyId);

		if (anonymousUserConfigurationWrapper != null) {
			return anonymousUserConfigurationWrapper.getUserId();
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"No anonymous user configuration found for company id: " +
					companyId);
		}

		return 0;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeAnonymousUserConfigurationWrapper"
	)
	protected void addAnonymousUserConfigurationWrapper(
		AnonymousUserConfigurationWrapper anonymousUserConfigurationWrapper) {

		_anonymousUserConfigurationWrappers.put(
			anonymousUserConfigurationWrapper.getCompanyId(),
			anonymousUserConfigurationWrapper);
	}

	protected void removeAnonymousUserConfigurationWrapper(
		AnonymousUserConfigurationWrapper anonymousUserConfigurationWrapper) {

		_anonymousUserConfigurationWrappers.remove(
			anonymousUserConfigurationWrapper);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnonymousUserConfigurationTracker.class);

	private final Map<Long, AnonymousUserConfigurationWrapper>
		_anonymousUserConfigurationWrappers = new ConcurrentHashMap<>();

}