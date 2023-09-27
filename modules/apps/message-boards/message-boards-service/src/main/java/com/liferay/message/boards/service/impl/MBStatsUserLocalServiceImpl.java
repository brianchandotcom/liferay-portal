/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.impl;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBMessageTable;
import com.liferay.message.boards.model.MBStatsUser;
import com.liferay.message.boards.model.MBThreadTable;
import com.liferay.message.boards.model.impl.MBStatsUserImpl;
import com.liferay.message.boards.service.base.MBStatsUserLocalServiceBaseImpl;
import com.liferay.message.boards.service.persistence.MBMessagePersistence;
import com.liferay.message.boards.service.persistence.MBThreadPersistence;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBStatsUser",
	service = AopService.class
)
public class MBStatsUserLocalServiceImpl
	extends MBStatsUserLocalServiceBaseImpl {

	@Override
	public Date getLastPostDateByUserId(long groupId, long userId) {
		List<Date> results = _mbThreadPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				DSLFunctionFactoryUtil.max(
					MBThreadTable.INSTANCE.lastPostDate
				).as(
					"maxLastPostDate"
				)
			).from(
				MBThreadTable.INSTANCE
			).where(
				MBThreadTable.INSTANCE.userId.eq(
					userId
				).and(
					MBThreadTable.INSTANCE.status.neq(
						WorkflowConstants.STATUS_IN_TRASH)
				)
			));

		return results.get(0);
	}

	@Override
	public int getMessageCount(long groupId, long userId) {
		return _mbMessagePersistence.countByG_U_S(
			groupId, userId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public long getMessageCountByGroupId(long groupId) throws PortalException {
		Group group = _groupLocalService.getGroup(groupId);

		return _mbMessagePersistence.dslQuery(
			DSLQueryFactoryUtil.count(
			).from(
				MBMessageTable.INSTANCE
			).where(
				MBMessageTable.INSTANCE.groupId.eq(
					groupId
				).and(
					MBMessageTable.INSTANCE.userId.neq(
						_userLocalService.getGuestUserId(group.getCompanyId()))
				).and(
					MBMessageTable.INSTANCE.categoryId.neq(
						MBCategoryConstants.DISCUSSION_CATEGORY_ID)
				).and(
					MBMessageTable.INSTANCE.status.eq(
						WorkflowConstants.STATUS_APPROVED)
				)
			));
	}

	@Override
	public long getMessageCountByUserId(long userId) {
		return _mbMessagePersistence.dslQuery(
			DSLQueryFactoryUtil.count(
			).from(
				MBMessageTable.INSTANCE
			).where(
				MBMessageTable.INSTANCE.userId.eq(
					userId
				).and(
					MBMessageTable.INSTANCE.categoryId.neq(
						MBCategoryConstants.DISCUSSION_CATEGORY_ID)
				).and(
					MBMessageTable.INSTANCE.status.eq(
						WorkflowConstants.STATUS_APPROVED)
				)
			));
	}

	@Override
	public List<MBStatsUser> getStatsUsersByGroupId(
			long groupId, int start, int end)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		long guestUserId = _userLocalService.getGuestUserId(
			group.getCompanyId());

		Expression<Long> countExpression = DSLFunctionFactoryUtil.count(
			MBMessageTable.INSTANCE.messageId
		).as(
			"messageCount"
		);

		List<Object[]> rows = _mbMessagePersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				MBMessageTable.INSTANCE.userId, countExpression,
				DSLFunctionFactoryUtil.max(
					MBMessageTable.INSTANCE.modifiedDate
				).as(
					"lastPostDate"
				)
			).from(
				MBMessageTable.INSTANCE
			).where(
				MBMessageTable.INSTANCE.groupId.eq(
					groupId
				).and(
					MBMessageTable.INSTANCE.userId.neq(guestUserId)
				).and(
					MBMessageTable.INSTANCE.categoryId.neq(
						MBCategoryConstants.DISCUSSION_CATEGORY_ID)
				).and(
					MBMessageTable.INSTANCE.status.eq(
						WorkflowConstants.STATUS_APPROVED)
				)
			).groupBy(
				MBMessageTable.INSTANCE.userId
			).orderBy(
				countExpression.descending()
			).limit(
				start, end
			));

		List<MBStatsUser> mbStatsUsers = new ArrayList<>(rows.size());

		for (Object[] columns : rows) {
			Long userId = (Long)columns[0];
			Long messageCount = (Long)columns[1];
			Date lastPostDate = (Date)columns[2];

			mbStatsUsers.add(
				new MBStatsUserImpl(
					userId, messageCount.intValue(), lastPostDate));
		}

		return mbStatsUsers;
	}

	@Override
	public int getStatsUsersByGroupIdCount(long groupId)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		return _mbMessagePersistence.dslQueryCount(
			DSLQueryFactoryUtil.countDistinct(
				MBMessageTable.INSTANCE.userId
			).from(
				MBMessageTable.INSTANCE
			).where(
				MBMessageTable.INSTANCE.groupId.eq(
					groupId
				).and(
					MBMessageTable.INSTANCE.userId.neq(
						_userLocalService.getGuestUserId(group.getCompanyId()))
				).and(
					MBMessageTable.INSTANCE.categoryId.neq(
						MBCategoryConstants.DISCUSSION_CATEGORY_ID)
				).and(
					MBMessageTable.INSTANCE.status.eq(
						WorkflowConstants.STATUS_APPROVED)
				)
			));
	}

	@Override
	public String[] getUserRank(long groupId, String languageId, long userId)
		throws PortalException {

		MBGroupServiceSettings mbGroupServiceSettings =
			MBGroupServiceSettings.getInstance(groupId);

		String[] rank = {StringPool.BLANK, StringPool.BLANK};

		int maxPosts = 0;

		User user = _userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		String[] ranks = mbGroupServiceSettings.getRanks(languageId);

		int messageCount = getMessageCount(groupId, userId);

		for (String curRank : ranks) {
			String[] kvp = StringUtil.split(curRank, CharPool.EQUAL);

			String kvpPosts = kvp[1];

			String[] curRankValueKvp = StringUtil.split(
				kvpPosts, CharPool.COLON);

			if (curRankValueKvp.length <= 1) {
				int posts = GetterUtil.getInteger(kvpPosts);

				if ((posts <= messageCount) && (posts >= maxPosts)) {
					rank[0] = kvp[0];
					maxPosts = posts;
				}
			}
			else {
				String entityType = curRankValueKvp[0];
				String entityValue = curRankValueKvp[1];

				try {
					if (_isEntityRank(
							companyId, user, entityType, entityValue)) {

						rank[1] = curRank;

						break;
					}
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}
				}
			}
		}

		return rank;
	}

	private boolean _isEntityRank(
			long companyId, User statsUser, String entityType,
			String entityValue)
		throws Exception {

		long userId = statsUser.getUserId();

		if (entityType.equals("organization-role") ||
			entityType.equals("site-role")) {

			Role role = _roleLocalService.getRole(companyId, entityValue);

			if (_userGroupRoleLocalService.hasUserGroupRole(
					userId, statsUser.getGroupId(), role.getRoleId(), true)) {

				return true;
			}
		}
		else if (entityType.equals("organization")) {
			Organization organization =
				_organizationLocalService.getOrganization(
					companyId, entityValue);

			if (_organizationLocalService.hasUserOrganization(
					userId, organization.getOrganizationId(), false, false)) {

				return true;
			}
		}
		else if (entityType.equals("regular-role")) {
			if (_roleLocalService.hasUserRole(
					userId, companyId, entityValue, true)) {

				return true;
			}
		}
		else if (entityType.equals("user-group")) {
			UserGroup userGroup = _userGroupLocalService.getUserGroup(
				companyId, entityValue);

			if (_userLocalService.hasUserGroupUser(
					userGroup.getUserGroupId(), userId)) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBStatsUserLocalServiceImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private MBMessagePersistence _mbMessagePersistence;

	@Reference
	private MBThreadPersistence _mbThreadPersistence;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}