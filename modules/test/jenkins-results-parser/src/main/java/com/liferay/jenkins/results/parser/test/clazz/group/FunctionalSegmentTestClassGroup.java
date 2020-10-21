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

package com.liferay.jenkins.results.parser.test.clazz.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class FunctionalSegmentTestClassGroup extends SegmentTestClassGroup {

	public List<FunctionalAxisTestClassGroup>
		getFunctionalAxisTestClassGroups() {

		List<FunctionalAxisTestClassGroup> functionalAxisTestClassGroups =
			new ArrayList<>();

		for (AxisTestClassGroup axisTestClassGroup :
				getChildAxisTestClassGroups()) {

			if (!(axisTestClassGroup instanceof FunctionalAxisTestClassGroup)) {
				continue;
			}

			FunctionalAxisTestClassGroup functionalAxisTestClassGroup =
				(FunctionalAxisTestClassGroup)axisTestClassGroup;

			functionalAxisTestClassGroups.add(functionalAxisTestClassGroup);
		}

		return functionalAxisTestClassGroups;
	}

	@Override
	public Integer getMinimumSlaveRAM() {
		Properties poshiProperties = getPoshiProperties();

		String minimumSlaveRAM = poshiProperties.getProperty(
			"minimum.slave.ram");

		if ((minimumSlaveRAM != null) && minimumSlaveRAM.matches("\\d+")) {
			return Integer.valueOf(minimumSlaveRAM);
		}

		return super.getMinimumSlaveRAM();
	}

	public Properties getPoshiProperties() {
		List<FunctionalAxisTestClassGroup> functionalAxisTestClassGroups =
			getFunctionalAxisTestClassGroups();

		FunctionalAxisTestClassGroup functionalAxisTestClassGroup =
			functionalAxisTestClassGroups.get(0);

		return functionalAxisTestClassGroup.getPoshiProperties();
	}

	protected FunctionalSegmentTestClassGroup(
		FunctionalBatchTestClassGroup parentFunctionalBatchTestClassGroup) {

		super(parentFunctionalBatchTestClassGroup);
	}

}