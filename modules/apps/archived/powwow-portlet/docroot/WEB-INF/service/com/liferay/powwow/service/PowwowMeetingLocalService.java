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

package com.liferay.powwow.service;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowParticipant;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for PowwowMeeting. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Shinn Lok
 * @see PowwowMeetingLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface PowwowMeetingLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.powwow.service.impl.PowwowMeetingLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the powwow meeting local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link PowwowMeetingLocalServiceUtil} if injection and service tracking are not available.
	 */
	@Indexable(type = IndexableType.REINDEX)
	public PowwowMeeting addPowwowMeeting(
			long userId, long groupId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Adds the powwow meeting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PowwowMeetingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param powwowMeeting the powwow meeting
	 * @return the powwow meeting that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public PowwowMeeting addPowwowMeeting(PowwowMeeting powwowMeeting);

	public void checkPowwowMeetings() throws PortalException;

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Creates a new powwow meeting with the primary key. Does not add the powwow meeting to the database.
	 *
	 * @param powwowMeetingId the primary key for the new powwow meeting
	 * @return the new powwow meeting
	 */
	@Transactional(enabled = false)
	public PowwowMeeting createPowwowMeeting(long powwowMeetingId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * Deletes the powwow meeting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PowwowMeetingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param powwowMeetingId the primary key of the powwow meeting
	 * @return the powwow meeting that was removed
	 * @throws PortalException if a powwow meeting with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public PowwowMeeting deletePowwowMeeting(long powwowMeetingId)
		throws PortalException;

	/**
	 * Deletes the powwow meeting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PowwowMeetingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param powwowMeeting the powwow meeting
	 * @return the powwow meeting that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	public PowwowMeeting deletePowwowMeeting(PowwowMeeting powwowMeeting)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.powwow.model.impl.PowwowMeetingModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.powwow.model.impl.PowwowMeetingModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PowwowMeeting fetchPowwowMeeting(long powwowMeetingId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowMeeting> getParticipantPowwowMeetings(
		long userId, int[] statuses, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getParticipantPowwowMeetingsCount(long userId, int[] statuses);

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Returns the powwow meeting with the primary key.
	 *
	 * @param powwowMeetingId the primary key of the powwow meeting
	 * @return the powwow meeting
	 * @throws PortalException if a powwow meeting with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PowwowMeeting getPowwowMeeting(long powwowMeetingId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowMeeting> getPowwowMeetings(int status);

	/**
	 * Returns a range of all the powwow meetings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.powwow.model.impl.PowwowMeetingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow meetings
	 * @param end the upper bound of the range of powwow meetings (not inclusive)
	 * @return the range of powwow meetings
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowMeeting> getPowwowMeetings(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowMeeting> getPowwowMeetings(
		long groupId, long userId, String name, String description, int status,
		boolean andSearch, int start, int end, String orderByField,
		String orderByType);

	/**
	 * Returns the number of powwow meetings.
	 *
	 * @return the number of powwow meetings
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowMeetingsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowMeetingsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowMeetingsCount(long powwowServerId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowMeetingsCount(
		long groupId, long userId, String name, String description, int status,
		boolean andSearch);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserPowwowMeetingsCount(long userId, int status);

	@Indexable(type = IndexableType.REINDEX)
	public PowwowMeeting updatePowwowMeeting(
			long powwowMeetingId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Updates the powwow meeting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PowwowMeetingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param powwowMeeting the powwow meeting
	 * @return the powwow meeting that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public PowwowMeeting updatePowwowMeeting(PowwowMeeting powwowMeeting);

	@Indexable(type = IndexableType.REINDEX)
	public PowwowMeeting updateStatus(long powwowMeetingId, int status)
		throws PortalException;

}