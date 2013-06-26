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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.security.permission.PermissionPropagator;
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
 * @author Jorge Ferrer
 */
public class PortletBagImpl implements PortletBag {

	public PortletBagImpl(
		String portletName, ServletContext servletContext,
		Portlet portletInstance,
		ConfigurationAction configurationActionInstance,
		List<Indexer> indexerInstances, OpenSearch openSearchInstance,
		FriendlyURLMapper friendlyURLMapperInstance,
		URLEncoder urlEncoderInstance,
		PortletDataHandler portletDataHandlerInstance,
		List<StagedModelDataHandler<?>> stagedModelDataHandlerInstances,
		TemplateHandler templateHandlerInstance,
		PortletLayoutListener portletLayoutListenerInstance,
		PollerProcessor pollerProcessorInstance,
		MessageListener popMessageListenerInstance,
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
		_configurationActionInstance = configurationActionInstance;
		_indexerInstances = indexerInstances;
		_openSearchInstance = openSearchInstance;
		_friendlyURLMapperInstance = friendlyURLMapperInstance;
		_urlEncoderInstance = urlEncoderInstance;
		_portletDataHandlerInstance = portletDataHandlerInstance;
		_stagedModelDataHandlerInstances = stagedModelDataHandlerInstances;
		_templateHandlerInstance = templateHandlerInstance;
		_portletLayoutListenerInstance = portletLayoutListenerInstance;
		_pollerProcessorInstance = pollerProcessorInstance;
		_popMessageListenerInstance = popMessageListenerInstance;
		_socialActivityInterpreterInstances =
			socialActivityInterpreterInstances;
		_socialRequestInterpreterInstance = socialRequestInterpreterInstance;
		_userNotificationHandlerInstances =
			userNotificationHandlerInstances;
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
			getConfigurationActionInstance(), getIndexerInstances(),
			getOpenSearchInstance(), getFriendlyURLMapperInstance(),
			getURLEncoderInstance(), getPortletDataHandlerInstance(),
			getStagedModelDataHandlerInstances(), getTemplateHandlerInstance(),
			getPortletLayoutListenerInstance(), getPollerProcessorInstance(),
			getPopMessageListenerInstance(),
			getSocialActivityInterpreterInstances(),
			getSocialRequestInterpreterInstance(),
			getUserNotificationHandlerInstances(),
			getWebDAVStorageInstance(), getXmlRpcMethodInstance(),
			getControlPanelEntryInstance(), getAssetRendererFactoryInstances(),
			getAtomCollectionAdapterInstances(),
			getCustomAttributesDisplayInstances(),
			getPermissionPropagatorInstance(), getTrashHandlerInstances(),
			getWorkflowHandlerInstances(), getPreferencesValidatorInstance(),
			getResourceBundles());
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
	public ConfigurationAction getConfigurationActionInstance() {
		return _configurationActionInstance;
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
	public FriendlyURLMapper getFriendlyURLMapperInstance() {
		return _friendlyURLMapperInstance;
	}

	@Override
	public List<Indexer> getIndexerInstances() {
		return _indexerInstances;
	}

	@Override
	public OpenSearch getOpenSearchInstance() {
		return _openSearchInstance;
	}

	@Override
	public PermissionPropagator getPermissionPropagatorInstance() {
		return _permissionPropagatorInstance;
	}

	@Override
	public PollerProcessor getPollerProcessorInstance() {
		return _pollerProcessorInstance;
	}

	@Override
	public MessageListener getPopMessageListenerInstance() {
		return _popMessageListenerInstance;
	}

	@Override
	public PortletDataHandler getPortletDataHandlerInstance() {
		return _portletDataHandlerInstance;
	}

	@Override
	public Portlet getPortletInstance() {
		return _portletInstance;
	}

	@Override
	public PortletLayoutListener getPortletLayoutListenerInstance() {
		return _portletLayoutListenerInstance;
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
	public TemplateHandler getTemplateHandlerInstance() {
		return _templateHandlerInstance;
	}

	@Override
	public List<TrashHandler> getTrashHandlerInstances() {
		return _trashHandlerInstances;
	}

	@Override
	public URLEncoder getURLEncoderInstance() {
		return _urlEncoderInstance;
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

	private List<AssetRendererFactory> _assetRendererFactoryInstances;
	private List<AtomCollectionAdapter<?>> _atomCollectionAdapterInstances;
	private ConfigurationAction _configurationActionInstance;
	private ControlPanelEntry _controlPanelEntryInstance;
	private List<CustomAttributesDisplay> _customAttributesDisplayInstances;
	private FriendlyURLMapper _friendlyURLMapperInstance;
	private List<Indexer> _indexerInstances;
	private OpenSearch _openSearchInstance;
	private PermissionPropagator _permissionPropagatorInstance;
	private PollerProcessor _pollerProcessorInstance;
	private MessageListener _popMessageListenerInstance;
	private PortletDataHandler _portletDataHandlerInstance;
	private Portlet _portletInstance;
	private PortletLayoutListener _portletLayoutListenerInstance;
	private String _portletName;
	private PreferencesValidator _preferencesValidatorInstance;
	private Map<String, ResourceBundle> _resourceBundles;
	private ServletContext _servletContext;
	private List<SocialActivityInterpreter> _socialActivityInterpreterInstances;
	private SocialRequestInterpreter _socialRequestInterpreterInstance;
	private List<StagedModelDataHandler<?>> _stagedModelDataHandlerInstances;
	private TemplateHandler _templateHandlerInstance;
	private List<TrashHandler> _trashHandlerInstances;
	private URLEncoder _urlEncoderInstance;
	private List<UserNotificationHandler>
		_userNotificationHandlerInstances;
	private WebDAVStorage _webDAVStorageInstance;
	private List<WorkflowHandler> _workflowHandlerInstances;
	private Method _xmlRpcMethodInstance;

}