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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bryan Engler
 */
public class VerifySocial extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<SocialRequest> requests =
			SocialRequestLocalServiceUtil.getSocialRequests(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (requests.isEmpty()) {
			return;
		}

		List<Group> groups = new ArrayList<Group>();

		for (long companyId : PortalInstances.getCompanyIdsBySQL()) {
			groups.addAll(
				GroupLocalServiceUtil.getCompanyGroups(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS));
		}

		List<Long> groupIds = new ArrayList<Long>();

		for (Group group : groups) {
			groupIds.add(group.getGroupId());
		}

		for (SocialRequest request : requests) {
			if (!groupIds.contains(request.getClassPK()) &&
				(request.getClassNameId() ==
					PortalUtil.getClassNameId(Group.class))) {

				SocialRequestLocalServiceUtil.deleteRequest(request);
			}
		}
	}

}