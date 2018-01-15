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

package com.liferay.consumer.talend.tliferayinput;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import static org.talend.components.api.component.ComponentDefinition.RETURN_ERROR_MESSAGE_PROP;
import static org.talend.components.api.component.ComponentDefinition.RETURN_TOTAL_RECORD_COUNT_PROP;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * @author Zoltán Takács
 */
public class TLiferayInputDefinitionTest {

	@Test
	public void testGetFamilies() {
		TLiferayInputDefinition definition = new TLiferayInputDefinition();

		String[] actual = definition.getFamilies();

		assertThat(Arrays.asList(actual), contains("Business/Liferay"));
	}

	@Test
	public void testGetPropertyClass() {
		TLiferayInputDefinition definition = new TLiferayInputDefinition();

		Class<?> propertyClass = definition.getPropertyClass();

		String canonicalName = propertyClass.getCanonicalName();

		assertThat(
			canonicalName,
			equalTo(
				"com.liferay.consumer.talend.tliferayinput." +
					"TLiferayInputProperties"));
	}

	@Test
	public void testGetReturnProperties() {
		TLiferayInputDefinition definition = new TLiferayInputDefinition();

		Property[] returnProperties = definition.getReturnProperties();

		List<Property> propertyList = Arrays.asList(returnProperties);

		assertThat(propertyList, hasSize(2));
		Assert.assertTrue(
			propertyList.contains(RETURN_TOTAL_RECORD_COUNT_PROP));
		Assert.assertTrue(propertyList.contains(RETURN_ERROR_MESSAGE_PROP));
	}

	@Test
	public void testGetRuntimeInfo() {
		TLiferayInputDefinition definition = new TLiferayInputDefinition();

		RuntimeInfo runtimeInfo = definition.getRuntimeInfo(
			ExecutionEngine.DI, null, ConnectorTopology.OUTGOING);

		String runtimeClassName = runtimeInfo.getRuntimeClassName();

		assertThat(
			runtimeClassName,
			equalTo("com.liferay.consumer.talend.runtime.LiferaySource"));
	}

	@Test
	public void testGetRuntimeInfoWrongEngine() {
		TLiferayInputDefinition definition = new TLiferayInputDefinition();
		thrown.expect(TalendRuntimeException.class);
		thrown.expectMessage(
			"WRONG_EXECUTION_ENGINE:{component=tLiferayInput, " +
				"requested=DI_SPARK_STREAMING, available=[DI]}");
		definition.getRuntimeInfo(
			ExecutionEngine.DI_SPARK_STREAMING, null,
			ConnectorTopology.OUTGOING);
	}

	@Ignore
	@Test
	public void testGetRuntimeInfoWrongTopology() {
		TLiferayInputDefinition definition = new TLiferayInputDefinition();
		thrown.expect(TalendRuntimeException.class);
		thrown.expectMessage("WRONG_CONNECTOR:{component=tLiferayInput}");
		definition.getRuntimeInfo(
			ExecutionEngine.DI, null, ConnectorTopology.INCOMING);
	}

	@Test
	public void testGetSupportedConnectorTopologies() {
		TLiferayInputDefinition definition = new TLiferayInputDefinition();

		Set<ConnectorTopology> connectorTopologies =
			definition.getSupportedConnectorTopologies();

		assertThat(connectorTopologies, contains(ConnectorTopology.OUTGOING));
		assertThat(
			connectorTopologies,
			not(
				contains(
					ConnectorTopology.INCOMING, ConnectorTopology.NONE,
					ConnectorTopology.INCOMING_AND_OUTGOING)));
	}

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

}