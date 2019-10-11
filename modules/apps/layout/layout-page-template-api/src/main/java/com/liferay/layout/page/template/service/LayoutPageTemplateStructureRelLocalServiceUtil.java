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
 * Provides the local service utility for LayoutPageTemplateStructureRel. This utility wraps
 * <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateStructureRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRelLocalService
 * @generated
 */
public class LayoutPageTemplateStructureRelLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateStructureRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the layout page template structure rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureRel the layout page template structure rel
	 * @return the layout page template structure rel that was added
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
			addLayoutPageTemplateStructureRel(
				com.liferay.layout.page.template.model.
					LayoutPageTemplateStructureRel
						layoutPageTemplateStructureRel) {

		return getService().addLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureRel);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
				addLayoutPageTemplateStructureRel(
					long userId, long groupId,
					long layoutPageTemplateStructureId,
					long segmentsExperienceId, String data,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addLayoutPageTemplateStructureRel(
			userId, groupId, layoutPageTemplateStructureId,
			segmentsExperienceId, data, serviceContext);
	}

	/**
	 * Creates a new layout page template structure rel with the primary key. Does not add the layout page template structure rel to the database.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key for the new layout page template structure rel
	 * @return the new layout page template structure rel
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
			createLayoutPageTemplateStructureRel(
				long layoutPageTemplateStructureRelId) {

		return getService().createLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureRelId);
	}

	/**
	 * Deletes the layout page template structure rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureRel the layout page template structure rel
	 * @return the layout page template structure rel that was removed
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
			deleteLayoutPageTemplateStructureRel(
				com.liferay.layout.page.template.model.
					LayoutPageTemplateStructureRel
						layoutPageTemplateStructureRel) {

		return getService().deleteLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureRel);
	}

	/**
	 * Deletes the layout page template structure rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the layout page template structure rel
	 * @return the layout page template structure rel that was removed
	 * @throws PortalException if a layout page template structure rel with the primary key could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
				deleteLayoutPageTemplateStructureRel(
					long layoutPageTemplateStructureRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureRelId);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
				deleteLayoutPageTemplateStructureRel(
					long layoutPageTemplateStructureId,
					long segmentsExperienceId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureId, segmentsExperienceId);
	}

	public static void deleteLayoutPageTemplateStructureRels(
		long layoutPageTemplateStructureId) {

		getService().deleteLayoutPageTemplateStructureRels(
			layoutPageTemplateStructureId);
	}

	public static void
		deleteLayoutPageTemplateStructureRelsBySegmentsExperienceId(
			long segmentsExperienceId) {

		getService().
			deleteLayoutPageTemplateStructureRelsBySegmentsExperienceId(
				segmentsExperienceId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureRelModelImpl</code>.
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
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
			fetchLayoutPageTemplateStructureRel(
				long layoutPageTemplateStructureRelId) {

		return getService().fetchLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureRelId);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
			fetchLayoutPageTemplateStructureRel(
				long layoutPageTemplateStructureId, long segmentsExperienceId) {

		return getService().fetchLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureId, segmentsExperienceId);
	}

	/**
	 * Returns the layout page template structure rel matching the UUID and group.
	 *
	 * @param uuid the layout page template structure rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
			fetchLayoutPageTemplateStructureRelByUuidAndGroupId(
				String uuid, long groupId) {

		return getService().fetchLayoutPageTemplateStructureRelByUuidAndGroupId(
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
	 * Returns the layout page template structure rel with the primary key.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the layout page template structure rel
	 * @return the layout page template structure rel
	 * @throws PortalException if a layout page template structure rel with the primary key could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
				getLayoutPageTemplateStructureRel(
					long layoutPageTemplateStructureRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureRelId);
	}

	/**
	 * Returns the layout page template structure rel matching the UUID and group.
	 *
	 * @param uuid the layout page template structure rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template structure rel
	 * @throws PortalException if a matching layout page template structure rel could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
				getLayoutPageTemplateStructureRelByUuidAndGroupId(
					String uuid, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutPageTemplateStructureRelByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the layout page template structure rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of layout page template structure rels
	 */
	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel>
			getLayoutPageTemplateStructureRels(int start, int end) {

		return getService().getLayoutPageTemplateStructureRels(start, end);
	}

	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel>
			getLayoutPageTemplateStructureRels(
				long layoutPageTemplateStructureId) {

		return getService().getLayoutPageTemplateStructureRels(
			layoutPageTemplateStructureId);
	}

	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel>
			getLayoutPageTemplateStructureRelsBySegmentsExperienceId(
				long segmentsExperienceId) {

		return getService().
			getLayoutPageTemplateStructureRelsBySegmentsExperienceId(
				segmentsExperienceId);
	}

	/**
	 * Returns all the layout page template structure rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template structure rels
	 * @param companyId the primary key of the company
	 * @return the matching layout page template structure rels, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel>
			getLayoutPageTemplateStructureRelsByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().
			getLayoutPageTemplateStructureRelsByUuidAndCompanyId(
				uuid, companyId);
	}

	/**
	 * Returns a range of layout page template structure rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template structure rels
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout page template structure rels, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel>
			getLayoutPageTemplateStructureRelsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateStructureRel> orderByComparator) {

		return getService().
			getLayoutPageTemplateStructureRelsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout page template structure rels.
	 *
	 * @return the number of layout page template structure rels
	 */
	public static int getLayoutPageTemplateStructureRelsCount() {
		return getService().getLayoutPageTemplateStructureRelsCount();
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

	/**
	 * Updates the layout page template structure rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureRel the layout page template structure rel
	 * @return the layout page template structure rel that was updated
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
			updateLayoutPageTemplateStructureRel(
				com.liferay.layout.page.template.model.
					LayoutPageTemplateStructureRel
						layoutPageTemplateStructureRel) {

		return getService().updateLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureRel);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel
				updateLayoutPageTemplateStructureRel(
					long layoutPageTemplateStructureId,
					long segmentsExperienceId, String data)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutPageTemplateStructureRel(
			layoutPageTemplateStructureId, segmentsExperienceId, data);
	}

	public static LayoutPageTemplateStructureRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutPageTemplateStructureRelLocalService,
		 LayoutPageTemplateStructureRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutPageTemplateStructureRelLocalService.class);

		ServiceTracker
			<LayoutPageTemplateStructureRelLocalService,
			 LayoutPageTemplateStructureRelLocalService> serviceTracker =
				new ServiceTracker
					<LayoutPageTemplateStructureRelLocalService,
					 LayoutPageTemplateStructureRelLocalService>(
						 bundle.getBundleContext(),
						 LayoutPageTemplateStructureRelLocalService.class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}