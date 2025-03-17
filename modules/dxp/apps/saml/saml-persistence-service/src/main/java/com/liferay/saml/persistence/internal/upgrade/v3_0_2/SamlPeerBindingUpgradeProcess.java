/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.internal.upgrade.v3_0_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.saml.persistence.model.impl.SamlPeerBindingImpl;

import java.util.Arrays;

/**
 * @author Stian Sigvartsen
 */
public class SamlPeerBindingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		dropIndexes(
			Arrays.asList("IX_E642E1AE", "IX_81ACF542", "IX_BC82BDFC"),
			SamlPeerBindingImpl.TABLE_NAME);
	}

}