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

package com.liferay.oauth2.provider.test.internal.activator;

import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandler;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandlerFactory;
import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Application;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public abstract class BaseTestPreparatorBundleActivator
	implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		this.bundleContext = bundleContext;

		autoCloseables = new ArrayList<>();

		try {
			prepareTest();
		}
		catch (Exception e) {
			_cleanUp();

			throw new RuntimeException(e);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_cleanUp();
	}

	protected User addAdminUser(Company company) throws Exception {
		User user = UserTestUtil.addCompanyAdminUser(company);

		autoCloseables.add(
			() -> UserLocalServiceUtil.deleteUser(user.getUserId()));

		return user;
	}

	protected User addUser(Company company) throws Exception {
		User user = UserTestUtil.addUser(company);

		autoCloseables.add(
			() -> UserLocalServiceUtil.deleteUser(user.getUserId()));

		return user;
	}

	protected Company createCompany(String hostName) throws PortalException {
		String virtualHostname = hostName + ".xyz";

		Company company = CompanyLocalServiceUtil.addCompany(
			hostName, virtualHostname, virtualHostname, false, 0, true);

		autoCloseables.add(
			() -> CompanyLocalServiceUtil.deleteCompany(
				company.getCompanyId()));

		return company;
	}

	protected Configuration createFactoryConfiguration(
		BundleContext bundleContext, String factoryPid,
		Dictionary<String, Object> properties) {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Dictionary<String, Object> registrationProperties =
			new HashMapDictionary<>();

		registrationProperties.put(Constants.SERVICE_PID, factoryPid);

		ServiceRegistration<ManagedServiceFactory> serviceRegistration =
			bundleContext.registerService(
				ManagedServiceFactory.class,
				new ManagedServiceFactory() {

					@Override
					public void deleted(String pid) {
					}

					@Override
					public String getName() {
						return
							"Test managedservicefactory for pid " + factoryPid;
					}

					@Override
					public void updated(
						String pid, Dictionary<String, ?> updatedProperties) {

						if (Validator.isNull(updatedProperties)) {
							return;
						}

						if (isIncluded(properties, updatedProperties)) {
							countDownLatch.countDown();
						}
					}

				},
				registrationProperties);

		try {
			ServiceReference<ConfigurationAdmin> serviceReference =
				bundleContext.getServiceReference(ConfigurationAdmin.class);

			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			Configuration configuration = null;

			try {
				configuration = configurationAdmin.createFactoryConfiguration(
					factoryPid, StringPool.QUESTION);

				configuration.update(properties);

				countDownLatch.await(5, TimeUnit.MINUTES);

				return configuration;
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			catch (InterruptedException ie) {
				try {
					configuration.delete();
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}

				throw new RuntimeException(ie);
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	protected Configuration createFactoryConfiguration(
		String factoryPid, Dictionary<String, Object> properties) {

		Configuration configuration = createFactoryConfiguration(
			bundleContext, factoryPid, properties);

		autoCloseables.add(configuration::delete);

		return configuration;
	}

	protected OAuth2Application createOAuth2Application(
			long companyId, User user, String clientId)
		throws PortalException {

		return createOAuth2Application(
			companyId, user, clientId,
			Arrays.asList(
				GrantType.CLIENT_CREDENTIALS,
				GrantType.RESOURCE_OWNER_PASSWORD),
			Arrays.asList("everything", "everything.readonly"));
	}

	protected OAuth2Application createOAuth2Application(
			long companyId, User user, String clientId,
			List<GrantType> availableGrants, List<String> availableScopes)
		throws PortalException {

		return createOAuth2Application(
			companyId, user, clientId, "oauthTestApplicationSecret",
			availableGrants, availableScopes);
	}

	protected OAuth2Application createOAuth2Application(
			long companyId, User user, String clientId,
			List<String> availableScopes)
		throws PortalException {

		return createOAuth2Application(
			companyId, user, clientId,
			Arrays.asList(
				GrantType.CLIENT_CREDENTIALS,
				GrantType.RESOURCE_OWNER_PASSWORD),
			availableScopes);
	}

	protected OAuth2Application createOAuth2Application(
			long companyId, User user, String clientId, String clientSecret,
			List<GrantType> availableGrants, List<String> availableScopes)
		throws PortalException {

		ServiceReference<OAuth2ApplicationLocalService> serviceReference =
			bundleContext.getServiceReference(
				OAuth2ApplicationLocalService.class);

		_oAuth2ApplicationLocalService = bundleContext.getService(
			serviceReference);

		try {
			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.addOAuth2Application(
					companyId, user.getUserId(), user.getLogin(),
					availableGrants, clientId, 0, clientSecret,
					"test oauth application",
					Collections.singletonList("token_introspection"),
					"http://localhost:8080", 0, "test application",
					"http://localhost:8080",
					Collections.singletonList("http://localhost:8080"),
					availableScopes, new ServiceContext());

			autoCloseables.add(
				() -> _oAuth2ApplicationLocalService.deleteOAuth2Application(
					oAuth2Application.getOAuth2ApplicationId()));

			return oAuth2Application;
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	protected void deleteConfiguration(
		BundleContext bundleContext, String pid) {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(Constants.SERVICE_PID, pid);

		ServiceRegistration<ManagedService> serviceRegistration =
			bundleContext.registerService(
				ManagedService.class,
				incomingProperties -> {
					if (Validator.isNull(incomingProperties)) {
						countDownLatch.countDown();
					}
				},
				properties);

		try {
			ServiceReference<ConfigurationAdmin> serviceReference =
				bundleContext.getServiceReference(ConfigurationAdmin.class);

			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			try {
				Configuration configuration =
					configurationAdmin.getConfiguration(
						pid, StringPool.QUESTION);

				configuration.delete();

				countDownLatch.await(5, TimeUnit.MINUTES);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	protected void executeAndWaitForReadiness(Runnable runnable) {
		int expectedModificationsCount = 23;

		CountDownLatch countDownLatch = new CountDownLatch(
			expectedModificationsCount);

		ServiceTracker<JaxrsServiceRuntime, Object> serviceTracker =
			ServiceTrackerFactory.open(
				bundleContext, JaxrsServiceRuntime.class,
				new ServiceTrackerCustomizer<JaxrsServiceRuntime, Object>() {

					@Override
					public Object addingService(
						ServiceReference<JaxrsServiceRuntime> reference) {

						return reference;
					}

					@Override
					public void modifiedService(
						ServiceReference<JaxrsServiceRuntime> reference,
						Object service) {

						countDownLatch.countDown();
					}

					@Override
					public void removedService(
						ServiceReference<JaxrsServiceRuntime> reference,
						Object service) {
					}

				});

		runnable.run();

		try {
			if (!countDownLatch.await(10, TimeUnit.MINUTES)) {
				throw new IllegalStateException(
					StringBundler.concat(
						"Expected modifications count ",
						expectedModificationsCount, " didn't match ",
						countDownLatch.getCount()));
			}
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}
		finally {
			serviceTracker.close();
		}
	}

	protected Configuration getConfiguration(
			BundleContext bundleContext, String pid)
		throws InvalidSyntaxException, IOException {

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		try {
			Configuration[] configurations =
				configurationAdmin.listConfigurations(
					"(" + Constants.SERVICE_PID + "=" + pid + ")");

			if (ArrayUtil.isEmpty(configurations)) {
				return null;
			}

			return configurations[0];
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	protected boolean isIncluded(
		Dictionary<String, ?> properties1, Dictionary<String, ?> properties2) {

		if (properties1.size() > properties2.size()) {
			return false;
		}

		Enumeration<String> keys = properties1.keys();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			if (!Objects.deepEquals(
					properties1.get(key), properties2.get(key))) {

				return false;
			}
		}

		return true;
	}

	protected abstract void prepareTest() throws Exception;

	protected ServiceRegistration<Application> registerJaxRsApplication(
		Application application, String path,
		Dictionary<String, Object> properties) {

		if ((properties == null) || properties.isEmpty()) {
			properties = new HashMapDictionary<>();
		}

		properties.put("oauth2.test.application", "true");
		properties.put("osgi.jaxrs.application.base", "/oauth2-test/" + path);
		properties.put(
			"osgi.jaxrs.extension.select", "(liferay.extension=OAuth2)");

		ServiceRegistration<Application> serviceRegistration =
			bundleContext.registerService(
				Application.class, application, properties);

		autoCloseables.add(serviceRegistration::unregister);

		return serviceRegistration;
	}

	protected ServiceRegistration<PrefixHandlerFactory> registerPrefixHandler(
		PrefixHandler prefixHandler, Dictionary<String, Object> properties) {

		ServiceRegistration<PrefixHandlerFactory> serviceRegistration =
			bundleContext.registerService(
				PrefixHandlerFactory.class, a -> prefixHandler, properties);

		autoCloseables.add(serviceRegistration::unregister);

		return serviceRegistration;
	}

	protected ServiceRegistration<ScopeMapper> registerScopeMapper(
		ScopeMapper scopeMapper, Dictionary<String, Object> properties) {

		ServiceRegistration<ScopeMapper> serviceRegistration =
			bundleContext.registerService(
				ScopeMapper.class, scopeMapper, properties);

		autoCloseables.add(serviceRegistration::unregister);

		return serviceRegistration;
	}

	protected Configuration updateConfiguration(
		BundleContext bundleContext, String servicePid,
		Dictionary<String, ?> properties) {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Dictionary<String, Object> registrationProperties =
			new HashMapDictionary<>();

		registrationProperties.put(Constants.SERVICE_PID, servicePid);

		ServiceRegistration<ManagedService> serviceRegistration =
			bundleContext.registerService(
				ManagedService.class,
				updatedProperties -> {
					if (Validator.isNull(updatedProperties)) {
						return;
					}

					if (isIncluded(properties, updatedProperties)) {
						countDownLatch.countDown();
					}
				},
				registrationProperties);

		try {
			ServiceReference<ConfigurationAdmin> serviceReference =
				bundleContext.getServiceReference(ConfigurationAdmin.class);

			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			Configuration configuration = null;

			try {
				configuration = configurationAdmin.getConfiguration(
					servicePid, StringPool.QUESTION);

				configuration.update(properties);

				countDownLatch.await(5, TimeUnit.MINUTES);

				return configuration;
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			catch (InterruptedException ie) {
				try {
					configuration.delete();
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}

				throw new RuntimeException(ie);
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	protected Runnable updateOrCreateConfiguration(
		String servicePid, Dictionary<String, ?> properties) {

		Configuration configuration = null;

		try {
			configuration = getConfiguration(bundleContext, servicePid);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (configuration == null) {
			updateConfiguration(bundleContext, servicePid, properties);

			return () -> deleteConfiguration(bundleContext, servicePid);
		}
		else {
			Dictionary<String, Object> oldProperties =
				configuration.getProperties();

			updateConfiguration(bundleContext, servicePid, properties);

			return () -> updateConfiguration(
				bundleContext, servicePid, oldProperties);
		}
	}

	protected ArrayList<AutoCloseable> autoCloseables;
	protected BundleContext bundleContext;

	private void _cleanUp() {
		ListIterator<AutoCloseable> listIterator = autoCloseables.listIterator(
			autoCloseables.size());

		while (listIterator.hasPrevious()) {
			AutoCloseable previousAutoCloseable = listIterator.previous();

			try {
				previousAutoCloseable.close();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseTestPreparatorBundleActivator.class);

	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

}