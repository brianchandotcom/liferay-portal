/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.asset.comparator;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.portal.defaultpermissions.asset.PortalDefaultPermissionsAsset;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Stefano Motta
 */
public class PortalDefaultPermissionsAssetOrderComparator
	implements Comparator
		<ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsAsset>>,
			   Serializable {

	public PortalDefaultPermissionsAssetOrderComparator() {
		this(true);
	}

	public PortalDefaultPermissionsAssetOrderComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsAsset> serviceWrapper1,
		ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsAsset> serviceWrapper2) {

		PortalDefaultPermissionsAsset portalDefaultPermissionsAsset1 =
			serviceWrapper1.getService();
		PortalDefaultPermissionsAsset portalDefaultPermissionsAsset2 =
			serviceWrapper2.getService();

		String label1 = portalDefaultPermissionsAsset1.getLabel();
		String label2 = portalDefaultPermissionsAsset2.getLabel();

		int value = label1.compareToIgnoreCase(label2);

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}