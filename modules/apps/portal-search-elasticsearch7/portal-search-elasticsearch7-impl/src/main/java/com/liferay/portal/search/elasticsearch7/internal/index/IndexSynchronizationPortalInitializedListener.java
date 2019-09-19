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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.search.elasticsearch7.spi.index.IndexRegistrar;
import com.liferay.portal.search.spi.index.IndexDefinition;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = {})
public class IndexSynchronizationPortalInitializedListener {

	@Activate
	public void activate() {
		_indexSynchronizer.synchronizeIndexes();

		_activated = true;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void addIndexDefinition(
		IndexDefinition indexDefinition, Map<String, Object> properties) {

		if (_activated) {
			_indexSynchronizer.synchronizeIndexDefinition(
				new IndexDefinitionData(indexDefinition, properties));
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void addIndexRegistrar(IndexRegistrar indexRegistrar) {
		if (_activated) {
			_indexSynchronizer.synchronizeIndexRegistrar(indexRegistrar);
		}
	}

	public void removeIndexDefinition(IndexDefinition indexDefinition) {
	}

	public void removeIndexRegistrar(IndexRegistrar indexRegistrar) {
	}

	@Reference(unbind = "-")
	public void setIndexSynchronizer(IndexSynchronizer indexSynchronizer) {
		_indexSynchronizer = indexSynchronizer;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	public void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Portal is initialized and indexes will be synchronized");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexSynchronizationPortalInitializedListener.class);

	private boolean _activated;
	private IndexSynchronizer _indexSynchronizer;

}