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

package com.liferay.portal.search.internal.reindexer;

import com.liferay.portal.search.spi.reindexer.BulkReindexer;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = BulkReindexersHolder.class)
public class BulkReindexersHolderImpl implements BulkReindexersHolder {

	@Override
	public BulkReindexer getBulkReindexer(String className) {
		return _get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		service = BulkReindexer.class
	)
	protected void addBulkReindexer(
		BulkReindexer bulkReindexer, Map<?, ?> properties) {

		Object obj = properties.get("indexer.class.name");

		if (obj != null) {
			_put(obj.toString(), bulkReindexer);
		}
	}

	protected void removeBulkReindexer(
		BulkReindexer bulkReindexer, Map<?, ?> properties) {

		Object obj = properties.get("indexer.class.name");

		if (obj != null) {
			_remove(obj.toString());
		}
	}

	private synchronized BulkReindexer _get(String className) {
		return _map.get(className);
	}

	private synchronized void _put(
		String className, BulkReindexer bulkReindexer) {

		_map.put(className, bulkReindexer);
	}

	private synchronized void _remove(String className) {
		_map.remove(className);
	}

	private final Map<String, BulkReindexer> _map = new HashMap<>();

}