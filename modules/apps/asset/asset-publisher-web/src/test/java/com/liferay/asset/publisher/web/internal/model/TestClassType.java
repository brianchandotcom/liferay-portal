/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.model;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public class TestClassType implements ClassType {

	public TestClassType(long classTypeId, String name) {
		_classTypeId = classTypeId;
		_name = name;
	}

	@Override
	public ClassTypeField getClassTypeField(String fieldName)
		throws PortalException {

		return null;
	}

	@Override
	public List<ClassTypeField> getClassTypeFields() throws PortalException {
		return null;
	}

	@Override
	public List<ClassTypeField> getClassTypeFields(int start, int end)
		throws PortalException {

		return null;
	}

	@Override
	public int getClassTypeFieldsCount() throws PortalException {
		return 0;
	}

	@Override
	public long getClassTypeId() {
		return _classTypeId;
	}

	@Override
	public String getName() {
		return _name;
	}

	private final long _classTypeId;
	private final String _name;

}