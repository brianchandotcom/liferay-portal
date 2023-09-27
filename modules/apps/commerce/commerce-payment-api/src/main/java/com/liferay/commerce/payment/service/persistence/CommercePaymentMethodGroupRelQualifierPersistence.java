/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service.persistence;

import com.liferay.commerce.payment.exception.NoSuchPaymentMethodGroupRelQualifierException;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce payment method group rel qualifier service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelQualifierUtil
 * @generated
 */
@ProviderType
public interface CommercePaymentMethodGroupRelQualifierPersistence
	extends BasePersistence<CommercePaymentMethodGroupRelQualifier> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePaymentMethodGroupRelQualifierUtil} to access the commerce payment method group rel qualifier persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long commercePaymentMethodGroupRelId);

	/**
	 * Returns a range of all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @return the range of matching commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long commercePaymentMethodGroupRelId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long commercePaymentMethodGroupRelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentMethodGroupRelQualifier> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long commercePaymentMethodGroupRelId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentMethodGroupRelQualifier> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier
			findByCommercePaymentMethodGroupRelId_First(
				long commercePaymentMethodGroupRelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePaymentMethodGroupRelQualifier> orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier
		fetchByCommercePaymentMethodGroupRelId_First(
			long commercePaymentMethodGroupRelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentMethodGroupRelQualifier> orderByComparator);

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier
			findByCommercePaymentMethodGroupRelId_Last(
				long commercePaymentMethodGroupRelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePaymentMethodGroupRelQualifier> orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier
		fetchByCommercePaymentMethodGroupRelId_Last(
			long commercePaymentMethodGroupRelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentMethodGroupRelQualifier> orderByComparator);

	/**
	 * Returns the commerce payment method group rel qualifiers before and after the current commerce payment method group rel qualifier in the ordered set where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the current commerce payment method group rel qualifier
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier[]
			findByCommercePaymentMethodGroupRelId_PrevAndNext(
				long commercePaymentMethodGroupRelQualifierId,
				long commercePaymentMethodGroupRelId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePaymentMethodGroupRelQualifier> orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	/**
	 * Removes all the commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63; from the database.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 */
	public void removeByCommercePaymentMethodGroupRelId(
		long commercePaymentMethodGroupRelId);

	/**
	 * Returns the number of commerce payment method group rel qualifiers where commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the number of matching commerce payment method group rel qualifiers
	 */
	public int countByCommercePaymentMethodGroupRelId(
		long commercePaymentMethodGroupRelId);

	/**
	 * Returns all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long commercePaymentMethodGroupRelId);

	/**
	 * Returns a range of all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @return the range of matching commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long commercePaymentMethodGroupRelId, int start,
		int end);

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long commercePaymentMethodGroupRelId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentMethodGroupRelQualifier> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long commercePaymentMethodGroupRelId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentMethodGroupRelQualifier> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier findByC_C_First(
			long classNameId, long commercePaymentMethodGroupRelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentMethodGroupRelQualifier> orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier fetchByC_C_First(
		long classNameId, long commercePaymentMethodGroupRelId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentMethodGroupRelQualifier> orderByComparator);

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier findByC_C_Last(
			long classNameId, long commercePaymentMethodGroupRelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentMethodGroupRelQualifier> orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier fetchByC_C_Last(
		long classNameId, long commercePaymentMethodGroupRelId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentMethodGroupRelQualifier> orderByComparator);

	/**
	 * Returns the commerce payment method group rel qualifiers before and after the current commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the current commerce payment method group rel qualifier
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier[] findByC_C_PrevAndNext(
			long commercePaymentMethodGroupRelQualifierId, long classNameId,
			long commercePaymentMethodGroupRelId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentMethodGroupRelQualifier> orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	/**
	 * Removes all the commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 */
	public void removeByC_C(
		long classNameId, long commercePaymentMethodGroupRelId);

	/**
	 * Returns the number of commerce payment method group rel qualifiers where classNameId = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the number of matching commerce payment method group rel qualifiers
	 */
	public int countByC_C(
		long classNameId, long commercePaymentMethodGroupRelId);

	/**
	 * Returns the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63; or throws a <code>NoSuchPaymentMethodGroupRelQualifierException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier findByC_C_C(
			long classNameId, long classPK,
			long commercePaymentMethodGroupRelId)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	/**
	 * Returns the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier fetchByC_C_C(
		long classNameId, long classPK, long commercePaymentMethodGroupRelId);

	/**
	 * Returns the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier fetchByC_C_C(
		long classNameId, long classPK, long commercePaymentMethodGroupRelId,
		boolean useFinderCache);

	/**
	 * Removes the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the commerce payment method group rel qualifier that was removed
	 */
	public CommercePaymentMethodGroupRelQualifier removeByC_C_C(
			long classNameId, long classPK,
			long commercePaymentMethodGroupRelId)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	/**
	 * Returns the number of commerce payment method group rel qualifiers where classNameId = &#63; and classPK = &#63; and commercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the number of matching commerce payment method group rel qualifiers
	 */
	public int countByC_C_C(
		long classNameId, long classPK, long commercePaymentMethodGroupRelId);

	/**
	 * Caches the commerce payment method group rel qualifier in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethodGroupRelQualifier the commerce payment method group rel qualifier
	 */
	public void cacheResult(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier);

	/**
	 * Caches the commerce payment method group rel qualifiers in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethodGroupRelQualifiers the commerce payment method group rel qualifiers
	 */
	public void cacheResult(
		java.util.List<CommercePaymentMethodGroupRelQualifier>
			commercePaymentMethodGroupRelQualifiers);

	/**
	 * Creates a new commerce payment method group rel qualifier with the primary key. Does not add the commerce payment method group rel qualifier to the database.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key for the new commerce payment method group rel qualifier
	 * @return the new commerce payment method group rel qualifier
	 */
	public CommercePaymentMethodGroupRelQualifier create(
		long commercePaymentMethodGroupRelQualifierId);

	/**
	 * Removes the commerce payment method group rel qualifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier that was removed
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier remove(
			long commercePaymentMethodGroupRelQualifierId)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	public CommercePaymentMethodGroupRelQualifier updateImpl(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier);

	/**
	 * Returns the commerce payment method group rel qualifier with the primary key or throws a <code>NoSuchPaymentMethodGroupRelQualifierException</code> if it could not be found.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier findByPrimaryKey(
			long commercePaymentMethodGroupRelQualifierId)
		throws NoSuchPaymentMethodGroupRelQualifierException;

	/**
	 * Returns the commerce payment method group rel qualifier with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier, or <code>null</code> if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	public CommercePaymentMethodGroupRelQualifier fetchByPrimaryKey(
		long commercePaymentMethodGroupRelQualifierId);

	/**
	 * Returns all the commerce payment method group rel qualifiers.
	 *
	 * @return the commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier> findAll();

	/**
	 * Returns a range of all the commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @return the range of commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentMethodGroupRelQualifier> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce payment method group rel qualifiers
	 */
	public java.util.List<CommercePaymentMethodGroupRelQualifier> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentMethodGroupRelQualifier> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce payment method group rel qualifiers from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce payment method group rel qualifiers.
	 *
	 * @return the number of commerce payment method group rel qualifiers
	 */
	public int countAll();

}