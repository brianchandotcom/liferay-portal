/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.definition.UserAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentLocalService;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoTaskAssignmentTableReferenceDefinitionTest
	extends BaseKaleoTableReferenceDefinitionTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_kaleoInstance = addKaleoInstance();
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _kaleoTaskAssignmentLocalService.addKaleoTaskAssignment(
			KaleoNode.class.getName(), _kaleoInstance.getClassPK(),
			_kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(), new UserAssignment(),
			serviceContext);
	}

	private KaleoInstance _kaleoInstance;

	@Inject
	private KaleoTaskAssignmentLocalService _kaleoTaskAssignmentLocalService;

}