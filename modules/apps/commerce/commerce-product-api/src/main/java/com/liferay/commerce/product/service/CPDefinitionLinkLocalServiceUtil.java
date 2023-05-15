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

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CPDefinitionLink. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPDefinitionLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkLocalService
 * @generated
 */
public class CPDefinitionLinkLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPDefinitionLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the cp definition link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionLink the cp definition link
	 * @return the cp definition link that was added
	 */
	public static CPDefinitionLink addCPDefinitionLink(
		CPDefinitionLink cpDefinitionLink) {

		return getService().addCPDefinitionLink(cpDefinitionLink);
	}

	public static CPDefinitionLink addCPDefinitionLinkByCProductId(
			long cpDefinitionId, long cProductId, double priority, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPDefinitionLinkByCProductId(
			cpDefinitionId, cProductId, priority, type, serviceContext);
	}

	/**
	 * Creates a new cp definition link with the primary key. Does not add the cp definition link to the database.
	 *
	 * @param CPDefinitionLinkId the primary key for the new cp definition link
	 * @return the new cp definition link
	 */
	public static CPDefinitionLink createCPDefinitionLink(
		long CPDefinitionLinkId) {

		return getService().createCPDefinitionLink(CPDefinitionLinkId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the cp definition link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionLink the cp definition link
	 * @return the cp definition link that was removed
	 * @throws PortalException
	 */
	public static CPDefinitionLink deleteCPDefinitionLink(
			CPDefinitionLink cpDefinitionLink)
		throws PortalException {

		return getService().deleteCPDefinitionLink(cpDefinitionLink);
	}

	/**
	 * Deletes the cp definition link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDefinitionLinkId the primary key of the cp definition link
	 * @return the cp definition link that was removed
	 * @throws PortalException if a cp definition link with the primary key could not be found
	 */
	public static CPDefinitionLink deleteCPDefinitionLink(
			long CPDefinitionLinkId)
		throws PortalException {

		return getService().deleteCPDefinitionLink(CPDefinitionLinkId);
	}

	public static void deleteCPDefinitionLinksByCPDefinitionId(
			long cpDefinitionId)
		throws PortalException {

		getService().deleteCPDefinitionLinksByCPDefinitionId(cpDefinitionId);
	}

	public static void deleteCPDefinitionLinksByCProductId(long cProductId)
		throws PortalException {

		getService().deleteCPDefinitionLinksByCProductId(cProductId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CPDefinitionLink fetchCPDefinitionLink(
		long CPDefinitionLinkId) {

		return getService().fetchCPDefinitionLink(CPDefinitionLinkId);
	}

	public static CPDefinitionLink fetchCPDefinitionLink(
		long cpDefinitionId, long cProductId, String type) {

		return getService().fetchCPDefinitionLink(
			cpDefinitionId, cProductId, type);
	}

	/**
	 * Returns the cp definition link matching the UUID and group.
	 *
	 * @param uuid the cp definition link's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	 */
	public static CPDefinitionLink fetchCPDefinitionLinkByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchCPDefinitionLinkByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cp definition link with the primary key.
	 *
	 * @param CPDefinitionLinkId the primary key of the cp definition link
	 * @return the cp definition link
	 * @throws PortalException if a cp definition link with the primary key could not be found
	 */
	public static CPDefinitionLink getCPDefinitionLink(long CPDefinitionLinkId)
		throws PortalException {

		return getService().getCPDefinitionLink(CPDefinitionLinkId);
	}

	/**
	 * Returns the cp definition link matching the UUID and group.
	 *
	 * @param uuid the cp definition link's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp definition link
	 * @throws PortalException if a matching cp definition link could not be found
	 */
	public static CPDefinitionLink getCPDefinitionLinkByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getCPDefinitionLinkByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the cp definition links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @return the range of cp definition links
	 */
	public static List<CPDefinitionLink> getCPDefinitionLinks(
		int start, int end) {

		return getService().getCPDefinitionLinks(start, end);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId) {

		return getService().getCPDefinitionLinks(cpDefinitionId);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId, int start, int end) {

		return getService().getCPDefinitionLinks(cpDefinitionId, start, end);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId, String type) {

		return getService().getCPDefinitionLinks(cpDefinitionId, type);
	}

	public static List<CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId, String type, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {

		return getService().getCPDefinitionLinks(
			cpDefinitionId, type, start, end, orderByComparator);
	}

	/**
	 * Returns all the cp definition links matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp definition links
	 * @param companyId the primary key of the company
	 * @return the matching cp definition links, or an empty list if no matches were found
	 */
	public static List<CPDefinitionLink> getCPDefinitionLinksByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getCPDefinitionLinksByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of cp definition links matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp definition links
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching cp definition links, or an empty list if no matches were found
	 */
	public static List<CPDefinitionLink> getCPDefinitionLinksByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {

		return getService().getCPDefinitionLinksByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of cp definition links.
	 *
	 * @return the number of cp definition links
	 */
	public static int getCPDefinitionLinksCount() {
		return getService().getCPDefinitionLinksCount();
	}

	public static int getCPDefinitionLinksCount(long cpDefinitionId) {
		return getService().getCPDefinitionLinksCount(cpDefinitionId);
	}

	public static int getCPDefinitionLinksCount(
		long cpDefinitionId, String type) {

		return getService().getCPDefinitionLinksCount(cpDefinitionId, type);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

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

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static List<CPDefinitionLink> getReverseCPDefinitionLinks(
		long cProductId, String type) {

		return getService().getReverseCPDefinitionLinks(cProductId, type);
	}

	/**
	 * Updates the cp definition link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionLink the cp definition link
	 * @return the cp definition link that was updated
	 */
	public static CPDefinitionLink updateCPDefinitionLink(
		CPDefinitionLink cpDefinitionLink) {

		return getService().updateCPDefinitionLink(cpDefinitionLink);
	}

	public static CPDefinitionLink updateCPDefinitionLink(
			long cpDefinitionLinkId, double priority,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPDefinitionLink(
			cpDefinitionLinkId, priority, serviceContext);
	}

	public static void updateCPDefinitionLinkCProductIds(
			long cpDefinitionId, long[] cProductIds, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateCPDefinitionLinkCProductIds(
			cpDefinitionId, cProductIds, type, serviceContext);
	}

	public static CPDefinitionLinkLocalService getService() {
		return _service;
	}

	public static void setService(CPDefinitionLinkLocalService service) {
		_service = service;
	}

	private static volatile CPDefinitionLinkLocalService _service;

}