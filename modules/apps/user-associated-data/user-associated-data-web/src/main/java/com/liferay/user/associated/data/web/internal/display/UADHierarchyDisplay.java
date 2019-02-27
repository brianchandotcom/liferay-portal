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
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

/**
 * @author Drew Brokke
 */
public class UADHierarchyDisplay {

	public UADHierarchyDisplay(
		UADHierarchyDeclaration uadHierarchyDeclaration) {

		_uadHierarchyDeclaration = uadHierarchyDeclaration;

		UADDisplay<?>[] containerUADDisplays =
			_uadHierarchyDeclaration.getContainerUADDisplays();

		_uadDisplays = ArrayUtil.append(
			containerUADDisplays,
			_uadHierarchyDeclaration.getNoncontainerUADDisplays());

		for (UADDisplay uadDisplay : _uadDisplays) {
			_uadDisplayMap.put(uadDisplay.getTypeClass(), uadDisplay);
		}

		if (containerUADDisplays.length == 0) {
			_containerTypeClasses = null;
		}
		else {
			Stream<UADDisplay> containerUADDisplayStream = Arrays.stream(
				containerUADDisplays);

			_containerTypeClasses = containerUADDisplayStream.map(
				UADDisplay::getTypeClass
			).toArray(
				Class[]::new
			);
		}

		if (_uadDisplays.length == 0) {
			_typeClasses = null;
		}
		else {
			Stream<UADDisplay> stream = Arrays.stream(_uadDisplays);

			_typeClasses = stream.map(
				UADDisplay::getTypeClass
			).toArray(
				Class[]::new
			);
		}
	}

	public long countAll(long userId) {
		long count = 0;

		for (UADDisplay uadDisplay : _uadDisplays) {
			count += uadDisplay.count(userId);
		}

		return count;
	}

	public String[] getColumnFieldNames() {
		return ArrayUtil.append(
			new String[] {"name", "count"},
			_uadHierarchyDeclaration.getExtraColumnNames());
	}

	public Class<?>[] getContainerTypeClasses() {
		return _containerTypeClasses;
	}

