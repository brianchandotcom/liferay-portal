/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

/**
 * @author Alexander Chow
 */
public class VerifyProcessSuite extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (FeatureFlagManagerUtil.isEnabled("LPS-157670")) {
			verify(new VerifyPermission());
			verify(new VerifyGroup());
			verify(new VerifyRole());

			verify(new VerifyAuditedModel());
			verify(new VerifyResourceActions());
			verify(new VerifyResourcePermissions());
			verify(new VerifyUser());
		}
		else {
			verify(new VerifyGroup());
			verify(new VerifyLayout());

			verify(new VerifyResourcePermissions());
		}
	}

}