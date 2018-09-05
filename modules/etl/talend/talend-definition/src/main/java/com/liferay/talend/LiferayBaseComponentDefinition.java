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

import com.liferay.talend.connection.LiferayConnectionProperties;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.runtime.DependenciesReader;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.components.api.component.runtime.JarRuntimeInfo;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.runtime.RuntimeInfo;
import org.talend.daikon.runtime.RuntimeUtil;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
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

	public static SandboxedInstance getSandboxedInstance(
		String runtimeClassName) {

		return getSandboxedInstance(runtimeClassName, false);
	}

	public static SandboxedInstance getSandboxedInstance(
		String runtimeClassName, boolean useCurrentJvmProperties) {

		return _sandboxedInstanceProvider.getSandboxedInstance(
			runtimeClassName, useCurrentJvmProperties);
	}

	public static SandboxedInstanceProvider getSandboxedInstanceProvider() {
		return _sandboxedInstanceProvider;
	}

	/**
	 * Set provider of SandboxedInstances.
	 *
	 * <p>
	 * The method is intended for debug/test purposes only and should not be
	 * used in production.
	 * </p>
	 *
	 * @param sandboxedInstanceProvider provider to be set, can't be {@code
	 *        null}
	 */
	public static void setSandboxedInstanceProvider(
		SandboxedInstanceProvider sandboxedInstanceProvider) {

		_sandboxedInstanceProvider = sandboxedInstanceProvider;
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

		return (Class<? extends ComponentProperties>[])
			new Class<?>[] {LiferayConnectionProperties.class};
	}

	/**
	 * Defines a list of Return Properties (a.k.a After Properties).
	 *
	 * <p>
	 * These properties collect different metrics and information during
	 * component execution. Values of these properties are returned after
	 * component finished his work. Runtime Platform may use this method to
	 * retrieve this list and show in UI.
	 * </p>
	 *
	 * <p>
	 * Here, it is defined two properties:
	 * </p>
	 *
	 * <ol>
	 * <li>
	 * Error message.
	 * </li>
	 * <li>
	 * Number of records processed.
	 * </li>
	 * </ol>
	 *
	 * <p>
	 * For Error message property no efforts are required from component
	 * developer to set its value. Runtime Platform will set its value by itself
	 * in case of Exception in runtime.
	 * </p>
	 *
	 * <p>
	 * As for Number of records property see Reader implementation in runtime
	 * part.
	 * </p>
	 */
	@Override
	public Property<?>[] getReturnProperties() {
		return new Property[] {
			RETURN_ERROR_MESSAGE_PROP, RETURN_TOTAL_RECORD_COUNT_PROP
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

	private static final String _MAVEN_GROUP_ID = "com.liferay";

	private static final String _MAVEN_RUNTIME_ARTIFACT_ID =
		"com.liferay.talend.runtime";

	private static final String _MAVEN_RUNTIME_URI =
		"mvn:" + _MAVEN_GROUP_ID + "/" + _MAVEN_RUNTIME_ARTIFACT_ID;

	private static SandboxedInstanceProvider _sandboxedInstanceProvider =
		SandboxedInstanceProvider.INSTANCE;

}