/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link CPInstanceUnitOfMeasureLocalService}.
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasureLocalService
 * @generated
 */
public class CPInstanceUnitOfMeasureLocalServiceWrapper
	implements CPInstanceUnitOfMeasureLocalService,
			   ServiceWrapper<CPInstanceUnitOfMeasureLocalService> {

	public CPInstanceUnitOfMeasureLocalServiceWrapper() {
		this(null);
	}

	public CPInstanceUnitOfMeasureLocalServiceWrapper(
		CPInstanceUnitOfMeasureLocalService
			cpInstanceUnitOfMeasureLocalService) {

		_cpInstanceUnitOfMeasureLocalService =
			cpInstanceUnitOfMeasureLocalService;
	}

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
	@Override
	public CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return _cpInstanceUnitOfMeasureLocalService.addCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasure);
	}

	@Override
	public CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
			long userId, long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			java.util.Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.addCPInstanceUnitOfMeasure(
			userId, cpInstanceId, active, incrementalOrderQuantity, key,
			nameMap, precision, primary, priority, rate, sku);
	}

	@Override
	public CPInstanceUnitOfMeasure addOrUpdateCPInstanceUnitOfMeasure(
			long userId, long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			java.util.Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.
			addOrUpdateCPInstanceUnitOfMeasure(
				userId, cpInstanceId, active, incrementalOrderQuantity, key,
				nameMap, precision, primary, priority, rate, sku);
	}

	/**
	 * Creates a new cp instance unit of measure with the primary key. Does not add the cp instance unit of measure to the database.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key for the new cp instance unit of measure
	 * @return the new cp instance unit of measure
	 */
	@Override
	public CPInstanceUnitOfMeasure createCPInstanceUnitOfMeasure(
		long CPInstanceUnitOfMeasureId) {

		return _cpInstanceUnitOfMeasureLocalService.
			createCPInstanceUnitOfMeasure(CPInstanceUnitOfMeasureId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public CPInstanceUnitOfMeasure deleteCPInstanceUnitOfMeasure(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return _cpInstanceUnitOfMeasureLocalService.
			deleteCPInstanceUnitOfMeasure(cpInstanceUnitOfMeasure);
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
	@Override
	public CPInstanceUnitOfMeasure deleteCPInstanceUnitOfMeasure(
			long CPInstanceUnitOfMeasureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.
			deleteCPInstanceUnitOfMeasure(CPInstanceUnitOfMeasureId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cpInstanceUnitOfMeasureLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cpInstanceUnitOfMeasureLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpInstanceUnitOfMeasureLocalService.dynamicQuery();
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

		return _cpInstanceUnitOfMeasureLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _cpInstanceUnitOfMeasureLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _cpInstanceUnitOfMeasureLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _cpInstanceUnitOfMeasureLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _cpInstanceUnitOfMeasureLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long CPInstanceUnitOfMeasureId) {

		return _cpInstanceUnitOfMeasureLocalService.
			fetchCPInstanceUnitOfMeasure(CPInstanceUnitOfMeasureId);
	}

	@Override
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long cpInstanceId, String key) {

		return _cpInstanceUnitOfMeasureLocalService.
			fetchCPInstanceUnitOfMeasure(cpInstanceId, key);
	}

	@Override
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long companyId, String key, String sku) {

		return _cpInstanceUnitOfMeasureLocalService.
			fetchCPInstanceUnitOfMeasure(companyId, key, sku);
	}

	/**
	 * Returns the cp instance unit of measure with the matching UUID and company.
	 *
	 * @param uuid the cp instance unit of measure's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure
		fetchCPInstanceUnitOfMeasureByUuidAndCompanyId(
			String uuid, long companyId) {

		return _cpInstanceUnitOfMeasureLocalService.
			fetchCPInstanceUnitOfMeasureByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public CPInstanceUnitOfMeasure fetchPrimaryCPInstanceUnitOfMeasure(
		long cpInstanceId) {

		return _cpInstanceUnitOfMeasureLocalService.
			fetchPrimaryCPInstanceUnitOfMeasure(cpInstanceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cpInstanceUnitOfMeasureLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<CPInstanceUnitOfMeasure>
		getActiveCPInstanceUnitOfMeasures(long cpInstanceId) {

		return _cpInstanceUnitOfMeasureLocalService.
			getActiveCPInstanceUnitOfMeasures(cpInstanceId);
	}

	@Override
	public int getActiveCPInstanceUnitOfMeasuresCount(long cpInstanceId) {
		return _cpInstanceUnitOfMeasureLocalService.
			getActiveCPInstanceUnitOfMeasuresCount(cpInstanceId);
	}

	/**
	 * Returns the cp instance unit of measure with the primary key.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure
	 * @throws PortalException if a cp instance unit of measure with the primary key could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long CPInstanceUnitOfMeasureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasure(
			CPInstanceUnitOfMeasureId);
	}

	@Override
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasure(
			cpInstanceId, key);
	}

	/**
	 * Returns the cp instance unit of measure with the matching UUID and company.
	 *
	 * @param uuid the cp instance unit of measure's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp instance unit of measure
	 * @throws PortalException if a matching cp instance unit of measure could not be found
	 */
	@Override
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasureByUuidAndCompanyId(
			String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.
			getCPInstanceUnitOfMeasureByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		int start, int end) {

		return _cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasures(
			start, end);
	}

	@Override
	public java.util.List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		long cpInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPInstanceUnitOfMeasure> orderByComparator) {

		return _cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasures(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		long companyId, String sku) {

		return _cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasures(
			companyId, sku);
	}

	/**
	 * Returns the number of cp instance unit of measures.
	 *
	 * @return the number of cp instance unit of measures
	 */
	@Override
	public int getCPInstanceUnitOfMeasuresCount() {
		return _cpInstanceUnitOfMeasureLocalService.
			getCPInstanceUnitOfMeasuresCount();
	}

	@Override
	public int getCPInstanceUnitOfMeasuresCount(long cpInstanceId) {
		return _cpInstanceUnitOfMeasureLocalService.
			getCPInstanceUnitOfMeasuresCount(cpInstanceId);
	}

	@Override
	public int getCPInstanceUnitOfMeasuresCount(long companyId, String sku) {
		return _cpInstanceUnitOfMeasureLocalService.
			getCPInstanceUnitOfMeasuresCount(companyId, sku);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _cpInstanceUnitOfMeasureLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpInstanceUnitOfMeasureLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpInstanceUnitOfMeasureLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

		return _cpInstanceUnitOfMeasureLocalService.
			updateCPInstanceUnitOfMeasure(cpInstanceUnitOfMeasure);
	}

	@Override
	public CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId, long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			java.util.Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureLocalService.
			updateCPInstanceUnitOfMeasure(
				cpInstanceUnitOfMeasureId, cpInstanceId, active,
				incrementalOrderQuantity, key, nameMap, precision, primary,
				priority, rate, sku);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpInstanceUnitOfMeasureLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<CPInstanceUnitOfMeasure> getCTPersistence() {
		return _cpInstanceUnitOfMeasureLocalService.getCTPersistence();
	}

	@Override
	public Class<CPInstanceUnitOfMeasure> getModelClass() {
		return _cpInstanceUnitOfMeasureLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CPInstanceUnitOfMeasure>, R, E>
				updateUnsafeFunction)
		throws E {

		return _cpInstanceUnitOfMeasureLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public CPInstanceUnitOfMeasureLocalService getWrappedService() {
		return _cpInstanceUnitOfMeasureLocalService;
	}

	@Override
	public void setWrappedService(
		CPInstanceUnitOfMeasureLocalService
			cpInstanceUnitOfMeasureLocalService) {

		_cpInstanceUnitOfMeasureLocalService =
			cpInstanceUnitOfMeasureLocalService;
	}

	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

}