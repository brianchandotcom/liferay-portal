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

package com.liferay.portal.search.tuning.blueprints.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Blueprint. This utility wraps
 * <code>com.liferay.portal.search.tuning.blueprints.service.impl.BlueprintLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlueprintLocalService
 * @generated
 */
public class BlueprintLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.search.tuning.blueprints.service.impl.BlueprintLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the blueprint to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlueprintLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blueprint the blueprint
	 * @return the blueprint that was added
	 */
	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
		addBlueprint(
			com.liferay.portal.search.tuning.blueprints.model.Blueprint
				blueprint) {

		return getService().addBlueprint(blueprint);
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			addBlueprint(
				long userId, long groupId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addBlueprint(
			userId, groupId, titleMap, descriptionMap, configuration,
			selectedElements, type, serviceContext);
	}

	/**
	 * Creates a new blueprint with the primary key. Does not add the blueprint to the database.
	 *
	 * @param blueprintId the primary key for the new blueprint
	 * @return the new blueprint
	 */
	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
		createBlueprint(long blueprintId) {

		return getService().createBlueprint(blueprintId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the blueprint from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlueprintLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blueprint the blueprint
	 * @return the blueprint that was removed
	 * @throws PortalException
	 */
	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			deleteBlueprint(
				com.liferay.portal.search.tuning.blueprints.model.Blueprint
					blueprint)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteBlueprint(blueprint);
	}

	/**
	 * Deletes the blueprint with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlueprintLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blueprintId the primary key of the blueprint
	 * @return the blueprint that was removed
	 * @throws PortalException if a blueprint with the primary key could not be found
	 */
	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			deleteBlueprint(long blueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteBlueprint(blueprintId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.search.tuning.blueprints.model.impl.BlueprintModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.search.tuning.blueprints.model.impl.BlueprintModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
		fetchBlueprint(long blueprintId) {

		return getService().fetchBlueprint(blueprintId);
	}

	/**
	 * Returns the blueprint matching the UUID and group.
	 *
	 * @param uuid the blueprint's UUID
	 * @param groupId the primary key of the group
	 * @return the matching blueprint, or <code>null</code> if a matching blueprint could not be found
	 */
	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
		fetchBlueprintByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchBlueprintByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the blueprint with the primary key.
	 *
	 * @param blueprintId the primary key of the blueprint
	 * @return the blueprint
	 * @throws PortalException if a blueprint with the primary key could not be found
	 */
	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			getBlueprint(long blueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBlueprint(blueprintId);
	}

	/**
	 * Returns the blueprint matching the UUID and group.
	 *
	 * @param uuid the blueprint's UUID
	 * @param groupId the primary key of the group
	 * @return the matching blueprint
	 * @throws PortalException if a matching blueprint could not be found
	 */
	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			getBlueprintByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBlueprintByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.search.tuning.blueprints.model.impl.BlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @return the range of blueprints
	 */
	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getBlueprints(int start, int end) {

		return getService().getBlueprints(start, end);
	}

	/**
	 * Returns all the blueprints matching the UUID and company.
	 *
	 * @param uuid the UUID of the blueprints
	 * @param companyId the primary key of the company
	 * @return the matching blueprints, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getBlueprintsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getBlueprintsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of blueprints matching the UUID and company.
	 *
	 * @param uuid the UUID of the blueprints
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of blueprints
	 * @param end the upper bound of the range of blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching blueprints, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getBlueprintsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.
						Blueprint> orderByComparator) {

		return getService().getBlueprintsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of blueprints.
	 *
	 * @return the number of blueprints
	 */
	public static int getBlueprintsCount() {
		return getService().getBlueprintsCount();
	}

	public static int getCompanyBlueprintsCount(long companyId, int type) {
		return getService().getCompanyBlueprintsCount(companyId, type);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(long groupId, int type, int start, int end) {

		return getService().getGroupBlueprints(groupId, type, start, end);
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				long groupId, int status, int type, int start, int end) {

		return getService().getGroupBlueprints(
			groupId, status, type, start, end);
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				long groupId, int status, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.
						Blueprint> orderByComparator) {

		return getService().getGroupBlueprints(
			groupId, status, type, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				long groupId, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.
						Blueprint> orderByComparator) {

		return getService().getGroupBlueprints(
			groupId, type, start, end, orderByComparator);
	}

	public static int getGroupBlueprintsCount(long groupId, int type) {
		return getService().getGroupBlueprintsCount(groupId, type);
	}

	public static int getGroupBlueprintsCount(
		long groupId, int status, int type) {

		return getService().getGroupBlueprintsCount(groupId, status, type);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the blueprint in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BlueprintLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param blueprint the blueprint
	 * @return the blueprint that was updated
	 */
	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
		updateBlueprint(
			com.liferay.portal.search.tuning.blueprints.model.Blueprint
				blueprint) {

		return getService().updateBlueprint(blueprint);
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			updateBlueprint(
				long userId, long blueprintId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateBlueprint(
			userId, blueprintId, titleMap, descriptionMap, configuration,
			selectedElements, serviceContext);
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			updateStatus(long userId, long blueprintId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(userId, blueprintId, status);
	}

	public static BlueprintLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<BlueprintLocalService, BlueprintLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(BlueprintLocalService.class);

		ServiceTracker<BlueprintLocalService, BlueprintLocalService>
			serviceTracker =
				new ServiceTracker
					<BlueprintLocalService, BlueprintLocalService>(
						bundle.getBundleContext(), BlueprintLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}