/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.util.comparator;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.portal.kernel.defaultpermissions.resource.PortalDefaultPermissionsModelResource;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Stefano Motta
 */
public class PortalDefaultPermissionsModelResourceComparator
	implements Comparator
		<ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsModelResource>>,
			   Serializable {

	public PortalDefaultPermissionsModelResourceComparator() {
		this(true);
	}

	public PortalDefaultPermissionsModelResourceComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsModelResource> serviceWrapper1,
		ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsModelResource> serviceWrapper2) {

		PortalDefaultPermissionsModelResource
			portalDefaultPermissionsModelResource1 =
				serviceWrapper1.getService();
		PortalDefaultPermissionsModelResource
			portalDefaultPermissionsModelResource2 =
				serviceWrapper2.getService();

		String label1 = portalDefaultPermissionsModelResource1.getLabel();
		String label2 = portalDefaultPermissionsModelResource2.getLabel();

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