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
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoTransitionLocalService;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoTransitionTableReferenceDefinitionTest
	extends BaseKaleoTableReferenceDefinitionTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_kaleoInstance = addKaleoInstance();

		_kaleoNode = addKaleoNode(
			_kaleoInstance,
			new Task(RandomTestUtil.randomString(), StringPool.BLANK));

		_targetKaleoNode = addKaleoNode(
			_kaleoInstance,
			new Task(RandomTestUtil.randomString(), StringPool.BLANK));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		State startState = new State("start", StringPool.BLANK, true);

		return _kaleoTransitionLocalService.addKaleoTransition(
			_kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(),
			_kaleoNode.getKaleoNodeId(),
			new Transition(
				true, null, "review", startState,
				new Task("review", StringPool.BLANK)),
			_kaleoNode, _targetKaleoNode, serviceContext);
	}

	private KaleoInstance _kaleoInstance;
	private KaleoNode _kaleoNode;

	@Inject
	private KaleoTransitionLocalService _kaleoTransitionLocalService;

	private KaleoNode _targetKaleoNode;

}