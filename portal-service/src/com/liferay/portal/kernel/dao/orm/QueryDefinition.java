/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class QueryDefinition {

	public QueryDefinition() {
	}

	public QueryDefinition(int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		}
		else {
			setStatus(status);
		}
	}

	public QueryDefinition(
		int status, boolean excludeStatus, int start, int end,
		OrderByComparator obc) {

		_status = status;
		_excludeStatus = excludeStatus;
		_start = start;
		_end = end;
		_orderByComparator = obc;
	}

	public QueryDefinition(
		int status, int start, int end, OrderByComparator obc) {

		if (status == WorkflowConstants.STATUS_ANY) {
			setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		}
		else {
			setStatus(status);
		}

		_start = start;
		_end = end;
		_orderByComparator = obc;
	}

	public Serializable getAttribute(String name) {
		if (_attributes == null) {
			return null;
		}

		return _attributes.get(name);
	}

	public Map<String, Serializable> getAttributes() {
		return _attributes;
	}

	public int getEnd() {
		return _end;
	}

	public OrderByComparator getOrderByComparator() {
		return _orderByComparator;
	}

	public int getStart() {
		return _start;
	}

	public int getStatus() {
		return _status;
	}

	public boolean isExcludeStatus() {
		return _excludeStatus;
	}

	public void setAttribute(String name, Serializable value) {
		_attributes.put(name, value);
	}

	public void setAttributes(Map<String, Serializable> attributes) {
		if (_attributes == null) {
			_attributes = new HashMap<String, Serializable>();
		}

		_attributes = attributes;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public void setOrderByComparator(OrderByComparator orderByComparator) {
		_orderByComparator = orderByComparator;
	}

	public void setStart(int start) {
		_start = start;
	}

	public void setStatus(int status) {
		setStatus(status, false);
	}

	public void setStatus(int status, boolean exclude) {
		_excludeStatus = exclude;
		_status = status;
	}

	private Map<String, Serializable> _attributes;
	private int _end = QueryUtil.ALL_POS;
	private boolean _excludeStatus;
	private OrderByComparator _orderByComparator;
	private int _start = QueryUtil.ALL_POS;
	private int _status = WorkflowConstants.STATUS_ANY;

}