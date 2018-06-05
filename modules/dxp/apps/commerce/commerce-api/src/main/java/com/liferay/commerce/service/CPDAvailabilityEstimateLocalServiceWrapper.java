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
 * Provides a wrapper for {@link CPDAvailabilityEstimateLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimateLocalService
 * @generated
 */
@ProviderType
public class CPDAvailabilityEstimateLocalServiceWrapper
	implements CPDAvailabilityEstimateLocalService,
		ServiceWrapper<CPDAvailabilityEstimateLocalService> {
	public CPDAvailabilityEstimateLocalServiceWrapper(
		CPDAvailabilityEstimateLocalService cpdAvailabilityEstimateLocalService) {
		_cpdAvailabilityEstimateLocalService = cpdAvailabilityEstimateLocalService;
	}

	/**
	* Adds the cpd availability estimate to the database. Also notifies the appropriate model listeners.
	*
	* @param cpdAvailabilityEstimate the cpd availability estimate
	* @return the cpd availability estimate that was added
	*/
	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate addCPDAvailabilityEstimate(
		com.liferay.commerce.model.CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		return _cpdAvailabilityEstimateLocalService.addCPDAvailabilityEstimate(cpdAvailabilityEstimate);
	}

	/**
	* Creates a new cpd availability estimate with the primary key. Does not add the cpd availability estimate to the database.
	*
	* @param CPDAvailabilityEstimateId the primary key for the new cpd availability estimate
	* @return the new cpd availability estimate
	*/
	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate createCPDAvailabilityEstimate(
		long CPDAvailabilityEstimateId) {
		return _cpdAvailabilityEstimateLocalService.createCPDAvailabilityEstimate(CPDAvailabilityEstimateId);
	}

	/**
	* Deletes the cpd availability estimate from the database. Also notifies the appropriate model listeners.
	*
	* @param cpdAvailabilityEstimate the cpd availability estimate
	* @return the cpd availability estimate that was removed
	*/
	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate deleteCPDAvailabilityEstimate(
		com.liferay.commerce.model.CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		return _cpdAvailabilityEstimateLocalService.deleteCPDAvailabilityEstimate(cpdAvailabilityEstimate);
	}

	/**
	* Deletes the cpd availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	* @return the cpd availability estimate that was removed
	* @throws PortalException if a cpd availability estimate with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate deleteCPDAvailabilityEstimate(
		long CPDAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpdAvailabilityEstimateLocalService.deleteCPDAvailabilityEstimate(CPDAvailabilityEstimateId);
	}

	@Override
	public void deleteCPDAvailabilityEstimateByCPDefinitionId(
		long cpDefinitionId) {
		_cpdAvailabilityEstimateLocalService.deleteCPDAvailabilityEstimateByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public void deleteCPDAvailabilityEstimates(
		long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpdAvailabilityEstimateLocalService.deleteCPDAvailabilityEstimates(commerceAvailabilityEstimateId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpdAvailabilityEstimateLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpdAvailabilityEstimateLocalService.dynamicQuery();
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
		return _cpdAvailabilityEstimateLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpdAvailabilityEstimateLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpdAvailabilityEstimateLocalService.dynamicQuery(dynamicQuery,
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
		return _cpdAvailabilityEstimateLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpdAvailabilityEstimateLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate fetchCPDAvailabilityEstimate(
		long CPDAvailabilityEstimateId) {
		return _cpdAvailabilityEstimateLocalService.fetchCPDAvailabilityEstimate(CPDAvailabilityEstimateId);
	}

	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate fetchCPDAvailabilityEstimateByCPDefinitionId(
		long cpDefinitionId) {
		return _cpdAvailabilityEstimateLocalService.fetchCPDAvailabilityEstimateByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the cpd availability estimate matching the UUID and group.
	*
	* @param uuid the cpd availability estimate's UUID
	* @param groupId the primary key of the group
	* @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	*/
	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate fetchCPDAvailabilityEstimateByUuidAndGroupId(
		String uuid, long groupId) {
		return _cpdAvailabilityEstimateLocalService.fetchCPDAvailabilityEstimateByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpdAvailabilityEstimateLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cpd availability estimate with the primary key.
	*
	* @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	* @return the cpd availability estimate
	* @throws PortalException if a cpd availability estimate with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate getCPDAvailabilityEstimate(
		long CPDAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpdAvailabilityEstimateLocalService.getCPDAvailabilityEstimate(CPDAvailabilityEstimateId);
	}

	/**
	* Returns the cpd availability estimate matching the UUID and group.
	*
	* @param uuid the cpd availability estimate's UUID
	* @param groupId the primary key of the group
	* @return the matching cpd availability estimate
	* @throws PortalException if a matching cpd availability estimate could not be found
	*/
	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate getCPDAvailabilityEstimateByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpdAvailabilityEstimateLocalService.getCPDAvailabilityEstimateByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the cpd availability estimates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @return the range of cpd availability estimates
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CPDAvailabilityEstimate> getCPDAvailabilityEstimates(
		int start, int end) {
		return _cpdAvailabilityEstimateLocalService.getCPDAvailabilityEstimates(start,
			end);
	}

	/**
	* Returns all the cpd availability estimates matching the UUID and company.
	*
	* @param uuid the UUID of the cpd availability estimates
	* @param companyId the primary key of the company
	* @return the matching cpd availability estimates, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CPDAvailabilityEstimate> getCPDAvailabilityEstimatesByUuidAndCompanyId(
		String uuid, long companyId) {
		return _cpdAvailabilityEstimateLocalService.getCPDAvailabilityEstimatesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cpd availability estimates matching the UUID and company.
	*
	* @param uuid the UUID of the cpd availability estimates
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cpd availability estimates
	* @param end the upper bound of the range of cpd availability estimates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cpd availability estimates, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CPDAvailabilityEstimate> getCPDAvailabilityEstimatesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CPDAvailabilityEstimate> orderByComparator) {
		return _cpdAvailabilityEstimateLocalService.getCPDAvailabilityEstimatesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cpd availability estimates.
	*
	* @return the number of cpd availability estimates
	*/
	@Override
	public int getCPDAvailabilityEstimatesCount() {
		return _cpdAvailabilityEstimateLocalService.getCPDAvailabilityEstimatesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpdAvailabilityEstimateLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpdAvailabilityEstimateLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpdAvailabilityEstimateLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpdAvailabilityEstimateLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cpd availability estimate in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpdAvailabilityEstimate the cpd availability estimate
	* @return the cpd availability estimate that was updated
	*/
	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate updateCPDAvailabilityEstimate(
		com.liferay.commerce.model.CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		return _cpdAvailabilityEstimateLocalService.updateCPDAvailabilityEstimate(cpdAvailabilityEstimate);
	}

	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate updateCPDAvailabilityEstimate(
		long cpdAvailabilityEstimateId, long cpDefinitionId,
		long commerceAvailabilityEstimateId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpdAvailabilityEstimateLocalService.updateCPDAvailabilityEstimate(cpdAvailabilityEstimateId,
			cpDefinitionId, commerceAvailabilityEstimateId, serviceContext);
	}

	@Override
	public CPDAvailabilityEstimateLocalService getWrappedService() {
		return _cpdAvailabilityEstimateLocalService;
	}

	@Override
	public void setWrappedService(
		CPDAvailabilityEstimateLocalService cpdAvailabilityEstimateLocalService) {
		_cpdAvailabilityEstimateLocalService = cpdAvailabilityEstimateLocalService;
	}

	private CPDAvailabilityEstimateLocalService _cpdAvailabilityEstimateLocalService;
}