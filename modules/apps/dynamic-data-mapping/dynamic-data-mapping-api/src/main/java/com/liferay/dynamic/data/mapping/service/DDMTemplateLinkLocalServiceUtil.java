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

package com.liferay.dynamic.data.mapping.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDMTemplateLink. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLinkLocalService
 * @generated
 */
public class DDMTemplateLinkLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ddm template link to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmTemplateLink the ddm template link
	 * @return the ddm template link that was added
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
		addDDMTemplateLink(
			com.liferay.dynamic.data.mapping.model.DDMTemplateLink
				ddmTemplateLink) {

		return getService().addDDMTemplateLink(ddmTemplateLink);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
		addTemplateLink(long classNameId, long classPK, long templateId) {

		return getService().addTemplateLink(classNameId, classPK, templateId);
	}

	/**
	 * Creates a new ddm template link with the primary key. Does not add the ddm template link to the database.
	 *
	 * @param templateLinkId the primary key for the new ddm template link
	 * @return the new ddm template link
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
		createDDMTemplateLink(long templateLinkId) {

		return getService().createDDMTemplateLink(templateLinkId);
	}

	/**
	 * Deletes the ddm template link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmTemplateLink the ddm template link
	 * @return the ddm template link that was removed
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
		deleteDDMTemplateLink(
			com.liferay.dynamic.data.mapping.model.DDMTemplateLink
				ddmTemplateLink) {

		return getService().deleteDDMTemplateLink(ddmTemplateLink);
	}

	/**
	 * Deletes the ddm template link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateLinkId the primary key of the ddm template link
	 * @return the ddm template link that was removed
	 * @throws PortalException if a ddm template link with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
			deleteDDMTemplateLink(long templateLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDDMTemplateLink(templateLinkId);
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

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
		deleteTemplateLink(
			com.liferay.dynamic.data.mapping.model.DDMTemplateLink
				templateLink) {

		return getService().deleteTemplateLink(templateLink);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
			deleteTemplateLink(long templateLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTemplateLink(templateLinkId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
		deleteTemplateLink(long classNameId, long classPK) {

		return getService().deleteTemplateLink(classNameId, classPK);
	}

	public static void deleteTemplateLinks(long templateId) {
		getService().deleteTemplateLinks(templateId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl</code>.
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

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
		fetchDDMTemplateLink(long templateLinkId) {

		return getService().fetchDDMTemplateLink(templateLinkId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the ddm template link with the primary key.
	 *
	 * @param templateLinkId the primary key of the ddm template link
	 * @return the ddm template link
	 * @throws PortalException if a ddm template link with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
			getDDMTemplateLink(long templateLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDMTemplateLink(templateLinkId);
	}

	/**
	 * Returns a range of all the ddm template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @return the range of ddm template links
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplateLink>
			getDDMTemplateLinks(int start, int end) {

		return getService().getDDMTemplateLinks(start, end);
	}

	/**
	 * Returns the number of ddm template links.
	 *
	 * @return the number of ddm template links
	 */
	public static int getDDMTemplateLinksCount() {
		return getService().getDDMTemplateLinksCount();
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
			getTemplateLink(long templateLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTemplateLink(templateLinkId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
			getTemplateLink(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTemplateLink(classNameId, classPK);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplateLink>
			getTemplateLinks(long classNameId) {

		return getService().getTemplateLinks(classNameId);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplateLink>
			getTemplateLinksByTemplateId(long templateId) {

		return getService().getTemplateLinksByTemplateId(templateId);
	}

	/**
	 * Updates the ddm template link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmTemplateLink the ddm template link
	 * @return the ddm template link that was updated
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
		updateDDMTemplateLink(
			com.liferay.dynamic.data.mapping.model.DDMTemplateLink
				ddmTemplateLink) {

		return getService().updateDDMTemplateLink(ddmTemplateLink);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
			updateTemplateLink(long templateLinkId, long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateTemplateLink(templateLinkId, templateId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink
		updateTemplateLink(long classNameId, long classPK, long templateId) {

		return getService().updateTemplateLink(
			classNameId, classPK, templateId);
	}

	public static DDMTemplateLinkLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMTemplateLinkLocalService, DDMTemplateLinkLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMTemplateLinkLocalService.class);

		ServiceTracker<DDMTemplateLinkLocalService, DDMTemplateLinkLocalService>
			serviceTracker =
				new ServiceTracker
					<DDMTemplateLinkLocalService, DDMTemplateLinkLocalService>(
						bundle.getBundleContext(),
						DDMTemplateLinkLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}