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

package com.liferay.talend;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.runtime.DependenciesReader;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.components.api.component.runtime.JarRuntimeInfo;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * @author Igor Beslic
 */
public abstract class LiferayDefinition extends AbstractComponentDefinition {

	public static RuntimeInfo getCommonRuntimeInfo(String className) {
		return new JarRuntimeInfo(
			_MAVEN_RUNTIME_URI,
			DependenciesReader.computeDependenciesFilePath(
				_MAVEN_GROUP_ID, _MAVEN_RUNTIME_ARTIFACT_ID),
			className);
	}

	public LiferayDefinition(
		String componentName, ExecutionEngine primaryExecutionEngine,
		ExecutionEngine... secondaryExecutionEngines) {

		super(componentName, primaryExecutionEngine, secondaryExecutionEngines);
	}

	@Override
	public String[] getFamilies() {
		return new String[] {"Business/Liferay"};
	}

	protected static final String BATCH_FILE_SINK_CLASS =
		"com.liferay.talend.runtime.LiferayBatchFileSink";

	private static final String _MAVEN_GROUP_ID = "com.liferay";

	private static final String _MAVEN_RUNTIME_ARTIFACT_ID =
		"com.liferay.talend.runtime";

	private static final String _MAVEN_RUNTIME_URI =
		"mvn:" + _MAVEN_GROUP_ID + "/" + _MAVEN_RUNTIME_ARTIFACT_ID;

}