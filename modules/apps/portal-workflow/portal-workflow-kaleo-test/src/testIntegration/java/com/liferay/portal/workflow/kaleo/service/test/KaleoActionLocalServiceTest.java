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
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class KaleoActionLocalServiceTest extends BaseKaleoLocalServiceTestCase {

	@Test
	public void testGetKaleoActions() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		KaleoInstance kaleoInstance = addKaleoInstance();

		KaleoNode kaleoNode = addKaleoNode(kaleoInstance);

		KaleoAction kaleoAction = addKaleoAction(kaleoInstance, kaleoNode);

		List<KaleoAction> kaleoActions =
			kaleoActionLocalService.getKaleoActions(
				companyId, KaleoNode.class.getName(),
				kaleoNode.getKaleoNodeId());

		Assert.assertEquals(kaleoActions.toString(), 1, kaleoActions.size());
		Assert.assertEquals(kaleoAction, kaleoActions.get(0));

		kaleoActions = kaleoActionLocalService.getKaleoActions(
			companyId, KaleoNode.class.getName(), kaleoNode.getKaleoNodeId(),
			ExecutionType.ON_ASSIGNMENT.getValue());

		Assert.assertEquals(kaleoActions.toString(), 1, kaleoActions.size());
		Assert.assertEquals(kaleoAction, kaleoActions.get(0));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

}