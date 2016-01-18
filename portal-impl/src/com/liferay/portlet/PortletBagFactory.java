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
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.ResourceBundleTracker;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.notifications.UserNotificationHandlerImpl;
import com.liferay.portal.security.permission.PermissionPropagator;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.JavaFieldsParser;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Ivica Cardic
 * @author Raymond Augé
 */
public class PortletBagFactory {

	public PortletBag create(Portlet portlet) throws Exception {
		validate();

		javax.portlet.Portlet portletInstance = getPortletInstance(portlet);

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(|(javax.portlet.name=" + portlet.getPortletId() +
				")(javax.portlet.name=ALL))");

		Map<String, Object> properties =
			Collections.<String, Object>singletonMap(
				"javax.portlet.name", portlet.getPortletId());

		List<ConfigurationAction> configurationActionInstances =
			newConfigurationActions(portlet, filter, properties);

		List<Indexer<?>> indexerInstances = newIndexers(
			portlet, filter, properties);

		List<OpenSearch> openSearchInstances = newOpenSearches(
			portlet, filter, properties);

		List<SchedulerEventMessageListener> schedulerEventMessageListeners =
			newSchedulerEventMessageListeners(portlet, filter, properties);

		FriendlyURLMapperTracker friendlyURLMapperTracker =
			newFriendlyURLMappers(portlet);

		List<URLEncoder> urlEncoderInstances = newURLEncoders(
			portlet, filter, properties);

		List<PortletDataHandler> portletDataHandlerInstances =
			newPortletDataHandlers(portlet, filter, properties);

		List<StagedModelDataHandler<?>> stagedModelDataHandlerInstances =
			newStagedModelDataHandler(portlet, filter, properties);

		List<TemplateHandler> templateHandlerInstances = newTemplateHandlers(
			portlet, filter, properties);

		List<PortletLayoutListener> portletLayoutListenerInstances =
			newPortletLayoutListeners(portlet, filter, properties);

		List<PollerProcessor> pollerProcessorInstances = newPollerProcessors(
			portlet, filter, properties);

		List<MessageListener> popMessageListenerInstances =
			newPOPMessageListeners(portlet, filter, properties);

		List<SocialActivityInterpreter> socialActivityInterpreterInstances =
			newSocialActivityInterpreterInstances(portlet, filter, properties);

		List<SocialRequestInterpreter> socialRequestInterpreterInstances =
			newSocialRequestInterpreterInstances(portlet, filter, properties);

		List<UserNotificationDefinition> userNotificationDefinitionInstances =
			newUserNotificationDefinitionInstances(portlet, filter, properties);

		List<UserNotificationHandler> userNotificationHandlerInstances =
			newUserNotificationHandlerInstances(portlet, filter, properties);

		List<WebDAVStorage> webDAVStorageInstances = newWebDAVStorageInstances(
			portlet, filter, properties);

		List<Method> xmlRpcMethodInstances = newXmlRpcMethodInstances(
			portlet, filter, properties);

		List<ControlPanelEntry> controlPanelEntryInstances =
			newControlPanelEntryInstances(portlet, filter, properties);

		List<AssetRendererFactory<?>> assetRendererFactoryInstances =
			newAssetRendererFactoryInstances(portlet, filter, properties);

		List<AtomCollectionAdapter<?>> atomCollectionAdapterInstances =
			newAtomCollectionAdapterInstances(portlet, filter, properties);

		List<CustomAttributesDisplay> customAttributesDisplayInstances =
			newCustomAttributesDisplayInstances(portlet, filter, properties);

		List<PermissionPropagator> permissionPropagatorInstances =
			newPermissionPropagators(portlet, filter, properties);

		List<TrashHandler> trashHandlerInstances = newTrashHandlerInstances(
			portlet, filter, properties);

		List<WorkflowHandler<?>> workflowHandlerInstances =
			newWorkflowHandlerInstances(portlet, filter, properties);

		List<PreferencesValidator> preferencesValidatorInstances =
			newPreferencesValidatorInstances(portlet, filter, properties);

		ResourceBundleTracker resourceBundleTracker = new ResourceBundleTracker(
			portlet.getPortletId());

		PortletBag portletBag = new PortletBagImpl(
			portlet.getPortletId(), _servletContext, portletInstance,
			resourceBundleTracker, configurationActionInstances,
			indexerInstances, openSearchInstances,
			schedulerEventMessageListeners, friendlyURLMapperTracker,
			urlEncoderInstances, portletDataHandlerInstances,
			stagedModelDataHandlerInstances, templateHandlerInstances,
			portletLayoutListenerInstances, pollerProcessorInstances,
			popMessageListenerInstances, socialActivityInterpreterInstances,
			socialRequestInterpreterInstances,
			userNotificationDefinitionInstances,
			userNotificationHandlerInstances, webDAVStorageInstances,
			xmlRpcMethodInstances, controlPanelEntryInstances,
			assetRendererFactoryInstances, atomCollectionAdapterInstances,
			customAttributesDisplayInstances, permissionPropagatorInstances,
			trashHandlerInstances, workflowHandlerInstances,
			preferencesValidatorInstances);

		PortletBagPool.put(portlet.getRootPortletId(), portletBag);

		try {
			PortletInstanceFactoryUtil.create(portlet, _servletContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return portletBag;
	}

	public void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public void setWARFile(boolean warFile) {
		_warFile = warFile;
	}

	protected String getContent(String fileName) throws Exception {
		String queryString = HttpUtil.getQueryString(fileName);

		if (Validator.isNull(queryString)) {
			return StringUtil.read(_classLoader, fileName);
		}

		int pos = fileName.indexOf(StringPool.QUESTION);

		String xml = StringUtil.read(_classLoader, fileName.substring(0, pos));

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			queryString);

		if (parameterMap == null) {
			return xml;
		}

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if (values.length == 0) {
				continue;
			}

			String value = values[0];

			xml = StringUtil.replace(xml, "@" + name + "@", value);
		}

		return xml;
	}

	protected String getPluginPropertyValue(String propertyKey)
		throws Exception {

		if (_configuration == null) {
			_configuration = ConfigurationFactoryUtil.getConfiguration(
				_classLoader, "portlet");
		}

		return _configuration.get(propertyKey);
	}

	protected javax.portlet.Portlet getPortletInstance(Portlet portlet)
		throws IllegalAccessException, InstantiationException {

		Class<?> portletClass = null;

		try {
			portletClass = _classLoader.loadClass(portlet.getPortletClass());
		}
		catch (Throwable t) {
			_log.error(t, t);

			PortletLocalServiceUtil.destroyPortlet(portlet);

			return null;
		}

		return (javax.portlet.Portlet)portletClass.newInstance();
	}

	protected List<AssetRendererFactory<?>> newAssetRendererFactoryInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<AssetRendererFactory<?>>
			assetRendererFactoryInstances = ServiceTrackerCollections.openList(
				(Class<AssetRendererFactory<?>>)(Class<?>)
					AssetRendererFactory.class,
				filter, properties);

		for (String assetRendererFactoryClass :
				portlet.getAssetRendererFactoryClasses()) {

			String assetRendererEnabledPropertyKey =
				PropsKeys.ASSET_RENDERER_ENABLED + assetRendererFactoryClass;

			String assetRendererEnabledPropertyValue = null;

			if (_warFile) {
				assetRendererEnabledPropertyValue = getPluginPropertyValue(
					assetRendererEnabledPropertyKey);
			}
			else {
				assetRendererEnabledPropertyValue = PropsUtil.get(
					assetRendererEnabledPropertyKey);
			}

			boolean assetRendererEnabledValue = GetterUtil.getBoolean(
				assetRendererEnabledPropertyValue, true);

			if (assetRendererEnabledValue) {
				AssetRendererFactory<?> assetRendererFactoryInstance =
					(AssetRendererFactory<?>)newInstance(
						AssetRendererFactory.class, assetRendererFactoryClass);

				assetRendererFactoryInstance.setClassName(
					assetRendererFactoryInstance.getClassName());
				assetRendererFactoryInstance.setPortletId(
					portlet.getPortletId());

				assetRendererFactoryInstances.add(assetRendererFactoryInstance);
			}
		}

		return assetRendererFactoryInstances;
	}

