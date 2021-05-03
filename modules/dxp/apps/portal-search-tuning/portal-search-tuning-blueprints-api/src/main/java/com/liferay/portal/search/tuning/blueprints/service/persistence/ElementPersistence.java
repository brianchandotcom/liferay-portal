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

package com.liferay.portal.search.tuning.blueprints.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.search.tuning.blueprints.exception.NoSuchElementException;
import com.liferay.portal.search.tuning.blueprints.model.Element;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the element service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ElementUtil
 * @generated
 */
@ProviderType
public interface ElementPersistence extends BasePersistence<Element> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ElementUtil} to access the element persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the elements where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching elements
	 */
	public java.util.List<Element> findByUuid(String uuid);

	/**
	 * Returns a range of all the elements where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public java.util.List<Element> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the elements where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns an ordered range of all the elements where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the first element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the last element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the last element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set where uuid = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] findByUuid_PrevAndNext(
			long elementId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Removes all the elements where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of elements where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching elements
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the element where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchElementException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByUUID_G(String uuid, long groupId)
		throws NoSuchElementException;

	/**
	 * Returns the element where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the element where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the element where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the element that was removed
	 */
	public Element removeByUUID_G(String uuid, long groupId)
		throws NoSuchElementException;

	/**
	 * Returns the number of elements where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching elements
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the elements where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching elements
	 */
	public java.util.List<Element> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the elements where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public java.util.List<Element> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the elements where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns an ordered range of all the elements where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the first element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the last element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the last element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] findByUuid_C_PrevAndNext(
			long elementId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Removes all the elements where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of elements where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching elements
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the elements where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching elements
	 */
	public java.util.List<Element> findByGroupId(long groupId);

	/**
	 * Returns a range of all the elements where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public java.util.List<Element> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the elements where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns an ordered range of all the elements where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the first element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the last element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the last element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set where groupId = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] findByGroupId_PrevAndNext(
			long elementId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns all the elements that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the elements that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the elements that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set of elements that the user has permission to view where groupId = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] filterFindByGroupId_PrevAndNext(
			long elementId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Removes all the elements where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of elements where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching elements
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of elements that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching elements that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns all the elements where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching elements
	 */
	public java.util.List<Element> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the elements where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public java.util.List<Element> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the elements where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns an ordered range of all the elements where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the first element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the last element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the last element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set where companyId = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] findByCompanyId_PrevAndNext(
			long elementId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Removes all the elements where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of elements where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching elements
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the elements where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching elements
	 */
	public java.util.List<Element> findByG_S(long groupId, int status);

	/**
	 * Returns a range of all the elements where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public java.util.List<Element> findByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByG_S_First(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByG_S_Last(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] findByG_S_PrevAndNext(
			long elementId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns all the elements that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByG_S(long groupId, int status);

	/**
	 * Returns a range of all the elements that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the elements that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set of elements that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] filterFindByG_S_PrevAndNext(
			long elementId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Removes all the elements where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public void removeByG_S(long groupId, int status);

	/**
	 * Returns the number of elements where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching elements
	 */
	public int countByG_S(long groupId, int status);

	/**
	 * Returns the number of elements that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching elements that the user has permission to view
	 */
	public int filterCountByG_S(long groupId, int status);

	/**
	 * Returns all the elements where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching elements
	 */
	public java.util.List<Element> findByG_T(long groupId, int type);

	/**
	 * Returns a range of all the elements where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public java.util.List<Element> findByG_T(
		long groupId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByG_T_First(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByG_T_First(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByG_T_Last(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByG_T_Last(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] findByG_T_PrevAndNext(
			long elementId, long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns all the elements that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByG_T(long groupId, int type);

	/**
	 * Returns a range of all the elements that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByG_T(
		long groupId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the elements that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set of elements that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] filterFindByG_T_PrevAndNext(
			long elementId, long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Removes all the elements where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public void removeByG_T(long groupId, int type);

	/**
	 * Returns the number of elements where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching elements
	 */
	public int countByG_T(long groupId, int type);

	/**
	 * Returns the number of elements that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching elements that the user has permission to view
	 */
	public int filterCountByG_T(long groupId, int type);

	/**
	 * Returns all the elements where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching elements
	 */
	public java.util.List<Element> findByC_T(long companyId, int type);

	/**
	 * Returns a range of all the elements where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public java.util.List<Element> findByC_T(
		long companyId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the elements where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns an ordered range of all the elements where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByC_T_First(
			long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the first element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the last element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByC_T_Last(
			long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the last element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByC_T_Last(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] findByC_T_PrevAndNext(
			long elementId, long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Removes all the elements where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, int type);

	/**
	 * Returns the number of elements where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching elements
	 */
	public int countByC_T(long companyId, int type);

	/**
	 * Returns all the elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the matching elements
	 */
	public java.util.List<Element> findByG_S_T(
		long groupId, int status, int type);

	/**
	 * Returns a range of all the elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public java.util.List<Element> findByG_S_T(
		long groupId, int status, int type, int start, int end);

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByG_S_T(
		long groupId, int status, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public java.util.List<Element> findByG_S_T(
		long groupId, int status, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByG_S_T_First(
			long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByG_S_T_First(
		long groupId, int status, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public Element findByG_S_T_Last(
			long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public Element fetchByG_S_T_Last(
		long groupId, int status, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] findByG_S_T_PrevAndNext(
			long elementId, long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Returns all the elements that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByG_S_T(
		long groupId, int status, int type);

	/**
	 * Returns a range of all the elements that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByG_S_T(
		long groupId, int status, int type, int start, int end);

	/**
	 * Returns an ordered range of all the elements that the user has permissions to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements that the user has permission to view
	 */
	public java.util.List<Element> filterFindByG_S_T(
		long groupId, int status, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns the elements before and after the current element in the ordered set of elements that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element[] filterFindByG_S_T_PrevAndNext(
			long elementId, long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Element>
				orderByComparator)
		throws NoSuchElementException;

	/**
	 * Removes all the elements where groupId = &#63; and status = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 */
	public void removeByG_S_T(long groupId, int status, int type);

	/**
	 * Returns the number of elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the number of matching elements
	 */
	public int countByG_S_T(long groupId, int status, int type);

	/**
	 * Returns the number of elements that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the number of matching elements that the user has permission to view
	 */
	public int filterCountByG_S_T(long groupId, int status, int type);

	/**
	 * Caches the element in the entity cache if it is enabled.
	 *
	 * @param element the element
	 */
	public void cacheResult(Element element);

	/**
	 * Caches the elements in the entity cache if it is enabled.
	 *
	 * @param elements the elements
	 */
	public void cacheResult(java.util.List<Element> elements);

	/**
	 * Creates a new element with the primary key. Does not add the element to the database.
	 *
	 * @param elementId the primary key for the new element
	 * @return the new element
	 */
	public Element create(long elementId);

	/**
	 * Removes the element with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param elementId the primary key of the element
	 * @return the element that was removed
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element remove(long elementId) throws NoSuchElementException;

	public Element updateImpl(Element element);

	/**
	 * Returns the element with the primary key or throws a <code>NoSuchElementException</code> if it could not be found.
	 *
	 * @param elementId the primary key of the element
	 * @return the element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public Element findByPrimaryKey(long elementId)
		throws NoSuchElementException;

	/**
	 * Returns the element with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param elementId the primary key of the element
	 * @return the element, or <code>null</code> if a element with the primary key could not be found
	 */
	public Element fetchByPrimaryKey(long elementId);

	/**
	 * Returns all the elements.
	 *
	 * @return the elements
	 */
	public java.util.List<Element> findAll();

	/**
	 * Returns a range of all the elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of elements
	 */
	public java.util.List<Element> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of elements
	 */
	public java.util.List<Element> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator);

	/**
	 * Returns an ordered range of all the elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of elements
	 */
	public java.util.List<Element> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Element>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the elements from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of elements.
	 *
	 * @return the number of elements
	 */
	public int countAll();

}