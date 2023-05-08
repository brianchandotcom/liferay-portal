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
import com.liferay.data.engine.rest.resource.exception.DataDefinitionValidationException;

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
@Component(service = DataDefinitionContentTypeRegistry.class)
public class DataDefinitionContentTypeRegistry {

	public Long getClassNameId(String contentType) throws Exception {
		Long id = _classNameIds.get(contentType);

		if (id == null) {
			throw new DataDefinitionValidationException.MustSetValidContentType(
				contentType);
		}

		return id;
	}

	public DataDefinitionContentType getDataDefinitionContentType(
		long classNameId) {

		return _dataDefinitionContentTypesByClassNameId.get(classNameId);
	}

	public DataDefinitionContentType getDataDefinitionContentType(
			String contentType)
		throws Exception {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypesByContentType.get(contentType);

		if (dataDefinitionContentType == null) {
			throw new DataDefinitionValidationException.MustSetValidContentType(
				contentType);
		}

		return dataDefinitionContentType;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(content.type=*)"
	)
	protected void addDataDefinitionContentType(
		DataDefinitionContentType dataDefinitionContentType,
		Map<String, Object> properties) {

		String contentType = (String)properties.get("content.type");

		_classNameIds.put(
			contentType, dataDefinitionContentType.getClassNameId());

		_dataDefinitionContentTypesByClassNameId.put(
			dataDefinitionContentType.getClassNameId(),
			dataDefinitionContentType);

		_dataDefinitionContentTypesByContentType.put(
			contentType, dataDefinitionContentType);
	}

	@Deactivate
	protected void deactivate() {
		_dataDefinitionContentTypesByContentType.clear();
	}

	protected void removeDataDefinitionContentType(
		DataDefinitionContentType dataDefinitionContentType,
		Map<String, Object> properties) {

		String contentType = (String)properties.get("content.type");

		_dataDefinitionContentTypesByClassNameId.remove(
			_classNameIds.get(contentType));

		_classNameIds.remove(contentType);
		_dataDefinitionContentTypesByContentType.remove(contentType);
	}

	private final Map<String, Long> _classNameIds = new TreeMap<>();
	private final Map<Long, DataDefinitionContentType>
		_dataDefinitionContentTypesByClassNameId = new TreeMap<>();
	private final Map<String, DataDefinitionContentType>
		_dataDefinitionContentTypesByContentType = new TreeMap<>();

}