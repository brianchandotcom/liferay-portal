/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d m structure local service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLocalService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLocalServiceImpl
 * @generated
 */
public class DDMStructureLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m structure to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDMStructure(ddmStructure);
	}

	/**
	* Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	*
	* @param structureId the primary key for the new d d m structure
	* @return the new d d m structure
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure createDDMStructure(
		long structureId) {
		return getService().createDDMStructure(structureId);
	}

	/**
	* Deletes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure that was removed
	* @throws PortalException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure deleteDDMStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDDMStructure(structureId);
	}

	/**
	* Deletes the d d m structure from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure deleteDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDDMStructure(ddmStructure);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchDDMStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDDMStructure(structureId);
	}

	/**
	* Returns the d d m structure with the primary key.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure
	* @throws PortalException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructure(structureId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructureByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructures(start, end);
	}

	/**
	* Returns the number of d d m structures.
	*
	* @return the number of d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDMStructuresCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructuresCount();
	}

	/**
	* Updates the d d m structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDMStructure(ddmStructure);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStructure(userId, groupId, classNameId, nameMap,
			descriptionMap, xsd, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStructure(userId, groupId, parentStructureId,
			classNameId, structureKey, nameMap, descriptionMap, xsd,
			storageType, type, serviceContext);
	}

	public static void addStructureResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addStructureResources(structure, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addStructureResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addStructureResources(structure, groupPermissions, guestPermissions);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		long userId, long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .copyStructure(userId, structureId, nameMap, descriptionMap,
			serviceContext);
	}

	public static void deleteStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructure(structure);
	}

	public static void deleteStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructure(structureId);
	}

	public static void deleteStructure(long groupId,
		java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructure(groupId, structureKey);
	}

	public static void deleteStructures(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructures(groupId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchStructure(structureId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchStructure(groupId, structureKey);
	}

	/**
	* @deprecated {@link #getClassStructures(long, long)}
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getClassStructures(classNameId);
	}

	/**
	* @deprecated {@link #getClassStructures(long, long, int, int)}
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getClassStructures(classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getClassStructures(companyId, classNameId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getClassStructures(companyId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getClassStructures(companyId, classNameId, orderByComparator);
	}

	/**
	* @deprecated {@link #getClassStructures(long, long, OrderByComparator)}
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getClassStructures(classNameId, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDLFileEntryTypeStructures(
		long dlFileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileEntryTypeStructures(dlFileEntryTypeId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructure(structureId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructure(groupId, structureKey);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, java.lang.String structureKey,
		boolean includeGlobalStructures)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getStructure(groupId, structureKey, includeGlobalStructures);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructure(
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructure(groupId, name, description);
	}

	/**
	* @deprecated {@link #getStructures}
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntries();
	}

	/**
	* @deprecated {@link #getStructures(long)}
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntries(groupId);
	}

	/**
	* @deprecated {@link #getStructures(long, int, int)}
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntries(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructures();
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructures(groupId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructures(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructures(groupId, classNameId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructures(groupId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getStructures(groupId, classNameId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructures(groupIds);
	}

	public static int getStructuresCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructuresCount(groupId);
	}

	public static int getStructuresCount(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructuresCount(groupId, classNameId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, classNameIds, keywords, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupIds, classNameIds, name,
			description, storageType, type, andOperator, start, end,
			orderByComparator);
	}

	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, keywords);
	}

	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String name,
		java.lang.String description, java.lang.String storageType, int type,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, name,
			description, storageType, type, andOperator);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long structureId, long parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructure(structureId, parentStructureId, nameMap,
			descriptionMap, xsd, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long groupId, long parentStructureId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructure(groupId, parentStructureId, structureKey,
			nameMap, descriptionMap, xsd, serviceContext);
	}

	public static DDMStructureLocalService getService() {
		if (_service == null) {
			_service = (DDMStructureLocalService)PortalBeanLocatorUtil.locate(DDMStructureLocalService.class.getName());

			ReferenceRegistry.registerReference(DDMStructureLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(DDMStructureLocalService service) {
	}

	private static DDMStructureLocalService _service;
}