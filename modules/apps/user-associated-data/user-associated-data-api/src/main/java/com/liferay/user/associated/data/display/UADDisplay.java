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

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the necessary methods to count, retrieve, and display information
 * about entities of type {@code T} related to a user. Can also provide a URL to
 * allow an admin to edit the an entity.
 *
 * @author William Newbury
 * @review
 */
public interface UADDisplay<T> extends UADComponent<T> {

	/**
	 * Returns the number of entities of type {@code T} associated with the
	 * a user.
	 *
	 * @param userId the primary key of the user whose data to count
	 * @return the number of entities associated with the user
	 * @review
	 */
	public long count(long userId);

	/**
	 * Retrieves an entity of type {@code T}.
	 *
	 * @param primaryKey the primary key of the entity to retrieve
	 * @return an entity of type {@code T}
	 * @throws Exception
	 * @review
	 */
	public T get(Serializable primaryKey) throws Exception;

	/**
	 * Returns field names to be used as table column headers when displaying a
	 * list of entities of type {@code T}.
	 *
	 * @return field names used for column headers
	 * @review
	 */
	public default String[] getColumnFieldNames() {
		return getDisplayFieldNames();
	}

	/**
	 * Returns names of fields to display when showing details about an entity
	 * of type {@code T}.
	 *
	 * @return field names identifying which information to display
	 * @review
	 */
	public String[] getDisplayFieldNames();

	/**
	 * Returns a string URL that allows the admin user to edit the entity. If
	 * {@code null} is returned then no edit option will be presented to the
	 * admin user.
	 *
	 * @param t the entity of type {@code T}
	 * @param liferayPortletRequest the current portlet request
	 * @param liferayPortletResponse the current portlet response
	 * @return a string URL, or {@code null}
	 * @throws Exception
	 * @review
	 */
	public default String getEditURL(
			T t, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	/**
	 * Returns a map of field names and values to be displayed in the UI.
	 *
	 * @param t the entity of type {@code T}
	 * @param fieldNames the field names for getting values from the entity
	 * @return a map of values to be displayed in the UI
	 * @review
	 */
	public Map<String, Object> getFieldValues(T t, String[] fieldNames);

	/**
	 * Returns the primary key of the entity of type {@code T}.
	 *
	 * @param t the entity to retrieve the primary key for
	 * @return the primary key of the entity
	 * @review
	 */
	public Serializable getPrimaryKey(T t);

	/**
	 * Returns entities of type {@code T} in the given range associated with a
	 * user.
	 *
	 * @param start the starting index of the result set, for pagination
	 * @param end the ending index of the result set, for pagination
	 * @return paginated entities related to the user ID
	 * @review
	 */
	public List<T> getRange(long userId, int start, int end);

	/**
	 * Returns the field names to be used as table column headers when sorting a
	 * list of entities of type {@code T}
	 *
	 * @return field names used for column headers when sorting
	 * @review
	 */
	public String[] getSortingFieldNames();

	/**
	 * Returns a localized string representing type {@code T}.
	 *
	 * @param locale the current locale
	 * @return a localized string representing type {@code T}
	 * @review
	 */
	public String getTypeName(Locale locale);

	/**
	 * Returns paginated sorted entities of type {@code T} related to a user,
	 * optionally filtered by groups and/or keywords.
	 *
	 * @param userId the primary key of the user
	 * @param groupIds the primary keys of the groups that the entities are
	 *                 associated with
	 * @param keywords the keywords which may occur in the entity's fields
	 * @param orderByField the field to sort the entities by
	 * @param orderByType the direction to sort the entities by, ascending or
	 *                    descending
	 * @param start the starting index of the result set
	 * @param end the ending index of the result set
	 * @return paginated sorted filtered entities associated with a user
	 * @review
	 */
	public List<T> search(
		long userId, long[] groupIds, String keywords, String orderByField,
		String orderByType, int start, int end);

	/**
	 * Returns a count of the number of entities of type {@code T} related to a
	 * user, optionally filtered by groups and/or keywords
	 *
	 * @param userId the primary key of the user
	 * @param groupIds the primary keys of the groups that the entities are
	 *                 associated with
	 * @param keywords the keywords which may occur in the entity's fields
	 * @return the number of filtered entities associated with a user
	 * @review
	 */
	public long searchCount(long userId, long[] groupIds, String keywords);

}