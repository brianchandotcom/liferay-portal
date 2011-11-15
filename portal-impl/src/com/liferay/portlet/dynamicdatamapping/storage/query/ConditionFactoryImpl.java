/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.storage.query;

/**
 * @author Marcellus Tavares
 */
public class ConditionFactoryImpl implements ConditionFactory {

	public Junction conjunction() {
		return new JunctionImpl(LogicalOperator.AND);
	}

	public Junction disjunction() {
		return new JunctionImpl(LogicalOperator.OR);
	}

	public Condition eq(String name, Object value) {
		return new FieldConditionImpl(name, value, ComparisonOperator.EQUALS);
	}

	public Condition gt(String name, Object value) {
		return new FieldConditionImpl(
			name, value, ComparisonOperator.GREATER_THAN);
	}

	public Condition gte(String name, Object value) {
		return new FieldConditionImpl(
			name, value, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO);
	}

	public Condition in(String name, Object value) {
		return new FieldConditionImpl(name, value, ComparisonOperator.IN);
	}

	public Condition like(String name, Object value) {
		return new FieldConditionImpl(name, value, ComparisonOperator.LIKE);
	}

	public Condition lt(String name, Object value) {
		return new FieldConditionImpl(
			name, value, ComparisonOperator.LESS_THAN);
	}

	public Condition lte(String name, Object value) {
		return new FieldConditionImpl(
			name, value, ComparisonOperator.LESS_THAN_OR_EQUAL_TO);
	}

	public Condition ne(String name, Object value) {
		return new FieldConditionImpl(
			name, value, ComparisonOperator.NOT_EQUALS);
	}

	public Condition notIn(String name, Object value) {
		return new FieldConditionImpl(name, value, ComparisonOperator.NOT_IN);
	}

}