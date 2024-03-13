/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoTaskAssignmentInstanceTableReferenceDefinitionTest
	extends BaseKaleoTableReferenceDefinitionTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		KaleoInstance kaleoInstance = addKaleoInstance();

		KaleoNode kaleoNode = addKaleoNode(
			kaleoInstance,
			new Task(RandomTestUtil.randomString(), StringPool.BLANK));

		KaleoInstanceToken kaleoInstanceToken = addKaleoInstanceToken(
			kaleoInstance, kaleoNode);

		_kaleoTaskInstanceToken = addKaleoTaskInstanceToken(
			kaleoInstance, kaleoInstanceToken);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _kaleoTaskAssignmentInstanceLocalService.
			addKaleoTaskAssignmentInstance(
				TestPropsValues.getGroupId(), _kaleoTaskInstanceToken,
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
				serviceContext);
	}

	@Inject
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	private KaleoTaskInstanceToken _kaleoTaskInstanceToken;

}