/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.service.persistence;

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionQualifierException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce shipping fixed option qualifier service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionQualifierUtil
 * @generated
 */
@ProviderType
public interface CommerceShippingFixedOptionQualifierPersistence
	extends BasePersistence<CommerceShippingFixedOptionQualifier> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShippingFixedOptionQualifierUtil} to access the commerce shipping fixed option qualifier persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(long commerceShippingFixedOptionId);

	/**
	 * Returns a range of all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @return the range of matching commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionQualifier> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionQualifier> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier
			findByCommerceShippingFixedOptionId_First(
				long commerceShippingFixedOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingFixedOptionQualifier> orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException;

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier
		fetchByCommerceShippingFixedOptionId_First(
			long commerceShippingFixedOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionQualifier> orderByComparator);

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier
			findByCommerceShippingFixedOptionId_Last(
				long commerceShippingFixedOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingFixedOptionQualifier> orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException;

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier
		fetchByCommerceShippingFixedOptionId_Last(
			long commerceShippingFixedOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionQualifier> orderByComparator);

	/**
	 * Returns the commerce shipping fixed option qualifiers before and after the current commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the current commerce shipping fixed option qualifier
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public CommerceShippingFixedOptionQualifier[]
			findByCommerceShippingFixedOptionId_PrevAndNext(
				long commerceShippingFixedOptionQualifierId,
				long commerceShippingFixedOptionId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceShippingFixedOptionQualifier> orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException;

	/**
	 * Removes all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	public void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	 * Returns the number of commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option qualifiers
	 */
	public int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	 * Returns all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId);

	/**
	 * Returns a range of all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @return the range of matching commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId, int start,
		int end);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionQualifier> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionQualifier> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier findByC_C_First(
			long classNameId, long commerceShippingFixedOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionQualifier> orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException;

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier fetchByC_C_First(
		long classNameId, long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionQualifier> orderByComparator);

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier findByC_C_Last(
			long classNameId, long commerceShippingFixedOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionQualifier> orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException;

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier fetchByC_C_Last(
		long classNameId, long commerceShippingFixedOptionId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionQualifier> orderByComparator);

	/**
	 * Returns the commerce shipping fixed option qualifiers before and after the current commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the current commerce shipping fixed option qualifier
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public CommerceShippingFixedOptionQualifier[] findByC_C_PrevAndNext(
			long commerceShippingFixedOptionQualifierId, long classNameId,
			long commerceShippingFixedOptionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceShippingFixedOptionQualifier> orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException;

	/**
	 * Removes all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	public void removeByC_C(
		long classNameId, long commerceShippingFixedOptionId);

	/**
	 * Returns the number of commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option qualifiers
	 */
	public int countByC_C(long classNameId, long commerceShippingFixedOptionId);

	/**
	 * Returns the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; or throws a <code>NoSuchShippingFixedOptionQualifierException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier findByC_C_C(
			long classNameId, long classPK, long commerceShippingFixedOptionId)
		throws NoSuchShippingFixedOptionQualifierException;

	/**
	 * Returns the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier fetchByC_C_C(
		long classNameId, long classPK, long commerceShippingFixedOptionId);

	/**
	 * Returns the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	public CommerceShippingFixedOptionQualifier fetchByC_C_C(
		long classNameId, long classPK, long commerceShippingFixedOptionId,
		boolean useFinderCache);

	/**
	 * Removes the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the commerce shipping fixed option qualifier that was removed
	 */
	public CommerceShippingFixedOptionQualifier removeByC_C_C(
			long classNameId, long classPK, long commerceShippingFixedOptionId)
		throws NoSuchShippingFixedOptionQualifierException;

	/**
	 * Returns the number of commerce shipping fixed option qualifiers where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option qualifiers
	 */
	public int countByC_C_C(
		long classNameId, long classPK, long commerceShippingFixedOptionId);

	/**
	 * Caches the commerce shipping fixed option qualifier in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionQualifier the commerce shipping fixed option qualifier
	 */
	public void cacheResult(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier);

	/**
	 * Caches the commerce shipping fixed option qualifiers in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionQualifiers the commerce shipping fixed option qualifiers
	 */
	public void cacheResult(
		java.util.List<CommerceShippingFixedOptionQualifier>
			commerceShippingFixedOptionQualifiers);

	/**
	 * Creates a new commerce shipping fixed option qualifier with the primary key. Does not add the commerce shipping fixed option qualifier to the database.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key for the new commerce shipping fixed option qualifier
	 * @return the new commerce shipping fixed option qualifier
	 */
	public CommerceShippingFixedOptionQualifier create(
		long commerceShippingFixedOptionQualifierId);

	/**
	 * Removes the commerce shipping fixed option qualifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier that was removed
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public CommerceShippingFixedOptionQualifier remove(
			long commerceShippingFixedOptionQualifierId)
		throws NoSuchShippingFixedOptionQualifierException;

	public CommerceShippingFixedOptionQualifier updateImpl(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier);

	/**
	 * Returns the commerce shipping fixed option qualifier with the primary key or throws a <code>NoSuchShippingFixedOptionQualifierException</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public CommerceShippingFixedOptionQualifier findByPrimaryKey(
			long commerceShippingFixedOptionQualifierId)
		throws NoSuchShippingFixedOptionQualifierException;

	/**
	 * Returns the commerce shipping fixed option qualifier with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier, or <code>null</code> if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	public CommerceShippingFixedOptionQualifier fetchByPrimaryKey(
		long commerceShippingFixedOptionQualifierId);

	/**
	 * Returns all the commerce shipping fixed option qualifiers.
	 *
	 * @return the commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier> findAll();

	/**
	 * Returns a range of all the commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @return the range of commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionQualifier> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping fixed option qualifiers
	 */
	public java.util.List<CommerceShippingFixedOptionQualifier> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceShippingFixedOptionQualifier> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce shipping fixed option qualifiers from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce shipping fixed option qualifiers.
	 *
	 * @return the number of commerce shipping fixed option qualifiers
	 */
	public int countAll();

}