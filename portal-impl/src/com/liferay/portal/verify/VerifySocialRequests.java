/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;

import java.util.List;
public class VerifySocialRequests extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			List<Group> groups = GroupLocalServiceUtil.getCompanyGroups(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			List<SocialRequest> requests =
				SocialRequestLocalServiceUtil.getSocialRequests(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (SocialRequest request : requests) {
				boolean deleteRequest = true;

				for (Group group : groups) {
					if ((request.getClassPK() == group.getGroupId()) &&
						(request.getClassNameId() == PortalUtil.getClassNameId(
							Group.class))) {

						deleteRequest = false;
						break;
					}
				}

				if (deleteRequest == true) {
					SocialRequestLocalServiceUtil.deleteRequest(request);
				}
			}
		}
	}

}