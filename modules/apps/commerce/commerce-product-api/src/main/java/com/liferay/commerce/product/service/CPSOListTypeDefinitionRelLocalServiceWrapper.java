/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPSOListTypeDefinitionRel;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link CPSOListTypeDefinitionRelLocalService}.
 *
 * @author Marco Leo
 * @see CPSOListTypeDefinitionRelLocalService
 * @generated
 */
public class CPSOListTypeDefinitionRelLocalServiceWrapper
	implements CPSOListTypeDefinitionRelLocalService,
			   ServiceWrapper<CPSOListTypeDefinitionRelLocalService> {

	public CPSOListTypeDefinitionRelLocalServiceWrapper() {
		this(null);
	}

	public CPSOListTypeDefinitionRelLocalServiceWrapper(
		CPSOListTypeDefinitionRelLocalService
			cpsoListTypeDefinitionRelLocalService) {

		_cpsoListTypeDefinitionRelLocalService =
			cpsoListTypeDefinitionRelLocalService;
	}

	/**
	 * Adds the cpso list type definition rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPSOListTypeDefinitionRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpsoListTypeDefinitionRel the cpso list type definition rel
	 * @return the cpso list type definition rel that was added
	 */
	@Override
	public CPSOListTypeDefinitionRel addCPSOListTypeDefinitionRel(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		return _cpsoListTypeDefinitionRelLocalService.
			addCPSOListTypeDefinitionRel(cpsoListTypeDefinitionRel);
	}

	@Override
	public CPSOListTypeDefinitionRel addCPSOListTypeDefinitionRel(
			long cpSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpsoListTypeDefinitionRelLocalService.
			addCPSOListTypeDefinitionRel(
				cpSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Creates a new cpso list type definition rel with the primary key. Does not add the cpso list type definition rel to the database.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key for the new cpso list type definition rel
	 * @return the new cpso list type definition rel
	 */
	@Override
	public CPSOListTypeDefinitionRel createCPSOListTypeDefinitionRel(
		long CPSOListTypeDefinitionRelId) {

		return _cpsoListTypeDefinitionRelLocalService.
			createCPSOListTypeDefinitionRel(CPSOListTypeDefinitionRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpsoListTypeDefinitionRelLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the cpso list type definition rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPSOListTypeDefinitionRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpsoListTypeDefinitionRel the cpso list type definition rel
	 * @return the cpso list type definition rel that was removed
	 */
	@Override
	public CPSOListTypeDefinitionRel deleteCPSOListTypeDefinitionRel(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		return _cpsoListTypeDefinitionRelLocalService.
			deleteCPSOListTypeDefinitionRel(cpsoListTypeDefinitionRel);
	}

	/**
	 * Deletes the cpso list type definition rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPSOListTypeDefinitionRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the cpso list type definition rel
	 * @return the cpso list type definition rel that was removed
	 * @throws PortalException if a cpso list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSOListTypeDefinitionRel deleteCPSOListTypeDefinitionRel(
			long CPSOListTypeDefinitionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpsoListTypeDefinitionRelLocalService.
			deleteCPSOListTypeDefinitionRel(CPSOListTypeDefinitionRelId);
	}

	@Override
	public void deleteCPSOListTypeDefinitionRel(
			long cpSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpsoListTypeDefinitionRelLocalService.deleteCPSOListTypeDefinitionRel(
			cpSpecificationOptionId, listTypeDefinitionId);
	}

	@Override
	public void deleteCPSOListTypeDefinitionRels(long cpSpecificationOptionId) {
		_cpsoListTypeDefinitionRelLocalService.deleteCPSOListTypeDefinitionRels(
			cpSpecificationOptionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpsoListTypeDefinitionRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cpsoListTypeDefinitionRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cpsoListTypeDefinitionRelLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpsoListTypeDefinitionRelLocalService.dynamicQuery();
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

		return _cpsoListTypeDefinitionRelLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPSOListTypeDefinitionRelModelImpl</code>.
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

		return _cpsoListTypeDefinitionRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPSOListTypeDefinitionRelModelImpl</code>.
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

		return _cpsoListTypeDefinitionRelLocalService.dynamicQuery(
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

		return _cpsoListTypeDefinitionRelLocalService.dynamicQueryCount(
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

		return _cpsoListTypeDefinitionRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public CPSOListTypeDefinitionRel fetchCPSOListTypeDefinitionRel(
		long CPSOListTypeDefinitionRelId) {

		return _cpsoListTypeDefinitionRelLocalService.
			fetchCPSOListTypeDefinitionRel(CPSOListTypeDefinitionRelId);
	}

	@Override
	public CPSOListTypeDefinitionRel fetchCPSOListTypeDefinitionRel(
		long cpSpecificationOptionId, long listTypeDefinitionId) {

		return _cpsoListTypeDefinitionRelLocalService.
			fetchCPSOListTypeDefinitionRel(
				cpSpecificationOptionId, listTypeDefinitionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cpsoListTypeDefinitionRelLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the cpso list type definition rel with the primary key.
	 *
	 * @param CPSOListTypeDefinitionRelId the primary key of the cpso list type definition rel
	 * @return the cpso list type definition rel
	 * @throws PortalException if a cpso list type definition rel with the primary key could not be found
	 */
	@Override
	public CPSOListTypeDefinitionRel getCPSOListTypeDefinitionRel(
			long CPSOListTypeDefinitionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpsoListTypeDefinitionRelLocalService.
			getCPSOListTypeDefinitionRel(CPSOListTypeDefinitionRelId);
	}

	/**
	 * Returns a range of all the cpso list type definition rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPSOListTypeDefinitionRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpso list type definition rels
	 * @param end the upper bound of the range of cpso list type definition rels (not inclusive)
	 * @return the range of cpso list type definition rels
	 */
	@Override
	public java.util.List<CPSOListTypeDefinitionRel>
		getCPSOListTypeDefinitionRels(int start, int end) {

		return _cpsoListTypeDefinitionRelLocalService.
			getCPSOListTypeDefinitionRels(start, end);
	}

	@Override
	public java.util.List<CPSOListTypeDefinitionRel>
		getCPSOListTypeDefinitionRels(long cpSpecificationOptionId) {

		return _cpsoListTypeDefinitionRelLocalService.
			getCPSOListTypeDefinitionRels(cpSpecificationOptionId);
	}

	/**
	 * Returns the number of cpso list type definition rels.
	 *
	 * @return the number of cpso list type definition rels
	 */
	@Override
	public int getCPSOListTypeDefinitionRelsCount() {
		return _cpsoListTypeDefinitionRelLocalService.
			getCPSOListTypeDefinitionRelsCount();
	}

	@Override
	public int getCPSOListTypeDefinitionRelsCount(long listTypeDefinitionId) {
		return _cpsoListTypeDefinitionRelLocalService.
			getCPSOListTypeDefinitionRelsCount(listTypeDefinitionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpsoListTypeDefinitionRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpsoListTypeDefinitionRelLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpsoListTypeDefinitionRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public boolean hasCPSOListTypeDefinitionRel(
		long cpSpecificationOptionId, long listTypeDefinitionId) {

		return _cpsoListTypeDefinitionRelLocalService.
			hasCPSOListTypeDefinitionRel(
				cpSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Updates the cpso list type definition rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPSOListTypeDefinitionRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpsoListTypeDefinitionRel the cpso list type definition rel
	 * @return the cpso list type definition rel that was updated
	 */
	@Override
	public CPSOListTypeDefinitionRel updateCPSOListTypeDefinitionRel(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		return _cpsoListTypeDefinitionRelLocalService.
			updateCPSOListTypeDefinitionRel(cpsoListTypeDefinitionRel);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpsoListTypeDefinitionRelLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<CPSOListTypeDefinitionRel> getCTPersistence() {
		return _cpsoListTypeDefinitionRelLocalService.getCTPersistence();
	}

	@Override
	public Class<CPSOListTypeDefinitionRel> getModelClass() {
		return _cpsoListTypeDefinitionRelLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CPSOListTypeDefinitionRel>, R, E>
				updateUnsafeFunction)
		throws E {

		return _cpsoListTypeDefinitionRelLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public CPSOListTypeDefinitionRelLocalService getWrappedService() {
		return _cpsoListTypeDefinitionRelLocalService;
	}

	@Override
	public void setWrappedService(
		CPSOListTypeDefinitionRelLocalService
			cpsoListTypeDefinitionRelLocalService) {

		_cpsoListTypeDefinitionRelLocalService =
			cpsoListTypeDefinitionRelLocalService;
	}

	private CPSOListTypeDefinitionRelLocalService
		_cpsoListTypeDefinitionRelLocalService;

}