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

package com.liferay.portal.template.freemarker;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration;

import freemarker.core.Environment;
import freemarker.core.TemplateClassResolver;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.Execute;
import freemarker.template.utility.ObjectConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true
)
public class LiferayTemplateClassResolver implements TemplateClassResolver {

	@Override
	public Class<?> resolve(
			String className, Environment environment, Template template)
		throws TemplateException {

		if (className.equals(Execute.class.getName()) ||
			className.equals(ObjectConstructor.class.getName())) {

			throw new TemplateException(
				"Instantiating " + className + " is not allowed in the " +
					"template for security reasons",
				environment);
		}

		String[] restrictedClassNames = GetterUtil.getStringValues(
			_freemarkerEngineConfiguration.restrictedClasses());

		for (String restrictedClassName : restrictedClassNames) {
			if (restrictedClassName.equals(StringPool.STAR)) {
				throw new TemplateException(
					"Instantiating " + className + " is not allowed in the " +
						"template for security reasons",
					environment);
			}
			else if (restrictedClassName.endsWith(StringPool.STAR)) {
				restrictedClassName = restrictedClassName.substring(
					0, restrictedClassName.length() -1);

				if (className.startsWith(restrictedClassName)) {
					throw new TemplateException(
						"Instantiating " + className + " is not allowed in " +
							"the template for security reasons",
						environment);
				}
			}
			else if (className.equals(restrictedClassName)) {
				throw new TemplateException(
					"Instantiating " + className + " is not allowed in the " +
						"template for security reasons",
					environment);
			}
		}

		boolean allowed = false;

		String[] allowedClasseNames = GetterUtil.getStringValues(
			_freemarkerEngineConfiguration.allowedClasses());

		for (String allowedClassName : allowedClasseNames) {
			if (allowedClassName.equals(StringPool.STAR)) {
				allowed = true;
				break;
			}
			else if (allowedClassName.endsWith(StringPool.STAR)) {
				allowedClassName = allowedClassName.substring(
					0, allowedClassName.length() - 1);

				if (className.startsWith(allowedClassName)) {
					allowed = true;
					break;
				}
			}
			else if (allowedClassName.equals(className)) {
				allowed = true;
				break;
			}
		}

		if (allowed) {
			try {
				ClassLoader[] whiteListedClassLoaders =
					_whiteListedClassloaders.toArray(
						new ClassLoader[_whiteListedClassloaders.size()]);

				ClassLoader[] classLoaders = ArrayUtil.append(
					whiteListedClassLoaders,
					ClassLoaderUtil.getContextClassLoader());

				ClassLoader whiteListedAggregateClassLoader =
					AggregateClassLoader.getAggregateClassLoader(classLoaders);

				return Class.forName(
					className, true, whiteListedAggregateClassLoader);
			}
			catch (Exception e) {
				throw new TemplateException(e, environment);
			}
		}

		throw new TemplateException(
			"Instantiating " + className + " is not allowed in the template " +
				"for security reasons",
			environment);
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_freemarkerEngineConfiguration = Configurable.createConfigurable(
			FreeMarkerEngineConfiguration.class, properties);

		_classResolverBundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new ClassResolverBundleTrackerCustomizer());

		_classResolverBundleTracker.open();

		Set<ClassLoader> allowedClassLoaders = _findAllowedClassLoaders(
			_freemarkerEngineConfiguration.allowedClasses(), bundleContext);

		_whiteListedClassloaders.addAll(allowedClassLoaders);

		_whiteListedClassloaders.add(getClass().getClassLoader());
	}

	@Deactivate
	protected void deactivate() {
		_classResolverBundleTracker.close();
	}

	private Set<ClassLoader> _findAllowedClassLoaders(
		String allowedClass, BundleContext bundleContext) {

		Bundle bundle = bundleContext.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		Set<ClassLoader> classLoaders = new HashSet<>();

		List<BundleCapability> capabilities = bundleWiring.getCapabilities(
			BundleRevision.PACKAGE_NAMESPACE);

		for (BundleCapability capability : capabilities) {
			Map<String, Object> attributes = capability.getAttributes();

			String exportPackage = (String)attributes.get(
				BundleRevision.PACKAGE_NAMESPACE);

			if (allowedClass.equals(StringPool.STAR)) {
				continue;
			}
			else if (allowedClass.endsWith(StringPool.STAR)) {
				allowedClass = allowedClass.substring(
					0, allowedClass.length() - 1);

				if (exportPackage.startsWith(allowedClass)) {
					BundleRevision provider = capability.getRevision();

					Bundle providerBundle = provider.getBundle();

					BundleWiring providerBundleWiring =
						providerBundle.adapt(BundleWiring.class);

					classLoaders.add(providerBundleWiring.getClassLoader());
				}
			}
			else if (allowedClass.equals(exportPackage)) {
				BundleRevision revision = capability.getRevision();

				Bundle revisionBundle = revision.getBundle();

				BundleWiring providerBundleWiring = revisionBundle.adapt(
					BundleWiring.class);

				classLoaders.add(providerBundleWiring.getClassLoader());
			}
			else {
				String allowedClassPackage = allowedClass.substring(
					0, allowedClass.lastIndexOf("."));

				if (allowedClassPackage.equals(exportPackage)) {
					BundleRevision revision = capability.getRevision();

					Bundle revisionBundle = revision.getBundle();

					BundleWiring providerBundleWiring = revisionBundle.adapt(
						BundleWiring.class);

					classLoaders.add(providerBundleWiring.getClassLoader());
				}
			}
		}

		return classLoaders;
	}

	private Set<ClassLoader> _findAllowedClassLoaders(
		String[] allowedClasses, BundleContext bundleContext) {

		Set<ClassLoader> classLoaders = new HashSet<>();

		for (String allowedClass : allowedClasses) {
			classLoaders.addAll(
				_findAllowedClassLoaders(allowedClass, bundleContext));
		}

		return classLoaders;
	}

	private boolean _matchesClassName(
		String className, String matchedClassName) {

		if (className.equals(StringPool.STAR)) {
			return true;
		}
		else if (className.endsWith(StringPool.STAR)) {
			className = className.substring(0, className.length() - 1);

			if (matchedClassName.startsWith(className)) {
				return true;
			}
		}
		else if (className.equals(matchedClassName)) {
			return true;
		}
		else {
			String classNamePackage = matchedClassName.substring(
				0, matchedClassName.lastIndexOf("."));

			if (classNamePackage.equals(className)) {
				return true;
			}
		}

		return false;
	}

	private BundleTracker<ClassLoader> _classResolverBundleTracker;
	private volatile FreeMarkerEngineConfiguration
		_freemarkerEngineConfiguration;
	private Set<ClassLoader> _whiteListedClassloaders = new HashSet<>();

	private class ClassResolverBundleTrackerCustomizer
		implements BundleTrackerCustomizer<ClassLoader> {

		@Override
		public ClassLoader addingBundle(
			Bundle bundle, BundleEvent bundleEvent) {

			Set<ClassLoader> allowedClassLoaders = _findAllowedClassLoaders(
				_freemarkerEngineConfiguration.allowedClasses(),
				bundle.getBundleContext());

			_whiteListedClassloaders.addAll(allowedClassLoaders);

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			return bundleWiring.getClassLoader();
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent, ClassLoader classLoader) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent, ClassLoader classLoader) {

			if (_whiteListedClassloaders.contains(classLoader)) {
				_whiteListedClassloaders.remove(classLoader);
			}
		}

	}

}