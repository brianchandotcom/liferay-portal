/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CPDAvailabilityEstimate. This utility wraps
 * <code>com.liferay.commerce.service.impl.CPDAvailabilityEstimateLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimateLocalService
 * @generated
 */
public class CPDAvailabilityEstimateLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CPDAvailabilityEstimateLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the cpd availability estimate to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDAvailabilityEstimateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpdAvailabilityEstimate the cpd availability estimate
	 * @return the cpd availability estimate that was added
	 */
	public static CPDAvailabilityEstimate addCPDAvailabilityEstimate(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		return getService().addCPDAvailabilityEstimate(cpdAvailabilityEstimate);
	}

	/**
	 * Creates a new cpd availability estimate with the primary key. Does not add the cpd availability estimate to the database.
	 *
	 * @param CPDAvailabilityEstimateId the primary key for the new cpd availability estimate
	 * @return the new cpd availability estimate
	 */
	public static CPDAvailabilityEstimate createCPDAvailabilityEstimate(
		long CPDAvailabilityEstimateId) {

		return getService().createCPDAvailabilityEstimate(
			CPDAvailabilityEstimateId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the cpd availability estimate from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDAvailabilityEstimateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpdAvailabilityEstimate the cpd availability estimate
	 * @return the cpd availability estimate that was removed
	 */
	public static CPDAvailabilityEstimate deleteCPDAvailabilityEstimate(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		return getService().deleteCPDAvailabilityEstimate(
			cpdAvailabilityEstimate);
	}

	/**
	 * Deletes the cpd availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDAvailabilityEstimateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	 * @return the cpd availability estimate that was removed
	 * @throws PortalException if a cpd availability estimate with the primary key could not be found
	 */
	public static CPDAvailabilityEstimate deleteCPDAvailabilityEstimate(
			long CPDAvailabilityEstimateId)
		throws PortalException {

		return getService().deleteCPDAvailabilityEstimate(
			CPDAvailabilityEstimateId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static void deleteCPDAvailabilityEstimateByCPDefinitionId(
		long cpDefinitionId) {

		getService().deleteCPDAvailabilityEstimateByCPDefinitionId(
			cpDefinitionId);
	}

	public static void deleteCPDAvailabilityEstimateByCProductId(
		long cProductId) {

		getService().deleteCPDAvailabilityEstimateByCProductId(cProductId);
	}

	public static void deleteCPDAvailabilityEstimates(
		long commerceAvailabilityEstimateId) {

		getService().deleteCPDAvailabilityEstimates(
			commerceAvailabilityEstimateId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CPDAvailabilityEstimate fetchCPDAvailabilityEstimate(
		long CPDAvailabilityEstimateId) {

		return getService().fetchCPDAvailabilityEstimate(
			CPDAvailabilityEstimateId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static CPDAvailabilityEstimate
		fetchCPDAvailabilityEstimateByCPDefinitionId(long cpDefinitionId) {

		return getService().fetchCPDAvailabilityEstimateByCPDefinitionId(
			cpDefinitionId);
	}

	public static CPDAvailabilityEstimate
		fetchCPDAvailabilityEstimateByCProductId(long cProductId) {

		return getService().fetchCPDAvailabilityEstimateByCProductId(
			cProductId);
	}

	/**
	 * Returns the cpd availability estimate with the matching UUID and company.
	 *
	 * @param uuid the cpd availability estimate's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	public static CPDAvailabilityEstimate
		fetchCPDAvailabilityEstimateByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchCPDAvailabilityEstimateByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cpd availability estimate with the primary key.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	 * @return the cpd availability estimate
	 * @throws PortalException if a cpd availability estimate with the primary key could not be found
	 */
	public static CPDAvailabilityEstimate getCPDAvailabilityEstimate(
			long CPDAvailabilityEstimateId)
		throws PortalException {

		return getService().getCPDAvailabilityEstimate(
			CPDAvailabilityEstimateId);
	}

	/**
	 * Returns the cpd availability estimate with the matching UUID and company.
	 *
	 * @param uuid the cpd availability estimate's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cpd availability estimate
	 * @throws PortalException if a matching cpd availability estimate could not be found
	 */
	public static CPDAvailabilityEstimate
			getCPDAvailabilityEstimateByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().getCPDAvailabilityEstimateByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the cpd availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @return the range of cpd availability estimates
	 */
	public static List<CPDAvailabilityEstimate> getCPDAvailabilityEstimates(
		int start, int end) {

		return getService().getCPDAvailabilityEstimates(start, end);
	}

	/**
	 * Returns the number of cpd availability estimates.
	 *
	 * @return the number of cpd availability estimates
	 */
	public static int getCPDAvailabilityEstimatesCount() {
		return getService().getCPDAvailabilityEstimatesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the cpd availability estimate in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDAvailabilityEstimateLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpdAvailabilityEstimate the cpd availability estimate
	 * @return the cpd availability estimate that was updated
	 */
	public static CPDAvailabilityEstimate updateCPDAvailabilityEstimate(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		return getService().updateCPDAvailabilityEstimate(
			cpdAvailabilityEstimate);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static CPDAvailabilityEstimate updateCPDAvailabilityEstimate(
			long cpdAvailabilityEstimateId, long cpDefinitionId,
			long commerceAvailabilityEstimateId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPDAvailabilityEstimate(
			cpdAvailabilityEstimateId, cpDefinitionId,
			commerceAvailabilityEstimateId, serviceContext);
	}

	public static CPDAvailabilityEstimate
			updateCPDAvailabilityEstimateByCProductId(
				long cpdAvailabilityEstimateId, long cProductId,
				long commerceAvailabilityEstimateId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPDAvailabilityEstimateByCProductId(
			cpdAvailabilityEstimateId, cProductId,
			commerceAvailabilityEstimateId, serviceContext);
	}

	public static CPDAvailabilityEstimateLocalService getService() {
		return _service;
	}

	public static void setService(CPDAvailabilityEstimateLocalService service) {
		_service = service;
	}

	private static volatile CPDAvailabilityEstimateLocalService _service;

}