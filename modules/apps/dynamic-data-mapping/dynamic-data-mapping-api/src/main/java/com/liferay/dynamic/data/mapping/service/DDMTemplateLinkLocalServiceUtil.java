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

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDMTemplateLink. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLinkLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLinkLocalService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMTemplateLinkLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLinkLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDMTemplateLinkLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLinkLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m template link to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateLink the d d m template link
	* @return the d d m template link that was added
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink addDDMTemplateLink(
		com.liferay.dynamic.data.mapping.model.DDMTemplateLink ddmTemplateLink) {
		return getService().addDDMTemplateLink(ddmTemplateLink);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink addTemplateLink(
		long classNameId, long classPK, long templateId) {
		return getService().addTemplateLink(classNameId, classPK, templateId);
	}

	/**
	* Creates a new d d m template link with the primary key. Does not add the d d m template link to the database.
	*
	* @param templateLinkId the primary key for the new d d m template link
	* @return the new d d m template link
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink createDDMTemplateLink(
		long templateLinkId) {
		return getService().createDDMTemplateLink(templateLinkId);
	}

	/**
	* Deletes the d d m template link from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateLink the d d m template link
	* @return the d d m template link that was removed
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink deleteDDMTemplateLink(
		com.liferay.dynamic.data.mapping.model.DDMTemplateLink ddmTemplateLink) {
		return getService().deleteDDMTemplateLink(ddmTemplateLink);
	}

	/**
	* Deletes the d d m template link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateLinkId the primary key of the d d m template link
	* @return the d d m template link that was removed
	* @throws PortalException if a d d m template link with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink deleteDDMTemplateLink(
		long templateLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDMTemplateLink(templateLinkId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink deleteTemplateLink(
		long classNameId, long classPK) {
		return getService().deleteTemplateLink(classNameId, classPK);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink deleteTemplateLink(
		com.liferay.dynamic.data.mapping.model.DDMTemplateLink templateLink) {
		return getService().deleteTemplateLink(templateLink);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink deleteTemplateLink(
		long templateLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteTemplateLink(templateLinkId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink fetchDDMTemplateLink(
		long templateLinkId) {
		return getService().fetchDDMTemplateLink(templateLinkId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the d d m template link with the primary key.
	*
	* @param templateLinkId the primary key of the d d m template link
	* @return the d d m template link
	* @throws PortalException if a d d m template link with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink getDDMTemplateLink(
		long templateLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMTemplateLink(templateLinkId);
	}

	/**
	* Returns a range of all the d d m template links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @return the range of d d m template links
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplateLink> getDDMTemplateLinks(
		int start, int end) {
		return getService().getDDMTemplateLinks(start, end);
	}

	/**
	* Returns the number of d d m template links.
	*
	* @return the number of d d m template links
	*/
	public static int getDDMTemplateLinksCount() {
		return getService().getDDMTemplateLinksCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink getTemplateLink(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplateLink(classNameId, classPK);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink getTemplateLink(
		long templateLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplateLink(templateLinkId);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplateLink> getTemplateLinks(
		long classNameId) {
		return getService().getTemplateLinks(classNameId);
	}

	/**
	* Updates the d d m template link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateLink the d d m template link
	* @return the d d m template link that was updated
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink updateDDMTemplateLink(
		com.liferay.dynamic.data.mapping.model.DDMTemplateLink ddmTemplateLink) {
		return getService().updateDDMTemplateLink(ddmTemplateLink);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink updateTemplateLink(
		long classNameId, long classPK, long templateId) {
		return getService().updateTemplateLink(classNameId, classPK, templateId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateLink updateTemplateLink(
		long templateLinkId, long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateTemplateLink(templateLinkId, templateId);
	}

	public static DDMTemplateLinkLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMTemplateLinkLocalService, DDMTemplateLinkLocalService> _serviceTracker =
		ServiceTrackerFactory.open(DDMTemplateLinkLocalService.class);
}