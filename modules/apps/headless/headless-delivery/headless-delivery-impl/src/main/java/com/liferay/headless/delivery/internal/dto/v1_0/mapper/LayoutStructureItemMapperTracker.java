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

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemMapperTracker.class)
public class LayoutStructureItemMapperTracker {

	public LayoutStructureItemMapper getLayoutStructureItemMapper(
		String className) {

		return _layoutStructureItemMappers.get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setLayoutStructureItemMapper(
		LayoutStructureItemMapper layoutStructureItemMapper) {

		_layoutStructureItemMappers.put(
			layoutStructureItemMapper.getClassName(),
			layoutStructureItemMapper);
	}

	protected void unsetLayoutStructureItemMapper(
		LayoutStructureItemMapper layoutStructureItemMapper) {

		_layoutStructureItemMappers.remove(layoutStructureItemMapper);
	}

	private final Map<String, LayoutStructureItemMapper>
		_layoutStructureItemMappers = new ConcurrentHashMap<>();

}