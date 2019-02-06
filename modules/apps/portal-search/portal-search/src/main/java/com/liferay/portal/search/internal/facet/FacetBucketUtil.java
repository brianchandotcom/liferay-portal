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

package com.liferay.portal.search.internal.facet;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class FacetBucketUtil {

	public static boolean isFieldInBucket(
		Field field, String term, Facet facet) {

		if (facet instanceof RangeFacet) {
			String[] range = RangeParserUtil.parserRange(term);

			String lower = range[0];
			String upper = range[1];

			String value = field.getValue();

			if ((lower.compareTo(value) <= 0) && (value.compareTo(upper) < 0)) {
				return true;
			}

			return false;
		}

		if (ArrayUtil.contains(field.getValues(), term, false)) {
			return true;
		}

		return false;
	}

}