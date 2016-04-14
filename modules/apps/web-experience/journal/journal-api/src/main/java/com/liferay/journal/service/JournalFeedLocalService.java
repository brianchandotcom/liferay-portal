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

package com.liferay.journal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.journal.model.JournalFeed;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for JournalFeed. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedLocalServiceUtil
 * @see com.liferay.journal.service.base.JournalFeedLocalServiceBaseImpl
 * @see com.liferay.journal.service.impl.JournalFeedLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface JournalFeedLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalFeedLocalServiceUtil} to access the journal feed local service. Add custom service methods to {@link com.liferay.journal.service.impl.JournalFeedLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public JournalFeed addFeed(long userId, long groupId,
		java.lang.String feedId, boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey,
		java.lang.String ddmRendererTemplateKey, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedFormat, double feedVersion,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds the journal feed to the database. Also notifies the appropriate model listeners.
	*
	* @param journalFeed the journal feed
	* @return the journal feed that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public JournalFeed addJournalFeed(JournalFeed journalFeed);

	/**
	* Creates a new journal feed with the primary key. Does not add the journal feed to the database.
	*
	* @param id the primary key for the new journal feed
	* @return the new journal feed
	*/
	public JournalFeed createJournalFeed(long id);

	/**
	* Deletes the journal feed from the database. Also notifies the appropriate model listeners.
	*
	* @param journalFeed the journal feed
	* @return the journal feed that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public JournalFeed deleteJournalFeed(JournalFeed journalFeed);

	/**
	* Deletes the journal feed with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal feed
	* @return the journal feed that was removed
	* @throws PortalException if a journal feed with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public JournalFeed deleteJournalFeed(long id) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JournalFeed fetchFeed(long groupId, java.lang.String feedId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JournalFeed fetchJournalFeed(long id);

	/**
	* Returns the journal feed matching the UUID and group.
	*
	* @param uuid the journal feed's UUID
	* @param groupId the primary key of the group
	* @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JournalFeed fetchJournalFeedByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JournalFeed getFeed(long feedId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JournalFeed getFeed(long groupId, java.lang.String feedId)
		throws PortalException;

	/**
	* Returns the journal feed with the primary key.
	*
	* @param id the primary key of the journal feed
	* @return the journal feed
	* @throws PortalException if a journal feed with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JournalFeed getJournalFeed(long id) throws PortalException;

	/**
	* Returns the journal feed matching the UUID and group.
	*
	* @param uuid the journal feed's UUID
	* @param groupId the primary key of the group
	* @return the matching journal feed
	* @throws PortalException if a matching journal feed could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JournalFeed getJournalFeedByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	public JournalFeed updateFeed(long groupId, java.lang.String feedId,
		java.lang.String name, java.lang.String description,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.lang.String ddmRendererTemplateKey, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedFormat, double feedVersion,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates the journal feed in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalFeed the journal feed
	* @return the journal feed that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public JournalFeed updateJournalFeed(JournalFeed journalFeed);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFeedsCount(long groupId);

	/**
	* Returns the number of journal feeds.
	*
	* @return the number of journal feeds
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getJournalFeedsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId,
		java.lang.String keywords);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.journal.model.impl.JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.journal.model.impl.JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<JournalFeed> getFeeds();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<JournalFeed> getFeeds(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<JournalFeed> getFeeds(long groupId, int start, int end);

	/**
	* Returns a range of all the journal feeds.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.journal.model.impl.JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @return the range of journal feeds
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<JournalFeed> getJournalFeeds(int start, int end);

	/**
	* Returns all the journal feeds matching the UUID and company.
	*
	* @param uuid the UUID of the journal feeds
	* @param companyId the primary key of the company
	* @return the matching journal feeds, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<JournalFeed> getJournalFeedsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of journal feeds matching the UUID and company.
	*
	* @param uuid the UUID of the journal feeds
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching journal feeds, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<JournalFeed> getJournalFeedsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<JournalFeed> search(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		OrderByComparator<JournalFeed> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<JournalFeed> search(long companyId, long groupId,
		java.lang.String keywords, int start, int end,
		OrderByComparator<JournalFeed> obc);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void addFeedResources(JournalFeed feed, boolean addGroupPermissions,
		boolean addGuestPermissions) throws PortalException;

	public void addFeedResources(JournalFeed feed,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws PortalException;

	public void addFeedResources(long feedId, boolean addGroupPermissions,
		boolean addGuestPermissions) throws PortalException;

	public void addFeedResources(long feedId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws PortalException;

	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteFeed(JournalFeed feed) throws PortalException;

	public void deleteFeed(long feedId) throws PortalException;

	public void deleteFeed(long groupId, java.lang.String feedId)
		throws PortalException;
}