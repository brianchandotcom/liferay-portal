/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class GeoBuildersInstantiationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testInstantiation() {
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.circleShape(
				GeoBuilders.INSTANCE.coordinate(0, 0),
				GeoBuilders.INSTANCE.geoDistance(10)));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.circleShapeBuilder(
			).center(
				GeoBuilders.INSTANCE.coordinate(0, 0)
			).radius(
				GeoBuilders.INSTANCE.geoDistance(10)
			).build());
		Assert.assertNotNull(GeoBuilders.INSTANCE.coordinate(0, 0));
		Assert.assertNotNull(GeoBuilders.INSTANCE.coordinate(0, 0, 0));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.envelopeShape(
				GeoBuilders.INSTANCE.coordinate(0, 10),
				GeoBuilders.INSTANCE.coordinate(10, 0)));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.envelopeShapeBuilder(
			).topLeft(
				GeoBuilders.INSTANCE.coordinate(0, 10)
			).bottomRight(
				GeoBuilders.INSTANCE.coordinate(10, 0)
			).build());
		Assert.assertNotNull(GeoBuilders.INSTANCE.geoDistance(10));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.geoDistance(10, DistanceUnit.KILOMETERS));
		Assert.assertNotNull(GeoBuilders.INSTANCE.geoLocationPoint(0));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.geoLocationPoint("9qh1rj45h"));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.geoLocationPoint(33.997727, -117.814457));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.geometryCollectionShapeBuilder(
			).shapes(
				GeoBuilders.INSTANCE.circleShape(
					GeoBuilders.INSTANCE.coordinate(0, 0),
					GeoBuilders.INSTANCE.geoDistance(10)),
				GeoBuilders.INSTANCE.envelopeShape(
					GeoBuilders.INSTANCE.coordinate(0, 10),
					GeoBuilders.INSTANCE.coordinate(10, 0))
			).build());
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.lineStringShape(
				Arrays.asList(
					GeoBuilders.INSTANCE.coordinate(0, 10),
					GeoBuilders.INSTANCE.coordinate(10, 0))));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.lineStringShapeBuilder(
			).coordinates(
				GeoBuilders.INSTANCE.coordinate(0, 10),
				GeoBuilders.INSTANCE.coordinate(10, 0)
			).build());
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.multiLineStringShapeBuilder(
			).addLineStringShape(
				GeoBuilders.INSTANCE.lineStringShape(
					Arrays.asList(
						GeoBuilders.INSTANCE.coordinate(0, 10),
						GeoBuilders.INSTANCE.coordinate(10, 0)))
			).build());
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.multiPointShape(
				Arrays.asList(
					GeoBuilders.INSTANCE.coordinate(0, 10),
					GeoBuilders.INSTANCE.coordinate(10, 0))));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.multiPointShapeBuilder(
			).coordinates(
				GeoBuilders.INSTANCE.coordinate(0, 10),
				GeoBuilders.INSTANCE.coordinate(10, 0)
			).build());
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.multiPolygonShape(Orientation.RIGHT));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.multiPolygonShapeBuilder(
			).addPolygonShape(
				GeoBuilders.INSTANCE.polygonShape(
					GeoBuilders.INSTANCE.lineStringShape(
						Arrays.asList(
							GeoBuilders.INSTANCE.coordinate(0, 10),
							GeoBuilders.INSTANCE.coordinate(10, 0))),
					Orientation.LEFT)
			).orientation(
				Orientation.RIGHT
			).build());
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.pointShape(
				GeoBuilders.INSTANCE.coordinate(0, 0)));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.pointShapeBuilder(
			).addCoordinate(
				GeoBuilders.INSTANCE.coordinate(0, 0)
			).build());
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.polygonShape(
				GeoBuilders.INSTANCE.lineStringShape(
					Arrays.asList(
						GeoBuilders.INSTANCE.coordinate(0, 10),
						GeoBuilders.INSTANCE.coordinate(10, 0))),
				Orientation.LEFT));
		Assert.assertNotNull(
			GeoBuilders.INSTANCE.polygonShapeBuilder(
			).addHole(
				GeoBuilders.INSTANCE.lineStringShape(
					Arrays.asList(
						GeoBuilders.INSTANCE.coordinate(0, 10),
						GeoBuilders.INSTANCE.coordinate(10, 0)))
			).orientation(
				Orientation.RIGHT
			).shell(
				GeoBuilders.INSTANCE.lineStringShape(
					Arrays.asList(
						GeoBuilders.INSTANCE.coordinate(0, 10),
						GeoBuilders.INSTANCE.coordinate(10, 0)))
			).build());
	}

}