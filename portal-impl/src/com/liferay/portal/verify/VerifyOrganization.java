/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyOrganization extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			OrganizationLocalServiceUtil.rebuildTree(companyId);
		}
	}

}