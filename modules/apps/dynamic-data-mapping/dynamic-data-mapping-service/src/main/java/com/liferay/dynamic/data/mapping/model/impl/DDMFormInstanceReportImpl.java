/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marcos Martins
 */
public class DDMFormInstanceReportImpl extends DDMFormInstanceReportBaseImpl {

	@Override
	public DDMFormInstance getFormInstance() throws PortalException {
		return DDMFormInstanceLocalServiceUtil.getFormInstance(
			getFormInstanceId());
	}

}