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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalContentSearch;

/**
 * The persistence interface for the journal content search service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalContentSearchPersistenceImpl
 * @see JournalContentSearchUtil
 * @generated
 */
public interface JournalContentSearchPersistence extends BasePersistence<JournalContentSearch> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalContentSearchUtil} to access the journal content search persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the journal content search in the entity cache if it is enabled.
	*
	* @param journalContentSearch the journal content search
	*/
	public void cacheResult(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch);

	/**
	* Caches the journal content searchs in the entity cache if it is enabled.
	*
	* @param journalContentSearchs the journal content searchs
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> journalContentSearchs);

	/**
	* Creates a new journal content search with the primary key. Does not add the journal content search to the database.
	*
	* @param contentSearchId the primary key for the new journal content search
	* @return the new journal content search
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch create(
		long contentSearchId);

	/**
	* Removes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search that was removed
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch remove(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch updateImpl(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal content search with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchContentSearchException} if it could not be found.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByPrimaryKey(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the journal content search with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search, or <code>null</code> if a journal content search with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch fetchByPrimaryKey(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the journal content searchs where articleId = &#63;.
	*
	* @param articleId the article ID
	* @return the matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByArticleId(
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal content searchs where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal content searchs where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal content search in the ordered set where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByArticleId_First(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByArticleId_Last(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch[] findByArticleId_PrevAndNext(
		long contentSearchId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_Last(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_A(
		long groupId, java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_A(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_A_First(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_A_Last(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_A_PrevAndNext(
		long contentSearchId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_First(
		long groupId, boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_Last(
		long groupId, boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_L_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @return the matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, java.lang.String articleId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, java.lang.String articleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_A_First(
		long groupId, boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_A_Last(
		long groupId, boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_A_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @return the matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_P_First(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_P_Last(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_L_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchContentSearchException} if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the matching journal content search
	* @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the journal content searchs.
	*
	* @return the journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal content searchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal content searchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal content searchs where articleId = &#63; from the database.
	*
	* @param articleId the article ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByArticleId(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the journal content search that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalContentSearch removeByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	/**
	* Removes all the journal content searchs from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal content searchs where articleId = &#63;.
	*
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByArticleId(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the number of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @return the number of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal content searchs.
	*
	* @return the number of journal content searchs
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}