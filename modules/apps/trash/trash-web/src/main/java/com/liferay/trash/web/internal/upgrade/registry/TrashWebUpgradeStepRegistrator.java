/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.web.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.trash.constants.TrashPortletKeys;

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
@Component(service = UpgradeStepRegistrator.class)
public class TrashWebUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new BasePortletIdUpgradeProcess() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {{"182", TrashPortletKeys.TRASH}};
				}

			});
	}

}