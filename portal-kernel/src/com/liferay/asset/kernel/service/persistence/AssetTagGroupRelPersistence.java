/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service.persistence;

import com.liferay.asset.kernel.exception.NoSuchTagGroupRelException;
import com.liferay.asset.kernel.model.AssetTagGroupRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset tag group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagGroupRelUtil
 * @generated
 */
@ProviderType
public interface AssetTagGroupRelPersistence
	extends BasePersistence<AssetTagGroupRel>, CTPersistence<AssetTagGroupRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetTagGroupRelUtil} to access the asset tag group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset tag group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByUuid(String uuid);

	/**
	 * Returns a range of all the asset tag group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
				orderByComparator)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
				orderByComparator)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Removes all the asset tag group rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset tag group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset tag group rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the asset tag group rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTagGroupRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByUUID_G(String uuid, long groupId)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the asset tag group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the asset tag group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the asset tag group rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset tag group rel that was removed
	 */
	public AssetTagGroupRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the number of asset tag group rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset tag group rels
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
				orderByComparator)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
				orderByComparator)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Removes all the asset tag group rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset tag group rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset tag group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByGroupId(long groupId);

	/**
	 * Returns a range of all the asset tag group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the asset tag group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset tag group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
				orderByComparator)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the first asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Returns the last asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
				orderByComparator)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the last asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Removes all the asset tag group rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of asset tag group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset tag group rels
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the asset tag group rels where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByTagId(long tagId);

	/**
	 * Returns a range of all the asset tag group rels where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByTagId(
		long tagId, int start, int end);

	/**
	 * Returns an ordered range of all the asset tag group rels where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset tag group rels where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByTagId_First(
			long tagId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
				orderByComparator)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the first asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByTagId_First(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Returns the last asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByTagId_Last(
			long tagId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
				orderByComparator)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the last asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByTagId_Last(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Removes all the asset tag group rels where tagId = &#63; from the database.
	 *
	 * @param tagId the tag ID
	 */
	public void removeByTagId(long tagId);

	/**
	 * Returns the number of asset tag group rels where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the number of matching asset tag group rels
	 */
	public int countByTagId(long tagId);

	/**
	 * Returns the asset tag group rel where groupId = &#63; and tagId = &#63; or throws a <code>NoSuchTagGroupRelException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel findByG_T(long groupId, long tagId)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the asset tag group rel where groupId = &#63; and tagId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByG_T(long groupId, long tagId);

	/**
	 * Returns the asset tag group rel where groupId = &#63; and tagId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public AssetTagGroupRel fetchByG_T(
		long groupId, long tagId, boolean useFinderCache);

	/**
	 * Removes the asset tag group rel where groupId = &#63; and tagId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the asset tag group rel that was removed
	 */
	public AssetTagGroupRel removeByG_T(long groupId, long tagId)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the number of asset tag group rels where groupId = &#63; and tagId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the number of matching asset tag group rels
	 */
	public int countByG_T(long groupId, long tagId);

	/**
	 * Caches the asset tag group rel in the entity cache if it is enabled.
	 *
	 * @param assetTagGroupRel the asset tag group rel
	 */
	public void cacheResult(AssetTagGroupRel assetTagGroupRel);

	/**
	 * Caches the asset tag group rels in the entity cache if it is enabled.
	 *
	 * @param assetTagGroupRels the asset tag group rels
	 */
	public void cacheResult(java.util.List<AssetTagGroupRel> assetTagGroupRels);

	/**
	 * Creates a new asset tag group rel with the primary key. Does not add the asset tag group rel to the database.
	 *
	 * @param assetTagGroupRelId the primary key for the new asset tag group rel
	 * @return the new asset tag group rel
	 */
	public AssetTagGroupRel create(long assetTagGroupRelId);

	/**
	 * Removes the asset tag group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagGroupRelId the primary key of the asset tag group rel
	 * @return the asset tag group rel that was removed
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	public AssetTagGroupRel remove(long assetTagGroupRelId)
		throws NoSuchTagGroupRelException;

	public AssetTagGroupRel updateImpl(AssetTagGroupRel assetTagGroupRel);

	/**
	 * Returns the asset tag group rel with the primary key or throws a <code>NoSuchTagGroupRelException</code> if it could not be found.
	 *
	 * @param assetTagGroupRelId the primary key of the asset tag group rel
	 * @return the asset tag group rel
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	public AssetTagGroupRel findByPrimaryKey(long assetTagGroupRelId)
		throws NoSuchTagGroupRelException;

	/**
	 * Returns the asset tag group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetTagGroupRelId the primary key of the asset tag group rel
	 * @return the asset tag group rel, or <code>null</code> if a asset tag group rel with the primary key could not be found
	 */
	public AssetTagGroupRel fetchByPrimaryKey(long assetTagGroupRelId);

	/**
	 * Returns all the asset tag group rels.
	 *
	 * @return the asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findAll();

	/**
	 * Returns a range of all the asset tag group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the asset tag group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset tag group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset tag group rels
	 */
	public java.util.List<AssetTagGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagGroupRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset tag group rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset tag group rels.
	 *
	 * @return the number of asset tag group rels
	 */
	public int countAll();

}
// LIFERAY-SERVICE-BUILDER-HASH:1632329678