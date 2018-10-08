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

package com.liferay.portal.spring.extender.internal.context;

import com.liferay.osgi.felix.util.AbstractExtender;
import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.spring.bean.LiferayBeanFactory;
import com.liferay.portal.spring.extender.internal.bean.ApplicationContextServicePublisherUtil;
import com.liferay.portal.spring.extender.internal.classloader.BundleResolverClassLoader;

import java.io.IOException;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.felix.utils.extender.Extension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedProperties;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.w3c.dom.Document;

import org.xml.sax.InputSource;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class ParentModuleApplicationContextExtender extends AbstractExtender {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		start(bundleContext);
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) throws Exception {
		stop(bundleContext);
	}

	@Override
	protected void debug(Bundle bundle, String s) {
		if (_log.isDebugEnabled()) {
			_log.debug(s);
		}
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers.get("Liferay-Service") == null) {
			return null;
		}

		return new ParentModuleApplicationContextExtension(bundle);
	}

	@Override
	protected void error(String s, Throwable throwable) {
		_log.error(s, throwable);
	}

	@Reference(target = "(original.bean=true)", unbind = "-")
	protected void setInfrastructureUtil(
		InfrastructureUtil infrastructureUtil) {
	}

	@Override
	protected void warn(Bundle bundle, String s, Throwable throwable) {
		if (_log.isWarnEnabled()) {
			_log.warn(s, throwable);
		}
	}

	private static final String[] _PARENT_CONFIG_LOCATIONS =
		{"META-INF/spring/parent"};

	private static final Log _log = LogFactoryUtil.getLog(
		ParentModuleApplicationContextExtender.class);

	private static class CacheableURLResource extends UrlResource {

		private CacheableURLResource(URL url) {
			super(url);
		}

	}

	private static class CachingXmlBeanDefinitionReader
		extends XmlBeanDefinitionReader {

		@Override
		protected Document doLoadDocument(
				InputSource inputSource, Resource resource)
			throws Exception {

			if (!(resource instanceof CacheableURLResource)) {
				return super.doLoadDocument(inputSource, resource);
			}

			Document document = _documents.get(resource.getFilename());

			if (document == null) {
				document = super.doLoadDocument(inputSource, resource);

				_documents.put(resource.getFilename(), document);
			}

			return document;
		}

		private CachingXmlBeanDefinitionReader(
			BeanDefinitionRegistry beanDefinitionRegistry) {

			super(beanDefinitionRegistry);
		}

		private static final Map<String, Document> _documents =
			new ConcurrentReferenceValueHashMap<>(
				FinalizeManager.SOFT_REFERENCE_FACTORY);

	}

	private static class ParentModuleApplicationContext
		extends ModuleApplicationContext {

		@Override
		public Resource[] getResources(String locationPattern) {
			List<Resource> resources = new ArrayList<>();

			Enumeration<URL> enumeration = _extenderBundle.findEntries(
				locationPattern, "*.xml", false);

			while (enumeration.hasMoreElements()) {
				resources.add(
					new CacheableURLResource(enumeration.nextElement()));
			}

			enumeration = bundle.findEntries(locationPattern, "*.xml", false);

			if (enumeration != null) {
				while (enumeration.hasMoreElements()) {
					resources.add(new UrlResource(enumeration.nextElement()));
				}
			}

			return resources.toArray(new Resource[resources.size()]);
		}

		@Override
		public void refresh() {
			prepareRefresh();

			ConfigurableListableBeanFactory configurableListableBeanFactory =
				obtainFreshBeanFactory();

			prepareBeanFactory(configurableListableBeanFactory);

			try {
				initMessageSource();

				initApplicationEventMulticaster();

				configurableListableBeanFactory.freezeConfiguration();

				configurableListableBeanFactory.preInstantiateSingletons();

				finishRefresh();
			}
			catch (BeansException be) {
				destroyBeans();

				cancelRefresh(be);

				throw be;
			}
		}

		@Override
		protected DefaultListableBeanFactory createBeanFactory() {
			return new ParentModuleApplicationContextBeanFactory(
				getInternalParentBeanFactory());
		}

		@Override
		protected void loadBeanDefinitions(
				DefaultListableBeanFactory defaultListableBeanFactory)
			throws IOException {

			XmlBeanDefinitionReader xmlBeanDefinitionReader =
				new CachingXmlBeanDefinitionReader(defaultListableBeanFactory);

			xmlBeanDefinitionReader.setEntityResolver(
				new ResourceEntityResolver(this));
			xmlBeanDefinitionReader.setEnvironment(getEnvironment());
			xmlBeanDefinitionReader.setResourceLoader(this);

			initBeanDefinitionReader(xmlBeanDefinitionReader);

			loadBeanDefinitions(xmlBeanDefinitionReader);
		}

		private ParentModuleApplicationContext(
			Bundle bundle, Bundle extenderBundle) {

			super(
				bundle, new BundleResolverClassLoader(bundle, extenderBundle),
				_PARENT_CONFIG_LOCATIONS);

			_extenderBundle = extenderBundle;
		}

		private final Bundle _extenderBundle;

	}

	private static class ParentModuleApplicationContextBeanFactory
		extends LiferayBeanFactory {

		@Override
		public void preInstantiateSingletons() {
			for (String beanName : getBeanDefinitionNames()) {
				RootBeanDefinition rootBeanDefinition =
					getMergedLocalBeanDefinition(beanName);

				if (!rootBeanDefinition.isAbstract() &&
					rootBeanDefinition.isSingleton() &&
					!rootBeanDefinition.isLazyInit() &&
					!isFactoryBean(beanName)) {

					getBean(beanName);
				}
			}
		}

		@Override
		protected void applyPropertyValues(
			String beanName, BeanDefinition beanDefinition,
			BeanWrapper beanWrapper, PropertyValues propertyValues) {

			try {
				for (PropertyValue propertyValue :
						propertyValues.getPropertyValues()) {

					_applyPropertyValue(
						beanName, beanDefinition, beanWrapper, propertyValue);
				}
			}
			catch (ReflectiveOperationException roe) {
				throw new BeanCreationException(beanName, roe);
			}
		}

		private ParentModuleApplicationContextBeanFactory(
			BeanFactory parentBeanFactory) {

			super(parentBeanFactory);
		}

		private void _applyPropertyValue(
				String beanName, BeanDefinition beanDefinition,
				BeanWrapper beanWrapper, PropertyValue propertyValue)
			throws ReflectiveOperationException {

			String name = propertyValue.getName();

			Object value = _resolvePropertyValue(
				beanName, beanDefinition, propertyValue.getValue());

			String methodKey = beanName.concat(StringPool.DASH.concat(name));

			Method method = _methods.get(methodKey);

			if (method == null) {
				String setterMethodName = "set".concat(
					TextFormatter.format(name, TextFormatter.G));

				Class<?> beanClass = beanWrapper.getWrappedClass();

				for (Method currentMethod : beanClass.getMethods()) {
					if (setterMethodName.equals(currentMethod.getName())) {
						Class<?>[] parameterTypes =
							currentMethod.getParameterTypes();

						if ((parameterTypes.length == 1) &&
							parameterTypes[0].isAssignableFrom(
								value.getClass())) {

							method = currentMethod;

							_methods.put(methodKey, currentMethod);

							break;
						}
					}
				}
			}

			if (method == null) {
				throw new BeanCreationException(
					StringBundler.concat(
						"Could not find setter for {bean=", beanName,
						", property=", name, "}"));
			}

			method.invoke(beanWrapper.getWrappedInstance(), value);
		}

		private Object _resolvePropertyValue(
			String beanName, BeanDefinition beanDefinition, Object value) {

			if (value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference)value;

				value = getBean(beanReference.getBeanName());
			}
			else if (value instanceof BeanDefinitionHolder) {
				BeanDefinitionHolder beanDefinitionHolder =
					(BeanDefinitionHolder)value;

				RootBeanDefinition mergedBeanDefinition =
					getMergedBeanDefinition(
						beanDefinitionHolder.getBeanName(),
						beanDefinitionHolder.getBeanDefinition(),
						beanDefinition);

				String innerBeanName =
					mergedBeanDefinition.getBeanClassName() + "#0";

				registerContainedBean(beanName, innerBeanName);

				value = createBean(innerBeanName, mergedBeanDefinition, null);
			}
			else if (value instanceof ManagedProperties) {
				ManagedProperties managedProperties = (ManagedProperties)value;

				Properties properties = new Properties();

				for (Map.Entry<Object, Object> propEntry :
						managedProperties.entrySet()) {

					Object propKey = propEntry.getKey();
					Object propValue = propEntry.getValue();

					if (propKey instanceof TypedStringValue) {
						TypedStringValue typedStringValue =
							(TypedStringValue)propKey;

						propKey = typedStringValue.getValue();
					}

					if (propValue instanceof TypedStringValue) {
						TypedStringValue typedStringValue =
							(TypedStringValue)propValue;

						propValue = typedStringValue.getValue();
					}

					properties.put(propKey, propValue);
				}

				value = properties;
			}
			else if (value instanceof TypedStringValue) {
				TypedStringValue typedStringValue = (TypedStringValue)value;

				value = typedStringValue.getValue();
			}

			return value;
		}

		private static final Map<String, Method> _methods =
			new ConcurrentHashMap<>();

	}

	private class ParentModuleApplicationContextExtension implements Extension {

		@Override
		public void destroy() {
			ApplicationContextServicePublisherUtil.unregisterContext(
				_serviceRegistrations);

			ParentModuleApplicationContextHolder.removeApplicationContext(
				_bundle);
		}

		@Override
		public void start() throws Exception {
			BundleContext extenderBundleContext =
				ParentModuleApplicationContextExtender.this.getBundleContext();

			Bundle extenderBundle = extenderBundleContext.getBundle();

			ModuleApplicationContext moduleApplicationContext =
				new ParentModuleApplicationContext(_bundle, extenderBundle);

			moduleApplicationContext.refresh();

			ParentModuleApplicationContextHolder.setApplicationContext(
				_bundle, moduleApplicationContext);

			_serviceRegistrations =
				ApplicationContextServicePublisherUtil.registerContext(
					moduleApplicationContext, _bundle.getBundleContext(), true);
		}

		private ParentModuleApplicationContextExtension(Bundle bundle) {
			_bundle = bundle;
		}

		private final Bundle _bundle;
		private List<ServiceRegistration<?>> _serviceRegistrations;

	}

}