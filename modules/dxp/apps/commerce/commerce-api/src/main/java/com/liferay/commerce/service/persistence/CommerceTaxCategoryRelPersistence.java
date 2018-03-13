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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchTaxCategoryRelException;
import com.liferay.commerce.model.CommerceTaxCategoryRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce tax category rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceTaxCategoryRelPersistenceImpl
 * @see CommerceTaxCategoryRelUtil
 * @generated
 */
@ProviderType
public interface CommerceTaxCategoryRelPersistence extends BasePersistence<CommerceTaxCategoryRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTaxCategoryRelUtil} to access the commerce tax category rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @return the matching commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId);

	/**
	* Returns a range of all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @return the range of matching commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findByCommerceTaxCategoryId(
		long commerceTaxCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel findByCommerceTaxCategoryId_First(
		long commerceTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws NoSuchTaxCategoryRelException;

	/**
	* Returns the first commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel fetchByCommerceTaxCategoryId_First(
		long commerceTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator);

	/**
	* Returns the last commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel findByCommerceTaxCategoryId_Last(
		long commerceTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws NoSuchTaxCategoryRelException;

	/**
	* Returns the last commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel fetchByCommerceTaxCategoryId_Last(
		long commerceTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator);

	/**
	* Returns the commerce tax category rels before and after the current commerce tax category rel in the ordered set where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryRelId the primary key of the current commerce tax category rel
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	*/
	public CommerceTaxCategoryRel[] findByCommerceTaxCategoryId_PrevAndNext(
		long commerceTaxCategoryRelId, long commerceTaxCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws NoSuchTaxCategoryRelException;

	/**
	* Removes all the commerce tax category rels where commerceTaxCategoryId = &#63; from the database.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	*/
	public void removeByCommerceTaxCategoryId(long commerceTaxCategoryId);

	/**
	* Returns the number of commerce tax category rels where commerceTaxCategoryId = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @return the number of matching commerce tax category rels
	*/
	public int countByCommerceTaxCategoryId(long commerceTaxCategoryId);

	/**
	* Returns all the commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findByC_C(long classNameId,
		long classPK);

	/**
	* Returns a range of all the commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @return the range of matching commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel findByC_C_First(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws NoSuchTaxCategoryRelException;

	/**
	* Returns the first commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel fetchByC_C_First(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator);

	/**
	* Returns the last commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel findByC_C_Last(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws NoSuchTaxCategoryRelException;

	/**
	* Returns the last commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel fetchByC_C_Last(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator);

	/**
	* Returns the commerce tax category rels before and after the current commerce tax category rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceTaxCategoryRelId the primary key of the current commerce tax category rel
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	*/
	public CommerceTaxCategoryRel[] findByC_C_PrevAndNext(
		long commerceTaxCategoryRelId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator)
		throws NoSuchTaxCategoryRelException;

	/**
	* Removes all the commerce tax category rels where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of commerce tax category rels where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce tax category rels
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns the commerce tax category rel where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchTaxCategoryRelException} if it could not be found.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel findByC_C_C(long commerceTaxCategoryId,
		long classNameId, long classPK) throws NoSuchTaxCategoryRelException;

	/**
	* Returns the commerce tax category rel where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel fetchByC_C_C(long commerceTaxCategoryId,
		long classNameId, long classPK);

	/**
	* Returns the commerce tax category rel where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tax category rel, or <code>null</code> if a matching commerce tax category rel could not be found
	*/
	public CommerceTaxCategoryRel fetchByC_C_C(long commerceTaxCategoryId,
		long classNameId, long classPK, boolean retrieveFromCache);

	/**
	* Removes the commerce tax category rel where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the commerce tax category rel that was removed
	*/
	public CommerceTaxCategoryRel removeByC_C_C(long commerceTaxCategoryId,
		long classNameId, long classPK) throws NoSuchTaxCategoryRelException;

	/**
	* Returns the number of commerce tax category rels where commerceTaxCategoryId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceTaxCategoryId the commerce tax category ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce tax category rels
	*/
	public int countByC_C_C(long commerceTaxCategoryId, long classNameId,
		long classPK);

	/**
	* Caches the commerce tax category rel in the entity cache if it is enabled.
	*
	* @param commerceTaxCategoryRel the commerce tax category rel
	*/
	public void cacheResult(CommerceTaxCategoryRel commerceTaxCategoryRel);

	/**
	* Caches the commerce tax category rels in the entity cache if it is enabled.
	*
	* @param commerceTaxCategoryRels the commerce tax category rels
	*/
	public void cacheResult(
		java.util.List<CommerceTaxCategoryRel> commerceTaxCategoryRels);

	/**
	* Creates a new commerce tax category rel with the primary key. Does not add the commerce tax category rel to the database.
	*
	* @param commerceTaxCategoryRelId the primary key for the new commerce tax category rel
	* @return the new commerce tax category rel
	*/
	public CommerceTaxCategoryRel create(long commerceTaxCategoryRelId);

	/**
	* Removes the commerce tax category rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel that was removed
	* @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	*/
	public CommerceTaxCategoryRel remove(long commerceTaxCategoryRelId)
		throws NoSuchTaxCategoryRelException;

	public CommerceTaxCategoryRel updateImpl(
		CommerceTaxCategoryRel commerceTaxCategoryRel);

	/**
	* Returns the commerce tax category rel with the primary key or throws a {@link NoSuchTaxCategoryRelException} if it could not be found.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel
	* @throws NoSuchTaxCategoryRelException if a commerce tax category rel with the primary key could not be found
	*/
	public CommerceTaxCategoryRel findByPrimaryKey(
		long commerceTaxCategoryRelId) throws NoSuchTaxCategoryRelException;

	/**
	* Returns the commerce tax category rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel, or <code>null</code> if a commerce tax category rel with the primary key could not be found
	*/
	public CommerceTaxCategoryRel fetchByPrimaryKey(
		long commerceTaxCategoryRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceTaxCategoryRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce tax category rels.
	*
	* @return the commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findAll();

	/**
	* Returns a range of all the commerce tax category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @return the range of commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce tax category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tax category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce tax category rels
	*/
	public java.util.List<CommerceTaxCategoryRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategoryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce tax category rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce tax category rels.
	*
	* @return the number of commerce tax category rels
	*/
	public int countAll();
}