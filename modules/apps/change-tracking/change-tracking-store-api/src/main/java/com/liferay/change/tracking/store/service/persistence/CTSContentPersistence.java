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

package com.liferay.change.tracking.store.service.persistence;

import com.liferay.change.tracking.store.exception.NoSuchContentException;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cts content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see CTSContentUtil
 * @generated
 */
@ProviderType
public interface CTSContentPersistence
	extends BasePersistence<CTSContent>, CTPersistence<CTSContent> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTSContentUtil} to access the cts content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cts contents where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching cts contents
	 */
	@Override
	public java.util.List<CTSContent> findByCTCollectionId(long ctCollectionId);

	/**
	 * Returns a range of all the cts contents where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	public java.util.List<CTSContent> findByCTCollectionId(
		long ctCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the cts contents where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	public java.util.List<CTSContent> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cts contents where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	public java.util.List<CTSContent> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cts content in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	public CTSContent findByCTCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Returns the first cts content in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByCTCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns the last cts content in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	public CTSContent findByCTCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Returns the last cts content in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByCTCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns the cts contents before and after the current cts content in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctsContentId the primary key of the current cts content
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	public CTSContent[] findByCTCollectionId_PrevAndNext(
			long ctsContentId, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Removes all the cts contents where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCTCollectionId(long ctCollectionId);

	/**
	 * Returns the number of cts contents where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching cts contents
	 */
	public int countByCTCollectionId(long ctCollectionId);

	/**
	 * Returns all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @return the matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType);

	/**
	 * Returns a range of all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType, int start,
		int end);

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_S(
		long companyId, long repositoryId, String storeType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	public CTSContent findByC_R_S_First(
			long companyId, long repositoryId, String storeType,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByC_R_S_First(
		long companyId, long repositoryId, String storeType,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	public CTSContent findByC_R_S_Last(
			long companyId, long repositoryId, String storeType,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByC_R_S_Last(
		long companyId, long repositoryId, String storeType,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns the cts contents before and after the current cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param ctsContentId the primary key of the current cts content
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	public CTSContent[] findByC_R_S_PrevAndNext(
			long ctsContentId, long companyId, long repositoryId,
			String storeType,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Removes all the cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 */
	public void removeByC_R_S(
		long companyId, long repositoryId, String storeType);

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	public int countByC_R_S(
		long companyId, long repositoryId, String storeType);

	/**
	 * Returns all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType);

	/**
	 * Returns a range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end);

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	public CTSContent findByC_R_P_S_First(
			long companyId, long repositoryId, String path, String storeType,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByC_R_P_S_First(
		long companyId, long repositoryId, String path, String storeType,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	public CTSContent findByC_R_P_S_Last(
			long companyId, long repositoryId, String path, String storeType,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByC_R_P_S_Last(
		long companyId, long repositoryId, String path, String storeType,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns the cts contents before and after the current cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param ctsContentId the primary key of the current cts content
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	public CTSContent[] findByC_R_P_S_PrevAndNext(
			long ctsContentId, long companyId, long repositoryId, String path,
			String storeType,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Removes all the cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 */
	public void removeByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType);

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	public int countByC_R_P_S(
		long companyId, long repositoryId, String path, String storeType);

	/**
	 * Returns all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType);

	/**
	 * Returns a range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end);

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cts contents
	 */
	public java.util.List<CTSContent> findByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	public CTSContent findByC_R_LikeP_S_First(
			long companyId, long repositoryId, String path, String storeType,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Returns the first cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByC_R_LikeP_S_First(
		long companyId, long repositoryId, String path, String storeType,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	public CTSContent findByC_R_LikeP_S_Last(
			long companyId, long repositoryId, String path, String storeType,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Returns the last cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByC_R_LikeP_S_Last(
		long companyId, long repositoryId, String path, String storeType,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns the cts contents before and after the current cts content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param ctsContentId the primary key of the current cts content
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	public CTSContent[] findByC_R_LikeP_S_PrevAndNext(
			long ctsContentId, long companyId, long repositoryId, String path,
			String storeType,
			com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
				orderByComparator)
		throws NoSuchContentException;

	/**
	 * Removes all the cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 */
	public void removeByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType);

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	public int countByC_R_LikeP_S(
		long companyId, long repositoryId, String path, String storeType);

	/**
	 * Returns the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; or throws a <code>NoSuchContentException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the matching cts content
	 * @throws NoSuchContentException if a matching cts content could not be found
	 */
	public CTSContent findByC_R_P_V_S(
			long companyId, long repositoryId, String path, String version,
			String storeType)
		throws NoSuchContentException;

	/**
	 * Returns the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByC_R_P_V_S(
		long companyId, long repositoryId, String path, String version,
		String storeType);

	/**
	 * Returns the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cts content, or <code>null</code> if a matching cts content could not be found
	 */
	public CTSContent fetchByC_R_P_V_S(
		long companyId, long repositoryId, String path, String version,
		String storeType, boolean useFinderCache);

	/**
	 * Removes the cts content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the cts content that was removed
	 */
	public CTSContent removeByC_R_P_V_S(
			long companyId, long repositoryId, String path, String version,
			String storeType)
		throws NoSuchContentException;

	/**
	 * Returns the number of cts contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; and storeType = &#63;.
	 *
	 * @param companyId the company ID
	 * @param repositoryId the repository ID
	 * @param path the path
	 * @param version the version
	 * @param storeType the store type
	 * @return the number of matching cts contents
	 */
	public int countByC_R_P_V_S(
		long companyId, long repositoryId, String path, String version,
		String storeType);

	/**
	 * Caches the cts content in the entity cache if it is enabled.
	 *
	 * @param ctsContent the cts content
	 */
	public void cacheResult(CTSContent ctsContent);

	/**
	 * Caches the cts contents in the entity cache if it is enabled.
	 *
	 * @param ctsContents the cts contents
	 */
	public void cacheResult(java.util.List<CTSContent> ctsContents);

	/**
	 * Creates a new cts content with the primary key. Does not add the cts content to the database.
	 *
	 * @param ctsContentId the primary key for the new cts content
	 * @return the new cts content
	 */
	public CTSContent create(long ctsContentId);

	/**
	 * Removes the cts content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content that was removed
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	public CTSContent remove(long ctsContentId) throws NoSuchContentException;

	public CTSContent updateImpl(CTSContent ctsContent);

	/**
	 * Returns the cts content with the primary key or throws a <code>NoSuchContentException</code> if it could not be found.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content
	 * @throws NoSuchContentException if a cts content with the primary key could not be found
	 */
	public CTSContent findByPrimaryKey(long ctsContentId)
		throws NoSuchContentException;

	/**
	 * Returns the cts content with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctsContentId the primary key of the cts content
	 * @return the cts content, or <code>null</code> if a cts content with the primary key could not be found
	 */
	public CTSContent fetchByPrimaryKey(long ctsContentId);

	/**
	 * Returns all the cts contents.
	 *
	 * @return the cts contents
	 */
	public java.util.List<CTSContent> findAll();

	/**
	 * Returns a range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @return the range of cts contents
	 */
	public java.util.List<CTSContent> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cts contents
	 */
	public java.util.List<CTSContent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cts contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cts contents
	 * @param end the upper bound of the range of cts contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cts contents
	 */
	public java.util.List<CTSContent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTSContent>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cts contents from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cts contents.
	 *
	 * @return the number of cts contents
	 */
	public int countAll();

}