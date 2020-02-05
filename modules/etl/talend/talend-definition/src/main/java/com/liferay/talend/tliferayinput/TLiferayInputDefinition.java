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

import com.liferay.talend.LiferayDefinition;
import com.liferay.talend.properties.input.LiferayInputProperties;

import java.util.EnumSet;
import java.util.Set;

import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.components.api.exception.error.ComponentsErrorCode;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * @author Zoltán Takács
 * @author Ivica Cardic
 */
public class TLiferayInputDefinition extends LiferayDefinition {

	public static final String COMPONENT_NAME = "tLiferayInput";

	public TLiferayInputDefinition() {
		super(COMPONENT_NAME, ExecutionEngine.DI);
	}

	@Override
	public Class<? extends ComponentProperties>[]
		getNestedCompatibleComponentPropertiesClass() {

		return concatPropertiesClasses(
			super.getNestedCompatibleComponentPropertiesClass(),
			(Class<? extends ComponentProperties>[])new Class<?>[] {
				LiferayInputProperties.class
			});
	}

	@Override
	public Class<? extends ComponentProperties> getPropertyClass() {
		return TLiferayInputProperties.class;
	}

	public Property<?>[] getReturnProperties() {
		return new Property[] {
			RETURN_ERROR_MESSAGE_PROP, RETURN_TOTAL_RECORD_COUNT_PROP
		};
	}

	@Override
	public RuntimeInfo getRuntimeInfo(
		ExecutionEngine executionEngine,
		ComponentProperties componentProperties,
		ConnectorTopology connectorTopology) {

		if (connectorTopology != ConnectorTopology.OUTGOING) {
			TalendRuntimeException.TalendRuntimeExceptionBuilder builder =
				new TalendRuntimeException.TalendRuntimeExceptionBuilder(
					ComponentsErrorCode.WRONG_CONNECTOR,
					new IllegalArgumentException());

			builder.put("component", COMPONENT_NAME);

			throw builder.create();
		}

		assertEngineCompatibility(executionEngine);

		return getCommonRuntimeInfo(SOURCE_CLASS_NAME);
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