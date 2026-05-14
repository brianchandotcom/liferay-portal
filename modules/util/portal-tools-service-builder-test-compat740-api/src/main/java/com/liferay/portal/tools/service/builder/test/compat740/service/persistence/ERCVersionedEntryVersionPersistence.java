/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchERCVersionedEntryVersionException;
import com.liferay.portal.tools.service.builder.test.compat740.model.ERCVersionedEntryVersion;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the erc versioned entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCVersionedEntryVersionUtil
 * @generated
 */
@ProviderType
public interface ERCVersionedEntryVersionPersistence
	extends BasePersistence<ERCVersionedEntryVersion> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ERCVersionedEntryVersionUtil} to access the erc versioned entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public java.util.List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion findByErcVersionedEntryId_First(
			long ercVersionedEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the first erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion fetchByErcVersionedEntryId_First(
		long ercVersionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator);

	/**
	 * Removes all the erc versioned entry versions where ercVersionedEntryId = &#63; from the database.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 */
	public void removeByErcVersionedEntryId(long ercVersionedEntryId);

	/**
	 * Returns the number of erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @return the number of matching erc versioned entry versions
	 */
	public int countByErcVersionedEntryId(long ercVersionedEntryId);

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion findByErcVersionedEntryId_Version(
			long ercVersionedEntryId, int version)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion fetchByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version, boolean useFinderCache);

	/**
	 * Removes the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the erc versioned entry version that was removed
	 */
	public ERCVersionedEntryVersion removeByErcVersionedEntryId_Version(
			long ercVersionedEntryId, int version)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the number of erc versioned entry versions where ercVersionedEntryId = &#63; and version = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	public int countByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version);

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public java.util.List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator);

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching erc versioned entry versions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public java.util.List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion findByUuid_Version_First(
			String uuid, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion fetchByUuid_Version_First(
		String uuid, int version,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator);

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	public void removeByUuid_Version(String uuid, int version);

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	public int countByUuid_Version(String uuid, int version);

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public java.util.List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion findByUUID_G_First(
			String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion fetchByUUID_G_First(
		String uuid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator);

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	public void removeByUUID_G(String uuid, long groupId);

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching erc versioned entry versions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion findByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version, boolean useFinderCache);

	/**
	 * Removes the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the erc versioned entry version that was removed
	 */
	public ERCVersionedEntryVersion removeByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and groupId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	public int countByUUID_G_Version(String uuid, long groupId, int version);

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public java.util.List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator);

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching erc versioned entry versions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public java.util.List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			com.liferay.portal.kernel.util.OrderByComparator
				<ERCVersionedEntryVersion> orderByComparator)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public ERCVersionedEntryVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator);

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	public void removeByUuid_C_Version(
		String uuid, long companyId, int version);

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	public int countByUuid_C_Version(String uuid, long companyId, int version);

	/**
	 * Creates a new erc versioned entry version with the primary key. Does not add the erc versioned entry version to the database.
	 *
	 * @param ercVersionedEntryVersionId the primary key for the new erc versioned entry version
	 * @return the new erc versioned entry version
	 */
	public ERCVersionedEntryVersion create(long ercVersionedEntryVersionId);

	/**
	 * Removes the erc versioned entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version that was removed
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public ERCVersionedEntryVersion remove(long ercVersionedEntryVersionId)
		throws NoSuchERCVersionedEntryVersionException;

	public ERCVersionedEntryVersion updateImpl(
		ERCVersionedEntryVersion ercVersionedEntryVersion);

	/**
	 * Returns the erc versioned entry version with the primary key or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public ERCVersionedEntryVersion findByPrimaryKey(
			long ercVersionedEntryVersionId)
		throws NoSuchERCVersionedEntryVersionException;

	/**
	 * Returns the erc versioned entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version, or <code>null</code> if a erc versioned entry version with the primary key could not be found
	 */
	public ERCVersionedEntryVersion fetchByPrimaryKey(
		long ercVersionedEntryVersionId);

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public default ERCVersionedEntryVersion fetchByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version) {

		return fetchByErcVersionedEntryId_Version(
			ercVersionedEntryId, version, true);
	}

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public default ERCVersionedEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version) {

		return fetchByUUID_G_Version(uuid, groupId, version, true);
	}

	/**
	 * Returns all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @return the matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion>
		findByErcVersionedEntryId(long ercVersionedEntryId) {

		return findByErcVersionedEntryId(
			ercVersionedEntryId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion>
		findByErcVersionedEntryId(
			long ercVersionedEntryId, int start, int end) {

		return findByErcVersionedEntryId(
			ercVersionedEntryId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion>
		findByErcVersionedEntryId(
			long ercVersionedEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<ERCVersionedEntryVersion> orderByComparator) {

		return findByErcVersionedEntryId(
			ercVersionedEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUuid(
		String uuid) {

		return findByUuid(
			uuid, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version) {

		return findByUuid_Version(
			uuid, version, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return findByUuid_Version(uuid, version, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid_Version(
			uuid, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId) {

		return findByUUID_G(
			uuid, groupId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion>
		findByUuid_C_Version(String uuid, long companyId, int version) {

		return findByUuid_C_Version(
			uuid, companyId, version,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion>
		findByUuid_C_Version(
			String uuid, long companyId, int version, int start, int end) {

		return findByUuid_C_Version(
			uuid, companyId, version, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public default java.util.List<ERCVersionedEntryVersion>
		findByUuid_C_Version(
			String uuid, long companyId, int version, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<ERCVersionedEntryVersion> orderByComparator) {

		return findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-53011125