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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionPropagator;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.PortletQNameUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletImpl extends PortletBaseImpl {

	/**
	 * Constructs a portlet with no parameters.
	 */
	public PortletImpl() {
	}

	/**
	 * Constructs a portlet with the specified parameters.
	 */
	public PortletImpl(long companyId, String portletId) {
		setCompanyId(companyId);
		setPortletId(portletId);
		setStrutsPath(portletId);
		setActive(true);
		_indexerClasses = new ArrayList<String>();
		_schedulerEntries = new ArrayList<SchedulerEntry>();
		_assetRendererFactoryClasses = new ArrayList<String>();
		_atomCollectionAdapterClasses = new ArrayList<String>();
		_customAttributesDisplayClasses = new ArrayList<String>();
		_workflowHandlerClasses = new ArrayList<String>();
		_autopropagatedParameters = new LinkedHashSet<String>();
		_headerPortalCss = new ArrayList<String>();
		_headerPortletCss = new ArrayList<String>();
		_headerPortalJavaScript = new ArrayList<String>();
		_headerPortletJavaScript = new ArrayList<String>();
		_footerPortalCss = new ArrayList<String>();
		_footerPortletCss = new ArrayList<String>();
		_footerPortalJavaScript = new ArrayList<String>();
		_footerPortletJavaScript = new ArrayList<String>();
		_unlinkedRoles = new HashSet<String>();
		_roleMappers = new LinkedHashMap<String, String>();
		_initParams = new HashMap<String, String>();
		_portletModes = new HashMap<String, Set<String>>();
		_windowStates = new HashMap<String, Set<String>>();
		_supportedLocales = new HashSet<String>();
		_portletFilters = new LinkedHashMap<String, PortletFilter>();
		_processingEvents = new HashSet<QName>();
		_publishingEvents = new HashSet<QName>();
		_publicRenderParameters = new HashSet<PublicRenderParameter>();
	}

	/**
	 * Constructs a portlet with the specified parameters.
	 */
	public PortletImpl(
		String portletId, Portlet rootPortlet, PluginPackage pluginPackage,
		PluginSetting pluginSetting, long companyId, long timestamp,
		String icon, String virtualPath, String strutsPath,
		String parentStrutsPath, String portletName, String displayName,
		String portletClass, String configurationActionClass,
		List <String> indexerClasses, String openSearchClass,
		List<SchedulerEntry> schedulerEntries, String portletURLClass,
		String friendlyURLMapperClass, String friendlyURLMapping,
		String friendlyURLRoutes, String urlEncoderClass,
		String portletDataHandlerClass, String portletLayoutListenerClass,
		String pollerProcessorClass, String popMessageListenerClass,
		String socialActivityInterpreterClass,
		String socialRequestInterpreterClass, String webDAVStorageToken,
		String webDAVStorageClass, String xmlRpcMethodClass,
		String controlPanelEntryCategory, double controlPanelEntryWeight,
		String controlPanelClass, List<String> assetRendererFactoryClasses,
		List<String> atomCollectionAdapterClasses,
		List<String> customAttributesDisplayClasses,
		String permissionPropagatorClass, List<String> workflowHandlerClasses,
		String defaultPreferences, String preferencesValidator,
		boolean preferencesCompanyWide, boolean preferencesUniquePerLayout,
		boolean preferencesOwnedByGroup, boolean useDefaultTemplate,
		boolean showPortletAccessDenied, boolean showPortletInactive,
		boolean actionURLRedirect, boolean restoreCurrentView,
		boolean maximizeEdit, boolean maximizeHelp, boolean popUpPrint,
		boolean layoutCacheable, boolean instanceable, boolean remoteable,
		boolean scopeable, String userPrincipalStrategy,
		boolean privateRequestAttributes, boolean privateSessionAttributes,
		Set<String> autopropagatedParameters, int actionTimeout,
		int renderTimeout, int renderWeight, boolean ajaxable,
		List<String> headerPortalCss, List<String> headerPortletCss,
		List<String> headerPortalJavaScript,
		List<String> headerPortletJavaScript, List<String> footerPortalCss,
		List<String> footerPortletCss, List<String> footerPortalJavaScript,
		List<String> footerPortletJavaScript, String cssClassWrapper,
		String facebookIntegration, boolean addDefaultResource, String roles,
		Set<String> unlinkedRoles, Map<String, String> roleMappers,
		boolean system, boolean active, boolean include,
		Map<String, String> initParams, Integer expCache,
		Map<String, Set<String>> portletModes,
		Map<String, Set<String>> windowStates, Set<String> supportedLocales,
		String resourceBundle, PortletInfo portletInfo,
		Map<String, PortletFilter> portletFilters, Set<QName> processingEvents,
		Set<QName> publishingEvents,
		Set<PublicRenderParameter> publicRenderParameters,
		PortletApp portletApp) {

		setPortletId(portletId);
		_rootPortlet = rootPortlet;
		_pluginPackage = pluginPackage;
		_defaultPluginSetting = pluginSetting;
		setCompanyId(companyId);
		_timestamp = timestamp;
		_icon = icon;
		_virtualPath = virtualPath;
		_strutsPath = strutsPath;
		_portletName = portletName;
		_parentStrutsPath = parentStrutsPath;
		_displayName = displayName;
		_portletClass = portletClass;
		_configurationActionClass = configurationActionClass;
		_indexerClasses = indexerClasses;
		_openSearchClass = openSearchClass;
		_schedulerEntries = schedulerEntries;
		_portletURLClass = portletURLClass;
		_friendlyURLMapperClass = friendlyURLMapperClass;
		_friendlyURLMapping = friendlyURLMapping;
		_friendlyURLRoutes = friendlyURLRoutes;
		_urlEncoderClass = urlEncoderClass;
		_portletDataHandlerClass = portletDataHandlerClass;
		_portletLayoutListenerClass = portletLayoutListenerClass;
		_pollerProcessorClass = pollerProcessorClass;
		_popMessageListenerClass = popMessageListenerClass;
		_socialActivityInterpreterClass = socialActivityInterpreterClass;
		_socialRequestInterpreterClass = socialRequestInterpreterClass;
		_webDAVStorageToken = webDAVStorageToken;
		_webDAVStorageClass = webDAVStorageClass;
		_xmlRpcMethodClass = xmlRpcMethodClass;
		_controlPanelEntryCategory = controlPanelEntryCategory;
		_controlPanelEntryWeight = controlPanelEntryWeight;
		_controlPanelEntryClass = controlPanelClass;
		_assetRendererFactoryClasses = assetRendererFactoryClasses;
		_atomCollectionAdapterClasses = atomCollectionAdapterClasses;
		_customAttributesDisplayClasses = customAttributesDisplayClasses;
		_permissionPropagatorClass = permissionPropagatorClass;
		_workflowHandlerClasses = workflowHandlerClasses;
		_defaultPreferences = defaultPreferences;
		_preferencesValidator = preferencesValidator;
		_preferencesCompanyWide = preferencesCompanyWide;
		_preferencesUniquePerLayout = preferencesUniquePerLayout;
		_preferencesOwnedByGroup = preferencesOwnedByGroup;
		_useDefaultTemplate = useDefaultTemplate;
		_showPortletAccessDenied = showPortletAccessDenied;
		_showPortletInactive = showPortletInactive;
		_actionURLRedirect = actionURLRedirect;
		_restoreCurrentView = restoreCurrentView;
		_maximizeEdit = maximizeEdit;
		_maximizeHelp = maximizeHelp;
		_popUpPrint = popUpPrint;
		_layoutCacheable = layoutCacheable;
		_instanceable = instanceable;
		_remoteable = remoteable;
		_scopeable = scopeable;
		_userPrincipalStrategy = userPrincipalStrategy;
		_privateRequestAttributes = privateRequestAttributes;
		_privateSessionAttributes = privateSessionAttributes;
		_autopropagatedParameters = autopropagatedParameters;
		_actionTimeout = actionTimeout;
		_renderTimeout = renderTimeout;
		_renderWeight = renderWeight;
		_ajaxable = ajaxable;
		_headerPortalCss = headerPortalCss;
		_headerPortletCss = headerPortletCss;
		_headerPortalJavaScript = headerPortalJavaScript;
		_headerPortletJavaScript = headerPortletJavaScript;
		_footerPortalCss = footerPortalCss;
		_footerPortletCss = footerPortletCss;
		_footerPortalJavaScript = footerPortalJavaScript;
		_footerPortletJavaScript = footerPortletJavaScript;
		_cssClassWrapper = cssClassWrapper;
		_facebookIntegration = facebookIntegration;
		_scopeable = scopeable;
		_addDefaultResource = addDefaultResource;
		setRoles(roles);
		_unlinkedRoles = unlinkedRoles;
		_roleMappers = roleMappers;
		_system = system;
		setActive(active);
		_include = include;
		_initParams = initParams;
		_expCache = expCache;
		_portletModes = portletModes;
		_windowStates = windowStates;
		_supportedLocales = supportedLocales;
		_resourceBundle = resourceBundle;
		_portletInfo = portletInfo;
		_portletFilters = portletFilters;
		setProcessingEvents(processingEvents);
		setPublishingEvents(publishingEvents);
		setPublicRenderParameters(publicRenderParameters);
		_portletApp = portletApp;
	}

	/**
	 * Returns the root portlet of this portlet instance.
	 *
	 * @return the root portlet of this portlet instance
	 */
	public Portlet getRootPortlet() {
		return _rootPortlet;
	}

	/**
	 * Returns the root portlet ID of the portlet.
	 *
	 * @return the root portlet ID of the portlet
	 */
	public String getRootPortletId() {
		return PortletConstants.getRootPortletId(getPortletId());
	}

	/**
	 * Returns the instance ID of the portlet.
	 *
	 * @return the instance ID of the portlet
	 */
	public String getInstanceId() {
		return PortletConstants.getInstanceId(getPortletId());
	}

	/**
	 * Returns the plugin ID of the portlet.
	 *
	 * @return the plugin ID of the portlet
	 */
	public String getPluginId() {
		return getRootPortletId();
	}

	/**
	 * Returns the plugin type of the portlet.
	 *
	 * @return the plugin type of the portlet
	 */
	public String getPluginType() {
		return Plugin.TYPE_PORTLET;
	}

	/**
	 * Returns this portlet's plugin package.
	 *
	 * @return this portlet's plugin package
	 */
	public PluginPackage getPluginPackage() {
		return _pluginPackage;
	}

	/**
	 * Sets this portlet's plugin package.
	 *
	 * @param pluginPackage this portlet's plugin package
	 */
	public void setPluginPackage(PluginPackage pluginPackage) {
		_pluginPackage = pluginPackage;
	}

	/**
	 * Get the default plugin settings of the portlet.
	 *
	 * @return the plugin settings
	 */
	public PluginSetting getDefaultPluginSetting() {
		return _defaultPluginSetting;
	}

	/**
	 * Sets the default plugin settings of the portlet.
	 *
	 * @param pluginSetting the plugin setting
	 */
	public void setDefaultPluginSetting(PluginSetting pluginSetting) {
		_defaultPluginSetting = pluginSetting;
	}

	/**
	 * Returns the timestamp of the portlet.
	 *
	 * @return the timestamp of the portlet
	 */
	public long getTimestamp() {
		return _timestamp;
	}

	/**
	 * Sets the timestamp of the portlet.
	 *
	 * @param timestamp the timestamp of the portlet
	 */
	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	/**
	 * Returns the icon of the portlet.
	 *
	 * @return the icon of the portlet
	 */
	public String getIcon() {
		return _icon;
	}

	/**
	 * Sets the icon of the portlet.
	 *
	 * @param icon the icon of the portlet
	 */
	public void setIcon(String icon) {
		_icon = icon;
	}

	/**
	 * Returns the virtual path of the portlet.
	 *
	 * @return the virtual path of the portlet
	 */
	public String getVirtualPath() {
		return _virtualPath;
	}

	/**
	 * Sets the virtual path of the portlet.
	 *
	 * @param virtualPath the virtual path of the portlet
	 */
	public void setVirtualPath(String virtualPath) {
		if (_portletApp.isWARFile() && Validator.isNull(virtualPath)) {
			virtualPath = PropsValues.PORTLET_VIRTUAL_PATH;
		}

		_virtualPath = virtualPath;
	}

	/**
	 * Returns the struts path of the portlet.
	 *
	 * @return the struts path of the portlet
	 */
	public String getStrutsPath() {
		return _strutsPath;
	}

	/**
	 * Sets the struts path of the portlet.
	 *
	 * @param strutsPath the struts path of the portlet
	 */
	public void setStrutsPath(String strutsPath) {
		_strutsPath = strutsPath;
	}

	/**
	 * Returns the parent struts path of the portlet.
	 *
	 * @return the parent struts path of the portlet.
	 */
	public String getParentStrutsPath() {
		return _parentStrutsPath;
	}

	/**
	 * Sets the parent struts path of the portlet.
	 *
	 * @param parentStrutsPath the parent struts path of the portlet
	 */
	public void setParentStrutsPath(String parentStrutsPath) {
		_parentStrutsPath = parentStrutsPath;
	}

	/**
	 * Returns the name of the portlet.
	 *
	 * @return the display name of the portlet
	 */
	public String getPortletName() {
		return _portletName;
	}

	/**
	 * Sets the name of the portlet.
	 *
	 * @param portletName the name of the portlet
	 */
	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	/**
	 * Returns the display name of the portlet.
	 *
	 * @return the display name of the portlet
	 */
	public String getDisplayName() {
		return _displayName;
	}

	/**
	 * Sets the display name of the portlet.
	 *
	 * @param displayName the display name of the portlet
	 */
	public void setDisplayName(String displayName) {
		_displayName = displayName;
	}

	/**
	 * Returns the name of the portlet class of the portlet.
	 *
	 * @return the name of the portlet class of the portlet
	 */
	public String getPortletClass() {
		return _portletClass;
	}

	/**
	 * Sets the name of the portlet class of the portlet.
	 *
	 * @param portletClass the name of the portlet class of the portlet
	 */
	public void setPortletClass(String portletClass) {
		_portletClass = portletClass;
	}

	/**
	 * Returns the configuration action class of the portlet.
	 *
	 * @return the configuration action class of the portlet
	 */
	public String getConfigurationActionClass() {
		return _configurationActionClass;
	}

	/**
	 * Sets the configuration action class of the portlet.
	 *
	 * @param configurationActionClass the configuration action class of the
	 *        portlet
	 */
	public void setConfigurationActionClass(String configurationActionClass) {
		_configurationActionClass = configurationActionClass;
	}

	/**
	 * Returns the configuration action instance of the portlet.
	 *
	 * @return the configuration action instance of the portlet
	 */
	public ConfigurationAction getConfigurationActionInstance() {
		if (Validator.isNull(getConfigurationActionClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getConfigurationActionInstance();
	}

	/**
	 * Returns the name of the classes that represent indexers associated with
	 * the portlet.
	 *
	 * @return the name of the classes that represent indexers associated with
	 *         the portlet
	 */
	public List<String> getIndexerClasses() {
		return _indexerClasses;
	}

	/**
	 * Sets the name of the classes that represent indexers associated with the
	 * portlet.
	 *
	 * @param indexerClasses the name of the classes that represent indexers
	 *        associated with the portlet
	 */
	public void setIndexerClasses(List<String> indexerClasses) {
		_indexerClasses = indexerClasses;
	}

	/**
	 * Returns the indexer instances of the portlet.
	 *
	 * @return the indexer instances of the portlet
	 */
	public List<Indexer> getIndexerInstances() {
		if (getIndexerClasses().isEmpty()) {
			return Collections.emptyList();
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getIndexerInstances();
	}

	/**
	 * Returns the name of the open search class of the portlet.
	 *
	 * @return the name of the open search class of the portlet
	 */
	public String getOpenSearchClass() {
		return _openSearchClass;
	}

	/**
	 * Sets the name of the open search class of the portlet.
	 *
	 * @param openSearchClass the name of the open search class of the portlet
	 */
	public void setOpenSearchClass(String openSearchClass) {
		_openSearchClass = openSearchClass;
	}

	/**
	 * Returns the indexer instance of the portlet.
	 *
	 * @return the indexer instance of the portlet
	 */
	public OpenSearch getOpenSearchInstance() {
		if (Validator.isNull(getOpenSearchClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getOpenSearchInstance();
	}

	/**
	 * Adds a scheduler entry.
	 */
	public void addSchedulerEntry(SchedulerEntry schedulerEntry) {
		_schedulerEntries.add(schedulerEntry);
	}

	/**
	 * Returns the scheduler entries of the portlet.
	 *
	 * @return the scheduler entries of the portlet
	 */
	public List<SchedulerEntry> getSchedulerEntries() {
		return _schedulerEntries;
	}

	/**
	 * Sets the scheduler entries of the portlet.
	 *
	 * @param schedulerEntries the scheduler entries of the portlet
	 */
	public void setSchedulerEntries(List<SchedulerEntry> schedulerEntries) {
		for (SchedulerEntry schedulerEntry : schedulerEntries) {
			addSchedulerEntry(schedulerEntry);
		}
	}

	/**
	 * Returns the name of the portlet URL class of the portlet.
	 *
	 * @return the name of the portlet URL class of the portlet
	 */
	public String getPortletURLClass() {
		return _portletURLClass;
	}

	/**
	 * Sets the name of the portlet URL class of the portlet.
	 *
	 * @param portletURLClass the name of the portlet URL class of the portlet
	 */
	public void setPortletURLClass(String portletURLClass) {
		_portletURLClass = portletURLClass;
	}

	/**
	 * Returns the name of the friendly URL mapper class of the portlet.
	 *
	 * @return the name of the friendly URL mapper class of the portlet
	 */
	public String getFriendlyURLMapperClass() {
		return _friendlyURLMapperClass;
	}

	/**
	 * Sets the name of the friendly URL mapper class of the portlet.
	 *
	 * @param friendlyURLMapperClass the name of the friendly URL mapper class
	 *        of the portlet
	 */
	public void setFriendlyURLMapperClass(String friendlyURLMapperClass) {
		_friendlyURLMapperClass = friendlyURLMapperClass;
	}

	/**
	 * Returns the friendly URL mapper instance of the portlet.
	 *
	 * @return the friendly URL mapper instance of the portlet
	 */
	public FriendlyURLMapper getFriendlyURLMapperInstance() {
		if (Validator.isNull(getFriendlyURLMapperClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getFriendlyURLMapperInstance();
	}

	/**
	 * Returns the name of the friendly URL mapping of the portlet.
	 *
	 * @return the name of the friendly URL mapping of the portlet
	 */
	public String getFriendlyURLMapping() {
		return _friendlyURLMapping;
	}

	/**
	 * Sets the name of the friendly URL mapping of the portlet.
	 *
	 * @param friendlyURLMapping the name of the friendly URL mapping of the
	 *        portlet
	 */
	public void setFriendlyURLMapping(String friendlyURLMapping) {
		_friendlyURLMapping = friendlyURLMapping;
	}

	/**
	 * Returns the class loader resource path to the friendly URL routes of the
	 * portlet.
	 *
	 * @return the class loader resource path to the friendly URL routes of the
	 *         portlet
	 */
	public String getFriendlyURLRoutes() {
		return _friendlyURLRoutes;
	}

	/**
	 * Sets the class loader resource path to the friendly URL routes of the
	 * portlet.
	 *
	 * @param friendlyURLRoutes the class loader resource path to the friendly
	 *        URL routes of the portlet
	 */
	public void setFriendlyURLRoutes(String friendlyURLRoutes) {
		_friendlyURLRoutes = friendlyURLRoutes;
	}

	/**
	 * Returns the name of the URL encoder class of the portlet.
	 *
	 * @return the name of the URL encoder class of the portlet
	 */
	public String getURLEncoderClass() {
		return _urlEncoderClass;
	}

	/**
	 * Sets the name of the URL encoder class of the portlet.
	 *
	 * @param urlEncoderClass the name of the URL encoder class of the portlet
	 */
	public void setURLEncoderClass(String urlEncoderClass) {
		_urlEncoderClass = urlEncoderClass;
	}

	/**
	 * Returns the URL encoder instance of the portlet.
	 *
	 * @return the URL encoder instance of the portlet
	 */
	public URLEncoder getURLEncoderInstance() {
		if (Validator.isNull(getURLEncoderClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getURLEncoderInstance();
	}

	/**
	 * Returns the name of the portlet data handler class of the portlet.
	 *
	 * @return the name of the portlet data handler class of the portlet
	 */
	public String getPortletDataHandlerClass() {
		return _portletDataHandlerClass;
	}

	/**
	 * Sets the name of the portlet data handler class of the portlet.
	 *
	 * @param portletDataHandlerClass the name of portlet data handler class of
	 *        the portlet
	 */
	public void setPortletDataHandlerClass(String portletDataHandlerClass) {
		_portletDataHandlerClass = portletDataHandlerClass;
	}

	/**
	 * Returns the portlet data handler instance of the portlet.
	 *
	 * @return the portlet data handler instance of the portlet
	 */
	public PortletDataHandler getPortletDataHandlerInstance() {
		if (Validator.isNull(getPortletDataHandlerClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getPortletDataHandlerInstance();
	}

	/**
	 * Returns the name of the portlet layout listener class of the portlet.
	 *
	 * @return the name of the portlet layout listener class of the portlet
	 */
	public String getPortletLayoutListenerClass() {
		return _portletLayoutListenerClass;
	}

	/**
	 * Sets the name of the portlet layout listener class of the portlet.
	 *
	 * @param portletLayoutListenerClass the name of the portlet layout listener
	 *        class of the portlet
	 */
	public void setPortletLayoutListenerClass(
		String portletLayoutListenerClass) {

		_portletLayoutListenerClass = portletLayoutListenerClass;
	}

	/**
	 * Returns the portlet layout listener instance of the portlet.
	 *
	 * @return the portlet layout listener instance of the portlet
	 */
	public PortletLayoutListener getPortletLayoutListenerInstance() {
		if (Validator.isNull(getPortletLayoutListenerClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getPortletLayoutListenerInstance();
	}

	/**
	 * Returns the name of the poller processor class of the portlet.
	 *
	 * @return the name of the poller processor class of the portlet
	 */
	public String getPollerProcessorClass() {
		return _pollerProcessorClass;
	}

	/**
	 * Sets the name of the poller processor class of the portlet.
	 *
	 * @param pollerProcessorClass the name of the poller processor class of the
	 *        portlet
	 */
	public void setPollerProcessorClass(String pollerProcessorClass) {
		_pollerProcessorClass = pollerProcessorClass;
	}

	/**
	 * Returns the poller processor instance of the portlet.
	 *
	 * @return the poller processor instance of the portlet
	 */
	public PollerProcessor getPollerProcessorInstance() {
		if (Validator.isNull(getPollerProcessorClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getPollerProcessorInstance();
	}

	/**
	 * Returns the name of the POP message listener class of the portlet.
	 *
	 * @return the name of the POP message listener class of the portlet
	 */
	public String getPopMessageListenerClass() {
		return _popMessageListenerClass;
	}

	/**
	 * Sets the name of the POP message listener class of the portlet.
	 *
	 * @param popMessageListenerClass the name of the POP message listener class
	 *        of the portlet
	 */
	public void setPopMessageListenerClass(String popMessageListenerClass) {
		_popMessageListenerClass = popMessageListenerClass;
	}

	/**
	 * Returns the POP message listener instance of the portlet.
	 *
	 * @return the POP message listener instance of the portlet
	 */
	public MessageListener getPopMessageListenerInstance() {
		if (Validator.isNull(getPopMessageListenerClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getPopMessageListenerInstance();
	}

	/**
	 * Returns the name of the social activity interpreter class of the portlet.
	 *
	 * @return the name of the social activity interpreter class of the portlet
	 */
	public String getSocialActivityInterpreterClass() {
		return _socialActivityInterpreterClass;
	}

	/**
	 * Sets the name of the social activity interpreter class of the portlet.
	 *
	 * @param socialActivityInterpreterClass the name of the activity
	 *        interpreter class of the portlet
	 */
	public void setSocialActivityInterpreterClass(
		String socialActivityInterpreterClass) {

		_socialActivityInterpreterClass = socialActivityInterpreterClass;
	}

	/**
	 * Returns the name of the social activity interpreter instance of the
	 * portlet.
	 *
	 * @return the name of the social activity interpreter instance of the
	 *         portlet
	 */
	public SocialActivityInterpreter getSocialActivityInterpreterInstance() {
		if (Validator.isNull(getSocialActivityInterpreterClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getSocialActivityInterpreterInstance();
	}

	/**
	 * Returns the name of the social request interpreter class of the portlet.
	 *
	 * @return the name of the social request interpreter class of the portlet
	 */
	public String getSocialRequestInterpreterClass() {
		return _socialRequestInterpreterClass;
	}

	/**
	 * Sets the name of the social request interpreter class of the portlet.
	 *
	 * @param socialRequestInterpreterClass the name of the request interpreter
	 *        class of the portlet
	 */
	public void setSocialRequestInterpreterClass(
		String socialRequestInterpreterClass) {

		_socialRequestInterpreterClass = socialRequestInterpreterClass;
	}

	/**
	 * Returns the name of the social request interpreter instance of the
	 * portlet.
	 *
	 * @return the name of the social request interpreter instance of the
	 *         portlet
	 */
	public SocialRequestInterpreter getSocialRequestInterpreterInstance() {
		if (Validator.isNull(getSocialRequestInterpreterClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getSocialRequestInterpreterInstance();
	}

	/**
	 * Returns the name of the WebDAV storage token of the portlet.
	 *
	 * @return the name of the WebDAV storage token of the portlet
	 */
	public String getWebDAVStorageToken() {
		return _webDAVStorageToken;
	}

	/**
	 * Sets the name of the WebDAV storage token of the portlet.
	 *
	 * @param webDAVStorageToken the name of the WebDAV storage token of the
	 *        portlet
	 */
	public void setWebDAVStorageToken(String webDAVStorageToken) {
		_webDAVStorageToken = webDAVStorageToken;
	}

	/**
	 * Returns the name of the WebDAV storage class of the portlet.
	 *
	 * @return the name of the WebDAV storage class of the portlet
	 */
	public String getWebDAVStorageClass() {
		return _webDAVStorageClass;
	}

	/**
	 * Sets the name of the WebDAV storage class of the portlet.
	 *
	 * @param webDAVStorageClass the name of the WebDAV storage class of the
	 *        portlet
	 */
	public void setWebDAVStorageClass(String webDAVStorageClass) {
		_webDAVStorageClass = webDAVStorageClass;
	}

	/**
	 * Returns the name of the WebDAV storage instance of the portlet.
	 *
	 * @return the name of the WebDAV storage instance of the portlet
	 */
	public WebDAVStorage getWebDAVStorageInstance() {
		if (Validator.isNull(getWebDAVStorageClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getWebDAVStorageInstance();
	}

	/**
	 * Returns the name of the XML-RPC method class of the portlet.
	 *
	 * @return the name of the XML-RPC method class of the portlet
	 */
	public String getXmlRpcMethodClass() {
		return _xmlRpcMethodClass;
	}

	/**
	 * Sets the name of the XML-RPC method class of the portlet.
	 *
	 * @param xmlRpcMethodClass the name of the XML-RPC method class of the
	 *        portlet
	 */
	public void setXmlRpcMethodClass(String xmlRpcMethodClass) {
		_xmlRpcMethodClass = xmlRpcMethodClass;
	}

	/**
	 * Returns the name of the XML-RPC method instance of the portlet.
	 *
	 * @return the name of the XML-RPC method instance of the portlet
	 */
	public Method getXmlRpcMethodInstance() {
		if (Validator.isNull(getXmlRpcMethodClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getXmlRpcMethodInstance();
	}

	/**
	 * Returns the name of the category of the Control Panel where the portlet
	 * will be shown.
	 *
	 * @return the name of the category of the Control Panel where the portlet
	 *         will be shown
	 */
	public String getControlPanelEntryCategory() {
		return _controlPanelEntryCategory;
	}

	/**
	 * Set the name of the category of the Control Panel where the portlet will
	 * be shown.
	 *
	 * @param controlPanelEntryCategory the name of the category of the Control
	 *        Panel where the portlet will be shown
	 */
	public void setControlPanelEntryCategory(String controlPanelEntryCategory) {
		_controlPanelEntryCategory = controlPanelEntryCategory;
	}

	/**
	 * Returns the relative weight of the portlet with respect to the other
	 * portlets in the same category of the Control Panel.
	 *
	 * @return the relative weight of the portlet with respect to the other
	 *         portlets in the same category of the Control Panel
	 */
	public double getControlPanelEntryWeight() {
		return _controlPanelEntryWeight;
	}

	/**
	 * Sets the relative weight of the portlet with respect to the other
	 * portlets in the same category of the Control Panel.
	 *
	 * @param controlPanelEntryWeight the relative weight of the portlet with
	 *        respect to the other portlets in the same category of the Control
	 *        Panel
	 */
	public void setControlPanelEntryWeight(double controlPanelEntryWeight) {
		_controlPanelEntryWeight = controlPanelEntryWeight;
	}

	/**
	 * Returns the name of the class that will control when the portlet will be
	 * shown in the Control Panel.
	 *
	 * @return the name of the class that will control when the portlet will be
	 *         shown in the Control Panel
	 */
	public String getControlPanelEntryClass() {
		return _controlPanelEntryClass;
	}

	/**
	 * Sets the name of the class that will control when the portlet will be
	 * shown in the Control Panel.
	 *
	 * @param controlPanelEntryClass the name of the class that will control
	 *        when the portlet will be shown in the Control Panel
	 */
	public void setControlPanelEntryClass(String controlPanelEntryClass) {
		_controlPanelEntryClass = controlPanelEntryClass;
	}

	/**
	 * Returns an instance of the class that will control when the portlet will
	 * be shown in the Control Panel.
	 *
	 * @return the instance of the class that will control when the portlet will
	 *         be shown in the Control Panel
	 */
	public ControlPanelEntry getControlPanelEntryInstance() {
		if (Validator.isNull(getControlPanelEntryClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getControlPanelEntryInstance();
	}

	/**
	 * Returns the names of the classes that represent asset types associated
	 * with the portlet.
	 *
	 * @return the names of the classes that represent asset types associated
	 *         with the portlet
	 */
	public List<String> getAssetRendererFactoryClasses() {
		return _assetRendererFactoryClasses;
	}

	/**
	 * Sets the name of the classes that represent asset types associated with
	 * the portlet.
	 *
	 * @param assetRendererFactoryClasses the names of the classes that
	 *        represent asset types associated with the portlet
	 */
	public void setAssetRendererFactoryClasses(
		List<String> assetRendererFactoryClasses) {

		_assetRendererFactoryClasses = assetRendererFactoryClasses;
	}

	/**
	 * Returns the asset type instances of the portlet.
	 *
	 * @return the asset type instances of the portlet
	 */
	public List<AssetRendererFactory> getAssetRendererFactoryInstances() {
		if (getAssetRendererFactoryClasses().isEmpty()) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getAssetRendererFactoryInstances();
	}

	/**
	 * Returns the names of the classes that represent atom collection adapters
	 * associated with the portlet.
	 *
	 * @return the names of the classes that represent atom collection adapters
	 *         associated with the portlet
	 */
	public List<String> getAtomCollectionAdapterClasses() {
		return _atomCollectionAdapterClasses;
	}

	/**
	 * Sets the name of the classes that represent atom collection adapters
	 * associated with the portlet.
	 *
	 * @param atomCollectionAdapterClasses the names of the classes that
	 *        represent atom collection adapters associated with the portlet
	 */
	public void setAtomCollectionAdapterClasses(
		List<String> atomCollectionAdapterClasses) {

		_atomCollectionAdapterClasses = atomCollectionAdapterClasses;
	}

	/**
	 * Returns the atom collection adapter instances of the portlet.
	 *
	 * @return the atom collection adapter instances of the portlet
	 */
	public List<AtomCollectionAdapter<?>> getAtomCollectionAdapterInstances() {
		if (getAtomCollectionAdapterClasses().isEmpty()) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getAtomCollectionAdapterInstances();
	}

	/**
	 * Returns the names of the classes that represent custom attribute displays
	 * associated with the portlet.
	 *
	 * @return the names of the classes that represent asset types associated
	 *         with the portlet
	 */
	public List<String> getCustomAttributesDisplayClasses() {
		return _customAttributesDisplayClasses;
	}

	/**
	 * Sets the name of the classes that represent custom attribute displays
	 * associated with the portlet.
	 *
	 * @param customAttributesDisplayClasses the names of the classes that
	 *        represent custom attribute displays associated with the portlet
	 */
	public void setCustomAttributesDisplayClasses(
		List<String> customAttributesDisplayClasses) {

		_customAttributesDisplayClasses = customAttributesDisplayClasses;
	}

	/**
	 * Returns the custom attribute display instances of the portlet.
	 *
	 * @return the custom attribute display instances of the portlet
	 */
	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		if (getCustomAttributesDisplayClasses().isEmpty()) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getCustomAttributesDisplayInstances();
	}

	/**
	 * Returns the name of the permission propagator class of the portlet.
	 *
	 * @return the name of the permission propagator class of the portlet
	 */
	public String getPermissionPropagatorClass() {
		return _permissionPropagatorClass;
	}

	/**
	 * Sets the name of the permission propagator class of the portlet.
	 *
	 * @param pollerProcessorClass the name of the permission propagator class
	 *        of the portlet
	 */
	public void setPermissionPropagatorClass(String permissionPropagatorClass) {
		_permissionPropagatorClass = permissionPropagatorClass;
	}

	/**
	 * Returns the permission propagator instance of the portlet.
	 *
	 * @return the permission propagator instance of the portlet
	 */
	public PermissionPropagator getPermissionPropagatorInstance() {
		if (Validator.isNull(getPermissionPropagatorClass())) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getPermissionPropagatorInstance();
	}

	/**
	 * Returns the names of the classes that represent workflow handlers
	 * associated with the portlet.
	 *
	 * @return the names of the classes that represent workflow handlers
	 *         associated with the portlet
	 */
	public List<String> getWorkflowHandlerClasses() {
		return _workflowHandlerClasses;
	}

	/**
	 * Sets the name of the classes that represent workflow handlers associated
	 * to the portlet.
	 *
	 * @param workflowHandlerClasses the names of the classes that represent
	 *        workflow handlers associated with the portlet
	 */
	public void setWorkflowHandlerClasses(List<String> workflowHandlerClasses) {
		_workflowHandlerClasses = workflowHandlerClasses;
	}

	/**
	 * Returns the workflow handler instances of the portlet.
	 *
	 * @return the workflow handler instances of the portlet
	 */
	public List<WorkflowHandler> getWorkflowHandlerInstances() {
		if (getWorkflowHandlerClasses().isEmpty()) {
			return null;
		}

		PortletBag portletBag = PortletBagPool.get(getRootPortletId());

		return portletBag.getWorkflowHandlerInstances();
	}

	/**
	 * Returns the default preferences of the portlet.
	 *
	 * @return the default preferences of the portlet
	 */
	public String getDefaultPreferences() {
		if (Validator.isNull(_defaultPreferences)) {
			return PortletConstants.DEFAULT_PREFERENCES;
		}
		else {
			return _defaultPreferences;
		}
	}

	/**
	 * Sets the default preferences of the portlet.
	 *
	 * @param defaultPreferences the default preferences of the portlet
	 */
	public void setDefaultPreferences(String defaultPreferences) {
		_defaultPreferences = defaultPreferences;
	}

	/**
	 * Returns the name of the preferences validator class of the portlet.
	 *
	 * @return the name of the preferences validator class of the portlet
	 */
	public String getPreferencesValidator() {
		return _preferencesValidator;
	}

	/**
	 * Sets the name of the preferences validator class of the portlet.
	 *
	 * @param preferencesValidator the name of the preferences validator class
	 *        of the portlet
	 */
	public void setPreferencesValidator(String preferencesValidator) {
		if (preferencesValidator != null) {

			// Trim this because XDoclet generates preferences validators with
			// extra white spaces

			_preferencesValidator = preferencesValidator.trim();
		}
		else {
			_preferencesValidator = null;
		}
	}

	/**
	 * Returns <code>true</code> if preferences are shared across the entire
	 * company.
	 *
	 * @return <code>true</code> if preferences are shared across the entire
	 *         company
	 */
	public boolean getPreferencesCompanyWide() {
		return _preferencesCompanyWide;
	}

	/**
	 * Returns <code>true</code> if preferences are shared across the entire
	 * company.
	 *
	 * @return <code>true</code> if preferences are shared across the entire
	 *         company
	 */
	public boolean isPreferencesCompanyWide() {
		return _preferencesCompanyWide;
	}

	/**
	 * Set to <code>true</code> if preferences are shared across the entire
	 * company.
	 *
	 * @param preferencesCompanyWide boolean value for whether preferences are
	 *        shared across the entire company
	 */
	public void setPreferencesCompanyWide(boolean preferencesCompanyWide) {
		_preferencesCompanyWide = preferencesCompanyWide;
	}

	/**
	 * Returns <code>true</code> if preferences are unique per layout.
	 *
	 * @return <code>true</code> if preferences are unique per layout
	 */
	public boolean getPreferencesUniquePerLayout() {
		return _preferencesUniquePerLayout;
	}

	/**
	 * Returns <code>true</code> if preferences are unique per layout.
	 *
	 * @return <code>true</code> if preferences are unique per layout
	 */
	public boolean isPreferencesUniquePerLayout() {
		return _preferencesUniquePerLayout;
	}

	/**
	 * Set to <code>true</code> if preferences are unique per layout.
	 *
	 * @param preferencesUniquePerLayout boolean value for whether preferences
	 *        are unique per layout
	 */
	public void setPreferencesUniquePerLayout(
		boolean preferencesUniquePerLayout) {

		_preferencesUniquePerLayout = preferencesUniquePerLayout;
	}

	/**
	 * Returns <code>true</code> if preferences are owned by the group when the
	 * portlet is shown in a group layout. Returns <code>false</code> if
	 * preferences are owned by the user at all times.
	 *
	 * @return <code>true</code> if preferences are owned by the group when the
	 *         portlet is shown in a group layout; <code>false</code> if
	 *         preferences are owned by the user at all times.
	 */
	public boolean getPreferencesOwnedByGroup() {
		return _preferencesOwnedByGroup;
	}

	/**
	 * Returns <code>true</code> if preferences are owned by the group when the
	 * portlet is shown in a group layout. Returns <code>false</code> if
	 * preferences are owned by the user at all times.
	 *
	 * @return <code>true</code> if preferences are owned by the group when the
	 *         portlet is shown in a group layout; <code>false</code> if
	 *         preferences are owned by the user at all times.
	 */
	public boolean isPreferencesOwnedByGroup() {
		return _preferencesOwnedByGroup;
	}

	/**
	 * Set to <code>true</code> if preferences are owned by the group when the
	 * portlet is shown in a group layout. Set to <code>false</code> if
	 * preferences are owned by the user at all times.
	 *
	 * @param preferencesOwnedByGroup boolean value for whether preferences are
	 *        owned by the group when the portlet is shown in a group layout or
	 *        preferences are owned by the user at all times
	 */
	public void setPreferencesOwnedByGroup(boolean preferencesOwnedByGroup) {
		_preferencesOwnedByGroup = preferencesOwnedByGroup;
	}

	/**
	 * Returns <code>true</code> if the portlet uses the default template.
	 *
	 * @return <code>true</code> if the portlet uses the default template
	 */
	public boolean getUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	/**
	 * Returns <code>true</code> if the portlet uses the default template.
	 *
	 * @return <code>true</code> if the portlet uses the default template
	 */
	public boolean isUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	/**
	 * Set to <code>true</code> if the portlet uses the default template.
	 *
	 * @param useDefaultTemplate boolean value for whether the portlet uses the
	 *        default template
	 */
	public void setUseDefaultTemplate(boolean useDefaultTemplate) {
		_useDefaultTemplate = useDefaultTemplate;
	}

	/**
	 * Returns <code>true</code> if users are shown that they do not have access
	 * to the portlet.
	 *
	 * @return <code>true</code> if users are shown that they do not have access
	 *         to the portlet
	 */
	public boolean getShowPortletAccessDenied() {
		return _showPortletAccessDenied;
	}

	/**
	 * Returns <code>true</code> if users are shown that they do not have access
	 * to the portlet.
	 *
	 * @return <code>true</code> if users are shown that they do not have access
	 *         to the portlet
	 */
	public boolean isShowPortletAccessDenied() {
		return _showPortletAccessDenied;
	}

	/**
	 * Set to <code>true</code> if users are shown that they do not have access
	 * to the portlet.
	 *
	 * @param showPortletAccessDenied boolean value for whether users are shown
	 *        that they do not have access to the portlet
	 */
	public void setShowPortletAccessDenied(boolean showPortletAccessDenied) {
		_showPortletAccessDenied = showPortletAccessDenied;
	}

	/**
	 * Returns <code>true</code> if users are shown that the portlet is
	 * inactive.
	 *
	 * @return <code>true</code> if users are shown that the portlet is inactive
	 */
	public boolean getShowPortletInactive() {
		return _showPortletInactive;
	}

	/**
	 * Returns <code>true</code> if users are shown that the portlet is
	 * inactive.
	 *
	 * @return <code>true</code> if users are shown that the portlet is inactive
	 */
	public boolean isShowPortletInactive() {
		return _showPortletInactive;
	}

	/**
	 * Set to <code>true</code> if users are shown that the portlet is inactive.
	 *
	 * @param showPortletInactive boolean value for whether users are shown that
	 *        the portlet is inactive
	 */
	public void setShowPortletInactive(boolean showPortletInactive) {
		_showPortletInactive = showPortletInactive;
	}

	/**
	 * Returns <code>true</code> if an action URL for this portlet should cause
	 * an auto redirect.
	 *
	 * @return <code>true</code> if an action URL for this portlet should cause
	 *         an auto redirect
	 */
	public boolean getActionURLRedirect() {
		return _actionURLRedirect;
	}

	/**
	 * Returns <code>true</code> if an action URL for this portlet should cause
	 * an auto redirect.
	 *
	 * @return <code>true</code> if an action URL for this portlet should cause
	 *         an auto redirect
	 */
	public boolean isActionURLRedirect() {
		return _actionURLRedirect;
	}

	/**
	 * Set to <code>true</code> if an action URL for this portlet should cause
	 * an auto redirect.
	 *
	 * @param actionURLRedirect boolean value for whether an action URL for this
	 *        portlet should cause an auto redirect
	 */
	public void setActionURLRedirect(boolean actionURLRedirect) {
		_actionURLRedirect = actionURLRedirect;
	}

	/**
	 * Returns <code>true</code> if the portlet restores to the current view
	 * from the maximized state.
	 *
	 * @return <code>true</code> if the portlet restores to the current view
	 *         from the maximized state
	 */
	public boolean getRestoreCurrentView() {
		return _restoreCurrentView;
	}

	/**
	 * Returns <code>true</code> if the portlet restores to the current view
	 * from the maximized state.
	 *
	 * @return <code>true</code> if the portlet restores to the current view
	 *         from the maximized state
	 */
	public boolean isRestoreCurrentView() {
		return _restoreCurrentView;
	}

	/**
	 * Set to <code>true</code> if the portlet restores to the current view from
	 * the maximized state.
	 *
	 * @param restoreCurrentView boolean value for whether the portlet restores
	 *        to the current view from the maximized state
	 */
	public void setRestoreCurrentView(boolean restoreCurrentView) {
		_restoreCurrentView = restoreCurrentView;
	}

	/**
	 * Returns <code>true</code> if the portlet goes into the maximized state
	 * when the user goes into the edit mode.
	 *
	 * @return <code>true</code> if the portlet goes into the maximized state
	 *         when the user goes into the edit mode
	 */
	public boolean getMaximizeEdit() {
		return _maximizeEdit;
	}

	/**
	 * Returns <code>true</code> if the portlet goes into the maximized state
	 * when the user goes into the edit mode.
	 *
	 * @return <code>true</code> if the portlet goes into the maximized state
	 *         when the user goes into the edit mode
	 */
	public boolean isMaximizeEdit() {
		return _maximizeEdit;
	}

	/**
	 * Set to <code>true</code> if the portlet goes into the maximized state
	 * when the user goes into the edit mode.
	 *
	 * @param maximizeEdit boolean value for whether the portlet goes into the
	 *        maximized state when the user goes into the edit mode
	 */
	public void setMaximizeEdit(boolean maximizeEdit) {
		_maximizeEdit = maximizeEdit;
	}

	/**
	 * Returns <code>true</code> if the portlet goes into the maximized state
	 * when the user goes into the help mode.
	 *
	 * @return <code>true</code> if the portlet goes into the maximized state
	 *         when the user goes into the help mode
	 */
	public boolean getMaximizeHelp() {
		return _maximizeHelp;
	}

	/**
	 * Returns <code>true</code> if the portlet goes into the maximized state
	 * when the user goes into the help mode.
	 *
	 * @return <code>true</code> if the portlet goes into the maximized state
	 *         when the user goes into the help mode
	 */
	public boolean isMaximizeHelp() {
		return _maximizeHelp;
	}

	/**
	 * Set to <code>true</code> if the portlet goes into the maximized state
	 * when the user goes into the help mode.
	 *
	 * @param maximizeHelp boolean value for whether the portlet goes into the
	 *        maximized state when the user goes into the help mode
	 */
	public void setMaximizeHelp(boolean maximizeHelp) {
		_maximizeHelp = maximizeHelp;
	}

	/**
	 * Returns <code>true</code> if the portlet goes into the pop up state when
	 * the user goes into the print mode.
	 *
	 * @return <code>true</code> if the portlet goes into the pop up state when
	 *         the user goes into the print mode
	 */
	public boolean getPopUpPrint() {
		return _popUpPrint;
	}

	/**
	 * Returns <code>true</code> if the portlet goes into the pop up state when
	 * the user goes into the print mode.
	 *
	 * @return <code>true</code> if the portlet goes into the pop up state when
	 *         the user goes into the print mode
	 */
	public boolean isPopUpPrint() {
		return _popUpPrint;
	}

	/**
	 * Set to <code>true</code> if the portlet goes into the pop up state when
	 * the user goes into the print mode.
	 *
	 * @param popUpPrint boolean value for whether the portlet goes into the pop
	 *        up state when the user goes into the print mode
	 */
	public void setPopUpPrint(boolean popUpPrint) {
		_popUpPrint = popUpPrint;
	}

	/**
	 * Returns <code>true</code> to allow the portlet to be cached within the
	 * layout.
	 *
	 * @return <code>true</code> if the portlet can be cached within the layout
	 */
	public boolean getLayoutCacheable() {
		return _layoutCacheable;
	}

	/**
	 * Returns <code>true</code> to allow the portlet to be cached within the
	 * layout.
	 *
	 * @return <code>true</code> if the portlet can be cached within the layout
	 */
	public boolean isLayoutCacheable() {
		return _layoutCacheable;
	}

	/**
	 * Set to <code>true</code> to allow the portlet to be cached within the
	 * layout.
	 *
	 * @param layoutCacheable boolean value for whether the portlet can be
	 *        cached within the layout
	 */
	public void setLayoutCacheable(boolean layoutCacheable) {
		_layoutCacheable = layoutCacheable;
	}

	/**
	 * Returns <code>true</code> if the portlet can be added multiple times to a
	 * layout.
	 *
	 * @return <code>true</code> if the portlet can be added multiple times to a
	 *         layout
	 */
	public boolean getInstanceable() {
		return _instanceable;
	}

	/**
	 * Returns <code>true</code> if the portlet can be added multiple times to a
	 * layout.
	 *
	 * @return <code>true</code> if the portlet can be added multiple times to a
	 *         layout
	 */
	public boolean isInstanceable() {
		return _instanceable;
	}

	/**
	 * Set to <code>true</code> if the portlet can be added multiple times to a
	 * layout.
	 *
	 * @param instanceable boolean value for whether the portlet can be added
	 *        multiple times to a layout
	 */
	public void setInstanceable(boolean instanceable) {
		_instanceable = instanceable;
	}

	/**
	 * Returns <code>true</code> if the portlet supports remoting.
	 *
	 * @return <code>true</code> if the portlet supports remoting
	 */
	public boolean getRemoteable() {
		return _remoteable;
	}

	/**
	 * Returns <code>true</code> if the portlet supports remoting.
	 *
	 * @return <code>true</code> if the portlet supports remoting
	 */
	public boolean isRemoteable() {
		return _remoteable;
	}

	/**
	 * Set to <code>true</code> if the portlet supports remoting
	 *
	 * @param remoteable boolean value for whether or not the the portlet
	 *        supports remoting
	 */
	public void setRemoteable(boolean remoteable) {
		_remoteable = remoteable;
	}

	/**
	 * Returns <code>true</code> if the portlet supports scoping of data.
	 *
	 * @return <code>true</code> if the portlet supports scoping of data
	 */
	public boolean getScopeable() {
		return _scopeable;
	}

	/**
	 * Returns <code>true</code> if the portlet supports scoping of data.
	 *
	 * @return <code>true</code> if the portlet supports scoping of data
	 */
	public boolean isScopeable() {
		return _scopeable;
	}

	/**
	 * Set to <code>true</code> if the portlet supports scoping of data.
	 *
	 * @param scopeable boolean value for whether or not the the portlet
	 *        supports scoping of data
	 */
	public void setScopeable(boolean scopeable) {
		_scopeable = scopeable;
	}

	/**
	 * Returns the user principal strategy of the portlet.
	 *
	 * @return the user principal strategy of the portlet
	 */
	public String getUserPrincipalStrategy() {
		return _userPrincipalStrategy;
	}

	/**
	 * Sets the user principal strategy of the portlet.
	 *
	 * @param userPrincipalStrategy the user principal strategy of the portlet
	 */
	public void setUserPrincipalStrategy(String userPrincipalStrategy) {
		if (Validator.isNotNull(userPrincipalStrategy)) {
			_userPrincipalStrategy = userPrincipalStrategy;
		}
	}

	/**
	 * Returns <code>true</code> if the portlet does not share request
	 * attributes with the portal or portlets from another WAR.
	 *
	 * @return <code>true</code> if the portlet does not share request
	 *         attributes with the portal or portlets from another WAR
	 */
	public boolean getPrivateRequestAttributes() {
		return _privateRequestAttributes;
	}

	/**
	 * Returns <code>true</code> if the portlet does not share request
	 * attributes with the portal or portlets from another WAR.
	 *
	 * @return <code>true</code> if the portlet does not share request
	 *         attributes with the portal or portlets from another WAR
	 */
	public boolean isPrivateRequestAttributes() {
		return _privateRequestAttributes;
	}

	/**
	 * Set to <code>true</code> if the portlet does not share request attributes
	 * with the portal or portlets from another WAR.
	 *
	 * @param privateRequestAttributes boolean value for whether the portlet
	 *        shares request attributes with the portal or portlets from another
	 *        WAR
	 */
	public void setPrivateRequestAttributes(boolean privateRequestAttributes) {
		_privateRequestAttributes = privateRequestAttributes;
	}

	/**
	 * Returns <code>true</code> if the portlet does not share session
	 * attributes with the portal.
	 *
	 * @return <code>true</code> if the portlet does not share session
	 *         attributes with the portal
	 */
	public boolean getPrivateSessionAttributes() {
		return _privateSessionAttributes;
	}

	/**
	 * Returns <code>true</code> if the portlet does not share session
	 * attributes with the portal.
	 *
	 * @return <code>true</code> if the portlet does not share session
	 *         attributes with the portal
	 */
	public boolean isPrivateSessionAttributes() {
		return _privateSessionAttributes;
	}

	/**
	 * Set to <code>true</code> if the portlet does not share session attributes
	 * with the portal.
	 *
	 * @param privateSessionAttributes boolean value for whether the portlet
	 *        shares session attributes with the portal
	 */
	public void setPrivateSessionAttributes(boolean privateSessionAttributes) {
		_privateSessionAttributes = privateSessionAttributes;
	}

	/**
	 * Returns the names of the parameters that will be automatically propagated
	 * through the portlet.
	 *
	 * @return the names of the parameters that will be automatically propagated
	 *         through the portlet
	 */
	public Set<String> getAutopropagatedParameters() {
		return _autopropagatedParameters;
	}

	/**
	 * Sets the names of the parameters that will be automatically propagated
	 * through the portlet.
	 *
	 * @param autopropagatedParameters the names of the parameters that will be
	 *        automatically propagated through the portlet
	 */
	public void setAutopropagatedParameters(
		Set<String> autopropagatedParameters) {

		_autopropagatedParameters = autopropagatedParameters;
	}

	/**
	 * Returns the action timeout of the portlet.
	 *
	 * @return the action timeout of the portlet
	 */
	public int getActionTimeout() {
		return _actionTimeout;
	}

	/**
	 * Sets the action timeout of the portlet.
	 *
	 * @param actionTimeout the action timeout of the portlet
	 */
	public void setActionTimeout(int actionTimeout) {
		_actionTimeout = actionTimeout;
	}

	/**
	 * Returns the render timeout of the portlet.
	 *
	 * @return the render timeout of the portlet
	 */
	public int getRenderTimeout() {
		return _renderTimeout;
	}

	/**
	 * Sets the render timeout of the portlet.
	 *
	 * @param renderTimeout the render timeout of the portlet
	 */
	public void setRenderTimeout(int renderTimeout) {
		_renderTimeout = renderTimeout;
	}

	/**
	 * Returns the render weight of the portlet.
	 *
	 * @return the render weight of the portlet
	 */
	public int getRenderWeight() {
		return _renderWeight;
	}

	/**
	 * Sets the render weight of the portlet.
	 *
	 * @param renderWeight int value for the render weight of the portlet
	 */
	public void setRenderWeight(int renderWeight) {
		_renderWeight = renderWeight;
	}

	/**
	 * Returns <code>true</code> if the portlet can be displayed via Ajax.
	 *
	 * @return <code>true</code> if the portlet can be displayed via Ajax
	 */
	public boolean getAjaxable() {
		return _ajaxable;
	}

	/**
	 * Returns <code>true</code> if the portlet can be displayed via Ajax.
	 *
	 * @return <code>true</code> if the portlet can be displayed via Ajax
	 */
	public boolean isAjaxable() {
		return _ajaxable;
	}

	/**
	 * Set to <code>true</code> if the portlet can be displayed via Ajax.
	 *
	 * @param ajaxable boolean value for whether the portlet can be displayed
	 *        via Ajax
	 */
	public void setAjaxable(boolean ajaxable) {
		_ajaxable = ajaxable;
	}

	/**
	 * Returns a list of CSS files that will be referenced from the page's
	 * header relative to the portal's context path.
	 *
	 * @return a list of CSS files that will be referenced from the page's
	 *         header relative to the portal's context path
	 */
	public List<String> getHeaderPortalCss() {
		return _headerPortalCss;
	}

	/**
	 * Sets a list of CSS files that will be referenced from the page's header
	 * relative to the portal's context path.
	 *
	 * @param headerPortalCss a list of CSS files that will be referenced from
	 *        the page's header relative to the portal's context path
	 */
	public void setHeaderPortalCss(List<String> headerPortalCss) {
		_headerPortalCss = headerPortalCss;
	}

	/**
	 * Returns a list of CSS files that will be referenced from the page's
	 * header relative to the portlet's context path.
	 *
	 * @return a list of CSS files that will be referenced from the page's
	 *         header relative to the portlet's context path
	 */
	public List<String> getHeaderPortletCss() {
		return _headerPortletCss;
	}

	/**
	 * Sets a list of CSS files that will be referenced from the page's header
	 * relative to the portlet's context path.
	 *
	 * @param headerPortletCss a list of CSS files that will be referenced from
	 *        the page's header relative to the portlet's context path
	 */
	public void setHeaderPortletCss(List<String> headerPortletCss) {
		_headerPortletCss = headerPortletCss;
	}

	/**
	 * Returns a list of JavaScript files that will be referenced from the
	 * page's header relative to the portal's context path.
	 *
	 * @return a list of JavaScript files that will be referenced from the
	 *         page's header relative to the portal's context path
	 */
	public List<String> getHeaderPortalJavaScript() {
		return _headerPortalJavaScript;
	}

	/**
	 * Sets a list of JavaScript files that will be referenced from the page's
	 * header relative to the portal's context path.
	 *
	 * @param headerPortalJavaScript a list of JavaScript files that will be
	 *        referenced from the page's header relative to the portal's context
	 *        path
	 */
	public void setHeaderPortalJavaScript(List<String> headerPortalJavaScript) {
		_headerPortalJavaScript = headerPortalJavaScript;
	}

	/**
	 * Returns a list of JavaScript files that will be referenced from the
	 * page's header relative to the portlet's context path.
	 *
	 * @return a list of JavaScript files that will be referenced from the
	 *         page's header relative to the portlet's context path
	 */
	public List<String> getHeaderPortletJavaScript() {
		return _headerPortletJavaScript;
	}

	/**
	 * Sets a list of JavaScript files that will be referenced from the page's
	 * header relative to the portlet's context path.
	 *
	 * @param headerPortletJavaScript a list of JavaScript files that will be
	 *        referenced from the page's header relative to the portlet's
	 *        context path
	 */
	public void setHeaderPortletJavaScript(
		List<String> headerPortletJavaScript) {

		_headerPortletJavaScript = headerPortletJavaScript;
	}

	/**
	 * Returns a list of CSS files that will be referenced from the page's
	 * footer relative to the portal's context path.
	 *
	 * @return a list of CSS files that will be referenced from the page's
	 *         footer relative to the portal's context path
	 */
	public List<String> getFooterPortalCss() {
		return _footerPortalCss;
	}

	/**
	 * Sets a list of CSS files that will be referenced from the page's footer
	 * relative to the portal's context path.
	 *
	 * @param footerPortalCss a list of CSS files that will be referenced from
	 *        the page's footer relative to the portal's context path
	 */
	public void setFooterPortalCss(List<String> footerPortalCss) {
		_footerPortalCss = footerPortalCss;
	}

	/**
	 * Returns a list of CSS files that will be referenced from the page's
	 * footer relative to the portlet's context path.
	 *
	 * @return a list of CSS files that will be referenced from the page's
	 *         footer relative to the portlet's context path
	 */
	public List<String> getFooterPortletCss() {
		return _footerPortletCss;
	}

	/**
	 * Sets a list of CSS files that will be referenced from the page's footer
	 * relative to the portlet's context path.
	 *
	 * @param footerPortletCss a list of CSS files that will be referenced from
	 *        the page's footer relative to the portlet's context path
	 */
	public void setFooterPortletCss(List<String> footerPortletCss) {
		_footerPortletCss = footerPortletCss;
	}

	/**
	 * Returns a list of JavaScript files that will be referenced from the
	 * page's footer relative to the portal's context path.
	 *
	 * @return a list of JavaScript files that will be referenced from the
	 *         page's footer relative to the portal's context path
	 */
	public List<String> getFooterPortalJavaScript() {
		return _footerPortalJavaScript;
	}

	/**
	 * Sets a list of JavaScript files that will be referenced from the page's
	 * footer relative to the portal's context path.
	 *
	 * @param footerPortalJavaScript a list of JavaScript files that will be
	 *        referenced from the page's footer relative to the portal's context
	 *        path
	 */
	public void setFooterPortalJavaScript(List<String> footerPortalJavaScript) {
		_footerPortalJavaScript = footerPortalJavaScript;
	}

	/**
	 * Returns a list of JavaScript files that will be referenced from the
	 * page's footer relative to the portlet's context path.
	 *
	 * @return a list of JavaScript files that will be referenced from the
	 *         page's footer relative to the portlet's context path
	 */
	public List<String> getFooterPortletJavaScript() {
		return _footerPortletJavaScript;
	}

	/**
	 * Sets a list of JavaScript files that will be referenced from the page's
	 * footer relative to the portlet's context path.
	 *
	 * @param footerPortletJavaScript a list of JavaScript files that will be
	 *        referenced from the page's footer relative to the portlet's
	 *        context path
	 */
	public void setFooterPortletJavaScript(
		List<String> footerPortletJavaScript) {

		_footerPortletJavaScript = footerPortletJavaScript;
	}

	/**
	 * Returns the name of the CSS class that will be injected in the DIV that
	 * wraps this portlet.
	 *
	 * @return the name of the CSS class that will be injected in the DIV that
	 *         wraps this portlet
	 */
	public String getCssClassWrapper() {
		return _cssClassWrapper;
	}

	/**
	 * Sets the name of the CSS class that will be injected in the DIV that
	 * wraps this portlet.
	 *
	 * @param cssClassWrapper the name of the CSS class that will be injected in
	 *        the DIV that wraps this portlet
	 */
	public void setCssClassWrapper(String cssClassWrapper) {
		_cssClassWrapper = cssClassWrapper;
	}

	/**
	 * Returns the Facebook integration method of the portlet.
	 *
	 * @return the Facebook integration method of the portlet
	 */
	public String getFacebookIntegration() {
		return _facebookIntegration;
	}

	/**
	 * Sets the Facebook integration method of the portlet.
	 *
	 * @param facebookIntegration the Facebook integration method of the portlet
	 */
	public void setFacebookIntegration(String facebookIntegration) {
		if (Validator.isNotNull(facebookIntegration)) {
			_facebookIntegration = facebookIntegration;
		}
	}

	/**
	 * Returns <code>true</code> if default resources for the portlet are added
	 * to a page.
	 *
	 * @return <code>true</code> if default resources for the portlet are added
	 *         to a page
	 */
	public boolean getAddDefaultResource() {
		return _addDefaultResource;
	}

	/**
	 * Returns <code>true</code> if default resources for the portlet are added
	 * to a page.
	 *
	 * @return <code>true</code> if default resources for the portlet are added
	 *         to a page
	 */
	public boolean isAddDefaultResource() {
		return _addDefaultResource;
	}

	/**
	 * Set to <code>true</code> if default resources for the portlet are added
	 * to a page.
	 *
	 * @param addDefaultResource boolean value for whether or not default
	 *        resources for the portlet are added to a page
	 */
	public void setAddDefaultResource(boolean addDefaultResource) {
		_addDefaultResource = addDefaultResource;
	}

	/**
	 * Sets a string of ordered comma delimited portlet IDs.
	 *
	 * @param roles a string of ordered comma delimited portlet IDs
	 */
	@Override
	public void setRoles(String roles) {
		_rolesArray = StringUtil.split(roles);

		super.setRoles(roles);
	}

	/**
	 * Returns an array of required roles of the portlet.
	 *
	 * @return an array of required roles of the portlet
	 */
	public String[] getRolesArray() {
		return _rolesArray;
	}

	/**
	 * Sets an array of required roles of the portlet.
	 *
	 * @param rolesArray an array of required roles of the portlet
	 */
	public void setRolesArray(String[] rolesArray) {
		_rolesArray = rolesArray;

		super.setRoles(StringUtil.merge(rolesArray));
	}

	/**
	 * Returns the unlinked roles of the portlet.
	 *
	 * @return unlinked roles of the portlet
	 */
	public Set<String> getUnlinkedRoles() {
		return _unlinkedRoles;
	}

	/**
	 * Sets the unlinked roles of the portlet.
	 *
	 * @param unlinkedRoles the unlinked roles of the portlet
	 */
	public void setUnlinkedRoles(Set<String> unlinkedRoles) {
		_unlinkedRoles = unlinkedRoles;
	}

	/**
	 * Returns the role mappers of the portlet.
	 *
	 * @return role mappers of the portlet
	 */
	public Map<String, String> getRoleMappers() {
		return _roleMappers;
	}

	/**
	 * Sets the role mappers of the portlet.
	 *
	 * @param roleMappers the role mappers of the portlet
	 */
	public void setRoleMappers(Map<String, String> roleMappers) {
		_roleMappers = roleMappers;
	}

	/**
	 * Link the role names set in portlet.xml with the Liferay roles set in
	 * liferay-portlet.xml.
	 */
	public void linkRoles() {
		List<String> linkedRoles = new ArrayList<String>();

		Iterator<String> itr = _unlinkedRoles.iterator();

		while (itr.hasNext()) {
			String unlinkedRole = itr.next();

			String roleLink = _roleMappers.get(unlinkedRole);

			if (Validator.isNotNull(roleLink)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Linking role for portlet [" + getPortletId() +
							"] with role-name [" + unlinkedRole +
								"] to role-link [" + roleLink + "]");
				}

				linkedRoles.add(roleLink);
			}
			else {
				_log.error(
					"Unable to link role for portlet [" + getPortletId() +
						"] with role-name [" + unlinkedRole +
							"] because role-link is null");
			}
		}

		String[] array = linkedRoles.toArray(new String[linkedRoles.size()]);

		Arrays.sort(array);

		setRolesArray(array);
	}

	/**
	 * Returns <code>true</code> if the portlet has a role with the specified
	 * name.
	 *
	 * @return <code>true</code> if the portlet has a role with the specified
	 *         name
	 */
	public boolean hasRoleWithName(String roleName) {
		if ((_rolesArray == null) || (_rolesArray.length == 0)) {
			return false;
		}

		for (int i = 0; i < _rolesArray.length; i++) {
			if (_rolesArray[i].equalsIgnoreCase(roleName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the user has the permission to add the
	 * portlet to a layout.
	 *
	 * @return <code>true</code> if the user has the permission to add the
	 *         portlet to a layout
	 */
	public boolean hasAddPortletPermission(long userId) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if ((permissionChecker == null) ||
				(permissionChecker.getUserId() != userId)) {

				User user = UserLocalServiceUtil.getUser(userId);

				permissionChecker = PermissionCheckerFactoryUtil.create(
					user, true);
			}

			if (PortletPermissionUtil.contains(
					permissionChecker, getRootPortletId(),
					ActionKeys.ADD_TO_PAGE)) {

				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the portlet is a system portlet that a user
	 * cannot manually add to their page.
	 *
	 * @return <code>true</code> if the portlet is a system portlet that a user
	 *         cannot manually add to their page
	 */
	public boolean getSystem() {
		return _system;
	}

	/**
	 * Returns <code>true</code> if the portlet is a system portlet that a user
	 * cannot manually add to their page.
	 *
	 * @return <code>true</code> if the portlet is a system portlet that a user
	 *         cannot manually add to their page
	 */
	public boolean isSystem() {
		return _system;
	}

	/**
	 * Set to <code>true</code> if the portlet is a system portlet that a user
	 * cannot manually add to their page.
	 *
	 * @param system boolean value for whether the portlet is a system portlet
	 *        that a user cannot manually add to their page
	 */
	public void setSystem(boolean system) {
		_system = system;
	}

	/**
	 * Returns <code>true</code> to include the portlet and make it available to
	 * be made active.
	 *
	 * @return <code>true</code> to include the portlet and make it available to
	 *         be made active
	 */
	public boolean getInclude() {
		return _include;
	}

	/**
	 * Returns <code>true</code> to include the portlet and make it available to
	 * be made active.
	 *
	 * @return <code>true</code> to include the portlet and make it available to
	 *         be made active
	 */
	public boolean isInclude() {
		return _include;
	}

	/**
	 * Set to <code>true</code> to include the portlet and make it available to
	 * be made active.
	 *
	 * @param include boolean value for whether to include the portlet and make
	 *        it available to be made active
	 */
	public void setInclude(boolean include) {
		_include = include;
	}

	/**
	 * Returns <code>true</code> if the portlet is ready to be used.
	 *
	 * @return <code>true</code> if the portlet is ready to be used
	 */
	public boolean getReady() {
		return isReady();
	}

	/**
	 * Returns <code>true</code> if the portlet is ready to be used.
	 *
	 * @return <code>true</code> if the portlet is ready to be used
	 */
	public boolean isReady() {
		Boolean ready = _readyMap.get(getRootPortletId());

		if (ready == null) {
			return true;
		}
		else {
			return ready;
		}
	}

	/**
	 * Set to <code>true</code> if the portlet is ready to be used.
	 *
	 * @param ready whether the portlet is ready to be used
	 */
	public void setReady(boolean ready) {
		_readyMap.put(getRootPortletId(), ready);
	}

	/**
	 * Returns the init parameters of the portlet.
	 *
	 * @return init parameters of the portlet
	 */
	public Map<String, String> getInitParams() {
		return _initParams;
	}

	/**
	 * Sets the init parameters of the portlet.
	 *
	 * @param initParams the init parameters of the portlet
	 */
	public void setInitParams(Map<String, String> initParams) {
		_initParams = initParams;
	}

	/**
	 * Returns expiration cache of the portlet.
	 *
	 * @return expiration cache of the portlet
	 */
	public Integer getExpCache() {
		return _expCache;
	}

	/**
	 * Sets expiration cache of the portlet.
	 *
	 * @param expCache expiration cache of the portlet
	 */
	public void setExpCache(Integer expCache) {
		_expCache = expCache;
	}

	/**
	 * Returns the portlet modes of the portlet.
	 *
	 * @return portlet modes of the portlet
	 */
	public Map<String, Set<String>> getPortletModes() {
		return _portletModes;
	}

	/**
	 * Sets the portlet modes of the portlet.
	 *
	 * @param portletModes the portlet modes of the portlet
	 */
	public void setPortletModes(Map<String, Set<String>> portletModes) {
		_portletModes = portletModes;
	}

	/**
	 * Returns <code>true</code> if the portlet supports the specified mime type
	 * and portlet mode.
	 *
	 * @return <code>true</code> if the portlet supports the specified mime type
	 *         and portlet mode
	 */
	public boolean hasPortletMode(String mimeType, PortletMode portletMode) {
		if (mimeType == null) {
			mimeType = ContentTypes.TEXT_HTML;
		}

		Set<String> mimeTypePortletModes = _portletModes.get(mimeType);

		if (mimeTypePortletModes == null) {
			return false;
		}

		if (mimeTypePortletModes.contains(portletMode.toString())) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns a list of all portlet modes supported by the portlet.
	 *
	 * @return a list of all portlet modes supported by the portlet
	 */
	public Set<String> getAllPortletModes() {
		Set<String> allPortletModes = new TreeSet<String>();

		Iterator<Map.Entry <String, Set<String>>> itr1 =
			_portletModes.entrySet().iterator();

		while (itr1.hasNext()) {
			Map.Entry<String, Set<String>> entry = itr1.next();

			Set<String> mimeTypePortletModes = entry.getValue();

			Iterator<String> itr2 = mimeTypePortletModes.iterator();

			while (itr2.hasNext()) {
				String portletMode = itr2.next();

				allPortletModes.add(portletMode);
			}
		}

		return allPortletModes;
	}

	/**
	 * Returns <code>true</code> if the portlet supports more than one mime
	 * type.
	 *
	 * @return <code>true</code> if the portlet supports more than one mime type
	 */
	public boolean hasMultipleMimeTypes() {
		if (_portletModes.size() > 1) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns the window states of the portlet.
	 *
	 * @return window states of the portlet
	 */
	public Map<String, Set<String>> getWindowStates() {
		return _windowStates;
	}

	/**
	 * Sets the window states of the portlet.
	 *
	 * @param windowStates the window states of the portlet
	 */
	public void setWindowStates(Map<String, Set<String>> windowStates) {
		_windowStates = windowStates;
	}

	/**
	 * Returns <code>true</code> if the portlet supports the specified mime type
	 * and window state.
	 *
	 * @return <code>true</code> if the portlet supports the specified mime type
	 *         and window state
	 */
	public boolean hasWindowState(String mimeType, WindowState windowState) {
		if (mimeType == null) {
			mimeType = ContentTypes.TEXT_HTML;
		}

		Set<String> mimeTypeWindowStates = _windowStates.get(mimeType);

		if (mimeTypeWindowStates == null) {
			return false;
		}

		if (mimeTypeWindowStates.contains(windowState.toString())) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns a list of all window states supported by the portlet.
	 *
	 * @return a list of all window states supported by the portlet
	 */
	public Set<String> getAllWindowStates() {
		Set<String> allWindowStates = new TreeSet<String>();

		Iterator<Map.Entry <String, Set<String>>> itr1 =
			_windowStates.entrySet().iterator();

		while (itr1.hasNext()) {
			Map.Entry<String, Set<String>> entry = itr1.next();

			Set<String> mimeTypeWindowStates = entry.getValue();

			Iterator<String> itr2 = mimeTypeWindowStates.iterator();

			while (itr2.hasNext()) {
				String windowState = itr2.next();

				allWindowStates.add(windowState);
			}
		}

		return allWindowStates;
	}

	/**
	 * Returns the supported locales of the portlet.
	 *
	 * @return supported locales of the portlet
	 */
	public Set<String> getSupportedLocales() {
		return _supportedLocales;
	}

	/**
	 * Sets the supported locales of the portlet.
	 *
	 * @param supportedLocales the supported locales of the portlet
	 */
	public void setSupportedLocales(Set<String> supportedLocales) {
		_supportedLocales = supportedLocales;
	}

	/**
	 * Returns the resource bundle of the portlet.
	 *
	 * @return resource bundle of the portlet
	 */
	public String getResourceBundle() {
		return _resourceBundle;
	}

	/**
	 * Sets the resource bundle of the portlet.
	 *
	 * @param resourceBundle the resource bundle of the portlet
	 */
	public void setResourceBundle(String resourceBundle) {
		_resourceBundle = resourceBundle;
	}

	/**
	 * Returns the portlet info of the portlet.
	 *
	 * @return portlet info of the portlet
	 */
	public PortletInfo getPortletInfo() {
		return _portletInfo;
	}

	/**
	 * Sets the portlet info of the portlet.
	 *
	 * @param portletInfo the portlet info of the portlet
	 */
	public void setPortletInfo(PortletInfo portletInfo) {
		_portletInfo = portletInfo;
	}

	/**
	 * Returns the filters of the portlet.
	 *
	 * @return filters of the portlet
	 */
	public Map<String, PortletFilter> getPortletFilters() {
		return _portletFilters;
	}

	/**
	 * Sets the filters of the portlet.
	 *
	 * @param portletFilters the filters of the portlet
	 */
	public void setPortletFilters(Map<String, PortletFilter> portletFilters) {
		_portletFilters = portletFilters;
	}

	/**
	 * Adds a supported processing event.
	 */
	public void addProcessingEvent(QName processingEvent) {
		_processingEvents.add(processingEvent);
		_processingEventsByQName.put(
			PortletQNameUtil.getKey(processingEvent), processingEvent);
	}

	/**
	 * Returns the supported processing event from a namespace URI and a local
	 * part.
	 *
	 * @return the supported processing event from a namespace URI and a local
	 *         part
	 */
	public QName getProcessingEvent(String uri, String localPart) {
		return _processingEventsByQName.get(
			PortletQNameUtil.getKey(uri, localPart));
	}

	/**
	 * Returns the supported processing events of the portlet.
	 *
	 * @return supported processing events of the portlet
	 */
	public Set<QName> getProcessingEvents() {
		return _processingEvents;
	}

	/**
	 * Sets the supported processing events of the portlet.
	 *
	 * @param processingEvents the supported processing events of the portlet
	 */
	public void setProcessingEvents(Set<QName> processingEvents) {
		for (QName processingEvent : processingEvents) {
			addProcessingEvent(processingEvent);
		}
	}

	/**
	 * Adds a supported publishing event.
	 */
	public void addPublishingEvent(QName publishingEvent) {
		_publishingEvents.add(publishingEvent);
	}

	/**
	 * Returns the supported publishing events of the portlet.
	 *
	 * @return supported publishing events of the portlet
	 */
	public Set<QName> getPublishingEvents() {
		return _publishingEvents;
	}

	/**
	 * Sets the supported publishing events of the portlet.
	 *
	 * @param publishingEvents the supported publishing events of the portlet
	 */
	public void setPublishingEvents(Set<QName> publishingEvents) {
		for (QName publishingEvent : publishingEvents) {
			addPublishingEvent(publishingEvent);
		}
	}

	/**
	 * Adds a supported public render parameter.
	 *
	 * @param publicRenderParameter a supported public render parameter
	 */
	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter) {

		_publicRenderParameters.add(publicRenderParameter);

		String identifier = publicRenderParameter.getIdentifier();

		_publicRenderParametersByIdentifier.put(
			identifier, publicRenderParameter);

		QName qName = publicRenderParameter.getQName();

		_publicRenderParametersByQName.put(
			PortletQNameUtil.getKey(qName), publicRenderParameter);

		String publicRenderParameterName =
			PortletQNameUtil.getPublicRenderParameterName(qName);

		PortletQNameUtil.setPublicRenderParameterIdentifier(
			publicRenderParameterName, identifier);
	}

	/**
	 * Returns the supported public render parameter from an identifier.
	 *
	 * @return the supported public render parameter from an identifier
	 */
	public PublicRenderParameter getPublicRenderParameter(String identifier) {
		return _publicRenderParametersByIdentifier.get(identifier);
	}

	/**
	 * Returns the supported public render parameter from a namespace URI and a
	 * local part.
	 *
	 * @return the supported public render parameter from a namespace URI and a
	 *         local part
	 */
	public PublicRenderParameter getPublicRenderParameter(
		String uri, String localPart) {

		return _publicRenderParametersByQName.get(
			PortletQNameUtil.getKey(uri, localPart));
	}

	/**
	 * Returns the supported public render parameters of the portlet.
	 *
	 * @return the supported public render parameters of the portlet
	 */
	public Set<PublicRenderParameter> getPublicRenderParameters() {
		return _publicRenderParameters;
	}

	/**
	 * Sets the supported public render parameters of the portlet.
	 *
	 * @param publicRenderParameters the supported public render parameters of
	 *        the portlet
	 */
	public void setPublicRenderParameters(
		Set<PublicRenderParameter> publicRenderParameters) {

		for (PublicRenderParameter publicRenderParameter :
				publicRenderParameters) {

			addPublicRenderParameter(publicRenderParameter);
		}
	}

	/**
	 * Returns the servlet context path of the portlet.
	 *
	 * @return the servlet context path of the portlet
	 */
	public String getContextPath() {
		if (!_portletApp.isWARFile()) {
			return PortalUtil.getPathContext();
		}

		String servletContextName = _portletApp.getServletContextName();

		if (ServletContextPool.containsKey(servletContextName)) {
			ServletContext servletContext = ServletContextPool.get(
				servletContextName);

			return servletContext.getContextPath();
		}

		return StringPool.SLASH.concat(servletContextName);
	}

	/**
	 * Returns the path for static resources served by this portlet.
	 *
	 * @return the path for static resources served by this portlet
	 */
	public String getStaticResourcePath() {
		String proxyPath = PortalUtil.getPathProxy();

		String virtualPath = getVirtualPath();

		if (Validator.isNotNull(virtualPath)) {
			return proxyPath.concat(virtualPath);
		}

		String contextPath = getContextPath();

		if (!_portletApp.isWARFile()) {
			return contextPath;
		}

		return proxyPath.concat(contextPath);
	}

	/**
	 * Returns this portlet's application.
	 *
	 * @return this portlet's application
	 */
	public PortletApp getPortletApp() {
		return _portletApp;
	}

	/**
	 * Sets this portlet's application.
	 *
	 * @param portletApp this portlet's application
	 */
	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;

		_portletApp.addPortlet(this);
	}

	/**
	 * Returns <code>true</code> if the portlet is found in a WAR file.
	 *
	 * @param  portletId the cloned instance portlet ID
	 * @return a cloned instance of the portlet
	 */
	public Portlet getClonedInstance(String portletId) {
		Portlet portlet = (Portlet)clone();

		portlet.setPortletId(portletId);

		return portlet;
	}

	/**
	 * Returns <code>true</code> if the portlet is a static portlet that is
	 * cannot be moved.
	 *
	 * @return <code>true</code> if the portlet is a static portlet that is
	 *         cannot be moved
	 */
	public boolean getStatic() {
		return _staticPortlet;
	}

	/**
	 * Returns <code>true</code> if the portlet is a static portlet that is
	 * cannot be moved.
	 *
	 * @return <code>true</code> if the portlet is a static portlet that is
	 *         cannot be moved
	 */
	public boolean isStatic() {
		return _staticPortlet;
	}

	/**
	 * Set to <code>true</code> if the portlet is a static portlet that is
	 * cannot be moved.
	 *
	 * @param staticPortlet boolean value for whether the portlet is a static
	 *        portlet that cannot be moved
	 */
	public void setStatic(boolean staticPortlet) {
		_staticPortlet = staticPortlet;
	}

	/**
	 * Returns <code>true</code> if the portlet is a static portlet at the start
	 * of a list of portlets.
	 *
	 * @return <code>true</code> if the portlet is a static portlet at the start
	 *         of a list of portlets
	 */
	public boolean getStaticStart() {
		return _staticPortletStart;
	}

	/**
	 * Returns <code>true</code> if the portlet is a static portlet at the start
	 * of a list of portlets.
	 *
	 * @return <code>true</code> if the portlet is a static portlet at the start
	 *         of a list of portlets
	 */
	public boolean isStaticStart() {
		return _staticPortletStart;
	}

	/**
	 * Set to <code>true</code> if the portlet is a static portlet at the start
	 * of a list of portlets.
	 *
	 * @param staticPortletStart boolean value for whether the portlet is a
	 *        static portlet at the start of a list of portlets
	 */
	public void setStaticStart(boolean staticPortletStart) {
		_staticPortletStart = staticPortletStart;
	}

	/**
	 * Returns <code>true</code> if the portlet is a static portlet at the end
	 * of a list of portlets.
	 *
	 * @return <code>true</code> if the portlet is a static portlet at the end
	 *         of a list of portlets
	 */
	public boolean getStaticEnd() {
		return !_staticPortletStart;
	}

	/**
	 * Returns <code>true</code> if the portlet is a static portlet at the end
	 * of a list of portlets.
	 *
	 * @return <code>true</code> if the portlet is a static portlet at the end
	 *         of a list of portlets
	 */
	public boolean isStaticEnd() {
		return !_staticPortletStart;
	}

	/**
	 * Returns <code>true</code> if the portlet is an undeployed portlet.
	 *
	 * @return <code>true</code> if the portlet is a placeholder of an
	 *         undeployed portlet
	 */
	public boolean getUndeployedPortlet() {
		return _undeployedPortlet;
	}

	/**
	 * Returns <code>true</code> if the portlet is an undeployed portlet.
	 *
	 * @return <code>true</code> if the portlet is a placeholder of an
	 *         undeployed portlet
	 */
	public boolean isUndeployedPortlet() {
		return _undeployedPortlet;
	}

	/**
	 * Set to <code>true</code> if the portlet is an undeployed portlet.
	 *
	 * @param undeployedPortlet boolean value for whether the portlet is an
	 *        undeployed portlet
	 */
	public void setUndeployedPortlet(boolean undeployedPortlet) {
		_undeployedPortlet = undeployedPortlet;
	}

	/**
	 * Creates and returns a copy of this object.
	 *
	 * @return a copy of this object
	 */
	@Override
	public Object clone() {
		Portlet portlet = new PortletImpl(
			getPortletId(), getRootPortlet(), getPluginPackage(),
			getDefaultPluginSetting(), getCompanyId(), getTimestamp(),
			getIcon(), getVirtualPath(), getStrutsPath(), getParentStrutsPath(),
			getPortletName(), getDisplayName(), getPortletClass(),
			getConfigurationActionClass(), getIndexerClasses(),
			getOpenSearchClass(), getSchedulerEntries(), getPortletURLClass(),
			getFriendlyURLMapperClass(), getFriendlyURLMapping(),
			getFriendlyURLRoutes(), getURLEncoderClass(),
			getPortletDataHandlerClass(), getPortletLayoutListenerClass(),
			getPollerProcessorClass(), getPopMessageListenerClass(),
			getSocialActivityInterpreterClass(),
			getSocialRequestInterpreterClass(), getWebDAVStorageToken(),
			getWebDAVStorageClass(), getXmlRpcMethodClass(),
			getControlPanelEntryCategory(), getControlPanelEntryWeight(),
			getControlPanelEntryClass(), getAssetRendererFactoryClasses(),
			getAtomCollectionAdapterClasses(),
			getCustomAttributesDisplayClasses(), getPermissionPropagatorClass(),
			getWorkflowHandlerClasses(), getDefaultPreferences(),
			getPreferencesValidator(), isPreferencesCompanyWide(),
			isPreferencesUniquePerLayout(), isPreferencesOwnedByGroup(),
			isUseDefaultTemplate(), isShowPortletAccessDenied(),
			isShowPortletInactive(), isActionURLRedirect(),
			isRestoreCurrentView(), isMaximizeEdit(), isMaximizeHelp(),
			isPopUpPrint(), isLayoutCacheable(), isInstanceable(),
			isRemoteable(), isScopeable(), getUserPrincipalStrategy(),
			isPrivateRequestAttributes(), isPrivateSessionAttributes(),
			getAutopropagatedParameters(), getActionTimeout(),
			getRenderTimeout(), getRenderWeight(), isAjaxable(),
			getHeaderPortalCss(), getHeaderPortletCss(),
			getHeaderPortalJavaScript(), getHeaderPortletJavaScript(),
			getFooterPortalCss(), getFooterPortletCss(),
			getFooterPortalJavaScript(), getFooterPortletJavaScript(),
			getCssClassWrapper(), getFacebookIntegration(),
			isAddDefaultResource(), getRoles(), getUnlinkedRoles(),
			getRoleMappers(), isSystem(), isActive(), isInclude(),
			getInitParams(), getExpCache(), getPortletModes(),
			getWindowStates(), getSupportedLocales(), getResourceBundle(),
			getPortletInfo(), getPortletFilters(), getProcessingEvents(),
			getPublishingEvents(), getPublicRenderParameters(),
			getPortletApp());

		portlet.setId(getId());
		portlet.setUndeployedPortlet(isUndeployedPortlet());

		return portlet;
	}

	/**
	 * Compares this portlet to the specified object.
	 *
	 * @param  portlet the portlet to compare this portlet against
	 * @return the value 0 if the argument portlet is equal to this portlet; a
	 *         value less than -1 if this portlet is less than the portlet
	 *         argument; and 1 if this portlet is greater than the portlet
	 *         argument
	 */
	@Override
	public int compareTo(Portlet portlet) {
		return getPortletId().compareTo(portlet.getPortletId());
	}

	/**
	 * Checks whether this portlet is equal to the specified object.
	 *
	 * @param  obj the object to compare this portlet against
	 * @return <code>true</code> if the portlet is equal to the specified object
	 */
	@Override
	public boolean equals(Object obj) {
		Portlet portlet = (Portlet)obj;

		return getPortletId().equals(portlet.getPortletId());
	}

	/**
	 * Log instance for this class.
	 */
	private static Log _log = LogFactoryUtil.getLog(PortletImpl.class);

	/**
	 * Map of the ready states of all portlets keyed by their root portlet ID.
	 */
	private static Map<String, Boolean> _readyMap =
		new ConcurrentHashMap<String, Boolean>();

	/**
	 * The root portlet of this portlet instance.
	 */
	private Portlet _rootPortlet = this;

	/**
	 * Package to which this plugin belongs.
	 */
	private PluginPackage _pluginPackage;

	/**
	 * Plugin settings associated with the portlet.
	 */
	private PluginSetting _defaultPluginSetting;

	/**
	 * The timestamp of the portlet.
	 */
	private long _timestamp;

	/**
	 * The icon of the portlet.
	 */
	private String _icon;

	/**
	 * The virtual path of the portlet.
	 */
	private String _virtualPath;

	/**
	 * The struts path of the portlet.
	 */
	private String _strutsPath;

	/**
	 * The parent struts path of the portlet.
	 */
	private String _parentStrutsPath;

	/**
	 * The name of the portlet.
	 */
	private String _portletName;

	/**
	 * The display name of the portlet.
	 */
	private String _displayName;

	/**
	 * The name of the portlet class of the portlet.
	 */
	private String _portletClass;

	/**
	 * The configuration action class of the portlet.
	 */
	private String _configurationActionClass;

	/**
	 * The name of the classes that represent indexers associated with the
	 * portlet.
	 */
	private List<String> _indexerClasses;

	/**
	 * The name of the open search class of the portlet.
	 */
	private String _openSearchClass;

	/**
	 * The scheduler entries of the portlet.
	 */
	private List<SchedulerEntry> _schedulerEntries;

	/**
	 * The name of the portlet URL class of the portlet.
	 */
	private String _portletURLClass;

	/**
	 * The name of the friendly URL mapper class of the portlet.
	 */
	private String _friendlyURLMapperClass;

	/**
	 * The name of the friendly URL mapping of the portlet.
	 */
	private String _friendlyURLMapping;

	/**
	 * The the class loader resource path to the friendly URL routes of the
	 * portlet.
	 */
	private String _friendlyURLRoutes;

	/**
	 * The name of the URL encoder class of the portlet.
	 */
	private String _urlEncoderClass;

	/**
	 * The name of the portlet data handler class of the portlet.
	 */
	private String _portletDataHandlerClass;

	/**
	 * The name of the portlet data layout listener class of the portlet.
	 */
	private String _portletLayoutListenerClass;

	/**
	 * The name of the poller processor class of the portlet.
	 */
	private String _pollerProcessorClass;

	/**
	 * The name of the POP message listener class of the portlet.
	 */
	private String _popMessageListenerClass;

	/**
	 * The name of the social activity interpreter class of the portlet.
	 */
	private String _socialActivityInterpreterClass;

	/**
	 * The name of the social request interpreter class of the portlet.
	 */
	private String _socialRequestInterpreterClass;

	/**
	 * The name of the WebDAV storage token of the portlet.
	 */
	private String _webDAVStorageToken;

	/**
	 * The name of the WebDAV storage class of the portlet.
	 */
	private String _webDAVStorageClass;

	/**
	 * The name of the XML-RPC method class of the portlet.
	 */
	private String _xmlRpcMethodClass;

	/**
	 * The default preferences of the portlet.
	 */
	private String _defaultPreferences;

	/**
	 * The name of the preferences validator class of the portlet.
	 */
	private String _preferencesValidator;

	/**
	 * <code>True</code> if preferences are shared across the entire company.
	 */
	private boolean _preferencesCompanyWide;

	/**
	 * <code>True</code> if preferences are unique per layout.
	 */
	private boolean _preferencesUniquePerLayout = true;

	/**
	 * <code>True</code> if preferences are owned by the group when the portlet
	 * is shown in a group layout. <code>False</code> if preferences are owned
	 * by the user at all times.
	 */
	private boolean _preferencesOwnedByGroup = true;

	/**
	 * The name of the category of the Control Panel where this portlet will be
	 * shown.
	 */
	private String _controlPanelEntryCategory;

	/**
	 * The relative weight of this portlet with respect to the other portlets in
	 * the same category of the Control Panel.
	 */
	private double _controlPanelEntryWeight = 100;

	/**
	 * The name of the class that will control when this portlet will be shown
	 * in the Control Panel.
	 */
	private String _controlPanelEntryClass;

	/**
	 * The names of the classes that represents asset types associated with the
	 * portlet.
	 */
	private List<String> _assetRendererFactoryClasses;

	/**
	 * The names of the classes that represents atom collection adapters
	 * associated with the portlet.
	 */
	private List<String> _atomCollectionAdapterClasses;

	/**
	 * The names of the classes that represents custom attribute displays
	 * associated with the portlet.
	 */
	private List<String> _customAttributesDisplayClasses;

	/**
	 * The name of the permission propagator class of the portlet.
	 */
	private String _permissionPropagatorClass;

	/**
	 * The names of the classes that represents workflow handlers associated
	 * with the portlet.
	 */
	private List<String> _workflowHandlerClasses;

	/**
	 * <code>True</code> if the portlet uses the default template.
	 */
	private boolean _useDefaultTemplate = true;

	/**
	 * <code>True</code> if users are shown that they do not have access to the
	 * portlet.
	 */
	private boolean _showPortletAccessDenied =
		PropsValues.LAYOUT_SHOW_PORTLET_ACCESS_DENIED;

	/**
	 * <code>True</code> if users are shown that the portlet is inactive.
	 */
	private boolean _showPortletInactive =
		PropsValues.LAYOUT_SHOW_PORTLET_INACTIVE;

	/**
	 * <code>True</code> if an action URL for this portlet should cause an auto
	 * redirect.
	 */
	private boolean _actionURLRedirect;

	/**
	 * <code>True</code> if the portlet restores to the current view from the
	 * maximized state.
	 */
	private boolean _restoreCurrentView = true;

	/**
	 * <code>True</code> if the portlet goes into the maximized state when the
	 * user goes into the edit mode.
	 */
	private boolean _maximizeEdit;

	/**
	 * <code>True</code> if the portlet goes into the maximized state when the
	 * user goes into the help mode.
	 */
	private boolean _maximizeHelp;

	/**
	 * <code>True</code> if the portlet goes into the pop up state when the user
	 * goes into the print mode.
	 */
	private boolean _popUpPrint = true;

	/**
	 * <code>True</code> if the portlet can be cached within the layout.
	 */
	private boolean _layoutCacheable;

	/**
	 * <code>True</code> if the portlet can be added multiple times to a layout.
	 */
	private boolean _instanceable;

	/**
	 * <code>True</code> if the portlet supports remoting.
	 */
	private boolean _remoteable;

	/**
	 * <code>True</code> if the portlet supports scoping of data.
	 */
	private boolean _scopeable;

	/**
	 * The user principal strategy of the portlet.
	 */
	private String _userPrincipalStrategy =
		PortletConstants.USER_PRINCIPAL_STRATEGY_USER_ID;

	/**
	 * <code>True</code> if the portlet does not share request attributes with
	 * the portal or portlets from another WAR.
	 */
	private boolean _privateRequestAttributes = true;

	/**
	 * <code>True</code> if the portlet does not share session attributes with
	 * the portal.
	 */
	private boolean _privateSessionAttributes = true;

	/**
	 * The names of the parameters that will be automatically propagated through
	 * the portlet.
	 */
	private Set<String> _autopropagatedParameters;

	/**
	 * The action timeout of the portlet.
	 */
	private int _actionTimeout;

	/**
	 * The render timeout of the portlet.
	 */
	private int _renderTimeout;

	/**
	 * Render weight of the portlet.
	 */
	private int _renderWeight = 1;

	/**
	 * <code>True</code> if the portlet can be displayed via Ajax.
	 */
	private boolean _ajaxable = true;

	/**
	 * A list of CSS files that will be referenced from the page's header
	 * relative to the portal's context path.
	 */
	private List<String> _headerPortalCss;

	/**
	 * A list of CSS files that will be referenced from the page's header
	 * relative to the portlet's context path.
	 */
	private List<String> _headerPortletCss;

	/**
	 * A list of JavaScript files that will be referenced from the page's header
	 * relative to the portal's context path.
	 */
	private List<String> _headerPortalJavaScript;

	/**
	 * A list of JavaScript files that will be referenced from the page's header
	 * relative to the portlet's context path.
	 */
	private List<String> _headerPortletJavaScript;

	/**
	 * A list of CSS files that will be referenced from the page's footer
	 * relative to the portal's context path.
	 */
	private List<String> _footerPortalCss;

	/**
	 * A list of CSS files that will be referenced from the page's footer
	 * relative to the portlet's context path.
	 */
	private List<String> _footerPortletCss;

	/**
	 * A list of JavaScript files that will be referenced from the page's footer
	 * relative to the portal's context path.
	 */
	private List<String> _footerPortalJavaScript;

	/**
	 * A list of JavaScript files that will be referenced from the page's footer
	 * relative to the portlet's context path.
	 */
	private List<String> _footerPortletJavaScript;

	/**
	 * The name of the CSS class that will be injected in the DIV that wraps
	 * this portlet.
	 */
	private String _cssClassWrapper = StringPool.BLANK;

	/**
	 * The Facebook integration method of the portlet.
	 */
	private String _facebookIntegration =
		PortletConstants.FACEBOOK_INTEGRATION_IFRAME;

	/**
	 * <code>True</code> if default resources for the portlet are added to a
	 * page.
	 */
	private boolean _addDefaultResource;

	/**
	 * An array of required roles of the portlet.
	 */
	private String[] _rolesArray = new String[0];

	/**
	 * The unlinked roles of the portlet.
	 */
	private Set<String> _unlinkedRoles;

	/**
	 * The role mappers of the portlet.
	 */
	private Map<String, String> _roleMappers;

	/**
	 * <code>True</code> if the portlet is a system portlet that a user cannot
	 * manually add to their page.
	 */
	private boolean _system;

	/**
	 * <code>True</code> to include the portlet and make it available to be made
	 * active.
	 */
	private boolean _include = true;

	/**
	 * The init parameters of the portlet.
	 */
	private Map<String, String> _initParams;

	/**
	 * The expiration cache of the portlet.
	 */
	private Integer _expCache;

	/**
	 * The portlet modes of the portlet.
	 */
	private Map<String, Set<String>> _portletModes;

	/**
	 * The window states of the portlet.
	 */
	private Map<String, Set<String>> _windowStates;

	/**
	 * The supported locales of the portlet.
	 */
	private Set<String> _supportedLocales;

	/**
	 * The resource bundle of the portlet.
	 */
	private String _resourceBundle;

	/**
	 * The portlet info of the portlet.
	 */
	private PortletInfo _portletInfo;

	/**
	 * The filters of the portlet.
	 */
	private Map<String, PortletFilter> _portletFilters;

	/**
	 * The supported processing events of the portlet.
	 */
	private Set<QName> _processingEvents = new HashSet<QName>();

	/**
	 * Map of the supported processing events of the portlet keyed by the QName.
	 */
	private Map<String, QName> _processingEventsByQName =
		new HashMap<String, QName>();

	/**
	 * The supported publishing events of the portlet.
	 */
	private Set<QName> _publishingEvents = new HashSet<QName>();

	/**
	 * The supported public render parameters of the portlet.
	 */
	private Set<PublicRenderParameter> _publicRenderParameters =
		new HashSet<PublicRenderParameter>();

	/**
	 * Map of the supported public render parameters of the portlet keyed by the
	 * identifier.
	 */
	private Map<String, PublicRenderParameter>
		_publicRenderParametersByIdentifier =
			new HashMap<String, PublicRenderParameter>();

	/**
	 * Map of the supported public render parameters of the portlet keyed by the
	 * QName.
	 */
	private Map<String, PublicRenderParameter>
		_publicRenderParametersByQName =
			new HashMap<String, PublicRenderParameter>();

	/**
	 * The application to which this portlet belongs.
	 */
	private PortletApp _portletApp;

	/**
	 * <code>True</code> if the portlet is a static portlet that is cannot be
	 * moved.
	 */
	private boolean _staticPortlet;

	/**
	 * <code>True</code> if the portlet is a static portlet at the start of a
	 * list of portlets.
	 */
	private boolean _staticPortletStart;

	/**
	 * <code>True</code> if the portlet is an undeployed portlet.
	 */
	private boolean _undeployedPortlet = false;

}