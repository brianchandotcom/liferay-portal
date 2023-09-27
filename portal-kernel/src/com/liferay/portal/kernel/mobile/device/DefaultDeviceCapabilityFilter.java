/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.mobile.device;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class DefaultDeviceCapabilityFilter implements DeviceCapabilityFilter {

	@Override
	public boolean accept(String capabilityName) {
		if (_acceptableCapabilityNames.isEmpty() ||
			_acceptableCapabilityNames.contains(capabilityName)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean accept(String capabilityName, String capabilityValue) {
		if (Validator.isNull(capabilityValue)) {
			return false;
		}

		capabilityValue = StringUtil.toLowerCase(capabilityValue);

		if (capabilityValue.equals("false") || !accept(capabilityName)) {
			return false;
		}

		return true;
	}

	public void setAcceptableCapabilityNames(
		Set<String> acceptableCapabilityNames) {

		_acceptableCapabilityNames.addAll(acceptableCapabilityNames);
	}

	private final Set<String> _acceptableCapabilityNames = new HashSet<>();

}