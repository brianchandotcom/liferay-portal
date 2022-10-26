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

package com.liferay.oauth.client.persistence.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 */
@Component(service = UpgradeStepRegistrator.class)
public class OAuthClientPersistenceServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			new com.liferay.oauth.client.persistence.internal.upgrade.V2_0_0.
				OAuthClientEntryUserInfoMapperJSONUpgradeProcess());

		registry.register(
			"1.1.0", "2.0.0",
			UpgradeProcessFactory.alterColumnName(
				"OAuthClientEntry", "oidcUserInfoMapperJSON",
				"userInfoMapperJSON VARCHAR(3999) null"));
	}

}