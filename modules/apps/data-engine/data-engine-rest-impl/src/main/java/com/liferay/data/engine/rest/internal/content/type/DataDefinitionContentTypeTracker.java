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

package com.liferay.data.engine.rest.internal.content.type;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.internal.model.InternalDataDefinition;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataDefinitionContentTypeTracker.class)
public class DataDefinitionContentTypeTracker {

	public DataDefinitionContentType getDataDefinitionContentType(
		String dataDefinitionContentType) {

		return _dataDefinitionContentTypes.getOrDefault(
			dataDefinitionContentType, new DefaultDataDefinitionContentType());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataDefinitionContentType(
		DataDefinitionContentType dataDefinitionContentType,
		Map<String, Object> properties) {

		if (!properties.containsKey("data.definition.content.type")) {
			return;
		}

		_dataDefinitionContentTypes.put(
			MapUtil.getString(properties, "data.definition.content.type"),
			dataDefinitionContentType);
	}

	@Deactivate
	protected void deactivate() {
		_dataDefinitionContentTypes.clear();
	}

	protected void removeDataDefinitionContentType(
		DataDefinitionContentType dataDefinitionContentType,
		Map<String, Object> properties) {

		_dataDefinitionContentTypes.remove(
			MapUtil.getString(properties, "data.definition.content.type"));
	}

	private final Map<String, DataDefinitionContentType>
		_dataDefinitionContentTypes = new TreeMap<>();

	@Reference
	private Portal _portal;

	private class DefaultDataDefinitionContentType
		implements DataDefinitionContentType {

		@Override
		public long getClassNameId() {
			return _portal.getClassNameId(
				InternalDataDefinition.class.getName());
		}

	}

}