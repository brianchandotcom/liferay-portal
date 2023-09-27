/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.helper;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
public class DDLRecordTestUtil {

	public static String getBasePath() {
		return "com/liferay/dynamic/data/lists/dependencies/";
	}

	public static ServiceContext getServiceContext(int workflowAction)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setUserId(TestPropsValues.getUserId());
		serviceContext.setWorkflowAction(workflowAction);

		return serviceContext;
	}

	public static String read(Class<?> testClass, String fileName)
		throws Exception {

		return StringUtil.read(
			testClass.getClassLoader(), getBasePath() + fileName);
	}

}