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

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

/**
 * @author Drew Brokke
 */
public class DummyService<T extends UserAssociatedEntity> {

	public static final long DEFAULT_CONTAINER_ID = 0;

	public DummyService(
		long currentId,
		Supplier<UserAssociatedEntity> userAssociatedEntitySupplier) {

		_currentId = currentId;
		_userAssociatedEntitySupplier = userAssociatedEntitySupplier;
	}

	public int count(long userId) {
		List<T> entities = getEntities(userId);

		return entities.size();
	}

	public T create(String name, long userId) {
		return create(name, userId, DEFAULT_CONTAINER_ID);
	}

	public T create(String name, long userId, long containerId) {
		_currentId += 1;

		UserAssociatedEntity containedEntity =
			_userAssociatedEntitySupplier.get();

		containedEntity.setContainerId(containerId);
		containedEntity.setId(_currentId);
		containedEntity.setName(name);
		containedEntity.setUserId(userId);

		_userAssociatedEntities.add((T)containedEntity);

		return (T)containedEntity;
	}

	public List<T> getEntities() {
		return ListUtil.copy(_userAssociatedEntities);
	}

	public List<T> getEntities(long userId) {
		return ListUtil.filter(
			_userAssociatedEntities,
			userAssociatedEntity -> userAssociatedEntity.getUserId() == userId);
	}

	public T getEntity(long primaryKey) {
		List<T> filteredList = ListUtil.filter(
			_userAssociatedEntities,
			userAssociatedEntity -> userAssociatedEntity.getId() == primaryKey);

		if (!filteredList.isEmpty()) {
			return filteredList.get(0);
		}

		return null;
	}

	private long _currentId;
	private List<T> _userAssociatedEntities = new CopyOnWriteArrayList<>();
	private final Supplier<UserAssociatedEntity> _userAssociatedEntitySupplier;

}