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

package com.liferay.commerce.pricing.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommercePriceModifier. This utility wraps
 * <code>com.liferay.commerce.pricing.service.impl.CommercePriceModifierLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierLocalService
 * @generated
 */
public class CommercePriceModifierLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.pricing.service.impl.CommercePriceModifierLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce price modifier to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceModifierLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceModifier the commerce price modifier
	 * @return the commerce price modifier that was added
	 */
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
		addCommercePriceModifier(
			com.liferay.commerce.pricing.model.CommercePriceModifier
				commercePriceModifier) {

		return getService().addCommercePriceModifier(commercePriceModifier);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			addCommercePriceModifier(
				long groupId, String title, long commercePriceListId,
				String modifierType, java.math.BigDecimal modifierAmount,
				double priority, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceModifier(
			groupId, title, commercePriceListId, modifierType, modifierAmount,
			priority, active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			addCommercePriceModifier(
				long groupId, String title, String target,
				long commercePriceListId, String modifierType,
				java.math.BigDecimal modifierAmount, double priority,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceModifier(
			groupId, title, target, commercePriceListId, modifierType,
			modifierAmount, priority, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			addCommercePriceModifier(
				long groupId, String title, String target,
				long commercePriceListId, String modifierType,
				java.math.BigDecimal modifierAmount, double priority,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceModifier(
			groupId, title, target, commercePriceListId, modifierType,
			modifierAmount, priority, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, externalReferenceCode,
			neverExpire, serviceContext);
	}

	/**
	 * Creates a new commerce price modifier with the primary key. Does not add the commerce price modifier to the database.
	 *
	 * @param commercePriceModifierId the primary key for the new commerce price modifier
	 * @return the new commerce price modifier
	 */
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
		createCommercePriceModifier(long commercePriceModifierId) {

		return getService().createCommercePriceModifier(
			commercePriceModifierId);
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
	 * Deletes the commerce price modifier from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceModifierLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceModifier the commerce price modifier
	 * @return the commerce price modifier that was removed
	 * @throws PortalException
	 */
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			deleteCommercePriceModifier(
				com.liferay.commerce.pricing.model.CommercePriceModifier
					commercePriceModifier)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePriceModifier(commercePriceModifier);
	}

	/**
	 * Deletes the commerce price modifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceModifierLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceModifierId the primary key of the commerce price modifier
	 * @return the commerce price modifier that was removed
	 * @throws PortalException if a commerce price modifier with the primary key could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			deleteCommercePriceModifier(long commercePriceModifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePriceModifier(
			commercePriceModifierId);
	}

	public static void deleteCommercePriceModifiers(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommercePriceModifiers(companyId);
	}

	public static void deleteCommercePriceModifiersByCommercePriceListId(
			long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommercePriceModifiersByCommercePriceListId(
			commercePriceListId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.pricing.model.impl.CommercePriceModifierModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.pricing.model.impl.CommercePriceModifierModelImpl</code>.
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

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
		fetchCommercePriceModifier(long commercePriceModifierId) {

		return getService().fetchCommercePriceModifier(commercePriceModifierId);
	}

	/**
	 * Returns the commerce price modifier with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce price modifier's external reference code
	 * @return the matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
		fetchCommercePriceModifierByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommercePriceModifierByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce price modifier matching the UUID and group.
	 *
	 * @param uuid the commerce price modifier's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
		fetchCommercePriceModifierByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchCommercePriceModifierByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce price modifier with the primary key.
	 *
	 * @param commercePriceModifierId the primary key of the commerce price modifier
	 * @return the commerce price modifier
	 * @throws PortalException if a commerce price modifier with the primary key could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			getCommercePriceModifier(long commercePriceModifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceModifier(commercePriceModifierId);
	}

	/**
	 * Returns the commerce price modifier matching the UUID and group.
	 *
	 * @param uuid the commerce price modifier's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce price modifier
	 * @throws PortalException if a matching commerce price modifier could not be found
	 */
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			getCommercePriceModifierByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceModifierByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the commerce price modifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.pricing.model.impl.CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of commerce price modifiers
	 */
	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
			getCommercePriceModifiers(int start, int end) {

		return getService().getCommercePriceModifiers(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
			getCommercePriceModifiers(long commercePriceListId) {

		return getService().getCommercePriceModifiers(commercePriceListId);
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
			getCommercePriceModifiers(
				long commercePriceListId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.pricing.model.CommercePriceModifier>
						orderByComparator) {

		return getService().getCommercePriceModifiers(
			commercePriceListId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
			getCommercePriceModifiers(long companyId, String target) {

		return getService().getCommercePriceModifiers(companyId, target);
	}

	/**
	 * Returns all the commerce price modifiers matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce price modifiers
	 * @param companyId the primary key of the company
	 * @return the matching commerce price modifiers, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
			getCommercePriceModifiersByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getCommercePriceModifiersByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of commerce price modifiers matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce price modifiers
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching commerce price modifiers, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
			getCommercePriceModifiersByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.pricing.model.CommercePriceModifier>
						orderByComparator) {

		return getService().getCommercePriceModifiersByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce price modifiers.
	 *
	 * @return the number of commerce price modifiers
	 */
	public static int getCommercePriceModifiersCount() {
		return getService().getCommercePriceModifiersCount();
	}

	public static int getCommercePriceModifiersCount(long commercePriceListId) {
		return getService().getCommercePriceModifiersCount(commercePriceListId);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifier>
			getQualifiedCommercePriceModifiers(
				long commercePriceListId, long cpDefinitionId) {

		return getService().getQualifiedCommercePriceModifiers(
			commercePriceListId, cpDefinitionId);
	}

	/**
	 * Updates the commerce price modifier in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceModifierLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceModifier the commerce price modifier
	 * @return the commerce price modifier that was updated
	 */
	public static com.liferay.commerce.pricing.model.CommercePriceModifier
		updateCommercePriceModifier(
			com.liferay.commerce.pricing.model.CommercePriceModifier
				commercePriceModifier) {

		return getService().updateCommercePriceModifier(commercePriceModifier);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			updateCommercePriceModifier(
				long commercePriceModifierId, long groupId, String title,
				String target, long commercePriceListId, String modifierType,
				java.math.BigDecimal modifierAmount, double priority,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceModifier(
			commercePriceModifierId, groupId, title, target,
			commercePriceListId, modifierType, modifierAmount, priority, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			updateStatus(
				long userId, long commercePriceModifierId, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext,
				java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, commercePriceModifierId, status, serviceContext,
			workflowContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifier
			upsertCommercePriceModifier(
				long userId, long commercePriceModifierId, long groupId,
				String title, String target, long commercePriceListId,
				String modifierType, java.math.BigDecimal modifierAmount,
				double priority, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceModifier(
			userId, commercePriceModifierId, groupId, title, target,
			commercePriceListId, modifierType, modifierAmount, priority, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	public static CommercePriceModifierLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceModifierLocalService, CommercePriceModifierLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceModifierLocalService.class);

		ServiceTracker
			<CommercePriceModifierLocalService,
			 CommercePriceModifierLocalService> serviceTracker =
				new ServiceTracker
					<CommercePriceModifierLocalService,
					 CommercePriceModifierLocalService>(
						 bundle.getBundleContext(),
						 CommercePriceModifierLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}