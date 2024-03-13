/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.workflow.kaleo.definition.Assignment;
import com.liferay.portal.workflow.kaleo.definition.RoleAssignment;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;

import java.util.HashSet;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoTaskFormTableReferenceDefinitionTest
	extends BaseKaleoTableReferenceDefinitionTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_kaleoInstance = addKaleoInstance();

		Task task = new Task(RandomTestUtil.randomString(), StringPool.BLANK);

		task.setAssignments(
			new HashSet<Assignment>() {
				{
					add(
						new RoleAssignment(
							RoleConstants.ADMINISTRATOR,
							RoleConstants.TYPE_REGULAR_LABEL));
				}
			});

		_kaleoNode = addKaleoNode(_kaleoInstance, task);

		_kaleoTask = addKaleoTask(_kaleoInstance, _kaleoNode, task);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return addKaleoTaskForm(_kaleoInstance, _kaleoNode, _kaleoTask);
	}

	private KaleoInstance _kaleoInstance;
	private KaleoNode _kaleoNode;
	private KaleoTask _kaleoTask;

}