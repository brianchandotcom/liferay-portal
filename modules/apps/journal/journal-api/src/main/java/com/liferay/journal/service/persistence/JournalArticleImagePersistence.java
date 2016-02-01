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

import com.liferay.journal.exception.NoSuchArticleImageException;
import com.liferay.journal.model.JournalArticleImage;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the journal article image service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.journal.service.persistence.impl.JournalArticleImagePersistenceImpl
 * @see JournalArticleImageUtil
 * @generated
 */
@ProviderType
public interface JournalArticleImagePersistence extends BasePersistence<JournalArticleImage> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalArticleImageUtil} to access the journal article image persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the journal article images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal article images
	*/
	public java.util.List<JournalArticleImage> findByGroupId(long groupId);

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
	public java.util.List<JournalArticleImage> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<JournalArticleImage> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

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
	public java.util.List<JournalArticleImage> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image
	* @throws NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public JournalArticleImage findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException;

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public JournalArticleImage fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image
	* @throws NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public JournalArticleImage findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException;

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public JournalArticleImage fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

	/**
	* Returns the journal article images before and after the current journal article image in the ordered set where groupId = &#63;.
	*
	* @param articleImageId the primary key of the current journal article image
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article image
	* @throws NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public JournalArticleImage[] findByGroupId_PrevAndNext(
		long articleImageId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException;

	/**
	* Removes all the journal article images where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of journal article images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal article images
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the journal article images where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @return the matching journal article images
	*/
	public java.util.List<JournalArticleImage> findByTempImage(
		boolean tempImage);

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
	public java.util.List<JournalArticleImage> findByTempImage(
		boolean tempImage, int start, int end);

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
	public java.util.List<JournalArticleImage> findByTempImage(
		boolean tempImage, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

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
	public java.util.List<JournalArticleImage> findByTempImage(
		boolean tempImage, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article image in the ordered set where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image
	* @throws NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public JournalArticleImage findByTempImage_First(boolean tempImage,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException;

	/**
	* Returns the first journal article image in the ordered set where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public JournalArticleImage fetchByTempImage_First(boolean tempImage,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

	/**
	* Returns the last journal article image in the ordered set where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image
	* @throws NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public JournalArticleImage findByTempImage_Last(boolean tempImage,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException;

	/**
	* Returns the last journal article image in the ordered set where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public JournalArticleImage fetchByTempImage_Last(boolean tempImage,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

	/**
	* Returns the journal article images before and after the current journal article image in the ordered set where tempImage = &#63;.
	*
	* @param articleImageId the primary key of the current journal article image
	* @param tempImage the temp image
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article image
	* @throws NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public JournalArticleImage[] findByTempImage_PrevAndNext(
		long articleImageId, boolean tempImage,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException;

	/**
	* Removes all the journal article images where tempImage = &#63; from the database.
	*
	* @param tempImage the temp image
	*/
	public void removeByTempImage(boolean tempImage);

	/**
	* Returns the number of journal article images where tempImage = &#63;.
	*
	* @param tempImage the temp image
	* @return the number of matching journal article images
	*/
	public int countByTempImage(boolean tempImage);

	/**
	* Returns all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the matching journal article images
	*/
	public java.util.List<JournalArticleImage> findByG_A_V(long groupId,
		java.lang.String articleId, double version);

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
	public java.util.List<JournalArticleImage> findByG_A_V(long groupId,
		java.lang.String articleId, double version, int start, int end);

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
	public java.util.List<JournalArticleImage> findByG_A_V(long groupId,
		java.lang.String articleId, double version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

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
	public java.util.List<JournalArticleImage> findByG_A_V(long groupId,
		java.lang.String articleId, double version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image
	* @throws NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public JournalArticleImage findByG_A_V_First(long groupId,
		java.lang.String articleId, double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException;

	/**
	* Returns the first journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public JournalArticleImage fetchByG_A_V_First(long groupId,
		java.lang.String articleId, double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image
	* @throws NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public JournalArticleImage findByG_A_V_Last(long groupId,
		java.lang.String articleId, double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException;

	/**
	* Returns the last journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article image, or <code>null</code> if a matching journal article image could not be found
	*/
	public JournalArticleImage fetchByG_A_V_Last(long groupId,
		java.lang.String articleId, double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

	/**
	* Returns the journal article images before and after the current journal article image in the ordered set where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param articleImageId the primary key of the current journal article image
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article image
	* @throws NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public JournalArticleImage[] findByG_A_V_PrevAndNext(long articleImageId,
		long groupId, java.lang.String articleId, double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator)
		throws NoSuchArticleImageException;

	/**
	* Removes all the journal article images where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	*/
	public void removeByG_A_V(long groupId, java.lang.String articleId,
		double version);

	/**
	* Returns the number of journal article images where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the number of matching journal article images
	*/
	public int countByG_A_V(long groupId, java.lang.String articleId,
		double version);

	/**
	* Returns the journal article image where groupId = &#63; and articleId = &#63; and version = &#63; and elInstanceId = &#63; and elName = &#63; and languageId = &#63; or throws a {@link NoSuchArticleImageException} if it could not be found.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param elInstanceId the el instance ID
	* @param elName the el name
	* @param languageId the language ID
	* @return the matching journal article image
	* @throws NoSuchArticleImageException if a matching journal article image could not be found
	*/
	public JournalArticleImage findByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) throws NoSuchArticleImageException;

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
	public JournalArticleImage fetchByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId);

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
	public JournalArticleImage fetchByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId, boolean retrieveFromCache);

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
	public JournalArticleImage removeByG_A_V_E_E_L(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) throws NoSuchArticleImageException;

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
	public int countByG_A_V_E_E_L(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId);

	/**
	* Caches the journal article image in the entity cache if it is enabled.
	*
	* @param journalArticleImage the journal article image
	*/
	public void cacheResult(JournalArticleImage journalArticleImage);

	/**
	* Caches the journal article images in the entity cache if it is enabled.
	*
	* @param journalArticleImages the journal article images
	*/
	public void cacheResult(
		java.util.List<JournalArticleImage> journalArticleImages);

	/**
	* Creates a new journal article image with the primary key. Does not add the journal article image to the database.
	*
	* @param articleImageId the primary key for the new journal article image
	* @return the new journal article image
	*/
	public JournalArticleImage create(long articleImageId);

	/**
	* Removes the journal article image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image that was removed
	* @throws NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public JournalArticleImage remove(long articleImageId)
		throws NoSuchArticleImageException;

	public JournalArticleImage updateImpl(
		JournalArticleImage journalArticleImage);

	/**
	* Returns the journal article image with the primary key or throws a {@link NoSuchArticleImageException} if it could not be found.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image
	* @throws NoSuchArticleImageException if a journal article image with the primary key could not be found
	*/
	public JournalArticleImage findByPrimaryKey(long articleImageId)
		throws NoSuchArticleImageException;

	/**
	* Returns the journal article image with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param articleImageId the primary key of the journal article image
	* @return the journal article image, or <code>null</code> if a journal article image with the primary key could not be found
	*/
	public JournalArticleImage fetchByPrimaryKey(long articleImageId);

	@Override
	public java.util.Map<java.io.Serializable, JournalArticleImage> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the journal article images.
	*
	* @return the journal article images
	*/
	public java.util.List<JournalArticleImage> findAll();

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
	public java.util.List<JournalArticleImage> findAll(int start, int end);

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
	public java.util.List<JournalArticleImage> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator);

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
	public java.util.List<JournalArticleImage> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticleImage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal article images from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of journal article images.
	*
	* @return the number of journal article images
	*/
	public int countAll();
}