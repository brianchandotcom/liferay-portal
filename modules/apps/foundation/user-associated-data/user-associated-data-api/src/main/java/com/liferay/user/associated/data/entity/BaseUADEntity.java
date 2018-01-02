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

package com.liferay.user.associated.data.entity;

/**
 * @author William Newbury
 */
public abstract class BaseUADEntity implements UADEntity {

	public BaseUADEntity(
		long userId, String uadEntityId, String uadRegistryKey) {

		_userId = userId;

		_uadEntityId = uadEntityId;
		_uadRegistryKey = uadRegistryKey;
	}

	@Override
	public abstract String getEditURL();

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

	private final String _uadEntityId;
	private final String _uadRegistryKey;
	private final long _userId;

}