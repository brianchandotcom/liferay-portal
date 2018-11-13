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

package com.liferay.portal.tools.java.parser;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaForStatement extends BaseJavaTerm {

	public void setConditionJavaExpression(
		JavaExpression conditionJavaExpression) {

		_conditionJavaExpression = conditionJavaExpression;
	}

	public void setInitializationJavaTerms(
		List<JavaTerm> initializationJavaTerms) {

		_initializationJavaTerms = initializationJavaTerms;
	}

	public void setIteratorJavaExpression(
		List<JavaExpression> iteratorJavaExpressions) {

		_iteratorJavaExpressions = iteratorJavaExpressions;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		return "TODO";
	}

	private JavaExpression _conditionJavaExpression;
	private List<JavaTerm> _initializationJavaTerms;
	private List<JavaExpression> _iteratorJavaExpressions;

}