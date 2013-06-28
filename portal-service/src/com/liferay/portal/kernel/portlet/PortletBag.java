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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.security.permission.PermissionPropagator;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public interface PortletBag extends Cloneable {

	public Object clone();

	public List<AssetRendererFactory> getAssetRendererFactoryInstances();

	public List<AtomCollectionAdapter<?>> getAtomCollectionAdapterInstances();

	public ConfigurationAction getConfigurationActionInstance();

	public ControlPanelEntry getControlPanelEntryInstance();

	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances();

	public FriendlyURLMapper getFriendlyURLMapperInstance();

	public List<Indexer> getIndexerInstances();

	public OpenSearch getOpenSearchInstance();

	public PermissionPropagator getPermissionPropagatorInstance();

	public PollerProcessor getPollerProcessorInstance();

	public MessageListener getPopMessageListenerInstance();

	public PortletDataHandler getPortletDataHandlerInstance();

	public Portlet getPortletInstance();

	public PortletLayoutListener getPortletLayoutListenerInstance();

	public String getPortletName();

	public PreferencesValidator getPreferencesValidatorInstance();

	public ResourceBundle getResourceBundle(Locale locale);

	public Map<String, ResourceBundle> getResourceBundles();

	public ServletContext getServletContext();

	public List<SocialActivityInterpreter>
		getSocialActivityInterpreterInstances();

	public SocialRequestInterpreter getSocialRequestInterpreterInstance();

	public List<StagedModelDataHandler<?>> getStagedModelDataHandlerInstances();

	public TemplateHandler getTemplateHandlerInstance();

	public List<TrashHandler> getTrashHandlerInstances();

	public URLEncoder getURLEncoderInstance();

	public List<UserNotificationHandler>
		getUserNotificationHandlerInstances();

	public WebDAVStorage getWebDAVStorageInstance();

	public List<WorkflowHandler> getWorkflowHandlerInstances();

	public Method getXmlRpcMethodInstance();

	public void setPortletInstance(Portlet portletInstance);

	public void setPortletName(String portletName);

}