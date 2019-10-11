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

package com.liferay.layout.page.template.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for LayoutPageTemplateStructure. This utility wraps
 * <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateStructureLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureLocalService
 * @generated
 */
public class LayoutPageTemplateStructureLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateStructureLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the layout page template structure to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 * @return the layout page template structure that was added
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			addLayoutPageTemplateStructure(
				com.liferay.layout.page.template.model.
					LayoutPageTemplateStructure layoutPageTemplateStructure) {

		return getService().addLayoutPageTemplateStructure(
			layoutPageTemplateStructure);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				addLayoutPageTemplateStructure(
					long userId, long groupId, long classNameId, long classPK,
					String data,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addLayoutPageTemplateStructure(
			userId, groupId, classNameId, classPK, data, serviceContext);
	}

	/**
	 * Creates a new layout page template structure with the primary key. Does not add the layout page template structure to the database.
	 *
	 * @param layoutPageTemplateStructureId the primary key for the new layout page template structure
	 * @return the new layout page template structure
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			createLayoutPageTemplateStructure(
				long layoutPageTemplateStructureId) {

		return getService().createLayoutPageTemplateStructure(
			layoutPageTemplateStructureId);
	}

	/**
	 * Deletes the layout page template structure from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 * @return the layout page template structure that was removed
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			deleteLayoutPageTemplateStructure(
				com.liferay.layout.page.template.model.
					LayoutPageTemplateStructure layoutPageTemplateStructure) {

		return getService().deleteLayoutPageTemplateStructure(
			layoutPageTemplateStructure);
	}

	/**
	 * Deletes the layout page template structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure that was removed
	 * @throws PortalException if a layout page template structure with the primary key could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				deleteLayoutPageTemplateStructure(
					long layoutPageTemplateStructureId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutPageTemplateStructure(
			layoutPageTemplateStructureId);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				deleteLayoutPageTemplateStructure(
					long groupId, long classNameId, long classPK)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutPageTemplateStructure(
			groupId, classNameId, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureModelImpl</code>.
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

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			fetchLayoutPageTemplateStructure(
				long layoutPageTemplateStructureId) {

		return getService().fetchLayoutPageTemplateStructure(
			layoutPageTemplateStructureId);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			fetchLayoutPageTemplateStructure(
				long groupId, long classNameId, long classPK) {

		return getService().fetchLayoutPageTemplateStructure(
			groupId, classNameId, classPK);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				fetchLayoutPageTemplateStructure(
					long groupId, long classNameId, long classPK,
					boolean rebuildStructure)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchLayoutPageTemplateStructure(
			groupId, classNameId, classPK, rebuildStructure);
	}

	/**
	 * Returns the layout page template structure matching the UUID and group.
	 *
	 * @param uuid the layout page template structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			fetchLayoutPageTemplateStructureByUuidAndGroupId(
				String uuid, long groupId) {

		return getService().fetchLayoutPageTemplateStructureByUuidAndGroupId(
			uuid, groupId);
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

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the layout page template structure with the primary key.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure
	 * @throws PortalException if a layout page template structure with the primary key could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				getLayoutPageTemplateStructure(
					long layoutPageTemplateStructureId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutPageTemplateStructure(
			layoutPageTemplateStructureId);
	}

	/**
	 * Returns the layout page template structure matching the UUID and group.
	 *
	 * @param uuid the layout page template structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template structure
	 * @throws PortalException if a matching layout page template structure could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				getLayoutPageTemplateStructureByUuidAndGroupId(
					String uuid, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutPageTemplateStructureByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the layout page template structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of layout page template structures
	 */
	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructure>
			getLayoutPageTemplateStructures(int start, int end) {

		return getService().getLayoutPageTemplateStructures(start, end);
	}

	/**
	 * Returns all the layout page template structures matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template structures
	 * @param companyId the primary key of the company
	 * @return the matching layout page template structures, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructure>
			getLayoutPageTemplateStructuresByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getLayoutPageTemplateStructuresByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of layout page template structures matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template structures
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout page template structures, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructure>
			getLayoutPageTemplateStructuresByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateStructure> orderByComparator) {

		return getService().getLayoutPageTemplateStructuresByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout page template structures.
	 *
	 * @return the number of layout page template structures
	 */
	public static int getLayoutPageTemplateStructuresCount() {
		return getService().getLayoutPageTemplateStructuresCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				rebuildLayoutPageTemplateStructure(
					long groupId, long classNameId, long classPK)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().rebuildLayoutPageTemplateStructure(
			groupId, classNameId, classPK);
	}

	/**
	 * Updates the layout page template structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 * @return the layout page template structure that was updated
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			updateLayoutPageTemplateStructure(
				com.liferay.layout.page.template.model.
					LayoutPageTemplateStructure layoutPageTemplateStructure) {

		return getService().updateLayoutPageTemplateStructure(
			layoutPageTemplateStructure);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				updateLayoutPageTemplateStructure(
					long groupId, long classNameId, long classPK,
					long segmentsExperienceId, String data)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutPageTemplateStructure(
			groupId, classNameId, classPK, segmentsExperienceId, data);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				updateLayoutPageTemplateStructure(
					long groupId, long classNameId, long classPK, String data)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutPageTemplateStructure(
			groupId, classNameId, classPK, data);
	}

	public static LayoutPageTemplateStructureLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutPageTemplateStructureLocalService,
		 LayoutPageTemplateStructureLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutPageTemplateStructureLocalService.class);

		ServiceTracker
			<LayoutPageTemplateStructureLocalService,
			 LayoutPageTemplateStructureLocalService> serviceTracker =
				new ServiceTracker
					<LayoutPageTemplateStructureLocalService,
					 LayoutPageTemplateStructureLocalService>(
						 bundle.getBundleContext(),
						 LayoutPageTemplateStructureLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}