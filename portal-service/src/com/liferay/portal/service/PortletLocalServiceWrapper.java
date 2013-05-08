/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

/**
 * Provides a wrapper for {@link PortletLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletLocalService
 * @generated
 */
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
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet addPortlet(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.addPortlet(portlet);
	}

	/**
	* Creates a new portlet with the primary key. Does not add the portlet to the database.
	*
	* @param id the primary key for the new portlet
	* @return the new portlet
	*/
	public com.liferay.portal.model.Portlet createPortlet(long id) {
		return _portletLocalService.createPortlet(id);
	}

	/**
	* Deletes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the portlet
	* @return the portlet that was removed
	* @throws PortalException if a portlet with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet deletePortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.deletePortlet(id);
	}

	/**
	* Deletes the portlet from the database. Also notifies the appropriate model listeners.
	*
	* @param portlet the portlet
	* @return the portlet that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet deletePortlet(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.deletePortlet(portlet);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _portletLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.Portlet fetchPortlet(long id)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.fetchPortlet(id);
	}

	/**
	* Returns the portlet with the primary key.
	*
	* @param id the primary key of the portlet
	* @return the portlet
	* @throws PortalException if a portlet with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet getPortlet(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortlet(id);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortlets(start, end);
	}

	/**
	* Returns the number of portlets.
	*
	* @return the number of portlets
	* @throws SystemException if a system exception occurred
	*/
	public int getPortletsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortletsCount();
	}

	/**
	* Updates the portlet in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portlet the portlet
	* @return the portlet that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet updatePortlet(
		com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.updatePortlet(portlet);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _portletLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_portletLocalService.setBeanIdentifier(beanIdentifier);
	}

	public void addPortletCategory(long companyId, java.lang.String categoryName) {
		_portletLocalService.addPortletCategory(companyId, categoryName);
	}

	public void checkPortlet(com.liferay.portal.model.Portlet portlet)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletLocalService.checkPortlet(portlet);
	}

	public void checkPortlets(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletLocalService.checkPortlets(companyId);
	}

	public void clearCache() {
		_portletLocalService.clearCache();
	}

	public void clearCompanyPortletsPool() {
		_portletLocalService.clearCompanyPortletsPool();
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #clonePortlet(String)}
	*/
	public com.liferay.portal.model.Portlet clonePortlet(long companyId,
		java.lang.String portletId) {
		return _portletLocalService.clonePortlet(companyId, portletId);
	}

	public com.liferay.portal.model.Portlet clonePortlet(
		java.lang.String portletId) {
		return _portletLocalService.clonePortlet(portletId);
	}

	public void deletePortlet(long companyId, java.lang.String portletId,
		long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletLocalService.deletePortlet(companyId, portletId, plid);
	}

	public void deletePortlets(long companyId, java.lang.String[] portletIds,
		long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletLocalService.deletePortlets(companyId, portletIds, plid);
	}

	public com.liferay.portal.model.Portlet deployRemotePortlet(
		com.liferay.portal.model.Portlet portlet, java.lang.String categoryName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.deployRemotePortlet(portlet, categoryName);
	}

	public com.liferay.portal.model.Portlet deployRemotePortlet(
		com.liferay.portal.model.Portlet portlet,
		java.lang.String[] categoryNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.deployRemotePortlet(portlet, categoryNames);
	}

	public void destroyPortlet(com.liferay.portal.model.Portlet portlet) {
		_portletLocalService.destroyPortlet(portlet);
	}

	public void destroyRemotePortlet(com.liferay.portal.model.Portlet portlet) {
		_portletLocalService.destroyRemotePortlet(portlet);
	}

	public java.util.List<com.liferay.portlet.expando.model.CustomAttributesDisplay> getCustomAttributesDisplays() {
		return _portletLocalService.getCustomAttributesDisplays();
	}

	public com.liferay.portal.model.PortletCategory getEARDisplay(
		java.lang.String xml)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getEARDisplay(xml);
	}

	public java.util.List<com.liferay.portal.model.Portlet> getFriendlyURLMapperPortlets() {
		return _portletLocalService.getFriendlyURLMapperPortlets();
	}

	public java.util.List<com.liferay.portal.kernel.portlet.FriendlyURLMapper> getFriendlyURLMappers() {
		return _portletLocalService.getFriendlyURLMappers();
	}

	public com.liferay.portal.model.PortletApp getPortletApp(
		java.lang.String servletContextName) {
		return _portletLocalService.getPortletApp(servletContextName);
	}

	public com.liferay.portal.model.Portlet getPortletById(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortletById(companyId, portletId);
	}

	public com.liferay.portal.model.Portlet getPortletById(
		java.lang.String portletId) {
		return _portletLocalService.getPortletById(portletId);
	}

	public com.liferay.portal.model.Portlet getPortletByStrutsPath(
		long companyId, java.lang.String strutsPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortletByStrutsPath(companyId, strutsPath);
	}

	public java.util.List<com.liferay.portal.model.Portlet> getPortlets() {
		return _portletLocalService.getPortlets();
	}

	public java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortlets(companyId);
	}

	public java.util.List<com.liferay.portal.model.Portlet> getPortlets(
		long companyId, boolean showSystem, boolean showPortal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getPortlets(companyId, showSystem,
			showPortal);
	}

	public java.util.List<com.liferay.portal.model.Portlet> getScopablePortlets() {
		return _portletLocalService.getScopablePortlets();
	}

	public com.liferay.portal.model.PortletCategory getWARDisplay(
		java.lang.String servletContextName, java.lang.String xml)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.getWARDisplay(servletContextName, xml);
	}

	public boolean hasPortlet(long companyId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.hasPortlet(companyId, portletId);
	}

	public void initEAR(javax.servlet.ServletContext servletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		_portletLocalService.initEAR(servletContext, xmls, pluginPackage);
	}

	public java.util.List<com.liferay.portal.model.Portlet> initWAR(
		java.lang.String servletContextName,
		javax.servlet.ServletContext servletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		return _portletLocalService.initWAR(servletContextName, servletContext,
			xmls, pluginPackage);
	}

	public java.util.Map<java.lang.String, com.liferay.portal.model.Portlet> loadGetPortletsPool(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.loadGetPortletsPool(companyId);
	}

	public void removeCompanyPortletsPool(long companyId) {
		_portletLocalService.removeCompanyPortletsPool(companyId);
	}

	public com.liferay.portal.model.Portlet updatePortlet(long companyId,
		java.lang.String portletId, java.lang.String roles, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletLocalService.updatePortlet(companyId, portletId, roles,
			active);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public PortletLocalService getWrappedPortletLocalService() {
		return _portletLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedPortletLocalService(
		PortletLocalService portletLocalService) {
		_portletLocalService = portletLocalService;
	}

	public PortletLocalService getWrappedService() {
		return _portletLocalService;
	}

	public void setWrappedService(PortletLocalService portletLocalService) {
		_portletLocalService = portletLocalService;
	}

	private PortletLocalService _portletLocalService;
}