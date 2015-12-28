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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for LayoutBranch. This utility wraps
 * {@link com.liferay.portal.service.impl.LayoutBranchLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchLocalService
 * @see com.liferay.portal.service.base.LayoutBranchLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutBranchLocalServiceImpl
 * @generated
 */
@ProviderType
public class LayoutBranchLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutBranchLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the layout branch to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranch the layout branch
	* @return the layout branch that was added
	*/
	public static com.liferay.portal.model.LayoutBranch addLayoutBranch(
		com.liferay.portal.model.LayoutBranch layoutBranch) {
		return getService().addLayoutBranch(layoutBranch);
	}

	public static com.liferay.portal.model.LayoutBranch addLayoutBranch(
		long layoutRevisionId, java.lang.String name,
		java.lang.String description, boolean master,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutBranch(layoutRevisionId, name, description,
			master, serviceContext);
	}

	public static com.liferay.portal.model.LayoutBranch addLayoutBranch(
		long layoutSetBranchId, long plid, java.lang.String name,
		java.lang.String description, boolean master,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutBranch(layoutSetBranchId, plid, name, description,
			master, serviceContext);
	}

	/**
	* Creates a new layout branch with the primary key. Does not add the layout branch to the database.
	*
	* @param layoutBranchId the primary key for the new layout branch
	* @return the new layout branch
	*/
	public static com.liferay.portal.model.LayoutBranch createLayoutBranch(
		long layoutBranchId) {
		return getService().createLayoutBranch(layoutBranchId);
	}

	/**
	* Deletes the layout branch from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranch the layout branch
	* @return the layout branch that was removed
	*/
	public static com.liferay.portal.model.LayoutBranch deleteLayoutBranch(
		com.liferay.portal.model.LayoutBranch layoutBranch) {
		return getService().deleteLayoutBranch(layoutBranch);
	}

	/**
	* Deletes the layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutBranchId the primary key of the layout branch
	* @return the layout branch that was removed
	* @throws PortalException if a layout branch with the primary key could not be found
	*/
	public static com.liferay.portal.model.LayoutBranch deleteLayoutBranch(
		long layoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteLayoutBranch(layoutBranchId);
	}

	public static void deleteLayoutSetBranchLayoutBranches(
		long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLayoutSetBranchLayoutBranches(layoutSetBranchId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.model.LayoutBranch fetchLayoutBranch(
		long layoutBranchId) {
		return getService().fetchLayoutBranch(layoutBranchId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout branch with the primary key.
	*
	* @param layoutBranchId the primary key of the layout branch
	* @return the layout branch
	* @throws PortalException if a layout branch with the primary key could not be found
	*/
	public static com.liferay.portal.model.LayoutBranch getLayoutBranch(
		long layoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutBranch(layoutBranchId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutBranch> getLayoutBranches(
		long layoutSetBranchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.LayoutBranch> orderByComparator) {
		return getService()
				   .getLayoutBranches(layoutSetBranchId, plid, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the layout branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout branchs
	* @param end the upper bound of the range of layout branchs (not inclusive)
	* @return the range of layout branchs
	*/
	public static java.util.List<com.liferay.portal.model.LayoutBranch> getLayoutBranchs(
		int start, int end) {
		return getService().getLayoutBranchs(start, end);
	}

	/**
	* Returns the number of layout branchs.
	*
	* @return the number of layout branchs
	*/
	public static int getLayoutBranchsCount() {
		return getService().getLayoutBranchsCount();
	}

	public static java.util.List<com.liferay.portal.model.LayoutBranch> getLayoutSetBranchLayoutBranches(
		long layoutSetBranchId) {
		return getService().getLayoutSetBranchLayoutBranches(layoutSetBranchId);
	}

	public static com.liferay.portal.model.LayoutBranch getMasterLayoutBranch(
		long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMasterLayoutBranch(layoutSetBranchId, plid);
	}

	public static com.liferay.portal.model.LayoutBranch getMasterLayoutBranch(
		long layoutSetBranchId, long plid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMasterLayoutBranch(layoutSetBranchId, plid,
			serviceContext);
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

	/**
	* Updates the layout branch in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutBranch the layout branch
	* @return the layout branch that was updated
	*/
	public static com.liferay.portal.model.LayoutBranch updateLayoutBranch(
		com.liferay.portal.model.LayoutBranch layoutBranch) {
		return getService().updateLayoutBranch(layoutBranch);
	}

	public static com.liferay.portal.model.LayoutBranch updateLayoutBranch(
		long layoutBranchId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutBranch(layoutBranchId, name, description,
			serviceContext);
	}

	public static LayoutBranchLocalService getService() {
		if (_service == null) {
			_service = (LayoutBranchLocalService)PortalBeanLocatorUtil.locate(LayoutBranchLocalService.class.getName());

			ReferenceRegistry.registerReference(LayoutBranchLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static LayoutBranchLocalService _service;
}