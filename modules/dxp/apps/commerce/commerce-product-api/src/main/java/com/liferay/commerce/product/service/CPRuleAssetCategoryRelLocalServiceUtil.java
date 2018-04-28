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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPRuleAssetCategoryRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPRuleAssetCategoryRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPRuleAssetCategoryRelLocalService
 * @see com.liferay.commerce.product.service.base.CPRuleAssetCategoryRelLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPRuleAssetCategoryRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPRuleAssetCategoryRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPRuleAssetCategoryRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp rule asset category rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cpRuleAssetCategoryRel the cp rule asset category rel
	* @return the cp rule asset category rel that was added
	*/
	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel addCPRuleAssetCategoryRel(
		com.liferay.commerce.product.model.CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		return getService().addCPRuleAssetCategoryRel(cpRuleAssetCategoryRel);
	}

	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel addCPRuleAssetCategoryRel(
		long cpRuleId, long assetCategoryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPRuleAssetCategoryRel(cpRuleId, assetCategoryId,
			serviceContext);
	}

	/**
	* Creates a new cp rule asset category rel with the primary key. Does not add the cp rule asset category rel to the database.
	*
	* @param CPRuleAssetCategoryRelId the primary key for the new cp rule asset category rel
	* @return the new cp rule asset category rel
	*/
	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel createCPRuleAssetCategoryRel(
		long CPRuleAssetCategoryRelId) {
		return getService()
				   .createCPRuleAssetCategoryRel(CPRuleAssetCategoryRelId);
	}

	/**
	* Deletes the cp rule asset category rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cpRuleAssetCategoryRel the cp rule asset category rel
	* @return the cp rule asset category rel that was removed
	*/
	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel deleteCPRuleAssetCategoryRel(
		com.liferay.commerce.product.model.CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		return getService().deleteCPRuleAssetCategoryRel(cpRuleAssetCategoryRel);
	}

	/**
	* Deletes the cp rule asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	* @return the cp rule asset category rel that was removed
	* @throws PortalException if a cp rule asset category rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel deleteCPRuleAssetCategoryRel(
		long CPRuleAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPRuleAssetCategoryRel(CPRuleAssetCategoryRelId);
	}

	public static void deleteCPRuleAssetCategoryRelsByAssetCategoryId(
		long assetCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCPRuleAssetCategoryRelsByAssetCategoryId(assetCategoryId);
	}

	public static void deleteCPRuleAssetCategoryRelsByCPRuleId(long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPRuleAssetCategoryRelsByCPRuleId(cpRuleId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel fetchCPRuleAssetCategoryRel(
		long CPRuleAssetCategoryRelId) {
		return getService().fetchCPRuleAssetCategoryRel(CPRuleAssetCategoryRelId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static long[] getAssetCategoryIds(long cpRuleId) {
		return getService().getAssetCategoryIds(cpRuleId);
	}

	/**
	* Returns the cp rule asset category rel with the primary key.
	*
	* @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	* @return the cp rule asset category rel
	* @throws PortalException if a cp rule asset category rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel getCPRuleAssetCategoryRel(
		long CPRuleAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPRuleAssetCategoryRel(CPRuleAssetCategoryRelId);
	}

	/**
	* Returns a range of all the cp rule asset category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @return the range of cp rule asset category rels
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPRuleAssetCategoryRel> getCPRuleAssetCategoryRels(
		int start, int end) {
		return getService().getCPRuleAssetCategoryRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPRuleAssetCategoryRel> getCPRuleAssetCategoryRels(
		long cpRuleId) {
		return getService().getCPRuleAssetCategoryRels(cpRuleId);
	}

	/**
	* Returns the number of cp rule asset category rels.
	*
	* @return the number of cp rule asset category rels
	*/
	public static int getCPRuleAssetCategoryRelsCount() {
		return getService().getCPRuleAssetCategoryRelsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp rule asset category rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpRuleAssetCategoryRel the cp rule asset category rel
	* @return the cp rule asset category rel that was updated
	*/
	public static com.liferay.commerce.product.model.CPRuleAssetCategoryRel updateCPRuleAssetCategoryRel(
		com.liferay.commerce.product.model.CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		return getService().updateCPRuleAssetCategoryRel(cpRuleAssetCategoryRel);
	}

	public static CPRuleAssetCategoryRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPRuleAssetCategoryRelLocalService, CPRuleAssetCategoryRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPRuleAssetCategoryRelLocalService.class);

		ServiceTracker<CPRuleAssetCategoryRelLocalService, CPRuleAssetCategoryRelLocalService> serviceTracker =
			new ServiceTracker<CPRuleAssetCategoryRelLocalService, CPRuleAssetCategoryRelLocalService>(bundle.getBundleContext(),
				CPRuleAssetCategoryRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}