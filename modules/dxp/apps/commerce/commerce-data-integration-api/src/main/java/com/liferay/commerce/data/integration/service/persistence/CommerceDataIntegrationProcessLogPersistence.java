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

package com.liferay.commerce.data.integration.service.persistence;

import com.liferay.commerce.data.integration.exception.NoSuchDataIntegrationProcessLogException;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce data integration process log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLogUtil
 * @generated
 */
@ProviderType
public interface CommerceDataIntegrationProcessLogPersistence
	extends BasePersistence<CommerceDataIntegrationProcessLog> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDataIntegrationProcessLogUtil} to access the commerce data integration process log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @return the matching commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(long CDataIntegrationProcessId);

	/**
	 * Returns a range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @return the range of matching commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(
			long CDataIntegrationProcessId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(
			long CDataIntegrationProcessId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcessLog> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(
			long CDataIntegrationProcessId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcessLog> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	public CommerceDataIntegrationProcessLog
			findByCDataIntegrationProcessId_First(
				long CDataIntegrationProcessId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceDataIntegrationProcessLog> orderByComparator)
		throws NoSuchDataIntegrationProcessLogException;

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	public CommerceDataIntegrationProcessLog
		fetchByCDataIntegrationProcessId_First(
			long CDataIntegrationProcessId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcessLog> orderByComparator);

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	public CommerceDataIntegrationProcessLog
			findByCDataIntegrationProcessId_Last(
				long CDataIntegrationProcessId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceDataIntegrationProcessLog> orderByComparator)
		throws NoSuchDataIntegrationProcessLogException;

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	public CommerceDataIntegrationProcessLog
		fetchByCDataIntegrationProcessId_Last(
			long CDataIntegrationProcessId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcessLog> orderByComparator);

	/**
	 * Returns the commerce data integration process logs before and after the current commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the current commerce data integration process log
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	public CommerceDataIntegrationProcessLog[]
			findByCDataIntegrationProcessId_PrevAndNext(
				long commerceDataIntegrationProcessLogId,
				long CDataIntegrationProcessId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceDataIntegrationProcessLog> orderByComparator)
		throws NoSuchDataIntegrationProcessLogException;

	/**
	 * Removes all the commerce data integration process logs where CDataIntegrationProcessId = &#63; from the database.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 */
	public void removeByCDataIntegrationProcessId(
		long CDataIntegrationProcessId);

	/**
	 * Returns the number of commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @return the number of matching commerce data integration process logs
	 */
	public int countByCDataIntegrationProcessId(long CDataIntegrationProcessId);

	/**
	 * Returns all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @return the matching commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status);

	/**
	 * Returns a range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @return the range of matching commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcessLog> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcessLog> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	public CommerceDataIntegrationProcessLog findByC_S_First(
			long CDataIntegrationProcessId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcessLog> orderByComparator)
		throws NoSuchDataIntegrationProcessLogException;

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	public CommerceDataIntegrationProcessLog fetchByC_S_First(
		long CDataIntegrationProcessId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcessLog> orderByComparator);

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	public CommerceDataIntegrationProcessLog findByC_S_Last(
			long CDataIntegrationProcessId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcessLog> orderByComparator)
		throws NoSuchDataIntegrationProcessLogException;

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	public CommerceDataIntegrationProcessLog fetchByC_S_Last(
		long CDataIntegrationProcessId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcessLog> orderByComparator);

	/**
	 * Returns the commerce data integration process logs before and after the current commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the current commerce data integration process log
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	public CommerceDataIntegrationProcessLog[] findByC_S_PrevAndNext(
			long commerceDataIntegrationProcessLogId,
			long CDataIntegrationProcessId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDataIntegrationProcessLog> orderByComparator)
		throws NoSuchDataIntegrationProcessLogException;

	/**
	 * Removes all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63; from the database.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 */
	public void removeByC_S(long CDataIntegrationProcessId, int status);

	/**
	 * Returns the number of commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @return the number of matching commerce data integration process logs
	 */
	public int countByC_S(long CDataIntegrationProcessId, int status);

	/**
	 * Caches the commerce data integration process log in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcessLog the commerce data integration process log
	 */
	public void cacheResult(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog);

	/**
	 * Caches the commerce data integration process logs in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcessLogs the commerce data integration process logs
	 */
	public void cacheResult(
		java.util.List<CommerceDataIntegrationProcessLog>
			commerceDataIntegrationProcessLogs);

	/**
	 * Creates a new commerce data integration process log with the primary key. Does not add the commerce data integration process log to the database.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key for the new commerce data integration process log
	 * @return the new commerce data integration process log
	 */
	public CommerceDataIntegrationProcessLog create(
		long commerceDataIntegrationProcessLogId);

	/**
	 * Removes the commerce data integration process log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the commerce data integration process log
	 * @return the commerce data integration process log that was removed
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	public CommerceDataIntegrationProcessLog remove(
			long commerceDataIntegrationProcessLogId)
		throws NoSuchDataIntegrationProcessLogException;

	public CommerceDataIntegrationProcessLog updateImpl(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog);

	/**
	 * Returns the commerce data integration process log with the primary key or throws a <code>NoSuchDataIntegrationProcessLogException</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the commerce data integration process log
	 * @return the commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	public CommerceDataIntegrationProcessLog findByPrimaryKey(
			long commerceDataIntegrationProcessLogId)
		throws NoSuchDataIntegrationProcessLogException;

	/**
	 * Returns the commerce data integration process log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the commerce data integration process log
	 * @return the commerce data integration process log, or <code>null</code> if a commerce data integration process log with the primary key could not be found
	 */
	public CommerceDataIntegrationProcessLog fetchByPrimaryKey(
		long commerceDataIntegrationProcessLogId);

	/**
	 * Returns all the commerce data integration process logs.
	 *
	 * @return the commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog> findAll();

	/**
	 * Returns a range of all the commerce data integration process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @return the range of commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce data integration process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcessLog> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce data integration process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce data integration process logs
	 */
	public java.util.List<CommerceDataIntegrationProcessLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDataIntegrationProcessLog> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce data integration process logs from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce data integration process logs.
	 *
	 * @return the number of commerce data integration process logs
	 */
	public int countAll();

}