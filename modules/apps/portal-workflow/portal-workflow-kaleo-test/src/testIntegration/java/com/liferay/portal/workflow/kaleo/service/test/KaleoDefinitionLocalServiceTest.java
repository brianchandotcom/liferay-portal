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

package com.liferay.portal.workflow.kaleo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class KaleoDefinitionLocalServiceTest
	extends BaseKaleoLocalServiceTestCase {

	@Test
	public void testAddKaleoDefinition() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		Assert.assertEquals(1, kaleoDefinition.getVersion());
	}

	@Test
	public void testDeactivateKaleoDefinition() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deactivateKaleoDefinition(kaleoDefinition);

		Assert.assertFalse(kaleoDefinition.isActive());
	}

	@Test(expected = WorkflowException.class)
	public void testDeleteKaleoDefinition1() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deleteKaleoDefinition(kaleoDefinition);
	}

	@Test(expected = NoSuchDefinitionException.class)
	public void testDeleteKaleoDefinition2() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deactivateKaleoDefinition(kaleoDefinition);

		deleteKaleoDefinition(kaleoDefinition);

		_kaleoDefinitionLocalService.getKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId());
	}

	@Test
	public void testUpdateKaleoDefinitionShouldIncrementVersion1()
		throws Exception {

		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		kaleoDefinition = updateKaleoDefinition(kaleoDefinition);

		Assert.assertEquals(2, kaleoDefinition.getVersion());
	}

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

}