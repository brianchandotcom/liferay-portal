/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.notification.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
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
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
 * Provides the local service interface for CommerceNotificationQueueEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryLocalServiceUtil
 * @see com.liferay.commerce.notification.service.base.CommerceNotificationQueueEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceNotificationQueueEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceNotificationQueueEntryLocalServiceUtil} to access the commerce notification queue entry local service. Add custom service methods to {@link com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce notification queue entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationQueueEntry the commerce notification queue entry
	* @return the commerce notification queue entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceNotificationQueueEntry addCommerceNotificationQueueEntry(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceNotificationQueueEntry addCommerceNotificationQueueEntry(
		long userId, long groupId, long commerceNotificationTemplateId,
		String from, String fromName, String to, String toName, String cc,
		String bcc, String subject, String body, double priority)
		throws PortalException;

	/**
	* Creates a new commerce notification queue entry with the primary key. Does not add the commerce notification queue entry to the database.
	*
	* @param commerceNotificationQueueEntryId the primary key for the new commerce notification queue entry
	* @return the new commerce notification queue entry
	*/
	@Transactional(enabled = false)
	public CommerceNotificationQueueEntry createCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId);

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceNotificationQueueEntry deleteCommerceNotificationQueue(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry)
		throws PortalException;

	public void deleteCommerceNotificationQueueEntries(Date sentDate);

	public void deleteCommerceNotificationQueueEntries(long groupId)
		throws PortalException;

	/**
	* Deletes the commerce notification queue entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationQueueEntry the commerce notification queue entry
	* @return the commerce notification queue entry that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceNotificationQueueEntry deleteCommerceNotificationQueueEntry(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry);

	/**
	* Deletes the commerce notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	* @return the commerce notification queue entry that was removed
	* @throws PortalException if a commerce notification queue entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceNotificationQueueEntry deleteCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public DynamicQuery dynamicQuery();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceNotificationQueueEntry fetchCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationQueueEntry> getCommerceNotificationQueueEntries(
		boolean sent);

	/**
	* Returns a range of all the commerce notification queue entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification queue entries
	* @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	* @return the range of commerce notification queue entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationQueueEntry> getCommerceNotificationQueueEntries(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationQueueEntry> getCommerceNotificationQueueEntries(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator);

	/**
	* Returns the number of commerce notification queue entries.
	*
	* @return the number of commerce notification queue entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceNotificationQueueEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceNotificationQueueEntriesCount(long groupId);

	/**
	* Returns the commerce notification queue entry with the primary key.
	*
	* @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	* @return the commerce notification queue entry
	* @throws PortalException if a commerce notification queue entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceNotificationQueueEntry getCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public CommerceNotificationQueueEntry resendCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId) throws PortalException;

	public void sendCommerceNotificationQueueEntries()
		throws Exception;

	public void updateCommerceNotificationQueueEntriesTemplateIds(
		long commerceNotificationTemplateId);

	/**
	* Updates the commerce notification queue entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationQueueEntry the commerce notification queue entry
	* @return the commerce notification queue entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceNotificationQueueEntry updateCommerceNotificationQueueEntry(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceNotificationQueueEntry updateSent(
		long commerceNotificationQueueEntryId, boolean sent)
		throws PortalException;
}