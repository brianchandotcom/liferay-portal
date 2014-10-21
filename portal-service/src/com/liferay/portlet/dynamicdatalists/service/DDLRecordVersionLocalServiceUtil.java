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

package com.liferay.portlet.dynamicdatalists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for DDLRecordVersion. This utility wraps
 * {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordVersionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionLocalService
 * @see com.liferay.portlet.dynamicdatalists.service.base.DDLRecordVersionLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordVersionLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDLRecordVersionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordVersionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d l record version to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordVersion the d d l record version
	* @return the d d l record version that was added
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion addDDLRecordVersion(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion ddlRecordVersion) {
		return getService().addDDLRecordVersion(ddlRecordVersion);
	}

	/**
	* Creates a new d d l record version with the primary key. Does not add the d d l record version to the database.
	*
	* @param recordVersionId the primary key for the new d d l record version
	* @return the new d d l record version
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion createDDLRecordVersion(
		long recordVersionId) {
		return getService().createDDLRecordVersion(recordVersionId);
	}

	/**
	* Deletes the d d l record version from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordVersion the d d l record version
	* @return the d d l record version that was removed
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion deleteDDLRecordVersion(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion ddlRecordVersion) {
		return getService().deleteDDLRecordVersion(ddlRecordVersion);
	}

	/**
	* Deletes the d d l record version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version that was removed
	* @throws PortalException if a d d l record version with the primary key could not be found
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion deleteDDLRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDLRecordVersion(recordVersionId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchDDLRecordVersion(
		long recordVersionId) {
		return getService().fetchDDLRecordVersion(recordVersionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Returns the d d l record version with the primary key.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version
	* @throws PortalException if a d d l record version with the primary key could not be found
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getDDLRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDLRecordVersion(recordVersionId);
	}

	/**
	* Returns a range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of d d l record versions
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> getDDLRecordVersions(
		int start, int end) {
		return getService().getDDLRecordVersions(start, end);
	}

	/**
	* Returns the number of d d l record versions.
	*
	* @return the number of d d l record versions
	*/
	public static int getDDLRecordVersionsCount() {
		return getService().getDDLRecordVersionsCount();
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getLatestRecordVersion(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestRecordVersion(recordId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordVersion(recordId, version);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordVersion(recordVersionId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> orderByComparator) {
		return getService()
				   .getRecordVersions(recordId, start, end, orderByComparator);
	}

	public static int getRecordVersionsCount(long recordId) {
		return getService().getRecordVersionsCount(recordId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the d d l record version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordVersion the d d l record version
	* @return the d d l record version that was updated
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion updateDDLRecordVersion(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion ddlRecordVersion) {
		return getService().updateDDLRecordVersion(ddlRecordVersion);
	}

	public static DDLRecordVersionLocalService getService() {
		if (_service == null) {
			_service = (DDLRecordVersionLocalService)PortalBeanLocatorUtil.locate(DDLRecordVersionLocalService.class.getName());

			ReferenceRegistry.registerReference(DDLRecordVersionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDLRecordVersionLocalService service) {
	}

	private static DDLRecordVersionLocalService _service;
}