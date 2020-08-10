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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionVersionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class KaleoDefinitionVersionLocalServiceTest
	extends BaseKaleoLocalServiceTestCase {

	@Test
	public void testAddKaleoDefinitionShouldCreateVersion() throws Exception {
		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				kaleoDefinition.getCompanyId(), kaleoDefinition.getName(),
				_getVersion(kaleoDefinition.getVersion()));

		Assert.assertEquals("1.0", kaleoDefinitionVersion.getVersion());
	}

	@Test(expected = NoSuchDefinitionVersionException.class)
	public void testDeleteKaleoDefinitionShouldDeleteVersion()
		throws Exception {

		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		deactivateKaleoDefinition(kaleoDefinition);

		deleteKaleoDefinition(kaleoDefinition);

		kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
			kaleoDefinition.getCompanyId(), kaleoDefinition.getName(),
			_getVersion(kaleoDefinition.getVersion()));
	}

	@Test
	public void testUpdateKaleoDefinitionShouldIncrementVersion1()
		throws Exception {

		KaleoDefinition kaleoDefinition = addKaleoDefinition();

		kaleoDefinition = updateKaleoDefinition(kaleoDefinition);

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				kaleoDefinition.getCompanyId(), kaleoDefinition.getName(),
				_getVersion(kaleoDefinition.getVersion()));

		Assert.assertEquals("2.0", kaleoDefinitionVersion.getVersion());
	}

	@Inject
	protected KaleoDefinitionVersionLocalService
		kaleoDefinitionVersionLocalService;

	private String _getVersion(int version) {
		return version + StringPool.PERIOD + 0;
	}

}