/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link ObjectValidationRuleSettingLocalService}.
 *
 * @author Marco Leo
 * @see ObjectValidationRuleSettingLocalService
 * @generated
 */
public class ObjectValidationRuleSettingLocalServiceWrapper
	implements ObjectValidationRuleSettingLocalService,
			   ServiceWrapper<ObjectValidationRuleSettingLocalService> {

	public ObjectValidationRuleSettingLocalServiceWrapper() {
		this(null);
	}

	public ObjectValidationRuleSettingLocalServiceWrapper(
		ObjectValidationRuleSettingLocalService
			objectValidationRuleSettingLocalService) {

		_objectValidationRuleSettingLocalService =
			objectValidationRuleSettingLocalService;
	}

	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
			addObjectValidationRuleSetting(
				long userId, long objectValidationRuleId, String name,
				String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleSettingLocalService.
			addObjectValidationRuleSetting(
				userId, objectValidationRuleId, name, value);
	}

	/**
	 * Adds the object validation rule setting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectValidationRuleSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectValidationRuleSetting the object validation rule setting
	 * @return the object validation rule setting that was added
	 */
	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
		addObjectValidationRuleSetting(
			com.liferay.object.model.ObjectValidationRuleSetting
				objectValidationRuleSetting) {

		return _objectValidationRuleSettingLocalService.
			addObjectValidationRuleSetting(objectValidationRuleSetting);
	}

	/**
	 * Creates a new object validation rule setting with the primary key. Does not add the object validation rule setting to the database.
	 *
	 * @param objectValidationRuleSettingId the primary key for the new object validation rule setting
	 * @return the new object validation rule setting
	 */
	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
		createObjectValidationRuleSetting(long objectValidationRuleSettingId) {

		return _objectValidationRuleSettingLocalService.
			createObjectValidationRuleSetting(objectValidationRuleSettingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleSettingLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the object validation rule setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectValidationRuleSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting that was removed
	 * @throws PortalException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
			deleteObjectValidationRuleSetting(
				long objectValidationRuleSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleSettingLocalService.
			deleteObjectValidationRuleSetting(objectValidationRuleSettingId);
	}

	/**
	 * Deletes the object validation rule setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectValidationRuleSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectValidationRuleSetting the object validation rule setting
	 * @return the object validation rule setting that was removed
	 */
	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
		deleteObjectValidationRuleSetting(
			com.liferay.object.model.ObjectValidationRuleSetting
				objectValidationRuleSetting) {

		return _objectValidationRuleSettingLocalService.
			deleteObjectValidationRuleSetting(objectValidationRuleSetting);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleSettingLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _objectValidationRuleSettingLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _objectValidationRuleSettingLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _objectValidationRuleSettingLocalService.dynamicQuery();
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

		return _objectValidationRuleSettingLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectValidationRuleSettingModelImpl</code>.
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

		return _objectValidationRuleSettingLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectValidationRuleSettingModelImpl</code>.
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

		return _objectValidationRuleSettingLocalService.dynamicQuery(
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

		return _objectValidationRuleSettingLocalService.dynamicQueryCount(
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

		return _objectValidationRuleSettingLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
		fetchObjectValidationRuleSetting(long objectValidationRuleSettingId) {

		return _objectValidationRuleSettingLocalService.
			fetchObjectValidationRuleSetting(objectValidationRuleSettingId);
	}

	/**
	 * Returns the object validation rule setting with the matching UUID and company.
	 *
	 * @param uuid the object validation rule setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
		fetchObjectValidationRuleSettingByUuidAndCompanyId(
			String uuid, long companyId) {

		return _objectValidationRuleSettingLocalService.
			fetchObjectValidationRuleSettingByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _objectValidationRuleSettingLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _objectValidationRuleSettingLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectValidationRuleSettingLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the object validation rule setting with the primary key.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting
	 * @throws PortalException if a object validation rule setting with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
			getObjectValidationRuleSetting(long objectValidationRuleSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleSettingLocalService.
			getObjectValidationRuleSetting(objectValidationRuleSettingId);
	}

	/**
	 * Returns the object validation rule setting with the matching UUID and company.
	 *
	 * @param uuid the object validation rule setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object validation rule setting
	 * @throws PortalException if a matching object validation rule setting could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
			getObjectValidationRuleSettingByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleSettingLocalService.
			getObjectValidationRuleSettingByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of object validation rule settings
	 */
	@Override
	public java.util.List<com.liferay.object.model.ObjectValidationRuleSetting>
		getObjectValidationRuleSettings(int start, int end) {

		return _objectValidationRuleSettingLocalService.
			getObjectValidationRuleSettings(start, end);
	}

	/**
	 * Returns the number of object validation rule settings.
	 *
	 * @return the number of object validation rule settings
	 */
	@Override
	public int getObjectValidationRuleSettingsCount() {
		return _objectValidationRuleSettingLocalService.
			getObjectValidationRuleSettingsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectValidationRuleSettingLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleSettingLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the object validation rule setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectValidationRuleSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectValidationRuleSetting the object validation rule setting
	 * @return the object validation rule setting that was updated
	 */
	@Override
	public com.liferay.object.model.ObjectValidationRuleSetting
		updateObjectValidationRuleSetting(
			com.liferay.object.model.ObjectValidationRuleSetting
				objectValidationRuleSetting) {

		return _objectValidationRuleSettingLocalService.
			updateObjectValidationRuleSetting(objectValidationRuleSetting);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _objectValidationRuleSettingLocalService.getBasePersistence();
	}

	@Override
	public ObjectValidationRuleSettingLocalService getWrappedService() {
		return _objectValidationRuleSettingLocalService;
	}

	@Override
	public void setWrappedService(
		ObjectValidationRuleSettingLocalService
			objectValidationRuleSettingLocalService) {

		_objectValidationRuleSettingLocalService =
			objectValidationRuleSettingLocalService;
	}

	private ObjectValidationRuleSettingLocalService
		_objectValidationRuleSettingLocalService;

}