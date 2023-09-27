/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.test.util.info.item.creator;

import com.liferay.info.exception.InfoFormException;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.info.test.util.model.MockObject;

/**
 * @author Lourdes Fernández Besada
 */
public class MockInfoItemCreator implements InfoItemCreator<MockObject> {

	@Override
	public MockObject createFromInfoItemFieldValues(
			long groupId, InfoItemFieldValues infoItemFieldValues, int status)
		throws InfoFormException {

		if (_infoFormException != null) {
			throw _infoFormException;
		}

		return _mockObject;
	}

	public InfoFormException getInfoFormException() {
		return _infoFormException;
	}

	public MockObject getMockObject() {
		return _mockObject;
	}

	public void setInfoFormException(InfoFormException infoFormException) {
		_infoFormException = infoFormException;
	}

	public void setMockObject(MockObject mockObject) {
		_mockObject = mockObject;
	}

	private InfoFormException _infoFormException;
	private MockObject _mockObject;

}