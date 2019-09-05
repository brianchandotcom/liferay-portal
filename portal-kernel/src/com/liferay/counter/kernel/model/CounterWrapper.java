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

package com.liferay.counter.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Counter}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Counter
 * @generated
 */
public class CounterWrapper
	extends BaseModelWrapper<Counter>
	implements Counter, ModelWrapper<Counter> {

	public CounterWrapper(Counter counter) {
		super(counter);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("name", getName());
		attributes.put("currentId", getCurrentId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long currentId = (Long)attributes.get("currentId");

		if (currentId != null) {
			setCurrentId(currentId);
		}
	}

	/**
	 * Returns the current ID of this counter.
	 *
	 * @return the current ID of this counter
	 */
	@Override
	public long getCurrentId() {
		return model.getCurrentId();
	}

	/**
	 * Returns the name of this counter.
	 *
	 * @return the name of this counter
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this counter.
	 *
	 * @return the primary key of this counter
	 */
	@Override
	public String getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Sets the current ID of this counter.
	 *
	 * @param currentId the current ID of this counter
	 */
	@Override
	public void setCurrentId(long currentId) {
		model.setCurrentId(currentId);
	}

	/**
	 * Sets the name of this counter.
	 *
	 * @param name the name of this counter
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this counter.
	 *
	 * @param primaryKey the primary key of this counter
	 */
	@Override
	public void setPrimaryKey(String primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected CounterWrapper wrap(Counter counter) {
		return new CounterWrapper(counter);
	}

}