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

package com.liferay.user.associated.data.anonymizer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.component.UADComponent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a way to retrieve, count, anonymize, and delete type {@code T}
 * entities for a user. The anonymous user used in the {@link #autoAnonymize}
 * and {@link #autoAnonymizeAll} methods is the anonymous user defined for a
 * particular company by {@code AnonymousUserConfiguration}.
 *
 * @author William Newbury
 * @param  <T> the entity type to be anonymized or deleted
 */
@ProviderType
public interface UADAnonymizer<T> extends UADComponent<T> {

	/**
	 * Anonymizes the given entity and persists the changes to the database. The
	 * primary key of the user is used to match against different fields on the
	 * given entity. The anonymous user is given to provide replacement
	 * user-related data, if needed.
	 *
	 * @param  t the entity to be anonymized
	 * @param  userId the primary key of the user associated with type {@code T}
	 * @param  anonymousUser the company's anonymous user
	 * @throws PortalException if the persistence threw an exception
	 */
	public void autoAnonymize(T t, long userId, User anonymousUser)
		throws PortalException;

	/**
	 * Performs anonymization on all entities of type {@code T} related to the
	 * user. This method is responsible for retrieving the relevant entities,
	 * performing anonymization, and persisting the changes.
	 *
	 * @param  userId the primary key of the user whose data is being anonymized
	 * @param  anonymousUser the company's anonymous user
	 * @throws PortalException if the persistence threw an exception
	 */
	public void autoAnonymizeAll(long userId, User anonymousUser)
		throws PortalException;

	/**
	 * Returns the number of type {@code T} entities associated with the user.
	 *
	 * @param  userId the primary key of the user whose data to count
	 * @return the number of entities associated with the user
	 */
	public long count(long userId) throws PortalException;

	/**
	 * Deletes the entity from the database.
	 *
	 * @param  t the entity to be deleted
	 * @throws PortalException if a portal exception occurred
	 */
	public void delete(T t) throws PortalException;

	/**
	 * Deletes all type {@code T} entities related to the user from the
	 * database.
	 *
	 * @param  userId the primary key of the user whose data to delete
	 * @throws PortalException if a portal exception occurred
	 */
	public void deleteAll(long userId) throws PortalException;

	/**
	 * Returns a map of error messages corresponding to exceptions.
	 *
	 * @param locale the locale of the language
	 */
	public default Map<Class, String> getExceptionMessageMap(Locale locale) {
		return new HashMap<>();
	}

}