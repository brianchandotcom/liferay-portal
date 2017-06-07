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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Portlet. This utility wraps
 * {@link com.liferay.portal.service.impl.PortletLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PortletLocalService
 * @see com.liferay.portal.service.base.PortletLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.PortletLocalServiceImpl
 * @generated
 */
@ProviderType
public class PortletLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PortletLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static boolean hasPortlet(long companyId, java.lang.String portletId) {
		return getService().hasPortlet(companyId, portletId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the portlet to the database. Also notifies the appropriate model listeners.
	*
	* @param portlet the portlet
	* @return the portlet that was added
	*/
	public static com.liferay.portal.kernel.model.Portlet addPortlet(
		com.liferay.portal.kernel.model.Portlet portlet) {
		return getService().addPortlet(portlet);
	}

	public static com.liferay.portal.kernel.model.Portlet clonePortlet(
		java.lang.String portletId) {
		return getService().clonePortlet(portletId);
	}

	/**
	* Creates a new portlet with the primary key. Does not add the portlet to the database.
	*
	* @param id the primary key for the new portlet
	* @return the new portlet
	*/
	public static com.liferay.portal.kernel.model.Portlet createPortlet(long id) {
		return getService().createPortlet(id);
	}

	/**
	* Deletes the portlet from the database. Also notifies the appropriate model listeners.
	*
	* @param portlet the portlet
	* @return the portlet that was removed
	*/
	public static com.liferay.portal.kernel.model.Portlet deletePortlet(
		com.liferay.portal.kernel.model.Portlet portlet) {
		return getService().deletePortlet(portlet);
	}

	/**
	* Deletes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the portlet
	* @return the portlet that was removed
	* @throws PortalException if a portlet with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.Portlet deletePortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePortlet(id);
	}

	public static com.liferay.portal.kernel.model.Portlet deployRemotePortlet(
		com.liferay.portal.kernel.model.Portlet portlet,
		java.lang.String categoryName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deployRemotePortlet(portlet, categoryName);
	}

	public static com.liferay.portal.kernel.model.Portlet deployRemotePortlet(
		com.liferay.portal.kernel.model.Portlet portlet,
		java.lang.String[] categoryNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deployRemotePortlet(portlet, categoryNames);
	}

	public static com.liferay.portal.kernel.model.Portlet deployRemotePortlet(
		com.liferay.portal.kernel.model.Portlet portlet,
		java.lang.String[] categoryNames, boolean eagerDestroy)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deployRemotePortlet(portlet, categoryNames, eagerDestroy);
	}

	public static com.liferay.portal.kernel.model.Portlet fetchPortlet(long id) {
		return getService().fetchPortlet(id);
	}

	public static com.liferay.portal.kernel.model.Portlet fetchPortletById(
		long companyId, java.lang.String portletId) {
		return getService().fetchPortletById(companyId, portletId);
	}

	/**
	* Returns the portlet with the primary key.
	*
	* @param id the primary key of the portlet
	* @return the portlet
	* @throws PortalException if a portlet with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.Portlet getPortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPortlet(id);
	}

	public static com.liferay.portal.kernel.model.Portlet getPortletById(
		java.lang.String portletId) {
		return getService().getPortletById(portletId);
	}

	public static com.liferay.portal.kernel.model.Portlet getPortletById(
		long companyId, java.lang.String portletId) {
		return getService().getPortletById(companyId, portletId);
	}

	public static com.liferay.portal.kernel.model.Portlet getPortletByStrutsPath(
		long companyId, java.lang.String strutsPath) {
		return getService().getPortletByStrutsPath(companyId, strutsPath);
	}

	/**
	* Updates the portlet in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portlet the portlet
	* @return the portlet that was updated
	*/
	public static com.liferay.portal.kernel.model.Portlet updatePortlet(
		com.liferay.portal.kernel.model.Portlet portlet) {
		return getService().updatePortlet(portlet);
	}

	public static com.liferay.portal.kernel.model.Portlet updatePortlet(
		long companyId, java.lang.String portletId, java.lang.String roles,
		boolean active) {
		return getService().updatePortlet(companyId, portletId, roles, active);
	}

	public static com.liferay.portal.kernel.model.PortletApp getPortletApp(
		java.lang.String servletContextName) {
		return getService().getPortletApp(servletContextName);
	}

	public static com.liferay.portal.kernel.model.PortletCategory getEARDisplay(
		java.lang.String xml) {
		return getService().getEARDisplay(xml);
	}

	public static com.liferay.portal.kernel.model.PortletCategory getWARDisplay(
		java.lang.String servletContextName, java.lang.String xml) {
		return getService().getWARDisplay(servletContextName, xml);
	}

	/**
	* Returns the number of portlets.
	*
	* @return the number of portlets
	*/
	public static int getPortletsCount() {
		return getService().getPortletsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static java.util.List<com.liferay.expando.kernel.model.CustomAttributesDisplay> getCustomAttributesDisplays() {
		return getService().getCustomAttributesDisplays();
	}

	public static java.util.List<com.liferay.portal.kernel.model.Portlet> getFriendlyURLMapperPortlets() {
		return getService().getFriendlyURLMapperPortlets();
	}

	public static java.util.List<com.liferay.portal.kernel.portlet.FriendlyURLMapper> getFriendlyURLMappers() {
		return getService().getFriendlyURLMappers();
	}

	public static java.util.List<com.liferay.portal.kernel.model.Portlet> getPortlets() {
		return getService().getPortlets();
	}

	/**
	* Returns a range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @return the range of portlets
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Portlet> getPortlets(
		int start, int end) {
		return getService().getPortlets(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Portlet> getPortlets(
		long companyId) {
		return getService().getPortlets(companyId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Portlet> getPortlets(
		long companyId, boolean showSystem, boolean showPortal) {
		return getService().getPortlets(companyId, showSystem, showPortal);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Portlet> getScopablePortlets() {
		return getService().getScopablePortlets();
	}

	public static java.util.List<com.liferay.portal.kernel.model.Portlet> initWAR(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return getService()
				   .initWAR(servletContextName, servletContext, xmls,
			pluginPackage);
	}

	public static java.util.Map<java.lang.String, com.liferay.portal.kernel.model.Portlet> loadGetPortletsMap(
		long companyId) {
		return getService().loadGetPortletsMap(companyId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #loadGetPortletsMap(long))}
	*/
	@Deprecated
	public static java.util.Map<java.lang.String, com.liferay.portal.kernel.model.Portlet> loadGetPortletsPool(
		long companyId) {
		return getService().loadGetPortletsPool(companyId);
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

	public static void addPortletCategory(long companyId,
		java.lang.String categoryName) {
		getService().addPortletCategory(companyId, categoryName);
	}

	public static void checkPortlet(
		com.liferay.portal.kernel.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkPortlet(portlet);
	}

	public static void checkPortlets(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkPortlets(companyId);
	}

	public static void clearCache() {
		getService().clearCache();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #clearPortletsMap)}
	*/
	@Deprecated
	public static void clearCompanyPortletsPool() {
		getService().clearCompanyPortletsPool();
	}

	public static void clearPortletsMap() {
		getService().clearPortletsMap();
	}

	public static void deletePortlet(long companyId,
		java.lang.String portletId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deletePortlet(companyId, portletId, plid);
	}

	public static void deletePortlets(long companyId,
		java.lang.String[] portletIds, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deletePortlets(companyId, portletIds, plid);
	}

	public static void deployPortlet(
		com.liferay.portal.kernel.model.Portlet portlet)
		throws java.lang.Exception {
		getService().deployPortlet(portlet);
	}

	public static void destroyPortlet(
		com.liferay.portal.kernel.model.Portlet portlet) {
		getService().destroyPortlet(portlet);
	}

	public static void destroyRemotePortlet(
		com.liferay.portal.kernel.model.Portlet portlet) {
		getService().destroyRemotePortlet(portlet);
	}

	public static void initEAR(javax.servlet.ServletContext servletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		getService().initEAR(servletContext, xmls, pluginPackage);
	}

	public static void removeCompanyPortletsPool(long companyId) {
		getService().removeCompanyPortletsPool(companyId);
	}

	public static void visitPortlets(long companyId,
		java.util.function.Consumer<com.liferay.portal.kernel.model.Portlet> consumer) {
		getService().visitPortlets(companyId, consumer);
	}

	public static PortletLocalService getService() {
		if (_service == null) {
			_service = (PortletLocalService)PortalBeanLocatorUtil.locate(PortletLocalService.class.getName());

			ReferenceRegistry.registerReference(PortletLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static PortletLocalService _service;
}