/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class WebsiteImpl extends WebsiteBaseImpl {

	public ListType getListType() throws PortalException {
		return ListTypeServiceUtil.getListType(getListTypeId());
	}

}