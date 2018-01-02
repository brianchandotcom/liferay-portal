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

package com.liferay.user.associated.data.registry;

import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.exporter.UADEntityExporter;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(immediate = true)
public class UADRegistryUtil {

	public static UADEntityAggregator getUADEntityAggregator(String key) {
		return _uadRegistry.getUADEntityAggregator(key);
	}

	public static Collection<UADEntityAggregator> getUADEntityAggregators() {
		return _uadRegistry.getUADEntityAggregators();
	}

	public static UADEntityAnonymizer getUADEntityAnonymizer(String key) {
		return _uadRegistry.getUADEntityAnonymizer(key);
	}

	public static Collection<UADEntityAnonymizer> getUADEntityAnonymizers() {
		return _uadRegistry.getUADEntityAnonymizers();
	}

	public static UADEntityExporter getUADEntityExporter(String key) {
		return _uadRegistry.getUADEntityExporter(key);
	}

	public static Collection<UADEntityExporter> getUADEntityExporters() {
		return _uadRegistry.getUADEntityExporters();
	}

	public static void notify(long userId) {
		_uadRegistry.notify(userId);
	}

	@Reference(unbind = "-")
	protected void setUADRegistry(UADRegistry uadRegistry) {
		_uadRegistry = uadRegistry;
	}

	private static UADRegistry _uadRegistry;

}