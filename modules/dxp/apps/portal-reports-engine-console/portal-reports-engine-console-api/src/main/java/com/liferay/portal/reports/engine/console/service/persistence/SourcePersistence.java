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

package com.liferay.portal.reports.engine.console.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.reports.engine.console.exception.NoSuchSourceException;
import com.liferay.portal.reports.engine.console.model.Source;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the source service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SourceUtil
 * @generated
 */
@ProviderType
public interface SourcePersistence extends BasePersistence<Source> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SourceUtil} to access the source persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the sources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sources
	 */
	public java.util.List<Source> findByUuid(String uuid);

	/**
	 * Returns a range of all the sources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @return the range of matching sources
	 */
	public java.util.List<Source> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the sources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sources
	 */
	public java.util.List<Source> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sources
	 */
	public java.util.List<Source> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching source
	 * @throws NoSuchSourceException if a matching source could not be found
	 */
	public Source findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the first source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns the last source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching source
	 * @throws NoSuchSourceException if a matching source could not be found
	 */
	public Source findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the last source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns the sources before and after the current source in the ordered set where uuid = &#63;.
	 *
	 * @param sourceId the primary key of the current source
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next source
	 * @throws NoSuchSourceException if a source with the primary key could not be found
	 */
	public Source[] findByUuid_PrevAndNext(
			long sourceId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Removes all the sources where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of sources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sources
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the source where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSourceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching source
	 * @throws NoSuchSourceException if a matching source could not be found
	 */
	public Source findByUUID_G(String uuid, long groupId)
		throws NoSuchSourceException;

	/**
	 * Returns the source where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the source where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the source where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the source that was removed
	 */
	public Source removeByUUID_G(String uuid, long groupId)
		throws NoSuchSourceException;

	/**
	 * Returns the number of sources where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching sources
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the sources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sources
	 */
	public java.util.List<Source> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the sources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @return the range of matching sources
	 */
	public java.util.List<Source> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sources
	 */
	public java.util.List<Source> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sources
	 */
	public java.util.List<Source> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching source
	 * @throws NoSuchSourceException if a matching source could not be found
	 */
	public Source findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the first source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns the last source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching source
	 * @throws NoSuchSourceException if a matching source could not be found
	 */
	public Source findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the last source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns the sources before and after the current source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sourceId the primary key of the current source
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next source
	 * @throws NoSuchSourceException if a source with the primary key could not be found
	 */
	public Source[] findByUuid_C_PrevAndNext(
			long sourceId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Removes all the sources where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of sources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sources
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the sources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching sources
	 */
	public java.util.List<Source> findByGroupId(long groupId);

	/**
	 * Returns a range of all the sources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @return the range of matching sources
	 */
	public java.util.List<Source> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the sources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sources
	 */
	public java.util.List<Source> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sources where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sources
	 */
	public java.util.List<Source> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first source in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching source
	 * @throws NoSuchSourceException if a matching source could not be found
	 */
	public Source findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the first source in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns the last source in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching source
	 * @throws NoSuchSourceException if a matching source could not be found
	 */
	public Source findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the last source in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns the sources before and after the current source in the ordered set where groupId = &#63;.
	 *
	 * @param sourceId the primary key of the current source
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next source
	 * @throws NoSuchSourceException if a source with the primary key could not be found
	 */
	public Source[] findByGroupId_PrevAndNext(
			long sourceId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns all the sources that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching sources that the user has permission to view
	 */
	public java.util.List<Source> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the sources that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @return the range of matching sources that the user has permission to view
	 */
	public java.util.List<Source> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the sources that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sources that the user has permission to view
	 */
	public java.util.List<Source> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns the sources before and after the current source in the ordered set of sources that the user has permission to view where groupId = &#63;.
	 *
	 * @param sourceId the primary key of the current source
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next source
	 * @throws NoSuchSourceException if a source with the primary key could not be found
	 */
	public Source[] filterFindByGroupId_PrevAndNext(
			long sourceId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Removes all the sources where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of sources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching sources
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of sources that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching sources that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns all the sources where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sources
	 */
	public java.util.List<Source> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the sources where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @return the range of matching sources
	 */
	public java.util.List<Source> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the sources where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sources
	 */
	public java.util.List<Source> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sources where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sources
	 */
	public java.util.List<Source> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching source
	 * @throws NoSuchSourceException if a matching source could not be found
	 */
	public Source findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the first source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns the last source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching source
	 * @throws NoSuchSourceException if a matching source could not be found
	 */
	public Source findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Returns the last source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching source, or <code>null</code> if a matching source could not be found
	 */
	public Source fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns the sources before and after the current source in the ordered set where companyId = &#63;.
	 *
	 * @param sourceId the primary key of the current source
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next source
	 * @throws NoSuchSourceException if a source with the primary key could not be found
	 */
	public Source[] findByCompanyId_PrevAndNext(
			long sourceId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Source>
				orderByComparator)
		throws NoSuchSourceException;

	/**
	 * Removes all the sources where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of sources where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sources
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the source in the entity cache if it is enabled.
	 *
	 * @param source the source
	 */
	public void cacheResult(Source source);

	/**
	 * Caches the sources in the entity cache if it is enabled.
	 *
	 * @param sources the sources
	 */
	public void cacheResult(java.util.List<Source> sources);

	/**
	 * Creates a new source with the primary key. Does not add the source to the database.
	 *
	 * @param sourceId the primary key for the new source
	 * @return the new source
	 */
	public Source create(long sourceId);

	/**
	 * Removes the source with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sourceId the primary key of the source
	 * @return the source that was removed
	 * @throws NoSuchSourceException if a source with the primary key could not be found
	 */
	public Source remove(long sourceId) throws NoSuchSourceException;

	public Source updateImpl(Source source);

	/**
	 * Returns the source with the primary key or throws a <code>NoSuchSourceException</code> if it could not be found.
	 *
	 * @param sourceId the primary key of the source
	 * @return the source
	 * @throws NoSuchSourceException if a source with the primary key could not be found
	 */
	public Source findByPrimaryKey(long sourceId) throws NoSuchSourceException;

	/**
	 * Returns the source with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sourceId the primary key of the source
	 * @return the source, or <code>null</code> if a source with the primary key could not be found
	 */
	public Source fetchByPrimaryKey(long sourceId);

	/**
	 * Returns all the sources.
	 *
	 * @return the sources
	 */
	public java.util.List<Source> findAll();

	/**
	 * Returns a range of all the sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @return the range of sources
	 */
	public java.util.List<Source> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sources
	 */
	public java.util.List<Source> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SourceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sources
	 * @param end the upper bound of the range of sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sources
	 */
	public java.util.List<Source> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Source>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the sources from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of sources.
	 *
	 * @return the number of sources
	 */
	public int countAll();

}