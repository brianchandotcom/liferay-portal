/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceAvailabilityEstimateLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimateLocalService
 * @generated
 */
@ProviderType
public class CommerceAvailabilityEstimateLocalServiceWrapper
	implements CommerceAvailabilityEstimateLocalService,
		ServiceWrapper<CommerceAvailabilityEstimateLocalService> {
	public CommerceAvailabilityEstimateLocalServiceWrapper(
		CommerceAvailabilityEstimateLocalService commerceAvailabilityEstimateLocalService) {
		_commerceAvailabilityEstimateLocalService = commerceAvailabilityEstimateLocalService;
	}

	/**
	* Adds the commerce availability estimate to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityEstimate the commerce availability estimate
	* @return the commerce availability estimate that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate addCommerceAvailabilityEstimate(
		com.liferay.commerce.model.CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		return _commerceAvailabilityEstimateLocalService.addCommerceAvailabilityEstimate(commerceAvailabilityEstimate);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate addCommerceAvailabilityEstimate(
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateLocalService.addCommerceAvailabilityEstimate(titleMap,
			priority, serviceContext);
	}

	/**
	* Creates a new commerce availability estimate with the primary key. Does not add the commerce availability estimate to the database.
	*
	* @param commerceAvailabilityEstimateId the primary key for the new commerce availability estimate
	* @return the new commerce availability estimate
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate createCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId) {
		return _commerceAvailabilityEstimateLocalService.createCommerceAvailabilityEstimate(commerceAvailabilityEstimateId);
	}

	/**
	* Deletes the commerce availability estimate from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityEstimate the commerce availability estimate
	* @return the commerce availability estimate that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate deleteCommerceAvailabilityEstimate(
		com.liferay.commerce.model.CommerceAvailabilityEstimate commerceAvailabilityEstimate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateLocalService.deleteCommerceAvailabilityEstimate(commerceAvailabilityEstimate);
	}

	/**
	* Deletes the commerce availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	* @return the commerce availability estimate that was removed
	* @throws PortalException if a commerce availability estimate with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate deleteCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateLocalService.deleteCommerceAvailabilityEstimate(commerceAvailabilityEstimateId);
	}

	@Override
	public void deleteCommerceAvailabilityEstimates(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceAvailabilityEstimateLocalService.deleteCommerceAvailabilityEstimates(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAvailabilityEstimateLocalService.dynamicQuery();
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
		return _commerceAvailabilityEstimateLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceAvailabilityEstimateLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceAvailabilityEstimateLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceAvailabilityEstimateLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceAvailabilityEstimateLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate fetchCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId) {
		return _commerceAvailabilityEstimateLocalService.fetchCommerceAvailabilityEstimate(commerceAvailabilityEstimateId);
	}

	/**
	* Returns the commerce availability estimate matching the UUID and group.
	*
	* @param uuid the commerce availability estimate's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate fetchCommerceAvailabilityEstimateByUuidAndGroupId(
		String uuid, long groupId) {
		return _commerceAvailabilityEstimateLocalService.fetchCommerceAvailabilityEstimateByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceAvailabilityEstimateLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce availability estimate with the primary key.
	*
	* @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	* @return the commerce availability estimate
	* @throws PortalException if a commerce availability estimate with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate getCommerceAvailabilityEstimate(
		long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateLocalService.getCommerceAvailabilityEstimate(commerceAvailabilityEstimateId);
	}

	/**
	* Returns the commerce availability estimate matching the UUID and group.
	*
	* @param uuid the commerce availability estimate's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce availability estimate
	* @throws PortalException if a matching commerce availability estimate could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate getCommerceAvailabilityEstimateByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateLocalService.getCommerceAvailabilityEstimateByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce availability estimates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @return the range of commerce availability estimates
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityEstimate> getCommerceAvailabilityEstimates(
		int start, int end) {
		return _commerceAvailabilityEstimateLocalService.getCommerceAvailabilityEstimates(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityEstimate> getCommerceAvailabilityEstimates(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityEstimate> orderByComparator) {
		return _commerceAvailabilityEstimateLocalService.getCommerceAvailabilityEstimates(groupId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce availability estimates matching the UUID and company.
	*
	* @param uuid the UUID of the commerce availability estimates
	* @param companyId the primary key of the company
	* @return the matching commerce availability estimates, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityEstimate> getCommerceAvailabilityEstimatesByUuidAndCompanyId(
		String uuid, long companyId) {
		return _commerceAvailabilityEstimateLocalService.getCommerceAvailabilityEstimatesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce availability estimates matching the UUID and company.
	*
	* @param uuid the UUID of the commerce availability estimates
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce availability estimates
	* @param end the upper bound of the range of commerce availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce availability estimates, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityEstimate> getCommerceAvailabilityEstimatesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityEstimate> orderByComparator) {
		return _commerceAvailabilityEstimateLocalService.getCommerceAvailabilityEstimatesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce availability estimates.
	*
	* @return the number of commerce availability estimates
	*/
	@Override
	public int getCommerceAvailabilityEstimatesCount() {
		return _commerceAvailabilityEstimateLocalService.getCommerceAvailabilityEstimatesCount();
	}

	@Override
	public int getCommerceAvailabilityEstimatesCount(long groupId) {
		return _commerceAvailabilityEstimateLocalService.getCommerceAvailabilityEstimatesCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceAvailabilityEstimateLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceAvailabilityEstimateLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAvailabilityEstimateLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce availability estimate in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityEstimate the commerce availability estimate
	* @return the commerce availability estimate that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate updateCommerceAvailabilityEstimate(
		com.liferay.commerce.model.CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		return _commerceAvailabilityEstimateLocalService.updateCommerceAvailabilityEstimate(commerceAvailabilityEstimate);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityEstimate updateCommerceAvailabilityEstimate(
		long commerceAvailabilityId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityEstimateLocalService.updateCommerceAvailabilityEstimate(commerceAvailabilityId,
			titleMap, priority, serviceContext);
	}

	@Override
	public CommerceAvailabilityEstimateLocalService getWrappedService() {
		return _commerceAvailabilityEstimateLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAvailabilityEstimateLocalService commerceAvailabilityEstimateLocalService) {
		_commerceAvailabilityEstimateLocalService = commerceAvailabilityEstimateLocalService;
	}

	private CommerceAvailabilityEstimateLocalService _commerceAvailabilityEstimateLocalService;
}