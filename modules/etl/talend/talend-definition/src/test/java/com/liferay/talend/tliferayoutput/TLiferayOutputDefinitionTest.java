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

package com.liferay.talend.tliferayoutput;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import static org.talend.components.api.component.ComponentDefinition.RETURN_ERROR_MESSAGE_PROP;
import static org.talend.components.api.component.ComponentDefinition.RETURN_REJECT_RECORD_COUNT_PROP;
import static org.talend.components.api.component.ComponentDefinition.RETURN_SUCCESS_RECORD_COUNT_PROP;
import static org.talend.components.api.component.ComponentDefinition.RETURN_TOTAL_RECORD_COUNT_PROP;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
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
public class TLiferayOutputDefinitionTest {

	@Before
	public void setUp() {
		_tLiferayOutputDefinition = new TLiferayOutputDefinition();
	}

	@Test
	public void testGetFamilies() {
		String[] actualFamilies = _tLiferayOutputDefinition.getFamilies();

		assertThat(Arrays.asList(actualFamilies), contains("Business/Liferay"));
	}

	@Test
	public void testGetPropertyClass() {
		Class<?> propertyClass = _tLiferayOutputDefinition.getPropertyClass();

		String canonicalName = propertyClass.getCanonicalName();

		assertThat(
			canonicalName,
			equalTo(
				"com.liferay.talend.tliferayoutput.TLiferayOutputProperties"));
	}

	@Test
	public void testGetReturnProperties() {
		Property<?>[] returnProperties =
			_tLiferayOutputDefinition.getReturnProperties();

		List<Property<?>> propertyList = Arrays.asList(returnProperties);

		assertThat(propertyList, hasSize(4));

		Assert.assertTrue(propertyList.contains(RETURN_ERROR_MESSAGE_PROP));
		Assert.assertTrue(
			propertyList.contains(RETURN_REJECT_RECORD_COUNT_PROP));
		Assert.assertTrue(
			propertyList.contains(RETURN_SUCCESS_RECORD_COUNT_PROP));
		Assert.assertTrue(
			propertyList.contains(RETURN_TOTAL_RECORD_COUNT_PROP));
	}

	@Test
	public void testGetRuntimeInfoForIncomingTopology() {
		RuntimeInfo runtimeInfo = _tLiferayOutputDefinition.getRuntimeInfo(
			ExecutionEngine.DI, null, ConnectorTopology.INCOMING);

		String runtimeClassName = runtimeInfo.getRuntimeClassName();

		assertThat(
			runtimeClassName,
			equalTo("com.liferay.talend.runtime.LiferaySink"));
	}

	@Test
	public void testGetRuntimeInfoWrongEngine() {
		expectedException.expect(TalendRuntimeException.class);
		expectedException.expectMessage(
			"WRONG_EXECUTION_ENGINE:{component=tLiferayOutput, " +
				"requested=DI_SPARK_STREAMING, available=[DI]}");

		_tLiferayOutputDefinition.getRuntimeInfo(
			ExecutionEngine.DI_SPARK_STREAMING, null,
			ConnectorTopology.INCOMING);
	}

	@Test
	public void testGetRuntimeInfoWrongTopology() {
		expectedException.expect(TalendRuntimeException.class);
		expectedException.expectMessage(
			"WRONG_CONNECTOR:{component=tLiferayOutput}");

		_tLiferayOutputDefinition.getRuntimeInfo(
			ExecutionEngine.DI, null, ConnectorTopology.OUTGOING);
	}

	@Test
	public void testGetSupportedConnectorTopologies() {
		Set<ConnectorTopology> connectorTopologies =
			_tLiferayOutputDefinition.getSupportedConnectorTopologies();

		assertThat(
			connectorTopologies,
			contains(
				ConnectorTopology.INCOMING,
				ConnectorTopology.INCOMING_AND_OUTGOING));
		assertThat(
			connectorTopologies,
			not(contains(ConnectorTopology.OUTGOING, ConnectorTopology.NONE)));
	}

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	private TLiferayOutputDefinition _tLiferayOutputDefinition;

}