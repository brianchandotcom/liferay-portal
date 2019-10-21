/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.rest.oauth2.internal.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.sharepoint.rest.oauth2.internal.upgrade.v2_0_0.UpgradeCompanyId;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alberto Chaparro
 */
@Component(
	immediate = true,
	service = {
		SharepointOauth2ServiceUpgrade.class, UpgradeStepRegistrator.class
	}
)
public class SharepointOauth2ServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(UpgradeStepRegistrator.Registry registry) {
		registry.register("1.0.0", "2.0.0", new UpgradeCompanyId());
	}

}