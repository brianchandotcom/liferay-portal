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

	public void propagateCategoryRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws Exception {

		long categoryId = GetterUtil.getLong(primKey);

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		List<Object> categoriesAndThreads =
			MBCategoryLocalServiceUtil.getCategoriesAndThreads(
				category.getGroupId(), categoryId);

		List<MBCategory> categories = new ArrayList<MBCategory>();
		List<MBMessage> messages = new ArrayList<MBMessage>();

		for (Object categoryOrThread : categoriesAndThreads) {
			if (categoryOrThread instanceof MBThread) {
				MBThread thread = (MBThread)categoryOrThread;

				messages.addAll(MBMessageLocalServiceUtil.getThreadMessages(
					thread.getThreadId(), WorkflowConstants.STATUS_ANY));
			}
			else {
				category = (MBCategory)categoryOrThread;

				List<Long> categoryIds = new ArrayList<Long>();

				categoryIds.add(category.getCategoryId());

				categoryIds = MBCategoryLocalServiceUtil.getSubcategoryIds(
					categoryIds, category.getGroupId(),
					category.getCategoryId());

				for (long addCategoryId : categoryIds) {
					categories.add(
						MBCategoryLocalServiceUtil.getCategory(addCategoryId));

					messages.addAll(
						MBMessageLocalServiceUtil.getCategoryMessages(
							category.getGroupId(), addCategoryId,
							WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
							QueryUtil.ALL_POS));
				}
			}
		}

		propagateRolePermissions(
			actionRequest, className, categoryId, roleIds, categories,
			messages);
	}

	public void propagateMBRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws Exception {

		long groupId = GetterUtil.getLong(primKey);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			groupId);
		List<MBMessage> messages = MBMessageLocalServiceUtil.getGroupMessages(
			groupId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		propagateRolePermissions(
			actionRequest, className, groupId, roleIds, categories, messages);
	}

	public void propagateRolePermissions(
			ActionRequest actionRequest, String className, long primaryKey,
			long[] roleIds, List<MBCategory> categories,
			List<MBMessage> messages)
		throws Exception {

		for (long roleId : roleIds) {
			for (MBCategory category : categories) {
				propagateRolePermissions(
					actionRequest, roleId, className, primaryKey,
					MBCategory.class.getName(), category.getPrimaryKey());
			}

			for (MBMessage message : messages) {
				propagateRolePermissions(
					actionRequest, roleId, className, primaryKey,
					MBMessage.class.getName(), message.getPrimaryKey());
			}
		}
	}

	public void propagateRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws Exception {

		if (className.equals(MBCategory.class.getName())) {
			propagateCategoryRolePermissions(
				actionRequest, className, primKey, roleIds);
		}
		else if (className.equals("com.liferay.portlet.messageboards")) {
			propagateMBRolePermissions(
				actionRequest, className, primKey, roleIds);
		}
	}

}