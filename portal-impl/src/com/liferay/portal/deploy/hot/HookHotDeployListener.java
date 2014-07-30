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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.captcha.CaptchaImpl;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.InvokerAction;
import com.liferay.portal.kernel.events.InvokerSessionAction;
import com.liferay.portal.kernel.events.InvokerSimpleAction;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.format.PhoneNumberFormat;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.LockListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.PortalHookPermission;
import com.liferay.portal.kernel.servlet.DirectServletRegistryUtil;
import com.liferay.portal.kernel.servlet.LiferayFilter;
import com.liferay.portal.kernel.servlet.LiferayFilterTracker;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.servlet.TryFinallyFilter;
import com.liferay.portal.kernel.servlet.WrapHttpServletRequestFilter;
import com.liferay.portal.kernel.servlet.WrapHttpServletResponseFilter;
import com.liferay.portal.kernel.servlet.filters.invoker.FilterMapping;
import com.liferay.portal.kernel.servlet.filters.invoker.InvokerFilterConfig;
import com.liferay.portal.kernel.servlet.filters.invoker.InvokerFilterHelper;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Release;
import com.liferay.portal.repository.util.ExternalRepositoryFactory;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryImpl;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;
import com.liferay.portal.security.auth.AuthFailure;
import com.liferay.portal.security.auth.AuthToken;
import com.liferay.portal.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.security.auth.AuthVerifier;
import com.liferay.portal.security.auth.AuthVerifierPipeline;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.EmailAddressGenerator;
import com.liferay.portal.security.auth.EmailAddressValidator;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameValidator;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameValidator;
import com.liferay.portal.security.lang.DoPrivilegedBean;
import com.liferay.portal.security.ldap.AttributesTransformer;
import com.liferay.portal.security.membershippolicy.OrganizationMembershipPolicy;
import com.liferay.portal.security.membershippolicy.RoleMembershipPolicy;
import com.liferay.portal.security.membershippolicy.RoleMembershipPolicyFactoryImpl;
import com.liferay.portal.security.membershippolicy.RoleMembershipPolicyFactoryUtil;
import com.liferay.portal.security.membershippolicy.SiteMembershipPolicy;
import com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicy;
import com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicyFactoryImpl;
import com.liferay.portal.security.membershippolicy.UserGroupMembershipPolicyFactoryUtil;
import com.liferay.portal.security.pwd.Toolkit;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.service.ServiceWrapper;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.struts.AuthPublicPathRegistry;
import com.liferay.portal.util.CustomJspRegistryUtil;
import com.liferay.portal.util.JavaScriptBundleUtil;
import com.liferay.portal.util.LayoutSettings;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.assetpublisher.util.AssetEntryQueryProcessor;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScanner;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerUtil;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerWrapper;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.portlet.documentlibrary.util.DLProcessor;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.taglib.FileAvailabilityUtil;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Wesley Gong
 * @author Ryan Park
 * @author Mika Koivisto
 * @author Peter Fellwock
 * @author Raymond Augé
 */
