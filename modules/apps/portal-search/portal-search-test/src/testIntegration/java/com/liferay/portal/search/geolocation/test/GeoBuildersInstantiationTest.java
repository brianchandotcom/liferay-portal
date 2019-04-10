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

package com.liferay.portal.search.geolocation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.geolocation.Orientation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class GeoBuildersInstantiationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testInstantiation() {
		Assert.assertNotNull(
			_geoBuilders.circleShape(
				_geoBuilders.coordinate(0, 0), _geoBuilders.geoDistance(10)));
		Assert.assertNotNull(
			_geoBuilders.circleShapeBuilder(
			).center(
				_geoBuilders.coordinate(0, 0)
			).radius(
				_geoBuilders.geoDistance(10)
			).build());
		Assert.assertNotNull(_geoBuilders.coordinate(0, 0));
		Assert.assertNotNull(_geoBuilders.coordinate(0, 0, 0));
		Assert.assertNotNull(
			_geoBuilders.envelopeShape(
				_geoBuilders.coordinate(0, 10),
				_geoBuilders.coordinate(10, 0)));
		Assert.assertNotNull(
			_geoBuilders.envelopeShapeBuilder(
			).topLeft(
				_geoBuilders.coordinate(0, 10)
			).bottomRight(
				_geoBuilders.coordinate(10, 0)
			).build());
		Assert.assertNotNull(_geoBuilders.geoDistance(10));
		Assert.assertNotNull(
			_geoBuilders.geoDistance(10, DistanceUnit.KILOMETERS));
		Assert.assertNotNull(_geoBuilders.geoLocationPoint(0));
		Assert.assertNotNull(_geoBuilders.geoLocationPoint("9qh1rj45h"));
		Assert.assertNotNull(
			_geoBuilders.geoLocationPoint(33.997727, -117.814457));
		Assert.assertNotNull(
			_geoBuilders.geometryCollectionShapeBuilder(
			).shapes(
				_geoBuilders.circleShape(
					_geoBuilders.coordinate(0, 0),
					_geoBuilders.geoDistance(10)),
				_geoBuilders.envelopeShape(
					_geoBuilders.coordinate(0, 10),
					_geoBuilders.coordinate(10, 0))
			).build());
		Assert.assertNotNull(
			_geoBuilders.lineStringShape(
				Arrays.asList(
					_geoBuilders.coordinate(0, 10),
					_geoBuilders.coordinate(10, 0))));
		Assert.assertNotNull(
			_geoBuilders.lineStringShapeBuilder(
			).coordinates(
				_geoBuilders.coordinate(0, 10), _geoBuilders.coordinate(10, 0)
			).build());
		Assert.assertNotNull(
			_geoBuilders.multiLineStringShapeBuilder(
			).addLineStringShape(
				_geoBuilders.lineStringShape(
					Arrays.asList(
						_geoBuilders.coordinate(0, 10),
						_geoBuilders.coordinate(10, 0)))
			).build());
		Assert.assertNotNull(
			_geoBuilders.multiPointShape(
				Arrays.asList(
					_geoBuilders.coordinate(0, 10),
					_geoBuilders.coordinate(10, 0))));
		Assert.assertNotNull(
			_geoBuilders.multiPointShapeBuilder(
			).coordinates(
				_geoBuilders.coordinate(0, 10), _geoBuilders.coordinate(10, 0)
			).build());
		Assert.assertNotNull(_geoBuilders.multiPolygonShape(Orientation.RIGHT));
		Assert.assertNotNull(
			_geoBuilders.multiPolygonShapeBuilder(
			).addPolygonShape(
				_geoBuilders.polygonShape(
					_geoBuilders.lineStringShape(
						Arrays.asList(
							_geoBuilders.coordinate(0, 10),
							_geoBuilders.coordinate(10, 0))),
					Orientation.LEFT)
			).orientation(
				Orientation.RIGHT
			).build());
		Assert.assertNotNull(
			_geoBuilders.pointShape(_geoBuilders.coordinate(0, 0)));
		Assert.assertNotNull(
			_geoBuilders.pointShapeBuilder(
			).addCoordinate(
				_geoBuilders.coordinate(0, 0)
			).build());
		Assert.assertNotNull(
			_geoBuilders.polygonShape(
				_geoBuilders.lineStringShape(
					Arrays.asList(
						_geoBuilders.coordinate(0, 10),
						_geoBuilders.coordinate(10, 0))),
				Orientation.LEFT));
		Assert.assertNotNull(
			_geoBuilders.polygonShapeBuilder(
			).addHole(
				_geoBuilders.lineStringShape(
					Arrays.asList(
						_geoBuilders.coordinate(0, 10),
						_geoBuilders.coordinate(10, 0)))
			).orientation(
				Orientation.RIGHT
			).shell(
				_geoBuilders.lineStringShape(
					Arrays.asList(
						_geoBuilders.coordinate(0, 10),
						_geoBuilders.coordinate(10, 0)))
			).build());
	}

	@Inject
	private static GeoBuilders _geoBuilders;

}