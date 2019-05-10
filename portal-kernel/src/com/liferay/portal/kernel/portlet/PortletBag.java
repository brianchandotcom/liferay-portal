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

package com.liferay.portal.kernel.portlet;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.security.permission.propagator.PermissionPropagator;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialRequestInterpreter;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface PortletBag extends Cloneable {

	public Object clone();

	public void destroy();

	public List<AssetRendererFactory<?>> getAssetRendererFactoryInstances();

	public List<AtomCollectionAdapter<?>> getAtomCollectionAdapterInstances();

	public List<ConfigurationAction> getConfigurationActionInstances();

	public List<ControlPanelEntry> getControlPanelEntryInstances();

	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances();

	public FriendlyURLMapperTracker getFriendlyURLMapperTracker();

	public List<Indexer<?>> getIndexerInstances();

	public List<OpenSearch> getOpenSearchInstances();

	public List<PermissionPropagator> getPermissionPropagatorInstances();

	public List<PollerProcessor> getPollerProcessorInstances();

	public List<MessageListener> getPopMessageListenerInstances();

	public List<PortletDataHandler> getPortletDataHandlerInstances();

	public Portlet getPortletInstance();

	public List<PortletLayoutListener> getPortletLayoutListenerInstances();

	public String getPortletName();

	public List<PreferencesValidator> getPreferencesValidatorInstances();

	public ResourceBundle getResourceBundle(Locale locale);

	public String getResourceBundleBaseName();

	public List<SchedulerEventMessageListener>
		getSchedulerEventMessageListeners();

	public ServletContext getServletContext();

	public List<SocialActivityInterpreter>
		getSocialActivityInterpreterInstances();

	public List<SocialRequestInterpreter>
		getSocialRequestInterpreterInstances();

	public List<StagedModelDataHandler<?>> getStagedModelDataHandlerInstances();

	public List<TemplateHandler> getTemplateHandlerInstances();

	public List<TrashHandler> getTrashHandlerInstances();

	public List<URLEncoder> getURLEncoderInstances();

	public List<UserNotificationDefinition>
		getUserNotificationDefinitionInstances();

	public List<UserNotificationHandler> getUserNotificationHandlerInstances();

	public List<WebDAVStorage> getWebDAVStorageInstances();

	public List<WorkflowHandler<?>> getWorkflowHandlerInstances();

	public List<Method> getXmlRpcMethodInstances();

	public void setPortletDataHandlerInstances(
		List<PortletDataHandler> portletDataHandlerInstances);

	public void setPortletInstance(Portlet portletInstance);

	public void setPortletName(String portletName);

}