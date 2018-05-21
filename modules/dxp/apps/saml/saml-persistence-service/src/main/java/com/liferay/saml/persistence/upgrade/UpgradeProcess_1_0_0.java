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

package com.liferay.saml.persistence.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.saml.persistence.upgrade.v1_0_0.UpgradeSamlIdpSpSession;
import com.liferay.saml.persistence.upgrade.v1_0_0.UpgradeSamlSpAuthRequest;
import com.liferay.saml.persistence.upgrade.v1_0_0.UpgradeSamlSpMessage;
import com.liferay.saml.persistence.upgrade.v1_0_0.UpgradeSamlSpSession;

/**
 * @author Mika Koivisto
 */
public class UpgradeProcess_1_0_0 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return 100;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSamlIdpSpSession.class);
		upgrade(UpgradeSamlSpAuthRequest.class);
		upgrade(UpgradeSamlSpMessage.class);
		upgrade(UpgradeSamlSpSession.class);
	}

}