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

package com.liferay.headless.delivery.internal.dto.v1_0.exporter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporterTracker.class)
public class LayoutStructureItemExporterTracker {

	public LayoutStructureItemExporter getLayoutStructureItemExporter(
		String className) {

		return _layoutStructureItemExporters.get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setLayoutStructureItemExporter(
		LayoutStructureItemExporter layoutStructureItemExporter) {

		_layoutStructureItemExporters.put(
			layoutStructureItemExporter.getClassName(),
			layoutStructureItemExporter);
	}

	protected void unsetLayoutStructureItemExporter(
		LayoutStructureItemExporter layoutStructureItemExporter) {

		_layoutStructureItemExporters.remove(layoutStructureItemExporter);
	}

	private final Map<String, LayoutStructureItemExporter>
		_layoutStructureItemExporters = new ConcurrentHashMap<>();

}