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
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;
import com.liferay.user.associated.data.web.internal.util.UADLanguageUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class UADHierarchyDisplay {

	public UADHierarchyDisplay(
		UADHierarchyDeclaration uadHierarchyDeclaration) {

		_uadHierarchyDeclaration = uadHierarchyDeclaration;

		_containerUADDisplays =
			_uadHierarchyDeclaration.getContainerUADDisplays();

		_uadDisplays = ArrayUtil.append(
			_containerUADDisplays,
			_uadHierarchyDeclaration.getNoncontainerUADDisplays());

		for (UADDisplay uadDisplay : _uadDisplays) {
			_uadDisplayMap.put(uadDisplay.getTypeClass(), uadDisplay);
		}

		Stream<UADDisplay> containerUADDisplayStream = Arrays.stream(
			_containerUADDisplays);

		_containerTypeClasses = containerUADDisplayStream.map(
			UADDisplay::getTypeClass
		).toArray(
			Class[]::new
		);

		Stream<UADDisplay> stream = Arrays.stream(_uadDisplays);

		_typeClasses = stream.map(
			UADDisplay::getTypeClass
		).toArray(
			Class[]::new
		);
	}

	public <T> void addPortletBreadcrumbEntries(
			HttpServletRequest request, RenderResponse renderResponse,
			Locale locale)
		throws Exception {

		PortletURL baseURL = renderResponse.createRenderURL();

		String applicationKey = ParamUtil.getString(request, "applicationKey");
		String puid = ParamUtil.getString(request, "p_u_i_d");
		String scope = ParamUtil.getString(request, "scope");

		baseURL.setParameter("applicationKey", applicationKey);
		baseURL.setParameter("p_u_i_d", puid);
		baseURL.setParameter("scope", scope);

		PortletURL applicationURL = PortletURLUtil.clone(
			baseURL, renderResponse);

		applicationURL.setParameter("mvcRenderCommandName", "/review_uad_data");

		String className = ParamUtil.getString(request, "parentContainerClass");

		UADDisplay uadDisplay = _getUADDisplayByClassName(className);

		String applicationName = UADLanguageUtil.getApplicationName(
			uadDisplay, locale);

		PortalUtil.addPortletBreadcrumbEntry(
			request, applicationName, applicationURL.toString());

		List<KeyValuePair> parentBreadcrumbs = new ArrayList<>();

		String primaryKey = ParamUtil.getString(request, "parentContainerId");

		Object object = uadDisplay.get(primaryKey);

		Class<?> parentContainerClass = uadDisplay.getParentContainerClass();
		long parentContainerId = (long)uadDisplay.getParentContainerId(object);

		while (parentContainerId > 0) {
			PortletURL portletURL = PortletURLUtil.clone(
				baseURL, renderResponse);

			portletURL.setParameter(
				"mvcRenderCommandName", "/view_uad_hierarchy");

			UADDisplay parentContainerUADDisplay = _getUADDisplayByClassName(
				parentContainerClass.getName());

			String parentContainerName = parentContainerUADDisplay.getName(
				parentContainerUADDisplay.get(parentContainerId), locale);

			portletURL.setParameter(
				"parentContainerClass", parentContainerClass.getName());
			portletURL.setParameter(
				"parentContainerId", String.valueOf(parentContainerId));

			parentBreadcrumbs.add(
				new KeyValuePair(parentContainerName, portletURL.toString()));

			parentContainerClass =
				parentContainerUADDisplay.getParentContainerClass();
			parentContainerId =
				(long)parentContainerUADDisplay.getParentContainerId(
					parentContainerUADDisplay.get(parentContainerId));
		}

		Collections.reverse(parentBreadcrumbs);

		for (KeyValuePair keyValuePair : parentBreadcrumbs) {
			PortalUtil.addPortletBreadcrumbEntry(
				request, keyValuePair.getKey(), keyValuePair.getValue());
		}

		String name = uadDisplay.getName(object, locale);

		PortalUtil.addPortletBreadcrumbEntry(request, name, null);
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

	public <T> List<Object> getContainerItems(
		Class<?> parentContainerClass, Serializable parentContainerId,
		String typeClassName, long userId) {

		List<Object> containerItems = new ArrayList<>();

		UADDisplay uadDisplay = _getUADDisplayByClassName(typeClassName);

		List<Object> searchItems = uadDisplay.search(
			userId, null, null, null, null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (Object searchItem : searchItems) {
			if (parentContainerId.equals(
					uadDisplay.getParentContainerId(searchItem))) {

				containerItems.add(searchItem);
			}
			else {
				boolean containerItem = false;

				for (UADDisplay containerUADDisplay :
						getContainerUADDisplays()) {

					Object topLevelContainer =
						containerUADDisplay.getTopLevelContainer(
							parentContainerClass, parentContainerId,
							searchItem);

					if (topLevelContainer != null) {
						containerItem = true;
					}
				}

				if (containerItem) {
					containerItems.add(searchItem);
				}
			}
		}

		return containerItems;
	}

	public Class<?>[] getContainerTypeClasses() {
		return _containerTypeClasses;
	}

	public UADDisplay<?>[] getContainerUADDisplays() {
		return _containerUADDisplays;
	}

	public <T> String getEditURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, T object)
		throws Exception {

		T unwrappedObject = unwrap(object);

		UADDisplay uadDisplay = _getUADDisplayByObject(unwrappedObject);

		return uadDisplay.getEditURL(
			unwrappedObject, liferayPortletRequest, liferayPortletResponse);
	}

	public <T> Map<String, Object> getFieldValues(T object, Locale locale) {
		Map<String, Object> fieldValues = new LinkedHashMap<>();

		UADDisplay uadDisplay = _getUADDisplayByObject(unwrap(object));

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
		return _containerTypeClasses[0];
	}

	public <T> Serializable getPrimaryKey(T object) {
		T unwrappedObject = unwrap(object);

		UADDisplay uadDisplay = _getUADDisplayByObject(unwrappedObject);

		return uadDisplay.getPrimaryKey(unwrappedObject);
	}

	public String[] getSortingFieldNames() {
		return getColumnFieldNames();
	}

	public <T> Class<?> getTypeClass(T object) {
		UADDisplay uadDisplay = _getUADDisplayByObject(unwrap(object));

		return uadDisplay.getTypeClass();
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

		UADDisplay uadDisplay = _getUADDisplayByObject(unwrappedObject);

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

	public boolean isContainer(Class<?> typeClass) {
		for (UADDisplay uadDisplay : getContainerUADDisplays()) {
			if (uadDisplay.getTypeClass() == typeClass) {
				return true;
			}
		}

		return false;
	}

	public <T> boolean isUserOwned(T object, long userId) {
		T unwrappedObject = unwrap(object);

		UADDisplay uadDisplay = _getUADDisplayByObject(unwrappedObject);

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

	private <T> UADDisplay<?> _getUADDisplayByClassName(String className) {
		for (Class<?> typeClass : _uadDisplayMap.keySet()) {
			if (className.equals(typeClass.getName())) {
				return _uadDisplayMap.get(typeClass);
			}
		}

		return null;
	}

	private <T> UADDisplay<?> _getUADDisplayByObject(T object) {
		for (Class<?> typeClass : _uadDisplayMap.keySet()) {
			if (typeClass.isInstance(object)) {
				return _uadDisplayMap.get(typeClass);
			}
		}

		return null;
	}

	private final Class<?>[] _containerTypeClasses;
	private final UADDisplay<?>[] _containerUADDisplays;
	private final Class<?>[] _typeClasses;
	private Map<Class<?>, UADDisplay<?>> _uadDisplayMap = new HashMap<>();
	private final UADDisplay<?>[] _uadDisplays;
	private final UADHierarchyDeclaration _uadHierarchyDeclaration;

}