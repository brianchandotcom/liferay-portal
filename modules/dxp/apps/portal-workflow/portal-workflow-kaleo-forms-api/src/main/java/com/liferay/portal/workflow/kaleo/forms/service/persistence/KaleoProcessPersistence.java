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

package com.liferay.portal.workflow.kaleo.forms.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.forms.exception.NoSuchKaleoProcessException;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo process service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcessUtil
 * @generated
 */
@ProviderType
public interface KaleoProcessPersistence extends BasePersistence<KaleoProcess> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoProcessUtil} to access the kaleo process persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the kaleo processes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByUuid(String uuid);

	/**
	 * Returns a range of all the kaleo processes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @return the range of matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo processes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo processes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo process in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process
	 * @throws NoSuchKaleoProcessException if a matching kaleo process could not be found
	 */
	public KaleoProcess findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the first kaleo process in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns the last kaleo process in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process
	 * @throws NoSuchKaleoProcessException if a matching kaleo process could not be found
	 */
	public KaleoProcess findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the last kaleo process in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns the kaleo processes before and after the current kaleo process in the ordered set where uuid = &#63;.
	 *
	 * @param kaleoProcessId the primary key of the current kaleo process
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo process
	 * @throws NoSuchKaleoProcessException if a kaleo process with the primary key could not be found
	 */
	public KaleoProcess[] findByUuid_PrevAndNext(
			long kaleoProcessId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Removes all the kaleo processes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of kaleo processes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching kaleo processes
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the kaleo process where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchKaleoProcessException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching kaleo process
	 * @throws NoSuchKaleoProcessException if a matching kaleo process could not be found
	 */
	public KaleoProcess findByUUID_G(String uuid, long groupId)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the kaleo process where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the kaleo process where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the kaleo process where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the kaleo process that was removed
	 */
	public KaleoProcess removeByUUID_G(String uuid, long groupId)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the number of kaleo processes where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching kaleo processes
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the kaleo processes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the kaleo processes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @return the range of matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo processes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo processes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo process in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process
	 * @throws NoSuchKaleoProcessException if a matching kaleo process could not be found
	 */
	public KaleoProcess findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the first kaleo process in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns the last kaleo process in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process
	 * @throws NoSuchKaleoProcessException if a matching kaleo process could not be found
	 */
	public KaleoProcess findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the last kaleo process in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns the kaleo processes before and after the current kaleo process in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param kaleoProcessId the primary key of the current kaleo process
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo process
	 * @throws NoSuchKaleoProcessException if a kaleo process with the primary key could not be found
	 */
	public KaleoProcess[] findByUuid_C_PrevAndNext(
			long kaleoProcessId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Removes all the kaleo processes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of kaleo processes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching kaleo processes
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the kaleo processes where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByGroupId(long groupId);

	/**
	 * Returns a range of all the kaleo processes where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @return the range of matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo processes where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo processes where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo processes
	 */
	public java.util.List<KaleoProcess> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo process in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process
	 * @throws NoSuchKaleoProcessException if a matching kaleo process could not be found
	 */
	public KaleoProcess findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the first kaleo process in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns the last kaleo process in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process
	 * @throws NoSuchKaleoProcessException if a matching kaleo process could not be found
	 */
	public KaleoProcess findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the last kaleo process in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns the kaleo processes before and after the current kaleo process in the ordered set where groupId = &#63;.
	 *
	 * @param kaleoProcessId the primary key of the current kaleo process
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo process
	 * @throws NoSuchKaleoProcessException if a kaleo process with the primary key could not be found
	 */
	public KaleoProcess[] findByGroupId_PrevAndNext(
			long kaleoProcessId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns all the kaleo processes that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching kaleo processes that the user has permission to view
	 */
	public java.util.List<KaleoProcess> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the kaleo processes that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @return the range of matching kaleo processes that the user has permission to view
	 */
	public java.util.List<KaleoProcess> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo processes that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo processes that the user has permission to view
	 */
	public java.util.List<KaleoProcess> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns the kaleo processes before and after the current kaleo process in the ordered set of kaleo processes that the user has permission to view where groupId = &#63;.
	 *
	 * @param kaleoProcessId the primary key of the current kaleo process
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo process
	 * @throws NoSuchKaleoProcessException if a kaleo process with the primary key could not be found
	 */
	public KaleoProcess[] filterFindByGroupId_PrevAndNext(
			long kaleoProcessId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
				orderByComparator)
		throws NoSuchKaleoProcessException;

	/**
	 * Removes all the kaleo processes where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of kaleo processes where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching kaleo processes
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of kaleo processes that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching kaleo processes that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the kaleo process where DDLRecordSetId = &#63; or throws a <code>NoSuchKaleoProcessException</code> if it could not be found.
	 *
	 * @param DDLRecordSetId the ddl record set ID
	 * @return the matching kaleo process
	 * @throws NoSuchKaleoProcessException if a matching kaleo process could not be found
	 */
	public KaleoProcess findByDDLRecordSetId(long DDLRecordSetId)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the kaleo process where DDLRecordSetId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param DDLRecordSetId the ddl record set ID
	 * @return the matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByDDLRecordSetId(long DDLRecordSetId);

	/**
	 * Returns the kaleo process where DDLRecordSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param DDLRecordSetId the ddl record set ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	public KaleoProcess fetchByDDLRecordSetId(
		long DDLRecordSetId, boolean useFinderCache);

	/**
	 * Removes the kaleo process where DDLRecordSetId = &#63; from the database.
	 *
	 * @param DDLRecordSetId the ddl record set ID
	 * @return the kaleo process that was removed
	 */
	public KaleoProcess removeByDDLRecordSetId(long DDLRecordSetId)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the number of kaleo processes where DDLRecordSetId = &#63;.
	 *
	 * @param DDLRecordSetId the ddl record set ID
	 * @return the number of matching kaleo processes
	 */
	public int countByDDLRecordSetId(long DDLRecordSetId);

	/**
	 * Caches the kaleo process in the entity cache if it is enabled.
	 *
	 * @param kaleoProcess the kaleo process
	 */
	public void cacheResult(KaleoProcess kaleoProcess);

	/**
	 * Caches the kaleo processes in the entity cache if it is enabled.
	 *
	 * @param kaleoProcesses the kaleo processes
	 */
	public void cacheResult(java.util.List<KaleoProcess> kaleoProcesses);

	/**
	 * Creates a new kaleo process with the primary key. Does not add the kaleo process to the database.
	 *
	 * @param kaleoProcessId the primary key for the new kaleo process
	 * @return the new kaleo process
	 */
	public KaleoProcess create(long kaleoProcessId);

	/**
	 * Removes the kaleo process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoProcessId the primary key of the kaleo process
	 * @return the kaleo process that was removed
	 * @throws NoSuchKaleoProcessException if a kaleo process with the primary key could not be found
	 */
	public KaleoProcess remove(long kaleoProcessId)
		throws NoSuchKaleoProcessException;

	public KaleoProcess updateImpl(KaleoProcess kaleoProcess);

	/**
	 * Returns the kaleo process with the primary key or throws a <code>NoSuchKaleoProcessException</code> if it could not be found.
	 *
	 * @param kaleoProcessId the primary key of the kaleo process
	 * @return the kaleo process
	 * @throws NoSuchKaleoProcessException if a kaleo process with the primary key could not be found
	 */
	public KaleoProcess findByPrimaryKey(long kaleoProcessId)
		throws NoSuchKaleoProcessException;

	/**
	 * Returns the kaleo process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoProcessId the primary key of the kaleo process
	 * @return the kaleo process, or <code>null</code> if a kaleo process with the primary key could not be found
	 */
	public KaleoProcess fetchByPrimaryKey(long kaleoProcessId);

	/**
	 * Returns all the kaleo processes.
	 *
	 * @return the kaleo processes
	 */
	public java.util.List<KaleoProcess> findAll();

	/**
	 * Returns a range of all the kaleo processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @return the range of kaleo processes
	 */
	public java.util.List<KaleoProcess> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the kaleo processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo processes
	 */
	public java.util.List<KaleoProcess> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>KaleoProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo processes
	 */
	public java.util.List<KaleoProcess> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoProcess>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the kaleo processes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of kaleo processes.
	 *
	 * @return the number of kaleo processes
	 */
	public int countAll();

}