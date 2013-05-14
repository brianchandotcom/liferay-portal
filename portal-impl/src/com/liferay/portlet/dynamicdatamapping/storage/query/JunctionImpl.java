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

package com.liferay.portlet.dynamicdatamapping.storage.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class JunctionImpl implements Junction {

	public JunctionImpl(LogicalOperator logicalOperator) {
		_logicalOperator = logicalOperator;
	}

	@Override
	public Junction add(Condition condition) {
		_conditions.add(condition);

		return this;
	}

	@Override
	public LogicalOperator getLogicalOperator() {
		return _logicalOperator;
	}

	@Override
	public boolean isJunction() {
		return _JUNCTION;
	}

	@Override
	public Iterator<Condition> iterator() {
		return _conditions.iterator();
	}

	private static final boolean _JUNCTION = true;

	private List<Condition> _conditions = new ArrayList<Condition>();
	private LogicalOperator _logicalOperator;

}