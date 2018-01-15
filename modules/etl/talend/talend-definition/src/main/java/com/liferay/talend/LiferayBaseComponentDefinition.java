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

	public static final String MAVEN_DEFINITION_ARTIFACT_ID =
		"com.liferay.talend.definition";

	public static final String MAVEN_GROUP_ID = "com.liferay";

	public static final String MAVEN_RUNTIME_ARTIFACT_ID =
		"com.liferay.talend.runtime";

	public static final String MAVEN_RUNTIME_URI =
		"mvn:" + MAVEN_GROUP_ID + "/" + MAVEN_RUNTIME_ARTIFACT_ID;

	public static final String RUNTIME_OLD_CLASS_NAME =
		"com.liferay.talend.runtime.reader.LiferayInputSource";

	public static final String RUNTIME_SINK_CLASS =
		"com.liferay.talend.runtime.LiferaySink";

	public static final String RUNTIME_SOURCE_CLASS =
		"com.liferay.talend.runtime.LiferaySource";

	public static final String RUNTIME_SOURCEORSINK_CLASS =
		"com.liferay.talend.runtime.LiferaySourceOrSink";

	public static RuntimeInfo getCommonRuntimeInfo(String clazz) {
		return new JarRuntimeInfo(
			MAVEN_RUNTIME_URI,
			DependenciesReader.computeDependenciesFilePath(
				MAVEN_GROUP_ID, MAVEN_RUNTIME_ARTIFACT_ID),
			clazz);
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
	 * <p>The method is intended for debug/test purposes only and should not be
	 * used in production.
	 *
	 * @param provider provider to be set, can't be {@code null}
	 */
	public static void setSandboxedInstanceProvider(
		SandboxedInstanceProvider provider) {

		_sandboxedInstanceProvider = provider;
	}

	public LiferayBaseComponentDefinition(
		String componentName, ExecutionEngine engine1,
		ExecutionEngine... engineOthers) {

		super(componentName, engine1, engineOthers);
	}

	@Override
	public String[] getFamilies() {
		return new String[] {"Business/Liferay"}; //$NON-NLS-1$
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends ComponentProperties>[]
		getNestedCompatibleComponentPropertiesClass() {

		return (Class<? extends ComponentProperties>[])
			new Class<?>[] {LiferayConnectionProperties.class};
	}

	/**
	 * Defines a list of Return Properties (a.k.a After Properties).
	 * These properties collect different metrics and information during
	 * component execution.
	 * Values of these properties are returned after component finished his
	 * work.
	 * Runtime Platform may use this method to retrieve a this list and show in
	 * UI
	 * Here, it is defined 2 properties: <br>
	 * 1) Error message
	 * 2) Number of records processed
	 * For Error message property no efforts are required from component
	 * developer to set its value.
	 * Runtime Platform will set its value by itself in case of Exception in
	 * runtime.
	 * As for Number of records property see Reader implementation in runtime
	 * part
	 */
	@SuppressWarnings("rawtypes")
	@Override
	/* Most of the components are on the input side, so put this here, the
	 * output definition will override this
	 */
	public Property[] getReturnProperties() {
		return new Property[] {
			RETURN_ERROR_MESSAGE_PROP, RETURN_TOTAL_RECORD_COUNT_PROP
		};
	}

	@Override
	public boolean isStartable() {
		return true;
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
			else {
				return RuntimeUtil.createRuntimeClass(runtimeInfo, classLoader);
			}
		}

	}

	private static SandboxedInstanceProvider _sandboxedInstanceProvider =
		SandboxedInstanceProvider.INSTANCE;

}