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

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCSDiagramPinException;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cs diagram pin service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramPinUtil
 * @generated
 */
@ProviderType
public interface CSDiagramPinPersistence
	extends BasePersistence<CSDiagramPin>, CTPersistence<CSDiagramPin> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CSDiagramPinUtil} to access the cs diagram pin persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cs diagram pins
	 */
	public java.util.List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId);

	/**
	 * Returns a range of all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @return the range of matching cs diagram pins
	 */
	public java.util.List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cs diagram pins
	 */
	public java.util.List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramPin>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cs diagram pins
	 */
	public java.util.List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramPin>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a matching cs diagram pin could not be found
	 */
	public CSDiagramPin findByCPDefinitionId_First(
			long CPDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<CSDiagramPin>
				orderByComparator)
		throws NoSuchCSDiagramPinException;

	/**
	 * Returns the first cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram pin, or <code>null</code> if a matching cs diagram pin could not be found
	 */
	public CSDiagramPin fetchByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramPin>
			orderByComparator);

	/**
	 * Returns the last cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a matching cs diagram pin could not be found
	 */
	public CSDiagramPin findByCPDefinitionId_Last(
			long CPDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<CSDiagramPin>
				orderByComparator)
		throws NoSuchCSDiagramPinException;

	/**
	 * Returns the last cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram pin, or <code>null</code> if a matching cs diagram pin could not be found
	 */
	public CSDiagramPin fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramPin>
			orderByComparator);

	/**
	 * Returns the cs diagram pins before and after the current cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CSDiagramPinId the primary key of the current cs diagram pin
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	public CSDiagramPin[] findByCPDefinitionId_PrevAndNext(
			long CSDiagramPinId, long CPDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<CSDiagramPin>
				orderByComparator)
		throws NoSuchCSDiagramPinException;

	/**
	 * Removes all the cs diagram pins where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	public void removeByCPDefinitionId(long CPDefinitionId);

	/**
	 * Returns the number of cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cs diagram pins
	 */
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	 * Caches the cs diagram pin in the entity cache if it is enabled.
	 *
	 * @param csDiagramPin the cs diagram pin
	 */
	public void cacheResult(CSDiagramPin csDiagramPin);

	/**
	 * Caches the cs diagram pins in the entity cache if it is enabled.
	 *
	 * @param csDiagramPins the cs diagram pins
	 */
	public void cacheResult(java.util.List<CSDiagramPin> csDiagramPins);

	/**
	 * Creates a new cs diagram pin with the primary key. Does not add the cs diagram pin to the database.
	 *
	 * @param CSDiagramPinId the primary key for the new cs diagram pin
	 * @return the new cs diagram pin
	 */
	public CSDiagramPin create(long CSDiagramPinId);

	/**
	 * Removes the cs diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin that was removed
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	public CSDiagramPin remove(long CSDiagramPinId)
		throws NoSuchCSDiagramPinException;

	public CSDiagramPin updateImpl(CSDiagramPin csDiagramPin);

	/**
	 * Returns the cs diagram pin with the primary key or throws a <code>NoSuchCSDiagramPinException</code> if it could not be found.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	public CSDiagramPin findByPrimaryKey(long CSDiagramPinId)
		throws NoSuchCSDiagramPinException;

	/**
	 * Returns the cs diagram pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin, or <code>null</code> if a cs diagram pin with the primary key could not be found
	 */
	public CSDiagramPin fetchByPrimaryKey(long CSDiagramPinId);

	/**
	 * Returns all the cs diagram pins.
	 *
	 * @return the cs diagram pins
	 */
	public java.util.List<CSDiagramPin> findAll();

	/**
	 * Returns a range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @return the range of cs diagram pins
	 */
	public java.util.List<CSDiagramPin> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cs diagram pins
	 */
	public java.util.List<CSDiagramPin> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramPin>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cs diagram pins
	 */
	public java.util.List<CSDiagramPin> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramPin>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cs diagram pins from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cs diagram pins.
	 *
	 * @return the number of cs diagram pins
	 */
	public int countAll();

}