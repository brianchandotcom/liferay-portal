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
 * Provides a wrapper for {@link CPDefinitionDiagramSettingLocalService}.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSettingLocalService
 * @generated
 */
public class CPDefinitionDiagramSettingLocalServiceWrapper
	implements CPDefinitionDiagramSettingLocalService,
			   ServiceWrapper<CPDefinitionDiagramSettingLocalService> {

	public CPDefinitionDiagramSettingLocalServiceWrapper(
		CPDefinitionDiagramSettingLocalService
			cpDefinitionDiagramSettingLocalService) {

		_cpDefinitionDiagramSettingLocalService =
			cpDefinitionDiagramSettingLocalService;
	}

	/**
	 * Adds the cp definition diagram setting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSetting the cp definition diagram setting
	 * @return the cp definition diagram setting that was added
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
		addCPDefinitionDiagramSetting(
			com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		return _cpDefinitionDiagramSettingLocalService.
			addCPDefinitionDiagramSetting(cpDefinitionDiagramSetting);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			addCPDefinitionDiagramSetting(
				long userId, long cpDefinitionId, long cpAttachmentFileEntryId,
				String color, double radius, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.
			addCPDefinitionDiagramSetting(
				userId, cpDefinitionId, cpAttachmentFileEntryId, color, radius,
				type);
	}

	/**
	 * Creates a new cp definition diagram setting with the primary key. Does not add the cp definition diagram setting to the database.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key for the new cp definition diagram setting
	 * @return the new cp definition diagram setting
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
		createCPDefinitionDiagramSetting(long CPDefinitionDiagramSettingId) {

		return _cpDefinitionDiagramSettingLocalService.
			createCPDefinitionDiagramSetting(CPDefinitionDiagramSettingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the cp definition diagram setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSetting the cp definition diagram setting
	 * @return the cp definition diagram setting that was removed
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
		deleteCPDefinitionDiagramSetting(
			com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		return _cpDefinitionDiagramSettingLocalService.
			deleteCPDefinitionDiagramSetting(cpDefinitionDiagramSetting);
	}

	/**
	 * Deletes the cp definition diagram setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting that was removed
	 * @throws PortalException if a cp definition diagram setting with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			deleteCPDefinitionDiagramSetting(long CPDefinitionDiagramSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.
			deleteCPDefinitionDiagramSetting(CPDefinitionDiagramSettingId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			deleteCPDefinitionDiagramSettingByCPDefinitionId(
				long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.
			deleteCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cpDefinitionDiagramSettingLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cpDefinitionDiagramSettingLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionDiagramSettingLocalService.dynamicQuery();
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

		return _cpDefinitionDiagramSettingLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingModelImpl</code>.
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

		return _cpDefinitionDiagramSettingLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingModelImpl</code>.
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

		return _cpDefinitionDiagramSettingLocalService.dynamicQuery(
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

		return _cpDefinitionDiagramSettingLocalService.dynamicQueryCount(
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

		return _cpDefinitionDiagramSettingLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
		fetchCPDefinitionDiagramSetting(long CPDefinitionDiagramSettingId) {

		return _cpDefinitionDiagramSettingLocalService.
			fetchCPDefinitionDiagramSetting(CPDefinitionDiagramSettingId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
		fetchCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId) {

		return _cpDefinitionDiagramSettingLocalService.
			fetchCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * Returns the cp definition diagram setting with the matching UUID and company.
	 *
	 * @param uuid the cp definition diagram setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
		fetchCPDefinitionDiagramSettingByUuidAndCompanyId(
			String uuid, long companyId) {

		return _cpDefinitionDiagramSettingLocalService.
			fetchCPDefinitionDiagramSettingByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cpDefinitionDiagramSettingLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the cp definition diagram setting with the primary key.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting
	 * @throws PortalException if a cp definition diagram setting with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			getCPDefinitionDiagramSetting(long CPDefinitionDiagramSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.
			getCPDefinitionDiagramSetting(CPDefinitionDiagramSettingId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			getCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.
			getCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * Returns the cp definition diagram setting with the matching UUID and company.
	 *
	 * @param uuid the cp definition diagram setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp definition diagram setting
	 * @throws PortalException if a matching cp definition diagram setting could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			getCPDefinitionDiagramSettingByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.
			getCPDefinitionDiagramSettingByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of cp definition diagram settings
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting>
			getCPDefinitionDiagramSettings(int start, int end) {

		return _cpDefinitionDiagramSettingLocalService.
			getCPDefinitionDiagramSettings(start, end);
	}

	/**
	 * Returns the number of cp definition diagram settings.
	 *
	 * @return the number of cp definition diagram settings
	 */
	@Override
	public int getCPDefinitionDiagramSettingsCount() {
		return _cpDefinitionDiagramSettingLocalService.
			getCPDefinitionDiagramSettingsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _cpDefinitionDiagramSettingLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpDefinitionDiagramSettingLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionDiagramSettingLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the cp definition diagram setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSetting the cp definition diagram setting
	 * @return the cp definition diagram setting that was updated
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
		updateCPDefinitionDiagramSetting(
			com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		return _cpDefinitionDiagramSettingLocalService.
			updateCPDefinitionDiagramSetting(cpDefinitionDiagramSetting);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			updateCPDefinitionDiagramSetting(
				long cpDefinitionDiagramSettingId, long cpAttachmentFileEntryId,
				String color, double radius, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingLocalService.
			updateCPDefinitionDiagramSetting(
				cpDefinitionDiagramSettingId, cpAttachmentFileEntryId, color,
				radius, type);
	}

	@Override
	public CPDefinitionDiagramSettingLocalService getWrappedService() {
		return _cpDefinitionDiagramSettingLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionDiagramSettingLocalService
			cpDefinitionDiagramSettingLocalService) {

		_cpDefinitionDiagramSettingLocalService =
			cpDefinitionDiagramSettingLocalService;
	}

	private CPDefinitionDiagramSettingLocalService
		_cpDefinitionDiagramSettingLocalService;

}