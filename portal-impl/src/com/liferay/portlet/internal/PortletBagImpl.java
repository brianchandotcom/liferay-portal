/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.internal;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletConfigurationListener;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
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

import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

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

		for (ServiceTrackerList<?> serviceTrackerList :
				_serviceTrackerListMap.values()) {

			serviceTrackerList.close();
		}
	}

	@Override
	public List<AssetRendererFactory<?>> getAssetRendererFactoryInstances() {
		return _getList(AssetRendererFactory.class);
	}

	@Override
	public List<ConfigurationAction> getConfigurationActionInstances() {
		return _getList(ConfigurationAction.class);
	}

	@Override
	public List<ControlPanelEntry> getControlPanelEntryInstances() {
		return _getList(ControlPanelEntry.class);
	}

	@Override
	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		return _getList(CustomAttributesDisplay.class);
	}

	@Override
	public FriendlyURLMapperTracker getFriendlyURLMapperTracker() {
		return _friendlyURLMapperTracker;
	}

	@Override
	public List<Indexer<?>> getIndexerInstances() {
		return _getList(Indexer.class);
	}

	@Override
	public List<OpenSearch> getOpenSearchInstances() {
		return _getList(OpenSearch.class);
	}

	@Override
	public List<PermissionPropagator> getPermissionPropagatorInstances() {
		return _getList(PermissionPropagator.class);
	}

	@Override
	public List<MessageListener> getPopMessageListenerInstances() {
		return _getList(MessageListener.class);
	}

	@Override
	public List<PortletConfigurationListener>
		getPortletConfigurationListenerInstances() {

		return _getList(PortletConfigurationListener.class);
	}

	@Override
	public List<PortletDataHandler> getPortletDataHandlerInstances() {
		return _getList(PortletDataHandler.class);
	}

	@Override
	public Portlet getPortletInstance() {
		return _portletInstance;
	}

	@Override
	public List<PortletLayoutListener> getPortletLayoutListenerInstances() {
		return _getList(PortletLayoutListener.class);
	}

	@Override
	public String getPortletName() {
		return _portletName;
	}

	@Override
	public List<PreferencesValidator> getPreferencesValidatorInstances() {
		return _getList(PreferencesValidator.class);
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader =
			_resourceBundleLoaderDCLSingleton.getSingleton(
				() -> ServiceProxyFactory.newServiceTrackedInstance(
					ResourceBundleLoader.class, DCLSingleton.class,
					_resourceBundleLoaderDCLSingleton, "_singleton",
					StringBundler.concat(
						"(resource.bundle.base.name=",
						getResourceBundleBaseName(), ")(servlet.context.name=",
						_servletContext.getServletContextName(), ")"),
					false));

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
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<SocialActivityInterpreter>
		getSocialActivityInterpreterInstances() {

		return _getList(SocialActivityInterpreter.class);
	}

	@Override
	public List<SocialRequestInterpreter>
		getSocialRequestInterpreterInstances() {

		return _getList(SocialRequestInterpreter.class);
	}

	@Override
	public List<StagedModelDataHandler<?>>
		getStagedModelDataHandlerInstances() {

		return _getList(StagedModelDataHandler.class);
	}

	@Override
	public List<TemplateHandler> getTemplateHandlerInstances() {
		return _getList(TemplateHandler.class);
	}

	@Override
	public List<TrashHandler> getTrashHandlerInstances() {
		return _getList(TrashHandler.class);
	}

	@Override
	public List<URLEncoder> getURLEncoderInstances() {
		return _getList(URLEncoder.class);
	}

	@Override
	public List<UserNotificationDefinition>
		getUserNotificationDefinitionInstances() {

		return _getList(UserNotificationDefinition.class);
	}

	@Override
	public List<UserNotificationHandler> getUserNotificationHandlerInstances() {
		return _getList(UserNotificationHandler.class);
	}

	@Override
	public List<WebDAVStorage> getWebDAVStorageInstances() {
		return _getList(WebDAVStorage.class);
	}

	@Override
	public List<WorkflowHandler<?>> getWorkflowHandlerInstances() {
		return _getList(WorkflowHandler.class);
	}

	@Override
	public List<Method> getXmlRpcMethodInstances() {
		return _getList(Method.class);
	}

	@Override
	public void setPortletInstance(Portlet portletInstance) {
		_portletInstance = portletInstance;
	}

	@Override
	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	private final <T> List<T> _getList(Class<?> clazz) {
		ServiceTrackerList<Class<?>> serviceTrackerList =
			_serviceTrackerListMap.computeIfAbsent(
				clazz,
				key ->
					(ServiceTrackerList<Class<?>>)
						(ServiceTrackerList)ServiceTrackerListFactory.open(
							_bundleContext, clazz, _filterString));

		return (List<T>)serviceTrackerList.toList();
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private final String _filterString;
	private final FriendlyURLMapperTracker _friendlyURLMapperTracker;
	private Portlet _portletInstance;
	private String _portletName;
	private final String _resourceBundleBaseName;
	private final DCLSingleton<ResourceBundleLoader>
		_resourceBundleLoaderDCLSingleton = new DCLSingleton<>();
	private final List<ServiceRegistration<?>> _serviceRegistrations;
	private final Map<Class<?>, ServiceTrackerList<Class<?>>>
		_serviceTrackerListMap = new ConcurrentHashMap<>();
	private final ServletContext _servletContext;

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