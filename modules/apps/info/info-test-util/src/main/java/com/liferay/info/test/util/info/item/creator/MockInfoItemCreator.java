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
			long groupId, InfoItemFieldValues infoItemFieldValues)
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