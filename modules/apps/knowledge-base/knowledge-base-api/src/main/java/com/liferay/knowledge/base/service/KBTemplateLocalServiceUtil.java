/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.knowledge.base.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for KBTemplate. This utility wraps
 * <code>com.liferay.knowledge.base.service.impl.KBTemplateLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KBTemplateLocalService
 * @generated
 */
public class KBTemplateLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBTemplateLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kb template to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbTemplate the kb template
	 * @return the kb template that was added
	 */
	public static com.liferay.knowledge.base.model.KBTemplate addKBTemplate(
		com.liferay.knowledge.base.model.KBTemplate kbTemplate) {

		return getService().addKBTemplate(kbTemplate);
	}

	public static com.liferay.knowledge.base.model.KBTemplate addKBTemplate(
			long userId, String title, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKBTemplate(
			userId, title, content, serviceContext);
	}

	/**
	 * Creates a new kb template with the primary key. Does not add the kb template to the database.
	 *
	 * @param kbTemplateId the primary key for the new kb template
	 * @return the new kb template
	 */
	public static com.liferay.knowledge.base.model.KBTemplate createKBTemplate(
		long kbTemplateId) {

		return getService().createKBTemplate(kbTemplateId);
	}

	public static void deleteGroupKBTemplates(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteGroupKBTemplates(groupId);
	}

	/**
	 * Deletes the kb template from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbTemplate the kb template
	 * @return the kb template that was removed
	 * @throws PortalException
	 */
	public static com.liferay.knowledge.base.model.KBTemplate deleteKBTemplate(
			com.liferay.knowledge.base.model.KBTemplate kbTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKBTemplate(kbTemplate);
	}

	/**
	 * Deletes the kb template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbTemplateId the primary key of the kb template
	 * @return the kb template that was removed
	 * @throws PortalException if a kb template with the primary key could not be found
	 */
	public static com.liferay.knowledge.base.model.KBTemplate deleteKBTemplate(
			long kbTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKBTemplate(kbTemplateId);
	}

	public static void deleteKBTemplates(long[] kbTemplateIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteKBTemplates(kbTemplateIds);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBTemplateModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBTemplateModelImpl</code>.
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

	public static com.liferay.knowledge.base.model.KBTemplate fetchKBTemplate(
		long kbTemplateId) {

		return getService().fetchKBTemplate(kbTemplateId);
	}

	/**
	 * Returns the kb template matching the UUID and group.
	 *
	 * @param uuid the kb template's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kb template, or <code>null</code> if a matching kb template could not be found
	 */
	public static com.liferay.knowledge.base.model.KBTemplate
		fetchKBTemplateByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchKBTemplateByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBTemplate>
		getGroupKBTemplates(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBTemplate>
					orderByComparator) {

		return getService().getGroupKBTemplates(
			groupId, start, end, orderByComparator);
	}

	public static int getGroupKBTemplatesCount(long groupId) {
		return getService().getGroupKBTemplatesCount(groupId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kb template with the primary key.
	 *
	 * @param kbTemplateId the primary key of the kb template
	 * @return the kb template
	 * @throws PortalException if a kb template with the primary key could not be found
	 */
	public static com.liferay.knowledge.base.model.KBTemplate getKBTemplate(
			long kbTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBTemplate(kbTemplateId);
	}

	/**
	 * Returns the kb template matching the UUID and group.
	 *
	 * @param uuid the kb template's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kb template
	 * @throws PortalException if a matching kb template could not be found
	 */
	public static com.liferay.knowledge.base.model.KBTemplate
			getKBTemplateByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBTemplateByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the kb templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @return the range of kb templates
	 */
	public static java.util.List<com.liferay.knowledge.base.model.KBTemplate>
		getKBTemplates(int start, int end) {

		return getService().getKBTemplates(start, end);
	}

	/**
	 * Returns all the kb templates matching the UUID and company.
	 *
	 * @param uuid the UUID of the kb templates
	 * @param companyId the primary key of the company
	 * @return the matching kb templates, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.knowledge.base.model.KBTemplate>
		getKBTemplatesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getKBTemplatesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of kb templates matching the UUID and company.
	 *
	 * @param uuid the UUID of the kb templates
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching kb templates, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.knowledge.base.model.KBTemplate>
		getKBTemplatesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBTemplate>
					orderByComparator) {

		return getService().getKBTemplatesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of kb templates.
	 *
	 * @return the number of kb templates
	 */
	public static int getKBTemplatesCount() {
		return getService().getKBTemplatesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBTemplate>
		search(
			long groupId, String title, String content,
			java.util.Date startDate, java.util.Date endDate,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBTemplate>
					orderByComparator) {

		return getService().search(
			groupId, title, content, startDate, endDate, andOperator, start,
			end, orderByComparator);
	}

	/**
	 * Updates the kb template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kbTemplate the kb template
	 * @return the kb template that was updated
	 */
	public static com.liferay.knowledge.base.model.KBTemplate updateKBTemplate(
		com.liferay.knowledge.base.model.KBTemplate kbTemplate) {

		return getService().updateKBTemplate(kbTemplate);
	}

	public static com.liferay.knowledge.base.model.KBTemplate updateKBTemplate(
			long kbTemplateId, String title, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateKBTemplate(
			kbTemplateId, title, content, serviceContext);
	}

	public static void updateKBTemplateResources(
			com.liferay.knowledge.base.model.KBTemplate kbTemplate,
			String[] groupPermissions, String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateKBTemplateResources(
			kbTemplate, groupPermissions, guestPermissions);
	}

	public static KBTemplateLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KBTemplateLocalService, KBTemplateLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(KBTemplateLocalService.class);

		ServiceTracker<KBTemplateLocalService, KBTemplateLocalService>
			serviceTracker =
				new ServiceTracker
					<KBTemplateLocalService, KBTemplateLocalService>(
						bundle.getBundleContext(), KBTemplateLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}