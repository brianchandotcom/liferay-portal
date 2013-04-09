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

package com.liferay.portlet.messageboards.security.permission;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.BasePermissionPropagator;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Kenneth Chang
 */
public class MBPermissionPropagatorImpl extends BasePermissionPropagator {

	public void propagateRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws Exception {

		if (!className.equals(MBCategory.class.getName()) &&
			!className.equals(_mbModelResource)) {

			return;
		}

		long parentPrimKey = GetterUtil.getLong(primKey);

		List<MBCategory> categories = null;

		List<MBMessage> messages = null;

		String parentClassName = null;

		if (className.equals(_mbModelResource)) {
			categories = MBCategoryLocalServiceUtil.getMBCategories(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			messages = MBMessageLocalServiceUtil.getGroupMessages(
				parentPrimKey, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			parentClassName = _mbModelResource;
		}
		else {
			MBCategory category = MBCategoryLocalServiceUtil.getCategory(
				parentPrimKey);

			long groupId = category.getGroupId();

			List<Object> categoriesAndThreads =
				MBCategoryLocalServiceUtil.getCategoriesAndThreads(
					groupId, parentPrimKey);

			categories = new ArrayList<MBCategory>();
			messages = new ArrayList<MBMessage>();

			for (Object categoryOrThread : categoriesAndThreads) {
				if (categoryOrThread instanceof MBThread) {
					long threadId = ((MBThread)categoryOrThread).getThreadId();

					List<MBMessage> threadMessages =
						MBMessageLocalServiceUtil.getThreadMessages(
							threadId, WorkflowConstants.STATUS_ANY);

					messages.addAll(threadMessages);
				}
				else {
					long categoryId =
						((MBCategory)categoryOrThread).getCategoryId();

					List<Long> categoryIds = ListUtil.toList(
						new long[]{categoryId});

					List<Long> addCategoryIds =
						MBCategoryLocalServiceUtil.getSubcategoryIds(
							categoryIds, groupId, categoryId);

					for (Long addCategoryId : addCategoryIds) {
						categories.add(
							MBCategoryLocalServiceUtil.getCategory(
								addCategoryId));

						messages.addAll(
							MBMessageLocalServiceUtil.getCategoryMessages(
								groupId, addCategoryId,
								WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
								QueryUtil.ALL_POS));
					}
				}
			}

			parentClassName = MBCategory.class.getName();
		}

		for (long roleId : roleIds) {
			for (MBCategory category : categories) {
				propagateRolePermissions(
					actionRequest, roleId, parentClassName, parentPrimKey,
					MBCategory.class.getName(), category.getPrimaryKey());
			}

			for (MBMessage message : messages) {
				propagateRolePermissions(
					actionRequest, roleId, parentClassName, parentPrimKey,
					MBMessage.class.getName(), message.getPrimaryKey());
			}
		}
	}

	private final String _mbModelResource = "com.liferay.portlet.messageboards";

}