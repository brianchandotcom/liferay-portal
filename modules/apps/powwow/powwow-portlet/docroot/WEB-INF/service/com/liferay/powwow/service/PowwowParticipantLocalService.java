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

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.kernel.service.InvokableLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.powwow.model.PowwowParticipant;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for PowwowParticipant. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Shinn Lok
 * @see PowwowParticipantLocalServiceUtil
 * @see com.liferay.powwow.service.base.PowwowParticipantLocalServiceBaseImpl
 * @see com.liferay.powwow.service.impl.PowwowParticipantLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface PowwowParticipantLocalService extends BaseLocalService,
	InvokableLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PowwowParticipantLocalServiceUtil} to access the powwow participant local service. Add custom service methods to {@link com.liferay.powwow.service.impl.PowwowParticipantLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

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

	/**
	* Adds the powwow participant to the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipant the powwow participant
	* @return the powwow participant that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public PowwowParticipant addPowwowParticipant(
		PowwowParticipant powwowParticipant);

	public PowwowParticipant addPowwowParticipant(long userId, long groupId,
		long powwowMeetingId, java.lang.String name, long participantUserId,
		java.lang.String emailAddress, int type, int status,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new powwow participant with the primary key. Does not add the powwow participant to the database.
	*
	* @param powwowParticipantId the primary key for the new powwow participant
	* @return the new powwow participant
	*/
	public PowwowParticipant createPowwowParticipant(long powwowParticipantId);

	/**
	* Deletes the powwow participant from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipant the powwow participant
	* @return the powwow participant that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public PowwowParticipant deletePowwowParticipant(
		PowwowParticipant powwowParticipant);

	/**
	* Deletes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant that was removed
	* @throws PortalException if a powwow participant with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public PowwowParticipant deletePowwowParticipant(long powwowParticipantId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PowwowParticipant fetchPowwowParticipant(long powwowMeetingId,
		java.lang.String emailAddress);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PowwowParticipant fetchPowwowParticipant(long powwowMeetingId,
		long participantUserId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PowwowParticipant fetchPowwowParticipant(long powwowParticipantId);

	/**
	* Returns the powwow participant with the primary key.
	*
	* @param powwowParticipantId the primary key of the powwow participant
	* @return the powwow participant
	* @throws PortalException if a powwow participant with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PowwowParticipant getPowwowParticipant(long powwowParticipantId)
		throws PortalException;

	/**
	* Updates the powwow participant in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param powwowParticipant the powwow participant
	* @return the powwow participant that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public PowwowParticipant updatePowwowParticipant(
		PowwowParticipant powwowParticipant);

	public PowwowParticipant updatePowwowParticipant(long powwowParticipantId,
		long powwowMeetingId, java.lang.String name, long participantUserId,
		java.lang.String emailAddress, int type, int status,
		ServiceContext serviceContext) throws PortalException;

	public PowwowParticipant updateStatus(long powwowParticipantId, int status)
		throws PortalException;

	/**
	* Returns the number of powwow participants.
	*
	* @return the number of powwow participants
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowParticipantsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowParticipantsCount(long powwowMeetingId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowParticipantsCount(long powwowMeetingId, int type);

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the powwow participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow participants
	* @param end the upper bound of the range of powwow participants (not inclusive)
	* @return the range of powwow participants
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowParticipant> getPowwowParticipants(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowParticipant> getPowwowParticipants(long powwowMeetingId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowParticipant> getPowwowParticipants(long powwowMeetingId,
		int type);

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
}