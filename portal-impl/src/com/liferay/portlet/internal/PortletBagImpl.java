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

package com.liferay.portlet.internal;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.security.permission.propagator.PermissionPropagator;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.language.LanguageResources;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialRequestInterpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletBagImpl implements PortletBag {

	public PortletBagImpl(
		String portletName, ServletContext servletContext,
		Portlet portletInstance, String resourceBundleBaseName,
		FriendlyURLMapperTracker friendlyURLMapperTracker,
		List<ServiceRegistration<?>> serviceRegistrations) {

		_portletName = portletName;
		_servletContext = servletContext;
		_portletInstance = portletInstance;
		_resourceBundleBaseName = resourceBundleBaseName;
		_friendlyURLMapperTracker = friendlyURLMapperTracker;
		_serviceRegistrations = serviceRegistrations;

		_filterString =
			"(|(javax.portlet.name=" + portletName +
				")(javax.portlet.name=ALL))";
	}

	@Override
	public Object clone() {
		return new PortletBagImpl(
			getPortletName(), getServletContext(), getPortletInstance(),
			getResourceBundleBaseName(), getFriendlyURLMapperTracker(), null);
	}

	@Override
	public void destroy() {
		if (_serviceRegistrations == null) {
			return;
		}

		_friendlyURLMapperTracker.close();

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Override
	public List<AssetRendererFactory<?>> getAssetRendererFactoryInstances() {
		if (_assetRendererFactoryInstances == null) {
			synchronized (this) {
				if (_assetRendererFactoryInstances == null) {
					_assetRendererFactoryInstances =
						ServiceTrackerListFactory.open(
							_bundleContext,
							(Class<AssetRendererFactory<?>>)
								(Class<?>)AssetRendererFactory.class,
							_filterString);
				}
			}
		}

		return _toList(_assetRendererFactoryInstances);
	}

	@Override
	public List<AtomCollectionAdapter<?>> getAtomCollectionAdapterInstances() {
		if (_atomCollectionAdapterInstances == null) {
			synchronized (this) {
				if (_atomCollectionAdapterInstances == null) {
					_atomCollectionAdapterInstances =
						ServiceTrackerListFactory.open(
							_bundleContext,
							(Class<AtomCollectionAdapter<?>>)
								(Class<?>)AtomCollectionAdapter.class,
							_filterString);
				}
			}
		}

		return _toList(_atomCollectionAdapterInstances);
	}

	@Override
	public List<ConfigurationAction> getConfigurationActionInstances() {
		if (_configurationActionInstances == null) {
			synchronized (this) {
				if (_configurationActionInstances == null) {
					_configurationActionInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, ConfigurationAction.class,
							_filterString);
				}
			}
		}

		return _toList(_configurationActionInstances);
	}

	@Override
	public List<ControlPanelEntry> getControlPanelEntryInstances() {
		if (_controlPanelEntryInstances == null) {
			synchronized (this) {
				if (_controlPanelEntryInstances == null) {
					_controlPanelEntryInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, ControlPanelEntry.class,
							_filterString);
				}
			}
		}

		return _toList(_controlPanelEntryInstances);
	}

	@Override
	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		if (_customAttributesDisplayInstances == null) {
			synchronized (this) {
				if (_customAttributesDisplayInstances == null) {
					_customAttributesDisplayInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, CustomAttributesDisplay.class,
							_filterString);
				}
			}
		}

		return _toList(_customAttributesDisplayInstances);
	}

	@Override
	public FriendlyURLMapperTracker getFriendlyURLMapperTracker() {
		return _friendlyURLMapperTracker;
	}

	@Override
	public List<Indexer<?>> getIndexerInstances() {
		if (_indexerInstances == null) {
			synchronized (this) {
				if (_indexerInstances == null) {
					_indexerInstances = ServiceTrackerListFactory.open(
						_bundleContext,
						(Class<Indexer<?>>)(Class<?>)Indexer.class,
						_filterString);
				}
			}
		}

		return _toList(_indexerInstances);
	}

	@Override
	public List<OpenSearch> getOpenSearchInstances() {
		if (_openSearchInstances == null) {
			synchronized (this) {
				if (_openSearchInstances == null) {
					_openSearchInstances = ServiceTrackerListFactory.open(
						_bundleContext, OpenSearch.class, _filterString);
				}
			}
		}

		return _toList(_openSearchInstances);
	}

	@Override
	public List<PermissionPropagator> getPermissionPropagatorInstances() {
		if (_permissionPropagatorInstances == null) {
			synchronized (this) {
				if (_permissionPropagatorInstances == null) {
					_permissionPropagatorInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, PermissionPropagator.class,
							_filterString);
				}
			}
		}

		return _toList(_permissionPropagatorInstances);
	}

	@Override
	public List<PollerProcessor> getPollerProcessorInstances() {
		if (_pollerProcessorInstances == null) {
			synchronized (this) {
				if (_pollerProcessorInstances == null) {
					_pollerProcessorInstances = ServiceTrackerListFactory.open(
						_bundleContext, PollerProcessor.class, _filterString);
				}
			}
		}

		return _toList(_pollerProcessorInstances);
	}

	@Override
	public List<MessageListener> getPopMessageListenerInstances() {
		if (_popMessageListenerInstances == null) {
			synchronized (this) {
				if (_popMessageListenerInstances == null) {
					_popMessageListenerInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, MessageListener.class,
							_filterString);
				}
			}
		}

		return _toList(_popMessageListenerInstances);
	}

	@Override
	public List<PortletDataHandler> getPortletDataHandlerInstances() {
		if (_portletDataHandlerInstances == null) {
			synchronized (this) {
				if (_portletDataHandlerInstances == null) {
					_portletDataHandlerInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, PortletDataHandler.class,
							_filterString);
				}
			}
		}

		return _toList(_portletDataHandlerInstances);
	}

	@Override
	public Portlet getPortletInstance() {
		return _portletInstance;
	}

	@Override
	public List<PortletLayoutListener> getPortletLayoutListenerInstances() {
		if (_portletLayoutListenerInstances == null) {
			synchronized (this) {
				if (_portletLayoutListenerInstances == null) {
					_portletLayoutListenerInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, PortletLayoutListener.class,
							_filterString);
				}
			}
		}

		return _toList(_portletLayoutListenerInstances);
	}

	@Override
	public String getPortletName() {
		return _portletName;
	}

	@Override
	public List<PreferencesValidator> getPreferencesValidatorInstances() {
		if (_preferencesValidatorInstances == null) {
			synchronized (this) {
				if (_preferencesValidatorInstances == null) {
					_preferencesValidatorInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, PreferencesValidator.class,
							_filterString);
				}
			}
		}

		return _toList(_preferencesValidatorInstances);
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader = _resourceBundleLoader;

		if (resourceBundleLoader == null) {
			synchronized (this) {
				if (_resourceBundleLoader == null) {
					_resourceBundleLoader =
						ServiceProxyFactory.newServiceTrackedInstance(
							ResourceBundleLoader.class, PortletBagImpl.class,
							this, "_resourceBundleLoader",
							StringBundler.concat(
								"(resource.bundle.base.name=",
								getResourceBundleBaseName(),
								")(servlet.context.name=",
								_servletContext.getServletContextName(), ")"),
							false);
				}

				resourceBundleLoader = _resourceBundleLoader;
			}
		}

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		if (resourceBundle == null) {
			resourceBundle = LanguageResources.getResourceBundle(locale);
		}

		return resourceBundle;
	}

	@Override
	public String getResourceBundleBaseName() {
		return _resourceBundleBaseName;
	}

	@Override
	public List<SchedulerEventMessageListener>
		getSchedulerEventMessageListeners() {

		if (_schedulerEventMessageListeners == null) {
			synchronized (this) {
				if (_schedulerEventMessageListeners == null) {
					_schedulerEventMessageListeners =
						ServiceTrackerListFactory.open(
							_bundleContext, SchedulerEventMessageListener.class,
							_filterString);
				}
			}
		}

		return _toList(_schedulerEventMessageListeners);
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<SocialActivityInterpreter>
		getSocialActivityInterpreterInstances() {

		if (_socialActivityInterpreterInstances == null) {
			synchronized (this) {
				if (_socialActivityInterpreterInstances == null) {
					_socialActivityInterpreterInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, SocialActivityInterpreter.class,
							_filterString);
				}
			}
		}

		return _toList(_socialActivityInterpreterInstances);
	}

	@Override
	public List<SocialRequestInterpreter>
		getSocialRequestInterpreterInstances() {

		if (_socialRequestInterpreterInstances == null) {
			synchronized (this) {
				if (_socialRequestInterpreterInstances == null) {
					_socialRequestInterpreterInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, SocialRequestInterpreter.class,
							_filterString);
				}
			}
		}

		return _toList(_socialRequestInterpreterInstances);
	}

	@Override
	public List<StagedModelDataHandler<?>>
		getStagedModelDataHandlerInstances() {

		if (_stagedModelDataHandlerInstances == null) {
			synchronized (this) {
				if (_stagedModelDataHandlerInstances == null) {
					_stagedModelDataHandlerInstances =
						ServiceTrackerListFactory.open(
							_bundleContext,
							(Class<StagedModelDataHandler<?>>)
								(Class<?>)StagedModelDataHandler.class,
							_filterString);
				}
			}
		}

		return _toList(_stagedModelDataHandlerInstances);
	}

	@Override
	public List<TemplateHandler> getTemplateHandlerInstances() {
		if (_templateHandlerInstances == null) {
			synchronized (this) {
				if (_templateHandlerInstances == null) {
					_templateHandlerInstances = ServiceTrackerListFactory.open(
						_bundleContext, TemplateHandler.class, _filterString);
				}
			}
		}

		return _toList(_templateHandlerInstances);
	}

	@Override
	public List<TrashHandler> getTrashHandlerInstances() {
		if (_trashHandlerInstances == null) {
			synchronized (this) {
				if (_trashHandlerInstances == null) {
					_trashHandlerInstances = ServiceTrackerListFactory.open(
						_bundleContext, TrashHandler.class, _filterString);
				}
			}
		}

		return _toList(_trashHandlerInstances);
	}

	@Override
	public List<URLEncoder> getURLEncoderInstances() {
		if (_urlEncoderInstances == null) {
			synchronized (this) {
				if (_urlEncoderInstances == null) {
					_urlEncoderInstances = ServiceTrackerListFactory.open(
						_bundleContext, URLEncoder.class, _filterString);
				}
			}
		}

		return _toList(_urlEncoderInstances);
	}

	@Override
	public List<UserNotificationDefinition>
		getUserNotificationDefinitionInstances() {

		if (_userNotificationDefinitionInstances == null) {
			synchronized (this) {
				if (_userNotificationDefinitionInstances == null) {
					_userNotificationDefinitionInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, UserNotificationDefinition.class,
							_filterString);
				}
			}
		}

		return _toList(_userNotificationDefinitionInstances);
	}

	@Override
	public List<UserNotificationHandler> getUserNotificationHandlerInstances() {
		if (_userNotificationHandlerInstances == null) {
			synchronized (this) {
				if (_userNotificationHandlerInstances == null) {
					_userNotificationHandlerInstances =
						ServiceTrackerListFactory.open(
							_bundleContext, UserNotificationHandler.class,
							_filterString);
				}
			}
		}

		return _toList(_userNotificationHandlerInstances);
	}

	@Override
	public List<WebDAVStorage> getWebDAVStorageInstances() {
		if (_webDAVStorageInstances == null) {
			synchronized (this) {
				if (_webDAVStorageInstances == null) {
					_webDAVStorageInstances = ServiceTrackerListFactory.open(
						_bundleContext, WebDAVStorage.class, _filterString);
				}
			}
		}

		return _toList(_webDAVStorageInstances);
	}

	@Override
	public List<WorkflowHandler<?>> getWorkflowHandlerInstances() {
		if (_workflowHandlerInstances == null) {
			synchronized (this) {
				if (_workflowHandlerInstances == null) {
					_workflowHandlerInstances = ServiceTrackerListFactory.open(
						_bundleContext,
						(Class<WorkflowHandler<?>>)
							(Class<?>)WorkflowHandler.class,
						_filterString);
				}
			}
		}

		return _toList(_workflowHandlerInstances);
	}

	@Override
	public List<Method> getXmlRpcMethodInstances() {
		if (_xmlRpcMethodInstances == null) {
			synchronized (this) {
				if (_xmlRpcMethodInstances == null) {
					_xmlRpcMethodInstances = ServiceTrackerListFactory.open(
						_bundleContext, Method.class, _filterString);
				}
			}
		}

		return _toList(_xmlRpcMethodInstances);
	}

	@Override
	public void setPortletInstance(Portlet portletInstance) {
		_portletInstance = portletInstance;
	}

	@Override
	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	private <T> List<T> _toList(ServiceTrackerList<T> serviceTrackerList) {
		List<T> list = new ArrayList<>(serviceTrackerList.size());

		serviceTrackerList.forEach(list::add);

		return Collections.unmodifiableList(list);
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private volatile ServiceTrackerList<AssetRendererFactory<?>>
		_assetRendererFactoryInstances;
	private volatile ServiceTrackerList<AtomCollectionAdapter<?>>
		_atomCollectionAdapterInstances;
	private volatile ServiceTrackerList<ConfigurationAction>
		_configurationActionInstances;
	private volatile ServiceTrackerList<ControlPanelEntry>
		_controlPanelEntryInstances;
	private volatile ServiceTrackerList<CustomAttributesDisplay>
		_customAttributesDisplayInstances;
	private final String _filterString;
	private final FriendlyURLMapperTracker _friendlyURLMapperTracker;
	private volatile ServiceTrackerList<Indexer<?>> _indexerInstances;
	private volatile ServiceTrackerList<OpenSearch> _openSearchInstances;
	private volatile ServiceTrackerList<PermissionPropagator>
		_permissionPropagatorInstances;
	private volatile ServiceTrackerList<PollerProcessor>
		_pollerProcessorInstances;
	private volatile ServiceTrackerList<MessageListener>
		_popMessageListenerInstances;
	private volatile ServiceTrackerList<PortletDataHandler>
		_portletDataHandlerInstances;
	private Portlet _portletInstance;
	private volatile ServiceTrackerList<PortletLayoutListener>
		_portletLayoutListenerInstances;
	private String _portletName;
	private volatile ServiceTrackerList<PreferencesValidator>
		_preferencesValidatorInstances;
	private final String _resourceBundleBaseName;
	private volatile ResourceBundleLoader _resourceBundleLoader;
	private volatile ServiceTrackerList<SchedulerEventMessageListener>
		_schedulerEventMessageListeners;
	private final List<ServiceRegistration<?>> _serviceRegistrations;
	private final ServletContext _servletContext;
	private volatile ServiceTrackerList<SocialActivityInterpreter>
		_socialActivityInterpreterInstances;
	private volatile ServiceTrackerList<SocialRequestInterpreter>
		_socialRequestInterpreterInstances;
	private volatile ServiceTrackerList<StagedModelDataHandler<?>>
		_stagedModelDataHandlerInstances;
	private volatile ServiceTrackerList<TemplateHandler>
		_templateHandlerInstances;
	private volatile ServiceTrackerList<TrashHandler> _trashHandlerInstances;
	private volatile ServiceTrackerList<URLEncoder> _urlEncoderInstances;
	private volatile ServiceTrackerList<UserNotificationDefinition>
		_userNotificationDefinitionInstances;
	private volatile ServiceTrackerList<UserNotificationHandler>
		_userNotificationHandlerInstances;
	private volatile ServiceTrackerList<WebDAVStorage> _webDAVStorageInstances;
	private volatile ServiceTrackerList<WorkflowHandler<?>>
		_workflowHandlerInstances;
	private volatile ServiceTrackerList<Method> _xmlRpcMethodInstances;

	@SuppressWarnings("deprecation")
	private static class PermissionPropagatorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<com.liferay.portal.kernel.security.permission.PermissionPropagator,
			 ServiceRegistration<PermissionPropagator>> {

		@Override
		public ServiceRegistration<PermissionPropagator> addingService(
			ServiceReference
				<com.liferay.portal.kernel.security.permission.
					PermissionPropagator> serviceReference) {

			return _bundleContext.registerService(
				PermissionPropagator.class,
				_bundleContext.getService(serviceReference),
				_toProperties(serviceReference));
		}

		@Override
		public void modifiedService(
			ServiceReference
				<com.liferay.portal.kernel.security.permission.
					PermissionPropagator> serviceReference,
			ServiceRegistration<PermissionPropagator> serviceRegistration) {

			serviceRegistration.setProperties(_toProperties(serviceReference));
		}

		@Override
		public void removedService(
			ServiceReference
				<com.liferay.portal.kernel.security.permission.
					PermissionPropagator> serviceReference,
			ServiceRegistration<PermissionPropagator> serviceRegistration) {

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

		private Dictionary<String, Object> _toProperties(
			ServiceReference<?> serviceReference) {

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			for (String key : serviceReference.getPropertyKeys()) {
				Object value = serviceReference.getProperty(key);

				properties.put(key, value);
			}

			return properties;
		}

	}

	static {
		ServiceTracker<?, ?> serviceTracker = new ServiceTracker<>(
			_bundleContext,
			com.liferay.portal.kernel.security.permission.PermissionPropagator.
				class,
			new PermissionPropagatorServiceTrackerCustomizer());

		serviceTracker.open();
	}

}