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

package com.liferay.app.builder.service;

import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.petra.sql.dsl.query.DSLQuery;
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
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for AppBuilderAppDataRecordLink. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDataRecordLinkLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface AppBuilderAppDataRecordLinkLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AppBuilderAppDataRecordLinkLocalServiceUtil} to access the app builder app data record link local service. Add custom service methods to <code>com.liferay.app.builder.service.impl.AppBuilderAppDataRecordLinkLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the app builder app data record link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 * @return the app builder app data record link that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addAppBuilderAppDataRecordLink(long, long, long, long, long)}
	 */
	@Deprecated
	public AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink(
		long companyId, long appBuilderAppId, long ddlRecordId);

	public AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink(
		long groupId, long companyId, long appBuilderAppId,
		long appBuilderAppVersionId, long ddlRecordId);

	/**
	 * Creates a new app builder app data record link with the primary key. Does not add the app builder app data record link to the database.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key for the new app builder app data record link
	 * @return the new app builder app data record link
	 */
	@Transactional(enabled = false)
	public AppBuilderAppDataRecordLink createAppBuilderAppDataRecordLink(
		long appBuilderAppDataRecordLinkId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the app builder app data record link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 * @return the app builder app data record link that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public AppBuilderAppDataRecordLink deleteAppBuilderAppDataRecordLink(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink);

	/**
	 * Deletes the app builder app data record link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link that was removed
	 * @throws PortalException if a app builder app data record link with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public AppBuilderAppDataRecordLink deleteAppBuilderAppDataRecordLink(
			long appBuilderAppDataRecordLinkId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl</code>.
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
	public AppBuilderAppDataRecordLink fetchAppBuilderAppDataRecordLink(
		long appBuilderAppDataRecordLinkId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AppBuilderAppDataRecordLink
		fetchDDLRecordAppBuilderAppDataRecordLink(long ddlRecordId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the app builder app data record link with the primary key.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link
	 * @throws PortalException if a app builder app data record link with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AppBuilderAppDataRecordLink getAppBuilderAppDataRecordLink(
			long appBuilderAppDataRecordLinkId)
		throws PortalException;

	/**
	 * Returns a range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of app builder app data record links
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderAppDataRecordLink> getAppBuilderAppDataRecordLinks(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderAppDataRecordLink> getAppBuilderAppDataRecordLinks(
		long appBuilderAppId);

	/**
	 * Returns the number of app builder app data record links.
	 *
	 * @return the number of app builder app data record links
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAppBuilderAppDataRecordLinksCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AppBuilderAppDataRecordLink getDDLRecordAppBuilderAppDataRecordLink(
			long ddlRecordId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Updates the app builder app data record link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 * @return the app builder app data record link that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public AppBuilderAppDataRecordLink updateAppBuilderAppDataRecordLink(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink);

}