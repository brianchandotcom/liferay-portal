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

package com.liferay.multi.factor.authentication.fido2.credential.internal.upgrade;

import com.liferay.multi.factor.authentication.fido2.credential.internal.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.multi.factor.authentication.fido2.credential.internal.upgrade.v2_0_0.MFAFIDO2CredentialUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 */
@Component(service = UpgradeStepRegistrator.class)
public class MFAFIDO2CredentialServiceUpgrade
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("1.0.0", "2.0.0", new UpgradeSchema());

		registry.register(
			"2.0.0", "2.1.0", new MFAFIDO2CredentialUpgradeProcess());
	}

}