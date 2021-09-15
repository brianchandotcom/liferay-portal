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

package com.liferay.search.experiences.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.search.experiences.exception.NoSuchSXPElementException;
import com.liferay.search.experiences.model.SXPElement;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the sxp element service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SXPElementUtil
 * @generated
 */
@ProviderType
public interface SXPElementPersistence extends BasePersistence<SXPElement> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SXPElementUtil} to access the sxp element persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the sxp elements where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sxp elements
	 */
	public java.util.List<SXPElement> findByUuid(String uuid);

	/**
	 * Returns a range of all the sxp elements where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @return the range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the sxp elements where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp elements where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the first sxp element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the last sxp element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the last sxp element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the sxp elements before and after the current sxp element in the ordered set where uuid = &#63;.
	 *
	 * @param sxpElementId the primary key of the current sxp element
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp element
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement[] findByUuid_PrevAndNext(
			long sxpElementId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Removes all the sxp elements where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of sxp elements where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sxp elements
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the sxp element where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSXPElementException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByUUID_G(String uuid, long groupId)
		throws NoSuchSXPElementException;

	/**
	 * Returns the sxp element where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the sxp element where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the sxp element where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the sxp element that was removed
	 */
	public SXPElement removeByUUID_G(String uuid, long groupId)
		throws NoSuchSXPElementException;

	/**
	 * Returns the number of sxp elements where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching sxp elements
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the sxp elements where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sxp elements
	 */
	public java.util.List<SXPElement> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the sxp elements where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @return the range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sxp elements where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp elements where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the first sxp element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the last sxp element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the last sxp element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the sxp elements before and after the current sxp element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sxpElementId the primary key of the current sxp element
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp element
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement[] findByUuid_C_PrevAndNext(
			long sxpElementId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Removes all the sxp elements where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of sxp elements where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sxp elements
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the sxp elements where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching sxp elements
	 */
	public java.util.List<SXPElement> findByGroupId(long groupId);

	/**
	 * Returns a range of all the sxp elements where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @return the range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the sxp elements where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp elements where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the first sxp element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the last sxp element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the last sxp element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the sxp elements before and after the current sxp element in the ordered set where groupId = &#63;.
	 *
	 * @param sxpElementId the primary key of the current sxp element
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp element
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement[] findByGroupId_PrevAndNext(
			long sxpElementId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Removes all the sxp elements where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of sxp elements where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching sxp elements
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the sxp elements where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sxp elements
	 */
	public java.util.List<SXPElement> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the sxp elements where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @return the range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sxp elements where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp elements where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the first sxp element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the last sxp element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the last sxp element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the sxp elements before and after the current sxp element in the ordered set where companyId = &#63;.
	 *
	 * @param sxpElementId the primary key of the current sxp element
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp element
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement[] findByCompanyId_PrevAndNext(
			long sxpElementId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Removes all the sxp elements where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of sxp elements where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sxp elements
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the sxp elements where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_S(long groupId, int status);

	/**
	 * Returns a range of all the sxp elements where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @return the range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the sxp elements where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp elements where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByG_S_First(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the first sxp element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the last sxp element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByG_S_Last(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the last sxp element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the sxp elements before and after the current sxp element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param sxpElementId the primary key of the current sxp element
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp element
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement[] findByG_S_PrevAndNext(
			long sxpElementId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Removes all the sxp elements where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public void removeByG_S(long groupId, int status);

	/**
	 * Returns the number of sxp elements where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching sxp elements
	 */
	public int countByG_S(long groupId, int status);

	/**
	 * Returns all the sxp elements where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_T(long groupId, int type);

	/**
	 * Returns a range of all the sxp elements where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @return the range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_T(
		long groupId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the sxp elements where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp elements where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByG_T_First(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the first sxp element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByG_T_First(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the last sxp element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByG_T_Last(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the last sxp element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByG_T_Last(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the sxp elements before and after the current sxp element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param sxpElementId the primary key of the current sxp element
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp element
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement[] findByG_T_PrevAndNext(
			long sxpElementId, long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Removes all the sxp elements where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public void removeByG_T(long groupId, int type);

	/**
	 * Returns the number of sxp elements where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching sxp elements
	 */
	public int countByG_T(long groupId, int type);

	/**
	 * Returns all the sxp elements where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching sxp elements
	 */
	public java.util.List<SXPElement> findByC_T(long companyId, int type);

	/**
	 * Returns a range of all the sxp elements where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @return the range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByC_T(
		long companyId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the sxp elements where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp elements where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByC_T_First(
			long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the first sxp element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the last sxp element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByC_T_Last(
			long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the last sxp element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByC_T_Last(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the sxp elements before and after the current sxp element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param sxpElementId the primary key of the current sxp element
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp element
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement[] findByC_T_PrevAndNext(
			long sxpElementId, long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Removes all the sxp elements where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, int type);

	/**
	 * Returns the number of sxp elements where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching sxp elements
	 */
	public int countByC_T(long companyId, int type);

	/**
	 * Returns all the sxp elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_S_T(
		long groupId, int status, int type);

	/**
	 * Returns a range of all the sxp elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @return the range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_S_T(
		long groupId, int status, int type, int start, int end);

	/**
	 * Returns an ordered range of all the sxp elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_S_T(
		long groupId, int status, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp elements
	 */
	public java.util.List<SXPElement> findByG_S_T(
		long groupId, int status, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sxp element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByG_S_T_First(
			long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the first sxp element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByG_S_T_First(
		long groupId, int status, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the last sxp element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element
	 * @throws NoSuchSXPElementException if a matching sxp element could not be found
	 */
	public SXPElement findByG_S_T_Last(
			long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Returns the last sxp element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp element, or <code>null</code> if a matching sxp element could not be found
	 */
	public SXPElement fetchByG_S_T_Last(
		long groupId, int status, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns the sxp elements before and after the current sxp element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param sxpElementId the primary key of the current sxp element
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp element
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement[] findByG_S_T_PrevAndNext(
			long sxpElementId, long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
				orderByComparator)
		throws NoSuchSXPElementException;

	/**
	 * Removes all the sxp elements where groupId = &#63; and status = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 */
	public void removeByG_S_T(long groupId, int status, int type);

	/**
	 * Returns the number of sxp elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the number of matching sxp elements
	 */
	public int countByG_S_T(long groupId, int status, int type);

	/**
	 * Caches the sxp element in the entity cache if it is enabled.
	 *
	 * @param sxpElement the sxp element
	 */
	public void cacheResult(SXPElement sxpElement);

	/**
	 * Caches the sxp elements in the entity cache if it is enabled.
	 *
	 * @param sxpElements the sxp elements
	 */
	public void cacheResult(java.util.List<SXPElement> sxpElements);

	/**
	 * Creates a new sxp element with the primary key. Does not add the sxp element to the database.
	 *
	 * @param sxpElementId the primary key for the new sxp element
	 * @return the new sxp element
	 */
	public SXPElement create(long sxpElementId);

	/**
	 * Removes the sxp element with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sxpElementId the primary key of the sxp element
	 * @return the sxp element that was removed
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement remove(long sxpElementId)
		throws NoSuchSXPElementException;

	public SXPElement updateImpl(SXPElement sxpElement);

	/**
	 * Returns the sxp element with the primary key or throws a <code>NoSuchSXPElementException</code> if it could not be found.
	 *
	 * @param sxpElementId the primary key of the sxp element
	 * @return the sxp element
	 * @throws NoSuchSXPElementException if a sxp element with the primary key could not be found
	 */
	public SXPElement findByPrimaryKey(long sxpElementId)
		throws NoSuchSXPElementException;

	/**
	 * Returns the sxp element with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sxpElementId the primary key of the sxp element
	 * @return the sxp element, or <code>null</code> if a sxp element with the primary key could not be found
	 */
	public SXPElement fetchByPrimaryKey(long sxpElementId);

	/**
	 * Returns all the sxp elements.
	 *
	 * @return the sxp elements
	 */
	public java.util.List<SXPElement> findAll();

	/**
	 * Returns a range of all the sxp elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @return the range of sxp elements
	 */
	public java.util.List<SXPElement> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the sxp elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sxp elements
	 */
	public java.util.List<SXPElement> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sxp elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp elements
	 * @param end the upper bound of the range of sxp elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sxp elements
	 */
	public java.util.List<SXPElement> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SXPElement>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the sxp elements from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of sxp elements.
	 *
	 * @return the number of sxp elements
	 */
	public int countAll();

}