/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.definition.Task;
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

		KaleoNode kaleoNode = addKaleoNode(
			kaleoInstance, new Task("task", StringPool.BLANK));

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