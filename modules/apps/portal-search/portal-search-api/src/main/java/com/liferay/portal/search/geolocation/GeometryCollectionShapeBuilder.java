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

package com.liferay.portal.search.geolocation;

import aQute.bnd.annotation.ProviderType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
@ProviderType
public class GeometryCollectionShapeBuilder extends ShapeBuilder {

	@Override
	public <T> T accept(ShapeBuilderTranslator<T> shapeBuilderTranslator) {
		return shapeBuilderTranslator.translate(this);
	}

	public void addShapeBuilder(ShapeBuilder shapeBuilder) {
		_shapeBuilders.add(shapeBuilder);
	}

	public void addShapeBuilders(ShapeBuilder... shapeBuilders) {
		Collections.addAll(_shapeBuilders, shapeBuilders);
	}

	public List<ShapeBuilder> getShapeBuilders() {
		return Collections.unmodifiableList(_shapeBuilders);
	}

	private final List<ShapeBuilder> _shapeBuilders = new ArrayList<>();

}