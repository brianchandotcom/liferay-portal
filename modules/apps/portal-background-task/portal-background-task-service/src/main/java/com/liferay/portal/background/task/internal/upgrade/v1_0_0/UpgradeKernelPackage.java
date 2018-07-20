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

package com.liferay.portal.background.task.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.upgrade.UpgradeException;

/**
 * @author Tina Tian
 */
public class UpgradeKernelPackage
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage {

	@Override
	protected void doUpgrade() throws UpgradeException {
		try {
			upgradeLongTextTable(
				"BackgroundTask", "taskContext", "backgroundTaskId",
				_CLASS_NAMES, WildcardMode.SURROUND);
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private static final String[][] _CLASS_NAMES = {
		{
			"com.liferay.portal.security.auth.HttpPrincipal",
			"com.liferay.portal.kernel.security.auth.HttpPrincipal"
		}
	};

}