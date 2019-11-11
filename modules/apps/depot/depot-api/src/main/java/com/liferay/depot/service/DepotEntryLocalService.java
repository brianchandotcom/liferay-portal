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

package com.liferay.depot.service;

import com.liferay.depot.model.DepotEntry;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
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
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for DepotEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface DepotEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DepotEntryLocalServiceUtil} to access the depot entry local service. Add custom service methods to <code>com.liferay.depot.service.impl.DepotEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the depot entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntry the depot entry
	 * @return the depot entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public DepotEntry addDepotEntry(DepotEntry depotEntry);

	public DepotEntry addDepotEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Creates a new depot entry with the primary key. Does not add the depot entry to the database.
	 *
	 * @param depotEntryId the primary key for the new depot entry
	 * @return the new depot entry
	 */
	@Transactional(enabled = false)
	public DepotEntry createDepotEntry(long depotEntryId);

	/**
	 * Deletes the depot entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntry the depot entry
	 * @return the depot entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public DepotEntry deleteDepotEntry(DepotEntry depotEntry);

	/**
	 * Deletes the depot entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryId the primary key of the depot entry
	 * @return the depot entry that was removed
	 * @throws PortalException if a depot entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public DepotEntry deleteDepotEntry(long depotEntryId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryModelImpl</code>.
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
	public DepotEntry fetchDepotEntry(long depotEntryId);

	/**
	 * Returns the depot entry matching the UUID and group.
	 *
	 * @param uuid the depot entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching depot entry, or <code>null</code> if a matching depot entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DepotEntry fetchDepotEntryByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns a range of all the depot entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entries
	 * @param end the upper bound of the range of depot entries (not inclusive)
	 * @return the range of depot entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DepotEntry> getDepotEntries(int start, int end);

	/**
	 * Returns all the depot entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the depot entries
	 * @param companyId the primary key of the company
	 * @return the matching depot entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DepotEntry> getDepotEntriesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of depot entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the depot entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of depot entries
	 * @param end the upper bound of the range of depot entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching depot entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DepotEntry> getDepotEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DepotEntry> orderByComparator);

	/**
	 * Returns the number of depot entries.
	 *
	 * @return the number of depot entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDepotEntriesCount();

	/**
	 * Returns the depot entry with the primary key.
	 *
	 * @param depotEntryId the primary key of the depot entry
	 * @return the depot entry
	 * @throws PortalException if a depot entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DepotEntry getDepotEntry(long depotEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DepotEntry getDepotEntryByGroupId(long groupId)
		throws PortalException;

	/**
	 * Returns the depot entry matching the UUID and group.
	 *
	 * @param uuid the depot entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching depot entry
	 * @throws PortalException if a matching depot entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DepotEntry getDepotEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException;

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
	 * Updates the depot entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntry the depot entry
	 * @return the depot entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public DepotEntry updateDepotEntry(DepotEntry depotEntry);

	public DepotEntry updateDepotEntry(
			long depotEntryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException;

}