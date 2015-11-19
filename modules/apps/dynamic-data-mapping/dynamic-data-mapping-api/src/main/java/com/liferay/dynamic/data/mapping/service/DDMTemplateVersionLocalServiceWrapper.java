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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMTemplateVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersionLocalService
 * @generated
 */
@ProviderType
public class DDMTemplateVersionLocalServiceWrapper
	implements DDMTemplateVersionLocalService,
		ServiceWrapper<DDMTemplateVersionLocalService> {
	public DDMTemplateVersionLocalServiceWrapper(
		DDMTemplateVersionLocalService ddmTemplateVersionLocalService) {
		_ddmTemplateVersionLocalService = ddmTemplateVersionLocalService;
	}

	/**
	* Adds the d d m template version to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateVersion the d d m template version
	* @return the d d m template version that was added
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion addDDMTemplateVersion(
		com.liferay.dynamic.data.mapping.model.DDMTemplateVersion ddmTemplateVersion) {
		return _ddmTemplateVersionLocalService.addDDMTemplateVersion(ddmTemplateVersion);
	}

	/**
	* Creates a new d d m template version with the primary key. Does not add the d d m template version to the database.
	*
	* @param templateVersionId the primary key for the new d d m template version
	* @return the new d d m template version
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion createDDMTemplateVersion(
		long templateVersionId) {
		return _ddmTemplateVersionLocalService.createDDMTemplateVersion(templateVersionId);
	}

	/**
	* Deletes the d d m template version from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateVersion the d d m template version
	* @return the d d m template version that was removed
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion deleteDDMTemplateVersion(
		com.liferay.dynamic.data.mapping.model.DDMTemplateVersion ddmTemplateVersion) {
		return _ddmTemplateVersionLocalService.deleteDDMTemplateVersion(ddmTemplateVersion);
	}

	/**
	* Deletes the d d m template version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version that was removed
	* @throws PortalException if a d d m template version with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion deleteDDMTemplateVersion(
		long templateVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplateVersionLocalService.deleteDDMTemplateVersion(templateVersionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplateVersionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmTemplateVersionLocalService.dynamicQuery();
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
		return _ddmTemplateVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmTemplateVersionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmTemplateVersionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _ddmTemplateVersionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ddmTemplateVersionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion fetchDDMTemplateVersion(
		long templateVersionId) {
		return _ddmTemplateVersionLocalService.fetchDDMTemplateVersion(templateVersionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ddmTemplateVersionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the d d m template version with the primary key.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version
	* @throws PortalException if a d d m template version with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getDDMTemplateVersion(
		long templateVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplateVersionLocalService.getDDMTemplateVersion(templateVersionId);
	}

	/**
	* Returns a range of all the d d m template versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @return the range of d d m template versions
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion> getDDMTemplateVersions(
		int start, int end) {
		return _ddmTemplateVersionLocalService.getDDMTemplateVersions(start, end);
	}

	/**
	* Returns the number of d d m template versions.
	*
	* @return the number of d d m template versions
	*/
	@Override
	public int getDDMTemplateVersionsCount() {
		return _ddmTemplateVersionLocalService.getDDMTemplateVersionsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ddmTemplateVersionLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getLatestTemplateVersion(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplateVersionLocalService.getLatestTemplateVersion(templateId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmTemplateVersionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplateVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getTemplateVersion(
		long templateId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplateVersionLocalService.getTemplateVersion(templateId,
			version);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getTemplateVersion(
		long templateVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplateVersionLocalService.getTemplateVersion(templateVersionId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion> getTemplateVersions(
		long templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion> orderByComparator) {
		return _ddmTemplateVersionLocalService.getTemplateVersions(templateId,
			start, end, orderByComparator);
	}

	@Override
	public int getTemplateVersionsCount(long templateId) {
		return _ddmTemplateVersionLocalService.getTemplateVersionsCount(templateId);
	}

	/**
	* Updates the d d m template version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateVersion the d d m template version
	* @return the d d m template version that was updated
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion updateDDMTemplateVersion(
		com.liferay.dynamic.data.mapping.model.DDMTemplateVersion ddmTemplateVersion) {
		return _ddmTemplateVersionLocalService.updateDDMTemplateVersion(ddmTemplateVersion);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDMTemplateVersionLocalService getWrappedDDMTemplateVersionLocalService() {
		return _ddmTemplateVersionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDMTemplateVersionLocalService(
		DDMTemplateVersionLocalService ddmTemplateVersionLocalService) {
		_ddmTemplateVersionLocalService = ddmTemplateVersionLocalService;
	}

	@Override
	public DDMTemplateVersionLocalService getWrappedService() {
		return _ddmTemplateVersionLocalService;
	}

	@Override
	public void setWrappedService(
		DDMTemplateVersionLocalService ddmTemplateVersionLocalService) {
		_ddmTemplateVersionLocalService = ddmTemplateVersionLocalService;
	}

	private DDMTemplateVersionLocalService _ddmTemplateVersionLocalService;
}