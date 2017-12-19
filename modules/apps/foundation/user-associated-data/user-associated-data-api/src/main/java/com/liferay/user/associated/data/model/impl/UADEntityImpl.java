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

package com.liferay.user.associated.data.model.impl;

import com.liferay.user.associated.data.model.UADEntity;

import java.net.URL;

import java.util.List;

/**
 * @author William Newbury
 */
public abstract class UADEntityImpl implements UADEntity {

	public UADEntityImpl(
		long userId, String uadEntityId, String uadRegistryKey,
		List<UADEntity> childUADEntities) {

		_userId = userId;

		_uadEntityId = uadEntityId;
		_uadRegistryKey = uadRegistryKey;
		_childUADEntities = childUADEntities;
	}

	@Override
	public List<UADEntity> getChildUADEntities() {
		return _childUADEntities;
	}

	@Override
	public abstract URL getEditURL();

	@Override
	public String getUADEntityId() {
		return _uadEntityId;
	}

	@Override
	public String getUADRegistryKey() {
		return _uadRegistryKey;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setChildUADEntities(List<UADEntity> childUADEntities) {
		_childUADEntities = childUADEntities;
	}

	private List<UADEntity> _childUADEntities;
	private final String _uadEntityId;
	private final String _uadRegistryKey;
	private final long _userId;

}