	public <T> String getEditURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, T object)
		throws Exception {

		T unwrappedObject = unwrap(object);

		UADDisplay uadDisplay = _getUADDisplay(unwrappedObject);

		return uadDisplay.getEditURL(
			unwrappedObject, liferayPortletRequest, liferayPortletResponse);
	}

	public <T> Map<String, Object> getFieldValues(T object, Locale locale) {
		Map<String, Object> fieldValues = new LinkedHashMap<>();

		UADDisplay uadDisplay = _getUADDisplay(unwrap(object));

		if (uadDisplay != null) {
			if (object instanceof ContainerDisplay) {
				ContainerDisplay<T> containerDisplay =
					(ContainerDisplay<T>)object;

				T containerObject = containerDisplay.getContainer();

				fieldValues.put(
					"name", uadDisplay.getName(containerObject, locale));

				fieldValues.put("count", containerDisplay.getCount());

				fieldValues.putAll(
					uadDisplay.getFieldValues(
						containerObject,
						_uadHierarchyDeclaration.getExtraColumnNames(),
						locale));
			}
			else {
				fieldValues.put("name", uadDisplay.getName(object, locale));

				fieldValues.put("count", "--");

				fieldValues.putAll(
					uadDisplay.getFieldValues(
						object, _uadHierarchyDeclaration.getExtraColumnNames(),
						locale));
			}
		}

		return fieldValues;
	}

	public Class<?> getFirstContainerTypeClass() {
		if (_containerTypeClasses.length == 0) {
			return null;
		}

		return _containerTypeClasses[0];
	}

	public <T> Serializable getPrimaryKey(T object) {
		T unwrappedObject = unwrap(object);

		UADDisplay uadDisplay = _getUADDisplay(unwrappedObject);

		return uadDisplay.getPrimaryKey(unwrappedObject);
	}

	public String[] getSortingFieldNames() {
		return getColumnFieldNames();
	}

	public Class<?>[] getTypeClasses() {
		return _typeClasses;
	}

	public UADDisplay<?>[] getUADDisplays() {
		return _uadDisplays;
	}

	public <T> String getViewURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String applicationKey, T object, long selectedUserId)
		throws Exception {

		T unwrappedObject = unwrap(object);

		UADDisplay uadDisplay = _getUADDisplay(unwrappedObject);

		Class<?> typeClass = uadDisplay.getTypeClass();

		if (!ArrayUtil.contains(_containerTypeClasses, typeClass)) {
			return null;
		}

		PortletURL renderURL = liferayPortletResponse.createRenderURL();

		renderURL.setParameter("applicationKey", applicationKey);
		renderURL.setParameter("mvcRenderCommandName", "/view_uad_hierarchy");
		renderURL.setParameter("parentContainerClass", typeClass.getName());
		renderURL.setParameter(
			"parentContainerId",
			String.valueOf(uadDisplay.getPrimaryKey(unwrappedObject)));
		renderURL.setParameter("p_u_i_d", String.valueOf(selectedUserId));

		String scope = ParamUtil.getString(liferayPortletRequest, "scope");

		renderURL.setParameter("scope", scope);

		return renderURL.toString();
	}

	public <T> boolean isUserOwned(T object, long userId) {
		T unwrappedObject = unwrap(object);

		UADDisplay uadDisplay = _getUADDisplay(unwrappedObject);

		return uadDisplay.isUserOwned(unwrappedObject, userId);
	}

	public List<Object> search(
			Class<?> parentContainerClass, Serializable parentContainerId,
			long userId, long[] groupIds, String keywords, String orderByField,
			String orderByType, int start, int end)
		throws Exception {

		Objects.requireNonNull(parentContainerClass);
		Objects.requireNonNull(parentContainerId);

		List<Object> searchResults = new ArrayList<>();

		List<Object> allUserItems = new ArrayList<>();

		for (UADDisplay uadDisplay : _uadDisplays) {
			allUserItems.addAll(
				uadDisplay.search(
					userId, groupIds, keywords, orderByField, orderByType,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS));
		}

		for (UADDisplay containerUADDisplay :
				_uadHierarchyDeclaration.getContainerUADDisplays()) {

			searchResults.addAll(
				getContainerDisplays(
					containerUADDisplay, parentContainerClass,
					parentContainerId, allUserItems));
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

	public <T> T unwrap(Object object) {
		if (object instanceof ContainerDisplay) {
			ContainerDisplay<T> containerDisplay = (ContainerDisplay<T>)object;

			return containerDisplay.getContainer();
		}

		return (T)object;
	}

	protected <T> Collection<ContainerDisplay<T>> getContainerDisplays(
		UADDisplay<T> containerUADDisplay, Class<?> parentContainerClass,
		Serializable parentContainerId, List<Object> allUserItems) {

		Map<Serializable, ContainerDisplay<T>> topLevelCategories =
			new HashMap<>();

		Class<T> containerClass = containerUADDisplay.getTypeClass();

		for (Object userItem : allUserItems) {
			if (userItem == null) {
				continue;
			}

			T topLevelContainer = containerUADDisplay.getTopLevelContainer(
				parentContainerClass, parentContainerId, userItem);

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

	private <T> UADDisplay<?> _getUADDisplay(T object) {
		for (Class<?> typeClass : _uadDisplayMap.keySet()) {
			if (typeClass.isInstance(object)) {
				return _uadDisplayMap.get(typeClass);
			}
		}

		return null;
	}

	private final Class<?>[] _containerTypeClasses;
	private final Class<?>[] _typeClasses;
	private Map<Class<?>, UADDisplay<?>> _uadDisplayMap = new HashMap<>();
	private final UADDisplay<?>[] _uadDisplays;
	private final UADHierarchyDeclaration _uadHierarchyDeclaration;

}