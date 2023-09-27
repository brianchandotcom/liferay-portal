/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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