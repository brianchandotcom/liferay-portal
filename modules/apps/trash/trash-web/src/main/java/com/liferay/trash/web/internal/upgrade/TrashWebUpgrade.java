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

package com.liferay.trash.web.internal.upgrade;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.trash.web.internal.constants.TrashPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * Provides an upgrade registry for the Recycle Bin portlet.
 *
 * <p>
 * To register a new upgrade process, follow the steps below:
 * </p>
 *
 * <ol>
 * <li>
 * Create a new upgrade process class that implements the
 * <code>UpgradeStep</code> (in <code>com.liferay.portal.kernel</code>)
 * interface.
 * </li>
 * <li>
 * In the <code>register</code> method of this class, add a new registry call
 * providing
 * <ul>
 * <li>
 * The bundle symbolic name of the component you want to upgrade
 * </li>
 * <li>
 * The version from which you want to upgrade
 * </li>
 * <li>
 * The version to which you want to upgrade
 * </li>
 * <li>
 * An instance of the upgrade step
 * </li>
 * </ul>
 * </li>
 * </ol>
 *
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class TrashWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.0.0", new DummyUpgradeStep());

		registry.register(
			"0.0.1", "1.0.0",
			new BaseUpgradePortletId() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {{"182", TrashPortletKeys.TRASH}};
				}

			});
	}

}