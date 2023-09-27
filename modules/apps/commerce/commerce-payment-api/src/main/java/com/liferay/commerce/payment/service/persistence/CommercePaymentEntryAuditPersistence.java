/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service.persistence;

import com.liferay.commerce.payment.exception.NoSuchPaymentEntryAuditException;
import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce payment entry audit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryAuditUtil
 * @generated
 */
@ProviderType
public interface CommercePaymentEntryAuditPersistence
	extends BasePersistence<CommercePaymentEntryAudit> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePaymentEntryAuditUtil} to access the commerce payment entry audit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @return the matching commerce payment entry audits
	 */
	public java.util.List<CommercePaymentEntryAudit>
		findByCommercePaymentEntryId(long commercePaymentEntryId);

	/**
	 * Returns a range of all the commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @return the range of matching commerce payment entry audits
	 */
	public java.util.List<CommercePaymentEntryAudit>
		findByCommercePaymentEntryId(
			long commercePaymentEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment entry audits
	 */
	public java.util.List<CommercePaymentEntryAudit>
		findByCommercePaymentEntryId(
			long commercePaymentEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntryAudit> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment entry audits
	 */
	public java.util.List<CommercePaymentEntryAudit>
		findByCommercePaymentEntryId(
			long commercePaymentEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntryAudit> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a matching commerce payment entry audit could not be found
	 */
	public CommercePaymentEntryAudit findByCommercePaymentEntryId_First(
			long commercePaymentEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntryAudit> orderByComparator)
		throws NoSuchPaymentEntryAuditException;

	/**
	 * Returns the first commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment entry audit, or <code>null</code> if a matching commerce payment entry audit could not be found
	 */
	public CommercePaymentEntryAudit fetchByCommercePaymentEntryId_First(
		long commercePaymentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentEntryAudit> orderByComparator);

	/**
	 * Returns the last commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a matching commerce payment entry audit could not be found
	 */
	public CommercePaymentEntryAudit findByCommercePaymentEntryId_Last(
			long commercePaymentEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntryAudit> orderByComparator)
		throws NoSuchPaymentEntryAuditException;

	/**
	 * Returns the last commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment entry audit, or <code>null</code> if a matching commerce payment entry audit could not be found
	 */
	public CommercePaymentEntryAudit fetchByCommercePaymentEntryId_Last(
		long commercePaymentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentEntryAudit> orderByComparator);

	/**
	 * Returns the commerce payment entry audits before and after the current commerce payment entry audit in the ordered set where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the current commerce payment entry audit
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a commerce payment entry audit with the primary key could not be found
	 */
	public CommercePaymentEntryAudit[] findByCommercePaymentEntryId_PrevAndNext(
			long commercePaymentEntryAuditId, long commercePaymentEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntryAudit> orderByComparator)
		throws NoSuchPaymentEntryAuditException;

	/**
	 * Returns all the commerce payment entry audits that the user has permission to view where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @return the matching commerce payment entry audits that the user has permission to view
	 */
	public java.util.List<CommercePaymentEntryAudit>
		filterFindByCommercePaymentEntryId(long commercePaymentEntryId);

	/**
	 * Returns a range of all the commerce payment entry audits that the user has permission to view where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @return the range of matching commerce payment entry audits that the user has permission to view
	 */
	public java.util.List<CommercePaymentEntryAudit>
		filterFindByCommercePaymentEntryId(
			long commercePaymentEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment entry audits that the user has permissions to view where commercePaymentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment entry audits that the user has permission to view
	 */
	public java.util.List<CommercePaymentEntryAudit>
		filterFindByCommercePaymentEntryId(
			long commercePaymentEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePaymentEntryAudit> orderByComparator);

	/**
	 * Returns the commerce payment entry audits before and after the current commerce payment entry audit in the ordered set of commerce payment entry audits that the user has permission to view where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the current commerce payment entry audit
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a commerce payment entry audit with the primary key could not be found
	 */
	public CommercePaymentEntryAudit[]
			filterFindByCommercePaymentEntryId_PrevAndNext(
				long commercePaymentEntryAuditId, long commercePaymentEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePaymentEntryAudit> orderByComparator)
		throws NoSuchPaymentEntryAuditException;

	/**
	 * Removes all the commerce payment entry audits where commercePaymentEntryId = &#63; from the database.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 */
	public void removeByCommercePaymentEntryId(long commercePaymentEntryId);

	/**
	 * Returns the number of commerce payment entry audits where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @return the number of matching commerce payment entry audits
	 */
	public int countByCommercePaymentEntryId(long commercePaymentEntryId);

	/**
	 * Returns the number of commerce payment entry audits that the user has permission to view where commercePaymentEntryId = &#63;.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID
	 * @return the number of matching commerce payment entry audits that the user has permission to view
	 */
	public int filterCountByCommercePaymentEntryId(long commercePaymentEntryId);

	/**
	 * Caches the commerce payment entry audit in the entity cache if it is enabled.
	 *
	 * @param commercePaymentEntryAudit the commerce payment entry audit
	 */
	public void cacheResult(
		CommercePaymentEntryAudit commercePaymentEntryAudit);

	/**
	 * Caches the commerce payment entry audits in the entity cache if it is enabled.
	 *
	 * @param commercePaymentEntryAudits the commerce payment entry audits
	 */
	public void cacheResult(
		java.util.List<CommercePaymentEntryAudit> commercePaymentEntryAudits);

	/**
	 * Creates a new commerce payment entry audit with the primary key. Does not add the commerce payment entry audit to the database.
	 *
	 * @param commercePaymentEntryAuditId the primary key for the new commerce payment entry audit
	 * @return the new commerce payment entry audit
	 */
	public CommercePaymentEntryAudit create(long commercePaymentEntryAuditId);

	/**
	 * Removes the commerce payment entry audit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the commerce payment entry audit
	 * @return the commerce payment entry audit that was removed
	 * @throws NoSuchPaymentEntryAuditException if a commerce payment entry audit with the primary key could not be found
	 */
	public CommercePaymentEntryAudit remove(long commercePaymentEntryAuditId)
		throws NoSuchPaymentEntryAuditException;

	public CommercePaymentEntryAudit updateImpl(
		CommercePaymentEntryAudit commercePaymentEntryAudit);

	/**
	 * Returns the commerce payment entry audit with the primary key or throws a <code>NoSuchPaymentEntryAuditException</code> if it could not be found.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the commerce payment entry audit
	 * @return the commerce payment entry audit
	 * @throws NoSuchPaymentEntryAuditException if a commerce payment entry audit with the primary key could not be found
	 */
	public CommercePaymentEntryAudit findByPrimaryKey(
			long commercePaymentEntryAuditId)
		throws NoSuchPaymentEntryAuditException;

	/**
	 * Returns the commerce payment entry audit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePaymentEntryAuditId the primary key of the commerce payment entry audit
	 * @return the commerce payment entry audit, or <code>null</code> if a commerce payment entry audit with the primary key could not be found
	 */
	public CommercePaymentEntryAudit fetchByPrimaryKey(
		long commercePaymentEntryAuditId);

	/**
	 * Returns all the commerce payment entry audits.
	 *
	 * @return the commerce payment entry audits
	 */
	public java.util.List<CommercePaymentEntryAudit> findAll();

	/**
	 * Returns a range of all the commerce payment entry audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @return the range of commerce payment entry audits
	 */
	public java.util.List<CommercePaymentEntryAudit> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce payment entry audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce payment entry audits
	 */
	public java.util.List<CommercePaymentEntryAudit> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentEntryAudit> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce payment entry audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentEntryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment entry audits
	 * @param end the upper bound of the range of commerce payment entry audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce payment entry audits
	 */
	public java.util.List<CommercePaymentEntryAudit> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePaymentEntryAudit> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce payment entry audits from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce payment entry audits.
	 *
	 * @return the number of commerce payment entry audits
	 */
	public int countAll();

}