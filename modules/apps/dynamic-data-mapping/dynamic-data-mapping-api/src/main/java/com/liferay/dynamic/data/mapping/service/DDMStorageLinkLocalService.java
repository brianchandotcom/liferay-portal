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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for DDMStorageLink. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkLocalServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMStorageLinkLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMStorageLinkLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMStorageLinkLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStorageLinkLocalServiceUtil} to access the d d m storage link local service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMStorageLinkLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the d d m storage link to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStorageLink the d d m storage link
	* @return the d d m storage link that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink addDDMStorageLink(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink ddmStorageLink);

	public com.liferay.dynamic.data.mapping.model.DDMStorageLink addStorageLink(
		long classNameId, long classPK, long structureId,
		com.liferay.portal.service.ServiceContext serviceContext);

	/**
	* Creates a new d d m storage link with the primary key. Does not add the d d m storage link to the database.
	*
	* @param storageLinkId the primary key for the new d d m storage link
	* @return the new d d m storage link
	*/
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink createDDMStorageLink(
		long storageLinkId);

	public void deleteClassStorageLink(long classPK);

	/**
	* Deletes the d d m storage link from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStorageLink the d d m storage link
	* @return the d d m storage link that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink deleteDDMStorageLink(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink ddmStorageLink);

	/**
	* Deletes the d d m storage link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link that was removed
	* @throws PortalException if a d d m storage link with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink deleteDDMStorageLink(
		long storageLinkId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public void deleteStorageLink(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink storageLink);

	public void deleteStorageLink(long storageLinkId);

	public void deleteStructureStorageLinks(long structureId);

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink fetchDDMStorageLink(
		long storageLinkId);

	/**
	* Returns the d d m storage link with the matching UUID and company.
	*
	* @param uuid the d d m storage link's UUID
	* @param companyId the primary key of the company
	* @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink fetchDDMStorageLinkByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink getClassStorageLink(
		long classPK) throws PortalException;

	/**
	* Returns the d d m storage link with the primary key.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link
	* @throws PortalException if a d d m storage link with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink getDDMStorageLink(
		long storageLinkId) throws PortalException;

	/**
	* Returns the d d m storage link with the matching UUID and company.
	*
	* @param uuid the d d m storage link's UUID
	* @param companyId the primary key of the company
	* @return the matching d d m storage link
	* @throws PortalException if a matching d d m storage link could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink getDDMStorageLinkByUuidAndCompanyId(
		java.lang.String uuid, long companyId) throws PortalException;

	/**
	* Returns a range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of d d m storage links
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStorageLink> getDDMStorageLinks(
		int start, int end);

	/**
	* Returns the number of d d m storage links.
	*
	* @return the number of d d m storage links
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDMStorageLinksCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink getStorageLink(
		long storageLinkId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStorageLink> getStructureStorageLinks(
		long structureId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStructureStorageLinksCount(long structureId);

	/**
	* Updates the d d m storage link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStorageLink the d d m storage link
	* @return the d d m storage link that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.dynamic.data.mapping.model.DDMStorageLink updateDDMStorageLink(
		com.liferay.dynamic.data.mapping.model.DDMStorageLink ddmStorageLink);

	public com.liferay.dynamic.data.mapping.model.DDMStorageLink updateStorageLink(
		long storageLinkId, long classNameId, long classPK)
		throws PortalException;
}