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

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.security.permission.propagator.PermissionPropagator;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.notifications.UserNotificationHandlerImpl;
import com.liferay.portal.util.JavaFieldsParser;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.internal.FriendlyURLMapperTrackerImpl;
import com.liferay.portlet.internal.PortletBagImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialRequestInterpreter;
import com.liferay.social.kernel.model.impl.SocialActivityInterpreterImpl;
import com.liferay.social.kernel.model.impl.SocialRequestInterpreterImpl;

import java.util.ArrayList;
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
		return create(portlet, false);
	}

	public PortletBag create(Portlet portlet, boolean destroyPrevious)
		throws Exception {

		_validate();

		javax.portlet.Portlet portletInstance = _getPortletInstance(portlet);

		return create(portlet, portletInstance, destroyPrevious);
	}

	public PortletBag create(
			Portlet portlet, javax.portlet.Portlet portletInstance,
			boolean destroyPrevious)
		throws Exception {

		_validate();

		Map<String, Object> properties = new HashMap<>();

		properties.put("javax.portlet.name", portlet.getPortletName());

		Registry registry = RegistryUtil.getRegistry();

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		_registerConfigurationActions(
			registry, portlet, properties, serviceRegistrations);

		_registerIndexers(registry, portlet, properties, serviceRegistrations);

		_registerOpenSearches(
			registry, portlet, properties, serviceRegistrations);

		_registerSchedulerEventMessageListeners(
			registry, portlet, properties, serviceRegistrations);

		FriendlyURLMapperTracker friendlyURLMapperTracker =
			_registerFriendlyURLMappers(portlet);

		_registerURLEncoders(
			registry, portlet, properties, serviceRegistrations);

		_registerPortletDataHandlers(
			registry, portlet, properties, serviceRegistrations);

		_registerStagedModelDataHandler(
			registry, portlet, properties, serviceRegistrations);

		_registerTemplateHandlers(
			registry, portlet, properties, serviceRegistrations);

		_registerPortletLayoutListeners(
			registry, portlet, properties, serviceRegistrations);

		_registerPollerProcessors(
			registry, portlet, properties, serviceRegistrations);

		_registerPOPMessageListeners(
			registry, portlet, properties, serviceRegistrations);

		_registerSocialActivityInterpreterInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerSocialRequestInterpreterInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerUserNotificationDefinitionInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerUserNotificationHandlerInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerWebDAVStorageInstances(registry, portlet);

		_registerXmlRpcMethodInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerControlPanelEntryInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerAssetRendererFactoryInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerAtomCollectionAdapterInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerCustomAttributesDisplayInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerPermissionPropagators(
			registry, portlet, properties, serviceRegistrations);

		_registerTrashHandlerInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerWorkflowHandlerInstances(
			registry, portlet, properties, serviceRegistrations);

		_registerPreferencesValidatorInstances(
			registry, portlet, properties, serviceRegistrations);

		PortletBag portletBag = new PortletBagImpl(
			portlet.getPortletName(), _servletContext, portletInstance,
			portlet.getResourceBundle(), friendlyURLMapperTracker,
			serviceRegistrations);

		PortletBagPool.put(portlet.getRootPortletId(), portletBag);

		try {
			PortletInstanceFactoryUtil.create(
				portlet, _servletContext, destroyPrevious);
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

	/**
	 * @see FriendlyURLMapperTrackerImpl#getContent(ClassLoader, String)
	 */
	private String _getContent(String fileName) throws Exception {
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
			String[] values = entry.getValue();

			if (values.length == 0) {
				continue;
			}

			String value = values[0];

			xml = StringUtil.replace(xml, "@" + entry.getKey() + "@", value);
		}

		return xml;
	}

	private String _getPluginPropertyValue(String propertyKey)
		throws Exception {

		if (_configuration == null) {
			_configuration = ConfigurationFactoryUtil.getConfiguration(
				_classLoader, "portlet");
		}

		return _configuration.get(propertyKey);
	}

	private javax.portlet.Portlet _getPortletInstance(Portlet portlet)
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

	private <T> T _newInstance(
			Class<? extends T> interfaceClass, String implClassName)
		throws Exception {

		if (_warFile) {
			return (T)ProxyFactory.newInstance(
				_classLoader, new Class<?>[] {interfaceClass}, implClassName);
		}

		Class<?> clazz = _classLoader.loadClass(implClassName);

		return (T)clazz.newInstance();
	}

	private void _registerAssetRendererFactoryInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (String assetRendererFactoryClass :
				portlet.getAssetRendererFactoryClasses()) {

			String assetRendererEnabledPropertyKey =
				PropsKeys.ASSET_RENDERER_ENABLED + assetRendererFactoryClass;

			String assetRendererEnabledPropertyValue = null;

			if (_warFile) {
				assetRendererEnabledPropertyValue = _getPluginPropertyValue(
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
					_newInstance(
						AssetRendererFactory.class, assetRendererFactoryClass);

				assetRendererFactoryInstance.setClassName(
					assetRendererFactoryInstance.getClassName());
				assetRendererFactoryInstance.setPortletId(
					portlet.getPortletId());

				ServiceRegistration<?> serviceRegistration =
					registry.registerService(
						AssetRendererFactory.class,
						assetRendererFactoryInstance, properties);

				serviceRegistrations.add(serviceRegistration);
			}
		}
	}

	private void _registerAtomCollectionAdapterInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (String atomCollectionAdapterClass :
				portlet.getAtomCollectionAdapterClasses()) {

			AtomCollectionAdapter<?> atomCollectionAdapterInstance =
				_newInstance(
					AtomCollectionAdapter.class, atomCollectionAdapterClass);

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					AtomCollectionAdapter.class, atomCollectionAdapterInstance,
					properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerConfigurationActions(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getConfigurationActionClass())) {
			ConfigurationAction configurationAction = _newInstance(
				ConfigurationAction.class,
				portlet.getConfigurationActionClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					ConfigurationAction.class, configurationAction, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerControlPanelEntryInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getControlPanelEntryClass())) {
			ControlPanelEntry controlPanelEntryInstance = _newInstance(
				ControlPanelEntry.class, portlet.getControlPanelEntryClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					ControlPanelEntry.class, controlPanelEntryInstance,
					properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerCustomAttributesDisplayInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (String customAttributesDisplayClass :
				portlet.getCustomAttributesDisplayClasses()) {

			CustomAttributesDisplay customAttributesDisplayInstance =
				_newInstance(
					CustomAttributesDisplay.class,
					customAttributesDisplayClass);

			customAttributesDisplayInstance.setClassNameId(
				PortalUtil.getClassNameId(
					customAttributesDisplayInstance.getClassName()));
			customAttributesDisplayInstance.setPortletId(
				portlet.getPortletId());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					CustomAttributesDisplay.class,
					customAttributesDisplayInstance, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private FriendlyURLMapperTracker _registerFriendlyURLMappers(
			Portlet portlet)
		throws Exception {

		FriendlyURLMapperTracker friendlyURLMapperTracker =
			new FriendlyURLMapperTrackerImpl(portlet);

		if (Validator.isNotNull(portlet.getFriendlyURLMapperClass())) {
			FriendlyURLMapper friendlyURLMapper = _newInstance(
				FriendlyURLMapper.class, portlet.getFriendlyURLMapperClass());

			friendlyURLMapperTracker.register(friendlyURLMapper);
		}

		return friendlyURLMapperTracker;
	}

	private void _registerIndexers(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (String indexerClass : portlet.getIndexerClasses()) {
			Indexer<?> indexerInstance = _newInstance(
				Indexer.class, indexerClass);

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					Indexer.class, indexerInstance, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerOpenSearches(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getOpenSearchClass())) {
			OpenSearch openSearch = _newInstance(
				OpenSearch.class, portlet.getOpenSearchClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					OpenSearch.class, openSearch, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerPermissionPropagators(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getPermissionPropagatorClass())) {
			PermissionPropagator permissionPropagatorInstance = _newInstance(
				PermissionPropagator.class,
				portlet.getPermissionPropagatorClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					PermissionPropagator.class, permissionPropagatorInstance,
					properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerPollerProcessors(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getPollerProcessorClass())) {
			PollerProcessor pollerProcessorInstance = _newInstance(
				PollerProcessor.class, portlet.getPollerProcessorClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					PollerProcessor.class, pollerProcessorInstance, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerPOPMessageListeners(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getPopMessageListenerClass())) {
			MessageListener popMessageListenerInstance = _newInstance(
				MessageListener.class, portlet.getPopMessageListenerClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					MessageListener.class, popMessageListenerInstance,
					properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerPortletDataHandlers(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getPortletDataHandlerClass())) {
			PortletDataHandler portletDataHandlerInstance = _newInstance(
				PortletDataHandler.class, portlet.getPortletDataHandlerClass());

			portletDataHandlerInstance.setPortletId(portlet.getPortletId());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					PortletDataHandler.class, portletDataHandlerInstance,
					properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerPortletLayoutListeners(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getPortletLayoutListenerClass())) {
			PortletLayoutListener portletLayoutListener = _newInstance(
				PortletLayoutListener.class,
				portlet.getPortletLayoutListenerClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					PortletLayoutListener.class, portletLayoutListener,
					properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerPreferencesValidatorInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getPreferencesValidator())) {
			PreferencesValidator preferencesValidatorInstance = _newInstance(
				PreferencesValidator.class, portlet.getPreferencesValidator());

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

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					PreferencesValidator.class, preferencesValidatorInstance,
					properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerSchedulerEventMessageListeners(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (SchedulerEntry schedulerEntry : portlet.getSchedulerEntries()) {
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

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					SchedulerEventMessageListener.class,
					schedulerEventMessageListenerWrapper, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerSocialActivityInterpreterInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (String socialActivityInterpreterClass :
				portlet.getSocialActivityInterpreterClasses()) {

			SocialActivityInterpreter socialActivityInterpreterInstance =
				_newInstance(
					SocialActivityInterpreter.class,
					socialActivityInterpreterClass);

			socialActivityInterpreterInstance =
				new SocialActivityInterpreterImpl(
					portlet.getPortletId(), socialActivityInterpreterInstance);

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					SocialActivityInterpreter.class,
					socialActivityInterpreterInstance, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerSocialRequestInterpreterInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getSocialRequestInterpreterClass())) {
			SocialRequestInterpreter socialRequestInterpreterInstance =
				_newInstance(
					SocialRequestInterpreter.class,
					portlet.getSocialRequestInterpreterClass());

			socialRequestInterpreterInstance = new SocialRequestInterpreterImpl(
				portlet.getPortletId(), socialRequestInterpreterInstance);

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					SocialRequestInterpreter.class,
					socialRequestInterpreterInstance, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerStagedModelDataHandler(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (String stagedModelDataHandlerClass :
				portlet.getStagedModelDataHandlerClasses()) {

			StagedModelDataHandler<?> stagedModelDataHandler = _newInstance(
				StagedModelDataHandler.class, stagedModelDataHandlerClass);

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					StagedModelDataHandler.class, stagedModelDataHandler,
					properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerTemplateHandlers(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getTemplateHandlerClass())) {
			TemplateHandler templateHandler = _newInstance(
				TemplateHandler.class, portlet.getTemplateHandlerClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					TemplateHandler.class, templateHandler, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerTrashHandlerInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (String trashHandlerClass : portlet.getTrashHandlerClasses()) {
			TrashHandler trashHandlerInstance = _newInstance(
				TrashHandler.class, trashHandlerClass);

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					TrashHandler.class, trashHandlerInstance, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerURLEncoders(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getURLEncoderClass())) {
			URLEncoder urlEncoder = _newInstance(
				URLEncoder.class, portlet.getURLEncoderClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					URLEncoder.class, urlEncoder, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerUserNotificationDefinitionInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNull(portlet.getUserNotificationDefinitions())) {
			return;
		}

		String xml = _getContent(portlet.getUserNotificationDefinitions());

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

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					UserNotificationDefinition.class,
					userNotificationDefinition, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerUserNotificationHandlerInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (String userNotificationHandlerClass :
				portlet.getUserNotificationHandlerClasses()) {

			UserNotificationHandler userNotificationHandlerInstance =
				_newInstance(
					UserNotificationHandler.class,
					userNotificationHandlerClass);

			userNotificationHandlerInstance = new UserNotificationHandlerImpl(
				userNotificationHandlerInstance);

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					UserNotificationHandler.class,
					userNotificationHandlerInstance, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerWebDAVStorageInstances(
			Registry registry, Portlet portlet)
		throws Exception {

		if (Validator.isNotNull(portlet.getWebDAVStorageClass())) {
			WebDAVStorage webDAVStorageInstance = _newInstance(
				WebDAVStorage.class, portlet.getWebDAVStorageClass());

			Map<String, Object> webDAVProperties = new HashMap<>();

			webDAVProperties.put("javax.portlet.name", portlet.getPortletId());
			webDAVProperties.put(
				"webdav.storage.token", portlet.getWebDAVStorageToken());

			registry.registerService(
				WebDAVStorage.class, webDAVStorageInstance, webDAVProperties);
		}
	}

	private void _registerWorkflowHandlerInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		for (String workflowHandlerClass :
				portlet.getWorkflowHandlerClasses()) {

			WorkflowHandler<?> workflowHandlerInstance = _newInstance(
				WorkflowHandler.class, workflowHandlerClass);

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					WorkflowHandler.class, workflowHandlerInstance, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _registerXmlRpcMethodInstances(
			Registry registry, Portlet portlet, Map<String, Object> properties,
			List<ServiceRegistration<?>> serviceRegistrations)
		throws Exception {

		if (Validator.isNotNull(portlet.getXmlRpcMethodClass())) {
			Method xmlRpcMethodInstance = _newInstance(
				Method.class, portlet.getXmlRpcMethodClass());

			ServiceRegistration<?> serviceRegistration =
				registry.registerService(
					Method.class, xmlRpcMethodInstance, properties);

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _validate() {
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