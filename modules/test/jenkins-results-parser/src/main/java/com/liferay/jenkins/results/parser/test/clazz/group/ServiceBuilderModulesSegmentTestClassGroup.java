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

/**
 * @author Michael Hashimoto
 */
public class ServiceBuilderModulesSegmentTestClassGroup
	extends ModulesSegmentTestClassGroup {

	public ServiceBuilderModulesSegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup) {

		super(parentBatchTestClassGroup);

		if (!(parentBatchTestClassGroup instanceof
				ServiceBuilderModulesBatchTestClassGroup)) {

			throw new RuntimeException("Invalid parent batch test class group");
		}

		_serviceBuilderModulesBatchTestClassGroup =
			(ServiceBuilderModulesBatchTestClassGroup)parentBatchTestClassGroup;
	}

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());

		ServiceBuilderModulesBatchTestClassGroup.BuildType buildType =
			_serviceBuilderModulesBatchTestClassGroup.getBuildType();

		if (buildType !=
				ServiceBuilderModulesBatchTestClassGroup.BuildType.CORE) {

			sb.append("SKIP_SERVICE_BUILDER_CORE=true\n");
		}

		if (buildType !=
				ServiceBuilderModulesBatchTestClassGroup.BuildType.FULL) {

			sb.append("SKIP_SERVICE_BUILDER_FULL=true\n");
		}

		return sb.toString();
	}

	private final ServiceBuilderModulesBatchTestClassGroup
		_serviceBuilderModulesBatchTestClassGroup;

}