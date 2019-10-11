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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutPageTemplateStructureLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureLocalService
 * @generated
 */
public class LayoutPageTemplateStructureLocalServiceWrapper
	implements LayoutPageTemplateStructureLocalService,
			   ServiceWrapper<LayoutPageTemplateStructureLocalService> {

	public LayoutPageTemplateStructureLocalServiceWrapper(
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService) {

		_layoutPageTemplateStructureLocalService =
			layoutPageTemplateStructureLocalService;
	}

	/**
	 * Adds the layout page template structure to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 * @return the layout page template structure that was added
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
		addLayoutPageTemplateStructure(
			com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				layoutPageTemplateStructure) {

		return _layoutPageTemplateStructureLocalService.
			addLayoutPageTemplateStructure(layoutPageTemplateStructure);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			addLayoutPageTemplateStructure(
				long userId, long groupId, long classNameId, long classPK,
				String data,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.
			addLayoutPageTemplateStructure(
				userId, groupId, classNameId, classPK, data, serviceContext);
	}

	/**
	 * Creates a new layout page template structure with the primary key. Does not add the layout page template structure to the database.
	 *
	 * @param layoutPageTemplateStructureId the primary key for the new layout page template structure
	 * @return the new layout page template structure
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
		createLayoutPageTemplateStructure(long layoutPageTemplateStructureId) {

		return _layoutPageTemplateStructureLocalService.
			createLayoutPageTemplateStructure(layoutPageTemplateStructureId);
	}

	/**
	 * Deletes the layout page template structure from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 * @return the layout page template structure that was removed
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
		deleteLayoutPageTemplateStructure(
			com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				layoutPageTemplateStructure) {

		return _layoutPageTemplateStructureLocalService.
			deleteLayoutPageTemplateStructure(layoutPageTemplateStructure);
	}

	/**
	 * Deletes the layout page template structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure that was removed
	 * @throws PortalException if a layout page template structure with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			deleteLayoutPageTemplateStructure(
				long layoutPageTemplateStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.
			deleteLayoutPageTemplateStructure(layoutPageTemplateStructureId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			deleteLayoutPageTemplateStructure(
				long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.
			deleteLayoutPageTemplateStructure(groupId, classNameId, classPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutPageTemplateStructureLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _layoutPageTemplateStructureLocalService.dynamicQuery(
			dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _layoutPageTemplateStructureLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _layoutPageTemplateStructureLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _layoutPageTemplateStructureLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _layoutPageTemplateStructureLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
		fetchLayoutPageTemplateStructure(long layoutPageTemplateStructureId) {

		return _layoutPageTemplateStructureLocalService.
			fetchLayoutPageTemplateStructure(layoutPageTemplateStructureId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
		fetchLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK) {

		return _layoutPageTemplateStructureLocalService.
			fetchLayoutPageTemplateStructure(groupId, classNameId, classPK);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			fetchLayoutPageTemplateStructure(
				long groupId, long classNameId, long classPK,
				boolean rebuildStructure)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.
			fetchLayoutPageTemplateStructure(
				groupId, classNameId, classPK, rebuildStructure);
	}

	/**
	 * Returns the layout page template structure matching the UUID and group.
	 *
	 * @param uuid the layout page template structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
		fetchLayoutPageTemplateStructureByUuidAndGroupId(
			String uuid, long groupId) {

		return _layoutPageTemplateStructureLocalService.
			fetchLayoutPageTemplateStructureByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _layoutPageTemplateStructureLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _layoutPageTemplateStructureLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _layoutPageTemplateStructureLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the layout page template structure with the primary key.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure
	 * @throws PortalException if a layout page template structure with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			getLayoutPageTemplateStructure(long layoutPageTemplateStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.
			getLayoutPageTemplateStructure(layoutPageTemplateStructureId);
	}

	/**
	 * Returns the layout page template structure matching the UUID and group.
	 *
	 * @param uuid the layout page template structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template structure
	 * @throws PortalException if a matching layout page template structure could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			getLayoutPageTemplateStructureByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.
			getLayoutPageTemplateStructureByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructure>
			getLayoutPageTemplateStructures(int start, int end) {

		return _layoutPageTemplateStructureLocalService.
			getLayoutPageTemplateStructures(start, end);
	}

	/**
	 * Returns all the layout page template structures matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template structures
	 * @param companyId the primary key of the company
	 * @return the matching layout page template structures, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructure>
			getLayoutPageTemplateStructuresByUuidAndCompanyId(
				String uuid, long companyId) {

		return _layoutPageTemplateStructureLocalService.
			getLayoutPageTemplateStructuresByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateStructure>
			getLayoutPageTemplateStructuresByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateStructure> orderByComparator) {

		return _layoutPageTemplateStructureLocalService.
			getLayoutPageTemplateStructuresByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout page template structures.
	 *
	 * @return the number of layout page template structures
	 */
	@Override
	public int getLayoutPageTemplateStructuresCount() {
		return _layoutPageTemplateStructureLocalService.
			getLayoutPageTemplateStructuresCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutPageTemplateStructureLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			rebuildLayoutPageTemplateStructure(
				long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.
			rebuildLayoutPageTemplateStructure(groupId, classNameId, classPK);
	}

	/**
	 * Updates the layout page template structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 * @return the layout page template structure that was updated
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
		updateLayoutPageTemplateStructure(
			com.liferay.layout.page.template.model.LayoutPageTemplateStructure
				layoutPageTemplateStructure) {

		return _layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(layoutPageTemplateStructure);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			updateLayoutPageTemplateStructure(
				long groupId, long classNameId, long classPK,
				long segmentsExperienceId, String data)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				groupId, classNameId, classPK, segmentsExperienceId, data);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			updateLayoutPageTemplateStructure(
				long groupId, long classNameId, long classPK, String data)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				groupId, classNameId, classPK, data);
	}

	@Override
	public LayoutPageTemplateStructureLocalService getWrappedService() {
		return _layoutPageTemplateStructureLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService) {

		_layoutPageTemplateStructureLocalService =
			layoutPageTemplateStructureLocalService;
	}

	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

}