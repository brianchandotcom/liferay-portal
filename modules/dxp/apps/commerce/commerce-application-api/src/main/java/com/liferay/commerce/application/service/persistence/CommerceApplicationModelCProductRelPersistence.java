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

package com.liferay.commerce.application.service.persistence;

import com.liferay.commerce.application.exception.NoSuchApplicationModelCProductRelException;
import com.liferay.commerce.application.model.CommerceApplicationModelCProductRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce application model c product rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelCProductRelUtil
 * @generated
 */
@ProviderType
public interface CommerceApplicationModelCProductRelPersistence
	extends BasePersistence<CommerceApplicationModelCProductRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceApplicationModelCProductRelUtil} to access the commerce application model c product rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the matching commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(long commerceApplicationModelId);

	/**
	 * Returns a range of all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of matching commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationModelCProductRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel>
		findByCommerceApplicationModelId(
			long commerceApplicationModelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationModelCProductRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	public CommerceApplicationModelCProductRel
			findByCommerceApplicationModelId_First(
				long commerceApplicationModelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceApplicationModelCProductRel> orderByComparator)
		throws NoSuchApplicationModelCProductRelException;

	/**
	 * Returns the first commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	public CommerceApplicationModelCProductRel
		fetchByCommerceApplicationModelId_First(
			long commerceApplicationModelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationModelCProductRel> orderByComparator);

	/**
	 * Returns the last commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	public CommerceApplicationModelCProductRel
			findByCommerceApplicationModelId_Last(
				long commerceApplicationModelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceApplicationModelCProductRel> orderByComparator)
		throws NoSuchApplicationModelCProductRelException;

	/**
	 * Returns the last commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	public CommerceApplicationModelCProductRel
		fetchByCommerceApplicationModelId_Last(
			long commerceApplicationModelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationModelCProductRel> orderByComparator);

	/**
	 * Returns the commerce application model c product rels before and after the current commerce application model c product rel in the ordered set where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the current commerce application model c product rel
	 * @param commerceApplicationModelId the commerce application model ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	public CommerceApplicationModelCProductRel[]
			findByCommerceApplicationModelId_PrevAndNext(
				long commerceApplicationModelCProductRelId,
				long commerceApplicationModelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceApplicationModelCProductRel> orderByComparator)
		throws NoSuchApplicationModelCProductRelException;

	/**
	 * Removes all the commerce application model c product rels where commerceApplicationModelId = &#63; from the database.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 */
	public void removeByCommerceApplicationModelId(
		long commerceApplicationModelId);

	/**
	 * Returns the number of commerce application model c product rels where commerceApplicationModelId = &#63;.
	 *
	 * @param commerceApplicationModelId the commerce application model ID
	 * @return the number of matching commerce application model c product rels
	 */
	public int countByCommerceApplicationModelId(
		long commerceApplicationModelId);

	/**
	 * Returns all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the matching commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId);

	/**
	 * Returns a range of all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of matching commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationModelCProductRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce application model c product rels where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel> findByCProductId(
		long CProductId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationModelCProductRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	public CommerceApplicationModelCProductRel findByCProductId_First(
			long CProductId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationModelCProductRel> orderByComparator)
		throws NoSuchApplicationModelCProductRelException;

	/**
	 * Returns the first commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	public CommerceApplicationModelCProductRel fetchByCProductId_First(
		long CProductId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationModelCProductRel> orderByComparator);

	/**
	 * Returns the last commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a matching commerce application model c product rel could not be found
	 */
	public CommerceApplicationModelCProductRel findByCProductId_Last(
			long CProductId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationModelCProductRel> orderByComparator)
		throws NoSuchApplicationModelCProductRelException;

	/**
	 * Returns the last commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce application model c product rel, or <code>null</code> if a matching commerce application model c product rel could not be found
	 */
	public CommerceApplicationModelCProductRel fetchByCProductId_Last(
		long CProductId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationModelCProductRel> orderByComparator);

	/**
	 * Returns the commerce application model c product rels before and after the current commerce application model c product rel in the ordered set where CProductId = &#63;.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the current commerce application model c product rel
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	public CommerceApplicationModelCProductRel[] findByCProductId_PrevAndNext(
			long commerceApplicationModelCProductRelId, long CProductId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceApplicationModelCProductRel> orderByComparator)
		throws NoSuchApplicationModelCProductRelException;

	/**
	 * Removes all the commerce application model c product rels where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 */
	public void removeByCProductId(long CProductId);

	/**
	 * Returns the number of commerce application model c product rels where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching commerce application model c product rels
	 */
	public int countByCProductId(long CProductId);

	/**
	 * Caches the commerce application model c product rel in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationModelCProductRel the commerce application model c product rel
	 */
	public void cacheResult(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel);

	/**
	 * Caches the commerce application model c product rels in the entity cache if it is enabled.
	 *
	 * @param commerceApplicationModelCProductRels the commerce application model c product rels
	 */
	public void cacheResult(
		java.util.List<CommerceApplicationModelCProductRel>
			commerceApplicationModelCProductRels);

	/**
	 * Creates a new commerce application model c product rel with the primary key. Does not add the commerce application model c product rel to the database.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key for the new commerce application model c product rel
	 * @return the new commerce application model c product rel
	 */
	public CommerceApplicationModelCProductRel create(
		long commerceApplicationModelCProductRelId);

	/**
	 * Removes the commerce application model c product rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel that was removed
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	public CommerceApplicationModelCProductRel remove(
			long commerceApplicationModelCProductRelId)
		throws NoSuchApplicationModelCProductRelException;

	public CommerceApplicationModelCProductRel updateImpl(
		CommerceApplicationModelCProductRel
			commerceApplicationModelCProductRel);

	/**
	 * Returns the commerce application model c product rel with the primary key or throws a <code>NoSuchApplicationModelCProductRelException</code> if it could not be found.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel
	 * @throws NoSuchApplicationModelCProductRelException if a commerce application model c product rel with the primary key could not be found
	 */
	public CommerceApplicationModelCProductRel findByPrimaryKey(
			long commerceApplicationModelCProductRelId)
		throws NoSuchApplicationModelCProductRelException;

	/**
	 * Returns the commerce application model c product rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel, or <code>null</code> if a commerce application model c product rel with the primary key could not be found
	 */
	public CommerceApplicationModelCProductRel fetchByPrimaryKey(
		long commerceApplicationModelCProductRelId);

	/**
	 * Returns all the commerce application model c product rels.
	 *
	 * @return the commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel> findAll();

	/**
	 * Returns a range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationModelCProductRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce application model c product rels
	 */
	public java.util.List<CommerceApplicationModelCProductRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceApplicationModelCProductRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce application model c product rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce application model c product rels.
	 *
	 * @return the number of commerce application model c product rels
	 */
	public int countAll();

}