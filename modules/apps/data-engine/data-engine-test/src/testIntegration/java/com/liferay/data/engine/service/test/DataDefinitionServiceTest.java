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

package com.liferay.data.engine.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
public class DataDefinitionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Ignore
	@Test
	public void testDelete() throws Exception {
	}

	@Ignore
	@Test
	public void testDeleteWithoutPermission() throws Exception {
	}

	@Ignore
	@Test
	public void testGet() throws Exception {
	}

	@Ignore
	@Test
	public void testGetWithoutPermission() throws Exception {
	}

	@Ignore
	@Test
	public void testInsert() throws Exception {
	}

	@Ignore
	@Test
	public void testInsertWithoutPermission() throws Exception {
	}

	@Ignore
	@Test
	public void testUpdate() throws Exception {
	}

	@Ignore
	@Test
	public void testUpdateWithoutPermission() throws Exception {
	}

}