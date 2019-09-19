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

package com.liferay.portal.search.elasticsearch6.internal.index;

import com.liferay.portal.search.spi.index.IndexDefinition;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author André de Oliveira
 */
@Component(service = IndexDefinitionsHolder.class)
public class IndexDefinitionsHolderImpl implements IndexDefinitionsHolder {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void addIndexDefinition(
		IndexDefinition indexDefinition, Map<String, Object> properties) {

		_list.add(new IndexDefinitionData(indexDefinition, properties));
	}

	@Override
	public void drainTo(Collection<IndexDefinitionData> collection) {
		collection.addAll(_list);

		_list.clear();
	}

	public void removeIndexDefinition(IndexDefinition indexDefinition) {
	}

	private final List<IndexDefinitionData> _list =
		new CopyOnWriteArrayList<>();

}