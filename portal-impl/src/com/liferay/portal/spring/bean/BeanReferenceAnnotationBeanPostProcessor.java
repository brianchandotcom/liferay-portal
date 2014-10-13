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

package com.liferay.portal.spring.bean;

import com.liferay.portal.cluster.ClusterableAdvice;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

/**
 * @author Michael Young
 * @author Shuyang Zhou
 */
public class BeanReferenceAnnotationBeanPostProcessor
	implements BeanFactoryAware, BeanPostProcessor {

	public BeanReferenceAnnotationBeanPostProcessor() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating instance " + this.hashCode());
		}
	}

	public void destroy() {
		_beans.clear();
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
		throws BeansException {

		if (bean instanceof IdentifiableBean) {
			IdentifiableBean identifiableBean = (IdentifiableBean)bean;

			identifiableBean.setBeanIdentifier(beanName);
		}
		else if (beanName.endsWith("Service")) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					beanName + " should implement " +
						IdentifiableBean.class.getName() +
							" for " + ClusterableAdvice.class.getName());
			}
		}

		_autoInject(bean, beanName, bean.getClass());

		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		_beanFactory = beanFactory;
	}

	private void _autoInject(
		Object targetBean, String targetBeanName, Class<?> beanClass) {

		if ((beanClass == null) || beanClass.isInterface()) {
			return;
		}

		String className = beanClass.getName();

		if (className.equals(_JAVA_LANG_OBJECT) ||
			className.startsWith(_ORG_SPRINGFRAMEWORK)) {

			return;
		}

		Field[] fields = beanClass.getDeclaredFields();

		for (Field field : fields) {
			BeanReference beanReference = field.getAnnotation(
				BeanReference.class);

			String referencedBeanName = null;
			Class<?> referencedBeanType = null;

			if (beanReference != null) {
				referencedBeanName = beanReference.name();
				referencedBeanType = beanReference.type();
			}
			else {
				continue;
			}

			if (!Object.class.equals(referencedBeanType)) {
				referencedBeanName = referencedBeanType.getName();
			}

			Object referencedBean = _beans.get(referencedBeanName);

			if (referencedBean == null) {
				try {
					referencedBean = _beanFactory.getBean(referencedBeanName);
				}
				catch (NoSuchBeanDefinitionException nsbde) {
					try {
						referencedBean = PortalBeanLocatorUtil.locate(
							referencedBeanName);
					}
					catch (BeanLocatorException ble) {
						StringWriter stringWriter = new StringWriter();

						try (PrintWriter printWriter = new PrintWriter(
								stringWriter)) {

							printWriter.print(
								"BeanFactory could not find bean: ");

							nsbde.printStackTrace(printWriter);

							printWriter.print(
								" and PortalBeanLocator failed with: ");
							printWriter.append(ble.getMessage());
						}

						throw new BeanLocatorException(
							stringWriter.toString(), ble);
					}
				}

				_beans.put(referencedBeanName, referencedBean);
			}

			ReflectionUtils.makeAccessible(field);

			BeanReferenceRefreshUtil.registerRefreshPoint(
				targetBean, field, referencedBeanName);

			try {
				field.set(targetBean, referencedBean);
			}
			catch (Throwable t) {
				throw new BeanCreationException(
					targetBeanName, "Could not inject BeanReference fields", t);
			}
		}

		_autoInject(targetBean, targetBeanName, beanClass.getSuperclass());
	}

	private static final String _JAVA_LANG_OBJECT = "java.lang.Object";

	private static final String _ORG_SPRINGFRAMEWORK = "org.springframework";

	private static final Log _log = LogFactoryUtil.getLog(
		BeanReferenceAnnotationBeanPostProcessor.class);

	private BeanFactory _beanFactory;
	private final Map<String, Object> _beans = new HashMap<String, Object>();

}