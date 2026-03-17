/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPConfigurationEntrySetting;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link CPConfigurationEntrySettingLocalService}.
 *
 * @author Marco Leo
 * @see CPConfigurationEntrySettingLocalService
 * @generated
 */
public class CPConfigurationEntrySettingLocalServiceWrapper
	implements CPConfigurationEntrySettingLocalService,
			   ServiceWrapper<CPConfigurationEntrySettingLocalService> {

	public CPConfigurationEntrySettingLocalServiceWrapper() {
		this(null);
	}

	public CPConfigurationEntrySettingLocalServiceWrapper(
		CPConfigurationEntrySettingLocalService
			cpConfigurationEntrySettingLocalService) {

		_cpConfigurationEntrySettingLocalService =
			cpConfigurationEntrySettingLocalService;
	}

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
	@Override
	public CPConfigurationEntrySetting addCPConfigurationEntrySetting(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		return _cpConfigurationEntrySettingLocalService.
			addCPConfigurationEntrySetting(cpConfigurationEntrySetting);
	}

	@Override
	public CPConfigurationEntrySetting addCPConfigurationEntrySetting(
			long userId, long groupId, long cpConfigurationEntryId, int type,
			String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntrySettingLocalService.
			addCPConfigurationEntrySetting(
				userId, groupId, cpConfigurationEntryId, type, value);
	}

	/**
	 * Creates a new cp configuration entry setting with the primary key. Does not add the cp configuration entry setting to the database.
	 *
	 * @param CPConfigurationEntrySettingId the primary key for the new cp configuration entry setting
	 * @return the new cp configuration entry setting
	 */
	@Override
	public CPConfigurationEntrySetting createCPConfigurationEntrySetting(
		long CPConfigurationEntrySettingId) {

		return _cpConfigurationEntrySettingLocalService.
			createCPConfigurationEntrySetting(CPConfigurationEntrySettingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntrySettingLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public CPConfigurationEntrySetting deleteCPConfigurationEntrySetting(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		return _cpConfigurationEntrySettingLocalService.
			deleteCPConfigurationEntrySetting(cpConfigurationEntrySetting);
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
	@Override
	public CPConfigurationEntrySetting deleteCPConfigurationEntrySetting(
			long CPConfigurationEntrySettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntrySettingLocalService.
			deleteCPConfigurationEntrySetting(CPConfigurationEntrySettingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntrySettingLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cpConfigurationEntrySettingLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cpConfigurationEntrySettingLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpConfigurationEntrySettingLocalService.dynamicQuery();
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

		return _cpConfigurationEntrySettingLocalService.dynamicQuery(
			dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _cpConfigurationEntrySettingLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _cpConfigurationEntrySettingLocalService.dynamicQuery(
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

		return _cpConfigurationEntrySettingLocalService.dynamicQueryCount(
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

		return _cpConfigurationEntrySettingLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public CPConfigurationEntrySetting fetchCPConfigurationEntrySetting(
		long CPConfigurationEntrySettingId) {

		return _cpConfigurationEntrySettingLocalService.
			fetchCPConfigurationEntrySetting(CPConfigurationEntrySettingId);
	}

	@Override
	public CPConfigurationEntrySetting fetchCPConfigurationEntrySetting(
		long cpConfigurationEntryId, int type) {

		return _cpConfigurationEntrySettingLocalService.
			fetchCPConfigurationEntrySetting(cpConfigurationEntryId, type);
	}

	/**
	 * Returns the cp configuration entry setting matching the UUID and group.
	 *
	 * @param uuid the cp configuration entry setting's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting
		fetchCPConfigurationEntrySettingByUuidAndGroupId(
			String uuid, long groupId) {

		return _cpConfigurationEntrySettingLocalService.
			fetchCPConfigurationEntrySettingByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cpConfigurationEntrySettingLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the cp configuration entry setting with the primary key.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting
	 * @throws PortalException if a cp configuration entry setting with the primary key could not be found
	 */
	@Override
	public CPConfigurationEntrySetting getCPConfigurationEntrySetting(
			long CPConfigurationEntrySettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntrySettingLocalService.
			getCPConfigurationEntrySetting(CPConfigurationEntrySettingId);
	}

	/**
	 * Returns the cp configuration entry setting matching the UUID and group.
	 *
	 * @param uuid the cp configuration entry setting's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp configuration entry setting
	 * @throws PortalException if a matching cp configuration entry setting could not be found
	 */
	@Override
	public CPConfigurationEntrySetting
			getCPConfigurationEntrySettingByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntrySettingLocalService.
			getCPConfigurationEntrySettingByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List<CPConfigurationEntrySetting>
		getCPConfigurationEntrySettings(int start, int end) {

		return _cpConfigurationEntrySettingLocalService.
			getCPConfigurationEntrySettings(start, end);
	}

	/**
	 * Returns all the cp configuration entry settings matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp configuration entry settings
	 * @param companyId the primary key of the company
	 * @return the matching cp configuration entry settings, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<CPConfigurationEntrySetting>
		getCPConfigurationEntrySettingsByUuidAndCompanyId(
			String uuid, long companyId) {

		return _cpConfigurationEntrySettingLocalService.
			getCPConfigurationEntrySettingsByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<CPConfigurationEntrySetting>
		getCPConfigurationEntrySettingsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationEntrySetting> orderByComparator) {

		return _cpConfigurationEntrySettingLocalService.
			getCPConfigurationEntrySettingsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of cp configuration entry settings.
	 *
	 * @return the number of cp configuration entry settings
	 */
	@Override
	public int getCPConfigurationEntrySettingsCount() {
		return _cpConfigurationEntrySettingLocalService.
			getCPConfigurationEntrySettingsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _cpConfigurationEntrySettingLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpConfigurationEntrySettingLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpConfigurationEntrySettingLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntrySettingLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public CPConfigurationEntrySetting updateCPConfigurationEntrySetting(
		CPConfigurationEntrySetting cpConfigurationEntrySetting) {

		return _cpConfigurationEntrySettingLocalService.
			updateCPConfigurationEntrySetting(cpConfigurationEntrySetting);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpConfigurationEntrySettingLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<CPConfigurationEntrySetting> getCTPersistence() {
		return _cpConfigurationEntrySettingLocalService.getCTPersistence();
	}

	@Override
	public Class<CPConfigurationEntrySetting> getModelClass() {
		return _cpConfigurationEntrySettingLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CPConfigurationEntrySetting>, R, E>
				updateUnsafeFunction)
		throws E {

		return _cpConfigurationEntrySettingLocalService.
			updateWithUnsafeFunction(updateUnsafeFunction);
	}

	@Override
	public CPConfigurationEntrySettingLocalService getWrappedService() {
		return _cpConfigurationEntrySettingLocalService;
	}

	@Override
	public void setWrappedService(
		CPConfigurationEntrySettingLocalService
			cpConfigurationEntrySettingLocalService) {

		_cpConfigurationEntrySettingLocalService =
			cpConfigurationEntrySettingLocalService;
	}

	private CPConfigurationEntrySettingLocalService
		_cpConfigurationEntrySettingLocalService;

}
// SB-Hash:-1601344319