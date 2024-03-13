/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoDefinitionVersionTableReferenceDefinitionTest
	extends BaseKaleoTableReferenceDefinitionTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_kaleoDefinition = addKaleoDefinition();
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _kaleoDefinitionVersionLocalService.addKaleoDefinitionVersion(
			_kaleoDefinition.getKaleoDefinitionId(), _kaleoDefinition.getName(),
			_kaleoDefinition.getTitle(), _kaleoDefinition.getDescription(),
			_kaleoDefinition.getContent(),
			String.valueOf(_kaleoDefinition.getVersion()), serviceContext);
	}

	private KaleoDefinition _kaleoDefinition;

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

}