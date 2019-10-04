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

package com.liferay.portal.kernel.mobile.device;

import java.util.Collections;
import java.util.Map;
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

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<Capability, Set<String>> getDeviceIds() {
		return Collections.emptyMap();
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