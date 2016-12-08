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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link Portlet}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Portlet
 * @generated
 */
@ProviderType
public class PortletWrapper implements Portlet, ModelWrapper<Portlet> {
	public PortletWrapper(Portlet portlet) {
		_portlet = portlet;
	}

	@Override
	public Class<?> getModelClass() {
		return Portlet.class;
	}

	@Override
	public String getModelClassName() {
		return Portlet.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("id", getId());
		attributes.put("companyId", getCompanyId());
		attributes.put("portletId", getPortletId());
		attributes.put("roles", getRoles());
		attributes.put("active", getActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}

		String roles = (String)attributes.get("roles");

		if (roles != null) {
			setRoles(roles);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	@Override
	public CacheModel<Portlet> toCacheModel() {
		return _portlet.toCacheModel();
	}

	/**
	* Get the default plugin settings of the portlet.
	*
	* @return the plugin settings
	*/
	@Override
	public PluginSetting getDefaultPluginSetting() {
		return _portlet.getDefaultPluginSetting();
	}

	@Override
	public Portlet toEscapedModel() {
		return new PortletWrapper(_portlet.toEscapedModel());
	}

	@Override
	public Portlet toUnescapedModel() {
		return new PortletWrapper(_portlet.toUnescapedModel());
	}

	/**
	* Returns this portlet's application.
	*
	* @return this portlet's application
	*/
	@Override
	public PortletApp getPortletApp() {
		return _portlet.getPortletApp();
	}

	/**
	* Returns the portlet info of the portlet.
	*
	* @return portlet info of the portlet
	*/
	@Override
	public PortletInfo getPortletInfo() {
		return _portlet.getPortletInfo();
	}

	/**
	* Returns the public render parameter from an identifier.
	*
	* @param identifier the identifier
	* @return the public render parameter from an identifier
	*/
	@Override
	public PublicRenderParameter getPublicRenderParameter(
		java.lang.String identifier) {
		return _portlet.getPublicRenderParameter(identifier);
	}

	/**
	* Returns the spublic render parameter from a namespace URI and a local
	* part.
	*
	* @param uri the namespace URI
	* @param localPart the local part
	* @return the spublic render parameter from a namespace URI and a local
	part
	*/
	@Override
	public PublicRenderParameter getPublicRenderParameter(
		java.lang.String uri, java.lang.String localPart) {
		return _portlet.getPublicRenderParameter(uri, localPart);
	}

	/**
	* Returns <code>true</code> if an action URL for this portlet should cause
	* an auto redirect.
	*
	* @return <code>true</code> if an action URL for this portlet should cause
	an auto redirect
	*/
	@Override
	public boolean getActionURLRedirect() {
		return _portlet.getActionURLRedirect();
	}

	/**
	* Returns the active of this portlet.
	*
	* @return the active of this portlet
	*/
	@Override
	public boolean getActive() {
		return _portlet.getActive();
	}

	/**
	* Returns <code>true</code> if default resources for the portlet are added
	* to a page.
	*
	* @return <code>true</code> if default resources for the portlet are added
	to a page
	*/
	@Override
	public boolean getAddDefaultResource() {
		return _portlet.getAddDefaultResource();
	}

	/**
	* Returns <code>true</code> if the portlet can be displayed via Ajax.
	*
	* @return <code>true</code> if the portlet can be displayed via Ajax
	*/
	@Override
	public boolean getAjaxable() {
		return _portlet.getAjaxable();
	}

	/**
	* Returns <code>true</code> to include the portlet and make it available to
	* be made active.
	*
	* @return <code>true</code> to include the portlet and make it available to
	be made active
	*/
	@Override
	public boolean getInclude() {
		return _portlet.getInclude();
	}

	/**
	* Returns <code>true</code> if the portlet can be added multiple times to a
	* layout.
	*
	* @return <code>true</code> if the portlet can be added multiple times to a
	layout
	*/
	@Override
	public boolean getInstanceable() {
		return _portlet.getInstanceable();
	}

	/**
	* Returns <code>true</code> to allow the portlet to be cached within the
	* layout.
	*
	* @return <code>true</code> if the portlet can be cached within the layout
	*/
	@Override
	public boolean getLayoutCacheable() {
		return _portlet.getLayoutCacheable();
	}

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the edit mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the edit mode
	*/
	@Override
	public boolean getMaximizeEdit() {
		return _portlet.getMaximizeEdit();
	}

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the help mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the help mode
	*/
	@Override
	public boolean getMaximizeHelp() {
		return _portlet.getMaximizeHelp();
	}

	/**
	* Returns <code>true</code> if the portlet goes into the pop up state when
	* the user goes into the print mode.
	*
	* @return <code>true</code> if the portlet goes into the pop up state when
	the user goes into the print mode
	*/
	@Override
	public boolean getPopUpPrint() {
		return _portlet.getPopUpPrint();
	}

	/**
	* Returns <code>true</code> if preferences are shared across the entire
	* company.
	*
	* @return <code>true</code> if preferences are shared across the entire
	company
	*/
	@Override
	public boolean getPreferencesCompanyWide() {
		return _portlet.getPreferencesCompanyWide();
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
	@Override
	public boolean getPreferencesOwnedByGroup() {
		return _portlet.getPreferencesOwnedByGroup();
	}

	/**
	* Returns <code>true</code> if preferences are unique per layout.
	*
	* @return <code>true</code> if preferences are unique per layout
	*/
	@Override
	public boolean getPreferencesUniquePerLayout() {
		return _portlet.getPreferencesUniquePerLayout();
	}

	/**
	* Returns <code>true</code> if the portlet does not share request
	* attributes with the portal or portlets from another WAR.
	*
	* @return <code>true</code> if the portlet does not share request
	attributes with the portal or portlets from another WAR
	*/
	@Override
	public boolean getPrivateRequestAttributes() {
		return _portlet.getPrivateRequestAttributes();
	}

	/**
	* Returns <code>true</code> if the portlet does not share session
	* attributes with the portal.
	*
	* @return <code>true</code> if the portlet does not share session
	attributes with the portal
	*/
	@Override
	public boolean getPrivateSessionAttributes() {
		return _portlet.getPrivateSessionAttributes();
	}

	/**
	* Returns <code>true</code> if the portlet is ready to be used.
	*
	* @return <code>true</code> if the portlet is ready to be used
	*/
	@Override
	public boolean getReady() {
		return _portlet.getReady();
	}

	/**
	* Returns <code>true</code> if the portlet supports remoting.
	*
	* @return <code>true</code> if the portlet supports remoting
	*/
	@Override
	public boolean getRemoteable() {
		return _portlet.getRemoteable();
	}

	/**
	* Returns <code>true</code> if the portlet restores to the current view
	* from the maximized state.
	*
	* @return <code>true</code> if the portlet restores to the current view
	from the maximized state
	*/
	@Override
	public boolean getRestoreCurrentView() {
		return _portlet.getRestoreCurrentView();
	}

	/**
	* Returns <code>true</code> if the portlet supports scoping of data.
	*
	* @return <code>true</code> if the portlet supports scoping of data
	*/
	@Override
	public boolean getScopeable() {
		return _portlet.getScopeable();
	}

	/**
	* Returns <code>true</code> if users are shown that they do not have access
	* to the portlet.
	*
	* @return <code>true</code> if users are shown that they do not have access
	to the portlet
	*/
	@Override
	public boolean getShowPortletAccessDenied() {
		return _portlet.getShowPortletAccessDenied();
	}

	/**
	* Returns <code>true</code> if users are shown that the portlet is
	* inactive.
	*
	* @return <code>true</code> if users are shown that the portlet is inactive
	*/
	@Override
	public boolean getShowPortletInactive() {
		return _portlet.getShowPortletInactive();
	}

	/**
	* Returns <code>true</code> if the portlet uses Single Page Application.
	*
	* @return <code>true</code> if the portlet uses Single Page Application
	*/
	@Override
	public boolean getSinglePageApplication() {
		return _portlet.getSinglePageApplication();
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet that is
	* cannot be moved.
	*
	* @return <code>true</code> if the portlet is a static portlet that is
	cannot be moved
	*/
	@Override
	public boolean getStatic() {
		return _portlet.getStatic();
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the end
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the end
	of a list of portlets
	*/
	@Override
	public boolean getStaticEnd() {
		return _portlet.getStaticEnd();
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the start
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the start
	of a list of portlets
	*/
	@Override
	public boolean getStaticStart() {
		return _portlet.getStaticStart();
	}

	/**
	* Returns <code>true</code> if the portlet is a system portlet that a user
	* cannot manually add to their page.
	*
	* @return <code>true</code> if the portlet is a system portlet that a user
	cannot manually add to their page
	*/
	@Override
	public boolean getSystem() {
		return _portlet.getSystem();
	}

	/**
	* Returns <code>true</code> if the portlet is an undeployed portlet.
	*
	* @return <code>true</code> if the portlet is a placeholder of an
	undeployed portlet
	*/
	@Override
	public boolean getUndeployedPortlet() {
		return _portlet.getUndeployedPortlet();
	}

	/**
	* Returns <code>true</code> if the portlet uses the default template.
	*
	* @return <code>true</code> if the portlet uses the default template
	*/
	@Override
	public boolean getUseDefaultTemplate() {
		return _portlet.getUseDefaultTemplate();
	}

	/**
	* Returns <code>true</code> if the user has the permission to add the
	* portlet to a layout.
	*
	* @param userId the primary key of the user
	* @return <code>true</code> if the user has the permission to add the
	portlet to a layout
	*/
	@Override
	public boolean hasAddPortletPermission(long userId) {
		return _portlet.hasAddPortletPermission(userId);
	}

	@Override
	public boolean hasFooterPortalCss() {
		return _portlet.hasFooterPortalCss();
	}

	@Override
	public boolean hasFooterPortalJavaScript() {
		return _portlet.hasFooterPortalJavaScript();
	}

	@Override
	public boolean hasFooterPortletCss() {
		return _portlet.hasFooterPortletCss();
	}

	@Override
	public boolean hasFooterPortletJavaScript() {
		return _portlet.hasFooterPortletJavaScript();
	}

	@Override
	public boolean hasHeaderPortalCss() {
		return _portlet.hasHeaderPortalCss();
	}

	@Override
	public boolean hasHeaderPortalJavaScript() {
		return _portlet.hasHeaderPortalJavaScript();
	}

	@Override
	public boolean hasHeaderPortletCss() {
		return _portlet.hasHeaderPortletCss();
	}

	@Override
	public boolean hasHeaderPortletJavaScript() {
		return _portlet.hasHeaderPortletJavaScript();
	}

	/**
	* Returns <code>true</code> if the portlet supports more than one mime
	* type.
	*
	* @return <code>true</code> if the portlet supports more than one mime type
	*/
	@Override
	public boolean hasMultipleMimeTypes() {
		return _portlet.hasMultipleMimeTypes();
	}

	/**
	* Returns <code>true</code> if the portlet supports the specified mime type
	* and portlet mode.
	*
	* @param mimeType the mime type
	* @param portletMode the portlet mode
	* @return <code>true</code> if the portlet supports the specified mime type
	and portlet mode
	*/
	@Override
	public boolean hasPortletMode(java.lang.String mimeType,
		javax.portlet.PortletMode portletMode) {
		return _portlet.hasPortletMode(mimeType, portletMode);
	}

	/**
	* Returns <code>true</code> if the portlet has a role with the specified
	* name.
	*
	* @param roleName the role name
	* @return <code>true</code> if the portlet has a role with the specified
	name
	*/
	@Override
	public boolean hasRoleWithName(java.lang.String roleName) {
		return _portlet.hasRoleWithName(roleName);
	}

	/**
	* Returns <code>true</code> if the portlet supports the specified mime type
	* and window state.
	*
	* @param mimeType the mime type
	* @param windowState the window state
	* @return <code>true</code> if the portlet supports the specified mime type
	and window state
	*/
	@Override
	public boolean hasWindowState(java.lang.String mimeType,
		javax.portlet.WindowState windowState) {
		return _portlet.hasWindowState(mimeType, windowState);
	}

	/**
	* Returns <code>true</code> if an action URL for this portlet should cause
	* an auto redirect.
	*
	* @return <code>true</code> if an action URL for this portlet should cause
	an auto redirect
	*/
	@Override
	public boolean isActionURLRedirect() {
		return _portlet.isActionURLRedirect();
	}

	/**
	* Returns <code>true</code> if this portlet is active.
	*
	* @return <code>true</code> if this portlet is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _portlet.isActive();
	}

	/**
	* Returns <code>true</code> if default resources for the portlet are added
	* to a page.
	*
	* @return <code>true</code> if default resources for the portlet are added
	to a page
	*/
	@Override
	public boolean isAddDefaultResource() {
		return _portlet.isAddDefaultResource();
	}

	/**
	* Returns <code>true</code> if the portlet can be displayed via Ajax.
	*
	* @return <code>true</code> if the portlet can be displayed via Ajax
	*/
	@Override
	public boolean isAjaxable() {
		return _portlet.isAjaxable();
	}

	@Override
	public boolean isCachedModel() {
		return _portlet.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _portlet.isEscapedModel();
	}

	@Override
	public boolean isFullPageDisplayable() {
		return _portlet.isFullPageDisplayable();
	}

	/**
	* Returns <code>true</code> to include the portlet and make it available to
	* be made active.
	*
	* @return <code>true</code> to include the portlet and make it available to
	be made active
	*/
	@Override
	public boolean isInclude() {
		return _portlet.isInclude();
	}

	/**
	* Returns <code>true</code> if the portlet can be added multiple times to a
	* layout.
	*
	* @return <code>true</code> if the portlet can be added multiple times to a
	layout
	*/
	@Override
	public boolean isInstanceable() {
		return _portlet.isInstanceable();
	}

	/**
	* Returns <code>true</code> to allow the portlet to be cached within the
	* layout.
	*
	* @return <code>true</code> if the portlet can be cached within the layout
	*/
	@Override
	public boolean isLayoutCacheable() {
		return _portlet.isLayoutCacheable();
	}

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the edit mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the edit mode
	*/
	@Override
	public boolean isMaximizeEdit() {
		return _portlet.isMaximizeEdit();
	}

	/**
	* Returns <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the help mode.
	*
	* @return <code>true</code> if the portlet goes into the maximized state
	when the user goes into the help mode
	*/
	@Override
	public boolean isMaximizeHelp() {
		return _portlet.isMaximizeHelp();
	}

	@Override
	public boolean isNew() {
		return _portlet.isNew();
	}

	/**
	* Returns <code>true</code> if the portlet goes into the pop up state when
	* the user goes into the print mode.
	*
	* @return <code>true</code> if the portlet goes into the pop up state when
	the user goes into the print mode
	*/
	@Override
	public boolean isPopUpPrint() {
		return _portlet.isPopUpPrint();
	}

	/**
	* Returns <code>true</code> if preferences are shared across the entire
	* company.
	*
	* @return <code>true</code> if preferences are shared across the entire
	company
	*/
	@Override
	public boolean isPreferencesCompanyWide() {
		return _portlet.isPreferencesCompanyWide();
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
	@Override
	public boolean isPreferencesOwnedByGroup() {
		return _portlet.isPreferencesOwnedByGroup();
	}

	/**
	* Returns <code>true</code> if preferences are unique per layout.
	*
	* @return <code>true</code> if preferences are unique per layout
	*/
	@Override
	public boolean isPreferencesUniquePerLayout() {
		return _portlet.isPreferencesUniquePerLayout();
	}

	/**
	* Returns <code>true</code> if the portlet does not share request
	* attributes with the portal or portlets from another WAR.
	*
	* @return <code>true</code> if the portlet does not share request
	attributes with the portal or portlets from another WAR
	*/
	@Override
	public boolean isPrivateRequestAttributes() {
		return _portlet.isPrivateRequestAttributes();
	}

	/**
	* Returns <code>true</code> if the portlet does not share session
	* attributes with the portal.
	*
	* @return <code>true</code> if the portlet does not share session
	attributes with the portal
	*/
	@Override
	public boolean isPrivateSessionAttributes() {
		return _portlet.isPrivateSessionAttributes();
	}

	/**
	* Returns <code>true</code> if the portlet is ready to be used.
	*
	* @return <code>true</code> if the portlet is ready to be used
	*/
	@Override
	public boolean isReady() {
		return _portlet.isReady();
	}

	/**
	* Returns <code>true</code> if the portlet supports remoting.
	*
	* @return <code>true</code> if the portlet supports remoting
	*/
	@Override
	public boolean isRemoteable() {
		return _portlet.isRemoteable();
	}

	/**
	* Returns <code>true</code> if the portlet will only process namespaced
	* parameters.
	*
	* @return <code>true</code> if the portlet will only process namespaced
	parameters
	*/
	@Override
	public boolean isRequiresNamespacedParameters() {
		return _portlet.isRequiresNamespacedParameters();
	}

	/**
	* Returns <code>true</code> if the portlet restores to the current view
	* from the maximized state.
	*
	* @return <code>true</code> if the portlet restores to the current view
	from the maximized state
	*/
	@Override
	public boolean isRestoreCurrentView() {
		return _portlet.isRestoreCurrentView();
	}

	/**
	* Returns <code>true</code> if the portlet supports scoping of data.
	*
	* @return <code>true</code> if the portlet supports scoping of data
	*/
	@Override
	public boolean isScopeable() {
		return _portlet.isScopeable();
	}

	/**
	* Returns <code>true</code> if users are shown that they do not have access
	* to the portlet.
	*
	* @return <code>true</code> if users are shown that they do not have access
	to the portlet
	*/
	@Override
	public boolean isShowPortletAccessDenied() {
		return _portlet.isShowPortletAccessDenied();
	}

	/**
	* Returns <code>true</code> if users are shown that the portlet is
	* inactive.
	*
	* @return <code>true</code> if users are shown that the portlet is inactive
	*/
	@Override
	public boolean isShowPortletInactive() {
		return _portlet.isShowPortletInactive();
	}

	/**
	* Returns <code>true</code> if the portlet uses Single Page Application.
	*
	* @return <code>true</code> if the portlet uses Single Page Application
	*/
	@Override
	public boolean isSinglePageApplication() {
		return _portlet.isSinglePageApplication();
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet that is
	* cannot be moved.
	*
	* @return <code>true</code> if the portlet is a static portlet that is
	cannot be moved
	*/
	@Override
	public boolean isStatic() {
		return _portlet.isStatic();
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the end
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the end
	of a list of portlets
	*/
	@Override
	public boolean isStaticEnd() {
		return _portlet.isStaticEnd();
	}

	/**
	* Returns <code>true</code> if the portlet is a static portlet at the start
	* of a list of portlets.
	*
	* @return <code>true</code> if the portlet is a static portlet at the start
	of a list of portlets
	*/
	@Override
	public boolean isStaticStart() {
		return _portlet.isStaticStart();
	}

	/**
	* Returns <code>true</code> if the portlet is a system portlet that a user
	* cannot manually add to their page.
	*
	* @return <code>true</code> if the portlet is a system portlet that a user
	cannot manually add to their page
	*/
	@Override
	public boolean isSystem() {
		return _portlet.isSystem();
	}

	/**
	* Returns <code>true</code> if the portlet is an undeployed portlet.
	*
	* @return <code>true</code> if the portlet is a placeholder of an
	undeployed portlet
	*/
	@Override
	public boolean isUndeployedPortlet() {
		return _portlet.isUndeployedPortlet();
	}

	/**
	* Returns <code>true</code> if the portlet uses the default template.
	*
	* @return <code>true</code> if the portlet uses the default template
	*/
	@Override
	public boolean isUseDefaultTemplate() {
		return _portlet.isUseDefaultTemplate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _portlet.getExpandoBridge();
	}

	/**
	* Returns the portlet data handler instance of the portlet.
	*
	* @return the portlet data handler instance of the portlet
	*/
	@Override
	public com.liferay.exportimport.kernel.lar.PortletDataHandler getPortletDataHandlerInstance() {
		return _portlet.getPortletDataHandlerInstance();
	}

	/**
	* Returns <code>true</code> if the portlet is found in a WAR file.
	*
	* @param portletId the cloned instance portlet ID
	* @return a cloned instance of the portlet
	*/
	@Override
	public Portlet getClonedInstance(java.lang.String portletId) {
		return _portlet.getClonedInstance(portletId);
	}

	/**
	* Returns the root portlet of this portlet instance.
	*
	* @return the root portlet of this portlet instance
	*/
	@Override
	public Portlet getRootPortlet() {
		return _portlet.getRootPortlet();
	}

	/**
	* Returns this portlet's plugin package.
	*
	* @return this portlet's plugin package
	*/
	@Override
	public com.liferay.portal.kernel.plugin.PluginPackage getPluginPackage() {
		return _portlet.getPluginPackage();
	}

	/**
	* Returns the poller processor instance of the portlet.
	*
	* @return the poller processor instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.poller.PollerProcessor getPollerProcessorInstance() {
		return _portlet.getPollerProcessorInstance();
	}

	/**
	* Returns the POP message listener instance of the portlet.
	*
	* @return the POP message listener instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.pop.MessageListener getPopMessageListenerInstance() {
		return _portlet.getPopMessageListenerInstance();
	}

	/**
	* Returns the configuration action instance of the portlet.
	*
	* @return the configuration action instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.portlet.ConfigurationAction getConfigurationActionInstance() {
		return _portlet.getConfigurationActionInstance();
	}

	/**
	* Returns an instance of the class that will control when the portlet will
	* be shown in the Control Panel.
	*
	* @return the instance of the class that will control when the portlet will
	be shown in the Control Panel
	*/
	@Override
	public com.liferay.portal.kernel.portlet.ControlPanelEntry getControlPanelEntryInstance() {
		return _portlet.getControlPanelEntryInstance();
	}

	/**
	* Returns the friendly URL mapper instance of the portlet.
	*
	* @return the friendly URL mapper instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.portlet.FriendlyURLMapper getFriendlyURLMapperInstance() {
		return _portlet.getFriendlyURLMapperInstance();
	}

	/**
	* Returns the portlet layout listener instance of the portlet.
	*
	* @return the portlet layout listener instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.portlet.PortletLayoutListener getPortletLayoutListenerInstance() {
		return _portlet.getPortletLayoutListenerInstance();
	}

	/**
	* Returns the indexer instance of the portlet.
	*
	* @return the indexer instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.search.OpenSearch getOpenSearchInstance() {
		return _portlet.getOpenSearchInstance();
	}

	/**
	* Returns the permission propagator instance of the portlet.
	*
	* @return the permission propagator instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.security.permission.PermissionPropagator getPermissionPropagatorInstance() {
		return _portlet.getPermissionPropagatorInstance();
	}

	/**
	* Returns the URL encoder instance of the portlet.
	*
	* @return the URL encoder instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.servlet.URLEncoder getURLEncoderInstance() {
		return _portlet.getURLEncoderInstance();
	}

	/**
	* Returns the template handler instance of the portlet.
	*
	* @return the template handler instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.template.TemplateHandler getTemplateHandlerInstance() {
		return _portlet.getTemplateHandlerInstance();
	}

	/**
	* Returns the name of the WebDAV storage instance of the portlet.
	*
	* @return the name of the WebDAV storage instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.webdav.WebDAVStorage getWebDAVStorageInstance() {
		return _portlet.getWebDAVStorageInstance();
	}

	/**
	* Returns the processing event from a namespace URI and a local part.
	*
	* @param uri the namespace URI
	* @param localPart the local part
	* @return the processing event from a namespace URI and a local part
	*/
	@Override
	public com.liferay.portal.kernel.xml.QName getProcessingEvent(
		java.lang.String uri, java.lang.String localPart) {
		return _portlet.getProcessingEvent(uri, localPart);
	}

	/**
	* Returns the name of the XML-RPC method instance of the portlet.
	*
	* @return the name of the XML-RPC method instance of the portlet
	*/
	@Override
	public com.liferay.portal.kernel.xmlrpc.Method getXmlRpcMethodInstance() {
		return _portlet.getXmlRpcMethodInstance();
	}

	/**
	* Returns the name of the social request interpreter instance of the
	* portlet.
	*
	* @return the name of the social request interpreter instance of the
	portlet
	*/
	@Override
	public com.liferay.social.kernel.model.SocialRequestInterpreter getSocialRequestInterpreterInstance() {
		return _portlet.getSocialRequestInterpreterInstance();
	}

	/**
	* Returns the relative weight of the portlet with respect to the other
	* portlets in the same category of the Control Panel.
	*
	* @return the relative weight of the portlet with respect to the other
	portlets in the same category of the Control Panel
	*/
	@Override
	public double getControlPanelEntryWeight() {
		return _portlet.getControlPanelEntryWeight();
	}

	@Override
	public int compareTo(Portlet portlet) {
		return _portlet.compareTo(portlet);
	}

	/**
	* Returns the action timeout of the portlet.
	*
	* @return the action timeout of the portlet
	*/
	@Override
	public int getActionTimeout() {
		return _portlet.getActionTimeout();
	}

	/**
	* Returns the render timeout of the portlet.
	*
	* @return the render timeout of the portlet
	*/
	@Override
	public int getRenderTimeout() {
		return _portlet.getRenderTimeout();
	}

	/**
	* Returns the render weight of the portlet.
	*
	* @return the render weight of the portlet
	*/
	@Override
	public int getRenderWeight() {
		return _portlet.getRenderWeight();
	}

	@Override
	public int hashCode() {
		return _portlet.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _portlet.getPrimaryKeyObj();
	}

	/**
	* Returns expiration cache of the portlet.
	*
	* @return expiration cache of the portlet
	*/
	@Override
	public java.lang.Integer getExpCache() {
		return _portlet.getExpCache();
	}

	@Override
	public java.lang.Object clone() {
		return new PortletWrapper((Portlet)_portlet.clone());
	}

	/**
	* Returns the configuration action class of the portlet.
	*
	* @return the configuration action class of the portlet
	*/
	@Override
	public java.lang.String getConfigurationActionClass() {
		return _portlet.getConfigurationActionClass();
	}

	/**
	* Returns the servlet context name of the portlet.
	*
	* @return the servlet context name of the portlet
	*/
	@Override
	public java.lang.String getContextName() {
		return _portlet.getContextName();
	}

	/**
	* Returns the servlet context path of the portlet.
	*
	* @return the servlet context path of the portlet
	*/
	@Override
	public java.lang.String getContextPath() {
		return _portlet.getContextPath();
	}

	/**
	* Returns the name of the category of the Control Panel where the portlet
	* will be shown.
	*
	* @return the name of the category of the Control Panel where the portlet
	will be shown
	*/
	@Override
	public java.lang.String getControlPanelEntryCategory() {
		return _portlet.getControlPanelEntryCategory();
	}

	/**
	* Returns the name of the class that will control when the portlet will be
	* shown in the Control Panel.
	*
	* @return the name of the class that will control when the portlet will be
	shown in the Control Panel
	*/
	@Override
	public java.lang.String getControlPanelEntryClass() {
		return _portlet.getControlPanelEntryClass();
	}

	/**
	* Returns the name of the CSS class that will be injected in the DIV that
	* wraps this portlet.
	*
	* @return the name of the CSS class that will be injected in the DIV that
	wraps this portlet
	*/
	@Override
	public java.lang.String getCssClassWrapper() {
		return _portlet.getCssClassWrapper();
	}

	/**
	* Returns the default preferences of the portlet.
	*
	* @return the default preferences of the portlet
	*/
	@Override
	public java.lang.String getDefaultPreferences() {
		return _portlet.getDefaultPreferences();
	}

	/**
	* Returns the display name of the portlet.
	*
	* @return the display name of the portlet
	*/
	@Override
	public java.lang.String getDisplayName() {
		return _portlet.getDisplayName();
	}

	/**
	* Returns the Facebook integration method of the portlet.
	*
	* @return the Facebook integration method of the portlet
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public java.lang.String getFacebookIntegration() {
		return _portlet.getFacebookIntegration();
	}

	/**
	* Returns the name of the friendly URL mapper class of the portlet.
	*
	* @return the name of the friendly URL mapper class of the portlet
	*/
	@Override
	public java.lang.String getFriendlyURLMapperClass() {
		return _portlet.getFriendlyURLMapperClass();
	}

	/**
	* Returns the name of the friendly URL mapping of the portlet.
	*
	* @return the name of the friendly URL mapping of the portlet
	*/
	@Override
	public java.lang.String getFriendlyURLMapping() {
		return _portlet.getFriendlyURLMapping();
	}

	/**
	* Returns the class loader resource path to the friendly URL routes of the
	* portlet.
	*
	* @return the class loader resource path to the friendly URL routes of the
	portlet
	*/
	@Override
	public java.lang.String getFriendlyURLRoutes() {
		return _portlet.getFriendlyURLRoutes();
	}

	/**
	* Returns the icon of the portlet.
	*
	* @return the icon of the portlet
	*/
	@Override
	public java.lang.String getIcon() {
		return _portlet.getIcon();
	}

	/**
	* Returns the instance ID of the portlet.
	*
	* @return the instance ID of the portlet
	*/
	@Override
	public java.lang.String getInstanceId() {
		return _portlet.getInstanceId();
	}

	/**
	* Returns the name of the open search class of the portlet.
	*
	* @return the name of the open search class of the portlet
	*/
	@Override
	public java.lang.String getOpenSearchClass() {
		return _portlet.getOpenSearchClass();
	}

	/**
	* Returns the parent struts path of the portlet.
	*
	* @return the parent struts path of the portlet.
	*/
	@Override
	public java.lang.String getParentStrutsPath() {
		return _portlet.getParentStrutsPath();
	}

	/**
	* Returns the name of the permission propagator class of the portlet.
	*
	* @return the name of the permission propagator class of the portlet
	*/
	@Override
	public java.lang.String getPermissionPropagatorClass() {
		return _portlet.getPermissionPropagatorClass();
	}

	/**
	* Returns the plugin ID of the portlet.
	*
	* @return the plugin ID of the portlet
	*/
	@Override
	public java.lang.String getPluginId() {
		return _portlet.getPluginId();
	}

	/**
	* Returns the plugin type of the portlet.
	*
	* @return the plugin type of the portlet
	*/
	@Override
	public java.lang.String getPluginType() {
		return _portlet.getPluginType();
	}

	/**
	* Returns the name of the poller processor class of the portlet.
	*
	* @return the name of the poller processor class of the portlet
	*/
	@Override
	public java.lang.String getPollerProcessorClass() {
		return _portlet.getPollerProcessorClass();
	}

	/**
	* Returns the name of the POP message listener class of the portlet.
	*
	* @return the name of the POP message listener class of the portlet
	*/
	@Override
	public java.lang.String getPopMessageListenerClass() {
		return _portlet.getPopMessageListenerClass();
	}

	/**
	* Returns the name of the portlet class of the portlet.
	*
	* @return the name of the portlet class of the portlet
	*/
	@Override
	public java.lang.String getPortletClass() {
		return _portlet.getPortletClass();
	}

	/**
	* Returns the name of the portlet data handler class of the portlet.
	*
	* @return the name of the portlet data handler class of the portlet
	*/
	@Override
	public java.lang.String getPortletDataHandlerClass() {
		return _portlet.getPortletDataHandlerClass();
	}

	/**
	* Returns the portlet ID of this portlet.
	*
	* @return the portlet ID of this portlet
	*/
	@Override
	public java.lang.String getPortletId() {
		return _portlet.getPortletId();
	}

	/**
	* Returns the name of the portlet layout listener class of the portlet.
	*
	* @return the name of the portlet layout listener class of the portlet
	*/
	@Override
	public java.lang.String getPortletLayoutListenerClass() {
		return _portlet.getPortletLayoutListenerClass();
	}

	/**
	* Returns the name of the portlet.
	*
	* @return the display name of the portlet
	*/
	@Override
	public java.lang.String getPortletName() {
		return _portlet.getPortletName();
	}

	/**
	* Returns the name of the portlet URL class of the portlet.
	*
	* @return the name of the portlet URL class of the portlet
	*/
	@Override
	public java.lang.String getPortletURLClass() {
		return _portlet.getPortletURLClass();
	}

	/**
	* Returns the name of the preferences validator class of the portlet.
	*
	* @return the name of the preferences validator class of the portlet
	*/
	@Override
	public java.lang.String getPreferencesValidator() {
		return _portlet.getPreferencesValidator();
	}

	/**
	* Returns the resource bundle of the portlet.
	*
	* @return resource bundle of the portlet
	*/
	@Override
	public java.lang.String getResourceBundle() {
		return _portlet.getResourceBundle();
	}

	/**
	* Returns the roles of this portlet.
	*
	* @return the roles of this portlet
	*/
	@Override
	public java.lang.String getRoles() {
		return _portlet.getRoles();
	}

	/**
	* Returns the root portlet ID of the portlet.
	*
	* @return the root portlet ID of the portlet
	*/
	@Override
	public java.lang.String getRootPortletId() {
		return _portlet.getRootPortletId();
	}

	/**
	* Returns the name of the social request interpreter class of the portlet.
	*
	* @return the name of the social request interpreter class of the portlet
	*/
	@Override
	public java.lang.String getSocialRequestInterpreterClass() {
		return _portlet.getSocialRequestInterpreterClass();
	}

	/**
	* Returns the path for static resources served by this portlet.
	*
	* @return the path for static resources served by this portlet
	*/
	@Override
	public java.lang.String getStaticResourcePath() {
		return _portlet.getStaticResourcePath();
	}

	/**
	* Returns the struts path of the portlet.
	*
	* @return the struts path of the portlet
	*/
	@Override
	public java.lang.String getStrutsPath() {
		return _portlet.getStrutsPath();
	}

	/**
	* Returns the name of the template handler class of the portlet.
	*
	* @return the name of the template handler class of the portlet
	*/
	@Override
	public java.lang.String getTemplateHandlerClass() {
		return _portlet.getTemplateHandlerClass();
	}

	/**
	* Returns the name of the URL encoder class of the portlet.
	*
	* @return the name of the URL encoder class of the portlet
	*/
	@Override
	public java.lang.String getURLEncoderClass() {
		return _portlet.getURLEncoderClass();
	}

	/**
	* Returns the class loader resource path to the use notification
	* definitions of the portlet.
	*
	* @return the class loader resource path to the use notification
	definitions of the portlet
	*/
	@Override
	public java.lang.String getUserNotificationDefinitions() {
		return _portlet.getUserNotificationDefinitions();
	}

	/**
	* Returns the user principal strategy of the portlet.
	*
	* @return the user principal strategy of the portlet
	*/
	@Override
	public java.lang.String getUserPrincipalStrategy() {
		return _portlet.getUserPrincipalStrategy();
	}

	/**
	* Returns the virtual path of the portlet.
	*
	* @return the virtual path of the portlet
	*/
	@Override
	public java.lang.String getVirtualPath() {
		return _portlet.getVirtualPath();
	}

	/**
	* Returns the name of the WebDAV storage class of the portlet.
	*
	* @return the name of the WebDAV storage class of the portlet
	*/
	@Override
	public java.lang.String getWebDAVStorageClass() {
		return _portlet.getWebDAVStorageClass();
	}

	/**
	* Returns the name of the WebDAV storage token of the portlet.
	*
	* @return the name of the WebDAV storage token of the portlet
	*/
	@Override
	public java.lang.String getWebDAVStorageToken() {
		return _portlet.getWebDAVStorageToken();
	}

	/**
	* Returns the name of the XML-RPC method class of the portlet.
	*
	* @return the name of the XML-RPC method class of the portlet
	*/
	@Override
	public java.lang.String getXmlRpcMethodClass() {
		return _portlet.getXmlRpcMethodClass();
	}

	@Override
	public java.lang.String toString() {
		return _portlet.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _portlet.toXmlString();
	}

	/**
	* Returns an array of required roles of the portlet.
	*
	* @return an array of required roles of the portlet
	*/
	@Override
	public java.lang.String[] getRolesArray() {
		return _portlet.getRolesArray();
	}

	/**
	* Returns the names of the classes that represent asset types associated
	* with the portlet.
	*
	* @return the names of the classes that represent asset types associated
	with the portlet
	*/
	@Override
	public java.util.List<java.lang.String> getAssetRendererFactoryClasses() {
		return _portlet.getAssetRendererFactoryClasses();
	}

	/**
	* Returns the asset type instances of the portlet.
	*
	* @return the asset type instances of the portlet
	*/
	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetRendererFactory<?>> getAssetRendererFactoryInstances() {
		return _portlet.getAssetRendererFactoryInstances();
	}

	/**
	* Returns the names of the classes that represent atom collection adapters
	* associated with the portlet.
	*
	* @return the names of the classes that represent atom collection adapters
	associated with the portlet
	*/
	@Override
	public java.util.List<java.lang.String> getAtomCollectionAdapterClasses() {
		return _portlet.getAtomCollectionAdapterClasses();
	}

	/**
	* Returns the atom collection adapter instances of the portlet.
	*
	* @return the atom collection adapter instances of the portlet
	*/
	@Override
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
	@Override
	public java.util.List<java.lang.String> getCustomAttributesDisplayClasses() {
		return _portlet.getCustomAttributesDisplayClasses();
	}

	/**
	* Returns the custom attribute display instances of the portlet.
	*
	* @return the custom attribute display instances of the portlet
	*/
	@Override
	public java.util.List<com.liferay.expando.kernel.model.CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		return _portlet.getCustomAttributesDisplayInstances();
	}

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* footer relative to the portal's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	footer relative to the portal's context path
	*/
	@Override
	public java.util.List<java.lang.String> getFooterPortalCss() {
		return _portlet.getFooterPortalCss();
	}

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's footer relative to the portal's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's footer relative to the portal's context path
	*/
	@Override
	public java.util.List<java.lang.String> getFooterPortalJavaScript() {
		return _portlet.getFooterPortalJavaScript();
	}

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* footer relative to the portlet's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	footer relative to the portlet's context path
	*/
	@Override
	public java.util.List<java.lang.String> getFooterPortletCss() {
		return _portlet.getFooterPortletCss();
	}

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's footer relative to the portlet's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's footer relative to the portlet's context path
	*/
	@Override
	public java.util.List<java.lang.String> getFooterPortletJavaScript() {
		return _portlet.getFooterPortletJavaScript();
	}

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* header relative to the portal's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	header relative to the portal's context path
	*/
	@Override
	public java.util.List<java.lang.String> getHeaderPortalCss() {
		return _portlet.getHeaderPortalCss();
	}

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's header relative to the portal's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's header relative to the portal's context path
	*/
	@Override
	public java.util.List<java.lang.String> getHeaderPortalJavaScript() {
		return _portlet.getHeaderPortalJavaScript();
	}

	/**
	* Returns a list of CSS files that will be referenced from the page's
	* header relative to the portlet's context path.
	*
	* @return a list of CSS files that will be referenced from the page's
	header relative to the portlet's context path
	*/
	@Override
	public java.util.List<java.lang.String> getHeaderPortletCss() {
		return _portlet.getHeaderPortletCss();
	}

	/**
	* Returns a list of JavaScript files that will be referenced from the
	* page's header relative to the portlet's context path.
	*
	* @return a list of JavaScript files that will be referenced from the
	page's header relative to the portlet's context path
	*/
	@Override
	public java.util.List<java.lang.String> getHeaderPortletJavaScript() {
		return _portlet.getHeaderPortletJavaScript();
	}

	/**
	* Returns the names of the classes that represent indexers associated with
	* the portlet.
	*
	* @return the names of the classes that represent indexers associated with
	the portlet
	*/
	@Override
	public java.util.List<java.lang.String> getIndexerClasses() {
		return _portlet.getIndexerClasses();
	}

	/**
	* Returns the indexer instances of the portlet.
	*
	* @return the indexer instances of the portlet
	*/
	@Override
	public java.util.List<com.liferay.portal.kernel.search.Indexer<?>> getIndexerInstances() {
		return _portlet.getIndexerInstances();
	}

	/**
	* Returns the scheduler entries of the portlet.
	*
	* @return the scheduler entries of the portlet
	*/
	@Override
	public java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> getSchedulerEntries() {
		return _portlet.getSchedulerEntries();
	}

	/**
	* Returns the names of the classes that represent social activity
	* interpreters associated with the portlet.
	*
	* @return the names of the classes that represent social activity
	interpreters associated with the portlet
	*/
	@Override
	public java.util.List<java.lang.String> getSocialActivityInterpreterClasses() {
		return _portlet.getSocialActivityInterpreterClasses();
	}

	/**
	* Returns the social activity interpreter instances of the portlet.
	*
	* @return the social activity interpreter instances of the portlet
	*/
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivityInterpreter> getSocialActivityInterpreterInstances() {
		return _portlet.getSocialActivityInterpreterInstances();
	}

	/**
	* Returns the names of the classes that represent staged model data
	* handlers associated with the portlet.
	*
	* @return the names of the classes that represent staged model data
	handlers associated with the portlet
	*/
	@Override
	public java.util.List<java.lang.String> getStagedModelDataHandlerClasses() {
		return _portlet.getStagedModelDataHandlerClasses();
	}

	/**
	* Returns the staged model data handler instances of the portlet.
	*
	* @return the staged model data handler instances of the portlet
	*/
	@Override
	public java.util.List<com.liferay.exportimport.kernel.lar.StagedModelDataHandler<?>> getStagedModelDataHandlerInstances() {
		return _portlet.getStagedModelDataHandlerInstances();
	}

	/**
	* Returns the names of the classes that represent trash handlers associated
	* with the portlet.
	*
	* @return the names of the classes that represent trash handlers associated
	with the portlet
	*/
	@Override
	public java.util.List<java.lang.String> getTrashHandlerClasses() {
		return _portlet.getTrashHandlerClasses();
	}

	/**
	* Returns the trash handler instances of the portlet.
	*
	* @return the trash handler instances of the portlet
	*/
	@Override
	public java.util.List<com.liferay.portal.kernel.trash.TrashHandler> getTrashHandlerInstances() {
		return _portlet.getTrashHandlerInstances();
	}

	/**
	* Returns the names of the classes that represent user notification
	* handlers associated with the portlet.
	*
	* @return the names of the classes that represent user notification
	handlers associated with the portlet
	*/
	@Override
	public java.util.List<java.lang.String> getUserNotificationHandlerClasses() {
		return _portlet.getUserNotificationHandlerClasses();
	}

	/**
	* Returns the user notification handler instances of the portlet.
	*
	* @return the user notification handler instances of the portlet
	*/
	@Override
	public java.util.List<com.liferay.portal.kernel.notifications.UserNotificationHandler> getUserNotificationHandlerInstances() {
		return _portlet.getUserNotificationHandlerInstances();
	}

	/**
	* Returns the names of the classes that represent workflow handlers
	* associated with the portlet.
	*
	* @return the names of the classes that represent workflow handlers
	associated with the portlet
	*/
	@Override
	public java.util.List<java.lang.String> getWorkflowHandlerClasses() {
		return _portlet.getWorkflowHandlerClasses();
	}

	/**
	* Returns the workflow handler instances of the portlet.
	*
	* @return the workflow handler instances of the portlet
	*/
	@Override
	public java.util.List<com.liferay.portal.kernel.workflow.WorkflowHandler<?>> getWorkflowHandlerInstances() {
		return _portlet.getWorkflowHandlerInstances();
	}

	/**
	* Returns the init parameters of the portlet.
	*
	* @return init parameters of the portlet
	*/
	@Override
	public Map<java.lang.String, java.lang.String> getInitParams() {
		return _portlet.getInitParams();
	}

	/**
	* Returns the filters of the portlet.
	*
	* @return filters of the portlet
	*/
	@Override
	public Map<java.lang.String, PortletFilter> getPortletFilters() {
		return _portlet.getPortletFilters();
	}

	/**
	* Returns the portlet modes of the portlet.
	*
	* @return portlet modes of the portlet
	*/
	@Override
	public Map<java.lang.String, java.util.Set<java.lang.String>> getPortletModes() {
		return _portlet.getPortletModes();
	}

	/**
	* Returns the role mappers of the portlet.
	*
	* @return role mappers of the portlet
	*/
	@Override
	public Map<java.lang.String, java.lang.String> getRoleMappers() {
		return _portlet.getRoleMappers();
	}

	/**
	* Returns the window states of the portlet.
	*
	* @return window states of the portlet
	*/
	@Override
	public Map<java.lang.String, java.util.Set<java.lang.String>> getWindowStates() {
		return _portlet.getWindowStates();
	}

	/**
	* Returns the portlet modes of the portlet.
	*
	* @return the portlet modes of the portlet
	*/
	@Override
	public java.util.Set<java.lang.String> getAllPortletModes() {
		return _portlet.getAllPortletModes();
	}

	/**
	* Returns the window states of the portlet.
	*
	* @return the window states of the portlet
	*/
	@Override
	public java.util.Set<java.lang.String> getAllWindowStates() {
		return _portlet.getAllWindowStates();
	}

	/**
	* Returns the application types of the portlet.
	*
	* @return the application types of the portlet
	*/
	@Override
	public java.util.Set<com.liferay.portal.kernel.application.type.ApplicationType> getApplicationTypes() {
		return _portlet.getApplicationTypes();
	}

	/**
	* Returns the names of the parameters that will be automatically propagated
	* through the portlet.
	*
	* @return the names of the parameters that will be automatically propagated
	through the portlet
	*/
	@Override
	public java.util.Set<java.lang.String> getAutopropagatedParameters() {
		return _portlet.getAutopropagatedParameters();
	}

	/**
	* Returns the processing events of the portlet.
	*
	* @return the processing events of the portlet
	*/
	@Override
	public java.util.Set<com.liferay.portal.kernel.xml.QName> getProcessingEvents() {
		return _portlet.getProcessingEvents();
	}

	/**
	* Returns the public render parameters of the portlet.
	*
	* @return the public render parameters of the portlet
	*/
	@Override
	public java.util.Set<PublicRenderParameter> getPublicRenderParameters() {
		return _portlet.getPublicRenderParameters();
	}

	/**
	* Returns the publishing events of the portlet.
	*
	* @return the publishing events of the portlet
	*/
	@Override
	public java.util.Set<com.liferay.portal.kernel.xml.QName> getPublishingEvents() {
		return _portlet.getPublishingEvents();
	}

	/**
	* Returns the supported locales of the portlet.
	*
	* @return the supported locales of the portlet
	*/
	@Override
	public java.util.Set<java.lang.String> getSupportedLocales() {
		return _portlet.getSupportedLocales();
	}

	/**
	* Returns the unlinked roles of the portlet.
	*
	* @return unlinked roles of the portlet
	*/
	@Override
	public java.util.Set<java.lang.String> getUnlinkedRoles() {
		return _portlet.getUnlinkedRoles();
	}

	/**
	* Returns the company ID of this portlet.
	*
	* @return the company ID of this portlet
	*/
	@Override
	public long getCompanyId() {
		return _portlet.getCompanyId();
	}

	/**
	* Returns the ID of this portlet.
	*
	* @return the ID of this portlet
	*/
	@Override
	public long getId() {
		return _portlet.getId();
	}

	/**
	* Returns the mvcc version of this portlet.
	*
	* @return the mvcc version of this portlet
	*/
	@Override
	public long getMvccVersion() {
		return _portlet.getMvccVersion();
	}

	/**
	* Returns the primary key of this portlet.
	*
	* @return the primary key of this portlet
	*/
	@Override
	public long getPrimaryKey() {
		return _portlet.getPrimaryKey();
	}

	/**
	* Returns the timestamp of the portlet.
	*
	* @return the timestamp of the portlet
	*/
	@Override
	public long getTimestamp() {
		return _portlet.getTimestamp();
	}

	/**
	* Returns the user ID of the portlet. This only applies when the portlet is
	* added by a user in a customizable layout.
	*
	* @return the user ID of the portlet
	*/
	@Override
	public long getUserId() {
		return _portlet.getUserId();
	}

	/**
	* Adds an application type.
	*
	* @param applicationType an application type
	*/
	@Override
	public void addApplicationType(
		com.liferay.portal.kernel.application.type.ApplicationType applicationType) {
		_portlet.addApplicationType(applicationType);
	}

	/**
	* Adds a processing event.
	*/
	@Override
	public void addProcessingEvent(
		com.liferay.portal.kernel.xml.QName processingEvent) {
		_portlet.addProcessingEvent(processingEvent);
	}

	/**
	* Adds a public render parameter.
	*
	* @param publicRenderParameter a public render parameter
	*/
	@Override
	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter) {
		_portlet.addPublicRenderParameter(publicRenderParameter);
	}

	/**
	* Adds a publishing event.
	*/
	@Override
	public void addPublishingEvent(
		com.liferay.portal.kernel.xml.QName publishingEvent) {
		_portlet.addPublishingEvent(publishingEvent);
	}

	/**
	* Adds a scheduler entry.
	*/
	@Override
	public void addSchedulerEntry(
		com.liferay.portal.kernel.scheduler.SchedulerEntry schedulerEntry) {
		_portlet.addSchedulerEntry(schedulerEntry);
	}

	/**
	* Link the role names set in portlet.xml with the Liferay roles set in
	* liferay-portlet.xml.
	*/
	@Override
	public void linkRoles() {
		_portlet.linkRoles();
	}

	@Override
	public void persist() {
		_portlet.persist();
	}

	/**
	* Sets the action timeout of the portlet.
	*
	* @param actionTimeout the action timeout of the portlet
	*/
	@Override
	public void setActionTimeout(int actionTimeout) {
		_portlet.setActionTimeout(actionTimeout);
	}

	/**
	* Set to <code>true</code> if an action URL for this portlet should cause
	* an auto redirect.
	*
	* @param actionURLRedirect boolean value for whether an action URL for this
	portlet should cause an auto redirect
	*/
	@Override
	public void setActionURLRedirect(boolean actionURLRedirect) {
		_portlet.setActionURLRedirect(actionURLRedirect);
	}

	/**
	* Sets whether this portlet is active.
	*
	* @param active the active of this portlet
	*/
	@Override
	public void setActive(boolean active) {
		_portlet.setActive(active);
	}

	/**
	* Set to <code>true</code> if default resources for the portlet are added
	* to a page.
	*
	* @param addDefaultResource boolean value for whether or not default
	resources for the portlet are added to a page
	*/
	@Override
	public void setAddDefaultResource(boolean addDefaultResource) {
		_portlet.setAddDefaultResource(addDefaultResource);
	}

	/**
	* Set to <code>true</code> if the portlet can be displayed via Ajax.
	*
	* @param ajaxable boolean value for whether the portlet can be displayed
	via Ajax
	*/
	@Override
	public void setAjaxable(boolean ajaxable) {
		_portlet.setAjaxable(ajaxable);
	}

	/**
	* Sets the application types of the portlet.
	*
	* @param applicationTypes the application types of the portlet
	*/
	@Override
	public void setApplicationTypes(
		java.util.Set<com.liferay.portal.kernel.application.type.ApplicationType> applicationTypes) {
		_portlet.setApplicationTypes(applicationTypes);
	}

	/**
	* Sets the names of the classes that represent asset types associated with
	* the portlet.
	*
	* @param assetRendererFactoryClasses the names of the classes that
	represent asset types associated with the portlet
	*/
	@Override
	public void setAssetRendererFactoryClasses(
		java.util.List<java.lang.String> assetRendererFactoryClasses) {
		_portlet.setAssetRendererFactoryClasses(assetRendererFactoryClasses);
	}

	/**
	* Sets the names of the classes that represent atom collection adapters
	* associated with the portlet.
	*
	* @param atomCollectionAdapterClasses the names of the classes that
	represent atom collection adapters associated with the portlet
	*/
	@Override
	public void setAtomCollectionAdapterClasses(
		java.util.List<java.lang.String> atomCollectionAdapterClasses) {
		_portlet.setAtomCollectionAdapterClasses(atomCollectionAdapterClasses);
	}

	/**
	* Sets the names of the parameters that will be automatically propagated
	* through the portlet.
	*
	* @param autopropagatedParameters the names of the parameters that will be
	automatically propagated through the portlet
	*/
	@Override
	public void setAutopropagatedParameters(
		java.util.Set<java.lang.String> autopropagatedParameters) {
		_portlet.setAutopropagatedParameters(autopropagatedParameters);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_portlet.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this portlet.
	*
	* @param companyId the company ID of this portlet
	*/
	@Override
	public void setCompanyId(long companyId) {
		_portlet.setCompanyId(companyId);
	}

	/**
	* Sets the configuration action class of the portlet.
	*
	* @param configurationActionClass the configuration action class of the
	portlet
	*/
	@Override
	public void setConfigurationActionClass(
		java.lang.String configurationActionClass) {
		_portlet.setConfigurationActionClass(configurationActionClass);
	}

	/**
	* Set the name of the category of the Control Panel where the portlet will
	* be shown.
	*
	* @param controlPanelEntryCategory the name of the category of the Control
	Panel where the portlet will be shown
	*/
	@Override
	public void setControlPanelEntryCategory(
		java.lang.String controlPanelEntryCategory) {
		_portlet.setControlPanelEntryCategory(controlPanelEntryCategory);
	}

	/**
	* Sets the name of the class that will control when the portlet will be
	* shown in the Control Panel.
	*
	* @param controlPanelEntryClass the name of the class that will control
	when the portlet will be shown in the Control Panel
	*/
	@Override
	public void setControlPanelEntryClass(
		java.lang.String controlPanelEntryClass) {
		_portlet.setControlPanelEntryClass(controlPanelEntryClass);
	}

	/**
	* Sets the relative weight of the portlet with respect to the other
	* portlets in the same category of the Control Panel.
	*
	* @param controlPanelEntryWeight the relative weight of the portlet with
	respect to the other portlets in the same category of the Control
	Panel
	*/
	@Override
	public void setControlPanelEntryWeight(double controlPanelEntryWeight) {
		_portlet.setControlPanelEntryWeight(controlPanelEntryWeight);
	}

	/**
	* Sets the name of the CSS class that will be injected in the DIV that
	* wraps this portlet.
	*
	* @param cssClassWrapper the name of the CSS class that will be injected in
	the DIV that wraps this portlet
	*/
	@Override
	public void setCssClassWrapper(java.lang.String cssClassWrapper) {
		_portlet.setCssClassWrapper(cssClassWrapper);
	}

	/**
	* Sets the names of the classes that represent custom attribute displays
	* associated with the portlet.
	*
	* @param customAttributesDisplayClasses the names of the classes that
	represent custom attribute displays associated with the portlet
	*/
	@Override
	public void setCustomAttributesDisplayClasses(
		java.util.List<java.lang.String> customAttributesDisplayClasses) {
		_portlet.setCustomAttributesDisplayClasses(customAttributesDisplayClasses);
	}

	/**
	* Sets the default plugin settings of the portlet.
	*
	* @param pluginSetting the plugin setting
	*/
	@Override
	public void setDefaultPluginSetting(PluginSetting pluginSetting) {
		_portlet.setDefaultPluginSetting(pluginSetting);
	}

	/**
	* Sets the default preferences of the portlet.
	*
	* @param defaultPreferences the default preferences of the portlet
	*/
	@Override
	public void setDefaultPreferences(java.lang.String defaultPreferences) {
		_portlet.setDefaultPreferences(defaultPreferences);
	}

	/**
	* Sets the display name of the portlet.
	*
	* @param displayName the display name of the portlet
	*/
	@Override
	public void setDisplayName(java.lang.String displayName) {
		_portlet.setDisplayName(displayName);
	}

	/**
	* Sets expiration cache of the portlet.
	*
	* @param expCache expiration cache of the portlet
	*/
	@Override
	public void setExpCache(java.lang.Integer expCache) {
		_portlet.setExpCache(expCache);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_portlet.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_portlet.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_portlet.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the Facebook integration method of the portlet.
	*
	* @param facebookIntegration the Facebook integration method of the portlet
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public void setFacebookIntegration(java.lang.String facebookIntegration) {
		_portlet.setFacebookIntegration(facebookIntegration);
	}

	/**
	* Sets a list of CSS files that will be referenced from the page's footer
	* relative to the portal's context path.
	*
	* @param footerPortalCss a list of CSS files that will be referenced from
	the page's footer relative to the portal's context path
	*/
	@Override
	public void setFooterPortalCss(
		java.util.List<java.lang.String> footerPortalCss) {
		_portlet.setFooterPortalCss(footerPortalCss);
	}

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* footer relative to the portal's context path.
	*
	* @param footerPortalJavaScript a list of JavaScript files that will be
	referenced from the page's footer relative to the portal's context
	path
	*/
	@Override
	public void setFooterPortalJavaScript(
		java.util.List<java.lang.String> footerPortalJavaScript) {
		_portlet.setFooterPortalJavaScript(footerPortalJavaScript);
	}

	/**
	* Sets a list of CSS files that will be referenced from the page's footer
	* relative to the portlet's context path.
	*
	* @param footerPortletCss a list of CSS files that will be referenced from
	the page's footer relative to the portlet's context path
	*/
	@Override
	public void setFooterPortletCss(
		java.util.List<java.lang.String> footerPortletCss) {
		_portlet.setFooterPortletCss(footerPortletCss);
	}

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* footer relative to the portlet's context path.
	*
	* @param footerPortletJavaScript a list of JavaScript files that will be
	referenced from the page's footer relative to the portlet's
	context path
	*/
	@Override
	public void setFooterPortletJavaScript(
		java.util.List<java.lang.String> footerPortletJavaScript) {
		_portlet.setFooterPortletJavaScript(footerPortletJavaScript);
	}

	/**
	* Sets the name of the friendly URL mapper class of the portlet.
	*
	* @param friendlyURLMapperClass the name of the friendly URL mapper class
	of the portlet
	*/
	@Override
	public void setFriendlyURLMapperClass(
		java.lang.String friendlyURLMapperClass) {
		_portlet.setFriendlyURLMapperClass(friendlyURLMapperClass);
	}

	/**
	* Sets the name of the friendly URL mapping of the portlet.
	*
	* @param friendlyURLMapping the name of the friendly URL mapping of the
	portlet
	*/
	@Override
	public void setFriendlyURLMapping(java.lang.String friendlyURLMapping) {
		_portlet.setFriendlyURLMapping(friendlyURLMapping);
	}

	/**
	* Sets the class loader resource path to the friendly URL routes of the
	* portlet.
	*
	* @param friendlyURLRoutes the class loader resource path to the friendly
	URL routes of the portlet
	*/
	@Override
	public void setFriendlyURLRoutes(java.lang.String friendlyURLRoutes) {
		_portlet.setFriendlyURLRoutes(friendlyURLRoutes);
	}

	/**
	* Sets a list of CSS files that will be referenced from the page's header
	* relative to the portal's context path.
	*
	* @param headerPortalCss a list of CSS files that will be referenced from
	the page's header relative to the portal's context path
	*/
	@Override
	public void setHeaderPortalCss(
		java.util.List<java.lang.String> headerPortalCss) {
		_portlet.setHeaderPortalCss(headerPortalCss);
	}

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* header relative to the portal's context path.
	*
	* @param headerPortalJavaScript a list of JavaScript files that will be
	referenced from the page's header relative to the portal's context
	path
	*/
	@Override
	public void setHeaderPortalJavaScript(
		java.util.List<java.lang.String> headerPortalJavaScript) {
		_portlet.setHeaderPortalJavaScript(headerPortalJavaScript);
	}

	/**
	* Sets a list of CSS files that will be referenced from the page's header
	* relative to the portlet's context path.
	*
	* @param headerPortletCss a list of CSS files that will be referenced from
	the page's header relative to the portlet's context path
	*/
	@Override
	public void setHeaderPortletCss(
		java.util.List<java.lang.String> headerPortletCss) {
		_portlet.setHeaderPortletCss(headerPortletCss);
	}

	/**
	* Sets a list of JavaScript files that will be referenced from the page's
	* header relative to the portlet's context path.
	*
	* @param headerPortletJavaScript a list of JavaScript files that will be
	referenced from the page's header relative to the portlet's
	context path
	*/
	@Override
	public void setHeaderPortletJavaScript(
		java.util.List<java.lang.String> headerPortletJavaScript) {
		_portlet.setHeaderPortletJavaScript(headerPortletJavaScript);
	}

	/**
	* Sets the icon of the portlet.
	*
	* @param icon the icon of the portlet
	*/
	@Override
	public void setIcon(java.lang.String icon) {
		_portlet.setIcon(icon);
	}

	/**
	* Sets the ID of this portlet.
	*
	* @param id the ID of this portlet
	*/
	@Override
	public void setId(long id) {
		_portlet.setId(id);
	}

	/**
	* Set to <code>true</code> to include the portlet and make it available to
	* be made active.
	*
	* @param include boolean value for whether to include the portlet and make
	it available to be made active
	*/
	@Override
	public void setInclude(boolean include) {
		_portlet.setInclude(include);
	}

	/**
	* Sets the names of the classes that represent indexers associated with the
	* portlet.
	*
	* @param indexerClasses the names of the classes that represent indexers
	associated with the portlet
	*/
	@Override
	public void setIndexerClasses(
		java.util.List<java.lang.String> indexerClasses) {
		_portlet.setIndexerClasses(indexerClasses);
	}

	/**
	* Sets the init parameters of the portlet.
	*
	* @param initParams the init parameters of the portlet
	*/
	@Override
	public void setInitParams(
		Map<java.lang.String, java.lang.String> initParams) {
		_portlet.setInitParams(initParams);
	}

	/**
	* Set to <code>true</code> if the portlet can be added multiple times to a
	* layout.
	*
	* @param instanceable boolean value for whether the portlet can be added
	multiple times to a layout
	*/
	@Override
	public void setInstanceable(boolean instanceable) {
		_portlet.setInstanceable(instanceable);
	}

	/**
	* Set to <code>true</code> to allow the portlet to be cached within the
	* layout.
	*
	* @param layoutCacheable boolean value for whether the portlet can be
	cached within the layout
	*/
	@Override
	public void setLayoutCacheable(boolean layoutCacheable) {
		_portlet.setLayoutCacheable(layoutCacheable);
	}

	/**
	* Set to <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the edit mode.
	*
	* @param maximizeEdit boolean value for whether the portlet goes into the
	maximized state when the user goes into the edit mode
	*/
	@Override
	public void setMaximizeEdit(boolean maximizeEdit) {
		_portlet.setMaximizeEdit(maximizeEdit);
	}

	/**
	* Set to <code>true</code> if the portlet goes into the maximized state
	* when the user goes into the help mode.
	*
	* @param maximizeHelp boolean value for whether the portlet goes into the
	maximized state when the user goes into the help mode
	*/
	@Override
	public void setMaximizeHelp(boolean maximizeHelp) {
		_portlet.setMaximizeHelp(maximizeHelp);
	}

	/**
	* Sets the mvcc version of this portlet.
	*
	* @param mvccVersion the mvcc version of this portlet
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_portlet.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_portlet.setNew(n);
	}

	/**
	* Sets the name of the open search class of the portlet.
	*
	* @param openSearchClass the name of the open search class of the portlet
	*/
	@Override
	public void setOpenSearchClass(java.lang.String openSearchClass) {
		_portlet.setOpenSearchClass(openSearchClass);
	}

	/**
	* Sets the parent struts path of the portlet.
	*
	* @param parentStrutsPath the parent struts path of the portlet
	*/
	@Override
	public void setParentStrutsPath(java.lang.String parentStrutsPath) {
		_portlet.setParentStrutsPath(parentStrutsPath);
	}

	/**
	* Sets the name of the permission propagator class of the portlet.
	*/
	@Override
	public void setPermissionPropagatorClass(
		java.lang.String permissionPropagatorClass) {
		_portlet.setPermissionPropagatorClass(permissionPropagatorClass);
	}

	/**
	* Sets this portlet's plugin package.
	*
	* @param pluginPackage this portlet's plugin package
	*/
	@Override
	public void setPluginPackage(
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		_portlet.setPluginPackage(pluginPackage);
	}

	/**
	* Sets the name of the poller processor class of the portlet.
	*
	* @param pollerProcessorClass the name of the poller processor class of the
	portlet
	*/
	@Override
	public void setPollerProcessorClass(java.lang.String pollerProcessorClass) {
		_portlet.setPollerProcessorClass(pollerProcessorClass);
	}

	/**
	* Sets the name of the POP message listener class of the portlet.
	*
	* @param popMessageListenerClass the name of the POP message listener class
	of the portlet
	*/
	@Override
	public void setPopMessageListenerClass(
		java.lang.String popMessageListenerClass) {
		_portlet.setPopMessageListenerClass(popMessageListenerClass);
	}

	/**
	* Set to <code>true</code> if the portlet goes into the pop up state when
	* the user goes into the print mode.
	*
	* @param popUpPrint boolean value for whether the portlet goes into the pop
	up state when the user goes into the print mode
	*/
	@Override
	public void setPopUpPrint(boolean popUpPrint) {
		_portlet.setPopUpPrint(popUpPrint);
	}

	/**
	* Sets this portlet's application.
	*
	* @param portletApp this portlet's application
	*/
	@Override
	public void setPortletApp(PortletApp portletApp) {
		_portlet.setPortletApp(portletApp);
	}

	/**
	* Sets the name of the portlet class of the portlet.
	*
	* @param portletClass the name of the portlet class of the portlet
	*/
	@Override
	public void setPortletClass(java.lang.String portletClass) {
		_portlet.setPortletClass(portletClass);
	}

	/**
	* Sets the name of the portlet data handler class of the portlet.
	*
	* @param portletDataHandlerClass the name of portlet data handler class of
	the portlet
	*/
	@Override
	public void setPortletDataHandlerClass(
		java.lang.String portletDataHandlerClass) {
		_portlet.setPortletDataHandlerClass(portletDataHandlerClass);
	}

	/**
	* Sets the filters of the portlet.
	*
	* @param portletFilters the filters of the portlet
	*/
	@Override
	public void setPortletFilters(
		Map<java.lang.String, PortletFilter> portletFilters) {
		_portlet.setPortletFilters(portletFilters);
	}

	/**
	* Sets the portlet ID of this portlet.
	*
	* @param portletId the portlet ID of this portlet
	*/
	@Override
	public void setPortletId(java.lang.String portletId) {
		_portlet.setPortletId(portletId);
	}

	/**
	* Sets the portlet info of the portlet.
	*
	* @param portletInfo the portlet info of the portlet
	*/
	@Override
	public void setPortletInfo(PortletInfo portletInfo) {
		_portlet.setPortletInfo(portletInfo);
	}

	/**
	* Sets the name of the portlet layout listener class of the portlet.
	*
	* @param portletLayoutListenerClass the name of the portlet layout listener
	class of the portlet
	*/
	@Override
	public void setPortletLayoutListenerClass(
		java.lang.String portletLayoutListenerClass) {
		_portlet.setPortletLayoutListenerClass(portletLayoutListenerClass);
	}

	/**
	* Sets the portlet modes of the portlet.
	*
	* @param portletModes the portlet modes of the portlet
	*/
	@Override
	public void setPortletModes(
		Map<java.lang.String, java.util.Set<java.lang.String>> portletModes) {
		_portlet.setPortletModes(portletModes);
	}

	/**
	* Sets the name of the portlet.
	*
	* @param portletName the name of the portlet
	*/
	@Override
	public void setPortletName(java.lang.String portletName) {
		_portlet.setPortletName(portletName);
	}

	/**
	* Sets the name of the portlet URL class of the portlet.
	*
	* @param portletURLClass the name of the portlet URL class of the portlet
	*/
	@Override
	public void setPortletURLClass(java.lang.String portletURLClass) {
		_portlet.setPortletURLClass(portletURLClass);
	}

	/**
	* Set to <code>true</code> if preferences are shared across the entire
	* company.
	*
	* @param preferencesCompanyWide boolean value for whether preferences are
	shared across the entire company
	*/
	@Override
	public void setPreferencesCompanyWide(boolean preferencesCompanyWide) {
		_portlet.setPreferencesCompanyWide(preferencesCompanyWide);
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
	@Override
	public void setPreferencesOwnedByGroup(boolean preferencesOwnedByGroup) {
		_portlet.setPreferencesOwnedByGroup(preferencesOwnedByGroup);
	}

	/**
	* Set to <code>true</code> if preferences are unique per layout.
	*
	* @param preferencesUniquePerLayout boolean value for whether preferences
	are unique per layout
	*/
	@Override
	public void setPreferencesUniquePerLayout(
		boolean preferencesUniquePerLayout) {
		_portlet.setPreferencesUniquePerLayout(preferencesUniquePerLayout);
	}

	/**
	* Sets the name of the preferences validator class of the portlet.
	*
	* @param preferencesValidator the name of the preferences validator class
	of the portlet
	*/
	@Override
	public void setPreferencesValidator(java.lang.String preferencesValidator) {
		_portlet.setPreferencesValidator(preferencesValidator);
	}

	/**
	* Sets the primary key of this portlet.
	*
	* @param primaryKey the primary key of this portlet
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_portlet.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_portlet.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Set to <code>true</code> if the portlet does not share request attributes
	* with the portal or portlets from another WAR.
	*
	* @param privateRequestAttributes boolean value for whether the portlet
	shares request attributes with the portal or portlets from another
	WAR
	*/
	@Override
	public void setPrivateRequestAttributes(boolean privateRequestAttributes) {
		_portlet.setPrivateRequestAttributes(privateRequestAttributes);
	}

	/**
	* Set to <code>true</code> if the portlet does not share session attributes
	* with the portal.
	*
	* @param privateSessionAttributes boolean value for whether the portlet
	shares session attributes with the portal
	*/
	@Override
	public void setPrivateSessionAttributes(boolean privateSessionAttributes) {
		_portlet.setPrivateSessionAttributes(privateSessionAttributes);
	}

	/**
	* Sets the processing events of the portlet.
	*
	* @param processingEvents the processing events of the portlet
	*/
	@Override
	public void setProcessingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> processingEvents) {
		_portlet.setProcessingEvents(processingEvents);
	}

	/**
	* Sets the public render parameters of the portlet.
	*
	* @param publicRenderParameters the public render parameters of the portlet
	*/
	@Override
	public void setPublicRenderParameters(
		java.util.Set<PublicRenderParameter> publicRenderParameters) {
		_portlet.setPublicRenderParameters(publicRenderParameters);
	}

	/**
	* Sets the publishing events of the portlet.
	*
	* @param publishingEvents the publishing events of the portlet
	*/
	@Override
	public void setPublishingEvents(
		java.util.Set<com.liferay.portal.kernel.xml.QName> publishingEvents) {
		_portlet.setPublishingEvents(publishingEvents);
	}

	/**
	* Set to <code>true</code> if the portlet is ready to be used.
	*
	* @param ready whether the portlet is ready to be used
	*/
	@Override
	public void setReady(boolean ready) {
		_portlet.setReady(ready);
	}

	/**
	* Set to <code>true</code> if the portlet supports remoting
	*
	* @param remoteable boolean value for whether or not the the portlet
	supports remoting
	*/
	@Override
	public void setRemoteable(boolean remoteable) {
		_portlet.setRemoteable(remoteable);
	}

	/**
	* Sets the render timeout of the portlet.
	*
	* @param renderTimeout the render timeout of the portlet
	*/
	@Override
	public void setRenderTimeout(int renderTimeout) {
		_portlet.setRenderTimeout(renderTimeout);
	}

	/**
	* Sets the render weight of the portlet.
	*
	* @param renderWeight int value for the render weight of the portlet
	*/
	@Override
	public void setRenderWeight(int renderWeight) {
		_portlet.setRenderWeight(renderWeight);
	}

	/**
	* Set to <code>true</code> if the portlet will only process namespaced
	* parameters.
	*
	* @param requiresNamespacedParameters boolean value for whether the portlet
	will only process namespaced parameters
	*/
	@Override
	public void setRequiresNamespacedParameters(
		boolean requiresNamespacedParameters) {
		_portlet.setRequiresNamespacedParameters(requiresNamespacedParameters);
	}

	/**
	* Sets the resource bundle of the portlet.
	*
	* @param resourceBundle the resource bundle of the portlet
	*/
	@Override
	public void setResourceBundle(java.lang.String resourceBundle) {
		_portlet.setResourceBundle(resourceBundle);
	}

	/**
	* Set to <code>true</code> if the portlet restores to the current view from
	* the maximized state.
	*
	* @param restoreCurrentView boolean value for whether the portlet restores
	to the current view from the maximized state
	*/
	@Override
	public void setRestoreCurrentView(boolean restoreCurrentView) {
		_portlet.setRestoreCurrentView(restoreCurrentView);
	}

	/**
	* Sets the role mappers of the portlet.
	*
	* @param roleMappers the role mappers of the portlet
	*/
	@Override
	public void setRoleMappers(
		Map<java.lang.String, java.lang.String> roleMappers) {
		_portlet.setRoleMappers(roleMappers);
	}

	/**
	* Sets the roles of this portlet.
	*
	* @param roles the roles of this portlet
	*/
	@Override
	public void setRoles(java.lang.String roles) {
		_portlet.setRoles(roles);
	}

	/**
	* Sets an array of required roles of the portlet.
	*
	* @param rolesArray an array of required roles of the portlet
	*/
	@Override
	public void setRolesArray(java.lang.String[] rolesArray) {
		_portlet.setRolesArray(rolesArray);
	}

	/**
	* Sets the scheduler entries of the portlet.
	*
	* @param schedulerEntries the scheduler entries of the portlet
	*/
	@Override
	public void setSchedulerEntries(
		java.util.List<com.liferay.portal.kernel.scheduler.SchedulerEntry> schedulerEntries) {
		_portlet.setSchedulerEntries(schedulerEntries);
	}

	/**
	* Set to <code>true</code> if the portlet supports scoping of data.
	*
	* @param scopeable boolean value for whether or not the the portlet
	supports scoping of data
	*/
	@Override
	public void setScopeable(boolean scopeable) {
		_portlet.setScopeable(scopeable);
	}

	/**
	* Set to <code>true</code> if users are shown that they do not have access
	* to the portlet.
	*
	* @param showPortletAccessDenied boolean value for whether users are shown
	that they do not have access to the portlet
	*/
	@Override
	public void setShowPortletAccessDenied(boolean showPortletAccessDenied) {
		_portlet.setShowPortletAccessDenied(showPortletAccessDenied);
	}

	/**
	* Set to <code>true</code> if users are shown that the portlet is inactive.
	*
	* @param showPortletInactive boolean value for whether users are shown that
	the portlet is inactive
	*/
	@Override
	public void setShowPortletInactive(boolean showPortletInactive) {
		_portlet.setShowPortletInactive(showPortletInactive);
	}

	/**
	* Set to <code>true</code> if the portlet uses Single Page Application.
	*
	* @param singlePageApplication boolean value for whether or not the the
	portlet uses Single Page Application
	*/
	@Override
	public void setSinglePageApplication(boolean singlePageApplication) {
		_portlet.setSinglePageApplication(singlePageApplication);
	}

	/**
	* Sets the names of the classes that represent social activity interpreters
	* associated with the portlet.
	*
	* @param socialActivityInterpreterClasses the names of the classes that
	represent social activity interpreters associated with the portlet
	*/
	@Override
	public void setSocialActivityInterpreterClasses(
		java.util.List<java.lang.String> socialActivityInterpreterClasses) {
		_portlet.setSocialActivityInterpreterClasses(socialActivityInterpreterClasses);
	}

	/**
	* Sets the name of the social request interpreter class of the portlet.
	*
	* @param socialRequestInterpreterClass the name of the request interpreter
	class of the portlet
	*/
	@Override
	public void setSocialRequestInterpreterClass(
		java.lang.String socialRequestInterpreterClass) {
		_portlet.setSocialRequestInterpreterClass(socialRequestInterpreterClass);
	}

	/**
	* Sets the names of the classes that represent staged model data handlers
	* associated with the portlet.
	*
	* @param stagedModelDataHandlerClasses the names of the classes that
	represent staged model data handlers associated with the portlet
	*/
	@Override
	public void setStagedModelDataHandlerClasses(
		java.util.List<java.lang.String> stagedModelDataHandlerClasses) {
		_portlet.setStagedModelDataHandlerClasses(stagedModelDataHandlerClasses);
	}

	/**
	* Set to <code>true</code> if the portlet is a static portlet that is
	* cannot be moved.
	*
	* @param staticPortlet boolean value for whether the portlet is a static
	portlet that cannot be moved
	*/
	@Override
	public void setStatic(boolean staticPortlet) {
		_portlet.setStatic(staticPortlet);
	}

	/**
	* Set to <code>true</code> if the portlet is a static portlet at the start
	* of a list of portlets.
	*
	* @param staticPortletStart boolean value for whether the portlet is a
	static portlet at the start of a list of portlets
	*/
	@Override
	public void setStaticStart(boolean staticPortletStart) {
		_portlet.setStaticStart(staticPortletStart);
	}

	/**
	* Sets the struts path of the portlet.
	*
	* @param strutsPath the struts path of the portlet
	*/
	@Override
	public void setStrutsPath(java.lang.String strutsPath) {
		_portlet.setStrutsPath(strutsPath);
	}

	/**
	* Sets the supported locales of the portlet.
	*
	* @param supportedLocales the supported locales of the portlet
	*/
	@Override
	public void setSupportedLocales(
		java.util.Set<java.lang.String> supportedLocales) {
		_portlet.setSupportedLocales(supportedLocales);
	}

	/**
	* Set to <code>true</code> if the portlet is a system portlet that a user
	* cannot manually add to their page.
	*
	* @param system boolean value for whether the portlet is a system portlet
	that a user cannot manually add to their page
	*/
	@Override
	public void setSystem(boolean system) {
		_portlet.setSystem(system);
	}

	/**
	* Sets the name of the template handler class of the portlet.
	*
	* @param templateHandlerClass the name of template handler class of the
	portlet
	*/
	@Override
	public void setTemplateHandlerClass(java.lang.String templateHandlerClass) {
		_portlet.setTemplateHandlerClass(templateHandlerClass);
	}

	/**
	* Sets the names of the classes that represent trash handlers associated to
	* the portlet.
	*
	* @param trashHandlerClasses the names of the classes that represent trash
	handlers associated with the portlet
	*/
	@Override
	public void setTrashHandlerClasses(
		java.util.List<java.lang.String> trashHandlerClasses) {
		_portlet.setTrashHandlerClasses(trashHandlerClasses);
	}

	/**
	* Sets the name of the URL encoder class of the portlet.
	*
	* @param urlEncoderClass the name of the URL encoder class of the portlet
	*/
	@Override
	public void setURLEncoderClass(java.lang.String urlEncoderClass) {
		_portlet.setURLEncoderClass(urlEncoderClass);
	}

	/**
	* Set to <code>true</code> if the portlet is an undeployed portlet.
	*
	* @param undeployedPortlet boolean value for whether the portlet is an
	undeployed portlet
	*/
	@Override
	public void setUndeployedPortlet(boolean undeployedPortlet) {
		_portlet.setUndeployedPortlet(undeployedPortlet);
	}

	/**
	* Sets the unlinked roles of the portlet.
	*
	* @param unlinkedRoles the unlinked roles of the portlet
	*/
	@Override
	public void setUnlinkedRoles(java.util.Set<java.lang.String> unlinkedRoles) {
		_portlet.setUnlinkedRoles(unlinkedRoles);
	}

	/**
	* Set to <code>true</code> if the portlet uses the default template.
	*
	* @param useDefaultTemplate boolean value for whether the portlet uses the
	default template
	*/
	@Override
	public void setUseDefaultTemplate(boolean useDefaultTemplate) {
		_portlet.setUseDefaultTemplate(useDefaultTemplate);
	}

	/**
	* Sets the class loader resource path to the user notification definitions
	* of the portlet.
	*
	* @param userNotificationDefinitions the class loader resource path to the
	user notification definitions of the portlet
	*/
	@Override
	public void setUserNotificationDefinitions(
		java.lang.String userNotificationDefinitions) {
		_portlet.setUserNotificationDefinitions(userNotificationDefinitions);
	}

	/**
	* Sets the names of the classes that represent user notification handlers
	* associated with the portlet.
	*
	* @param userNotificationHandlerClasses the names of the classes that
	represent user notification handlers associated with the portlet
	*/
	@Override
	public void setUserNotificationHandlerClasses(
		java.util.List<java.lang.String> userNotificationHandlerClasses) {
		_portlet.setUserNotificationHandlerClasses(userNotificationHandlerClasses);
	}

	/**
	* Sets the user principal strategy of the portlet.
	*
	* @param userPrincipalStrategy the user principal strategy of the portlet
	*/
	@Override
	public void setUserPrincipalStrategy(java.lang.String userPrincipalStrategy) {
		_portlet.setUserPrincipalStrategy(userPrincipalStrategy);
	}

	/**
	* Sets the virtual path of the portlet.
	*
	* @param virtualPath the virtual path of the portlet
	*/
	@Override
	public void setVirtualPath(java.lang.String virtualPath) {
		_portlet.setVirtualPath(virtualPath);
	}

	/**
	* Sets the name of the WebDAV storage class of the portlet.
	*
	* @param webDAVStorageClass the name of the WebDAV storage class of the
	portlet
	*/
	@Override
	public void setWebDAVStorageClass(java.lang.String webDAVStorageClass) {
		_portlet.setWebDAVStorageClass(webDAVStorageClass);
	}

	/**
	* Sets the name of the WebDAV storage token of the portlet.
	*
	* @param webDAVStorageToken the name of the WebDAV storage token of the
	portlet
	*/
	@Override
	public void setWebDAVStorageToken(java.lang.String webDAVStorageToken) {
		_portlet.setWebDAVStorageToken(webDAVStorageToken);
	}

	/**
	* Sets the window states of the portlet.
	*
	* @param windowStates the window states of the portlet
	*/
	@Override
	public void setWindowStates(
		Map<java.lang.String, java.util.Set<java.lang.String>> windowStates) {
		_portlet.setWindowStates(windowStates);
	}

	/**
	* Sets the names of the classes that represent workflow handlers associated
	* to the portlet.
	*
	* @param workflowHandlerClasses the names of the classes that represent
	workflow handlers associated with the portlet
	*/
	@Override
	public void setWorkflowHandlerClasses(
		java.util.List<java.lang.String> workflowHandlerClasses) {
		_portlet.setWorkflowHandlerClasses(workflowHandlerClasses);
	}

	/**
	* Sets the name of the XML-RPC method class of the portlet.
	*
	* @param xmlRpcMethodClass the name of the XML-RPC method class of the
	portlet
	*/
	@Override
	public void setXmlRpcMethodClass(java.lang.String xmlRpcMethodClass) {
		_portlet.setXmlRpcMethodClass(xmlRpcMethodClass);
	}

	@Override
	public void unsetReady() {
		_portlet.unsetReady();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortletWrapper)) {
			return false;
		}

		PortletWrapper portletWrapper = (PortletWrapper)obj;

		if (Objects.equals(_portlet, portletWrapper._portlet)) {
			return true;
		}

		return false;
	}

	@Override
	public Portlet getWrappedModel() {
		return _portlet;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _portlet.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _portlet.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_portlet.resetOriginalValues();
	}

	private final Portlet _portlet;
}