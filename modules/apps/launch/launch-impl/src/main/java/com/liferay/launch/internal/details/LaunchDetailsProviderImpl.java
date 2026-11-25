/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.details;

import com.liferay.launch.internal.util.LaunchUtil;
import com.liferay.launch.details.LaunchDetails;
import com.liferay.launch.details.provider.LaunchDetailsProvider;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.util.MapUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = LaunchDetailsProvider.class)
public class LaunchDetailsProviderImpl implements LaunchDetailsProvider {

	@Override
	public LaunchDetails fetchLaunchDetails(long launchId) {
		ObjectEntry objectEntry = LaunchUtil.fetchLaunchObjectEntry(
			launchId);

		if (objectEntry == null) {
			return null;
		}

		return new LaunchDetails(
			launchId, MapUtil.getString(objectEntry.getValues(), "name"));
	}

}