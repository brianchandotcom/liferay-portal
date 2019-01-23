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
public class MultipleLineStringShapeBuilder extends ShapeBuilder {

	@Override
	public <T> T accept(ShapeBuilderTranslator<T> shapeBuilderTranslator) {
		return shapeBuilderTranslator.translate(this);
	}

	public void addLineStringShapeBuilder(
		LineStringShapeBuilder lineStringShapeBuilder) {

		_lineStringShapeBuilders.add(lineStringShapeBuilder);
	}

	public void addLineStringShapeBuilders(
		LineStringShapeBuilder... lineStringShapeBuilders) {

		Collections.addAll(_lineStringShapeBuilders, lineStringShapeBuilders);
	}

	public void addLineStringShapeBuilders(
		List<LineStringShapeBuilder> lineStringShapeBuilders) {

		_lineStringShapeBuilders.addAll(lineStringShapeBuilders);
	}

	public List<LineStringShapeBuilder> getLineStringShapeBuilders() {
		return Collections.unmodifiableList(_lineStringShapeBuilders);
	}

	private final List<LineStringShapeBuilder> _lineStringShapeBuilders =
		new ArrayList<>();

}