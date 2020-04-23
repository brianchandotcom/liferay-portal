/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.comment.upgrade;

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Roberto Díaz
 */
public class UpgradeDiscussionSubscriptionClassName extends UpgradeProcess {

	public UpgradeDiscussionSubscriptionClassName(
		ClassNameLocalService classNameLocalService,
		SubscriptionLocalService subscriptionLocalService,
		String oldSubscriptionClassName, DeletionMode deletionMode) {

		_classNameLocalService = classNameLocalService;
		_subscriptionLocalService = subscriptionLocalService;
		_oldSubscriptionClassName = oldSubscriptionClassName;
		_deletionMode = deletionMode;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public UpgradeDiscussionSubscriptionClassName(
		SubscriptionLocalService subscriptionLocalService,
		String oldSubscriptionClassName, DeletionMode deletionMode) {

		this(
			ClassNameLocalServiceUtil.getService(), subscriptionLocalService,
			oldSubscriptionClassName, deletionMode);
	}

	public enum DeletionMode {

		/**
		 * @deprecated As of Athanasius (7.3.x), replaced by {@link #UPDATE}
		 */
		@Deprecated
		ADD_NEW,
		DELETE_OLD, UPDATE

	}

	@Override
	protected void doUpgrade() throws Exception {
		if (_deletionMode == DeletionMode.DELETE_OLD) {
			_deleteSubscriptions();
		}
		else {
			_updateSubscriptions();
		}
	}

	private void _deleteSubscriptions() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_subscriptionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"classNameId",
					_classNameLocalService.getClassNameId(
						_oldSubscriptionClassName))));
		actionableDynamicQuery.setPerformActionMethod(
			(Subscription subscription) ->
				_subscriptionLocalService.deleteSubscription(
					subscription.getSubscriptionId()));

		actionableDynamicQuery.performActions();
	}

	private void _updateSubscriptions() throws IOException, SQLException {
		String newSubscriptionClassName =
			MBDiscussion.class.getName() + StringPool.UNDERLINE +
				_oldSubscriptionClassName;

		long newClassNameId = ClassNameLocalServiceUtil.getClassNameId(
			newSubscriptionClassName);

		long oldClassNameId = ClassNameLocalServiceUtil.getClassNameId(
			_oldSubscriptionClassName);

		try (PreparedStatement ps = connection.prepareStatement(
				"select classPK from Subscription where classNameId = " +
					oldClassNameId);
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update AssetEntry set classNameId = ? where classNameId " +
						"= ? and classPK = ?"));
			PreparedStatement ps3 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update SocialActivity set classNameId = ? where " +
						"classNameId = ? and classPK = ?"));
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long classPK = rs.getLong("classPK");

				ps2.setLong(1, newClassNameId);
				ps2.setLong(2, oldClassNameId);
				ps2.setLong(3, classPK);

				ps2.addBatch();

				ps3.setLong(1, newClassNameId);
				ps3.setLong(2, oldClassNameId);
				ps3.setLong(3, classPK);

				ps3.addBatch();
			}

			ps2.executeBatch();

			ps3.executeBatch();
		}

		runSQL(
			StringBundler.concat(
				"update Subscription set classNameId = ", newClassNameId,
				" where classNameId = ", oldClassNameId));
	}

	private final ClassNameLocalService _classNameLocalService;
	private final DeletionMode _deletionMode;
	private final String _oldSubscriptionClassName;
	private final SubscriptionLocalService _subscriptionLocalService;

}