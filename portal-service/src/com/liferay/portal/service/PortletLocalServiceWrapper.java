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

/**
 * Provides a wrapper for {@link PortletLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortletLocalService
 * @generated
 */
@ProviderType
public class PortletLocalServiceWrapper implements PortletLocalService,
	ServiceWrapper<PortletLocalService> {
	public PortletLocalServiceWrapper(PortletLocalService portletLocalService) {
		_portletLocalService = portletLocalService;
	}

	/**
	* Adds the portlet to the database. Also notifies the appropriate model listeners.
	*
	* @param portlet the portlet
	* @return the portlet that was added
	*/
	@Override
	public com.liferay.portal.model.Portlet addPortlet(
		com.liferay.portal.model.Portlet portlet) {
		return _portletLocalService.addPortlet(portlet);
	}

	@Override
	public void addPortletCategory(long companyId, java.lang.String categoryName) {
		_portletLocalService.addPortletCategory(companyId, categoryName);
	}

	@Override
	public void checkPortlet(com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.PortalException {
		_portletLocalService.checkPortlet(portlet);
	}

	@Override
	public void checkPortlets(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_portletLocalService.checkPortlets(companyId);
	}

	@Override
	public void clearCache() {
		_portletLocalService.clearCache();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #clearPortletsMap)}
	*/
	@Deprecated
	@Override
	public void clearCompanyPortletsPool() {
		_portletLocalService.clearCompanyPortletsPool();
	}

	@Override
	public void clearPortletsMap() {
		_portletLocalService.clearPortletsMap();
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #clonePortlet(String)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.Portlet clonePortlet(long companyId,
		java.lang.String portletId) {
		return _portletLocalService.clonePortlet(companyId, portletId);
	}

	@Override
	public com.liferay.portal.model.Portlet clonePortlet(
		java.lang.String portletId) {
		return _portletLocalService.clonePortlet(portletId);
	}

	/**
	* Creates a new portlet with the primary key. Does not add the portlet to the database.
	*
	* @param id the primary key for the new portlet
	* @return the new portlet
	*/
	@Override
	public com.liferay.portal.model.Portlet createPortlet(long id) {
		return _portletLocalService.createPortlet(id);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deletePortlet(long companyId, java.lang.String portletId,
		long plid) throws com.liferay.portal.kernel.exception.PortalException {
		_portletLocalService.deletePortlet(companyId, portletId, plid);
	}

	/**
	* Deletes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the portlet
	* @return the portlet that was removed
	* @throws PortalException if a portlet with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Portlet deletePortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletLocalService.deletePortlet(id);
	}

	/**
	* Deletes the portlet from the database. Also notifies the appropriate model listeners.
	*
	* @param portlet the portlet
	* @return the portlet that was removed
	*/
	@Override
	public com.liferay.portal.model.Portlet deletePortlet(
		com.liferay.portal.model.Portlet portlet) {
		return _portletLocalService.deletePortlet(portlet);
	}

	@Override
	public void deletePortlets(long companyId, java.lang.String[] portletIds,
		long plid) throws com.liferay.portal.kernel.exception.PortalException {
		_portletLocalService.deletePortlets(companyId, portletIds, plid);
	}

	@Override
	public com.liferay.portal.model.Portlet deployRemotePortlet(
		com.liferay.portal.model.Portlet portlet, java.lang.String categoryName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletLocalService.deployRemotePortlet(portlet, categoryName);
	}

	@Override
	public com.liferay.portal.model.Portlet deployRemotePortlet(
		com.liferay.portal.model.Portlet portlet,
		java.lang.String[] categoryNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletLocalService.deployRemotePortlet(portlet, categoryNames);
	}

	@Override
	public com.liferay.portal.model.Portlet deployRemotePortlet(
		com.liferay.portal.model.Portlet portlet,
		java.lang.String[] categoryNames, boolean eagerDestroy)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletLocalService.deployRemotePortlet(portlet, categoryNames,
			eagerDestroy);
	}

	@Override
	public void destroyPortlet(com.liferay.portal.model.Portlet portlet) {
		_portletLocalService.destroyPortlet(portlet);
	}

	@Override
	public void destroyRemotePortlet(com.liferay.portal.model.Portlet portlet) {
		_portletLocalService.destroyRemotePortlet(portlet);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _portletLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _portletLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _portletLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _portletLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _portletLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _portletLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.Portlet fetchPortlet(long id) {
		return _portletLocalService.fetchPortlet(id);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _portletLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portlet.expando.model.CustomAttributesDisplay> getCustomAttributesDisplays() {
		return _portletLocalService.getCustomAttributesDisplays();
	}

	@Override
	public com.liferay.portal.model.PortletCategory getEARDisplay(
		java.lang.String xml) {
		return _portletLocalService.getEARDisplay(xml);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Portlet> getFriendlyURLMapperPortlets() {
		return _portletLocalService.getFriendlyURLMapperPortlets();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.portlet.FriendlyURLMapper> getFriendlyURLMappers() {
		return _portletLocalService.getFriendlyURLMappers();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _portletLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _portletLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the portlet with the primary key.
	*
	* @param id the primary key of the portlet
	* @return the portlet
	* @throws PortalException if a portlet with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Portlet getPortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _portletLocalService.getPortlet(id);
	}

	@Override
	public com.liferay.portal.model.PortletApp getPortletApp(
		java.lang.String servletContextName) {
		return _portletLocalService.getPortletApp(servletContextName);
	}

	@Override
	public com.liferay.portal.model.Portlet getPortletById(long companyId,
		java.lang.String portletId) {
		return _portletLocalService.getPortletById(companyId, portletId);
	}

	@Override
	public com.liferay.portal.model.Portlet getPortletById(
		java.lang.String portletId) {
		return _portletLocalService.getPortletById(portletId);
	}

	@Override
	public com.liferay.portal.model.Portlet getPortletByStrutsPath(
		long companyId, java.lang.String strutsPath) {
		return _portletLocalService.getPortletByStrutsPath(companyId, strutsPath);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Portlet> getPortlets() {
		return _portletLocalService.getPortlets();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		long companyId) {
		return _portletLocalService.getPortlets(companyId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		long companyId, boolean showSystem, boolean showPortal) {
		return _portletLocalService.getPortlets(companyId, showSystem,
			showPortal);
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
	@Override
	public java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		int start, int end) {
		return _portletLocalService.getPortlets(start, end);
	}

	/**
	* Returns the number of portlets.
	*
	* @return the number of portlets
	*/
	@Override
	public int getPortletsCount() {
		return _portletLocalService.getPortletsCount();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Portlet> getScopablePortlets() {
		return _portletLocalService.getScopablePortlets();
	}

	@Override
	public com.liferay.portal.model.PortletCategory getWARDisplay(
		java.lang.String servletContextName, java.lang.String xml) {
		return _portletLocalService.getWARDisplay(servletContextName, xml);
	}

	@Override
	public boolean hasPortlet(long companyId, java.lang.String portletId) {
		return _portletLocalService.hasPortlet(companyId, portletId);
	}

	@Override
	public void initEAR(javax.servlet.ServletContext servletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		_portletLocalService.initEAR(servletContext, xmls, pluginPackage);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Portlet> initWAR(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _portletLocalService.initWAR(servletContextName, servletContext,
			xmls, pluginPackage);
	}

	@Override
	public java.util.Map<java.lang.String, com.liferay.portal.model.Portlet> loadGetPortletsMap(
		long companyId) {
		return _portletLocalService.loadGetPortletsMap(companyId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #loadGetPortletsMap(long))}
	*/
	@Deprecated
	@Override
	public java.util.Map<java.lang.String, com.liferay.portal.model.Portlet> loadGetPortletsPool(
		long companyId) {
		return _portletLocalService.loadGetPortletsPool(companyId);
	}

	@Override
	public void removeCompanyPortletsPool(long companyId) {
		_portletLocalService.removeCompanyPortletsPool(companyId);
	}

	@Override
	public com.liferay.portal.model.Portlet updatePortlet(long companyId,
		java.lang.String portletId, java.lang.String roles, boolean active) {
		return _portletLocalService.updatePortlet(companyId, portletId, roles,
			active);
	}

	/**
	* Updates the portlet in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portlet the portlet
	* @return the portlet that was updated
	*/
	@Override
	public com.liferay.portal.model.Portlet updatePortlet(
		com.liferay.portal.model.Portlet portlet) {
		return _portletLocalService.updatePortlet(portlet);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public PortletLocalService getWrappedPortletLocalService() {
		return _portletLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedPortletLocalService(
		PortletLocalService portletLocalService) {
		_portletLocalService = portletLocalService;
	}

	@Override
	public PortletLocalService getWrappedService() {
		return _portletLocalService;
	}

	@Override
	public void setWrappedService(PortletLocalService portletLocalService) {
		_portletLocalService = portletLocalService;
	}

	private PortletLocalService _portletLocalService;
}