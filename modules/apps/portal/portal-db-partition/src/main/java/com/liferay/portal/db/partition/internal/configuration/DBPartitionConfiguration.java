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

package com.liferay.portal.db.partition.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alberto Chaparro
 */
@ExtendedObjectClassDefinition(category = "infrastructure", generateUI = false)
@Meta.OCD(
	id = "com.liferay.portal.db.partition.internal.configuration.DBPartitionConfiguration",
	localization = "content/Language", name = "db-partition-configuration-name"
)
public interface DBPartitionConfiguration {

	@Meta.AD(
		deflt = "liferay/adaptive_media_image_configuration|liferay/scheduler_engine|liferay/scheduler_scripting",
		description = "excluded-message-bus-destination-names-description",
		name = "excluded-message-bus-destination-names", required = false
	)
	public String[] excludedMessageBusDestinationNames();

	@Meta.AD(
		deflt = "com.liferay.analytics.settings.internal.messaging.CheckAnalyticsConnectionsMessageListener|com.liferay.portal.store.s3.AbortedMultipartUploadCleaner|com.liferay.server.admin.web.internal.messaging.PluginRepositoriesMessageListener",
		description = "excluded-scheduler-job-names-description",
		name = "excluded-scheduler-job-names", required = false
	)
	public String[] excludedSchedulerJobNames();

}