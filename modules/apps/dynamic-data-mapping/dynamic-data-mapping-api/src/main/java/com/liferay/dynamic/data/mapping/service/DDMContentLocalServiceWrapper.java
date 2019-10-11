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

package com.liferay.dynamic.data.mapping.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMContentLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMContentLocalService
 * @generated
 */
public class DDMContentLocalServiceWrapper
	implements DDMContentLocalService, ServiceWrapper<DDMContentLocalService> {

	public DDMContentLocalServiceWrapper(
		DDMContentLocalService ddmContentLocalService) {

		_ddmContentLocalService = ddmContentLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMContentLocalServiceUtil} to access the ddm content local service. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMContentLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent addContent(
			long userId, long groupId, String name, String description,
			String data,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmContentLocalService.addContent(
			userId, groupId, name, description, data, serviceContext);
	}

	/**
	 * Adds the ddm content to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmContent the ddm content
	 * @return the ddm content that was added
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent addDDMContent(
		com.liferay.dynamic.data.mapping.model.DDMContent ddmContent) {

		return _ddmContentLocalService.addDDMContent(ddmContent);
	}

	/**
	 * Creates a new ddm content with the primary key. Does not add the ddm content to the database.
	 *
	 * @param contentId the primary key for the new ddm content
	 * @return the new ddm content
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent createDDMContent(
		long contentId) {

		return _ddmContentLocalService.createDDMContent(contentId);
	}

	@Override
	public void deleteContent(
		com.liferay.dynamic.data.mapping.model.DDMContent content) {

		_ddmContentLocalService.deleteContent(content);
	}

	@Override
	public void deleteContents(long groupId) {
		_ddmContentLocalService.deleteContents(groupId);
	}

	/**
	 * Deletes the ddm content from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmContent the ddm content
	 * @return the ddm content that was removed
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent deleteDDMContent(
		com.liferay.dynamic.data.mapping.model.DDMContent ddmContent) {

		return _ddmContentLocalService.deleteDDMContent(ddmContent);
	}

	/**
	 * Deletes the ddm content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentId the primary key of the ddm content
	 * @return the ddm content that was removed
	 * @throws PortalException if a ddm content with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent deleteDDMContent(
			long contentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmContentLocalService.deleteDDMContent(contentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmContentLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmContentLocalService.dynamicQuery();
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

		return _ddmContentLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMContentModelImpl</code>.
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

		return _ddmContentLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMContentModelImpl</code>.
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

		return _ddmContentLocalService.dynamicQuery(
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

		return _ddmContentLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ddmContentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent fetchDDMContent(
		long contentId) {

		return _ddmContentLocalService.fetchDDMContent(contentId);
	}

	/**
	 * Returns the ddm content matching the UUID and group.
	 *
	 * @param uuid the ddm content's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm content, or <code>null</code> if a matching ddm content could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent
		fetchDDMContentByUuidAndGroupId(String uuid, long groupId) {

		return _ddmContentLocalService.fetchDDMContentByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ddmContentLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent getContent(
			long contentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmContentLocalService.getContent(contentId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMContent>
		getContents() {

		return _ddmContentLocalService.getContents();
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMContent>
		getContents(long groupId) {

		return _ddmContentLocalService.getContents(groupId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMContent>
		getContents(long groupId, int start, int end) {

		return _ddmContentLocalService.getContents(groupId, start, end);
	}

	@Override
	public int getContentsCount(long groupId) {
		return _ddmContentLocalService.getContentsCount(groupId);
	}

	/**
	 * Returns the ddm content with the primary key.
	 *
	 * @param contentId the primary key of the ddm content
	 * @return the ddm content
	 * @throws PortalException if a ddm content with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent getDDMContent(
			long contentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmContentLocalService.getDDMContent(contentId);
	}

	/**
	 * Returns the ddm content matching the UUID and group.
	 *
	 * @param uuid the ddm content's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm content
	 * @throws PortalException if a matching ddm content could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent
			getDDMContentByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmContentLocalService.getDDMContentByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the ddm contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm contents
	 * @param end the upper bound of the range of ddm contents (not inclusive)
	 * @return the range of ddm contents
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMContent>
		getDDMContents(int start, int end) {

		return _ddmContentLocalService.getDDMContents(start, end);
	}

	/**
	 * Returns all the ddm contents matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm contents
	 * @param companyId the primary key of the company
	 * @return the matching ddm contents, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMContent>
		getDDMContentsByUuidAndCompanyId(String uuid, long companyId) {

		return _ddmContentLocalService.getDDMContentsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of ddm contents matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm contents
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddm contents
	 * @param end the upper bound of the range of ddm contents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddm contents, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMContent>
		getDDMContentsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMContent>
					orderByComparator) {

		return _ddmContentLocalService.getDDMContentsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ddm contents.
	 *
	 * @return the number of ddm contents
	 */
	@Override
	public int getDDMContentsCount() {
		return _ddmContentLocalService.getDDMContentsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _ddmContentLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ddmContentLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmContentLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmContentLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent updateContent(
			long contentId, String name, String description, String data,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmContentLocalService.updateContent(
			contentId, name, description, data, serviceContext);
	}

	/**
	 * Updates the ddm content in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmContent the ddm content
	 * @return the ddm content that was updated
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMContent updateDDMContent(
		com.liferay.dynamic.data.mapping.model.DDMContent ddmContent) {

		return _ddmContentLocalService.updateDDMContent(ddmContent);
	}

	@Override
	public DDMContentLocalService getWrappedService() {
		return _ddmContentLocalService;
	}

	@Override
	public void setWrappedService(
		DDMContentLocalService ddmContentLocalService) {

		_ddmContentLocalService = ddmContentLocalService;
	}

	private DDMContentLocalService _ddmContentLocalService;

}