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

package com.liferay.portal.search.elasticsearch7.internal.filter;

/**
 * @author Michael C. Han
 */
public class ElasticsearchFilterTranslatorFixture {

	public ElasticsearchFilterTranslatorFixture() {
		_elasticsearchFilterTranslator = new ElasticsearchFilterTranslator() {
			{
				booleanFilterTranslator = new BooleanFilterTranslatorImpl();
				dateRangeFilterTranslator = new DateRangeFilterTranslatorImpl();
				dateRangeTermFilterTranslator =
					new DateRangeTermFilterTranslatorImpl();
				existsFilterTranslator = new ExistsFilterTranslatorImpl();
				geoBoundingBoxFilterTranslator =
					new GeoBoundingBoxFilterTranslatorImpl();
				geoDistanceFilterTranslator =
					new GeoDistanceFilterTranslatorImpl();
				geoDistanceRangeFilterTranslator =
					new GeoDistanceRangeFilterTranslatorImpl();
				missingFilterTranslator = new MissingFilterTranslatorImpl();
				prefixFilterTranslator = new PrefixFilterTranslatorImpl();
				rangeTermFilterTranslator = new RangeTermFilterTranslatorImpl();
				termFilterTranslator = new TermFilterTranslatorImpl();
				termsFilterTranslator = new TermsFilterTranslatorImpl();
				termsSetFilterTranslator = new TermsSetFilterTranslatorImpl();
			}
		};
	}

	public ElasticsearchFilterTranslator getElasticsearchFilterTranslator() {
		return _elasticsearchFilterTranslator;
	}

	private final ElasticsearchFilterTranslator _elasticsearchFilterTranslator;

}