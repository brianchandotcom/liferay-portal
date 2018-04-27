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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPFriendlyURLEntry. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPFriendlyURLEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntryLocalService
 * @see com.liferay.commerce.product.service.base.CPFriendlyURLEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPFriendlyURLEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPFriendlyURLEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPFriendlyURLEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addCPFriendlyURLEntries(long groupId, long companyId,
		Class<?> clazz, long classPK,
		java.util.Map<java.util.Locale, String> urlTitleMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addCPFriendlyURLEntries(groupId, companyId, clazz, classPK,
			urlTitleMap);
	}

	/**
	* Adds the cp friendly url entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cpFriendlyURLEntry the cp friendly url entry
	* @return the cp friendly url entry that was added
	*/
	public static com.liferay.commerce.product.model.CPFriendlyURLEntry addCPFriendlyURLEntry(
		com.liferay.commerce.product.model.CPFriendlyURLEntry cpFriendlyURLEntry) {
		return getService().addCPFriendlyURLEntry(cpFriendlyURLEntry);
	}

	public static String buildUrlTitle(long groupId, long classNameId,
		long classPK, String languageId, String title) {
		return getService()
				   .buildUrlTitle(groupId, classNameId, classPK, languageId,
			title);
	}

	/**
	* Creates a new cp friendly url entry with the primary key. Does not add the cp friendly url entry to the database.
	*
	* @param CPFriendlyURLEntryId the primary key for the new cp friendly url entry
	* @return the new cp friendly url entry
	*/
	public static com.liferay.commerce.product.model.CPFriendlyURLEntry createCPFriendlyURLEntry(
		long CPFriendlyURLEntryId) {
		return getService().createCPFriendlyURLEntry(CPFriendlyURLEntryId);
	}

	public static void deleteCPFriendlyURLEntries(long groupId, Class<?> clazz,
		long classPK) {
		getService().deleteCPFriendlyURLEntries(groupId, clazz, classPK);
	}

	/**
	* Deletes the cp friendly url entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cpFriendlyURLEntry the cp friendly url entry
	* @return the cp friendly url entry that was removed
	*/
	public static com.liferay.commerce.product.model.CPFriendlyURLEntry deleteCPFriendlyURLEntry(
		com.liferay.commerce.product.model.CPFriendlyURLEntry cpFriendlyURLEntry) {
		return getService().deleteCPFriendlyURLEntry(cpFriendlyURLEntry);
	}

	/**
	* Deletes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry that was removed
	* @throws PortalException if a cp friendly url entry with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPFriendlyURLEntry deleteCPFriendlyURLEntry(
		long CPFriendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPFriendlyURLEntry(CPFriendlyURLEntryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long CPFriendlyURLEntryId) {
		return getService().fetchCPFriendlyURLEntry(CPFriendlyURLEntryId);
	}

	public static com.liferay.commerce.product.model.CPFriendlyURLEntry fetchCPFriendlyURLEntry(
		long groupId, long classNameId, long classPK, String languageId,
		boolean main) {
		return getService()
				   .fetchCPFriendlyURLEntry(groupId, classNameId, classPK,
			languageId, main);
	}

	/**
	* Returns the cp friendly url entry matching the UUID and group.
	*
	* @param uuid the cp friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static com.liferay.commerce.product.model.CPFriendlyURLEntry fetchCPFriendlyURLEntryByUuidAndGroupId(
		String uuid, long groupId) {
		return getService()
				   .fetchCPFriendlyURLEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the cp friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of cp friendly url entries
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPFriendlyURLEntry> getCPFriendlyURLEntries(
		int start, int end) {
		return getService().getCPFriendlyURLEntries(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPFriendlyURLEntry> getCPFriendlyURLEntries(
		long groupId, long classNameId, long classPK) {
		return getService()
				   .getCPFriendlyURLEntries(groupId, classNameId, classPK);
	}

	/**
	* Returns all the cp friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp friendly url entries
	* @param companyId the primary key of the company
	* @return the matching cp friendly url entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPFriendlyURLEntry> getCPFriendlyURLEntriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService()
				   .getCPFriendlyURLEntriesByUuidAndCompanyId(uuid, companyId);
	}

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
	public static java.util.List<com.liferay.commerce.product.model.CPFriendlyURLEntry> getCPFriendlyURLEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPFriendlyURLEntry> orderByComparator) {
		return getService()
				   .getCPFriendlyURLEntriesByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of cp friendly url entries.
	*
	* @return the number of cp friendly url entries
	*/
	public static int getCPFriendlyURLEntriesCount() {
		return getService().getCPFriendlyURLEntriesCount();
	}

	/**
	* Returns the cp friendly url entry with the primary key.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry
	* @throws PortalException if a cp friendly url entry with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPFriendlyURLEntry getCPFriendlyURLEntry(
		long CPFriendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPFriendlyURLEntry(CPFriendlyURLEntryId);
	}

	public static com.liferay.commerce.product.model.CPFriendlyURLEntry getCPFriendlyURLEntry(
		long groupId, long classNameId, String languageId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPFriendlyURLEntry(groupId, classNameId, languageId,
			urlTitle);
	}

	/**
	* Returns the cp friendly url entry matching the UUID and group.
	*
	* @param uuid the cp friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp friendly url entry
	* @throws PortalException if a matching cp friendly url entry could not be found
	*/
	public static com.liferay.commerce.product.model.CPFriendlyURLEntry getCPFriendlyURLEntryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPFriendlyURLEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.Map<String, String> getLanguageIdToUrlTitleMap(
		long groupId, long classNameId, long classPK) {
		return getService()
				   .getLanguageIdToUrlTitleMap(groupId, classNameId, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.Map<java.util.Locale, String> getUrlTitleMap(
		long groupId, long classNameId, long classPK) {
		return getService().getUrlTitleMap(groupId, classNameId, classPK);
	}

	public static String getUrlTitleMapAsXML(long groupId, long classNameId,
		long classPK, String defaultLanguageId) {
		return getService()
				   .getUrlTitleMapAsXML(groupId, classNameId, classPK,
			defaultLanguageId);
	}

	/**
	* Updates the cp friendly url entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpFriendlyURLEntry the cp friendly url entry
	* @return the cp friendly url entry that was updated
	*/
	public static com.liferay.commerce.product.model.CPFriendlyURLEntry updateCPFriendlyURLEntry(
		com.liferay.commerce.product.model.CPFriendlyURLEntry cpFriendlyURLEntry) {
		return getService().updateCPFriendlyURLEntry(cpFriendlyURLEntry);
	}

	public static CPFriendlyURLEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPFriendlyURLEntryLocalService, CPFriendlyURLEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPFriendlyURLEntryLocalService.class);

		ServiceTracker<CPFriendlyURLEntryLocalService, CPFriendlyURLEntryLocalService> serviceTracker =
			new ServiceTracker<CPFriendlyURLEntryLocalService, CPFriendlyURLEntryLocalService>(bundle.getBundleContext(),
				CPFriendlyURLEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}