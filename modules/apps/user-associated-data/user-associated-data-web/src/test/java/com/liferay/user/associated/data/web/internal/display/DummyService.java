/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	private final List<T> _userAssociatedEntities =
		new CopyOnWriteArrayList<>();
	private final Supplier<UserAssociatedEntity> _userAssociatedEntitySupplier;

}