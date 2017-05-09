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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPDefinitionMedia. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionMediaLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPDefinitionMediaLocalService
 * @see com.liferay.commerce.product.service.base.CPDefinitionMediaLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionMediaLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionMediaLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionMediaLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp definition media to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionMedia the cp definition media
	* @return the cp definition media that was added
	*/
	public static com.liferay.commerce.product.model.CPDefinitionMedia addCPDefinitionMedia(
		com.liferay.commerce.product.model.CPDefinitionMedia cpDefinitionMedia) {
		return getService().addCPDefinitionMedia(cpDefinitionMedia);
	}

	public static com.liferay.commerce.product.model.CPDefinitionMedia addCPDefinitionMedia(
		long cpDefinitionId, long fileEntryId, java.lang.String ddmContent,
		int priority, long cpMediaTypeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionMedia(cpDefinitionId, fileEntryId,
			ddmContent, priority, cpMediaTypeId, serviceContext);
	}

	/**
	* Creates a new cp definition media with the primary key. Does not add the cp definition media to the database.
	*
	* @param CPDefinitionMediaId the primary key for the new cp definition media
	* @return the new cp definition media
	*/
	public static com.liferay.commerce.product.model.CPDefinitionMedia createCPDefinitionMedia(
		long CPDefinitionMediaId) {
		return getService().createCPDefinitionMedia(CPDefinitionMediaId);
	}

	/**
	* Deletes the cp definition media from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionMedia the cp definition media
	* @return the cp definition media that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CPDefinitionMedia deleteCPDefinitionMedia(
		com.liferay.commerce.product.model.CPDefinitionMedia cpDefinitionMedia)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinitionMedia(cpDefinitionMedia);
	}

	/**
	* Deletes the cp definition media with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media that was removed
	* @throws PortalException if a cp definition media with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionMedia deleteCPDefinitionMedia(
		long CPDefinitionMediaId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinitionMedia(CPDefinitionMediaId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionMedia fetchCPDefinitionMedia(
		long CPDefinitionMediaId) {
		return getService().fetchCPDefinitionMedia(CPDefinitionMediaId);
	}

	/**
	* Returns the cp definition media matching the UUID and group.
	*
	* @param uuid the cp definition media's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionMedia fetchCPDefinitionMediaByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchCPDefinitionMediaByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the cp definition media with the primary key.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media
	* @throws PortalException if a cp definition media with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionMedia getCPDefinitionMedia(
		long CPDefinitionMediaId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionMedia(CPDefinitionMediaId);
	}

	/**
	* Returns the cp definition media matching the UUID and group.
	*
	* @param uuid the cp definition media's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition media
	* @throws PortalException if a matching cp definition media could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinitionMedia getCPDefinitionMediaByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionMediaByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the cp definition media in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionMedia the cp definition media
	* @return the cp definition media that was updated
	*/
	public static com.liferay.commerce.product.model.CPDefinitionMedia updateCPDefinitionMedia(
		com.liferay.commerce.product.model.CPDefinitionMedia cpDefinitionMedia) {
		return getService().updateCPDefinitionMedia(cpDefinitionMedia);
	}

	public static com.liferay.commerce.product.model.CPDefinitionMedia updateCPDefinitionMedia(
		long cpDefinitionMediaId, java.lang.String ddmContent, int priority,
		long cpMediaTypeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionMedia(cpDefinitionMediaId, ddmContent,
			priority, cpMediaTypeId, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPMediaType(cpMediaTypeId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of cp definition medias.
	*
	* @return the number of cp definition medias
	*/
	public static int getCPDefinitionMediasCount() {
		return getService().getCPDefinitionMediasCount();
	}

	public static int getDefinitionMediasCount(long cpDefinitionId) {
		return getService().getDefinitionMediasCount(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns a range of all the cp definition medias.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @return the range of cp definition medias
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getCPDefinitionMedias(
		int start, int end) {
		return getService().getCPDefinitionMedias(start, end);
	}

	/**
	* Returns all the cp definition medias matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition medias
	* @param companyId the primary key of the company
	* @return the matching cp definition medias, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getCPDefinitionMediasByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCPDefinitionMediasByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of cp definition medias matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition medias
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition medias, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getCPDefinitionMediasByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionMedia> orderByComparator) {
		return getService()
				   .getCPDefinitionMediasByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getDefinitionMedias(
		long cpDefinitionId, int start, int end) {
		return getService().getDefinitionMedias(cpDefinitionId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getDefinitionMedias(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionMedia> orderByComparator) {
		return getService()
				   .getDefinitionMedias(cpDefinitionId, start, end,
			orderByComparator);
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

	public static CPDefinitionMediaLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionMediaLocalService, CPDefinitionMediaLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CPDefinitionMediaLocalService.class);
}