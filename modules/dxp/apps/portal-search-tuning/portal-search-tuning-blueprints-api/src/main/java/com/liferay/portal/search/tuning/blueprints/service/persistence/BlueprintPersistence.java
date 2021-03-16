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
import com.liferay.portal.search.tuning.blueprints.exception.NoSuchBlueprintException;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the blueprint service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlueprintUtil
 * @generated
 */
@ProviderType
public interface BlueprintPersistence extends BasePersistence<Blueprint> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BlueprintUtil} to access the blueprint persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the blueprints where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching blueprints
	 */
	public java.util.List<Blueprint> findByUuid(String uuid);

	/**
	 * Returns a range of all the blueprints where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints
	 */
	public java.util.List<Blueprint> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blueprints where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the first blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the last blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the last blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] findByUuid_PrevAndNext(
			long blueprintId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Removes all the blueprints where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of blueprints where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching blueprints
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the blueprint where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchBlueprintException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByUUID_G(String uuid, long groupId)
		throws NoSuchBlueprintException;

	/**
	 * Returns the blueprint where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the blueprint where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the blueprint where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the blueprint that was removed
	 */
	public Blueprint removeByUUID_G(String uuid, long groupId)
		throws NoSuchBlueprintException;

	/**
	 * Returns the number of blueprints where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching blueprints
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching blueprints
	 */
	public java.util.List<Blueprint> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints
	 */
	public java.util.List<Blueprint> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the first blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the last blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the last blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] findByUuid_C_PrevAndNext(
			long blueprintId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Removes all the blueprints where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching blueprints
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the blueprints where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching blueprints
	 */
	public java.util.List<Blueprint> findByGroupId(long groupId);

	/**
	 * Returns a range of all the blueprints where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints
	 */
	public java.util.List<Blueprint> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blueprints where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the first blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the last blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the last blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] findByGroupId_PrevAndNext(
			long blueprintId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns all the blueprints that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the blueprints that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set of blueprints that the user has permission to view where groupId = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] filterFindByGroupId_PrevAndNext(
			long blueprintId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Removes all the blueprints where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of blueprints where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching blueprints
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of blueprints that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching blueprints that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns all the blueprints where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching blueprints
	 */
	public java.util.List<Blueprint> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the blueprints where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints
	 */
	public java.util.List<Blueprint> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blueprints where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the first blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the last blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the last blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] findByCompanyId_PrevAndNext(
			long blueprintId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Removes all the blueprints where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of blueprints where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching blueprints
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the blueprints where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching blueprints
	 */
	public java.util.List<Blueprint> findByG_S(long groupId, int status);

	/**
	 * Returns a range of all the blueprints where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints
	 */
	public java.util.List<Blueprint> findByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blueprints where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByG_S_First(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the first blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the last blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByG_S_Last(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the last blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] findByG_S_PrevAndNext(
			long blueprintId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns all the blueprints that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByG_S(long groupId, int status);

	/**
	 * Returns a range of all the blueprints that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set of blueprints that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] filterFindByG_S_PrevAndNext(
			long blueprintId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Removes all the blueprints where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public void removeByG_S(long groupId, int status);

	/**
	 * Returns the number of blueprints where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching blueprints
	 */
	public int countByG_S(long groupId, int status);

	/**
	 * Returns the number of blueprints that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching blueprints that the user has permission to view
	 */
	public int filterCountByG_S(long groupId, int status);

	/**
	 * Returns all the blueprints where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching blueprints
	 */
	public java.util.List<Blueprint> findByG_T(long groupId, int type);

	/**
	 * Returns a range of all the blueprints where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints
	 */
	public java.util.List<Blueprint> findByG_T(
		long groupId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blueprints where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blueprint in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByG_T_First(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the first blueprint in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByG_T_First(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the last blueprint in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByG_T_Last(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the last blueprint in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByG_T_Last(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] findByG_T_PrevAndNext(
			long blueprintId, long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns all the blueprints that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByG_T(long groupId, int type);

	/**
	 * Returns a range of all the blueprints that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByG_T(
		long groupId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set of blueprints that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] filterFindByG_T_PrevAndNext(
			long blueprintId, long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Removes all the blueprints where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public void removeByG_T(long groupId, int type);

	/**
	 * Returns the number of blueprints where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching blueprints
	 */
	public int countByG_T(long groupId, int type);

	/**
	 * Returns the number of blueprints that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching blueprints that the user has permission to view
	 */
	public int filterCountByG_T(long groupId, int type);

	/**
	 * Returns all the blueprints where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching blueprints
	 */
	public java.util.List<Blueprint> findByC_T(long companyId, int type);

	/**
	 * Returns a range of all the blueprints where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints
	 */
	public java.util.List<Blueprint> findByC_T(
		long companyId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blueprints where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blueprint in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByC_T_First(
			long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the first blueprint in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the last blueprint in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByC_T_Last(
			long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the last blueprint in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByC_T_Last(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] findByC_T_PrevAndNext(
			long blueprintId, long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Removes all the blueprints where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, int type);

	/**
	 * Returns the number of blueprints where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching blueprints
	 */
	public int countByC_T(long companyId, int type);

	/**
	 * Returns all the blueprints where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the matching blueprints
	 */
	public java.util.List<Blueprint> findByG_S_T(
		long groupId, int status, int type);

	/**
	 * Returns a range of all the blueprints where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints
	 */
	public java.util.List<Blueprint> findByG_S_T(
		long groupId, int status, int type, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByG_S_T(
		long groupId, int status, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blueprints where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blueprints
	 */
	public java.util.List<Blueprint> findByG_S_T(
		long groupId, int status, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first blueprint in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByG_S_T_First(
			long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the first blueprint in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByG_S_T_First(
		long groupId, int status, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the last blueprint in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint
	 * @throws NoSuchBlueprintException if a matching blueprint could not be found
	 */
	public Blueprint findByG_S_T_Last(
			long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns the last blueprint in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public Blueprint fetchByG_S_T_Last(
		long groupId, int status, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] findByG_S_T_PrevAndNext(
			long blueprintId, long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Returns all the blueprints that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByG_S_T(
		long groupId, int status, int type);

	/**
	 * Returns a range of all the blueprints that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByG_S_T(
		long groupId, int status, int type, int start, int end);

	/**
	 * Returns an ordered range of all the blueprints that the user has permissions to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blueprints that the user has permission to view
	 */
	public java.util.List<Blueprint> filterFindByG_S_T(
		long groupId, int status, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns the blueprints before and after the current blueprint in the ordered set of blueprints that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param blueprintId the primary key of the current blueprint
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint[] filterFindByG_S_T_PrevAndNext(
			long blueprintId, long groupId, int status, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
				orderByComparator)
		throws NoSuchBlueprintException;

	/**
	 * Removes all the blueprints where groupId = &#63; and status = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 */
	public void removeByG_S_T(long groupId, int status, int type);

	/**
	 * Returns the number of blueprints where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the number of matching blueprints
	 */
	public int countByG_S_T(long groupId, int status, int type);

	/**
	 * Returns the number of blueprints that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the number of matching blueprints that the user has permission to view
	 */
	public int filterCountByG_S_T(long groupId, int status, int type);

	/**
	 * Caches the blueprint in the entity cache if it is enabled.
	 *
	 * @param blueprint the blueprint
	 */
	public void cacheResult(Blueprint blueprint);

	/**
	 * Caches the blueprints in the entity cache if it is enabled.
	 *
	 * @param blueprints the blueprints
	 */
	public void cacheResult(java.util.List<Blueprint> blueprints);

	/**
	 * Creates a new blueprint with the primary key. Does not add the blueprint to the database.
	 *
	 * @param blueprintId the primary key for the new blueprint
	 * @return the new blueprint
	 */
	public Blueprint create(long blueprintId);

	/**
	 * Removes the blueprint with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param blueprintId the primary key of the blueprint
	 * @return the blueprint that was removed
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint remove(long blueprintId) throws NoSuchBlueprintException;

	public Blueprint updateImpl(Blueprint blueprint);

	/**
	 * Returns the blueprint with the primary key or throws a <code>NoSuchBlueprintException</code> if it could not be found.
	 *
	 * @param blueprintId the primary key of the blueprint
	 * @return the blueprint
	 * @throws NoSuchBlueprintException if a blueprint with the primary key could not be found
	 */
	public Blueprint findByPrimaryKey(long blueprintId)
		throws NoSuchBlueprintException;

	/**
	 * Returns the blueprint with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param blueprintId the primary key of the blueprint
	 * @return the blueprint, or <code>null</code> if a blueprint with the primary key could not be found
	 */
	public Blueprint fetchByPrimaryKey(long blueprintId);

	/**
	 * Returns all the blueprints.
	 *
	 * @return the blueprints
	 */
	public java.util.List<Blueprint> findAll();

	/**
	 * Returns a range of all the blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of blueprints
	 */
	public java.util.List<Blueprint> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of blueprints
	 */
	public java.util.List<Blueprint> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator);

	/**
	 * Returns an ordered range of all the blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of blueprints
	 */
	public java.util.List<Blueprint> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Blueprint>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the blueprints from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of blueprints.
	 *
	 * @return the number of blueprints
	 */
	public int countAll();

}