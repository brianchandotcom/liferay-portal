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

package com.liferay.portlet.softwarecatalog.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for SCProductScreenshot. This utility wraps
 * {@link com.liferay.portlet.softwarecatalog.service.impl.SCProductScreenshotLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductScreenshotLocalService
 * @see com.liferay.portlet.softwarecatalog.service.base.SCProductScreenshotLocalServiceBaseImpl
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCProductScreenshotLocalServiceImpl
 * @generated
 */
@ProviderType
public class SCProductScreenshotLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.softwarecatalog.service.impl.SCProductScreenshotLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the s c product screenshot to the database. Also notifies the appropriate model listeners.
	*
	* @param scProductScreenshot the s c product screenshot
	* @return the s c product screenshot that was added
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot addSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot) {
		return getService().addSCProductScreenshot(scProductScreenshot);
	}

	/**
	* Creates a new s c product screenshot with the primary key. Does not add the s c product screenshot to the database.
	*
	* @param productScreenshotId the primary key for the new s c product screenshot
	* @return the new s c product screenshot
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot createSCProductScreenshot(
		long productScreenshotId) {
		return getService().createSCProductScreenshot(productScreenshotId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot productScreenshot)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteProductScreenshot(productScreenshot);
	}

	public static void deleteProductScreenshots(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteProductScreenshots(productEntryId);
	}

	/**
	* Deletes the s c product screenshot with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productScreenshotId the primary key of the s c product screenshot
	* @return the s c product screenshot that was removed
	* @throws PortalException if a s c product screenshot with the primary key could not be found
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot deleteSCProductScreenshot(
		long productScreenshotId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSCProductScreenshot(productScreenshotId);
	}

	/**
	* Deletes the s c product screenshot from the database. Also notifies the appropriate model listeners.
	*
	* @param scProductScreenshot the s c product screenshot
	* @return the s c product screenshot that was removed
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot deleteSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot) {
		return getService().deleteSCProductScreenshot(scProductScreenshot);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot fetchSCProductScreenshot(
		long productScreenshotId) {
		return getService().fetchSCProductScreenshot(productScreenshotId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
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

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshot(
		long productEntryId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getProductScreenshot(productEntryId, priority);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshotByFullImageId(
		long fullImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getProductScreenshotByFullImageId(fullImageId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshotByThumbnailId(
		long thumbnailId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getProductScreenshotByThumbnailId(thumbnailId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getProductScreenshots(
		long productEntryId) {
		return getService().getProductScreenshots(productEntryId);
	}

	/**
	* Returns the s c product screenshot with the primary key.
	*
	* @param productScreenshotId the primary key of the s c product screenshot
	* @return the s c product screenshot
	* @throws PortalException if a s c product screenshot with the primary key could not be found
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getSCProductScreenshot(
		long productScreenshotId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSCProductScreenshot(productScreenshotId);
	}

	/**
	* Returns a range of all the s c product screenshots.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product screenshots
	* @param end the upper bound of the range of s c product screenshots (not inclusive)
	* @return the range of s c product screenshots
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getSCProductScreenshots(
		int start, int end) {
		return getService().getSCProductScreenshots(start, end);
	}

	/**
	* Returns the number of s c product screenshots.
	*
	* @return the number of s c product screenshots
	*/
	public static int getSCProductScreenshotsCount() {
		return getService().getSCProductScreenshotsCount();
	}

	/**
	* Updates the s c product screenshot in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scProductScreenshot the s c product screenshot
	* @return the s c product screenshot that was updated
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot updateSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot) {
		return getService().updateSCProductScreenshot(scProductScreenshot);
	}

	public static SCProductScreenshotLocalService getService() {
		if (_service == null) {
			_service = (SCProductScreenshotLocalService)PortalBeanLocatorUtil.locate(SCProductScreenshotLocalService.class.getName());

			ReferenceRegistry.registerReference(SCProductScreenshotLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SCProductScreenshotLocalService service) {
	}

	private static SCProductScreenshotLocalService _service;
}