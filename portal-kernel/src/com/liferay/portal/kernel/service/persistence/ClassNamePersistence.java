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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchClassNameException;
import com.liferay.portal.kernel.model.ClassName;

/**
 * The persistence interface for the class name service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClassNameUtil
 * @generated
 */
@ProviderType
public interface ClassNamePersistence extends BasePersistence<ClassName> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ClassNameUtil} to access the class name persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the class name where value = &#63; or throws a <code>NoSuchClassNameException</code> if it could not be found.
	*
	* @param value the value
	* @return the matching class name
	* @throws NoSuchClassNameException if a matching class name could not be found
	*/
	public ClassName findByValue(String value) throws NoSuchClassNameException;

	/**
	* Returns the class name where value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param value the value
	* @return the matching class name, or <code>null</code> if a matching class name could not be found
	*/
	public ClassName fetchByValue(String value);

	/**
	* Returns the class name where value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param value the value
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching class name, or <code>null</code> if a matching class name could not be found
	*/
	public ClassName fetchByValue(String value, boolean retrieveFromCache);

	/**
	* Removes the class name where value = &#63; from the database.
	*
	* @param value the value
	* @return the class name that was removed
	*/
	public ClassName removeByValue(String value)
		throws NoSuchClassNameException;

	/**
	* Returns the number of class names where value = &#63;.
	*
	* @param value the value
	* @return the number of matching class names
	*/
	public int countByValue(String value);

	/**
	* Caches the class name in the entity cache if it is enabled.
	*
	* @param className the class name
	*/
	public void cacheResult(ClassName className);

	/**
	* Caches the class names in the entity cache if it is enabled.
	*
	* @param classNames the class names
	*/
	public void cacheResult(java.util.List<ClassName> classNames);

	/**
	* Creates a new class name with the primary key. Does not add the class name to the database.
	*
	* @param classNameId the primary key for the new class name
	* @return the new class name
	*/
	public ClassName create(long classNameId);

	/**
	* Removes the class name with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param classNameId the primary key of the class name
	* @return the class name that was removed
	* @throws NoSuchClassNameException if a class name with the primary key could not be found
	*/
	public ClassName remove(long classNameId) throws NoSuchClassNameException;

	public ClassName updateImpl(ClassName className);

	/**
	* Returns the class name with the primary key or throws a <code>NoSuchClassNameException</code> if it could not be found.
	*
	* @param classNameId the primary key of the class name
	* @return the class name
	* @throws NoSuchClassNameException if a class name with the primary key could not be found
	*/
	public ClassName findByPrimaryKey(long classNameId)
		throws NoSuchClassNameException;

	/**
	* Returns the class name with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param classNameId the primary key of the class name
	* @return the class name, or <code>null</code> if a class name with the primary key could not be found
	*/
	public ClassName fetchByPrimaryKey(long classNameId);

	/**
	* Returns all the class names.
	*
	* @return the class names
	*/
	public java.util.List<ClassName> findAll();

	/**
	* Returns a range of all the class names.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ClassNameModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of class names
	* @param end the upper bound of the range of class names (not inclusive)
	* @return the range of class names
	*/
	public java.util.List<ClassName> findAll(int start, int end);

	/**
	* Returns an ordered range of all the class names.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ClassNameModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of class names
	* @param end the upper bound of the range of class names (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of class names
	*/
	public java.util.List<ClassName> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClassName> orderByComparator);

	/**
	* Returns an ordered range of all the class names.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ClassNameModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of class names
	* @param end the upper bound of the range of class names (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of class names
	*/
	public java.util.List<ClassName> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClassName> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the class names from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of class names.
	*
	* @return the number of class names
	*/
	public int countAll();
}