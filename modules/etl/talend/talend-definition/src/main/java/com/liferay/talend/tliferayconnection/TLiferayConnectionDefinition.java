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

package com.liferay.talend.tliferayconnection;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.connection.LiferayConnectionProperties;

import java.util.EnumSet;
import java.util.Set;

import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * @author Zoltán Takács
 */
public class TLiferayConnectionDefinition
	extends LiferayBaseComponentDefinition {

	public static final String COMPONENT_NAME = "tLiferayConnection";

	public TLiferayConnectionDefinition() {
		super(COMPONENT_NAME, ExecutionEngine.DI);
	}

	@Override
	public Class<? extends ComponentProperties> getPropertyClass() {
		return LiferayConnectionProperties.class;
	}

	@Override
	public Property<?>[] getReturnProperties() {
		return new Property[] {RETURN_ERROR_MESSAGE_PROP};
	}

	@Override
	public RuntimeInfo getRuntimeInfo(
		ExecutionEngine executionEngine,
		ComponentProperties componentProperties,
		ConnectorTopology connectorTopology) {

		assertConnectorTopologyCompatibility(connectorTopology);
		assertEngineCompatibility(executionEngine);

		if (connectorTopology == ConnectorTopology.NONE) {
			return getCommonRuntimeInfo(RUNTIME_SOURCE_OR_SINK_CLASS_NAME);
		}

		return null;
	}

	@Override
	public Set<ConnectorTopology> getSupportedConnectorTopologies() {
		return EnumSet.of(ConnectorTopology.NONE);
	}

	@Override
	public boolean isStartable() {
		return true;
	}

}