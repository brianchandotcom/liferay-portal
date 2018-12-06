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

package com.liferay.portal.messaging.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(
	category = "infrastructure",
	factoryInstanceLabelAttribute = "destinationName"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.messaging.internal.configuration.DestinationWorkerConfiguration",
	localization = "content/Language",
	name = "destination-workfer-configuration-name"
)
public interface DestinationWorkerConfiguration {

	@Meta.AD(deflt = "", name = "destination-name")
	public String destinationName();

	@Meta.AD(
		deflt = "-1", description = "max-queue-size-help",
		name = "max-queue-size", required = false
	)
	public int maxQueueSize();

	@Meta.AD(
		deflt = "2", description = "worker-core-size-help",
		name = "worker-core-size", required = false
	)
	public int workerCoreSize();

	@Meta.AD(
		deflt = "5", description = "worker-max-size-help",
		name = "worker-max-size", required = false
	)
	public int workerMaxSize();

}