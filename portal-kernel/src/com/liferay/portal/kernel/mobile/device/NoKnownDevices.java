/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.mobile.device;

import java.util.Collections;
import java.util.Set;

/**
 * Class represents unknown device
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public class NoKnownDevices implements KnownDevices {

	public static NoKnownDevices getInstance() {
		return _noKnownDevices;
	}

	@Override
	public Set<VersionableName> getBrands() {
		return _unknownVersionableNames;
	}

	@Override
	public Set<VersionableName> getBrowsers() {
		return _unknownVersionableNames;
	}

	@Override
	public Set<VersionableName> getOperatingSystems() {
		return _unknownVersionableNames;
	}

	@Override
	public Set<String> getPointingMethods() {
		return _pointingMethods;
	}

	@Override
	public void reload() {
	}

	private NoKnownDevices() {
	}

	private static final NoKnownDevices _noKnownDevices = new NoKnownDevices();

	private final Set<String> _pointingMethods = Collections.singleton(
		VersionableName.UNKNOWN.getName());
	private final Set<VersionableName> _unknownVersionableNames =
		Collections.singleton(VersionableName.UNKNOWN);

}