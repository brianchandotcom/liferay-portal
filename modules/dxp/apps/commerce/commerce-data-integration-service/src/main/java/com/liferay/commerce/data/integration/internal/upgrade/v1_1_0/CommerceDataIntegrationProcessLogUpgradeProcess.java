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

package com.liferay.commerce.data.integration.internal.upgrade.v1_1_0;

import com.liferay.commerce.data.integration.internal.upgrade.base.BaseCommerceDataIntegrationServiceUpgradeProcess;
import com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessLogModelImpl;

/**
 * @author Ethan Bustad
 */
public class CommerceDataIntegrationProcessLogUpgradeProcess
	extends BaseCommerceDataIntegrationServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		renameColumn(
			CommerceDataIntegrationProcessLogModelImpl.class,
			CommerceDataIntegrationProcessLogModelImpl.TABLE_NAME, "output",
			"output_ TEXT null");
	}

}