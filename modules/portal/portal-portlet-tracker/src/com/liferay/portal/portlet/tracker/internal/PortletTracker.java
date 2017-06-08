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

package com.liferay.portal.portlet.tracker.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.security.permission.ResourceActions;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.ResourceActionLocalService;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletBagFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.registry.util.StringPlus;
import com.liferay.util.JS;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.Portlet;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	service = PortletTracker.class
)
public class PortletTracker
	implements ServiceTrackerCustomizer<Portlet, Portlet> {

	@Override
	public Portlet addingService(ServiceReference<Portlet> serviceReference) {
		String portletName = (String)serviceReference.getProperty(
			"javax.portlet.name");

		String portletId = JS.getSafeName(portletName);

		if (portletId.length() > _PORTLET_ID_MAX_LENGTH) {
			throw new RuntimeException(
				"Portlet id " + portletId + " has more than " +
					_PORTLET_ID_MAX_LENGTH + " characters");
		}

		if (_log.isInfoEnabled()) {
			_log.info("Reading portlet " + portletId);
		}

		try {
			BundleContext bundleContext = _componentContext.getBundleContext();

			Portlet portlet = bundleContext.getService(serviceReference);

			addingPortlet(serviceReference, portlet, portletName, portletId);

			return portlet;
		}
		catch (Throwable t) {
			_log.error(t, t);
		}

		return null;
	}

	@Override
	public void modifiedService(
		ServiceReference<Portlet> serviceReference, Portlet portletarg1) {

		removedService(serviceReference, portletarg1);
		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<Portlet> serviceReference, Portlet portlet) {

		String portletName = (String)serviceReference.getProperty(
			"javax.portlet.name");

		if (Validator.isNull(portletName)) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Removing: " + serviceReference);
		}

		String portletId = JS.getSafeName(portletName);

		com.liferay.portal.model.Portlet portletModel =
			_portletLocalService.getPortletById(portletId);

		_portletInstanceFactory.destroy(portletModel);

		List<Company> companies;

		try {
			companies = _companyLocalService.getCompanies();
		}
		catch (SystemException se) {
			throw new RuntimeException(se);
		}

		for (Company company : companies) {
			PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
				company.getCompanyId(), WebKeys.PORTLET_CATEGORY);

			portletCategory.separate(portletId);
		}

		PortletBag portletBag = PortletBagPool.remove(portletId);

		if (portletBag != null) {
			portletBag.destroy();
		}
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;

		BundleContext bundleContext = _componentContext.getBundleContext();

		Filter filter;

		try {
			filter = bundleContext.createFilter(
				"(&(javax.portlet.name=*)(objectClass=" +
					Portlet.class.getName() +  "))");
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}

		_serviceTracker = new ServiceTracker<Portlet, Portlet>(
			bundleContext, filter, this);

		_serviceTracker.open();

		if (_log.isInfoEnabled()) {
			_log.info("Activated!");
		}
	}

	protected void addingPortlet(
			ServiceReference<Portlet> serviceReference, Portlet portlet,
			String portletName, String portletId)
		throws Exception {

		com.liferay.portal.model.Portlet portletModel =
			assembleInitialPortletModel(portletId);

		portletModel.setPortletName(portletName);

		String displayName = GetterUtil.getString(
			serviceReference.getProperty("javax.portlet.display-name"),
			portletName);

		portletModel.setDisplayName(displayName);
		portletModel.setPortletClass(portlet.getClass().getName());

		collectCacheScope(serviceReference, portletModel);
		collectExpirationCache(serviceReference, portletModel);
		collectInitParams(serviceReference, portletModel);
		collectPortletModes(serviceReference, portletModel);
		collectPortletInfo(serviceReference, portletModel, displayName);
		collectPortletPreferences(serviceReference, portletModel);
		collectSecurityRoleRefs(serviceReference, portletModel);
		collectWindowStates(serviceReference, portletModel);

		Bundle bundle = serviceReference.getBundle();

		PortletBagFactory portletBagFactory = new TrackerPortletBagFactory(
			bundle, portlet);

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader classLoader = bundleWiring.getClassLoader();

		portletBagFactory.setClassLoader(classLoader);
		portletBagFactory.setServletContext(_servletContext);
		portletBagFactory.setWARFile(true);

		portletBagFactory.create(portletModel);

		List<Company> companies = _companyLocalService.getCompanies();

		deployPortlet(serviceReference, portletModel, companies);
		checkResources(serviceReference, portletModel, companies);

		portletModel.setReady(true);

		if (_log.isInfoEnabled()) {
			_log.info("Added: " + serviceReference);
		}
	}

	protected com.liferay.portal.model.Portlet assembleInitialPortletModel(
		String portletId) {

		com.liferay.portal.model.Portlet portletModel;

		try {
			portletModel = _portletLocalService.getPortletById(
				CompanyConstants.SYSTEM, PortletKeys.PORTAL);
		}
		catch (SystemException e) {
			throw new RuntimeException(e);
		}

		PortletApp portletApp = portletModel.getPortletApp();
		PluginPackage pluginPackage = portletModel.getPluginPackage();

		portletModel = new PortletImpl(CompanyConstants.SYSTEM, portletId);

		portletModel.setTimestamp(System.currentTimeMillis());

		portletModel.setPluginPackage(pluginPackage);
		portletModel.setPortletApp(portletApp);

		return portletModel;
	}

	protected void collectCacheScope(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		// Liferay doesn't support this
	}

	protected void collectExpirationCache(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Integer expirationCache = (Integer)serviceReference.getProperty(
			"javax.portlet.expiration-cache");

		portletModel.setExpCache(expirationCache);
	}

	protected void collectInitParams(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Map<String, String> initParams = new HashMap<String, String>();

		for (String initParamKey : serviceReference.getPropertyKeys()) {
			if (!initParamKey.startsWith("javax.portlet.init-param.")) {
				continue;
			}

			initParams.put(
				initParamKey.substring("javax.portlet.init-param.".length()),
				GetterUtil.getString(serviceReference.getProperty(initParamKey)));
		}

		portletModel.setInitParams(initParams);
	}

	protected void collectPortletInfo(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel, String displayName) {

		String portletInfoTitle = (String)serviceReference.getProperty(
			"javax.portlet.info.title");

		if (portletInfoTitle == null) {
			portletInfoTitle = displayName;
		}

		String portletInfoShortTitle = (String)serviceReference.getProperty(
			"javax.portlet.info.short-title");
		String portletInfoKeyWords = (String)serviceReference.getProperty(
			"javax.portlet.info.keywords");
		String portletDescription = (String)serviceReference.getProperty(
			"javax.portlet.description");

		PortletInfo portletInfo = new PortletInfo(
			portletInfoTitle, portletInfoShortTitle, portletInfoKeyWords,
			portletDescription);

		portletModel.setPortletInfo(portletInfo);
	}

	protected void collectPortletPreferences(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		String defaultPreferences = (String)serviceReference.getProperty(
			"javax.portlet.preferences");

		if ((defaultPreferences != null) &&
			defaultPreferences.startsWith("classpath:")) {

			Bundle bundle = serviceReference.getBundle();

			URL resource = bundle.getResource(
				defaultPreferences.substring("classpath:".length()));

			if (resource != null) {
				try {
					defaultPreferences = StringUtil.read(resource.openStream());
				}
				catch (IOException e) {
					_log.error(e, e);
				}
			}
		}

		portletModel.setDefaultPreferences(defaultPreferences);
	}

	protected void collectSecurityRoleRefs(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		List<String> roleRefs = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.security-role-ref"));

		Set<String> unlinkedRoles = new HashSet<String>(roleRefs);

//		Map<String, String> roleMappers = portletModel.getRoleMappers();
//
//		for (String key : serviceReference.getPropertyKeys()) {
//			if (!key.startsWith("javax.portlet.security-role-ref.")) {
//				continue;
//			}
//
//			String roleName = key.substring(
//				"javax.portlet.security-role-ref.".length());
//			String roleLink = (String)serviceReference.getProperty(key);
//
//			unlinkedRoles.add(roleName);
//			roleMappers.put(roleName, roleLink);
//		}
//
//		if (roleMappers.isEmpty()) {
//			roleMappers.put("user", "User");
//		}

		portletModel.setUnlinkedRoles(unlinkedRoles);
//		portletModel.linkRoles();
	}

	protected void collectPortletModes(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Map<String, Set<String>> portletModes =
			new HashMap<String, Set<String>>();

		Set<String> defaultModes = new HashSet<String>();

		defaultModes.add(PortletMode.VIEW.toString().toLowerCase());

		portletModes.put(ContentTypes.TEXT_HTML, defaultModes);

		List<String> portletModeStrings = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.portletModes"));

		for (String portletModeString : portletModeStrings) {
			String[] parts = StringUtil.split(portletModeString, ';');

			if (parts.length != 2) {
				continue;
			}

			String mimeType = parts[0];

			Set<String> mimeTypePortletModes = new HashSet<String>();

			String[] modes = StringUtil.split(parts[1]);

			mimeTypePortletModes.add(
				PortletMode.VIEW.toString().toLowerCase());

			for (String mode : modes) {
				mimeTypePortletModes.add(mode.trim().toLowerCase());
			}

			portletModes.put(mimeType, mimeTypePortletModes);
		}

		portletModel.setPortletModes(portletModes);
	}

	protected void collectWindowStates(
		ServiceReference<Portlet> serviceReference,
		com.liferay.portal.model.Portlet portletModel) {

		Map<String, Set<String>> windowStates =
			new HashMap<String, Set<String>>();

		Set<String> defaultWindowStates = new HashSet<String>();

		defaultWindowStates.add(WindowState.NORMAL.toString().toLowerCase());
		defaultWindowStates.add(WindowState.MAXIMIZED.toString().toLowerCase());
		defaultWindowStates.add(WindowState.MINIMIZED.toString().toLowerCase());
		defaultWindowStates.add(
			LiferayWindowState.EXCLUSIVE.toString().toLowerCase());
		defaultWindowStates.add(
			LiferayWindowState.POP_UP.toString().toLowerCase());

		windowStates.put(ContentTypes.TEXT_HTML, defaultWindowStates);

		List<String> windowStateStrings = StringPlus.asList(
			serviceReference.getProperty("javax.portlet.windowStates"));

		for (String windowStateString : windowStateStrings) {
			String[] parts = StringUtil.split(windowStateString, ';');

			if (parts.length != 2) {
				continue;
			}

			String mimeType = parts[0];

			Set<String> mimeTypeWindowStates = new HashSet<String>();

			String[] states = StringUtil.split(parts[1]);

			mimeTypeWindowStates.add(
				WindowState.NORMAL.toString().toLowerCase());

			boolean addDefaultStates = true;

			for (String state : states) {
				mimeTypeWindowStates.add(state.trim().toLowerCase());

				addDefaultStates = false;
			}

			if (addDefaultStates) {
				mimeTypeWindowStates.add(
					WindowState.MAXIMIZED.toString().toLowerCase());
				mimeTypeWindowStates.add(
					WindowState.MINIMIZED.toString().toLowerCase());
				mimeTypeWindowStates.add(
					LiferayWindowState.EXCLUSIVE.toString().toLowerCase());
				mimeTypeWindowStates.add(
					LiferayWindowState.POP_UP.toString().toLowerCase());
			}

			windowStates.put(mimeType, mimeTypeWindowStates);
		}

		portletModel.setWindowStates(windowStates);
	}

	protected void checkResources(
			ServiceReference<Portlet> serviceReference,
			com.liferay.portal.model.Portlet portlet, List<Company> companies)
		throws PortalException, SystemException {

		List<String> modelNames = _resourceActions.getPortletModelResources(
			portlet.getPortletId());

		List<String> portletActions =
			_resourceActions.getPortletResourceActions(
				portlet.getPortletId());

		_resourceActionLocalService.checkResourceActions(
			portlet.getPortletId(), portletActions);

		for (String modelName : modelNames) {
			List<String> modelActions =
				_resourceActions.getModelResourceActions(modelName);

			_resourceActionLocalService.checkResourceActions(
				modelName, modelActions);
		}

		for (Company company : companies) {
			com.liferay.portal.model.Portlet curPortlet =
				_portletLocalService.getPortletById(
					company.getCompanyId(), portlet.getPortletId());

			_portletLocalService.checkPortlet(curPortlet);
		}
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_componentContext = null;
		_serviceTracker = null;

		if (_log.isInfoEnabled()) {
			_log.info("Deactivated!");
		}
	}

	protected void deployPortlet(
			ServiceReference<Portlet> serviceReference,
			com.liferay.portal.model.Portlet portletModel,
			List<Company> companies)
		throws PortalException, SystemException {

		String categoryName = (String)serviceReference.getProperty(
			"com.liferay.display.category");

		if (categoryName == null) {
			categoryName = "category.undefined";
		}

		for (Company company : companies) {
			com.liferay.portal.model.Portlet curPortletModel =
				(com.liferay.portal.model.Portlet)portletModel.clone();

			curPortletModel.setCompanyId(company.getCompanyId());

			_portletLocalService.deployRemotePortlet(
				curPortletModel, categoryName);
		}
	}

	@Reference
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference
	protected void setPortletInstanceFactory(
		PortletInstanceFactory portletInstanceFactory) {

		_portletInstanceFactory = portletInstanceFactory;
	}

	@Reference
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference
	protected void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Reference
	protected void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Reference(
		target = "(original.bean=*)"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected void unsetCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = null;
	}

	protected void unsetPortletInstanceFactory(
		PortletInstanceFactory portletInstanceFactory) {

		_portletInstanceFactory = null;
	}

	protected void unsetResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = null;
	}

	protected void unsetResourceActions(ResourceActions resourceActions) {
		_resourceActions = null;
	}

	protected void unsetServletContext(ServletContext servletContext) {
		_servletContext = null;
	}

	private static final int _PORTLET_ID_MAX_LENGTH =
		255 - PortletConstants.INSTANCE_SEPARATOR.length() +
			PortletConstants.USER_SEPARATOR.length() + 39;

	private static final Log _log = LogFactoryUtil.getLog(PortletTracker.class);

	private ComponentContext _componentContext;
	private CompanyLocalService _companyLocalService;
	private PortletInstanceFactory _portletInstanceFactory;
	private PortletLocalService _portletLocalService;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourceActions _resourceActions;
	private ServiceTracker<Portlet, Portlet> _serviceTracker;
	private ServletContext _servletContext;

}