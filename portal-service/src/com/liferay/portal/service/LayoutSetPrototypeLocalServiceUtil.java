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
 * Provides the local service utility for LayoutSetPrototype. This utility wraps
 * {@link com.liferay.portal.service.impl.LayoutSetPrototypeLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototypeLocalService
 * @see com.liferay.portal.service.base.LayoutSetPrototypeLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutSetPrototypeLocalServiceImpl
 * @generated
 */
@ProviderType
public class LayoutSetPrototypeLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutSetPrototypeLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the layout set prototype to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetPrototype the layout set prototype
	* @return the layout set prototype that was added
	*/
	public static com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype) {
		return getService().addLayoutSetPrototype(layoutSetPrototype);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addLayoutSetPrototype(long,
	long, Map, Map, boolean, boolean, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		long userId, long companyId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		boolean layoutsUpdateable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutSetPrototype(userId, companyId, nameMap,
			description, active, layoutsUpdateable, serviceContext);
	}

	public static com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		long userId, long companyId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, boolean layoutsUpdateable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutSetPrototype(userId, companyId, nameMap,
			descriptionMap, active, layoutsUpdateable, serviceContext);
	}

	/**
	* Creates a new layout set prototype with the primary key. Does not add the layout set prototype to the database.
	*
	* @param layoutSetPrototypeId the primary key for the new layout set prototype
	* @return the new layout set prototype
	*/
	public static com.liferay.portal.model.LayoutSetPrototype createLayoutSetPrototype(
		long layoutSetPrototypeId) {
		return getService().createLayoutSetPrototype(layoutSetPrototypeId);
	}

	/**
	* Deletes the layout set prototype from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetPrototype the layout set prototype
	* @return the layout set prototype that was removed
	* @throws PortalException
	*/
	public static com.liferay.portal.model.LayoutSetPrototype deleteLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteLayoutSetPrototype(layoutSetPrototype);
	}

	/**
	* Deletes the layout set prototype with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetPrototypeId the primary key of the layout set prototype
	* @return the layout set prototype that was removed
	* @throws PortalException if a layout set prototype with the primary key could not be found
	*/
	public static com.liferay.portal.model.LayoutSetPrototype deleteLayoutSetPrototype(
		long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteLayoutSetPrototype(layoutSetPrototypeId);
	}

	public static void deleteLayoutSetPrototypes()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLayoutSetPrototypes();
	}

	public static void deleteNondefaultLayoutSetPrototypes(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteNondefaultLayoutSetPrototypes(companyId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.model.LayoutSetPrototype fetchLayoutSetPrototype(
		long layoutSetPrototypeId) {
		return getService().fetchLayoutSetPrototype(layoutSetPrototypeId);
	}

	/**
	* Returns the layout set prototype with the matching UUID and company.
	*
	* @param uuid the layout set prototype's UUID
	* @param companyId the primary key of the company
	* @return the matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	*/
	public static com.liferay.portal.model.LayoutSetPrototype fetchLayoutSetPrototypeByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .fetchLayoutSetPrototypeByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout set prototype with the primary key.
	*
	* @param layoutSetPrototypeId the primary key of the layout set prototype
	* @return the layout set prototype
	* @throws PortalException if a layout set prototype with the primary key could not be found
	*/
	public static com.liferay.portal.model.LayoutSetPrototype getLayoutSetPrototype(
		long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutSetPrototype(layoutSetPrototypeId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getLayoutSetPrototypeByUuidAndCompanyId(String, long)}
	*/
	@Deprecated
	public static com.liferay.portal.model.LayoutSetPrototype getLayoutSetPrototypeByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutSetPrototypeByUuid(uuid);
	}

	/**
	* Returns the layout set prototype with the matching UUID and company.
	*
	* @param uuid the layout set prototype's UUID
	* @param companyId the primary key of the company
	* @return the matching layout set prototype
	* @throws PortalException if a matching layout set prototype could not be found
	*/
	public static com.liferay.portal.model.LayoutSetPrototype getLayoutSetPrototypeByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayoutSetPrototypeByUuidAndCompanyId(uuid, companyId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> getLayoutSetPrototypes(
		long companyId) {
		return getService().getLayoutSetPrototypes(companyId);
	}

	/**
	* Returns a range of all the layout set prototypes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set prototypes
	* @param end the upper bound of the range of layout set prototypes (not inclusive)
	* @return the range of layout set prototypes
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> getLayoutSetPrototypes(
		int start, int end) {
		return getService().getLayoutSetPrototypes(start, end);
	}

	/**
	* Returns the number of layout set prototypes.
	*
	* @return the number of layout set prototypes
	*/
	public static int getLayoutSetPrototypesCount() {
		return getService().getLayoutSetPrototypesCount();
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

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> search(
		long companyId, java.lang.Boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.LayoutSetPrototype> obc) {
		return getService().search(companyId, active, start, end, obc);
	}

	public static int searchCount(long companyId, java.lang.Boolean active) {
		return getService().searchCount(companyId, active);
	}

	/**
	* Updates the layout set prototype in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutSetPrototype the layout set prototype
	* @return the layout set prototype that was updated
	*/
	public static com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype) {
		return getService().updateLayoutSetPrototype(layoutSetPrototype);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#updateLayoutSetPrototype(long, Map, Map, boolean, boolean,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		boolean layoutsUpdateable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutSetPrototype(layoutSetPrototypeId, nameMap,
			description, active, layoutsUpdateable, serviceContext);
	}

	public static com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, boolean layoutsUpdateable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutSetPrototype(layoutSetPrototypeId, nameMap,
			descriptionMap, active, layoutsUpdateable, serviceContext);
	}

	public static com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId, java.lang.String settings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutSetPrototype(layoutSetPrototypeId, settings);
	}

	public static LayoutSetPrototypeLocalService getService() {
		if (_service == null) {
			_service = (LayoutSetPrototypeLocalService)PortalBeanLocatorUtil.locate(LayoutSetPrototypeLocalService.class.getName());

			ReferenceRegistry.registerReference(LayoutSetPrototypeLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static LayoutSetPrototypeLocalService _service;
}