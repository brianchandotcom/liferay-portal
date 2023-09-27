/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.internal.scheduler;

import com.liferay.commerce.wish.list.internal.configuration.CommerceWishListConfiguration;
import com.liferay.commerce.wish.list.service.CommerceWishListLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.wish.list.internal.configuration.CommerceWishListConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckGuestCommerceWishListsSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			int deleteInterval =
				_commerceWishListConfiguration.deleteInterval();

			Date createDate = new Date(
				System.currentTimeMillis() - (deleteInterval * Time.MINUTE));

			_commerceWishListLocalService.deleteCommerceWishLists(
				UserConstants.USER_ID_DEFAULT, createDate);
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_commerceWishListConfiguration.checkInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceWishListConfiguration = ConfigurableUtil.createConfigurable(
			CommerceWishListConfiguration.class, properties);
	}

	private volatile CommerceWishListConfiguration
		_commerceWishListConfiguration;

	@Reference
	private CommerceWishListLocalService _commerceWishListLocalService;

}