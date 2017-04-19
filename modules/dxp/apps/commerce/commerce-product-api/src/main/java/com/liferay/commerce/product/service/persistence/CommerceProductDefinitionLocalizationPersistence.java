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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException;
import com.liferay.commerce.product.model.CommerceProductDefinitionLocalization;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce product definition localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductDefinitionLocalizationPersistenceImpl
 * @see CommerceProductDefinitionLocalizationUtil
 * @generated
 */
@ProviderType
public interface CommerceProductDefinitionLocalizationPersistence
	extends BasePersistence<CommerceProductDefinitionLocalization> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductDefinitionLocalizationUtil} to access the commerce product definition localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @return the matching commerce product definition localizations
	*/
	public java.util.List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK);

	/**
	* Returns a range of all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @return the range of matching commerce product definition localizations
	*/
	public java.util.List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK, int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition localizations
	*/
	public java.util.List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition localizations
	*/
	public java.util.List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a matching commerce product definition localization could not be found
	*/
	public CommerceProductDefinitionLocalization findByCommerceProductDefinitionPK_First(
		long commerceProductDefinitionPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator)
		throws NoSuchProductDefinitionLocalizationException;

	/**
	* Returns the first commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	*/
	public CommerceProductDefinitionLocalization fetchByCommerceProductDefinitionPK_First(
		long commerceProductDefinitionPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator);

	/**
	* Returns the last commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a matching commerce product definition localization could not be found
	*/
	public CommerceProductDefinitionLocalization findByCommerceProductDefinitionPK_Last(
		long commerceProductDefinitionPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator)
		throws NoSuchProductDefinitionLocalizationException;

	/**
	* Returns the last commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	*/
	public CommerceProductDefinitionLocalization fetchByCommerceProductDefinitionPK_Last(
		long commerceProductDefinitionPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator);

	/**
	* Returns the commerce product definition localizations before and after the current commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionLocalizationId the primary key of the current commerce product definition localization
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	*/
	public CommerceProductDefinitionLocalization[] findByCommerceProductDefinitionPK_PrevAndNext(
		long commerceProductDefinitionLocalizationId,
		long commerceProductDefinitionPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator)
		throws NoSuchProductDefinitionLocalizationException;

	/**
	* Removes all the commerce product definition localizations where commerceProductDefinitionPK = &#63; from the database.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	*/
	public void removeByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK);

	/**
	* Returns the number of commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @return the number of matching commerce product definition localizations
	*/
	public int countByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK);

	/**
	* Returns the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; or throws a {@link NoSuchProductDefinitionLocalizationException} if it could not be found.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @return the matching commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a matching commerce product definition localization could not be found
	*/
	public CommerceProductDefinitionLocalization findByCPD_L(
		long commerceProductDefinitionPK, java.lang.String languageId)
		throws NoSuchProductDefinitionLocalizationException;

	/**
	* Returns the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @return the matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	*/
	public CommerceProductDefinitionLocalization fetchByCPD_L(
		long commerceProductDefinitionPK, java.lang.String languageId);

	/**
	* Returns the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	*/
	public CommerceProductDefinitionLocalization fetchByCPD_L(
		long commerceProductDefinitionPK, java.lang.String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; from the database.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @return the commerce product definition localization that was removed
	*/
	public CommerceProductDefinitionLocalization removeByCPD_L(
		long commerceProductDefinitionPK, java.lang.String languageId)
		throws NoSuchProductDefinitionLocalizationException;

	/**
	* Returns the number of commerce product definition localizations where commerceProductDefinitionPK = &#63; and languageId = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @return the number of matching commerce product definition localizations
	*/
	public int countByCPD_L(long commerceProductDefinitionPK,
		java.lang.String languageId);

	/**
	* Caches the commerce product definition localization in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionLocalization the commerce product definition localization
	*/
	public void cacheResult(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization);

	/**
	* Caches the commerce product definition localizations in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionLocalizations the commerce product definition localizations
	*/
	public void cacheResult(
		java.util.List<CommerceProductDefinitionLocalization> commerceProductDefinitionLocalizations);

	/**
	* Creates a new commerce product definition localization with the primary key. Does not add the commerce product definition localization to the database.
	*
	* @param commerceProductDefinitionLocalizationId the primary key for the new commerce product definition localization
	* @return the new commerce product definition localization
	*/
	public CommerceProductDefinitionLocalization create(
		long commerceProductDefinitionLocalizationId);

	/**
	* Removes the commerce product definition localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionLocalizationId the primary key of the commerce product definition localization
	* @return the commerce product definition localization that was removed
	* @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	*/
	public CommerceProductDefinitionLocalization remove(
		long commerceProductDefinitionLocalizationId)
		throws NoSuchProductDefinitionLocalizationException;

	public CommerceProductDefinitionLocalization updateImpl(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization);

	/**
	* Returns the commerce product definition localization with the primary key or throws a {@link NoSuchProductDefinitionLocalizationException} if it could not be found.
	*
	* @param commerceProductDefinitionLocalizationId the primary key of the commerce product definition localization
	* @return the commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	*/
	public CommerceProductDefinitionLocalization findByPrimaryKey(
		long commerceProductDefinitionLocalizationId)
		throws NoSuchProductDefinitionLocalizationException;

	/**
	* Returns the commerce product definition localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductDefinitionLocalizationId the primary key of the commerce product definition localization
	* @return the commerce product definition localization, or <code>null</code> if a commerce product definition localization with the primary key could not be found
	*/
	public CommerceProductDefinitionLocalization fetchByPrimaryKey(
		long commerceProductDefinitionLocalizationId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceProductDefinitionLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce product definition localizations.
	*
	* @return the commerce product definition localizations
	*/
	public java.util.List<CommerceProductDefinitionLocalization> findAll();

	/**
	* Returns a range of all the commerce product definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @return the range of commerce product definition localizations
	*/
	public java.util.List<CommerceProductDefinitionLocalization> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the commerce product definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product definition localizations
	*/
	public java.util.List<CommerceProductDefinitionLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product definition localizations
	*/
	public java.util.List<CommerceProductDefinitionLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce product definition localizations from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce product definition localizations.
	*
	* @return the number of commerce product definition localizations
	*/
	public int countAll();
}