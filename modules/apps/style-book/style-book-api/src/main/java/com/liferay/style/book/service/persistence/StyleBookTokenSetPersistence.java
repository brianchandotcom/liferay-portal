/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.style.book.exception.NoSuchTokenSetException;
import com.liferay.style.book.model.StyleBookTokenSet;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the style book token set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookTokenSetUtil
 * @generated
 */
@ProviderType
public interface StyleBookTokenSetPersistence
	extends BasePersistence<StyleBookTokenSet>,
			CTPersistence<StyleBookTokenSet> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link StyleBookTokenSetUtil} to access the style book token set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the style book token sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book token sets
	 */
	public java.util.List<StyleBookTokenSet> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book token set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	public StyleBookTokenSet findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
				orderByComparator)
		throws NoSuchTokenSetException;

	/**
	 * Returns the first style book token set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public StyleBookTokenSet fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
			orderByComparator);

	/**
	 * Removes all the style book token sets where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of style book token sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching style book token sets
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the style book token set where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTokenSetException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	public StyleBookTokenSet findByUUID_G(String uuid, long groupId)
		throws NoSuchTokenSetException;

	/**
	 * Returns the style book token set where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public StyleBookTokenSet fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the style book token set where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the style book token set that was removed
	 */
	public StyleBookTokenSet removeByUUID_G(String uuid, long groupId)
		throws NoSuchTokenSetException;

	/**
	 * Returns the number of style book token sets where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching style book token sets
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns an ordered range of all the style book token sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book token sets
	 */
	public java.util.List<StyleBookTokenSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book token set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	public StyleBookTokenSet findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
				orderByComparator)
		throws NoSuchTokenSetException;

	/**
	 * Returns the first style book token set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public StyleBookTokenSet fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
			orderByComparator);

	/**
	 * Removes all the style book token sets where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of style book token sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching style book token sets
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns an ordered range of all the style book token sets where styleBookEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book token sets
	 */
	public java.util.List<StyleBookTokenSet> findByStyleBookEntryId(
		long styleBookEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first style book token set in the ordered set where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	public StyleBookTokenSet findByStyleBookEntryId_First(
			long styleBookEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
				orderByComparator)
		throws NoSuchTokenSetException;

	/**
	 * Returns the first style book token set in the ordered set where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public StyleBookTokenSet fetchByStyleBookEntryId_First(
		long styleBookEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
			orderByComparator);

	/**
	 * Removes all the style book token sets where styleBookEntryId = &#63; from the database.
	 *
	 * @param styleBookEntryId the style book entry ID
	 */
	public void removeByStyleBookEntryId(long styleBookEntryId);

	/**
	 * Returns the number of style book token sets where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @return the number of matching style book token sets
	 */
	public int countByStyleBookEntryId(long styleBookEntryId);

	/**
	 * Returns the style book token set where styleBookEntryId = &#63; and frontendTokenCategoryName = &#63; and frontendTokenDefinitionId = &#63; and name = &#63; or throws a <code>NoSuchTokenSetException</code> if it could not be found.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param frontendTokenCategoryName the frontend token category name
	 * @param frontendTokenDefinitionId the frontend token definition ID
	 * @param name the name
	 * @return the matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	public StyleBookTokenSet findBySBEI_FTCN_FTDI_N(
			long styleBookEntryId, String frontendTokenCategoryName,
			String frontendTokenDefinitionId, String name)
		throws NoSuchTokenSetException;

	/**
	 * Returns the style book token set where styleBookEntryId = &#63; and frontendTokenCategoryName = &#63; and frontendTokenDefinitionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param frontendTokenCategoryName the frontend token category name
	 * @param frontendTokenDefinitionId the frontend token definition ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public StyleBookTokenSet fetchBySBEI_FTCN_FTDI_N(
		long styleBookEntryId, String frontendTokenCategoryName,
		String frontendTokenDefinitionId, String name, boolean useFinderCache);

	/**
	 * Removes the style book token set where styleBookEntryId = &#63; and frontendTokenCategoryName = &#63; and frontendTokenDefinitionId = &#63; and name = &#63; from the database.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param frontendTokenCategoryName the frontend token category name
	 * @param frontendTokenDefinitionId the frontend token definition ID
	 * @param name the name
	 * @return the style book token set that was removed
	 */
	public StyleBookTokenSet removeBySBEI_FTCN_FTDI_N(
			long styleBookEntryId, String frontendTokenCategoryName,
			String frontendTokenDefinitionId, String name)
		throws NoSuchTokenSetException;

	/**
	 * Returns the number of style book token sets where styleBookEntryId = &#63; and frontendTokenCategoryName = &#63; and frontendTokenDefinitionId = &#63; and name = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param frontendTokenCategoryName the frontend token category name
	 * @param frontendTokenDefinitionId the frontend token definition ID
	 * @param name the name
	 * @return the number of matching style book token sets
	 */
	public int countBySBEI_FTCN_FTDI_N(
		long styleBookEntryId, String frontendTokenCategoryName,
		String frontendTokenDefinitionId, String name);

	/**
	 * Returns the style book token set where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchTokenSetException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching style book token set
	 * @throws NoSuchTokenSetException if a matching style book token set could not be found
	 */
	public StyleBookTokenSet findByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchTokenSetException;

	/**
	 * Returns the style book token set where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public StyleBookTokenSet fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache);

	/**
	 * Removes the style book token set where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the style book token set that was removed
	 */
	public StyleBookTokenSet removeByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchTokenSetException;

	/**
	 * Returns the number of style book token sets where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching style book token sets
	 */
	public int countByERC_G(String externalReferenceCode, long groupId);

	/**
	 * Creates a new style book token set with the primary key. Does not add the style book token set to the database.
	 *
	 * @param styleBookTokenSetId the primary key for the new style book token set
	 * @return the new style book token set
	 */
	public StyleBookTokenSet create(long styleBookTokenSetId);

	/**
	 * Removes the style book token set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set that was removed
	 * @throws NoSuchTokenSetException if a style book token set with the primary key could not be found
	 */
	public StyleBookTokenSet remove(long styleBookTokenSetId)
		throws NoSuchTokenSetException;

	public StyleBookTokenSet updateImpl(StyleBookTokenSet styleBookTokenSet);

	/**
	 * Returns the style book token set with the primary key or throws a <code>NoSuchTokenSetException</code> if it could not be found.
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set
	 * @throws NoSuchTokenSetException if a style book token set with the primary key could not be found
	 */
	public StyleBookTokenSet findByPrimaryKey(long styleBookTokenSetId)
		throws NoSuchTokenSetException;

	/**
	 * Returns the style book token set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set, or <code>null</code> if a style book token set with the primary key could not be found
	 */
	public StyleBookTokenSet fetchByPrimaryKey(long styleBookTokenSetId);

	/**
	 * Returns the style book token set where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public default StyleBookTokenSet fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the style book token set where styleBookEntryId = &#63; and frontendTokenCategoryName = &#63; and frontendTokenDefinitionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param frontendTokenCategoryName the frontend token category name
	 * @param frontendTokenDefinitionId the frontend token definition ID
	 * @param name the name
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public default StyleBookTokenSet fetchBySBEI_FTCN_FTDI_N(
		long styleBookEntryId, String frontendTokenCategoryName,
		String frontendTokenDefinitionId, String name) {

		return fetchBySBEI_FTCN_FTDI_N(
			styleBookEntryId, frontendTokenCategoryName,
			frontendTokenDefinitionId, name, true);
	}

	/**
	 * Returns the style book token set where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	public default StyleBookTokenSet fetchByERC_G(
		String externalReferenceCode, long groupId) {

		return fetchByERC_G(externalReferenceCode, groupId, true);
	}

	/**
	 * Returns all the style book token sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching style book token sets
	 */
	public default java.util.List<StyleBookTokenSet> findByUuid(String uuid) {
		return findByUuid(
			uuid, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the style book token sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @return the range of matching style book token sets
	 */
	public default java.util.List<StyleBookTokenSet> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the style book token sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book token sets
	 */
	public default java.util.List<StyleBookTokenSet> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the style book token sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching style book token sets
	 */
	public default java.util.List<StyleBookTokenSet> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the style book token sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @return the range of matching style book token sets
	 */
	public default java.util.List<StyleBookTokenSet> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the style book token sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book token sets
	 */
	public default java.util.List<StyleBookTokenSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the style book token sets where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @return the matching style book token sets
	 */
	public default java.util.List<StyleBookTokenSet> findByStyleBookEntryId(
		long styleBookEntryId) {

		return findByStyleBookEntryId(
			styleBookEntryId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the style book token sets where styleBookEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @return the range of matching style book token sets
	 */
	public default java.util.List<StyleBookTokenSet> findByStyleBookEntryId(
		long styleBookEntryId, int start, int end) {

		return findByStyleBookEntryId(styleBookEntryId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the style book token sets where styleBookEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book token sets
	 */
	public default java.util.List<StyleBookTokenSet> findByStyleBookEntryId(
		long styleBookEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<StyleBookTokenSet>
			orderByComparator) {

		return findByStyleBookEntryId(
			styleBookEntryId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-427640873