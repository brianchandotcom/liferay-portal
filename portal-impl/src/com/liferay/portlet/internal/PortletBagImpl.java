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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
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
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialRequestInterpreter;

import java.util.Collections;
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
		Portlet portletInstance, String resourceBundleBaseName,
		FriendlyURLMapperTracker friendlyURLMapperTracker,
		List<ServiceRegistration<?>> serviceRegistrations) {

		_portletName = portletName;
		_servletContext = servletContext;
		_portletInstance = portletInstance;
		_resourceBundleBaseName = resourceBundleBaseName;
		_friendlyURLMapperTracker = friendlyURLMapperTracker;
		_serviceRegistrations = serviceRegistrations;

		Registry registry = RegistryUtil.getRegistry();

		_filter = registry.getFilter(
			"(|(javax.portlet.name=" + portletName +
				")(javax.portlet.name=ALL))");

		_properties = Collections.singletonMap(
			"javax.portlet.name", portletName);
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
						ServiceTrackerCollections.openList(
							(Class<AssetRendererFactory<?>>)
								(Class<?>)AssetRendererFactory.class,
							_filter, _properties);
				}
			}
		}

		return _assetRendererFactoryInstances;
	}

	@Override
	public List<AtomCollectionAdapter<?>> getAtomCollectionAdapterInstances() {
		if (_atomCollectionAdapterInstances == null) {
			synchronized (this) {
				if (_atomCollectionAdapterInstances == null) {
					_atomCollectionAdapterInstances =
						ServiceTrackerCollections.openList(
							(Class<AtomCollectionAdapter<?>>)
								(Class<?>)AtomCollectionAdapter.class,
							_filter, _properties);
				}
			}
		}

		return _atomCollectionAdapterInstances;
	}

	@Override
	public List<ConfigurationAction> getConfigurationActionInstances() {
		if (_configurationActionInstances == null) {
			synchronized (this) {
				if (_configurationActionInstances == null) {
					_configurationActionInstances =
						ServiceTrackerCollections.openList(
							ConfigurationAction.class, _filter, _properties);
				}
			}
		}

		return _configurationActionInstances;
	}

	@Override
	public List<ControlPanelEntry> getControlPanelEntryInstances() {
		if (_controlPanelEntryInstances == null) {
			synchronized (this) {
				if (_controlPanelEntryInstances == null) {
					_controlPanelEntryInstances =
						ServiceTrackerCollections.openList(
							ControlPanelEntry.class, _filter, _properties);
				}
			}
		}

		return _controlPanelEntryInstances;
	}

	@Override
	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		if (_customAttributesDisplayInstances == null) {
			synchronized (this) {
				if (_customAttributesDisplayInstances == null) {
					_customAttributesDisplayInstances =
						ServiceTrackerCollections.openList(
							CustomAttributesDisplay.class, _filter,
							_properties);
				}
			}
		}

		return _customAttributesDisplayInstances;
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
					_indexerInstances = ServiceTrackerCollections.openList(
						(Class<Indexer<?>>)(Class<?>)Indexer.class, _filter,
						_properties);
				}
			}
		}

		return _indexerInstances;
	}

	@Override
	public List<OpenSearch> getOpenSearchInstances() {
		if (_openSearchInstances == null) {
			synchronized (this) {
				if (_openSearchInstances == null) {
					_openSearchInstances = ServiceTrackerCollections.openList(
						OpenSearch.class, _filter, _properties);
				}
			}
		}

		return _openSearchInstances;
	}

	@Override
	public List<PermissionPropagator> getPermissionPropagatorInstances() {
		if (_permissionPropagatorInstances == null) {
			synchronized (this) {
				if (_permissionPropagatorInstances == null) {
					_permissionPropagatorInstances =
						ServiceTrackerCollections.openList(
							PermissionPropagator.class, _filter, _properties);
				}
			}
		}

		return _permissionPropagatorInstances;
	}

	@Override
	public List<PollerProcessor> getPollerProcessorInstances() {
		if (_pollerProcessorInstances == null) {
			synchronized (this) {
				if (_pollerProcessorInstances == null) {
					_pollerProcessorInstances =
						ServiceTrackerCollections.openList(
							PollerProcessor.class, _filter, _properties);
				}
			}
		}

		return _pollerProcessorInstances;
	}

	@Override
	public List<MessageListener> getPopMessageListenerInstances() {
		if (_popMessageListenerInstances == null) {
			synchronized (this) {
				if (_popMessageListenerInstances == null) {
					_popMessageListenerInstances =
						ServiceTrackerCollections.openList(
							MessageListener.class, _filter, _properties);
				}
			}
		}

		return _popMessageListenerInstances;
	}

	@Override
	public List<PortletDataHandler> getPortletDataHandlerInstances() {
		if (_portletDataHandlerInstances == null) {
			synchronized (this) {
				if (_portletDataHandlerInstances == null) {
					_portletDataHandlerInstances =
						ServiceTrackerCollections.openList(
							PortletDataHandler.class, _filter, _properties);
				}
			}
		}

		return _portletDataHandlerInstances;
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
						ServiceTrackerCollections.openList(
							PortletLayoutListener.class, _filter, _properties);
				}
			}
		}

		return _portletLayoutListenerInstances;
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
						ServiceTrackerCollections.openList(
							PreferencesValidator.class, _filter, _properties);
				}
			}
		}

		return _preferencesValidatorInstances;
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader = _resourceBundleLoader;

		if (resourceBundleLoader == null) {
			synchronized (this) {
				if (_resourceBundleLoader == null) {
					StringBundler sb = new StringBundler(5);

					sb.append("(resource.bundle.base.name=");
					sb.append(getResourceBundleBaseName());
					sb.append(")(servlet.context.name=");
					sb.append(_servletContext.getServletContextName());
					sb.append(")");

					_resourceBundleLoader =
						ServiceProxyFactory.newServiceTrackedInstance(
							ResourceBundleLoader.class, PortletBagImpl.class,
							this, "_resourceBundleLoader", sb.toString(),
							false);
				}

				resourceBundleLoader = _resourceBundleLoader;
			}
		}

		return resourceBundleLoader.loadResourceBundle(locale);
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
						ServiceTrackerCollections.openList(
							SchedulerEventMessageListener.class, _filter,
							_properties);
				}
			}
		}

		return _schedulerEventMessageListeners;
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
						ServiceTrackerCollections.openList(
							SocialActivityInterpreter.class, _filter,
							_properties);
				}
			}
		}

		return _socialActivityInterpreterInstances;
	}

	@Override
	public List<SocialRequestInterpreter>
		getSocialRequestInterpreterInstances() {

		if (_socialRequestInterpreterInstances == null) {
			synchronized (this) {
				if (_socialRequestInterpreterInstances == null) {
					_socialRequestInterpreterInstances =
						ServiceTrackerCollections.openList(
							SocialRequestInterpreter.class, _filter,
							_properties);
				}
			}
		}

		return _socialRequestInterpreterInstances;
	}

	@Override
	public List<StagedModelDataHandler<?>>
		getStagedModelDataHandlerInstances() {

		if (_stagedModelDataHandlerInstances == null) {
			synchronized (this) {
				if (_stagedModelDataHandlerInstances == null) {
					_stagedModelDataHandlerInstances =
						ServiceTrackerCollections.openList(
							(Class<StagedModelDataHandler<?>>)
								(Class<?>)StagedModelDataHandler.class,
							_filter, _properties);
				}
			}
		}

		return _stagedModelDataHandlerInstances;
	}

	@Override
	public List<TemplateHandler> getTemplateHandlerInstances() {
		if (_templateHandlerInstances == null) {
			synchronized (this) {
				if (_templateHandlerInstances == null) {
					_templateHandlerInstances =
						ServiceTrackerCollections.openList(
							TemplateHandler.class, _filter, _properties);
				}
			}
		}

		return _templateHandlerInstances;
	}

	@Override
	public List<TrashHandler> getTrashHandlerInstances() {
		if (_trashHandlerInstances == null) {
			synchronized (this) {
				if (_trashHandlerInstances == null) {
					_trashHandlerInstances = ServiceTrackerCollections.openList(
						TrashHandler.class, _filter, _properties);
				}
			}
		}

		return _trashHandlerInstances;
	}

	@Override
	public List<URLEncoder> getURLEncoderInstances() {
		if (_urlEncoderInstances == null) {
			synchronized (this) {
				if (_urlEncoderInstances == null) {
					_urlEncoderInstances = ServiceTrackerCollections.openList(
						URLEncoder.class, _filter, _properties);
				}
			}
		}

		return _urlEncoderInstances;
	}

	@Override
	public List<UserNotificationDefinition>
		getUserNotificationDefinitionInstances() {

		if (_userNotificationDefinitionInstances == null) {
			synchronized (this) {
				if (_userNotificationDefinitionInstances == null) {
					_userNotificationDefinitionInstances =
						ServiceTrackerCollections.openList(
							UserNotificationDefinition.class, _filter,
							_properties);
				}
			}
		}

		return _userNotificationDefinitionInstances;
	}

	@Override
	public List<UserNotificationHandler> getUserNotificationHandlerInstances() {
		if (_userNotificationHandlerInstances == null) {
			synchronized (this) {
				if (_userNotificationHandlerInstances == null) {
					_userNotificationHandlerInstances =
						ServiceTrackerCollections.openList(
							UserNotificationHandler.class, _filter,
							_properties);
				}
			}
		}

		return _userNotificationHandlerInstances;
	}

	@Override
	public List<WebDAVStorage> getWebDAVStorageInstances() {
		if (_webDAVStorageInstances == null) {
			synchronized (this) {
				if (_webDAVStorageInstances == null) {
					_webDAVStorageInstances =
						ServiceTrackerCollections.openList(
							WebDAVStorage.class, _filter, _properties);
				}
			}
		}

		return _webDAVStorageInstances;
	}

	@Override
	public List<WorkflowHandler<?>> getWorkflowHandlerInstances() {
		if (_workflowHandlerInstances == null) {
			synchronized (this) {
				if (_workflowHandlerInstances == null) {
					_workflowHandlerInstances =
						ServiceTrackerCollections.openList(
							(Class<WorkflowHandler<?>>)
								(Class<?>)WorkflowHandler.class,
							_filter, _properties);
				}
			}
		}

		return _workflowHandlerInstances;
	}

	@Override
	public List<Method> getXmlRpcMethodInstances() {
		if (_xmlRpcMethodInstances == null) {
			synchronized (this) {
				if (_xmlRpcMethodInstances == null) {
					_xmlRpcMethodInstances = ServiceTrackerCollections.openList(
						Method.class, _filter, _properties);
				}
			}
		}

		return _xmlRpcMethodInstances;
	}

	@Override
	public void setPortletDataHandlerInstances(
		List<PortletDataHandler> portletDataHandlerInstances) {

		_portletDataHandlerInstances = portletDataHandlerInstances;
	}

	@Override
	public void setPortletInstance(Portlet portletInstance) {
		_portletInstance = portletInstance;
	}

	@Override
	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	private volatile List<AssetRendererFactory<?>>
		_assetRendererFactoryInstances;
	private volatile List<AtomCollectionAdapter<?>>
		_atomCollectionAdapterInstances;
	private volatile List<ConfigurationAction> _configurationActionInstances;
	private volatile List<ControlPanelEntry> _controlPanelEntryInstances;
	private volatile List<CustomAttributesDisplay>
		_customAttributesDisplayInstances;
	private final Filter _filter;
	private final FriendlyURLMapperTracker _friendlyURLMapperTracker;
	private volatile List<Indexer<?>> _indexerInstances;
	private volatile List<OpenSearch> _openSearchInstances;
	private volatile List<PermissionPropagator> _permissionPropagatorInstances;
	private volatile List<PollerProcessor> _pollerProcessorInstances;
	private volatile List<MessageListener> _popMessageListenerInstances;
	private volatile List<PortletDataHandler> _portletDataHandlerInstances;
	private Portlet _portletInstance;
	private volatile List<PortletLayoutListener>
		_portletLayoutListenerInstances;
	private String _portletName;
	private volatile List<PreferencesValidator> _preferencesValidatorInstances;
	private final Map<String, Object> _properties;
	private final String _resourceBundleBaseName;
	private volatile ResourceBundleLoader _resourceBundleLoader;
	private volatile List<SchedulerEventMessageListener>
		_schedulerEventMessageListeners;
	private final List<ServiceRegistration<?>> _serviceRegistrations;
	private final ServletContext _servletContext;
	private volatile List<SocialActivityInterpreter>
		_socialActivityInterpreterInstances;
	private volatile List<SocialRequestInterpreter>
		_socialRequestInterpreterInstances;
	private volatile List<StagedModelDataHandler<?>>
		_stagedModelDataHandlerInstances;
	private volatile List<TemplateHandler> _templateHandlerInstances;
	private volatile List<TrashHandler> _trashHandlerInstances;
	private volatile List<URLEncoder> _urlEncoderInstances;
	private volatile List<UserNotificationDefinition>
		_userNotificationDefinitionInstances;
	private volatile List<UserNotificationHandler>
		_userNotificationHandlerInstances;
	private volatile List<WebDAVStorage> _webDAVStorageInstances;
	private volatile List<WorkflowHandler<?>> _workflowHandlerInstances;
	private volatile List<Method> _xmlRpcMethodInstances;

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

			Registry registry = RegistryUtil.getRegistry();

			return registry.registerService(
				PermissionPropagator.class,
				registry.getService(serviceReference),
				serviceReference.getProperties());
		}

		@Override
		public void modifiedService(
			ServiceReference
				<com.liferay.portal.kernel.security.permission.
					PermissionPropagator> serviceReference,
			ServiceRegistration<PermissionPropagator> serviceRegistration) {

			serviceRegistration.setProperties(serviceReference.getProperties());
		}

		@Override
		public void removedService(
			ServiceReference
				<com.liferay.portal.kernel.security.permission.
					PermissionPropagator> serviceReference,
			ServiceRegistration<PermissionPropagator> serviceRegistration) {

			serviceRegistration.unregister();

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

	}

	static {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<?, ?> serviceTracker = registry.trackServices(
			com.liferay.portal.kernel.security.permission.PermissionPropagator.
				class,
			new PermissionPropagatorServiceTrackerCustomizer());

		serviceTracker.open();
	}

}