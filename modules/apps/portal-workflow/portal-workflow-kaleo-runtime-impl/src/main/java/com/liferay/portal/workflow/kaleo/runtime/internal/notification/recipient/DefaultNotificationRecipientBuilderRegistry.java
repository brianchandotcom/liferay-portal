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

package com.liferay.portal.workflow.kaleo.runtime.internal.notification.recipient;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.RecipientType;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.NotificationRecipientBuilder;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.NotificationRecipientBuilderRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, service = NotificationRecipientBuilderRegistry.class
)
public class DefaultNotificationRecipientBuilderRegistry
	implements NotificationRecipientBuilderRegistry {

	@Override
	public NotificationRecipientBuilder getNotificationRecipientBuilder(
		RecipientType recipientType) {

		NotificationRecipientBuilder notificationRecipientBuilder =
			_notificationRecipientBuilders.getService(recipientType);

		if (notificationRecipientBuilder == null) {
			throw new IllegalArgumentException(
				"No notification recipient builder for " + recipientType);
		}

		return notificationRecipientBuilder;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_notificationRecipientBuilders =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, NotificationRecipientBuilder.class, null,
				new ServiceReferenceMapper
					<RecipientType, NotificationRecipientBuilder>() {

					@Override
					public void map(
						ServiceReference<NotificationRecipientBuilder>
							serviceReference,
						ServiceReferenceMapper.Emitter<RecipientType> emitter) {

						Object value = serviceReference.getProperty(
							"recipient.type");

						if (Validator.isNull(value)) {
							throw new IllegalArgumentException(
								"The property \"recipient.type\" is invalid " +
									"for " + serviceReference);
						}

						emitter.emit(
							RecipientType.valueOf(String.valueOf(value)));
					}

				});
	}

	@Deactivate
	protected void deactivate() {
		_notificationRecipientBuilders.close();
	}

	private ServiceTrackerMap<RecipientType, NotificationRecipientBuilder>
		_notificationRecipientBuilders;

}