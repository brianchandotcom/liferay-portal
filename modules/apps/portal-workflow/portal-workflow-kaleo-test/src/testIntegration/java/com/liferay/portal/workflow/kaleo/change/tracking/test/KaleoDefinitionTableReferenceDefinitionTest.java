/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoDefinitionTableReferenceDefinitionTest
	extends BaseKaleoTableReferenceDefinitionTestCase {

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return addKaleoDefinition();
	}

}