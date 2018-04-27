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

package com.liferay.commerce.product.type.virtual.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPDefinitionVirtualSetting. This utility wraps
 * {@link com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingLocalService
 * @see com.liferay.commerce.product.type.virtual.service.base.CPDefinitionVirtualSettingLocalServiceBaseImpl
 * @see com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionVirtualSettingLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.type.virtual.service.impl.CPDefinitionVirtualSettingLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp definition virtual setting to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionVirtualSetting the cp definition virtual setting
	* @return the cp definition virtual setting that was added
	*/
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
		com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		return getService()
				   .addCPDefinitionVirtualSetting(cpDefinitionVirtualSetting);
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
		long cpDefinitionId, long fileEntryId, String url,
		String activationStatus, long duration, int maxUsages,
		boolean useSample, long sampleFileEntryId, String sampleUrl,
		boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionVirtualSetting(cpDefinitionId, fileEntryId,
			url, activationStatus, duration, maxUsages, useSample,
			sampleFileEntryId, sampleUrl, termsOfUseRequired,
			termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
			serviceContext);
	}

	/**
	* Creates a new cp definition virtual setting with the primary key. Does not add the cp definition virtual setting to the database.
	*
	* @param CPDefinitionVirtualSettingId the primary key for the new cp definition virtual setting
	* @return the new cp definition virtual setting
	*/
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting createCPDefinitionVirtualSetting(
		long CPDefinitionVirtualSettingId) {
		return getService()
				   .createCPDefinitionVirtualSetting(CPDefinitionVirtualSettingId);
	}

	/**
	* Deletes the cp definition virtual setting from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionVirtualSetting the cp definition virtual setting
	* @return the cp definition virtual setting that was removed
	*/
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
		com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		return getService()
				   .deleteCPDefinitionVirtualSetting(cpDefinitionVirtualSetting);
	}

	/**
	* Deletes the cp definition virtual setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting that was removed
	* @throws PortalException if a cp definition virtual setting with the primary key could not be found
	*/
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
		long CPDefinitionVirtualSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCPDefinitionVirtualSetting(CPDefinitionVirtualSettingId);
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting deleteCPDefinitionVirtualSettingByCPDefinitionId(
		long cpDefinitionId) {
		return getService()
				   .deleteCPDefinitionVirtualSettingByCPDefinitionId(cpDefinitionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting fetchCPDefinitionVirtualSetting(
		long CPDefinitionVirtualSettingId) {
		return getService()
				   .fetchCPDefinitionVirtualSetting(CPDefinitionVirtualSettingId);
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting fetchCPDefinitionVirtualSettingByCPDefinitionId(
		long cpDefinitionId) {
		return getService()
				   .fetchCPDefinitionVirtualSettingByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the cp definition virtual setting matching the UUID and group.
	*
	* @param uuid the cp definition virtual setting's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition virtual setting, or <code>null</code> if a matching cp definition virtual setting could not be found
	*/
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting fetchCPDefinitionVirtualSettingByUuidAndGroupId(
		String uuid, long groupId) {
		return getService()
				   .fetchCPDefinitionVirtualSettingByUuidAndGroupId(uuid,
			groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the cp definition virtual setting with the primary key.
	*
	* @param CPDefinitionVirtualSettingId the primary key of the cp definition virtual setting
	* @return the cp definition virtual setting
	* @throws PortalException if a cp definition virtual setting with the primary key could not be found
	*/
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting getCPDefinitionVirtualSetting(
		long CPDefinitionVirtualSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionVirtualSetting(CPDefinitionVirtualSettingId);
	}

	/**
	* Returns the cp definition virtual setting matching the UUID and group.
	*
	* @param uuid the cp definition virtual setting's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition virtual setting
	* @throws PortalException if a matching cp definition virtual setting could not be found
	*/
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting getCPDefinitionVirtualSettingByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionVirtualSettingByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the cp definition virtual settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @return the range of cp definition virtual settings
	*/
	public static java.util.List<com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting> getCPDefinitionVirtualSettings(
		int start, int end) {
		return getService().getCPDefinitionVirtualSettings(start, end);
	}

	/**
	* Returns all the cp definition virtual settings matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition virtual settings
	* @param companyId the primary key of the company
	* @return the matching cp definition virtual settings, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting> getCPDefinitionVirtualSettingsByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService()
				   .getCPDefinitionVirtualSettingsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition virtual settings matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition virtual settings
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition virtual settings
	* @param end the upper bound of the range of cp definition virtual settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition virtual settings, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting> getCPDefinitionVirtualSettingsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting> orderByComparator) {
		return getService()
				   .getCPDefinitionVirtualSettingsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition virtual settings.
	*
	* @return the number of cp definition virtual settings
	*/
	public static int getCPDefinitionVirtualSettingsCount() {
		return getService().getCPDefinitionVirtualSettingsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
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
	* Updates the cp definition virtual setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionVirtualSetting the cp definition virtual setting
	* @return the cp definition virtual setting that was updated
	*/
	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
		com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		return getService()
				   .updateCPDefinitionVirtualSetting(cpDefinitionVirtualSetting);
	}

	public static com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
		long cpDefinitionVirtualSettingId, long fileEntryId, String url,
		String activationStatus, long duration, int maxUsages,
		boolean useSample, long sampleFileEntryId, String sampleUrl,
		boolean termsOfUseRequired,
		java.util.Map<java.util.Locale, String> termsOfUseContentMap,
		long termsOfUseJournalArticleResourcePrimKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId,
			fileEntryId, url, activationStatus, duration, maxUsages, useSample,
			sampleFileEntryId, sampleUrl, termsOfUseRequired,
			termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
			serviceContext);
	}

	public static CPDefinitionVirtualSettingLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionVirtualSettingLocalService, CPDefinitionVirtualSettingLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionVirtualSettingLocalService.class);

		ServiceTracker<CPDefinitionVirtualSettingLocalService, CPDefinitionVirtualSettingLocalService> serviceTracker =
			new ServiceTracker<CPDefinitionVirtualSettingLocalService, CPDefinitionVirtualSettingLocalService>(bundle.getBundleContext(),
				CPDefinitionVirtualSettingLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}