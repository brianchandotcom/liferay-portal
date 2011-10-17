/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 * <p>
 * This class is a wrapper for {@link DDMStructureLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureLocalService
 * @generated
 */
public class DDMStructureLocalServiceWrapper implements DDMStructureLocalService,
	ServiceWrapper<DDMStructureLocalService> {
	public DDMStructureLocalServiceWrapper(
		DDMStructureLocalService ddmStructureLocalService) {
		_ddmStructureLocalService = ddmStructureLocalService;
	}

	/**
	* Adds the d d m structure to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.addDDMStructure(ddmStructure);
	}

	/**
	* Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	*
	* @param structureId the primary key for the new d d m structure
	* @return the new d d m structure
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure createDDMStructure(
		long structureId) {
		return _ddmStructureLocalService.createDDMStructure(structureId);
	}

	/**
	* Deletes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureId the primary key of the d d m structure
	* @throws PortalException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteDDMStructure(structureId);
	}

	/**
	* Deletes the d d m structure from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteDDMStructure(ddmStructure);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the d d m structure with the primary key.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure
	* @throws PortalException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getDDMStructure(structureId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the d d m structure with the UUID in the group.
	*
	* @param uuid the UUID of d d m structure
	* @param groupId the group id of the d d m structure
	* @return the d d m structure
	* @throws PortalException if a d d m structure with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructureByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getDDMStructureByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getDDMStructures(start, end);
	}

	/**
	* Returns the number of d d m structures.
	*
	* @return the number of d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int getDDMStructuresCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getDDMStructuresCount();
	}

	/**
	* Updates the d d m structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.updateDDMStructure(ddmStructure);
	}

	/**
	* Updates the d d m structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @param merge whether to merge the d d m structure with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d m structure that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.updateDDMStructure(ddmStructure, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ddmStructureLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmStructureLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd, java.lang.String storageType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.addStructure(userId, groupId,
			classNameId, structureKey, nameMap, descriptionMap, xsd,
			storageType, serviceContext);
	}

	public void addStructureResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.addStructureResources(structure,
			addGroupPermissions, addGuestPermissions);
	}

	public void addStructureResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.addStructureResources(structure,
			groupPermissions, guestPermissions);
	}

	public void deleteStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteStructure(structure);
	}

	public void deleteStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteStructure(structureId);
	}

	public void deleteStructure(long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteStructure(groupId, structureKey);
	}

	public void deleteStructures(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteStructures(groupId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.fetchStructure(structureId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.fetchStructure(groupId, structureKey);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getClassStructures(classNameId);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getClassStructures(classNameId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDLFileEntryTypeStructures(
		long dlFileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getDLFileEntryTypeStructures(dlFileEntryTypeId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructure(structureId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructure(groupId, structureKey);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructure(
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructure(groupId, name, description);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructureEntries();
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructureEntries(groupId);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructureEntries(groupId, start, end);
	}

	public int getStructureEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructureEntriesCount(groupId);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long groupId, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.search(companyId, groupId,
			classNameIds, keywords, start, end, orderByComparator);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long groupId, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.search(companyId, groupId,
			classNameIds, name, description, storageType, andOperator, start,
			end, orderByComparator);
	}

	public int searchCount(long companyId, long groupId, long[] classNameIds,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.searchCount(companyId, groupId,
			classNameIds, keywords);
	}

	public int searchCount(long companyId, long groupId, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.searchCount(companyId, groupId,
			classNameIds, name, description, storageType, andOperator);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.updateStructure(structureId, nameMap,
			descriptionMap, xsd, serviceContext);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long groupId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.updateStructure(groupId, structureKey,
			nameMap, descriptionMap, xsd, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public DDMStructureLocalService getWrappedDDMStructureLocalService() {
		return _ddmStructureLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {
		_ddmStructureLocalService = ddmStructureLocalService;
	}

	public DDMStructureLocalService getWrappedService() {
		return _ddmStructureLocalService;
	}

	public void setWrappedService(
		DDMStructureLocalService ddmStructureLocalService) {
		_ddmStructureLocalService = ddmStructureLocalService;
	}

	private DDMStructureLocalService _ddmStructureLocalService;
}