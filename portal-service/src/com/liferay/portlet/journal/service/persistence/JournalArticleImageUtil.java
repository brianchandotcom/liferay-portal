/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.journal.model.JournalArticleImage;

import java.util.List;

/**
 * The persistence utility for the journal article image service. This utility wraps {@link JournalArticleImagePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleImagePersistence
 * @see JournalArticleImagePersistenceImpl
 * @generated
 */
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
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<JournalArticleImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalArticleImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalArticleImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static JournalArticleImage update(
		JournalArticleImage journalArticleImage, boolean merge)
		throws SystemException {
		return getPersistence().update(journalArticleImage, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static JournalArticleImage update(
		JournalArticleImage journalArticleImage, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(journalArticleImage, merge, serviceContext);
	}

	/**
	* Caches the journal article image in the entity cache if it is enabled.
	*
	* @param journalArticleImage the journal article image
	*/
	public static void cacheResult(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage) {
		getPersistence().cacheResult(journalArticleImage);
	}

	/**
	* Caches the journal article images in the entity cache if it is enabled.
	*
	* @param journalArticleImages the journal article images
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> journalArticleImages) {
		getPersistence().cacheResult(journalArticleImages);
	}

	/**
	* Creates a new journal article image with the primary key. Does not add the journal article image to the database.
	*
	* @param articleImageId the primary key for the new journal article image
	* @return the new journal article image
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage create(
		long articleImageId) {
		return getPersistence().create(articleImageId);
	}

	/**
	* Removes the journal article image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image that was removed
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage remove(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().remove(articleImageId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage updateImpl(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(journalArticleImage, merge);
	}

	/**
	* Returns the journal article image with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchArticleImageException} if it could not be found.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage findByPrimaryKey(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByPrimaryKey(articleImageId);
	}

	/**
	* Returns the journal article image with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image, or <code>null</code> if a journal article image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage fetchByPrimaryKey(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(articleImageId);
	}

	/**
	* Returns all the journal article images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the journal article images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @return the range of matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the journal article images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a matching journal article image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a matching journal article image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the journal article images before and after the current journal article image in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param articleImageId the primary key of the current journal article image
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage[] findByGroupId_PrevAndNext(
		long articleImageId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(articleImageId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the journal article images where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @return the matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findByTempImage(
		boolean tempImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTempImage(tempImage);
	}

	/**
	* Returns a range of all the journal article images where tempImage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tempImage the temp image
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @return the range of matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findByTempImage(
		boolean tempImage, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTempImage(tempImage, start, end);
	}

	/**
	* Returns an ordered range of all the journal article images where tempImage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tempImage the temp image
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findByTempImage(
		boolean tempImage, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByTempImage(tempImage, start, end, orderByComparator);
	}

	/**
	* Returns the first journal article image in the ordered set where tempImage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a matching journal article image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage findByTempImage_First(
		boolean tempImage,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence()
				   .findByTempImage_First(tempImage, orderByComparator);
	}

	/**
	* Returns the last journal article image in the ordered set where tempImage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a matching journal article image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage findByTempImage_Last(
		boolean tempImage,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence()
				   .findByTempImage_Last(tempImage, orderByComparator);
	}

	/**
	* Returns the journal article images before and after the current journal article image in the ordered set where tempImage = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param articleImageId the primary key of the current journal article image
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage[] findByTempImage_PrevAndNext(
		long articleImageId, boolean tempImage,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence()
				   .findByTempImage_PrevAndNext(articleImageId, tempImage,
			orderByComparator);
	}

	/**
	* Returns all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findByG_A_V(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_A_V(groupId, articleId, version);
	}

	/**
	* Returns a range of all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @return the range of matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findByG_A_V(
		long groupId, java.lang.String articleId, double version, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_A_V(groupId, articleId, version, start, end);
	}

	/**
	* Returns an ordered range of all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findByG_A_V(
		long groupId, java.lang.String articleId, double version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_A_V(groupId, articleId, version, start, end,
			orderByComparator);
	}

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a matching journal article image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage findByG_A_V_First(
		long groupId, java.lang.String articleId, double version,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence()
				   .findByG_A_V_First(groupId, articleId, version,
			orderByComparator);
	}

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a matching journal article image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage findByG_A_V_Last(
		long groupId, java.lang.String articleId, double version,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence()
				   .findByG_A_V_Last(groupId, articleId, version,
			orderByComparator);
	}

	/**
	* Returns the journal article images before and after the current journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param articleImageId the primary key of the current journal article image
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a journal article image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage[] findByG_A_V_PrevAndNext(
		long articleImageId, long groupId, java.lang.String articleId,
		double version,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence()
				   .findByG_A_V_PrevAndNext(articleImageId, groupId, articleId,
			version, orderByComparator);
	}

	/**
	* Returns the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleImageException} if it could not be found.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param elInstanceId the el instance ID
	* @param elName the el name
	* @param languageId the language ID
	* @return the matching journal article image
	* @throws com.liferay.portlet.journal.NoSuchArticleImageException if a matching journal article image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage findByG_A_V_E_E_L(
		long groupId, java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage fetchByG_A_V_E_E_L(
		long groupId, java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal article image, or <code>null</code> if a matching journal article image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage fetchByG_A_V_E_E_L(
		long groupId, java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_A_V_E_E_L(groupId, articleId, version,
			elInstanceId, elName, languageId, retrieveFromCache);
	}

	/**
	* Returns all the journal article images.
	*
	* @return the journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the journal article images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @return the range of journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the journal article images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal article images
	* @param end the upper bound of the range of journal article images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the journal article images where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the journal article images where tempImage = &#63; from the database.
	*
	* @param tempImage the temp image
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByTempImage(boolean tempImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByTempImage(tempImage);
	}

	/**
	* Removes all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_A_V(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_A_V(groupId, articleId, version);
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalArticleImage removeByG_A_V_E_E_L(
		long groupId, java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence()
				   .removeByG_A_V_E_E_L(groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	/**
	* Removes all the journal article images from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of journal article images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of journal article images where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @return the number of matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByTempImage(boolean tempImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTempImage(tempImage);
	}

	/**
	* Returns the number of journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the number of matching journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_A_V(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_A_V(groupId, articleId, version);
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
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_A_V_E_E_L(groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	/**
	* Returns the number of journal article images.
	*
	* @return the number of journal article images
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static JournalArticleImagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (JournalArticleImagePersistence)PortalBeanLocatorUtil.locate(JournalArticleImagePersistence.class.getName());

			ReferenceRegistry.registerReference(JournalArticleImageUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(JournalArticleImagePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(JournalArticleImageUtil.class,
			"_persistence");
	}

	private static JournalArticleImagePersistence _persistence;
}