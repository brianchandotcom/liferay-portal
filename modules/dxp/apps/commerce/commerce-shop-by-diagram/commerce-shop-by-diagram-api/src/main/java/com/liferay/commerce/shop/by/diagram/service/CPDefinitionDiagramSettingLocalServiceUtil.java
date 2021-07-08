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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CPDefinitionDiagramSetting. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramSettingLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSettingLocalService
 * @generated
 */
public class CPDefinitionDiagramSettingLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramSettingLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static CPDefinitionDiagramSetting addCPDefinitionDiagramSetting(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		return getService().addCPDefinitionDiagramSetting(
			cpDefinitionDiagramSetting);
	}

	public static CPDefinitionDiagramSetting addCPDefinitionDiagramSetting(
			long userId, long cpDefinitionId, long cpAttachmentFileEntryId,
			String color, double radius, String type)
		throws PortalException {

		return getService().addCPDefinitionDiagramSetting(
			userId, cpDefinitionId, cpAttachmentFileEntryId, color, radius,
			type);
	}

	/**
	 * Creates a new cp definition diagram setting with the primary key. Does not add the cp definition diagram setting to the database.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key for the new cp definition diagram setting
	 * @return the new cp definition diagram setting
	 */
	public static CPDefinitionDiagramSetting createCPDefinitionDiagramSetting(
		long CPDefinitionDiagramSettingId) {

		return getService().createCPDefinitionDiagramSetting(
			CPDefinitionDiagramSettingId);
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
	 * Deletes the cp definition diagram setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSetting the cp definition diagram setting
	 * @return the cp definition diagram setting that was removed
	 */
	public static CPDefinitionDiagramSetting deleteCPDefinitionDiagramSetting(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		return getService().deleteCPDefinitionDiagramSetting(
			cpDefinitionDiagramSetting);
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
	public static CPDefinitionDiagramSetting deleteCPDefinitionDiagramSetting(
			long CPDefinitionDiagramSettingId)
		throws PortalException {

		return getService().deleteCPDefinitionDiagramSetting(
			CPDefinitionDiagramSettingId);
	}

	public static CPDefinitionDiagramSetting
			deleteCPDefinitionDiagramSettingByCPDefinitionId(
				long cpDefinitionId)
		throws PortalException {

		return getService().deleteCPDefinitionDiagramSettingByCPDefinitionId(
			cpDefinitionId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingModelImpl</code>.
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

	public static CPDefinitionDiagramSetting fetchCPDefinitionDiagramSetting(
		long CPDefinitionDiagramSettingId) {

		return getService().fetchCPDefinitionDiagramSetting(
			CPDefinitionDiagramSettingId);
	}

	public static CPDefinitionDiagramSetting
		fetchCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId) {

		return getService().fetchCPDefinitionDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	/**
	 * Returns the cp definition diagram setting with the matching UUID and company.
	 *
	 * @param uuid the cp definition diagram setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting
		fetchCPDefinitionDiagramSettingByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchCPDefinitionDiagramSettingByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cp definition diagram setting with the primary key.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting
	 * @throws PortalException if a cp definition diagram setting with the primary key could not be found
	 */
	public static CPDefinitionDiagramSetting getCPDefinitionDiagramSetting(
			long CPDefinitionDiagramSettingId)
		throws PortalException {

		return getService().getCPDefinitionDiagramSetting(
			CPDefinitionDiagramSettingId);
	}

	public static CPDefinitionDiagramSetting
			getCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	/**
	 * Returns the cp definition diagram setting with the matching UUID and company.
	 *
	 * @param uuid the cp definition diagram setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp definition diagram setting
	 * @throws PortalException if a matching cp definition diagram setting could not be found
	 */
	public static CPDefinitionDiagramSetting
			getCPDefinitionDiagramSettingByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().getCPDefinitionDiagramSettingByUuidAndCompanyId(
			uuid, companyId);
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
	public static List<CPDefinitionDiagramSetting>
		getCPDefinitionDiagramSettings(int start, int end) {

		return getService().getCPDefinitionDiagramSettings(start, end);
	}

	/**
	 * Returns the number of cp definition diagram settings.
	 *
	 * @return the number of cp definition diagram settings
	 */
	public static int getCPDefinitionDiagramSettingsCount() {
		return getService().getCPDefinitionDiagramSettingsCount();
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
	 * Updates the cp definition diagram setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSetting the cp definition diagram setting
	 * @return the cp definition diagram setting that was updated
	 */
	public static CPDefinitionDiagramSetting updateCPDefinitionDiagramSetting(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		return getService().updateCPDefinitionDiagramSetting(
			cpDefinitionDiagramSetting);
	}

	public static CPDefinitionDiagramSetting updateCPDefinitionDiagramSetting(
			long cpDefinitionDiagramSettingId, long cpAttachmentFileEntryId,
			String color, double radius, String type)
		throws PortalException {

		return getService().updateCPDefinitionDiagramSetting(
			cpDefinitionDiagramSettingId, cpAttachmentFileEntryId, color,
			radius, type);
	}

	public static CPDefinitionDiagramSettingLocalService getService() {
		return _service;
	}

	private static volatile CPDefinitionDiagramSettingLocalService _service;

}