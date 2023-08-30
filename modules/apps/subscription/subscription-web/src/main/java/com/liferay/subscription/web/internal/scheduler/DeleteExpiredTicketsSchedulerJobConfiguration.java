/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.web.internal.scheduler;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.web.internal.configuration.SubscriptionConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.subscription.web.internal.configuration.SubscriptionConfiguration",
	service = SchedulerJobConfiguration.class
)
public class DeleteExpiredTicketsSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			long subscriptionClassNameId =
				_classNameLocalService.getClassNameId(Subscription.class);

			ActionableDynamicQuery actionableDynamicQuery =
				_ticketLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> dynamicQuery.add(
					RestrictionsFactoryUtil.and(
						RestrictionsFactoryUtil.eq(
							"type", TicketConstants.TYPE_SUBSCRIPTION),
						RestrictionsFactoryUtil.eq(
							"classNameId", subscriptionClassNameId))));
			actionableDynamicQuery.setPerformActionMethod(
				(Ticket ticket) -> {
					if (ticket.isExpired()) {
						_ticketLocalService.deleteTicket(ticket);
					}
				});

			actionableDynamicQuery.performActions();
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_subscriptionConfiguration.deleteExpiredTicketsInterval(),
			TimeUnit.HOUR);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_subscriptionConfiguration = ConfigurableUtil.createConfigurable(
			SubscriptionConfiguration.class, properties);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private volatile SubscriptionConfiguration _subscriptionConfiguration;

	@Reference
	private TicketLocalService _ticketLocalService;

}