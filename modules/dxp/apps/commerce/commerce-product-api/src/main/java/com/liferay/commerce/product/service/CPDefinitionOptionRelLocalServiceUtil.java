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
 * Provides the local service utility for CPDefinitionOptionRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRelLocalService
 * @see com.liferay.commerce.product.service.base.CPDefinitionOptionRelLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionOptionRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp definition option rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	* @return the cp definition option rel that was added
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		com.liferay.commerce.product.model.CPDefinitionOptionRel cpDefinitionOptionRel) {
		return getService().addCPDefinitionOptionRel(cpDefinitionOptionRel);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId, boolean importOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionOptionRel(cpDefinitionId, cpOptionId,
			importOptionValue, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, double priority, boolean facetable,
		boolean required, boolean skuContributor, boolean importOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionOptionRel(cpDefinitionId, cpOptionId,
			nameMap, descriptionMap, ddmFormFieldTypeName, priority, facetable,
			required, skuContributor, importOptionValue, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionOptionRel(cpDefinitionId, cpOptionId,
			serviceContext);
	}

	/**
	* Creates a new cp definition option rel with the primary key. Does not add the cp definition option rel to the database.
	*
	* @param CPDefinitionOptionRelId the primary key for the new cp definition option rel
	* @return the new cp definition option rel
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel createCPDefinitionOptionRel(
		long CPDefinitionOptionRelId) {
		return getService().createCPDefinitionOptionRel(CPDefinitionOptionRelId);
	}

	/**
	* Deletes the cp definition option rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	* @return the cp definition option rel that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		com.liferay.commerce.product.model.CPDefinitionOptionRel cpDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinitionOptionRel(cpDefinitionOptionRel);
	}

	/**
	* Deletes the cp definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	* @return the cp definition option rel that was removed
	* @throws PortalException if a cp definition option rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		long CPDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinitionOptionRel(CPDefinitionOptionRelId);
	}

	public static void deleteCPDefinitionOptionRels(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPDefinitionOptionRels(cpDefinitionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel fetchCPDefinitionOptionRel(
		long CPDefinitionOptionRelId) {
		return getService().fetchCPDefinitionOptionRel(CPDefinitionOptionRelId);
	}

	/**
	* Returns the cp definition option rel matching the UUID and group.
	*
	* @param uuid the cp definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel fetchCPDefinitionOptionRelByUuidAndGroupId(
		String uuid, long groupId) {
		return getService()
				   .fetchCPDefinitionOptionRelByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the cp definition option rel with the primary key.
	*
	* @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	* @return the cp definition option rel
	* @throws PortalException if a cp definition option rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel getCPDefinitionOptionRel(
		long CPDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionOptionRel(CPDefinitionOptionRelId);
	}

	/**
	* Returns the cp definition option rel matching the UUID and group.
	*
	* @param uuid the cp definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option rel
	* @throws PortalException if a matching cp definition option rel could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel getCPDefinitionOptionRelByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionOptionRelByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the cp definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of cp definition option rels
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		int start, int end) {
		return getService().getCPDefinitionOptionRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId) {
		return getService().getCPDefinitionOptionRels(cpDefinitionId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, boolean skuContributor) {
		return getService()
				   .getCPDefinitionOptionRels(cpDefinitionId, skuContributor);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end) {
		return getService().getCPDefinitionOptionRels(cpDefinitionId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionRel> orderByComparator) {
		return getService()
				   .getCPDefinitionOptionRels(cpDefinitionId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the cp definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option rels
	* @param companyId the primary key of the company
	* @return the matching cp definition option rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRelsByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService()
				   .getCPDefinitionOptionRelsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of cp definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition option rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRelsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionRel> orderByComparator) {
		return getService()
				   .getCPDefinitionOptionRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition option rels.
	*
	* @return the number of cp definition option rels
	*/
	public static int getCPDefinitionOptionRelsCount() {
		return getService().getCPDefinitionOptionRelsCount();
	}

	public static int getCPDefinitionOptionRelsCount(long cpDefinitionId) {
		return getService().getCPDefinitionOptionRelsCount(cpDefinitionId);
	}

	public static int getCPDefinitionOptionRelsCount(long cpDefinitionId,
		boolean skuContributor) {
		return getService()
				   .getCPDefinitionOptionRelsCount(cpDefinitionId,
			skuContributor);
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

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionRel> searchCPDefinitionOptionRels(
		long companyId, long groupId, long cpDefinitionId, String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPDefinitionOptionRels(companyId, groupId,
			cpDefinitionId, keywords, start, end, sort);
	}

	/**
	* Updates the cp definition option rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	* @return the cp definition option rel that was updated
	*/
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel updateCPDefinitionOptionRel(
		com.liferay.commerce.product.model.CPDefinitionOptionRel cpDefinitionOptionRel) {
		return getService().updateCPDefinitionOptionRel(cpDefinitionOptionRel);
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel updateCPDefinitionOptionRel(
		long cpDefinitionOptionRelId, long cpOptionId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, double priority, boolean facetable,
		boolean required, boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionOptionRel(cpDefinitionOptionRelId,
			cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
			priority, facetable, required, skuContributor, serviceContext);
	}

	public static CPDefinitionOptionRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionOptionRelLocalService, CPDefinitionOptionRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionOptionRelLocalService.class);

		ServiceTracker<CPDefinitionOptionRelLocalService, CPDefinitionOptionRelLocalService> serviceTracker =
			new ServiceTracker<CPDefinitionOptionRelLocalService, CPDefinitionOptionRelLocalService>(bundle.getBundleContext(),
				CPDefinitionOptionRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}