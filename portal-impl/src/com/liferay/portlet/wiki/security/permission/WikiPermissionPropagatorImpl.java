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

package com.liferay.portlet.wiki.security.permission;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.BasePermissionPropagator;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Hugo Huijser
 * @author Angelo Jefferson
 */
public class WikiPermissionPropagatorImpl extends BasePermissionPropagator {

	@Override
	public void propagateRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws PortalException, SystemException {

		if (!className.equals(WikiNode.class.getName())) {
			return;
		}

		long nodeId = GetterUtil.getLong(primKey);

		List<WikiPage> wikiPages = WikiPageLocalServiceUtil.getPages(
			nodeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (WikiPage wikiPage : wikiPages) {
			for (long roleId : roleIds) {
				propagateRolePermissions(
					actionRequest, roleId, WikiNode.class.getName(), nodeId,
					WikiPage.class.getName(), wikiPage.getResourcePrimKey());
			}
		}
	}

}