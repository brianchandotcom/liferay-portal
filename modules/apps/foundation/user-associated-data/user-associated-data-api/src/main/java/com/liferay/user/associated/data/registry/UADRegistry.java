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
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exporter.UADEntityExporter;
import com.liferay.user.associated.data.util.UADEntitySetComposite;
import com.liferay.user.associated.data.util.UADEntityTypeComposite;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
* @author William Newbury
*/
public interface UADRegistry {

	public List<UADEntitySetComposite> getUADBundleComposites(long userId);

	public UADEntityAggregator getUADEntityAggregator(String key);

	public UADEntityAggregator getUADEntityAggregator(UADEntity uadEntity);

	public Set<String> getUADEntityAggregatorKeySet();

	public Collection<UADEntityAggregator> getUADEntityAggregators();

	public UADEntityAnonymizer getUADEntityAnonymizer(String key);

	public UADEntityAnonymizer getUADEntityAnonymizer(UADEntity uadEntity);

	public Set<String> getUADEntityAnonymizerKeySet();

	public Collection<UADEntityAnonymizer> getUADEntityAnonymizers();

	public UADEntityDisplay getUADEntityDisplay(String key);

	public UADEntityDisplay getUADEntityDisplay(UADEntity uadEntity);

	public Set<String> getUADEntityDisplayKeySet();

	public UADEntityExporter getUADEntityExporter(String key);

	public UADEntityExporter getUADEntityExporter(UADEntity uadEntity);

	public Set<String> getUADEntityExporterKeySet();

	public Collection<UADEntityExporter> getUADEntityExporters();

	public UADEntityTypeComposite getUADEntityTypeComposite(
		long userId, String key);

	public List<UADEntityTypeComposite> getUADEntityTypeComposites(
		long userId, String bundleId);

}