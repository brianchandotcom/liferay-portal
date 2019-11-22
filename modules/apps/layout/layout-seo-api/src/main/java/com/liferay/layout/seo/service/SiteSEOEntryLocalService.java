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

package com.liferay.layout.seo.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.layout.seo.model.SiteSEOEntry;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for SiteSEOEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SiteSEOEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface SiteSEOEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteSEOEntryLocalServiceUtil} to access the site seo entry local service. Add custom service methods to <code>com.liferay.layout.seo.service.impl.SiteSEOEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the site seo entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntry the site seo entry
	 * @return the site seo entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public SiteSEOEntry addSiteSEOEntry(SiteSEOEntry siteSEOEntry);

	/**
	 * Creates a new site seo entry with the primary key. Does not add the site seo entry to the database.
	 *
	 * @param siteSEOEntryId the primary key for the new site seo entry
	 * @return the new site seo entry
	 */
	@Transactional(enabled = false)
	public SiteSEOEntry createSiteSEOEntry(long siteSEOEntryId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * Deletes the site seo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry that was removed
	 * @throws PortalException if a site seo entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public SiteSEOEntry deleteSiteSEOEntry(long siteSEOEntryId)
		throws PortalException;

	/**
	 * Deletes the site seo entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntry the site seo entry
	 * @return the site seo entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public SiteSEOEntry deleteSiteSEOEntry(SiteSEOEntry siteSEOEntry);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.SiteSEOEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.SiteSEOEntryModelImpl</code>.
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
	public SiteSEOEntry fetchSiteSEOEntry(long siteSEOEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SiteSEOEntry fetchSiteSEOEntryByGroupId(long groupId);

	/**
	 * Returns the site seo entry matching the UUID and group.
	 *
	 * @param uuid the site seo entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SiteSEOEntry fetchSiteSEOEntryByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

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

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Returns a range of all the site seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @return the range of site seo entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SiteSEOEntry> getSiteSEOEntries(int start, int end);

	/**
	 * Returns all the site seo entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the site seo entries
	 * @param companyId the primary key of the company
	 * @return the matching site seo entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SiteSEOEntry> getSiteSEOEntriesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of site seo entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the site seo entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching site seo entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SiteSEOEntry> getSiteSEOEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteSEOEntry> orderByComparator);

	/**
	 * Returns the number of site seo entries.
	 *
	 * @return the number of site seo entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSiteSEOEntriesCount();

	/**
	 * Returns the site seo entry with the primary key.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry
	 * @throws PortalException if a site seo entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SiteSEOEntry getSiteSEOEntry(long siteSEOEntryId)
		throws PortalException;

	/**
	 * Returns the site seo entry matching the UUID and group.
	 *
	 * @param uuid the site seo entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site seo entry
	 * @throws PortalException if a matching site seo entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SiteSEOEntry getSiteSEOEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	public SiteSEOEntry updateSiteSEOEntry(
			long userId, long groupId, boolean openGraphEnabled,
			long openGraphImageFileEntryId, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Updates the site seo entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntry the site seo entry
	 * @return the site seo entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public SiteSEOEntry updateSiteSEOEntry(SiteSEOEntry siteSEOEntry);

}