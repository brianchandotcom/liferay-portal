/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;

/**
 * Provides a wrapper for {@link KaleoNodeSettingLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeSettingLocalService
 * @generated
 */
public class KaleoNodeSettingLocalServiceWrapper
	implements KaleoNodeSettingLocalService,
			   ServiceWrapper<KaleoNodeSettingLocalService> {

	public KaleoNodeSettingLocalServiceWrapper() {
		this(null);
	}

	public KaleoNodeSettingLocalServiceWrapper(
		KaleoNodeSettingLocalService kaleoNodeSettingLocalService) {

		_kaleoNodeSettingLocalService = kaleoNodeSettingLocalService;
	}

	/**
	 * Adds the kaleo node setting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNodeSetting the kaleo node setting
	 * @return the kaleo node setting that was added
	 */
	@Override
	public KaleoNodeSetting addKaleoNodeSetting(
		KaleoNodeSetting kaleoNodeSetting) {

		return _kaleoNodeSettingLocalService.addKaleoNodeSetting(
			kaleoNodeSetting);
	}

	@Override
	public KaleoNodeSetting addKaleoNodeSetting(
			long userId, long kaleoNodeId, String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeSettingLocalService.addKaleoNodeSetting(
			userId, kaleoNodeId, name, value);
	}

	/**
	 * Creates a new kaleo node setting with the primary key. Does not add the kaleo node setting to the database.
	 *
	 * @param kaleoNodeSettingId the primary key for the new kaleo node setting
	 * @return the new kaleo node setting
	 */
	@Override
	public KaleoNodeSetting createKaleoNodeSetting(long kaleoNodeSettingId) {
		return _kaleoNodeSettingLocalService.createKaleoNodeSetting(
			kaleoNodeSettingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeSettingLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the kaleo node setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNodeSetting the kaleo node setting
	 * @return the kaleo node setting that was removed
	 */
	@Override
	public KaleoNodeSetting deleteKaleoNodeSetting(
		KaleoNodeSetting kaleoNodeSetting) {

		return _kaleoNodeSettingLocalService.deleteKaleoNodeSetting(
			kaleoNodeSetting);
	}

	/**
	 * Deletes the kaleo node setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNodeSettingId the primary key of the kaleo node setting
	 * @return the kaleo node setting that was removed
	 * @throws PortalException if a kaleo node setting with the primary key could not be found
	 */
	@Override
	public KaleoNodeSetting deleteKaleoNodeSetting(long kaleoNodeSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeSettingLocalService.deleteKaleoNodeSetting(
			kaleoNodeSettingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeSettingLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _kaleoNodeSettingLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _kaleoNodeSettingLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoNodeSettingLocalService.dynamicQuery();
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

		return _kaleoNodeSettingLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeSettingModelImpl</code>.
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

		return _kaleoNodeSettingLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeSettingModelImpl</code>.
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

		return _kaleoNodeSettingLocalService.dynamicQuery(
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

		return _kaleoNodeSettingLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kaleoNodeSettingLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public KaleoNodeSetting fetchKaleoNodeSetting(long kaleoNodeSettingId) {
		return _kaleoNodeSettingLocalService.fetchKaleoNodeSetting(
			kaleoNodeSettingId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoNodeSettingLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoNodeSettingLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo node setting with the primary key.
	 *
	 * @param kaleoNodeSettingId the primary key of the kaleo node setting
	 * @return the kaleo node setting
	 * @throws PortalException if a kaleo node setting with the primary key could not be found
	 */
	@Override
	public KaleoNodeSetting getKaleoNodeSetting(long kaleoNodeSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeSettingLocalService.getKaleoNodeSetting(
			kaleoNodeSettingId);
	}

	/**
	 * Returns a range of all the kaleo node settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @return the range of kaleo node settings
	 */
	@Override
	public java.util.List<KaleoNodeSetting> getKaleoNodeSettings(
		int start, int end) {

		return _kaleoNodeSettingLocalService.getKaleoNodeSettings(start, end);
	}

	@Override
	public java.util.List<KaleoNodeSetting> getKaleoNodeSettings(
		long kaleoNodeId) {

		return _kaleoNodeSettingLocalService.getKaleoNodeSettings(kaleoNodeId);
	}

	/**
	 * Returns the number of kaleo node settings.
	 *
	 * @return the number of kaleo node settings
	 */
	@Override
	public int getKaleoNodeSettingsCount() {
		return _kaleoNodeSettingLocalService.getKaleoNodeSettingsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoNodeSettingLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoNodeSettingLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo node setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNodeSetting the kaleo node setting
	 * @return the kaleo node setting that was updated
	 */
	@Override
	public KaleoNodeSetting updateKaleoNodeSetting(
		KaleoNodeSetting kaleoNodeSetting) {

		return _kaleoNodeSettingLocalService.updateKaleoNodeSetting(
			kaleoNodeSetting);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoNodeSettingLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<KaleoNodeSetting> getCTPersistence() {
		return _kaleoNodeSettingLocalService.getCTPersistence();
	}

	@Override
	public Class<KaleoNodeSetting> getModelClass() {
		return _kaleoNodeSettingLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<KaleoNodeSetting>, R, E>
				updateUnsafeFunction)
		throws E {

		return _kaleoNodeSettingLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public KaleoNodeSettingLocalService getWrappedService() {
		return _kaleoNodeSettingLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoNodeSettingLocalService kaleoNodeSettingLocalService) {

		_kaleoNodeSettingLocalService = kaleoNodeSettingLocalService;
	}

	private KaleoNodeSettingLocalService _kaleoNodeSettingLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:1864541228