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

package com.liferay.portal.search.elasticsearch6.internal.query;

import com.liferay.portal.search.query.function.CombineFunction;

/**
 * @author Michael C. Han
 */
public class CombineFunctionTranslator {

	public org.elasticsearch.common.lucene.search.function.CombineFunction
		translate(CombineFunction combineFunction) {

		if (combineFunction == CombineFunction.AVG) {
			return org.elasticsearch.common.lucene.search.function.
				CombineFunction.AVG;
		}
		else if (combineFunction == CombineFunction.MAX) {
			return org.elasticsearch.common.lucene.search.function.
				CombineFunction.MAX;
		}
		else if (combineFunction == CombineFunction.MIN) {
			return org.elasticsearch.common.lucene.search.function.
				CombineFunction.MIN;
		}
		else if (combineFunction == CombineFunction.MULTIPLY) {
			return org.elasticsearch.common.lucene.search.function.
				CombineFunction.MULTIPLY;
		}
		else if (combineFunction == CombineFunction.REPLACE) {
			return org.elasticsearch.common.lucene.search.function.
				CombineFunction.REPLACE;
		}
		else if (combineFunction == CombineFunction.SUM) {
			return org.elasticsearch.common.lucene.search.function.
				CombineFunction.SUM;
		}

		throw new IllegalArgumentException(
			"Invalid CombineFunction: " + combineFunction);
	}

}