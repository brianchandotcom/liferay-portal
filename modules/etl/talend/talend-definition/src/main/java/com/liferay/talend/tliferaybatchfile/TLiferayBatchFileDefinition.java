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

package com.liferay.talend.tliferaybatchfile;

import com.liferay.talend.LiferayDefinition;

import java.util.EnumSet;
import java.util.Set;

import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * @author Igor Beslic
 */
public class TLiferayBatchFileDefinition extends LiferayDefinition {

	public static final String COMPONENT_NAME = "tLiferayBatchFile";

	public TLiferayBatchFileDefinition() {
		super(COMPONENT_NAME, ExecutionEngine.DI);
	}

	@Override
	public Class<? extends ComponentProperties>[]
		getNestedCompatibleComponentPropertiesClass() {

		return concatPropertiesClasses(
			super.getNestedCompatibleComponentPropertiesClass(),
			(Class<? extends ComponentProperties>[])new Class<?>[] {
				SchemaProperties.class
			});
	}

	@Override
	public Class<? extends ComponentProperties> getPropertyClass() {
		return TLiferayBatchFileProperties.class;
	}

	@Override
	public Property[] getReturnProperties() {
		return new Property[] {
			RETURN_ERROR_MESSAGE_PROP, RETURN_REJECT_RECORD_COUNT_PROP,
			RETURN_SUCCESS_RECORD_COUNT_PROP, RETURN_TOTAL_RECORD_COUNT_PROP
		};
	}

	@Override
	public RuntimeInfo getRuntimeInfo(
		ExecutionEngine executionEngine,
		ComponentProperties componentProperties,
		ConnectorTopology connectorTopology) {

		assertEngineCompatibility(executionEngine);

		if (connectorTopology == ConnectorTopology.INCOMING) {
			return getCommonRuntimeInfo(BATCH_FILE_SINK_CLASS);
		}

		return null;
	}

	@Override
	public Set<ConnectorTopology> getSupportedConnectorTopologies() {
		return EnumSet.of(ConnectorTopology.INCOMING);
	}

	@Override
	public boolean isStartable() {
		return true;
	}

}