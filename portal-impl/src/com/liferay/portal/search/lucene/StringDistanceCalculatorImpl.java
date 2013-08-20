/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.StringDistanceCalculator;

import org.apache.lucene.search.spell.StringDistance;

/**
 * @author Michael C. Han
 */
public class StringDistanceCalculatorImpl implements StringDistanceCalculator {

	@Override
	public float getDistance(String string1, String string2) {
		return _stringDistance.getDistance(string1, string2);
	}

	public void setStringDistance(StringDistance stringDistance) {
		_stringDistance = stringDistance;
	}

	private StringDistance _stringDistance;

}