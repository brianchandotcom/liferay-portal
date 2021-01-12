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

package com.liferay.portal.tools.service.builder.test.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ERCCompanyEntity. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.ERCCompanyEntityLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntityLocalService
 * @generated
 */
public class ERCCompanyEntityLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.ERCCompanyEntityLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the erc company entity to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntity the erc company entity
	 * @return the erc company entity that was added
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
			addERCCompanyEntity(
				com.liferay.portal.tools.service.builder.test.model.
					ERCCompanyEntity ercCompanyEntity) {

		return getService().addERCCompanyEntity(ercCompanyEntity);
	}

	/**
	 * Creates a new erc company entity with the primary key. Does not add the erc company entity to the database.
	 *
	 * @param ercCompanyEntityId the primary key for the new erc company entity
	 * @return the new erc company entity
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
			createERCCompanyEntity(long ercCompanyEntityId) {

		return getService().createERCCompanyEntity(ercCompanyEntityId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the erc company entity from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntity the erc company entity
	 * @return the erc company entity that was removed
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
			deleteERCCompanyEntity(
				com.liferay.portal.tools.service.builder.test.model.
					ERCCompanyEntity ercCompanyEntity) {

		return getService().deleteERCCompanyEntity(ercCompanyEntity);
	}

	/**
	 * Deletes the erc company entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity that was removed
	 * @throws PortalException if a erc company entity with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
				deleteERCCompanyEntity(long ercCompanyEntityId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteERCCompanyEntity(ercCompanyEntityId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntityModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntityModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static
		com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
			fetchERCCompanyEntity(long ercCompanyEntityId) {

		return getService().fetchERCCompanyEntity(ercCompanyEntityId);
	}

	/**
	 * Returns the erc company entity with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the erc company entity's external reference code
	 * @return the matching erc company entity, or <code>null</code> if a matching erc company entity could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
			fetchERCCompanyEntityByReferenceCode(
				long companyId, String externalReferenceCode) {

		return getService().fetchERCCompanyEntityByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the erc company entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entities
	 * @param end the upper bound of the range of erc company entities (not inclusive)
	 * @return the range of erc company entities
	 */
	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity>
			getERCCompanyEntities(int start, int end) {

		return getService().getERCCompanyEntities(start, end);
	}

	/**
	 * Returns the number of erc company entities.
	 *
	 * @return the number of erc company entities
	 */
	public static int getERCCompanyEntitiesCount() {
		return getService().getERCCompanyEntitiesCount();
	}

	/**
	 * Returns the erc company entity with the primary key.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity
	 * @throws PortalException if a erc company entity with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
				getERCCompanyEntity(long ercCompanyEntityId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getERCCompanyEntity(ercCompanyEntityId);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the erc company entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntity the erc company entity
	 * @return the erc company entity that was updated
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
			updateERCCompanyEntity(
				com.liferay.portal.tools.service.builder.test.model.
					ERCCompanyEntity ercCompanyEntity) {

		return getService().updateERCCompanyEntity(ercCompanyEntity);
	}

	public static ERCCompanyEntityLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ERCCompanyEntityLocalService, ERCCompanyEntityLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ERCCompanyEntityLocalService.class);

		ServiceTracker
			<ERCCompanyEntityLocalService, ERCCompanyEntityLocalService>
				serviceTracker =
					new ServiceTracker
						<ERCCompanyEntityLocalService,
						 ERCCompanyEntityLocalService>(
							 bundle.getBundleContext(),
							 ERCCompanyEntityLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}