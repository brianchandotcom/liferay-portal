/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionDiagramSettingsLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettingsLocalService
 * @generated
 */
public class CPDefinitionDiagramSettingsLocalServiceWrapper
	implements CPDefinitionDiagramSettingsLocalService,
			   ServiceWrapper<CPDefinitionDiagramSettingsLocalService> {

	public CPDefinitionDiagramSettingsLocalServiceWrapper(
		CPDefinitionDiagramSettingsLocalService
			cpDefinitionDiagramSettingsLocalService) {

		_cpDefinitionDiagramSettingsLocalService =
			cpDefinitionDiagramSettingsLocalService;
	}

	/**
	 * Adds the cp definition diagram settings to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 * @return the cp definition diagram settings that was added
	 */
	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
			addCPDefinitionDiagramSettings(
				com.liferay.commerce.shop.by.diagram.model.
					CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		return _cpDefinitionDiagramSettingsLocalService.
			addCPDefinitionDiagramSettings(cpDefinitionDiagramSettings);
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				addCPDefinitionDiagramSettings(
					long userId, long cpDefinitionId, long assetCategoryId,
					String name)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsLocalService.
			addCPDefinitionDiagramSettings(
				userId, cpDefinitionId, assetCategoryId, name);
	}

	/**
	 * Creates a new cp definition diagram settings with the primary key. Does not add the cp definition diagram settings to the database.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key for the new cp definition diagram settings
	 * @return the new cp definition diagram settings
	 */
	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
			createCPDefinitionDiagramSettings(
				long CPDefinitionDiagramSettingsId) {

		return _cpDefinitionDiagramSettingsLocalService.
			createCPDefinitionDiagramSettings(CPDefinitionDiagramSettingsId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the cp definition diagram settings from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 * @return the cp definition diagram settings that was removed
	 */
	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
			deleteCPDefinitionDiagramSettings(
				com.liferay.commerce.shop.by.diagram.model.
					CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		return _cpDefinitionDiagramSettingsLocalService.
			deleteCPDefinitionDiagramSettings(cpDefinitionDiagramSettings);
	}

	/**
	 * Deletes the cp definition diagram settings with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings that was removed
	 * @throws PortalException if a cp definition diagram settings with the primary key could not be found
	 */
	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				deleteCPDefinitionDiagramSettings(
					long CPDefinitionDiagramSettingsId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsLocalService.
			deleteCPDefinitionDiagramSettings(CPDefinitionDiagramSettingsId);
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				deleteCPDefinitionDiagramSettingsByCPDefinitionId(
					long cpDefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsLocalService.
			deleteCPDefinitionDiagramSettingsByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public void deleteCPDefinitionDiagramSettingsCategory(long assetCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpDefinitionDiagramSettingsLocalService.
			deleteCPDefinitionDiagramSettingsCategory(assetCategoryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cpDefinitionDiagramSettingsLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionDiagramSettingsLocalService.dynamicQuery();
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

		return _cpDefinitionDiagramSettingsLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsModelImpl</code>.
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

		return _cpDefinitionDiagramSettingsLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsModelImpl</code>.
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

		return _cpDefinitionDiagramSettingsLocalService.dynamicQuery(
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

		return _cpDefinitionDiagramSettingsLocalService.dynamicQueryCount(
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

		return _cpDefinitionDiagramSettingsLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
			fetchCPDefinitionDiagramSettings(
				long CPDefinitionDiagramSettingsId) {

		return _cpDefinitionDiagramSettingsLocalService.
			fetchCPDefinitionDiagramSettings(CPDefinitionDiagramSettingsId);
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
			fetchCPDefinitionDiagramSettingsByCPDefinitionId(
				long cpDefinitionId) {

		return _cpDefinitionDiagramSettingsLocalService.
			fetchCPDefinitionDiagramSettingsByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cpDefinitionDiagramSettingsLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the cp definition diagram settings with the primary key.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings
	 * @throws PortalException if a cp definition diagram settings with the primary key could not be found
	 */
	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				getCPDefinitionDiagramSettings(
					long CPDefinitionDiagramSettingsId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsLocalService.
			getCPDefinitionDiagramSettings(CPDefinitionDiagramSettingsId);
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				getCPDefinitionDiagramSettingsByCPDefinitionId(
					long cpDefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsLocalService.
			getCPDefinitionDiagramSettingsByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * Returns a range of all the cp definition diagram settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @return the range of cp definition diagram settingses
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings>
			getCPDefinitionDiagramSettingses(int start, int end) {

		return _cpDefinitionDiagramSettingsLocalService.
			getCPDefinitionDiagramSettingses(start, end);
	}

	/**
	 * Returns the number of cp definition diagram settingses.
	 *
	 * @return the number of cp definition diagram settingses
	 */
	@Override
	public int getCPDefinitionDiagramSettingsesCount() {
		return _cpDefinitionDiagramSettingsLocalService.
			getCPDefinitionDiagramSettingsesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpDefinitionDiagramSettingsLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionDiagramSettingsLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the cp definition diagram settings in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 * @return the cp definition diagram settings that was updated
	 */
	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
			updateCPDefinitionDiagramSettings(
				com.liferay.commerce.shop.by.diagram.model.
					CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		return _cpDefinitionDiagramSettingsLocalService.
			updateCPDefinitionDiagramSettings(cpDefinitionDiagramSettings);
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				updateCPDefinitionDiagramSettings(
					long cpDefinitionDiagramSettingsId, long assetCategoryId,
					String name)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsLocalService.
			updateCPDefinitionDiagramSettings(
				cpDefinitionDiagramSettingsId, assetCategoryId, name);
	}

	@Override
	public CPDefinitionDiagramSettingsLocalService getWrappedService() {
		return _cpDefinitionDiagramSettingsLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionDiagramSettingsLocalService
			cpDefinitionDiagramSettingsLocalService) {

		_cpDefinitionDiagramSettingsLocalService =
			cpDefinitionDiagramSettingsLocalService;
	}

	private CPDefinitionDiagramSettingsLocalService
		_cpDefinitionDiagramSettingsLocalService;

}