/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPConfigurationEntrySetting;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CPConfigurationEntrySetting. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPConfigurationEntrySettingLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPConfigurationEntrySettingLocalService
 * @generated
 */
public class CPConfigurationEntrySettingLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPConfigurationEntrySettingLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the cp configuration entry setting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPConfigurationEntrySettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpConfigurationEntrySetting the cp configuration entry setting
	 * @return the cp configuration entry setting that was added
	 */
	public static CPConfigurationEntrySetting addCPConfigurationEntrySetting(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		return getService().addCPConfigurationEntrySetting(
			cpConfigurationEntrySetting);
	}

	public static CPConfigurationEntrySetting addCPConfigurationEntrySetting(
			long userId, long groupId, long cpConfigurationEntryId, int type,
			String value)
		throws PortalException {

		return getService().addCPConfigurationEntrySetting(
			userId, groupId, cpConfigurationEntryId, type, value);
	}

	/**
	 * Creates a new cp configuration entry setting with the primary key. Does not add the cp configuration entry setting to the database.
	 *
	 * @param CPConfigurationEntrySettingId the primary key for the new cp configuration entry setting
	 * @return the new cp configuration entry setting
	 */
	public static CPConfigurationEntrySetting createCPConfigurationEntrySetting(
		long CPConfigurationEntrySettingId) {

		return getService().createCPConfigurationEntrySetting(
			CPConfigurationEntrySettingId);
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
	 * Deletes the cp configuration entry setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPConfigurationEntrySettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpConfigurationEntrySetting the cp configuration entry setting
	 * @return the cp configuration entry setting that was removed
	 */
	public static CPConfigurationEntrySetting deleteCPConfigurationEntrySetting(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		return getService().deleteCPConfigurationEntrySetting(
			cpConfigurationEntrySetting);
	}

	/**
	 * Deletes the cp configuration entry setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPConfigurationEntrySettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting that was removed
	 * @throws PortalException if a cp configuration entry setting with the primary key could not be found
	 */
	public static CPConfigurationEntrySetting deleteCPConfigurationEntrySetting(
			long CPConfigurationEntrySettingId)
		throws PortalException {

		return getService().deleteCPConfigurationEntrySetting(
			CPConfigurationEntrySettingId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPConfigurationEntrySettingModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPConfigurationEntrySettingModelImpl</code>.
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

	public static CPConfigurationEntrySetting fetchCPConfigurationEntrySetting(
		long CPConfigurationEntrySettingId) {

		return getService().fetchCPConfigurationEntrySetting(
			CPConfigurationEntrySettingId);
	}

	public static CPConfigurationEntrySetting fetchCPConfigurationEntrySetting(
		long cpConfigurationEntryId, int type) {

		return getService().fetchCPConfigurationEntrySetting(
			cpConfigurationEntryId, type);
	}

	/**
	 * Returns the cp configuration entry setting matching the UUID and group.
	 *
	 * @param uuid the cp configuration entry setting's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	public static CPConfigurationEntrySetting
		fetchCPConfigurationEntrySettingByUuidAndGroupId(
			String uuid, long groupId) {

		return getService().fetchCPConfigurationEntrySettingByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cp configuration entry setting with the primary key.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting
	 * @throws PortalException if a cp configuration entry setting with the primary key could not be found
	 */
	public static CPConfigurationEntrySetting getCPConfigurationEntrySetting(
			long CPConfigurationEntrySettingId)
		throws PortalException {

		return getService().getCPConfigurationEntrySetting(
			CPConfigurationEntrySettingId);
	}

	/**
	 * Returns the cp configuration entry setting matching the UUID and group.
	 *
	 * @param uuid the cp configuration entry setting's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp configuration entry setting
	 * @throws PortalException if a matching cp configuration entry setting could not be found
	 */
	public static CPConfigurationEntrySetting
			getCPConfigurationEntrySettingByUuidAndGroupId(
				String uuid, long groupId)
		throws PortalException {

		return getService().getCPConfigurationEntrySettingByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the cp configuration entry settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @return the range of cp configuration entry settings
	 */
	public static List<CPConfigurationEntrySetting>
		getCPConfigurationEntrySettings(int start, int end) {

		return getService().getCPConfigurationEntrySettings(start, end);
	}

	/**
	 * Returns all the cp configuration entry settings matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp configuration entry settings
	 * @param companyId the primary key of the company
	 * @return the matching cp configuration entry settings, or an empty list if no matches were found
	 */
	public static List<CPConfigurationEntrySetting>
		getCPConfigurationEntrySettingsByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().getCPConfigurationEntrySettingsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of cp configuration entry settings matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp configuration entry settings
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching cp configuration entry settings, or an empty list if no matches were found
	 */
	public static List<CPConfigurationEntrySetting>
		getCPConfigurationEntrySettingsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<CPConfigurationEntrySetting> orderByComparator) {

		return getService().getCPConfigurationEntrySettingsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of cp configuration entry settings.
	 *
	 * @return the number of cp configuration entry settings
	 */
	public static int getCPConfigurationEntrySettingsCount() {
		return getService().getCPConfigurationEntrySettingsCount();
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
	 * Updates the cp configuration entry setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPConfigurationEntrySettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpConfigurationEntrySetting the cp configuration entry setting
	 * @return the cp configuration entry setting that was updated
	 */
	public static CPConfigurationEntrySetting updateCPConfigurationEntrySetting(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		return getService().updateCPConfigurationEntrySetting(
			cpConfigurationEntrySetting);
	}

	public static CPConfigurationEntrySettingLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<CPConfigurationEntrySettingLocalService>
		_serviceSnapshot = new Snapshot<>(
			CPConfigurationEntrySettingLocalServiceUtil.class,
			CPConfigurationEntrySettingLocalService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:1723863365