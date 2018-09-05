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

package com.liferay.portal.servlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.events.StartupAction;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletFilter;
import com.liferay.portal.kernel.model.PortletURLListener;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.patcher.PatchInconsistencyException;
import com.liferay.portal.kernel.patcher.PatcherUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.InactiveRequestHandler;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.service.impl.LayoutTemplateLocalServiceImpl;
import com.liferay.portal.servlet.filters.absoluteredirects.AbsoluteRedirectsResponse;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.setup.SetupWizardSampleDataUtil;
import com.liferay.portal.struts.PortletRequestProcessor;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.ExtRegistry;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portlet.PortletBagFactory;
import com.liferay.portlet.PortletFilterFactory;
import com.liferay.portlet.PortletURLListenerFactory;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.dependency.ServiceDependencyListener;
import com.liferay.registry.dependency.ServiceDependencyManager;
import com.liferay.social.kernel.util.SocialConfigurationUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesUtilImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Brian Myunghun Kim
 */
public class MainServlet extends ActionServlet {

	@Override
	public void destroy() {
		if (_log.isDebugEnabled()) {
			_log.debug("Destroy plugins");
		}

		_portalInitializedModuleServiceLifecycleServiceRegistration.
			unregister();
		_servletContextServiceRegistration.unregister();
		_systemCheckModuleServiceLifecycleServiceRegistration.unregister();

		PortalLifecycleUtil.flushDestroys();

		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();

		if (_log.isDebugEnabled()) {
			_log.debug("Destroy portlets");
		}

		try {
			destroyPortlets(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Destroy companies");
		}

		try {
			destroyCompanies();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Process global shutdown events");
		}

		try {
			processGlobalShutdownEvents();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Destroy");
		}

		callParentDestroy();
	}

	@Override
	public void init() throws ServletException {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize");
		}

		ServletContext servletContext = getServletContext();

		servletContext.setAttribute(MainServlet.class.getName(), Boolean.TRUE);

		callParentInit();

		if (_log.isDebugEnabled()) {
			_log.debug("Verify patch levels");
		}

		try {
			PatcherUtil.verifyPatchLevels();
		}
		catch (PatchInconsistencyException pie) {
			if (!PropsValues.VERIFY_PATCH_LEVELS_DISABLED) {
				_log.error(
					"Stopping the server due to the inconsistent patch levels");

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Set the property \"verify.patch.levels.disabled\" " +
							"to override stopping the server due to the " +
								"inconsistent patch levels",
						pie);
				}

				System.exit(0);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Verify JVM configuration");
		}

