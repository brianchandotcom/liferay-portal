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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;
import com.liferay.user.associated.data.web.internal.util.UADLanguageUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;

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

import javax.portlet.ActionRequest;
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

		Stream<UADDisplay> containerUADDisplayStream = Arrays.stream(
			_containerUADDisplays);

		_containerTypeClasses = containerUADDisplayStream.map(
			UADDisplay::getTypeClass
		).toArray(
			Class[]::new
		);

		_uadDisplays = ArrayUtil.append(
			_containerUADDisplays,
			_uadHierarchyDeclaration.getNoncontainerUADDisplays());

		Map<Class<?>, UADDisplay<?>> uadDisplayMap = new LinkedHashMap<>();

		for (UADDisplay uadDisplay : _uadDisplays) {
			uadDisplayMap.put(uadDisplay.getTypeClass(), uadDisplay);
		}

		_uadDisplayMap = uadDisplayMap;

		Stream<UADDisplay> stream = Arrays.stream(_uadDisplays);

		_typeClasses = stream.map(
			UADDisplay::getTypeClass
		).toArray(
			Class[]::new
		);
	}

	public <T> void addPortletBreadcrumbEntries(
			HttpServletRequest httpServletRequest,
			RenderResponse renderResponse, Locale locale)
		throws Exception {

		PortletURL baseURL = renderResponse.createRenderURL();

		String applicationKey = ParamUtil.getString(
			httpServletRequest, "applicationKey");
		String puid = ParamUtil.getString(httpServletRequest, "p_u_i_d");
		String scope = ParamUtil.getString(httpServletRequest, "scope");

		baseURL.setParameter("applicationKey", applicationKey);
		baseURL.setParameter("p_u_i_d", puid);

		if (Validator.isNotNull(scope)) {
			baseURL.setParameter("scope", scope);
		}

		PortletURL applicationURL = PortletURLUtil.clone(
			baseURL, renderResponse);

		applicationURL.setParameter("mvcRenderCommandName", "/review_uad_data");

		String className = ParamUtil.getString(
			httpServletRequest, "parentContainerClass");

		UADDisplay uadDisplay = _getUADDisplayByTypeClassName(className);

		String applicationName = UADLanguageUtil.getApplicationName(
			uadDisplay, locale);

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest, applicationName, applicationURL.toString());

		List<KeyValuePair> parentBreadcrumbs = new ArrayList<>();

		String primaryKey = ParamUtil.getString(
			httpServletRequest, "parentContainerId");

		Object container = uadDisplay.get(primaryKey);

		Class<?> parentContainerClass = uadDisplay.getParentContainerClass();
		String parentContainerId = String.valueOf(
			uadDisplay.getParentContainerId(container));

		while (!parentContainerId.equals("0") &&
			   !parentContainerId.equals("-1")) {

			PortletURL portletURL = PortletURLUtil.clone(
				baseURL, renderResponse);

			portletURL.setParameter(
				"mvcRenderCommandName", "/view_uad_hierarchy");

			UADDisplay parentContainerUADDisplay = _getUADDisplayByTypeClass(
				parentContainerClass);

			String parentContainerName = parentContainerUADDisplay.getName(
				parentContainerUADDisplay.get(parentContainerId), locale);

			portletURL.setParameter(
				"parentContainerClass", parentContainerClass.getName());
			portletURL.setParameter("parentContainerId", parentContainerId);

			parentBreadcrumbs.add(
				new KeyValuePair(parentContainerName, portletURL.toString()));

			parentContainerClass =
				parentContainerUADDisplay.getParentContainerClass();
			parentContainerId = String.valueOf(
				parentContainerUADDisplay.getParentContainerId(
					parentContainerUADDisplay.get(parentContainerId)));
		}

		Collections.reverse(parentBreadcrumbs);

		for (KeyValuePair keyValuePair : parentBreadcrumbs) {
			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest, keyValuePair.getKey(),
				keyValuePair.getValue());
		}

		String name = uadDisplay.getName(container, locale);

		PortalUtil.addPortletBreadcrumbEntry(httpServletRequest, name, null);
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

	public Map<Class<?>, List<Serializable>> getContainerItemPKsMap(
		Class<?> parentContainerClass, Serializable parentContainerId,
		long userId) {

		Map<Class<?>, List<Serializable>> containerItemPKsMap =
			new LinkedHashMap<>();

		if (ArrayUtil.contains(_containerTypeClasses, parentContainerClass)) {
			for (UADDisplay containerItemUADDisplay : _uadDisplays) {
				Class<?> containerItemTypeClass =
					containerItemUADDisplay.getTypeClass();

				List<Serializable> containerItemPKs = _getContainerItemPKs(
					parentContainerClass, parentContainerId,
					containerItemTypeClass, userId);

				_addEntities(
					containerItemPKsMap, containerItemPKs,
					containerItemTypeClass);
			}
		}

		return containerItemPKsMap;
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

	public String getEntitiesTypeLabel(Locale locale) {
		return _uadHierarchyDeclaration.getEntitiesTypeLabel(locale);
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

	public <T> String getParentContainerURL(
			ActionRequest actionRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		String className = ParamUtil.getString(
			actionRequest, "parentContainerClass");

		if (Validator.isNull(className)) {
			return null;
		}

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String applicationKey = ParamUtil.getString(
			actionRequest, "applicationKey");
		String primaryKey = ParamUtil.getString(
			actionRequest, "parentContainerId");
		String puid = ParamUtil.getString(actionRequest, "p_u_i_d");
		String scope = ParamUtil.getString(actionRequest, "scope");

		portletURL.setParameter("applicationKey", applicationKey);
		portletURL.setParameter("p_u_i_d", puid);

		if (Validator.isNotNull(scope)) {
			portletURL.setParameter("scope", scope);
		}

		UADDisplay uadDisplay = _getUADDisplayByTypeClassName(className);

		Object container = uadDisplay.get(primaryKey);

		Class<?> parentContainerClass = uadDisplay.getParentContainerClass();

		String parentContainerId = String.valueOf(
			uadDisplay.getParentContainerId(container));

		if (parentContainerId.equals("0") || parentContainerId.equals("-1")) {
			portletURL.setParameter("mvcRenderCommandName", "/review_uad_data");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/view_uad_hierarchy");
			portletURL.setParameter(
				"parentContainerClass", parentContainerClass.getName());
			portletURL.setParameter("parentContainerId", parentContainerId);
		}

		return portletURL.toString();
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

		renderURL.setParameter("mvcRenderCommandName", "/view_uad_hierarchy");
		renderURL.setParameter("applicationKey", applicationKey);
		renderURL.setParameter("parentContainerClass", typeClass.getName());
		renderURL.setParameter(
			"parentContainerId",
			String.valueOf(uadDisplay.getPrimaryKey(unwrappedObject)));
		renderURL.setParameter("p_u_i_d", String.valueOf(selectedUserId));

		String scope = ParamUtil.getString(liferayPortletRequest, "scope");

		renderURL.setParameter("scope", scope);

		return renderURL.toString();
	}

	public <T> boolean isInTrash(T object)
		throws IllegalAccessException, InvocationTargetException {

		T unwrappedObject = unwrap(object);

		UADDisplay uadDisplay = _getUADDisplayByObject(unwrappedObject);

		return uadDisplay.isInTrash(unwrappedObject);
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

	public long searchCount(long userId, long[] groupIds, String keywords) {
		long count = 0;

		for (UADDisplay uadDisplay : _uadDisplays) {
			count += uadDisplay.searchCount(userId, groupIds, keywords);
		}

		return count;
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
				Objects.equals(
					containerUADDisplay.getParentContainerId((T)userItem),
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

	private void _addEntities(
		Map<Class<?>, List<Serializable>> entitiesMap,
		List<Serializable> entities, Class<?> typeClass) {

		if (!entitiesMap.containsKey(typeClass)) {
			entitiesMap.put(typeClass, new ArrayList<>());
		}

		List<Serializable> entitiesList = entitiesMap.get(typeClass);

		entitiesList.addAll(entities);
	}

	private <T> List<Serializable> _getContainerItemPKs(
		Class<?> parentContainerClass, Serializable parentContainerId,
		Class<?> typeClass, long userId) {

		List<Serializable> containerItemPKs = new ArrayList<>();

		UADDisplay uadDisplay = _getUADDisplayByTypeClass(typeClass);

		List<Object> searchItems = uadDisplay.search(
			userId, null, null, null, null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (Object searchItem : searchItems) {
			if (parentContainerId.equals(
					uadDisplay.getParentContainerId(searchItem))) {

				containerItemPKs.add(uadDisplay.getPrimaryKey(searchItem));
			}
			else {
				boolean containerItem = false;

				for (UADDisplay containerUADDisplay : _containerUADDisplays) {
					Object topLevelContainer =
						containerUADDisplay.getTopLevelContainer(
							parentContainerClass, parentContainerId,
							searchItem);

					if (topLevelContainer != null) {
						containerItem = true;
					}
				}

				if (containerItem) {
					containerItemPKs.add(uadDisplay.getPrimaryKey(searchItem));
				}
			}
		}

		return containerItemPKs;
	}

	private <T> UADDisplay<?> _getUADDisplayByObject(T object) {
		for (Class<?> typeClass : _uadDisplayMap.keySet()) {
			if (typeClass.isInstance(object)) {
				return _uadDisplayMap.get(typeClass);
			}
		}

		return null;
	}

	private <T> UADDisplay<?> _getUADDisplayByTypeClass(Class<?> typeClass) {
		if (!_uadDisplayMap.containsKey(typeClass)) {
			return null;
		}

		return _uadDisplayMap.get(typeClass);
	}

	private <T> UADDisplay<?> _getUADDisplayByTypeClassName(
		String typeClassName) {

		for (Class<?> typeClass : _uadDisplayMap.keySet()) {
			if (typeClassName.equals(typeClass.getName())) {
				return _uadDisplayMap.get(typeClass);
			}
		}

		return null;
	}

	private final Class<?>[] _containerTypeClasses;
	private final UADDisplay<?>[] _containerUADDisplays;
	private final Class<?>[] _typeClasses;
	private final Map<Class<?>, UADDisplay<?>> _uadDisplayMap;
	private final UADDisplay<?>[] _uadDisplays;
	private final UADHierarchyDeclaration _uadHierarchyDeclaration;

}