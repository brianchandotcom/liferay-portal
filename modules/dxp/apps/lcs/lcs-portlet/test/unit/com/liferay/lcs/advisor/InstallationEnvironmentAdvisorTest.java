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

package com.liferay.lcs.advisor;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class InstallationEnvironmentAdvisorTest {

	@Test
	public void testGetHardwareMetadata() {
		Map<String, String> hardwareMetadata =
			_installationEnvironmentAdvisor.getHardwareMetadata();

		Assert.assertNotNull(hardwareMetadata.get("cpu.total.cores"));
		Assert.assertNotNull(hardwareMetadata.get("fs.root"));
		Assert.assertNotNull(hardwareMetadata.get("fs.root.total.space"));
		Assert.assertNotNull(hardwareMetadata.get("fs.root.usable.space"));
		Assert.assertNotNull(hardwareMetadata.get("physical.memory.free"));
		Assert.assertNotNull(hardwareMetadata.get("physical.memory.total"));
		Assert.assertNotNull(hardwareMetadata.get("swap.free"));
		Assert.assertNotNull(hardwareMetadata.get("swap.total"));
	}

	private final InstallationEnvironmentAdvisor
		_installationEnvironmentAdvisor =
			InstallationEnvironmentAdvisorFactory.getInstance();

}