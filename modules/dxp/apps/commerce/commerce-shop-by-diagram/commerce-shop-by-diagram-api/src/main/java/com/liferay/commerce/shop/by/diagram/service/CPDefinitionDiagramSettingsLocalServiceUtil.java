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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CPDefinitionDiagramSettings. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramSettingsLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettingsLocalService
 * @generated
 */
public class CPDefinitionDiagramSettingsLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramSettingsLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static CPDefinitionDiagramSettings addCPDefinitionDiagramSettings(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		return getService().addCPDefinitionDiagramSettings(
			cpDefinitionDiagramSettings);
	}

	public static CPDefinitionDiagramSettings addCPDefinitionDiagramSettings(
			long userId, long cpDefinitionId, long assetCategoryId, String name)
		throws PortalException {

		return getService().addCPDefinitionDiagramSettings(
			userId, cpDefinitionId, assetCategoryId, name);
	}

	/**
	 * Creates a new cp definition diagram settings with the primary key. Does not add the cp definition diagram settings to the database.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key for the new cp definition diagram settings
	 * @return the new cp definition diagram settings
	 */
	public static CPDefinitionDiagramSettings createCPDefinitionDiagramSettings(
		long CPDefinitionDiagramSettingsId) {

		return getService().createCPDefinitionDiagramSettings(
			CPDefinitionDiagramSettingsId);
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
	 * Deletes the cp definition diagram settings from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 * @return the cp definition diagram settings that was removed
	 */
	public static CPDefinitionDiagramSettings deleteCPDefinitionDiagramSettings(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		return getService().deleteCPDefinitionDiagramSettings(
			cpDefinitionDiagramSettings);
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
	public static CPDefinitionDiagramSettings deleteCPDefinitionDiagramSettings(
			long CPDefinitionDiagramSettingsId)
		throws PortalException {

		return getService().deleteCPDefinitionDiagramSettings(
			CPDefinitionDiagramSettingsId);
	}

	public static CPDefinitionDiagramSettings
			deleteCPDefinitionDiagramSettingsByCPDefinitionId(
				long cpDefinitionId)
		throws PortalException {

		return getService().deleteCPDefinitionDiagramSettingsByCPDefinitionId(
			cpDefinitionId);
	}

	public static void deleteCPDefinitionDiagramSettingsCategory(
			long assetCategoryId)
		throws PortalException {

		getService().deleteCPDefinitionDiagramSettingsCategory(assetCategoryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsModelImpl</code>.
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

	public static CPDefinitionDiagramSettings fetchCPDefinitionDiagramSettings(
		long CPDefinitionDiagramSettingsId) {

		return getService().fetchCPDefinitionDiagramSettings(
			CPDefinitionDiagramSettingsId);
	}

	public static CPDefinitionDiagramSettings
		fetchCPDefinitionDiagramSettingsByCPDefinitionId(long cpDefinitionId) {

		return getService().fetchCPDefinitionDiagramSettingsByCPDefinitionId(
			cpDefinitionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cp definition diagram settings with the primary key.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings
	 * @throws PortalException if a cp definition diagram settings with the primary key could not be found
	 */
	public static CPDefinitionDiagramSettings getCPDefinitionDiagramSettings(
			long CPDefinitionDiagramSettingsId)
		throws PortalException {

		return getService().getCPDefinitionDiagramSettings(
			CPDefinitionDiagramSettingsId);
	}

	public static CPDefinitionDiagramSettings
			getCPDefinitionDiagramSettingsByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionDiagramSettingsByCPDefinitionId(
			cpDefinitionId);
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
	public static List<CPDefinitionDiagramSettings>
		getCPDefinitionDiagramSettingses(int start, int end) {

		return getService().getCPDefinitionDiagramSettingses(start, end);
	}

	/**
	 * Returns the number of cp definition diagram settingses.
	 *
	 * @return the number of cp definition diagram settingses
	 */
	public static int getCPDefinitionDiagramSettingsesCount() {
		return getService().getCPDefinitionDiagramSettingsesCount();
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
	 * Updates the cp definition diagram settings in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 * @return the cp definition diagram settings that was updated
	 */
	public static CPDefinitionDiagramSettings updateCPDefinitionDiagramSettings(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		return getService().updateCPDefinitionDiagramSettings(
			cpDefinitionDiagramSettings);
	}

	public static CPDefinitionDiagramSettings updateCPDefinitionDiagramSettings(
			long cpDefinitionDiagramSettingsId, long assetCategoryId,
			String name)
		throws PortalException {

		return getService().updateCPDefinitionDiagramSettings(
			cpDefinitionDiagramSettingsId, assetCategoryId, name);
	}

	public static CPDefinitionDiagramSettingsLocalService getService() {
		return _service;
	}

	private static volatile CPDefinitionDiagramSettingsLocalService _service;

}