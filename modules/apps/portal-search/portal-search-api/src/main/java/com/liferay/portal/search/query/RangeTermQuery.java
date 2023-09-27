/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				return StringPool.LESS_THAN_OR_EQUAL;
			}

			return StringPool.BLANK;
		}

	}

}