/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMStructureLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkLocalService
 * @generated
 */
public class DDMStructureLinkLocalServiceWrapper
	implements DDMStructureLinkLocalService,
		ServiceWrapper<DDMStructureLinkLocalService> {
	public DDMStructureLinkLocalServiceWrapper(
		DDMStructureLinkLocalService ddmStructureLinkLocalService) {
		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}

	/**
	* Adds the d d m structure link to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureLink the d d m structure link
	* @return the d d m structure link that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink addDDMStructureLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.addDDMStructureLink(ddmStructureLink);
	}

	/**
	* Creates a new d d m structure link with the primary key. Does not add the d d m structure link to the database.
	*
	* @param structureLinkId the primary key for the new d d m structure link
	* @return the new d d m structure link
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink createDDMStructureLink(
		long structureLinkId) {
		return _ddmStructureLinkLocalService.createDDMStructureLink(structureLinkId);
	}

	/**
	* Deletes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link that was removed
	* @throws PortalException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink deleteDDMStructureLink(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.deleteDDMStructureLink(structureLinkId);
	}

	/**
	* Deletes the d d m structure link from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureLink the d d m structure link
	* @return the d d m structure link that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink deleteDDMStructureLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.deleteDDMStructureLink(ddmStructureLink);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmStructureLinkLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchDDMStructureLink(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.fetchDDMStructureLink(structureLinkId);
	}

	/**
	* Returns the d d m structure link with the primary key.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link
	* @throws PortalException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink getDDMStructureLink(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.getDDMStructureLink(structureLinkId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> getDDMStructureLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.getDDMStructureLinks(start, end);
	}

	/**
	* Returns the number of d d m structure links.
	*
	* @return the number of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getDDMStructureLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.getDDMStructureLinksCount();
	}

	/**
	* Updates the d d m structure link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureLink the d d m structure link
	* @return the d d m structure link that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink updateDDMStructureLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.updateDDMStructureLink(ddmStructureLink);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _ddmStructureLinkLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmStructureLinkLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink addStructureLink(
		long classNameId, long classPK, long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.addStructureLink(classNameId,
			classPK, structureId, serviceContext);
	}

	@Override
	public void deleteClassStructureLink(long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLinkLocalService.deleteClassStructureLink(classPK);
	}

	@Override
	public void deleteStructureLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink structureLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLinkLocalService.deleteStructureLink(structureLink);
	}

	@Override
	public void deleteStructureLink(long structureLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLinkLocalService.deleteStructureLink(structureLinkId);
	}

	@Override
	public void deleteStructureStructureLinks(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLinkLocalService.deleteStructureStructureLinks(structureId);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink getClassStructureLink(
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.getClassStructureLink(classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> getClassStructureLinks(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.getClassStructureLinks(classNameId);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink getStructureLink(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.getStructureLink(structureLinkId);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> getStructureLinks(
		long structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.getStructureLinks(structureId,
			start, end);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink updateStructureLink(
		long structureLinkId, long classNameId, long classPK, long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLinkLocalService.updateStructureLink(structureLinkId,
			classNameId, classPK, structureId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public DDMStructureLinkLocalService getWrappedDDMStructureLinkLocalService() {
		return _ddmStructureLinkLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedDDMStructureLinkLocalService(
		DDMStructureLinkLocalService ddmStructureLinkLocalService) {
		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}

	@Override
	public DDMStructureLinkLocalService getWrappedService() {
		return _ddmStructureLinkLocalService;
	}

	@Override
	public void setWrappedService(
		DDMStructureLinkLocalService ddmStructureLinkLocalService) {
		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}

	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;
}