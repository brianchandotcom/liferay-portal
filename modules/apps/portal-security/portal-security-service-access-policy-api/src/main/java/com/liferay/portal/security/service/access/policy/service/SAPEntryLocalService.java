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

package com.liferay.portal.security.service.access.policy.service;

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
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for SAPEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface SAPEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SAPEntryLocalServiceUtil} to access the sap entry local service. Add custom service methods to <code>com.liferay.portal.security.service.access.policy.service.impl.SAPEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public SAPEntry addSAPEntry(
			long userId, String allowedServiceSignatures,
			boolean defaultSAPEntry, boolean enabled, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Adds the sap entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntry the sap entry
	 * @return the sap entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public SAPEntry addSAPEntry(SAPEntry sapEntry);

	public void checkSystemSAPEntries(long companyId) throws PortalException;

	/**
	 * Creates a new sap entry with the primary key. Does not add the sap entry to the database.
	 *
	 * @param sapEntryId the primary key for the new sap entry
	 * @return the new sap entry
	 */
	@Transactional(enabled = false)
	public SAPEntry createSAPEntry(long sapEntryId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * Deletes the sap entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry that was removed
	 * @throws PortalException if a sap entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public SAPEntry deleteSAPEntry(long sapEntryId) throws PortalException;

	/**
	 * Deletes the sap entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntry the sap entry
	 * @return the sap entry that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	public SAPEntry deleteSAPEntry(SAPEntry sapEntry) throws PortalException;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.service.access.policy.model.impl.SAPEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.service.access.policy.model.impl.SAPEntryModelImpl</code>.
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
	public SAPEntry fetchSAPEntry(long sapEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry fetchSAPEntry(long companyId, String name);

	/**
	 * Returns the sap entry with the matching UUID and company.
	 *
	 * @param uuid the sap entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry fetchSAPEntryByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end, OrderByComparator<SAPEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanySAPEntriesCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SAPEntry> getDefaultSAPEntries(
		long companyId, boolean defaultSAPEntry);

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
	 * Returns a range of all the sap entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.service.access.policy.model.impl.SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of sap entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SAPEntry> getSAPEntries(int start, int end);

	/**
	 * Returns the number of sap entries.
	 *
	 * @return the number of sap entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSAPEntriesCount();

	/**
	 * Returns the sap entry with the primary key.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry
	 * @throws PortalException if a sap entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry getSAPEntry(long sapEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry getSAPEntry(long companyId, String name)
		throws PortalException;

	/**
	 * Returns the sap entry with the matching UUID and company.
	 *
	 * @param uuid the sap entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sap entry
	 * @throws PortalException if a matching sap entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry getSAPEntryByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException;

	public SAPEntry updateSAPEntry(
			long sapEntryId, String allowedServiceSignatures,
			boolean defaultSAPEntry, boolean enabled, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Updates the sap entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntry the sap entry
	 * @return the sap entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public SAPEntry updateSAPEntry(SAPEntry sapEntry);

}