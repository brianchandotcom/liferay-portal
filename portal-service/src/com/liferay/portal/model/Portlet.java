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

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Portlet service. Represents a row in the &quot;Portlet&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PortletModel
 * @see com.liferay.portal.model.impl.PortletImpl
 * @see com.liferay.portal.model.impl.PortletModelImpl
 * @generated
 */
public interface Portlet extends PortletModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.PortletImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Portlet, String> PORTLET_ID_ACCESSOR = new Accessor<Portlet, String>() {
			@Override
			public String get(Portlet portlet) {
				return portlet.getPortletId();
			}
		};

	/**
	* Adds a supported processing event.
	*/
	public void addProcessingEvent(
		com.liferay.portal.kernel.xml.QName processingEvent);

	/**
	* Adds a supported public render parameter.
	*
	* @param publicRenderParameter a supported public render parameter
	*/
	public void addPublicRenderParameter(
		com.liferay.portal.model.PublicRenderParameter publicRenderParameter);

	/**
	* Adds a supported publishing event.
	*/
	public void addPublishingEvent(
		com.liferay.portal.kernel.xml.QName publishingEvent);

	/**
	* Adds a scheduler entry.
	*/
	public void addSchedulerEntry(
		com.liferay.portal.kernel.scheduler.SchedulerEntry schedulerEntry);

	/**
	* Checks whether this portlet is equal to the specified object.
	*
	* @param obj the object to compare this portlet against
	* @return <code>true</code> if the portlet is equal to the specified object
	*/
	@Override
	public boolean equals(java.lang.Object obj);

	/**
	* Returns the action timeout of the portlet.
	*
	* @return the action timeout of the portlet
	*/
	public int getActionTimeout();

	/**
	* Returns <code>true</code> if an action URL for this portlet should cause
	* an auto redirect.
	*
	* @return <code>true</code> if an action URL for this portlet should cause
	an auto redirect
	*/
	public boolean getActionURLRedirect();

	/**
	* Returns <code>true</code> if default resources for the portlet are added
	* to a page.
	*
	* @return <code>true</code> if default resources for the portlet are added
	to a page
	*/
	public boolean getAddDefaultResource();

	/**
	* Returns <code>true</code> if the portlet can be displayed via Ajax.
	*
	* @return <code>true</code> if the portlet can be displayed via Ajax
	*/
	public boolean getAjaxable();

	/**
	* Returns a list of all portlet modes supported by the portlet.
	*
	* @return a list of all portlet modes supported by the portlet
	*/
	public java.util.Set<java.lang.String> getAllPortletModes();

	/**
	* Returns a list of all window states supported by the portlet.
	*
	* @return a list of all window states supported by the portlet
	*/
	public java.util.Set<java.lang.String> getAllWindowStates();

	/**
	* Returns the names of the classes that represent asset types associated
	* with the portlet.
	*
	* @return the names of the classes that represent asset types associated
	with the portlet
	*/
	public java.util.List<java.lang.String> getAssetRendererFactoryClasses();

	/**
	* Returns the asset type instances of the portlet.
	*
	* @return the asset type instances of the portlet
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetRendererFactory> getAssetRendererFactoryInstances();

	/**
	* Returns the names of the classes that represent atom collection adapters
	* associated with the portlet.
	*
	* @return the names of the classes that represent atom collection adapters
	associated with the portlet
	*/
	public java.util.List<java.lang.String> getAtomCollectionAdapterClasses();

	/**
	* Returns the atom collection adapter instances of the portlet.
	*
	* @return the atom collection adapter instances of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.atom.AtomCollectionAdapter<?>> getAtomCollectionAdapterInstances();

	/**
	* Returns the names of the parameters that will be automatically propagated
	* through the portlet.
	*
	* @return the names of the parameters that will be automatically propagated
	through the portlet
	*/
	public java.util.Set<java.lang.String> getAutopropagatedParameters();

	/**
	* Returns <code>true</code> if the portlet is found in a WAR file.
	*
	* @param portletId the cloned instance portlet ID
	* @return a cloned instance of the portlet
	*/
	public com.liferay.portal.model.Portlet getClonedInstance(
		java.lang.String portletId);

	/**
	* Returns the configuration action class of the portlet.
	*
	* @return the configuration action class of the portlet
	*/
	public java.lang.String getConfigurationActionClass();

	/**
	* Returns the configuration action instance of the portlet.
	*
	* @return the configuration action instance of the portlet
	*/
	public com.liferay.portal.kernel.portlet.ConfigurationAction getConfigurationActionInstance();

	/**
	* Returns the servlet context path of the portlet.
	*
	* @return the servlet context path of the portlet
	*/
	public java.lang.String getContextPath();

	/**
	* Returns the name of the category of the Control Panel where the portlet
	* will be shown.
	*
	* @return the name of the category of the Control Panel where the portlet
	will be shown
	*/
	public java.lang.String getControlPanelEntryCategory();

	/**
	* Returns the name of the class that will control when the portlet will be
	* shown in the Control Panel.
	*
	* @return the name of the class that will control when the portlet will be
	shown in the Control Panel
	*/
	public java.lang.String getControlPanelEntryClass();

	/**
	* Returns an instance of the class that will control when the portlet will
	* be shown in the Control Panel.
	*
	* @return the instance of the class that will control when the portlet will
	be shown in the Control Panel
	*/
	public com.liferay.portlet.ControlPanelEntry getControlPanelEntryInstance();

	/**
	* Returns the relative weight of the portlet with respect to the other
	* portlets in the same category of the Control Panel.
	*
	* @return the relative weight of the portlet with respect to the other
	portlets in the same category of the Control Panel
	*/
	public double getControlPanelEntryWeight();

	/**
	* Returns the name of the CSS class that will be injected in the DIV that
	* wraps this portlet.
	*
	* @return the name of the CSS class that will be injected in the DIV that
	wraps this portlet
	*/
	public java.lang.String getCssClassWrapper();

	/**
	* Returns the names of the classes that represent custom attribute displays
	* associated with the portlet.
	*
	* @return the names of the classes that represent asset types associated
	with the portlet
	*/
	public java.util.List<java.lang.String> getCustomAttributesDisplayClasses();

	/**
	* Returns the custom attribute display instances of the portlet.
	*
	* @return the custom attribute display instances of the portlet
	*/
	public java.util.List<com.liferay.portlet.expando.model.CustomAttributesDisplay> getCustomAttributesDisplayInstances();

	/**
	* Returns the name of the dynamic data mapping display class of the
	* portlet.
	*
	* @return the name of the dynamic data mapping display class of the portlet
	*/
	public java.lang.String getDDMDisplayClass();

	/**
	* Get the default plugin settings of the portlet.
	*
	* @return the plugin settings
	*/
	public com.liferay.portal.model.PluginSetting getDefaultPluginSetting();

	/**
	* Returns the default preferences of the portlet.
	*
	* @return the default preferences of the portlet
	*/
	public java.lang.String getDefaultPreferences();

	/**
	* Returns the display name of the portlet.
	*
	* @return the display name of the portlet
	*/
	public java.lang.String getDisplayName();

	/**
	* Returns expiration cache of the portlet.
	*
	* @return expiration cache of the portlet
	*/
	public java.lang.Integer getExpCache();

	/**
	* Returns the Facebook integration method of the portlet.
	*
	* @return the Facebook integration method of the portlet
	*/
	public java.lang.String getFacebookIntegration();

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* footer relative to the portal's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	footer relative to the portal's context path
	*/
	public java.util.List<java.lang.String> getFooterPortalCss();

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's footer relative to the portal's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's footer relative to the portal's context path
	*/
	public java.util.List<java.lang.String> getFooterPortalJavaScript();

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* footer relative to the portlet's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	footer relative to the portlet's context path
	*/
	public java.util.List<java.lang.String> getFooterPortletCss();

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's footer relative to the portlet's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's footer relative to the portlet's context path
	*/
	public java.util.List<java.lang.String> getFooterPortletJavaScript();

	/**
	* Returns the name of the friendly URL mapper class of the portlet.
	*
	* @return the name of the friendly URL mapper class of the portlet
	*/
	public java.lang.String getFriendlyURLMapperClass();

	/**
	* Returns the friendly URL mapper instance of the portlet.
	*
	* @return the friendly URL mapper instance of the portlet
	*/
	public com.liferay.portal.kernel.portlet.FriendlyURLMapper getFriendlyURLMapperInstance();

	/**
	* Returns the name of the friendly URL mapping of the portlet.
	*
	* @return the name of the friendly URL mapping of the portlet
	*/
	public java.lang.String getFriendlyURLMapping();

	/**
	* Returns the class loader resource path to the friendly URL routes of the
	* portlet.
	*
	* @return the class loader resource path to the friendly URL routes of the
	portlet
	*/
	public java.lang.String getFriendlyURLRoutes();

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* header relative to the portal's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	header relative to the portal's context path
	*/
	public java.util.List<java.lang.String> getHeaderPortalCss();

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's header relative to the portal's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's header relative to the portal's context path
	*/
	public java.util.List<java.lang.String> getHeaderPortalJavaScript();

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* header relative to the portlet's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	header relative to the portlet's context path
	*/
	public java.util.List<java.lang.String> getHeaderPortletCss();

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's header relative to the portlet's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's header relative to the portlet's context path
	*/
	public java.util.List<java.lang.String> getHeaderPortletJavaScript();

	/**
	* Returns the icon of the portlet.
	*
	* @return the icon of the portlet
	*/
	public java.lang.String getIcon();

	/**
	* Returns <code>true</code> to include the portlet and make it available to
	* be made active.
	*
	* @return <code>true</code> to include the portlet and make it available to
	be made active
	*/
	public boolean getInclude();

	/**
	* Returns the names of the classes that represent indexers associated with
	* the portlet.
	*
	* @return the names of the classes that represent indexers associated with
	the portlet
	*/
	public java.util.List<java.lang.String> getIndexerClasses();

	/**
	* Returns the indexer instances of the portlet.
	*
	* @return the indexer instances of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.search.Indexer> getIndexerInstances();

	/**
	* Returns the init parameters of the portlet.
	*
	* @return init parameters of the portlet
	*/
	public java.util.Map<java.lang.String, java.lang.String> getInitParams();

	/**
	* Returns <code>true</code> if the portlet can be added multiple times to a
	* layout.
	*
	* @return <code>true</code> if the portlet can be added multiple times to a
	layout
	*/
	public boolean getInstanceable();

	/**
	* Returns the instance ID of the portlet.
	*
	* @return the instance ID of the portlet
	*/
	public java.lang.String getInstanceId();

	/**
	* Returns <code>true</code> to allow the portlet to be cached within the
	* layout.
	*
	* @return <code>true</code> if the portlet can be cached within the layout
	*/
	public boolean getLayoutCacheable();

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the edit mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the edit mode
	*/
	public boolean getMaximizeEdit();

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the help mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the help mode
	*/
	public boolean getMaximizeHelp();

	/**
	* Returns the name of the open search class of the portlet.
	*
	* @return the name of the open search class of the portlet
	*/
	public java.lang.String getOpenSearchClass();

	/**
	* Returns the indexer instance of the portlet.
	*
	* @return the indexer instance of the portlet
	*/
	public com.liferay.portal.kernel.search.OpenSearch getOpenSearchInstance();

	/**
	* Returns the parent struts path of the portlet.
	*
	* @return the parent struts path of the portlet.
	*/
	public java.lang.String getParentStrutsPath();

	/**
	* Returns the name of the permission propagator class of the portlet.
	*
	* @return the name of the permission propagator class of the portlet
	*/
	public java.lang.String getPermissionPropagatorClass();

	/**
	* Returns the permission propagator instance of the portlet.
	*
	* @return the permission propagator instance of the portlet
	*/
	public com.liferay.portal.security.permission.PermissionPropagator getPermissionPropagatorInstance();

	/**
	* Returns the plugin ID of the portlet.
	*
	* @return the plugin ID of the portlet
	*/
	public java.lang.String getPluginId();

	/**
	* Returns this portlet's plugin package.
	*
	* @return this portlet's plugin package
	*/
	public com.liferay.portal.kernel.plugin.PluginPackage getPluginPackage();

	/**
	* Returns the plugin type of the portlet.
	*
	* @return the plugin type of the portlet
	*/
	public java.lang.String getPluginType();

	/**
	* Returns the name of the poller processor class of the portlet.
	*
	* @return the name of the poller processor class of the portlet
	*/
	public java.lang.String getPollerProcessorClass();

	/**
	* Returns the poller processor instance of the portlet.
	*
	* @return the poller processor instance of the portlet
	*/
	public com.liferay.portal.kernel.poller.PollerProcessor getPollerProcessorInstance();

	/**
	* Returns the name of the POP message listener class of the portlet.
	*
	* @return the name of the POP message listener class of the portlet
	*/
	public java.lang.String getPopMessageListenerClass();

	/**
	* Returns the POP message listener instance of the portlet.
	*
	* @return the POP message listener instance of the portlet
	*/
	public com.liferay.portal.kernel.pop.MessageListener getPopMessageListenerInstance();

	/**
	* Returns <code>true</code> if the portlet goes into the pop up state when
	* the user goes into the print mode.
	*
	* @return <code>true</code> if the portlet goes into the pop up state when
	the user goes into the print mode
	*/
	public boolean getPopUpPrint();

	/**
	* Returns this portlet's application.
	*
	* @return this portlet's application
	*/
	public com.liferay.portal.model.PortletApp getPortletApp();

	/**
	* Returns the name of the portlet class of the portlet.
	*
	* @return the name of the portlet class of the portlet
	*/
	public java.lang.String getPortletClass();

	/**
	* Returns the name of the portlet data handler class of the portlet.
	*
	* @return the name of the portlet data handler class of the portlet
	*/
	public java.lang.String getPortletDataHandlerClass();

	/**
	* Returns the portlet data handler instance of the portlet.
	*
	* @return the portlet data handler instance of the portlet
	*/
	public com.liferay.portal.kernel.lar.PortletDataHandler getPortletDataHandlerInstance();

	/**
	* Returns the filters of the portlet.
	*
	* @return filters of the portlet
	*/
	public java.util.Map<java.lang.String, com.liferay.portal.model.PortletFilter> getPortletFilters();

	/**
	* Returns the portlet info of the portlet.
	*
	* @return portlet info of the portlet
	*/
	public com.liferay.portal.model.PortletInfo getPortletInfo();

	/**
	* Returns the name of the portlet layout listener class of the portlet.
	*
	* @return the name of the portlet layout listener class of the portlet
	*/
	public java.lang.String getPortletLayoutListenerClass();

	/**
	* Returns the portlet layout listener instance of the portlet.
	*
	* @return the portlet layout listener instance of the portlet
	*/
	public com.liferay.portal.kernel.portlet.PortletLayoutListener getPortletLayoutListenerInstance();

	/**
	* Returns the portlet modes of the portlet.
	*
	* @return portlet modes of the portlet
	*/
	public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getPortletModes();

	/**
	* Returns the name of the portlet.
	*
	* @return the display name of the portlet
	*/
	public java.lang.String getPortletName();

	/**
	* Returns the name of the portlet URL class of the portlet.
	*
	* @return the name of the portlet URL class of the portlet
	*/
	public java.lang.String getPortletURLClass();

	/**
	* Returns <code>true</code> if preferences are shared across the entire
	* company.
	*
	* @return <code>true</code> if preferences are shared across the entire
	company
	*/
	public boolean getPreferencesCompanyWide();

	/**
	* Returns <code>true</code> if preferences are owned by the group when the
	* portlet is shown in a group layout. Returns <code>false</code> if
	* preferences are owned by the user at all times.
	*
	* @return <code>true</code> if preferences are owned by the group when the
	portlet is shown in a group layout; <code>false</code> if
	preferences are owned by the user at all times.
	*/
	public boolean getPreferencesOwnedByGroup();

	/**
	* Returns <code>true</code> if preferences are unique per layout.
	*
	* @return <code>true</code> if preferences are unique per layout
	*/
	public boolean getPreferencesUniquePerLayout();

	/**
	* Returns the name of the preferences validator class of the portlet.
	*
	* @return the name of the preferences validator class of the portlet
	*/
	public java.lang.String getPreferencesValidator();

	/**
	* Returns <code>true</code> if the portlet does not share request
	* attributes with the portal or portlets from another WAR.
	*
	* @return <code>true</code> if the portlet does not share request
	attributes with the portal or portlets from another WAR
	*/
	public boolean getPrivateRequestAttributes();

	/**
	* Returns <code>true</code> if the portlet does not share session
	* attributes with the portal.
	*
	* @return <code>true</code> if the portlet does not share session
	attributes with the portal
	*/
	public boolean getPrivateSessionAttributes();

	/**
	* Returns the supported processing event from a namespace URI and a local
	* part.
	*
	* @param uri the namespace URI
	* @param localPart the local part
	* @return the supported processing event from a namespace URI and a local
	part
	*/
	public com.liferay.portal.kernel.xml.QName getProcessingEvent(
		java.lang.String uri, java.lang.String localPart);

	/**
	* Returns the supported processing events of the portlet.
	*
	* @return supported processing events of the portlet
	*/
	public java.util.Set<com.liferay.portal.kernel.xml.QName> getProcessingEvents();

	/**
	* Returns the supported public render parameter from an identifier.
	*
	* @param identifier the identifier
	* @return the supported public render parameter from an identifier
	*/
	public com.liferay.portal.model.PublicRenderParameter getPublicRenderParameter(
		java.lang.String identifier);

	/**
	* Returns the supported public render parameter from a namespace URI and a
	* local part.
	*
	* @param uri the namespace URI
	* @param localPart the local part
	* @return the supported public render parameter from a namespace URI and a
	local part
	*/
	public com.liferay.portal.model.PublicRenderParameter getPublicRenderParameter(
		java.lang.String uri, java.lang.String localPart);

	/**
	* Returns the supported public render parameters of the portlet.
	*
	* @return the supported public render parameters of the portlet
	*/
	public java.util.Set<com.liferay.portal.model.PublicRenderParameter> getPublicRenderParameters();

	/**
	* Returns the supported publishing events of the portlet.
	*
	* @return supported publishing events of the portlet
	*/
	public java.util.Set<com.liferay.portal.kernel.xml.QName> getPublishingEvents();

	/**
	* Returns <code>true</code> if the portlet is ready to be used.
	*
	* @return <code>true</code> if the portlet is ready to be used
	*/
	public boolean getReady();

	/**
	* Returns <code>true</code> if the portlet supports remoting.
	*
	* @return <code>true</code> if the portlet supports remoting
	*/
	public boolean getRemoteable();

	/**
	* Returns the render timeout of the portlet.
	*
	* @return the render timeout of the portlet
	*/
	public int getRenderTimeout();

	/**
	* Returns the render weight of the portlet.
	*
	* @return the render weight of the portlet
	*/
	public int getRenderWeight();

	/**
	* Returns the resource bundle of the portlet.
	*
	* @return resource bundle of the portlet
	*/
	public java.lang.String getResourceBundle();

	/**
	* Returns <code>true</code> if the portlet restores to the current view
	* from the maximized state.
	*
	* @return <code>true</code> if the portlet restores to the current view
	from the maximized state
	*/
	public boolean getRestoreCurrentView();

	/**
	* Returns the role mappers of the portlet.
	*
	* @return role mappers of the portlet
	*/
	public java.util.Map<java.lang.String, java.lang.String> getRoleMappers();

	/**
	* Returns an array of required roles of the portlet.
	*
	* @return an array of required roles of the portlet
	*/
	public java.lang.String[] getRolesArray();

	/**
	* Returns the root portlet of this portlet instance.
	*
	* @return the root portlet of this portlet instance
	*/
	public com.liferay.portal.model.Portlet getRootPortlet();

	/**
	* Returns the root portlet ID of the portlet.
	*
	* @return the root portlet ID of the portlet
	*/
	public java.lang.String getRootPortletId();

	/**
	* Returns the scheduler entries of the portlet.
	*
	* @return the scheduler entries of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> getSchedulerEntries();

	/**
	* Returns <code>true</code> if the portlet supports scoping of data.
	*
	* @return <code>true</code> if the portlet supports scoping of data
	*/
	public boolean getScopeable();

	/**
	* Returns <code>true</code> if users are shown that they do not have access
	* to the portlet.
	*
	* @return <code>true</code> if users are shown that they do not have access
	to the portlet
	*/
	public boolean getShowPortletAccessDenied();

	/**
	* Returns <code>true</code> if users are shown that the portlet is
	* inactive.
	*
	* @return <code>true</code> if users are shown that the portlet is inactive
	*/
	public boolean getShowPortletInactive();

	/**
	* Returns the names of the classes that represent social activity
	* interpreters associated with the portlet.
	*
	* @return the names of the classes that represent social activity
	interpreters associated with the portlet
	*/
	public java.util.List<java.lang.String> getSocialActivityInterpreterClasses();

	/**
	* Returns the social activity interpreter instances of the portlet.
	*
	* @return the social activity interpreter instances of the portlet
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityInterpreter> getSocialActivityInterpreterInstances();

	/**
	* Returns the name of the social request interpreter class of the portlet.
	*
	* @return the name of the social request interpreter class of the portlet
	*/
	public java.lang.String getSocialRequestInterpreterClass();

	/**
	* Returns the name of the social request interpreter instance of the
	* portlet.
	*
	* @return the name of the social request interpreter instance of the
	portlet
	*/
	public com.liferay.portlet.social.model.SocialRequestInterpreter getSocialRequestInterpreterInstance();

	/**
	* Returns the names of the classes that represent staged model data
	* handlers associated with the portlet.
	*
	* @return the names of the classes that represent staged model data
	handlers associated with the portlet
	*/
	public java.util.List<java.lang.String> getStagedModelDataHandlerClasses();

	/**
	* Returns the staged model data handler instances of the portlet.
	*
	* @return the staged model data handler instances of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.lar.StagedModelDataHandler<?>> getStagedModelDataHandlerInstances();

	/**
	* Returns <code>true</code> if the portlet is a static portlet that is
	* cannot be moved.
	*
	* @return <code>true</code> if the portlet is a static portlet that is
	cannot be moved
	*/
	public boolean getStatic();

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the end
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the end
	of a list of portlets
	*/
	public boolean getStaticEnd();

	/**
	* Returns the path for static resources served by this portlet.
	*
	* @return the path for static resources served by this portlet
	*/
	public java.lang.String getStaticResourcePath();

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the start
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the start
	of a list of portlets
	*/
	public boolean getStaticStart();

	/**
	* Returns the struts path of the portlet.
	*
	* @return the struts path of the portlet
	*/
	public java.lang.String getStrutsPath();

	/**
	* Returns the supported locales of the portlet.
	*
	* @return supported locales of the portlet
	*/
	public java.util.Set<java.lang.String> getSupportedLocales();

	/**
	* Returns <code>true</code> if the portlet is a system portlet that a user
	* cannot manually add to their page.
	*
	* @return <code>true</code> if the portlet is a system portlet that a user
	cannot manually add to their page
	*/
	public boolean getSystem();

	/**
	* Returns the name of the template handler class of the portlet.
	*
	* @return the name of the template handler class of the portlet
	*/
	public java.lang.String getTemplateHandlerClass();

	/**
	* Returns the template handler instance of the portlet.
	*
	* @return the template handler instance of the portlet
	*/
	public com.liferay.portal.kernel.template.TemplateHandler getTemplateHandlerInstance();

	/**
	* Returns the timestamp of the portlet.
	*
	* @return the timestamp of the portlet
	*/
	public long getTimestamp();

	/**
	* Returns the names of the classes that represent trash handlers associated
	* with the portlet.
	*
	* @return the names of the classes that represent trash handlers associated
	with the portlet
	*/
	public java.util.List<java.lang.String> getTrashHandlerClasses();

	/**
	* Returns the trash handler instances of the portlet.
	*
	* @return the trash handler instances of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.trash.TrashHandler> getTrashHandlerInstances();

	/**
	* Returns <code>true</code> if the portlet is an undeployed portlet.
	*
	* @return <code>true</code> if the portlet is a placeholder of an
	undeployed portlet
	*/
	public boolean getUndeployedPortlet();

	/**
	* Returns the unlinked roles of the portlet.
	*
	* @return unlinked roles of the portlet
	*/
	public java.util.Set<java.lang.String> getUnlinkedRoles();

	/**
	* Returns the name of the URL encoder class of the portlet.
	*
	* @return the name of the URL encoder class of the portlet
	*/
	public java.lang.String getURLEncoderClass();

	/**
	* Returns the URL encoder instance of the portlet.
	*
	* @return the URL encoder instance of the portlet
	*/
	public com.liferay.portal.kernel.servlet.URLEncoder getURLEncoderInstance();

	/**
	* Returns <code>true</code> if the portlet uses the default template.
	*
	* @return <code>true</code> if the portlet uses the default template
	*/
	public boolean getUseDefaultTemplate();

	/**
	* Returns the user ID of the portlet. This only applies when the portlet is
	* added by a user in a customizable layout.
	*
	* @return the user ID of the portlet
	*/
	public long getUserId();

	/**
	* Returns the names of the classes that represent user notification
	* interpreters associated with the portlet.
	*
	* @return the names of the classes that represent user notification
	interpreters associated with the portlet
	*/
	public java.util.List<java.lang.String> getUserNotificationInterpreterClasses();

	/**
	* Returns the user notification interpreter instances of the portlet.
	*
	* @return the user notification interpreter instances of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.notifications.UserNotificationInterpreter> getUserNotificationInterpreterInstances();

	/**
	* Returns the user principal strategy of the portlet.
	*
	* @return the user principal strategy of the portlet
	*/
	public java.lang.String getUserPrincipalStrategy();

	/**
	* Returns the virtual path of the portlet.
	*
	* @return the virtual path of the portlet
	*/
	public java.lang.String getVirtualPath();

	/**
	* Returns the name of the WebDAV storage class of the portlet.
	*
	* @return the name of the WebDAV storage class of the portlet
	*/
	public java.lang.String getWebDAVStorageClass();

	/**
	* Returns the name of the WebDAV storage instance of the portlet.
	*
	* @return the name of the WebDAV storage instance of the portlet
	*/
	public com.liferay.portal.kernel.webdav.WebDAVStorage getWebDAVStorageInstance();

	/**
	* Returns the name of the WebDAV storage token of the portlet.
	*
	* @return the name of the WebDAV storage token of the portlet
	*/
	public java.lang.String getWebDAVStorageToken();

	/**
	* Returns the window states of the portlet.
	*
	* @return window states of the portlet
	*/
	public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getWindowStates();

	/**
	* Returns the names of the classes that represent workflow handlers
	* associated with the portlet.
	*
	* @return the names of the classes that represent workflow handlers
	associated with the portlet
	*/
	public java.util.List<java.lang.String> getWorkflowHandlerClasses();

	/**
	* Returns the workflow handler instances of the portlet.
	*
	* @return the workflow handler instances of the portlet
	*/
	public java.util.List<com.liferay.portal.kernel.workflow.WorkflowHandler> getWorkflowHandlerInstances();

	/**
	* Returns the name of the XML-RPC method class of the portlet.
	*
	* @return the name of the XML-RPC method class of the portlet
	*/
	public java.lang.String getXmlRpcMethodClass();

	/**
	* Returns the name of the XML-RPC method instance of the portlet.
	*
	* @return the name of the XML-RPC method instance of the portlet
	*/
	public com.liferay.portal.kernel.xmlrpc.Method getXmlRpcMethodInstance();

	/**
	* Returns <code>true</code> if the user has the permission to add the
	* portlet to a layout.
	*
	* @param userId the primary key of the user
	* @return <code>true</code> if the user has the permission to add the
	portlet to a layout
	*/
	public boolean hasAddPortletPermission(long userId);

	/**
	* Returns <code>true</code> if the portlet supports more than one mime
	* type.
	*
	* @return <code>true</code> if the portlet supports more than one mime type
	*/
	public boolean hasMultipleMimeTypes();

	/**
	* Returns <code>true</code> if the portlet supports the specified mime type
	* and portlet mode.
	*
	* @param mimeType the mime type
	* @param portletMode the portlet mode
	* @return <code>true</code> if the portlet supports the specified mime type
	and portlet mode
	*/
	public boolean hasPortletMode(java.lang.String mimeType,
		javax.portlet.PortletMode portletMode);

	/**
	* Returns <code>true</code> if the portlet has a role with the specified
	* name.
	*
	* @param roleName the role name
	* @return <code>true</code> if the portlet has a role with the specified
	name
	*/
	public boolean hasRoleWithName(java.lang.String roleName);

	/**
	* Returns <code>true</code> if the portlet supports the specified mime type
	* and window state.
	*
	* @param mimeType the mime type
	* @param windowState the window state
	* @return <code>true</code> if the portlet supports the specified mime type
	and window state
	*/
	public boolean hasWindowState(java.lang.String mimeType,
		javax.portlet.WindowState windowState);

	/**
	* Returns <code>true</code> if an action URL for this portlet should cause
	* an auto redirect.
	*
	* @return <code>true</code> if an action URL for this portlet should cause
	an auto redirect
	*/
	public boolean isActionURLRedirect();

	/**
	* Returns <code>true</code> if default resources for the portlet are added
	* to a page.
	*
	* @return <code>true</code> if default resources for the portlet are added
	to a page
	*/
	public boolean isAddDefaultResource();

	/**
	* Returns <code>true</code> if the portlet can be displayed via Ajax.
	*
	* @return <code>true</code> if the portlet can be displayed via Ajax
	*/
	public boolean isAjaxable();

	/**
	* Returns <code>true</code> to include the portlet and make it available to
	* be made active.
	*
	* @return <code>true</code> to include the portlet and make it available to
	be made active
	*/
	public boolean isInclude();

	/**
	* Returns <code>true</code> if the portlet can be added multiple times to a
	* layout.
	*
	* @return <code>true</code> if the portlet can be added multiple times to a
	layout
	*/
	public boolean isInstanceable();

	/**
	* Returns <code>true</code> to allow the portlet to be cached within the
	* layout.
	*
	* @return <code>true</code> if the portlet can be cached within the layout
	*/
	public boolean isLayoutCacheable();

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the edit mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the edit mode
	*/
	public boolean isMaximizeEdit();

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the help mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the help mode
	*/
	public boolean isMaximizeHelp();

	/**
	* Returns <code>true</code> if the portlet goes into the pop up state when
	* the user goes into the print mode.
	*
	* @return <code>true</code> if the portlet goes into the pop up state when
	the user goes into the print mode
	*/
	public boolean isPopUpPrint();

	/**
	* Returns <code>true</code> if preferences are shared across the entire
	* company.
	*
	* @return <code>true</code> if preferences are shared across the entire
	company
	*/
	public boolean isPreferencesCompanyWide();

	/**
	* Returns <code>true</code> if preferences are owned by the group when the
	* portlet is shown in a group layout. Returns <code>false</code> if
	* preferences are owned by the user at all times.
	*
	* @return <code>true</code> if preferences are owned by the group when the
	portlet is shown in a group layout; <code>false</code> if
	preferences are owned by the user at all times.
	*/
	public boolean isPreferencesOwnedByGroup();

	/**
	* Returns <code>true</code> if preferences are unique per layout.
	*
	* @return <code>true</code> if preferences are unique per layout
	*/
	public boolean isPreferencesUniquePerLayout();

	/**
	* Returns <code>true</code> if the portlet does not share request
	* attributes with the portal or portlets from another WAR.
	*
	* @return <code>true</code> if the portlet does not share request
	attributes with the portal or portlets from another WAR
	*/
	public boolean isPrivateRequestAttributes();

	/**
	* Returns <code>true</code> if the portlet does not share session
	* attributes with the portal.
	*
	* @return <code>true</code> if the portlet does not share session
	attributes with the portal
	*/
	public boolean isPrivateSessionAttributes();

	/**
	* Returns <code>true</code> if the portlet is ready to be used.
	*
	* @return <code>true</code> if the portlet is ready to be used
	*/
	public boolean isReady();

	/**
	* Returns <code>true</code> if the portlet supports remoting.
	*
	* @return <code>true</code> if the portlet supports remoting
	*/
	public boolean isRemoteable();

	/**
	* Returns <code>true</code> if the portlet restores to the current view
	* from the maximized state.
	*
	* @return <code>true</code> if the portlet restores to the current view
	from the maximized state
	*/
	public boolean isRestoreCurrentView();

	/**
	* Returns <code>true</code> if the portlet supports scoping of data.
	*
	* @return <code>true</code> if the portlet supports scoping of data
	*/
	public boolean isScopeable();

	/**
	* Returns <code>true</code> if users are shown that they do not have access
	* to the portlet.
	*
	* @return <code>true</code> if users are shown that they do not have access
	to the portlet
	*/
	public boolean isShowPortletAccessDenied();

	/**
	* Returns <code>true</code> if users are shown that the portlet is
	* inactive.
	*
	* @return <code>true</code> if users are shown that the portlet is inactive
	*/
	public boolean isShowPortletInactive();

	/**
	* Returns <code>true</code> if the portlet is a static portlet that is
	* cannot be moved.
	*
	* @return <code>true</code> if the portlet is a static portlet that is
	cannot be moved
	*/
	public boolean isStatic();

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the end
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the end
	of a list of portlets
	*/
	public boolean isStaticEnd();

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the start
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the start
	of a list of portlets
	*/
	public boolean isStaticStart();

	/**
	* Returns <code>true</code> if the portlet is a system portlet that a user
	* cannot manually add to their page.
	*
	* @return <code>true</code> if the portlet is a system portlet that a user
	cannot manually add to their page
	*/
	public boolean isSystem();

	/**
	* Returns <code>true</code> if the portlet is an undeployed portlet.
	*
	* @return <code>true</code> if the portlet is a placeholder of an
	undeployed portlet
	*/
	public boolean isUndeployedPortlet();

	/**
	* Returns <code>true</code> if the portlet uses the default template.
	*
	* @return <code>true</code> if the portlet uses the default template
	*/
	public boolean isUseDefaultTemplate();

	/**
	* Link the role names set in portlet.xml with the Liferay roles set in
	* liferay-portlet.xml.
	*/
	public void linkRoles();

	/**
	* Sets the action timeout of the portlet.
	*
	* @param actionTimeout the action timeout of the portlet
	*/
	public void setActionTimeout(int actionTimeout);

	/**
	* Set to <code>true</code> if an action URL for this portlet should cause
	* an auto redirect.
	*
	* @param actionURLRedirect boolean value for whether an action URL for this
	portlet should cause an auto redirect
	*/
	public void setActionURLRedirect(boolean actionURLRedirect);

	/**
	* Set to <code>true</code> if default resources for the portlet are added
	* to a page.
	*
	* @param addDefaultResource boolean value for whether or not default
	resources for the portlet are added to a page
	*/
	public void setAddDefaultResource(boolean addDefaultResource);

	/**
	* Set to <code>true</code> if the portlet can be displayed via Ajax.
	*
	* @param ajaxable boolean value for whether the portlet can be displayed
	via Ajax
	*/
	public void setAjaxable(boolean ajaxable);

	/**
	* Sets the names of the classes that represent asset types associated with
	* the portlet.
	*
	* @param assetRendererFactoryClasses the names of the classes that
	represent asset types associated with the portlet
	*/
	public void setAssetRendererFactoryClasses(
		java.util.List<java.lang.String> assetRendererFactoryClasses);

	/**
	* Sets the names of the classes that represent atom collection adapters
	* associated with the portlet.
	*
	* @param atomCollectionAdapterClasses the names of the classes that
	represent atom collection adapters associated with the portlet
	*/
	public void setAtomCollectionAdapterClasses(
		java.util.List<java.lang.String> atomCollectionAdapterClasses);

	/**
	* Sets the names of the parameters that will be automatically propagated
	* through the portlet.
	*
	* @param autopropagatedParameters the names of the parameters that will be
	automatically propagated through the portlet
	*/
	public void setAutopropagatedParameters(
		java.util.Set<java.lang.String> autopropagatedParameters);

	/**
	* Sets the configuration action class of the portlet.
	*
	* @param configurationActionClass the configuration action class of the
	portlet
	*/
	public void setConfigurationActionClass(
		java.lang.String configurationActionClass);

	/**
	* Set the name of the category of the Control Panel where the portlet will
	* be shown.
	*
	* @param controlPanelEntryCategory the name of the category of the Control
	Panel where the portlet will be shown
	*/
	public void setControlPanelEntryCategory(
		java.lang.String controlPanelEntryCategory);

	/**
	* Sets the name of the class that will control when the portlet will be
	* shown in the Control Panel.
	*
	* @param controlPanelEntryClass the name of the class that will control
	when the portlet will be shown in the Control Panel
	*/
	public void setControlPanelEntryClass(
		java.lang.String controlPanelEntryClass);

	/**
	* Sets the relative weight of the portlet with respect to the other
	* portlets in the same category of the Control Panel.
	*
	* @param controlPanelEntryWeight the relative weight of the portlet with
	respect to the other portlets in the same category of the Control
	Panel
	*/
	public void setControlPanelEntryWeight(double controlPanelEntryWeight);

	/**
	* Sets the name of the CSS class that will be injected in the DIV that
	* wraps this portlet.
	*
	* @param cssClassWrapper the name of the CSS class that will be injected in
	the DIV that wraps this portlet
	*/
	public void setCssClassWrapper(java.lang.String cssClassWrapper);

	/**
	* Sets the names of the classes that represent custom attribute displays
	* associated with the portlet.
	*
	* @param customAttributesDisplayClasses the names of the classes that
	represent custom attribute displays associated with the portlet
	*/
	public void setCustomAttributesDisplayClasses(
		java.util.List<java.lang.String> customAttributesDisplayClasses);

	/**
	* Sets the name of the dynamic data mapping display class of the portlet.
	*
	* @param ddmDisplayClass the name of dynamic data mapping display class of
	the portlet
	*/
	public void setDDMDisplayClass(java.lang.String ddmDisplayClass);

	/**
	* Sets the default plugin settings of the portlet.
	*
	* @param pluginSetting the plugin setting
	*/
	public void setDefaultPluginSetting(
		com.liferay.portal.model.PluginSetting pluginSetting);

	/**
	* Sets the default preferences of the portlet.
	*
	* @param defaultPreferences the default preferences of the portlet
	*/
	public void setDefaultPreferences(java.lang.String defaultPreferences);

	/**
	* Sets the display name of the portlet.
	*
	* @param displayName the display name of the portlet
	*/
	public void setDisplayName(java.lang.String displayName);

	/**
	* Sets expiration cache of the portlet.
	*
	* @param expCache expiration cache of the portlet
	*/
	public void setExpCache(java.lang.Integer expCache);

	/**
	* Sets the Facebook integration method of the portlet.
	*
	* @param facebookIntegration the Facebook integration method of the portlet
	*/
	public void setFacebookIntegration(java.lang.String facebookIntegration);

	/**
	* Sets a list of CSS files that will be referenced from the page's footer
	* relative to the portal's context path.
	*
	* @param footerPortalCss a list of CSS files that will be referenced from
	the page's footer relative to the portal's context path
	*/
	public void setFooterPortalCss(
		java.util.List<java.lang.String> footerPortalCss);

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* footer relative to the portal's context path.
	*
	* @param footerPortalJavaScript a list of JavaScript files that will be
	referenced from the page's footer relative to the portal's context
	path
	*/
	public void setFooterPortalJavaScript(
		java.util.List<java.lang.String> footerPortalJavaScript);

	/**
	* Sets a list of CSS files that will be referenced from the page's footer
	* relative to the portlet's context path.
	*
	* @param footerPortletCss a list of CSS files that will be referenced from
	the page's footer relative to the portlet's context path
	*/
	public void setFooterPortletCss(
		java.util.List<java.lang.String> footerPortletCss);

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* footer relative to the portlet's context path.
	*
	* @param footerPortletJavaScript a list of JavaScript files that will be
	referenced from the page's footer relative to the portlet's
	context path
	*/
	public void setFooterPortletJavaScript(
		java.util.List<java.lang.String> footerPortletJavaScript);

	/**
	* Sets the name of the friendly URL mapper class of the portlet.
	*
	* @param friendlyURLMapperClass the name of the friendly URL mapper class
	of the portlet
	*/
	public void setFriendlyURLMapperClass(
		java.lang.String friendlyURLMapperClass);

	/**
	* Sets the name of the friendly URL mapping of the portlet.
	*
	* @param friendlyURLMapping the name of the friendly URL mapping of the
	portlet
	*/
	public void setFriendlyURLMapping(java.lang.String friendlyURLMapping);

	/**
	* Sets the class loader resource path to the friendly URL routes of the
	* portlet.
	*
	* @param friendlyURLRoutes the class loader resource path to the friendly
	URL routes of the portlet
	*/
	public void setFriendlyURLRoutes(java.lang.String friendlyURLRoutes);

	/**
	* Sets a list of CSS files that will be referenced from the page's header
	* relative to the portal's context path.
	*
	* @param headerPortalCss a list of CSS files that will be referenced from
	the page's header relative to the portal's context path
	*/
	public void setHeaderPortalCss(
		java.util.List<java.lang.String> headerPortalCss);

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* header relative to the portal's context path.
	*
	* @param headerPortalJavaScript a list of JavaScript files that will be
	referenced from the page's header relative to the portal's context
	path
	*/
	public void setHeaderPortalJavaScript(
		java.util.List<java.lang.String> headerPortalJavaScript);

	/**
	* Sets a list of CSS files that will be referenced from the page's header
	* relative to the portlet's context path.
	*
	* @param headerPortletCss a list of CSS files that will be referenced from
	the page's header relative to the portlet's context path
	*/
	public void setHeaderPortletCss(
		java.util.List<java.lang.String> headerPortletCss);

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* header relative to the portlet's context path.
	*
	* @param headerPortletJavaScript a list of JavaScript files that will be
	referenced from the page's header relative to the portlet's
	context path
	*/
	public void setHeaderPortletJavaScript(
		java.util.List<java.lang.String> headerPortletJavaScript);

	/**
	* Sets the icon of the portlet.
	*
	* @param icon the icon of the portlet
	*/
	public void setIcon(java.lang.String icon);

	/**
	* Set to <code>true</code> to include the portlet and make it available to
	* be made active.
	*
	* @param include boolean value for whether to include the portlet and make
	it available to be made active
	*/
	public void setInclude(boolean include);

	/**
	* Sets the names of the classes that represent indexers associated with the
	* portlet.
	*
	* @param indexerClasses the names of the classes that represent indexers
	associated with the portlet
	*/
	public void setIndexerClasses(
		java.util.List<java.lang.String> indexerClasses);

	/**
	* Sets the init parameters of the portlet.
	*
	* @param initParams the init parameters of the portlet
	*/
	public void setInitParams(
		java.util.Map<java.lang.String, java.lang.String> initParams);

	/**
	* Set to <code>true</code> if the portlet can be added multiple times to a
	* layout.
	*
	* @param instanceable boolean value for whether the portlet can be added
	multiple times to a layout
	*/
	public void setInstanceable(boolean instanceable);

	/**
	* Set to <code>true</code> to allow the portlet to be cached within the
	* layout.
	*
	* @param layoutCacheable boolean value for whether the portlet can be
	cached within the layout
	*/
	public void setLayoutCacheable(boolean layoutCacheable);

	/**
	* Set to <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the edit mode.
	*
	* @param maximizeEdit boolean value for whether the portlet goes into the
	maximized state when the user goes into the edit mode
	*/
	public void setMaximizeEdit(boolean maximizeEdit);

	/**
	* Set to <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the help mode.
	*
	* @param maximizeHelp boolean value for whether the portlet goes into the
	maximized state when the user goes into the help mode
	*/
	public void setMaximizeHelp(boolean maximizeHelp);

	/**
	* Sets the name of the open search class of the portlet.
	*
	* @param openSearchClass the name of the open search class of the portlet
	*/
	public void setOpenSearchClass(java.lang.String openSearchClass);

	/**
	* Sets the parent struts path of the portlet.
	*
	* @param parentStrutsPath the parent struts path of the portlet
	*/
	public void setParentStrutsPath(java.lang.String parentStrutsPath);

	/**
	* Sets the name of the permission propagator class of the portlet.
	*/
	public void setPermissionPropagatorClass(
		java.lang.String permissionPropagatorClass);

	/**
	* Sets this portlet's plugin package.
	*
	* @param pluginPackage this portlet's plugin package
	*/
	public void setPluginPackage(
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage);

	/**
	* Sets the name of the poller processor class of the portlet.
	*
	* @param pollerProcessorClass the name of the poller processor class of the
	portlet
	*/
	public void setPollerProcessorClass(java.lang.String pollerProcessorClass);

	/**
	* Sets the name of the POP message listener class of the portlet.
	*
	* @param popMessageListenerClass the name of the POP message listener class
	of the portlet
	*/
	public void setPopMessageListenerClass(
		java.lang.String popMessageListenerClass);

	/**
	* Set to <code>true</code> if the portlet goes into the pop up state when
	* the user goes into the print mode.
	*
	* @param popUpPrint boolean value for whether the portlet goes into the pop
	up state when the user goes into the print mode
	*/
	public void setPopUpPrint(boolean popUpPrint);

	/**
	* Sets this portlet's application.
	*
	* @param portletApp this portlet's application
	*/
	public void setPortletApp(com.liferay.portal.model.PortletApp portletApp);

	/**
	* Sets the name of the portlet class of the portlet.
	*
	* @param portletClass the name of the portlet class of the portlet
	*/
	public void setPortletClass(java.lang.String portletClass);

	/**
	* Sets the name of the portlet data handler class of the portlet.
	*
	* @param portletDataHandlerClass the name of portlet data handler class of
	the portlet
	*/
	public void setPortletDataHandlerClass(
		java.lang.String portletDataHandlerClass);

	/**
	* Sets the filters of the portlet.
	*
	* @param portletFilters the filters of the portlet
	*/
	public void setPortletFilters(
		java.util.Map<java.lang.String, com.liferay.portal.model.PortletFilter> portletFilters);

	/**
	* Sets the portlet info of the portlet.
	*
	* @param portletInfo the portlet info of the portlet
	*/
	public void setPortletInfo(com.liferay.portal.model.PortletInfo portletInfo);

	/**
	* Sets the name of the portlet layout listener class of the portlet.
	*
	* @param portletLayoutListenerClass the name of the portlet layout listener
	class of the portlet
	*/
	public void setPortletLayoutListenerClass(
		java.lang.String portletLayoutListenerClass);

	/**
	* Sets the portlet modes of the portlet.
	*
	* @param portletModes the portlet modes of the portlet
	*/
	public void setPortletModes(
		java.util.Map<java.lang.String, java.util.Set<java.lang.String>> portletModes);

	/**
	* Sets the name of the portlet.
	*
	* @param portletName the name of the portlet
	*/
	public void setPortletName(java.lang.String portletName);

	/**
	* Sets the name of the portlet URL class of the portlet.
	*
	* @param portletURLClass the name of the portlet URL class of the portlet
	*/
	public void setPortletURLClass(java.lang.String portletURLClass);

	/**
	* Set to <code>true</code> if preferences are shared across the entire
	* company.
	*
	* @param preferencesCompanyWide boolean value for whether preferences are
	shared across the entire company
	*/
	public void setPreferencesCompanyWide(boolean preferencesCompanyWide);

	/**
	* Set to <code>true</code> if preferences are owned by the group when the
	* portlet is shown in a group layout. Set to <code>false</code> if
	* preferences are owned by the user at all times.
	*
	* @param preferencesOwnedByGroup boolean value for whether preferences are
	owned by the group when the portlet is shown in a group layout or
	preferences are owned by the user at all times
	*/
	public void setPreferencesOwnedByGroup(boolean preferencesOwnedByGroup);

	/**
	* Set to <code>true</code> if preferences are unique per layout.
	*
	* @param preferencesUniquePerLayout boolean value for whether preferences
	are unique per layout
	*/
	public void setPreferencesUniquePerLayout(
		boolean preferencesUniquePerLayout);

	/**
	* Sets the name of the preferences validator class of the portlet.
	*
	* @param preferencesValidator the name of the preferences validator class
	of the portlet
	*/
	public void setPreferencesValidator(java.lang.String preferencesValidator);

	/**
	* Set to <code>true</code> if the portlet does not share request attributes
	* with the portal or portlets from another WAR.
	*
	* @param privateRequestAttributes boolean value for whether the portlet
	shares request attributes with the portal or portlets from another
	WAR
	*/
	public void setPrivateRequestAttributes(boolean privateRequestAttributes);

	/**
	* Set to <code>true</code> if the portlet does not share session attributes
	* with the portal.
	*
	* @param privateSessionAttributes boolean value for whether the portlet
	shares session attributes with the portal
	*/
	public void setPrivateSessionAttributes(boolean privateSessionAttributes);

	/**
	* Sets the supported processing events of the portlet.
	*
	* @param processingEvents the supported processing events of the portlet
	*/
	public void setProcessingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> processingEvents);

	/**
	* Sets the supported public render parameters of the portlet.
	*
	* @param publicRenderParameters the supported public render parameters of
	the portlet
	*/
	public void setPublicRenderParameters(
		java.util.Set<com.liferay.portal.model.PublicRenderParameter> publicRenderParameters);

	/**
	* Sets the supported publishing events of the portlet.
	*
	* @param publishingEvents the supported publishing events of the portlet
	*/
	public void setPublishingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> publishingEvents);

	/**
	* Set to <code>true</code> if the portlet is ready to be used.
	*
	* @param ready whether the portlet is ready to be used
	*/
	public void setReady(boolean ready);

	/**
	* Set to <code>true</code> if the portlet supports remoting
	*
	* @param remoteable boolean value for whether or not the the portlet
	supports remoting
	*/
	public void setRemoteable(boolean remoteable);

	/**
	* Sets the render timeout of the portlet.
	*
	* @param renderTimeout the render timeout of the portlet
	*/
	public void setRenderTimeout(int renderTimeout);

	/**
	* Sets the render weight of the portlet.
	*
	* @param renderWeight int value for the render weight of the portlet
	*/
	public void setRenderWeight(int renderWeight);

	/**
	* Sets the resource bundle of the portlet.
	*
	* @param resourceBundle the resource bundle of the portlet
	*/
	public void setResourceBundle(java.lang.String resourceBundle);

	/**
	* Set to <code>true</code> if the portlet restores to the current view from
	* the maximized state.
	*
	* @param restoreCurrentView boolean value for whether the portlet restores
	to the current view from the maximized state
	*/
	public void setRestoreCurrentView(boolean restoreCurrentView);

	/**
	* Sets the role mappers of the portlet.
	*
	* @param roleMappers the role mappers of the portlet
	*/
	public void setRoleMappers(
		java.util.Map<java.lang.String, java.lang.String> roleMappers);

	/**
	* Sets an array of required roles of the portlet.
	*
	* @param rolesArray an array of required roles of the portlet
	*/
	public void setRolesArray(java.lang.String[] rolesArray);

	/**
	* Sets the scheduler entries of the portlet.
	*
	* @param schedulerEntries the scheduler entries of the portlet
	*/
	public void setSchedulerEntries(
		java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> schedulerEntries);

	/**
	* Set to <code>true</code> if the portlet supports scoping of data.
	*
	* @param scopeable boolean value for whether or not the the portlet
	supports scoping of data
	*/
	public void setScopeable(boolean scopeable);

	/**
	* Set to <code>true</code> if users are shown that they do not have access
	* to the portlet.
	*
	* @param showPortletAccessDenied boolean value for whether users are shown
	that they do not have access to the portlet
	*/
	public void setShowPortletAccessDenied(boolean showPortletAccessDenied);

	/**
	* Set to <code>true</code> if users are shown that the portlet is inactive.
	*
	* @param showPortletInactive boolean value for whether users are shown that
	the portlet is inactive
	*/
	public void setShowPortletInactive(boolean showPortletInactive);

	/**
	* Sets the names of the classes that represent social activity interpreters
	* associated with the portlet.
	*
	* @param socialActivityInterpreterClasses the names of the classes that
	represent social activity interpreters associated with the portlet
	*/
	public void setSocialActivityInterpreterClasses(
		java.util.List<java.lang.String> socialActivityInterpreterClasses);

	/**
	* Sets the name of the social request interpreter class of the portlet.
	*
	* @param socialRequestInterpreterClass the name of the request interpreter
	class of the portlet
	*/
	public void setSocialRequestInterpreterClass(
		java.lang.String socialRequestInterpreterClass);

	/**
	* Sets the names of the classes that represent staged model data handlers
	* associated with the portlet.
	*
	* @param stagedModelDataHandlerClasses the names of the classes that
	represent staged model data handlers associated with the portlet
	*/
	public void setStagedModelDataHandlerClasses(
		java.util.List<java.lang.String> stagedModelDataHandlerClasses);

	/**
	* Set to <code>true</code> if the portlet is a static portlet that is
	* cannot be moved.
	*
	* @param staticPortlet boolean value for whether the portlet is a static
	portlet that cannot be moved
	*/
	public void setStatic(boolean staticPortlet);

	/**
	* Set to <code>true</code> if the portlet is a static portlet at the start
	* of a list of portlets.
	*
	* @param staticPortletStart boolean value for whether the portlet is a
	static portlet at the start of a list of portlets
	*/
	public void setStaticStart(boolean staticPortletStart);

	/**
	* Sets the struts path of the portlet.
	*
	* @param strutsPath the struts path of the portlet
	*/
	public void setStrutsPath(java.lang.String strutsPath);

	/**
	* Sets the supported locales of the portlet.
	*
	* @param supportedLocales the supported locales of the portlet
	*/
	public void setSupportedLocales(
		java.util.Set<java.lang.String> supportedLocales);

	/**
	* Set to <code>true</code> if the portlet is a system portlet that a user
	* cannot manually add to their page.
	*
	* @param system boolean value for whether the portlet is a system portlet
	that a user cannot manually add to their page
	*/
	public void setSystem(boolean system);

	/**
	* Sets the name of the template handler class of the portlet.
	*
	* @param templateHandlerClass the name of template handler class of the
	portlet
	*/
	public void setTemplateHandlerClass(java.lang.String templateHandlerClass);

	/**
	* Sets the timestamp of the portlet.
	*
	* @param timestamp the timestamp of the portlet
	*/
	public void setTimestamp(long timestamp);

	/**
	* Sets the names of the classes that represent trash handlers associated to
	* the portlet.
	*
	* @param trashHandlerClasses the names of the classes that represent trash
	handlers associated with the portlet
	*/
	public void setTrashHandlerClasses(
		java.util.List<java.lang.String> trashHandlerClasses);

	/**
	* Set to <code>true</code> if the portlet is an undeployed portlet.
	*
	* @param undeployedPortlet boolean value for whether the portlet is an
	undeployed portlet
	*/
	public void setUndeployedPortlet(boolean undeployedPortlet);

	/**
	* Sets the unlinked roles of the portlet.
	*
	* @param unlinkedRoles the unlinked roles of the portlet
	*/
	public void setUnlinkedRoles(java.util.Set<java.lang.String> unlinkedRoles);

	/**
	* Sets the name of the URL encoder class of the portlet.
	*
	* @param urlEncoderClass the name of the URL encoder class of the portlet
	*/
	public void setURLEncoderClass(java.lang.String urlEncoderClass);

	/**
	* Set to <code>true</code> if the portlet uses the default template.
	*
	* @param useDefaultTemplate boolean value for whether the portlet uses the
	default template
	*/
	public void setUseDefaultTemplate(boolean useDefaultTemplate);

	/**
	* Sets the names of the classes that represent user notification
	* interpreters associated with the portlet.
	*
	* @param userNotificationInterpreterClasses the names of the classes that
	represent user notification interpreters associated with the
	portlet
	*/
	public void setUserNotificationInterpreterClasses(
		java.util.List<java.lang.String> userNotificationInterpreterClasses);

	/**
	* Sets the user principal strategy of the portlet.
	*
	* @param userPrincipalStrategy the user principal strategy of the portlet
	*/
	public void setUserPrincipalStrategy(java.lang.String userPrincipalStrategy);

	/**
	* Sets the virtual path of the portlet.
	*
	* @param virtualPath the virtual path of the portlet
	*/
	public void setVirtualPath(java.lang.String virtualPath);

	/**
	* Sets the name of the WebDAV storage class of the portlet.
	*
	* @param webDAVStorageClass the name of the WebDAV storage class of the
	portlet
	*/
	public void setWebDAVStorageClass(java.lang.String webDAVStorageClass);

	/**
	* Sets the name of the WebDAV storage token of the portlet.
	*
	* @param webDAVStorageToken the name of the WebDAV storage token of the
	portlet
	*/
	public void setWebDAVStorageToken(java.lang.String webDAVStorageToken);

	/**
	* Sets the window states of the portlet.
	*
	* @param windowStates the window states of the portlet
	*/
	public void setWindowStates(
		java.util.Map<java.lang.String, java.util.Set<java.lang.String>> windowStates);

	/**
	* Sets the names of the classes that represent workflow handlers associated
	* to the portlet.
	*
	* @param workflowHandlerClasses the names of the classes that represent
	workflow handlers associated with the portlet
	*/
	public void setWorkflowHandlerClasses(
		java.util.List<java.lang.String> workflowHandlerClasses);

	/**
	* Sets the name of the XML-RPC method class of the portlet.
	*
	* @param xmlRpcMethodClass the name of the XML-RPC method class of the
	portlet
	*/
	public void setXmlRpcMethodClass(java.lang.String xmlRpcMethodClass);
}