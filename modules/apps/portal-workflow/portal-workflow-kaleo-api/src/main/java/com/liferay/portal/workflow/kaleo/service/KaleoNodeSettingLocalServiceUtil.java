/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for KaleoNodeSetting. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoNodeSettingLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeSettingLocalService
 * @generated
 */
public class KaleoNodeSettingLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoNodeSettingLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static KaleoNodeSetting addKaleoNodeSetting(
		KaleoNodeSetting kaleoNodeSetting) {

		return getService().addKaleoNodeSetting(kaleoNodeSetting);
	}

	public static KaleoNodeSetting addKaleoNodeSetting(
			long userId, long kaleoNodeId, String name, String value)
		throws PortalException {

		return getService().addKaleoNodeSetting(
			userId, kaleoNodeId, name, value);
	}

	/**
	 * Creates a new kaleo node setting with the primary key. Does not add the kaleo node setting to the database.
	 *
	 * @param kaleoNodeSettingId the primary key for the new kaleo node setting
	 * @return the new kaleo node setting
	 */
	public static KaleoNodeSetting createKaleoNodeSetting(
		long kaleoNodeSettingId) {

		return getService().createKaleoNodeSetting(kaleoNodeSettingId);
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
	 * Deletes the kaleo node setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNodeSetting the kaleo node setting
	 * @return the kaleo node setting that was removed
	 */
	public static KaleoNodeSetting deleteKaleoNodeSetting(
		KaleoNodeSetting kaleoNodeSetting) {

		return getService().deleteKaleoNodeSetting(kaleoNodeSetting);
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
	public static KaleoNodeSetting deleteKaleoNodeSetting(
			long kaleoNodeSettingId)
		throws PortalException {

		return getService().deleteKaleoNodeSetting(kaleoNodeSettingId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeSettingModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeSettingModelImpl</code>.
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

	public static KaleoNodeSetting fetchKaleoNodeSetting(
		long kaleoNodeSettingId) {

		return getService().fetchKaleoNodeSetting(kaleoNodeSettingId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo node setting with the primary key.
	 *
	 * @param kaleoNodeSettingId the primary key of the kaleo node setting
	 * @return the kaleo node setting
	 * @throws PortalException if a kaleo node setting with the primary key could not be found
	 */
	public static KaleoNodeSetting getKaleoNodeSetting(long kaleoNodeSettingId)
		throws PortalException {

		return getService().getKaleoNodeSetting(kaleoNodeSettingId);
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
	public static List<KaleoNodeSetting> getKaleoNodeSettings(
		int start, int end) {

		return getService().getKaleoNodeSettings(start, end);
	}

	public static List<KaleoNodeSetting> getKaleoNodeSettings(
		long kaleoNodeId) {

		return getService().getKaleoNodeSettings(kaleoNodeId);
	}

	/**
	 * Returns the number of kaleo node settings.
	 *
	 * @return the number of kaleo node settings
	 */
	public static int getKaleoNodeSettingsCount() {
		return getService().getKaleoNodeSettingsCount();
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
	 * Updates the kaleo node setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoNodeSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoNodeSetting the kaleo node setting
	 * @return the kaleo node setting that was updated
	 */
	public static KaleoNodeSetting updateKaleoNodeSetting(
		KaleoNodeSetting kaleoNodeSetting) {

		return getService().updateKaleoNodeSetting(kaleoNodeSetting);
	}

	public static KaleoNodeSettingLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<KaleoNodeSettingLocalService>
		_serviceSnapshot = new Snapshot<>(
			KaleoNodeSettingLocalServiceUtil.class,
			KaleoNodeSettingLocalService.class);

}
// SB-Hash:1072817743