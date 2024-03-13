/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.definition.Condition;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoConditionLocalService;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoConditionTableReferenceDefinitionTest
	extends BaseKaleoTableReferenceDefinitionTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_kaleoInstance = addKaleoInstance();

		_condition = new Condition(
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), "java", StringPool.BLANK);

		_kaleoNode = addKaleoNode(_kaleoInstance, _condition);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _kaleoConditionLocalService.addKaleoCondition(
			_kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(),
			_kaleoNode.getKaleoNodeId(), _condition, serviceContext);
	}

	private Condition _condition;

	@Inject
	private KaleoConditionLocalService _kaleoConditionLocalService;

	private KaleoInstance _kaleoInstance;
	private KaleoNode _kaleoNode;

}