	protected List<AtomCollectionAdapter<?>> newAtomCollectionAdapterInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<AtomCollectionAdapter<?>>
			atomCollectionAdapterInstances = ServiceTrackerCollections.openList(
				(Class<AtomCollectionAdapter<?>>)(Class<?>)
					AtomCollectionAdapter.class, filter, properties);

		for (String atomCollectionAdapterClass :
				portlet.getAtomCollectionAdapterClasses()) {

			AtomCollectionAdapter<?> atomCollectionAdapterInstance =
				(AtomCollectionAdapter<?>)newInstance(
					AtomCollectionAdapter.class, atomCollectionAdapterClass);

			atomCollectionAdapterInstances.add(atomCollectionAdapterInstance);
		}

		return atomCollectionAdapterInstances;
	}

	protected List<ConfigurationAction> newConfigurationActions(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<ConfigurationAction> configurationActionInstances =
			ServiceTrackerCollections.openList(
				ConfigurationAction.class, filter, properties);

		if (Validator.isNotNull(portlet.getConfigurationActionClass())) {
			ConfigurationAction configurationAction =
				(ConfigurationAction)newInstance(
					ConfigurationAction.class,
					portlet.getConfigurationActionClass());

			configurationActionInstances.add(configurationAction);
		}

		return configurationActionInstances;
	}

	protected List<ControlPanelEntry> newControlPanelEntryInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<ControlPanelEntry> controlPanelEntryInstances =
			ServiceTrackerCollections.openList(
				ControlPanelEntry.class, filter, properties);

		if (Validator.isNotNull(portlet.getControlPanelEntryClass())) {
			ControlPanelEntry controlPanelEntryInstance =
				(ControlPanelEntry)newInstance(
					ControlPanelEntry.class,
					portlet.getControlPanelEntryClass());

			controlPanelEntryInstances.add(controlPanelEntryInstance);
		}

		return controlPanelEntryInstances;
	}

	protected List<CustomAttributesDisplay> newCustomAttributesDisplayInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<CustomAttributesDisplay>
			customAttributesDisplayInstances =
				ServiceTrackerCollections.openList(
					CustomAttributesDisplay.class, filter, properties);

		for (String customAttributesDisplayClass :
				portlet.getCustomAttributesDisplayClasses()) {

			CustomAttributesDisplay customAttributesDisplayInstance =
				(CustomAttributesDisplay)newInstance(
					CustomAttributesDisplay.class,
					customAttributesDisplayClass);

			customAttributesDisplayInstance.setClassNameId(
				PortalUtil.getClassNameId(
					customAttributesDisplayInstance.getClassName()));
			customAttributesDisplayInstance.setPortletId(
				portlet.getPortletId());

			customAttributesDisplayInstances.add(
				customAttributesDisplayInstance);
		}

		return customAttributesDisplayInstances;
	}

	protected FriendlyURLMapperTracker newFriendlyURLMappers(Portlet portlet)
		throws Exception {

		FriendlyURLMapperTracker friendlyURLMapperTracker =
			new FriendlyURLMapperTrackerImpl(portlet);

		if (Validator.isNotNull(portlet.getFriendlyURLMapperClass())) {
			FriendlyURLMapper friendlyURLMapper =
				(FriendlyURLMapper)newInstance(
					FriendlyURLMapper.class,
					portlet.getFriendlyURLMapperClass());

			friendlyURLMapperTracker.register(friendlyURLMapper);
		}

		return friendlyURLMapperTracker;
	}

	protected List<Indexer<?>> newIndexers(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<Indexer<?>> indexerInstances =
			ServiceTrackerCollections.openList(
				(Class<Indexer<?>>)(Class<?>)Indexer.class, filter, properties);

		List<String> indexerClasses = portlet.getIndexerClasses();

		for (String indexerClass : indexerClasses) {
			Indexer<?> indexerInstance = (Indexer<?>)newInstance(
				Indexer.class, indexerClass);

			indexerInstances.add(indexerInstance);
		}

		return indexerInstances;
	}

	protected Object newInstance(Class<?> interfaceClass, String implClassName)
		throws Exception {

		return newInstance(new Class[] {interfaceClass}, implClassName);
	}

	protected Object newInstance(
			Class<?>[] interfaceClasses, String implClassName)
		throws Exception {

		if (_warFile) {
			return ProxyFactory.newInstance(
				_classLoader, interfaceClasses, implClassName);
		}
		else {
			Class<?> clazz = _classLoader.loadClass(implClassName);

			return clazz.newInstance();
		}
	}

	protected List<OpenSearch> newOpenSearches(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<OpenSearch> openSearchInstances =
			ServiceTrackerCollections.openList(
				OpenSearch.class, filter, properties);

		if (Validator.isNotNull(portlet.getOpenSearchClass())) {
			OpenSearch openSearch = (OpenSearch)newInstance(
				OpenSearch.class, portlet.getOpenSearchClass());

			openSearchInstances.add(openSearch);
		}

		return openSearchInstances;
	}

	protected List<PermissionPropagator> newPermissionPropagators(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<PermissionPropagator> permissionPropagatorInstances =
			ServiceTrackerCollections.openList(
				PermissionPropagator.class, filter, properties);

		if (Validator.isNotNull(portlet.getPermissionPropagatorClass())) {
			PermissionPropagator permissionPropagatorInstance =
				(PermissionPropagator)newInstance(
					PermissionPropagator.class,
					portlet.getPermissionPropagatorClass());

			permissionPropagatorInstances.add(permissionPropagatorInstance);
		}

		return permissionPropagatorInstances;
	}

	protected List<PollerProcessor> newPollerProcessors(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<PollerProcessor> pollerProcessorInstances =
			ServiceTrackerCollections.openList(
				PollerProcessor.class, filter, properties);

		if (Validator.isNotNull(portlet.getPollerProcessorClass())) {
			PollerProcessor pollerProcessorInstance =
				(PollerProcessor)newInstance(
					PollerProcessor.class, portlet.getPollerProcessorClass());

			pollerProcessorInstances.add(pollerProcessorInstance);
		}

		return pollerProcessorInstances;
	}

	protected List<MessageListener> newPOPMessageListeners(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<MessageListener> messageListenerInstances =
			ServiceTrackerCollections.openList(
				MessageListener.class, filter, properties);

		if (Validator.isNotNull(portlet.getPopMessageListenerClass())) {
			MessageListener popMessageListenerInstance =
				(MessageListener)newInstance(
					MessageListener.class,
					portlet.getPopMessageListenerClass());

			messageListenerInstances.add(popMessageListenerInstance);
		}

		return messageListenerInstances;
	}

	protected List<PortletDataHandler> newPortletDataHandlers(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<PortletDataHandler> portletDataHandlerInstances =
			ServiceTrackerCollections.openList(
				PortletDataHandler.class, filter, properties);

		if (Validator.isNotNull(portlet.getPortletDataHandlerClass())) {
			PortletDataHandler portletDataHandlerInstance =
				(PortletDataHandler)newInstance(
					PortletDataHandler.class,
					portlet.getPortletDataHandlerClass());

			portletDataHandlerInstance.setPortletId(portlet.getPortletId());

			portletDataHandlerInstances.add(portletDataHandlerInstance);
		}

		return portletDataHandlerInstances;
	}

	protected List<PortletLayoutListener> newPortletLayoutListeners(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<PortletLayoutListener>
			portletLayoutListenerInstances = ServiceTrackerCollections.openList(
				PortletLayoutListener.class, filter, properties);

		if (Validator.isNotNull(portlet.getPortletLayoutListenerClass())) {
			PortletLayoutListener portletLayoutListener =
				(PortletLayoutListener)newInstance(
					PortletLayoutListener.class,
					portlet.getPortletLayoutListenerClass());

			portletLayoutListenerInstances.add(portletLayoutListener);
		}

		return portletLayoutListenerInstances;
	}

	protected List<PreferencesValidator> newPreferencesValidatorInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<PreferencesValidator>
			preferencesValidatorInstances = ServiceTrackerCollections.openList(
				PreferencesValidator.class, filter, properties);

		if (Validator.isNotNull(portlet.getPreferencesValidator())) {
			PreferencesValidator preferencesValidatorInstance =
				(PreferencesValidator)newInstance(
					PreferencesValidator.class,
					portlet.getPreferencesValidator());

			try {
				if (PropsValues.PREFERENCE_VALIDATE_ON_STARTUP) {
					preferencesValidatorInstance.validate(
						PortletPreferencesFactoryUtil.fromDefaultXML(
							portlet.getDefaultPreferences()));
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Portlet with the name " + portlet.getPortletId() +
							" does not have valid default preferences");
				}
			}

			preferencesValidatorInstances.add(preferencesValidatorInstance);
		}

		return preferencesValidatorInstances;
	}

	protected List<SchedulerEventMessageListener>
			newSchedulerEventMessageListeners(
				Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<SchedulerEventMessageListener>
			schedulerEventMessageListeners = ServiceTrackerCollections.openList(
				SchedulerEventMessageListener.class, filter, properties);

		List<SchedulerEntry> schedulerEntries = portlet.getSchedulerEntries();

		for (SchedulerEntry schedulerEntry : schedulerEntries) {
			SchedulerEventMessageListenerWrapper
				schedulerEventMessageListenerWrapper =
					new SchedulerEventMessageListenerWrapper();

			com.liferay.portal.kernel.messaging.MessageListener
				messageListener =
					(com.liferay.portal.kernel.messaging.MessageListener)
						InstanceFactory.newInstance(
							_classLoader,
							schedulerEntry.getEventListenerClass());

			schedulerEventMessageListenerWrapper.setMessageListener(
				messageListener);

			schedulerEventMessageListenerWrapper.setSchedulerEntry(
				schedulerEntry);

			schedulerEventMessageListeners.add(
				schedulerEventMessageListenerWrapper);
		}

		return schedulerEventMessageListeners;
	}

	protected List<SocialActivityInterpreter>
			newSocialActivityInterpreterInstances(
				Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<SocialActivityInterpreter>
			socialActivityInterpreterInstances =
				ServiceTrackerCollections.openList(
					SocialActivityInterpreter.class, filter, properties);

		for (String socialActivityInterpreterClass :
				portlet.getSocialActivityInterpreterClasses()) {

			SocialActivityInterpreter socialActivityInterpreterInstance =
				(SocialActivityInterpreter)newInstance(
					SocialActivityInterpreter.class,
					socialActivityInterpreterClass);

			socialActivityInterpreterInstance =
				new SocialActivityInterpreterImpl(
					portlet.getPortletId(), socialActivityInterpreterInstance);

			socialActivityInterpreterInstances.add(
				socialActivityInterpreterInstance);
		}

		return socialActivityInterpreterInstances;
	}

	protected List<SocialRequestInterpreter>
			newSocialRequestInterpreterInstances(
				Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<SocialRequestInterpreter>
			socialRequestInterpreterInstances =
				ServiceTrackerCollections.openList(
					SocialRequestInterpreter.class, filter, properties);

		if (Validator.isNotNull(portlet.getSocialRequestInterpreterClass())) {
			SocialRequestInterpreter socialRequestInterpreterInstance =
				(SocialRequestInterpreter)newInstance(
					SocialRequestInterpreter.class,
					portlet.getSocialRequestInterpreterClass());

			socialRequestInterpreterInstance = new SocialRequestInterpreterImpl(
				portlet.getPortletId(), socialRequestInterpreterInstance);

			socialRequestInterpreterInstances.add(
				socialRequestInterpreterInstance);
		}

		return socialRequestInterpreterInstances;
	}

	protected List<StagedModelDataHandler<?>> newStagedModelDataHandler(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<StagedModelDataHandler<?>>
			stagedModelDataHandlerInstances =
				ServiceTrackerCollections.openList(
					(Class<StagedModelDataHandler<?>>)(Class<?>)
						StagedModelDataHandler.class,
					filter, properties);

		List<String> stagedModelDataHandlerClasses =
			portlet.getStagedModelDataHandlerClasses();

		for (String stagedModelDataHandlerClass :
				stagedModelDataHandlerClasses) {

			StagedModelDataHandler<?> stagedModelDataHandler =
				(StagedModelDataHandler<?>)newInstance(
					StagedModelDataHandler.class, stagedModelDataHandlerClass);

			stagedModelDataHandlerInstances.add(stagedModelDataHandler);
		}

		return stagedModelDataHandlerInstances;
	}

	protected List<TemplateHandler> newTemplateHandlers(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<TemplateHandler> templateHandlerInstances =
			ServiceTrackerCollections.openList(
				TemplateHandler.class, filter, properties);

		if (Validator.isNotNull(portlet.getTemplateHandlerClass())) {
			TemplateHandler templateHandler = (TemplateHandler)newInstance(
				TemplateHandler.class, portlet.getTemplateHandlerClass());

			templateHandlerInstances.add(templateHandler);
		}

		return templateHandlerInstances;
	}

	protected List<TrashHandler> newTrashHandlerInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<TrashHandler> trashHandlerInstances =
			ServiceTrackerCollections.openList(
				TrashHandler.class, filter, properties);

		for (String trashHandlerClass : portlet.getTrashHandlerClasses()) {
			TrashHandler trashHandlerInstance = (TrashHandler)newInstance(
				TrashHandler.class, trashHandlerClass);

			trashHandlerInstances.add(trashHandlerInstance);
		}

		return trashHandlerInstances;
	}

	protected List<URLEncoder> newURLEncoders(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<URLEncoder> urlEncoderInstances =
			ServiceTrackerCollections.openList(
				URLEncoder.class, filter, properties);

		if (Validator.isNotNull(portlet.getURLEncoderClass())) {
			URLEncoder urlEncoder = (URLEncoder)newInstance(
				URLEncoder.class, portlet.getURLEncoderClass());

			urlEncoderInstances.add(urlEncoder);
		}

		return urlEncoderInstances;
	}

	protected List<UserNotificationDefinition>
			newUserNotificationDefinitionInstances(
				Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<UserNotificationDefinition>
			userNotificationDefinitionInstances =
				ServiceTrackerCollections.openList(
					UserNotificationDefinition.class, filter, properties);

		if (Validator.isNull(portlet.getUserNotificationDefinitions())) {
			return userNotificationDefinitionInstances;
		}

		String xml = getContent(portlet.getUserNotificationDefinitions());

		xml = JavaFieldsParser.parse(_classLoader, xml);

		Document document = UnsecureSAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		for (Element definitionElement : rootElement.elements("definition")) {
			String modelName = definitionElement.elementText("model-name");

			long classNameId = 0;

			if (Validator.isNotNull(modelName)) {
				classNameId = PortalUtil.getClassNameId(modelName);
			}

			int notificationType = GetterUtil.getInteger(
				definitionElement.elementText("notification-type"));

			String description = GetterUtil.getString(
				definitionElement.elementText("description"));

			UserNotificationDefinition userNotificationDefinition =
				new UserNotificationDefinition(
					portlet.getPortletId(), classNameId, notificationType,
					description);

			for (Element deliveryTypeElement :
					definitionElement.elements("delivery-type")) {

				String name = deliveryTypeElement.elementText("name");
				int type = GetterUtil.getInteger(
					deliveryTypeElement.elementText("type"));
				boolean defaultValue = GetterUtil.getBoolean(
					deliveryTypeElement.elementText("default"));
				boolean modifiable = GetterUtil.getBoolean(
					deliveryTypeElement.elementText("modifiable"));

				userNotificationDefinition.addUserNotificationDeliveryType(
					new UserNotificationDeliveryType(
						name, type, defaultValue, modifiable));
			}

			userNotificationDefinitionInstances.add(userNotificationDefinition);
		}

		return userNotificationDefinitionInstances;
	}

	protected List<UserNotificationHandler> newUserNotificationHandlerInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<UserNotificationHandler>
			userNotificationHandlerInstances =
				ServiceTrackerCollections.openList(
					UserNotificationHandler.class, filter, properties);

		for (String userNotificationHandlerClass :
				portlet.getUserNotificationHandlerClasses()) {

			UserNotificationHandler userNotificationHandlerInstance =
				(UserNotificationHandler)newInstance(
					UserNotificationHandler.class,
					userNotificationHandlerClass);

			userNotificationHandlerInstance = new UserNotificationHandlerImpl(
				userNotificationHandlerInstance);

			userNotificationHandlerInstances.add(
				userNotificationHandlerInstance);
		}

		return userNotificationHandlerInstances;
	}

	protected List<WebDAVStorage> newWebDAVStorageInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<WebDAVStorage> webDAVStorageInstances =
			ServiceTrackerCollections.openList(
				WebDAVStorage.class, filter, properties);

		if (Validator.isNotNull(portlet.getWebDAVStorageClass())) {
			WebDAVStorage webDAVStorageInstance = (WebDAVStorage)newInstance(
				WebDAVStorage.class, portlet.getWebDAVStorageClass());

			Map<String, Object> webDAVProperties = new HashMap<>();

			webDAVProperties.put("javax.portlet.name", portlet.getPortletId());
			webDAVProperties.put(
				"webdav.storage.token", portlet.getWebDAVStorageToken());

			webDAVStorageInstances.add(webDAVStorageInstance, webDAVProperties);
		}

		return webDAVStorageInstances;
	}

	protected List<WorkflowHandler<?>> newWorkflowHandlerInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<WorkflowHandler<?>> workflowHandlerInstances =
			ServiceTrackerCollections.openList(
				(Class<WorkflowHandler<?>>)(Class<?>)WorkflowHandler.class,
				filter, properties);

		for (String workflowHandlerClass :
				portlet.getWorkflowHandlerClasses()) {

			WorkflowHandler<?> workflowHandlerInstance =
				(WorkflowHandler<?>)newInstance(
					WorkflowHandler.class, workflowHandlerClass);

			workflowHandlerInstances.add(workflowHandlerInstance);
		}

		return workflowHandlerInstances;
	}

	protected List<Method> newXmlRpcMethodInstances(
			Portlet portlet, Filter filter, Map<String, Object> properties)
		throws Exception {

		ServiceTrackerList<Method> xmlRpcMethodInstances =
			ServiceTrackerCollections.openList(
				Method.class, filter, properties);

		if (Validator.isNotNull(portlet.getXmlRpcMethodClass())) {
			Method xmlRpcMethodInstance = (Method)newInstance(
				Method.class, portlet.getXmlRpcMethodClass());

			xmlRpcMethodInstances.add(xmlRpcMethodInstance);
		}

		return xmlRpcMethodInstances;
	}

	protected void validate() {
		if (_classLoader == null) {
			throw new IllegalStateException("Class loader is null");
		}

		if (_servletContext == null) {
			throw new IllegalStateException("Servlet context is null");
		}

		if (_warFile == null) {
			throw new IllegalStateException("WAR file is null");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletBagFactory.class);

	private ClassLoader _classLoader;
	private Configuration _configuration;
	private ServletContext _servletContext;
	private Boolean _warFile;

}