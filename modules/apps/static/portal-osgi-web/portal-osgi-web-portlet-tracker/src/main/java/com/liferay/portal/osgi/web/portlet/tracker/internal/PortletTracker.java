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

package com.liferay.portal.osgi.web.portlet.tracker.internal;

import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.asm.ASMWrapperUtil;
import com.liferay.portal.kernel.application.type.ApplicationType;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.ResourceActionsException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.EventDefinition;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletInfo;
import com.liferay.portal.kernel.model.PortletURLListener;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.portlet.PortletDependencyFactory;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletInstanceFactory;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.model.impl.EventDefinitionImpl;
import com.liferay.portal.model.impl.PortletURLListenerImpl;
import com.liferay.portal.model.impl.PublicRenderParameterImpl;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperFactory;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;
import com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl.IndividualPortletResourcePermissionProvider;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portlet.PortletBagFactory;
import com.liferay.portlet.PortletContextBag;
import com.liferay.portlet.PortletContextBagPool;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.portlet.Portlet;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.runtime.HttpServiceRuntime;
import org.osgi.service.http.runtime.HttpServiceRuntimeConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
@Component(immediate = true, service = PortletTracker.class)
public class PortletTracker
	implements ServiceTrackerCustomizer
		<Portlet, com.liferay.portal.kernel.model.Portlet> {

	@Override
	public com.liferay.portal.kernel.model.Portlet addingService(
		ServiceReference<Portlet> serviceReference) {

		Portlet portlet = _bundleContext.getService(serviceReference);

		if (portlet == null) {
			return null;
		}

		String portletName = (String)serviceReference.getProperty(
			"javax.portlet.name");

		if (Validator.isNull(portletName)) {
			Class<?> clazz = portlet.getClass();

			portletName = clazz.getName();
		}

		String portletId = StringUtil.replace(
			portletName, new char[] {'.', '$'}, new char[] {'_', '_'});

		portletId = _portal.getJsSafePortletId(portletId);

		if (portletId.length() >
				PortletIdCodec.PORTLET_INSTANCE_KEY_MAX_LENGTH) {

			_log.error(
				StringBundler.concat(
					"Portlet ID ", portletId, " has more than ",
					PortletIdCodec.PORTLET_INSTANCE_KEY_MAX_LENGTH,
					" characters"));

			_bundleContext.ungetService(serviceReference);

			return null;
		}

		com.liferay.portal.kernel.model.Portlet portletModel =
			_portletLocalService.getPortletById(portletId);

		if (portletModel != null) {
			_log.error("Portlet id " + portletId + " is already in use");

			_bundleContext.ungetService(serviceReference);

			return null;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Adding " + serviceReference);
		}

		portletModel = addingPortlet(
			serviceReference, portlet, portletName, portletId);

		if (portletModel == null) {
			_bundleContext.ungetService(serviceReference);
		}

		return portletModel;
	}

	@Override
	public void modifiedService(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		removedService(serviceReference, portletModel);

		com.liferay.portal.kernel.model.Portlet newPortletModel = addingService(
			serviceReference);

		if (newPortletModel == null) {
			return;
		}

		BeanPropertiesUtil.copyProperties(newPortletModel, portletModel);
	}

	@Override
	public void removedService(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		portletModel.unsetReady();

		ServiceRegistrations serviceRegistrations = _serviceRegistrations.get(
			(Long)serviceReference.getProperty(Constants.SERVICE_BUNDLEID));

		if (serviceRegistrations == null) {
			return;
		}

		PortletApp portletApp = serviceRegistrations.getPortletApp();

		portletApp.removePortlet(portletModel);

		try {
			_bundleContext.ungetService(serviceReference);
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException, illegalStateException);
			}

			// We still need to remove the service so we can ignore this and
			// keep going

		}

		_portletInstanceFactory.destroy(portletModel);

		Long companyId = (Long)serviceReference.getProperty(
			"com.liferay.portlet.company");

		if (companyId == null) {
			_companyLocalService.forEachCompanyId(
				curCompanyId -> {
					PortletCategory portletCategory =
						(PortletCategory)WebAppPool.get(
							curCompanyId, WebKeys.PORTLET_CATEGORY);

					if (portletCategory == null) {
						_log.error(
							"Unable to get portlet category for " +
								curCompanyId);
					}
					else {
						portletCategory.separate(
							portletModel.getRootPortletId());
					}
				});
		}
		else {
			PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
				companyId, WebKeys.PORTLET_CATEGORY);

			if (portletCategory == null) {
				_log.error("Unable to get portlet category for " + companyId);
			}
			else {
				portletCategory.separate(portletModel.getRootPortletId());
			}
		}

		serviceRegistrations.removeServiceReference(serviceReference);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_executorService = _portalExecutorManager.getPortalExecutor(
			PortletTracker.class.getName());

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, Portlet.class, this);

		FutureTask<Void> futureTask = new FutureTask<>(
			() -> {
				_portalPortletModel = _portletLocalService.getPortletById(
					CompanyConstants.SYSTEM, PortletKeys.PORTAL);

				ServiceRegistration<IndividualPortletResourcePermissionProvider>
					serviceRegistration = bundleContext.registerService(
						IndividualPortletResourcePermissionProvider.class,
						new StartupIndividualPortletResourcePermissionProvider(
							_resourcePermissionLocalService),
						null);

				DependencyManagerSyncUtil.registerSyncCallable(
					() -> {
						serviceRegistration.unregister();

						return null;
					});

				_serviceTracker.open();

				return null;
			});

		Thread serviceTrackerOpenerThread = new Thread(
			futureTask,
			PortletTracker.class.getName() + "-ServiceTrackerOpener");

		serviceTrackerOpenerThread.setDaemon(true);

		serviceTrackerOpenerThread.start();

		DependencyManagerSyncUtil.registerSyncFuture(futureTask);

		if (_log.isInfoEnabled()) {
			_log.info("Activated");
		}
	}

	protected com.liferay.portal.kernel.model.Portlet addingPortlet(
		ServiceReference<Portlet> serviceReference, Portlet portlet,
		String portletName, String portletId) {

		_warnPorletProperties(portletName, serviceReference);

		Bundle bundle = serviceReference.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader bundleClassLoader = bundleWiring.getClassLoader();

		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		thread.setContextClassLoader(bundleWiring.getClassLoader());

		ServiceRegistrations serviceRegistrations = getServiceRegistrations(
			bundle);

		try {
			PortletApp portletApp = createBundlePortletApp(
				bundle, bundleClassLoader, serviceRegistrations);

			portletApp.setDefaultNamespace(
				(String)serviceReference.getProperty(
					"javax.portlet.default-namespace"));

			String jxPortletVersion = (String)serviceReference.getProperty(
				"javax.portlet.version");

			if (jxPortletVersion == null) {
				portletApp.setSpecMajorVersion(2);
				portletApp.setSpecMinorVersion(0);
			}
			else {
				String[] jxPortletVersionParts = StringUtil.split(
					jxPortletVersion, CharPool.PERIOD);

				if (jxPortletVersionParts.length > 0) {
					portletApp.setSpecMajorVersion(
						GetterUtil.getInteger(jxPortletVersionParts[0], 2));

					if (jxPortletVersionParts.length > 1) {
						portletApp.setSpecMinorVersion(
							GetterUtil.getInteger(jxPortletVersionParts[1]));
					}
				}
			}

			com.liferay.portal.kernel.model.Portlet portletModel =
				buildPortletModel(
					portletApp, portletId, bundle,
					(Long)serviceReference.getProperty(
						"com.liferay.portlet.company"));

			portletModel.setPortletName(portletName);

			String displayName = GetterUtil.getString(
				serviceReference.getProperty("javax.portlet.display-name"),
				portletName);

			portletModel.setDisplayName(displayName);

			Class<?> portletClazz = portlet.getClass();

			portletModel.setPortletClass(portletClazz.getName());

			collectJxPortletFeatures(serviceReference, portletModel);
			collectLiferayFeatures(serviceReference, portletModel);

			PortletContextBag portletContextBag = new PortletContextBag(
				portletApp.getServletContextName());

			PortletContextBagPool.put(
				portletApp.getServletContextName(), portletContextBag);

			PortletBagFactory portletBagFactory = new PortletBagFactory();

			portletBagFactory.setClassLoader(bundleClassLoader);
			portletBagFactory.setServletContext(portletApp.getServletContext());
			portletBagFactory.setWARFile(true);

			portletBagFactory.create(portletModel, portlet, true);

			_resourceActions.populatePortletResource(
				portletModel, bundleClassLoader,
				serviceRegistrations.getSources());

			deployPortlet(serviceReference, portletModel);

			portletModel.setReady(true);

			if (_log.isInfoEnabled()) {
				_log.info("Added " + serviceReference);
			}

			serviceRegistrations.addServiceReference(serviceReference);

			return portletModel;
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Portlet ", portletId, " from ", bundle,
					" failed to initialize"),
				exception);

			return null;
		}
		finally {
			thread.setContextClassLoader(contextClassLoader);
		}
	}

	protected com.liferay.portal.kernel.model.Portlet buildPortletModel(
		PortletApp portletApp, String portletId, Bundle bundle,
		Long companyId) {

		com.liferay.portal.kernel.model.Portlet portletModel =
			_portletLocalService.createPortlet(0);

		portletModel.setPortletId(portletId);

		if (companyId == null) {
			portletModel.setCompanyId(CompanyConstants.SYSTEM);
		}
		else {
			portletModel.setCompanyId(companyId);
		}

		portletModel.setPluginPackage(
			new BundlePluginPackage(bundle, portletApp));
		portletModel.setPortletApp(portletApp);
		portletModel.setRoleMappers(_portalPortletModel.getRoleMappers());
		portletModel.setStrutsPath(portletId);

		return portletModel;
	}

	protected void collectApplicationTypes(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		Set<ApplicationType> applicationTypes = new HashSet<>();

		List<String> applicationTypeValues = StringPlus.asList(
			get(serviceReference, "application-type"));

		for (String applicationTypeValue : applicationTypeValues) {
			try {
				ApplicationType applicationType = ApplicationType.parse(
					applicationTypeValue);

				applicationTypes.add(applicationType);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_log.error(
					"Application type " + applicationTypeValue,
					illegalArgumentException);
			}
		}

		if (applicationTypes.isEmpty()) {
			applicationTypes.add(ApplicationType.WIDGET);
		}

		portletModel.setApplicationTypes(applicationTypes);
	}

	protected void collectAsyncSupported(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		boolean asyncSupported = GetterUtil.getBoolean(
			serviceReference.getProperty("javax.portlet.async-supported"));

		portletModel.setAsyncSupported(asyncSupported);
	}

	protected void collectCacheScope(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {
	}

	protected void collectContainerRuntimeOptions(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		PortletApp portletApp = portletModel.getPortletApp();

		Map<String, String[]> containerRuntimeOptionsMap =
			portletApp.getContainerRuntimeOptions();

		for (String servicePropertyKey : serviceReference.getPropertyKeys()) {
			if (!servicePropertyKey.startsWith(
					"javax.portlet.container-runtime-option.")) {

				continue;
			}

			String name = servicePropertyKey.substring(
				"javax.portlet.container-runtime-option.".length());

			String portletName = portletModel.getPortletName();

			int index = portletName.indexOf(PortletConstants.WAR_SEPARATOR);

			if (index != -1) {
				portletName = portletName.substring(0, index);
			}

			String containerRuntimeOptionPrefix =
				LiferayPortletConfig.class.getName();

			containerRuntimeOptionPrefix = containerRuntimeOptionPrefix.concat(
				portletName);

			List<String> values = StringPlus.asList(
				serviceReference.getProperty(servicePropertyKey));

			containerRuntimeOptionsMap.put(
				containerRuntimeOptionPrefix.concat(name),
				values.toArray(new String[0]));
		}
	}

	protected void collectEventDefinitions(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> definitions = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.event-definition"));

		for (String definition : definitions) {
			EventDefinition eventDefinition = null;

			String[] definitionParts = StringUtil.split(definition);

			for (int i = 0; i < definitionParts.length; i++) {
				String event = definitionParts[i];

				String name = event;

				String uri = null;

				String[] parts = StringUtil.split(event, CharPool.SEMICOLON);

				if (parts.length >= 2) {
					name = parts[0];
					uri = parts[1];
				}

				QName qName = getQName(
					name, uri, portletApp.getDefaultNamespace());

				if (i == 0) {
					String valueType = null;

					if (parts.length == 3) {
						valueType = parts[2];
					}

					eventDefinition = new EventDefinitionImpl(
						qName, valueType, portletApp);
				}
				else {
					eventDefinition.addAliasQName(qName);
				}
			}

			if (eventDefinition != null) {
				portletApp.addEventDefinition(eventDefinition);
			}
		}
	}

	protected void collectExpirationCache(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		int expirationCache = GetterUtil.getInteger(
			serviceReference.getProperty("javax.portlet.expiration-cache"));

		portletModel.setExpCache(expirationCache);
	}

	protected void collectInitParams(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		Map<String, String> initParams = new HashMap<>();

		for (String initParamKey : serviceReference.getPropertyKeys()) {
			if (!initParamKey.startsWith("javax.portlet.init-param.")) {
				continue;
			}

			initParams.put(
				initParamKey.substring("javax.portlet.init-param.".length()),
				GetterUtil.getString(
					serviceReference.getProperty(initParamKey)));
		}

		initParams.put(
			InvokerPortlet.INIT_INVOKER_PORTLET_NAME, "portlet-servlet");

		portletModel.setInitParams(initParams);
	}

	protected void collectJxPortletFeatures(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		collectApplicationTypes(serviceReference, portletModel);
		collectAsyncSupported(serviceReference, portletModel);
		collectCacheScope(serviceReference, portletModel);
		collectContainerRuntimeOptions(serviceReference, portletModel);
		collectEventDefinitions(serviceReference, portletModel);
		collectExpirationCache(serviceReference, portletModel);
		collectInitParams(serviceReference, portletModel);
		collectListeners(serviceReference, portletModel);
		collectMultipartConfig(serviceReference, portletModel);
		collectPortletDependencies(serviceReference, portletModel);
		collectPortletInfo(serviceReference, portletModel);
		collectPortletModes(serviceReference, portletModel);
		collectPortletPreferences(serviceReference, portletModel);
		collectResourceBundle(serviceReference, portletModel);
		collectSecurityRoleRefs(serviceReference, portletModel);
		collectSupportedLocales(serviceReference, portletModel);
		collectSupportedProcessingEvents(serviceReference, portletModel);
		collectSupportedPublicRenderParameters(serviceReference, portletModel);
		collectSupportedPublishingEvents(serviceReference, portletModel);
		collectWindowStates(serviceReference, portletModel);
	}

	protected void collectLiferayFeatures(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		portletModel.setActionTimeout(
			GetterUtil.getInteger(
				get(serviceReference, "action-timeout"),
				portletModel.getActionTimeout()));
		portletModel.setActionURLRedirect(
			GetterUtil.getBoolean(
				get(serviceReference, "action-url-redirect"),
				portletModel.getActionURLRedirect()));
		portletModel.setActive(
			GetterUtil.getBoolean(
				get(serviceReference, "active"), portletModel.isActive()));
		portletModel.setAddDefaultResource(
			GetterUtil.getBoolean(
				get(serviceReference, "add-default-resource"),
				portletModel.isAddDefaultResource()));
		portletModel.setAjaxable(
			GetterUtil.getBoolean(
				get(serviceReference, "ajaxable"), portletModel.isAjaxable()));

		Set<String> autopropagatedParameters = SetUtil.fromCollection(
			StringPlus.asList(
				get(serviceReference, "autopropagated-parameters")));

		portletModel.setAutopropagatedParameters(autopropagatedParameters);

		portletModel.setControlPanelEntryWeight(
			GetterUtil.getDouble(
				get(serviceReference, "control-panel-entry-weight"),
				portletModel.getControlPanelEntryWeight()));
		portletModel.setCssClassWrapper(
			GetterUtil.getString(
				get(serviceReference, "css-class-wrapper"),
				portletModel.getCssClassWrapper()));
		portletModel.setFooterPortalCss(
			StringPlus.asList(get(serviceReference, "footer-portal-css")));
		portletModel.setFooterPortalJavaScript(
			StringPlus.asList(
				get(serviceReference, "footer-portal-javascript")));
		portletModel.setFooterPortletCss(
			StringPlus.asList(get(serviceReference, "footer-portlet-css")));
		portletModel.setFooterPortletJavaScript(
			StringPlus.asList(
				get(serviceReference, "footer-portlet-javascript")));
		portletModel.setFriendlyURLMapping(
			GetterUtil.getString(
				get(serviceReference, "friendly-url-mapping"),
				portletModel.getFriendlyURLMapping()));
		portletModel.setFriendlyURLRoutes(
			GetterUtil.getString(
				get(serviceReference, "friendly-url-routes"),
				portletModel.getFriendlyURLRoutes()));
		portletModel.setHeaderPortalCss(
			StringPlus.asList(get(serviceReference, "header-portal-css")));
		portletModel.setHeaderPortalJavaScript(
			StringPlus.asList(
				get(serviceReference, "header-portal-javascript")));
		portletModel.setHeaderPortletCss(
			StringPlus.asList(get(serviceReference, "header-portlet-css")));
		portletModel.setHeaderPortletJavaScript(
			StringPlus.asList(
				get(serviceReference, "header-portlet-javascript")));
		portletModel.setHeaderRequestAttributePrefixes(
			StringPlus.asList(
				get(serviceReference, "header-request-attribute-prefix")));
		portletModel.setHeaderTimeout(
			GetterUtil.getInteger(
				get(serviceReference, "header-timeout"),
				portletModel.getHeaderTimeout()));
		portletModel.setIcon(
			GetterUtil.getString(
				get(serviceReference, "icon"), portletModel.getIcon()));
		portletModel.setInclude(
			GetterUtil.getBoolean(
				get(serviceReference, "include"), portletModel.isInclude()));
		portletModel.setInstanceable(
			GetterUtil.getBoolean(
				get(serviceReference, "instanceable"),
				portletModel.isInstanceable()));
		portletModel.setLayoutCacheable(
			GetterUtil.getBoolean(
				get(serviceReference, "layout-cacheable"),
				portletModel.isLayoutCacheable()));
		portletModel.setMaximizeEdit(
			GetterUtil.getBoolean(
				get(serviceReference, "maximize-edit"),
				portletModel.isMaximizeEdit()));
		portletModel.setMaximizeHelp(
			GetterUtil.getBoolean(
				get(serviceReference, "maximize-help"),
				portletModel.isMaximizeHelp()));
		portletModel.setParentStrutsPath(
			GetterUtil.getString(
				get(serviceReference, "parent-struts-path"),
				portletModel.getParentStrutsPath()));
		portletModel.setPartialActionServeResource(
			GetterUtil.getBoolean(
				get(serviceReference, "partial-action-serve-resource"),
				portletModel.isPartialActionServeResource()));
		portletModel.setPopUpPrint(
			GetterUtil.getBoolean(
				get(serviceReference, "pop-up-print"),
				portletModel.isPopUpPrint()));
		portletModel.setPortletDependencyCssEnabled(
			GetterUtil.getBoolean(
				get(serviceReference, "portlet-dependency-css-enabled"),
				portletModel.isPortletDependencyCssEnabled()));
		portletModel.setPortletDependencyJavaScriptEnabled(
			GetterUtil.getBoolean(
				get(serviceReference, "portlet-dependency-javascript-enabled"),
				portletModel.isPortletDependencyJavaScriptEnabled()));
		portletModel.setPreferencesCompanyWide(
			GetterUtil.getBoolean(
				get(serviceReference, "preferences-company-wide"),
				portletModel.isPreferencesCompanyWide()));
		portletModel.setPreferencesOwnedByGroup(
			GetterUtil.getBoolean(
				get(serviceReference, "preferences-owned-by-group"),
				portletModel.isPreferencesOwnedByGroup()));
		portletModel.setPreferencesUniquePerLayout(
			GetterUtil.getBoolean(
				get(serviceReference, "preferences-unique-per-layout"),
				portletModel.isPreferencesUniquePerLayout()));
		portletModel.setPrivateRequestAttributes(
			GetterUtil.getBoolean(
				get(serviceReference, "private-request-attributes"),
				portletModel.isPrivateRequestAttributes()));
		portletModel.setPrivateSessionAttributes(
			GetterUtil.getBoolean(
				get(serviceReference, "private-session-attributes"),
				portletModel.isPrivateSessionAttributes()));
		portletModel.setRenderTimeout(
			GetterUtil.getInteger(
				get(serviceReference, "render-timeout"),
				portletModel.getRenderTimeout()));
		portletModel.setRenderWeight(
			GetterUtil.getInteger(
				get(serviceReference, "render-weight"),
				portletModel.getRenderWeight()));

		if (!portletModel.isAjaxable() &&
			(portletModel.getRenderWeight() < 1)) {

			portletModel.setRenderWeight(1);
		}

		boolean defaultRequiresNamespacedParameters = GetterUtil.getBoolean(
			get(serviceReference, "requires-namespaced-parameters"),
			portletModel.isRequiresNamespacedParameters());

		portletModel.setRequiresNamespacedParameters(
			GetterUtil.getBoolean(
				serviceReference.getProperty("requires-namespaced-parameters"),
				defaultRequiresNamespacedParameters));

		portletModel.setRestoreCurrentView(
			GetterUtil.getBoolean(
				get(serviceReference, "restore-current-view"),
				portletModel.isRestoreCurrentView()));
		portletModel.setScopeable(
			GetterUtil.getBoolean(
				get(serviceReference, "scopeable"),
				portletModel.isScopeable()));
		portletModel.setShowPortletAccessDenied(
			GetterUtil.getBoolean(
				get(serviceReference, "show-portlet-access-denied"),
				portletModel.isShowPortletAccessDenied()));
		portletModel.setShowPortletInactive(
			GetterUtil.getBoolean(
				get(serviceReference, "show-portlet-inactive"),
				portletModel.isShowPortletInactive()));
		portletModel.setSinglePageApplication(
			GetterUtil.getBoolean(
				get(serviceReference, "single-page-application"),
				portletModel.isSinglePageApplication()));
		portletModel.setStrutsPath(
			GetterUtil.getString(
				get(serviceReference, "struts-path"),
				portletModel.getStrutsPath()));
		portletModel.setSystem(
			GetterUtil.getBoolean(
				get(serviceReference, "system"), portletModel.isSystem()));
		portletModel.setUseDefaultTemplate(
			GetterUtil.getBoolean(
				get(serviceReference, "use-default-template"),
				portletModel.isUseDefaultTemplate()));
		portletModel.setUserPrincipalStrategy(
			GetterUtil.getString(
				get(serviceReference, "user-principal-strategy"),
				portletModel.getUserPrincipalStrategy()));
		portletModel.setVirtualPath(
			GetterUtil.getString(
				get(serviceReference, "virtual-path"),
				portletModel.getVirtualPath()));
	}

	protected void collectListeners(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> listenerClassNames = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.listener"));

		List<PortletURLListener> portletURLListeners = new ArrayList<>();

		for (String listenerClassName : listenerClassNames) {
			int ordinal = 0;

			String[] parts = StringUtil.split(
				listenerClassName, CharPool.SEMICOLON);

			if (parts.length == 2) {
				listenerClassName = parts[0];
				ordinal = GetterUtil.getInteger(parts[1]);
			}

			portletURLListeners.add(
				new PortletURLListenerImpl(
					listenerClassName, ordinal, portletApp));
		}

		Collections.sort(
			portletURLListeners,
			Comparator.comparingInt(PortletURLListener::getOrdinal));

		for (PortletURLListener portletURLListener : portletURLListeners) {
			portletApp.addPortletURLListener(portletURLListener);
		}
	}

	protected void collectMultipartConfig(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		portletModel.setMultipartFileSizeThreshold(
			GetterUtil.getInteger(
				serviceReference.getProperty(
					"javax.portlet.multipart.file-size-threshold")));
		portletModel.setMultipartLocation(
			GetterUtil.getString(
				serviceReference.getProperty(
					"javax.portlet.multipart.location"),
				portletModel.getMultipartLocation()));
		portletModel.setMultipartMaxFileSize(
			GetterUtil.getLong(
				serviceReference.getProperty(
					"javax.portlet.multipart.max-file-size"),
				-1L));
		portletModel.setMultipartMaxRequestSize(
			GetterUtil.getLong(
				serviceReference.getProperty(
					"javax.portlet.multipart.max-request-size"),
				-1L));
	}

	protected void collectPortletDependencies(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		List<String> dependencies = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.dependency"));

		for (String dependency : dependencies) {
			String[] parts = StringUtil.split(dependency, CharPool.SEMICOLON);

			portletModel.addPortletDependency(
				_portletDependencyFactory.createPortletDependency(
					parts[0], parts[1], parts[2]));
		}
	}

	protected void collectPortletInfo(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		String portletInfoTitle = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.info.title"));

		String portletDisplayName = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.display-name"),
			portletInfoTitle);

		String portletInfoShortTitle = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.info.short-title"));
		String portletInfoKeyWords = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.info.keywords"));
		String portletDescription = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.description"));

		PortletInfo portletInfo = new PortletInfo(
			portletDisplayName, portletInfoShortTitle, portletInfoKeyWords,
			portletDescription);

		portletModel.setPortletInfo(portletInfo);
	}

	protected void collectPortletModes(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		Map<String, Set<String>> portletModes = null;

		List<String> portletModesStrings = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.portlet-mode"));

		for (String portletModesString : portletModesStrings) {
			String[] portletModesStringParts = StringUtil.split(
				portletModesString, CharPool.SEMICOLON);

			if (portletModesStringParts.length != 2) {
				continue;
			}

			String mimeType = portletModesStringParts[0];

			Set<String> mimeTypePortletModes = new HashSet<>();

			mimeTypePortletModes.add(toLowerCase(PortletMode.VIEW));
			mimeTypePortletModes.addAll(
				toLowerCaseSet(portletModesStringParts[1]));

			if (portletModes == null) {
				portletModes = new HashMap<>(_defaultPortletModes);
			}

			portletModes.put(mimeType, mimeTypePortletModes);
		}

		if (portletModes == null) {
			portletModes = _defaultPortletModes;
		}

		portletModel.setPortletModes(portletModes);
	}

	protected void collectPortletPreferences(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		String defaultPreferences = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.preferences"));

		if ((defaultPreferences != null) &&
			defaultPreferences.startsWith("classpath:")) {

			Bundle bundle = serviceReference.getBundle();

			URL url = bundle.getResource(
				defaultPreferences.substring("classpath:".length()));

			if (url != null) {
				try {
					defaultPreferences = StringUtil.read(url.openStream());
				}
				catch (IOException ioException) {
					_log.error(ioException, ioException);
				}
			}
		}

		portletModel.setDefaultPreferences(defaultPreferences);

		String preferencesValidator = GetterUtil.getString(
			serviceReference.getProperty(
				"javax.portlet.preferences-validator"));

		if (Validator.isNotNull(preferencesValidator)) {
			portletModel.setPreferencesValidator(preferencesValidator);
		}
	}

	protected void collectResourceBundle(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		String resourceBundle = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.resource-bundle"),
			portletModel.getResourceBundle());

		portletModel.setResourceBundle(resourceBundle);
	}

	protected void collectSecurityRoleRefs(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		Set<String> unlinkedRoles = new HashSet<>();

		List<String> roleRefs = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.security-role-ref"));

		if (roleRefs.isEmpty()) {
			roleRefs.add("administrator");
			roleRefs.add("guest");
			roleRefs.add("power-user");
			roleRefs.add("user");
		}

		for (String roleRef : roleRefs) {
			for (String curRoleRef : StringUtil.split(roleRef)) {
				unlinkedRoles.add(curRoleRef);
			}
		}

		portletModel.setUnlinkedRoles(unlinkedRoles);

		portletModel.linkRoles();
	}

	protected void collectSupportedLocales(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		Set<String> supportedLocales = new LinkedHashSet<>();

		supportedLocales.addAll(
			StringPlus.asList(
				serviceReference.getProperty(
					"javax.portlet.supported-locale")));

		portletModel.setSupportedLocales(supportedLocales);
	}

	protected void collectSupportedProcessingEvents(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		Set<QName> processingEvents = new HashSet<>();

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> supportedProcessingEvents = StringPlus.asList(
			serviceReference.getProperty(
				"javax.portlet.supported-processing-event"));

		for (String supportedProcessingEvent : supportedProcessingEvents) {
			String name = supportedProcessingEvent;
			String uri = null;

			String[] parts = StringUtil.split(
				supportedProcessingEvent, CharPool.SEMICOLON);

			if (parts.length == 2) {
				name = parts[0];
				uri = parts[1];
			}

			QName qName = getQName(name, uri, portletApp.getDefaultNamespace());

			processingEvents.add(qName);

			Set<EventDefinition> eventDefinitions =
				portletApp.getEventDefinitions();

			for (EventDefinition eventDefinition : eventDefinitions) {
				Set<QName> qNames = eventDefinition.getQNames();

				if (qNames.contains(qName)) {
					processingEvents.addAll(qNames);
				}
			}
		}

		portletModel.setProcessingEvents(processingEvents);
	}

	protected void collectSupportedPublicRenderParameters(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		Set<PublicRenderParameter> publicRenderParameters = new HashSet<>();

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> supportedPublicRenderParameters = StringPlus.asList(
			serviceReference.getProperty(
				"javax.portlet.supported-public-render-parameter"));

		for (String supportedPublicRenderParameter :
				supportedPublicRenderParameters) {

			String name = supportedPublicRenderParameter;
			String uri = null;

			String[] parts = StringUtil.split(
				supportedPublicRenderParameter, CharPool.SEMICOLON);

			if (parts.length == 2) {
				name = parts[0];
				uri = parts[1];
			}

			PublicRenderParameter publicRenderParameter =
				new PublicRenderParameterImpl(
					name, getQName(name, uri, portletApp.getDefaultNamespace()),
					portletApp);

			publicRenderParameters.add(publicRenderParameter);
		}

		portletModel.setPublicRenderParameters(publicRenderParameters);
	}

	protected void collectSupportedPublishingEvents(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		Set<QName> publishingEvents = new HashSet<>();

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> supportedPublishingEvents = StringPlus.asList(
			serviceReference.getProperty(
				"javax.portlet.supported-publishing-event"));

		for (String supportedPublishingEvent : supportedPublishingEvents) {
			String name = supportedPublishingEvent;
			String uri = null;

			String[] parts = StringUtil.split(
				supportedPublishingEvent, CharPool.SEMICOLON);

			if (parts.length == 2) {
				name = parts[0];
				uri = parts[1];
			}

			publishingEvents.add(
				getQName(name, uri, portletApp.getDefaultNamespace()));
		}

		portletModel.setPublishingEvents(publishingEvents);
	}

	protected void collectWindowStates(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.kernel.model.Portlet portletModel) {

		Map<String, Set<String>> windowStates = null;

		List<String> windowStatesStrings = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.window-state"));

		for (String windowStatesString : windowStatesStrings) {
			String[] windowStatesStringParts = StringUtil.split(
				windowStatesString, CharPool.SEMICOLON);

			if (windowStatesStringParts.length != 2) {
				continue;
			}

			String mimeType = windowStatesStringParts[0];

			Set<String> mimeTypeWindowStates = new HashSet<>();

			mimeTypeWindowStates.add(toLowerCase(WindowState.NORMAL));

			Set<String> windowStatesSet = toLowerCaseSet(
				windowStatesStringParts[1]);

			if (windowStatesSet.isEmpty()) {
				mimeTypeWindowStates.add(
					toLowerCase(LiferayWindowState.EXCLUSIVE));
				mimeTypeWindowStates.add(
					toLowerCase(LiferayWindowState.POP_UP));
				mimeTypeWindowStates.add(toLowerCase(WindowState.MAXIMIZED));
				mimeTypeWindowStates.add(toLowerCase(WindowState.MINIMIZED));
			}
			else {
				mimeTypeWindowStates.addAll(windowStatesSet);
			}

			if (windowStates == null) {
				windowStates = new HashMap<>(_defaultWindowStates);
			}

			windowStates.put(mimeType, mimeTypeWindowStates);
		}

		if (windowStates == null) {
			windowStates = _defaultWindowStates;
		}

		portletModel.setWindowStates(windowStates);
	}

	protected PortletApp createBundlePortletApp(
		Bundle bundle, ClassLoader classLoader,
		ServiceRegistrations serviceRegistrations) {

		PortletApp portletApp = serviceRegistrations.getPortletApp();

		if (portletApp != null) {
			return portletApp;
		}

		BundleContext bundleContext = bundle.getBundleContext();

		_servletContextHelperRegistrationServiceReference =
			bundleContext.getServiceReference(
				ServletContextHelperRegistration.class);

		ServletContextHelperRegistration servletContextHelperRegistration =
			bundleContext.getService(
				_servletContextHelperRegistrationServiceReference);

		BundlePortletAppDelegate bundlePortletAppDelegate =
			new BundlePortletAppDelegate(
				_portalPortletModel,
				servletContextHelperRegistration.getServletContext());

		PortletApp portletAppDefault = _portalPortletModel.getPortletApp();

		portletApp = ASMWrapperUtil.createASMWrapper(
			PortletTracker.class.getClassLoader(), PortletApp.class,
			bundlePortletAppDelegate, portletAppDefault);

		serviceRegistrations.setPortletApp(portletApp);

		serviceRegistrations.doConfiguration(classLoader);

		return portletApp;
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_executorService.shutdownNow();

		if (_log.isInfoEnabled()) {
			_log.info("Deactivated");
		}
	}

	protected void deployPortlet(
			ServiceReference<Portlet> serviceReference,
			com.liferay.portal.kernel.model.Portlet portletModel)
		throws PortalException {

		Long companyId = (Long)serviceReference.getProperty(
			"com.liferay.portlet.company");

		List<String> categoryNames = StringPlus.asList(
			get(serviceReference, "display-category"));

		if (categoryNames.isEmpty()) {
			categoryNames.add("category.undefined");
		}

		if (companyId != null) {
			_portletLocalService.deployRemotePortlet(
				new long[] {companyId}, portletModel,
				ArrayUtil.toStringArray(categoryNames), false, false);

			_portletLocalService.clearCache();

			return;
		}

		List<Future<Void>> futures = new ArrayList<>();

		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			futures.add(
				_executorService.submit(
					() -> {
						_portletLocalService.deployRemotePortlet(
							new long[] {company.getCompanyId()}, portletModel,
							ArrayUtil.toStringArray(categoryNames), false,
							false);

						return null;
					}));
		}

		for (Future<Void> future : futures) {
			try {
				future.get();
			}
			catch (Exception exception) {
				if (exception instanceof ExecutionException) {
					throw new PortalException(exception.getCause());
				}

				throw new PortalException(exception);
			}
		}

		_portletLocalService.clearCache();
	}

	protected Object get(
		ServiceReference<Portlet> serviceReference, String property) {

		return serviceReference.getProperty(_NAMESPACE + property);
	}

	protected QName getQName(String name, String uri, String defaultNamespace) {
		if (Validator.isNull(name) && Validator.isNull(uri)) {
			return null;
		}

		if (Validator.isNull(uri)) {
			return _saxReader.createQName(
				name, _saxReader.createNamespace(defaultNamespace));
		}

		return _saxReader.createQName(name, _saxReader.createNamespace(uri));
	}

	protected ServiceRegistrations getServiceRegistrations(Bundle bundle) {
		Long bundleId = bundle.getBundleId();

		ServiceRegistrations serviceRegistrations = _serviceRegistrations.get(
			bundleId);

		if (serviceRegistrations == null) {
			serviceRegistrations = new ServiceRegistrations(bundle);

			ServiceRegistrations oldServiceRegistrations =
				_serviceRegistrations.putIfAbsent(
					bundleId, serviceRegistrations);

			if (oldServiceRegistrations != null) {
				serviceRegistrations = oldServiceRegistrations;
			}
		}

		return serviceRegistrations;
	}

	@Reference(unbind = "-")
	protected void setHttpServiceRuntime(
		HttpServiceRuntime httpServiceRuntime, Map<String, Object> properties) {

		List<String> httpServiceEndpoints = StringPlus.asList(
			properties.get(HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT));

		if (!httpServiceEndpoints.isEmpty()) {
			_httpServiceEndpoint = httpServiceEndpoints.get(0);
		}

		if ((_httpServiceEndpoint.length() > 0) &&
			_httpServiceEndpoint.endsWith("/")) {

			_httpServiceEndpoint = _httpServiceEndpoint.substring(
				0, _httpServiceEndpoint.length() - 1);
		}
	}

	protected String toLowerCase(Object object) {
		String string = String.valueOf(object);

		return StringUtil.toLowerCase(string.trim());
	}

	protected Set<String> toLowerCaseSet(String string) {
		String[] array = StringUtil.split(string);

		for (int i = 0; i < array.length; i++) {
			array[i] = toLowerCase(array[i]);
		}

		return SetUtil.fromArray(array);
	}

	private void _warnPorletProperties(
		String portletName, ServiceReference<Portlet> serviceReference) {

		if (!_log.isWarnEnabled()) {
			return;
		}

		List<String> invalidKeys = _portletPropertyValidator.validate(
			serviceReference.getPropertyKeys());

		for (String invalidKey : invalidKeys) {
			_log.warn(
				StringBundler.concat(
					"Invalid property ", invalidKey, " for portlet ",
					portletName));
		}
	}

	private static final String _NAMESPACE = "com.liferay.portlet.";

	private static final Log _log = LogFactoryUtil.getLog(PortletTracker.class);

	private static final Map<String, Set<String>> _defaultPortletModes =
		Collections.singletonMap(
			ContentTypes.TEXT_HTML,
			Collections.singleton(String.valueOf(PortletMode.VIEW)));
	private static final Map<String, Set<String>> _defaultWindowStates =
		Collections.singletonMap(
			ContentTypes.TEXT_HTML,
			SetUtil.fromArray(
				new String[] {
					String.valueOf(LiferayWindowState.EXCLUSIVE),
					String.valueOf(LiferayWindowState.POP_UP),
					String.valueOf(WindowState.MAXIMIZED),
					String.valueOf(WindowState.MINIMIZED),
					String.valueOf(WindowState.NORMAL)
				}));

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	private ExecutorService _executorService;
	private String _httpServiceEndpoint = StringPool.BLANK;

	@Reference(
		target = ModuleServiceLifecycle.PORTLETS_INITIALIZED, unbind = "-"
	)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	private com.liferay.portal.kernel.model.Portlet _portalPortletModel;

	@Reference
	private PortletDependencyFactory _portletDependencyFactory;

	@Reference
	private PortletInstanceFactory _portletInstanceFactory;

	@Reference
	private PortletLocalService _portletLocalService;

	private final PortletPropertyValidator _portletPropertyValidator =
		new PortletPropertyValidator();

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private SAXReader _saxReader;

	private final ConcurrentMap<Long, ServiceRegistrations>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private ServiceTracker<Portlet, com.liferay.portal.kernel.model.Portlet>
		_serviceTracker;

	@Reference
	private ServletContextHelperFactory _servletContextHelperFactory;

	private ServiceReference<ServletContextHelperRegistration>
		_servletContextHelperRegistrationServiceReference;

	private static class StartupIndividualPortletResourcePermissionProvider
		implements IndividualPortletResourcePermissionProvider {

		@Override
		public List<ResourcePermission> getResourcePermissions(
			long companyId, String name) {

			Map<String, List<ResourcePermission>> resourcePermissions =
				_resourcePermissionMaps.computeIfAbsent(
					companyId,
					_resourcePermissionLocalService::
						getIndividualPortletResourcePermissions);

			return resourcePermissions.get(name);
		}

		@Override
		public void removeResourcePermissions(long companyId, String name) {
			Map<String, List<ResourcePermission>> resourcePermissions =
				_resourcePermissionMaps.get(companyId);

			if (resourcePermissions != null) {
				resourcePermissions.remove(name);
			}
		}

		private StartupIndividualPortletResourcePermissionProvider(
			ResourcePermissionLocalService resourcePermissionLocalService) {

			_resourcePermissionLocalService = resourcePermissionLocalService;
		}

		private final ResourcePermissionLocalService
			_resourcePermissionLocalService;
		private final Map<Long, Map<String, List<ResourcePermission>>>
			_resourcePermissionMaps = new ConcurrentHashMap<>();

	}

	private class ServiceRegistrations {

		public synchronized void addServiceReference(
			ServiceReference<Portlet> serviceReference) {

			_serviceReferences.add(serviceReference);
		}

		public synchronized String[] getSources() {
			return _sources;
		}

		public synchronized void removeServiceReference(
			ServiceReference<Portlet> serviceReference) {

			_serviceReferences.remove(serviceReference);

			if (!_serviceReferences.isEmpty()) {
				return;
			}

			_serviceReferences.clear();

			_portletApp = null;

			_serviceRegistrations.remove(_bundle.getBundleId());

			BundleContext bundleContext = _bundle.getBundleContext();

			try {
				bundleContext.ungetService(
					_servletContextHelperRegistrationServiceReference);
			}
			catch (IllegalStateException illegalStateException) {
			}
		}

		public synchronized void setPortletApp(PortletApp portletApp) {
			_portletApp = portletApp;
		}

		protected synchronized void doConfiguration(ClassLoader classLoader) {
			if (classLoader.getResource("portlet.properties") != null) {
				Configuration configuration =
					ConfigurationFactoryUtil.getConfiguration(
						classLoader, "portlet");

				Properties properties = configuration.getProperties();

				_sources = StringUtil.split(
					properties.getProperty(PropsKeys.RESOURCE_ACTIONS_CONFIGS));

				if (!PropsValues.RESOURCE_ACTIONS_STRICT_MODE_ENABLED) {
					try {
						_resourceActions.populateModelResources(
							classLoader, _sources);
					}
					catch (ResourceActionsException resourceActionsException) {
						_log.error(
							"Unable to read resource actions config in " +
								PropsKeys.RESOURCE_ACTIONS_CONFIGS,
							resourceActionsException);
					}
				}
			}
		}

		protected synchronized PortletApp getPortletApp() {
			return _portletApp;
		}

		private ServiceRegistrations(Bundle bundle) {
			_bundle = bundle;
		}

		private final Bundle _bundle;
		private PortletApp _portletApp;
		private final List<ServiceReference<Portlet>> _serviceReferences =
			new ArrayList<>();
		private String[] _sources;

	}

}