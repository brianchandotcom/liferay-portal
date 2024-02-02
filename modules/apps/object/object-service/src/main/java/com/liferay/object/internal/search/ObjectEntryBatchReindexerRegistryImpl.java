/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Feliphe Marinho
 * @author Gabriel Albuquerque
 */
@Component(service = ObjectEntryBatchReindexerRegistry.class)
public class ObjectEntryBatchReindexerRegistryImpl
	implements ObjectEntryBatchReindexerRegistry {

	@Override
	public Collection<ObjectEntryBatchReindexer>
		getObjectEntryBatchReindexers() {

		return _objectEntryBatchReindexers.toList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_objectEntryBatchReindexers = ServiceTrackerListFactory.open(
			bundleContext, ObjectEntryBatchReindexer.class);
	}

	@Deactivate
	protected void deactivate() {
		_objectEntryBatchReindexers.close();
	}

	private ServiceTrackerList<ObjectEntryBatchReindexer>
		_objectEntryBatchReindexers;

}