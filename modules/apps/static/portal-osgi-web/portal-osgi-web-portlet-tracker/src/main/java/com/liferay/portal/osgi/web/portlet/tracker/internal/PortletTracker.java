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

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.osgi.util.StringPlus;
import com.liferay.portal.kernel.application.type.ApplicationType;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.EventDefinition;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.PortletInfo;
import com.liferay.portal.kernel.model.PortletInstance;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletInstanceFactory;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.model.impl.PublicRenderParameterImpl;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperFactory;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portlet.PortletBagFactory;
import com.liferay.portlet.PortletContextBag;
import com.liferay.portlet.PortletContextBagPool;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.portlet.Portlet;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
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
	implements
		ServiceTrackerCustomizer
			<Portlet, com.liferay.portal.kernel.model.Portlet> {

	@Override
	public com.liferay.portal.kernel.model.Portlet addingService(
		ServiceReference<Portlet> serviceReference) {

		Portlet portlet = _bundleContext.getService(serviceReference);

		String portletName = (String)serviceReference.getProperty(
			"javax.portlet.name");

		if (Validator.isNull(portletName)) {
			Class<?> clazz = portlet.getClass();

			portletName = clazz.getName();
		}

		String portletId = PortalUtil.getJsSafePortletId(portletName);

		portletId = StringUtil.replace(
			portletId, new char[] {'$'}, new char[] {'_'});

		if (portletId.length() >
				PortletInstance.PORTLET_INSTANCE_KEY_MAX_LENGTH) {

			_log.error(
				"Portlet ID " + portletId + " has more than " +
					PortletInstance.PORTLET_INSTANCE_KEY_MAX_LENGTH +
						" characters");

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

		BundlePortletApp bundlePortletApp =
			serviceRegistrations.getBundlePortletApp();

		bundlePortletApp.removePortlet(portletModel);

		try {
			_bundleContext.ungetService(serviceReference);
		}
		catch (IllegalStateException ise) {

			// We still need to remove the service so we can ignore this and
			// keep going

		}

		_portletInstanceFactory.destroy(portletModel);

		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
				company.getCompanyId(), WebKeys.PORTLET_CATEGORY);

			portletCategory.separate(portletModel.getRootPortletId());
		}

		serviceRegistrations.removeServiceReference(serviceReference);
	}

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) {
		if (_serviceTracker != null) {
			_serviceTracker.close();
		}

		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext, Portlet.class, this);

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
			bundle.getBundleId());

		try {
			BundlePortletApp bundlePortletApp = createBundlePortletApp(
				bundle, bundleClassLoader, serviceRegistrations);

			com.liferay.portal.kernel.model.Portlet portletModel =
				buildPortletModel(bundlePortletApp, portletId);

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

			portletBagFactory.setClassLoader(bundleClassLoader);
			portletBagFactory.setServletContext(
				bundlePortletApp.getServletContext());
			portletBagFactory.setWARFile(true);

			portletBagFactory.create(portletModel, true);

			List<Company> companies = _companyLocalService.getCompanies();

			deployPortlet(serviceReference, portletModel, companies);

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
		finally {
			thread.setContextClassLoader(contextClassLoader);
		}
	}

	protected com.liferay.portal.kernel.model.Portlet buildPortletModel(
		BundlePortletApp bundlePortletApp, String portletId) {

		com.liferay.portal.kernel.model.Portlet portletModel =
			_portletLocalService.createPortlet(0);

		portletModel.setPortletId(portletId);

		portletModel.setCompanyId(CompanyConstants.SYSTEM);
		portletModel.setPluginPackage(bundlePortletApp.getPluginPackage());
		portletModel.setPortletApp(bundlePortletApp);
		portletModel.setRoleMappers(bundlePortletApp.getRoleMappers());
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
		com.liferay.portal.kernel.model.Portlet portletModel) {
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
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		portletModel.setDefaultPreferences(defaultPreferences);
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
		com.liferay.portal.kernel.model.Portlet portletModel) {

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
		com.liferay.portal.kernel.model.Portlet portletModel) {

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
		com.liferay.portal.kernel.model.Portlet portletModel) {

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

		com.liferay.portal.kernel.model.Portlet portalPortletModel =
			_portletLocalService.getPortletById(
				CompanyConstants.SYSTEM, PortletKeys.PORTAL);

		ServiceTracker<ServletContextHelperRegistration, ServletContext>
			serviceTracker = getServletContextHelperRegistrationServiceTracker(
				bundle, serviceRegistrations);

		serviceRegistrations.setServiceTracker(serviceTracker);

		bundlePortletApp = new BundlePortletApp(
			bundle, portalPortletModel, serviceTracker);

		serviceRegistrations.setBundlePortletApp(bundlePortletApp);

		serviceRegistrations.doConfiguration(classLoader);

		return bundlePortletApp;
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		if (_log.isInfoEnabled()) {
			_log.info("Deactivated");
		}
	}

	protected void deployPortlet(
			ServiceReference<Portlet> serviceReference,
			com.liferay.portal.kernel.model.Portlet portletModel,
			List<Company> companies)
		throws PortalException {

		List<String> categoryNames = StringPlus.asList(
			get(serviceReference, "display-category"));

		if (categoryNames.isEmpty()) {
			categoryNames.add("category.undefined");
		}

		String[] categoryNamesArray = ArrayUtil.toStringArray(categoryNames);

		for (Company company : companies) {
			com.liferay.portal.kernel.model.Portlet companyPortletModel =
				(com.liferay.portal.kernel.model.Portlet)portletModel.clone();

			companyPortletModel.setCompanyId(company.getCompanyId());

			_portletLocalService.deployRemotePortlet(
				companyPortletModel, categoryNamesArray, false);
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

	protected ServiceRegistrations getServiceRegistrations(Long bundleId) {
		ServiceRegistrations serviceRegistrations = _serviceRegistrations.get(
			bundleId);

		if (serviceRegistrations == null) {
			serviceRegistrations = new ServiceRegistrations(bundleId);

			ServiceRegistrations oldServiceRegistrations =
				_serviceRegistrations.putIfAbsent(
					bundleId, serviceRegistrations);

			if (oldServiceRegistrations != null) {
				serviceRegistrations = oldServiceRegistrations;
			}
		}

		return serviceRegistrations;
	}

	protected ServiceTracker<ServletContextHelperRegistration, ServletContext>
		getServletContextHelperRegistrationServiceTracker(
			Bundle bundle, ServiceRegistrations serviceRegistrations) {

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceTracker<ServletContextHelperRegistration, ServletContext>
			serviceTracker = new ServiceTracker<>(
				bundleContext, ServletContextHelperRegistration.class,
				new ServletContextServiceTrackerCustomizer(bundleContext));

		serviceTracker.open();

		return serviceTracker;
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
				"Invalid property " + invalidKey + " for portlet " +
					portletName);
		}
	}

	private static final String _NAMESPACE = "com.liferay.portlet.";

	private static final Log _log = LogFactoryUtil.getLog(PortletTracker.class);

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	private String _httpServiceEndpoint = StringPool.BLANK;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

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
	private SAXReader _saxReader;

	private final ConcurrentMap<Long, ServiceRegistrations>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private ServiceTracker<Portlet, com.liferay.portal.kernel.model.Portlet>
		_serviceTracker;

	@Reference
	private ServletContextHelperFactory _servletContextHelperFactory;

	private class ServiceRegistrations {

		public ServiceRegistrations(Long bundleId) {
			_bundleId = bundleId;
		}

		public synchronized void addServiceReference(
			ServiceReference<Portlet> serviceReference) {

			_serviceReferences.add(serviceReference);
		}

		public synchronized void removeServiceReference(
			ServiceReference<Portlet> serviceReference) {

			_serviceReferences.remove(serviceReference);

			if (!_serviceReferences.isEmpty()) {
				return;
			}

			_serviceReferences.clear();

			_bundlePortletApp = null;

			_serviceRegistrations.remove(_bundleId);

			_serviceTracker.close();
		}

		public synchronized void setBundlePortletApp(
			BundlePortletApp bundlePortletApp) {

			_bundlePortletApp = bundlePortletApp;
		}

		public synchronized void setServiceTracker(
			ServiceTracker<ServletContextHelperRegistration, ServletContext>
				serviceTracker) {

			_serviceTracker = serviceTracker;
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

		private final Long _bundleId;
		private BundlePortletApp _bundlePortletApp;
		private Configuration _configuration;
		private final List<ServiceReference<Portlet>> _serviceReferences =
			new ArrayList<>();
		private ServiceTracker<ServletContextHelperRegistration, ServletContext>
			_serviceTracker;

	}

	private class ServletContextServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ServletContextHelperRegistration, ServletContext> {

		public ServletContextServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ServletContext addingService(
			ServiceReference<ServletContextHelperRegistration> reference) {

			ServletContextHelperRegistration service =
				_bundleContext.getService(reference);

			return service.getServletContext();
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletContextHelperRegistration> reference,
			ServletContext servletContext) {
		}

		@Override
		public void removedService(
			ServiceReference<ServletContextHelperRegistration> reference,
			ServletContext servletContext) {

			try {
				_bundleContext.ungetService(reference);
			}
			catch (IllegalStateException ise) {

				// Ignore this because the service is already ungotten

			}
		}

		private final BundleContext _bundleContext;

	}

}