/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Portlet}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Portlet
 * @generated
 */
public class PortletWrapper implements Portlet, ModelWrapper<Portlet> {
	public PortletWrapper(Portlet portlet) {
		_portlet = portlet;
	}

	public Class<?> getModelClass() {
		return Portlet.class;
	}

	public String getModelClassName() {
		return Portlet.class.getName();
	}

	/**
	* Returns the primary key of this portlet.
	*
	* @return the primary key of this portlet
	*/
	public long getPrimaryKey() {
		return _portlet.getPrimaryKey();
	}

	/**
	* Sets the primary key of this portlet.
	*
	* @param primaryKey the primary key of this portlet
	*/
	public void setPrimaryKey(long primaryKey) {
		_portlet.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the ID of this portlet.
	*
	* @return the ID of this portlet
	*/
	public long getId() {
		return _portlet.getId();
	}

	/**
	* Sets the ID of this portlet.
	*
	* @param id the ID of this portlet
	*/
	public void setId(long id) {
		_portlet.setId(id);
	}

	/**
	* Returns the company ID of this portlet.
	*
	* @return the company ID of this portlet
	*/
	public long getCompanyId() {
		return _portlet.getCompanyId();
	}

	/**
	* Sets the company ID of this portlet.
	*
	* @param companyId the company ID of this portlet
	*/
	public void setCompanyId(long companyId) {
		_portlet.setCompanyId(companyId);
	}

	/**
	* Returns the portlet ID of this portlet.
	*
	* @return the portlet ID of this portlet
	*/
	public java.lang.String getPortletId() {
		return _portlet.getPortletId();
	}

	/**
	* Sets the portlet ID of this portlet.
	*
	* @param portletId the portlet ID of this portlet
	*/
	public void setPortletId(java.lang.String portletId) {
		_portlet.setPortletId(portletId);
	}

	/**
	* Returns the roles of this portlet.
	*
	* @return the roles of this portlet
	*/
	public java.lang.String getRoles() {
		return _portlet.getRoles();
	}

	/**
	* Sets the roles of this portlet.
	*
	* @param roles the roles of this portlet
	*/
	public void setRoles(java.lang.String roles) {
		_portlet.setRoles(roles);
	}

	/**
	* Returns the active of this portlet.
	*
	* @return the active of this portlet
	*/
	public boolean getActive() {
		return _portlet.getActive();
	}

	/**
	* Returns <code>true</code> if this portlet is active.
	*
	* @return <code>true</code> if this portlet is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _portlet.isActive();
	}

	/**
	* Sets whether this portlet is active.
	*
	* @param active the active of this portlet
	*/
	public void setActive(boolean active) {
		_portlet.setActive(active);
	}

	public boolean isNew() {
		return _portlet.isNew();
	}

	public void setNew(boolean n) {
		_portlet.setNew(n);
	}

	public boolean isCachedModel() {
		return _portlet.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_portlet.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _portlet.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _portlet.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_portlet.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portlet.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portlet.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PortletWrapper((Portlet)_portlet.clone());
	}

	public int compareTo(com.liferay.portal.model.Portlet portlet) {
		return _portlet.compareTo(portlet);
	}

	@Override
	public int hashCode() {
		return _portlet.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Portlet> toCacheModel() {
		return _portlet.toCacheModel();
	}

	public com.liferay.portal.model.Portlet toEscapedModel() {
		return new PortletWrapper(_portlet.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _portlet.toString();
	}

	public java.lang.String toXmlString() {
		return _portlet.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portlet.persist();
	}

	/**
	* Returns the root portlet of this portlet instance.
	*
	* @return the root portlet of this portlet instance
	*/
	public com.liferay.portal.model.Portlet getRootPortlet() {
		return _portlet.getRootPortlet();
	}

	/**
	* Returns the root portlet ID of the portlet.
	*
	* @return the root portlet ID of the portlet
	*/
	public java.lang.String getRootPortletId() {
		return _portlet.getRootPortletId();
	}

	/**
	* Returns the instance ID of the portlet.
	*
	* @return the instance ID of the portlet
	*/
	public java.lang.String getInstanceId() {
		return _portlet.getInstanceId();
	}

	/**
	* Returns the plugin ID of the portlet.
	*
	* @return the plugin ID of the portlet
	*/
	public java.lang.String getPluginId() {
		return _portlet.getPluginId();
	}

	/**
	* Returns the plugin type of the portlet.
	*
	* @return the plugin type of the portlet
	*/
	public java.lang.String getPluginType() {
		return _portlet.getPluginType();
	}

	/**
	* Returns this portlet's plugin package.
	*
	* @return this portlet's plugin package
	*/
	public com.liferay.portal.kernel.plugin.PluginPackage getPluginPackage() {
		return _portlet.getPluginPackage();
	}

	/**
	* Sets this portlet's plugin package.
	*
	* @param pluginPackage this portlet's plugin package
	*/
	public void setPluginPackage(
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		_portlet.setPluginPackage(pluginPackage);
	}

	/**
	* Get the default plugin settings of the portlet.
	*
	* @return the plugin settings
	*/
	public com.liferay.portal.model.PluginSetting getDefaultPluginSetting() {
		return _portlet.getDefaultPluginSetting();
	}

	/**
	* Sets the default plugin settings of the portlet.
	*
	* @param pluginSetting the plugin setting
	*/
	public void setDefaultPluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting) {
		_portlet.setDefaultPluginSetting(pluginSetting);
	}

	/**
	* Returns the timestamp of the portlet.
	*
	* @return the timestamp of the portlet
	*/
	public long getTimestamp() {
		return _portlet.getTimestamp();
	}

	/**
	* Sets the timestamp of the portlet.
	*
	* @param timestamp the timestamp of the portlet
	*/
	public void setTimestamp(long timestamp) {
		_portlet.setTimestamp(timestamp);
	}

	/**
	* Returns the icon of the portlet.
	*
	* @return the icon of the portlet
	*/
	public java.lang.String getIcon() {
		return _portlet.getIcon();
	}

	/**
	* Sets the icon of the portlet.
	*
	* @param icon the icon of the portlet
	*/
	public void setIcon(java.lang.String icon) {
		_portlet.setIcon(icon);
	}

	/**
	* Returns the virtual path of the portlet.
	*
	* @return the virtual path of the portlet
	*/
	public java.lang.String getVirtualPath() {
		return _portlet.getVirtualPath();
	}

	/**
	* Sets the virtual path of the portlet.
	*
	* @param virtualPath the virtual path of the portlet
	*/
	public void setVirtualPath(java.lang.String virtualPath) {
		_portlet.setVirtualPath(virtualPath);
	}

	/**
	* Returns the struts path of the portlet.
	*
	* @return the struts path of the portlet
	*/
	public java.lang.String getStrutsPath() {
		return _portlet.getStrutsPath();
	}

	/**
	* Sets the struts path of the portlet.
	*
	* @param strutsPath the struts path of the portlet
	*/
	public void setStrutsPath(java.lang.String strutsPath) {
		_portlet.setStrutsPath(strutsPath);
	}

	/**
	* Returns the parent struts path of the portlet.
	*
	* @return the parent struts path of the portlet.
	*/
	public java.lang.String getParentStrutsPath() {
		return _portlet.getParentStrutsPath();
	}

	/**
	* Sets the parent struts path of the portlet.
	*
	* @param parentStrutsPath the parent struts path of the portlet
	*/
	public void setParentStrutsPath(java.lang.String parentStrutsPath) {
		_portlet.setParentStrutsPath(parentStrutsPath);
	}

	/**
	* Returns the name of the portlet.
	*
	* @return the display name of the portlet
	*/
	public java.lang.String getPortletName() {
		return _portlet.getPortletName();
	}

	/**
	* Sets the name of the portlet.
	*
	* @param portletName the name of the portlet
	*/
	public void setPortletName(java.lang.String portletName) {
		_portlet.setPortletName(portletName);
	}

	/**
	* Returns the display name of the portlet.
	*
	* @return the display name of the portlet
	*/
	public java.lang.String getDisplayName() {
		return _portlet.getDisplayName();
	}

	/**
	* Sets the display name of the portlet.
	*
	* @param displayName the display name of the portlet
	*/
	public void setDisplayName(java.lang.String displayName) {
		_portlet.setDisplayName(displayName);
	}

	/**
	* Returns the name of the portlet class of the portlet.
	*
	* @return the name of the portlet class of the portlet
	*/
	public java.lang.String getPortletClass() {
		return _portlet.getPortletClass();
	}

	/**
	* Sets the name of the portlet class of the portlet.
	*
	* @param portletClass the name of the portlet class of the portlet
	*/
	public void setPortletClass(java.lang.String portletClass) {
		_portlet.setPortletClass(portletClass);
	}

	/**
	* Returns the configuration action class of the portlet.
	*
	* @return the configuration action class of the portlet
	*/
	public java.lang.String getConfigurationActionClass() {
		return _portlet.getConfigurationActionClass();
	}

	/**
	* Sets the configuration action class of the portlet.
	*
	* @param configurationActionClass the configuration action class of the
	portlet
	*/
	public void setConfigurationActionClass(
		java.lang.String configurationActionClass) {
		_portlet.setConfigurationActionClass(configurationActionClass);
	}

	/**
	* Returns the configuration action instance of the portlet.
	*
	* @return the configuration action instance of the portlet
	*/
	public com.liferay.portal.kernel.portlet.ConfigurationAction getConfigurationActionInstance() {
		return _portlet.getConfigurationActionInstance();
	}

	/**
	* Returns the name of the classes that represent indexers associated with
	* the portlet.
	*
	* @return the name of the classes that represent indexers associated with
	the portlet
	*/
	public java.util.List<java.lang.String> getIndexerClasses() {
		return _portlet.getIndexerClasses();
	}

	/**
	* Sets the name of the classes that represent indexers associated with the
	* portlet.
	*
	* @param indexerClasses the name of the classes that represent indexers
	associated with the portlet
	*/
	public void setIndexerClasses(
		java.util.List<java.lang.String> indexerClasses) {
		_portlet.setIndexerClasses(indexerClasses);
	}

	/**
	* Returns the indexer instances of the portlet.
	*
	* @return the indexer instances of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.search.Indexer> getIndexerInstances() {
		return _portlet.getIndexerInstances();
	}

	/**
	* Returns the name of the open search class of the portlet.
	*
	* @return the name of the open search class of the portlet
	*/
	public java.lang.String getOpenSearchClass() {
		return _portlet.getOpenSearchClass();
	}

	/**
	* Sets the name of the open search class of the portlet.
	*
	* @param openSearchClass the name of the open search class of the portlet
	*/
	public void setOpenSearchClass(java.lang.String openSearchClass) {
		_portlet.setOpenSearchClass(openSearchClass);
	}

	/**
	* Returns the indexer instance of the portlet.
	*
	* @return the indexer instance of the portlet
	*/
	public com.liferay.portal.kernel.search.OpenSearch getOpenSearchInstance() {
		return _portlet.getOpenSearchInstance();
	}

	/**
	* Adds a scheduler entry.
	*/
	public void addSchedulerEntry(
		com.liferay.portal.kernel.scheduler.SchedulerEntry schedulerEntry) {
		_portlet.addSchedulerEntry(schedulerEntry);
	}

	/**
	* Returns the scheduler entries of the portlet.
	*
	* @return the scheduler entries of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> getSchedulerEntries() {
		return _portlet.getSchedulerEntries();
	}

	/**
	* Sets the scheduler entries of the portlet.
	*
	* @param schedulerEntries the scheduler entries of the portlet
	*/
	public void setSchedulerEntries(
		java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> schedulerEntries) {
		_portlet.setSchedulerEntries(schedulerEntries);
	}

	/**
	* Returns the name of the portlet URL class of the portlet.
	*
	* @return the name of the portlet URL class of the portlet
	*/
	public java.lang.String getPortletURLClass() {
		return _portlet.getPortletURLClass();
	}

	/**
	* Sets the name of the portlet URL class of the portlet.
	*
	* @param portletURLClass the name of the portlet URL class of the portlet
	*/
	public void setPortletURLClass(java.lang.String portletURLClass) {
		_portlet.setPortletURLClass(portletURLClass);
	}

	/**
	* Returns the name of the friendly URL mapper class of the portlet.
	*
	* @return the name of the friendly URL mapper class of the portlet
	*/
	public java.lang.String getFriendlyURLMapperClass() {
		return _portlet.getFriendlyURLMapperClass();
	}

	/**
	* Sets the name of the friendly URL mapper class of the portlet.
	*
	* @param friendlyURLMapperClass the name of the friendly URL mapper class
	of the portlet
	*/
	public void setFriendlyURLMapperClass(
		java.lang.String friendlyURLMapperClass) {
		_portlet.setFriendlyURLMapperClass(friendlyURLMapperClass);
	}

	/**
	* Returns the friendly URL mapper instance of the portlet.
	*
	* @return the friendly URL mapper instance of the portlet
	*/
	public com.liferay.portal.kernel.portlet.FriendlyURLMapper getFriendlyURLMapperInstance() {
		return _portlet.getFriendlyURLMapperInstance();
	}

	/**
	* Returns the name of the friendly URL mapping of the portlet.
	*
	* @return the name of the friendly URL mapping of the portlet
	*/
	public java.lang.String getFriendlyURLMapping() {
		return _portlet.getFriendlyURLMapping();
	}

	/**
	* Sets the name of the friendly URL mapping of the portlet.
	*
	* @param friendlyURLMapping the name of the friendly URL mapping of the
	portlet
	*/
	public void setFriendlyURLMapping(java.lang.String friendlyURLMapping) {
		_portlet.setFriendlyURLMapping(friendlyURLMapping);
	}

	/**
	* Returns the class loader resource path to the friendly URL routes of the
	* portlet.
	*
	* @return the class loader resource path to the friendly URL routes of the
	portlet
	*/
	public java.lang.String getFriendlyURLRoutes() {
		return _portlet.getFriendlyURLRoutes();
	}

	/**
	* Sets the class loader resource path to the friendly URL routes of the
	* portlet.
	*
	* @param friendlyURLRoutes the class loader resource path to the friendly
	URL routes of the portlet
	*/
	public void setFriendlyURLRoutes(java.lang.String friendlyURLRoutes) {
		_portlet.setFriendlyURLRoutes(friendlyURLRoutes);
	}

	/**
	* Returns the name of the URL encoder class of the portlet.
	*
	* @return the name of the URL encoder class of the portlet
	*/
	public java.lang.String getURLEncoderClass() {
		return _portlet.getURLEncoderClass();
	}

	/**
	* Sets the name of the URL encoder class of the portlet.
	*
	* @param urlEncoderClass the name of the URL encoder class of the portlet
	*/
	public void setURLEncoderClass(java.lang.String urlEncoderClass) {
		_portlet.setURLEncoderClass(urlEncoderClass);
	}

	/**
	* Returns the URL encoder instance of the portlet.
	*
	* @return the URL encoder instance of the portlet
	*/
	public com.liferay.portal.kernel.servlet.URLEncoder getURLEncoderInstance() {
		return _portlet.getURLEncoderInstance();
	}

	/**
	* Returns the name of the portlet data handler class of the portlet.
	*
	* @return the name of the portlet data handler class of the portlet
	*/
	public java.lang.String getPortletDataHandlerClass() {
		return _portlet.getPortletDataHandlerClass();
	}

	/**
	* Sets the name of the portlet data handler class of the portlet.
	*
	* @param portletDataHandlerClass the name of portlet data handler class of
	the portlet
	*/
	public void setPortletDataHandlerClass(
		java.lang.String portletDataHandlerClass) {
		_portlet.setPortletDataHandlerClass(portletDataHandlerClass);
	}

	/**
	* Returns the portlet data handler instance of the portlet.
	*
	* @return the portlet data handler instance of the portlet
	*/
	public com.liferay.portal.kernel.lar.PortletDataHandler getPortletDataHandlerInstance() {
		return _portlet.getPortletDataHandlerInstance();
	}

	/**
	* Returns the name of the portlet layout listener class of the portlet.
	*
	* @return the name of the portlet layout listener class of the portlet
	*/
	public java.lang.String getPortletLayoutListenerClass() {
		return _portlet.getPortletLayoutListenerClass();
	}

	/**
	* Sets the name of the portlet layout listener class of the portlet.
	*
	* @param portletLayoutListenerClass the name of the portlet layout listener
	class of the portlet
	*/
	public void setPortletLayoutListenerClass(
		java.lang.String portletLayoutListenerClass) {
		_portlet.setPortletLayoutListenerClass(portletLayoutListenerClass);
	}

	/**
	* Returns the portlet layout listener instance of the portlet.
	*
	* @return the portlet layout listener instance of the portlet
	*/
	public com.liferay.portal.kernel.portlet.PortletLayoutListener getPortletLayoutListenerInstance() {
		return _portlet.getPortletLayoutListenerInstance();
	}

	/**
	* Returns the name of the poller processor class of the portlet.
	*
	* @return the name of the poller processor class of the portlet
	*/
	public java.lang.String getPollerProcessorClass() {
		return _portlet.getPollerProcessorClass();
	}

	/**
	* Sets the name of the poller processor class of the portlet.
	*
	* @param pollerProcessorClass the name of the poller processor class of the
	portlet
	*/
	public void setPollerProcessorClass(java.lang.String pollerProcessorClass) {
		_portlet.setPollerProcessorClass(pollerProcessorClass);
	}

	/**
	* Returns the poller processor instance of the portlet.
	*
	* @return the poller processor instance of the portlet
	*/
	public com.liferay.portal.kernel.poller.PollerProcessor getPollerProcessorInstance() {
		return _portlet.getPollerProcessorInstance();
	}

	/**
	* Returns the name of the POP message listener class of the portlet.
	*
	* @return the name of the POP message listener class of the portlet
	*/
	public java.lang.String getPopMessageListenerClass() {
		return _portlet.getPopMessageListenerClass();
	}

	/**
	* Sets the name of the POP message listener class of the portlet.
	*
	* @param popMessageListenerClass the name of the POP message listener class
	of the portlet
	*/
	public void setPopMessageListenerClass(
		java.lang.String popMessageListenerClass) {
		_portlet.setPopMessageListenerClass(popMessageListenerClass);
	}

	/**
	* Returns the POP message listener instance of the portlet.
	*
	* @return the POP message listener instance of the portlet
	*/
	public com.liferay.portal.kernel.pop.MessageListener getPopMessageListenerInstance() {
		return _portlet.getPopMessageListenerInstance();
	}

	/**
	* Returns the name of the social activity interpreter class of the portlet.
	*
	* @return the name of the social activity interpreter class of the portlet
	*/
	public java.lang.String getSocialActivityInterpreterClass() {
		return _portlet.getSocialActivityInterpreterClass();
	}

	/**
	* Sets the name of the social activity interpreter class of the portlet.
	*
	* @param socialActivityInterpreterClass the name of the activity
	interpreter class of the portlet
	*/
	public void setSocialActivityInterpreterClass(
		java.lang.String socialActivityInterpreterClass) {
		_portlet.setSocialActivityInterpreterClass(socialActivityInterpreterClass);
	}

	/**
	* Returns the name of the social activity interpreter instance of the
	* portlet.
	*
	* @return the name of the social activity interpreter instance of the
	portlet
	*/
	public com.liferay.portlet.social.model.SocialActivityInterpreter getSocialActivityInterpreterInstance() {
		return _portlet.getSocialActivityInterpreterInstance();
	}

	/**
	* Returns the name of the social request interpreter class of the portlet.
	*
	* @return the name of the social request interpreter class of the portlet
	*/
	public java.lang.String getSocialRequestInterpreterClass() {
		return _portlet.getSocialRequestInterpreterClass();
	}

	/**
	* Sets the name of the social request interpreter class of the portlet.
	*
	* @param socialRequestInterpreterClass the name of the request interpreter
	class of the portlet
	*/
	public void setSocialRequestInterpreterClass(
		java.lang.String socialRequestInterpreterClass) {
		_portlet.setSocialRequestInterpreterClass(socialRequestInterpreterClass);
	}

	/**
	* Returns the name of the social request interpreter instance of the
	* portlet.
	*
	* @return the name of the social request interpreter instance of the
	portlet
	*/
	public com.liferay.portlet.social.model.SocialRequestInterpreter getSocialRequestInterpreterInstance() {
		return _portlet.getSocialRequestInterpreterInstance();
	}

	/**
	* Returns the name of the WebDAV storage token of the portlet.
	*
	* @return the name of the WebDAV storage token of the portlet
	*/
	public java.lang.String getWebDAVStorageToken() {
		return _portlet.getWebDAVStorageToken();
	}

	/**
	* Sets the name of the WebDAV storage token of the portlet.
	*
	* @param webDAVStorageToken the name of the WebDAV storage token of the
	portlet
	*/
	public void setWebDAVStorageToken(java.lang.String webDAVStorageToken) {
		_portlet.setWebDAVStorageToken(webDAVStorageToken);
	}

	/**
	* Returns the name of the WebDAV storage class of the portlet.
	*
	* @return the name of the WebDAV storage class of the portlet
	*/
	public java.lang.String getWebDAVStorageClass() {
		return _portlet.getWebDAVStorageClass();
	}

	/**
	* Sets the name of the WebDAV storage class of the portlet.
	*
	* @param webDAVStorageClass the name of the WebDAV storage class of the
	portlet
	*/
	public void setWebDAVStorageClass(java.lang.String webDAVStorageClass) {
		_portlet.setWebDAVStorageClass(webDAVStorageClass);
	}

	/**
	* Returns the name of the WebDAV storage instance of the portlet.
	*
	* @return the name of the WebDAV storage instance of the portlet
	*/
	public com.liferay.portal.kernel.webdav.WebDAVStorage getWebDAVStorageInstance() {
		return _portlet.getWebDAVStorageInstance();
	}

	/**
	* Returns the name of the XML-RPC method class of the portlet.
	*
	* @return the name of the XML-RPC method class of the portlet
	*/
	public java.lang.String getXmlRpcMethodClass() {
		return _portlet.getXmlRpcMethodClass();
	}

	/**
	* Sets the name of the XML-RPC method class of the portlet.
	*
	* @param xmlRpcMethodClass the name of the XML-RPC method class of the
	portlet
	*/
	public void setXmlRpcMethodClass(java.lang.String xmlRpcMethodClass) {
		_portlet.setXmlRpcMethodClass(xmlRpcMethodClass);
	}

	/**
	* Returns the name of the XML-RPC method instance of the portlet.
	*
	* @return the name of the XML-RPC method instance of the portlet
	*/
	public com.liferay.portal.kernel.xmlrpc.Method getXmlRpcMethodInstance() {
		return _portlet.getXmlRpcMethodInstance();
	}

	/**
	* Returns the name of the category of the Control Panel where the portlet
	* will be shown.
	*
	* @return the name of the category of the Control Panel where the portlet
	will be shown
	*/
	public java.lang.String getControlPanelEntryCategory() {
		return _portlet.getControlPanelEntryCategory();
	}

	/**
	* Set the name of the category of the Control Panel where the portlet will
	* be shown.
	*
	* @param controlPanelEntryCategory the name of the category of the Control
	Panel where the portlet will be shown
	*/
	public void setControlPanelEntryCategory(
		java.lang.String controlPanelEntryCategory) {
		_portlet.setControlPanelEntryCategory(controlPanelEntryCategory);
	}

	/**
	* Returns the relative weight of the portlet with respect to the other
	* portlets in the same category of the Control Panel.
	*
	* @return the relative weight of the portlet with respect to the other
	portlets in the same category of the Control Panel
	*/
	public double getControlPanelEntryWeight() {
		return _portlet.getControlPanelEntryWeight();
	}

	/**
	* Sets the relative weight of the portlet with respect to the other
	* portlets in the same category of the Control Panel.
	*
	* @param controlPanelEntryWeight the relative weight of the portlet with
	respect to the other portlets in the same category of the Control
	Panel
	*/
	public void setControlPanelEntryWeight(double controlPanelEntryWeight) {
		_portlet.setControlPanelEntryWeight(controlPanelEntryWeight);
	}

	/**
	* Returns the name of the class that will control when the portlet will be
	* shown in the Control Panel.
	*
	* @return the name of the class that will control when the portlet will be
	shown in the Control Panel
	*/
	public java.lang.String getControlPanelEntryClass() {
		return _portlet.getControlPanelEntryClass();
	}

	/**
	* Sets the name of the class that will control when the portlet will be
	* shown in the Control Panel.
	*
	* @param controlPanelEntryClass the name of the class that will control
	when the portlet will be shown in the Control Panel
	*/
	public void setControlPanelEntryClass(
		java.lang.String controlPanelEntryClass) {
		_portlet.setControlPanelEntryClass(controlPanelEntryClass);
	}

	/**
	* Returns an instance of the class that will control when the portlet will
	* be shown in the Control Panel.
	*
	* @return the instance of the class that will control when the portlet will
	be shown in the Control Panel
	*/
	public com.liferay.portlet.ControlPanelEntry getControlPanelEntryInstance() {
		return _portlet.getControlPanelEntryInstance();
	}

	/**
	* Returns the names of the classes that represent asset types associated
	* with the portlet.
	*
	* @return the names of the classes that represent asset types associated
	with the portlet
	*/
	public java.util.List<java.lang.String> getAssetRendererFactoryClasses() {
		return _portlet.getAssetRendererFactoryClasses();
	}

	/**
	* Sets the name of the classes that represent asset types associated with
	* the portlet.
	*
	* @param assetRendererFactoryClasses the names of the classes that
	represent asset types associated with the portlet
	*/
	public void setAssetRendererFactoryClasses(
		java.util.List<java.lang.String> assetRendererFactoryClasses) {
		_portlet.setAssetRendererFactoryClasses(assetRendererFactoryClasses);
	}

	/**
	* Returns the asset type instances of the portlet.
	*
	* @return the asset type instances of the portlet
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetRendererFactory> getAssetRendererFactoryInstances() {
		return _portlet.getAssetRendererFactoryInstances();
	}

	/**
	* Returns the names of the classes that represent atom collection adapters
	* associated with the portlet.
	*
	* @return the names of the classes that represent atom collection adapters
	associated with the portlet
	*/
	public java.util.List<java.lang.String> getAtomCollectionAdapterClasses() {
		return _portlet.getAtomCollectionAdapterClasses();
	}

	/**
	* Sets the name of the classes that represent atom collection adapters
	* associated with the portlet.
	*
	* @param atomCollectionAdapterClasses the names of the classes that
	represent atom collection adapters associated with the portlet
	*/
	public void setAtomCollectionAdapterClasses(
		java.util.List<java.lang.String> atomCollectionAdapterClasses) {
		_portlet.setAtomCollectionAdapterClasses(atomCollectionAdapterClasses);
	}

	/**
	* Returns the atom collection adapter instances of the portlet.
	*
	* @return the atom collection adapter instances of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.atom.AtomCollectionAdapter<?>> getAtomCollectionAdapterInstances() {
		return _portlet.getAtomCollectionAdapterInstances();
	}

	/**
	* Returns the names of the classes that represent custom attribute displays
	* associated with the portlet.
	*
	* @return the names of the classes that represent asset types associated
	with the portlet
	*/
	public java.util.List<java.lang.String> getCustomAttributesDisplayClasses() {
		return _portlet.getCustomAttributesDisplayClasses();
	}

	/**
	* Sets the name of the classes that represent custom attribute displays
	* associated with the portlet.
	*
	* @param customAttributesDisplayClasses the names of the classes that
	represent custom attribute displays associated with the portlet
	*/
	public void setCustomAttributesDisplayClasses(
		java.util.List<java.lang.String> customAttributesDisplayClasses) {
		_portlet.setCustomAttributesDisplayClasses(customAttributesDisplayClasses);
	}

	/**
	* Returns the custom attribute display instances of the portlet.
	*
	* @return the custom attribute display instances of the portlet
	*/
	public java.util.List<com.liferay.portlet.expando.model.CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		return _portlet.getCustomAttributesDisplayInstances();
	}

	/**
	* Returns the name of the permission propagator class of the portlet.
	*
	* @return the name of the permission propagator class of the portlet
	*/
	public java.lang.String getPermissionPropagatorClass() {
		return _portlet.getPermissionPropagatorClass();
	}

	/**
	* Sets the name of the permission propagator class of the portlet.
	*
	* @param pollerProcessorClass the name of the permission propagator class
	of the portlet
	*/
	public void setPermissionPropagatorClass(
		java.lang.String permissionPropagatorClass) {
		_portlet.setPermissionPropagatorClass(permissionPropagatorClass);
	}

	/**
	* Returns the permission propagator instance of the portlet.
	*
	* @return the permission propagator instance of the portlet
	*/
	public com.liferay.portal.security.permission.PermissionPropagator getPermissionPropagatorInstance() {
		return _portlet.getPermissionPropagatorInstance();
	}

	/**
	* Returns the names of the classes that represent workflow handlers
	* associated with the portlet.
	*
	* @return the names of the classes that represent workflow handlers
	associated with the portlet
	*/
	public java.util.List<java.lang.String> getWorkflowHandlerClasses() {
		return _portlet.getWorkflowHandlerClasses();
	}

	/**
	* Sets the name of the classes that represent workflow handlers associated
	* to the portlet.
	*
	* @param workflowHandlerClasses the names of the classes that represent
	workflow handlers associated with the portlet
	*/
	public void setWorkflowHandlerClasses(
		java.util.List<java.lang.String> workflowHandlerClasses) {
		_portlet.setWorkflowHandlerClasses(workflowHandlerClasses);
	}

	/**
	* Returns the workflow handler instances of the portlet.
	*
	* @return the workflow handler instances of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.workflow.WorkflowHandler> getWorkflowHandlerInstances() {
		return _portlet.getWorkflowHandlerInstances();
	}

	/**
	* Returns the default preferences of the portlet.
	*
	* @return the default preferences of the portlet
	*/
	public java.lang.String getDefaultPreferences() {
		return _portlet.getDefaultPreferences();
	}

	/**
	* Sets the default preferences of the portlet.
	*
	* @param defaultPreferences the default preferences of the portlet
	*/
	public void setDefaultPreferences(java.lang.String defaultPreferences) {
		_portlet.setDefaultPreferences(defaultPreferences);
	}

	/**
	* Returns the name of the preferences validator class of the portlet.
	*
	* @return the name of the preferences validator class of the portlet
	*/
	public java.lang.String getPreferencesValidator() {
		return _portlet.getPreferencesValidator();
	}

	/**
	* Sets the name of the preferences validator class of the portlet.
	*
	* @param preferencesValidator the name of the preferences validator class
	of the portlet
	*/
	public void setPreferencesValidator(java.lang.String preferencesValidator) {
		_portlet.setPreferencesValidator(preferencesValidator);
	}

	/**
	* Returns <code>true</code> if preferences are shared across the entire
	* company.
	*
	* @return <code>true</code> if preferences are shared across the entire
	company
	*/
	public boolean getPreferencesCompanyWide() {
		return _portlet.getPreferencesCompanyWide();
	}

	/**
	* Returns <code>true</code> if preferences are shared across the entire
	* company.
	*
	* @return <code>true</code> if preferences are shared across the entire
	company
	*/
	public boolean isPreferencesCompanyWide() {
		return _portlet.isPreferencesCompanyWide();
	}

	/**
	* Set to <code>true</code> if preferences are shared across the entire
	* company.
	*
	* @param preferencesCompanyWide boolean value for whether preferences are
	shared across the entire company
	*/
	public void setPreferencesCompanyWide(boolean preferencesCompanyWide) {
		_portlet.setPreferencesCompanyWide(preferencesCompanyWide);
	}

	/**
	* Returns <code>true</code> if preferences are unique per layout.
	*
	* @return <code>true</code> if preferences are unique per layout
	*/
	public boolean getPreferencesUniquePerLayout() {
		return _portlet.getPreferencesUniquePerLayout();
	}

	/**
	* Returns <code>true</code> if preferences are unique per layout.
	*
	* @return <code>true</code> if preferences are unique per layout
	*/
	public boolean isPreferencesUniquePerLayout() {
		return _portlet.isPreferencesUniquePerLayout();
	}

	/**
	* Set to <code>true</code> if preferences are unique per layout.
	*
	* @param preferencesUniquePerLayout boolean value for whether preferences
	are unique per layout
	*/
	public void setPreferencesUniquePerLayout(
		boolean preferencesUniquePerLayout) {
		_portlet.setPreferencesUniquePerLayout(preferencesUniquePerLayout);
	}

	/**
	* Returns <code>true</code> if preferences are owned by the group when the
	* portlet is shown in a group layout. Returns <code>false</code> if
	* preferences are owned by the user at all times.
	*
	* @return <code>true</code> if preferences are owned by the group when the
	portlet is shown in a group layout; <code>false</code> if
	preferences are owned by the user at all times.
	*/
	public boolean getPreferencesOwnedByGroup() {
		return _portlet.getPreferencesOwnedByGroup();
	}

	/**
	* Returns <code>true</code> if preferences are owned by the group when the
	* portlet is shown in a group layout. Returns <code>false</code> if
	* preferences are owned by the user at all times.
	*
	* @return <code>true</code> if preferences are owned by the group when the
	portlet is shown in a group layout; <code>false</code> if
	preferences are owned by the user at all times.
	*/
	public boolean isPreferencesOwnedByGroup() {
		return _portlet.isPreferencesOwnedByGroup();
	}

	/**
	* Set to <code>true</code> if preferences are owned by the group when the
	* portlet is shown in a group layout. Set to <code>false</code> if
	* preferences are owned by the user at all times.
	*
	* @param preferencesOwnedByGroup boolean value for whether preferences are
	owned by the group when the portlet is shown in a group layout or
	preferences are owned by the user at all times
	*/
	public void setPreferencesOwnedByGroup(boolean preferencesOwnedByGroup) {
		_portlet.setPreferencesOwnedByGroup(preferencesOwnedByGroup);
	}

	/**
	* Returns <code>true</code> if the portlet uses the default template.
	*
	* @return <code>true</code> if the portlet uses the default template
	*/
	public boolean getUseDefaultTemplate() {
		return _portlet.getUseDefaultTemplate();
	}

	/**
	* Returns <code>true</code> if the portlet uses the default template.
	*
	* @return <code>true</code> if the portlet uses the default template
	*/
	public boolean isUseDefaultTemplate() {
		return _portlet.isUseDefaultTemplate();
	}

	/**
	* Set to <code>true</code> if the portlet uses the default template.
	*
	* @param useDefaultTemplate boolean value for whether the portlet uses the
	default template
	*/
	public void setUseDefaultTemplate(boolean useDefaultTemplate) {
		_portlet.setUseDefaultTemplate(useDefaultTemplate);
	}

	/**
	* Returns <code>true</code> if users are shown that they do not have access
	* to the portlet.
	*
	* @return <code>true</code> if users are shown that they do not have access
	to the portlet
	*/
	public boolean getShowPortletAccessDenied() {
		return _portlet.getShowPortletAccessDenied();
	}

	/**
	* Returns <code>true</code> if users are shown that they do not have access
	* to the portlet.
	*
	* @return <code>true</code> if users are shown that they do not have access
	to the portlet
	*/
	public boolean isShowPortletAccessDenied() {
		return _portlet.isShowPortletAccessDenied();
	}

	/**
	* Set to <code>true</code> if users are shown that they do not have access
	* to the portlet.
	*
	* @param showPortletAccessDenied boolean value for whether users are shown
	that they do not have access to the portlet
	*/
	public void setShowPortletAccessDenied(boolean showPortletAccessDenied) {
		_portlet.setShowPortletAccessDenied(showPortletAccessDenied);
	}

	/**
	* Returns <code>true</code> if users are shown that the portlet is
	* inactive.
	*
	* @return <code>true</code> if users are shown that the portlet is inactive
	*/
	public boolean getShowPortletInactive() {
		return _portlet.getShowPortletInactive();
	}

	/**
	* Returns <code>true</code> if users are shown that the portlet is
	* inactive.
	*
	* @return <code>true</code> if users are shown that the portlet is inactive
	*/
	public boolean isShowPortletInactive() {
		return _portlet.isShowPortletInactive();
	}

	/**
	* Set to <code>true</code> if users are shown that the portlet is inactive.
	*
	* @param showPortletInactive boolean value for whether users are shown that
	the portlet is inactive
	*/
	public void setShowPortletInactive(boolean showPortletInactive) {
		_portlet.setShowPortletInactive(showPortletInactive);
	}

	/**
	* Returns <code>true</code> if an action URL for this portlet should cause
	* an auto redirect.
	*
	* @return <code>true</code> if an action URL for this portlet should cause
	an auto redirect
	*/
	public boolean getActionURLRedirect() {
		return _portlet.getActionURLRedirect();
	}

	/**
	* Returns <code>true</code> if an action URL for this portlet should cause
	* an auto redirect.
	*
	* @return <code>true</code> if an action URL for this portlet should cause
	an auto redirect
	*/
	public boolean isActionURLRedirect() {
		return _portlet.isActionURLRedirect();
	}

	/**
	* Set to <code>true</code> if an action URL for this portlet should cause
	* an auto redirect.
	*
	* @param actionURLRedirect boolean value for whether an action URL for this
	portlet should cause an auto redirect
	*/
	public void setActionURLRedirect(boolean actionURLRedirect) {
		_portlet.setActionURLRedirect(actionURLRedirect);
	}

	/**
	* Returns <code>true</code> if the portlet restores to the current view
	* from the maximized state.
	*
	* @return <code>true</code> if the portlet restores to the current view
	from the maximized state
	*/
	public boolean getRestoreCurrentView() {
		return _portlet.getRestoreCurrentView();
	}

	/**
	* Returns <code>true</code> if the portlet restores to the current view
	* from the maximized state.
	*
	* @return <code>true</code> if the portlet restores to the current view
	from the maximized state
	*/
	public boolean isRestoreCurrentView() {
		return _portlet.isRestoreCurrentView();
	}

	/**
	* Set to <code>true</code> if the portlet restores to the current view from
	* the maximized state.
	*
	* @param restoreCurrentView boolean value for whether the portlet restores
	to the current view from the maximized state
	*/
	public void setRestoreCurrentView(boolean restoreCurrentView) {
		_portlet.setRestoreCurrentView(restoreCurrentView);
	}

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the edit mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the edit mode
	*/
	public boolean getMaximizeEdit() {
		return _portlet.getMaximizeEdit();
	}

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the edit mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the edit mode
	*/
	public boolean isMaximizeEdit() {
		return _portlet.isMaximizeEdit();
	}

	/**
	* Set to <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the edit mode.
	*
	* @param maximizeEdit boolean value for whether the portlet goes into the
	maximized state when the user goes into the edit mode
	*/
	public void setMaximizeEdit(boolean maximizeEdit) {
		_portlet.setMaximizeEdit(maximizeEdit);
	}

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the help mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the help mode
	*/
	public boolean getMaximizeHelp() {
		return _portlet.getMaximizeHelp();
	}

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the help mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the help mode
	*/
	public boolean isMaximizeHelp() {
		return _portlet.isMaximizeHelp();
	}

	/**
	* Set to <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the help mode.
	*
	* @param maximizeHelp boolean value for whether the portlet goes into the
	maximized state when the user goes into the help mode
	*/
	public void setMaximizeHelp(boolean maximizeHelp) {
		_portlet.setMaximizeHelp(maximizeHelp);
	}

	/**
	* Returns <code>true</code> if the portlet goes into the pop up state when
	* the user goes into the print mode.
	*
	* @return <code>true</code> if the portlet goes into the pop up state when
	the user goes into the print mode
	*/
	public boolean getPopUpPrint() {
		return _portlet.getPopUpPrint();
	}

	/**
	* Returns <code>true</code> if the portlet goes into the pop up state when
	* the user goes into the print mode.
	*
	* @return <code>true</code> if the portlet goes into the pop up state when
	the user goes into the print mode
	*/
	public boolean isPopUpPrint() {
		return _portlet.isPopUpPrint();
	}

	/**
	* Set to <code>true</code> if the portlet goes into the pop up state when
	* the user goes into the print mode.
	*
	* @param popUpPrint boolean value for whether the portlet goes into the pop
	up state when the user goes into the print mode
	*/
	public void setPopUpPrint(boolean popUpPrint) {
		_portlet.setPopUpPrint(popUpPrint);
	}

	/**
	* Returns <code>true</code> to allow the portlet to be cached within the
	* layout.
	*
	* @return <code>true</code> if the portlet can be cached within the layout
	*/
	public boolean getLayoutCacheable() {
		return _portlet.getLayoutCacheable();
	}

	/**
	* Returns <code>true</code> to allow the portlet to be cached within the
	* layout.
	*
	* @return <code>true</code> if the portlet can be cached within the layout
	*/
	public boolean isLayoutCacheable() {
		return _portlet.isLayoutCacheable();
	}

	/**
	* Set to <code>true</code> to allow the portlet to be cached within the
	* layout.
	*
	* @param layoutCacheable boolean value for whether the portlet can be
	cached within the layout
	*/
	public void setLayoutCacheable(boolean layoutCacheable) {
		_portlet.setLayoutCacheable(layoutCacheable);
	}

	/**
	* Returns <code>true</code> if the portlet can be added multiple times to a
	* layout.
	*
	* @return <code>true</code> if the portlet can be added multiple times to a
	layout
	*/
	public boolean getInstanceable() {
		return _portlet.getInstanceable();
	}

	/**
	* Returns <code>true</code> if the portlet can be added multiple times to a
	* layout.
	*
	* @return <code>true</code> if the portlet can be added multiple times to a
	layout
	*/
	public boolean isInstanceable() {
		return _portlet.isInstanceable();
	}

	/**
	* Set to <code>true</code> if the portlet can be added multiple times to a
	* layout.
	*
	* @param instanceable boolean value for whether the portlet can be added
	multiple times to a layout
	*/
	public void setInstanceable(boolean instanceable) {
		_portlet.setInstanceable(instanceable);
	}

	/**
	* Returns <code>true</code> if the portlet supports remoting.
	*
	* @return <code>true</code> if the portlet supports remoting
	*/
	public boolean getRemoteable() {
		return _portlet.getRemoteable();
	}

	/**
	* Returns <code>true</code> if the portlet supports remoting.
	*
	* @return <code>true</code> if the portlet supports remoting
	*/
	public boolean isRemoteable() {
		return _portlet.isRemoteable();
	}

	/**
	* Set to <code>true</code> if the portlet supports remoting
	*
	* @param remoteable boolean value for whether or not the the portlet
	supports remoting
	*/
	public void setRemoteable(boolean remoteable) {
		_portlet.setRemoteable(remoteable);
	}

	/**
	* Returns <code>true</code> if the portlet supports scoping of data.
	*
	* @return <code>true</code> if the portlet supports scoping of data
	*/
	public boolean getScopeable() {
		return _portlet.getScopeable();
	}

	/**
	* Returns <code>true</code> if the portlet supports scoping of data.
	*
	* @return <code>true</code> if the portlet supports scoping of data
	*/
	public boolean isScopeable() {
		return _portlet.isScopeable();
	}

	/**
	* Set to <code>true</code> if the portlet supports scoping of data.
	*
	* @param scopeable boolean value for whether or not the the portlet
	supports scoping of data
	*/
	public void setScopeable(boolean scopeable) {
		_portlet.setScopeable(scopeable);
	}

	/**
	* Returns the user principal strategy of the portlet.
	*
	* @return the user principal strategy of the portlet
	*/
	public java.lang.String getUserPrincipalStrategy() {
		return _portlet.getUserPrincipalStrategy();
	}

	/**
	* Sets the user principal strategy of the portlet.
	*
	* @param userPrincipalStrategy the user principal strategy of the portlet
	*/
	public void setUserPrincipalStrategy(java.lang.String userPrincipalStrategy) {
		_portlet.setUserPrincipalStrategy(userPrincipalStrategy);
	}

	/**
	* Returns <code>true</code> if the portlet does not share request
	* attributes with the portal or portlets from another WAR.
	*
	* @return <code>true</code> if the portlet does not share request
	attributes with the portal or portlets from another WAR
	*/
	public boolean getPrivateRequestAttributes() {
		return _portlet.getPrivateRequestAttributes();
	}

	/**
	* Returns <code>true</code> if the portlet does not share request
	* attributes with the portal or portlets from another WAR.
	*
	* @return <code>true</code> if the portlet does not share request
	attributes with the portal or portlets from another WAR
	*/
	public boolean isPrivateRequestAttributes() {
		return _portlet.isPrivateRequestAttributes();
	}

	/**
	* Set to <code>true</code> if the portlet does not share request attributes
	* with the portal or portlets from another WAR.
	*
	* @param privateRequestAttributes boolean value for whether the portlet
	shares request attributes with the portal or portlets from another
	WAR
	*/
	public void setPrivateRequestAttributes(boolean privateRequestAttributes) {
		_portlet.setPrivateRequestAttributes(privateRequestAttributes);
	}

	/**
	* Returns <code>true</code> if the portlet does not share session
	* attributes with the portal.
	*
	* @return <code>true</code> if the portlet does not share session
	attributes with the portal
	*/
	public boolean getPrivateSessionAttributes() {
		return _portlet.getPrivateSessionAttributes();
	}

	/**
	* Returns <code>true</code> if the portlet does not share session
	* attributes with the portal.
	*
	* @return <code>true</code> if the portlet does not share session
	attributes with the portal
	*/
	public boolean isPrivateSessionAttributes() {
		return _portlet.isPrivateSessionAttributes();
	}

	/**
	* Set to <code>true</code> if the portlet does not share session attributes
	* with the portal.
	*
	* @param privateSessionAttributes boolean value for whether the portlet
	shares session attributes with the portal
	*/
	public void setPrivateSessionAttributes(boolean privateSessionAttributes) {
		_portlet.setPrivateSessionAttributes(privateSessionAttributes);
	}

	/**
	* Returns the names of the parameters that will be automatically propagated
	* through the portlet.
	*
	* @return the names of the parameters that will be automatically propagated
	through the portlet
	*/
	public java.util.Set<java.lang.String> getAutopropagatedParameters() {
		return _portlet.getAutopropagatedParameters();
	}

	/**
	* Sets the names of the parameters that will be automatically propagated
	* through the portlet.
	*
	* @param autopropagatedParameters the names of the parameters that will be
	automatically propagated through the portlet
	*/
	public void setAutopropagatedParameters(
		java.util.Set<java.lang.String> autopropagatedParameters) {
		_portlet.setAutopropagatedParameters(autopropagatedParameters);
	}

	/**
	* Returns the action timeout of the portlet.
	*
	* @return the action timeout of the portlet
	*/
	public int getActionTimeout() {
		return _portlet.getActionTimeout();
	}

	/**
	* Sets the action timeout of the portlet.
	*
	* @param actionTimeout the action timeout of the portlet
	*/
	public void setActionTimeout(int actionTimeout) {
		_portlet.setActionTimeout(actionTimeout);
	}

	/**
	* Returns the render timeout of the portlet.
	*
	* @return the render timeout of the portlet
	*/
	public int getRenderTimeout() {
		return _portlet.getRenderTimeout();
	}

	/**
	* Sets the render timeout of the portlet.
	*
	* @param renderTimeout the render timeout of the portlet
	*/
	public void setRenderTimeout(int renderTimeout) {
		_portlet.setRenderTimeout(renderTimeout);
	}

	/**
	* Returns the render weight of the portlet.
	*
	* @return the render weight of the portlet
	*/
	public int getRenderWeight() {
		return _portlet.getRenderWeight();
	}

	/**
	* Sets the render weight of the portlet.
	*
	* @param renderWeight int value for the render weight of the portlet
	*/
	public void setRenderWeight(int renderWeight) {
		_portlet.setRenderWeight(renderWeight);
	}

	/**
	* Returns <code>true</code> if the portlet can be displayed via Ajax.
	*
	* @return <code>true</code> if the portlet can be displayed via Ajax
	*/
	public boolean getAjaxable() {
		return _portlet.getAjaxable();
	}

	/**
	* Returns <code>true</code> if the portlet can be displayed via Ajax.
	*
	* @return <code>true</code> if the portlet can be displayed via Ajax
	*/
	public boolean isAjaxable() {
		return _portlet.isAjaxable();
	}

	/**
	* Set to <code>true</code> if the portlet can be displayed via Ajax.
	*
	* @param ajaxable boolean value for whether the portlet can be displayed
	via Ajax
	*/
	public void setAjaxable(boolean ajaxable) {
		_portlet.setAjaxable(ajaxable);
	}

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* header relative to the portal's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	header relative to the portal's context path
	*/
	public java.util.List<java.lang.String> getHeaderPortalCss() {
		return _portlet.getHeaderPortalCss();
	}

	/**
	* Sets a list of CSS files that will be referenced from the page's header
	* relative to the portal's context path.
	*
	* @param headerPortalCss a list of CSS files that will be referenced from
	the page's header relative to the portal's context path
	*/
	public void setHeaderPortalCss(
		java.util.List<java.lang.String> headerPortalCss) {
		_portlet.setHeaderPortalCss(headerPortalCss);
	}

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* header relative to the portlet's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	header relative to the portlet's context path
	*/
	public java.util.List<java.lang.String> getHeaderPortletCss() {
		return _portlet.getHeaderPortletCss();
	}

	/**
	* Sets a list of CSS files that will be referenced from the page's header
	* relative to the portlet's context path.
	*
	* @param headerPortletCss a list of CSS files that will be referenced from
	the page's header relative to the portlet's context path
	*/
	public void setHeaderPortletCss(
		java.util.List<java.lang.String> headerPortletCss) {
		_portlet.setHeaderPortletCss(headerPortletCss);
	}

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's header relative to the portal's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's header relative to the portal's context path
	*/
	public java.util.List<java.lang.String> getHeaderPortalJavaScript() {
		return _portlet.getHeaderPortalJavaScript();
	}

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* header relative to the portal's context path.
	*
	* @param headerPortalJavaScript a list of JavaScript files that will be
	referenced from the page's header relative to the portal's context
	path
	*/
	public void setHeaderPortalJavaScript(
		java.util.List<java.lang.String> headerPortalJavaScript) {
		_portlet.setHeaderPortalJavaScript(headerPortalJavaScript);
	}

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's header relative to the portlet's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's header relative to the portlet's context path
	*/
	public java.util.List<java.lang.String> getHeaderPortletJavaScript() {
		return _portlet.getHeaderPortletJavaScript();
	}

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* header relative to the portlet's context path.
	*
	* @param headerPortletJavaScript a list of JavaScript files that will be
	referenced from the page's header relative to the portlet's
	context path
	*/
	public void setHeaderPortletJavaScript(
		java.util.List<java.lang.String> headerPortletJavaScript) {
		_portlet.setHeaderPortletJavaScript(headerPortletJavaScript);
	}

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* footer relative to the portal's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	footer relative to the portal's context path
	*/
	public java.util.List<java.lang.String> getFooterPortalCss() {
		return _portlet.getFooterPortalCss();
	}

	/**
	* Sets a list of CSS files that will be referenced from the page's footer
	* relative to the portal's context path.
	*
	* @param footerPortalCss a list of CSS files that will be referenced from
	the page's footer relative to the portal's context path
	*/
	public void setFooterPortalCss(
		java.util.List<java.lang.String> footerPortalCss) {
		_portlet.setFooterPortalCss(footerPortalCss);
	}

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* footer relative to the portlet's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	footer relative to the portlet's context path
	*/
	public java.util.List<java.lang.String> getFooterPortletCss() {
		return _portlet.getFooterPortletCss();
	}

	/**
	* Sets a list of CSS files that will be referenced from the page's footer
	* relative to the portlet's context path.
	*
	* @param footerPortletCss a list of CSS files that will be referenced from
	the page's footer relative to the portlet's context path
	*/
	public void setFooterPortletCss(
		java.util.List<java.lang.String> footerPortletCss) {
		_portlet.setFooterPortletCss(footerPortletCss);
	}

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's footer relative to the portal's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's footer relative to the portal's context path
	*/
	public java.util.List<java.lang.String> getFooterPortalJavaScript() {
		return _portlet.getFooterPortalJavaScript();
	}

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* footer relative to the portal's context path.
	*
	* @param footerPortalJavaScript a list of JavaScript files that will be
	referenced from the page's footer relative to the portal's context
	path
	*/
	public void setFooterPortalJavaScript(
		java.util.List<java.lang.String> footerPortalJavaScript) {
		_portlet.setFooterPortalJavaScript(footerPortalJavaScript);
	}

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's footer relative to the portlet's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's footer relative to the portlet's context path
	*/
	public java.util.List<java.lang.String> getFooterPortletJavaScript() {
		return _portlet.getFooterPortletJavaScript();
	}

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* footer relative to the portlet's context path.
	*
	* @param footerPortletJavaScript a list of JavaScript files that will be
	referenced from the page's footer relative to the portlet's
	context path
	*/
	public void setFooterPortletJavaScript(
		java.util.List<java.lang.String> footerPortletJavaScript) {
		_portlet.setFooterPortletJavaScript(footerPortletJavaScript);
	}

	/**
	* Returns the name of the CSS class that will be injected in the DIV that
	* wraps this portlet.
	*
	* @return the name of the CSS class that will be injected in the DIV that
	wraps this portlet
	*/
	public java.lang.String getCssClassWrapper() {
		return _portlet.getCssClassWrapper();
	}

	/**
	* Sets the name of the CSS class that will be injected in the DIV that
	* wraps this portlet.
	*
	* @param cssClassWrapper the name of the CSS class that will be injected in
	the DIV that wraps this portlet
	*/
	public void setCssClassWrapper(java.lang.String cssClassWrapper) {
		_portlet.setCssClassWrapper(cssClassWrapper);
	}

	/**
	* Returns the Facebook integration method of the portlet.
	*
	* @return the Facebook integration method of the portlet
	*/
	public java.lang.String getFacebookIntegration() {
		return _portlet.getFacebookIntegration();
	}

	/**
	* Sets the Facebook integration method of the portlet.
	*
	* @param facebookIntegration the Facebook integration method of the portlet
	*/
	public void setFacebookIntegration(java.lang.String facebookIntegration) {
		_portlet.setFacebookIntegration(facebookIntegration);
	}

	/**
	* Returns <code>true</code> if default resources for the portlet are added
	* to a page.
	*
	* @return <code>true</code> if default resources for the portlet are added
	to a page
	*/
	public boolean getAddDefaultResource() {
		return _portlet.getAddDefaultResource();
	}

	/**
	* Returns <code>true</code> if default resources for the portlet are added
	* to a page.
	*
	* @return <code>true</code> if default resources for the portlet are added
	to a page
	*/
	public boolean isAddDefaultResource() {
		return _portlet.isAddDefaultResource();
	}

	/**
	* Set to <code>true</code> if default resources for the portlet are added
	* to a page.
	*
	* @param addDefaultResource boolean value for whether or not default
	resources for the portlet are added to a page
	*/
	public void setAddDefaultResource(boolean addDefaultResource) {
		_portlet.setAddDefaultResource(addDefaultResource);
	}

	/**
	* Returns an array of required roles of the portlet.
	*
	* @return an array of required roles of the portlet
	*/
	public java.lang.String[] getRolesArray() {
		return _portlet.getRolesArray();
	}

	/**
	* Sets an array of required roles of the portlet.
	*
	* @param rolesArray an array of required roles of the portlet
	*/
	public void setRolesArray(java.lang.String[] rolesArray) {
		_portlet.setRolesArray(rolesArray);
	}

	/**
	* Returns the unlinked roles of the portlet.
	*
	* @return unlinked roles of the portlet
	*/
	public java.util.Set<java.lang.String> getUnlinkedRoles() {
		return _portlet.getUnlinkedRoles();
	}

	/**
	* Sets the unlinked roles of the portlet.
	*
	* @param unlinkedRoles the unlinked roles of the portlet
	*/
	public void setUnlinkedRoles(java.util.Set<java.lang.String> unlinkedRoles) {
		_portlet.setUnlinkedRoles(unlinkedRoles);
	}

	/**
	* Returns the role mappers of the portlet.
	*
	* @return role mappers of the portlet
	*/
	public java.util.Map<java.lang.String, java.lang.String> getRoleMappers() {
		return _portlet.getRoleMappers();
	}

	/**
	* Sets the role mappers of the portlet.
	*
	* @param roleMappers the role mappers of the portlet
	*/
	public void setRoleMappers(
		java.util.Map<java.lang.String, java.lang.String> roleMappers) {
		_portlet.setRoleMappers(roleMappers);
	}

	/**
	* Link the role names set in portlet.xml with the Liferay roles set in
	* liferay-portlet.xml.
	*/
	public void linkRoles() {
		_portlet.linkRoles();
	}

	/**
	* Returns <code>true</code> if the portlet has a role with the specified
	* name.
	*
	* @return <code>true</code> if the portlet has a role with the specified
	name
	*/
	public boolean hasRoleWithName(java.lang.String roleName) {
		return _portlet.hasRoleWithName(roleName);
	}

	/**
	* Returns <code>true</code> if the user has the permission to add the
	* portlet to a layout.
	*
	* @return <code>true</code> if the user has the permission to add the
	portlet to a layout
	*/
	public boolean hasAddPortletPermission(long userId) {
		return _portlet.hasAddPortletPermission(userId);
	}

	/**
	* Returns <code>true</code> if the portlet is a system portlet that a user
	* cannot manually add to their page.
	*
	* @return <code>true</code> if the portlet is a system portlet that a user
	cannot manually add to their page
	*/
	public boolean getSystem() {
		return _portlet.getSystem();
	}

	/**
	* Returns <code>true</code> if the portlet is a system portlet that a user
	* cannot manually add to their page.
	*
	* @return <code>true</code> if the portlet is a system portlet that a user
	cannot manually add to their page
	*/
	public boolean isSystem() {
		return _portlet.isSystem();
	}

	/**
	* Set to <code>true</code> if the portlet is a system portlet that a user
	* cannot manually add to their page.
	*
	* @param system boolean value for whether the portlet is a system portlet
	that a user cannot manually add to their page
	*/
	public void setSystem(boolean system) {
		_portlet.setSystem(system);
	}

	/**
	* Returns <code>true</code> to include the portlet and make it available to
	* be made active.
	*
	* @return <code>true</code> to include the portlet and make it available to
	be made active
	*/
	public boolean getInclude() {
		return _portlet.getInclude();
	}

	/**
	* Returns <code>true</code> to include the portlet and make it available to
	* be made active.
	*
	* @return <code>true</code> to include the portlet and make it available to
	be made active
	*/
	public boolean isInclude() {
		return _portlet.isInclude();
	}

	/**
	* Set to <code>true</code> to include the portlet and make it available to
	* be made active.
	*
	* @param include boolean value for whether to include the portlet and make
	it available to be made active
	*/
	public void setInclude(boolean include) {
		_portlet.setInclude(include);
	}

	/**
	* Returns <code>true</code> if the portlet is ready to be used.
	*
	* @return <code>true</code> if the portlet is ready to be used
	*/
	public boolean getReady() {
		return _portlet.getReady();
	}

	/**
	* Returns <code>true</code> if the portlet is ready to be used.
	*
	* @return <code>true</code> if the portlet is ready to be used
	*/
	public boolean isReady() {
		return _portlet.isReady();
	}

	/**
	* Set to <code>true</code> if the portlet is ready to be used.
	*
	* @param ready whether the portlet is ready to be used
	*/
	public void setReady(boolean ready) {
		_portlet.setReady(ready);
	}

	/**
	* Returns the init parameters of the portlet.
	*
	* @return init parameters of the portlet
	*/
	public java.util.Map<java.lang.String, java.lang.String> getInitParams() {
		return _portlet.getInitParams();
	}

	/**
	* Sets the init parameters of the portlet.
	*
	* @param initParams the init parameters of the portlet
	*/
	public void setInitParams(
		java.util.Map<java.lang.String, java.lang.String> initParams) {
		_portlet.setInitParams(initParams);
	}

	/**
	* Returns expiration cache of the portlet.
	*
	* @return expiration cache of the portlet
	*/
	public java.lang.Integer getExpCache() {
		return _portlet.getExpCache();
	}

	/**
	* Sets expiration cache of the portlet.
	*
	* @param expCache expiration cache of the portlet
	*/
	public void setExpCache(java.lang.Integer expCache) {
		_portlet.setExpCache(expCache);
	}

	/**
	* Returns the portlet modes of the portlet.
	*
	* @return portlet modes of the portlet
	*/
	public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getPortletModes() {
		return _portlet.getPortletModes();
	}

	/**
	* Sets the portlet modes of the portlet.
	*
	* @param portletModes the portlet modes of the portlet
	*/
	public void setPortletModes(
		java.util.Map<java.lang.String, java.util.Set<java.lang.String>> portletModes) {
		_portlet.setPortletModes(portletModes);
	}

	/**
	* Returns <code>true</code> if the portlet supports the specified mime type
	* and portlet mode.
	*
	* @return <code>true</code> if the portlet supports the specified mime type
	and portlet mode
	*/
	public boolean hasPortletMode(java.lang.String mimeType,
		javax.portlet.PortletMode portletMode) {
		return _portlet.hasPortletMode(mimeType, portletMode);
	}

	/**
	* Returns a list of all portlet modes supported by the portlet.
	*
	* @return a list of all portlet modes supported by the portlet
	*/
	public java.util.Set<java.lang.String> getAllPortletModes() {
		return _portlet.getAllPortletModes();
	}

	/**
	* Returns <code>true</code> if the portlet supports more than one mime
	* type.
	*
	* @return <code>true</code> if the portlet supports more than one mime type
	*/
	public boolean hasMultipleMimeTypes() {
		return _portlet.hasMultipleMimeTypes();
	}

	/**
	* Returns the window states of the portlet.
	*
	* @return window states of the portlet
	*/
	public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getWindowStates() {
		return _portlet.getWindowStates();
	}

	/**
	* Sets the window states of the portlet.
	*
	* @param windowStates the window states of the portlet
	*/
	public void setWindowStates(
		java.util.Map<java.lang.String, java.util.Set<java.lang.String>> windowStates) {
		_portlet.setWindowStates(windowStates);
	}

	/**
	* Returns <code>true</code> if the portlet supports the specified mime type
	* and window state.
	*
	* @return <code>true</code> if the portlet supports the specified mime type
	and window state
	*/
	public boolean hasWindowState(java.lang.String mimeType,
		javax.portlet.WindowState windowState) {
		return _portlet.hasWindowState(mimeType, windowState);
	}

	/**
	* Returns a list of all window states supported by the portlet.
	*
	* @return a list of all window states supported by the portlet
	*/
	public java.util.Set<java.lang.String> getAllWindowStates() {
		return _portlet.getAllWindowStates();
	}

	/**
	* Returns the supported locales of the portlet.
	*
	* @return supported locales of the portlet
	*/
	public java.util.Set<java.lang.String> getSupportedLocales() {
		return _portlet.getSupportedLocales();
	}

	/**
	* Sets the supported locales of the portlet.
	*
	* @param supportedLocales the supported locales of the portlet
	*/
	public void setSupportedLocales(
		java.util.Set<java.lang.String> supportedLocales) {
		_portlet.setSupportedLocales(supportedLocales);
	}

	/**
	* Returns the resource bundle of the portlet.
	*
	* @return resource bundle of the portlet
	*/
	public java.lang.String getResourceBundle() {
		return _portlet.getResourceBundle();
	}

	/**
	* Sets the resource bundle of the portlet.
	*
	* @param resourceBundle the resource bundle of the portlet
	*/
	public void setResourceBundle(java.lang.String resourceBundle) {
		_portlet.setResourceBundle(resourceBundle);
	}

	/**
	* Returns the portlet info of the portlet.
	*
	* @return portlet info of the portlet
	*/
	public com.liferay.portal.model.PortletInfo getPortletInfo() {
		return _portlet.getPortletInfo();
	}

	/**
	* Sets the portlet info of the portlet.
	*
	* @param portletInfo the portlet info of the portlet
	*/
	public void setPortletInfo(com.liferay.portal.model.PortletInfo portletInfo) {
		_portlet.setPortletInfo(portletInfo);
	}

	/**
	* Returns the filters of the portlet.
	*
	* @return filters of the portlet
	*/
	public java.util.Map<java.lang.String, com.liferay.portal.model.PortletFilter> getPortletFilters() {
		return _portlet.getPortletFilters();
	}

	/**
	* Sets the filters of the portlet.
	*
	* @param portletFilters the filters of the portlet
	*/
	public void setPortletFilters(
		java.util.Map<java.lang.String, com.liferay.portal.model.PortletFilter> portletFilters) {
		_portlet.setPortletFilters(portletFilters);
	}

	/**
	* Adds a supported processing event.
	*/
	public void addProcessingEvent(
		com.liferay.portal.kernel.xml.QName processingEvent) {
		_portlet.addProcessingEvent(processingEvent);
	}

	/**
	* Returns the supported processing event from a namespace URI and a local
	* part.
	*
	* @return the supported processing event from a namespace URI and a local
	part
	*/
	public com.liferay.portal.kernel.xml.QName getProcessingEvent(
		java.lang.String uri, java.lang.String localPart) {
		return _portlet.getProcessingEvent(uri, localPart);
	}

	/**
	* Returns the supported processing events of the portlet.
	*
	* @return supported processing events of the portlet
	*/
	public java.util.Set<com.liferay.portal.kernel.xml.QName> getProcessingEvents() {
		return _portlet.getProcessingEvents();
	}

	/**
	* Sets the supported processing events of the portlet.
	*
	* @param processingEvents the supported processing events of the portlet
	*/
	public void setProcessingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> processingEvents) {
		_portlet.setProcessingEvents(processingEvents);
	}

