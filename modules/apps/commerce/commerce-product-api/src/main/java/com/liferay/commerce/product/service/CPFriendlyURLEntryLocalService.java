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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPFriendlyURLEntry;
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
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CPFriendlyURLEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntryLocalServiceUtil
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalServiceImpl}
 * @generated
 */
@Deprecated
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CPFriendlyURLEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPFriendlyURLEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the cp friendly url entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CPFriendlyURLEntryLocalServiceUtil} if injection and service tracking are not available.
	 */
	public void addCPFriendlyURLEntries(
			long groupId, long companyId, Class<?> clazz, long classPK,
			Map<Locale, String> urlTitleMap)
		throws PortalException;

	/**
	 * Adds the cp friendly url entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPFriendlyURLEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpFriendlyURLEntry the cp friendly url entry
	 * @return the cp friendly url entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CPFriendlyURLEntry addCPFriendlyURLEntry(
		CPFriendlyURLEntry cpFriendlyURLEntry);

	public String buildUrlTitle(
		long groupId, long classNameId, long classPK, String languageId,
		String title);

	/**
	 * Creates a new cp friendly url entry with the primary key. Does not add the cp friendly url entry to the database.
	 *
	 * @param CPFriendlyURLEntryId the primary key for the new cp friendly url entry
	 * @return the new cp friendly url entry
	 */
	@Transactional(enabled = false)
	public CPFriendlyURLEntry createCPFriendlyURLEntry(
		long CPFriendlyURLEntryId);

	public void deleteCPFriendlyURLEntries(
		long groupId, Class<?> clazz, long classPK);

	/**
	 * Deletes the cp friendly url entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPFriendlyURLEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpFriendlyURLEntry the cp friendly url entry
	 * @return the cp friendly url entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public CPFriendlyURLEntry deleteCPFriendlyURLEntry(
		CPFriendlyURLEntry cpFriendlyURLEntry);

	/**
	 * Deletes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPFriendlyURLEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	 * @return the cp friendly url entry that was removed
	 * @throws PortalException if a cp friendly url entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public CPFriendlyURLEntry deleteCPFriendlyURLEntry(
			long CPFriendlyURLEntryId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl</code>.
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
	public CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long CPFriendlyURLEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long groupId, long classNameId, long classPK, String languageId,
		boolean main);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long groupId, long classNameId, String languageId, String urlTitle);

	/**
	 * Returns the cp friendly url entry matching the UUID and group.
	 *
	 * @param uuid the cp friendly url entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPFriendlyURLEntry fetchCPFriendlyURLEntryByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns a range of all the cp friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of cp friendly url entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPFriendlyURLEntry> getCPFriendlyURLEntries(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPFriendlyURLEntry> getCPFriendlyURLEntries(
		long groupId, long classNameId, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPFriendlyURLEntry> getCPFriendlyURLEntries(
		long groupId, long classNameId, String urlTitle);

	/**
	 * Returns all the cp friendly url entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp friendly url entries
	 * @param companyId the primary key of the company
	 * @return the matching cp friendly url entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPFriendlyURLEntry> getCPFriendlyURLEntriesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of cp friendly url entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp friendly url entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching cp friendly url entries, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPFriendlyURLEntry> getCPFriendlyURLEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator);

	/**
	 * Returns the number of cp friendly url entries.
	 *
	 * @return the number of cp friendly url entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPFriendlyURLEntriesCount();

	/**
	 * Returns the cp friendly url entry with the primary key.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	 * @return the cp friendly url entry
	 * @throws PortalException if a cp friendly url entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPFriendlyURLEntry getCPFriendlyURLEntry(long CPFriendlyURLEntryId)
		throws PortalException;

	/**
	 * Returns the cp friendly url entry matching the UUID and group.
	 *
	 * @param uuid the cp friendly url entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp friendly url entry
	 * @throws PortalException if a matching cp friendly url entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPFriendlyURLEntry getCPFriendlyURLEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, String> getLanguageIdToUrlTitleMap(
		long groupId, long classNameId, long classPK);

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Locale, String> getUrlTitleMap(
		long groupId, long classNameId, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getUrlTitleMapAsXML(
		long groupId, long classNameId, long classPK, String defaultLanguageId);

	/**
	 * Updates the cp friendly url entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPFriendlyURLEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpFriendlyURLEntry the cp friendly url entry
	 * @return the cp friendly url entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CPFriendlyURLEntry updateCPFriendlyURLEntry(
		CPFriendlyURLEntry cpFriendlyURLEntry);

}