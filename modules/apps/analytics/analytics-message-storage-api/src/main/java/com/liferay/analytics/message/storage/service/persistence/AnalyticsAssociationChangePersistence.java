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

package com.liferay.analytics.message.storage.service.persistence;

import com.liferay.analytics.message.storage.exception.NoSuchAssociationChangeException;
import com.liferay.analytics.message.storage.model.AnalyticsAssociationChange;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the analytics association change service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsAssociationChangeUtil
 * @generated
 */
@ProviderType
public interface AnalyticsAssociationChangePersistence
	extends BasePersistence<AnalyticsAssociationChange> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnalyticsAssociationChangeUtil} to access the analytics association change persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @return the matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_A(
		long companyId, String associationClassName);

	/**
	 * Returns a range of all the analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @return the range of matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_A(
		long companyId, String associationClassName, int start, int end);

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_A(
		long companyId, String associationClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_A(
		long companyId, String associationClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange findByC_A_First(
			long companyId, String associationClassName,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsAssociationChange> orderByComparator)
		throws NoSuchAssociationChangeException;

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange fetchByC_A_First(
		long companyId, String associationClassName,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange findByC_A_Last(
			long companyId, String associationClassName,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsAssociationChange> orderByComparator)
		throws NoSuchAssociationChangeException;

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange fetchByC_A_Last(
		long companyId, String associationClassName,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns the analytics association changes before and after the current analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param analyticsAssociationChangeId the primary key of the current analytics association change
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics association change
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public AnalyticsAssociationChange[] findByC_A_PrevAndNext(
			long analyticsAssociationChangeId, long companyId,
			String associationClassName,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsAssociationChange> orderByComparator)
		throws NoSuchAssociationChangeException;

	/**
	 * Removes all the analytics association changes where companyId = &#63; and associationClassName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 */
	public void removeByC_A(long companyId, String associationClassName);

	/**
	 * Returns the number of analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @return the number of matching analytics association changes
	 */
	public int countByC_A(long companyId, String associationClassName);

	/**
	 * Returns all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @return the matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName);

	/**
	 * Returns a range of all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @return the range of matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end);

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange findByC_GtM_A_First(
			long companyId, Date modifiedDate, String associationClassName,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsAssociationChange> orderByComparator)
		throws NoSuchAssociationChangeException;

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange fetchByC_GtM_A_First(
		long companyId, Date modifiedDate, String associationClassName,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange findByC_GtM_A_Last(
			long companyId, Date modifiedDate, String associationClassName,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsAssociationChange> orderByComparator)
		throws NoSuchAssociationChangeException;

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange fetchByC_GtM_A_Last(
		long companyId, Date modifiedDate, String associationClassName,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns the analytics association changes before and after the current analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param analyticsAssociationChangeId the primary key of the current analytics association change
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics association change
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public AnalyticsAssociationChange[] findByC_GtM_A_PrevAndNext(
			long analyticsAssociationChangeId, long companyId,
			Date modifiedDate, String associationClassName,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsAssociationChange> orderByComparator)
		throws NoSuchAssociationChangeException;

	/**
	 * Removes all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 */
	public void removeByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName);

	/**
	 * Returns the number of analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @return the number of matching analytics association changes
	 */
	public int countByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName);

	/**
	 * Returns all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @return the matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK);

	/**
	 * Returns a range of all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @return the range of matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK,
		int start, int end);

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange findByC_A_A_First(
			long companyId, String associationClassName,
			long associationClassPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsAssociationChange> orderByComparator)
		throws NoSuchAssociationChangeException;

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange fetchByC_A_A_First(
		long companyId, String associationClassName, long associationClassPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange findByC_A_A_Last(
			long companyId, String associationClassName,
			long associationClassPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsAssociationChange> orderByComparator)
		throws NoSuchAssociationChangeException;

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public AnalyticsAssociationChange fetchByC_A_A_Last(
		long companyId, String associationClassName, long associationClassPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns the analytics association changes before and after the current analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param analyticsAssociationChangeId the primary key of the current analytics association change
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics association change
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public AnalyticsAssociationChange[] findByC_A_A_PrevAndNext(
			long analyticsAssociationChangeId, long companyId,
			String associationClassName, long associationClassPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<AnalyticsAssociationChange> orderByComparator)
		throws NoSuchAssociationChangeException;

	/**
	 * Removes all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 */
	public void removeByC_A_A(
		long companyId, String associationClassName, long associationClassPK);

	/**
	 * Returns the number of analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @return the number of matching analytics association changes
	 */
	public int countByC_A_A(
		long companyId, String associationClassName, long associationClassPK);

	/**
	 * Caches the analytics association change in the entity cache if it is enabled.
	 *
	 * @param analyticsAssociationChange the analytics association change
	 */
	public void cacheResult(
		AnalyticsAssociationChange analyticsAssociationChange);

	/**
	 * Caches the analytics association changes in the entity cache if it is enabled.
	 *
	 * @param analyticsAssociationChanges the analytics association changes
	 */
	public void cacheResult(
		java.util.List<AnalyticsAssociationChange> analyticsAssociationChanges);

	/**
	 * Creates a new analytics association change with the primary key. Does not add the analytics association change to the database.
	 *
	 * @param analyticsAssociationChangeId the primary key for the new analytics association change
	 * @return the new analytics association change
	 */
	public AnalyticsAssociationChange create(long analyticsAssociationChangeId);

	/**
	 * Removes the analytics association change with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsAssociationChangeId the primary key of the analytics association change
	 * @return the analytics association change that was removed
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public AnalyticsAssociationChange remove(long analyticsAssociationChangeId)
		throws NoSuchAssociationChangeException;

	public AnalyticsAssociationChange updateImpl(
		AnalyticsAssociationChange analyticsAssociationChange);

	/**
	 * Returns the analytics association change with the primary key or throws a <code>NoSuchAssociationChangeException</code> if it could not be found.
	 *
	 * @param analyticsAssociationChangeId the primary key of the analytics association change
	 * @return the analytics association change
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public AnalyticsAssociationChange findByPrimaryKey(
			long analyticsAssociationChangeId)
		throws NoSuchAssociationChangeException;

	/**
	 * Returns the analytics association change with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsAssociationChangeId the primary key of the analytics association change
	 * @return the analytics association change, or <code>null</code> if a analytics association change with the primary key could not be found
	 */
	public AnalyticsAssociationChange fetchByPrimaryKey(
		long analyticsAssociationChangeId);

	/**
	 * Returns all the analytics association changes.
	 *
	 * @return the analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findAll();

	/**
	 * Returns a range of all the analytics association changes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @return the range of analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the analytics association changes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator);

	/**
	 * Returns an ordered range of all the analytics association changes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics association changes
	 */
	public java.util.List<AnalyticsAssociationChange> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AnalyticsAssociationChange> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the analytics association changes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of analytics association changes.
	 *
	 * @return the number of analytics association changes
	 */
	public int countAll();

}