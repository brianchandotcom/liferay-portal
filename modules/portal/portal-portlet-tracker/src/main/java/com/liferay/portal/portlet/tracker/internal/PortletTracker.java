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

package com.liferay.portal.portlet.tracker.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.application.type.ApplicationType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.RestrictPortletServletRequest;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.EventDefinition;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.PortletInstance;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.impl.PublicRenderParameterImpl;
import com.liferay.portal.security.permission.ResourceActions;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.ResourceActionLocalService;
import com.liferay.portal.servlet.jsp.compiler.JspServlet;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.PortletBagFactory;
import com.liferay.portlet.PortletContextBag;
import com.liferay.portlet.PortletContextBagPool;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.registry.util.StringPlus;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.portlet.Portlet;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.runtime.HttpServiceRuntime;
import org.osgi.service.http.runtime.HttpServiceRuntimeConstants;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
@Component(immediate = true, service = PortletTracker.class)
public class PortletTracker
	implements
		ServiceTrackerCustomizer<Portlet, com.liferay.portal.model.Portlet> {

	@Override
	public com.liferay.portal.model.Portlet addingService(
		ServiceReference<Portlet> serviceReference) {

		BundleContext bundleContext = _componentContext.getBundleContext();

		Portlet portlet = bundleContext.getService(serviceReference);

		String portletName = (String)serviceReference.getProperty(
			"javax.portlet.name");

		if (Validator.isNull(portletName)) {
			Class<?> clazz = portlet.getClass();

			portletName = StringUtil.replace(
				clazz.getName(), new String[] {".", "$"},
				new String[] {"_", "_"});
		}

		String portletId = StringUtil.replace(
			portletName, new String[] {".", "$"}, new String[] {"_", "_"});

		if (portletId.length() >
				PortletInstance.PORTLET_INSTANCE_KEY_MAX_LENGTH) {

			_log.error(
				"Portlet ID " + portletId + " has more than " +
					PortletInstance.PORTLET_INSTANCE_KEY_MAX_LENGTH +
						" characters");

			bundleContext.ungetService(serviceReference);

			return null;
		}

		com.liferay.portal.model.Portlet portletModel =
			_portletLocalService.getPortletById(portletId);

		if (portletModel != null) {
			_log.error("Portlet id " + portletId + " is already in use");

			bundleContext.ungetService(serviceReference);

			return null;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Adding " + serviceReference);
		}

		portletModel = addingPortlet(
			serviceReference, portlet, portletName, portletId);

		if (portletModel == null) {
			bundleContext.ungetService(serviceReference);
		}

		return portletModel;
	}

	@Override
	public void modifiedService(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		removedService(serviceReference, portletModel);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		portletModel.unsetReady();

		ServiceRegistrations serviceRegistrations = _serviceRegistrations.get(
			serviceReference.getBundle());

		if (serviceRegistrations == null) {
			return;
		}

		BundlePortletApp bundlePortletApp =
			serviceRegistrations.getBundlePortletApp();

		bundlePortletApp.removePortlet(portletModel);

		serviceRegistrations.removeServiceReference(serviceReference);

		BundleContext bundleContext = _componentContext.getBundleContext();

		bundleContext.ungetService(serviceReference);

		_portletInstanceFactory.destroy(portletModel);

		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
				company.getCompanyId(), WebKeys.PORTLET_CATEGORY);

			portletCategory.separate(portletModel.getRootPortletId());
		}

		PortletBag portletBag = PortletBagPool.remove(
			portletModel.getRootPortletId());

		if (portletBag != null) {
			portletBag.destroy();
		}
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;

		if (_serviceTracker != null) {
			_serviceTracker.close();
		}

		BundleContext bundleContext = _componentContext.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, Portlet.class, this);

		if (_log.isInfoEnabled()) {
			_log.info("Activated");
		}
	}

	protected com.liferay.portal.model.Portlet addingPortlet(
		ServiceReference<Portlet> serviceReference, Portlet portlet,
		String portletName, String portletId) {

		warnPorletProperties(portletName, serviceReference);

		Bundle bundle = serviceReference.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ServiceRegistrations serviceRegistrations = getServiceRegistrations(
			bundle);

		BundlePortletApp bundlePortletApp = createBundlePortletApp(
			bundle, bundleWiring.getClassLoader(), serviceRegistrations);

		com.liferay.portal.model.Portlet portletModel = buildPortletModel(
			bundlePortletApp, portletId);

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
			bundlePortletApp.getServletContextName());

		PortletContextBagPool.put(
			bundlePortletApp.getServletContextName(), portletContextBag);

		PortletBagFactory portletBagFactory = new BundlePortletBagFactory(
			portlet);

		portletBagFactory.setClassLoader(bundleWiring.getClassLoader());
		portletBagFactory.setServletContext(
			bundlePortletApp.getServletContext());
		portletBagFactory.setWARFile(true);

		try {
			portletBagFactory.create(portletModel);

			checkWebResources(
				bundle.getBundleContext(),
				bundlePortletApp.getServletContextName(),
				bundleWiring.getClassLoader(), serviceRegistrations);

			checkResourceBundles(
				bundle.getBundleContext(), bundleWiring.getClassLoader(),
				portletModel, serviceRegistrations);

			List<Company> companies = _companyLocalService.getCompanies();

			deployPortlet(serviceReference, portletModel, companies);

			checkResources(serviceReference, portletModel, companies);

			portletModel.setReady(true);

			if (_log.isInfoEnabled()) {
				_log.info("Added " + serviceReference);
			}

			serviceRegistrations.addServiceReference(serviceReference);

			return portletModel;
		}
		catch (Exception e) {
			_log.error(
				"Portlet " + portletId + " from " + bundle +
					" failed to initialize",
				e);

			return null;
		}
	}

	protected com.liferay.portal.model.Portlet buildPortletModel(
		BundlePortletApp bundlePortletApp, String portletId) {

		com.liferay.portal.model.Portlet portletModel =
			_portletLocalService.createPortlet(0);

		portletModel.setPortletId(portletId);

		portletModel.setCompanyId(CompanyConstants.SYSTEM);
		portletModel.setPluginPackage(bundlePortletApp.getPluginPackage());
		portletModel.setPortletApp(bundlePortletApp);
		portletModel.setRoleMappers(bundlePortletApp.getRoleMappers());
		portletModel.setStrutsPath(portletId);

		return portletModel;
	}

	protected void checkResourceBundles(
		BundleContext bundleContext, ClassLoader classLoader,
		com.liferay.portal.model.Portlet portletModel,
		ServiceRegistrations serviceRegistrations) {

		if (Validator.isBlank(portletModel.getResourceBundle())) {
			return;
		}

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				portletModel.getResourceBundle(), locale, classLoader);

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("javax.portlet.name", portletModel.getPortletId());
			properties.put("language.id", LocaleUtil.toLanguageId(locale));

			ServiceRegistration<ResourceBundle> serviceRegistration =
				bundleContext.registerService(
					ResourceBundle.class, resourceBundle, properties);

			serviceRegistrations.addServiceRegistration(serviceRegistration);
		}
	}

	protected void checkResources(
			ServiceReference<Portlet> serviceReference,
			com.liferay.portal.model.Portlet portletModel,
			List<Company> companies)
		throws PortalException {

		List<String> portletActions =
			_resourceActions.getPortletResourceActions(
				portletModel.getPortletId());

		_resourceActionLocalService.checkResourceActions(
			portletModel.getPortletId(), portletActions);

		List<String> modelNames = _resourceActions.getPortletModelResources(
			portletModel.getPortletId());

		for (String modelName : modelNames) {
			List<String> modelActions =
				_resourceActions.getModelResourceActions(modelName);

			_resourceActionLocalService.checkResourceActions(
				modelName, modelActions);
		}

		for (Company company : companies) {
			com.liferay.portal.model.Portlet companyPortletModel =
				_portletLocalService.getPortletById(
					company.getCompanyId(), portletModel.getPortletId());

			_portletLocalService.checkPortlet(companyPortletModel);
		}
	}

	protected void checkWebResources(
		BundleContext bundleContext, String contextName,
		ClassLoader classLoader, ServiceRegistrations serviceRegistrations) {

		serviceRegistrations.addServiceRegistration(
			createDefaultServlet(bundleContext, contextName));
		serviceRegistrations.addServiceRegistration(
			createJspServlet(bundleContext, contextName, classLoader));
		serviceRegistrations.addServiceRegistration(
			createPortletServlet(bundleContext, contextName, classLoader));
		serviceRegistrations.addServiceRegistration(
			createRestrictPortletServletRequestFilter(
				bundleContext, contextName, classLoader));
	}

	protected void collectApplicationTypes(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Set<ApplicationType> applicationTypes = new HashSet<>();

		List<String> applicationTypeValues = StringPlus.asList(
			get(serviceReference, "application-type"));

		for (String applicationTypeValue : applicationTypeValues) {
			try {
				ApplicationType applicationType = ApplicationType.parse(
					applicationTypeValue);

				applicationTypes.add(applicationType);
			}
			catch (IllegalArgumentException iae) {
				_log.error("Application type " + applicationTypeValue);

				continue;
			}
		}

		if (applicationTypes.isEmpty()) {
			applicationTypes.add(ApplicationType.WIDGET);
		}

		portletModel.setApplicationTypes(applicationTypes);
	}

	protected void collectCacheScope(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {
	}

	protected void collectExpirationCache(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		int expirationCache = GetterUtil.getInteger(
			serviceReference.getProperty("javax.portlet.expiration-cache"));

		portletModel.setExpCache(expirationCache);
	}

	protected void collectInitParams(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

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
		com.liferay.portal.model.Portlet portletModel) {

		collectApplicationTypes(serviceReference, portletModel);
		collectCacheScope(serviceReference, portletModel);
		collectExpirationCache(serviceReference, portletModel);
		collectInitParams(serviceReference, portletModel);
		collectPortletInfo(serviceReference, portletModel);
		collectPortletModes(serviceReference, portletModel);
		collectPortletPreferences(serviceReference, portletModel);
		collectResourceBundle(serviceReference, portletModel);
		collectSecurityRoleRefs(serviceReference, portletModel);
		collectSupportedProcessingEvents(serviceReference, portletModel);
		collectSupportedPublicRenderParameters(serviceReference, portletModel);
		collectSupportedPublishingEvents(serviceReference, portletModel);
		collectWindowStates(serviceReference, portletModel);
	}

	protected void collectLiferayFeatures(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

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
		portletModel.setFacebookIntegration(
			GetterUtil.getString(
				get(serviceReference, "facebook-integration"),
				portletModel.getFacebookIntegration()));
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
		portletModel.setPopUpPrint(
			GetterUtil.getBoolean(
				get(serviceReference, "pop-up-print"),
				portletModel.isPopUpPrint()));
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
		portletModel.setRemoteable(
			GetterUtil.getBoolean(
				get(serviceReference, "remoteable"),
				portletModel.isRemoteable()));
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

	protected void collectPortletInfo(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

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
		com.liferay.portal.model.Portlet portletModel) {

		Map<String, Set<String>> portletModes = new HashMap<>();

		portletModes.put(
			ContentTypes.TEXT_HTML,
			SetUtil.fromArray(new String[] {toLowerCase(PortletMode.VIEW)}));

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

			portletModes.put(mimeType, mimeTypePortletModes);
		}

		portletModel.setPortletModes(portletModes);
	}

	protected void collectPortletPreferences(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

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
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		portletModel.setDefaultPreferences(defaultPreferences);
	}

	protected void collectResourceBundle(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		String resourceBundle = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.resource-bundle"),
			portletModel.getResourceBundle());

		portletModel.setResourceBundle(resourceBundle);
	}

	protected void collectSecurityRoleRefs(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

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

	protected void collectSupportedProcessingEvents(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Set<QName> processingEvents = new HashSet<>();

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> supportedProcessingEvents = StringPlus.asList(
			serviceReference.getProperty(
				"javax.portlet.supported-processing-event"));

		for (String supportedProcessingEvent : supportedProcessingEvents) {
			String name = supportedProcessingEvent;
			String qname = null;

			String[] parts = StringUtil.split(
				supportedProcessingEvent, StringPool.SEMICOLON);

			if (parts.length == 2) {
				name = parts[0];
				qname = parts[1];
			}

			QName qName = getQName(
				name, qname, portletApp.getDefaultNamespace());

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
		com.liferay.portal.model.Portlet portletModel) {

		Set<PublicRenderParameter> publicRenderParameters = new HashSet<>();

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> supportedPublicRenderParameters = StringPlus.asList(
			serviceReference.getProperty(
				"javax.portlet.supported-public-render-parameter"));

		for (String supportedPublicRenderParameter :
				supportedPublicRenderParameters) {

			String name = supportedPublicRenderParameter;
			String qname = null;

			String[] parts = StringUtil.split(
				supportedPublicRenderParameter, StringPool.SEMICOLON);

			if (parts.length == 2) {
				name = parts[0];
				qname = parts[1];
			}

			QName qName = getQName(
				name, qname, portletApp.getDefaultNamespace());

			PublicRenderParameter publicRenderParameter =
				new PublicRenderParameterImpl(name, qName, portletApp);

			publicRenderParameters.add(publicRenderParameter);
		}

		portletModel.setPublicRenderParameters(publicRenderParameters);
	}

	protected void collectSupportedPublishingEvents(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Set<QName> publishingEvents = new HashSet<>();

		PortletApp portletApp = portletModel.getPortletApp();

		List<String> supportedPublishingEvents = StringPlus.asList(
			serviceReference.getProperty(
				"javax.portlet.supported-publishing-event"));

		for (String supportedPublishingEvent : supportedPublishingEvents) {
			String name = supportedPublishingEvent;
			String qname = null;

			String[] parts = StringUtil.split(
				supportedPublishingEvent, StringPool.SEMICOLON);

			if (parts.length == 2) {
				name = parts[0];
				qname = parts[1];
			}

			QName qName = getQName(
				name, qname, portletApp.getDefaultNamespace());

			publishingEvents.add(qName);
		}

		portletModel.setPublishingEvents(publishingEvents);
	}

	protected void collectWindowStates(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Map<String, Set<String>> windowStates = new HashMap<>();

		windowStates.put(
			ContentTypes.TEXT_HTML,
			SetUtil.fromArray(
				new String[] {
					toLowerCase(LiferayWindowState.EXCLUSIVE),
					toLowerCase(LiferayWindowState.POP_UP),
					toLowerCase(WindowState.MAXIMIZED),
					toLowerCase(WindowState.MINIMIZED),
					toLowerCase(WindowState.NORMAL)
				}));

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

			windowStates.put(mimeType, mimeTypeWindowStates);
		}

		portletModel.setWindowStates(windowStates);
	}

	protected BundlePortletApp createBundlePortletApp(
		Bundle bundle, ClassLoader classLoader,
		ServiceRegistrations serviceRegistrations) {

		BundlePortletApp bundlePortletApp =
			serviceRegistrations.getBundlePortletApp();

		if (bundlePortletApp != null) {
			return bundlePortletApp;
		}

		com.liferay.portal.model.Portlet portalPortletModel =
			_portletLocalService.getPortletById(
				CompanyConstants.SYSTEM, PortletKeys.PORTAL);

		bundlePortletApp = new BundlePortletApp(
			bundle, portalPortletModel, _httpServiceEndpoint);

		createContext(bundle, bundlePortletApp, serviceRegistrations);

		serviceRegistrations.setBundlePortletApp(bundlePortletApp);

		serviceRegistrations.doConfiguration(classLoader);

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			bundlePortletApp.getServletContextName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER,
			Boolean.TRUE.toString());

		serviceRegistrations.addServiceRegistration(
			bundleContext.registerService(
				ServletContextListener.class, bundlePortletApp, properties));

		return bundlePortletApp;
	}

	protected void createContext(
		Bundle bundle, PortletApp portletApp,
		ServiceRegistrations serviceRegistrations) {

		ServletContextHelper servletContextHelper =
			new BundlePortletServletContextHelper(bundle);

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			portletApp.getServletContextName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
			"/" + portletApp.getServletContextName());
		properties.put(Constants.SERVICE_RANKING, 1000);

		serviceRegistrations.addServiceRegistration(
			bundleContext.registerService(
				ServletContextHelper.class, servletContextHelper, properties));
	}

	protected ServiceRegistration<?> createDefaultServlet(
		BundleContext bundleContext, String contextName) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX,
			"/META-INF/resources");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN, "/*");

		return bundleContext.registerService(
			Object.class, new Object(), properties);
	}

	protected ServiceRegistration<Servlet> createJspServlet(
		BundleContext bundleContext, String contextName,
		ClassLoader classLoader) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		Dictionary<String, Object> componentProperties =
			_componentContext.getProperties();

		for (Enumeration<String> keys = componentProperties.keys();
				keys.hasMoreElements();) {

			String key = keys.nextElement();

			if (!key.startsWith(_JSP_SERVLET_INIT_PARAM_PREFIX)) {
				continue;
			}

			String paramName =
				_SERVLET_INIT_PARAM_PREFIX +
					key.substring(_JSP_SERVLET_INIT_PARAM_PREFIX.length());

			properties.put(paramName, componentProperties.get(key));
		}

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "jsp");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "*.jsp");

		return bundleContext.registerService(
			Servlet.class, new JspServletWrapper(), properties);
	}

	protected ServiceRegistration<Servlet> createPortletServlet(
		BundleContext bundleContext, String contextName,
		ClassLoader classLoader) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"Portlet Servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/portlet-servlet/*");

		return bundleContext.registerService(
			Servlet.class, new PortletServletWrapper(), properties);
	}

	protected ServiceRegistration<?> createRestrictPortletServletRequestFilter(
		BundleContext bundleContext, String contextName,
		ClassLoader classLoader) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			contextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_ASYNC_SUPPORTED,
			Boolean.TRUE);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_DISPATCHER,
			new String[] {
				DispatcherType.ASYNC.toString(),
				DispatcherType.FORWARD.toString(),
				DispatcherType.INCLUDE.toString(),
				DispatcherType.REQUEST.toString()
			});
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN, "/*");

		return bundleContext.registerService(
			Filter.class, new RestrictPortletServletRequestFilter(),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;

		_componentContext = null;

		if (_log.isInfoEnabled()) {
			_log.info("Deactivated");
		}
	}

	protected void deployPortlet(
			ServiceReference<Portlet> serviceReference,
			com.liferay.portal.model.Portlet portletModel,
			List<Company> companies)
		throws PortalException {

		String categoryName = GetterUtil.getString(
			get(serviceReference, "display-category"), "category.undefined");

		for (Company company : companies) {
			com.liferay.portal.model.Portlet companyPortletModel =
				(com.liferay.portal.model.Portlet)portletModel.clone();

			companyPortletModel.setCompanyId(company.getCompanyId());

			_portletLocalService.deployRemotePortlet(
				companyPortletModel, new String[] {categoryName}, false);
		}
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
		ServiceRegistrations serviceRegistrations = _serviceRegistrations.get(
			bundle);

		if (serviceRegistrations == null) {
			serviceRegistrations = new ServiceRegistrations();

			ServiceRegistrations oldServiceRegistrations =
				_serviceRegistrations.putIfAbsent(bundle, serviceRegistrations);

			if (oldServiceRegistrations != null) {
				serviceRegistrations = oldServiceRegistrations;
			}
		}

		return serviceRegistrations;
	}

	protected void readResourceActions(
		Configuration configuration, String servletContextName,
		ClassLoader classLoader) {

		if (configuration == null) {
			return;
		}

		Properties properties = configuration.getProperties();

		String[] resourceActionConfigs = StringUtil.split(
			properties.getProperty(PropsKeys.RESOURCE_ACTIONS_CONFIGS));

		for (String resourceActionConfig : resourceActionConfigs) {
			try {
				ResourceActionsUtil.read(
					null, classLoader, resourceActionConfig);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setHttpServiceRuntime(
		HttpServiceRuntime httpServiceRuntime, Map<String, Object> properties) {

		List<String> httpServiceEndpoints = StringPlus.asList(
			properties.get(HttpServiceRuntimeConstants.HTTP_SERVICE_ENDPOINT));

		if (!httpServiceEndpoints.isEmpty()) {
			_httpServiceEndpoint = httpServiceEndpoints.get(0);
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setPortletInstanceFactory(
		PortletInstanceFactory portletInstanceFactory) {

		_portletInstanceFactory = portletInstanceFactory;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Reference(unbind = "-")
	protected void setSAXReader(SAXReader saxReader) {
		_saxReader = saxReader;
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

	private void warnPorletProperties(
		String portletName, ServiceReference<Portlet> serviceReference) {

		if (!_log.isWarnEnabled()) {
			return;
		}

		List<String> invalidKeys = _portletPropertyValidator.validate(
			serviceReference.getPropertyKeys());

		for (String invalidKey : invalidKeys) {
			_log.warn(
				"Invalid property " + invalidKey + " for portlet " +
					portletName);
		}
	}

	private static final String _JSP_SERVLET_INIT_PARAM_PREFIX =
		"jsp.servlet.init.param.";

	private static final String _NAMESPACE = "com.liferay.portlet.";

	private static final String _SERVLET_INIT_PARAM_PREFIX =
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX;

	private static final Log _log = LogFactoryUtil.getLog(PortletTracker.class);

	private volatile CompanyLocalService _companyLocalService;
	private ComponentContext _componentContext;
	private String _httpServiceEndpoint = StringPool.BLANK;
	private volatile PortletInstanceFactory _portletInstanceFactory;
	private volatile PortletLocalService _portletLocalService;
	private final PortletPropertyValidator _portletPropertyValidator =
		new PortletPropertyValidator();
	private volatile ResourceActionLocalService _resourceActionLocalService;
	private volatile ResourceActions _resourceActions;
	private volatile SAXReader _saxReader;
	private final ConcurrentMap<Bundle, ServiceRegistrations>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private ServiceTracker<Portlet, com.liferay.portal.model.Portlet>
		_serviceTracker;

	private class JspServletWrapper extends HttpServlet {

		@Override
		public void destroy() {
			_servlet.destroy();
		}

		@Override
		public ServletConfig getServletConfig() {
			return _servlet.getServletConfig();
		}

		@Override
		public void init(ServletConfig servletConfig) throws ServletException {
			_servlet.init(servletConfig);
		}

		@Override
		public void service(
				ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException, ServletException {

			_servlet.service(servletRequest, servletResponse);
		}

		private final Servlet _servlet = new JspServlet();

	}

	private class PortletServletWrapper extends HttpServlet {

		@Override
		protected void service(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException, ServletException {

			_servlet.service(httpServletRequest, httpServletResponse);
		}

		private final Servlet _servlet = new PortletServlet();

	}

	private class RestrictPortletServletRequestFilter implements Filter {

		@Override
		public void destroy() {
		}

		@Override
		public void doFilter(
				ServletRequest servletRequest, ServletResponse servletResponse,
				FilterChain filterChain)
			throws IOException, ServletException {

			try {
				filterChain.doFilter(servletRequest, servletResponse);
			}
			finally {
				PortletRequest portletRequest =
					(PortletRequest)servletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST);

				if (portletRequest == null) {
					return;
				}

				RestrictPortletServletRequest restrictPortletServletRequest =
					new RestrictPortletServletRequest(
						PortalUtil.getHttpServletRequest(portletRequest));

				Enumeration<String> enumeration =
					servletRequest.getAttributeNames();

				while (enumeration.hasMoreElements()) {
					String name = enumeration.nextElement();

					if (!RestrictPortletServletRequest.isSharedRequestAttribute(
							name)) {

						continue;
					}

					restrictPortletServletRequest.setAttribute(
						name, servletRequest.getAttribute(name));
				}

				restrictPortletServletRequest.mergeSharedAttributes();
			}
		}

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
		}

	}

	private class ServiceRegistrations {

		public synchronized void addServiceReference(
			ServiceReference<Portlet> serviceReference) {

			_serviceReferences.add(serviceReference);
		}

		public synchronized void addServiceRegistration(
			ServiceRegistration<?> serviceRegistration) {

			if (serviceRegistration == null) {
				return;
			}

			_serviceRegistrations.add(serviceRegistration);
		}

		public synchronized void removeServiceReference(
			ServiceReference<Portlet> serviceReference) {

			_serviceReferences.remove(serviceReference);

			if (!_serviceReferences.isEmpty()) {
				return;
			}

			_serviceRegistrations.remove(this);

			close();
		}

		public synchronized void setBundlePortletApp(
			BundlePortletApp bundlePortletApp) {

			_bundlePortletApp = bundlePortletApp;
		}

		protected synchronized void close() {
			for (ServiceRegistration<?> serviceRegistration :
					_serviceRegistrations) {

				serviceRegistration.unregister();
			}

			_bundlePortletApp = null;

			_serviceRegistrations.clear();
		}

		protected synchronized void doConfiguration(ClassLoader classLoader) {
			try {
				_configuration = ConfigurationFactoryUtil.getConfiguration(
					classLoader, "portlet");
			}
			catch (Exception e) {
			}

			readResourceActions(
				_configuration, _bundlePortletApp.getServletContextName(),
				classLoader);
		}

		protected synchronized BundlePortletApp getBundlePortletApp() {
			return _bundlePortletApp;
		}

		private BundlePortletApp _bundlePortletApp;
		private Configuration _configuration;
		private final List<ServiceReference<Portlet>> _serviceReferences =
			new ArrayList<>();
		private final List<ServiceRegistration<?>> _serviceRegistrations =
			new ArrayList<>();

	}

}