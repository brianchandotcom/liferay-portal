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

package com.liferay.talend.source;

import com.liferay.talend.configuration.LiferayInputMapperConfiguration;

import java.io.IOException;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.junit.SimpleComponentRule;
import org.talend.sdk.component.runtime.input.Mapper;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayInputMapperTest {

	@ClassRule
	public static final SimpleComponentRule COMPONENT_FACTORY =
		new SimpleComponentRule("com.liferay.talend");

	@Ignore("You need to complete this test")
	@Test
	public void testProduce() throws IOException {

		// Source configuration
		// Setup your component configuration for the test here

		final LiferayInputMapperConfiguration configuration =
			new LiferayInputMapperConfiguration();

		// .setRestDataSet()
		// .setTimeout()
		// .setBatchSize() */;

		// We create the component mapper instance using the configuration
		// filled above

		final Mapper mapper = COMPONENT_FACTORY.createMapper(
			LiferayInputMapper.class, configuration);

        // Collect the source as a list
		Assert.assertEquals(
			Arrays.asList(/* TODO - give the expected data */),
			COMPONENT_FACTORY.collectAsList(Record.class, mapper));
	}

}