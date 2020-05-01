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

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
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
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for AppBuilderApp. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface AppBuilderAppLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AppBuilderAppLocalServiceUtil} to access the app builder app local service. Add custom service methods to <code>com.liferay.app.builder.service.impl.AppBuilderAppLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the app builder app to the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public AppBuilderApp addAppBuilderApp(AppBuilderApp appBuilderApp);

	@Indexable(type = IndexableType.REINDEX)
	public AppBuilderApp addAppBuilderApp(
			long groupId, long companyId, long userId, long ddmStructureId,
			long ddmStructureLayoutId, long deDataListViewId,
			Map<Locale, String> nameMap, int status)
		throws PortalException;

	/**
	 * Creates a new app builder app with the primary key. Does not add the app builder app to the database.
	 *
	 * @param appBuilderAppId the primary key for the new app builder app
	 * @return the new app builder app
	 */
	@Transactional(enabled = false)
	public AppBuilderApp createAppBuilderApp(long appBuilderAppId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the app builder app from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public AppBuilderApp deleteAppBuilderApp(AppBuilderApp appBuilderApp);

	/**
	 * Deletes the app builder app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app that was removed
	 * @throws PortalException if a app builder app with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public AppBuilderApp deleteAppBuilderApp(long appBuilderAppId)
		throws PortalException;

	public void deleteAppBuilderApps(long ddmStructureId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppModelImpl</code>.
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
	public AppBuilderApp fetchAppBuilderApp(long appBuilderAppId);

	/**
	 * Returns the app builder app matching the UUID and group.
	 *
	 * @param uuid the app builder app's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AppBuilderApp fetchAppBuilderAppByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the app builder app with the primary key.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app
	 * @throws PortalException if a app builder app with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AppBuilderApp getAppBuilderApp(long appBuilderAppId)
		throws PortalException;

	/**
	 * Returns the app builder app matching the UUID and group.
	 *
	 * @param uuid the app builder app's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app
	 * @throws PortalException if a matching app builder app could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AppBuilderApp getAppBuilderAppByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Long> getAppBuilderAppIds(int status, String type);

	/**
	 * Returns a range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of app builder apps
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderApp> getAppBuilderApps(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderApp> getAppBuilderApps(long ddmStructureId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderApp> getAppBuilderApps(long companyId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderApp> getAppBuilderApps(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderApp> getAppBuilderApps(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator);

	/**
	 * Returns all the app builder apps matching the UUID and company.
	 *
	 * @param uuid the UUID of the app builder apps
	 * @param companyId the primary key of the company
	 * @return the matching app builder apps, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderApp> getAppBuilderAppsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of app builder apps matching the UUID and company.
	 *
	 * @param uuid the UUID of the app builder apps
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching app builder apps, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderApp> getAppBuilderAppsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator);

	/**
	 * Returns the number of app builder apps.
	 *
	 * @return the number of app builder apps
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAppBuilderAppsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAppBuilderAppsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAppBuilderAppsCount(
		long groupId, long companyId, long ddmStructureId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppBuilderApp> getCompanyAppBuilderApps(
		long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyAppBuilderAppsCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

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
	 * Updates the app builder app in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public AppBuilderApp updateAppBuilderApp(AppBuilderApp appBuilderApp);

	@Indexable(type = IndexableType.REINDEX)
	public AppBuilderApp updateAppBuilderApp(
			long userId, long appBuilderAppId, long ddmStructureId,
			long ddmStructureLayoutId, long deDataListViewId,
			Map<Locale, String> nameMap, int status)
		throws PortalException;

}