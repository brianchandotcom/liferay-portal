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

package com.liferay.portal.security.audit.storage.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.security.audit.storage.exception.NoSuchEventException;
import com.liferay.portal.security.audit.storage.model.AuditEvent;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the audit event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventUtil
 * @generated
 */
@ProviderType
public interface AuditEventPersistence extends BasePersistence<AuditEvent> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AuditEventUtil} to access the audit event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the audit events where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching audit events
	 */
	public java.util.List<AuditEvent> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the audit events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AuditEventModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @return the range of matching audit events
	 */
	public java.util.List<AuditEvent> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the audit events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AuditEventModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching audit events
	 */
	public java.util.List<AuditEvent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AuditEvent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the audit events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AuditEventModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching audit events
	 */
	public java.util.List<AuditEvent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AuditEvent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first audit event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit event
	 * @throws NoSuchEventException if a matching audit event could not be found
	 */
	public AuditEvent findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AuditEvent>
				orderByComparator)
		throws NoSuchEventException;

	/**
	 * Returns the first audit event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching audit event, or <code>null</code> if a matching audit event could not be found
	 */
	public AuditEvent fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AuditEvent>
			orderByComparator);

	/**
	 * Returns the last audit event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit event
	 * @throws NoSuchEventException if a matching audit event could not be found
	 */
	public AuditEvent findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AuditEvent>
				orderByComparator)
		throws NoSuchEventException;

	/**
	 * Returns the last audit event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching audit event, or <code>null</code> if a matching audit event could not be found
	 */
	public AuditEvent fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AuditEvent>
			orderByComparator);

	/**
	 * Returns the audit events before and after the current audit event in the ordered set where companyId = &#63;.
	 *
	 * @param auditEventId the primary key of the current audit event
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next audit event
	 * @throws NoSuchEventException if a audit event with the primary key could not be found
	 */
	public AuditEvent[] findByCompanyId_PrevAndNext(
			long auditEventId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AuditEvent>
				orderByComparator)
		throws NoSuchEventException;

	/**
	 * Removes all the audit events where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of audit events where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching audit events
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the audit event in the entity cache if it is enabled.
	 *
	 * @param auditEvent the audit event
	 */
	public void cacheResult(AuditEvent auditEvent);

	/**
	 * Caches the audit events in the entity cache if it is enabled.
	 *
	 * @param auditEvents the audit events
	 */
	public void cacheResult(java.util.List<AuditEvent> auditEvents);

	/**
	 * Creates a new audit event with the primary key. Does not add the audit event to the database.
	 *
	 * @param auditEventId the primary key for the new audit event
	 * @return the new audit event
	 */
	public AuditEvent create(long auditEventId);

	/**
	 * Removes the audit event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEventId the primary key of the audit event
	 * @return the audit event that was removed
	 * @throws NoSuchEventException if a audit event with the primary key could not be found
	 */
	public AuditEvent remove(long auditEventId) throws NoSuchEventException;

	public AuditEvent updateImpl(AuditEvent auditEvent);

	/**
	 * Returns the audit event with the primary key or throws a <code>NoSuchEventException</code> if it could not be found.
	 *
	 * @param auditEventId the primary key of the audit event
	 * @return the audit event
	 * @throws NoSuchEventException if a audit event with the primary key could not be found
	 */
	public AuditEvent findByPrimaryKey(long auditEventId)
		throws NoSuchEventException;

	/**
	 * Returns the audit event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param auditEventId the primary key of the audit event
	 * @return the audit event, or <code>null</code> if a audit event with the primary key could not be found
	 */
	public AuditEvent fetchByPrimaryKey(long auditEventId);

	/**
	 * Returns all the audit events.
	 *
	 * @return the audit events
	 */
	public java.util.List<AuditEvent> findAll();

	/**
	 * Returns a range of all the audit events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AuditEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @return the range of audit events
	 */
	public java.util.List<AuditEvent> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the audit events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AuditEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of audit events
	 */
	public java.util.List<AuditEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AuditEvent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the audit events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AuditEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of audit events
	 */
	public java.util.List<AuditEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AuditEvent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the audit events from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of audit events.
	 *
	 * @return the number of audit events
	 */
	public int countAll();

}