		if (_log.isWarnEnabled()) {
			if (!StringPool.DEFAULT_CHARSET_NAME.startsWith("UTF-")) {
				StringBundler sb = new StringBundler(4);

				sb.append("The default JVM character set \"");
				sb.append(StringPool.DEFAULT_CHARSET_NAME);
				sb.append("\" is not UTF. Please review the JVM property ");
				sb.append("\"file.encoding\".");

				_log.warn(sb.toString());
			}

			TimeZone timeZone = TimeZone.getDefault();

			String timeZoneID = timeZone.getID();

			if (!Objects.equals("UTC", timeZoneID) &&
				!Objects.equals("GMT", timeZoneID)) {

				StringBundler sb = new StringBundler(4);

				sb.append("The default JVM time zone \"");
				sb.append(timeZoneID);
				sb.append("\" is not UTC or GMT. Please review the JVM ");
				sb.append("property \"user.timezone\".");

				_log.warn(sb.toString());
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Process startup events");
		}

		try {
			processStartupEvents();
		}
		catch (Exception e) {
			_log.error(e, e);

			System.out.println(
				"Stopping the server due to unexpected startup errors");

			System.exit(0);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize plugin package");
		}

		PluginPackage pluginPackage = null;

		try {
			pluginPackage = initPluginPackage();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize portlets");
		}

		List<Portlet> portlets = new ArrayList<>();

		try {
			portlets.addAll(initPortlets(pluginPackage));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			initLayoutTemplates(pluginPackage);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize social");
		}

		try {
			initSocial(pluginPackage);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize themes");
		}

		try {
			initThemes(pluginPackage, portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize web settings");
		}

		try {
			initWebSettings();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize extension environment");
		}

		try {
			initExt();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Process global startup events");
		}

		try {
			processGlobalStartupEvents();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize resource actions");
		}

		try {
			initResourceActions(portlets);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			initCompanies();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (StartupHelperUtil.isDBNew() &&
			PropsValues.SETUP_WIZARD_ADD_SAMPLE_DATA) {

			try {
				SetupWizardSampleDataUtil.addSampleData(
					PortalInstances.getDefaultCompanyId());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize plugins");
		}

		try {
			initPlugins();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		servletContext.setAttribute(WebKeys.STARTUP_FINISHED, Boolean.TRUE);

		StartupHelperUtil.setStartupFinished(true);

		registerPortalInitialized();

		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Process service request");
		}

		if (processShutdownRequest(request, response)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processed shutdown request");
			}

			return;
		}

		if (processMaintenanceRequest(request, response)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processed maintenance request");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Get company id");
		}

		long companyId = getCompanyId(request);

		if (processCompanyInactiveRequest(request, response, companyId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processed company inactive request");
			}

			return;
		}

		try {
			if (processGroupInactiveRequest(request, response)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Processed site inactive request");
				}

				return;
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutException) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
			else {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Set portal port");
		}

		setPortalInetSocketAddresses(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Check variables");
		}

		checkServletContext(request);
		checkPortletRequestProcessor(request);
		checkTilesDefinitionsFactory();

		if (_log.isDebugEnabled()) {
			_log.debug("Handle non-serializable request");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Encrypt request");
		}

		request = encryptRequest(request, companyId);

		long userId = getUserId(request);

		String remoteUser = getRemoteUser(request, userId);

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Authenticate user id ", String.valueOf(userId),
						" and remote user ", remoteUser));
			}

			userId = loginUser(
				request, response, companyId, userId, remoteUser);

			if (_log.isDebugEnabled()) {
				_log.debug("Authenticated user id " + userId);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Set session thread local");
		}

		PortalSessionThreadLocal.setHttpSession(request.getSession());

		if (_log.isDebugEnabled()) {
			_log.debug("Process service pre events");
		}

		if (processServicePre(request, response, userId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing service pre events has errors");
			}

			return;
		}

		if (hasAbsoluteRedirect(request)) {
			if (_log.isDebugEnabled()) {
				String currentURL = PortalUtil.getCurrentURL(request);

				_log.debug(
					"Current URL " + currentURL + " has absolute redirect");
			}

			return;
		}

		if (!hasThemeDisplay(request)) {
			if (_log.isDebugEnabled()) {
				String currentURL = PortalUtil.getCurrentURL(request);

				_log.debug(
					"Current URL " + currentURL +
						" does not have a theme display");
			}

			return;
		}

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Call parent service");
			}

			callParentService(request, response);
		}
		finally {
			if (_log.isDebugEnabled()) {
				_log.debug("Process service post events");
			}

			processServicePost(request, response);
		}
	}

	protected void callParentDestroy() {
		super.destroy();
	}

	protected void callParentInit() throws ServletException {
		super.init();
	}

	protected void callParentService(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		super.service(request, response);
	}

	protected void checkPortletRequestProcessor(HttpServletRequest request)
		throws ServletException {

		ServletContext servletContext = getServletContext();

		PortletRequestProcessor portletReqProcessor =
			(PortletRequestProcessor)servletContext.getAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR);

		if (portletReqProcessor == null) {
			ModuleConfig moduleConfig = getModuleConfig(request);

			portletReqProcessor = PortletRequestProcessor.getInstance(
				this, moduleConfig);

			servletContext.setAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR, portletReqProcessor);
		}
	}

	protected void checkServletContext(HttpServletRequest request) {
		ServletContext servletContext = getServletContext();

		request.setAttribute(WebKeys.CTX, servletContext);
	}

	protected void checkTilesDefinitionsFactory() {
		ServletContext servletContext = getServletContext();

		if (servletContext.getAttribute(TilesUtilImpl.DEFINITIONS_FACTORY) !=
				null) {

			return;
		}

		servletContext.setAttribute(
			TilesUtilImpl.DEFINITIONS_FACTORY,
			servletContext.getAttribute(TilesUtilImpl.DEFINITIONS_FACTORY));
	}

	protected void checkWebSettings(String xml) throws DocumentException {
		Document doc = UnsecureSAXReaderUtil.read(xml);

		Element root = doc.getRootElement();

		int timeout = PropsValues.SESSION_TIMEOUT;

		Element sessionConfig = root.element("session-config");

		if (sessionConfig != null) {
			String sessionTimeout = sessionConfig.elementText(
				"session-timeout");

			timeout = GetterUtil.getInteger(sessionTimeout, timeout);
		}

		PropsUtil.set(PropsKeys.SESSION_TIMEOUT, String.valueOf(timeout));

		PropsValues.SESSION_TIMEOUT = timeout;

		I18nServlet.setLanguageIds(root);

		I18nFilter.setLanguageIds(I18nServlet.getLanguageIds());
	}

	protected void destroyCompanies() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIds();

		for (long companyId : companyIds) {
			destroyCompany(companyId);
		}
	}

	protected void destroyCompany(long companyId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Process shutdown events");
		}

		try {
			EventsProcessorUtil.process(
				PropsKeys.APPLICATION_SHUTDOWN_EVENTS,
				PropsValues.APPLICATION_SHUTDOWN_EVENTS,
				new String[] {String.valueOf(companyId)});
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void destroyPortlets(List<Portlet> portlets) throws Exception {
		for (Portlet portlet : portlets) {
			PortletInstanceFactoryUtil.destroy(portlet);

			Map<String, PortletFilter> portletFilters =
				portlet.getPortletFilters();

			for (PortletFilter portletFilter : portletFilters.values()) {
				PortletFilterFactory.destroy(portletFilter);
			}
		}
	}

	protected HttpServletRequest encryptRequest(
		HttpServletRequest request, long companyId) {

		boolean encryptRequest = ParamUtil.getBoolean(request, WebKeys.ENCRYPT);

		if (!encryptRequest) {
			return request;
		}

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

			request = new EncryptedServletRequest(request, company.getKeyObj());
		}
		catch (Exception e) {
		}

		return request;
	}

	protected long getCompanyId(HttpServletRequest request) {
		return PortalInstances.getCompanyId(request);
	}

	protected String getRemoteUser(HttpServletRequest request, long userId) {
		String remoteUser = request.getRemoteUser();

		if (!PropsValues.PORTAL_JAAS_ENABLE) {
			HttpSession session = request.getSession();

			String jRemoteUser = (String)session.getAttribute("j_remoteuser");

			if (jRemoteUser != null) {
				remoteUser = jRemoteUser;
			}
		}

		if ((userId > 0) && (remoteUser == null)) {
			remoteUser = String.valueOf(userId);
		}

		return remoteUser;
	}

	@Override
	protected synchronized RequestProcessor getRequestProcessor(
			ModuleConfig moduleConfig)
		throws ServletException {

		ServletContext servletContext = getServletContext();

		String key = Globals.REQUEST_PROCESSOR_KEY + moduleConfig.getPrefix();

		RequestProcessor requestProcessor =
			(RequestProcessor)servletContext.getAttribute(key);

		if (requestProcessor == null) {
			ControllerConfig controllerConfig =
				moduleConfig.getControllerConfig();

			try {
				requestProcessor =
					(RequestProcessor)InstanceFactory.newInstance(
						ClassLoaderUtil.getPortalClassLoader(),
						controllerConfig.getProcessorClass());
			}
			catch (Exception e) {
				throw new ServletException(e);
			}

			requestProcessor.init(this, moduleConfig);

			servletContext.setAttribute(key, requestProcessor);
		}

		return requestProcessor;
	}

	protected long getUserId(HttpServletRequest request) {
		return PortalUtil.getUserId(request);
	}

	protected boolean hasAbsoluteRedirect(HttpServletRequest request) {
		if (request.getAttribute(AbsoluteRedirectsResponse.class.getName()) ==
				null) {

			return false;
		}

		return true;
	}

	protected boolean hasThemeDisplay(HttpServletRequest request) {
		if (request.getAttribute(WebKeys.THEME_DISPLAY) == null) {
			return false;
		}

		return true;
	}

	protected void initCompanies() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize companies");
		}

		ServletContext servletContext = getServletContext();

		try {
			String[] webIds = PortalInstances.getWebIds();

			for (String webId : webIds) {
				PortalInstances.initCompany(servletContext, webId);
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(
				PortalInstances.getDefaultCompanyId());
		}
	}

	protected void initExt() throws Exception {
		ServletContext servletContext = getServletContext();

		ExtRegistry.registerPortal(servletContext);
	}

	protected void initLayoutTemplates(final PluginPackage pluginPackage) {
		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					try {
						if (_log.isDebugEnabled()) {
							_log.debug("Initialize layout templates");
						}

						ServletContext servletContext = getServletContext();

						String[] xmls = {
							HttpUtil.URLtoString(
								servletContext.getResource(
									"/WEB-INF/liferay-layout-templates.xml")),
							HttpUtil.URLtoString(
								servletContext.getResource(
									"/WEB-INF" +
										"/liferay-layout-templates-ext.xml"))
						};

						List<LayoutTemplate> layoutTemplates =
							LayoutTemplateLocalServiceUtil.init(
								servletContext, xmls, pluginPackage);

						servletContext.setAttribute(
							WebKeys.PLUGIN_LAYOUT_TEMPLATES, layoutTemplates);
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}

				@Override
				public void destroy() {
				}

			});

		Registry registry = RegistryUtil.getRegistry();

		Collection<Filter> filters = new ArrayList<>();

		for (String langType :
				LayoutTemplateLocalServiceImpl.supportedLangTypes) {

			StringBundler sb = new StringBundler(5);

			sb.append("(&(language.type=");
			sb.append(langType);
			sb.append(")(objectClass=");
			sb.append(TemplateManager.class.getName());
			sb.append("))");

			Filter filter = registry.getFilter(sb.toString());

			filters.add(filter);
		}

		serviceDependencyManager.registerDependencies(
			filters.toArray(new Filter[0]));
	}

	protected PluginPackage initPluginPackage() throws Exception {
		ServletContext servletContext = getServletContext();

		return PluginPackageUtil.readPluginPackageServletContext(
			servletContext);
	}

	protected void initPlugins() throws Exception {
		HotDeployUtil.setCapturePrematureEvents(false);

		PortalLifecycleUtil.flushInits();
	}

	protected void initPortletApp(
			Portlet portlet, ServletContext servletContext)
		throws PortletException {

		PortletApp portletApp = portlet.getPortletApp();

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			PortletFilterFactory.create(portletFilter, portletContext);
		}

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLListenerFactory.create(portletURLListener);
		}
	}

	protected List<Portlet> initPortlets(PluginPackage pluginPackage)
		throws Exception {

		ServletContext servletContext = getServletContext();

		String[] xmls = new String[PropsValues.PORTLET_CONFIGS.length];

		for (int i = 0; i < PropsValues.PORTLET_CONFIGS.length; i++) {
			xmls[i] = HttpUtil.URLtoString(
				servletContext.getResource(PropsValues.PORTLET_CONFIGS[i]));
		}

		PortletLocalServiceUtil.initEAR(servletContext, xmls, pluginPackage);

		PortletBagFactory portletBagFactory = new PortletBagFactory();

		portletBagFactory.setClassLoader(
			ClassLoaderUtil.getPortalClassLoader());
		portletBagFactory.setServletContext(servletContext);
		portletBagFactory.setWARFile(false);

		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();

		for (int i = 0; i < portlets.size(); i++) {
			Portlet portlet = portlets.get(i);

			portletBagFactory.create(portlet);

			if (i == 0) {
				initPortletApp(portlet, servletContext);
			}
		}

		servletContext.setAttribute(WebKeys.PLUGIN_PORTLETS, portlets);

		return portlets;
	}

	protected void initResourceActions(List<Portlet> portlets)
		throws Exception {

		for (Portlet portlet : portlets) {
			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(portlet);

			ResourceActionLocalServiceUtil.checkResourceActions(
				portlet.getPortletId(), portletActions);

			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(
					portlet.getPortletId());

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected void initServerDetector() throws Exception {
	}

	protected void initSocial(PluginPackage pluginPackage) throws Exception {
		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		ServletContext servletContext = getServletContext();

		String[] xmls = {
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-social.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-social-ext.xml"))
		};

		SocialConfigurationUtil.read(classLoader, xmls);
	}

	protected void initThemes(
			PluginPackage pluginPackage, List<Portlet> portlets)
		throws Exception {

		ServletContext servletContext = getServletContext();

		String[] xmls = {
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-look-and-feel.xml")),
			HttpUtil.URLtoString(
				servletContext.getResource(
					"/WEB-INF/liferay-look-and-feel-ext.xml"))
		};

		List<Theme> themes = ThemeLocalServiceUtil.init(
			servletContext, null, true, xmls, pluginPackage);

		servletContext.setAttribute(WebKeys.PLUGIN_THEMES, themes);
	}

	protected void initWebSettings() throws Exception {
		ServletContext servletContext = getServletContext();

		String xml = HttpUtil.URLtoString(
			servletContext.getResource("/WEB-INF/web.xml"));

		checkWebSettings(xml);
	}

	protected long loginUser(
			HttpServletRequest request, HttpServletResponse response,
			long companyId, long userId, String remoteUser)
		throws PortalException {

		if ((userId > 0) || (remoteUser == null)) {
			return userId;
		}

		userId = GetterUtil.getLong(remoteUser);

		User user = UserLocalServiceUtil.getUserById(userId);

		if (!user.isDefaultUser()) {
			EventsProcessorUtil.process(
				PropsKeys.LOGIN_EVENTS_PRE, PropsValues.LOGIN_EVENTS_PRE,
				request, response);

			if (PropsValues.USERS_UPDATE_LAST_LOGIN ||
				(user.getLastLoginDate() == null)) {

				user = UserLocalServiceUtil.updateLastLogin(
					userId, request.getRemoteAddr());
			}
		}

		if (request.getAttribute(WebKeys.USER) != null) {
			request.setAttribute(WebKeys.USER, user);
			request.setAttribute(WebKeys.USER_ID, Long.valueOf(userId));
		}

		HttpSession session = request.getSession();

		session.setAttribute(Globals.LOCALE_KEY, user.getLocale());
		session.setAttribute(WebKeys.USER, user);
		session.setAttribute(WebKeys.USER_ID, Long.valueOf(userId));

		session.removeAttribute("j_remoteuser");

		if (!user.isDefaultUser()) {
			EventsProcessorUtil.process(
				PropsKeys.LOGIN_EVENTS_POST, PropsValues.LOGIN_EVENTS_POST,
				request, response);
		}

		return userId;
	}

	protected boolean processCompanyInactiveRequest(
			HttpServletRequest request, HttpServletResponse response,
			long companyId)
		throws IOException {

		if (PortalInstances.isCompanyActive(companyId)) {
			return false;
		}

		_inactiveRequestHandler.processInactiveRequest(
			request, response,
			"this-instance-is-inactive-please-contact-the-administrator");

		return true;
	}

	protected void processGlobalShutdownEvents() throws Exception {
		EventsProcessorUtil.process(
			PropsKeys.GLOBAL_SHUTDOWN_EVENTS,
			PropsValues.GLOBAL_SHUTDOWN_EVENTS);

		super.destroy();
	}

	protected void processGlobalStartupEvents() throws Exception {
		EventsProcessorUtil.process(
			PropsKeys.GLOBAL_STARTUP_EVENTS, PropsValues.GLOBAL_STARTUP_EVENTS);
	}

	protected boolean processGroupInactiveRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, PortalException {

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid <= 0) {
			return false;
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		Group group = layout.getGroup();

		if (GroupLocalServiceUtil.isLiveGroupActive(group)) {
			return false;
		}

		_inactiveRequestHandler.processInactiveRequest(
			request, response,
			"this-site-is-inactive-please-contact-the-administrator");

		return true;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected void processInactiveRequest(
			HttpServletRequest request, HttpServletResponse response,
			String messageKey)
		throws IOException {
	}

	protected boolean processMaintenanceRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (!MaintenanceUtil.isMaintaining()) {
			return false;
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			"/html/portal/maintenance.jsp");

		requestDispatcher.include(request, response);

		return true;
	}

	protected void processServicePost(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_POST,
				PropsValues.SERVLET_SERVICE_EVENTS_POST, request, response);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected boolean processServicePre(
			HttpServletRequest request, HttpServletResponse response,
			long userId)
		throws IOException, ServletException {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, request, response);
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof NoSuchLayoutException) {
				sendError(
					HttpServletResponse.SC_NOT_FOUND, cause, request, response);

				return true;
			}
			else if (cause instanceof PrincipalException) {
				processServicePrePrincipalException(
					cause, userId, request, response);

				return true;
			}

			_log.error(e, e);

			request.setAttribute(PageContext.EXCEPTION, e);

			ServletContext servletContext = getServletContext();

			StrutsUtil.forward(
				PropsValues.SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE,
				servletContext, request, response);

			if (e == request.getAttribute(PageContext.EXCEPTION)) {
				request.removeAttribute(PageContext.EXCEPTION);
				request.removeAttribute(RequestDispatcher.ERROR_EXCEPTION);
				request.removeAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
				request.removeAttribute(RequestDispatcher.ERROR_MESSAGE);
				request.removeAttribute(RequestDispatcher.ERROR_REQUEST_URI);
				request.removeAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
				request.removeAttribute(RequestDispatcher.ERROR_STATUS_CODE);
			}

			return true;
		}

		if (_HTTP_HEADER_VERSION_VERBOSITY_DEFAULT) {
		}
		else if (_HTTP_HEADER_VERSION_VERBOSITY_PARTIAL) {
			response.addHeader(
				_LIFERAY_PORTAL_REQUEST_HEADER, ReleaseInfo.getName());
		}
		else {
			response.addHeader(
				_LIFERAY_PORTAL_REQUEST_HEADER, ReleaseInfo.getReleaseInfo());
		}

		return false;
	}

	protected void processServicePrePrincipalException(
			Throwable t, long userId, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		if ((userId > 0) ||
			(ParamUtil.getInteger(request, "p_p_lifecycle") == 2)) {

			sendError(
				HttpServletResponse.SC_UNAUTHORIZED, t, request, response);

			return;
		}

		String redirect = PortalUtil.getPathMain().concat("/portal/login");

		String currentURL = PortalUtil.getCurrentURL(request);

		redirect = HttpUtil.addParameter(redirect, "redirect", currentURL);

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid > 0) {
			try {
				redirect = HttpUtil.addParameter(redirect, "refererPlid", plid);

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				Group group = layout.getGroup();

				plid = group.getDefaultPublicPlid();

				if ((plid == LayoutConstants.DEFAULT_PLID) ||
					group.isStagingGroup()) {

					Group guestGroup = GroupLocalServiceUtil.getGroup(
						layout.getCompanyId(), GroupConstants.GUEST);

					plid = guestGroup.getDefaultPublicPlid();
				}

				redirect = HttpUtil.addParameter(redirect, "p_l_id", plid);
			}
			catch (Exception e) {
			}
		}

		response.sendRedirect(redirect);
	}

	protected boolean processShutdownRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		if (!ShutdownUtil.isShutdown()) {
			return false;
		}

		String messageKey = ShutdownUtil.getMessage();

		if (Validator.isNull(messageKey)) {
			messageKey = "the-system-is-shutdown-please-try-again-later";
		}

		_inactiveRequestHandler.processInactiveRequest(
			request, response, messageKey);

		return true;
	}

	protected void processStartupEvents() throws Exception {
		StartupAction startupAction = new StartupAction();

		startupAction.run(null);
	}

	protected void registerPortalInitialized() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("module.service.lifecycle", "portal.initialized");
		properties.put("service.vendor", ReleaseInfo.getVendor());
		properties.put("service.version", ReleaseInfo.getVersion());

		_portalInitializedModuleServiceLifecycleServiceRegistration =
			registry.registerService(
				ModuleServiceLifecycle.class, new ModuleServiceLifecycle() {},
				properties);

		properties = new HashMap<>();

		properties.put("bean.id", ServletContext.class.getName());
		properties.put("original.bean", Boolean.TRUE);
		properties.put("service.vendor", ReleaseInfo.getVendor());

		_servletContextServiceRegistration = registry.registerService(
			ServletContext.class, getServletContext(), properties);

		properties = new HashMap<>();

		properties.put("module.service.lifecycle", "system.check");
		properties.put("service.vendor", ReleaseInfo.getVendor());
		properties.put("service.version", ReleaseInfo.getVersion());

		_systemCheckModuleServiceLifecycleServiceRegistration =
			registry.registerService(
				ModuleServiceLifecycle.class, new ModuleServiceLifecycle() {},
				properties);
	}

	protected void sendError(
			int status, Throwable t, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		DynamicServletRequest dynamicRequest = new DynamicServletRequest(
			request);

		// Reset layout params or there will be an infinite loop

		dynamicRequest.setParameter("p_l_id", StringPool.BLANK);

		dynamicRequest.setParameter("groupId", StringPool.BLANK);
		dynamicRequest.setParameter("layoutId", StringPool.BLANK);
		dynamicRequest.setParameter("privateLayout", StringPool.BLANK);

		PortalUtil.sendError(status, (Exception)t, dynamicRequest, response);
	}

	protected void setPortalInetSocketAddresses(HttpServletRequest request) {
		PortalUtil.setPortalInetSocketAddresses(request);
	}

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_DEFAULT =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, ReleaseInfo.getName());

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_PARTIAL =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, "partial");

	private static final String _LIFERAY_PORTAL_REQUEST_HEADER =
		"Liferay-Portal";

	private static final Log _log = LogFactoryUtil.getLog(MainServlet.class);

	private static volatile InactiveRequestHandler _inactiveRequestHandler =
		ServiceProxyFactory.newServiceTrackedInstance(
			InactiveRequestHandler.class, MainServlet.class,
			"_inactiveRequestHandler", false);

	private ServiceRegistration<ModuleServiceLifecycle>
		_portalInitializedModuleServiceLifecycleServiceRegistration;
	private ServiceRegistration<ServletContext>
		_servletContextServiceRegistration;
	private ServiceRegistration<ModuleServiceLifecycle>
		_systemCheckModuleServiceLifecycleServiceRegistration;

}