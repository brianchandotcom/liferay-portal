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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

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

		MatcherAssert.assertThat(
			Arrays.asList(actualFamilies),
			Matchers.contains("Business/Liferay"));
	}

	@Test
	public void testGetPropertyClass() {
		Class<?> propertyClass = _tLiferayInputDefinition.getPropertyClass();

		MatcherAssert.assertThat(
			propertyClass.getCanonicalName(),
			Matchers.equalTo(
				"com.liferay.talend.tliferayinput.TLiferayInputProperties"));
	}

	@Test
	public void testGetReturnProperties() {
		List<Property<?>> propertyList = Arrays.asList(
			_tLiferayInputDefinition.getReturnProperties());

		MatcherAssert.assertThat(propertyList, Matchers.hasSize(2));

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

		MatcherAssert.assertThat(
			runtimeInfo.getRuntimeClassName(),
			Matchers.equalTo("com.liferay.talend.runtime.LiferaySource"));
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
		Assert.assertNull(
			_tLiferayInputDefinition.getRuntimeInfo(
				ExecutionEngine.DI, null, ConnectorTopology.INCOMING));
	}

	@Test
	public void testGetSupportedConnectorTopologies() {
		Set<ConnectorTopology> connectorTopologies =
			_tLiferayInputDefinition.getSupportedConnectorTopologies();

		MatcherAssert.assertThat(
			connectorTopologies, Matchers.contains(ConnectorTopology.OUTGOING));
		MatcherAssert.assertThat(
			connectorTopologies,
			Matchers.not(
				Matchers.contains(
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