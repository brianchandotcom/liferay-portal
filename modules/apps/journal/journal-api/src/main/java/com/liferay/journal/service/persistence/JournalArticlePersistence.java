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

import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;

import com.liferay.portal.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the journal article service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.journal.service.persistence.impl.JournalArticlePersistenceImpl
 * @see JournalArticleUtil
 * @generated
 */
@ProviderType
public interface JournalArticlePersistence extends BasePersistence<JournalArticle> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalArticleUtil} to access the journal article persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the journal articles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the journal articles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the journal articles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where uuid = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByUuid_PrevAndNext(long id,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of journal articles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal articles
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the journal article where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchArticleException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchArticleException;

	/**
	* Returns the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the journal article where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal article that was removed
	*/
	public JournalArticle removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchArticleException;

	/**
	* Returns the number of journal articles where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal articles
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the journal articles where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the journal articles where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByUuid_C_PrevAndNext(long id,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of journal articles where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching journal articles
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the journal articles where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey);

	/**
	* Returns a range of all the journal articles where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByResourcePrimKey_First(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByResourcePrimKey_First(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByResourcePrimKey_Last(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByResourcePrimKey_Last(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByResourcePrimKey_PrevAndNext(long id,
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	*/
	public void removeByResourcePrimKey(long resourcePrimKey);

	/**
	* Returns the number of journal articles where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the number of matching journal articles
	*/
	public int countByResourcePrimKey(long resourcePrimKey);

	/**
	* Returns all the journal articles where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByGroupId(long groupId);

	/**
	* Returns a range of all the journal articles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByGroupId_PrevAndNext(long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByGroupId_PrevAndNext(long id,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of journal articles where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal articles
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the journal articles where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByCompanyId(long companyId);

	/**
	* Returns a range of all the journal articles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByCompanyId_PrevAndNext(long id,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of journal articles where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching journal articles
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the journal articles where DDMStructureKey = &#63;.
	*
	* @param DDMStructureKey the d d m structure key
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMStructureKey(
		java.lang.String DDMStructureKey);

	/**
	* Returns a range of all the journal articles where DDMStructureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMStructureKey the d d m structure key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMStructureKey(
		java.lang.String DDMStructureKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where DDMStructureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMStructureKey the d d m structure key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMStructureKey(
		java.lang.String DDMStructureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where DDMStructureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMStructureKey the d d m structure key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMStructureKey(
		java.lang.String DDMStructureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where DDMStructureKey = &#63;.
	*
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByDDMStructureKey_First(
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where DDMStructureKey = &#63;.
	*
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByDDMStructureKey_First(
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where DDMStructureKey = &#63;.
	*
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByDDMStructureKey_Last(
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where DDMStructureKey = &#63;.
	*
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByDDMStructureKey_Last(
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where DDMStructureKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByDDMStructureKey_PrevAndNext(long id,
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles where DDMStructureKey = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMStructureKeies the d d m structure keies
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMStructureKey(
		java.lang.String[] DDMStructureKeies);

	/**
	* Returns a range of all the journal articles where DDMStructureKey = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMStructureKeies the d d m structure keies
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMStructureKey(
		java.lang.String[] DDMStructureKeies, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where DDMStructureKey = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMStructureKeies the d d m structure keies
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMStructureKey(
		java.lang.String[] DDMStructureKeies, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where DDMStructureKey = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMStructureKey the d d m structure key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMStructureKey(
		java.lang.String[] DDMStructureKeies, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal articles where DDMStructureKey = &#63; from the database.
	*
	* @param DDMStructureKey the d d m structure key
	*/
	public void removeByDDMStructureKey(java.lang.String DDMStructureKey);

	/**
	* Returns the number of journal articles where DDMStructureKey = &#63;.
	*
	* @param DDMStructureKey the d d m structure key
	* @return the number of matching journal articles
	*/
	public int countByDDMStructureKey(java.lang.String DDMStructureKey);

	/**
	* Returns the number of journal articles where DDMStructureKey = any &#63;.
	*
	* @param DDMStructureKeies the d d m structure keies
	* @return the number of matching journal articles
	*/
	public int countByDDMStructureKey(java.lang.String[] DDMStructureKeies);

	/**
	* Returns all the journal articles where DDMTemplateKey = &#63;.
	*
	* @param DDMTemplateKey the d d m template key
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMTemplateKey(
		java.lang.String DDMTemplateKey);

	/**
	* Returns a range of all the journal articles where DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMTemplateKey(
		java.lang.String DDMTemplateKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMTemplateKey(
		java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByDDMTemplateKey(
		java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where DDMTemplateKey = &#63;.
	*
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByDDMTemplateKey_First(
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where DDMTemplateKey = &#63;.
	*
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByDDMTemplateKey_First(
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where DDMTemplateKey = &#63;.
	*
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByDDMTemplateKey_Last(
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where DDMTemplateKey = &#63;.
	*
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByDDMTemplateKey_Last(
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where DDMTemplateKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByDDMTemplateKey_PrevAndNext(long id,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where DDMTemplateKey = &#63; from the database.
	*
	* @param DDMTemplateKey the d d m template key
	*/
	public void removeByDDMTemplateKey(java.lang.String DDMTemplateKey);

	/**
	* Returns the number of journal articles where DDMTemplateKey = &#63;.
	*
	* @param DDMTemplateKey the d d m template key
	* @return the number of matching journal articles
	*/
	public int countByDDMTemplateKey(java.lang.String DDMTemplateKey);

	/**
	* Returns all the journal articles where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByLayoutUuid(
		java.lang.String layoutUuid);

	/**
	* Returns a range of all the journal articles where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByLayoutUuid_First(java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByLayoutUuid_First(java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByLayoutUuid_Last(java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByLayoutUuid_Last(java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where layoutUuid = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByLayoutUuid_PrevAndNext(long id,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where layoutUuid = &#63; from the database.
	*
	* @param layoutUuid the layout uuid
	*/
	public void removeByLayoutUuid(java.lang.String layoutUuid);

	/**
	* Returns the number of journal articles where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @return the number of matching journal articles
	*/
	public int countByLayoutUuid(java.lang.String layoutUuid);

	/**
	* Returns all the journal articles where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findBySmallImageId(long smallImageId);

	/**
	* Returns a range of all the journal articles where smallImageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param smallImageId the small image ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where smallImageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param smallImageId the small image ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where smallImageId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param smallImageId the small image ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findBySmallImageId_First(long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchBySmallImageId_First(long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findBySmallImageId_Last(long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchBySmallImageId_Last(long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where smallImageId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param smallImageId the small image ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findBySmallImageId_PrevAndNext(long id,
		long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID
	*/
	public void removeBySmallImageId(long smallImageId);

	/**
	* Returns the number of journal articles where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @return the number of matching journal articles
	*/
	public int countBySmallImageId(long smallImageId);

	/**
	* Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I(long resourcePrimKey,
		boolean indexable);

	/**
	* Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I(long resourcePrimKey,
		boolean indexable, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I(long resourcePrimKey,
		boolean indexable, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I(long resourcePrimKey,
		boolean indexable, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByR_I_First(long resourcePrimKey,
		boolean indexable,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByR_I_First(long resourcePrimKey,
		boolean indexable,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByR_I_Last(long resourcePrimKey,
		boolean indexable,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByR_I_Last(long resourcePrimKey,
		boolean indexable,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByR_I_PrevAndNext(long id,
		long resourcePrimKey, boolean indexable,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where resourcePrimKey = &#63; and indexable = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	*/
	public void removeByR_I(long resourcePrimKey, boolean indexable);

	/**
	* Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @return the number of matching journal articles
	*/
	public int countByR_I(long resourcePrimKey, boolean indexable);

	/**
	* Returns all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_ST(long resourcePrimKey,
		int status);

	/**
	* Returns a range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_ST(long resourcePrimKey,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_ST(long resourcePrimKey,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_ST(long resourcePrimKey,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByR_ST_First(long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByR_ST_First(long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByR_ST_Last(long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByR_ST_Last(long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByR_ST_PrevAndNext(long id,
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param statuses the statuses
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_ST(long resourcePrimKey,
		int[] statuses);

	/**
	* Returns a range of all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_ST(long resourcePrimKey,
		int[] statuses, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_ST(long resourcePrimKey,
		int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_ST(long resourcePrimKey,
		int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal articles where resourcePrimKey = &#63; and status = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	*/
	public void removeByR_ST(long resourcePrimKey, int status);

	/**
	* Returns the number of journal articles where resourcePrimKey = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByR_ST(long resourcePrimKey, int status);

	/**
	* Returns the number of journal articles where resourcePrimKey = &#63; and status = any &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param statuses the statuses
	* @return the number of matching journal articles
	*/
	public int countByR_ST(long resourcePrimKey, int[] statuses);

	/**
	* Returns all the journal articles where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_U(long groupId, long userId);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_U(long groupId, long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_U(long groupId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_U(long groupId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_U_First(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_U_First(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_U_PrevAndNext(long id, long groupId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_U(long groupId,
		long userId);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_U(long groupId,
		long userId, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_U(long groupId,
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_U_PrevAndNext(long id, long groupId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	*/
	public void removeByG_U(long groupId, long userId);

	/**
	* Returns the number of journal articles where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching journal articles
	*/
	public int countByG_U(long groupId, long userId);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_U(long groupId, long userId);

	/**
	* Returns all the journal articles where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F(long groupId, long folderId);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F(long groupId,
		long folderId, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F(long groupId,
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F(long groupId,
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_F_First(long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_F_First(long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_F_Last(long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_F_Last(long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_F_PrevAndNext(long id, long groupId,
		long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F(long groupId,
		long folderId);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F(long groupId,
		long folderId, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F(long groupId,
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_F_PrevAndNext(long id, long groupId,
		long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F(long groupId,
		long[] folderIds);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F(long groupId,
		long[] folderIds, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F(long groupId,
		long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns all the journal articles where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F(long groupId,
		long[] folderIds);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F(long groupId,
		long[] folderIds, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F(long groupId,
		long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F(long groupId,
		long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal articles where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	*/
	public void removeByG_F(long groupId, long folderId);

	/**
	* Returns the number of journal articles where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching journal articles
	*/
	public int countByG_F(long groupId, long folderId);

	/**
	* Returns the number of journal articles where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching journal articles
	*/
	public int countByG_F(long groupId, long[] folderIds);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_F(long groupId, long folderId);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_F(long groupId, long[] folderIds);

	/**
	* Returns all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A(long groupId,
		java.lang.String articleId);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A(long groupId,
		java.lang.String articleId, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A(long groupId,
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A(long groupId,
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_A_First(long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_A_First(long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_A_Last(long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_A_Last(long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_A_PrevAndNext(long id, long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A(long groupId,
		java.lang.String articleId);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A(long groupId,
		java.lang.String articleId, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A(long groupId,
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_A_PrevAndNext(long id, long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	*/
	public void removeByG_A(long groupId, java.lang.String articleId);

	/**
	* Returns the number of journal articles where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the number of matching journal articles
	*/
	public int countByG_A(long groupId, java.lang.String articleId);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_A(long groupId, java.lang.String articleId);

	/**
	* Returns all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_UT(long groupId,
		java.lang.String urlTitle);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_UT(long groupId,
		java.lang.String urlTitle, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_UT(long groupId,
		java.lang.String urlTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_UT(long groupId,
		java.lang.String urlTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_UT_First(long groupId,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_UT_First(long groupId,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_UT_Last(long groupId,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_UT_Last(long groupId,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_UT_PrevAndNext(long id, long groupId,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_UT(long groupId,
		java.lang.String urlTitle);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_UT(long groupId,
		java.lang.String urlTitle, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_UT(long groupId,
		java.lang.String urlTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_UT_PrevAndNext(long id, long groupId,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	*/
	public void removeByG_UT(long groupId, java.lang.String urlTitle);

	/**
	* Returns the number of journal articles where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the number of matching journal articles
	*/
	public int countByG_UT(long groupId, java.lang.String urlTitle);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_UT(long groupId, java.lang.String urlTitle);

	/**
	* Returns all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_DDMSK(long groupId,
		java.lang.String DDMStructureKey);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_DDMSK(long groupId,
		java.lang.String DDMStructureKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_DDMSK(long groupId,
		java.lang.String DDMStructureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_DDMSK(long groupId,
		java.lang.String DDMStructureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_DDMSK_First(long groupId,
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_DDMSK_First(long groupId,
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_DDMSK_Last(long groupId,
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_DDMSK_Last(long groupId,
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_DDMSK_PrevAndNext(long id, long groupId,
		java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_DDMSK(long groupId,
		java.lang.String DDMStructureKey);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_DDMSK(long groupId,
		java.lang.String DDMStructureKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_DDMSK(long groupId,
		java.lang.String DDMStructureKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_DDMSK_PrevAndNext(long id,
		long groupId, java.lang.String DDMStructureKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and DDMStructureKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	*/
	public void removeByG_DDMSK(long groupId, java.lang.String DDMStructureKey);

	/**
	* Returns the number of journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @return the number of matching journal articles
	*/
	public int countByG_DDMSK(long groupId, java.lang.String DDMStructureKey);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMStructureKey the d d m structure key
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_DDMSK(long groupId,
		java.lang.String DDMStructureKey);

	/**
	* Returns all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_DDMTK(long groupId,
		java.lang.String DDMTemplateKey);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_DDMTK(long groupId,
		java.lang.String DDMTemplateKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_DDMTK(long groupId,
		java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_DDMTK(long groupId,
		java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_DDMTK_First(long groupId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_DDMTK_First(long groupId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_DDMTK_Last(long groupId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_DDMTK_Last(long groupId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_DDMTK_PrevAndNext(long id, long groupId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_DDMTK(long groupId,
		java.lang.String DDMTemplateKey);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_DDMTK(long groupId,
		java.lang.String DDMTemplateKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_DDMTK(long groupId,
		java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_DDMTK_PrevAndNext(long id,
		long groupId, java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and DDMTemplateKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	*/
	public void removeByG_DDMTK(long groupId, java.lang.String DDMTemplateKey);

	/**
	* Returns the number of journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @return the number of matching journal articles
	*/
	public int countByG_DDMTK(long groupId, java.lang.String DDMTemplateKey);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param DDMTemplateKey the d d m template key
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_DDMTK(long groupId,
		java.lang.String DDMTemplateKey);

	/**
	* Returns all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_L(long groupId,
		java.lang.String layoutUuid);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_L(long groupId,
		java.lang.String layoutUuid, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_L(long groupId,
		java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_L(long groupId,
		java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_L_First(long groupId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_L_First(long groupId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_L_Last(long groupId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_L_Last(long groupId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_L_PrevAndNext(long id, long groupId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_L(long groupId,
		java.lang.String layoutUuid);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_L(long groupId,
		java.lang.String layoutUuid, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_L(long groupId,
		java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_L_PrevAndNext(long id, long groupId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and layoutUuid = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	*/
	public void removeByG_L(long groupId, java.lang.String layoutUuid);

	/**
	* Returns the number of journal articles where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @return the number of matching journal articles
	*/
	public int countByG_L(long groupId, java.lang.String layoutUuid);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_L(long groupId, java.lang.String layoutUuid);

	/**
	* Returns all the journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_ST(long groupId, int status);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_ST(long groupId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_ST(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_ST(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_ST_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_ST_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_ST_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_ST_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_ST_PrevAndNext(long id, long groupId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_ST(long groupId,
		int status);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_ST(long groupId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_ST(long groupId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_ST_PrevAndNext(long id, long groupId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_ST(long groupId, int status);

	/**
	* Returns the number of journal articles where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByG_ST(long groupId, int status);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_ST(long groupId, int status);

	/**
	* Returns all the journal articles where companyId = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_V(long companyId,
		double version);

	/**
	* Returns a range of all the journal articles where companyId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param version the version
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_V(long companyId,
		double version, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param version the version
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_V(long companyId,
		double version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param version the version
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_V(long companyId,
		double version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where companyId = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_V_First(long companyId, double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where companyId = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_V_First(long companyId, double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where companyId = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_V_Last(long companyId, double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where companyId = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_V_Last(long companyId, double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and version = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param companyId the company ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByC_V_PrevAndNext(long id, long companyId,
		double version,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where companyId = &#63; and version = &#63; from the database.
	*
	* @param companyId the company ID
	* @param version the version
	*/
	public void removeByC_V(long companyId, double version);

	/**
	* Returns the number of journal articles where companyId = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @return the number of matching journal articles
	*/
	public int countByC_V(long companyId, double version);

	/**
	* Returns all the journal articles where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_ST(long companyId, int status);

	/**
	* Returns a range of all the journal articles where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_ST(long companyId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_ST(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_ST(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_ST_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_ST_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_ST_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_ST_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByC_ST_PrevAndNext(long id, long companyId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_ST(long companyId, int status);

	/**
	* Returns the number of journal articles where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByC_ST(long companyId, int status);

	/**
	* Returns all the journal articles where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_NotST(long companyId,
		int status);

	/**
	* Returns a range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_NotST(long companyId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_NotST(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_NotST(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_NotST_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_NotST_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_NotST_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_NotST_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param id the primary key of the current journal article
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByC_NotST_PrevAndNext(long id, long companyId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_NotST(long companyId, int status);

	/**
	* Returns the number of journal articles where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByC_NotST(long companyId, int status);

	/**
	* Returns all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_DDMTK(long classNameId,
		java.lang.String DDMTemplateKey);

	/**
	* Returns a range of all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_DDMTK(long classNameId,
		java.lang.String DDMTemplateKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_DDMTK(long classNameId,
		java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_DDMTK(long classNameId,
		java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_DDMTK_First(long classNameId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_DDMTK_First(long classNameId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_DDMTK_Last(long classNameId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_DDMTK_Last(long classNameId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByC_DDMTK_PrevAndNext(long id,
		long classNameId, java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	*/
	public void removeByC_DDMTK(long classNameId,
		java.lang.String DDMTemplateKey);

	/**
	* Returns the number of journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @return the number of matching journal articles
	*/
	public int countByC_DDMTK(long classNameId, java.lang.String DDMTemplateKey);

	/**
	* Returns all the journal articles where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByLtD_S(Date displayDate,
		int status);

	/**
	* Returns a range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByLtD_S(Date displayDate,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByLtD_S(Date displayDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByLtD_S(Date displayDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByLtD_S_PrevAndNext(long id, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByLtD_S(Date displayDate, int status);

	/**
	* Returns the number of journal articles where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByLtD_S(Date displayDate, int status);

	/**
	* Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int status);

	/**
	* Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByR_I_S_First(long resourcePrimKey,
		boolean indexable, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByR_I_S_First(long resourcePrimKey,
		boolean indexable, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByR_I_S_Last(long resourcePrimKey,
		boolean indexable, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByR_I_S_Last(long resourcePrimKey,
		boolean indexable, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByR_I_S_PrevAndNext(long id,
		long resourcePrimKey, boolean indexable, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param statuses the statuses
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int[] statuses);

	/**
	* Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int[] statuses, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	*/
	public void removeByR_I_S(long resourcePrimKey, boolean indexable,
		int status);

	/**
	* Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByR_I_S(long resourcePrimKey, boolean indexable, int status);

	/**
	* Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param indexable the indexable
	* @param statuses the statuses
	* @return the number of matching journal articles
	*/
	public int countByR_I_S(long resourcePrimKey, boolean indexable,
		int[] statuses);

	/**
	* Returns all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_U_C(long groupId,
		long userId, long classNameId);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_U_C(long groupId,
		long userId, long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_U_C(long groupId,
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_U_C(long groupId,
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_U_C_First(long groupId, long userId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_U_C_First(long groupId, long userId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_U_C_Last(long groupId, long userId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_U_C_Last(long groupId, long userId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_U_C_PrevAndNext(long id, long groupId,
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_U_C(long groupId,
		long userId, long classNameId);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_U_C(long groupId,
		long userId, long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_U_C(long groupId,
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_U_C_PrevAndNext(long id,
		long groupId, long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	*/
	public void removeByG_U_C(long groupId, long userId, long classNameId);

	/**
	* Returns the number of journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @return the number of matching journal articles
	*/
	public int countByG_U_C(long groupId, long userId, long classNameId);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_U_C(long groupId, long userId, long classNameId);

	/**
	* Returns all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F_ST(long groupId,
		long folderId, int status);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F_ST(long groupId,
		long folderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F_ST(long groupId,
		long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F_ST(long groupId,
		long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_F_ST_First(long groupId, long folderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_F_ST_First(long groupId, long folderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_F_ST_Last(long groupId, long folderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_F_ST_Last(long groupId, long folderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_F_ST_PrevAndNext(long id, long groupId,
		long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F_ST(long groupId,
		long folderId, int status);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F_ST(long groupId,
		long folderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F_ST(long groupId,
		long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_F_ST_PrevAndNext(long id,
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param statuses the statuses
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F_ST(long groupId,
		long folderId, int[] statuses);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F_ST(long groupId,
		long folderId, int[] statuses, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_F_ST(long groupId,
		long folderId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param statuses the statuses
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F_ST(long groupId,
		long folderId, int[] statuses);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F_ST(long groupId,
		long folderId, int[] statuses, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F_ST(long groupId,
		long folderId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_F_ST(long groupId,
		long folderId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	*/
	public void removeByG_F_ST(long groupId, long folderId, int status);

	/**
	* Returns the number of journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByG_F_ST(long groupId, long folderId, int status);

	/**
	* Returns the number of journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param statuses the statuses
	* @return the number of matching journal articles
	*/
	public int countByG_F_ST(long groupId, long folderId, int[] statuses);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_F_ST(long groupId, long folderId, int status);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param statuses the statuses
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_F_ST(long groupId, long folderId, int[] statuses);

	/**
	* Returns all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_C(long groupId,
		long classNameId, long classPK);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_C_C_PrevAndNext(long id, long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_C_C(long groupId,
		long classNameId, long classPK);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_C_C_PrevAndNext(long id,
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching journal articles
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; or throws a {@link NoSuchArticleException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMStructureKey the d d m structure key
	* @return the matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_C_DDMSK(long groupId, long classNameId,
		java.lang.String DDMStructureKey) throws NoSuchArticleException;

	/**
	* Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMStructureKey the d d m structure key
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_C_DDMSK(long groupId, long classNameId,
		java.lang.String DDMStructureKey);

	/**
	* Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMStructureKey the d d m structure key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_C_DDMSK(long groupId, long classNameId,
		java.lang.String DDMStructureKey, boolean retrieveFromCache);

	/**
	* Removes the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMStructureKey the d d m structure key
	* @return the journal article that was removed
	*/
	public JournalArticle removeByG_C_DDMSK(long groupId, long classNameId,
		java.lang.String DDMStructureKey) throws NoSuchArticleException;

	/**
	* Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMStructureKey the d d m structure key
	* @return the number of matching journal articles
	*/
	public int countByG_C_DDMSK(long groupId, long classNameId,
		java.lang.String DDMStructureKey);

	/**
	* Returns all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_DDMTK(long groupId,
		long classNameId, java.lang.String DDMTemplateKey);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_DDMTK(long groupId,
		long classNameId, java.lang.String DDMTemplateKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_DDMTK(long groupId,
		long classNameId, java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_DDMTK(long groupId,
		long classNameId, java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_C_DDMTK_First(long groupId, long classNameId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_C_DDMTK_First(long groupId,
		long classNameId, java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_C_DDMTK_Last(long groupId, long classNameId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_C_DDMTK_Last(long groupId, long classNameId,
		java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_C_DDMTK_PrevAndNext(long id, long groupId,
		long classNameId, java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_C_DDMTK(long groupId,
		long classNameId, java.lang.String DDMTemplateKey);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_C_DDMTK(long groupId,
		long classNameId, java.lang.String DDMTemplateKey, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_C_DDMTK(long groupId,
		long classNameId, java.lang.String DDMTemplateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_C_DDMTK_PrevAndNext(long id,
		long groupId, long classNameId, java.lang.String DDMTemplateKey,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	*/
	public void removeByG_C_DDMTK(long groupId, long classNameId,
		java.lang.String DDMTemplateKey);

	/**
	* Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @return the number of matching journal articles
	*/
	public int countByG_C_DDMTK(long groupId, long classNameId,
		java.lang.String DDMTemplateKey);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param DDMTemplateKey the d d m template key
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_C_DDMTK(long groupId, long classNameId,
		java.lang.String DDMTemplateKey);

	/**
	* Returns all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_L(long groupId,
		long classNameId, java.lang.String layoutUuid);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_L(long groupId,
		long classNameId, java.lang.String layoutUuid, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_L(long groupId,
		long classNameId, java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_C_L(long groupId,
		long classNameId, java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_C_L_First(long groupId, long classNameId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_C_L_First(long groupId, long classNameId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_C_L_Last(long groupId, long classNameId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_C_L_Last(long groupId, long classNameId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_C_L_PrevAndNext(long id, long groupId,
		long classNameId, java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_C_L(long groupId,
		long classNameId, java.lang.String layoutUuid);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_C_L(long groupId,
		long classNameId, java.lang.String layoutUuid, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_C_L(long groupId,
		long classNameId, java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_C_L_PrevAndNext(long id,
		long groupId, long classNameId, java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	*/
	public void removeByG_C_L(long groupId, long classNameId,
		java.lang.String layoutUuid);

	/**
	* Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @return the number of matching journal articles
	*/
	public int countByG_C_L(long groupId, long classNameId,
		java.lang.String layoutUuid);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param layoutUuid the layout uuid
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_C_L(long groupId, long classNameId,
		java.lang.String layoutUuid);

	/**
	* Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or throws a {@link NoSuchArticleException} if it could not be found.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_A_V(long groupId, java.lang.String articleId,
		double version) throws NoSuchArticleException;

	/**
	* Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_A_V(long groupId,
		java.lang.String articleId, double version);

	/**
	* Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_A_V(long groupId,
		java.lang.String articleId, double version, boolean retrieveFromCache);

	/**
	* Removes the journal article where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the journal article that was removed
	*/
	public JournalArticle removeByG_A_V(long groupId,
		java.lang.String articleId, double version)
		throws NoSuchArticleException;

	/**
	* Returns the number of journal articles where groupId = &#63; and articleId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param version the version
	* @return the number of matching journal articles
	*/
	public int countByG_A_V(long groupId, java.lang.String articleId,
		double version);

	/**
	* Returns all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_ST(long groupId,
		java.lang.String articleId, int status);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_ST(long groupId,
		java.lang.String articleId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_ST(long groupId,
		java.lang.String articleId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_ST(long groupId,
		java.lang.String articleId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_A_ST_First(long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_A_ST_First(long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_A_ST_Last(long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_A_ST_Last(long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_A_ST_PrevAndNext(long id, long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A_ST(long groupId,
		java.lang.String articleId, int status);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A_ST(long groupId,
		java.lang.String articleId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A_ST(long groupId,
		java.lang.String articleId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_A_ST_PrevAndNext(long id,
		long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param statuses the statuses
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A_ST(long groupId,
		java.lang.String articleId, int[] statuses);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A_ST(long groupId,
		java.lang.String articleId, int[] statuses, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A_ST(long groupId,
		java.lang.String articleId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param statuses the statuses
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_ST(long groupId,
		java.lang.String articleId, int[] statuses);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_ST(long groupId,
		java.lang.String articleId, int[] statuses, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param statuses the statuses
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_ST(long groupId,
		java.lang.String articleId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_ST(long groupId,
		java.lang.String articleId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	*/
	public void removeByG_A_ST(long groupId, java.lang.String articleId,
		int status);

	/**
	* Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByG_A_ST(long groupId, java.lang.String articleId,
		int status);

	/**
	* Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param statuses the statuses
	* @return the number of matching journal articles
	*/
	public int countByG_A_ST(long groupId, java.lang.String articleId,
		int[] statuses);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_A_ST(long groupId, java.lang.String articleId,
		int status);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param statuses the statuses
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_A_ST(long groupId, java.lang.String articleId,
		int[] statuses);

	/**
	* Returns all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_NotST(long groupId,
		java.lang.String articleId, int status);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_NotST(long groupId,
		java.lang.String articleId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_NotST(long groupId,
		java.lang.String articleId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_A_NotST(long groupId,
		java.lang.String articleId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_A_NotST_First(long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_A_NotST_First(long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_A_NotST_Last(long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_A_NotST_Last(long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_A_NotST_PrevAndNext(long id, long groupId,
		java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A_NotST(long groupId,
		java.lang.String articleId, int status);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A_NotST(long groupId,
		java.lang.String articleId, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_A_NotST(long groupId,
		java.lang.String articleId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_A_NotST_PrevAndNext(long id,
		long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	*/
	public void removeByG_A_NotST(long groupId, java.lang.String articleId,
		int status);

	/**
	* Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByG_A_NotST(long groupId, java.lang.String articleId,
		int status);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param status the status
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_A_NotST(long groupId, java.lang.String articleId,
		int status);

	/**
	* Returns all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_UT_ST(long groupId,
		java.lang.String urlTitle, int status);

	/**
	* Returns a range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_UT_ST(long groupId,
		java.lang.String urlTitle, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_UT_ST(long groupId,
		java.lang.String urlTitle, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByG_UT_ST(long groupId,
		java.lang.String urlTitle, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_UT_ST_First(long groupId,
		java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_UT_ST_First(long groupId,
		java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByG_UT_ST_Last(long groupId,
		java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByG_UT_ST_Last(long groupId,
		java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByG_UT_ST_PrevAndNext(long id, long groupId,
		java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @return the matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_UT_ST(long groupId,
		java.lang.String urlTitle, int status);

	/**
	* Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_UT_ST(long groupId,
		java.lang.String urlTitle, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles that the user has permission to view
	*/
	public java.util.List<JournalArticle> filterFindByG_UT_ST(long groupId,
		java.lang.String urlTitle, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] filterFindByG_UT_ST_PrevAndNext(long id,
		long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	*/
	public void removeByG_UT_ST(long groupId, java.lang.String urlTitle,
		int status);

	/**
	* Returns the number of journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByG_UT_ST(long groupId, java.lang.String urlTitle,
		int status);

	/**
	* Returns the number of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param status the status
	* @return the number of matching journal articles that the user has permission to view
	*/
	public int filterCountByG_UT_ST(long groupId, java.lang.String urlTitle,
		int status);

	/**
	* Returns all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @return the matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_V_ST(long companyId,
		double version, int status);

	/**
	* Returns a range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_V_ST(long companyId,
		double version, int status, int start, int end);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_V_ST(long companyId,
		double version, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal articles
	*/
	public java.util.List<JournalArticle> findByC_V_ST(long companyId,
		double version, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_V_ST_First(long companyId, double version,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the first journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_V_ST_First(long companyId, double version,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the last journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article
	* @throws NoSuchArticleException if a matching journal article could not be found
	*/
	public JournalArticle findByC_V_ST_Last(long companyId, double version,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Returns the last journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	*/
	public JournalArticle fetchByC_V_ST_Last(long companyId, double version,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* @param id the primary key of the current journal article
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle[] findByC_V_ST_PrevAndNext(long id, long companyId,
		double version, int status,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException;

	/**
	* Removes all the journal articles where companyId = &#63; and version = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	*/
	public void removeByC_V_ST(long companyId, double version, int status);

	/**
	* Returns the number of journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param version the version
	* @param status the status
	* @return the number of matching journal articles
	*/
	public int countByC_V_ST(long companyId, double version, int status);

	/**
	* Caches the journal article in the entity cache if it is enabled.
	*
	* @param journalArticle the journal article
	*/
	public void cacheResult(JournalArticle journalArticle);

	/**
	* Caches the journal articles in the entity cache if it is enabled.
	*
	* @param journalArticles the journal articles
	*/
	public void cacheResult(java.util.List<JournalArticle> journalArticles);

	/**
	* Creates a new journal article with the primary key. Does not add the journal article to the database.
	*
	* @param id the primary key for the new journal article
	* @return the new journal article
	*/
	public JournalArticle create(long id);

	/**
	* Removes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal article
	* @return the journal article that was removed
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle remove(long id) throws NoSuchArticleException;

	public JournalArticle updateImpl(JournalArticle journalArticle);

	/**
	* Returns the journal article with the primary key or throws a {@link NoSuchArticleException} if it could not be found.
	*
	* @param id the primary key of the journal article
	* @return the journal article
	* @throws NoSuchArticleException if a journal article with the primary key could not be found
	*/
	public JournalArticle findByPrimaryKey(long id)
		throws NoSuchArticleException;

	/**
	* Returns the journal article with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the journal article
	* @return the journal article, or <code>null</code> if a journal article with the primary key could not be found
	*/
	public JournalArticle fetchByPrimaryKey(long id);

	@Override
	public java.util.Map<java.io.Serializable, JournalArticle> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the journal articles.
	*
	* @return the journal articles
	*/
	public java.util.List<JournalArticle> findAll();

	/**
	* Returns a range of all the journal articles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @return the range of journal articles
	*/
	public java.util.List<JournalArticle> findAll(int start, int end);

	/**
	* Returns an ordered range of all the journal articles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal articles
	*/
	public java.util.List<JournalArticle> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator);

	/**
	* Returns an ordered range of all the journal articles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal articles
	* @param end the upper bound of the range of journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of journal articles
	*/
	public java.util.List<JournalArticle> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalArticle> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal articles from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of journal articles.
	*
	* @return the number of journal articles
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}