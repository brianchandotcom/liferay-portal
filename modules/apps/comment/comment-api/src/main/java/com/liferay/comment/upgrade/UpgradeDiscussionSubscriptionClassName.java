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

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalService;

/**
 * @author Roberto Díaz
 */
public class UpgradeDiscussionSubscriptionClassName extends UpgradeProcess {

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public UpgradeDiscussionSubscriptionClassName(
		AssetEntryLocalService assetEntryLocalService,
		ClassNameLocalService classNameLocalService,
		SubscriptionLocalService subscriptionLocalService,
		String oldSubscriptionClassName, DeletionMode deletionMode) {

		this(
			classNameLocalService, subscriptionLocalService,
			oldSubscriptionClassName, deletionMode);
	}

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

	private void _deleteSubscriptions() throws Exception {
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

	private void _updateSubscriptions() throws Exception {
		String newSubscriptionClassName =
			MBDiscussion.class.getName() + StringPool.UNDERLINE +
				_oldSubscriptionClassName;

		runSQL(
			StringBundler.concat(
				"update Subscription set classNameId = ",
				ClassNameLocalServiceUtil.getClassNameId(
					newSubscriptionClassName),
				" where classNameId = ",
				ClassNameLocalServiceUtil.getClassNameId(
					_oldSubscriptionClassName)));
	}

	private final ClassNameLocalService _classNameLocalService;
	private final DeletionMode _deletionMode;
	private final String _oldSubscriptionClassName;
	private final SubscriptionLocalService _subscriptionLocalService;

}