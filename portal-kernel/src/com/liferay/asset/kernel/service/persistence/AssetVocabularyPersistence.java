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

package com.liferay.asset.kernel.service.persistence;

import com.liferay.asset.kernel.exception.NoSuchVocabularyException;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset vocabulary service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyUtil
 * @generated
 */
@ProviderType
public interface AssetVocabularyPersistence
	extends BasePersistence<AssetVocabulary>, CTPersistence<AssetVocabulary> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetVocabularyUtil} to access the asset vocabulary persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset vocabularies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByUuid(String uuid);

	/**
	 * Returns a range of all the asset vocabularies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset vocabularies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the first asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the last asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the last asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where uuid = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary[] findByUuid_PrevAndNext(
			long vocabularyId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Removes all the asset vocabularies where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset vocabularies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset vocabularies
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the asset vocabulary where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVocabularyException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByUUID_G(String uuid, long groupId)
		throws NoSuchVocabularyException;

	/**
	 * Returns the asset vocabulary where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the asset vocabulary where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the asset vocabulary where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset vocabulary that was removed
	 */
	public AssetVocabulary removeByUUID_G(String uuid, long groupId)
		throws NoSuchVocabularyException;

	/**
	 * Returns the number of asset vocabularies where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset vocabularies
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset vocabularies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the first asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the last asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the last asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary[] findByUuid_C_PrevAndNext(
			long vocabularyId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Removes all the asset vocabularies where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset vocabularies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset vocabularies
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset vocabularies where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching asset vocabularies
	 */
	@Override
	public java.util.List<AssetVocabulary> findByCTCollectionId(
		long ctCollectionId);

	/**
	 * Returns a range of all the asset vocabularies where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByCTCollectionId(
		long ctCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset vocabularies where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByCTCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the first asset vocabulary in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByCTCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the last asset vocabulary in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByCTCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the last asset vocabulary in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByCTCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary[] findByCTCollectionId_PrevAndNext(
			long vocabularyId, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Removes all the asset vocabularies where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCTCollectionId(long ctCollectionId);

	/**
	 * Returns the number of asset vocabularies where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching asset vocabularies
	 */
	public int countByCTCollectionId(long ctCollectionId);

	/**
	 * Returns all the asset vocabularies where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByGroupId(long groupId);

	/**
	 * Returns a range of all the asset vocabularies where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset vocabularies where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the first asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the last asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the last asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where groupId = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary[] findByGroupId_PrevAndNext(
			long vocabularyId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns all the asset vocabularies that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset vocabularies that the user has permission to view
	 */
	public java.util.List<AssetVocabulary> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the asset vocabularies that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies that the user has permission to view
	 */
	public java.util.List<AssetVocabulary> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies that the user has permission to view
	 */
	public java.util.List<AssetVocabulary> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set of asset vocabularies that the user has permission to view where groupId = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary[] filterFindByGroupId_PrevAndNext(
			long vocabularyId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns all the asset vocabularies that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching asset vocabularies that the user has permission to view
	 */
	public java.util.List<AssetVocabulary> filterFindByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the asset vocabularies that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies that the user has permission to view
	 */
	public java.util.List<AssetVocabulary> filterFindByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies that the user has permission to view
	 */
	public java.util.List<AssetVocabulary> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns all the asset vocabularies where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the asset vocabularies where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset vocabularies where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset vocabularies where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of asset vocabularies where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset vocabularies
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of asset vocabularies where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching asset vocabularies
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns the number of asset vocabularies that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset vocabularies that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the number of asset vocabularies that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching asset vocabularies that the user has permission to view
	 */
	public int filterCountByGroupId(long[] groupIds);

	/**
	 * Returns all the asset vocabularies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the asset vocabularies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset vocabularies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the first asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the last asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the last asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where companyId = &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary[] findByCompanyId_PrevAndNext(
			long vocabularyId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Removes all the asset vocabularies where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of asset vocabularies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching asset vocabularies
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the asset vocabulary where groupId = &#63; and name = &#63; or throws a <code>NoSuchVocabularyException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByG_N(long groupId, String name)
		throws NoSuchVocabularyException;

	/**
	 * Returns the asset vocabulary where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByG_N(long groupId, String name);

	/**
	 * Returns the asset vocabulary where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByG_N(
		long groupId, String name, boolean useFinderCache);

	/**
	 * Removes the asset vocabulary where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the asset vocabulary that was removed
	 */
	public AssetVocabulary removeByG_N(long groupId, String name)
		throws NoSuchVocabularyException;

	/**
	 * Returns the number of asset vocabularies where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching asset vocabularies
	 */
	public int countByG_N(long groupId, String name);

	/**
	 * Returns all the asset vocabularies where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByG_LikeN(
		long groupId, String name);

	/**
	 * Returns a range of all the asset vocabularies where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByG_LikeN(
		long groupId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset vocabularies where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByG_LikeN_First(
			long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the first asset vocabulary in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByG_LikeN_First(
		long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the last asset vocabulary in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByG_LikeN_Last(
			long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns the last asset vocabulary in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByG_LikeN_Last(
		long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary[] findByG_LikeN_PrevAndNext(
			long vocabularyId, long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Returns all the asset vocabularies that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching asset vocabularies that the user has permission to view
	 */
	public java.util.List<AssetVocabulary> filterFindByG_LikeN(
		long groupId, String name);

	/**
	 * Returns a range of all the asset vocabularies that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of matching asset vocabularies that the user has permission to view
	 */
	public java.util.List<AssetVocabulary> filterFindByG_LikeN(
		long groupId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabularies that the user has permission to view
	 */
	public java.util.List<AssetVocabulary> filterFindByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns the asset vocabularies before and after the current asset vocabulary in the ordered set of asset vocabularies that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param vocabularyId the primary key of the current asset vocabulary
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary[] filterFindByG_LikeN_PrevAndNext(
			long vocabularyId, long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				orderByComparator)
		throws NoSuchVocabularyException;

	/**
	 * Removes all the asset vocabularies where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	public void removeByG_LikeN(long groupId, String name);

	/**
	 * Returns the number of asset vocabularies where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching asset vocabularies
	 */
	public int countByG_LikeN(long groupId, String name);

	/**
	 * Returns the number of asset vocabularies that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching asset vocabularies that the user has permission to view
	 */
	public int filterCountByG_LikeN(long groupId, String name);

	/**
	 * Returns the asset vocabulary where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchVocabularyException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching asset vocabulary
	 * @throws NoSuchVocabularyException if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchVocabularyException;

	/**
	 * Returns the asset vocabulary where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the asset vocabulary where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary, or <code>null</code> if a matching asset vocabulary could not be found
	 */
	public AssetVocabulary fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the asset vocabulary where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the asset vocabulary that was removed
	 */
	public AssetVocabulary removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchVocabularyException;

	/**
	 * Returns the number of asset vocabularies where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching asset vocabularies
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the asset vocabulary in the entity cache if it is enabled.
	 *
	 * @param assetVocabulary the asset vocabulary
	 */
	public void cacheResult(AssetVocabulary assetVocabulary);

	/**
	 * Caches the asset vocabularies in the entity cache if it is enabled.
	 *
	 * @param assetVocabularies the asset vocabularies
	 */
	public void cacheResult(java.util.List<AssetVocabulary> assetVocabularies);

	/**
	 * Creates a new asset vocabulary with the primary key. Does not add the asset vocabulary to the database.
	 *
	 * @param vocabularyId the primary key for the new asset vocabulary
	 * @return the new asset vocabulary
	 */
	public AssetVocabulary create(long vocabularyId);

	/**
	 * Removes the asset vocabulary with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param vocabularyId the primary key of the asset vocabulary
	 * @return the asset vocabulary that was removed
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary remove(long vocabularyId)
		throws NoSuchVocabularyException;

	public AssetVocabulary updateImpl(AssetVocabulary assetVocabulary);

	/**
	 * Returns the asset vocabulary with the primary key or throws a <code>NoSuchVocabularyException</code> if it could not be found.
	 *
	 * @param vocabularyId the primary key of the asset vocabulary
	 * @return the asset vocabulary
	 * @throws NoSuchVocabularyException if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary findByPrimaryKey(long vocabularyId)
		throws NoSuchVocabularyException;

	/**
	 * Returns the asset vocabulary with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param vocabularyId the primary key of the asset vocabulary
	 * @return the asset vocabulary, or <code>null</code> if a asset vocabulary with the primary key could not be found
	 */
	public AssetVocabulary fetchByPrimaryKey(long vocabularyId);

	/**
	 * Returns all the asset vocabularies.
	 *
	 * @return the asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findAll();

	/**
	 * Returns a range of all the asset vocabularies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @return the range of asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the asset vocabularies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset vocabularies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabularies
	 * @param end the upper bound of the range of asset vocabularies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset vocabularies
	 */
	public java.util.List<AssetVocabulary> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset vocabularies from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset vocabularies.
	 *
	 * @return the number of asset vocabularies
	 */
	public int countAll();

}