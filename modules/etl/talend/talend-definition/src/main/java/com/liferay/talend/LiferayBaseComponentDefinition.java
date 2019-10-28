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

import com.liferay.talend.common.oas.OASSource;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.properties.ExceptionUtils;
import com.liferay.talend.source.LiferayOASSource;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.runtime.DependenciesReader;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.components.api.component.runtime.JarRuntimeInfo;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.runtime.RuntimeInfo;
import org.talend.daikon.runtime.RuntimeUtil;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public abstract class LiferayBaseComponentDefinition
	extends AbstractComponentDefinition {

	public static final String RUNTIME_SINK_CLASS_NAME =
		"com.liferay.talend.runtime.LiferaySink";

	public static final String RUNTIME_SOURCE_CLASS_NAME =
		"com.liferay.talend.runtime.LiferaySource";

	public static final String RUNTIME_SOURCE_OR_SINK_CLASS_NAME =
		"com.liferay.talend.runtime.LiferaySourceOrSink";

	public static RuntimeInfo getCommonRuntimeInfo(String className) {
		return new JarRuntimeInfo(
			_MAVEN_RUNTIME_URI,
			DependenciesReader.computeDependenciesFilePath(
				_MAVEN_GROUP_ID, _MAVEN_RUNTIME_ARTIFACT_ID),
			className);
	}

	public static LiferayOASSource getLiferayOASSource(
		LiferayConnectionProperties liferayConnectionProperties) {

		try (SandboxedInstance sandboxedInstance = _getSandboxedInstance(
				RUNTIME_SOURCE_OR_SINK_CLASS_NAME, false)) {

			OASSource oasSource = (OASSource)sandboxedInstance.getInstance();

			return new LiferayOASSource(
				oasSource, oasSource.initialize(liferayConnectionProperties));
		}
		catch (Exception e) {
			return new LiferayOASSource(
				null, ExceptionUtils.exceptionToValidationResult(e));
		}
	}

	public LiferayBaseComponentDefinition(
		String componentName, ExecutionEngine executionEngine,
		ExecutionEngine... otherExecutionEngines) {

		super(componentName, executionEngine, otherExecutionEngines);
	}

	@Override
	public String[] getFamilies() {
		return new String[] {"Business/Liferay"};
	}

	@Override
	public Class<? extends ComponentProperties>[]
		getNestedCompatibleComponentPropertiesClass() {

		return (Class<? extends ComponentProperties>[])new Class<?>[] {
			LiferayConnectionProperties.class
		};
	}

	public static class SandboxedInstanceProvider {

		public static final SandboxedInstanceProvider INSTANCE =
			new SandboxedInstanceProvider();

		public SandboxedInstance getSandboxedInstance(
			final String runtimeClassName,
			final boolean useCurrentJvmProperties) {

			ClassLoader classLoader =
				LiferayBaseComponentDefinition.class.getClassLoader();
			RuntimeInfo runtimeInfo =
				LiferayBaseComponentDefinition.getCommonRuntimeInfo(
					runtimeClassName);

			if (useCurrentJvmProperties) {
				return RuntimeUtil.createRuntimeClassWithCurrentJVMProperties(
					runtimeInfo, classLoader);
			}

			return RuntimeUtil.createRuntimeClass(runtimeInfo, classLoader);
		}

	}

	private static SandboxedInstance _getSandboxedInstance(
		String runtimeClassName, boolean useCurrentJvmProperties) {

		return _sandboxedInstanceProvider.getSandboxedInstance(
			runtimeClassName, useCurrentJvmProperties);
	}

	private static final String _MAVEN_GROUP_ID = "com.liferay";

	private static final String _MAVEN_RUNTIME_ARTIFACT_ID =
		"com.liferay.talend.runtime";

	private static final String _MAVEN_RUNTIME_URI =
		"mvn:" + _MAVEN_GROUP_ID + "/" + _MAVEN_RUNTIME_ARTIFACT_ID;

	private static SandboxedInstanceProvider _sandboxedInstanceProvider =
		SandboxedInstanceProvider.INSTANCE;

}