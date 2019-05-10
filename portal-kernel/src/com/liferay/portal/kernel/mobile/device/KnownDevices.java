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

import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Milen Dyankov
 * @author Michael C. Han
 */
@ProviderType
public interface KnownDevices {

	public Set<VersionableName> getBrands();

	public Set<VersionableName> getBrowsers();

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public Map<Capability, Set<String>> getDeviceIds();

	public Set<VersionableName> getOperatingSystems();

	public Set<String> getPointingMethods();

	public void reload() throws Exception;

}