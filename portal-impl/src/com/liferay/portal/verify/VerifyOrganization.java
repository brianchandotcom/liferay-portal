/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyOrganization extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			OrganizationLocalServiceUtil.rebuildTree(companyId);
			fixOrganizationsWithPagesAndNoSite(companyId);
		}
	}

	protected void fixOrganizationsWithPagesAndNoSite(long companyId)
		throws PortalException, SystemException {

		List<Group> organizationGroups = GroupLocalServiceUtil.getGroups(
			companyId, Organization.class.getName(), false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (Group organizationGroup : organizationGroups) {
			int publicPagesCount =
				organizationGroup.getPublicLayoutsPageCount();
			int privatePagesCount =
				organizationGroup.getPrivateLayoutsPageCount();

			if ((publicPagesCount > 0) || (privatePagesCount > 0)) {
				organizationGroup.setSite(true);

				GroupLocalServiceUtil.updateGroup(organizationGroup);
			}
		}
	}

}