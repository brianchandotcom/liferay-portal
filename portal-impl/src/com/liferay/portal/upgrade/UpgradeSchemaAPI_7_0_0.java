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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alberto Chaparro
 */
public class UpgradeSchemaAPI_7_0_0 extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("alter table ResourcePermission add primKeyId LONG");

		if (_log.isDebugEnabled()) {
			_log.debug("Added column primKeyId to table ResourcePermission");
		}

		runSQL("alter table ResourcePermission add viewActionId BOOLEAN");

		if (_log.isDebugEnabled()) {
			_log.debug("Added column viewActionId to table ResourcePermission");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeSchemaAPI_7_0_0.class);

}