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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.persistence.GroupActionableDynamicQuery;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;
import com.liferay.portlet.social.service.persistence.SocialRequestActionableDynamicQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bryan Engler
 * @author Sherry Yang
 */
public class VerifySocial extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		ActionableDynamicQuery socialRequestActionableDynamicQuery =
			new SocialRequestActionableDynamicQuery() {

			@Override
			protected void performAction(Object object) throws SystemException {
				SocialRequest socialRequest = (SocialRequest)object;

				if (!_groupIds.contains(socialRequest.getClassPK()) &&
					(socialRequest.getClassNameId() ==
						PortalUtil.getClassNameId(Group.class))) {

					SocialRequestLocalServiceUtil.deleteRequest(socialRequest);
				}
			}

		};

		if (socialRequestActionableDynamicQuery.performCount() == 0) {
			return;
		}

		ActionableDynamicQuery groupActionableDynamicQuery =
			new GroupActionableDynamicQuery() {

			@Override
			protected void performAction(Object object) {
				Group group = (Group)object;

				_groupIds.add(group.getGroupId());
			}

		};

		for (long companyId : PortalInstances.getCompanyIdsBySQL()) {
			groupActionableDynamicQuery.setCompanyId(companyId);

			groupActionableDynamicQuery.performActions();
		}

		socialRequestActionableDynamicQuery.performActions();
	}

	private List<Long> _groupIds = new ArrayList<Long>();

}