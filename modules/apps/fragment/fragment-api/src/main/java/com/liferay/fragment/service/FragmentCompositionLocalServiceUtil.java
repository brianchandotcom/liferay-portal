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

package com.liferay.fragment.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for FragmentComposition. This utility wraps
 * <code>com.liferay.fragment.service.impl.FragmentCompositionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCompositionLocalService
 * @generated
 */
public class FragmentCompositionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentCompositionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the fragment composition to the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentComposition the fragment composition
	 * @return the fragment composition that was added
	 */
	public static com.liferay.fragment.model.FragmentComposition
		addFragmentComposition(
			com.liferay.fragment.model.FragmentComposition
				fragmentComposition) {

		return getService().addFragmentComposition(fragmentComposition);
	}

	public static com.liferay.fragment.model.FragmentComposition
			addFragmentComposition(
				long userId, long groupId, long fragmentCollectionId,
				String fragmentCompositionKey, String name, String description,
				String data, long previewFileEntryId, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentComposition(
			userId, groupId, fragmentCollectionId, fragmentCompositionKey, name,
			description, data, previewFileEntryId, status, serviceContext);
	}

	/**
	 * Creates a new fragment composition with the primary key. Does not add the fragment composition to the database.
	 *
	 * @param fragmentCompositionId the primary key for the new fragment composition
	 * @return the new fragment composition
	 */
	public static com.liferay.fragment.model.FragmentComposition
		createFragmentComposition(long fragmentCompositionId) {

		return getService().createFragmentComposition(fragmentCompositionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the fragment composition from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentComposition the fragment composition
	 * @return the fragment composition that was removed
	 * @throws PortalException
	 */
	public static com.liferay.fragment.model.FragmentComposition
			deleteFragmentComposition(
				com.liferay.fragment.model.FragmentComposition
					fragmentComposition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFragmentComposition(fragmentComposition);
	}

	/**
	 * Deletes the fragment composition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentCompositionId the primary key of the fragment composition
	 * @return the fragment composition that was removed
	 * @throws PortalException if a fragment composition with the primary key could not be found
	 */
	public static com.liferay.fragment.model.FragmentComposition
			deleteFragmentComposition(long fragmentCompositionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFragmentComposition(fragmentCompositionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentCompositionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentCompositionModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.fragment.model.FragmentComposition
		fetchFragmentComposition(long fragmentCompositionId) {

		return getService().fetchFragmentComposition(fragmentCompositionId);
	}

	public static com.liferay.fragment.model.FragmentComposition
		fetchFragmentComposition(long groupId, String fragmentCompositionKey) {

		return getService().fetchFragmentComposition(
			groupId, fragmentCompositionKey);
	}

	/**
	 * Returns the fragment composition matching the UUID and group.
	 *
	 * @param uuid the fragment composition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public static com.liferay.fragment.model.FragmentComposition
		fetchFragmentCompositionByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchFragmentCompositionByUuidAndGroupId(
			uuid, groupId);
	}

	public static String generateFragmentCompositionKey(
		long groupId, String name) {

		return getService().generateFragmentCompositionKey(groupId, name);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns the fragment composition with the primary key.
	 *
	 * @param fragmentCompositionId the primary key of the fragment composition
	 * @return the fragment composition
	 * @throws PortalException if a fragment composition with the primary key could not be found
	 */
	public static com.liferay.fragment.model.FragmentComposition
			getFragmentComposition(long fragmentCompositionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFragmentComposition(fragmentCompositionId);
	}

	/**
	 * Returns the fragment composition matching the UUID and group.
	 *
	 * @param uuid the fragment composition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment composition
	 * @throws PortalException if a matching fragment composition could not be found
	 */
	public static com.liferay.fragment.model.FragmentComposition
			getFragmentCompositionByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFragmentCompositionByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the fragment compositions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of fragment compositions
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(int start, int end) {

		return getService().getFragmentCompositions(start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(long fragmentCollectionId) {

		return getService().getFragmentCompositions(fragmentCollectionId);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(long fragmentCollectionId, int start, int end) {

		return getService().getFragmentCompositions(
			fragmentCollectionId, start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(
			long groupId, long fragmentCollectionId, int status) {

		return getService().getFragmentCompositions(
			groupId, fragmentCollectionId, status);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(
			long groupId, long fragmentCollectionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentComposition>
					orderByComparator) {

		return getService().getFragmentCompositions(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(
			long groupId, long fragmentCollectionId, String name, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentComposition>
					orderByComparator) {

		return getService().getFragmentCompositions(
			groupId, fragmentCollectionId, name, start, end, orderByComparator);
	}

	/**
	 * Returns all the fragment compositions matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment compositions
	 * @param companyId the primary key of the company
	 * @return the matching fragment compositions, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositionsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getFragmentCompositionsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of fragment compositions matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment compositions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching fragment compositions, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositionsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentComposition>
					orderByComparator) {

		return getService().getFragmentCompositionsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of fragment compositions.
	 *
	 * @return the number of fragment compositions
	 */
	public static int getFragmentCompositionsCount() {
		return getService().getFragmentCompositionsCount();
	}

	public static int getFragmentCompositionsCount(long fragmentCollectionId) {
		return getService().getFragmentCompositionsCount(fragmentCollectionId);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static String[] getTempFileNames(
			long userId, long groupId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTempFileNames(userId, groupId, folderName);
	}

	public static com.liferay.fragment.model.FragmentComposition
			moveFragmentComposition(
				long fragmentCompositionId, long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveFragmentComposition(
			fragmentCompositionId, fragmentCollectionId);
	}

	/**
	 * Updates the fragment composition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentComposition the fragment composition
	 * @return the fragment composition that was updated
	 */
	public static com.liferay.fragment.model.FragmentComposition
		updateFragmentComposition(
			com.liferay.fragment.model.FragmentComposition
				fragmentComposition) {

		return getService().updateFragmentComposition(fragmentComposition);
	}

	public static com.liferay.fragment.model.FragmentComposition
			updateFragmentComposition(
				long fragmentCompositionId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentComposition(
			fragmentCompositionId, previewFileEntryId);
	}

	public static com.liferay.fragment.model.FragmentComposition
			updateFragmentComposition(
				long userId, long fragmentCompositionId, String name,
				String description, String data, long previewFileEntryId,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentComposition(
			userId, fragmentCompositionId, name, description, data,
			previewFileEntryId, status);
	}

	public static com.liferay.fragment.model.FragmentComposition
			updateFragmentComposition(long fragmentCompositionId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentComposition(
			fragmentCompositionId, name);
	}

	public static FragmentCompositionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<FragmentCompositionLocalService, FragmentCompositionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FragmentCompositionLocalService.class);

		ServiceTracker
			<FragmentCompositionLocalService, FragmentCompositionLocalService>
				serviceTracker =
					new ServiceTracker
						<FragmentCompositionLocalService,
						 FragmentCompositionLocalService>(
							 bundle.getBundleContext(),
							 FragmentCompositionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}