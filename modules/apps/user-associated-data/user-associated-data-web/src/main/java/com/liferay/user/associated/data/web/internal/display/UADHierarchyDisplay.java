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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Drew Brokke
 */
public class UADHierarchyDisplay {

	public UADHierarchyDisplay(
		UADHierarchyDeclaration uadHierarchyDeclaration) {

		_uadHierarchyDeclaration = uadHierarchyDeclaration;

		_uadDisplays = ArrayUtil.append(
			_uadHierarchyDeclaration.getContainerUADDisplays(),
			_uadHierarchyDeclaration.getNoncontainerUADDisplays());

		for (UADDisplay<?> uadDisplay : _uadDisplays) {
			_uadDisplayMap.put(uadDisplay.getTypeClass(), uadDisplay);
		}
	}

	public long countAll(long userId) {
		long count = 0;

		for (UADDisplay<?> uadDisplay : _uadDisplays) {
			count += uadDisplay.count(userId);
		}

		return count;
	}

	public <T> Map<String, Object> getFieldValues(
		T object, String[] fieldNames) {

		Map<String, Object> fieldValues = new HashMap<>();

		Class<?> clazz = object.getClass();

		if (object instanceof ContainerDisplay) {
			ContainerDisplay<T> containerDisplay = (ContainerDisplay)object;

			T containerObject = containerDisplay.getContainer();

			clazz = containerObject.getClass();
		}

		UADDisplay<T> uadDisplay = (UADDisplay<T>)_uadDisplayMap.get(clazz);

		if (uadDisplay != null) {
			String[] allFieldNames = ArrayUtil.append(
				fieldNames, _uadHierarchyDeclaration.getExtraColumnNames());

			if (object instanceof ContainerDisplay) {
				ContainerDisplay<T> containerDisplay =
					(ContainerDisplay<T>)object;

				T containerObject = containerDisplay.getContainer();

				fieldValues = uadDisplay.getFieldValues(
					containerObject, allFieldNames);

				fieldValues.put("count", containerDisplay.getCount());
			}
			else {
				fieldValues = uadDisplay.getFieldValues(object, allFieldNames);

				fieldValues.put("count", "--");
			}
		}

		return fieldValues;
	}

	public List<Object> search(
			Class<?> parentContainerType, Serializable parentContainerId,
			long userId, long[] groupIds, String keywords, String orderByField,
			String orderByType, int start, int end)
		throws Exception {

		Objects.requireNonNull(parentContainerType);
		Objects.requireNonNull(parentContainerId);

		List<Object> searchResults = new ArrayList<>();

		List<Object> allUserItems = new ArrayList<>();

		for (UADDisplay<?> uadDisplay : _uadDisplays) {
			allUserItems.addAll(
				uadDisplay.search(
					userId, groupIds, keywords, orderByField, orderByType,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS));
		}

		for (UADDisplay<?> containerUADDisplay :
				_uadHierarchyDeclaration.getContainerUADDisplays()) {

			searchResults.addAll(
				getContainerDisplays(
					containerUADDisplay, parentContainerType, parentContainerId,
					allUserItems));
		}

		for (UADDisplay nonContainerUADDisplay :
				_uadHierarchyDeclaration.getNoncontainerUADDisplays()) {

			Class<?> typeClass = nonContainerUADDisplay.getTypeClass();

			for (Object userItem : allUserItems) {
				if ((userItem != null) &&
					typeClass.isAssignableFrom(userItem.getClass()) &&
					parentContainerId.equals(
						nonContainerUADDisplay.getParentContainerId(
							userItem))) {

					searchResults.add(userItem);
				}
			}
		}

		return ListUtil.subList(searchResults, start, end);
	}

	protected <T> Collection<ContainerDisplay<T>> getContainerDisplays(
		UADDisplay<T> containerUADDisplay, Class<?> parentContainerType,
		Serializable parentContainerId, List<Object> allUserItems) {

		Map<Serializable, ContainerDisplay<T>> topLevelCategories =
			new HashMap<>();

		Class<T> containerClass = containerUADDisplay.getTypeClass();

		for (Object userItem : allUserItems) {
			if (userItem == null) {
				continue;
			}

			T topLevelContainer = containerUADDisplay.getTopLevelContainer(
				parentContainerType, parentContainerId, userItem);

			if (topLevelContainer == null) {
				continue;
			}

			Serializable topLevelContainerId =
				containerUADDisplay.getPrimaryKey(topLevelContainer);

			if (containerClass.isAssignableFrom(userItem.getClass()) &&
				(containerUADDisplay.getParentContainerId((T)userItem) ==
					parentContainerId)) {

				topLevelCategories.putIfAbsent(
					topLevelContainerId,
					new ContainerDisplay<>(topLevelContainer));
			}
			else {
				topLevelCategories.compute(
					topLevelContainerId,
					(key, value) -> {
						if (value == null) {
							value = new ContainerDisplay<>(topLevelContainer);
						}

						value.increment();

						return value;
					});
			}
		}

		return topLevelCategories.values();
	}

	private Map<Class<?>, UADDisplay<?>> _uadDisplayMap = new HashMap<>();
	private final UADDisplay<?>[] _uadDisplays;
	private final UADHierarchyDeclaration _uadHierarchyDeclaration;

}