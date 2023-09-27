/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.json.storage.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeStep;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public interface JSONStorageUpgradeStepFactory {

	public UpgradeStep createUpgradeStep(
		Class<?> modelClass, String tableName, String primaryKeyName,
		String jsonColumnName);

}