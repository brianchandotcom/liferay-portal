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

package com.liferay.talend.tliferayinput;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.resource.LiferayResourceProperties;

import java.util.EnumSet;
import java.util.Set;

import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * @author Zoltán Takács
 */
public class TLiferayInputDefinition extends LiferayBaseComponentDefinition {

	public static final String COMPONENT_NAME = "tLiferayInput";

	public TLiferayInputDefinition() {
		super(COMPONENT_NAME, ExecutionEngine.DI);
	}

	@Override
	public Class<? extends ComponentProperties>[]
		getNestedCompatibleComponentPropertiesClass() {

		return concatPropertiesClasses(
			super.getNestedCompatibleComponentPropertiesClass(),
			(Class<? extends ComponentProperties>[])
				new Class<?>[] {LiferayResourceProperties.class});
	}

	@Override
	public Class<? extends ComponentProperties> getPropertyClass() {
		return TLiferayInputProperties.class;
	}

	@Override
	public RuntimeInfo getRuntimeInfo(
		ExecutionEngine executionEngine,
		ComponentProperties componentProperties,
		ConnectorTopology connectorTopology) {

		assertConnectorTopologyCompatibility(connectorTopology);
		assertEngineCompatibility(executionEngine);

		return getCommonRuntimeInfo(RUNTIME_SOURCE_CLASS_NAME);
	}

	@Override
	public Set<ConnectorTopology> getSupportedConnectorTopologies() {
		return EnumSet.of(ConnectorTopology.OUTGOING);
	}

	@Override
	public boolean isStartable() {
		return true;
	}

}