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

package com.liferay.user.associated.data.display;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.user.associated.data.component.UADComponent;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the methods to count, retrieve, and display information about type
 * {@code T} entities related to a user. This interface can also provide a URL
 * to allow an admin to edit an entity.
 *
 * @author William Newbury
 */
public interface UADDisplay<T> extends UADComponent<T> {

	/**
	 * Returns the number of type {@code T} entities associated with the the
	 * user.
	 *
	 * @param  userId the primary key of the user whose data to count
	 * @return the number of entities associated with the user
	 */
	public long count(long userId);

	/**
	 * Retrieves a type {@code T} entity.
	 *
	 * @param  primaryKey the primary key of the entity to retrieve
	 * @return an entity of type {@code T}
	 * @throws Exception if an exception occurred
	 */
	public T get(Serializable primaryKey) throws Exception;

	/**
	 * Returns field names to be used as table column headers when displaying a
	 * list of type {@code T} entities.
	 *
	 * @return the field names used for column headers
	 */
	public default String[] getColumnFieldNames() {
		return getDisplayFieldNames();
	}

	/**
	 * Returns field names to display when showing details about a type {@code
	 * T} entity.
	 *
	 * @return the field names identifying which information to display
	 */
	public String[] getDisplayFieldNames();

	/**
	 * Returns a string URL that allows the admin user to edit the entity. If
	 * {@code null} is returned, no edit option is presented to the admin user.
	 *
	 * @param  t the type {@code T} entity
	 * @param  liferayPortletRequest the current portlet request
	 * @param  liferayPortletResponse the current portlet response
	 * @return a string URL, or {@code null}
	 * @throws Exception if an exception occurred
	 */
	public default String getEditURL(
			T t, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	/**
	 * Returns a map of field names and values to display in the UI.
	 *
	 * @param  t the type {@code T} entity
	 * @param  fieldNames the field names for getting values from the entity
	 * @param  locale the current locale
	 * @return a map of values to display in the UI
	 */
	public Map<String, Object> getFieldValues(
		T t, String[] fieldNames, Locale locale);

	public default String getName(T t, Locale locale) {
		throw new UnsupportedOperationException();
	}

	public default Class<?> getParentContainerClass() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the primary key of the parent container for the given entity.
	 *
	 * <p>
	 * This method is optional and only applies when the implementation is
	 * returned from {@link UADHierarchyDeclaration#getContainerUADDisplays()}
	 * or {@link UADHierarchyDeclaration#getNoncontainerUADDisplays()}. It is
	 * required for hierarchy display to function correctly, but not for normal
	 * usage.
	 * </p>
	 *
	 * @param  t the entity whose parent container's primary key to retreive
	 * @return the primary key of the parent container of the given entity
	 * @see    UADHierarchyDeclaration
	 */
	public default Serializable getParentContainerId(T t) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the primary key of the type {@code T} entity.
	 *
	 * @param  t the entity whose primary key to retrieve
	 * @return the primary key of the entity
	 */
	public Serializable getPrimaryKey(T t);

	/**
	 * Returns type {@code T} entities in the given range associated with a
	 * user.
	 *
	 * @param  start the starting index of the result set, for pagination
	 * @param  end the ending index of the result set, for pagination
	 * @return the paginated entities related to the user ID
	 */
	public List<T> getRange(long userId, int start, int end);

	/**
	 * Returns the field names to be used as table column headers when sorting
	 * type {@code T} entities.
	 *
	 * @return the field names used for column headers when sorting
	 */
	public String[] getSortingFieldNames();

	/**
	 * Returns the type {@code T} entity that satisfies two conditions:
	 *
	 * <ol>
	 * <li>
	 * It is an immediate child of the container identified by the parent
	 * container class and parent container ID.
	 * </li>
	 * <li>
	 * It is an ancestor of the child object. It does not have to be an
	 * immediate ancestor.
	 * </li>
	 * </ol>
	 *
	 * <p>
	 * If neither of these conditions are met, this method should return {@code
	 * null}.
	 * </p>
	 *
	 * <p>
	 * This method is optional and only applies when the implementation is
	 * returned from {@link UADHierarchyDeclaration#getContainerUADDisplays()}.
	 * It is required for hierarchy display to function correctly, but not for
	 * normal usage.
	 * </p>
	 *
	 * @param  parentContainerClass the class identifying the returned entity's
	 *         parent container
	 * @param  parentContainerId the primary key of the returned entity's parent
	 *         container
	 * @param  childObject the returned entity's child
	 * @return the highest level parent of the child object that is also a child
	 *         of the given container type and primary key
	 * @see    UADHierarchyDeclaration
	 */
	public default T getTopLevelContainer(
		Class<?> parentContainerClass, Serializable parentContainerId,
		Object childObject) {

		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the localized string representing type {@code T}.
	 *
	 * @param  locale the current locale
	 * @return the localized string representing type {@code T}
	 */
	public String getTypeName(Locale locale);

	public boolean isInTrash(T t)
		throws IllegalAccessException, InvocationTargetException;

	/**
	 * Returns <code>true</code> if type {@code T} entities are scoped by site.
	 *
	 * @return <code>true</code> if type {@code T} entities are scoped by site;
	 *         <code>false</code> otherwise
	 */
	public default boolean isSiteScoped() {
		return false;
	}

	public default boolean isUserOwned(T t, long userId) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns paginated sorted type {@code T} entities related to the user,
	 * optionally filtered by groups and/or keywords.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupIds the primary keys of the groups that the entities are
	 *         associated with
	 * @param  keywords the keywords which may occur in the entity's fields
	 * @param  orderByField the field to sort the entities by
	 * @param  orderByType the direction to sort the entities by (ascending or
	 *         descending)
	 * @param  start the result set's starting index
	 * @param  end the result set's ending index
	 * @return the paginated, sorted, and filtered entities associated with the
	 *         user
	 */
	public List<T> search(
		long userId, long[] groupIds, String keywords, String orderByField,
		String orderByType, int start, int end);

	/**
	 * Returns the number of type {@code T} entities related to the user,
	 * optionally filtered by groups and/or keywords.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupIds the primary keys of the groups that the entities are
	 *         associated with
	 * @param  keywords the keywords which may occur in the entity's fields
	 * @return the number of filtered entities associated with the user
	 */
	public long searchCount(long userId, long[] groupIds, String keywords);

}