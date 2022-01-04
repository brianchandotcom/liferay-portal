/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.akismet.internal.util;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Jamie Sammons
 */
public class ModerationUtil {

	public static List<MBMessage> getMBMessages(
			long scopeGroupId, int start, int end)
		throws PortalException {

		DynamicQuery dynamicQuery = _buildMBMessageDynamicQuery(
			scopeGroupId, false);

		return MBMessageLocalServiceUtil.dynamicQuery(dynamicQuery, start, end);
	}

	public static int getMBMessagesCount(long scopeGroupId)
		throws PortalException {

		DynamicQuery dynamicQuery = _buildMBMessageDynamicQuery(
			scopeGroupId, false);

		return (int)MBMessageLocalServiceUtil.dynamicQueryCount(dynamicQuery);
	}

	private static DynamicQuery _buildMBMessageDynamicQuery(
			long scopeGroupId, boolean discussion)
		throws PortalException {

		MBMessageLocalService mbMessageLocalService =
			MBMessageLocalServiceUtil.getService();

		Class<?> clazz = mbMessageLocalService.getClass();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBMessage.class, clazz.getClassLoader());

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		if (!group.isCompany()) {
			Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

			Long[] scopeGroupIds = _getChildScopeGroupIds(scopeGroupId);

			dynamicQuery.add(groupIdProperty.in(scopeGroupIds));
		}

		Property categoryIdProperty = PropertyFactoryUtil.forName("categoryId");

		if (discussion) {
			dynamicQuery.add(
				categoryIdProperty.eq(
					MBCategoryConstants.DISCUSSION_CATEGORY_ID));
		}
		else {
			dynamicQuery.add(
				categoryIdProperty.ne(
					MBCategoryConstants.DISCUSSION_CATEGORY_ID));
		}

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(WorkflowConstants.STATUS_DENIED));

		return dynamicQuery;
	}

	private static Long[] _getChildScopeGroupIds(long parentGroupId) {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Group.class, PortalClassLoaderUtil.getClassLoader());

		Property parentGroupIdProperty = PropertyFactoryUtil.forName(
			"parentGroupId");

		dynamicQuery.add(parentGroupIdProperty.eq(parentGroupId));

		List<Group> groups = GroupLocalServiceUtil.dynamicQuery(dynamicQuery);

		Long[] scopeGroupIds = new Long[groups.size() + 1];

		scopeGroupIds[0] = parentGroupId;

		for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);

			scopeGroupIds[i + 1] = group.getGroupId();
		}

		return scopeGroupIds;
	}

}