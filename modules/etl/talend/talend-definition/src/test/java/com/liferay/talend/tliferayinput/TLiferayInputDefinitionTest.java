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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * @author Zoltán Takács
 */
public class TLiferayInputDefinitionTest {

	@Before
	public void setUp() {
		_tLiferayInputDefinition = new TLiferayInputDefinition();
	}

	@Test
	public void testGetFamilies() {
		String[] actualFamilies = _tLiferayInputDefinition.getFamilies();

		assertThat(Arrays.asList(actualFamilies), contains("Business/Liferay"));
	}

	@Test
	public void testGetPropertyClass() {
		Class<?> propertyClass = _tLiferayInputDefinition.getPropertyClass();

		String canonicalName = propertyClass.getCanonicalName();

		assertThat(
			canonicalName,
			equalTo(
				"com.liferay.talend.tliferayinput.TLiferayInputProperties"));
	}

	@Test
	public void testGetReturnProperties() {
		Property<?>[] returnProperties =
			_tLiferayInputDefinition.getReturnProperties();

		List<Property<?>> propertyList = Arrays.asList(returnProperties);

		assertThat(propertyList, hasSize(2));

		Assert.assertTrue(
			propertyList.contains(
				ComponentDefinition.RETURN_TOTAL_RECORD_COUNT_PROP));
		Assert.assertTrue(
			propertyList.contains(
				ComponentDefinition.RETURN_ERROR_MESSAGE_PROP));
	}

	@Test
	public void testGetRuntimeInfoForOutgoingTopology() {
		RuntimeInfo runtimeInfo = _tLiferayInputDefinition.getRuntimeInfo(
			ExecutionEngine.DI, null, ConnectorTopology.OUTGOING);

		String runtimeClassName = runtimeInfo.getRuntimeClassName();

		assertThat(
			runtimeClassName,
			equalTo("com.liferay.talend.runtime.LiferaySource"));
	}

	@Test
	public void testGetRuntimeInfoWrongEngine() {
		expectedException.expect(TalendRuntimeException.class);
		expectedException.expectMessage(
			"WRONG_EXECUTION_ENGINE:{component=tLiferayInput, " +
				"requested=DI_SPARK_STREAMING, available=[DI]}");

		_tLiferayInputDefinition.getRuntimeInfo(
			ExecutionEngine.DI_SPARK_STREAMING, null,
			ConnectorTopology.OUTGOING);
	}

	@Test
	public void testGetRuntimeInfoWrongTopology() {
		expectedException.expect(TalendRuntimeException.class);
		expectedException.expectMessage(
			"WRONG_CONNECTOR:{component=tLiferayInput}");

		_tLiferayInputDefinition.getRuntimeInfo(
			ExecutionEngine.DI, null, ConnectorTopology.INCOMING);
	}

	@Test
	public void testGetSupportedConnectorTopologies() {
		Set<ConnectorTopology> connectorTopologies =
			_tLiferayInputDefinition.getSupportedConnectorTopologies();

		assertThat(connectorTopologies, contains(ConnectorTopology.OUTGOING));
		assertThat(
			connectorTopologies,
			not(
				contains(
					ConnectorTopology.INCOMING, ConnectorTopology.NONE,
					ConnectorTopology.INCOMING_AND_OUTGOING)));
	}

	@Test
	public void testSupportsProperties() {
		TLiferayInputProperties tLiferayInputProperties =
			new TLiferayInputProperties("liferayInputProperties");

		Assert.assertTrue(
			_tLiferayInputDefinition.supportsProperties(
				tLiferayInputProperties));
	}

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	private TLiferayInputDefinition _tLiferayInputDefinition;

}