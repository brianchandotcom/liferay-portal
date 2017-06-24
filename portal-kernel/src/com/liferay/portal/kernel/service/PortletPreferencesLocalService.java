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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.spring.aop.Property;
import com.liferay.portal.kernel.spring.aop.Retry;
import com.liferay.portal.kernel.spring.aop.Skip;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service interface for PortletPreferences. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesLocalServiceUtil
 * @see com.liferay.portal.service.base.PortletPreferencesLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.PortletPreferencesLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface PortletPreferencesLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletPreferencesLocalServiceUtil} to access the portlet preferences local service. Add custom service methods to {@link com.liferay.portal.service.impl.PortletPreferencesLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
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
	* Adds the portlet preferences to the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferences the portlet preferences
	* @return the portlet preferences that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public PortletPreferences addPortletPreferences(
		PortletPreferences portletPreferences);

	public PortletPreferences addPortletPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		Portlet portlet, java.lang.String defaultPreferences);

	/**
	* Creates a new portlet preferences with the primary key. Does not add the portlet preferences to the database.
	*
	* @param portletPreferencesId the primary key for the new portlet preferences
	* @return the new portlet preferences
	*/
	public PortletPreferences createPortletPreferences(
		long portletPreferencesId);

	/**
	* Deletes the portlet preferences from the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferences the portlet preferences
	* @return the portlet preferences that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public PortletPreferences deletePortletPreferences(
		PortletPreferences portletPreferences);

	/**
	* Deletes the portlet preferences with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences that was removed
	* @throws PortalException if a portlet preferences with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public PortletPreferences deletePortletPreferences(
		long portletPreferencesId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PortletPreferences fetchPortletPreferences(long ownerId,
		int ownerType, long plid, java.lang.String portletId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PortletPreferences fetchPortletPreferences(long portletPreferencesId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PortletPreferences getPortletPreferences(long ownerId,
		int ownerType, long plid, java.lang.String portletId)
		throws PortalException;

	/**
	* Returns the portlet preferences with the primary key.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences
	* @throws PortalException if a portlet preferences with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PortletPreferences getPortletPreferences(long portletPreferencesId)
		throws PortalException;

	/**
	* Updates the portlet preferences in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portletPreferences the portlet preferences
	* @return the portlet preferences that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public PortletPreferences updatePortletPreferences(
		PortletPreferences portletPreferences);

	public PortletPreferences updatePreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId, java.lang.String xml);

	public PortletPreferences updatePreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId,
		javax.portlet.PortletPreferences portletPreferences);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Settings getPortletInstanceSettings(long companyId, long groupId,
		java.lang.String portletId,
		PortletInstanceSettingsLocator portletInstanceSettingsLocator,
		Settings portalPreferencesSettings);

	/**
	* Returns the number of portlet preferenceses.
	*
	* @return the number of portlet preferenceses
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPortletPreferencesesCount();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<PortletPreferences> getPortletPreferences();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PortletPreferences> getPortletPreferences(int ownerType,
		long plid, java.lang.String portletId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PortletPreferences> getPortletPreferences(long companyId,
		long groupId, long ownerId, int ownerType, java.lang.String portletId,
		boolean privateLayout);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PortletPreferences> getPortletPreferences(long ownerId,
		int ownerType, long plid);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PortletPreferences> getPortletPreferences(long plid,
		java.lang.String portletId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PortletPreferences> getPortletPreferencesByPlid(long plid);

	/**
	* Returns a range of all the portlet preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of portlet preferenceses
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PortletPreferences> getPortletPreferenceses(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<java.lang.String, javax.portlet.PortletPreferences> getStrictPreferences(
		Layout layout, List<Portlet> portlets);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public javax.portlet.PortletPreferences fetchPreferences(
		PortletPreferencesIds portletPreferencesIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public javax.portlet.PortletPreferences fetchPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId);

	@Skip
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, java.lang.String portletId);

	@Retry(acceptor = ExceptionRetryAcceptor.class, properties =  {
		@Property(name = ExceptionRetryAcceptor.EXCEPTION_NAME, value = "org.springframework.dao.DataIntegrityViolationException")
	}
	)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public javax.portlet.PortletPreferences getPreferences(
		PortletPreferencesIds portletPreferencesIds);

	@Retry(acceptor = ExceptionRetryAcceptor.class, properties =  {
		@Property(name = ExceptionRetryAcceptor.EXCEPTION_NAME, value = "org.springframework.dao.DataIntegrityViolationException")
	}
	)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public javax.portlet.PortletPreferences getPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId);

	@Retry(acceptor = ExceptionRetryAcceptor.class, properties =  {
		@Property(name = ExceptionRetryAcceptor.EXCEPTION_NAME, value = "org.springframework.dao.DataIntegrityViolationException")
	}
	)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public javax.portlet.PortletPreferences getPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		java.lang.String defaultPreferences);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public javax.portlet.PortletPreferences getStrictPreferences(
		PortletPreferencesIds portletPreferencesIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public javax.portlet.PortletPreferences getStrictPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId);

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
	public long getPortletPreferencesCount(int ownerType,
		java.lang.String portletId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getPortletPreferencesCount(int ownerType, long plid,
		java.lang.String portletId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getPortletPreferencesCount(long ownerId, int ownerType,
		java.lang.String portletId, boolean excludeDefaultPreferences);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getPortletPreferencesCount(long ownerId, int ownerType,
		long plid, Portlet portlet, boolean excludeDefaultPreferences);

	public void deletePortletPreferences(long ownerId, int ownerType, long plid);

	public void deletePortletPreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId) throws PortalException;

	public void deletePortletPreferencesByPlid(long plid);
}