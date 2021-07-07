/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramPinException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp definition diagram pin service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramPinUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionDiagramPinPersistence
	extends BasePersistence<CPDefinitionDiagramPin> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionDiagramPinUtil} to access the cp definition diagram pin persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram pins
	 */
	public java.util.List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId);

	/**
	 * Returns a range of all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @return the range of matching cp definition diagram pins
	 */
	public java.util.List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram pins
	 */
	public java.util.List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionDiagramPin>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram pins
	 */
	public java.util.List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionDiagramPin>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a matching cp definition diagram pin could not be found
	 */
	public CPDefinitionDiagramPin findByCPDefinitionId_First(
			long CPDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramPin> orderByComparator)
		throws NoSuchCPDefinitionDiagramPinException;

	/**
	 * Returns the first cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram pin, or <code>null</code> if a matching cp definition diagram pin could not be found
	 */
	public CPDefinitionDiagramPin fetchByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionDiagramPin>
			orderByComparator);

	/**
	 * Returns the last cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a matching cp definition diagram pin could not be found
	 */
	public CPDefinitionDiagramPin findByCPDefinitionId_Last(
			long CPDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramPin> orderByComparator)
		throws NoSuchCPDefinitionDiagramPinException;

	/**
	 * Returns the last cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram pin, or <code>null</code> if a matching cp definition diagram pin could not be found
	 */
	public CPDefinitionDiagramPin fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionDiagramPin>
			orderByComparator);

	/**
	 * Returns the cp definition diagram pins before and after the current cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the current cp definition diagram pin
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	public CPDefinitionDiagramPin[] findByCPDefinitionId_PrevAndNext(
			long CPDefinitionDiagramPinId, long CPDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramPin> orderByComparator)
		throws NoSuchCPDefinitionDiagramPinException;

	/**
	 * Removes all the cp definition diagram pins where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	public void removeByCPDefinitionId(long CPDefinitionId);

	/**
	 * Returns the number of cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram pins
	 */
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	 * Caches the cp definition diagram pin in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramPin the cp definition diagram pin
	 */
	public void cacheResult(CPDefinitionDiagramPin cpDefinitionDiagramPin);

	/**
	 * Caches the cp definition diagram pins in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramPins the cp definition diagram pins
	 */
	public void cacheResult(
		java.util.List<CPDefinitionDiagramPin> cpDefinitionDiagramPins);

	/**
	 * Creates a new cp definition diagram pin with the primary key. Does not add the cp definition diagram pin to the database.
	 *
	 * @param CPDefinitionDiagramPinId the primary key for the new cp definition diagram pin
	 * @return the new cp definition diagram pin
	 */
	public CPDefinitionDiagramPin create(long CPDefinitionDiagramPinId);

	/**
	 * Removes the cp definition diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin that was removed
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	public CPDefinitionDiagramPin remove(long CPDefinitionDiagramPinId)
		throws NoSuchCPDefinitionDiagramPinException;

	public CPDefinitionDiagramPin updateImpl(
		CPDefinitionDiagramPin cpDefinitionDiagramPin);

	/**
	 * Returns the cp definition diagram pin with the primary key or throws a <code>NoSuchCPDefinitionDiagramPinException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	public CPDefinitionDiagramPin findByPrimaryKey(
			long CPDefinitionDiagramPinId)
		throws NoSuchCPDefinitionDiagramPinException;

	/**
	 * Returns the cp definition diagram pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin, or <code>null</code> if a cp definition diagram pin with the primary key could not be found
	 */
	public CPDefinitionDiagramPin fetchByPrimaryKey(
		long CPDefinitionDiagramPinId);

	/**
	 * Returns all the cp definition diagram pins.
	 *
	 * @return the cp definition diagram pins
	 */
	public java.util.List<CPDefinitionDiagramPin> findAll();

	/**
	 * Returns a range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @return the range of cp definition diagram pins
	 */
	public java.util.List<CPDefinitionDiagramPin> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram pins
	 */
	public java.util.List<CPDefinitionDiagramPin> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionDiagramPin>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram pins
	 */
	public java.util.List<CPDefinitionDiagramPin> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionDiagramPin>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp definition diagram pins from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp definition diagram pins.
	 *
	 * @return the number of cp definition diagram pins
	 */
	public int countAll();

}