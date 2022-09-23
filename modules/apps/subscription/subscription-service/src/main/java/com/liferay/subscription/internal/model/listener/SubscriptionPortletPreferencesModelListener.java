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

package com.liferay.subscription.internal.model.listener;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.CopyLayoutThreadLocal;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ModelListener.class)
public class SubscriptionPortletPreferencesModelListener
	extends BaseModelListener<PortletPreferences> {

	@Override
	public void onAfterRemove(PortletPreferences portletPreferences) {
		deleteSubscriptions(portletPreferences);
	}

	protected void deleteSubscriptions(PortletPreferences portletPreferences) {
		if (portletPreferences == null) {
			return;
		}

		try {
			if (!CopyLayoutThreadLocal.isCopyLayout()) {
				_subscriptionLocalService.deleteSubscriptions(
					portletPreferences.getCompanyId(),
					portletPreferences.getModelClassName(),
					portletPreferences.getPortletPreferencesId());

				return;
			}

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					PortletPreferences remainingPortletPreferences =
						_portletPreferencesLocalService.fetchPortletPreferences(
							portletPreferences.getOwnerId(),
							portletPreferences.getOwnerType(),
							portletPreferences.getPlid(),
							portletPreferences.getPortletId());

					if (remainingPortletPreferences == null) {
						_subscriptionLocalService.deleteSubscriptions(
							portletPreferences.getCompanyId(),
							portletPreferences.getModelClassName(),
							portletPreferences.getPortletPreferencesId());

						return null;
					}

					_subscriptionLocalService.updateSubscriptions(
						remainingPortletPreferences.getCompanyId(),
						_classNameLocalService.getClassNameId(
							remainingPortletPreferences.getModelClassName()),
						portletPreferences.getPortletPreferencesId(),
						remainingPortletPreferences.getPortletPreferencesId());

					return null;
				});
		}
		catch (Exception exception) {
			_log.error("Unable to delete subscriptions", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SubscriptionPortletPreferencesModelListener.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}