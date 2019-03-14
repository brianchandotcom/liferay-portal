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

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.EnvelopeShape;
import com.liferay.portal.search.geolocation.EnvelopeShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class EnvelopeShapeImpl extends ShapeImpl implements EnvelopeShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public Coordinate getBottomRight() {
		return _bottomRightCoordinate;
	}

	@Override
	public Coordinate getTopLeft() {
		return _topLeftCoordinate;
	}

	public static class EnvelopeShapeBuilderImpl
		implements EnvelopeShapeBuilder {

		@Override
		public EnvelopeShapeBuilder addCoordinate(Coordinate coordinate) {
			_envelopeShapeImpl.addCoordinate(coordinate);

			return this;
		}

		@Override
		public EnvelopeShapeBuilder bottomRight(Coordinate coordinate) {
			_envelopeShapeImpl._bottomRightCoordinate = coordinate;

			return this;
		}

		@Override
		public EnvelopeShape build() {
			return new EnvelopeShapeImpl(_envelopeShapeImpl);
		}

		@Override
		public EnvelopeShapeBuilder coordinates(Coordinate... coordinates) {
			_envelopeShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public EnvelopeShapeBuilder coordinates(List<Coordinate> coordinates) {
			_envelopeShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public EnvelopeShapeBuilder topLeft(Coordinate coordinate) {
			_envelopeShapeImpl._topLeftCoordinate = coordinate;

			return this;
		}

		private final EnvelopeShapeImpl _envelopeShapeImpl =
			new EnvelopeShapeImpl();

	}

	protected EnvelopeShapeImpl() {
	}

	protected EnvelopeShapeImpl(EnvelopeShapeImpl envelopeShapeImpl) {
		_bottomRightCoordinate = envelopeShapeImpl._bottomRightCoordinate;
		_topLeftCoordinate = envelopeShapeImpl._topLeftCoordinate;

		setCoordinates(envelopeShapeImpl.getCoordinates());
	}

	private Coordinate _bottomRightCoordinate;
	private Coordinate _topLeftCoordinate;

}