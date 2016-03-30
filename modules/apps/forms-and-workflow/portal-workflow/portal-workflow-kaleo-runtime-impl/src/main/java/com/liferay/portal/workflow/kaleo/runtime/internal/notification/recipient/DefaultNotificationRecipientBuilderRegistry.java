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

import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.RecipientType;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.NotificationRecipientBuilder;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.NotificationRecipientBuilderRegistry;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, service = NotificationRecipientBuilderRegistry.class
)
public class DefaultNotificationRecipientBuilderRegistry
	implements NotificationRecipientBuilderRegistry {

	@Override
	public NotificationRecipientBuilder
		getNotificationRecipientBuilder(RecipientType recipientType) {

		NotificationRecipientBuilder notificationRecipientBuilder =
			_notificationRecipientBuilders.get(recipientType);

		if (notificationRecipientBuilder == null) {
			throw new IllegalArgumentException(
				"No notification recipient builder for " + recipientType);
		}

		return notificationRecipientBuilder;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeNotificationRecipientBuilder"
	)
	protected void addNotificationRecipientBuilder(
		NotificationRecipientBuilder notificationRecipientBuilder,
		Map<String, Object> properties) {

		RecipientType recipientType = _getRecipientType(
			notificationRecipientBuilder, properties);

		_notificationRecipientBuilders.put(
			recipientType, notificationRecipientBuilder);
	}

	protected void removeNotificationRecipientBuilder(
		NotificationRecipientBuilder notificationRecipientBuilder,
		Map<String, Object> properties) {

		RecipientType recipientType = _getRecipientType(
			notificationRecipientBuilder, properties);

		_notificationRecipientBuilders.remove(recipientType);
	}

	private RecipientType _getRecipientType(
		NotificationRecipientBuilder notificationRecipientBuilder,
		Map<String, Object> properties) {

		String value = (String)properties.get("recipient.type");

		if (Validator.isNull(value)) {
			throw new IllegalArgumentException(
				"The property \"recipient.type\" is invalid for " +
					ClassUtil.getClassName(notificationRecipientBuilder));
		}

		return RecipientType.valueOf(value);
	}

	private final Map<RecipientType, NotificationRecipientBuilder>
		_notificationRecipientBuilders = new HashMap<>();

}