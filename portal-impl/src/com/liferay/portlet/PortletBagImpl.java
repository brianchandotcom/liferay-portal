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

package com.liferay.portlet;

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
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
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;

import java.io.Closeable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
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
		Portlet portletInstance,
		List<ConfigurationAction> configurationActionInstances,
		List<Indexer> indexerInstances, List<OpenSearch> openSearchInstances,
		List<FriendlyURLMapper> friendlyURLMapperInstances,
		List<URLEncoder> urlEncoderInstances,
		List<PortletDataHandler> portletDataHandlerInstances,
		List<StagedModelDataHandler<?>> stagedModelDataHandlerInstances,
		List<TemplateHandler> templateHandlerInstances,
		List<PortletLayoutListener> portletLayoutListenerInstances,
		List<PollerProcessor> pollerProcessorInstances,
		List<MessageListener> popMessageListenerInstances,
		List<SocialActivityInterpreter> socialActivityInterpreterInstances,
		SocialRequestInterpreter socialRequestInterpreterInstance,
		List<UserNotificationHandler> userNotificationHandlerInstances,
		WebDAVStorage webDAVStorageInstance, Method xmlRpcMethodInstance,
		ControlPanelEntry controlPanelEntryInstance,
		List<AssetRendererFactory> assetRendererFactoryInstances,
		List<AtomCollectionAdapter<?>> atomCollectionAdapters,
		List<CustomAttributesDisplay> customAttributesDisplayInstances,
		PermissionPropagator permissionPropagatorInstance,
		List<TrashHandler> trashHandlerInstances,
		List<WorkflowHandler> workflowHandlerInstances,
		PreferencesValidator preferencesValidatorInstance,
		Map<String, ResourceBundle> resourceBundles) {

		_portletName = portletName;
		_servletContext = servletContext;
		_portletInstance = portletInstance;
		_configurationActionInstances = configurationActionInstances;
		_indexerInstances = indexerInstances;
		_openSearchInstances = openSearchInstances;
		_friendlyURLMapperInstances = friendlyURLMapperInstances;
		_urlEncoderInstances = urlEncoderInstances;
		_portletDataHandlerInstances = portletDataHandlerInstances;
		_stagedModelDataHandlerInstances = stagedModelDataHandlerInstances;
		_templateHandlerInstances = templateHandlerInstances;
		_portletLayoutListenerInstances = portletLayoutListenerInstances;
		_pollerProcessorInstances = pollerProcessorInstances;
		_popMessageListenerInstances = popMessageListenerInstances;
		_socialActivityInterpreterInstances =
			socialActivityInterpreterInstances;
		_socialRequestInterpreterInstance = socialRequestInterpreterInstance;
		_userNotificationHandlerInstances = userNotificationHandlerInstances;
		_webDAVStorageInstance = webDAVStorageInstance;
		_xmlRpcMethodInstance = xmlRpcMethodInstance;
		_controlPanelEntryInstance = controlPanelEntryInstance;
		_assetRendererFactoryInstances = assetRendererFactoryInstances;
		_atomCollectionAdapterInstances = atomCollectionAdapters;
		_customAttributesDisplayInstances = customAttributesDisplayInstances;
		_permissionPropagatorInstance = permissionPropagatorInstance;
		_trashHandlerInstances = trashHandlerInstances;
		_workflowHandlerInstances = workflowHandlerInstances;
		_preferencesValidatorInstance = preferencesValidatorInstance;
		_resourceBundles = resourceBundles;
	}

	@Override
	public Object clone() {
		return new PortletBagImpl(
			getPortletName(), getServletContext(), getPortletInstance(),
			getConfigurationActionInstances(), getIndexerInstances(),
			getOpenSearchInstances(), getFriendlyURLMapperInstances(),
			getURLEncoderInstances(), getPortletDataHandlerInstances(),
			getStagedModelDataHandlerInstances(), getTemplateHandlerInstances(),
			getPortletLayoutListenerInstances(), getPollerProcessorInstances(),
			getPopMessageListenerInstances(),
			getSocialActivityInterpreterInstances(),
			getSocialRequestInterpreterInstance(),
			getUserNotificationHandlerInstances(), getWebDAVStorageInstance(),
			getXmlRpcMethodInstance(), getControlPanelEntryInstance(),
			getAssetRendererFactoryInstances(),
			getAtomCollectionAdapterInstances(),
			getCustomAttributesDisplayInstances(),
			getPermissionPropagatorInstance(), getTrashHandlerInstances(),
			getWorkflowHandlerInstances(), getPreferencesValidatorInstance(),
			getResourceBundles());
	}

	@Override
	public void destroy() {
		close(_configurationActionInstances);
		close(_friendlyURLMapperInstances);
		close(_indexerInstances);
		close(_openSearchInstances);
		close(_pollerProcessorInstances);
		close(_popMessageListenerInstances);
		close(_portletDataHandlerInstances);
		close(_portletLayoutListenerInstances);
		close(_socialActivityInterpreterInstances);
		close(_templateHandlerInstances);
		close(_urlEncoderInstances);
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
	public ControlPanelEntry getControlPanelEntryInstance() {
		return _controlPanelEntryInstance;
	}

	@Override
	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		return _customAttributesDisplayInstances;
	}

	@Override
	public List<FriendlyURLMapper> getFriendlyURLMapperInstances() {
		return _friendlyURLMapperInstances;
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
	public PermissionPropagator getPermissionPropagatorInstance() {
		return _permissionPropagatorInstance;
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
	public PreferencesValidator getPreferencesValidatorInstance() {
		return _preferencesValidatorInstance;
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = _resourceBundles.get(
			LocaleUtil.toLanguageId(locale));

		if (resourceBundle == null) {
			resourceBundle = _resourceBundles.get(locale.getLanguage());

			if (resourceBundle == null) {
				resourceBundle = _resourceBundles.get(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
			}
		}

		return resourceBundle;
	}

	@Override
	public Map<String, ResourceBundle> getResourceBundles() {
		return _resourceBundles;
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
	public SocialRequestInterpreter getSocialRequestInterpreterInstance() {
		return _socialRequestInterpreterInstance;
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
	public WebDAVStorage getWebDAVStorageInstance() {
		return _webDAVStorageInstance;
	}

	@Override
	public List<WorkflowHandler> getWorkflowHandlerInstances() {
		return _workflowHandlerInstances;
	}

	@Override
	public Method getXmlRpcMethodInstance() {
		return _xmlRpcMethodInstance;
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

	private List<AssetRendererFactory> _assetRendererFactoryInstances;
	private List<AtomCollectionAdapter<?>> _atomCollectionAdapterInstances;
	private List<ConfigurationAction> _configurationActionInstances;
	private ControlPanelEntry _controlPanelEntryInstance;
	private List<CustomAttributesDisplay> _customAttributesDisplayInstances;
	private List<FriendlyURLMapper> _friendlyURLMapperInstances;
	private List<Indexer> _indexerInstances;
	private List<OpenSearch> _openSearchInstances;
	private PermissionPropagator _permissionPropagatorInstance;
	private List<PollerProcessor> _pollerProcessorInstances;
	private List<MessageListener> _popMessageListenerInstances;
	private List<PortletDataHandler> _portletDataHandlerInstances;
	private Portlet _portletInstance;
	private List<PortletLayoutListener> _portletLayoutListenerInstances;
	private String _portletName;
	private PreferencesValidator _preferencesValidatorInstance;
	private Map<String, ResourceBundle> _resourceBundles;
	private ServletContext _servletContext;
	private List<SocialActivityInterpreter> _socialActivityInterpreterInstances;
	private SocialRequestInterpreter _socialRequestInterpreterInstance;
	private List<StagedModelDataHandler<?>> _stagedModelDataHandlerInstances;
	private List<TemplateHandler> _templateHandlerInstances;
	private List<TrashHandler> _trashHandlerInstances;
	private List<URLEncoder> _urlEncoderInstances;
	private List<UserNotificationHandler>
		_userNotificationHandlerInstances;
	private WebDAVStorage _webDAVStorageInstance;
	private List<WorkflowHandler> _workflowHandlerInstances;
	private Method _xmlRpcMethodInstance;

}