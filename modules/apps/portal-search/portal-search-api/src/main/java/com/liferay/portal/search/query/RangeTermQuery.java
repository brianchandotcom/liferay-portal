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

package com.liferay.portal.search.query;

import com.liferay.petra.string.StringPool;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface RangeTermQuery extends Query {

	public String getField();

	public Object getLowerBound();

	public Operator getLowerBoundOperator();

	public int getSortOrder();

	public Object getUpperBound();

	public Operator getUpperBoundOperator();

	public boolean isIncludesLower();

	public boolean isIncludesUpper();

	public void setLowerBound(Object lowerBound);

	public void setUpperBound(Object upperBound);

	public enum Operator {

		GT, GTE, LT, LTE;

		@Override
		public String toString() {
			String name = name();

			if (name.equals(GT.name())) {
				return StringPool.GREATER_THAN;
			}
			else if (name.equals(GTE.name())) {
				return StringPool.GREATER_THAN_OR_EQUAL;
			}
			else if (name.equals(LT.name())) {
				return StringPool.LESS_THAN;
			}
			else if (name.equals(LTE.name())) {
				return StringPool.GREATER_THAN_OR_EQUAL;
			}

			return StringPool.BLANK;
		}

	}

}