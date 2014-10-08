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

package com.liferay.portlet;

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.ResourceBundleTracker;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.security.permission.PermissionPropagator;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.dynamicdatamapping.util.DDMDisplay;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;

import java.io.Closeable;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletBagImpl implements PortletBag {

	public PortletBagImpl(
		String portletName, ServletContext servletContext,
		Portlet portletInstance, ResourceBundleTracker resourceBundleTracker,
		List<ConfigurationAction> configurationActionInstances,
		List<Indexer> indexerInstances, List<OpenSearch> openSearchInstances,
		List<SchedulerEntry> schedulerEntryInstances,
		FriendlyURLMapperTracker friendlyURLMapperTracker,
		List<URLEncoder> urlEncoderInstances,
		List<PortletDataHandler> portletDataHandlerInstances,
		List<StagedModelDataHandler<?>> stagedModelDataHandlerInstances,
		List<TemplateHandler> templateHandlerInstances,
		List<PortletLayoutListener> portletLayoutListenerInstances,
		List<PollerProcessor> pollerProcessorInstances,
		List<MessageListener> popMessageListenerInstances,
		List<SocialActivityInterpreter> socialActivityInterpreterInstances,
		List<SocialRequestInterpreter> socialRequestInterpreterInstances,
		List<UserNotificationHandler> userNotificationHandlerInstances,
		List<WebDAVStorage> webDAVStorageInstances,
		List<Method> xmlRpcMethodInstances,
		List<ControlPanelEntry> controlPanelEntryInstances,
		List<AssetRendererFactory> assetRendererFactoryInstances,
		List<AtomCollectionAdapter<?>> atomCollectionAdapters,
		List<CustomAttributesDisplay> customAttributesDisplayInstances,
		List<DDMDisplay> ddmDisplayInstances,
		List<PermissionPropagator> permissionPropagatorInstances,
		List<TrashHandler> trashHandlerInstances,
		List<WorkflowHandler<?>> workflowHandlerInstances,
		List<PreferencesValidator> preferencesValidatorInstances) {

		_portletName = portletName;
		_servletContext = servletContext;
		_portletInstance = portletInstance;
		_resourceBundleTracker = resourceBundleTracker;
		_configurationActionInstances = configurationActionInstances;
		_indexerInstances = indexerInstances;
		_openSearchInstances = openSearchInstances;
		_schedulerEntryInstances = schedulerEntryInstances;
		_friendlyURLMapperTracker = friendlyURLMapperTracker;
		_urlEncoderInstances = urlEncoderInstances;
		_portletDataHandlerInstances = portletDataHandlerInstances;
		_stagedModelDataHandlerInstances = stagedModelDataHandlerInstances;
		_templateHandlerInstances = templateHandlerInstances;
		_portletLayoutListenerInstances = portletLayoutListenerInstances;
		_pollerProcessorInstances = pollerProcessorInstances;
		_popMessageListenerInstances = popMessageListenerInstances;
		_socialActivityInterpreterInstances =
			socialActivityInterpreterInstances;
		_socialRequestInterpreterInstances = socialRequestInterpreterInstances;
		_userNotificationHandlerInstances = userNotificationHandlerInstances;
		_webDAVStorageInstances = webDAVStorageInstances;
		_xmlRpcMethodInstances = xmlRpcMethodInstances;
		_controlPanelEntryInstances = controlPanelEntryInstances;
		_assetRendererFactoryInstances = assetRendererFactoryInstances;
		_atomCollectionAdapterInstances = atomCollectionAdapters;
		_customAttributesDisplayInstances = customAttributesDisplayInstances;
		_ddmDisplayInstances = ddmDisplayInstances;
		_permissionPropagatorInstances = permissionPropagatorInstances;
		_trashHandlerInstances = trashHandlerInstances;
		_workflowHandlerInstances = workflowHandlerInstances;
		_preferencesValidatorInstances = preferencesValidatorInstances;
	}

	@Override
	public Object clone() {
		return new PortletBagImpl(
			getPortletName(), getServletContext(), getPortletInstance(),
			getResourceBundleTracker(), getConfigurationActionInstances(),
			getIndexerInstances(), getOpenSearchInstances(),
			getSchedulerEntryInstances(), getFriendlyURLMapperTracker(),
			getURLEncoderInstances(), getPortletDataHandlerInstances(),
			getStagedModelDataHandlerInstances(), getTemplateHandlerInstances(),
			getPortletLayoutListenerInstances(), getPollerProcessorInstances(),
			getPopMessageListenerInstances(),
			getSocialActivityInterpreterInstances(),
			getSocialRequestInterpreterInstances(),
			getUserNotificationHandlerInstances(), getWebDAVStorageInstances(),
			getXmlRpcMethodInstances(), getControlPanelEntryInstances(),
			getAssetRendererFactoryInstances(),
			getAtomCollectionAdapterInstances(),
			getCustomAttributesDisplayInstances(), getDdmDisplayInstances(),
			getPermissionPropagatorInstances(), getTrashHandlerInstances(),
			getWorkflowHandlerInstances(), getPreferencesValidatorInstances());
	}

	@Override
	public void destroy() {
		close(_assetRendererFactoryInstances);
		close(_atomCollectionAdapterInstances);
		close(_configurationActionInstances);
		close(_controlPanelEntryInstances);
		close(_customAttributesDisplayInstances);
		close(_ddmDisplayInstances);
		close(_friendlyURLMapperTracker);
		close(_indexerInstances);
		close(_openSearchInstances);
		close(_permissionPropagatorInstances);
		close(_pollerProcessorInstances);
		close(_popMessageListenerInstances);
		close(_portletDataHandlerInstances);
		close(_portletLayoutListenerInstances);
		close(_preferencesValidatorInstances);
		close(_resourceBundleTracker);
		close(_schedulerEntryInstances);
		close(_socialActivityInterpreterInstances);
		close(_socialRequestInterpreterInstances);
		close(_templateHandlerInstances);
		close(_trashHandlerInstances);
		close(_urlEncoderInstances);
		close(_userNotificationHandlerInstances);
		close(_webDAVStorageInstances);
		close(_workflowHandlerInstances);
		close(_xmlRpcMethodInstances);
	}

	@Override
	public List<AssetRendererFactory> getAssetRendererFactoryInstances() {
		return _assetRendererFactoryInstances;
	}

	@Override
	public List<AtomCollectionAdapter<?>> getAtomCollectionAdapterInstances() {
		return _atomCollectionAdapterInstances;
	}

	@Override
	public List<ConfigurationAction> getConfigurationActionInstances() {
		return _configurationActionInstances;
	}

	@Override
	public List<ControlPanelEntry> getControlPanelEntryInstances() {
		return _controlPanelEntryInstances;
	}

	@Override
	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		return _customAttributesDisplayInstances;
	}

	@Override
	public List<DDMDisplay> getDdmDisplayInstances() {
		return _ddmDisplayInstances;
	}

	@Override
	public FriendlyURLMapperTracker getFriendlyURLMapperTracker() {
		return _friendlyURLMapperTracker;
	}

	@Override
	public List<Indexer> getIndexerInstances() {
		return _indexerInstances;
	}

	@Override
	public List<OpenSearch> getOpenSearchInstances() {
		return _openSearchInstances;
	}

	@Override
	public List<PermissionPropagator> getPermissionPropagatorInstances() {
		return _permissionPropagatorInstances;
	}

	@Override
	public List<PollerProcessor> getPollerProcessorInstances() {
		return _pollerProcessorInstances;
	}

	@Override
	public List<MessageListener> getPopMessageListenerInstances() {
		return _popMessageListenerInstances;
	}

	@Override
	public List<PortletDataHandler> getPortletDataHandlerInstances() {
		return _portletDataHandlerInstances;
	}

	@Override
	public Portlet getPortletInstance() {
		return _portletInstance;
	}

	@Override
	public List<PortletLayoutListener> getPortletLayoutListenerInstances() {
		return _portletLayoutListenerInstances;
	}

	@Override
	public String getPortletName() {
		return _portletName;
	}

	@Override
	public List<PreferencesValidator> getPreferencesValidatorInstances() {
		return _preferencesValidatorInstances;
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = _resourceBundleTracker.getResouceBundle(
			LocaleUtil.toLanguageId(locale));

		if (resourceBundle == null) {
			resourceBundle = _resourceBundleTracker.getResouceBundle(
				locale.getLanguage());

			if (resourceBundle == null) {
				resourceBundle = _resourceBundleTracker.getResouceBundle(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
			}
		}

		return resourceBundle;
	}

	@Override
	public ResourceBundleTracker getResourceBundleTracker() {
		return _resourceBundleTracker;
	}

	@Override
	public List<SchedulerEntry> getSchedulerEntryInstances() {
		return _schedulerEntryInstances;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<SocialActivityInterpreter>
		getSocialActivityInterpreterInstances() {

		return _socialActivityInterpreterInstances;
	}

	@Override
	public List<SocialRequestInterpreter>
		getSocialRequestInterpreterInstances() {

		return _socialRequestInterpreterInstances;
	}

	@Override
	public List<StagedModelDataHandler<?>>
		getStagedModelDataHandlerInstances() {

		return _stagedModelDataHandlerInstances;
	}

	@Override
	public List<TemplateHandler> getTemplateHandlerInstances() {
		return _templateHandlerInstances;
	}

	@Override
	public List<TrashHandler> getTrashHandlerInstances() {
		return _trashHandlerInstances;
	}

	@Override
	public List<URLEncoder> getURLEncoderInstances() {
		return _urlEncoderInstances;
	}

	@Override
	public List<UserNotificationHandler>
		getUserNotificationHandlerInstances() {

		return _userNotificationHandlerInstances;
	}

	@Override
	public List<WebDAVStorage> getWebDAVStorageInstances() {
		return _webDAVStorageInstances;
	}

	@Override
	public List<WorkflowHandler<?>> getWorkflowHandlerInstances() {
		return _workflowHandlerInstances;
	}

	@Override
	public List<Method> getXmlRpcMethodInstances() {
		return _xmlRpcMethodInstances;
	}

	@Override
	public void setPortletInstance(Portlet portletInstance) {
		_portletInstance = portletInstance;
	}

	@Override
	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	protected void close(Object object) {
		try {
			Closeable closeable = (Closeable)object;

			closeable.close();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to close " + ClassUtil.getClassName(object), e);
		}
	}

	private final List<AssetRendererFactory> _assetRendererFactoryInstances;
	private final List<AtomCollectionAdapter<?>>
		_atomCollectionAdapterInstances;
	private final List<ConfigurationAction> _configurationActionInstances;
	private final List<ControlPanelEntry> _controlPanelEntryInstances;
	private final List<CustomAttributesDisplay>
		_customAttributesDisplayInstances;
	private final List<DDMDisplay> _ddmDisplayInstances;
	private final FriendlyURLMapperTracker _friendlyURLMapperTracker;
	private final List<Indexer> _indexerInstances;
	private final List<OpenSearch> _openSearchInstances;
	private final List<PermissionPropagator> _permissionPropagatorInstances;
	private final List<PollerProcessor> _pollerProcessorInstances;
	private final List<MessageListener> _popMessageListenerInstances;
	private final List<PortletDataHandler> _portletDataHandlerInstances;
	private Portlet _portletInstance;
	private final List<PortletLayoutListener> _portletLayoutListenerInstances;
	private String _portletName;
	private final List<PreferencesValidator> _preferencesValidatorInstances;
	private final ResourceBundleTracker _resourceBundleTracker;
	private final List<SchedulerEntry> _schedulerEntryInstances;
	private final ServletContext _servletContext;
	private final List<SocialActivityInterpreter>
		_socialActivityInterpreterInstances;
	private final List<SocialRequestInterpreter>
		_socialRequestInterpreterInstances;
	private final List<StagedModelDataHandler<?>>
		_stagedModelDataHandlerInstances;
	private final List<TemplateHandler> _templateHandlerInstances;
	private final List<TrashHandler> _trashHandlerInstances;
	private final List<URLEncoder> _urlEncoderInstances;
	private final List<UserNotificationHandler>
		_userNotificationHandlerInstances;
	private final List<WebDAVStorage> _webDAVStorageInstances;
	private final List<WorkflowHandler<?>> _workflowHandlerInstances;
	private final List<Method> _xmlRpcMethodInstances;

}