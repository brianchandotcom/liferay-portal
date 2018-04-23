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

package com.liferay.commerce.product.type.grouped.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPDefinitionGroupedEntry. This utility wraps
 * {@link com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryLocalService
 * @see com.liferay.commerce.product.type.grouped.service.base.CPDefinitionGroupedEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionGroupedEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addCPDefinitionGroupedEntries(long cpDefinitionId,
		long[] entryCPDefinitionIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addCPDefinitionGroupedEntries(cpDefinitionId,
			entryCPDefinitionIds, serviceContext);
	}

	/**
	* Adds the cp definition grouped entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionGroupedEntry the cp definition grouped entry
	* @return the cp definition grouped entry that was added
	*/
	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		return getService().addCPDefinitionGroupedEntry(cpDefinitionGroupedEntry);
	}

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
		long cpDefinitionId, long entryCPDefinitionId, double priority,
		int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionGroupedEntry(cpDefinitionId,
			entryCPDefinitionId, priority, quantity, serviceContext);
	}

	/**
	* Creates a new cp definition grouped entry with the primary key. Does not add the cp definition grouped entry to the database.
	*
	* @param CPDefinitionGroupedEntryId the primary key for the new cp definition grouped entry
	* @return the new cp definition grouped entry
	*/
	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry createCPDefinitionGroupedEntry(
		long CPDefinitionGroupedEntryId) {
		return getService()
				   .createCPDefinitionGroupedEntry(CPDefinitionGroupedEntryId);
	}

	public static void deleteCPDefinitionGroupedEntries(long cpDefinitionId) {
		getService().deleteCPDefinitionGroupedEntries(cpDefinitionId);
	}

	/**
	* Deletes the cp definition grouped entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionGroupedEntry the cp definition grouped entry
	* @return the cp definition grouped entry that was removed
	*/
	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry deleteCPDefinitionGroupedEntry(
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		return getService()
				   .deleteCPDefinitionGroupedEntry(cpDefinitionGroupedEntry);
	}

	/**
	* Deletes the cp definition grouped entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry that was removed
	* @throws PortalException if a cp definition grouped entry with the primary key could not be found
	*/
	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry deleteCPDefinitionGroupedEntry(
		long CPDefinitionGroupedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPDefinitionGroupedEntry(CPDefinitionGroupedEntryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry fetchCPDefinitionGroupedEntry(
		long CPDefinitionGroupedEntryId) {
		return getService()
				   .fetchCPDefinitionGroupedEntry(CPDefinitionGroupedEntryId);
	}

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry fetchCPDefinitionGroupedEntryByC_E(
		long cpDefinitionId, long entryCPDefinitionId) {
		return getService()
				   .fetchCPDefinitionGroupedEntryByC_E(cpDefinitionId,
			entryCPDefinitionId);
	}

	/**
	* Returns the cp definition grouped entry matching the UUID and group.
	*
	* @param uuid the cp definition grouped entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry fetchCPDefinitionGroupedEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCPDefinitionGroupedEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the cp definition grouped entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of cp definition grouped entries
	*/
	public static java.util.List<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		int start, int end) {
		return getService().getCPDefinitionGroupedEntries(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		long cpDefinitionId) {
		return getService().getCPDefinitionGroupedEntries(cpDefinitionId);
	}

	public static java.util.List<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> orderByComparator) {
		return getService()
				   .getCPDefinitionGroupedEntries(cpDefinitionId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> getCPDefinitionGroupedEntriesByCPDefinitionId(
		long cpDefinitionId) {
		return getService()
				   .getCPDefinitionGroupedEntriesByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns all the cp definition grouped entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition grouped entries
	* @param companyId the primary key of the company
	* @return the matching cp definition grouped entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> getCPDefinitionGroupedEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCPDefinitionGroupedEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition grouped entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition grouped entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition grouped entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> getCPDefinitionGroupedEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> orderByComparator) {
		return getService()
				   .getCPDefinitionGroupedEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition grouped entries.
	*
	* @return the number of cp definition grouped entries
	*/
	public static int getCPDefinitionGroupedEntriesCount() {
		return getService().getCPDefinitionGroupedEntriesCount();
	}

	public static int getCPDefinitionGroupedEntriesCount(long cpDefinitionId) {
		return getService().getCPDefinitionGroupedEntriesCount(cpDefinitionId);
	}

	/**
	* Returns the cp definition grouped entry with the primary key.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry
	* @throws PortalException if a cp definition grouped entry with the primary key could not be found
	*/
	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry getCPDefinitionGroupedEntry(
		long CPDefinitionGroupedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionGroupedEntry(CPDefinitionGroupedEntryId);
	}

	/**
	* Returns the cp definition grouped entry matching the UUID and group.
	*
	* @param uuid the cp definition grouped entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition grouped entry
	* @throws PortalException if a matching cp definition grouped entry could not be found
	*/
	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry getCPDefinitionGroupedEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionGroupedEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp definition grouped entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionGroupedEntry the cp definition grouped entry
	* @return the cp definition grouped entry that was updated
	*/
	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		return getService()
				   .updateCPDefinitionGroupedEntry(cpDefinitionGroupedEntry);
	}

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
		long cpDefinitionGroupedEntryId, double priority, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionGroupedEntry(cpDefinitionGroupedEntryId,
			priority, quantity);
	}

	public static CPDefinitionGroupedEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionGroupedEntryLocalService, CPDefinitionGroupedEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionGroupedEntryLocalService.class);

		ServiceTracker<CPDefinitionGroupedEntryLocalService, CPDefinitionGroupedEntryLocalService> serviceTracker =
			new ServiceTracker<CPDefinitionGroupedEntryLocalService, CPDefinitionGroupedEntryLocalService>(bundle.getBundleContext(),
				CPDefinitionGroupedEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}