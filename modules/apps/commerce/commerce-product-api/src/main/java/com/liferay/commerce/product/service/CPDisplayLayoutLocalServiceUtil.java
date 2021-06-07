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

import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CPDisplayLayout. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPDisplayLayoutLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPDisplayLayoutLocalService
 * @generated
 */
public class CPDisplayLayoutLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPDisplayLayoutLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the cp display layout to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDisplayLayoutLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDisplayLayout the cp display layout
	 * @return the cp display layout that was added
	 */
	public static CPDisplayLayout addCPDisplayLayout(
		CPDisplayLayout cpDisplayLayout) {

		return getService().addCPDisplayLayout(cpDisplayLayout);
	}

	public static CPDisplayLayout addCPDisplayLayout(
			long userId, long groupId, Class<?> clazz, long classPK,
			String layoutUuid)
		throws PortalException {

		return getService().addCPDisplayLayout(
			userId, groupId, clazz, classPK, layoutUuid);
	}

	/**
	 * Creates a new cp display layout with the primary key. Does not add the cp display layout to the database.
	 *
	 * @param CPDisplayLayoutId the primary key for the new cp display layout
	 * @return the new cp display layout
	 */
	public static CPDisplayLayout createCPDisplayLayout(
		long CPDisplayLayoutId) {

		return getService().createCPDisplayLayout(CPDisplayLayoutId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static CPDisplayLayout deleteCPDisplayLayout(
		Class<?> clazz, long classPK) {

		return getService().deleteCPDisplayLayout(clazz, classPK);
	}

	/**
	 * Deletes the cp display layout from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDisplayLayoutLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDisplayLayout the cp display layout
	 * @return the cp display layout that was removed
	 */
	public static CPDisplayLayout deleteCPDisplayLayout(
		CPDisplayLayout cpDisplayLayout) {

		return getService().deleteCPDisplayLayout(cpDisplayLayout);
	}

	/**
	 * Deletes the cp display layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDisplayLayoutLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDisplayLayoutId the primary key of the cp display layout
	 * @return the cp display layout that was removed
	 * @throws PortalException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout deleteCPDisplayLayout(long CPDisplayLayoutId)
		throws PortalException {

		return getService().deleteCPDisplayLayout(CPDisplayLayoutId);
	}

	public static void deleteCPDisplayLayouts(Class<?> clazz, long classPK) {
		getService().deleteCPDisplayLayouts(clazz, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPDisplayLayoutModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPDisplayLayoutModelImpl</code>.
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

	public static CPDisplayLayout fetchCPDisplayLayout(long CPDisplayLayoutId) {
		return getService().fetchCPDisplayLayout(CPDisplayLayoutId);
	}

	public static CPDisplayLayout fetchCPDisplayLayout(
		long groupId, Class<?> clazz, long classPK) {

		return getService().fetchCPDisplayLayout(groupId, clazz, classPK);
	}

	public static List<CPDisplayLayout>
		fetchCPDisplayLayoutByGroupIdAndLayoutUuid(
			long groupId, String layoutUuid) {

		return getService().fetchCPDisplayLayoutByGroupIdAndLayoutUuid(
			groupId, layoutUuid);
	}

	public static List<CPDisplayLayout>
		fetchCPDisplayLayoutByGroupIdAndLayoutUuid(
			long groupId, String layoutUuid, int start, int end) {

		return getService().fetchCPDisplayLayoutByGroupIdAndLayoutUuid(
			groupId, layoutUuid, start, end);
	}

	/**
	 * Returns the cp display layout matching the UUID and group.
	 *
	 * @param uuid the cp display layout's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout fetchCPDisplayLayoutByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchCPDisplayLayoutByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cp display layout with the primary key.
	 *
	 * @param CPDisplayLayoutId the primary key of the cp display layout
	 * @return the cp display layout
	 * @throws PortalException if a cp display layout with the primary key could not be found
	 */
	public static CPDisplayLayout getCPDisplayLayout(long CPDisplayLayoutId)
		throws PortalException {

		return getService().getCPDisplayLayout(CPDisplayLayoutId);
	}

	/**
	 * Returns the cp display layout matching the UUID and group.
	 *
	 * @param uuid the cp display layout's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp display layout
	 * @throws PortalException if a matching cp display layout could not be found
	 */
	public static CPDisplayLayout getCPDisplayLayoutByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getCPDisplayLayoutByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the cp display layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPDisplayLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of cp display layouts
	 */
	public static List<CPDisplayLayout> getCPDisplayLayouts(
		int start, int end) {

		return getService().getCPDisplayLayouts(start, end);
	}

	/**
	 * Returns all the cp display layouts matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp display layouts
	 * @param companyId the primary key of the company
	 * @return the matching cp display layouts, or an empty list if no matches were found
	 */
	public static List<CPDisplayLayout> getCPDisplayLayoutsByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getCPDisplayLayoutsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of cp display layouts matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp display layouts
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching cp display layouts, or an empty list if no matches were found
	 */
	public static List<CPDisplayLayout> getCPDisplayLayoutsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {

		return getService().getCPDisplayLayoutsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of cp display layouts.
	 *
	 * @return the number of cp display layouts
	 */
	public static int getCPDisplayLayoutsCount() {
		return getService().getCPDisplayLayoutsCount();
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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CPDisplayLayout> searchCPDisplayLayout(
				long companyId, long groupId, String className, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCPDisplayLayout(
			companyId, groupId, className, keywords, start, end, sort);
	}

	/**
	 * Updates the cp display layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDisplayLayoutLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDisplayLayout the cp display layout
	 * @return the cp display layout that was updated
	 */
	public static CPDisplayLayout updateCPDisplayLayout(
		CPDisplayLayout cpDisplayLayout) {

		return getService().updateCPDisplayLayout(cpDisplayLayout);
	}

	public static CPDisplayLayout updateCPDisplayLayout(
			long cpDisplayLayoutId, long classPK, String layoutUuid)
		throws PortalException {

		return getService().updateCPDisplayLayout(
			cpDisplayLayoutId, classPK, layoutUuid);
	}

	public static CPDisplayLayoutLocalService getService() {
		return _service;
	}

	private static volatile CPDisplayLayoutLocalService _service;

}