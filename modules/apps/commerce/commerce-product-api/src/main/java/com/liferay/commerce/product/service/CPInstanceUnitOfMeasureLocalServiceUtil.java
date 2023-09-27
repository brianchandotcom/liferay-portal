/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for CPInstanceUnitOfMeasure. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPInstanceUnitOfMeasureLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasureLocalService
 * @generated
 */
public class CPInstanceUnitOfMeasureLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPInstanceUnitOfMeasureLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the cp instance unit of measure to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceUnitOfMeasureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpInstanceUnitOfMeasure the cp instance unit of measure
	 * @return the cp instance unit of measure that was added
	 */
	public static CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return getService().addCPInstanceUnitOfMeasure(cpInstanceUnitOfMeasure);
	}

	public static CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
			long userId, long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws PortalException {

		return getService().addCPInstanceUnitOfMeasure(
			userId, cpInstanceId, active, incrementalOrderQuantity, key,
			nameMap, precision, primary, priority, rate, sku);
	}

	public static CPInstanceUnitOfMeasure addOrUpdateCPInstanceUnitOfMeasure(
			long userId, long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws PortalException {

		return getService().addOrUpdateCPInstanceUnitOfMeasure(
			userId, cpInstanceId, active, incrementalOrderQuantity, key,
			nameMap, precision, primary, priority, rate, sku);
	}

	/**
	 * Creates a new cp instance unit of measure with the primary key. Does not add the cp instance unit of measure to the database.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key for the new cp instance unit of measure
	 * @return the new cp instance unit of measure
	 */
	public static CPInstanceUnitOfMeasure createCPInstanceUnitOfMeasure(
		long CPInstanceUnitOfMeasureId) {

		return getService().createCPInstanceUnitOfMeasure(
			CPInstanceUnitOfMeasureId);
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
	 * Deletes the cp instance unit of measure from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceUnitOfMeasureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpInstanceUnitOfMeasure the cp instance unit of measure
	 * @return the cp instance unit of measure that was removed
	 */
	public static CPInstanceUnitOfMeasure deleteCPInstanceUnitOfMeasure(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return getService().deleteCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasure);
	}

	/**
	 * Deletes the cp instance unit of measure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceUnitOfMeasureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure that was removed
	 * @throws PortalException if a cp instance unit of measure with the primary key could not be found
	 */
	public static CPInstanceUnitOfMeasure deleteCPInstanceUnitOfMeasure(
			long CPInstanceUnitOfMeasureId)
		throws PortalException {

		return getService().deleteCPInstanceUnitOfMeasure(
			CPInstanceUnitOfMeasureId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureModelImpl</code>.
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

	public static CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long CPInstanceUnitOfMeasureId) {

		return getService().fetchCPInstanceUnitOfMeasure(
			CPInstanceUnitOfMeasureId);
	}

	public static CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long cpInstanceId, String key) {

		return getService().fetchCPInstanceUnitOfMeasure(cpInstanceId, key);
	}

	public static CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long companyId, String key, String sku) {

		return getService().fetchCPInstanceUnitOfMeasure(companyId, key, sku);
	}

	/**
	 * Returns the cp instance unit of measure with the matching UUID and company.
	 *
	 * @param uuid the cp instance unit of measure's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure
		fetchCPInstanceUnitOfMeasureByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchCPInstanceUnitOfMeasureByUuidAndCompanyId(
			uuid, companyId);
	}

	public static CPInstanceUnitOfMeasure fetchPrimaryCPInstanceUnitOfMeasure(
		long cpInstanceId) {

		return getService().fetchPrimaryCPInstanceUnitOfMeasure(cpInstanceId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<CPInstanceUnitOfMeasure>
		getActiveCPInstanceUnitOfMeasures(long cpInstanceId) {

		return getService().getActiveCPInstanceUnitOfMeasures(cpInstanceId);
	}

	public static int getActiveCPInstanceUnitOfMeasuresCount(
		long cpInstanceId) {

		return getService().getActiveCPInstanceUnitOfMeasuresCount(
			cpInstanceId);
	}

	/**
	 * Returns the cp instance unit of measure with the primary key.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure
	 * @throws PortalException if a cp instance unit of measure with the primary key could not be found
	 */
	public static CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long CPInstanceUnitOfMeasureId)
		throws PortalException {

		return getService().getCPInstanceUnitOfMeasure(
			CPInstanceUnitOfMeasureId);
	}

	public static CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException {

		return getService().getCPInstanceUnitOfMeasure(cpInstanceId, key);
	}

	/**
	 * Returns the cp instance unit of measure with the matching UUID and company.
	 *
	 * @param uuid the cp instance unit of measure's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp instance unit of measure
	 * @throws PortalException if a matching cp instance unit of measure could not be found
	 */
	public static CPInstanceUnitOfMeasure
			getCPInstanceUnitOfMeasureByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().getCPInstanceUnitOfMeasureByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the cp instance unit of measures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of cp instance unit of measures
	 */
	public static List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		int start, int end) {

		return getService().getCPInstanceUnitOfMeasures(start, end);
	}

	public static List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		long cpInstanceId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return getService().getCPInstanceUnitOfMeasures(
			cpInstanceId, start, end, orderByComparator);
	}

	public static List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		long companyId, String sku) {

		return getService().getCPInstanceUnitOfMeasures(companyId, sku);
	}

	/**
	 * Returns the number of cp instance unit of measures.
	 *
	 * @return the number of cp instance unit of measures
	 */
	public static int getCPInstanceUnitOfMeasuresCount() {
		return getService().getCPInstanceUnitOfMeasuresCount();
	}

	public static int getCPInstanceUnitOfMeasuresCount(long cpInstanceId) {
		return getService().getCPInstanceUnitOfMeasuresCount(cpInstanceId);
	}

	public static int getCPInstanceUnitOfMeasuresCount(
		long companyId, String sku) {

		return getService().getCPInstanceUnitOfMeasuresCount(companyId, sku);
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
	 * Updates the cp instance unit of measure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceUnitOfMeasureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpInstanceUnitOfMeasure the cp instance unit of measure
	 * @return the cp instance unit of measure that was updated
	 */
	public static CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return getService().updateCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasure);
	}

	public static CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId, long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws PortalException {

		return getService().updateCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasureId, cpInstanceId, active,
			incrementalOrderQuantity, key, nameMap, precision, primary,
			priority, rate, sku);
	}

	public static CPInstanceUnitOfMeasureLocalService getService() {
		return _service;
	}

	public static void setService(CPInstanceUnitOfMeasureLocalService service) {
		_service = service;
	}

	private static volatile CPInstanceUnitOfMeasureLocalService _service;

}