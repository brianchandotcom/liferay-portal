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

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.IndexerRegistryConfiguration",
	localization = "content/Language",
	name = "indexer-registry-configuration-name"
)
@ProviderType
public interface IndexerRegistryConfiguration {

	@Meta.AD(deflt = "true", name = "buffered", required = false)
	public boolean buffered();

	@Meta.AD(
		deflt = "DEFAULT", name = "buffered-execution-mode", required = false
	)
	public String bufferedExecutionMode();

	@Meta.AD(deflt = "10000", name = "max-buffer-size", required = false)
	public int maxBufferSize();

	@Meta.AD(
		deflt = "0.90", max = "0.99", min = "0.1",
		name = "minimum-buffer-availability-percentage", required = false
	)
	public float minimumBufferAvailabilityPercentage();

}