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

import com.liferay.journal.exception.NoSuchContentSearchException;
import com.liferay.journal.model.JournalContentSearch;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the journal content search service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.journal.service.persistence.impl.JournalContentSearchPersistenceImpl
 * @see JournalContentSearchUtil
 * @generated
 */
@ProviderType
public interface JournalContentSearchPersistence extends BasePersistence<JournalContentSearch> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalContentSearchUtil} to access the journal content search persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the journal content searchs where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByPortletId(
		java.lang.String portletId);

	/**
	* Returns a range of all the journal content searchs where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByPortletId(
		java.lang.String portletId, int start, int end);

	/**
	* Returns an ordered range of all the journal content searchs where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByPortletId(
		java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns an ordered range of all the journal content searchs where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByPortletId(
		java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal content search in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByPortletId_First(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the first journal content search in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByPortletId_First(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the last journal content search in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByPortletId_Last(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByPortletId_Last(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where portletId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch[] findByPortletId_PrevAndNext(
		long contentSearchId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Removes all the journal content searchs where portletId = &#63; from the database.
	*
	* @param portletId the portlet ID
	*/
	public void removeByPortletId(java.lang.String portletId);

	/**
	* Returns the number of journal content searchs where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the number of matching journal content searchs
	*/
	public int countByPortletId(java.lang.String portletId);

	/**
	* Returns all the journal content searchs where articleId = &#63;.
	*
	* @param articleId the article ID
	* @return the matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByArticleId(
		java.lang.String articleId);

	/**
	* Returns a range of all the journal content searchs where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end);

	/**
	* Returns an ordered range of all the journal content searchs where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns an ordered range of all the journal content searchs where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal content search in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByArticleId_First(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the first journal content search in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByArticleId_First(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the last journal content search in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByArticleId_Last(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByArticleId_Last(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where articleId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch[] findByArticleId_PrevAndNext(
		long contentSearchId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Removes all the journal content searchs where articleId = &#63; from the database.
	*
	* @param articleId the article ID
	*/
	public void removeByArticleId(java.lang.String articleId);

	/**
	* Returns the number of journal content searchs where articleId = &#63;.
	*
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	*/
	public int countByArticleId(java.lang.String articleId);

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout);

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_P_First(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_First(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_P_Last(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_Last(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch[] findByG_P_PrevAndNext(long contentSearchId,
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	*/
	public void removeByG_P(long groupId, boolean privateLayout);

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching journal content searchs
	*/
	public int countByG_P(long groupId, boolean privateLayout);

	/**
	* Returns all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_A(long groupId,
		java.lang.String articleId);

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_A(long groupId,
		java.lang.String articleId, int start, int end);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_A(long groupId,
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_A(long groupId,
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_A_First(long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_A_First(long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_A_Last(long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_A_Last(long groupId,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch[] findByG_A_PrevAndNext(long contentSearchId,
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	*/
	public void removeByG_A(long groupId, java.lang.String articleId);

	/**
	* Returns the number of journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	*/
	public int countByG_A(long groupId, java.lang.String articleId);

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId);

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_P_L_First(long groupId,
		boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_L_First(long groupId,
		boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_P_L_Last(long groupId,
		boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_L_Last(long groupId,
		boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch[] findByG_P_L_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	*/
	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId);

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the number of matching journal content searchs
	*/
	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId);

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @return the matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, java.lang.String articleId);

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, java.lang.String articleId, int start, int end);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_P_A_First(long groupId,
		boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_A_First(long groupId,
		boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_P_A_Last(long groupId,
		boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_A_Last(long groupId,
		boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch[] findByG_P_A_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	*/
	public void removeByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId);

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	*/
	public int countByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId);

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @return the matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId);

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		int start, int end);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public java.util.List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_P_L_P_First(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_L_P_First(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_P_L_P_Last(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_L_P_Last(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch[] findByG_P_L_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException;

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	*/
	public void removeByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId);

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @return the number of matching journal content searchs
	*/
	public int countByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId);

	/**
	* Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or throws a {@link NoSuchContentSearchException} if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the matching journal content search
	* @throws NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public JournalContentSearch findByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId) throws NoSuchContentSearchException;

	/**
	* Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId);

	/**
	* Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId, boolean retrieveFromCache);

	/**
	* Removes the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the journal content search that was removed
	*/
	public JournalContentSearch removeByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId) throws NoSuchContentSearchException;

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	*/
	public int countByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId, java.lang.String articleId);

	/**
	* Caches the journal content search in the entity cache if it is enabled.
	*
	* @param journalContentSearch the journal content search
	*/
	public void cacheResult(JournalContentSearch journalContentSearch);

	/**
	* Caches the journal content searchs in the entity cache if it is enabled.
	*
	* @param journalContentSearchs the journal content searchs
	*/
	public void cacheResult(
		java.util.List<JournalContentSearch> journalContentSearchs);

	/**
	* Creates a new journal content search with the primary key. Does not add the journal content search to the database.
	*
	* @param contentSearchId the primary key for the new journal content search
	* @return the new journal content search
	*/
	public JournalContentSearch create(long contentSearchId);

	/**
	* Removes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search that was removed
	* @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch remove(long contentSearchId)
		throws NoSuchContentSearchException;

	public JournalContentSearch updateImpl(
		JournalContentSearch journalContentSearch);

	/**
	* Returns the journal content search with the primary key or throws a {@link NoSuchContentSearchException} if it could not be found.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search
	* @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch findByPrimaryKey(long contentSearchId)
		throws NoSuchContentSearchException;

	/**
	* Returns the journal content search with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search, or <code>null</code> if a journal content search with the primary key could not be found
	*/
	public JournalContentSearch fetchByPrimaryKey(long contentSearchId);

	@Override
	public java.util.Map<java.io.Serializable, JournalContentSearch> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the journal content searchs.
	*
	* @return the journal content searchs
	*/
	public java.util.List<JournalContentSearch> findAll();

	/**
	* Returns a range of all the journal content searchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of journal content searchs
	*/
	public java.util.List<JournalContentSearch> findAll(int start, int end);

	/**
	* Returns an ordered range of all the journal content searchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal content searchs
	*/
	public java.util.List<JournalContentSearch> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator);

	/**
	* Returns an ordered range of all the journal content searchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of journal content searchs
	*/
	public java.util.List<JournalContentSearch> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal content searchs from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of journal content searchs.
	*
	* @return the number of journal content searchs
	*/
	public int countAll();
}