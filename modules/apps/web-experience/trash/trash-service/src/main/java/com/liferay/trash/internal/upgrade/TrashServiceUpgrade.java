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

package com.liferay.trash.internal.upgrade;

import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.trash.internal.upgrade.v1_0_0.UpgradeClassNames;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class TrashServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.trash.service", "0.0.1", "1.0.0",
			new UpgradeClassNames());
	}

	@Reference
	protected void setLockManager(LockManager lockManager) {
	}

}