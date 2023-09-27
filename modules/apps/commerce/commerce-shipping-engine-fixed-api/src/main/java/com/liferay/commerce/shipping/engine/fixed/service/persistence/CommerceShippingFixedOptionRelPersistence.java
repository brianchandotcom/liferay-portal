/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.service.persistence;

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce shipping fixed option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelUtil
 * @generated
 */
@ProviderType
public interface CommerceShippingFixedOptionRelPersistence
	extends BasePersistence<CommerceShippingFixedOptionRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShippingFixedOptionRelUtil} to access the commerce shipping fixed option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the matching commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel>
		findByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	 * Returns a range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @return the range of matching commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel>
		findByCommerceShippingMethodId(
			long commerceShippingMethodId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel>
		findByCommerceShippingMethodId(
			long commerceShippingMethodId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel>
		findByCommerceShippingMethodId(
			long commerceShippingMethodId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	public CommerceShippingFixedOptionRel findByCommerceShippingMethodId_First(
			long commerceShippingMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	public CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_First(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	public CommerceShippingFixedOptionRel findByCommerceShippingMethodId_Last(
			long commerceShippingMethodId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	public CommerceShippingFixedOptionRel fetchByCommerceShippingMethodId_Last(
		long commerceShippingMethodId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	 * Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	public CommerceShippingFixedOptionRel[]
			findByCommerceShippingMethodId_PrevAndNext(
				long commerceShippingFixedOptionRelId,
				long commerceShippingMethodId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	 * Removes all the commerce shipping fixed option rels where commerceShippingMethodId = &#63; from the database.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 */
	public void removeByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	 * Returns the number of commerce shipping fixed option rels where commerceShippingMethodId = &#63;.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID
	 * @return the number of matching commerce shipping fixed option rels
	 */
	public int countByCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	 * Returns all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel>
		findByCommerceShippingFixedOptionId(long commerceShippingFixedOptionId);

	/**
	 * Returns a range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @return the range of matching commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	public CommerceShippingFixedOptionRel
			findByCommerceShippingFixedOptionId_First(
				long commerceShippingFixedOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	 * Returns the first commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	public CommerceShippingFixedOptionRel
		fetchByCommerceShippingFixedOptionId_First(
			long commerceShippingFixedOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a matching commerce shipping fixed option rel could not be found
	 */
	public CommerceShippingFixedOptionRel
			findByCommerceShippingFixedOptionId_Last(
				long commerceShippingFixedOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	 * Returns the last commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option rel, or <code>null</code> if a matching commerce shipping fixed option rel could not be found
	 */
	public CommerceShippingFixedOptionRel
		fetchByCommerceShippingFixedOptionId_Last(
			long commerceShippingFixedOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	 * Returns the commerce shipping fixed option rels before and after the current commerce shipping fixed option rel in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the current commerce shipping fixed option rel
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	public CommerceShippingFixedOptionRel[]
			findByCommerceShippingFixedOptionId_PrevAndNext(
				long commerceShippingFixedOptionRelId,
				long commerceShippingFixedOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingFixedOptionRel> orderByComparator)
		throws NoSuchShippingFixedOptionRelException;

	/**
	 * Removes all the commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	public void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	 * Returns the number of commerce shipping fixed option rels where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option rels
	 */
	public int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	 * Caches the commerce shipping fixed option rel in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionRel the commerce shipping fixed option rel
	 */
	public void cacheResult(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel);

	/**
	 * Caches the commerce shipping fixed option rels in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionRels the commerce shipping fixed option rels
	 */
	public void cacheResult(
		java.util.List<CommerceShippingFixedOptionRel>
			commerceShippingFixedOptionRels);

	/**
	 * Creates a new commerce shipping fixed option rel with the primary key. Does not add the commerce shipping fixed option rel to the database.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key for the new commerce shipping fixed option rel
	 * @return the new commerce shipping fixed option rel
	 */
	public CommerceShippingFixedOptionRel create(
		long commerceShippingFixedOptionRelId);

	/**
	 * Removes the commerce shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel that was removed
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	public CommerceShippingFixedOptionRel remove(
			long commerceShippingFixedOptionRelId)
		throws NoSuchShippingFixedOptionRelException;

	public CommerceShippingFixedOptionRel updateImpl(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel);

	/**
	 * Returns the commerce shipping fixed option rel with the primary key or throws a <code>NoSuchShippingFixedOptionRelException</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel
	 * @throws NoSuchShippingFixedOptionRelException if a commerce shipping fixed option rel with the primary key could not be found
	 */
	public CommerceShippingFixedOptionRel findByPrimaryKey(
			long commerceShippingFixedOptionRelId)
		throws NoSuchShippingFixedOptionRelException;

	/**
	 * Returns the commerce shipping fixed option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionRelId the primary key of the commerce shipping fixed option rel
	 * @return the commerce shipping fixed option rel, or <code>null</code> if a commerce shipping fixed option rel with the primary key could not be found
	 */
	public CommerceShippingFixedOptionRel fetchByPrimaryKey(
		long commerceShippingFixedOptionRelId);

	/**
	 * Returns all the commerce shipping fixed option rels.
	 *
	 * @return the commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel> findAll();

	/**
	 * Returns a range of all the commerce shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @return the range of commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option rels
	 * @param end the upper bound of the range of commerce shipping fixed option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping fixed option rels
	 */
	public java.util.List<CommerceShippingFixedOptionRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce shipping fixed option rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce shipping fixed option rels.
	 *
	 * @return the number of commerce shipping fixed option rels
	 */
	public int countAll();

}