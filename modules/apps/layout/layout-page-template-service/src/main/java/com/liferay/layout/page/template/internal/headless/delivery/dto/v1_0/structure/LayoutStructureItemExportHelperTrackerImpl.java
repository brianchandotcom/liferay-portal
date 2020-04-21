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

package com.liferay.layout.page.template.internal.headless.delivery.dto.v1_0.structure;

import com.liferay.layout.page.template.headless.delivery.dto.v1_0.structure.LayoutStructureItemExportHelper;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.structure.LayoutStructureItemExportHelperTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExportHelperTracker.class)
public class LayoutStructureItemExportHelperTrackerImpl
	implements LayoutStructureItemExportHelperTracker {

	@Override
	public LayoutStructureItemExportHelper getLayoutStructureItemExportHelper(
		String className) {

		List<LayoutStructureItemExportHelper>
			layoutStructureItemExportHelperList = new ArrayList<>(
				_layoutStructureItemExportHelpers);

		Stream<LayoutStructureItemExportHelper> stream =
			layoutStructureItemExportHelperList.stream();

		return stream.filter(
			layoutStructureItemExportHelper -> Objects.equals(
				layoutStructureItemExportHelper.getClassName(), className)
		).findFirst(
		).get();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setLayoutStructureItemExportHelper(
		LayoutStructureItemExportHelper layoutStructureItemExportHelper) {

		_layoutStructureItemExportHelpers.add(layoutStructureItemExportHelper);
	}

	protected void unsetLayoutStructureItemExportHelper(
		LayoutStructureItemExportHelper layoutStructureItemExportHelper) {

		_layoutStructureItemExportHelpers.remove(
			layoutStructureItemExportHelper);
	}

	private final List<LayoutStructureItemExportHelper>
		_layoutStructureItemExportHelpers = new CopyOnWriteArrayList<>();

}