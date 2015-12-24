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

package com.liferay.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.model.JournalArticleImage;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the journal article image service. This utility wraps {@link com.liferay.journal.service.persistence.impl.JournalArticleImagePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleImagePersistence
 * @see com.liferay.journal.service.persistence.impl.JournalArticleImagePersistenceImpl
 * @generated
 */
@ProviderType
public class JournalArticleImageUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(JournalArticleImage journalArticleImage) {
		getPersistence().clearCache(journalArticleImage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<JournalArticleImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalArticleImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalArticleImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static JournalArticleImage update(
		JournalArticleImage journalArticleImage) {
		return getPersistence().update(journalArticleImage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static JournalArticleImage update(
		JournalArticleImage journalArticleImage, ServiceContext serviceContext) {
		return getPersistence().update(journalArticleImage, serviceContext);
	}

	/**
	* Returns all the journal article images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal article images
	*/
	public static List<JournalArticleImage> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the journal article images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @return the range of matching journal article images
	*/
	public static List<JournalArticleImage> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the journal article images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article images
	*/
	public static List<JournalArticleImage> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal article images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal article images
	*/
	public static List<JournalArticleImage> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public static JournalArticleImage findByGroupId_First(long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public static JournalArticleImage fetchByGroupId_First(long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public static JournalArticleImage findByGroupId_Last(long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public static JournalArticleImage fetchByGroupId_Last(long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the journal article images before and after the current journal article image in the ordered set where groupId = &#63;.
	*
	* @param articleImageId the primary key of the current journal article image
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public static JournalArticleImage[] findByGroupId_PrevAndNext(
		long articleImageId, long groupId,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(articleImageId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the journal article images where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of journal article images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal article images
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the journal article images where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @return the matching journal article images
	*/
	public static List<JournalArticleImage> findByTempImage(boolean tempImage) {
		return getPersistence().findByTempImage(tempImage);
	}

	/**
	* Returns a range of all the journal article images where tempImage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tempImage the temp image
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @return the range of matching journal article images
	*/
	public static List<JournalArticleImage> findByTempImage(boolean tempImage,
		int start, int end) {
		return getPersistence().findByTempImage(tempImage, start, end);
	}

	/**
	* Returns an ordered range of all the journal article images where tempImage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tempImage the temp image
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article images
	*/
	public static List<JournalArticleImage> findByTempImage(boolean tempImage,
		int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence()
				   .findByTempImage(tempImage, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal article images where tempImage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tempImage the temp image
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal article images
	*/
	public static List<JournalArticleImage> findByTempImage(boolean tempImage,
		int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByTempImage(tempImage, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first journal article image in the ordered set where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public static JournalArticleImage findByTempImage_First(boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence()
				   .findByTempImage_First(tempImage, orderByComparator);
	}

	/**
	* Returns the first journal article image in the ordered set where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public static JournalArticleImage fetchByTempImage_First(
		boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence()
				   .fetchByTempImage_First(tempImage, orderByComparator);
	}

	/**
	* Returns the last journal article image in the ordered set where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public static JournalArticleImage findByTempImage_Last(boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence()
				   .findByTempImage_Last(tempImage, orderByComparator);
	}

	/**
	* Returns the last journal article image in the ordered set where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public static JournalArticleImage fetchByTempImage_Last(boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence()
				   .fetchByTempImage_Last(tempImage, orderByComparator);
	}

	/**
	* Returns the journal article images before and after the current journal article image in the ordered set where tempImage = &#63;.
	*
	* @param articleImageId the primary key of the current journal article image
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public static JournalArticleImage[] findByTempImage_PrevAndNext(
		long articleImageId, boolean tempImage,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence()
				   .findByTempImage_PrevAndNext(articleImageId, tempImage,
			orderByComparator);
	}

	/**
	* Removes all the journal article images where tempImage = &#63; from the database.
	*
	* @param tempImage the temp image
	*/
	public static void removeByTempImage(boolean tempImage) {
		getPersistence().removeByTempImage(tempImage);
	}

	/**
	* Returns the number of journal article images where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @return the number of matching journal article images
	*/
	public static int countByTempImage(boolean tempImage) {
		return getPersistence().countByTempImage(tempImage);
	}

	/**
	* Returns all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the matching journal article images
	*/
	public static List<JournalArticleImage> findByG_A_V(long groupId,
		java.lang.String articleId, double version) {
		return getPersistence().findByG_A_V(groupId, articleId, version);
	}

	/**
	* Returns a range of all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @return the range of matching journal article images
	*/
	public static List<JournalArticleImage> findByG_A_V(long groupId,
		java.lang.String articleId, double version, int start, int end) {
		return getPersistence()
				   .findByG_A_V(groupId, articleId, version, start, end);
	}

	/**
	* Returns an ordered range of all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article images
	*/
	public static List<JournalArticleImage> findByG_A_V(long groupId,
		java.lang.String articleId, double version, int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence()
				   .findByG_A_V(groupId, articleId, version, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal article images
	*/
	public static List<JournalArticleImage> findByG_A_V(long groupId,
		java.lang.String articleId, double version, int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A_V(groupId, articleId, version, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public static JournalArticleImage findByG_A_V_First(long groupId,
		java.lang.String articleId, double version,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence()
				   .findByG_A_V_First(groupId, articleId, version,
			orderByComparator);
	}

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public static JournalArticleImage fetchByG_A_V_First(long groupId,
		java.lang.String articleId, double version,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_V_First(groupId, articleId, version,
			orderByComparator);
	}

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public static JournalArticleImage findByG_A_V_Last(long groupId,
		java.lang.String articleId, double version,
		OrderByComparator<JournalArticleImage> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence()
				   .findByG_A_V_Last(groupId, articleId, version,
			orderByComparator);
	}

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public static JournalArticleImage fetchByG_A_V_Last(long groupId,
		java.lang.String articleId, double version,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_V_Last(groupId, articleId, version,
			orderByComparator);
	}

	/**
	* Returns the journal article images before and after the current journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param articleImageId the primary key of the current journal article image
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public static JournalArticleImage[] findByG_A_V_PrevAndNext(
		long articleImageId, long groupId, java.lang.String articleId,
		double version, OrderByComparator<JournalArticleImage> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence()
				   .findByG_A_V_PrevAndNext(articleImageId, groupId, articleId,
			version, orderByComparator);
	}

	/**
	* Removes all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	*/
	public static void removeByG_A_V(long groupId, java.lang.String articleId,
		double version) {
		getPersistence().removeByG_A_V(groupId, articleId, version);
	}

	/**
	* Returns the number of journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the number of matching journal article images
	*/
	public static int countByG_A_V(long groupId, java.lang.String articleId,
		double version) {
		return getPersistence().countByG_A_V(groupId, articleId, version);
	}

	/**
	* Returns the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; or throws a {@link com.liferay.journal.NoSuchArticleImageException} if it could not be found.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param elInstanceId the el instance ID
	* @param elName the el name
	* @param languageId the language ID
	* @return the matching journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public static JournalArticleImage findByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence()
				   .findByG_A_V_E_E_L(groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	/**
	* Returns the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param elInstanceId the el instance ID
	* @param elName the el name
	* @param languageId the language ID
	* @return the matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public static JournalArticleImage fetchByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) {
		return getPersistence()
				   .fetchByG_A_V_E_E_L(groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	/**
	* Returns the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param elInstanceId the el instance ID
	* @param elName the el name
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public static JournalArticleImage fetchByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_A_V_E_E_L(groupId, articleId, version,
			elInstanceId, elName, languageId, retrieveFromCache);
	}

	/**
	* Removes the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param elInstanceId the el instance ID
	* @param elName the el name
	* @param languageId the language ID
	* @return the journal article image that was removed
	*/
	public static JournalArticleImage removeByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence()
				   .removeByG_A_V_E_E_L(groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	/**
	* Returns the number of journal article images where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param elInstanceId the el instance ID
	* @param elName the el name
	* @param languageId the language ID
	* @return the number of matching journal article images
	*/
	public static int countByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) {
		return getPersistence()
				   .countByG_A_V_E_E_L(groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	/**
	* Caches the journal article image in the entity cache if it is enabled.
	*
	* @param journalArticleImage the journal article image
	*/
	public static void cacheResult(JournalArticleImage journalArticleImage) {
		getPersistence().cacheResult(journalArticleImage);
	}

	/**
	* Caches the journal article images in the entity cache if it is enabled.
	*
	* @param journalArticleImages the journal article images
	*/
	public static void cacheResult(
		List<JournalArticleImage> journalArticleImages) {
		getPersistence().cacheResult(journalArticleImages);
	}

	/**
	* Creates a new journal article image with the primary key. Does not add the journal article image to the database.
	*
	* @param articleImageId the primary key for the new journal article image
	* @return the new journal article image
	*/
	public static JournalArticleImage create(long articleImageId) {
		return getPersistence().create(articleImageId);
	}

	/**
	* Removes the journal article image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image that was removed
	* @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public static JournalArticleImage remove(long articleImageId)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence().remove(articleImageId);
	}

	public static JournalArticleImage updateImpl(
		JournalArticleImage journalArticleImage) {
		return getPersistence().updateImpl(journalArticleImage);
	}

	/**
	* Returns the journal article image with the primary key or throws a {@link com.liferay.journal.NoSuchArticleImageException} if it could not be found.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image
	* @throws com.liferay.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public static JournalArticleImage findByPrimaryKey(long articleImageId)
		throws com.liferay.journal.exception.NoSuchArticleImageException {
		return getPersistence().findByPrimaryKey(articleImageId);
	}

	/**
	* Returns the journal article image with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image, or <code>null</code> if a journal article image with the primary key could not be found
	*/
	public static JournalArticleImage fetchByPrimaryKey(long articleImageId) {
		return getPersistence().fetchByPrimaryKey(articleImageId);
	}

	public static java.util.Map<java.io.Serializable, JournalArticleImage> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the journal article images.
	*
	* @return the journal article images
	*/
	public static List<JournalArticleImage> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the journal article images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @return the range of journal article images
	*/
	public static List<JournalArticleImage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the journal article images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal article images
	*/
	public static List<JournalArticleImage> findAll(int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal article images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of journal article images
	*/
	public static List<JournalArticleImage> findAll(int start, int end,
		OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the journal article images from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of journal article images.
	*
	* @return the number of journal article images
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static JournalArticleImagePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<JournalArticleImagePersistence, JournalArticleImagePersistence> _serviceTracker =
		ServiceTrackerFactory.open(JournalArticleImagePersistence.class);
}