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

package com.liferay.portlet.messageboards.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for MBStatsUser. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserLocalServiceUtil
 * @see com.liferay.portlet.messageboards.service.base.MBStatsUserLocalServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBStatsUserLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MBStatsUserLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBStatsUserLocalServiceUtil} to access the message boards stats user local service. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBStatsUserLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the message boards stats user to the database. Also notifies the appropriate model listeners.
	*
	* @param mbStatsUser the message boards stats user
	* @return the message boards stats user that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.messageboards.model.MBStatsUser addMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser);

	public com.liferay.portlet.messageboards.model.MBStatsUser addStatsUser(
		long groupId, long userId);

	/**
	* Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	*
	* @param statsUserId the primary key for the new message boards stats user
	* @return the new message boards stats user
	*/
	public com.liferay.portlet.messageboards.model.MBStatsUser createMBStatsUser(
		long statsUserId);

	/**
	* Deletes the message boards stats user from the database. Also notifies the appropriate model listeners.
	*
	* @param mbStatsUser the message boards stats user
	* @return the message boards stats user that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.messageboards.model.MBStatsUser deleteMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser);

	/**
	* Deletes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsUserId the primary key of the message boards stats user
	* @return the message boards stats user that was removed
	* @throws PortalException if a message boards stats user with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.messageboards.model.MBStatsUser deleteMBStatsUser(
		long statsUserId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public void deleteStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser statsUser);

	public void deleteStatsUser(long statsUserId) throws PortalException;

	public void deleteStatsUsersByGroupId(long groupId);

	public void deleteStatsUsersByUserId(long userId);

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBStatsUser fetchMBStatsUser(
		long statsUserId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.Date getLastPostDateByUserId(long groupId, long userId);

	/**
	* Returns the message boards stats user with the primary key.
	*
	* @param statsUserId the primary key of the message boards stats user
	* @return the message boards stats user
	* @throws PortalException if a message boards stats user with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBStatsUser getMBStatsUser(
		long statsUserId) throws PortalException;

	/**
	* Returns a range of all the message boards stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards stats users
	* @param end the upper bound of the range of message boards stats users (not inclusive)
	* @return the range of message boards stats users
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getMBStatsUsers(
		int start, int end);

	/**
	* Returns the number of message boards stats users.
	*
	* @return the number of message boards stats users
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMBStatsUsersCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getMessageCountByGroupId(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getMessageCountByUserId(long userId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBStatsUser getStatsUser(
		long groupId, long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByGroupId(
		long groupId, int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStatsUsersByGroupIdCount(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByUserId(
		long userId);

	/**
	* Updates the message boards stats user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbStatsUser the message boards stats user
	* @return the message boards stats user that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.messageboards.model.MBStatsUser updateMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser);

	public com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId);

	public com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId, java.util.Date lastPostDate);

	public com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId, int messageCount, java.util.Date lastPostDate);
}