public class HookHotDeployListener
	extends BaseHotDeployListener implements PropsKeys {

	public static final String[] SUPPORTED_PROPERTIES = {
		"admin.default.group.names", "admin.default.role.names",
		"admin.default.user.group.names",
		"asset.publisher.asset.entry.query.processors",
		"asset.publisher.display.styles",
		"asset.publisher.query.form.configuration", "auth.forward.by.last.path",
		"auth.public.paths", "auth.verifier.pipeline", "auto.deploy.listeners",
		"application.startup.events", "auth.failure", "auth.max.failures",
		"auth.token.ignore.actions", "auth.token.ignore.origins",
		"auth.token.ignore.portlets", "auth.token.impl", "auth.pipeline.post",
		"auth.pipeline.pre", "auto.login.hooks",
		"captcha.check.portal.create_account", "captcha.engine.impl",
		"company.default.locale", "company.default.time.zone",
		"company.settings.form.authentication",
		"company.settings.form.configuration",
		"company.settings.form.identification",
		"company.settings.form.miscellaneous", "company.settings.form.social",
		"control.panel.entry.class.default", "convert.processes",
		"default.landing.page.path", "default.regular.color.scheme.id",
		"default.regular.theme.id", "default.wap.color.scheme.id",
		"default.wap.theme.id", "dl.file.entry.drafts.enabled",
		"dl.file.entry.open.in.ms.office.manual.check.in.required",
		"dl.file.entry.processors", "dl.repository.impl",
		"dl.store.antivirus.impl", "dl.store.impl", "dockbar.add.portlets",
		"field.enable.com.liferay.portal.model.Contact.birthday",
		"field.enable.com.liferay.portal.model.Contact.male",
		"field.enable.com.liferay.portal.model.Organization.status",
		"hot.deploy.listeners", "javascript.fast.load",
		"journal.article.form.add", "journal.article.form.translate",
		"journal.article.form.update", "layout.form.add", "layout.form.update",
		"layout.set.form.update", "layout.static.portlets.all",
		"layout.template.cache.enabled", "layout.types",
		"layout.user.private.layouts.auto.create",
		"layout.user.private.layouts.enabled",
		"layout.user.private.layouts.power.user.required",
		"layout.user.public.layouts.auto.create",
		"layout.user.public.layouts.enabled",
		"layout.user.public.layouts.power.user.required",
		"ldap.attrs.transformer.impl", "locales", "locales.beta",
		"locales.enabled", "lock.listeners",
		"login.create.account.allow.custom.password", "login.dialog.disabled",
		"login.events.post", "login.events.pre", "login.form.navigation.post",
		"login.form.navigation.pre", "logout.events.post", "logout.events.pre",
		"mail.hook.impl", "my.sites.show.private.sites.with.no.layouts",
		"my.sites.show.public.sites.with.no.layouts",
		"my.sites.show.user.private.sites.with.no.layouts",
		"my.sites.show.user.public.sites.with.no.layouts",
		"organizations.form.add.identification", "organizations.form.add.main",
		"organizations.form.add.miscellaneous",
		"passwords.passwordpolicytoolkit.generator",
		"passwords.passwordpolicytoolkit.static", "phone.number.format.impl",
		"phone.number.format.international.regexp",
		"phone.number.format.usa.regexp",
		"portlet.add.default.resource.check.enabled",
		"portlet.add.default.resource.check.whitelist",
		"portlet.add.default.resource.check.whitelist.actions",
		"rss.feeds.enabled", "sanitizer.impl", "servlet.session.create.events",
		"servlet.session.destroy.events", "servlet.service.events.post",
		"servlet.service.events.pre", "session.max.allowed",
		"session.phishing.protected.attributes", "session.store.password",
		"sites.form.add.advanced", "sites.form.add.main",
		"sites.form.add.miscellaneous", "sites.form.add.seo",
		"sites.form.update.advanced", "sites.form.update.main",
		"sites.form.update.miscellaneous", "sites.form.update.seo",
		"social.activity.sets.bundling.enabled", "social.activity.sets.enabled",
		"social.activity.sets.selector", "social.bookmark.*",
		"terms.of.use.required", "theme.css.fast.load",
		"theme.images.fast.load", "theme.jsp.override.enabled",
		"theme.loader.new.theme.id.on.import", "theme.portlet.decorate.default",
		"theme.portlet.sharing.default", "theme.shortcut.icon", "time.zones",
		"upgrade.processes", "user.notification.event.confirmation.enabled",
		"users.email.address.generator", "users.email.address.validator",
		"users.email.address.required", "users.form.add.identification",
		"users.form.add.main", "users.form.add.miscellaneous",
		"users.form.my.account.identification", "users.form.my.account.main",
		"users.form.my.account.miscellaneous",
		"users.form.update.identification", "users.form.update.main",
		"users.form.update.miscellaneous", "users.full.name.generator",
		"users.full.name.validator", "users.image.check.token",
		"users.image.max.height", "users.image.max.width",
		"users.screen.name.always.autogenerate", "users.screen.name.generator",
		"users.screen.name.validator", "value.object.listener.*"
	};

	public HookHotDeployListener() {
		for (String key : _PROPS_VALUES_MERGE_STRING_ARRAY) {
			_mergeStringArraysContainerMap.put(
				key, new MergeStringArraysContainer(key));
		}

		for (String key : _PROPS_VALUES_OVERRIDE_STRING_ARRAY) {
			_overrideStringArraysContainerMap.put(
				key, new OverrideStringArraysContainer(key));
		}
	}

	@Override
	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeDeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error registering hook for ", t);
		}
	}

	@Override
	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeUndeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error unregistering hook for ", t);
		}
	}

	public <T> void registerService(
		String servletContextName, Object serviceRegistrationKey,
		Class<T> clazz, T service, Map<String, Object> properties) {

		Map<Object, ServiceRegistration<?>> serviceRegistrations =
			getServiceRegistrations(servletContextName);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<T> serviceRegistration = registry.registerService(
			clazz, service, properties);

		serviceRegistrations.put(serviceRegistrationKey, serviceRegistration);
	}

	public <T> void registerService(
		String servletContextName, Object serviceRegistrationKey,
		Class<T> clazz, T service, Object... propertyKVPs) {

		if ((propertyKVPs.length % 2) != 0) {
			throw new IllegalArgumentException(
				"Properties length is not an even number");
		}

		Map<String, Object> properties = new HashMap<String, Object>();

		for (int i = 0; i < propertyKVPs.length; i += 2) {
			String propertyName = String.valueOf(propertyKVPs[i]);
			Object propertyValue = propertyKVPs[i + 1];

			properties.put(propertyName, propertyValue);
		}

		registerService(
			servletContextName, serviceRegistrationKey, clazz, service,
			properties);
	}

	public <T> void registerService(
		String servletContextName, Object serviceRegistrationKey,
		Class<T> clazz, T service, Properties properties) {

		registerService(
			servletContextName, serviceRegistrationKey, clazz, service,
			PropertiesUtil.toMap(properties));
	}

	protected boolean checkPermission(
		String name, ClassLoader portletClassLoader, Object subject,
		String message) {

		try {
			PortalHookPermission.checkPermission(
				name, portletClassLoader, subject);
		}
		catch (SecurityException se) {
			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			return false;
		}

		return true;
	}

	protected boolean containsKey(Properties portalProperties, String key) {
		if (_log.isDebugEnabled()) {
			return true;
		}
		else {
			return portalProperties.containsKey(key);
		}
	}

	protected void destroyCustomJspBag(
			String servletContextName, CustomJspBag customJspBag)
		throws Exception {

		String customJspDir = customJspBag.getCustomJspDir();
		boolean customJspGlobal = customJspBag.isCustomJspGlobal();
		List<String> customJsps = customJspBag.getCustomJsps();

		String portalWebDir = PortalUtil.getPortalWebDir();

		for (String customJsp : customJsps) {
			int pos = customJsp.indexOf(customJspDir);

			String portalJsp = customJsp.substring(pos + customJspDir.length());

			if (customJspGlobal) {
				File portalJspFile = new File(portalWebDir + portalJsp);
				File portalJspBackupFile = getPortalJspBackupFile(
					portalJspFile);

				if (portalJspBackupFile.exists()) {
					FileUtil.copyFile(portalJspBackupFile, portalJspFile);

					portalJspBackupFile.delete();
				}
				else if (portalJspFile.exists()) {
					portalJspFile.delete();
				}
			}
			else {
				portalJsp = CustomJspRegistryUtil.getCustomJspFileName(
					servletContextName, portalJsp);

				File portalJspFile = new File(portalWebDir + portalJsp);

				if (portalJspFile.exists()) {
					portalJspFile.delete();
				}
			}
		}

		if (!customJspGlobal) {
			CustomJspRegistryUtil.unregisterServletContextName(
				servletContextName);
		}
	}

	protected void destroyPortalProperties(
			String servletContextName, Properties portalProperties)
		throws Exception {

		PropsUtil.removeProperties(portalProperties);

		if (_log.isDebugEnabled() && portalProperties.containsKey(LOCALES)) {
			_log.debug(
				"Portlet locales " + portalProperties.getProperty(LOCALES));
			_log.debug("Original locales " + PropsUtil.get(LOCALES));
			_log.debug(
				"Original locales array length " +
					PropsUtil.getArray(LOCALES).length);
		}

		resetPortalProperties(servletContextName, portalProperties, false);

		if (portalProperties.containsKey(
				PropsKeys.ASSET_PUBLISHER_ASSET_ENTRY_QUERY_PROCESSORS)) {

			String[] assetQueryProcessorClassNames = StringUtil.split(
				portalProperties.getProperty(
					PropsKeys.ASSET_PUBLISHER_ASSET_ENTRY_QUERY_PROCESSORS));

			for (String assetQueryProcessorClassName :
					assetQueryProcessorClassNames) {

				AssetPublisherUtil.unregisterAssetQueryProcessor(
					assetQueryProcessorClassName);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unregistered asset query processor " +
							assetQueryProcessorClassName);
				}
			}
		}

		if (portalProperties.containsKey(PropsKeys.CAPTCHA_ENGINE_IMPL)) {
			CaptchaImpl captchaImpl = null;

			Captcha captcha = CaptchaUtil.getCaptcha();

			if (captcha instanceof DoPrivilegedBean) {
				DoPrivilegedBean doPrivilegedBean = (DoPrivilegedBean)captcha;

				captchaImpl = (CaptchaImpl)doPrivilegedBean.getActualBean();
			}
			else {
				captchaImpl = (CaptchaImpl)captcha;
			}

			captchaImpl.setCaptcha(null);
		}

		if (portalProperties.containsKey(PropsKeys.DL_FILE_ENTRY_PROCESSORS)) {
			DLFileEntryProcessorContainer dlFileEntryProcessorContainer =
				_dlFileEntryProcessorContainerMap.remove(servletContextName);

			dlFileEntryProcessorContainer.unregisterDLProcessors();
		}

		if (portalProperties.containsKey(PropsKeys.DL_REPOSITORY_IMPL)) {
			DLRepositoryContainer dlRepositoryContainer =
				_dlRepositoryContainerMap.remove(servletContextName);

			dlRepositoryContainer.unregisterRepositoryFactories();
		}

		if (portalProperties.containsKey(PropsKeys.DL_STORE_ANTIVIRUS_IMPL)) {
			AntivirusScannerWrapper antivirusScannerWrapper =
				(AntivirusScannerWrapper)
					AntivirusScannerUtil.getAntivirusScanner();

			antivirusScannerWrapper.setAntivirusScanner(null);
		}

		if (portalProperties.containsKey(PropsKeys.DL_STORE_IMPL)) {
			StoreFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.MEMBERSHIP_POLICY_ROLES)) {
			RoleMembershipPolicyFactoryImpl roleMembershipPolicyFactoryImpl =
				(RoleMembershipPolicyFactoryImpl)
					RoleMembershipPolicyFactoryUtil.
						getRoleMembershipPolicyFactory();

			roleMembershipPolicyFactoryImpl.setRoleMembershipPolicy(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.MEMBERSHIP_POLICY_USER_GROUPS)) {

			UserGroupMembershipPolicyFactoryImpl
				userGroupMembershipPolicyFactoryImpl =
					(UserGroupMembershipPolicyFactoryImpl)
						UserGroupMembershipPolicyFactoryUtil.
							getUserGroupMembershipPolicyFactory();

			userGroupMembershipPolicyFactoryImpl.setUserGroupMembershipPolicy(
				null);
		}

		Set<String> liferayFilterClassNames =
			LiferayFilterTracker.getClassNames();

		for (String liferayFilterClassName : liferayFilterClassNames) {
			if (!portalProperties.containsKey(liferayFilterClassName)) {
				continue;
			}

			boolean filterEnabled = GetterUtil.getBoolean(
				PropsUtil.get(liferayFilterClassName));

			Set<LiferayFilter> liferayFilters =
				LiferayFilterTracker.getLiferayFilters(liferayFilterClassName);

			for (LiferayFilter liferayFilter : liferayFilters) {
				liferayFilter.setFilterEnabled(filterEnabled);
			}
		}
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String xml = HttpUtil.URLtoString(
			servletContext.getResource("/WEB-INF/liferay-hook.xml"));

		if (xml == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registering hook for " + servletContextName);
		}

		_servletContextNames.add(servletContextName);

		Document document = SAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		ClassLoader portletClassLoader = hotDeployEvent.getContextClassLoader();

		initPortalProperties(
			servletContextName, portletClassLoader, rootElement);

		initLanguageProperties(
			servletContextName, portletClassLoader, rootElement);

		initCustomJspDir(
			servletContext, servletContextName, portletClassLoader,
			hotDeployEvent.getPluginPackage(), rootElement);

		initIndexerPostProcessors(
			servletContextName, portletClassLoader, rootElement);

		initServices(servletContextName, portletClassLoader, rootElement);

		initServletFilters(
			servletContext, servletContextName, portletClassLoader,
			rootElement);

		initStrutsActions(servletContextName, portletClassLoader, rootElement);

		// Begin backwards compatibility for 5.1.0

		ModelListenersContainer modelListenersContainer =
			_modelListenersContainerMap.get(servletContextName);

		if (modelListenersContainer == null) {
			modelListenersContainer = new ModelListenersContainer();

			_modelListenersContainerMap.put(
				servletContextName, modelListenersContainer);
		}

		List<Element> modelListenerElements = rootElement.elements(
			"model-listener");

		for (Element modelListenerElement : modelListenerElements) {
			String modelName = modelListenerElement.elementText("model-name");
			String modelListenerClassName = modelListenerElement.elementText(
				"model-listener-class");

			ModelListener<BaseModel<?>> modelListener = initModelListener(
				servletContextName, portletClassLoader, modelName,
				modelListenerClassName);

			if (modelListener != null) {
				modelListenersContainer.registerModelListener(
					modelName, modelListener);
			}
		}

		List<Element> eventElements = rootElement.elements("event");

		for (Element eventElement : eventElements) {
			String eventName = eventElement.elementText("event-type");
			String eventClassName = eventElement.elementText("event-class");

			initEvent(
				servletContextName, portletClassLoader, eventName,
				eventClassName);
		}

		// End backwards compatibility for 5.1.0

		registerClpMessageListeners(servletContext, portletClassLoader);

		DirectServletRegistryUtil.clearServlets();
		FileAvailabilityUtil.reset();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Hook for " + servletContextName + " is available for use");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		if (!_servletContextNames.remove(servletContextName)) {
			return;
		}

		AuthPublicPathsContainer authPublicPathsContainer =
			_authPublicPathsContainerMap.remove(servletContextName);

		if (authPublicPathsContainer != null) {
			authPublicPathsContainer.unregisterPaths();
		}

		CustomJspBag customJspBag = _customJspBagsMap.remove(
			servletContextName);

		if (customJspBag != null) {
			destroyCustomJspBag(servletContextName, customJspBag);
		}

		HotDeployListenersContainer hotDeployListenersContainer =
			_hotDeployListenersContainerMap.remove(servletContextName);

		if (hotDeployListenersContainer != null) {
			hotDeployListenersContainer.unregisterHotDeployListeners();
		}

		LanguagesContainer languagesContainer = _languagesContainerMap.remove(
			servletContextName);

		if (languagesContainer != null) {
			languagesContainer.unregisterLanguages();
		}

		ModelListenersContainer modelListenersContainer =
			_modelListenersContainerMap.remove(servletContextName);

		if (modelListenersContainer != null) {
			modelListenersContainer.unregisterModelListeners(
				servletContextName);
		}

		Properties portalProperties = _portalPropertiesMap.remove(
			servletContextName);

		if (portalProperties != null) {
			destroyPortalProperties(servletContextName, portalProperties);
		}

		ServletFiltersContainer servletFiltersContainer =
			_servletFiltersContainerMap.remove(servletContextName);

		if (servletFiltersContainer != null) {
			servletFiltersContainer.unregisterFilterMappings();
		}

		unregisterClpMessageListeners(servletContext);

		Map<Object, ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrations.remove(servletContextName);

		if (serviceRegistrations != null) {
			for (ServiceRegistration<?> serviceRegistration :
					serviceRegistrations.values()) {

				serviceRegistration.unregister();
			}

			serviceRegistrations.clear();
		}

		if (_log.isInfoEnabled()) {
			_log.info("Hook for " + servletContextName + " was unregistered");
		}
	}

	protected void getCustomJsps(
		ServletContext servletContext, String webDir, String resourcePath,
		List<String> customJsps) {

		Set<String> resourcePaths = servletContext.getResourcePaths(
			resourcePath);

		if ((resourcePaths == null) || resourcePaths.isEmpty()) {
			return;
		}

		for (String curResourcePath : resourcePaths) {
			if (curResourcePath.endsWith(StringPool.SLASH)) {
				getCustomJsps(
					servletContext, webDir, curResourcePath, customJsps);
			}
			else {
				String customJsp = webDir + curResourcePath;

				customJsp = StringUtil.replace(
					customJsp, StringPool.DOUBLE_SLASH, StringPool.SLASH);

				customJsps.add(customJsp);
			}
		}
	}

	protected Locale getLocale(String languagePropertiesLocation) {
		int x = languagePropertiesLocation.indexOf(CharPool.UNDERLINE);
		int y = languagePropertiesLocation.indexOf(".properties");

		Locale locale = null;

		if ((x != -1) && (y != 1)) {
			String localeKey = languagePropertiesLocation.substring(x + 1, y);

			locale = LocaleUtil.fromLanguageId(localeKey, true, false);
		}

		return locale;
	}

	protected BasePersistence<?> getPersistence(
		String servletContextName, String modelName) {

		int pos = modelName.lastIndexOf(CharPool.PERIOD);

		String entityName = modelName.substring(pos + 1);

		pos = modelName.lastIndexOf(".model.");

		String packagePath = modelName.substring(0, pos);

		String beanName =
			packagePath + ".service.persistence." + entityName + "Persistence";

		try {
			return (BasePersistence<?>)PortalBeanLocatorUtil.locate(beanName);
		}
		catch (BeanLocatorException ble) {
			return (BasePersistence<?>)PortletBeanLocatorUtil.locate(
				servletContextName, beanName);
		}
	}

	protected File getPortalJspBackupFile(File portalJspFile) {
		String fileName = portalJspFile.getName();
		String filePath = portalJspFile.toString();

		int fileNameIndex = fileName.lastIndexOf(CharPool.PERIOD);

		if (fileNameIndex > 0) {
			int filePathIndex = filePath.lastIndexOf(fileName);

			fileName =
				fileName.substring(0, fileNameIndex) + ".portal" +
					fileName.substring(fileNameIndex);

			filePath = filePath.substring(0, filePathIndex) + fileName;
		}
		else {
			filePath += ".portal";
		}

		return new File(filePath);
	}

	protected Map<Object, ServiceRegistration<?>> getServiceRegistrations(
		String servletContextName) {

		Map<Object, ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrations.get(servletContextName);

		if (serviceRegistrations == null) {
			serviceRegistrations = newMap();

			_serviceRegistrations.put(servletContextName, serviceRegistrations);
		}

		return serviceRegistrations;
	}

	protected void initAuthenticators(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		initAuthenticators(
			servletContextName, portletClassLoader, portalProperties,
			AUTH_PIPELINE_PRE);
		initAuthenticators(
			servletContextName, portletClassLoader, portalProperties,
			AUTH_PIPELINE_POST);
	}

	protected void initAuthenticators(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties, String key)
		throws Exception {

		String[] authenticatorClassNames = StringUtil.split(
			portalProperties.getProperty(key));

		for (String authenticatorClassName : authenticatorClassNames) {
			Authenticator authenticator = (Authenticator)newInstance(
				portletClassLoader, Authenticator.class,
				authenticatorClassName);

			registerService(
				servletContextName, authenticatorClassName, Authenticator.class,
				authenticator, "key", key);
		}
	}

	protected void initAuthFailures(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		initAuthFailures(
			servletContextName, portletClassLoader, portalProperties,
			AUTH_FAILURE);
		initAuthFailures(
			servletContextName, portletClassLoader, portalProperties,
			AUTH_MAX_FAILURES);
	}

	protected void initAuthFailures(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties, String key)
		throws Exception {

		String[] authFailureClassNames = StringUtil.split(
			portalProperties.getProperty(key));

		for (String authFailureClassName : authFailureClassNames) {
			AuthFailure authFailure = (AuthFailure)newInstance(
				portletClassLoader, AuthFailure.class, authFailureClassName);

			registerService(
				servletContextName, authFailureClassName, AuthFailure.class,
				authFailure, "key", key);
		}
	}

	protected void initAuthPublicPaths(
			String servletContextName, Properties portalProperties)
		throws Exception {

		AuthPublicPathsContainer authPublicPathsContainer =
			new AuthPublicPathsContainer();

		_authPublicPathsContainerMap.put(
			servletContextName, authPublicPathsContainer);

		String[] publicPaths = StringUtil.split(
			portalProperties.getProperty(AUTH_PUBLIC_PATHS));

		authPublicPathsContainer.registerPaths(publicPaths);
	}

	protected void initAuthVerifiers(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		String[] authVerifierClassNames = StringUtil.split(
			portalProperties.getProperty(AUTH_VERIFIER_PIPELINE));

		for (String authVerifierClassName : authVerifierClassNames) {
			AuthVerifier authVerifier = (AuthVerifier)newInstance(
				portletClassLoader, AuthVerifier.class, authVerifierClassName);

			Properties properties = PropertiesUtil.getProperties(
				portalProperties,
				AuthVerifierPipeline.getAuthVerifierPropertyName(
					authVerifierClassName),
				true);

			registerService(
				servletContextName, authVerifierClassName, AuthVerifier.class,
				authVerifier, properties);
		}
	}

	protected void initAutoDeployListeners(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		String[] autoDeployListenerClassNames = StringUtil.split(
			portalProperties.getProperty(PropsKeys.AUTO_DEPLOY_LISTENERS));

		if (autoDeployListenerClassNames.length == 0) {
			return;
		}

		for (String autoDeployListenerClassName :
				autoDeployListenerClassNames) {

			AutoDeployListener autoDeployListener =
				(AutoDeployListener)newInstance(
					portletClassLoader, AutoDeployListener.class,
					autoDeployListenerClassName);

			registerService(
				servletContextName, autoDeployListenerClassName,
				AutoDeployListener.class, autoDeployListener);
		}
	}

	protected void initAutoLogins(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		String[] autoLoginClassNames = StringUtil.split(
			portalProperties.getProperty(AUTO_LOGIN_HOOKS));

		for (String autoLoginClassName : autoLoginClassNames) {
			AutoLogin autoLogin = (AutoLogin)newInstance(
				portletClassLoader, AutoLogin.class, autoLoginClassName);

			registerService(
				servletContextName, autoLoginClassName, AutoLogin.class,
				autoLogin);
		}
	}

	protected void initCustomJspBag(
			String servletContextName, String displayName,
			CustomJspBag customJspBag)
		throws Exception {

		String customJspDir = customJspBag.getCustomJspDir();
		boolean customJspGlobal = customJspBag.isCustomJspGlobal();
		List<String> customJsps = customJspBag.getCustomJsps();

		String portalWebDir = PortalUtil.getPortalWebDir();

		for (String customJsp : customJsps) {
			int pos = customJsp.indexOf(customJspDir);

			String portalJsp = customJsp.substring(pos + customJspDir.length());

			if (customJspGlobal) {
				File portalJspFile = new File(portalWebDir + portalJsp);
				File portalJspBackupFile = getPortalJspBackupFile(
					portalJspFile);

				if (portalJspFile.exists() && !portalJspBackupFile.exists()) {
					FileUtil.copyFile(portalJspFile, portalJspBackupFile);
				}
			}
			else {
				portalJsp = CustomJspRegistryUtil.getCustomJspFileName(
					servletContextName, portalJsp);
			}

			FileUtil.copyFile(customJsp, portalWebDir + portalJsp);
		}

		if (!customJspGlobal) {
			CustomJspRegistryUtil.registerServletContextName(
				servletContextName, displayName);
		}
	}

	protected void initCustomJspDir(
			ServletContext servletContext, String servletContextName,
			ClassLoader portletClassLoader, PluginPackage pluginPackage,
			Element rootElement)
		throws Exception {

		String customJspDir = rootElement.elementText("custom-jsp-dir");

		if (Validator.isNull(customJspDir)) {
			return;
		}

		if (!checkPermission(
				PACLConstants.PORTAL_HOOK_PERMISSION_CUSTOM_JSP_DIR,
				portletClassLoader, null, "Rejecting custom JSP directory")) {

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Custom JSP directory: " + customJspDir);
		}

		boolean customJspGlobal = GetterUtil.getBoolean(
			rootElement.elementText("custom-jsp-global"), true);

		List<String> customJsps = new ArrayList<String>();

		String webDir = servletContext.getRealPath(StringPool.SLASH);

		getCustomJsps(servletContext, webDir, customJspDir, customJsps);

		if (customJsps.isEmpty()) {
			return;
		}

		CustomJspBag customJspBag = new CustomJspBag(
			customJspDir, customJspGlobal, customJsps);

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(customJsps.size() * 2 + 1);

			sb.append("Custom JSP files:\n");

			for (String customJsp : customJsps) {
				sb.append(customJsp);
				sb.append(StringPool.NEW_LINE);
			}

			sb.setIndex(sb.index() - 1);

			_log.debug(sb.toString());
		}

		_customJspBagsMap.put(servletContextName, customJspBag);

		initCustomJspBag(
			servletContextName, pluginPackage.getName(), customJspBag);
	}

	protected void initEvent(
			String servletContextName, ClassLoader portletClassLoader,
			String eventName, String eventClassName)
		throws Exception {

		if (eventName.equals(APPLICATION_STARTUP_EVENTS)) {
			Class<?> clazz = portletClassLoader.loadClass(eventClassName);

			SimpleAction simpleAction = (SimpleAction)clazz.newInstance();

			simpleAction = new InvokerSimpleAction(
				simpleAction, portletClassLoader);

			Long companyId = CompanyThreadLocal.getCompanyId();

			try {
				long[] companyIds = PortalInstances.getCompanyIds();

				for (long curCompanyId : companyIds) {
					CompanyThreadLocal.setCompanyId(curCompanyId);

					simpleAction.run(
						new String[] {String.valueOf(curCompanyId)});
				}
			}
			finally {
				CompanyThreadLocal.setCompanyId(companyId);
			}
		}

		if (_propsKeysEvents.contains(eventName)) {
			Class<?> clazz = portletClassLoader.loadClass(eventClassName);

			Action action = (Action)clazz.newInstance();

			action = new InvokerAction(action, portletClassLoader);

			registerService(
				servletContextName, eventClassName, LifecycleAction.class,
				action, "key", eventName);
		}

		if (_propsKeysSessionEvents.contains(eventName)) {
			Class<?> clazz = portletClassLoader.loadClass(eventClassName);

			SessionAction sessionAction = (SessionAction)clazz.newInstance();

			sessionAction = new InvokerSessionAction(
				sessionAction, portletClassLoader);

			registerService(
				servletContextName, eventClassName, LifecycleAction.class,
				sessionAction, "key", eventName);
		}
	}

	protected void initEvents(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		for (Map.Entry<Object, Object> entry : portalProperties.entrySet()) {
			String key = (String)entry.getKey();

			if (!key.equals(APPLICATION_STARTUP_EVENTS) &&
				!_propsKeysEvents.contains(key) &&
				!_propsKeysSessionEvents.contains(key)) {

				continue;
			}

			String eventName = key;
			String[] eventClassNames = StringUtil.split(
				(String)entry.getValue());

			for (String eventClassName : eventClassNames) {
				initEvent(
					servletContextName, portletClassLoader, eventName,
					eventClassName);
			}
		}
	}

	protected void initHotDeployListeners(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		String[] hotDeployListenerClassNames = StringUtil.split(
			portalProperties.getProperty(PropsKeys.HOT_DEPLOY_LISTENERS));

		if (hotDeployListenerClassNames.length == 0) {
			return;
		}

		HotDeployListenersContainer hotDeployListenersContainer =
			new HotDeployListenersContainer();

		_hotDeployListenersContainerMap.put(
			servletContextName, hotDeployListenersContainer);

		for (String hotDeployListenerClassName : hotDeployListenerClassNames) {
			HotDeployListener hotDeployListener =
				(HotDeployListener)newInstance(
					portletClassLoader, HotDeployListener.class,
					hotDeployListenerClassName);

			hotDeployListenersContainer.registerHotDeployListener(
				hotDeployListener);
		}
	}

	protected void initIndexerPostProcessors(
			String servletContextName, ClassLoader portletClassLoader,
			Element parentElement)
		throws Exception {

		List<Element> indexerPostProcessorElements = parentElement.elements(
			"indexer-post-processor");

		for (Element indexerPostProcessorElement :
				indexerPostProcessorElements) {

			String indexerClassName = indexerPostProcessorElement.elementText(
				"indexer-class-name");

			if (!checkPermission(
					PACLConstants.PORTAL_HOOK_PERMISSION_INDEXER,
					portletClassLoader, indexerClassName,
					"Rejecting indexer " + indexerClassName)) {

				continue;
			}

			String indexerPostProcessorImpl =
				indexerPostProcessorElement.elementText(
					"indexer-post-processor-impl");

			IndexerPostProcessor indexerPostProcessor =
				(IndexerPostProcessor)InstanceFactory.newInstance(
					portletClassLoader, indexerPostProcessorImpl);

			registerService(
				servletContextName, indexerPostProcessorImpl,
				IndexerPostProcessor.class, indexerPostProcessor,
				"indexer.class.name", indexerClassName);
		}
	}

	protected void initLanguageProperties(
			String servletContextName, ClassLoader portletClassLoader,
			Element parentElement)
		throws Exception {

		LanguagesContainer languagesContainer = new LanguagesContainer();

		_languagesContainerMap.put(servletContextName, languagesContainer);

		List<Element> languagePropertiesElements = parentElement.elements(
			"language-properties");

		Map<String, String> baseLanguageMap = null;

		for (Element languagePropertiesElement : languagePropertiesElements) {
			Properties properties = null;

			String languagePropertiesLocation =
				languagePropertiesElement.getText();

			Locale locale = getLocale(languagePropertiesLocation);

			if (locale != null) {
				if (!checkPermission(
						PACLConstants.
							PORTAL_HOOK_PERMISSION_LANGUAGE_PROPERTIES_LOCALE,
						portletClassLoader, locale,
						"Rejecting locale " + locale)) {

					continue;
				}
			}

			try {
				URL url = portletClassLoader.getResource(
					languagePropertiesLocation);

				if (url == null) {
					continue;
				}

				InputStream is = url.openStream();

				properties = PropertiesUtil.load(is, StringPool.UTF8);

				is.close();
			}
			catch (Exception e) {
				_log.error("Unable to read " + languagePropertiesLocation, e);

				continue;
			}

			Map<String, String> languageMap = new HashMap<String, String>();

			if (baseLanguageMap != null) {
				languageMap.putAll(baseLanguageMap);
			}

			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = (String)entry.getKey();
				String value = (String)entry.getValue();

				value = LanguageResources.fixValue(value);

				languageMap.put(key, value);
			}

			if (locale != null) {
				languagesContainer.addLanguage(locale, languageMap);
			}
			else if (!languageMap.isEmpty()) {
				baseLanguageMap = languageMap;
			}
		}

		if (baseLanguageMap != null) {
			Locale locale = new Locale(StringPool.BLANK);

			languagesContainer.addLanguage(locale, baseLanguageMap);
		}
	}

	@SuppressWarnings("rawtypes")
	protected ModelListener<BaseModel<?>> initModelListener(
			String servletContextName, ClassLoader portletClassLoader,
			String modelName, String modelListenerClassName)
		throws Exception {

		ModelListener<BaseModel<?>> modelListener =
			(ModelListener<BaseModel<?>>)newInstance(
				portletClassLoader, ModelListener.class,
				modelListenerClassName);

		BasePersistence persistence = getPersistence(
			servletContextName, modelName);

		persistence.registerListener(modelListener);

		return modelListener;
	}

	protected void initModelListeners(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		ModelListenersContainer modelListenersContainer =
			new ModelListenersContainer();

		_modelListenersContainerMap.put(
			servletContextName, modelListenersContainer);

		for (Map.Entry<Object, Object> entry : portalProperties.entrySet()) {
			String key = (String)entry.getKey();

			if (!key.startsWith(VALUE_OBJECT_LISTENER)) {
				continue;
			}

			String modelName = key.substring(VALUE_OBJECT_LISTENER.length());

			String[] modelListenerClassNames = StringUtil.split(
				(String)entry.getValue());

			for (String modelListenerClassName : modelListenerClassNames) {
				ModelListener<BaseModel<?>> modelListener = initModelListener(
					servletContextName, portletClassLoader, modelName,
					modelListenerClassName);

				if (modelListener != null) {
					modelListenersContainer.registerModelListener(
						modelName, modelListener);
				}
			}
		}
	}

	protected void initPortalProperties(
			String servletContextName, ClassLoader portletClassLoader,
			Element parentElement)
		throws Exception {

		String portalPropertiesLocation = parentElement.elementText(
			"portal-properties");

		if (Validator.isNull(portalPropertiesLocation)) {
			return;
		}

		Configuration portalPropertiesConfiguration = null;

		try {
			String name = portalPropertiesLocation;

			int pos = name.lastIndexOf(".properties");

			if (pos != -1) {
				name = name.substring(0, pos);
			}

			portalPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					portletClassLoader, name);
		}
		catch (Exception e) {
			_log.error("Unable to read " + portalPropertiesLocation, e);
		}

		if (portalPropertiesConfiguration == null) {
			return;
		}

		Properties portalProperties =
			portalPropertiesConfiguration.getProperties();

		if (portalProperties.isEmpty()) {
			return;
		}

		Set<Object> set = portalProperties.keySet();

		Iterator<Object> iterator = set.iterator();

		while (iterator.hasNext()) {
			String key = (String)iterator.next();

			if (!checkPermission(
					PACLConstants.PORTAL_HOOK_PERMISSION_PORTAL_PROPERTIES_KEY,
					portletClassLoader, key,
					"Rejecting portal.properties key " + key)) {

				iterator.remove();
			}
		}

		Properties unfilteredPortalProperties =
			(Properties)portalProperties.clone();

		portalProperties.remove(PropsKeys.RELEASE_INFO_BUILD_NUMBER);
		portalProperties.remove(PropsKeys.RELEASE_INFO_PREVIOUS_BUILD_NUMBER);
		portalProperties.remove(PropsKeys.UPGRADE_PROCESSES);

		_portalPropertiesMap.put(servletContextName, portalProperties);

		// Initialize properties, auto logins, model listeners, and events in
		// that specific order. Events have to be loaded last because they may
		// require model listeners to have been registered.

		initPortalProperties(
			servletContextName, portletClassLoader, portalProperties,
			unfilteredPortalProperties);
		initAuthFailures(
			servletContextName, portletClassLoader, portalProperties);
		initAutoDeployListeners(
			servletContextName, portletClassLoader, portalProperties);
		initAutoLogins(
			servletContextName, portletClassLoader, portalProperties);
		initAuthenticators(
			servletContextName, portletClassLoader, portalProperties);
		initAuthVerifiers(
			servletContextName, portletClassLoader, portalProperties);
		initHotDeployListeners(
			servletContextName, portletClassLoader, portalProperties);
		initModelListeners(
			servletContextName, portletClassLoader, portalProperties);
		initEvents(servletContextName, portletClassLoader, portalProperties);
	}

	protected void initPortalProperties(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties, Properties unfilteredPortalProperties)
		throws Exception {

		PropsUtil.addProperties(portalProperties);

		if (_log.isDebugEnabled() && portalProperties.containsKey(LOCALES)) {
			_log.debug(
				"Portlet locales " + portalProperties.getProperty(LOCALES));
			_log.debug("Merged locales " + PropsUtil.get(LOCALES));
			_log.debug(
				"Merged locales array length " +
					PropsUtil.getArray(LOCALES).length);
		}

		for (String key : _PROPS_VALUES_OBSOLETE) {
			if (_log.isInfoEnabled() && portalProperties.contains(key)) {
				_log.info("Portal property \"" + key + "\" is obsolete");
			}
		}

		resetPortalProperties(servletContextName, portalProperties, true);

		if (portalProperties.containsKey(
				PropsKeys.ASSET_PUBLISHER_ASSET_ENTRY_QUERY_PROCESSORS)) {

			String[] assetQueryProcessorClassNames = StringUtil.split(
				portalProperties.getProperty(
					PropsKeys.ASSET_PUBLISHER_ASSET_ENTRY_QUERY_PROCESSORS));

			for (String assetQueryProcessorClassName :
					assetQueryProcessorClassNames) {

				AssetEntryQueryProcessor assetQueryProcessor =
					(AssetEntryQueryProcessor)newInstance(
						portletClassLoader, AssetEntryQueryProcessor.class,
						assetQueryProcessorClassName);

				AssetPublisherUtil.registerAssetQueryProcessor(
					assetQueryProcessorClassName, assetQueryProcessor);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Registered asset query processor " +
							assetQueryProcessorClassName);
				}
			}
		}

		if (portalProperties.containsKey(PropsKeys.AUTH_PUBLIC_PATHS)) {
			initAuthPublicPaths(servletContextName, portalProperties);
		}

		if (portalProperties.containsKey(PropsKeys.AUTH_TOKEN_IMPL)) {
			String authTokenClassName = portalProperties.getProperty(
				PropsKeys.AUTH_TOKEN_IMPL);

			AuthToken authToken = (AuthToken)newInstance(
				portletClassLoader, AuthToken.class, authTokenClassName);

			registerService(
				servletContextName, authTokenClassName, AuthToken.class,
				authToken);
		}

		if (portalProperties.containsKey(PropsKeys.CAPTCHA_ENGINE_IMPL)) {
			String captchaClassName = portalProperties.getProperty(
				PropsKeys.CAPTCHA_ENGINE_IMPL);

			Captcha captcha = (Captcha)newInstance(
				portletClassLoader, Captcha.class, captchaClassName);

			CaptchaImpl captchaImpl = null;

			Captcha currentCaptcha = CaptchaUtil.getCaptcha();

			if (currentCaptcha instanceof DoPrivilegedBean) {
				DoPrivilegedBean doPrivilegedBean =
					(DoPrivilegedBean)currentCaptcha;

				captchaImpl = (CaptchaImpl)doPrivilegedBean.getActualBean();
			}
			else {
				captchaImpl = (CaptchaImpl)currentCaptcha;
			}

			captchaImpl.setCaptcha(captcha);
		}

		if (portalProperties.containsKey(
				PropsKeys.CONTROL_PANEL_DEFAULT_ENTRY_CLASS)) {

			String controlPanelEntryClassName = portalProperties.getProperty(
				PropsKeys.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);

			ControlPanelEntry controlPanelEntry =
				(ControlPanelEntry)newInstance(
					portletClassLoader, ControlPanelEntry.class,
					controlPanelEntryClassName);

			registerService(
				servletContextName, controlPanelEntryClassName,
				ControlPanelEntry.class, controlPanelEntry);
		}

		if (portalProperties.containsKey(PropsKeys.DL_FILE_ENTRY_PROCESSORS)) {
			String[] dlProcessorClassNames = StringUtil.split(
				portalProperties.getProperty(
					PropsKeys.DL_FILE_ENTRY_PROCESSORS));

			DLFileEntryProcessorContainer dlFileEntryProcessorContainer =
				new DLFileEntryProcessorContainer();

			_dlFileEntryProcessorContainerMap.put(
				servletContextName, dlFileEntryProcessorContainer);

			for (String dlProcessorClassName : dlProcessorClassNames) {
				DLProcessor dlProcessor = (DLProcessor)newInstance(
					portletClassLoader, DLProcessor.class,
					dlProcessorClassName);

				dlFileEntryProcessorContainer.registerDLProcessor(dlProcessor);
			}
		}

		if (portalProperties.containsKey(PropsKeys.DL_REPOSITORY_IMPL)) {
			String[] dlRepositoryClassNames = StringUtil.split(
				portalProperties.getProperty(PropsKeys.DL_REPOSITORY_IMPL));

			DLRepositoryContainer dlRepositoryContainer =
				new DLRepositoryContainer();

			_dlRepositoryContainerMap.put(
				servletContextName, dlRepositoryContainer);

			for (String dlRepositoryClassName : dlRepositoryClassNames) {
				ExternalRepositoryFactory externalRepositoryFactory =
					new ExternalRepositoryFactoryImpl(
						dlRepositoryClassName, portletClassLoader);

				dlRepositoryContainer.registerRepositoryFactory(
					dlRepositoryClassName, externalRepositoryFactory);
			}
		}

		if (portalProperties.containsKey(PropsKeys.DL_STORE_ANTIVIRUS_IMPL)) {
			String antivirusScannerClassName = portalProperties.getProperty(
				PropsKeys.DL_STORE_ANTIVIRUS_IMPL);

			AntivirusScanner antivirusScanner = (AntivirusScanner)newInstance(
				portletClassLoader, AntivirusScanner.class,
				antivirusScannerClassName);

			AntivirusScannerWrapper antivirusScannerWrapper =
				(AntivirusScannerWrapper)
					AntivirusScannerUtil.getAntivirusScanner();

			antivirusScannerWrapper.setAntivirusScanner(antivirusScanner);
		}

		if (portalProperties.containsKey(PropsKeys.DL_STORE_IMPL)) {
			String storeClassName = portalProperties.getProperty(
				PropsKeys.DL_STORE_IMPL);

			Store store = (Store)newInstance(
				portletClassLoader, Store.class, storeClassName);

			StoreFactory.setInstance(store);
		}

		if (portalProperties.containsKey(
				PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL)) {

			String attributesTransformerClassName =
				portalProperties.getProperty(
					PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL);

			AttributesTransformer attributesTransformer =
				(AttributesTransformer)newInstance(
					portletClassLoader, AttributesTransformer.class,
					attributesTransformerClassName);

			registerService(
				servletContextName, attributesTransformerClassName,
				AttributesTransformer.class, attributesTransformer);
		}

		if (portalProperties.containsKey(LOCK_LISTENERS)) {
			String[] lockListenerClassNames = StringUtil.split(
				portalProperties.getProperty(LOCK_LISTENERS));

			for (String lockListenerClassName : lockListenerClassNames) {
				LockListener lockListener = (LockListener)newInstance(
					portletClassLoader, LockListener.class,
					lockListenerClassName);

				registerService(
					servletContextName, lockListenerClassName,
					LockListener.class, lockListener);
			}
		}

		if (portalProperties.containsKey(PropsKeys.MAIL_HOOK_IMPL)) {
			String mailHookClassName = portalProperties.getProperty(
				PropsKeys.MAIL_HOOK_IMPL);

			com.liferay.mail.util.Hook mailHook =
				(com.liferay.mail.util.Hook)newInstance(
					portletClassLoader, com.liferay.mail.util.Hook.class,
					mailHookClassName);

			registerService(
				servletContextName, mailHookClassName,
				com.liferay.mail.util.Hook.class, mailHook);
		}

		if (portalProperties.containsKey(
				PropsKeys.MEMBERSHIP_POLICY_ORGANIZATIONS)) {

			String organizationMembershipPolicyClassName =
				portalProperties.getProperty(
					PropsKeys.MEMBERSHIP_POLICY_ORGANIZATIONS);

			OrganizationMembershipPolicy organizationMembershipPolicy =
				(OrganizationMembershipPolicy)newInstance(
					portletClassLoader, OrganizationMembershipPolicy.class,
					organizationMembershipPolicyClassName);

			registerService(
				servletContextName, organizationMembershipPolicyClassName,
				OrganizationMembershipPolicy.class,
				organizationMembershipPolicy);
		}

		if (portalProperties.containsKey(PropsKeys.MEMBERSHIP_POLICY_ROLES)) {
			String roleMembershipPolicyClassName = portalProperties.getProperty(
				PropsKeys.MEMBERSHIP_POLICY_ROLES);

			RoleMembershipPolicyFactoryImpl roleMembershipPolicyFactoryImpl =
				(RoleMembershipPolicyFactoryImpl)
					RoleMembershipPolicyFactoryUtil.
						getRoleMembershipPolicyFactory();

			RoleMembershipPolicy roleMembershipPolicy =
				(RoleMembershipPolicy)newInstance(
					portletClassLoader, RoleMembershipPolicy.class,
					roleMembershipPolicyClassName);

			roleMembershipPolicyFactoryImpl.setRoleMembershipPolicy(
				roleMembershipPolicy);

			if (PropsValues.MEMBERSHIP_POLICY_AUTO_VERIFY) {
				roleMembershipPolicy.verifyPolicy();
			}
		}

		if (portalProperties.containsKey(PropsKeys.MEMBERSHIP_POLICY_SITES)) {
			String siteMembershipPolicyClassName = portalProperties.getProperty(
				PropsKeys.MEMBERSHIP_POLICY_SITES);

			SiteMembershipPolicy siteMembershipPolicy =
				(SiteMembershipPolicy)newInstance(
					portletClassLoader, SiteMembershipPolicy.class,
					siteMembershipPolicyClassName);

			registerService(
				servletContextName, siteMembershipPolicyClassName,
				SiteMembershipPolicy.class, siteMembershipPolicy);
		}

		if (portalProperties.containsKey(
				PropsKeys.MEMBERSHIP_POLICY_USER_GROUPS)) {

			String userGroupMembershipPolicyClassName =
				portalProperties.getProperty(
					PropsKeys.MEMBERSHIP_POLICY_USER_GROUPS);

			UserGroupMembershipPolicyFactoryImpl
				userGroupMembershipPolicyFactoryImpl =
					(UserGroupMembershipPolicyFactoryImpl)
						UserGroupMembershipPolicyFactoryUtil.
							getUserGroupMembershipPolicyFactory();

			UserGroupMembershipPolicy userGroupMembershipPolicy =
				(UserGroupMembershipPolicy)newInstance(
					portletClassLoader, UserGroupMembershipPolicy.class,
					userGroupMembershipPolicyClassName);

			userGroupMembershipPolicyFactoryImpl.setUserGroupMembershipPolicy(
				userGroupMembershipPolicy);

			if (PropsValues.MEMBERSHIP_POLICY_AUTO_VERIFY) {
				userGroupMembershipPolicy.verifyPolicy();
			}
		}

		if (portalProperties.containsKey(PropsKeys.PASSWORDS_TOOLKIT)) {
			String toolkitClassName = portalProperties.getProperty(
				PropsKeys.PASSWORDS_TOOLKIT);

			Toolkit toolkit = (Toolkit)newInstance(
				portletClassLoader, Toolkit.class, toolkitClassName);

			registerService(
				servletContextName, toolkitClassName, Toolkit.class, toolkit);
		}

		if (portalProperties.containsKey(PropsKeys.PHONE_NUMBER_FORMAT_IMPL)) {
			String phoneNumberFormatClassName = portalProperties.getProperty(
				PropsKeys.PHONE_NUMBER_FORMAT_IMPL);

			PhoneNumberFormat phoneNumberFormat =
				(PhoneNumberFormat)newInstance(
					portletClassLoader, PhoneNumberFormat.class,
					phoneNumberFormatClassName);

			registerService(
				servletContextName, phoneNumberFormatClassName,
				PhoneNumberFormat.class, phoneNumberFormat);
		}

		if (portalProperties.containsKey(PropsKeys.SANITIZER_IMPL)) {
			String[] sanitizerClassNames = StringUtil.split(
				portalProperties.getProperty(PropsKeys.SANITIZER_IMPL));

			for (String sanitizerClassName : sanitizerClassNames) {
				Sanitizer sanitizer = (Sanitizer)newInstance(
					portletClassLoader, Sanitizer.class, sanitizerClassName);

				registerService(
					servletContextName, sanitizerClassName, Sanitizer.class,
					sanitizer);
			}
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_EMAIL_ADDRESS_GENERATOR)) {

			String emailAddressGeneratorClassName =
				portalProperties.getProperty(
					PropsKeys.USERS_EMAIL_ADDRESS_GENERATOR);

			EmailAddressGenerator emailAddressGenerator =
				(EmailAddressGenerator)newInstance(
					portletClassLoader, EmailAddressGenerator.class,
					emailAddressGeneratorClassName);

			registerService(
				servletContextName, emailAddressGeneratorClassName,
				EmailAddressGenerator.class, emailAddressGenerator);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_EMAIL_ADDRESS_VALIDATOR)) {

			String emailAddressValidatorClassName =
				portalProperties.getProperty(
					PropsKeys.USERS_EMAIL_ADDRESS_VALIDATOR);

			EmailAddressValidator emailAddressValidator =
				(EmailAddressValidator)newInstance(
					portletClassLoader, EmailAddressValidator.class,
					emailAddressValidatorClassName);

			registerService(
				servletContextName, emailAddressValidatorClassName,
				EmailAddressValidator.class, emailAddressValidator);
		}

		if (portalProperties.containsKey(PropsKeys.USERS_FULL_NAME_GENERATOR)) {
			String fullNameGeneratorClassName = portalProperties.getProperty(
				PropsKeys.USERS_FULL_NAME_GENERATOR);

			FullNameGenerator fullNameGenerator =
				(FullNameGenerator)newInstance(
					portletClassLoader, FullNameGenerator.class,
					fullNameGeneratorClassName);

			registerService(
				servletContextName, fullNameGeneratorClassName,
				FullNameGenerator.class, fullNameGenerator);
		}

		if (portalProperties.containsKey(PropsKeys.USERS_FULL_NAME_VALIDATOR)) {
			String fullNameValidatorClassName = portalProperties.getProperty(
				PropsKeys.USERS_FULL_NAME_VALIDATOR);

			FullNameValidator fullNameValidator =
				(FullNameValidator)newInstance(
					portletClassLoader, FullNameValidator.class,
					fullNameValidatorClassName);

			registerService(
				servletContextName, fullNameValidatorClassName,
				FullNameValidator.class, fullNameValidator);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_GENERATOR)) {

			String screenNameGeneratorClassName = portalProperties.getProperty(
				PropsKeys.USERS_SCREEN_NAME_GENERATOR);

			ScreenNameGenerator screenNameGenerator =
				(ScreenNameGenerator)newInstance(
					portletClassLoader, ScreenNameGenerator.class,
					screenNameGeneratorClassName);

			registerService(
				servletContextName, screenNameGeneratorClassName,
				ScreenNameGenerator.class, screenNameGenerator);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_VALIDATOR)) {

			String screenNameValidatorClassName = portalProperties.getProperty(
				PropsKeys.USERS_SCREEN_NAME_VALIDATOR);

			ScreenNameValidator screenNameValidator =
				(ScreenNameValidator)newInstance(
					portletClassLoader, ScreenNameValidator.class,
					screenNameValidatorClassName);

			registerService(
				servletContextName, screenNameValidatorClassName,
				ScreenNameValidator.class, screenNameValidator);
		}

		Set<String> liferayFilterClassNames =
			LiferayFilterTracker.getClassNames();

		for (String liferayFilterClassName : liferayFilterClassNames) {
			if (!portalProperties.containsKey(liferayFilterClassName)) {
				continue;
			}

			boolean filterEnabled = GetterUtil.getBoolean(
				portalProperties.getProperty(liferayFilterClassName));

			Set<LiferayFilter> liferayFilters =
				LiferayFilterTracker.getLiferayFilters(liferayFilterClassName);

			for (LiferayFilter liferayFilter : liferayFilters) {
				liferayFilter.setFilterEnabled(filterEnabled);
			}
		}

		if (unfilteredPortalProperties.containsKey(
				PropsKeys.RELEASE_INFO_BUILD_NUMBER) ||
			unfilteredPortalProperties.containsKey(
				PropsKeys.UPGRADE_PROCESSES)) {

			updateRelease(
				servletContextName, portletClassLoader,
				unfilteredPortalProperties);
		}
	}

	protected void initServices(
			String servletContextName, ClassLoader portletClassLoader,
			Element parentElement)
		throws Exception {

		List<Element> serviceElements = parentElement.elements("service");

		for (Element serviceElement : serviceElements) {
			String serviceType = serviceElement.elementText("service-type");
			String serviceImpl = serviceElement.elementText("service-impl");

			if (!checkPermission(
					PACLConstants.PORTAL_HOOK_PERMISSION_SERVICE,
					portletClassLoader, serviceType,
					"Rejecting service " + serviceImpl)) {

				continue;
			}

			Class<?> serviceTypeClass = portletClassLoader.loadClass(
				serviceType);

			Class<?> serviceImplClass = portletClassLoader.loadClass(
				serviceImpl);

			Constructor<?> serviceImplConstructor =
				serviceImplClass.getConstructor(
					new Class<?>[] {serviceTypeClass});

			Object serviceProxy = PortalBeanLocatorUtil.locate(serviceType);

			if (ProxyUtil.isProxyClass(serviceProxy.getClass())) {
				initServices(
					servletContextName, portletClassLoader, serviceType,
					serviceTypeClass, serviceImplConstructor, serviceProxy);
			}
			else {
				_log.error(
					"Service hooks require Spring to be configured to use " +
						"JdkDynamicProxy and will not work with CGLIB");
			}
		}
	}

	protected void initServices(
			String servletContextName, ClassLoader portletClassLoader,
			String serviceType, Class<?> serviceTypeClass,
			Constructor<?> serviceImplConstructor, Object serviceProxy)
		throws Exception {

		AdvisedSupport advisedSupport = ServiceBeanAopProxy.getAdvisedSupport(
			serviceProxy);

		TargetSource targetSource = advisedSupport.getTargetSource();

		Object previousService = targetSource.getTarget();

		ServiceWrapper<?> serviceWrapper =
			(ServiceWrapper<?>)serviceImplConstructor.newInstance(
				previousService);

		registerService(
			servletContextName, serviceImplConstructor, ServiceWrapper.class,
			serviceWrapper);
	}

	protected Filter initServletFilter(
			String filterClassName, ClassLoader portletClassLoader)
		throws Exception {

		Filter filter = (Filter)InstanceFactory.newInstance(
			portletClassLoader, filterClassName);

		List<Class<?>> interfaces = new ArrayList<Class<?>>();

		if (filter instanceof TryFilter) {
			interfaces.add(TryFilter.class);
		}

		if (filter instanceof TryFinallyFilter) {
			interfaces.add(TryFinallyFilter.class);
		}

		if (filter instanceof WrapHttpServletRequestFilter) {
			interfaces.add(WrapHttpServletRequestFilter.class);
		}

		if (filter instanceof WrapHttpServletResponseFilter) {
			interfaces.add(WrapHttpServletResponseFilter.class);
		}

		if (filter instanceof LiferayFilter) {
			interfaces.add(LiferayFilter.class);
		}
		else {
			interfaces.add(Filter.class);
		}

		filter = (Filter)ProxyUtil.newProxyInstance(
			portletClassLoader,
			interfaces.toArray(new Class[interfaces.size()]),
			new ClassLoaderBeanHandler(filter, portletClassLoader));

		return filter;
	}

	protected void initServletFilters(
			ServletContext servletContext, String servletContextName,
			ClassLoader portletClassLoader, Element parentElement)
		throws Exception {

		ServletFiltersContainer servletFiltersContainer =
			_servletFiltersContainerMap.get(servletContextName);

		if (servletFiltersContainer == null) {
			servletFiltersContainer = new ServletFiltersContainer();

			_servletFiltersContainerMap.put(
				servletContextName, servletFiltersContainer);
		}

		List<Element> servletFilterElements = parentElement.elements(
			"servlet-filter");

		if (!servletFilterElements.isEmpty() &&
			!checkPermission(
				PACLConstants.PORTAL_HOOK_PERMISSION_SERVLET_FILTERS,
				portletClassLoader, null, "Rejecting servlet filters")) {

			return;
		}

		for (Element servletFilterElement : servletFilterElements) {
			String servletFilterName = servletFilterElement.elementText(
				"servlet-filter-name");
			String servletFilterImpl = servletFilterElement.elementText(
				"servlet-filter-impl");

			List<Element> initParamElements = servletFilterElement.elements(
				"init-param");

			Map<String, String> initParameterMap =
				new HashMap<String, String>();

			for (Element initParamElement : initParamElements) {
				String paramName = initParamElement.elementText("param-name");
				String paramValue = initParamElement.elementText("param-value");

				initParameterMap.put(paramName, paramValue);
			}

			Filter filter = initServletFilter(
				servletFilterImpl, portletClassLoader);

			FilterConfig filterConfig = new InvokerFilterConfig(
				servletContext, servletFilterName, initParameterMap);

			filter.init(filterConfig);

			servletFiltersContainer.registerFilter(
				servletFilterName, filter, filterConfig);
		}

		List<Element> servletFilterMappingElements = parentElement.elements(
			"servlet-filter-mapping");

		for (Element servletFilterMappingElement :
				servletFilterMappingElements) {

			String servletFilterName = servletFilterMappingElement.elementText(
				"servlet-filter-name");
			String afterFilter = servletFilterMappingElement.elementText(
				"after-filter");
			String beforeFilter = servletFilterMappingElement.elementText(
				"before-filter");

			String positionFilterName = beforeFilter;
			boolean after = false;

			if (Validator.isNotNull(afterFilter)) {
				positionFilterName = afterFilter;
				after = true;
			}

			List<Element> urlPatternElements =
				servletFilterMappingElement.elements("url-pattern");

			List<String> urlPatterns = new ArrayList<String>();

			for (Element urlPatternElement : urlPatternElements) {
				String urlPattern = urlPatternElement.getTextTrim();

				urlPatterns.add(urlPattern);
			}

			List<Element> dispatcherElements =
				servletFilterMappingElement.elements("dispatcher");

			List<String> dispatchers = new ArrayList<String>();

			for (Element dispatcherElement : dispatcherElements) {
				String dispatcher = dispatcherElement.getTextTrim();

				dispatcher = StringUtil.toUpperCase(dispatcher);

				dispatchers.add(dispatcher);
			}

			servletFiltersContainer.registerFilterMapping(
				servletFilterName, urlPatterns, dispatchers, positionFilterName,
				after);
		}
	}

	protected void initStrutsAction(
			String servletContextName, ClassLoader portletClassLoader,
			String strutsActionPath, String strutsActionClassName)
		throws Exception {

		Object strutsActionObject = InstanceFactory.newInstance(
			portletClassLoader, strutsActionClassName);

		if (strutsActionObject instanceof StrutsAction) {
			StrutsAction strutsAction =
				(StrutsAction)ProxyUtil.newProxyInstance(
					portletClassLoader, new Class[] {StrutsAction.class},
					new ClassLoaderBeanHandler(
						strutsActionObject, portletClassLoader));

			registerService(
				servletContextName, strutsActionClassName, StrutsAction.class,
				strutsAction, "path", strutsActionPath);
		}
		else {
			StrutsPortletAction strutsPortletAction =
				(StrutsPortletAction)ProxyUtil.newProxyInstance(
					portletClassLoader, new Class[] {StrutsPortletAction.class},
					new ClassLoaderBeanHandler(
						strutsActionObject, portletClassLoader));

			registerService(
				servletContextName, strutsActionClassName,
				StrutsPortletAction.class, strutsPortletAction, "path",
				strutsActionPath);
		}
	}

	protected void initStrutsActions(
			String servletContextName, ClassLoader portletClassLoader,
			Element parentElement)
		throws Exception {

		List<Element> strutsActionElements = parentElement.elements(
			"struts-action");

		for (Element strutsActionElement : strutsActionElements) {
			String strutsActionPath = strutsActionElement.elementText(
				"struts-action-path");

			if (!checkPermission(
					PACLConstants.PORTAL_HOOK_PERMISSION_STRUTS_ACTION_PATH,
					portletClassLoader, strutsActionPath,
					"Rejecting struts action path " + strutsActionPath)) {

				continue;
			}

			String strutsActionImpl = strutsActionElement.elementText(
				"struts-action-impl");

			initStrutsAction(
				servletContextName, portletClassLoader, strutsActionPath,
				strutsActionImpl);
		}
	}

	protected <S, T> Map<S, T> newMap() {
		return new ConcurrentHashMap<S, T>();
	}

	protected void resetPortalProperties(
			String servletContextName, Properties portalProperties,
			boolean initPhase)
		throws Exception {

		for (String key : _PROPS_VALUES_BOOLEAN) {
			String fieldName = StringUtil.replace(
				StringUtil.toUpperCase(key), CharPool.PERIOD,
				CharPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				Boolean value = Boolean.valueOf(
					GetterUtil.getBoolean(PropsUtil.get(key)));

				field.setBoolean(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_INTEGER) {
			String fieldName = StringUtil.replace(
				StringUtil.toUpperCase(key), CharPool.PERIOD,
				CharPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				Integer value = Integer.valueOf(
					GetterUtil.getInteger(PropsUtil.get(key)));

				field.setInt(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_LONG) {
			String fieldName = StringUtil.replace(
				StringUtil.toUpperCase(key), CharPool.PERIOD,
				CharPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				Long value = Long.valueOf(
					GetterUtil.getLong(PropsUtil.get(key)));

				field.setLong(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_STRING) {
			String fieldName = StringUtil.replace(
				StringUtil.toUpperCase(key), CharPool.PERIOD,
				CharPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				String value = GetterUtil.getString(PropsUtil.get(key));

				field.set(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		resetPortalPropertiesStringArray(
			servletContextName, portalProperties, initPhase,
			_PROPS_VALUES_MERGE_STRING_ARRAY, _mergeStringArraysContainerMap);

		resetPortalPropertiesStringArray(
			servletContextName, portalProperties, initPhase,
			_PROPS_VALUES_OVERRIDE_STRING_ARRAY,
			_overrideStringArraysContainerMap);

		if (containsKey(portalProperties, LOCALES) ||
			containsKey(portalProperties, LOCALES_BETA)) {

			PropsValues.LOCALES = PropsUtil.getArray(LOCALES);

			LanguageUtil.init();
		}

		if (containsKey(portalProperties, LOCALES_ENABLED)) {
			PropsValues.LOCALES_ENABLED = PropsUtil.getArray(LOCALES_ENABLED);

			LanguageUtil.init();
		}

		if (containsKey(portalProperties, AUTH_TOKEN_IGNORE_ACTIONS)) {
			AuthTokenWhitelistUtil.resetPortletCSRFWhitelistActions();
		}

		if (containsKey(portalProperties, AUTH_TOKEN_IGNORE_ORIGINS)) {
			AuthTokenWhitelistUtil.resetOriginCSRFWhitelist();
		}

		if (containsKey(portalProperties, AUTH_TOKEN_IGNORE_PORTLETS)) {
			AuthTokenWhitelistUtil.resetPortletCSRFWhitelist();
		}

		if (containsKey(
				portalProperties,
				PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST)) {

			AuthTokenWhitelistUtil.resetPortletInvocationWhitelist();
		}

		if (containsKey(
				portalProperties,
				PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS)) {

			AuthTokenWhitelistUtil.resetPortletInvocationWhitelistActions();
		}

		CacheUtil.clearCache();

		JavaScriptBundleUtil.clearCache();
	}

	protected void resetPortalPropertiesStringArray(
		String servletContextName, Properties portalProperties,
		boolean initPhase, String[] propsValuesStringArray,
		Map<String, StringArraysContainer> stringArraysContainerMap) {

		for (String key : propsValuesStringArray) {
			String fieldName = StringUtil.replace(
				StringUtil.toUpperCase(key), CharPool.PERIOD,
				CharPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				resetPortalPropertiesStringArray(
					servletContextName, portalProperties, initPhase,
					propsValuesStringArray, stringArraysContainerMap, key,
					fieldName);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}
	}

	protected void resetPortalPropertiesStringArray(
			String servletContextName, Properties portalProperties,
			boolean initPhase, String[] propsValuesStringArray,
			Map<String, StringArraysContainer> stringArraysContainerMap,
			String key, String fieldName)
		throws Exception {

		Field field = PropsValues.class.getField(fieldName);

		StringArraysContainer stringArraysContainer =
			stringArraysContainerMap.get(key);

		String[] value = null;

		if (initPhase) {
			if (stringArraysContainer
					instanceof OverrideStringArraysContainer) {

				OverrideStringArraysContainer overrideStringArraysContainer =
					(OverrideStringArraysContainer)stringArraysContainer;

				if (overrideStringArraysContainer.isOverridden()) {
					_log.error("Error setting overridden field " + fieldName);

					return;
				}

				value = StringUtil.split(portalProperties.getProperty(key));
			}
			else {
				value = PropsUtil.getArray(key);
			}
		}

		stringArraysContainer.setPluginStringArray(servletContextName, value);

		value = stringArraysContainer.getStringArray();

		field.set(null, value);

		if (key.equals(PropsKeys.LAYOUT_TYPES)) {
			Map<String, LayoutSettings> layoutSettingsMap =
				LayoutSettings.getLayoutSettingsMap();

			Set<Map.Entry<String, LayoutSettings>> set =
				layoutSettingsMap.entrySet();

			Iterator<Map.Entry<String, LayoutSettings>> iterator =
				set.iterator();

			while (iterator.hasNext()) {
				Map.Entry<String, LayoutSettings> entry = iterator.next();

				String layoutType = entry.getKey();

				if (!layoutType.equals(LayoutConstants.TYPE_CONTROL_PANEL) &&
					!ArrayUtil.contains(value, layoutType)) {

					iterator.remove();
				}
			}

			for (String type : value) {
				if (!layoutSettingsMap.containsKey(type)) {
					LayoutSettings.addLayoutSetting(type);
				}
			}
		}
	}

	protected void updateRelease(
			String servletContextName, ClassLoader portletClassLoader,
			Properties unfilteredPortalProperties)
		throws Exception {

		int buildNumber = GetterUtil.getInteger(
			unfilteredPortalProperties.getProperty(
				PropsKeys.RELEASE_INFO_BUILD_NUMBER));

		if (buildNumber <= 0) {
			_log.error(
				"Skipping upgrade processes for " + servletContextName +
					" because \"release.info.build.number\" is not specified");

			return;
		}

		Release release = ReleaseLocalServiceUtil.fetchRelease(
			servletContextName);

		if (release == null) {
			int previousBuildNumber = GetterUtil.getInteger(
				unfilteredPortalProperties.getProperty(
					PropsKeys.RELEASE_INFO_PREVIOUS_BUILD_NUMBER),
				buildNumber);

			release = ReleaseLocalServiceUtil.addRelease(
				servletContextName, previousBuildNumber);
		}

		if (buildNumber == release.getBuildNumber()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping upgrade processes for " + servletContextName +
						" because it is already up to date");
			}
		}
		else if (buildNumber < release.getBuildNumber()) {
			throw new UpgradeException(
				"Skipping upgrade processes for " + servletContextName +
					" because you are trying to upgrade with an older version");
		}
		else {
			String[] upgradeProcessClassNames = StringUtil.split(
				unfilteredPortalProperties.getProperty(
					PropsKeys.UPGRADE_PROCESSES));

			boolean indexOnUpgrade = GetterUtil.getBoolean(
				unfilteredPortalProperties.getProperty(
					PropsKeys.INDEX_ON_UPGRADE),
				PropsValues.INDEX_ON_UPGRADE);

			UpgradeProcessUtil.upgradeProcess(
				release.getBuildNumber(), upgradeProcessClassNames,
				portletClassLoader, indexOnUpgrade);
		}

		ReleaseLocalServiceUtil.updateRelease(
			release.getReleaseId(), buildNumber, null, true);
	}

	private static final String[] _PROPS_KEYS_EVENTS = {
		LOGIN_EVENTS_POST, LOGIN_EVENTS_PRE, LOGOUT_EVENTS_POST,
		LOGOUT_EVENTS_PRE, SERVLET_SERVICE_EVENTS_POST,
		SERVLET_SERVICE_EVENTS_PRE
	};

	private static final String[] _PROPS_KEYS_SESSION_EVENTS = {
		SERVLET_SESSION_CREATE_EVENTS, SERVLET_SESSION_DESTROY_EVENTS
	};

	private static final String[] _PROPS_VALUES_BOOLEAN = {
		"auth.forward.by.last.path", "captcha.check.portal.create_account",
		"dl.file.entry.drafts.enabled",
		"dl.file.entry.open.in.ms.office.manual.check.in.required",
		"field.enable.com.liferay.portal.model.Contact.birthday",
		"field.enable.com.liferay.portal.model.Contact.male",
		"field.enable.com.liferay.portal.model.Organization.status",
		"javascript.fast.load", "layout.template.cache.enabled",
		"layout.user.private.layouts.auto.create",
		"layout.user.private.layouts.enabled",
		"layout.user.private.layouts.power.user.required",
		"layout.user.public.layouts.auto.create",
		"layout.user.public.layouts.enabled",
		"layout.user.public.layouts.power.user.required",
		"login.create.account.allow.custom.password", "login.dialog.disabled",
		"my.sites.show.private.sites.with.no.layouts",
		"my.sites.show.public.sites.with.no.layouts",
		"my.sites.show.user.private.sites.with.no.layouts",
		"my.sites.show.user.public.sites.with.no.layouts",
		"portlet.add.default.resource.check.enabled", "rss.feeds.enabled",
		"session.store.password", "social.activity.sets.bundling.enabled",
		"social.activity.sets.enabled", "terms.of.use.required",
		"theme.css.fast.load", "theme.images.fast.load",
		"theme.jsp.override.enabled", "theme.loader.new.theme.id.on.import",
		"theme.portlet.decorate.default", "theme.portlet.sharing.default",
		"user.notification.event.confirmation.enabled",
		"users.email.address.required", "users.image.check.token",
		"users.screen.name.always.autogenerate"
	};

	private static final String[] _PROPS_VALUES_INTEGER = {
		"session.max.allowed", "users.image.max.height",
		"users.image.max.width",
	};

	private static final String[] _PROPS_VALUES_LONG = {
	};

	private static final String[] _PROPS_VALUES_MERGE_STRING_ARRAY = {
		"asset.publisher.query.form.configuration", "auth.token.ignore.actions",
		"auth.token.ignore.origins", "auth.token.ignore.portlets",
		"admin.default.group.names", "admin.default.role.names",
		"admin.default.user.group.names", "asset.publisher.display.styles",
		"company.settings.form.authentication",
		"company.settings.form.configuration",
		"company.settings.form.identification",
		"company.settings.form.miscellaneous", "company.settings.form.social",
		"convert.processes", "dockbar.add.portlets", "journal.article.form.add",
		"journal.article.form.translate", "journal.article.form.update",
		"layout.form.add", "layout.form.update", "layout.set.form.update",
		"layout.static.portlets.all", "layout.types",
		"login.form.navigation.post", "login.form.navigation.pre",
		"organizations.form.add.identification", "organizations.form.add.main",
		"organizations.form.add.miscellaneous",
		"portlet.add.default.resource.check.whitelist",
		"portlet.add.default.resource.check.whitelist.actions",
		"session.phishing.protected.attributes", "sites.form.add.advanced",
		"sites.form.add.main", "sites.form.add.miscellaneous",
		"sites.form.add.seo", "sites.form.update.advanced",
		"sites.form.update.main", "sites.form.update.miscellaneous",
		"sites.form.update.seo", "users.form.add.identification",
		"users.form.add.main", "users.form.add.miscellaneous",
		"users.form.my.account.identification", "users.form.my.account.main",
		"users.form.my.account.miscellaneous",
		"users.form.update.identification", "users.form.update.main",
		"users.form.update.miscellaneous"
	};

	private static final String[] _PROPS_VALUES_OBSOLETE = {
		"layout.user.private.layouts.modifiable",
		"layout.user.public.layouts.modifiable"
	};

	private static final String[] _PROPS_VALUES_OVERRIDE_STRING_ARRAY = {
		"locales.beta"
	};

	private static final String[] _PROPS_VALUES_STRING = {
		"company.default.locale", "company.default.time.zone",
		"default.landing.page.path", "default.regular.color.scheme.id",
		"default.regular.theme.id", "default.wap.color.scheme.id",
		"default.wap.theme.id", "passwords.passwordpolicytoolkit.generator",
		"passwords.passwordpolicytoolkit.static",
		"phone.number.format.international.regexp",
		"phone.number.format.usa.regexp", "social.activity.sets.selector",
		"theme.shortcut.icon"
	};

	private static Log _log = LogFactoryUtil.getLog(
		HookHotDeployListener.class);

	private Map<String, AuthPublicPathsContainer> _authPublicPathsContainerMap =
		new HashMap<String, AuthPublicPathsContainer>();
	private Map<String, CustomJspBag> _customJspBagsMap =
		new HashMap<String, CustomJspBag>();
	private Map<String, DLFileEntryProcessorContainer>
		_dlFileEntryProcessorContainerMap =
			new HashMap<String, DLFileEntryProcessorContainer>();
	private Map<String, DLRepositoryContainer> _dlRepositoryContainerMap =
		new HashMap<String, DLRepositoryContainer>();
	private Map<String, HotDeployListenersContainer>
		_hotDeployListenersContainerMap =
			new HashMap<String, HotDeployListenersContainer>();
	private Map<String, LanguagesContainer> _languagesContainerMap =
		new HashMap<String, LanguagesContainer>();
	private Map<String, StringArraysContainer> _mergeStringArraysContainerMap =
		new HashMap<String, StringArraysContainer>();
	private Map<String, ModelListenersContainer> _modelListenersContainerMap =
		new HashMap<String, ModelListenersContainer>();
	private Map<String, StringArraysContainer>
		_overrideStringArraysContainerMap =
			new HashMap<String, StringArraysContainer>();
	private Map<String, Properties> _portalPropertiesMap =
		new HashMap<String, Properties>();
	private Set<String> _propsKeysEvents = SetUtil.fromArray(
		_PROPS_KEYS_EVENTS);
	private Set<String> _propsKeysSessionEvents = SetUtil.fromArray(
		_PROPS_KEYS_SESSION_EVENTS);
	private Map<String, Map<Object, ServiceRegistration<?>>>
		_serviceRegistrations = newMap();
	private Set<String> _servletContextNames = new HashSet<String>();
	private Map<String, ServletFiltersContainer> _servletFiltersContainerMap =
		new HashMap<String, ServletFiltersContainer>();

	private class AuthPublicPathsContainer {

		public void registerPaths(String[] paths) {
			for (String path : paths) {
				_paths.add(path);
			}

			AuthPublicPathRegistry.register(paths);
		}

		public void unregisterPaths() {
			for (String path : _paths) {
				AuthPublicPathRegistry.unregister(path);
			}

			_paths.clear();
		}

		private Set<String> _paths = new HashSet<String>();

	}

	private class CustomJspBag {

		public CustomJspBag(
			String customJspDir, boolean customJspGlobal,
			List<String> customJsps) {

			_customJspDir = customJspDir;
			_customJspGlobal = customJspGlobal;
			_customJsps = customJsps;
		}

		public String getCustomJspDir() {
			return _customJspDir;
		}

		public List<String> getCustomJsps() {
			return _customJsps;
		}

		public boolean isCustomJspGlobal() {
			return _customJspGlobal;
		}

		private String _customJspDir;
		private boolean _customJspGlobal;
		private List<String> _customJsps;

	}

	private class DLFileEntryProcessorContainer {

		public void registerDLProcessor(DLProcessor dlProcessor) {
			DLProcessorRegistryUtil.register(dlProcessor);

			_dlProcessors.add(dlProcessor);
		}

		public void unregisterDLProcessors() {
			for (DLProcessor dlProcessor : _dlProcessors) {
				DLProcessorRegistryUtil.unregister(dlProcessor);
			}

			_dlProcessors.clear();
		}

		private List<DLProcessor> _dlProcessors = new ArrayList<DLProcessor>();

	}

	private class DLRepositoryContainer {

		public void registerRepositoryFactory(
			String className,
			ExternalRepositoryFactory externalRepositoryFactory) {

			ExternalRepositoryFactoryUtil.registerExternalRepositoryFactory(
				className, externalRepositoryFactory);

			_classNames.add(className);
		}

		public void unregisterRepositoryFactories() {
			for (String className : _classNames) {
				ExternalRepositoryFactoryUtil.
					unregisterExternalRepositoryFactory(className);
			}

			_classNames.clear();
		}

		private List<String> _classNames = new ArrayList<String>();

	}

	private class HotDeployListenersContainer {

		public void registerHotDeployListener(
			HotDeployListener hotDeployListener) {

			HotDeployUtil.registerListener(hotDeployListener);

			_hotDeployListeners.add(hotDeployListener);
		}

		public void unregisterHotDeployListeners() {
			for (HotDeployListener hotDeployListener : _hotDeployListeners) {
				HotDeployUtil.unregisterListener(hotDeployListener);
			}
		}

		private List<HotDeployListener> _hotDeployListeners =
			new ArrayList<HotDeployListener>();

	}

	private class LanguagesContainer {

		public void addLanguage(
			Locale locale, Map<String, String> languageMap) {

			Map<String, String> oldLanguageMap =
				LanguageResources.putLanguageMap(locale, languageMap);

			_languagesMap.put(locale, oldLanguageMap);
		}

		public void unregisterLanguages() {
			for (Map.Entry<Locale, Map<String, String>> entry :
					_languagesMap.entrySet()) {

				Locale locale = entry.getKey();
				Map<String, String> languageMap = entry.getValue();

				LanguageResources.putLanguageMap(locale, languageMap);
			}
		}

		private Map<Locale, Map<String, String>> _languagesMap =
			new HashMap<Locale, Map<String, String>>();

	}

	private class MergeStringArraysContainer implements StringArraysContainer {

		@Override
		public String[] getStringArray() {
			Set<String> mergedStringSet = new LinkedHashSet<String>();

			mergedStringSet.addAll(Arrays.asList(_portalStringArray));

			for (Map.Entry<String, String[]> entry :
					_pluginStringArrayMap.entrySet()) {

				mergedStringSet.addAll(Arrays.asList(entry.getValue()));
			}

			return mergedStringSet.toArray(new String[mergedStringSet.size()]);
		}

		@Override
		public void setPluginStringArray(
			String servletContextName, String[] pluginStringArray) {

			if (pluginStringArray != null) {
				_pluginStringArrayMap.put(
					servletContextName, pluginStringArray);
			}
			else {
				_pluginStringArrayMap.remove(servletContextName);
			}
		}

		private MergeStringArraysContainer(String key) {
			_portalStringArray = PropsUtil.getArray(key);
		}

		private Map<String, String[]> _pluginStringArrayMap =
			new HashMap<String, String[]>();
		private String[] _portalStringArray;

	}

	private class ModelListenersContainer {

		public void registerModelListener(
			String modelName, ModelListener<BaseModel<?>> modelListener) {

			List<ModelListener<BaseModel<?>>> modelListeners =
				_modelListenersMap.get(modelName);

			if (modelListeners == null) {
				modelListeners = new ArrayList<ModelListener<BaseModel<?>>>();

				_modelListenersMap.put(modelName, modelListeners);
			}

			modelListeners.add(modelListener);
		}

		@SuppressWarnings("rawtypes")
		public void unregisterModelListeners(String servletContextName) {
			for (Map.Entry<String, List<ModelListener<BaseModel<?>>>> entry :
					_modelListenersMap.entrySet()) {

				String modelName = entry.getKey();
				List<ModelListener<BaseModel<?>>> modelListeners =
					entry.getValue();

				BasePersistence persistence = getPersistence(
					servletContextName, modelName);

				for (ModelListener<BaseModel<?>> modelListener :
						modelListeners) {

					persistence.unregisterListener(modelListener);
				}
			}
		}

		private Map<String, List<ModelListener<BaseModel<?>>>>
			_modelListenersMap =
				new HashMap<String, List<ModelListener<BaseModel<?>>>>();

	}

	private class OverrideStringArraysContainer
		implements StringArraysContainer {

		@Override
		public String[] getStringArray() {
			if (_pluginStringArray != null) {
				return _pluginStringArray;
			}

			return _portalStringArray;
		}

		public boolean isOverridden() {
			if (Validator.isNotNull(_servletContextName)) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public void setPluginStringArray(
			String servletContextName, String[] pluginStringArray) {

			if (pluginStringArray != null) {
				if (!isOverridden()) {
					_servletContextName = servletContextName;
					_pluginStringArray = pluginStringArray;
				}
			}
			else {
				if (_servletContextName.equals(servletContextName)) {
					_servletContextName = null;
					_pluginStringArray = null;
				}
			}
		}

		private OverrideStringArraysContainer(String key) {
			_portalStringArray = PropsUtil.getArray(key);
		}

		private String[] _pluginStringArray;
		private String[] _portalStringArray;
		private String _servletContextName;

	}

	private class ServletFiltersContainer {

		public InvokerFilterHelper getInvokerFilterHelper() {
			ServletContext portalServletContext = ServletContextPool.get(
				PortalUtil.getServletContextName());

			InvokerFilterHelper invokerFilterHelper =
				(InvokerFilterHelper)portalServletContext.getAttribute(
					InvokerFilterHelper.class.getName());

			return invokerFilterHelper;
		}

		public void registerFilter(
			String filterName, Filter filter, FilterConfig filterConfig) {

			InvokerFilterHelper invokerFilterHelper = getInvokerFilterHelper();

			Filter previousFilter = invokerFilterHelper.registerFilter(
				filterName, filter);

			_filterConfigs.put(filterName, filterConfig);
			_filters.put(filterName, previousFilter);
		}

		public void registerFilterMapping(
			String filterName, List<String> urlPatterns,
			List<String> dispatchers, String positionFilterName,
			boolean after) {

			InvokerFilterHelper invokerFilterHelper = getInvokerFilterHelper();

			Filter filter = invokerFilterHelper.getFilter(filterName);

			FilterConfig filterConfig = _filterConfigs.get(filterName);

			if (filterConfig == null) {
				filterConfig = invokerFilterHelper.getFilterConfig(filterName);
			}

			if (filter == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No filter exists with filter mapping " + filterName);
				}

				return;
			}

			FilterMapping filterMapping = new FilterMapping(
				filter, filterConfig, urlPatterns, dispatchers);

			invokerFilterHelper.registerFilterMapping(
				filterMapping, positionFilterName, after);
		}

		public void unregisterFilterMappings() {
			InvokerFilterHelper invokerFilterHelper = getInvokerFilterHelper();

			for (String filterName : _filters.keySet()) {
				Filter filter = _filters.get(filterName);

				Filter previousFilter = invokerFilterHelper.registerFilter(
					filterName, filter);

				previousFilter.destroy();
			}

			for (FilterMapping filterMapping : _filterMappings) {
				invokerFilterHelper.unregisterFilterMapping(filterMapping);

				filterMapping.setFilter(null);
			}
		}

		private Map<String, FilterConfig> _filterConfigs =
			new HashMap<String, FilterConfig>();
		private List<FilterMapping> _filterMappings =
			new ArrayList<FilterMapping>();
		private Map<String, Filter> _filters = new HashMap<String, Filter>();

	}

	private interface StringArraysContainer {

		public String[] getStringArray();

		public void setPluginStringArray(
			String servletContextName, String[] pluginStringArray);

	}

}