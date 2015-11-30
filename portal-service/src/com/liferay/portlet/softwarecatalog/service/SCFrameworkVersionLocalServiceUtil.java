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
 * Provides the local service utility for SCFrameworkVersion. This utility wraps
 * {@link com.liferay.portlet.softwarecatalog.service.impl.SCFrameworkVersionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionLocalService
 * @see com.liferay.portlet.softwarecatalog.service.base.SCFrameworkVersionLocalServiceBaseImpl
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCFrameworkVersionLocalServiceImpl
 * @generated
 */
@ProviderType
public class SCFrameworkVersionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.softwarecatalog.service.impl.SCFrameworkVersionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String name, java.lang.String url,
		boolean active, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFrameworkVersion(userId, name, url, active, priority,
			serviceContext);
	}

	public static void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addFrameworkVersionResources(frameworkVersion,
			addGroupPermissions, addGuestPermissions);
	}

	public static void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addFrameworkVersionResources(frameworkVersion, modelPermissions);
	}

	public static void addFrameworkVersionResources(long frameworkVersionId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addFrameworkVersionResources(frameworkVersionId,
			addGroupPermissions, addGuestPermissions);
	}

	public static void addFrameworkVersionResources(long frameworkVersionId,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addFrameworkVersionResources(frameworkVersionId, modelPermissions);
	}

	/**
	* Adds the s c framework version to the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was added
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		return getService().addSCFrameworkVersion(scFrameworkVersion);
	}

	public static void addSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId) {
		getService()
			.addSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	public static void addSCProductVersionSCFrameworkVersion(
		long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		getService()
			.addSCProductVersionSCFrameworkVersion(productVersionId,
			scFrameworkVersion);
	}

	public static void addSCProductVersionSCFrameworkVersions(
		long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions) {
		getService()
			.addSCProductVersionSCFrameworkVersions(productVersionId,
			SCFrameworkVersions);
	}

	public static void addSCProductVersionSCFrameworkVersions(
		long productVersionId, long[] frameworkVersionIds) {
		getService()
			.addSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	public static void clearSCProductVersionSCFrameworkVersions(
		long productVersionId) {
		getService().clearSCProductVersionSCFrameworkVersions(productVersionId);
	}

	/**
	* Creates a new s c framework version with the primary key. Does not add the s c framework version to the database.
	*
	* @param frameworkVersionId the primary key for the new s c framework version
	* @return the new s c framework version
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion createSCFrameworkVersion(
		long frameworkVersionId) {
		return getService().createSCFrameworkVersion(frameworkVersionId);
	}

	public static void deleteFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion) {
		getService().deleteFrameworkVersion(frameworkVersion);
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFrameworkVersion(frameworkVersionId);
	}

	public static void deleteFrameworkVersions(long groupId) {
		getService().deleteFrameworkVersions(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version that was removed
	* @throws PortalException if a s c framework version with the primary key could not be found
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSCFrameworkVersion(frameworkVersionId);
	}

	/**
	* Deletes the s c framework version from the database. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was removed
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion deleteSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		return getService().deleteSCFrameworkVersion(scFrameworkVersion);
	}

	public static void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId) {
		getService()
			.deleteSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	public static void deleteSCProductVersionSCFrameworkVersion(
		long productVersionId,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		getService()
			.deleteSCProductVersionSCFrameworkVersion(productVersionId,
			scFrameworkVersion);
	}

	public static void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> SCFrameworkVersions) {
		getService()
			.deleteSCProductVersionSCFrameworkVersions(productVersionId,
			SCFrameworkVersions);
	}

	public static void deleteSCProductVersionSCFrameworkVersions(
		long productVersionId, long[] frameworkVersionIds) {
		getService()
			.deleteSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchSCFrameworkVersion(
		long frameworkVersionId) {
		return getService().fetchSCFrameworkVersion(frameworkVersionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFrameworkVersion(frameworkVersionId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active) {
		return getService().getFrameworkVersions(groupId, active);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end) {
		return getService().getFrameworkVersions(groupId, active, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, int start, int end) {
		return getService().getFrameworkVersions(groupId, start, end);
	}

	public static int getFrameworkVersionsCount(long groupId) {
		return getService().getFrameworkVersionsCount(groupId);
	}

	public static int getFrameworkVersionsCount(long groupId, boolean active) {
		return getService().getFrameworkVersionsCount(groupId, active);
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

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getProductVersionFrameworkVersions(
		long productVersionId) {
		return getService().getProductVersionFrameworkVersions(productVersionId);
	}

	/**
	* Returns the s c framework version with the primary key.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version
	* @throws PortalException if a s c framework version with the primary key could not be found
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSCFrameworkVersion(frameworkVersionId);
	}

	/**
	* Returns a range of all the s c framework versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of s c framework versions
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		int start, int end) {
		return getService().getSCFrameworkVersions(start, end);
	}

	/**
	* Returns the number of s c framework versions.
	*
	* @return the number of s c framework versions
	*/
	public static int getSCFrameworkVersionsCount() {
		return getService().getSCFrameworkVersionsCount();
	}

	/**
	* Returns the productVersionIds of the s c product versions associated with the s c framework version.
	*
	* @param frameworkVersionId the frameworkVersionId of the s c framework version
	* @return long[] the productVersionIds of s c product versions associated with the s c framework version
	*/
	public static long[] getSCProductVersionPrimaryKeys(long frameworkVersionId) {
		return getService().getSCProductVersionPrimaryKeys(frameworkVersionId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId) {
		return getService()
				   .getSCProductVersionSCFrameworkVersions(productVersionId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end) {
		return getService()
				   .getSCProductVersionSCFrameworkVersions(productVersionId,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCProductVersionSCFrameworkVersions(
		long productVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> orderByComparator) {
		return getService()
				   .getSCProductVersionSCFrameworkVersions(productVersionId,
			start, end, orderByComparator);
	}

	public static int getSCProductVersionSCFrameworkVersionsCount(
		long productVersionId) {
		return getService()
				   .getSCProductVersionSCFrameworkVersionsCount(productVersionId);
	}

	public static boolean hasSCProductVersionSCFrameworkVersion(
		long productVersionId, long frameworkVersionId) {
		return getService()
				   .hasSCProductVersionSCFrameworkVersion(productVersionId,
			frameworkVersionId);
	}

	public static boolean hasSCProductVersionSCFrameworkVersions(
		long productVersionId) {
		return getService()
				   .hasSCProductVersionSCFrameworkVersions(productVersionId);
	}

	public static void setSCProductVersionSCFrameworkVersions(
		long productVersionId, long[] frameworkVersionIds) {
		getService()
			.setSCProductVersionSCFrameworkVersions(productVersionId,
			frameworkVersionIds);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFrameworkVersion(frameworkVersionId, name, url,
			active, priority);
	}

	/**
	* Updates the s c framework version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scFrameworkVersion the s c framework version
	* @return the s c framework version that was updated
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		return getService().updateSCFrameworkVersion(scFrameworkVersion);
	}

	public static SCFrameworkVersionLocalService getService() {
		if (_service == null) {
			_service = (SCFrameworkVersionLocalService)PortalBeanLocatorUtil.locate(SCFrameworkVersionLocalService.class.getName());

			ReferenceRegistry.registerReference(SCFrameworkVersionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SCFrameworkVersionLocalService service) {
	}

	private static SCFrameworkVersionLocalService _service;
}