	/**
	* Adds a supported publishing event.
	*/
	public void addPublishingEvent(
		com.liferay.portal.kernel.xml.QName publishingEvent) {
		_portlet.addPublishingEvent(publishingEvent);
	}

	/**
	* Returns the supported publishing events of the portlet.
	*
	* @return supported publishing events of the portlet
	*/
	public java.util.Set<com.liferay.portal.kernel.xml.QName> getPublishingEvents() {
		return _portlet.getPublishingEvents();
	}

	/**
	* Sets the supported publishing events of the portlet.
	*
	* @param publishingEvents the supported publishing events of the portlet
	*/
	public void setPublishingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> publishingEvents) {
		_portlet.setPublishingEvents(publishingEvents);
	}

	/**
	* Adds a supported public render parameter.
	*
	* @param publicRenderParameter a supported public render parameter
	*/
	public void addPublicRenderParameter(
		com.liferay.portal.model.PublicRenderParameter publicRenderParameter) {
		_portlet.addPublicRenderParameter(publicRenderParameter);
	}

	/**
	* Returns the supported public render parameter from an identifier.
	*
	* @return the supported public render parameter from an identifier
	*/
	public com.liferay.portal.model.PublicRenderParameter getPublicRenderParameter(
		java.lang.String identifier) {
		return _portlet.getPublicRenderParameter(identifier);
	}

	/**
	* Returns the supported public render parameter from a namespace URI and a
	* local part.
	*
	* @return the supported public render parameter from a namespace URI and a
	local part
	*/
	public com.liferay.portal.model.PublicRenderParameter getPublicRenderParameter(
		java.lang.String uri, java.lang.String localPart) {
		return _portlet.getPublicRenderParameter(uri, localPart);
	}

	/**
	* Returns the supported public render parameters of the portlet.
	*
	* @return the supported public render parameters of the portlet
	*/
	public java.util.Set<com.liferay.portal.model.PublicRenderParameter> getPublicRenderParameters() {
		return _portlet.getPublicRenderParameters();
	}

	/**
	* Sets the supported public render parameters of the portlet.
	*
	* @param publicRenderParameters the supported public render parameters of
	the portlet
	*/
	public void setPublicRenderParameters(
		java.util.Set<com.liferay.portal.model.PublicRenderParameter> publicRenderParameters) {
		_portlet.setPublicRenderParameters(publicRenderParameters);
	}

	/**
	* Returns the servlet context path of the portlet.
	*
	* @return the servlet context path of the portlet
	*/
	public java.lang.String getContextPath() {
		return _portlet.getContextPath();
	}

	/**
	* Returns the path for static resources served by this portlet.
	*
	* @return the path for static resources served by this portlet
	*/
	public java.lang.String getStaticResourcePath() {
		return _portlet.getStaticResourcePath();
	}

	/**
	* Returns this portlet's application.
	*
	* @return this portlet's application
	*/
	public com.liferay.portal.model.PortletApp getPortletApp() {
		return _portlet.getPortletApp();
	}

	/**
	* Sets this portlet's application.
	*
	* @param portletApp this portlet's application
	*/
	public void setPortletApp(com.liferay.portal.model.PortletApp portletApp) {
		_portlet.setPortletApp(portletApp);
	}

	/**
	* Returns <code>true</code> if the portlet is found in a WAR file.
	*
	* @param portletId the cloned instance portlet ID
	* @return a cloned instance of the portlet
	*/
	public com.liferay.portal.model.Portlet getClonedInstance(
		java.lang.String portletId) {
		return _portlet.getClonedInstance(portletId);
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet that is
	* cannot be moved.
	*
	* @return <code>true</code> if the portlet is a static portlet that is
	cannot be moved
	*/
	public boolean getStatic() {
		return _portlet.getStatic();
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet that is
	* cannot be moved.
	*
	* @return <code>true</code> if the portlet is a static portlet that is
	cannot be moved
	*/
	public boolean isStatic() {
		return _portlet.isStatic();
	}

	/**
	* Set to <code>true</code> if the portlet is a static portlet that is
	* cannot be moved.
	*
	* @param staticPortlet boolean value for whether the portlet is a static
	portlet that cannot be moved
	*/
	public void setStatic(boolean staticPortlet) {
		_portlet.setStatic(staticPortlet);
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the start
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the start
	of a list of portlets
	*/
	public boolean getStaticStart() {
		return _portlet.getStaticStart();
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the start
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the start
	of a list of portlets
	*/
	public boolean isStaticStart() {
		return _portlet.isStaticStart();
	}

	/**
	* Set to <code>true</code> if the portlet is a static portlet at the start
	* of a list of portlets.
	*
	* @param staticPortletStart boolean value for whether the portlet is a
	static portlet at the start of a list of portlets
	*/
	public void setStaticStart(boolean staticPortletStart) {
		_portlet.setStaticStart(staticPortletStart);
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the end
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the end
	of a list of portlets
	*/
	public boolean getStaticEnd() {
		return _portlet.getStaticEnd();
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the end
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the end
	of a list of portlets
	*/
	public boolean isStaticEnd() {
		return _portlet.isStaticEnd();
	}

	/**
	* Returns <code>true</code> if the portlet is an undeployed portlet.
	*
	* @return <code>true</code> if the portlet is a placeholder of an
	undeployed portlet
	*/
	public boolean getUndeployedPortlet() {
		return _portlet.getUndeployedPortlet();
	}

	/**
	* Returns <code>true</code> if the portlet is an undeployed portlet.
	*
	* @return <code>true</code> if the portlet is a placeholder of an
	undeployed portlet
	*/
	public boolean isUndeployedPortlet() {
		return _portlet.isUndeployedPortlet();
	}

	/**
	* Set to <code>true</code> if the portlet is an undeployed portlet.
	*
	* @param undeployedPortlet boolean value for whether the portlet is an
	undeployed portlet
	*/
	public void setUndeployedPortlet(boolean undeployedPortlet) {
		_portlet.setUndeployedPortlet(undeployedPortlet);
	}

	/**
	* Checks whether this portlet is equal to the specified object.
	*
	* @param obj the object to compare this portlet against
	* @return <code>true</code> if the portlet is equal to the specified object
	*/
	public boolean equals(java.lang.Object obj) {
		return _portlet.equals(obj);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Portlet getWrappedPortlet() {
		return _portlet;
	}

	public Portlet getWrappedModel() {
		return _portlet;
	}

	public void resetOriginalValues() {
		_portlet.resetOriginalValues();
	}

	private Portlet _portlet;
}