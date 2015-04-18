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

package com.liferay.portal.search.lucene.internal.query;

import com.liferay.portal.kernel.search.BooleanClauseOccur;

/**
 * @author Brian Wing Shun Chan
 */
public class BooleanClauseOccurTranslator {

	public static org.apache.lucene.search.BooleanClause.Occur translate(
		BooleanClauseOccur occur) {

		if (occur.equals(BooleanClauseOccur.MUST)) {
			return org.apache.lucene.search.BooleanClause.Occur.MUST;
		}
		else if (occur.equals(BooleanClauseOccur.MUST_NOT)) {
			return org.apache.lucene.search.BooleanClause.Occur.MUST_NOT;
		}
		else if (occur.equals(BooleanClauseOccur.SHOULD)) {
			return org.apache.lucene.search.BooleanClause.Occur.SHOULD;
		}
		else {
			return null;
		}
	}

}