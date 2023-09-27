/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.bean;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.internal.cluster.ClusterableAdvice;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

/**
 * @author Michael Young
 * @author Shuyang Zhou
 */
public class BeanReferenceAnnotationBeanPostProcessor
	implements BeanPostProcessor {

	public BeanReferenceAnnotationBeanPostProcessor(BeanFactory beanFactory) {
		_beanFactory = beanFactory;
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

		if (!(bean instanceof IdentifiableOSGiService) &&
			beanName.endsWith("Service") && _log.isWarnEnabled()) {

			_log.warn(
				StringBundler.concat(
					beanName, " should implement ",
					IdentifiableOSGiService.class.getName(), " for ",
					ClusterableAdvice.class.getName()));
		}

		_autoInject(bean, beanName, bean.getClass());

		return bean;
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
				catch (NoSuchBeanDefinitionException
							noSuchBeanDefinitionException) {

					try {
						referencedBean = PortalBeanLocatorUtil.locate(
							referencedBeanName);
					}
					catch (BeanLocatorException beanLocatorException) {
						StringWriter stringWriter = new StringWriter();

						try (PrintWriter printWriter = new PrintWriter(
								stringWriter)) {

							printWriter.print(
								"BeanFactory could not find bean: ");

							noSuchBeanDefinitionException.printStackTrace(
								printWriter);

							printWriter.print(
								" and PortalBeanLocator failed with: ");
							printWriter.append(
								beanLocatorException.getMessage());
						}

						throw new BeanLocatorException(
							stringWriter.toString(), beanLocatorException);
					}
				}

				_beans.put(referencedBeanName, referencedBean);
			}

			ReflectionUtils.makeAccessible(field);

			try {
				field.set(targetBean, referencedBean);
			}
			catch (Throwable throwable) {
				throw new BeanCreationException(
					targetBeanName, "Could not inject BeanReference fields",
					throwable);
			}
		}

		_autoInject(targetBean, targetBeanName, beanClass.getSuperclass());
	}

	private static final String _JAVA_LANG_OBJECT = "java.lang.Object";

	private static final String _ORG_SPRINGFRAMEWORK = "org.springframework";

	private static final Log _log = LogFactoryUtil.getLog(
		BeanReferenceAnnotationBeanPostProcessor.class);

	private final BeanFactory _beanFactory;
	private final Map<String, Object> _beans = new HashMap<>();

}