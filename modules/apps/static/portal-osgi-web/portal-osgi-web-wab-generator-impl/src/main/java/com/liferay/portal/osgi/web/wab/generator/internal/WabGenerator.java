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

package com.liferay.portal.osgi.web.wab.generator.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.osgi.web.wab.generator.internal.artifact.ArtifactURLUtil;
import com.liferay.portal.osgi.web.wab.generator.internal.artifact.WarArtifactUrlTransformer;
import com.liferay.portal.osgi.web.wab.generator.internal.handler.WabURLStreamHandlerService;
import com.liferay.portal.osgi.web.wab.generator.internal.processor.WabProcessor;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletContext;

import org.apache.felix.fileinstall.ArtifactUrlTransformer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	service = com.liferay.portal.osgi.web.wab.generator.WabGenerator.class
)
public class WabGenerator
	implements com.liferay.portal.osgi.web.wab.generator.WabGenerator {

	@Override
	public File generate(
			ClassLoader classLoader, File file,
			Map<String, String[]> parameters)
		throws IOException {

		WabProcessor wabProcessor = new WabProcessor(
			classLoader, file, parameters);

		return wabProcessor.getProcessedFile();
	}

	@Activate
	public void start(BundleContext bundleContext) throws Exception {
		registerURLStreamHandlerService(bundleContext);

		registerArtifactUrlTransformer(bundleContext);

		final Set<String> requiredForStartupContextPaths =
			getRequiredForStartupContextPaths(
				Paths.get(PropsValues.LIFERAY_HOME, "osgi/war"));

		if (requiredForStartupContextPaths.isEmpty()) {
			return;
		}

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		BundleTracker<Void> bundleTracker = new BundleTracker<Void>(
			bundleContext, Bundle.ACTIVE, null) {

			@Override
			public Void addingBundle(Bundle bundle, BundleEvent bundleEvent) {
				String location = bundle.getLocation();

				if (_log.isDebugEnabled()) {
					_log.debug("Activated bundle " + location);
				}

				if (requiredForStartupContextPaths.remove(
						_http.getParameter(
							location, "Web-ContextPath", false))) {

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Bundle " + location + " is required for startup");
					}

					if (requiredForStartupContextPaths.isEmpty()) {
						countDownLatch.countDown();
					}
				}

				return null;
			}

		};

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Bundles required for startup: " +
					requiredForStartupContextPaths);
		}

		bundleTracker.open();

		while (true) {
			if (countDownLatch.await(1, TimeUnit.MINUTES)) {
				break;
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Waiting on startup required bundles to activate: " +
						requiredForStartupContextPaths);
			}
		}

		bundleTracker.close();

		if (_log.isDebugEnabled()) {
			_log.debug("All startup required bundles are active");
		}
	}

	@Deactivate
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceRegistration.unregister();

		_serviceRegistration = null;
	}

	protected Set<String> getRequiredForStartupContextPaths(Path path)
		throws IOException {

		Set<String> contextPaths = new HashSet<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				path.toRealPath(), "*.war")) {

			for (Path warPath : directoryStream) {
				URI uri = warPath.toUri();

				try (ZipFile zipFile = new ZipFile(new File(uri));
					InputStream inputStream = zipFile.getInputStream(
						new ZipEntry(
							"WEB-INF/liferay-plugin-package.properties"))) {

					if (inputStream == null) {
						continue;
					}

					Properties properties = new Properties();

					properties.load(inputStream);

					if (!Boolean.valueOf(
							properties.getProperty("required-for-startup"))) {

						continue;
					}

					URL url = ArtifactURLUtil.transform(uri.toURL());

					contextPaths.add(
						_http.getParameter(
							url.toString(), "Web-ContextPath", false));
				}
			}
		}

		return contextPaths;
	}

	protected void registerArtifactUrlTransformer(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			ArtifactUrlTransformer.class,
			new WarArtifactUrlTransformer(_portalIsReady), null);
	}

	protected void registerURLStreamHandlerService(
		BundleContext bundleContext) {

		Bundle bundle = bundleContext.getBundle(0);

		Class<?> clazz = bundle.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			URLConstants.URL_HANDLER_PROTOCOL, new String[] {"webbundle"});

		bundleContext.registerService(
			URLStreamHandlerService.class.getName(),
			new WabURLStreamHandlerService(classLoader, this), properties);
	}

	/**
	 * This reference is held to force a dependency on the portal's complete
	 * startup.
	 */
	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(original.bean=true)(bean.id=javax.servlet.ServletContext))"
	)
	protected void setServletContext(ServletContext servletContext) {
		_portalIsReady.set(true);
	}

	protected void unsetModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	protected void unsetServletContext(ServletContext servletContext) {
		_portalIsReady.set(false);
	}

	private static final Log _log = LogFactoryUtil.getLog(WabGenerator.class);

	@Reference
	private Http _http;

	private final AtomicBoolean _portalIsReady = new AtomicBoolean();
	private ServiceRegistration<ArtifactUrlTransformer> _serviceRegistration;

}