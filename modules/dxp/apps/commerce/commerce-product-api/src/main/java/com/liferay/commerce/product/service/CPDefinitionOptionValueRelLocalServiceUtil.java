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
 * Provides the local service utility for CPDefinitionOptionValueRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionValueRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRelLocalService
 * @see com.liferay.commerce.product.service.base.CPDefinitionOptionValueRelLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionOptionValueRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionOptionValueRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionValueRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp definition option value rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionValueRel the cp definition option value rel
	* @return the cp definition option value rel that was added
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		return getService()
				   .addCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId,
		com.liferay.commerce.product.model.CPOptionValue cpOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionOptionValueRel(cpDefinitionOptionRelId,
			cpOptionValue, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionOptionValueRel(cpDefinitionOptionRelId,
			titleMap, priority, key, serviceContext);
	}

	/**
	* Creates a new cp definition option value rel with the primary key. Does not add the cp definition option value rel to the database.
	*
	* @param CPDefinitionOptionValueRelId the primary key for the new cp definition option value rel
	* @return the new cp definition option value rel
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel createCPDefinitionOptionValueRel(
		long CPDefinitionOptionValueRelId) {
		return getService()
				   .createCPDefinitionOptionValueRel(CPDefinitionOptionValueRelId);
	}

	/**
	* Deletes the cp definition option value rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionValueRel the cp definition option value rel
	* @return the cp definition option value rel that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	/**
	* Deletes the cp definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	* @return the cp definition option value rel that was removed
	* @throws PortalException if a cp definition option value rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
		long CPDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPDefinitionOptionValueRel(CPDefinitionOptionValueRelId);
	}

	public static void deleteCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPDefinitionOptionValueRels(cpDefinitionOptionRelId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
		long CPDefinitionOptionValueRelId) {
		return getService()
				   .fetchCPDefinitionOptionValueRel(CPDefinitionOptionValueRelId);
	}

	/**
	* Returns the cp definition option value rel matching the UUID and group.
	*
	* @param uuid the cp definition option value rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRelByUuidAndGroupId(
		String uuid, long groupId) {
		return getService()
				   .fetchCPDefinitionOptionValueRelByUuidAndGroupId(uuid,
			groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the cp definition option value rel with the primary key.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	* @return the cp definition option value rel
	* @throws PortalException if a cp definition option value rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
		long CPDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionOptionValueRel(CPDefinitionOptionValueRelId);
	}

	/**
	* Returns the cp definition option value rel matching the UUID and group.
	*
	* @param uuid the cp definition option value rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option value rel
	* @throws PortalException if a matching cp definition option value rel could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel getCPDefinitionOptionValueRelByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionOptionValueRelByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the cp definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @return the range of cp definition option value rels
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		int start, int end) {
		return getService().getCPDefinitionOptionValueRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId) {
		return getService()
				   .getCPDefinitionOptionValueRels(cpDefinitionOptionRelId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end) {
		return getService()
				   .getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> orderByComparator) {
		return getService()
				   .getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long[] cpDefinitionOptionValueRelsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionOptionValueRels(cpDefinitionOptionValueRelsId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		String key, int start, int end) {
		return getService().getCPDefinitionOptionValueRels(key, start, end);
	}

	/**
	* Returns all the cp definition option value rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option value rels
	* @param companyId the primary key of the company
	* @return the matching cp definition option value rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRelsByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService()
				   .getCPDefinitionOptionValueRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition option value rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option value rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition option value rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRelsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> orderByComparator) {
		return getService()
				   .getCPDefinitionOptionValueRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition option value rels.
	*
	* @return the number of cp definition option value rels
	*/
	public static int getCPDefinitionOptionValueRelsCount() {
		return getService().getCPDefinitionOptionValueRelsCount();
	}

	public static int getCPDefinitionOptionValueRelsCount(
		long cpDefinitionOptionRelId) {
		return getService()
				   .getCPDefinitionOptionValueRelsCount(cpDefinitionOptionRelId);
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
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void importCPDefinitionOptionRels(
		long cpDefinitionOptionRelId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.importCPDefinitionOptionRels(cpDefinitionOptionRelId,
			serviceContext);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> searchCPDefinitionOptionValueRels(
		long companyId, long groupId, long cpDefinitionOptionRelId,
		String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPDefinitionOptionValueRels(companyId, groupId,
			cpDefinitionOptionRelId, keywords, start, end, sort);
	}

	/**
	* Updates the cp definition option value rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionValueRel the cp definition option value rel
	* @return the cp definition option value rel that was updated
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		return getService()
				   .updateCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId,
			titleMap, priority, key, serviceContext);
	}

	public static CPDefinitionOptionValueRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionOptionValueRelLocalService, CPDefinitionOptionValueRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionOptionValueRelLocalService.class);

		ServiceTracker<CPDefinitionOptionValueRelLocalService, CPDefinitionOptionValueRelLocalService> serviceTracker =
			new ServiceTracker<CPDefinitionOptionValueRelLocalService, CPDefinitionOptionValueRelLocalService>(bundle.getBundleContext(),
				CPDefinitionOptionValueRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}