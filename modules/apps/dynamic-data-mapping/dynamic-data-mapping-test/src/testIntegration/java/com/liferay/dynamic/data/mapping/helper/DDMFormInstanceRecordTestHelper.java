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

package com.liferay.dynamic.data.mapping.helper;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Lino Alves
 */
public class DDMFormInstanceRecordTestHelper {

	public DDMFormInstanceRecordTestHelper(
		Group group, DDMFormInstance ddmFormInstance) {

		_group = group;
		_ddmFormInstance = ddmFormInstance;
	}

	public DDMFormInstanceRecord addDDMFormInstanceRecord(
			DDMFormValues ddmFormValues, int actionPublish)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return DDMFormInstanceRecordLocalServiceUtil.addFormInstanceRecord(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_ddmFormInstance.getFormInstanceId(), ddmFormValues,
			serviceContext);
	}

	public DDMFormInstance getDDMFormInstance() {
		return _ddmFormInstance;
	}

	private final DDMFormInstance _ddmFormInstance;
	private final Group _group;

}